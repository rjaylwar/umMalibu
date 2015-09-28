package com.parse.ummalibu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;
import com.parse.ummalibu.base.ToolbarActivity;

public class ParseStarterProjectActivity extends ToolbarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ParseQueryAdapter<ParseObject> mainAdapter;
    private CustomAdapter alternateListAdapter;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //setTheme(android.R.style.Theme_Holo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Initialize main ParseQueryAdapter
        mainAdapter = new ParseQueryAdapter<ParseObject>(this, "sermon");
        mainAdapter.setTextKey("title");
        mainAdapter.setImageKey("graphic");

        // Initialize the subclass of ParseQueryAdapter
        alternateListAdapter = new CustomAdapter(this);

        // Initialize ListView and set initial view to mainAdapter
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(mainAdapter);
        mainAdapter.loadObjects();

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
        String datePreached = queryObject.getString("datePreached");
        String linkUrl = queryObject.getString("directLink");
        String series = queryObject.getString("series");
        String videoType = queryObject.getString("videoType");
        //JSONArray myArray = queryObject.getJSONArray("verses");
        String sourceLink = queryObject.getString("sourceLink");
        String source = queryObject.getString("source");
        String imageUrl = queryObject.getString("imageUrl");
        String mediaPlayerText = queryObject.getString("mediaPlayerText");

        //ParseFile graphic = queryObject.getParseFile("graphic");

        // create an Intent to take you over to a new DetailActivity
        Intent detailIntent = new Intent(this, SermonDetailActivity.class);

        // pack away the data about the cover
        // into your Intent before you head out
        detailIntent.putExtra("linkUrl", linkUrl);
        detailIntent.putExtra("title", title);
        detailIntent.putExtra("datePreached", datePreached);
        detailIntent.putExtra("series", series);
        detailIntent.putExtra("videoType", videoType);
        detailIntent.putExtra("sourceLink", sourceLink);
        detailIntent.putExtra("source", source);
        detailIntent.putExtra("imageUrl", imageUrl);
        detailIntent.putExtra("mediaPlayerText", mediaPlayerText);

        //detailIntent.putExtra("graphic", graphic);

        // TODO: add any other data you'd like as Extras

        // start the next Activity using your prepared Intent
        startActivity(detailIntent);

    }
}