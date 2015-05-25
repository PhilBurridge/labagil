package com.example.phil.labagil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.phil.labagil.data.Datasource;
import com.example.phil.labagil.data.Item;
import com.example.phil.labagil.dummy.DummyContent;

import javax.sql.DataSource;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    //private DummyContent.DummyItem mItem;
    //private Datasource.datasource mItem
    Item mItem;

    //private DataSource dataSource;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments().containsKey(Datasource.COLUMN_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            // HUR GÖR MAN
            //mItem = DataSource.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            mItem = new Item();
            mItem.setId(getArguments().getLong(Datasource.COLUMN_ID));
            mItem.setTitle(getArguments().getString(Datasource.COLUMN_TITLE));
            mItem.setDescription(getArguments().getString(Datasource.COLUMN_DESCRIPTION));
            mItem.setRating(getArguments().getInt(Datasource.COLUMN_RATING));


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nya_layout, container, false);
        Log.d("manu", "KOMMER DETTA SKRIVAS FÖRE?");
        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            //((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.content);
            ((TextView) rootView.findViewById(R.id.textView)).setText(mItem.getTitle());
            ((TextView) rootView.findViewById(R.id.textView2)).setText(mItem.getDescription());
            ((TextView) rootView.findViewById(R.id.textView3)).setText(Long.toString(mItem.getId()));

            //((TextView) rootView.findViewById(R.id.ratingBar)).set;
            //findViewById
        }

        return rootView;
    }
}
