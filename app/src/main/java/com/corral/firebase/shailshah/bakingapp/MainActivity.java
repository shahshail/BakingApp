package com.corral.firebase.shailshah.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.corral.firebase.shailshah.bakingapp.helper.BakeryInformationHelper;
import com.corral.firebase.shailshah.bakingapp.helper.BakeryStepsHelper;
import com.corral.firebase.shailshah.bakingapp.provider.BakingAppContractor;
import com.corral.firebase.shailshah.bakingapp.sync.BakerySyncUtils;
import com.corral.firebase.shailshah.bakingapp.utils.OpenBakingJsonUtils;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,BakeryAdapterOnclickListener{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private static final int ID_BAKERY_LOADER = 50;
    private static int newPosition;
    private SimpleItemRecyclerViewAdapter mDataAdapter;
    private RecyclerView mRecyclerView;
    private int mPosition = RecyclerView.NO_POSITION;
    private int i =0;
    private ProgressBar mProgressbar;
    private int adapterPosition;
    private LinearLayoutManager layoutManager;
    private static final String SELECTED_KEY = "selected_position";


    public static final String[] MAIN_BAKERY_PROJECTION = {
            BakingAppContractor.BakeryEntry._ID,
            BakingAppContractor.BakeryEntry.COLUMN_BAKERY_ID,
            BakingAppContractor.BakeryEntry.COLUMN_NAME,
            BakingAppContractor.BakeryEntry.COLUMN_QUANTITY,
            BakingAppContractor.BakeryEntry.COLUMN_MEASURE,
            BakingAppContractor.BakeryEntry.COLUMN_INGREDIENT,
            BakingAppContractor.BakeryEntry.COLUMN_STEPS_ID,
            BakingAppContractor.BakeryEntry.COLUMN_SHORT_DESCRIPTION,
            BakingAppContractor.BakeryEntry.COLUMN_DESCRIPTION,
            BakingAppContractor.BakeryEntry.COLUMN_VIDEO_URL,
            BakingAppContractor.BakeryEntry.COLUMN_THUMBNAIL_URL,
            BakingAppContractor.BakeryEntry.COLUMN_THUMBNAIL,
            BakingAppContractor.BakeryEntry.COLUMN_SERVINGS,
            BakingAppContractor.BakeryEntry.COLIMN_IMAGE_URL,
            BakingAppContractor.BakeryEntry.COLUMN_WIDGET_INFO
    };


    public static final int INDEX_ID = 0;
    public static final int INDEX_BAKERY_ID = 1;
    public static final int INDEX_BAKERY_NAME = 2;
    public static final int INDEX_QUANTITY =3;
    public static final int INDEX_MEASURE= 4;
    public static final int INDEX_INGREDIENT = 5;
    public static final int INDEX_STEPS_ID = 6;
    public static final int INDEX_SHORT_DESCRIPTION = 7;
    public static final int INDEX_DESCRIPTION = 8;
    public static final int INDEX_VIDEO_URL = 9;
    public static final int INDEX_THUMBNAIL_URL = 10;
    public static final int INDEX_THUMBNAILS = 11;
    public static final int INDEX_SERVINGS = 12;
    public static final int INDEX_IMAGE_URL = 13;
    public static final int INDEX_WIDGET_INFO = 14;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        LoaderManager.LoaderCallbacks<Cursor> callback = MainActivity.this;
        Bundle bundleForLoader = null;
        getSupportLoaderManager().initLoader(ID_BAKERY_LOADER, bundleForLoader, callback);


        mProgressbar = (ProgressBar) findViewById(R.id.main_progressbar);

        mRecyclerView = (RecyclerView)findViewById(R.id.item_list);
        mRecyclerView.setHasFixedSize(true);
        mDataAdapter = new SimpleItemRecyclerViewAdapter(this,this);
        mRecyclerView.setAdapter(mDataAdapter);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
       // View recyclerView = findViewById(R.id.item_list);

        if (findViewById(R.id.item_layout_info) != null) {
            GridLayoutManager layoutManager = new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mDataAdapter);

            mTwoPane = true;
        }

        BakerySyncUtils.initialize(this);

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        //gridView = (GridView) findViewById(R.id.main_grid_layout);
        int mCurrentPosition = adapterPosition;
        mPosition = mCurrentPosition;
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        mDataAdapter.notifyDataSetChanged();
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        mPosition = savedInstanceState.getInt(SELECTED_KEY);
        if (mPosition != ListView.INVALID_POSITION) {
            layoutManager.smoothScrollToPosition(mRecyclerView,null,mPosition);
        }
        mDataAdapter.notifyDataSetChanged();
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        mProgressbar = (ProgressBar) findViewById(R.id.main_progressbar);
        mProgressbar.setVisibility(View.VISIBLE);
        switch (id)
        {
            case ID_BAKERY_LOADER: {
                Uri bakeryQueryUri = BakingAppContractor.BakeryEntry.CONTENT_URI;

                return new CursorLoader(
                        this, bakeryQueryUri, MAIN_BAKERY_PROJECTION, null, null, null

                );
            }
                default:
                {
                    throw new RuntimeException("LOader Not Implemented" + id);

                }
            }


    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mProgressbar.setVisibility(View.INVISIBLE);
        mDataAdapter.swapCursor(data);
        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        mRecyclerView.smoothScrollToPosition(mPosition);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mDataAdapter.swapCursor(null);

    }

    @Override
    public void onClick(String movieForday) {

    }










    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {
        private Cursor mCursor;
        private final BakeryAdapterOnclickListener mClickHandler;
        private final Context mContext;


        public SimpleItemRecyclerViewAdapter(Context mContext,BakeryAdapterOnclickListener mClickHandler) {
            this.mClickHandler = mClickHandler;
            this.mContext = mContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);

            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {



            mCursor.moveToPosition(position);

            String name  = mCursor.getString(INDEX_BAKERY_NAME);
            holder.mNameView.setText(name);
            if (mCursor.getString(INDEX_IMAGE_URL).equals(""))

            {
                Drawable drawable = new BitmapDrawable(OpenBakingJsonUtils.convertByteToBitmap(mCursor.getBlob(INDEX_THUMBNAIL_URL)));
                holder.mThumbnail.setBackgroundDrawable(drawable);
            }
            else
            {
                Picasso.with(mContext).load(mCursor.getString(INDEX_IMAGE_URL)).into(holder.mThumbnail);
            }

            holder.mServings.setText(mCursor.getString(INDEX_SERVINGS));
            newPosition = position;
            adapterPosition = position;

        }
        void swapCursor(Cursor newCursor) {
            mCursor = newCursor;
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            if (null == mCursor) return 0;
            return mCursor.getCount();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView mNameView;
            private ImageView mThumbnail;
            TextView mServings;

            public ViewHolder(View view) {
                super(view);
                mNameView = (TextView) view.findViewById(R.id.tv_item_name);
                mThumbnail = (ImageView) view.findViewById(R.id.thumnail_image_pview);
                mServings = (TextView) view.findViewById(R.id.tv_servings);

                view.setOnClickListener(this);
            }


            @Override
            public void onClick(View v) {
                int AdapterPosition = getAdapterPosition();
                mCursor.moveToPosition(AdapterPosition);
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
                BakeryInformationHelper.setThumbnails(OpenBakingJsonUtils.convertStringToArray(mCursor.getString(INDEX_THUMBNAILS)));
               BakeryStepsHelper.setTotalSteps(String.valueOf(BakeryInformationHelper.getStepsId().length));

                Log.v(MainActivity.class.getSimpleName(),"The Cursotr name for the bakery is "  +BakeryInformationHelper.getItemName());

                SharedPreferences sharedPref = mContext.getSharedPreferences(getString(R.string.widget_preference),0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(getString(R.string.widget_preference), AdapterPosition);
                editor.commit();

              updateAllWidgets();
                Intent intent = new Intent(v.getContext(),RacipesListActivity.class);
                //intent.putExtra("Values",String.valueOf(mCursor.getPosition()));
                BakeryStepsHelper.setStepPosition(mCursor.getPosition());
                startActivity(intent);

                mClickHandler.onClick(id);
            }
        }
        private void updateAllWidgets(){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, BakeryWidjetProvider.class));
            if (appWidgetIds.length > 0) {
                new BakeryWidjetProvider().onUpdate(mContext, appWidgetManager, appWidgetIds);
            }
        }







    }

}
