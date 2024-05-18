package com.example.project;


import static com.example.project.R.id.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

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
    TextView textView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

//        toolbar = (Toolbar) findViewById(R.id.CustomerToolbar);
//        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(nav_view);
//        Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_LONG).show();
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
            startActivity(new Intent(this, CustHome.class));
            overridePendingTransition(0, 0);
            return true;


        } else if (id == R.id.c_pizza) {
            showToast("Item 2 clicked");
            startActivity(new Intent(this, CustPizzaMenu.class));
            overridePendingTransition(0, 0);
            return true;

    } else if (id == R.id.c_profile) {
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
