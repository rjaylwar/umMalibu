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
 * Created by rjaylward on 12/19/14.
 */
public class EventsAdapter extends ParseQueryAdapter<ParseObject> {

    Context context;
    //private List<WorldPopulation> worldpopulationlist = null;
    //private ArrayList<WorldPopulation> arraylist;

    public EventsAdapter(Context context) {

        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("event");
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
            v = View.inflate(getContext(), R.layout.activity_events, null);
        }
        super.getItemView(object, v, parent);

        // Add and download the image
        ParseImageView todoImage = (ParseImageView) v.findViewById(R.id.icon);
        ParseFile imageFile = object.getParseFile("image");
        if (imageFile != null) {
            todoImage.setParseFile(imageFile);
            todoImage.loadInBackground();
        }
        // Add the title view
        TextView titleView = (TextView) v.findViewById(R.id.event_name);
        titleView.setText(object.getString("title"));

        TextView dateView = (TextView) v.findViewById(R.id.event_date);
        dateView.setText(object.getString("date"));

        TextView descriptionView = (TextView) v.findViewById(R.id.description_view);
        descriptionView.setText(object.getString("descriptionShort"));

        // Add a reminder of how long this item has been outstanding
        TextView costView = (TextView) v.findViewById(R.id.price_view);
        costView.setText(object.getString("cost"));
        return v;
    }
}