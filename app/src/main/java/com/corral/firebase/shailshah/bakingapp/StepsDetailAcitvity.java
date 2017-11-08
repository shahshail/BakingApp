package com.corral.firebase.shailshah.bakingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.corral.firebase.shailshah.bakingapp.helper.BakeryInformationHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An activity representing a single Detail detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RacipesListActivity}.
 */
public class StepsDetailAcitvity extends AppCompatActivity {

    private TextView mIngredientTextView;
    private ArrayList<String> finalIngredient;
    private AppBarLayout layout;
    private ImageView imageView;
    private Drawable drawable;
    Bitmap bitmapImage;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        layout = (AppBarLayout) findViewById(R.id.app_bar);
        layout.setBackground(getResources().getDrawable(R.drawable.ic_demo_muffin));


        setTitle(BakeryInformationHelper.getItemName());
        Bundle bundle = getIntent().getExtras();


            position = bundle.getInt("Step");
        if (BakeryInformationHelper.getThumbnails()[position].equals(" "))
       {
            drawable = new BitmapDrawable(BakeryInformationHelper.getStepsThumbnailUrl());

            layout.setBackground(drawable);
        }
        else
        {
            new AsyncTask<Void,Void,Void>()
            {

                @Override
                protected Void doInBackground(Void... params) { MediaMetadataRetriever mediaMetadataRetriever = null ;
                    try {
                        mediaMetadataRetriever = new MediaMetadataRetriever();
                        if (Build.VERSION.SDK_INT >= 14)
                            // no headers included
                            mediaMetadataRetriever.setDataSource(BakeryInformationHelper.getThumbnails()[position], new HashMap<String, String>());
                        else
                            mediaMetadataRetriever.setDataSource(BakeryInformationHelper.getThumbnails()[position]);

                        bitmapImage = mediaMetadataRetriever.getFrameAtTime(15000000);
                    } catch (Exception e) {
                        e.printStackTrace();

                    } finally {
                        if (mediaMetadataRetriever != null)
                            mediaMetadataRetriever.release();
                    }
                    drawable = new BitmapDrawable(bitmapImage);


                    return null;
                }
            }.execute();
            layout.setBackground(drawable);

        }




        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {

            StapesDetailFragment fragment = new StapesDetailFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, RacipesListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
