package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        if(json==null||TextUtils.isEmpty(json)){
            return null;
        }

        Sandwich sandwich=null;

        try {
            JSONObject root = new JSONObject(json);
            JSONObject nameJSONObject= root.getJSONObject("name");
            String mainName=nameJSONObject.getString("mainName").trim();
            JSONArray alsoKnownAsJSONArray=nameJSONObject.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>();
            if(alsoKnownAsJSONArray.length()!=0) {
                for(int i=0;i<alsoKnownAsJSONArray.length();i++){
                    String additionalName=alsoKnownAsJSONArray.getString(i);
                    alsoKnownAs.add(additionalName);
                }
            }

            String placeOfOrigin=root.getString("placeOfOrigin").trim();
            String description=root.getString("description").trim();
            String image=root.getString("image").trim();

            JSONArray ingredientsJSONArray=root.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();
            if(ingredientsJSONArray.length()!=0) {
                for(int i=0;i<ingredientsJSONArray.length();i++){
                    String ingredient=ingredientsJSONArray.getString(i);
                    ingredients.add(ingredient);
                }
            }

                sandwich= new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);

        } catch (JSONException e){
            e.printStackTrace();
        }

        return sandwich;

    }
}
