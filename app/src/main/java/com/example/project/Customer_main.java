package com.example.project;


import static com.example.project.R.id.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;


//public class Customer_main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
public class Customer_main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView imageViewProfilePicture;
    TextView textViewHeaderName, textViewHeaderEmail;
    Toolbar toolbar;
    private SharedPreferences sharedPreferences;
    private DataBaseHelper dataBaseHelper;



    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        dataBaseHelper = new DataBaseHelper(this, "DB", null, 1);



        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(nav_view);

        View headerView = navigationView.getHeaderView(0);
        textViewHeaderName = headerView.findViewById(R.id.textViewHeaderName);
        textViewHeaderEmail=headerView.findViewById(R.id.textViewHeaderEmail);
        imageViewProfilePicture=headerView.findViewById(R.id.imageViewProfilePicture);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        Toast.makeText(this,"this is it tt : " + email, (Toast.LENGTH_LONG)*4 ).show();
        String password = sharedPreferences.getString("password", "");
        Toast.makeText(this,"this is it tt : " + password, (Toast.LENGTH_LONG)*4 ).show();

//
//
//        Cursor CustomerCursor = dataBaseHelper.findCustomer(email, password);
//        if (CustomerCursor != null && CustomerCursor.moveToFirst()) {
//            textViewHeaderName.setText(CustomerCursor.getString(CustomerCursor.getColumnIndex("NAME")));
//
//        }
//        CustomerCursor.close();
//
//        textViewHeaderEmail.setText(email);


        Cursor CustomerCursor = dataBaseHelper.findCustomer(email, password);
        if (CustomerCursor != null && CustomerCursor.moveToFirst()) {
            String name = CustomerCursor.getString(CustomerCursor.getColumnIndex("NAME"));
            String profilePictureUri = CustomerCursor.getString(CustomerCursor.getColumnIndex("PROFILE_PICTURE_PATH"));

            textViewHeaderName.setText(name);
            textViewHeaderEmail.setText(email);

            // Load profile picture into ImageView
            if (!TextUtils.isEmpty(profilePictureUri)) {
                imageViewProfilePicture.setImageURI(Uri.parse(profilePictureUri));
            } else {
                // If no profile picture is available, you can set a default image
                imageViewProfilePicture.setImageResource(R.drawable.ic_default_profile);
            }
        }



        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        int id = menuItem.getItemId();
        if (id == R.id.c_home) {
            showToast("Item 1 clicked");
//            startActivity(new Intent(this, CustHome.class));
            startActivity(new Intent(this, Customer_main.class));

            overridePendingTransition(0, 0);
            return true;


        } else if (id == R.id.c_pizza) {
            showToast("Item 2 clicked");
            startActivity(new Intent(this, CustPizzaMenu.class));
            overridePendingTransition(0, 0);
            return true;

        } else if (id == R.id.c_fav) {
            showToast("Item 3 clicked");
            startActivity(new Intent(this, CustFavs.class));
            overridePendingTransition(0, 0);
            return true;

        }

        else if (id == R.id.c_profile) {
            showToast("Profile clicked");
            startActivity(new Intent(this, ProfileActivity.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (id == R.id.c_contact) {
            showToast("Contact Us clicked");
            startActivity(new Intent(this, ContactUsActivity.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (id == R.id.c_logout) {
            logout();
            return true;
        }
        else if (id == R.id.c_offers) {
            showToast("Special Offers clicked");
            startActivity(new Intent(this, CustSpecialOffers.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (id == R.id.c_orders) {
            showToast("My orders clicked");
            startActivity(new Intent(this, CustOrders.class));
            overridePendingTransition(0, 0);
            return true;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    private void logout() {
        // Clear any user session data or perform logout actions here
        // For example, if using SharedPreferences for session management, clear it:
        // SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
        // editor.clear().apply();

        // Navigate to the login activity
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Finish the current activity to prevent returning to it when pressing back
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}













//package com.example.project;
//
//import static com.example.project.R.id.*;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.core.view.GravityCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.Toolbar;
//
//import com.google.android.material.navigation.NavigationView;
//
//public class Customer_main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//
//    private static final int PERMISSION_REQUEST_CODE = 1;
//
//    DrawerLayout drawerLayout;
//    NavigationView navigationView;
//    ImageView imageViewProfilePicture;
//    TextView textViewHeaderName, textViewHeaderEmail;
//    Toolbar toolbar;
//    private SharedPreferences sharedPreferences;
//    private DataBaseHelper dataBaseHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_customer_main);
//        dataBaseHelper = new DataBaseHelper(this, "DB", null, 1);
//
//        drawerLayout = findViewById(R.id.drawer_layout);
//        navigationView = findViewById(nav_view);
//
//        View headerView = navigationView.getHeaderView(0);
//        textViewHeaderName = headerView.findViewById(R.id.textViewHeaderName);
//        textViewHeaderEmail = headerView.findViewById(R.id.textViewHeaderEmail);
//        imageViewProfilePicture = headerView.findViewById(R.id.imageViewProfilePicture);
//
//        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        String email = sharedPreferences.getString("email", "");
//        String password = sharedPreferences.getString("password", "");
//
//        loadCustomerData(email, password);
//
//        navigationView.setNavigationItemSelectedListener(this);
//
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//
//        imageViewProfilePicture.setOnClickListener(v -> {
//            if (checkPermission()) {
//                loadProfilePicture();
//            } else {
//                requestPermission();
//            }
//        });
//    }
//    @SuppressLint("Range")
//    private void loadCustomerData(String email, String password) {
//        Cursor customerCursor = dataBaseHelper.findCustomer(email, password);
//        if (customerCursor != null && customerCursor.moveToFirst()) {
//            String name = customerCursor.getString(customerCursor.getColumnIndex("NAME"));
//             String profilePictureUri = customerCursor.getString(customerCursor.getColumnIndex("PROFILE_PICTURE_PATH"));
//
//            textViewHeaderName.setText(name);
//            textViewHeaderEmail.setText(email);
//
//            // Load profile picture into ImageView
//            if (!TextUtils.isEmpty(profilePictureUri)) {
//                loadProfilePicture();
//            } else {
//                // If no profile picture is available, you can set a default image
//                imageViewProfilePicture.setImageResource(R.drawable.ic_default_profile);
//            }
//            customerCursor.close();
//        }
//    }
//
//    private void loadProfilePicture() {
//        String profilePictureUri = sharedPreferences.getString("profile_picture_uri", "");
//        if (!TextUtils.isEmpty(profilePictureUri)) {
//            imageViewProfilePicture.setImageURI(Uri.parse(profilePictureUri));
//        } else {
//            imageViewProfilePicture.setImageResource(R.drawable.ic_default_profile);
//        }
//    }
//
//    private boolean checkPermission() {
//        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
//        return result == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void requestPermission() {
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted
//                loadProfilePicture();
//            } else {
//                // Permission denied
//                Toast.makeText(this, "Permission Denied. Unable to load profile picture.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    @SuppressLint("NonConstantResourceId")
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//
//
//        int id = menuItem.getItemId();
//        if (id == R.id.c_home) {
//            showToast("Item 1 clicked");
////            startActivity(new Intent(this, CustHome.class));
//            startActivity(new Intent(this, Customer_main.class));
//
//            overridePendingTransition(0, 0);
//            return true;
//
//
//        } else if (id == R.id.c_pizza) {
//            showToast("Item 2 clicked");
//            startActivity(new Intent(this, CustPizzaMenu.class));
//            overridePendingTransition(0, 0);
//            return true;
//
//        } else if (id == R.id.c_fav) {
//            showToast("Item 3 clicked");
//            startActivity(new Intent(this, CustFavs.class));
//            overridePendingTransition(0, 0);
//            return true;
//
//        }
//
//        else if (id == R.id.c_profile) {
//            showToast("Profile clicked");
//            startActivity(new Intent(this, ProfileActivity.class));
//            overridePendingTransition(0, 0);
//            return true;
//        } else if (id == R.id.c_contact) {
//            showToast("Contact Us clicked");
//            startActivity(new Intent(this, ContactUsActivity.class));
//            overridePendingTransition(0, 0);
//            return true;
//        } else if (id == R.id.c_logout) {
//            logout();
//            return true;
//        }
//        else if (id == R.id.c_offers) {
//            showToast("Special Offers clicked");
//            startActivity(new Intent(this, CustSpecialOffers.class));
//            overridePendingTransition(0, 0);
//            return true;
//        } else if (id == R.id.c_orders) {
//            showToast("My orders clicked");
//            startActivity(new Intent(this, CustOrders.class));
//            overridePendingTransition(0, 0);
//            return true;
//        }
//        drawerLayout.closeDrawer(GravityCompat.START);
//        return false;
//    }
//
//    private void logout() {
//        // Clear any user session data or perform logout actions here
//        // For example, if using SharedPreferences for session management, clear it:
//        // SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
//        // editor.clear().apply();
//
//        // Navigate to the login activity
//        Intent intent = new Intent(this, Login.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        finish(); // Finish the current activity to prevent returning to it when pressing back
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    private void showToast(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }
//}