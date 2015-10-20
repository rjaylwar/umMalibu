package com.parse.ummalibu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ummalibu.base.ToolbarActivity;


public class PrayerActivity extends ToolbarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView listView;
    private PrayerAdapter alternateListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the subclass of ParseQueryAdapter
        alternateListAdapter = new PrayerAdapter(this);

        // Initialize ListView and set initial view to mainAdapter
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(alternateListAdapter);
        alternateListAdapter.loadObjects();

        // 5. Set this activity to react to list items being pressed
        listView.setOnItemClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_prayer;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prayer, menu);
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
            case R.id.action_new:
                // location found
                newPrayer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    private void newPrayer() {
        Intent i = new Intent(this, NewPrayerActivity.class);
        startActivity(i);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(PrayerActivity.this,
                "Thank you for your prayers.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

    }
}
