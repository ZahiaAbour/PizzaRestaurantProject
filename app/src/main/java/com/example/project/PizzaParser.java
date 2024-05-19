package com.example.project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PizzaParser {
    public static List<Pizza> getObjectFromJson(String json) {
        List<Pizza> pizzas = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray typesArray = jsonObject.getJSONArray("types");
            for (int i = 0; i < typesArray.length(); i++) {
//                String pizzaType = typesArray.getString(i);



                jsonObject = (JSONObject) typesArray.get(i);
                Pizza pizza = new Pizza();
                pizza.setID(jsonObject.getInt("id"));
                pizza.setName(jsonObject.getString("name"));
                pizza.setPrice(jsonObject.getDouble("price"));
                pizza.setImageUrl(jsonObject.getString("image_url"));
                pizza.setCategory(jsonObject.getString("category"));
                pizzas.add(pizza);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pizzas;
    }
}
