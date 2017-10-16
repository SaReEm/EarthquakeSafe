package com.codepath.earthquakemonitor.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.codepath.earthquakemonitor.R;
import com.codepath.earthquakemonitor.models.Filters;

/**
 * Created by hezhang on 10/12/17.
 */

public class FilterDialogFragment extends DialogFragment
{

    private SeekBar sbMagnitude;
    private SeekBar sbDistance;
    private SeekBar sbDepth;

    private int currentMagnitude;
    private int currentDistance;
    private int currentDepth;

    private boolean modifiedMagnitude = false;
    private boolean modifiedDistance = false;
    private boolean modifiedDepth = false;

    Filters filter;
    public FilterDialogFragment() {
    }

    // constructor
    public static FilterDialogFragment newInstance() {
        FilterDialogFragment filterDialogFragment = new FilterDialogFragment();
        // directly via getInstance
        Bundle args = new Bundle();
//        args.putParcelable("filters", filter);
        filterDialogFragment.setArguments(args);
        return filterDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_filters, container);
        sbMagnitude = (SeekBar) view.findViewById(R.id.seekBarMagnitude);
        sbDistance = (SeekBar) view.findViewById(R.id.seekBarDistance);
        sbDepth = (SeekBar) view.findViewById(R.id.seekBarDepth);

        filter = Filters.getInstance();
        sbMagnitude.setProgress(filter.getMinMagnitude());
        sbMagnitude.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    currentMagnitude = progress;
                    modifiedMagnitude = true;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbDistance.setProgress(filter.getDistance());
        sbDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentDistance = progress;
                modifiedDistance = true;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbDepth.setProgress(filter.getMaxDepth());
        sbDepth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentDepth = progress;
                modifiedDepth = true;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        Button btnSaveFilters = (Button) view.findViewById(R.id.btnSaveFilters);
        btnSaveFilters.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                saveFiltersSettings();
                Toast.makeText(getActivity(),"Save filters setting!", Toast.LENGTH_LONG ).show();
            }
        });
    }

    void saveFiltersSettings(){
        if(modifiedMagnitude){
            filter.setMinMagnitude(currentMagnitude);
            Log.d("saveFiltersSettings", "Modify filter minMagnitude = " + currentMagnitude);
        }
        if(modifiedDistance){
            filter.setDistance(currentDistance);
            Log.d("saveFiltersSettings", "Modify filter distance = " + currentDistance);
        }
        if(modifiedDepth){
            filter.setMaxDepth(currentDepth);
            Log.d("saveFiltersSettings", "Modify filter depth = " + currentDepth);
        }
        dismiss();
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

}
