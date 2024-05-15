package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {
    private Button signUpButton, signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView signupTextView = (TextView) findViewById(R.id.signupMainText);
        Button signinButton = (Button) findViewById(R.id.signinMainButton);

        signupTextView.setPaintFlags(signupTextView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

//        ainee trainee1 = new Trainee("Aya", "Murra", "h.aya2001@gmail.com",
//                DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this, "Project", null, 1);
//        Tr    "Aya@2001", "0595601012", "Ramallah");
//        dbHelper.insertTrainee(trainee1);
//        Trainee trainee2 = new Trainee("Hala", "Burghal", "h.hala2001@gmail.com",
//                "Hala@2001", "0595601012", "Ramallah");
//        dbHelper.insertTrainee(trainee2);
//        Instructor instructor1 = new Instructor("Marah", "Hamarsheh", "h.marah2005@gmail.com",
//                "Marah@2005", "0595601012", "Ramallah",
//                "Fronten Development", "BSc", "HTML, CSS");
//
//        dbHelper.insertInstructor(instructor1);
//
//        Instructor instructor2 = new Instructor("Mazen", "Hamarsheh", "h.mazenHamarsheh@gmail.com",
//                "Addone@11", "0595601012", "Ramallah",
//                "Backend Development", "Bhs", "Java, Python");
//
//        dbHelper.insertInstructor(instructor2);

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,Login.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });

        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,Signup.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });


    }


}