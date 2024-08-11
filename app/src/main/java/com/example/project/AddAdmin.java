package com.example.project;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Patterns;


public class AddAdmin extends AppCompatActivity {

    private EditText adminName,adminLastName , adminEmail, adminPassword, adminPhone;
    private Button addAdminButton;
    private Spinner spinnerGender;
    private DataBaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        adminName = findViewById(R.id.adminName);
        adminLastName= findViewById(R.id.adminLastName);
        adminEmail = findViewById(R.id.adminEmail);
        adminPassword = findViewById(R.id.adminPassword);
        adminPhone= findViewById(R.id.adminPhone);
        addAdminButton = findViewById(R.id.addAdminButton);
        spinnerGender = findViewById(R.id.spinnerGender);

        databaseHelper = new DataBaseHelper(this);

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.gender_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, R.layout.spinner_list);

        adapter.setDropDownViewResource( R.layout.spinner_list);
        spinnerGender.setAdapter(adapter);

        addAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = adminName.getText().toString();
                String last_name = adminLastName.getText().toString();
                String email = adminEmail.getText().toString();
                String password = adminPassword.getText().toString();
                String phone = adminPhone.getText().toString();
                String gender= spinnerGender.getSelectedItem().toString();

                User user= new User (name, last_name,  phone , gender, email, password);

                if (validateInput(name, email, password)) {
                    boolean isInserted = databaseHelper.insertAdmin(user);
                    if (isInserted) {
                        Toast.makeText(AddAdmin.this, "Admin added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddAdmin.this, "Error adding admin", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean validateInput(String name, String email, String password) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
//            showError(adminName, "Name must be at least 3 characters");

            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError(adminEmail, "Enter a valid email");
//            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(name) || name.length() < 3) {
            showError(adminName, "Name must be at least 3 characters");
            return false;
        }

        if (password.length() < 8 || !password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
            showError(adminPassword, "Password must be at least 8 characters and include at least 1 letter and 1 number");
            return false;
        }


//        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            showError(adminEmail, "Enter a valid email");
//            return false;
//        }


//        if (!phone.matches("05\\d{8}")) {
//            showError(editTextPhone, "Phone number must be 10 digits and start with 05");
//            return false;
//        }

        return true;
    }

    private void showError(EditText field, String message) {
        field.setError(message);
        field.requestFocus();
    }
}
