package com.example.project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class CustFavs extends AppCompatActivity {

    private LinearLayout favoritesLayout;
    private SharedPreferences sharedPreferences;
    private TextView noFavoritesTextView;
    ImageView nofavsImage;
    String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_favs);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        user_email = sharedPreferences.getString("email", "");

        favoritesLayout = findViewById(R.id.favoritesLayout);
        noFavoritesTextView = findViewById(R.id.noFavoritesTextView);
        nofavsImage= findViewById(R.id.cry);

        loadFavorites();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavorites();
    }
    @SuppressLint("Range")
    private void loadFavorites() {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this, "DB", null, 1);
        Cursor allPizzasCursor = dataBaseHelper.getAllFavs(user_email);
        favoritesLayout.removeAllViews();
        if (allPizzasCursor == null || allPizzasCursor.getCount() == 0) {
            noFavoritesTextView.setVisibility(View.VISIBLE);
            nofavsImage.setVisibility(View.VISIBLE);

            return;
        }

        LayoutInflater inflater = LayoutInflater.from(this);
        while (allPizzasCursor.moveToNext()) {
            View pizzaItemView = inflater.inflate(R.layout.pizza_fav_view, favoritesLayout, false);



            ImageView imageView = pizzaItemView.findViewById(R.id.pizza_image);
            String imageUrl = allPizzasCursor.getString(allPizzasCursor.getColumnIndex("IMAGE_URL"));
            ConnectionAsyncTask t = new ConnectionAsyncTask();
            t.loadImageFromUrl(imageUrl, imageView);

            TextView nameTextView = pizzaItemView.findViewById(R.id.pizza_name);
            String name = allPizzasCursor.getString(allPizzasCursor.getColumnIndex("NAME"));
            nameTextView.setText(name);

//            TextView priceTextView = pizzaItemView.findViewById(R.id.pizza_price);
//            priceTextView.setText("$" + allPizzasCursor.getDouble(allPizzasCursor.getColumnIndex("PRICE")));

            Button orderButton = pizzaItemView.findViewById(R.id.order_button);
            ImageButton heartButton = pizzaItemView.findViewById(R.id.heart_button);

            int pizzaId = allPizzasCursor.getInt(allPizzasCursor.getColumnIndex("ID"));

            heartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataBaseHelper.delete_Pizza_from_favs(pizzaId, user_email);
                    loadFavorites();
                }
            });

            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pizzaName = name;
                    Toast.makeText(CustFavs.this, pizzaName, Toast.LENGTH_LONG).show();
                    View dialogView = getLayoutInflater().inflate(R.layout.pizza_details, null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(CustFavs.this);
                    builder.setView(dialogView);

                    TextView priceTextview = dialogView.findViewById(R.id.priceText);
                    Spinner sizeSpinner = dialogView.findViewById(R.id.sizeSpinner);
                    NumberPicker pick = dialogView.findViewById(R.id.picker1);

                    pick.setMinValue(1);
                    pick.setMaxValue(20);
                    pick.setWrapSelectorWheel(true);
                    pick.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                    Order order = new Order();
                    Pizza newPizza = new Pizza();

                    for (Pizza pizza : CustPizzaMenu.all_pizzas) {
                        if (pizza.getName().equals(pizzaName)) {
                            newPizza = pizza;
                            break;
                        }
                    }

                    if (newPizza == null) {
                        Toast.makeText(CustFavs.this, "Pizza " + pizzaName + " not found", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Pizza finalNewPizza = newPizza;
                    sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedSize = sizeSpinner.getSelectedItem().toString();
                            int quantity = pick.getValue();

                            order.setPizza(finalNewPizza);
                            order.setPrice(selectedSize, quantity);
                            order.setQuantity(quantity);
                            order.setSize(selectedSize);
                            order.setCust_email(user_email);
                            order.setOrderDate(new Date());

                            double price = order.getPrice();
                            priceTextview.setText(String.valueOf(price));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                    Pizza finalNewPizza1 = newPizza;
                    pick.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            String selectedSize = sizeSpinner.getSelectedItem().toString();
                            int quantity = pick.getValue();

                            order.setPizza(finalNewPizza1);
                            order.setPrice(selectedSize, quantity);
                            order.setQuantity(quantity);
                            order.setSize(selectedSize);
                            order.setCust_email(user_email);
                            order.setOrderDate(new Date());

                            double price = order.getPrice();
                            priceTextview.setText(String.valueOf(price));
                        }
                    });

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dataBaseHelper.insertOrder(order);
                            Toast.makeText(CustFavs.this, order.getPizza().getName(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            favoritesLayout.addView(pizzaItemView);
        }
        allPizzasCursor.close();
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}












