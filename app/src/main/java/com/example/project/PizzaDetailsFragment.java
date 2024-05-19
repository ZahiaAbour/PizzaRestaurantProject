//package com.example.project;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link PizzaDetailsFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class PizzaDetailsFragment extends Fragment {
//
//    private static final String ARG_PIZZA_NAME = "pizza_name";
//    private static final String ARG_PIZZA_PRICE = "pizza_price";
//    private static final String ARG_PIZZA_URL = "pizza_image";
//
//    private String pizzaName;
//    private double pizzaPrice;
//    private String pizzaURL;
//
//    public PizzaDetailsFragment() {
//        // Required empty public constructor
//    }
//
//    public static PizzaDetailsFragment newInstance(String pizzaName, double pizzaPrice, String pizzaURL ) {
//        PizzaDetailsFragment fragment = new PizzaDetailsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PIZZA_NAME, pizzaName);
//        args.putDouble(ARG_PIZZA_PRICE, pizzaPrice);
//        args.putString(ARG_PIZZA_URL, pizzaURL);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            pizzaName = getArguments().getString(ARG_PIZZA_NAME);
//            pizzaPrice = getArguments().getDouble(ARG_PIZZA_PRICE);
//            pizzaURL = getArguments().getString(ARG_PIZZA_URL);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_pizza_details, container, false);
//
//        TextView pizzaNameTextView = view.findViewById(R.id.pizzaNameTextView);
//        TextView pizzaPriceTextView = view.findViewById(R.id.pizzaPriceTextView);
//        ImageView imageView = view.findViewById(R.id.Pizzaimage);
//
//        // Set the pizza name and price to the TextViews
//        pizzaNameTextView.setText(pizzaName);
//        pizzaPriceTextView.setText(String.valueOf(pizzaPrice));
//
//        // Load the pizza image from URL
//        ConnectionAsyncTask t = new ConnectionAsyncTask();
//        String imageURL = pizzaURL;
//        t.loadImageFromUrl(imageURL, imageView);
//
//        ImageButton addToFavoritesButton = view.findViewById(R.id.addToFavoritesButton);
//        addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
//            boolean isFavorite = false;
//
//            @Override
//            public void onClick(View v) {
//                isFavorite = !isFavorite;
//                addToFavoritesButton.setImageResource(isFavorite ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);
//                DataBaseHelper dataBaseHelper =new DataBaseHelper(getContext(),"DB",null,1);
//                Pizza new_pizza= new Pizza(pizzaName, pizzaPrice, pizzaURL);
//
//                dataBaseHelper.insertPizza(new_pizza);
//
//            }
//        });
//
//        return view;
//    }
//}
package com.example.project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class PizzaDetailsFragment extends Fragment {

    private static final String ARG_PIZZA_NAME = "pizza_name";
    private static final String ARG_PIZZA_PRICE = "pizza_price";
    private static final String ARG_PIZZA_URL = "pizza_image";
    private static final String ARG_PIZZA_CATEGORY = "pizza_category";
    private static final String ARG_PIZZA_ID = "pizza_id";

    private String pizzaName;
    private double pizzaPrice;
    private String pizzaURL;
    private String pizzaCategory;
    private int pizzaId;

    public PizzaDetailsFragment() {
        // Required empty public constructor
    }

    public static PizzaDetailsFragment newInstance(String pizzaName, double pizzaPrice, String pizzaURL, String pizzaCategory, int pizzaId) {
        PizzaDetailsFragment fragment = new PizzaDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PIZZA_NAME, pizzaName);
        args.putDouble(ARG_PIZZA_PRICE, pizzaPrice);
        args.putString(ARG_PIZZA_URL, pizzaURL);
        args.putString(ARG_PIZZA_CATEGORY, pizzaCategory);
        args.putInt(ARG_PIZZA_ID, pizzaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pizzaName = getArguments().getString(ARG_PIZZA_NAME);
            pizzaPrice = getArguments().getDouble(ARG_PIZZA_PRICE);
            pizzaURL = getArguments().getString(ARG_PIZZA_URL);
            pizzaCategory = getArguments().getString(ARG_PIZZA_CATEGORY);
            pizzaId = getArguments().getInt(ARG_PIZZA_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pizza_details, container, false);

        TextView pizzaNameTextView = view.findViewById(R.id.pizzaNameTextView);
        TextView pizzaPriceTextView = view.findViewById(R.id.pizzaPriceTextView);
        ImageView imageView = view.findViewById(R.id.Pizzaimage);
        ImageButton addToFavoritesButton = view.findViewById(R.id.addToFavoritesButton);

        // Set the pizza name and price to the TextViews
        pizzaNameTextView.setText(pizzaName);
        pizzaPriceTextView.setText(String.valueOf(pizzaPrice));

        // Load the pizza image from URL
        ConnectionAsyncTask t = new ConnectionAsyncTask();
        t.loadImageFromUrl(pizzaURL, imageView);

        // Check if the pizza is in favorites and set the button accordingly
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext(), "DB", null, 1);
        if (dataBaseHelper.isPizzaInFavorites(pizzaId)) {
            addToFavoritesButton.setImageResource(R.drawable.ic_heart_filled);
        } else {
            addToFavoritesButton.setImageResource(R.drawable.ic_heart_outline);
        }

        addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
            boolean isFavorite = dataBaseHelper.isPizzaInFavorites(pizzaId);

            @Override
            public void onClick(View v) {
                isFavorite = !isFavorite;
                addToFavoritesButton.setImageResource(isFavorite ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);
                Pizza newPizza = new Pizza(pizzaId, pizzaName, pizzaPrice, pizzaURL, pizzaCategory);

                if (isFavorite) {
                    long result = dataBaseHelper.insertPizza(newPizza);
                    if (result == -1) {
                        Toast.makeText(getContext(), "Failed to add to favorites", Toast.LENGTH_SHORT).show();
                        Log.e("PizzaDetailsFragment", "Failed to insert pizza into database");
                    } else {
                        Toast.makeText(getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                        Log.d("PizzaDetailsFragment", "Pizza inserted successfully");
                    }
                } else {
                    long result = dataBaseHelper.deletePizza(pizzaId);
                    if (result == -1) {
                        Toast.makeText(getContext(), "Failed to remove from favorites", Toast.LENGTH_SHORT).show();
                        Log.e("PizzaDetailsFragment", "Failed to delete pizza from database");
                    } else {
                        Toast.makeText(getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                        Log.d("PizzaDetailsFragment", "Pizza deleted successfully");
                    }
                }
            }
        });

        return view;
    }

}
