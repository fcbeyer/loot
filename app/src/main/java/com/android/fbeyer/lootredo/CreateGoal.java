package com.android.fbeyer.lootredo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CreateGoal extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);
    }

    public void saveGoalData(View view) {
        EditText goalName = (EditText) findViewById(R.id.goalName);
        EditText goalCost = (EditText) findViewById(R.id.goalCost);
        DatePicker goalDate = (DatePicker) findViewById(R.id.goalDate);
        int day = goalDate.getDayOfMonth();
        int month = goalDate.getMonth();
        int year = goalDate.getYear();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM-dd-yyyy");
        String formatedDate = sdf.format(new Date(year, month, day));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_goal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