//package com.example.project;
//
//import android.annotation.SuppressLint;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.SharedPreferences;
//import android.database.Cursor;
//import android.graphics.Color;
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.NumberPicker;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.util.Date;
//
//public class CustFavs extends AppCompatActivity {
//
//    private LinearLayout favoritesLayout;
//    private SharedPreferences sharedPreferences;
//    String user_email;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cust_favs);
//
//        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        user_email = sharedPreferences.getString("email", "");
//
//        favoritesLayout = findViewById(R.id.favoritesLayout);
//
//        loadFavorites();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        loadFavorites();
//    }
//
//    @SuppressLint("Range")
//    private void loadFavorites() {
//        DataBaseHelper dataBaseHelper = new DataBaseHelper(this, "DB", null, 1);
//        Cursor allPizzasCursor = dataBaseHelper.getAllFavs();
//        favoritesLayout.removeAllViews();
//        if (allPizzasCursor == null || allPizzasCursor.getCount() == 0) {
//            Toast.makeText(this, "No favorites found", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        while (allPizzasCursor.moveToNext()) {
//            // Create a horizontal LinearLayout for each pizza
//            LinearLayout pizzaLayout = new LinearLayout(this);
//            pizzaLayout.setLayoutParams(new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//            ));
//            pizzaLayout.setOrientation(LinearLayout.HORIZONTAL);
//
//            // Create an ImageView for the pizza image
//            ImageView imageView = new ImageView(this);
//            // Set the image URL from the cursor
//            String imageUrl = allPizzasCursor.getString(allPizzasCursor.getColumnIndex("IMAGE_URL"));
//            ConnectionAsyncTask t = new ConnectionAsyncTask();
//            t.loadImageFromUrl(imageUrl, imageView);
//
//            // Set the size of the ImageView
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    dpToPx(90), // Width in dp
//                    dpToPx(150)  // Height in dp
//            );
//            imageView.setLayoutParams(params);
//
//            pizzaLayout.addView(imageView);
//
//            // Create a LinearLayout to hold the name and price
//            LinearLayout textLayout = new LinearLayout(this);
//            textLayout.setLayoutParams(new LinearLayout.LayoutParams(
//                    0,
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    1.0f // Weight to distribute remaining space
//            ));
//            textLayout.setOrientation(LinearLayout.VERTICAL);
//
//            // Create a TextView for the pizza name
//            TextView nameTextView = new TextView(this);
//            String name= allPizzasCursor.getString(allPizzasCursor.getColumnIndex("NAME"));
////            nameTextView.setText("\t\t " + allPizzasCursor.getString(allPizzasCursor.getColumnIndex("NAME")));
//            nameTextView.setText(name);
//
//            nameTextView.setTextSize(25); // Set text size in sp
//            nameTextView.setTypeface(null, Typeface.BOLD); // Make text bold
//
//            // Create a TextView for the pizza price
//            TextView priceTextView = new TextView(this);
//            priceTextView.setText("\t\t $" + allPizzasCursor.getDouble(allPizzasCursor.getColumnIndex("PRICE")));
//            priceTextView.setTextSize(20); // Set text size in sp
//
//
//            Button orderButton= new Button(this);
//            orderButton.setText("order");
//            // Add the TextViews to the textLayout
//            textLayout.addView(nameTextView);
//            textLayout.addView(priceTextView);
//            textLayout.addView(orderButton);
//
//            // Add the textLayout to the pizzaLayout
//            pizzaLayout.addView(textLayout);
//
//            // Create an ImageButton for the heart icon
//            ImageButton heartButton = new ImageButton(this);
//            heartButton.setImageResource(R.drawable.ic_heart_filled); // Default to filled heart
//            heartButton.setBackgroundColor(Color.TRANSPARENT); // Remove background
//
//            // Set the layout parameters for the heart button
//            LinearLayout.LayoutParams heartParams = new LinearLayout.LayoutParams(
//                    dpToPx(40),
//                    dpToPx(40)
//            );
//            heartButton.setLayoutParams(heartParams);
//
//            // Get the pizza ID for the current row
//            int pizzaId = allPizzasCursor.getInt(allPizzasCursor.getColumnIndex("ID"));
//
//            // Set onClickListener for the heart button
//            heartButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Remove the pizza from favorites
//                    dataBaseHelper.delete_Pizza_from_favs(pizzaId);
//                    loadFavorites(); // Refresh the favorites list
//                }
//            });
//
//            // Add the heart button to the pizzaLayout
//            pizzaLayout.addView(heartButton);
//
//            // Add the pizzaLayout to the favoritesLayout
//            favoritesLayout.addView(pizzaLayout);
//
//
//
//
//            orderButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String pizzaName= name;
//                    Toast.makeText(CustFavs.this,pizzaName, (Toast.LENGTH_LONG)*3).show();
//                    View dialogView = getLayoutInflater().inflate(R.layout.pizza_details, null);
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(CustFavs.this);
//                    builder.setView(dialogView);
//
//                    TextView priceTextview = dialogView.findViewById(R.id.priceText);
//                    Spinner sizeSpinner= dialogView.findViewById(R.id.sizeSpinner);
//
//                    NumberPicker pick = dialogView.findViewById(R.id.picker1);
//                    pick.setMinValue(1); // Set the minimum value
//                    pick.setMaxValue(20); // Set the maximum value
//                    pick.setWrapSelectorWheel(true); // Enable circular scrolling
//                    pick.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
//
//                    Order order = new Order();
////                    Pizza newPizza = new Pizza(pizzaId, pizzaName, pizzaPrice, pizzaURL, pizzaCategory);
//                    Pizza newPizza = new Pizza();
////                    newPizza.setName(name);
//
////                    Pizza selectedPizza = null;
//                    for (Pizza pizza : CustPizzaMenu.all_pizzas) {
//                        if (pizza.getName().equals(pizzaName)) {
//                            newPizza = pizza;
//                            break;
//                        }
//                    }
//
//                    if (newPizza == null) {
//                        Toast.makeText(CustFavs.this, "Pizza " +pizzaName+ "not found", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//                    Pizza finalNewPizza = newPizza;
//                    sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            String selectedSize = sizeSpinner.getSelectedItem().toString();
//
//                            int quantity = pick.getValue();
//
////                            order.setPizza(finalNewPizza);
////                            order.setPrice(selectedSize, quantity);
////                            order.setQuantity(quantity);
////                            order.setSize(selectedSize);
////                            order.setCust_email(user_email);
//
//                            order.setPizza(finalNewPizza);
//                            Log.d("CustFavs", "Selected Pizza: " + finalNewPizza.getName() +" name (" + pizzaName+ ")");
//                            order.setPrice(selectedSize, quantity);
//                            Log.d("CustFavs", "Set Price: " + order.getPrice());
//                            order.setQuantity(quantity);
//                            Log.d("CustFavs", "Set Quantity: " + order.getQuantity());
//                            order.setSize(selectedSize);
//                            Log.d("CustFavs", "Set Size: " + order.getSize());
//                            order.setCust_email(user_email);
//                            Log.d("CustFavs", "Set Customer Email: " + user_email);
//                            order.setOrderDate(new Date());
//
//                            double price = order.getPrice();
//                            priceTextview.setText(String.valueOf(price));
//
//                        }
//
//
//
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//
//                    });
//
//                    Pizza finalNewPizza1 = newPizza;
//                    pick.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//                        @Override
//                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                            String selectedSize = sizeSpinner.getSelectedItem().toString();
//
//                            int quantity = pick.getValue();
//
//                            order.setPizza(finalNewPizza1);
//                            order.setPrice(selectedSize, quantity);
//                            order.setQuantity(quantity);
//                            order.setSize(selectedSize);
//                            order.setCust_email(user_email);
//                            order.setOrderDate(new Date());
//
//
//                            double price = order.getPrice();
//                            priceTextview.setText(String.valueOf(price));
//                        }
//                    });
//
//
//
//
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            dataBaseHelper.insertOrder(order);
//                            Toast.makeText(CustFavs.this, order.getPizza().getName(), Toast.LENGTH_SHORT).show();
//
//
////                        long result = dataBaseHelper.insertOrder(order);
////
////                        if (result == -1) {
////                            Toast.makeText(getContext(), "Failed to place order", Toast.LENGTH_SHORT).show();
////                            Log.e("PizzaDetailsFragment", "Failed to insert order into database");
////                        } else {
////                            Toast.makeText(getContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();
////                            Log.d("PizzaDetailsFragment", "Order inserted successfully");
////                        }
//                        }
//                    });
//
//                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // Handle the negative button click
//                        }
//                    });
//
//                    // Create the dialog
//                    AlertDialog dialog = builder.create();
//
//                    // Show the dialog
//                    dialog.show();
//                }
//            });
//
//
//
//
//        }
//        allPizzasCursor.close();
//    }
//
//    // Convert dp to pixels
//    private int dpToPx(int dp) {
//        float density = getResources().getDisplayMetrics().density;
//        return Math.round(dp * density);
//    }
//}
