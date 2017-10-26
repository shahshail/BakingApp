package com.corral.firebase.shailshah.bakingapp.utils;

import android.content.ContentValues;
import android.content.Context;

import com.corral.firebase.shailshah.bakingapp.provider.BakingAppContractor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * Created by shailshah on 10/25/17.
 */

public class OpenBakingJsonUtils {
    public static String strSeparator = "__,__";

    public static ContentValues[] getSimpleMovieDataFromJson(final Context context) throws JSONException {


        URL bakingUrl = NetworkUtils.buildUrl();


        try {
            String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(bakingUrl);
           // JSONObject bakingObject = new JSONObject(jsonWeatherResponse);

            JSONArray array = new JSONArray(jsonWeatherResponse);
            ContentValues BakingContentValue[] = new ContentValues[array.length()];
            for (int i =0 ; i< array.length() ; i++)
            {
                ContentValues bakingValues = new ContentValues();
                JSONObject object = array.getJSONObject(i);

                String name = object.getString("name");
                int id = object.getInt("id");


                JSONArray ingredients = object.getJSONArray("ingredients");
                String[] quantity = new String[ingredients.length()];
                String[] meaasure = new String[ingredients.length()];
                String[] ingre = new String[ingredients.length()];
                for (int j = 0 ; j< ingredients.length() ; j++)
                {
                    JSONObject indegrientObject = ingredients.getJSONObject(j);
                    quantity[j] = indegrientObject.getString("quantity");
                    meaasure[j] = indegrientObject.getString("measure");
                    ingre[j] = indegrientObject.getString("ingredient");

                   // Log.v(OpenBakingJsonUtils.class.getSimpleName(),"The Quantity are : " + quantity[j]);
                    //Log.v(OpenBakingJsonUtils.class.getSimpleName(),"The measure are : " + meaasure[j]);
                   // Log.v(OpenBakingJsonUtils.class.getSimpleName(),"The ingredient are : " + ingre[j]);

                }

                JSONArray stepsArray = object.getJSONArray("steps");
                String[] Stepid = new String[stepsArray.length()];
                String[] shortDescription = new String[stepsArray.length()];
                String[] description = new String[stepsArray.length()];
                String[] videoUrl = new String[stepsArray.length()];
                String[] thumbnailUrl = new String[stepsArray.length()];

                for (int j = 0; j< stepsArray.length(); j++)
                {
                    JSONObject stepsObject = stepsArray.getJSONObject(j);
                    Stepid[j] = stepsObject.getString("id");
                    shortDescription[j] = stepsObject.getString("shortDescription");
                    description[j] = stepsObject.getString("description");
                    videoUrl[j] = stepsObject.getString("videoURL");
                    thumbnailUrl[j] = stepsObject.getString("thumbnailURL");

                }

                String servings = object.getString("servings");
                String image = object.getString("image");



                bakingValues.put(BakingAppContractor.BakeryEntry.COLUMN_BAKERY_ID,id);
                bakingValues.put(BakingAppContractor.BakeryEntry.COLUMN_NAME,name);
                bakingValues.put(BakingAppContractor.BakeryEntry.COLUMN_QUANTITY,OpenBakingJsonUtils.convertArrayToString(quantity));
                bakingValues.put(BakingAppContractor.BakeryEntry.COLUMN_MEASURE,OpenBakingJsonUtils.convertArrayToString(meaasure));
                bakingValues.put(BakingAppContractor.BakeryEntry.COLUMN_INGREDIENT, OpenBakingJsonUtils.convertArrayToString(ingre));
                bakingValues.put(BakingAppContractor.BakeryEntry.COLUMN_STEPS_ID,OpenBakingJsonUtils.convertArrayToString(Stepid));
                bakingValues.put(BakingAppContractor.BakeryEntry.COLUMN_SHORT_DESCRIPTION,OpenBakingJsonUtils.convertArrayToString(shortDescription));
                bakingValues.put(BakingAppContractor.BakeryEntry.COLUMN_DESCRIPTION,OpenBakingJsonUtils.convertArrayToString(description));
                bakingValues.put(BakingAppContractor.BakeryEntry.COLUMN_VIDEO_URL,OpenBakingJsonUtils.convertArrayToString(videoUrl));
                bakingValues.put(BakingAppContractor.BakeryEntry.COLUMN_THUMBNAIL_URL, OpenBakingJsonUtils.convertArrayToString(thumbnailUrl));
                bakingValues.put(BakingAppContractor.BakeryEntry.COLUMN_SERVINGS,servings);
                bakingValues.put(BakingAppContractor.BakeryEntry.COLIMN_IMAGE_URL,image);

                BakingContentValue[i] = bakingValues;

            }
            return BakingContentValue;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


        }


    public static String convertArrayToString(String[] array){
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    }
    public static String[] convertStringToArray(String str){
        String[] arr = str.split(strSeparator);
        return arr;
    }



}

