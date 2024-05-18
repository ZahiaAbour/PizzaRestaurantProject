package com.example.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ContactUsActivity extends AppCompatActivity {

    private Button btnCallUs, btnFindUs, btnEmailUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        btnCallUs = findViewById(R.id.btnCallUs);
        btnFindUs = findViewById(R.id.btnFindUs);
        btnEmailUs = findViewById(R.id.btnEmailUs);

        btnCallUs.setOnClickListener(v -> callUs());
        btnFindUs.setOnClickListener(v -> findUs());
        btnEmailUs.setOnClickListener(v -> emailUs());
    }

    private void callUs() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:0599000000"));
        startActivity(callIntent);
    }

    private void findUs() {
        Uri location = Uri.parse("geo:31.961013,35.190483?q=31.961013,35.190483(Pizza Restaurant)");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    private void emailUs() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:AdvancePizza@Pizza.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry from Customer");
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }
}
