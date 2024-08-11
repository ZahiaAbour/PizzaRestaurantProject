package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private CheckBox checkBoxRememberMe;
    private SharedPreferences sharedPreferences;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);
        Button buttonSignIn = findViewById(R.id.buttonSignIn);
        dataBaseHelper = new DataBaseHelper(this, "DB", null, 1);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Load the last used email and saved password if "Remember Me" was checked
        String savedEmail = sharedPreferences.getString("last_used_email", "");
        String savedPassword = sharedPreferences.getString("password", "");

        if (!savedEmail.isEmpty()) {
            editTextEmail.setText(savedEmail);
        }
        if (!savedEmail.isEmpty() && !savedPassword.isEmpty()) {
            editTextEmail.setText(savedEmail);
//            editTextPassword.setText(savedPassword);
            checkBoxRememberMe.setChecked(true);
        }

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                if (validateCredentials(email, password)) {
                    // Save the last used email in shared preferences
                    sharedPreferences.edit().putString("email", email).apply();
                    sharedPreferences.edit().putString("password", password).apply();


                    if (checkBoxRememberMe.isChecked()) {
                        sharedPreferences.edit().putString("last_used_email", email).apply();
                    } else {
                        sharedPreferences.edit().remove("last_used_email").apply();
                    }

                    Cursor CustomerCursor = dataBaseHelper.findCustomer(email, password);
                    if (CustomerCursor != null && CustomerCursor.moveToFirst()) {
                        startActivity(new Intent(Login.this, Customer_main.class));
                        CustomerCursor.close(); // Close cursor to prevent memory leaks
                        return;
                    }

                    Cursor AdminCursor = dataBaseHelper.findAdmin(email, password);
                    if (AdminCursor != null && AdminCursor.moveToFirst()) {
                        startActivity(new Intent(Login.this, Admin_main.class));
                        AdminCursor.close(); // Close cursor to prevent memory leaks
                        return;
                    }

                    // If neither customer nor admin was found
                    Toast.makeText(Login.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateCredentials(String email, String password) {
        Toast.makeText(Login.this, email, Toast.LENGTH_SHORT).show();

        Cursor cursor = dataBaseHelper.findUser(email, password);
        if (cursor != null && cursor.moveToFirst()) {
            Toast.makeText(Login.this, email, Toast.LENGTH_SHORT).show();
            cursor.close(); // Close cursor to prevent memory leaks
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Login.this, Regestration.class);
        Login.this.startActivity(intent);
    }
}





//// LoginActivity.java
//package com.example.project;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class Login extends AppCompatActivity {
//
//    private EditText editTextEmail, editTextPassword;
//    private CheckBox checkBoxRememberMe;
//    private SharedPreferences sharedPreferences;
//    private DataBaseHelper dataBaseHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        editTextEmail = findViewById(R.id.editTextEmail);
//        editTextPassword = findViewById(R.id.editTextPassword);
//        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);
//        Button buttonSignIn = findViewById(R.id.buttonSignIn);
//        dataBaseHelper = new DataBaseHelper(this, "DB", null, 1);
//
//        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//
//        String savedEmail = sharedPreferences.getString("email", "");
//        if (!savedEmail.isEmpty()) {
//            editTextEmail.setText(savedEmail);
//        }
//
//        buttonSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = editTextEmail.getText().toString();
//                String password = editTextPassword.getText().toString();
//                if (validateCredentials(email, password)) {
//                    if (checkBoxRememberMe.isChecked()) {
//                        sharedPreferences.edit().putString("email", email).apply();
//                        sharedPreferences.edit().putString("password", password).apply();
//                    } else {
//                        sharedPreferences.edit().remove("email").apply();
//                        sharedPreferences.edit().remove("password").apply();
//                    }
//
//
//                    Cursor CustomerCursor = dataBaseHelper.findCustomer(email, password);
//                    if (CustomerCursor != null && CustomerCursor.moveToFirst()) {
//                        startActivity(new Intent(Login.this, Customer_main.class));
//                    }
//                    CustomerCursor.close(); // Close cursor to prevent memory leaks
//
//                    Cursor AdminCursor = dataBaseHelper.findAdmin(email, password);
//                    if (AdminCursor != null && AdminCursor.moveToFirst()) {
//                        startActivity(new Intent(Login.this, Admin_main.class));
//                        AdminCursor.close(); // Close cursor to prevent memory leaks
//
//                    }
//
//
//                } else {
//                    Toast.makeText(Login.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    private boolean validateCredentials(String email, String password) {
//        Toast.makeText(Login.this, email, Toast.LENGTH_SHORT).show();
//
//
//        Cursor cursor = dataBaseHelper.findUser(email, password);
//
//        if (cursor != null && cursor.moveToFirst()) {
//            Toast.makeText(Login.this, email, Toast.LENGTH_SHORT).show();
////            Toast.makeText(Login.this, "USER FOUNDDD", Toast.LENGTH_SHORT).show();
//            cursor.close(); // Close cursor to prevent memory leaks
//
//            return true;
//        } else {
//            return false;
//        }
////        return !email.isEmpty() && !password.isEmpty();
//    }
//
//    @Override
//    public void onBackPressed() {
//
//        super.onBackPressed();
//        Intent intent = new Intent(Login.this, Regestration.class);
//        Login.this.startActivity(intent);
//
//    }
//}
