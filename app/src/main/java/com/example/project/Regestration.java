package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class Regestration extends AppCompatActivity  {
    private Button signUpButton, signInButton;
    public List<Pizza> pizzas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration);

        TextView signupTextView = (TextView) findViewById(R.id.signupMainText);
        Button signinButton = (Button) findViewById(R.id.signinMainButton);

        signupTextView.setPaintFlags(signupTextView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Regestration.this,Login.class);
                Regestration.this.startActivity(intent);
                finish();
            }
        });

        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Regestration.this,Signup.class);
                Regestration.this.startActivity(intent);
                finish();
            }
        });


    }


}