package com.parse.ummalibu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * Created by rjaylward on 1/8/15.
 */
public class NotificationsAdapter extends ParseQueryAdapter<ParseObject>{

    public NotificationsAdapter(Context context) {

        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("notification");
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
            v = View.inflate(getContext(), R.layout.notification_cell_layout, null);
        }

        super.getItemView(object, v, parent);

        // Add the title view
        TextView alertTextView = (TextView) v.findViewById(R.id.alert_text);
        alertTextView.setText(object.getString("alert"));

        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String alertText = formatter.format(object.getCreatedAt());

        TextView alertDateTextView = (TextView) v.findViewById(R.id.alert_date);
        alertDateTextView.setText(alertText);

        return v;

    }

}
