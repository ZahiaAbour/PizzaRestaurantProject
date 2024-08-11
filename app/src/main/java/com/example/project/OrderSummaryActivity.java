package com.example.project;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class OrderSummaryActivity extends AppCompatActivity {

    TextView summaryTextView;
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        dbHelper = new DataBaseHelper(this, "DB", null, 1);

        summaryTextView = findViewById(R.id.summaryTextView);
//        dbHelper = new DataBaseHelper(this);

        displayOrderSummary();
    }
    @SuppressLint("Range")
    private void displayOrderSummary() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.getAllOrders();
//        Cursor cursor = db.query(
//                DataBaseHelper.TABLE_ORDERS,
//                new String[]{DataBaseHelper.COLUMN_PIZZA_TYPE, DataBaseHelper.COLUMN_QUANTITY, DataBaseHelper.COLUMN_PRICE},
//                null, null, null, null, null
//        );

        Map<String, OrderSummary> summaryMap = new HashMap<>();
        double totalIncome = 0;

        if (cursor != null) {
            while (cursor.moveToNext()) {

                String pizzaType = cursor.getString(cursor.getColumnIndex("CATEGORY"));
                int quantity = cursor.getInt(cursor.getColumnIndex("QUANTITY"));
                double price = cursor.getDouble(cursor.getColumnIndex("PRICE"));

                OrderSummary orderSummary = summaryMap.getOrDefault(pizzaType, new OrderSummary());
                orderSummary.incrementCount(quantity);
                orderSummary.addIncome(quantity * price);
                summaryMap.put(pizzaType, orderSummary);
            }
            cursor.close();
        }
        db.close();

        StringBuilder summaryText = new StringBuilder();
        for (Map.Entry<String, OrderSummary> entry : summaryMap.entrySet()) {
            OrderSummary orderSummary = entry.getValue();
            summaryText.append("Pizza Type: ").append(entry.getKey())
                    .append("\n# pizzas : ").append(orderSummary.getCount())
                    .append(String.format("\nIncome: $%.2f", orderSummary.getIncome()))
                    .append("\n\n");
            totalIncome += orderSummary.getIncome();
        }

        summaryText.append(String.format("Total Income: $%.2f", totalIncome));

        summaryTextView.setText(summaryText.toString());
    }

    private static class OrderSummary {
        private int count;
        private double income;

        public void incrementCount(int quantity) {
            count += quantity;
        }

        public void addIncome(double amount) {
            income += amount;
        }

        public int getCount() {
            return count;
        }

        public double getIncome() {
            return income;
        }
    }
}