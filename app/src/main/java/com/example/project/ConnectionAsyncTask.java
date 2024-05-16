package com.example.project;

import android.app.Activity;
import android.os.AsyncTask;
import java.util.List;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionAsyncTask extends AsyncTask<String, String, String> {
    Activity activity;
    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }
    public ConnectionAsyncTask( ) {

    }
    @Override
    protected void onPreExecute() {
        ((CustPizzaMenu) activity).setButtonText("connecting");
        super.onPreExecute();
        ((CustPizzaMenu) activity).setProgress(true);
    }
    @Override
    protected String doInBackground(String... params) {
        String data = HttpManager.getData(params[0]);
        return data;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        ((CustPizzaMenu) activity).setProgress(false);
        ((CustPizzaMenu) activity).setButtonText("connected");
        List<Pizza> Pizzas =
               PizzaParser.getObjectFromJson(s);
        ((CustPizzaMenu) activity).fillPizzas(Pizzas);
    }


    public void loadImageFromUrl(String imageURL, ImageView imageView) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Once the executor parses the URL and receives the image, handler will load it in the ImageView
        Handler handler = new Handler(Looper.getMainLooper());

        // Only for Background process (can take time depending on the Internet speed)
        executor.execute(() -> {
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
    }


}

