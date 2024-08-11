package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.content.Intent;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Button signUpButton, signInButton;
    public List<Pizza> pizzas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getStarted = findViewById(R.id.connectButton);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this, "DB", null, 1);

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new ConnectionAsyncTask().execute("https://mocki.io/v1/5d7971b4-6031-4e52-b421-159182328271");
                new ConnectionAsyncTask().execute("https://mocki.io/v1/15424d04-fecd-4c99-a436-8aa3f3c82535");
                // Add a delay before proceeding
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Log the pizza information after the delay
                        for (Pizza pizza : CustPizzaMenu.all_pizzas) {
                            Log.d("PizzaInfo", "Pizza: " + pizza.getName());
                        }
                        User admin = new User("admin", "admin",  "0591234567", "Male", "a@admin.com", "password");
                        dataBaseHelper.insertAdmin(admin);
                        dataBaseHelper.insertUser(admin);

                        // Start the AdminSpecialOffers activity
//                        Intent intent = new Intent(MainActivity.this, CustSpecialOffers.class);
//                        Intent intent = new Intent(MainActivity.this, AdminSpecialOffers.class);
                        Intent intent = new Intent(MainActivity.this, Regestration.class);
//                          Intent intent = new Intent(MainActivity.this, Customer_main.class);
//                        Intent intent = new Intent(MainActivity.this, CustPizzaMenu.class);

//                        Intent intent = new Intent(MainActivity.this, Admin_main.class);

                        MainActivity.this.startActivity(intent);
                        finish();
                    }
                }, 5000); // 5-second delay
            }
        });
    }

}


