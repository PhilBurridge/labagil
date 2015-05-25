package com.example.phil.labagil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.phil.labagil.data.Datasource;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ItemListFragment} and the item details
 * (if present) is a {@link ItemDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link ItemListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ItemListActivity extends ActionBarActivity
        implements ItemListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ItemListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.item_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link ItemListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(long id, String name, int score, String desc) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Log.d("menu", "SELCTECTEDTWOPANE");
            Bundle arguments = new Bundle();
            arguments.putLong(Datasource.COLUMN_ID, id);
            arguments.putString(Datasource.COLUMN_TITLE, name);
            arguments.putString(Datasource.COLUMN_DESCRIPTION, desc);
            arguments.putInt(Datasource.COLUMN_RATING, score);

            getSupportFragmentManager().popBackStack();
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment).addToBackStack(null)
                    .commit();
            //ItemDetailFragment idf = new ItemDetailFragment();
            //idf.setArguments(arguments); vad är deettA?


            //mActionMode = startActionMode(mActionModeCallback);

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Log.d("menu", "SELCTECTEDONEPANE");
            Intent detailIntent = new Intent(this, ItemDetailActivity.class);
            detailIntent.putExtra(Datasource.COLUMN_ID, id);
            detailIntent.putExtra(Datasource.COLUMN_DESCRIPTION, desc);
            detailIntent.putExtra(Datasource.COLUMN_TITLE, name);
            detailIntent.putExtra(Datasource.COLUMN_RATING, score);
            //startActivityForResult(detailIntent, 1);
            startActivity(detailIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
