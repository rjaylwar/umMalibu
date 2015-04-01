package com.parse.ummalibu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by rjaylward on 12/21/14.
 */
public class SongsTableAdapter extends ParseQueryAdapter<ParseObject>{

    //Context context;
    //private List<WorldPopulation> worldpopulationlist = null;
    //private ArrayList<WorldPopulation> arraylist;

    public SongsTableAdapter(Context context) {

        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("songs");
                query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
                query.orderByDescending("createdAt");
                return query;
            }
        });
    }

    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.songs_table_layout, null);
        }

        super.getItemView(object, v, parent);

        // Add the title view
        TextView titleTextView = (TextView) v.findViewById(R.id.song_name);
        titleTextView.setText(object.getString("title"));

        return v;

    }

}
