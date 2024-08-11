package com.example.project;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "your_database_name.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_ORDER_DATE = "order_date";
    public static final String COLUMN_ORDER_DETAILS = "order_details";

    public static final String TABLE_FAVORITES = "favorites";
    public static final String COLUMN_FAVORITE_ID = "favorite_id";
    public static final String COLUMN_FAVORITE_NAME = "name";
    public static final String COLUMN_FAVORITE_PRICE = "price";
    public static final String COLUMN_FAVORITE_IMAGE_URL = "image_url";


    public DataBaseHelper(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        {

        }


    }

    //here
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Fav_Pizza(ID INTEGER PRIMARY  KEY,NAME TEXT, PRICE DOUBLE ,CATEGORY TEXT, IMAGE_URL TEXT, USER_EMAIL TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE Cust_Order (ID INTEGER PRIMARY KEY AUTOINCREMENT, CUSTOMER_ID INTEGER, NAME TEXT, PRICE DOUBLE, CATEGORY TEXT, IMAGE_URL TEXT, QUANTITY INTEGER, SIZE TEXT, USER_EMAIL TEXT, ORDER_DATE TEXT)");


        sqLiteDatabase.execSQL("CREATE TABLE Offers (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PRICE_BEFORE DOUBLE, CATEGORY TEXT, IMAGE_URL TEXT, QUANTITY INTEGER, SIZE TEXT, DISCOUNT DOUBLE, PRICE_AFTER DOUBLE, OFFER_START TEXT, OFFER_FINISH TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE Users (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, LAST_NAME TEXT, GENDER TEXT,  PHONE TEXT, EMAIL TEXT, PASSWORD TEXT, PROFILE_PICTURE_PATH TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE Admins (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, LAST_NAME TEXT,GENDER TEXT,PHONE TEXT, EMAIL TEXT, PASSWORD TEXT, PROFILE_PICTURE_PATH TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE Customers (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT,LAST_NAME TEXT, GENDER TEXT,PHONE TEXT, EMAIL TEXT, PASSWORD TEXT, PROFILE_PICTURE_PATH TEXT)");


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public long insertPizza(Pizza pizza, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID", pizza.getID());
        values.put("NAME", pizza.getName());
        values.put("PRICE", pizza.getPrice());
        values.put("IMAGE_URL", pizza.getImageUrl());
        values.put("CATEGORY", pizza.getCategory());
        values.put("USER_EMAIL", email);

        long result = db.insert("Fav_Pizza", null, values);
        if (result == -1) {
            Log.e("DataBaseHelper", "Failed to insert pizza");
        } else {
            Log.d("DataBaseHelper", "Pizza inserted successfully");
        }
        db.close();

        return result;
    }


    public long delete_Pizza_from_favs(int pizzaId, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result;
        try {
            String sql = "DELETE FROM Fav_Pizza WHERE ID = ? AND USER_EMAIL = ?";
            db.execSQL(sql, new Object[]{pizzaId, email});
            result = 1; // Successful deletion
        } catch (Exception e) {
            Log.e("DataBaseHelper", "Failed to delete pizza", e);
            result = -1; // Failed deletion
        } finally {
            db.close();
        }
        return result;
    }

//    public boolean isPizzaInFavorites(int pizzaId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] projection = {"ID"};
//        String selection = "ID = ?";
//        String[] selectionArgs = {String.valueOf(pizzaId)};
//        Cursor cursor = db.query("Fav_Pizza", projection, selection, selectionArgs, null, null, null);
//        boolean isInFavorites = cursor.getCount() > 0;
//        cursor.close();
//        return isInFavorites;
//    }

    public boolean isPizzaInFavorites(int pizzaId, String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {"ID"};
        String selection = "ID = ? AND USER_EMAIL = ?";
        String[] selectionArgs = {String.valueOf(pizzaId), userEmail};
        Cursor cursor = db.query("Fav_Pizza", projection, selection, selectionArgs, null, null, null);
        boolean isInFavorites = cursor.getCount() > 0;
        cursor.close();
        return isInFavorites;
    }



    public Cursor getAllFavs(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] selectionArgs = {email};
        Log.d("DataBaseHelper", "this is ittttttttttt   " + email);
        return sqLiteDatabase.rawQuery("SELECT * FROM Fav_Pizza WHERE USER_EMAIL = ?", selectionArgs);

    }


    public Cursor getAllOrders() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        return sqLiteDatabase.rawQuery("SELECT * FROM Cust_Order WHERE CUSTOMER_ID= id", null);
        return sqLiteDatabase.rawQuery("SELECT * FROM Cust_Order", null);

    }

    public Cursor getAllOrdersFromCustomer(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] selectionArgs = {email};
        Log.d("DataBaseHelper", "this is ittttttttttt   " + email);
        return sqLiteDatabase.rawQuery("SELECT * FROM Cust_Order WHERE USER_EMAIL = ?", selectionArgs);
    }


    public long insertOrder(Order order) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("ID", order.getID());
        values.put("NAME", order.getPizza().getName());
        values.put("PRICE", order.getPrice());
        values.put("IMAGE_URL", order.getPizza().getImageUrl());
        values.put("CATEGORY", order.getPizza().getCategory());
        values.put("SIZE", order.getSize());
        values.put("QUANTITY", order.getQuantity());
        values.put("USER_EMAIL", order.getCust_email());
        //
        int date = order.getOrderDate().getDate();
        int month = order.getOrderDate().getMonth() + 1;
        int year = order.getOrderDate().getYear() + 1900;
        values.put("ORDER_DATE", "" + date + "-" + month + "-" + year);


        long result = db.insert("Cust_Order", null, values);
        if (result == -1) {
            Log.e("DataBaseHelper", "Failed to insert order");
        } else {
            Log.d("DataBaseHelper", "order inserted successfully");
        }
        db.close();

        return result;
    }


    public Cursor getAllOffers() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM Offers", null);

    }

    public long insertOffer(Offer offer) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("ID", order.getID());
        double priceBefore = (offer.getPrice_after() / (100 - offer.getDiscount())) * 100;

        values.put("NAME", offer.getPizza().getName());

        values.put("IMAGE_URL", offer.getPizza().getImageUrl());
        values.put("CATEGORY", offer.getPizza().getCategory());
        values.put("DISCOUNT", offer.getDiscount());
        values.put("PRICE_AFTER", Math.round(offer.getPrice_after() * 100.0) / 100.0);
        values.put("PRICE_BEFORE", Math.round(priceBefore * 100.0) / 100.0);
        values.put("SIZE", offer.getSize());
        values.put("QUANTITY", offer.getQuantity());



        int date = offer.getStart_date().getDate();
        int month = offer.getStart_date().getMonth() + 1;
        int year = offer.getStart_date().getYear() + 1900;
        values.put("OFFER_START", "" + date + "-" + month + "-" + year);
         date = offer.getEnd_date().getDate();
         month = offer.getEnd_date().getMonth() + 1;
         year = offer.getEnd_date().getYear() + 1900;

        values.put("OFFER_FINISH", "" + date + "-" + month + "-" + year);


        long result = db.insert("Offers", null, values);

        if (result == -1) {
            Log.e("DataBaseHelper", "Failed to insert order");
        } else {
            Log.d("DataBaseHelper", "offer inserted successfully");
            Log.d("DataBaseHelper", "Inserted Offer: " +
                    "NAME=" + offer.getPizza().getName() +
                    ", PRICE_BEFORE=" + priceBefore +
                    ", IMAGE_URL=" + offer.getPizza().getImageUrl() +
                    ", CATEGORY=" + offer.getPizza().getCategory() +
                    ", DISCOUNT=" + offer.getDiscount() +
                    ", SIZE=" + offer.getSize() +
                    ", QUANTITY=" + offer.getQuantity());
        }
        db.close();

        return result;
    }

    @SuppressLint("Range")
    public void removeInvalidOffers() {

        SQLiteDatabase db = getReadableDatabase();

        // Query to select all offers
        String query = "SELECT * FROM Offers";

        Cursor cursor = db.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                // Retrieve offer details from cursor
                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                String pizzaName = cursor.getString(cursor.getColumnIndex("NAME"));
                String offerStartStr = cursor.getString(cursor.getColumnIndex("OFFER_START"));
                String offerFinishStr = cursor.getString(cursor.getColumnIndex("OFFER_FINISH"));
                double priceAfter = cursor.getDouble(cursor.getColumnIndex("PRICE_AFTER"));
                double discount = cursor.getDouble(cursor.getColumnIndex("DISCOUNT"));
                int quantity = cursor.getInt(cursor.getColumnIndex("QUANTITY"));
                String size = cursor.getString(cursor.getColumnIndex("SIZE"));
                String imageUrl = cursor.getString(cursor.getColumnIndex("IMAGE_URL"));

                // Parse offer start date
                Date offerStartDate = parseDateString(offerStartStr);

                // Parse offer finish date
                Date offerFinishDate = parseDateString(offerFinishStr);

                // Check if offer finish date is today or in the future
                if (offerFinishDate != null && offerFinishDate.compareTo(new Date()) >= 0) {
                    // Create Offer object
//                    Pizza pizza = new Pizza(pizzaName); // Replace with your Pizza constructor
//                    pizza.setImageUrl(imageUrl); // Set image URL if needed
//
                    Offer offer = new Offer();
////                    offer.setId(id);
//                    offer.setPizza(pizza);
//                    offer.setStart_date(offerStartDate);
//                    offer.setEnd_date(offerFinishDate);
////                    offer.setPrice_after(priceAfter);
//                    offer.setDiscount(discount);
//                    offer.setQuantity(quantity);
//                    offer.setSize(size);
//
//                    // Add valid offer to list
//                    validOffers.add(offer);
                }

                else{
                    deleteOrder(id);

                }
            }
        } finally {
            cursor.close();
        }


    }



    public boolean deleteOrder(int orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete("Offers", "ID" + " = ?",
                new String[]{String.valueOf(orderId)});
        db.close();
        return deletedRows > 0;
    }

    // Method to parse date string in dd-MM-yyyy format to Date object
    private Date parseDateString(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            Log.e("DatabaseHelper", "Error parsing date: " + dateString, e);
            return null; // Return null if parsing fails
        }
    }




    public long insertUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("ID", order.getID());
        values.put("NAME", user.getName());
        values.put("PHONE", user.getPhone());
        values.put("EMAIL", user.getEmail());
        values.put("PASSWORD", user.getPassword());
        values.put("LAST_NAME", user.getLast_name());
        values.put("GENDER", user.getGender());


        long result = db.insert("Users", null, values);
        if (result == -1) {
            Log.e("DataBaseHelper", "Failed to sign up");
        } else {
            Log.d("DataBaseHelper", "sign up successfully");
        }
        db.close();

        return result;
    }


    public Cursor findUser(String email, String password) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        return sqLiteDatabase.rawQuery("SELECT * FROM Offers", null);
        String query = "SELECT * FROM Users WHERE EMAIL = ? AND PASSWORD = ?";
        String[] selectionArgs = {email, password};
        return sqLiteDatabase.rawQuery(query, selectionArgs);
    }

    public Cursor findUser(String email ) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        return sqLiteDatabase.rawQuery("SELECT * FROM Offers", null);
        String query = "SELECT * FROM Users WHERE EMAIL = ? ";
        String[] selectionArgs = {email};
        return sqLiteDatabase.rawQuery(query, selectionArgs);
    }


    public boolean insertAdmin(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", user.getName());
        contentValues.put("EMAIL", user.getEmail());
        contentValues.put("PHONE", user.getPhone());
        contentValues.put("PASSWORD", user.getPassword());
        contentValues.put("LAST_NAME", user.getLast_name());
        contentValues.put("GENDER", user.getGender());

        Log.e("DataBaseHelper", "Admin inserted");

        long result = db.insert("Admins", null, contentValues);
        db.close();
        return result != -1;
    }

    public boolean insertCustomer(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", user.getName());
        contentValues.put("PHONE", user.getPhone());
        contentValues.put("EMAIL", user.getEmail());
        contentValues.put("PASSWORD", user.getPassword());
        contentValues.put("LAST_NAME", user.getLast_name());
        contentValues.put("GENDER", user.getGender());


        long result = db.insert("Customers", null, contentValues);
        if (result == -1) {
            Log.e("DataBaseHelper", "Customer not inserted");
        } else {
            Log.d("DataBaseHelper", "Customer inserted");
        }
        db.close();
        return result != -1;
    }


    public void updateCustomer(String email, User newCustomer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", newCustomer.getName());
        values.put("PHONE", newCustomer.getPhone());
        values.put("GENDER", newCustomer.getGender());
        values.put("PROFILE_PICTURE_PATH", newCustomer.getProfilePicturePath()); // Save the path


        int rowsAffected = db.update("Customers", values, "EMAIL = ?", new String[]{email});
        db.close();

        if (rowsAffected > 0) {
            Log.d("DataBaseHelper", "Customer updated successfully");
        } else {
            Log.e("DataBaseHelper", "Failed to update customer");
        }
    }


    public void updateAdmin(String email, User newAdmin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", newAdmin.getName());
        values.put("LAST_NAME", newAdmin.getLast_name());
        values.put("PHONE", newAdmin.getPhone());
        values.put("GENDER", newAdmin.getGender());
        values.put("PROFILE_PICTURE_PATH", newAdmin.getProfilePicturePath()); // Save the path


        int rowsAffected = db.update("Admins", values, "EMAIL = ?", new String[]{email});
        db.close();

        if (rowsAffected > 0) {
            Log.d("DataBaseHelper", "Admin updated successfully");
        } else {
            Log.e("DataBaseHelper", "Failed to update Admin");
        }
    }

    public Cursor findCustomer(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Customers WHERE email = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        if (cursor != null) {
            Log.d("DataBaseHelper", "Cursor count: " + cursor.getCount());
        } else {
            Log.d("DataBaseHelper", "Cursor is null");
        }

        return cursor;
    }


    public Cursor findAdmin(String email, String password) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        return sqLiteDatabase.rawQuery("SELECT * FROM Offers", null);
        String query = "SELECT * FROM Admins WHERE EMAIL = ? AND PASSWORD = ?";
        String[] selectionArgs = {email, password};
        return sqLiteDatabase.rawQuery(query, selectionArgs);
    }


}

