//package com.example.project;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.database.Cursor;
//import android.graphics.Color;
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class CustOrders extends AppCompatActivity {
//
//    private LinearLayout favoritesLayout;
//    private SharedPreferences sharedPreferences;
//    String user_email;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cust_orders);
//        favoritesLayout = findViewById(R.id.OrdersLayout);
//
//        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        user_email = sharedPreferences.getString("email", "");
//        Toast.makeText(CustOrders.this, user_email, Toast.LENGTH_SHORT).show();
//
//
//        loadOrders();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        loadOrders();
//    }
//
//    @SuppressLint({"Range", "SetTextI18n"})
//    private void loadOrders() {
//        DataBaseHelper dataBaseHelper = new DataBaseHelper(this, "DB", null, 1);
////        Cursor allOrdersCursor = dataBaseHelper.getAllOrders();
//        Cursor allOrdersCursor = dataBaseHelper.getAllOrdersFromCustomer(user_email);
//        favoritesLayout.removeAllViews();
//        if (allOrdersCursor == null || allOrdersCursor.getCount() == 0) {
//            Toast.makeText(this, "No orders found", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        while (allOrdersCursor.moveToNext()) {
//            // Create a horizontal LinearLayout for each pizza
//            LinearLayout pizzaLayout = new LinearLayout(this);
//            pizzaLayout.setLayoutParams(new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//            ));
//            pizzaLayout.setOrientation(LinearLayout.HORIZONTAL);
//
//
//            ImageView imageView = new ImageView(this);
//            String imageUrl = allOrdersCursor.getString(allOrdersCursor.getColumnIndex("IMAGE_URL"));
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
//            nameTextView.setText("\t\t " + allOrdersCursor.getString(allOrdersCursor.getColumnIndex("NAME")));
//            nameTextView.setTextSize(25); // Set text size in sp
//            nameTextView.setTypeface(null, Typeface.BOLD); // Make text bold
//
//            // Create a TextView for the pizza price
//            TextView priceTextView = new TextView(this);
//            priceTextView.setText("\t\t Total Price: $" + allOrdersCursor.getDouble(allOrdersCursor.getColumnIndex("PRICE")));
//            priceTextView.setTextSize(20); // Set text size in sp
//
//            TextView quantityTextView = new TextView(this);
//            quantityTextView.setText("\t\t Quantity: " + allOrdersCursor.getString(allOrdersCursor.getColumnIndex("QUANTITY")));
//            quantityTextView.setTextSize(20); // Set text size in sp
//
//            TextView sizeTextView = new TextView(this);
//            sizeTextView.setText("\t\t In size: " + allOrdersCursor.getString(allOrdersCursor.getColumnIndex("SIZE")));
//            sizeTextView.setTextSize(20); // Set text size in sp
//
//            TextView dateTextView = new TextView(this);
//            dateTextView.setText("\t\t Order date: " + allOrdersCursor.getString(allOrdersCursor.getColumnIndex("ORDER_DATE")));
//            dateTextView.setTextSize(20); // Set text size in sp
//
//
//            // Add the TextViews to the textLayout
//            textLayout.addView(nameTextView);
//            textLayout.addView(priceTextView);
//            textLayout.addView(quantityTextView);
//            textLayout.addView(sizeTextView);
//            textLayout.addView(dateTextView);
//
//            // Add the textLayout to the pizzaLayout
//            pizzaLayout.addView(textLayout);
//
//
//            // Add the pizzaLayout to the favoritesLayout
//            favoritesLayout.addView(pizzaLayout);
//        }
//        allOrdersCursor.close();
//    }
//
//    // Convert dp to pixels
//    private int dpToPx(int dp) {
//        float density = getResources().getDisplayMetrics().density;
//        return Math.round(dp * density);
//    }
//}
//
//
//
//


package com.example.project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CustOrders extends AppCompatActivity {

    private LinearLayout favoritesLayout;
    private TextView noFavoritesTextView;
    ImageView nofavsImage;
    private SharedPreferences sharedPreferences;
    String user_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_orders);





        favoritesLayout = findViewById(R.id.orders_layout);
        noFavoritesTextView = findViewById(R.id.noFavoritesTextView);
        nofavsImage= findViewById(R.id.cry);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        user_email = sharedPreferences.getString("email", "");
//        Toast.makeText(CustOrders.this, user_email, Toast.LENGTH_SHORT).show();


        loadOrders();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadOrders();
    }

    @SuppressLint({"Range", "SetTextI18n"})
    private void loadOrders() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this, "DB", null, 1);
