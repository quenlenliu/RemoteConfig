package com.quenlen.remote;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quenlen.remote.dummy.DummyContent;
import com.ziwa.remoteconfig.impl.FuelBaseRemoteConfig;
import com.ziwa.remoteconfig.net.Task;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
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
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            Task task = FuelBaseRemoteConfig.getInstance().fetch();
            task.addOnCompleteListener(new Task.OnCompleteListener() {
                @Override
                public void onComplete(Task task) {
                    Log.d("Detail", Thread.currentThread().getName());
                    if (task.isSuccessful()) {
                        FuelBaseRemoteConfig.getInstance().activateFetched();
                        getView().post(new Runnable() {
                            @Override
                            public void run() {
                                ((TextView)getView().findViewById(R.id.item_detail)).setText("Active After: " + FuelBaseRemoteConfig.getInstance().getString("test"));
                            }
                        });
                    }
                }
            });
            ((TextView) rootView.findViewById(R.id.item_detail)).setText("Active Before: " + FuelBaseRemoteConfig.getInstance().getString("test"));
        }

        return rootView;
    }
}
