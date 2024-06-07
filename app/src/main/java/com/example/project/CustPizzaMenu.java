package com.example.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class CustPizzaMenu extends AppCompatActivity {

    Button button;
    LinearLayout linearLayout;
    LinearLayout linearLayout2;
    ImageView imageView;

    private Button  filterButton;

    private Spinner categorySpinner;
    private EditText priceEditText;
    public static List<Pizza> all_pizzas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_pizza_menu);

        button = findViewById(R.id.button);
        filterButton = findViewById(R.id.filterButton);
        linearLayout = findViewById(R.id.layout);
        categorySpinner = findViewById(R.id.categorySpinner);
        priceEditText = findViewById(R.id.priceEditText);
//        new ConnectionAsyncTask(CustPizzaMenu.this).execute("https://mocki.io/v1/5d7971b4-6031-4e52-b421-159182328271");

        fillPizzas(all_pizzas);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categorySpinner.setSelection(0);
                priceEditText.setText("");
                new ConnectionAsyncTask(CustPizzaMenu.this).execute("https://mocki.io/v1/5d7971b4-6031-4e52-b421-159182328271");

            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    applyFilters(all_pizzas);
            }
        });
    }



    public void setButtonText(String text) {
        button.setText(text);
    }


////////////////////////////////////////// Fragments
public  void fillPizzas(List<Pizza> pizzas) {
    linearLayout.removeAllViews();
    all_pizzas=pizzas;
    for (final Pizza pizza : pizzas) {
        TextView textView = new TextView(this);

        textView.setText(pizza.getName());
         textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPizzaDetails(pizza.getName(), pizza.getPrice(), pizza.getImageUrl(), pizza.getCategory(), pizza.getID());

            }
        });
        linearLayout.addView(textView);
    }

}





    private void showPizzaDetails(String name, double price, String url, String category, int id) {
        // Hide other views
        findViewById(R.id.mainTextView).setVisibility(View.GONE);
        findViewById(R.id.button).setVisibility(View.GONE);
        findViewById(R.id.layout).setVisibility(View.GONE);
        findViewById(R.id.filterButton).setVisibility(View.GONE);
        findViewById(R.id.priceEditText).setVisibility(View.GONE);
        findViewById(R.id.categorySpinner).setVisibility(View.GONE);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        PizzaDetailsFragment fragment = PizzaDetailsFragment.newInstance(name, price, url, category, id );
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
            findViewById(R.id.filterButton).setVisibility(View.VISIBLE);
            findViewById(R.id.priceEditText).setVisibility(View.VISIBLE);
            findViewById(R.id.categorySpinner).setVisibility(View.VISIBLE);

            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }


    public void applyFilters(List<Pizza> pizzas) {
        String selectedCategory = "ALL";
        String maxPriceString = "1000";
        double maxPrice = 1000;
        // Ensure pizzas is not null
        if (pizzas == null) {
            Toast.makeText(this, "No pizzas available to filter", Toast.LENGTH_SHORT).show();
            return;
        }

//        String selectedCategory = categorySpinner.getSelectedItem().toString();
//        String maxPriceString = priceEditText.getText().toString();
//        double maxPrice = Double.MAX_VALUE;

         selectedCategory = categorySpinner.getSelectedItem().toString();
         maxPriceString = priceEditText.getText().toString();
         maxPrice = Double.MAX_VALUE;

        // Attempt to parse the max price input, if any
        if (!maxPriceString.isEmpty()) {
            try {
                maxPrice = Double.parseDouble(maxPriceString);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid price input", Toast.LENGTH_SHORT).show();
                return;
            }
        }


        Log.d("CustPizzaMenu", "Selected category: " + selectedCategory);
        Log.d("CustPizzaMenu", "Max price: " + maxPrice);

        List<Pizza> filteredPizzas = new ArrayList<>();
        for (Pizza pizza : pizzas) {
            String pizzaCategory = pizza.getCategory();
            double pizzaPrice = pizza.getPrice();

            // Ensure pizzaCategory is not null
            if (pizzaCategory == null) {
                Log.w("CustPizzaMenu", "Skipping pizza with null category: " + pizza.getName());
                continue;
            }

            Log.d("CustPizzaMenu", "Checking pizza: " + pizza.getName());

            boolean matchesCategory = "All".equals(selectedCategory) || pizzaCategory.equals(selectedCategory);
            boolean matchesPrice = pizzaPrice >= maxPrice;

            Log.d("CustPizzaMenu", "Matches category: " + matchesCategory);
            Log.d("CustPizzaMenu", "Matches price: " + matchesPrice);

            // Add pizza if it matches either category or price or both
            if (matchesCategory || matchesPrice) {
                filteredPizzas.add(pizza);
            }
        }

        Log.d("CustPizzaMenu", "Filtered pizzas count: " + filteredPizzas.size());


        fillPizzas(filteredPizzas);

    }

















//    public void setProgress(boolean progress) {
//        ProgressBar progressBar = (ProgressBar)
//                findViewById(R.id.progressBar);
//        if (progress) {
//            progressBar.setVisibility(View.VISIBLE);
//        } else {
//            progressBar.setVisibility(View.GONE);
//        }
//    }

}