package com.corral.firebase.shailshah.bakingapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.corral.firebase.shailshah.bakingapp.helper.BakeryInformationHelper;
import com.corral.firebase.shailshah.bakingapp.helper.BakeryStepsHelper;

/**
 * A fragment representing a single Detail detail screen.
 * This fragment is either contained in a {@link RacipesListActivity}
 * in two-pane mode (on tablets) or a {@link StepsDetailAcitvity}
 * on handsets.
 */
public class StapesDetailFragment extends Fragment {
    private TextView mDescroptionTextview;

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StapesDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
//                appBarLayout.setTitle(mItem.content);
            }
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_detail, container, false);


        mDescroptionTextview = (TextView) rootView.findViewById(R.id.detail_description);
        mDescroptionTextview.setText(BakeryInformationHelper.getStepsDescription()[BakeryStepsHelper.getStepPosition()]);
        return rootView;
    }

}
