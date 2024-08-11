package com.example.project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Signup extends AppCompatActivity {

    private EditText editTextName, editTextLastName, editTextEmail, editTextPhone, editTextPassword, editTextConfirmPassword;
    private Spinner spinnerGender;

    String name, last_name, email, phone, password, confirmPassword, gender;
    private DataBaseHelper dataBaseHelper;

    private Button buttonCreateAccount, buttonCustomer, buttonAdmin;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        spinnerGender = findViewById(R.id.spinnerGender);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);


//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.gender_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, R.layout.spinner_list);

        adapter.setDropDownViewResource( R.layout.spinner_list);

        spinnerGender.setAdapter(adapter);

        dataBaseHelper = new DataBaseHelper(this, "DB", null, 1);




        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    Toast.makeText(Signup.this, name, Toast.LENGTH_SHORT).show();
                    User newUser = new User(name, last_name, phone, gender, email, password);
                    Cursor cursor = dataBaseHelper.findUser(email, password);
                    if (cursor != null && cursor.moveToFirst()) {
                        Toast.makeText(Signup.this, "USER FOUNDDD", Toast.LENGTH_SHORT).show();
                    } else {
                        dataBaseHelper.insertUser(newUser);

                        User customer = new User(name, last_name, phone, gender, email, password);
                        dataBaseHelper.insertCustomer(customer);
                        Toast.makeText(Signup.this, "CUSTOMER Signed up successfully!", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(Signup.this, Login.class);
                        Signup.this.startActivity(intent);
                    }
                    cursor.close();
                } else {
                    Toast.makeText(Signup.this, "Error! please sign up again!", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private boolean validateInputs() {
        name = editTextName.getText().toString();
        last_name = editTextLastName.getText().toString();
        email = editTextEmail.getText().toString();
        phone = editTextPhone.getText().toString();
        password = editTextPassword.getText().toString();
        confirmPassword = editTextConfirmPassword.getText().toString();
        gender = spinnerGender.getSelectedItem().toString();
        User newUser = new User(name, last_name, phone, gender, email, password);


        if (TextUtils.isEmpty(name) || name.length() < 3) {
            showError(editTextName, "Name must be at least 3 characters");
            return false;
        }

        if (TextUtils.isEmpty(last_name)) {
            showError(editTextLastName, "Username is required");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError(editTextEmail, "Enter a valid email");
            return false;
        }
        if (!phone.matches("05\\d{8}")) {
            showError(editTextPhone, "Phone number must be 10 digits and start with 05");
            return false;
        }
        if (password.length() < 8 || !password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
            showError(editTextPassword, "Password must be at least 8 characters and include at least 1 letter and 1 number");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            showError(editTextConfirmPassword, "Passwords do not match");
            return false;
        }

        return true;
    }

    private void showError(EditText field, String message) {
        field.setError(message);
        field.requestFocus();
    }



    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = new Intent(Signup.this, Regestration.class);
        Signup.this.startActivity(intent);

    }
}
