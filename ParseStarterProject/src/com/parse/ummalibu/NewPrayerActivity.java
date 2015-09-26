package com.parse.ummalibu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ummalibu.Base.ToolbarActivity;


public class NewPrayerActivity extends ToolbarActivity {

    Button submitButton;
    Switch mySwitch;
    EditText nameText;
    EditText prayerText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_prayer);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        nameText = (EditText) findViewById(R.id.name_edit);
        prayerText = (EditText) findViewById(R.id.new_request_box);


        mySwitch = (Switch) findViewById(R.id.public_prayer_switch);
        //set the switch to ON
        mySwitch.setChecked(true);
        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (mySwitch.isChecked()){
                    Toast.makeText(NewPrayerActivity.this,
                            "Your prayer request will be visible to others", Toast.LENGTH_SHORT).show();}
                else {Toast.makeText(NewPrayerActivity.this,
                        "Your prayer request will not be visible to others", Toast.LENGTH_SHORT).show();}
            }
        });
        addListenerOnButton();

    }

    private void addListenerOnButton() {

        submitButton = (Button) findViewById(R.id.submit_prayer_button);

        submitButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (mySwitch.isChecked()){
                    Toast.makeText(NewPrayerActivity.this,
                            "Your prayer request will be visible to others", Toast.LENGTH_SHORT).show();
                    ParseObject prayerRequest = new ParseObject("prayers");
                    prayerRequest.put("name", nameText.getText().toString());
                    prayerRequest.put("prayerRequest", prayerText.getText().toString());
                    prayerRequest.saveInBackground();
                }else{
                    Toast.makeText(NewPrayerActivity.this,
                            "Your prayer request will not be visible to others", Toast.LENGTH_SHORT).show();
                    ParseObject prayerRequest = new ParseObject("private_prayers");
                    prayerRequest.put("name", nameText.getText().toString());
                    prayerRequest.put("prayerRequest", prayerText.getText().toString());
                    prayerRequest.saveInBackground();
                }
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_prayer, menu);
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
