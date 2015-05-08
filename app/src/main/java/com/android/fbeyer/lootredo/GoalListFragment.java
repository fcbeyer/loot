package com.android.fbeyer.lootredo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.android.fbeyer.lootredo.dummy.DummyContent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.android.fbeyer.lootredo.GoalContract.GoalEntry.setCurrentId;

/**
 * A list fragment representing a list of Goals. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link GoalDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class GoalListFragment extends ListFragment {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GoalListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Confirm Delete");
                alert.setMessage("Are you sure you want to delete this record?");
                final int deleteMe = position;
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do your work here
                        DummyContent.ITEMS.remove(deleteMe);
                        setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(
                                getActivity(),
                                android.R.layout.simple_list_item_activated_1,
                                android.R.id.text1,
                                DummyContent.ITEMS));
                        dialog.dismiss();

                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();
                return true;

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: replace with a real list adapter.
        setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                DummyContent.ITEMS));
        readData();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(String.valueOf(DummyContent.ITEMS.get(position).id));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    private void readData() {
        GoalContract.GoalContractDbHelper mDbHelper = new GoalContract.GoalContractDbHelper(getActivity().getApplicationContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                GoalContract.GoalEntry._ID,
                GoalContract.GoalEntry.COLUMN_NAME_GOAL_NAME,
                GoalContract.GoalEntry.COLUMN_NAME_GOAL_COST,
                GoalContract.GoalEntry.COLUMN_NAME_GOAL_DATE
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                GoalContract.GoalEntry._ID + " DESC";

        /*
        Cursor cursor = db.query(
                GoalContract.GoalEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                GoalContract.GoalEntry.COLUMN_NAME_NAME + " = ?",  // The columns for the WHERE clause
                new String[] {"cheerio"},                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        */
        Cursor cursor = db.rawQuery("select * from goal", null);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMMM d HH:mm:ss z yyyy");
        Date itemDate = new Date();
        cursor.moveToFirst();
        String test;
        boolean moveAlong = true;
        if(cursor.getCount() != 0) {
            setCurrentId(cursor.getCount());

            while (moveAlong) {
                test = cursor.getString(cursor.getColumnIndexOrThrow(GoalContract.GoalEntry.COLUMN_NAME_GOAL_DATE));
                try {
                    itemDate = sdf.parse(test);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DummyContent.addTest(new DummyContent.DummyItem(
                        cursor.getInt(cursor.getColumnIndexOrThrow(GoalContract.GoalEntry.COLUMN_NAME_GOAL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(GoalContract.GoalEntry.COLUMN_NAME_GOAL_NAME)),
                        Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(GoalContract.GoalEntry.COLUMN_NAME_GOAL_COST))),
                        itemDate
                ));
                moveAlong = cursor.moveToNext();
            }


        }
    }
}
