package com.udacity.sandwichclub.utils;

import android.util.Log;
import com.udacity.sandwichclub.model.Sandwich;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;

        try {
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject name = sandwichJson.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            String placeOfOrigin = sandwichJson.getString("placeOfOrigin");
            String description = sandwichJson.getString("description");
            String image = sandwichJson.getString("image");
            JSONArray ingredients = sandwichJson.getJSONArray("ingredients");

            List<String> alsoKnownAsList = parseJsonArray(alsoKnownAs);
            List<String> ingredientsList = parseJsonArray(ingredients);

            sandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    private static List<String> parseJsonArray(JSONArray alsoKnownAs) {
        List<String> jsonList = new ArrayList<>();

        if (alsoKnownAs != null) {
            for (int i=0; i<alsoKnownAs.length(); i++){
                try {
                    jsonList.add(alsoKnownAs.get(i).toString());
                } catch (JSONException e) {
                    Log.i(JsonUtils.class.toString(), "Not able to get item from JsonArray");
                    e.printStackTrace();
                }
            }
        }

        return jsonList;
    }
}
