package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PizzaDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PizzaDetailsFragment extends Fragment {

    private static final String ARG_PIZZA_NAME = "pizza_name";
    private static final String ARG_PIZZA_PRICE = "pizza_price";

    private String pizzaName;
    private String pizzaPrice;

    public PizzaDetailsFragment() {
        // Required empty public constructor
    }

    public static PizzaDetailsFragment newInstance(String pizzaName, String pizzaPrice) {
        PizzaDetailsFragment fragment = new PizzaDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PIZZA_NAME, pizzaName);
        args.putString(ARG_PIZZA_PRICE, pizzaPrice);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pizzaName = getArguments().getString(ARG_PIZZA_NAME);
            pizzaPrice = getArguments().getString(ARG_PIZZA_PRICE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pizza_details, container, false);

        TextView pizzaNameTextView = view.findViewById(R.id.pizzaNameTextView);
        TextView pizzaPriceTextView = view.findViewById(R.id.pizzaPriceTextView);

        // Set the pizza name and price to the TextViews
        pizzaNameTextView.setText(pizzaName);
        pizzaPriceTextView.setText(pizzaPrice);

        return view;
    }
}
