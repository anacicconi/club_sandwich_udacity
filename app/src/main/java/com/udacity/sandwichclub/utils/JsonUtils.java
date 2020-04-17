package com.udacity.sandwichclub.utils;

import android.util.Log;
import com.udacity.sandwichclub.model.Sandwich;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;

        // optString -> add a fallback if the key is not in the Json
        try {
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject name = sandwichJson.getJSONObject(NAME);
            String mainName = name.optString(MAIN_NAME, "Sandwich");
            String placeOfOrigin = sandwichJson.optString(PLACE_OF_ORIGIN, "Unknown");
            String description = sandwichJson.optString(DESCRIPTION, "Unknown");
            String image = sandwichJson.optString(IMAGE, null);
            JSONArray alsoKnownAs = name.getJSONArray(ALSO_KNOWN_AS);
            JSONArray ingredients = sandwichJson.getJSONArray(INGREDIENTS);

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
