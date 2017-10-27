package com.codepath.earthquakemonitor.fragments;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.earthquakemonitor.R;
import com.codepath.earthquakemonitor.models.Earthquake;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import org.parceler.Parcels;

/**
 * Created by hezhang on 10/23/17.
 */

public class EarthquakeActionFragment extends DialogFragment
{
    private ShareButton btnFbShare;
    private TextView tvUSGS;

    public EarthquakeActionFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_earthquake_action, container);

        // Get the earthquake object
        final Earthquake earthquake = Parcels.unwrap(getArguments().getParcelable("Earthquake"));
        Toast.makeText(getContext(),earthquake.getUrl(), Toast.LENGTH_SHORT).show();

        // Define the share content as the usgs url
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(earthquake.getUrl()))
                .build();

        btnFbShare = (ShareButton) view.findViewById(R.id.btnFbShare);
        tvUSGS = view.findViewById(R.id.btnFindOnMap);

        // Add a share button
        btnFbShare.setShareContent(content);

        btnFbShare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getActivity(),"Share", Toast.LENGTH_SHORT).show();
            }
        });

        tvUSGS.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Center the current earthquake on the map
                Toast.makeText(getActivity(), "Focus", Toast.LENGTH_SHORT).show();
                // Transfer the clicked earthquake's lat/long to the mapActivity
                ((EarthquakeListFragment.EarthquakeSelectedListener) getActivity()).onEarthquakeClicked(earthquake);

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
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
