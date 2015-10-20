package com.parse.ummalibu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ummalibu.base.ToolbarActivity;

public class EventsDetailActivity extends ToolbarActivity {

    String linkUrl;
    Button registerButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        linkUrl = i.getStringExtra("linkUrl");
        String title = i.getStringExtra("title");
        String eventDate = i.getStringExtra("eventDate");
        String descriptionLong = i.getStringExtra("descriptionLong");
        String objectId = i.getStringExtra("objectId");
        String cost = i.getStringExtra("cost");
        String registerButtonText = i.getStringExtra("registerButtonText");


        TextView titleTextView = (TextView) findViewById(R.id.event_detail_title);
        titleTextView.setText(title);

        TextView eventDateTextView = (TextView) findViewById(R.id.event_detail_date);
        eventDateTextView.setText(eventDate);

        TextView eventDescriptionTextView = (TextView) findViewById(R.id.event_description_long);
        eventDescriptionTextView.setText(descriptionLong);

        TextView costTextView = (TextView) findViewById(R.id.event_detail_cost);
        costTextView.setText(cost);

        Button registerButton = (Button) findViewById(R.id.event_detail_register_button);
        registerButton.setText(registerButtonText);
        

        ParseQuery<ParseObject> query = ParseQuery.getQuery("event");
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.whereEqualTo("objectId", objectId);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d("objectId", "The getFirst request failed.");
                } else {
                    ParseImageView ImageViewz = (ParseImageView)findViewById(R.id.events_detail_imgview);
                    //ImageView.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
                    try {
                    ImageViewz.setParseFile(object.getParseFile("image"));
                    ImageViewz.loadInBackground();} catch (Error error) {

                    }
                }
            }
        });

        addListenerOnButton();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_events_detail;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addListenerOnButton() {
        registerButton = (Button) findViewById(R.id.event_detail_register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(linkUrl));
                startActivity(intent);
            }
        });
    }
}
