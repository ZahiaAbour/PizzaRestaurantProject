package com.example.project;


import static com.example.project.R.id.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.io.IOException;


//public class Customer_main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
public class Admin_main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView ivProfilePicture;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView textViewHeaderName, textViewHeaderEmail;
    private Spinner spinnerGender;
    TextView textView;
    Toolbar toolbar;
    ImageView imageViewProfilePicture;
    private EditText etFirstName, etLastName, etPassword, etPhoneNumber;
    private Button btnSave, btnChangeProfilePicture;
    String email;
    private SharedPreferences sharedPreferences;
    private DataBaseHelper dataBaseHelper;
    private Uri profilePictureUri;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        dataBaseHelper = new DataBaseHelper(this, "DB", null, 1);


//        toolbar = (Toolbar) findViewById(R.id.CustomerToolbar);
//        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(nav_view);


        View headerView = navigationView.getHeaderView(0);
        textViewHeaderName = headerView.findViewById(R.id.textViewHeaderName);
        textViewHeaderEmail=headerView.findViewById(R.id.textViewHeaderEmail);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");


        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPassword = findViewById(R.id.etPassword);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        spinnerGender=findViewById(R.id.spinner3);
        btnSave = findViewById(R.id.btnSave);
        btnChangeProfilePicture = findViewById(R.id.btnChangeProfilePicture);
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        imageViewProfilePicture=headerView.findViewById(R.id.imageViewProfilePicture);


//        Cursor AdminCursor = dataBaseHelper.findAdmin(email, password);
//        if (AdminCursor != null && AdminCursor.moveToFirst()) {
//            textViewHeaderName.setText(AdminCursor.getString(AdminCursor.getColumnIndex("NAME")));
//
//        }
//        AdminCursor.close();
//
//        textViewHeaderEmail.setText(email);

        Cursor AdminCursor = dataBaseHelper.findAdmin(email, password);
        if (AdminCursor != null && AdminCursor.moveToFirst()) {
            String name = AdminCursor.getString(AdminCursor.getColumnIndex("NAME"));
            String profilePictureUri = AdminCursor.getString(AdminCursor.getColumnIndex("PROFILE_PICTURE_PATH"));

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



//        Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_LONG).show();
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();




        btnChangeProfilePicture.setOnClickListener(v -> openImagePicker());

        loadUserData();


        btnSave.setOnClickListener(v -> {
            if (validateInput()) {
                email = sharedPreferences.getString("email", "");

                // Save the updated user data
                saveUserData(email);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        int id = menuItem.getItemId();
        if (id == R.id.a_profile) {
            showToast("Item 1 clicked");
//            startActivity(new Intent(this, CustHome.class));
            startActivity(new Intent(this, Admin_main.class));

            overridePendingTransition(0, 0);
            return true;


        } else if (id == R.id.a_addAdmin) {
            showToast("Item 2 clicked");
            startActivity(new Intent(this, AddAdmin.class));
            overridePendingTransition(0, 0);
            return true;

        } else if (id == R.id.a_offers) {
            showToast("Item 3 clicked");
            startActivity(new Intent(this, AdminSpecialOffers.class));
            overridePendingTransition(0, 0);
            return true;

        }

        else if (id == R.id.a_orders) {
            showToast("Profile clicked");
            startActivity(new Intent(this, AdminOrders.class));
            overridePendingTransition(0, 0);
            return true;
        }

        else if (id== R.id.summary){
            showToast("Profile clicked");
            startActivity(new Intent(this, OrderSummaryActivity.class));
            overridePendingTransition(0, 0);
            return true;

        }

        else if (id == R.id.a_logout) {
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

    @SuppressLint("Range")
    private void loadUserData() {
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");



        Cursor AdminCursor = dataBaseHelper.findAdmin(email, password);
        if (AdminCursor != null && AdminCursor.moveToFirst()) {
            etFirstName.setText(AdminCursor.getString(AdminCursor.getColumnIndex("NAME")));
            etLastName.setText(AdminCursor.getString(AdminCursor.getColumnIndex("LAST_NAME")));
            etPassword.setText(AdminCursor.getString(AdminCursor.getColumnIndex("PASSWORD")));
            etPhoneNumber.setText(AdminCursor.getString(AdminCursor.getColumnIndex("PHONE")));


            String profilePicturePath = AdminCursor.getString(AdminCursor.getColumnIndex("PROFILE_PICTURE_PATH"));
            if (profilePicturePath != null) {
                profilePictureUri = Uri.parse(profilePicturePath);
                ivProfilePicture.setImageURI(profilePictureUri);
            }



//            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                    R.array.gender_array, android.R.layout.simple_spinner_item);
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.gender_array, R.layout.spinner_list);

            adapter.setDropDownViewResource( R.layout.spinner_list);
            spinnerGender.setAdapter(adapter);
            int spinnerPosition = adapter.getPosition(AdminCursor.getString(AdminCursor.getColumnIndex("GENDER")));
            spinnerGender.setSelection(spinnerPosition);




        }
        AdminCursor.close();



    }

    private boolean validateInput() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();

        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password should be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!phoneNumber.matches("\\d{10}")) {
            Toast.makeText(this, "Phone number should be 10 digits", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void saveUserData(String email) {
        User admin= new User();

        admin.setName(etFirstName.getText().toString().trim());
        admin.setLast_name(etLastName.getText().toString().trim());
        admin.setPassword(etPassword.getText().toString().trim());
        admin.setPhone(etPhoneNumber.getText().toString().trim());
        admin.setGender(spinnerGender.getSelectedItem().toString());

        if (profilePictureUri != null) {
            admin.setProfilePicturePath(profilePictureUri.toString());
        }

        dataBaseHelper.updateAdmin(email,  admin);

        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
    }
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            profilePictureUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), profilePictureUri);
                ivProfilePicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Admin_main.this, Admin_main.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            super.onBackPressed(); // Ensure the default back behavior is called

//            finish(); // Optional: finish the current activity

        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
