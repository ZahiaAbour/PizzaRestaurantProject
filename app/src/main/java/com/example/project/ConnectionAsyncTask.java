package com.example.project;

import android.app.Activity;
import android.os.AsyncTask;
import java.util.List;
public class ConnectionAsyncTask extends AsyncTask<String, String, String> {
    Activity activity;
    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
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
}