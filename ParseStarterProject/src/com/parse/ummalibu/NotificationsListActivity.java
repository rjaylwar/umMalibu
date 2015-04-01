package com.parse.ummalibu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class NotificationsListActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private NotificationsAdapter alternateListAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(android.R.style.Theme_Holo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //setContentView(R.layout.activity_notifications_list);

        // Initialize the subclass of ParseQueryAdapter
        alternateListAdapter = new NotificationsAdapter(this);

        // Initialize ListView and set initial view to mainAdapter
        listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(alternateListAdapter);
        alternateListAdapter.loadObjects();

        // 5. Set this activity to react to list items being pressed
        listView.setOnItemClickListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notifications_list, menu);
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

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
