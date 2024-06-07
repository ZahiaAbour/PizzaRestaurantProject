package com.example.project;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CustOrders extends AppCompatActivity {

    private LinearLayout favoritesLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_orders);
        favoritesLayout = findViewById(R.id.OrdersLayout);

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
        Cursor allOrdersCursor = dataBaseHelper.getAllOrders();
        favoritesLayout.removeAllViews();
        if (allOrdersCursor == null || allOrdersCursor.getCount() == 0) {
            Toast.makeText(this, "No orders found", Toast.LENGTH_LONG).show();
            return;
        }

        while (allOrdersCursor.moveToNext()) {
            // Create a horizontal LinearLayout for each pizza
            LinearLayout pizzaLayout = new LinearLayout(this);
            pizzaLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            pizzaLayout.setOrientation(LinearLayout.HORIZONTAL);


            ImageView imageView = new ImageView(this);
            String imageUrl = allOrdersCursor.getString(allOrdersCursor.getColumnIndex("IMAGE_URL"));
            ConnectionAsyncTask t = new ConnectionAsyncTask();
            t.loadImageFromUrl(imageUrl, imageView);

            // Set the size of the ImageView
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    dpToPx(90), // Width in dp
                    dpToPx(150)  // Height in dp
            );
            imageView.setLayoutParams(params);

            pizzaLayout.addView(imageView);

            // Create a LinearLayout to hold the name and price
            LinearLayout textLayout = new LinearLayout(this);
            textLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f // Weight to distribute remaining space
            ));
            textLayout.setOrientation(LinearLayout.VERTICAL);

            // Create a TextView for the pizza name
            TextView nameTextView = new TextView(this);
            nameTextView.setText("\t\t " + allOrdersCursor.getString(allOrdersCursor.getColumnIndex("NAME")));
            nameTextView.setTextSize(25); // Set text size in sp
            nameTextView.setTypeface(null, Typeface.BOLD); // Make text bold

            // Create a TextView for the pizza price
            TextView priceTextView = new TextView(this);
            priceTextView.setText("\t\t Total Price: $" + allOrdersCursor.getDouble(allOrdersCursor.getColumnIndex("PRICE")));
            priceTextView.setTextSize(20); // Set text size in sp

            TextView quantityTextView = new TextView(this);
            quantityTextView.setText("\t\t Quantity: " + allOrdersCursor.getString(allOrdersCursor.getColumnIndex("QUANTITY")));
            quantityTextView.setTextSize(20); // Set text size in sp

            TextView sizeTextView = new TextView(this);
            sizeTextView.setText("\t\t In size: " + allOrdersCursor.getString(allOrdersCursor.getColumnIndex("SIZE")));
            sizeTextView.setTextSize(20); // Set text size in sp


            // Add the TextViews to the textLayout
            textLayout.addView(nameTextView);
            textLayout.addView(priceTextView);
            textLayout.addView(quantityTextView);
            textLayout.addView(sizeTextView);

            // Add the textLayout to the pizzaLayout
            pizzaLayout.addView(textLayout);








            // Add the pizzaLayout to the favoritesLayout
            favoritesLayout.addView(pizzaLayout);
        }
        allOrdersCursor.close();
    }

    // Convert dp to pixels
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
