package com.corral.firebase.shailshah.bakingapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * Created by shailshah on 10/25/17.
 */

public class OpenBakingJsonUtils {

    public static void getSimpleMovieDataFromJson(final Context context) throws JSONException {


        URL bakingUrl = NetworkUtils.buildUrl();

        try {
            String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(bakingUrl);
           // JSONObject bakingObject = new JSONObject(jsonWeatherResponse);

            JSONArray array = new JSONArray(jsonWeatherResponse);
            for (int i =0 ; i< array.length() ; i++)
            {
                ContentValues bakingValues = new ContentValues();
                JSONObject object = array.getJSONObject(i);
                String name = object.getString("name");
                int id = object.getInt("id");

                JSONArray ingredients = object.getJSONArray("ingredients");



                Log.v(OpenBakingJsonUtils.class.getSimpleName(),"Hello the baking name is ...... Please Print dont do this to me...." + name);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }




        /**
             movieValues.put(MovieDataContractor.MovieEntry.COLUMN_DATE,RELEASE_DATE);
             movieValues.put(MovieDataContractor.MovieEntry.COLUMN_MOVIE_ID,MOVIE_ID);
             movieValues.put(MovieDataContractor.MovieEntry.COLUMN_TITLE,MOVIE_TITLE);
             movieValues.put(MovieDataContractor.MovieEntry.COLUMN_OVERWIEW,OVERVIEW);
             movieValues.put(MovieDataContractor.MovieEntry.COLUMN_VOTE_AVERAGE,VOTE_AVERAGE);
             movieValues.put(MovieDataContractor.MovieEntry.COLUMN_POSTER_PATH,POSTE_PATH);
             movieValues.put(MovieDataContractor.MovieEntry.COLUMN_POSTER_BACKDROP_PATH,BACKDROP_PATH);
             movieValues.put(MovieDataContractor.MovieEntry.COLUMN_POPULARITY,POPULARITY);
             movieValues.put(MovieDataContractor.MovieEntry.COLUMN_SORT_ORDER, criteria);


             Log.v(OpenMovieJsonUtils.class.getSimpleName(),"Hello the movie " + movieValues);
             */


        }


    }

