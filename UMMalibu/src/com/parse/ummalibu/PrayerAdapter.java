package com.parse.ummalibu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by rjaylward on 12/19/14.
 */
public class PrayerAdapter extends ParseQueryAdapter<ParseObject> {

    Context context;
    //private List<WorldPopulation> worldpopulationlist = null;
    //private ArrayList<WorldPopulation> arraylist;

    public PrayerAdapter(Context context) {

        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("prayers");
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
            v = View.inflate(getContext(), R.layout.prayer_cell_layout, null);
        }

        super.getItemView(object, v, parent);

        // Add the title view
        TextView nameView = (TextView) v.findViewById(R.id.name_view);
        nameView.setText(object.getString("name"));

        // Add a reminder of how long this item has been outstanding
        TextView prayerView = (TextView) v.findViewById(R.id.prayer_request_view);
        prayerView.setText(object.getString("prayerRequest"));
        return v;
    }
}