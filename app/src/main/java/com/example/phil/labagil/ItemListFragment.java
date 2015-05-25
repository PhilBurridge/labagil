package com.example.phil.labagil;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.phil.labagil.data.Datasource;
import com.example.phil.labagil.data.Item;
import com.example.phil.labagil.dummy.DummyContent;

/**
 * A list fragment representing a list of Items. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link ItemDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ItemListFragment extends ListFragment {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    private Datasource dataFoo;
    private ArrayAdapter<Item> adapter;
    ActionMode mActionMode;
    boolean checked;


    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(long id, String name, int rating, String desc);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(long id, String name, int rating, String desc) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataFoo = new Datasource(getActivity());
        setHasOptionsMenu(true);
        dataFoo.open();

        // TODO: replace with a real list adapter.
        adapter = new ArrayAdapter<Item>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                dataFoo.fetchAll(0, true));
        setListAdapter(adapter);
    }

    public void deleteItem(Long id) {
        dataFoo.deleteItem(Long.toString(id));
        adapter.notifyDataSetChanged();
        adapter.clear();
        adapter.addAll(dataFoo.fetchAll(0,true));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add:
                dataFoo.insertItem("a",4,"hej");
                adapter.notifyDataSetChanged();
                adapter.clear();
                adapter.addAll(dataFoo.fetchAll(0,true));

                return true;
            case R.id.sorting:
                Sorting1 sortingDialog = new Sorting1();
                sortingDialog.show(getActivity().getSupportFragmentManager(), null);
                return true;
            case R.id.ascendingorder:

                checked = getActivity().getPreferences(0).getBoolean("asc", false);
                Log.d("menu", "ja " + checked);
                item.setChecked(!checked);
                SharedPreferences pref = getActivity().getPreferences(0);
                pref.edit().putBoolean("asc", item.isChecked()).apply();

                adapter.notifyDataSetChanged();
                adapter.clear();
                adapter.addAll(dataFoo.fetchAll(0,checked));

                return true;
            default:
                return false;
        }
    }

    public void updateList() {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        //mCallbacks.onItemSelected(DummyContent.ITEMS.get(position).id);
        //mCallbacks.onItemSelected(dataFoo.fetchAll(0,true));
        Item mItem = (Item) adapter.getItem(position);
        Log.d("manu", "clicking");
        mCallbacks.onItemSelected( mItem.getId(), mItem.getTitle(), mItem.getRating(), mItem.getDescription());
        setActivatedPosition(position);
        if(mActionMode == null)
            mActionMode = getActivity().startActionMode(mActionModeCallback);
        //mCallbacks.onItemSelected(String.valueOf(adapter.getItem(position).getId()), adapter.getItem(position).getTitle(), adapter.getItem(position).getRating(), adapter.getItem(position).getDescription());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        checked = getActivity().getPreferences(0).getBoolean("asc", false);
        Log.d("menu", "on create " + checked);
        menu.findItem(R.id.ascendingorder).setChecked(!checked);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // FÖR CONTExUAL MENU
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.contextual_bar, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
               case R.id.delete:
                    Log.d("menu", "INNNAN DELETE!");
                   if(mActivatedPosition > -1) {
                        deleteItem(adapter.getItem(mActivatedPosition).getId());
                        getActivity().getSupportFragmentManager().popBackStack();
                   }
                   mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
            return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };


}
