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

//public class MainActivity extends AppCompatActivity  {
//    private Button signUpButton, signInButton;
//    public List<Pizza> pizzas;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Button getStarted = (Button) findViewById(R.id.connectButton);
//        getStarted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                new ConnectionAsyncTask().execute("https://mocki.io/v1/5d7971b4-6031-4e52-b421-159182328271");
//                Intent intent =new Intent(MainActivity.this,Regestration.class);
////                for (Pizza pizza : CustPizzaMenu.all_pizzas) {
////                    Log.d("PizzaInfo", "Pizza: " + pizza.getName());
////                }
////                Intent intent =new Intent(MainActivity.this,AdminSpecialOffers.class);
//
//
//                MainActivity.this.startActivity(intent);
//                finish();
//            }
//        });
//
//    }
//
//
//}


public class MainActivity extends AppCompatActivity {
    private Button signUpButton, signInButton;
    public List<Pizza> pizzas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getStarted = findViewById(R.id.connectButton);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ConnectionAsyncTask().execute("https://mocki.io/v1/5d7971b4-6031-4e52-b421-159182328271");
//                new ConnectionAsyncTask().execute("https://mocki.io/v1/15424d04-fecd-4c99-a436-8aa3f3c82535");
                // Add a delay before proceeding
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Log the pizza information after the delay
                        for (Pizza pizza : CustPizzaMenu.all_pizzas) {
                            Log.d("PizzaInfo", "Pizza: " + pizza.getName());
                        }

                        // Start the AdminSpecialOffers activity
//                        Intent intent = new Intent(MainActivity.this, CustSpecialOffers.class);
                        Intent intent = new Intent(MainActivity.this, AdminSpecialOffers.class);
//                        Intent intent = new Intent(MainActivity.this, Regestration.class);

                        MainActivity.this.startActivity(intent);
                        finish();
                    }
                }, 5000); // 5-second delay
            }
        });
    }

}


//package com.example.project;
//
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//import android.content.Intent;
//import android.os.Handler;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import java.util.List;
//
//public class MainActivity extends AppCompatActivity {
//    private boolean isTaskFinished = false;
//    private boolean isSuccess = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Button getStarted = findViewById(R.id.connectButton);
//        getStarted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ConnectionAsyncTask task = new ConnectionAsyncTask() {
//                    @Override
//                    protected void onPostExecute(String s) {
//                        super.onPostExecute(s);
//                        isSuccess = this.isSuccess();
//                        isTaskFinished = true;
//                    }
//                };
////                task.execute("https://mocki.io/v1/5d7971b4-6031-4e52-b421-159182328271");
//               task.execute( "https://mocki.io/v1/15424d04-fecd-4c99-a436-8aa3f3c82535");
//
//                // Check if the task is finished periodically
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (isTaskFinished) {
//                            if (isSuccess) {
//                                Intent intent = new Intent(MainActivity.this, Regestration.class);
//                                MainActivity.this.startActivity(intent);
//                                finish();
//                            } else {
//                                Toast.makeText(MainActivity.this, "Connection failed", Toast.LENGTH_LONG).show();
//                            }
//                        } else {
//                            handler.postDelayed(this, 5000); // Check again after 500ms
//                        }
//                    }
//                }, 5000); // Initial delay
//            }
//        });
//    }
//}
