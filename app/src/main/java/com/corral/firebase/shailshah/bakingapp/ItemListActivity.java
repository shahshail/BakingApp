package com.corral.firebase.shailshah.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.corral.firebase.shailshah.bakingapp.helper.BakeryInformationHelper;
import com.corral.firebase.shailshah.bakingapp.provider.BakingAppContractor;
import com.corral.firebase.shailshah.bakingapp.sync.BakerySyncUtils;
import com.corral.firebase.shailshah.bakingapp.utils.OpenBakingJsonUtils;

public class ItemListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,BakeryAdapterOnclickListener{

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
            BakingAppContractor.BakeryEntry.COLUMN_SERVINGS,
            BakingAppContractor.BakeryEntry.COLIMN_IMAGE_URL
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
    public static final int INDEX_SERVINGS = 11;
    public static final int INDEX_IMAGE_URL = 12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        LoaderManager.LoaderCallbacks<Cursor> callback = ItemListActivity.this;
        Bundle bundleForLoader = null;
        getSupportLoaderManager().initLoader(ID_BAKERY_LOADER, bundleForLoader, callback);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mRecyclerView = (RecyclerView)findViewById(R.id.item_list);
        mRecyclerView.setHasFixedSize(true);
        mDataAdapter = new SimpleItemRecyclerViewAdapter(this,this);
        mRecyclerView.setAdapter(mDataAdapter);
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
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
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
            newPosition = position;

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
            public final TextView mIdView;
            public final TextView mContentView;
            TextView mNameView;

            public ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
                mNameView = (TextView) view.findViewById(R.id.tv_item_name);
                view.setOnClickListener(this);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }

            @Override
            public void onClick(View v) {
                int AdapterPosition = getAdapterPosition();
                mCursor.moveToPosition(AdapterPosition);
                String name = mCursor.getString(INDEX_BAKERY_NAME);
                String id = mCursor.getString(INDEX_ID);

                BakeryInformationHelper.setID(id);
                BakeryInformationHelper.setBakeryId(mCursor.getString(INDEX_BAKERY_ID));
                BakeryInformationHelper.setItemName(name);
                BakeryInformationHelper.setItemQuentity(OpenBakingJsonUtils.convertStringToArray(mCursor.getString(INDEX_QUANTITY)));
                BakeryInformationHelper.setItemMeasure(OpenBakingJsonUtils.convertStringToArray(mCursor.getString(INDEX_MEASURE)));
                BakeryInformationHelper.setItemIngredient(OpenBakingJsonUtils.convertStringToArray(mCursor.getString(INDEX_INGREDIENT)));
                BakeryInformationHelper.setStepsId(OpenBakingJsonUtils.convertStringToArray(mCursor.getString(INDEX_STEPS_ID)));
                BakeryInformationHelper.setStepsShortDescription(OpenBakingJsonUtils.convertStringToArray(mCursor.getString(INDEX_SHORT_DESCRIPTION)));
                BakeryInformationHelper.setStepsDescription(OpenBakingJsonUtils.convertStringToArray(mCursor.getString(INDEX_DESCRIPTION)));
                BakeryInformationHelper.setStepsVideoUrl(OpenBakingJsonUtils.convertStringToArray(mCursor.getString(INDEX_VIDEO_URL)));
                BakeryInformationHelper.setStepsThumbnailUrl(OpenBakingJsonUtils.convertStringToArray(mCursor.getString(INDEX_THUMBNAIL_URL)));
                BakeryInformationHelper.setItemServings(mCursor.getString(INDEX_SERVINGS));
                BakeryInformationHelper.setItemImagePath(mCursor.getString(INDEX_IMAGE_URL));



                Log.v(ItemListActivity.class.getSimpleName(),"The Cursotr name for the bakery is "  +BakeryInformationHelper.getItemName());
                /**if (mTwoPane) {
                 Bundle arguments = new Bundle();
                 arguments.putString(ItemDetailFragment.ARG_ITEM_ID, mItem.id);
                 ItemDetailFragment fragment = new ItemDetailFragment();
                 fragment.setArguments(arguments);
                 getSupportFragmentManager().beginTransaction()
                 .replace(R.id.item_detail_container, fragment)
                 .commit();
                 } else {
                 Context context = v.getContext();
                 Intent intent = new Intent(context, ItemDetailActivity.class);
                 intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, mItem.id);
                 context.startActivity(intent);
                 }*/
                Intent intent = new Intent(v.getContext(),DetailListActivity.class);
                startActivity(intent);

                mClickHandler.onClick(id);
            }
        }







    }

}
