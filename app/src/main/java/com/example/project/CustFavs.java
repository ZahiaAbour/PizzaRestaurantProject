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
            nameTextView.setText("\t\t " + allPizzasCursor.getString(allPizzasCursor.getColumnIndex("NAME")));
            nameTextView.setTextSize(25); // Set text size in sp
            nameTextView.setTypeface(null, Typeface.BOLD); // Make text bold

            // Create a TextView for the pizza price
            TextView priceTextView = new TextView(this);
            priceTextView.setText("\t\t $" + allPizzasCursor.getDouble(allPizzasCursor.getColumnIndex("PRICE")));
            priceTextView.setTextSize(20); // Set text size in sp

            // Add the TextViews to the textLayout
            textLayout.addView(nameTextView);
            textLayout.addView(priceTextView);

            // Add the textLayout to the pizzaLayout
            pizzaLayout.addView(textLayout);

            // Create an ImageButton for the heart icon
            ImageButton heartButton = new ImageButton(this);
            heartButton.setImageResource(R.drawable.ic_heart_filled); // Default to filled heart
            heartButton.setBackgroundColor(Color.TRANSPARENT); // Remove background

            // Set the layout parameters for the heart button
            LinearLayout.LayoutParams heartParams = new LinearLayout.LayoutParams(
                    dpToPx(40),
                    dpToPx(40)
            );
            heartButton.setLayoutParams(heartParams);

            // Get the pizza ID for the current row
            int pizzaId = allPizzasCursor.getInt(allPizzasCursor.getColumnIndex("ID"));

            // Set onClickListener for the heart button
            heartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Remove the pizza from favorites
                    dataBaseHelper.delete_Pizza_from_favs(pizzaId);
                    loadFavorites(); // Refresh the favorites list
                }
            });

            // Add the heart button to the pizzaLayout
            pizzaLayout.addView(heartButton);

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
