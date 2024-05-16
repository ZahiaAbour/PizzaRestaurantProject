package com.example.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class CustPizzaMenu extends AppCompatActivity {

    Button button;
    LinearLayout linearLayout;
    LinearLayout linearLayout2;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_pizza_menu);

        button = findViewById(R.id.button);
        linearLayout = findViewById(R.id.layout);
//        imageView = findViewById(R.id.imageView); // Add this line

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new ConnectionAsyncTask(CustPizzaMenu.this).execute("http://www.mocky.io/v2/5b4e6b4e3200002c009c2a44");
//                new ConnectionAsyncTask(CustPizzaMenu.this).execute("https://mocki.io/v1/7f002b85-a983-4107-846b-f5635717e164");
                new ConnectionAsyncTask(CustPizzaMenu.this).execute("https://18fbea62d74a40eab49f72e12163fe6c.api.mockbin.io/");

            }
        });



    }

    public void setButtonText(String text) {
        button.setText(text);
    }

//    public void fillPizzas(List<Pizza> pizzas) {
//        linearLayout.removeAllViews();
//        for (int i = 0; i < pizzas.size(); i++) {
//            TextView textView = new TextView(this);
//            textView.setText(pizzas.get(i).getName());
//
//
//
//
//            linearLayout.addView(textView);
//
//
//        }
//    }

//    public void fillPizzas(List<Pizza> pizzas) {
//        linearLayout.removeAllViews();
//        for (int i = 0; i < pizzas.size(); i++) {
//            // Create a horizontal LinearLayout to hold name and index
//            LinearLayout horizontalLayout = new LinearLayout(this);
//            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
//
//            // Create a TextView for pizza name
//            TextView nameTextView = new TextView(this);
//            nameTextView.setText(pizzas.get(i).getName());
//
//            // Create a TextView for pizza index (assuming you want to display the index)
//            TextView indexTextView = new TextView(this);
//            indexTextView.setText("\t\t"+ String.valueOf(i));
//
//            // Add the TextViews to the horizontal layout
//            horizontalLayout.addView(nameTextView);
//            horizontalLayout.addView(indexTextView);
//
//            // Add the horizontal layout to the main LinearLayout
//            linearLayout.addView(horizontalLayout);
//        }
//    }


/////////////////////////////////////////////////////////
    //////////////////////////////////   shows as Alert when clicked (pop ip)
//    private void showPizzaDetails(String name, String price) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        View dialogView = getLayoutInflater().inflate(R.layout.pizza_details, null);
//        builder.setView(dialogView);
//
//        TextView pizzaNameTextView = dialogView.findViewById(R.id.pizzaNameTextView);
//        TextView pizzaPriceTextView = dialogView.findViewById(R.id.pizzaPriceTextView);
//
//        pizzaNameTextView.setText(name);
//        pizzaPriceTextView.setText(price);
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//
//    public void fillPizzas(List<Pizza> pizzas) {
//        linearLayout.removeAllViews();
//        for (int i = 0; i < pizzas.size(); i++) {
//            final String pizzaName = pizzas.get(i).getName();
//            final String pizzaPrice = String.valueOf(i);
//
//            TextView textView = new TextView(this);
//            textView.setText(pizzaName);
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showPizzaDetails(pizzaName, pizzaPrice);
//                }
//            });
//
//            linearLayout.addView(textView);
//        }
//    }


////////////////////////////////////////// Fragments
public void fillPizzas(List<Pizza> pizzas) {
    linearLayout.removeAllViews();
    for (final Pizza pizza : pizzas) {
        TextView textView = new TextView(this);

        textView.setText(pizza.getName());
         textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showPizzaDetails(pizza.getName(), pizza.getPrice().toString());
//                String imageURL = "https://media.geeksforgeeks.org/wp-content/cdn-uploads/gfg_200x200-min.png";
                String imageURL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcToTsn6g4wVCs1KgTpt9TwuHpZnqtfBrzJ8zA&usqp=CAU";
                showPizzaDetails(pizza.getName(), "15example", imageURL);

            }
        });
        linearLayout.addView(textView);
    }
}





    private void showPizzaDetails(String name, String price, String url) {
        // Hide other views
        findViewById(R.id.mainTextView).setVisibility(View.GONE);
        findViewById(R.id.button).setVisibility(View.GONE);
        findViewById(R.id.layout).setVisibility(View.GONE);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        PizzaDetailsFragment fragment = PizzaDetailsFragment.newInstance(name, price, url);
        transaction.replace(R.id.fragment_container, fragment, "PizzaDetailsFragment");
        transaction.addToBackStack(null);

        transaction.commit();
    }





    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("PizzaDetailsFragment");
        if (fragment != null && ((Fragment) fragment).isVisible()) {

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.detach(fragment);
            transaction.commit();


            findViewById(R.id.mainTextView).setVisibility(View.VISIBLE);
            findViewById(R.id.button).setVisibility(View.VISIBLE);
            findViewById(R.id.layout).setVisibility(View.VISIBLE);
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

















    public void setProgress(boolean progress) {
        ProgressBar progressBar = (ProgressBar)
                findViewById(R.id.progressBar);
        if (progress) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}