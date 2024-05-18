package com.example.project;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etPassword, etPhoneNumber;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPassword = findViewById(R.id.etPassword);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnSave = findViewById(R.id.btnSave);

        // Load existing user data from database or shared preferences
        loadUserData();

        btnSave.setOnClickListener(v -> {
            if (validateInput()) {
                // Save the updated user data
                saveUserData();
            }
        });
    }

    private void loadUserData() {
        // Placeholder: Load user data from database or shared preferences
        // For example:
        // etFirstName.setText(user.getFirstName());
        // etLastName.setText(user.getLastName());
        // etPhoneNumber.setText(user.getPhoneNumber());
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

    private void saveUserData() {
        // Placeholder: Save user data to database or shared preferences
        // For example:
        // user.setFirstName(etFirstName.getText().toString().trim());
        // user.setLastName(etLastName.getText().toString().trim());
        // user.setPassword(etPassword.getText().toString().trim());
        // user.setPhoneNumber(etPhoneNumber.getText().toString().trim());

        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
    }
}
