//package com.example.project;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class AdminSpecialOffers extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_admin_special_offers);
//        DataBaseHelper dataBaseHelper = new DataBaseHelper(this, "DB", null, 1);
//
//
//        Button getStarted = (Button) findViewById(R.id.addOffer);
//        Offer offer = new Offer();
//        offer.setDiscount(0.5);
//        offer.setPizza(new Pizza("pizza" ));
//        offer.getPizza().setPrice(15) ;
//        offer.setQuantity(3);
//        offer.setSize("Small");
//        offer.getPizza().setImageUrl("dddddddddddddddd");
//        offer.getPizza().setCategory("beef");
//
//
//        getStarted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dataBaseHelper.insertOffer(offer);
//
//            }
//        });
//
//
//
//
//    }
//}


package com.example.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AdminSpecialOffers extends AppCompatActivity {

    private Spinner spinner, sizeSpinner;
    private EditText quantityEditText, newPriceEditText;
    private TextView oldPriceTextView, finalPriceTextView;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_special_offers);
        dataBaseHelper = new DataBaseHelper(this, "DB", null, 1);

        spinner = findViewById(R.id.spinner);
        quantityEditText = findViewById(R.id.quantity);
        newPriceEditText = findViewById(R.id.discount);
        oldPriceTextView = findViewById(R.id.oldPrice);
        finalPriceTextView=findViewById(R.id.finalPrice);
        sizeSpinner= findViewById(R.id.sizeSpinner);
        Button addOfferButton = findViewById(R.id.addOffer);
        final String[] selectedSize = new String[1];
        final String[] quantityText = new String[1];

        // Assuming CustPizzaMenu.all_pizzas is a public static List<Pizza>
        List<Pizza> allPizzas = CustPizzaMenu.all_pizzas;
        if (allPizzas == null) {
            allPizzas = new ArrayList<>(); // Initialize to an empty list to avoid null pointer
        }

        List<String> pizzaNames = new ArrayList<>();
        for (Pizza pizza : allPizzas) {
            pizzaNames.add(pizza.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pizzaNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSize[0] = sizeSpinner.getSelectedItem().toString();
                updateOffer(selectedSize[0]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPizza = spinner.getSelectedItem().toString();
                updateOffer(selectedSize[0]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        quantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do something before text is changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do something as the text is being changed
                updateOffer(selectedSize[0]);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something after the text has been changed
                updateOffer(selectedSize[0]);
            }


        });


        newPriceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do something before text is changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do something as the text is being changed
                updateOffer(selectedSize[0]);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do something after the text has been changed
                updateOffer(selectedSize[0]);
            }


        });



        addOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                addOffer();
            }
        });
    }
    private void updateOffer(String selectedSize ) {
        double discount;
        String quantityStr = quantityEditText.getText().toString();
        String discountStr= newPriceEditText.getText().toString();
        if (!discountStr.isEmpty()){
            discount= Double.valueOf(discountStr);
            discount=discount/100;
        }
        else {
            discount=0;
        }
        if (!quantityStr.isEmpty()) {
            try {
                int quantity = Integer.parseInt(quantityStr);
                String selectedPizzaName = spinner.getSelectedItem().toString();
                Pizza selectedPizza = null;
                for (Pizza pizza : CustPizzaMenu.all_pizzas) {
                    if (pizza.getName().equals(selectedPizzaName)) {
                        selectedPizza = pizza;
                        break;
                    }
                }
                if (selectedPizza != null) {
                    Offer offer = new Offer();

                    offer.setPizza(selectedPizza);
                    offer.setPrice(selectedSize , quantity);
                    Log.d("AdminSpecialOffers", "********** offer " + offer.getPrice_after());
                    oldPriceTextView.setText(String.valueOf(offer.getPrice_after()));
                    offer.setDiscount(discount);
                    offer.setPrice(selectedSize , quantity);
                    finalPriceTextView.setText(String.valueOf(offer.getPrice_after()));
                }
            } catch (NumberFormatException e) {
                Log.e("EditText", "Invalid quantity input", e);
                oldPriceTextView.setText(""); // Clear the price if input is invalid
                finalPriceTextView.setText("");
            }
        } else {
            oldPriceTextView.setText(""); // Clear the price if the input is empty
            finalPriceTextView.setText(""); // Clear the price if the input is empty
        }
    }
    private void addOffer() {
        String selectedPizzaName = spinner.getSelectedItem().toString();
        String selectedSizeString= sizeSpinner.getSelectedItem().toString();
        String quantityString = quantityEditText.getText().toString();
        String newPriceString = newPriceEditText.getText().toString();
        String finalPriceString= finalPriceTextView.getText().toString();

        if (selectedPizzaName.isEmpty() || quantityString.isEmpty() || newPriceString.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity;
        double newPrice;
        double discount;

        try {
            quantity = Integer.parseInt(quantityString);
            discount = Double.parseDouble(newPriceString);
            newPrice= Double.parseDouble(finalPriceString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers for quantity and price", Toast.LENGTH_SHORT).show();
            return;
        }

        Pizza selectedPizza = null;
        for (Pizza pizza : CustPizzaMenu.all_pizzas) {
            if (pizza.getName().equals(selectedPizzaName)) {
                selectedPizza = pizza;
                break;
            }
        }

        if (selectedPizza == null) {
            Toast.makeText(this, "Selected pizza not found", Toast.LENGTH_SHORT).show();
            return;
        }

//        selectedPizza.setPrice(newPrice); // Update the price of the selected pizza

        Offer offer = new Offer();
        offer.setPizza(selectedPizza);
        offer.setQuantity(quantity);
        offer.setSize(selectedSizeString); // Set your size here
        offer.setDiscount(discount);
//        offer.setPrice(selectedSizeString, quantity);
        offer.setPrice(newPrice);

        offer.getPizza().setImageUrl(selectedPizza.getImageUrl()); // Set your image URL here
        offer.getPizza().setCategory(selectedPizza.getCategory()); // Set your category here





        dataBaseHelper.insertOffer(offer);
        Toast.makeText(this, "Offer added successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AdminSpecialOffers.this, CustSpecialOffers.class);
        AdminSpecialOffers.this.startActivity(intent);
        finish();
    }
}
