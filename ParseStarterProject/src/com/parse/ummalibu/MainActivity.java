package com.parse.ummalibu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends Activity {

    //ImageButton sermonButton;
    //ImageButton aboutButton;
    Intent detailIntent;
    Intent worshipIntent;
    Intent prayerIntent;
    Intent eventsIntent;
    Intent aboutUMIntent;
    Intent coffeeIntent;
    Intent umslIntent;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setTheme(android.R.style.Theme_Holo);

        addListenerOnButton();
        detailIntent = new Intent(this, ParseStarterProjectActivity.class);
        worshipIntent = new Intent(this, WorshipActivity.class);
        prayerIntent = new Intent(this, PrayerActivity.class);
        eventsIntent = new Intent(this, EventsActivity.class);
        coffeeIntent = new Intent(this, CoffeeActivity.class);
        aboutUMIntent = new Intent(this, AboutUMActivity.class);
        umslIntent = new Intent(this, UmslMenuActivity.class);
    }

    public void addListenerOnButton() {

        ImageButton sermonButton = (ImageButton) findViewById(R.id.sermonButton1);
        sermonButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(detailIntent);
            }
        });
        ImageButton aboutButton = (ImageButton) findViewById(R.id.aboutButton2);
        aboutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(aboutUMIntent);
            }
        });
        ImageButton worshipButton = (ImageButton) findViewById(R.id.worshipButton6);
        worshipButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(worshipIntent);
            }
        });
        ImageButton prayerButton = (ImageButton) findViewById(R.id.prayerButton3);
        prayerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(prayerIntent);
            }
        });
        ImageButton eventsButton = (ImageButton) findViewById(R.id.eventsButton5);
        eventsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(eventsIntent);
            }
        });
        ImageButton coffeeButton = (ImageButton) findViewById(R.id.coffeeButton4);
        coffeeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(coffeeIntent);
            }
        });
        ImageButton umslButton = (ImageButton) findViewById(R.id.umslButton7);
        umslButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // get prompts.xml view
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View promptView = layoutInflater.inflate(R.layout.password_prompt, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                // set prompts.xml to be the layout file of the alertdialog builder
                alertDialogBuilder.setView(promptView);
                final EditText passwordEditText = (EditText) promptView.findViewById(R.id.password_editText);

                // setup a dialog window
                alertDialogBuilder
                        //.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                if (passwordEditText.getText().toString().equalsIgnoreCase("umsl2014")) {
                                    startActivity(umslIntent);
                                } else {
                                    Toast.makeText(MainActivity.this,
                                            "Password was incorrect", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create an alert dialog
                AlertDialog alertD = alertDialogBuilder.create();
                alertD.show();
                //startActivity(umslIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
