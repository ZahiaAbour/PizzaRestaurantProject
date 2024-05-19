package com.example.project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustHome extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cust_home);

        imageView = findViewById(R.id.imageView2);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Once the executor parses the URL
        // and receives the image, handler will load it
        // in the ImageView
        Handler handler = new Handler(Looper.getMainLooper());

        // Only for Background process (can take time depending on the Internet speed)
        executor.execute(() -> {
            // Image URL
            String imageURL = "https://media.geeksforgeeks.org/wp-content/cdn-uploads/gfg_200x200-min.png";

            // Tries to get the image and post it in the ImageView
            // with the help of Handler
            try {
                URL url = new URL(imageURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap image = BitmapFactory.decodeStream(input);

                // Only for making changes in UI
                handler.post(() -> imageView.setImageBitmap(image));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

//        ImageButton addToFavoritesButton = findViewById(R.id.addToFavoritesButton);
//        addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
//            boolean isFavorite = false;
//
//            @Override
//            public void onClick(View v) {
//                isFavorite = !isFavorite;
//                addToFavoritesButton.setImageResource(isFavorite ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);
//
//            }
//        });








    }
}