//        Cursor allOrdersCursor = dataBaseHelper.getAllOrders();
        Cursor allOrdersCursor = dataBaseHelper.getAllOrdersFromCustomer(user_email);
        favoritesLayout.removeAllViews();
        if (allOrdersCursor == null || allOrdersCursor.getCount() == 0) {
            noFavoritesTextView.setVisibility(View.VISIBLE);
            nofavsImage.setVisibility(View.VISIBLE);
            return;
        }


        LayoutInflater inflater = LayoutInflater.from(this);

        while (allOrdersCursor.moveToNext()) {

            View pizzaItemView = inflater.inflate(R.layout.pizza_fav_view, favoritesLayout, false);
            pizzaItemView.findViewById(R.id.order_button).setVisibility(View.GONE);
            pizzaItemView.findViewById(R.id.heart_button).setVisibility(View.GONE);

            pizzaItemView.findViewById(R.id.pizza_date).setVisibility(View.VISIBLE);

            ImageView imageView = pizzaItemView.findViewById(R.id.pizza_image);
            String imageUrl = allOrdersCursor.getString(allOrdersCursor.getColumnIndex("IMAGE_URL"));
            ConnectionAsyncTask t = new ConnectionAsyncTask();
            t.loadImageFromUrl(imageUrl, imageView);

            TextView nameTextView = pizzaItemView.findViewById(R.id.pizza_name);
            String name = allOrdersCursor.getString(allOrdersCursor.getColumnIndex("NAME"));
            nameTextView.setText(name);

            TextView dateTextView = pizzaItemView.findViewById(R.id.pizza_date);
            String date= allOrdersCursor.getString(allOrdersCursor.getColumnIndex("ORDER_DATE"));
            dateTextView.setText(date);


            double price_after = allOrdersCursor.getDouble(allOrdersCursor.getColumnIndex("PRICE"));
            int quantity=  allOrdersCursor.getInt(allOrdersCursor.getColumnIndex("QUANTITY"));
            String size= allOrdersCursor.getString(allOrdersCursor.getColumnIndex("SIZE"));
            String url= allOrdersCursor.getString(allOrdersCursor.getColumnIndex("IMAGE_URL"));


//
//            Button orderButton = pizzaItemView.findViewById(R.id.order_button);
//            ImageButton heartButton = pizzaItemView.findViewById(R.id.heart_button);
//
//            int pizzaId = allOrdersCursor.getInt(allOrdersCursor.getColumnIndex("ID"));



            pizzaItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(CustOrders.this, "pizza"+ name, (Toast.LENGTH_LONG)*3).show();
                    showOrderDetails(name,price_after,date, size, quantity, url);




                }
            });
            favoritesLayout.addView(pizzaItemView);
        }
        allOrdersCursor.close();
    }

    // Convert dp to pixels
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }



    private void showOrderDetails(String name, double price, String date, String size, int quantity, String url) {
        // Hide other views
        findViewById(R.id.orders_layout).setVisibility(View.GONE);


//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//
//        OrderDetailsFragment fragment = OrderDetailsFragment.newInstance(name, price, date, size, quantity, url);
//        transaction.replace(R.id.fragment_container, fragment, "OrderDetailsFragment");
//        transaction.addToBackStack(null);

        View fragmentContainer = findViewById(R.id.fragment_container);
        fragmentContainer.setVisibility(View.VISIBLE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        OrderDetailsFragment fragment = OrderDetailsFragment.newInstance(name, price, date, size, quantity, url);
        transaction.replace(R.id.fragment_container, fragment, "OrderDetailsFragment");
        transaction.addToBackStack(null);

        transaction.commit();



//        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("OrderDetailsFragment");
        if (fragment != null && ((Fragment) fragment).isVisible()) {

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.detach(fragment);
            transaction.commit();


            findViewById(R.id.orders_layout).setVisibility(View.VISIBLE);


            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }


}




