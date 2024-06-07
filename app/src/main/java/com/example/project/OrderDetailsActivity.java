package com.example.project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OrderDetailsActivity extends AppCompatActivity {

    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        dbHelper = new DataBaseHelper(this);

        int orderId = getIntent().getIntExtra("order_id", -1);
        if (orderId != -1) {
            displayOrderDetails(orderId);
        }
    }

    private void displayOrderDetails(int orderId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataBaseHelper.TABLE_ORDERS,
                new String[]{DataBaseHelper.COLUMN_ORDER_DATE, DataBaseHelper.COLUMN_ORDER_DETAILS},
                DataBaseHelper.COLUMN_ORDER_ID + "=?",
                new String[]{String.valueOf(orderId)},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            String orderDate = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_ORDER_DATE));
            String orderDetails = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_ORDER_DETAILS));

            TextView textViewDate = findViewById(R.id.textViewOrderDate);
            TextView textViewDetails = findViewById(R.id.textViewOrderDetails);

            textViewDate.setText("Date: " + orderDate);
            textViewDetails.setText("Details: " + orderDetails);

            cursor.close();
        }
        db.close();
    }
}
