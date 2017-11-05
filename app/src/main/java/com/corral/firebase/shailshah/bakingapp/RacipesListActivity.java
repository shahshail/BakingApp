package com.corral.firebase.shailshah.bakingapp;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.corral.firebase.shailshah.bakingapp.helper.BakeryInformationHelper;
import com.corral.firebase.shailshah.bakingapp.helper.BakeryStepsHelper;
import com.corral.firebase.shailshah.bakingapp.provider.BakingAppContractor;
import com.corral.firebase.shailshah.bakingapp.utils.OpenBakingJsonUtils;

import static com.corral.firebase.shailshah.bakingapp.MainActivity.INDEX_BAKERY_ID;
import static com.corral.firebase.shailshah.bakingapp.MainActivity.INDEX_BAKERY_NAME;
import static com.corral.firebase.shailshah.bakingapp.MainActivity.INDEX_DESCRIPTION;
import static com.corral.firebase.shailshah.bakingapp.MainActivity.INDEX_ID;
import static com.corral.firebase.shailshah.bakingapp.MainActivity.INDEX_IMAGE_URL;
import static com.corral.firebase.shailshah.bakingapp.MainActivity.INDEX_INGREDIENT;
import static com.corral.firebase.shailshah.bakingapp.MainActivity.INDEX_MEASURE;
import static com.corral.firebase.shailshah.bakingapp.MainActivity.INDEX_QUANTITY;
import static com.corral.firebase.shailshah.bakingapp.MainActivity.INDEX_SERVINGS;
import static com.corral.firebase.shailshah.bakingapp.MainActivity.INDEX_SHORT_DESCRIPTION;
import static com.corral.firebase.shailshah.bakingapp.MainActivity.INDEX_STEPS_ID;
import static com.corral.firebase.shailshah.bakingapp.MainActivity.INDEX_THUMBNAIL_URL;
import static com.corral.firebase.shailshah.bakingapp.MainActivity.INDEX_VIDEO_URL;
import static java.lang.System.lineSeparator;

public class RacipesListActivity extends AppCompatActivity implements SetpsAdapterOnclickListner{
    private RecyclerView mRecyclerView;
    private int mPosition = RecyclerView.NO_POSITION;
    private RecipesItemRecyclerViewAdapter mAdapte;
    private TextView mIngredient;
    private Cursor mCursor;
    int newString;
    String value;


    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
        setTitle(BakeryInformationHelper.getItemName()+" Ingredients");
        Bundle values = getIntent().getExtras();
        if (values == null)
        {
            newString = BakeryStepsHelper.getStepPosition();
        }
        else
        {
            value = values.getString("Values");
            newString = Integer.parseInt(value);
            ContentResolver resolver = getContentResolver();
            mCursor = resolver.query(BakingAppContractor.BakeryEntry.CONTENT_URI,null,null,null,null);
            mCursor.moveToPosition(newString);
            String name = mCursor.getString(INDEX_BAKERY_NAME);
            String id = mCursor.getString(INDEX_ID);

            BakeryInformationHelper.setID(mCursor.getString(INDEX_ID));
            BakeryInformationHelper.setBakeryId(mCursor.getString(INDEX_BAKERY_ID));
            BakeryInformationHelper.setItemName(mCursor.getString(INDEX_BAKERY_NAME));
            BakeryInformationHelper.setItemQuentity(OpenBakingJsonUtils.convertStringToArray(mCursor.getString(INDEX_QUANTITY)));
            BakeryInformationHelper.setItemMeasure(OpenBakingJsonUtils.convertStringToArray(mCursor.getString(INDEX_MEASURE)));
            BakeryInformationHelper.setItemIngredient(OpenBakingJsonUtils.convertStringToArray(mCursor.getString(INDEX_INGREDIENT)));
            BakeryInformationHelper.setStepsId(OpenBakingJsonUtils.convertStringToArray(mCursor.getString(INDEX_STEPS_ID)));
            BakeryInformationHelper.setStepsShortDescription(OpenBakingJsonUtils.convertStringToArray(mCursor.getString(INDEX_SHORT_DESCRIPTION)));
            BakeryInformationHelper.setStepsDescription(OpenBakingJsonUtils.convertStringToArray(mCursor.getString(INDEX_DESCRIPTION)));
            BakeryInformationHelper.setStepsVideoUrl(OpenBakingJsonUtils.convertStringToArray(mCursor.getString(INDEX_VIDEO_URL)));
            BakeryInformationHelper.setStepsThumbnailUrl(OpenBakingJsonUtils.convertByteToBitmap(mCursor.getBlob(INDEX_THUMBNAIL_URL)));
            BakeryInformationHelper.setItemServings(mCursor.getString(INDEX_SERVINGS));
            BakeryInformationHelper.setItemImagePath(mCursor.getString(INDEX_IMAGE_URL));


        }

        ContentResolver resolver = getContentResolver();
        mCursor = resolver.query(BakingAppContractor.BakeryEntry.CONTENT_URI,null,null,null,null);
        mCursor.moveToPosition(newString);

        String finalResult = new String();
      mRecyclerView = (RecyclerView) findViewById(R.id.detail_list);
        mRecyclerView.setHasFixedSize(true);
        mAdapte = new RecipesItemRecyclerViewAdapter(this,this);
        mRecyclerView.setAdapter(mAdapte);
        mIngredient = (TextView) findViewById(R.id.tv_ingredient_view);

        for (int i = 0 ; i< BakeryInformationHelper.getItemQuentity().length ; i++ )
        {
            if (i==0)
            {
                finalResult += BakeryInformationHelper.getItemQuentity()[i] + " " + BakeryInformationHelper.getItemMeasure()[i] + " " + BakeryInformationHelper.getItemIngredient()[i]+ ". " ;
            }
           finalResult += lineSeparator() + BakeryInformationHelper.getItemQuentity()[i] + " " + BakeryInformationHelper.getItemMeasure()[i] + " " + BakeryInformationHelper.getItemIngredient()[i]+ ". " ;
            Log.v(RacipesListActivity.class.getSimpleName(), "Result is  ::: :: :: :: " + finalResult);

        }

        //BakeryStepsHelper.setIngredientResult(finalResult);

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
           if (Integer.valueOf(stepsId[position]) == 0)
           {
               holder.stepView.setText("Recipe Introduction");
           }
           else
           {
               holder.mStepsIdView.setText(stepsId[position]);
           }

            holder.mStepsDescriptionView.setText(stepsShortDescription[position]);



            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BakeryStepsHelper.setStepPosition(position);

                   // Log.v(RacipesListActivity.class.getSimpleName(),"Information is " + BakeryInformationHelper.getItemQuentity()[position] + " " + BakeryInformationHelper.getItemMeasure()[position] + " " + BakeryInformationHelper.getItemIngredient()[position]  );
                    //Log.v(RacipesListActivity.class.getSimpleName(),"Stored position is : " + BakeryStepsHelper.getStepPosition());

                    Bundle bundle = new Bundle();
                    bundle.putInt("Step",position);
                    if (mTwoPane) {
                        StapesDetailFragment fragment = new StapesDetailFragment();
                        fragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.detail_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, StepsDetailAcitvity.class);
                        intent.putExtra("Step",position);
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
            TextView stepView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mStepsIdView = (TextView)mView.findViewById(R.id.id);
                mStepsDescriptionView = (TextView)mView.findViewById(R.id.content);
                stepView = (TextView)mView.findViewById(R.id.textView);
            }


        }
    }
}
