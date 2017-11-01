package com.corral.firebase.shailshah.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.corral.firebase.shailshah.bakingapp.dummy.DummyContent;
import com.corral.firebase.shailshah.bakingapp.helper.BakeryInformationHelper;
import com.corral.firebase.shailshah.bakingapp.helper.BakeryStepsHelper;

/**
 * An activity representing a list of Detail. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepsDetailAcitvity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RacipesListActivity extends AppCompatActivity implements SetpsAdapterOnclickListner{
    private RecyclerView mRecyclerView;
    private int mPosition = RecyclerView.NO_POSITION;
    private RecipesItemRecyclerViewAdapter mAdapte;
    private TextView mIngredient;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);

        String finalResult = new String();
      mRecyclerView = (RecyclerView) findViewById(R.id.detail_list);
        mRecyclerView.setHasFixedSize(true);
        mAdapte = new RecipesItemRecyclerViewAdapter(this,this);
        mRecyclerView.setAdapter(mAdapte);
        mIngredient = (TextView) findViewById(R.id.tv_ingredient_view);


        for (int i = 0 ; i< BakeryInformationHelper.getItemQuentity().length ; i++ )
        {
           finalResult += BakeryInformationHelper.getItemQuentity()[i] + " " + BakeryInformationHelper.getItemMeasure()[i] + " " + BakeryInformationHelper.getItemIngredient()[i]+ ". " + System.lineSeparator();
            Log.v(RacipesListActivity.class.getSimpleName(), "Result is  ::: :: :: :: " + finalResult);

        }

        BakeryStepsHelper.setIngredientResult(finalResult);

        mIngredient.setText(finalResult);
        if (findViewById(R.id.detail_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }


    @Override
    public void onClick(String s) {

    }

    public class RecipesItemRecyclerViewAdapter extends RecyclerView.Adapter<RecipesItemRecyclerViewAdapter.ViewHolder> {

      private final SetpsAdapterOnclickListner mClickListner;
        private final Context mContext;





        public RecipesItemRecyclerViewAdapter(Context context, SetpsAdapterOnclickListner mClickLostner) {
            this.mContext = context;
            this.mClickListner = mClickLostner;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.detail_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            String[] stepsId = BakeryInformationHelper.getStepsId();
            String[] stepsShortDescription = BakeryInformationHelper.getStepsShortDescription();
            holder.mStepsIdView.setText(stepsId[position]);
            holder.mStepsDescriptionView.setText(stepsShortDescription[position]);



            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BakeryStepsHelper.setStepPosition(position);

                    Log.v(RacipesListActivity.class.getSimpleName(),"Information is " + BakeryInformationHelper.getItemQuentity()[position] + " " + BakeryInformationHelper.getItemMeasure()[position] + " " + BakeryInformationHelper.getItemIngredient()[position]  );
                    Log.v(RacipesListActivity.class.getSimpleName(),"Stored position is : " + BakeryStepsHelper.getStepPosition());

                    if (mTwoPane) {
                        StapesDetailFragment fragment = new StapesDetailFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.detail_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, StepsDetailAcitvity.class);
                        //intent.putExtra(StapesDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return BakeryInformationHelper.getStepsId().length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            private TextView mStepsIdView;
            private TextView mStepsDescriptionView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mStepsIdView = (TextView)mView.findViewById(R.id.id);
                mStepsDescriptionView = (TextView)mView.findViewById(R.id.content);

            }


        }
    }
}
