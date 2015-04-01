package com.parse.ummalibu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by rjaylward on 12/15/14.
 */
public class CustomAdapter extends ParseQueryAdapter<ParseObject> {

    Context context;
    //private List<WorldPopulation> worldpopulationlist = null;
    //private ArrayList<WorldPopulation> arraylist;

    public CustomAdapter(Context context) {

        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("sermons");
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
            v = View.inflate(getContext(), R.layout.sub_layout, null);
        }

        super.getItemView(object, v, parent);

        // Add and download the image
        ParseImageView todoImage = (ParseImageView) v.findViewById(R.id.icon);
        ParseFile imageFile = object.getParseFile("graphic");
        if (imageFile != null) {
            todoImage.setParseFile(imageFile);
            todoImage.loadInBackground();
        }

        // Add the title view
        TextView titleTextView = (TextView) v.findViewById(R.id.text1);
        titleTextView.setText(object.getString("title"));

        // Add a reminder of how long this item has been outstanding
        TextView datePreached = (TextView) v.findViewById(R.id.datePreached);
        datePreached.setText(object.getString("datePreached"));
        return v;

    }

}
