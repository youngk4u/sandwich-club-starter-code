package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /**
     *  This method takes a String json and parses it to each catagories
     *  and returns an object Sandwich with all the parameters.
     *
     * @param json
     * @return Sandwich
     */
    public static Sandwich parseSandwichJson(String json) {

        String mainName = new String();
        List<String> alsoKnownAs = new ArrayList<String>();
        String placeOfOrigin = new String();
        String description = new String();
        String imageUrl = new String();
        List<String> ingredients = new ArrayList<String>();

        try {
            JSONObject reader = new JSONObject(json);
            JSONObject jsonName = reader.getJSONObject("name");
            mainName = jsonName.getString("mainName");
            JSONArray jsonAka = jsonName.getJSONArray("alsoKnownAs");
            if (jsonAka != null) {
                for (int i = 0; i < jsonAka.length(); i++) {
                    alsoKnownAs.add(jsonAka.getString(i));
                }
            }
            placeOfOrigin = reader.getString("placeOfOrigin");
            description = reader.getString("description");
            imageUrl = reader.getString("image");
            JSONArray jsonIngre = reader.getJSONArray("ingredients");
            if (jsonIngre != null) {
                for (int i = 0; i < jsonIngre.length(); i++) {
                    ingredients.add(jsonIngre.getString(i));
                }
            }

        } catch(Exception e) {
            Log.e("ERROR", "Wrong input.  Please try different Json.");
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, imageUrl, ingredients);
    }
}
