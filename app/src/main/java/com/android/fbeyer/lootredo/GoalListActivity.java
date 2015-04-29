package com.android.fbeyer.lootredo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


/**
 * An activity representing a list of Goals. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link GoalDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link GoalListFragment} and the item details
 * (if present) is a {@link GoalDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link GoalListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class GoalListActivity extends FragmentActivity
        implements GoalListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;


    /*
     * We need an action bar on this screen so that the plus button for a new goal can be added
     * Only do this until we have floating button awesomeness!
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.goal_list_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*
     * Handle the clicking of our action menu!
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_new_goal:
                launchCreateGoalActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void launchCreateGoalActivity() {
        Intent detailIntent = new Intent(this, GoalDetailActivity.class);
        detailIntent.putExtra(GoalDetailFragment.ARG_ITEM_ID, "3");
        startActivity(detailIntent);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_list);
        //setHasOptionsMenu();

        if (findViewById(R.id.goal_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((GoalListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.goal_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link GoalListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(GoalDetailFragment.ARG_ITEM_ID, id);
            GoalDetailFragment fragment = new GoalDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.goal_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, GoalDetailActivity.class);
            detailIntent.putExtra(GoalDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
