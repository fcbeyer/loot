package com.android.fbeyer.lootredo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.android.fbeyer.lootredo.GoalContract.GoalEntry.getCurrentId;
import static com.android.fbeyer.lootredo.GoalContract.GoalEntry.increaseCurrentId;


public class CreateGoal extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);
    }

    public void saveGoalData(View view) {
        EditText goalName = (EditText) findViewById(R.id.goalName);
        EditText goalCost = (EditText) findViewById(R.id.goalCost);
        DatePicker goalDatePicker = (DatePicker) findViewById(R.id.goalDate);
        int day = goalDatePicker.getDayOfMonth();
        int month = goalDatePicker.getMonth();
        int year = goalDatePicker.getYear();
        Calendar cal = new GregorianCalendar(year,month,day);
        Date goalDate = cal.getTime();
        saveGoalsInDB(goalName.getText().toString(), goalCost.getText().toString(), goalDate.toString());
        Intent detailIntent = new Intent(this, GoalListActivity.class);
        startActivity(detailIntent);
    }

    private void saveGoalsInDB(String name, String cost, String date) {
        GoalContract.GoalContractDbHelper mDbHelper = new GoalContract.GoalContractDbHelper(getApplicationContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        int id = getCurrentId() + 1;
        values.put(GoalContract.GoalEntry.COLUMN_NAME_GOAL_ID, id);
        values.put(GoalContract.GoalEntry.COLUMN_NAME_GOAL_NAME, name);
        values.put(GoalContract.GoalEntry.COLUMN_NAME_GOAL_COST, cost);
        values.put(GoalContract.GoalEntry.COLUMN_NAME_GOAL_DATE, date);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                GoalContract.GoalEntry.TABLE_NAME,
                null,
                values);
        increaseCurrentId();
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
