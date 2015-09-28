package com.parse.ummalibu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ummalibu.base.ToolbarActivity;


public class EventsActivity extends ToolbarActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    private EventsAdapter alternateListAdapter;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //setTheme(android.R.style.Theme_Holo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Initialize the subclass of ParseQueryAdapter
        alternateListAdapter = new EventsAdapter(this);

        // Initialize ListView and set initial view to mainAdapter
        listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(alternateListAdapter);
        alternateListAdapter.loadObjects();

        // 5. Set this activity to react to list items being pressed
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ParseObject queryObject = alternateListAdapter.getItem(position);

        String title = queryObject.getString("title");
        String eventDate = queryObject.getString("date");
        String linkUrl = queryObject.getString("tumblrUrl");
        String descriptionLong = queryObject.getString("descriptionLong");
        String objectId = queryObject.getObjectId();
        String cost = queryObject.getString("cost");
        String registerButtonText = queryObject.getString("buttonText");

        ParseFile image = queryObject.getParseFile("image");

        // create an Intent to take you over to a new DetailActivity
        Intent detailIntent = new Intent(this, EventsDetailActivity.class);

        detailIntent.putExtra("title", title);
        detailIntent.putExtra("cost", cost);
        detailIntent.putExtra("eventDate", eventDate);
        detailIntent.putExtra("linkUrl", linkUrl);
        detailIntent.putExtra("descriptionLong", descriptionLong);
        detailIntent.putExtra("objectId", objectId);
        detailIntent.putExtra("registerButtonText", registerButtonText);

        // TODO: add any other data you'd like as Extras
        // start the next Activity using your prepared Intent

        //ParseObject eventImage = new ParseObject("eventImage");
        //eventImage.put("image", image);
        //eventImage.pinInBackground();

        startActivity(detailIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        switch (item.getItemId()) {
            case R.id.action_settings:
                // search action
                return true;
            case R.id.action_alerts:
                // location found
                eventMessages();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    private void eventMessages() {
        Intent i = new Intent(this, NotificationsListActivity.class);
        startActivity(i);
    }

}
