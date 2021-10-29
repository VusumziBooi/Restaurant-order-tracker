package com.example.rot;

import org.json.JSONException;
import org.json.JSONObject;

public class Menu {
    String order_name,description,cost;
    public Menu(JSONObject jsonObject){
        try {
            order_name = jsonObject.getString("ORDER_NAME");
            description = jsonObject.getString("DESCRIPTION");
            cost = jsonObject.getString("COST");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
