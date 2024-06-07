package com.example.project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {

    private DataBaseHelper dbHelper;
    private ArrayList<String> ordersList;
    private ArrayList<Integer> orderIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

//        dbHelper = new DataBaseHelper(this);
//        ordersList = new ArrayList<>();
//        orderIds = new ArrayList<>();
//
//        ListView listView = findViewById(R.id.listViewOrders);
//        fetchOrders();
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ordersList);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                int orderId = orderIds.get(position);
//                Intent intent = new Intent(OrderListActivity.this, OrderDetailsActivity.class);
//                intent.putExtra("order_id", orderId);
//                startActivity(intent);
//            }
//        });
    }

    private void fetchOrders() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataBaseHelper.TABLE_ORDERS,
                new String[]{DataBaseHelper.COLUMN_ORDER_ID, DataBaseHelper.COLUMN_ORDER_DATE, DataBaseHelper.COLUMN_ORDER_DETAILS},
                null, null, null, null,
                DataBaseHelper.COLUMN_ORDER_DATE + " DESC"
        );

        while (cursor.moveToNext()) {
            int orderId = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_ORDER_ID));
            String orderDate = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_ORDER_DATE));
            String orderDetails = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_ORDER_DETAILS));
            ordersList.add("Date: " + orderDate + "\nDetails: " + orderDetails);
            orderIds.add(orderId);
        }
        cursor.close();
        db.close();
    }
}
