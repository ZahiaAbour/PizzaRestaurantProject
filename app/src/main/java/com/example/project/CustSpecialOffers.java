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

public class CustSpecialOffers extends AppCompatActivity {

    private LinearLayout favoritesLayout;
    private SharedPreferences sharedPreferences;
    String email;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_special_offers);
        favoritesLayout = findViewById(R.id.OffersLayout);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");

        loadOffers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadOffers();
    }

    @SuppressLint({"Range", "SetTextI18n"})
    private void loadOffers() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this, "DB", null, 1);
        dataBaseHelper.removeInvalidOffers();
        Cursor allOffersCursor = dataBaseHelper.getAllOffers();


        favoritesLayout.removeAllViews();
        if (allOffersCursor == null || allOffersCursor.getCount() == 0) {
            Toast.makeText(this, "No orders found", Toast.LENGTH_LONG).show();
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(this);


        while (allOffersCursor.moveToNext()) {
            View pizzaItemView = inflater.inflate(R.layout.pizza_fav_view, favoritesLayout, false);
            pizzaItemView.findViewById(R.id.order_button).setVisibility(View.GONE);
            pizzaItemView.findViewById(R.id.heart_button).setVisibility(View.GONE);

            pizzaItemView.findViewById(R.id.pizza_date).setVisibility(View.GONE);
            pizzaItemView.findViewById(R.id.old_price).setVisibility(View.VISIBLE);

            pizzaItemView.findViewById(R.id.new_price).setVisibility(View.VISIBLE);

            pizzaItemView.findViewById(R.id.offer_discount).setVisibility(View.VISIBLE);
            pizzaItemView.findViewById(R.id.offer_quantity).setVisibility(View.VISIBLE);
            pizzaItemView.findViewById(R.id.offer_size).setVisibility(View.VISIBLE);
            pizzaItemView.findViewById(R.id.offer_start).setVisibility(View.VISIBLE);
            pizzaItemView.findViewById(R.id.offer_finish).setVisibility(View.VISIBLE);
            pizzaItemView.findViewById(R.id.order_button).setVisibility(View.VISIBLE);


            ImageView imageView = pizzaItemView.findViewById(R.id.pizza_image);
            String imageUrl = allOffersCursor.getString(allOffersCursor.getColumnIndex("IMAGE_URL"));
            ConnectionAsyncTask t = new ConnectionAsyncTask();
            t.loadImageFromUrl(imageUrl, imageView);

            TextView nameTextView = pizzaItemView.findViewById(R.id.pizza_name);
            String name = allOffersCursor.getString(allOffersCursor.getColumnIndex("NAME"));
            nameTextView.setText(name);

            TextView pBeforeTextView= pizzaItemView.findViewById(R.id.old_price);
            double priceBefore = allOffersCursor.getDouble(allOffersCursor.getColumnIndex("PRICE_BEFORE"));
            pBeforeTextView.setText(" Old Price: $" +priceBefore);


            TextView pAfterTextView= pizzaItemView.findViewById(R.id.new_price);
            double priceAfter = allOffersCursor.getDouble(allOffersCursor.getColumnIndex("PRICE_AFTER"));
            pAfterTextView.setText(" New Price: $" +priceAfter);



            TextView discountTextView = pizzaItemView.findViewById(R.id.offer_discount);
            double discount = allOffersCursor.getDouble(allOffersCursor.getColumnIndex("DISCOUNT"));
            discountTextView.setText(" Discount: $" + discount);

            TextView quantityTextView = pizzaItemView.findViewById(R.id.offer_quantity);
            int quantity = allOffersCursor.getInt(allOffersCursor.getColumnIndex("QUANTITY"));
            quantityTextView.setText(" Quantity: " + quantity);


            TextView sizeTextView = pizzaItemView.findViewById(R.id.offer_size);
            String size = allOffersCursor.getString(allOffersCursor.getColumnIndex("SIZE"));
            sizeTextView.setText(" Size: " + size);


            TextView startTextView = pizzaItemView.findViewById(R.id.offer_start);
            String startDate = allOffersCursor.getString(allOffersCursor.getColumnIndex("OFFER_START"));
            startTextView.setText(" start Date: " + startDate);

            TextView finishTextView = pizzaItemView.findViewById(R.id.offer_finish);
            String finsihDate = allOffersCursor.getString(allOffersCursor.getColumnIndex("OFFER_FINISH"));
            finishTextView.setText(" End Date: " + finsihDate);









            Button orderButton = pizzaItemView.findViewById(R.id.order_button);



            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order order = new Order();
                    Pizza selectedPizza = new Pizza();
//                    newPizza.setName(name);

//                    Pizza selectedPizza = null;
                    for (Pizza pizza : CustPizzaMenu.all_pizzas) {
                        if (pizza.getName().equals(name)) {
                            selectedPizza = pizza;
                            break;
                        }
                    }
                    order.setPizza(selectedPizza);
                    order.setSize(size);
                    order.setQuantity(quantity);
                    order.setPrice(priceAfter);

                    order.setCust_email(email);
                    order.setOrderDate(new Date());
                    Toast.makeText(CustSpecialOffers.this, email, Toast.LENGTH_SHORT).show();
                    Toast.makeText(CustSpecialOffers.this, email, Toast.LENGTH_SHORT).show();

                    dataBaseHelper.insertOrder(order);
                    Toast.makeText(CustSpecialOffers.this, order.getPizza().getName(), Toast.LENGTH_SHORT).show();
                }
            });











            // Add the pizzaLayout to the favoritesLayout
            favoritesLayout.addView(pizzaItemView);
        }
        allOffersCursor.close();
    }

    // Convert dp to pixels
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }



}
