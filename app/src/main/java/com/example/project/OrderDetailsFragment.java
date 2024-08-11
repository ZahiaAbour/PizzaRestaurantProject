package com.example.project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OrderDetailsFragment extends Fragment {

//    private static final String ARG_ORDER_ID = "order_id";
//    private static final String ARG_ORDER_DETAILS = "order_details";


    private static final String ARG_PIZZA_NAME = "name";
    private static final String ARG_PIZZA_PRICE = "price";
    private static final String ARG_PIZZA_URL = "pizza_image";
    private static final String ARG_PIZZA_DATE = "date";
    private static final String ARG_PIZZA_QUANTITY = "quantity";
    private static final String ARG_PIZZA_SIZE = "size";

    private String pizzaName;
    private double price;
    private String pizzaURL;
    private String date;
    private String size;
    private int quantity;
    String user_email;

    public OrderDetailsFragment() {
        // Required empty public constructor
    }


    public static OrderDetailsFragment newInstance(String name, double price, String date, String size, int quantity, String url) {
        OrderDetailsFragment fragment = new OrderDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PIZZA_NAME, name);
        args.putDouble(ARG_PIZZA_PRICE, price);
        args.putString(ARG_PIZZA_URL, url);
        args.putString(ARG_PIZZA_DATE, date);
        args.putInt(ARG_PIZZA_QUANTITY, quantity);
        args.putString(ARG_PIZZA_SIZE, size);




        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pizzaName= getArguments().getString(ARG_PIZZA_NAME);
            price=getArguments().getDouble(ARG_PIZZA_PRICE);
            pizzaURL=getArguments().getString(ARG_PIZZA_URL);
            date=getArguments().getString(ARG_PIZZA_DATE);
            size=getArguments().getString(ARG_PIZZA_SIZE);
            quantity=getArguments().getInt(ARG_PIZZA_QUANTITY);


            Toast.makeText(getActivity(), "Data received: " + pizzaName, Toast.LENGTH_SHORT).show();

        }
    }


//    @Override
//    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
//
//        TextView orderNameTextView = view.findViewById(R.id.order_name_text_view);
//        TextView orderPriceTextView = view.findViewById(R.id.order_price_text_view);
//
//        TextView orderDateTextView = view.findViewById(R.id.order_date_text_view);
//        TextView orderSizeTextView = view.findViewById(R.id.order_size_text_view);
//        TextView orderQuantityTextView = view.findViewById(R.id.order_quantity_text_view);
//        ImageView orderUrlImageView = view.findViewById(R.id.order_image_view);
//
//
//        orderNameTextView.setText(pizzaName);
//        orderPriceTextView.setText(String.valueOf(price));
//        orderDateTextView.setText(date);
//        orderSizeTextView.setText(size);
//        orderQuantityTextView.setText(String.valueOf(quantity));
//
//
//
//
//        ConnectionAsyncTask t = new ConnectionAsyncTask();
//        t.loadImageFromUrl(pizzaURL, orderUrlImageView);
//
//        return view;
//    }
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    Log.d("OrderDetailsFragment", "onCreateView called");
    View view = inflater.inflate(R.layout.fragment_order_details, container, false);

    Log.d("OrderDetailsFragment", "Inflating view");

    TextView orderNameTextView = view.findViewById(R.id.order_name_text_view);
    TextView orderPriceTextView = view.findViewById(R.id.order_price_text_view);
    TextView orderDateTextView = view.findViewById(R.id.order_date_text_view);
    TextView orderSizeTextView = view.findViewById(R.id.order_size_text_view);
    TextView orderQuantityTextView = view.findViewById(R.id.order_quantity_text_view);
    ImageView orderUrlImageView = view.findViewById(R.id.order_image_view);

    Log.d("OrderDetailsFragment", "Setting text and image values");
    orderNameTextView.setText(pizzaName);
    orderPriceTextView.setText(String.valueOf(price));
    orderDateTextView.setText(date);
    orderSizeTextView.setText(size);
    orderQuantityTextView.setText(String.valueOf(quantity));

    Log.d("OrderDetailsFragment", "Loading image from URL: " + pizzaURL);
    ConnectionAsyncTask t = new ConnectionAsyncTask();
    t.loadImageFromUrl(pizzaURL, orderUrlImageView);

    Log.d("OrderDetailsFragment", "View setup complete");
    return view;
}

}
