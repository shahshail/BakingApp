package com.corral.firebase.shailshah.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.corral.firebase.shailshah.bakingapp.helper.BakeryStepsHelper;

import java.util.ArrayList;

/**
 * An activity representing a single Detail detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RacipesListActivity}.
 */
public class StepsDetailAcitvity extends AppCompatActivity {

    private TextView mIngredientTextView;
    private ArrayList<String> finalIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);



        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            arguments.putString(StapesDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(StapesDetailFragment.ARG_ITEM_ID));
            StapesDetailFragment fragment = new StapesDetailFragment();
            fragment.setArguments(arguments);
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
