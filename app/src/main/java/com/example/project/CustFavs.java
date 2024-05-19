package com.example.project;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CustFavs extends AppCompatActivity {

    private LinearLayout favoritesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_favs);

        favoritesLayout = findViewById(R.id.favoritesLayout);

        loadFavorites();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavorites();
    }


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
//            TextView textView = new TextView(this);
//            textView.setText(
//                    "Name: " + allPizzasCursor.getString(allPizzasCursor.getColumnIndex("NAME")) +
//                            "\nPrice: " + allPizzasCursor.getDouble(allPizzasCursor.getColumnIndex("PRICE")) +
//                            "\nImage URL: " + allPizzasCursor.getString(allPizzasCursor.getColumnIndex("IMAGE_URL")) +
//                            "\n\n"
//            );
//            favoritesLayout.addView(textView);
//        }
//        allPizzasCursor.close();
//    }
@SuppressLint("Range")

private void loadFavorites() {
    DataBaseHelper dataBaseHelper = new DataBaseHelper(this, "DB", null, 1);
    Cursor allPizzasCursor = dataBaseHelper.getAllFavs();
    favoritesLayout.removeAllViews();
    if (allPizzasCursor == null || allPizzasCursor.getCount() == 0) {
        Toast.makeText(this, "No favorites found", Toast.LENGTH_LONG).show();
        return;
    }

    while (allPizzasCursor.moveToNext()) {
        // Create a horizontal LinearLayout for each pizza
        LinearLayout pizzaLayout = new LinearLayout(this);
        pizzaLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        pizzaLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Create an ImageView for the pizza image
        ImageView imageView = new ImageView(this);
        // Set the image URL from the cursor
        String imageUrl = allPizzasCursor.getString(allPizzasCursor.getColumnIndex("IMAGE_URL"));
        ConnectionAsyncTask t = new ConnectionAsyncTask();
        t.loadImageFromUrl(imageUrl, imageView);

        // Set the size of the ImageView
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                dpToPx(90), // 20dp width
                dpToPx(150)  // 40dp height
        );
        imageView.setLayoutParams(params);

        pizzaLayout.addView(imageView);

        // Create a LinearLayout to hold the name and price
        LinearLayout textLayout = new LinearLayout(this);
        textLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        textLayout.setOrientation(LinearLayout.VERTICAL);

        // Create a TextView for the pizza name
        TextView nameTextView = new TextView(this);
//        nameTextView.setText("Name: " + allPizzasCursor.getString(allPizzasCursor.getColumnIndex("NAME")));
        nameTextView.setText("\t\t "+ allPizzasCursor.getString(allPizzasCursor.getColumnIndex("NAME")));
        nameTextView.setTextSize(25); // Set text size in sp
//        nameTextView.setTextColor(Color.BLACK); // Set text color
        nameTextView.setTypeface(null, Typeface.BOLD); // Make text bold


        // Create a TextView for the pizza price
        TextView priceTextView = new TextView(this);
//        priceTextView.setText("Price: " + allPizzasCursor.getDouble(allPizzasCursor.getColumnIndex("PRICE")));
        priceTextView.setText("\t\t $" + allPizzasCursor.getDouble(allPizzasCursor.getColumnIndex("PRICE")));
        priceTextView.setTextSize(20); // Set text size in sp
        // Add the TextViews to the textLayout
        textLayout.addView(nameTextView);
        textLayout.addView(priceTextView);

        // Add the textLayout to the pizzaLayout
        pizzaLayout.addView(textLayout);

        // Add the pizzaLayout to the favoritesLayout
        favoritesLayout.addView(pizzaLayout);
    }
    allPizzasCursor.close();
}

    // Convert dp to pixels
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }





}
