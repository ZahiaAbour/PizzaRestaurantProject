package com.example.project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.Manifest;


public class ProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int READ_EXTERNAL_STORAGE = 1;


    private EditText etFirstName, etLastName, etPassword, etPhoneNumber;
    private Spinner spinnerGender;
    private ImageView ivProfilePicture;
    private Button btnSave, btnChangeProfilePicture;
    String email;
    private SharedPreferences sharedPreferences;
    private DataBaseHelper dataBaseHelper;
    private Uri profilePictureUri;
    private int requestCode;
    private String[] permissions;
    private int[] grantResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPassword = findViewById(R.id.etPassword);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnSave = findViewById(R.id.btnSave);
        spinnerGender = findViewById(R.id.spinner3);
        btnChangeProfilePicture = findViewById(R.id.btnChangeProfilePicture);
        ivProfilePicture = findViewById(R.id.ivProfilePicture);


        dataBaseHelper = new DataBaseHelper(this, "DB", null, 1);
        loadUserData();


        btnChangeProfilePicture.setOnClickListener(v -> openImagePicker());

        // Check and request READ_EXTERNAL_STORAGE permission if not granted
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                    REQUEST_READ_EXTERNAL_STORAGE);
//        } else {
//            // Permission already granted, proceed to load user data
//            loadUserData();
//        }


        btnSave.setOnClickListener(v -> {
            if (validateInput()) {
                email = sharedPreferences.getString("email", "");

                // Save the updated user data
                saveUserData(email);
            }
        });
    }

    @SuppressLint("Range")
    private void loadUserData() {
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");


        Cursor CustomerCursor = dataBaseHelper.findCustomer(email, password);
        if (CustomerCursor != null && CustomerCursor.moveToFirst()) {
            etFirstName.setText(CustomerCursor.getString(CustomerCursor.getColumnIndex("NAME")));
            etLastName.setText(CustomerCursor.getString(CustomerCursor.getColumnIndex("NAME")));
            etPassword.setText(CustomerCursor.getString(CustomerCursor.getColumnIndex("PASSWORD")));
            etPhoneNumber.setText(CustomerCursor.getString(CustomerCursor.getColumnIndex("PHONE")));

            String profilePicturePath = CustomerCursor.getString(CustomerCursor.getColumnIndex("PROFILE_PICTURE_PATH"));
            if (profilePicturePath != null) {
                profilePictureUri = Uri.parse(profilePicturePath);
                ivProfilePicture.setImageURI(profilePictureUri);
            }


//            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                    R.array.gender_array, android.R.layout.simple_spinner_item);
//
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.gender_array, R.layout.spinner_list);

            adapter.setDropDownViewResource( R.layout.spinner_list);


            spinnerGender.setAdapter(adapter);
            int spinnerPosition = adapter.getPosition(CustomerCursor.getString(CustomerCursor.getColumnIndex("GENDER")));
            spinnerGender.setSelection(spinnerPosition);


        }
        CustomerCursor.close();


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
        User customer = new User();

        customer.setName(etFirstName.getText().toString().trim());
        customer.setLast_name(etLastName.getText().toString().trim());
        customer.setPassword(etPassword.getText().toString().trim());
        customer.setPhone(etPhoneNumber.getText().toString().trim());
        customer.setGender(spinnerGender.getSelectedItem().toString());

        if (profilePictureUri != null) {
            customer.setProfilePicturePath(profilePictureUri.toString());
        }


        dataBaseHelper.updateCustomer(email, customer);

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

    private Uri saveImageToExternalStorage(Bitmap bitmap) {
        // Create a file to save the image
        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = new File(directory, "profile_picture.jpg");

        try (FileOutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            return Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//    private void requestReadExternalStoragePermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, load user data
                loadUserData();
            } else {
                // Permission denied, inform the user or handle accordingly
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Customer_main.class);
        startActivity(intent);

    }


}
