package com.example.project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminOrders extends AppCompatActivity {

    private LinearLayout favoritesLayout;
    private SharedPreferences sharedPreferences;
    String user_email;

    private TextView noFavoritesTextView;
    ImageView nofavsImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_orders);
        favoritesLayout = findViewById(R.id.orders_layout);
        noFavoritesTextView = findViewById(R.id.noFavoritesTextView);
        nofavsImage= findViewById(R.id.cry);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        user_email = sharedPreferences.getString("email", "");
        Toast.makeText(AdminOrders.this, user_email, Toast.LENGTH_SHORT).show();

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
        Cursor allOrdersCursor = dataBaseHelper.getAllOrders();
        favoritesLayout.removeAllViews();
        if (allOrdersCursor == null || allOrdersCursor.getCount() == 0) {
            noFavoritesTextView.setVisibility(View.VISIBLE);
            nofavsImage.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No orders found", Toast.LENGTH_LONG).show();
            return;
        }


        LayoutInflater inflater = LayoutInflater.from(this);

        while (allOrdersCursor.moveToNext()) {
            View pizzaItemView = inflater.inflate(R.layout.pizza_fav_view, favoritesLayout, false);
            pizzaItemView.findViewById(R.id.order_button).setVisibility(View.GONE);
            pizzaItemView.findViewById(R.id.heart_button).setVisibility(View.GONE);

            pizzaItemView.findViewById(R.id.pizza_date).setVisibility(View.VISIBLE);
//            pizzaItemView.findViewById(R.id.old_price).setVisibility(View.VISIBLE);

            pizzaItemView.findViewById(R.id.new_price).setVisibility(View.VISIBLE);

//            pizzaItemView.findViewById(R.id.offer_discount).setVisibility(View.VISIBLE);
            pizzaItemView.findViewById(R.id.offer_quantity).setVisibility(View.VISIBLE);
            pizzaItemView.findViewById(R.id.offer_size).setVisibility(View.VISIBLE);
//            pizzaItemView.findViewById(R.id.offer_start).setVisibility(View.VISIBLE);
//            pizzaItemView.findViewById(R.id.offer_finish).setVisibility(View.VISIBLE);
//            pizzaItemView.findViewById(R.id.order_button).setVisibility(View.VISIBLE);
            pizzaItemView.findViewById(R.id.userInfo).setVisibility(View.VISIBLE);



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

            TextView pAfterTextView= pizzaItemView.findViewById(R.id.new_price);
            double priceAfter = allOrdersCursor.getDouble(allOrdersCursor.getColumnIndex("PRICE"));
            pAfterTextView.setText("  Price: $" +priceAfter);

            TextView quantitytTextView= pizzaItemView.findViewById(R.id.offer_quantity);
            int quantity = allOrdersCursor.getInt(allOrdersCursor.getColumnIndex("QUANTITY"));
            quantitytTextView.setText("Quantity: " +quantity);

            TextView sizeTextView = pizzaItemView.findViewById(R.id.offer_size);
            String size = allOrdersCursor.getString(allOrdersCursor.getColumnIndex("SIZE"));
            sizeTextView.setText(" Size: " + size);

            TextView userTextView = pizzaItemView.findViewById(R.id.userInfo);



            String e = allOrdersCursor.getString(allOrdersCursor.getColumnIndex("USER_EMAIL"));
            Cursor user = dataBaseHelper.findUser(e);
            String user_name="";
            String user_phone="";
            while (user.moveToNext()) {
                String first = user.getString(user.getColumnIndex("NAME"));
                String last=  user.getString(user.getColumnIndex("LAST_NAME"));
                user_name=first + " " + last;
                user_phone=user.getString(user.getColumnIndex("PHONE"));
            }
            userTextView.setText("Customer Name: " + user_name + "  \nphone number: " + user_phone);





            favoritesLayout.addView(pizzaItemView);
        }
        allOrdersCursor.close();
    }

    // Convert dp to pixels
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
