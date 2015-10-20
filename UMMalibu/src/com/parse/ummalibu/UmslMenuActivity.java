package com.parse.ummalibu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.SaveCallback;
import com.parse.ummalibu.base.ToolbarActivity;


public class UmslMenuActivity extends ToolbarActivity {

    Button submitButton;
    EditText channelTextBox;
    EditText expirationTextBox;
    EditText messageTextBox;
    CheckBox checkBox;
    Boolean addToList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        channelTextBox = (EditText) findViewById(R.id.channel_edittext);
        expirationTextBox = (EditText) findViewById(R.id.expiration_edittext);
        messageTextBox = (EditText) findViewById(R.id.message_edittext);

        addToList = true;

        addListenerOnButton();
        addListenerOnChk();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_umsl_menu;
    }

    public void addListenerOnChk() {

        checkBox = (CheckBox) findViewById(R.id.addToListBox);
        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    addToList = true;
                }
                if (!checkBox.isChecked()) {
                    addToList = false;
                }
            }
        });}

    public void addListenerOnButton() {
        submitButton = (Button) findViewById(R.id.send_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (channelTextBox.getText().toString().equals("+") || channelTextBox.getText().toString().equals("add channel") || channelTextBox.getText().toString().equals("delete channel") || channelTextBox.getText().toString().equals("-")) {

                    if (channelTextBox.getText().toString().equals("+") || channelTextBox.getText().toString().equals("add channel")) {
                        ParsePush.subscribeInBackground(expirationTextBox.getText().toString(), new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                                    Toast.makeText(getApplicationContext(), "You have subscribed to the broadcast channel",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("com.parse.ummalibu", "failed to subscribe for push", e);
                                }
                            }
                        });
                    } else {ParsePush.unsubscribeInBackground(expirationTextBox.getText().toString(), new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.d("com.parse.ummalibu", "successfully unsubscribed from the broadcast channel.");
                                Toast.makeText(getApplicationContext(), "You have unsubscribed from the broadcast channel",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("com.parse.ummalibu", "failed to unsubscribe from push", e);
                            }
                        }
                    });} }
                else {
                    presentAlert();}
            }
        });
    }

    public void presentAlert() {

        new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Are you sure you want to send this message to all users?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ParsePush push = new ParsePush();
                        push.setChannel(channelTextBox.getText().toString());

                        if (expirationTextBox.getText().toString().length() > 1){
                            long expirationTime = Long.parseLong(expirationTextBox.getText().toString());
                            push.setExpirationTimeInterval(expirationTime);
                        }

                        push.setMessage(messageTextBox.getText().toString());
                        push.sendInBackground();

                        if (addToList) {
                        ParseObject notificationObject = new ParseObject("notification");
                        notificationObject.put("alert", messageTextBox.getText().toString());
                        notificationObject.saveInBackground(); }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_umsl_menu, menu);
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

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
