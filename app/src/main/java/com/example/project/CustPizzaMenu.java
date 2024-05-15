package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class CustPizzaMenu extends AppCompatActivity {

    Button button;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_pizza_menu);

        button = findViewById(R.id.button);
        linearLayout = findViewById(R.id.layout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new ConnectionAsyncTask(CustPizzaMenu.this).execute("http://www.mocky.io/v2/5b4e6b4e3200002c009c2a44");
//                new ConnectionAsyncTask(CustPizzaMenu.this).execute("https://mocki.io/v1/7f002b85-a983-4107-846b-f5635717e164");
                new ConnectionAsyncTask(CustPizzaMenu.this).execute("https://18fbea62d74a40eab49f72e12163fe6c.api.mockbin.io/");

            }
        });
    }

    public void setButtonText(String text) {
        button.setText(text);
    }

    public void fillPizzas(List<Pizza> pizzas) {
        linearLayout.removeAllViews();
        for (int i = 0; i < pizzas.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText(pizzas.get(i).getName());



            linearLayout.addView(textView);
           
        }
    }


    public void setProgress(boolean progress) {
        ProgressBar progressBar = (ProgressBar)
                findViewById(R.id.progressBar);
        if (progress) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}