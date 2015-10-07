package com.parse.ummalibu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.ummalibu.base.ToolbarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends ToolbarActivity {

    private Intent detailIntent;
    private Intent worshipIntent;
    private Intent prayerIntent;
    private Intent eventsIntent;
    private Intent aboutUMIntent;
    private Intent coffeeIntent;
    private Intent umslIntent;

    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.navigation_view) NavigationView mNavigationView;

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mNavigationView.setBackgroundColor(getResources().getColor(R.color.um_dark_blue));
        mNavigationView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        mNavigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (!menuItem.isChecked())
                    menuItem.setChecked(true);

                switch (menuItem.getItemId()) {
                    case R.id.menu_item_rideshare:
                        startActivity(RideShareActivity.createIntent(MainActivity.this));
                        return true;
                    case R.id.menu_item_settings:
                        startActivity(LoginActivity.createIntent(MainActivity.this));
                        return true;
                    default:
                        return true;
                }
            }
        });

        addListenerOnButton();
        detailIntent = new Intent(this, ParseStarterProjectActivity.class);
        worshipIntent = new Intent(this, WorshipActivity.class);
        prayerIntent = new Intent(this, PrayerActivity.class);
        eventsIntent = new Intent(this, EventsActivity.class);
        coffeeIntent = new Intent(this, CoffeeActivity.class);
        aboutUMIntent = new Intent(this, AboutUMActivity.class);
        umslIntent = new Intent(this, UmslMenuActivity.class);

        initDrawer(mToolbar);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    public void initDrawer(@NonNull Toolbar toolbar) {
        if(mDrawerLayout != null) {
            ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, 0, 0) {
                @Override
                public void onDrawerClosed(View view) {
                    invalidateOptionsMenu();
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    invalidateOptionsMenu();
                }
            };

            mDrawerLayout.setDrawerListener(drawerToggle);
            drawerToggle.syncState();
        }
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
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View promptView = layoutInflater.inflate(R.layout.password_prompt, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                // set prompts.xml to be the layout file of the alertdialog builder
                alertDialogBuilder.setView(promptView);
                final EditText passwordEditText = (EditText) promptView.findViewById(R.id.password_editText);

                // setup a dialog window
                alertDialogBuilder
                        //.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                if (passwordEditText.getText().toString().equalsIgnoreCase("umslisthebest")) {
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
            }
        });
    }
}
