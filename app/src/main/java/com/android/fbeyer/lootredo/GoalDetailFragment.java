package com.android.fbeyer.lootredo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.android.fbeyer.lootredo.dummy.DummyContent;

/**
 * A fragment representing a single Goal detail screen.
 * This fragment is either contained in a {@link GoalListActivity}
 * in two-pane mode (on tablets) or a {@link GoalDetailActivity}
 * on handsets.
 */
public class GoalDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GoalDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_goal_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            getActivity().setTitle(mItem.name + " Detail");
            ((TextView) rootView.findViewById(R.id.goal_name)).setText(mItem.name);
            ((TextView) rootView.findViewById(R.id.goal_cost)).setText(String.format("%.2f", mItem.cost));
            ((TextView) rootView.findViewById(R.id.goal_date)).setText(mItem.date.toString());
        }

        return rootView;
    }
}
