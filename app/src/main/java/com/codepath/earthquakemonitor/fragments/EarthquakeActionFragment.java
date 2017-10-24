package com.codepath.earthquakemonitor.fragments;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.codepath.earthquakemonitor.R;

/**
 * Created by hezhang on 10/23/17.
 */

public class EarthquakeActionFragment extends DialogFragment
{
    private ImageButton btnFbShare;
    private ImageButton btnFindOnMap;

    public EarthquakeActionFragment() {
    }

    public static EarthquakeActionFragment newInstance() {
        EarthquakeActionFragment earthquakeActionFragment = new EarthquakeActionFragment();
        Bundle args = new Bundle();
        earthquakeActionFragment.setArguments(args);
        return earthquakeActionFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_earthquake_action, container);

        btnFbShare = view.findViewById(R.id.btnFbShare);
        btnFindOnMap = view.findViewById(R.id.btnFindOnMap);

        btnFbShare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getActivity(),"Share", Toast.LENGTH_SHORT).show();
            }
        });

        btnFindOnMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Center the current earthquake on the map
                Toast.makeText(getActivity(), "Focus", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }
}
