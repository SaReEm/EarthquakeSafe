package com.codepath.earthquakemonitor.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.codepath.earthquakemonitor.R;
import com.codepath.earthquakemonitor.models.Filters;
import com.codepath.earthquakemonitor.utils.ConversionsUtils;

/**
 * Created by hezhang on 10/12/17.
 */

public class FilterDialogFragment extends DialogFragment implements DatePickerFragment.DateDialogListener
{
    private final String TAG = "FilterDialogFragTAG";

    private CheckBox cbUseMagnitude;
    private TextView tvMagnitudeDisplay;
    private SeekBar sbMagnitude;

    private CheckBox cbUseDistance;
    private TextView tvDistanceDisplay;
    private SeekBar sbDistance;

    private CheckBox cbUseDepth;
    private TextView tvDepthDisplay;
    private SeekBar sbDepth;

    private Button btnStartTime;

    private int currentMagnitude;
    private int currentDistance;
    private int currentDepth;

    private boolean modifiedMagnitude = false;
    private boolean modifiedDistance = false;
    private boolean modifiedDepth = false;

    private SharedPreferences mSettings;
    private SharedPreferences.Editor mEditor;

    private final String sharedPrefMinMag = "min_magnitude";
    private final String sharedPrefMinDist = "min_distance";
    private final String sharedPrefMaxDepth = "max_depth";

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
        mSettings = getActivity().getSharedPreferences("Filters", Context.MODE_PRIVATE);
        filter = Filters.getInstance();
        View view = inflater.inflate(R.layout.fragment_filters, container);
        sbMagnitude = view.findViewById(R.id.seekBarMagnitude);
        sbDistance = view.findViewById(R.id.seekBarDistance);
        sbDepth = view.findViewById(R.id.seekBarDepth);
        btnStartTime = view.findViewById(R.id.btnStartTimeValue);
        tvDistanceDisplay = view.findViewById(R.id.tvDistanceDisplayed);

        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        btnStartTime.setText(filter.getStartTime());

        cbUseMagnitude = view.findViewById(R.id.cbUseMagnitude);
        cbUseMagnitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = cbUseMagnitude.isChecked();
                onClickUseMagnitude(isChecked);
            }
        });
        tvMagnitudeDisplay = view.findViewById(R.id.tvMagnitudeDisplayed);

        sbMagnitude.setProgress(filter.getMinMagnitude());
        sbMagnitude.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    currentMagnitude = progress;
                    modifiedMagnitude = true;

                    tvMagnitudeDisplay.setText(Integer.toString(progress));
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
                int distMiles = ConversionsUtils.kmToMiles(progress);
                String distString = distMiles + " miles";
                tvDistanceDisplay.setText(distString);
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
                int depthMiles = ConversionsUtils.kmToMiles(progress);
                String distString = depthMiles + " miles";
                tvDepthDisplay.setText(distString);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        cbUseDistance = view.findViewById(R.id.cbUseDistance);
        cbUseDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = cbUseDistance.isChecked();
                onClickUseDistance(isChecked);
            }
        });

        tvDepthDisplay = view.findViewById(R.id.tvDepthDisplayed);
        cbUseDepth = view.findViewById(R.id.cbUseDepth);
        cbUseDepth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = cbUseDepth.isChecked();
                onClickUseDepth(isChecked);
            }
        });

        initValues();

        return view;
    }

    private void initValues(){
        //****MAGNITUDE
        int setting = mSettings.getInt(sharedPrefMinMag, -1);
        int minMagnitude;
        if(setting == -1){
            minMagnitude = filter.getMinMagnitude();
            Log.d(TAG, "No value in sharedPreference for min_magnitude");

        }
        else{
            minMagnitude = setting;
            Log.d(TAG, "Found value in sharedPreference for min_magnitude = " + setting);
        }
        boolean useMagnitude = filter.isUseMinMagnitude();
        sbMagnitude.setProgress(minMagnitude);
        tvMagnitudeDisplay.setText(Integer.toString(minMagnitude));
        sbMagnitude.setActivated(useMagnitude);
        cbUseMagnitude.setChecked(useMagnitude);

        //****DISTANCE
        setting = mSettings.getInt(sharedPrefMinDist, -1);
        int dist;
        if(setting == -1){
            dist = filter.getDistance();
            Log.d(TAG, "No value in sharedPreference for min_distance");
        }
        else{
            dist = setting;
            Log.d(TAG, "Found value in sharedPreference for min_distance = " + setting);

        }
        boolean useDistance = filter.isUseDistance();
        sbDistance.setProgress(dist);
        int distMiles = ConversionsUtils.kmToMiles(dist);
        String distString = distMiles + " miles";
        tvDistanceDisplay.setText(distString);
        sbDistance.setActivated(useDistance);
        cbUseDistance.setChecked(useDistance);

        //****DEPTH
        setting = mSettings.getInt(sharedPrefMaxDepth, -1);
        int depth;
        if(setting == -1){
            depth = filter.getMaxDepth();
            Log.d(TAG, "No value in sharedPreference for max_depth");
        }
        else{
            depth = setting;
            Log.d(TAG, "Found value in sharedPreference for max_depth = " + setting);
        }

        boolean useDepth = filter.isUseDepth();
        sbDepth.setProgress(depth);
        int depthMiles = ConversionsUtils.kmToMiles(depth);
        String depthString = depthMiles + " miles";
        tvDepthDisplay.setText(depthString);
        sbDepth.setActivated(useDepth);
        cbUseDepth.setChecked(useDepth);

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
                Log.d(TAG,"Save filters setting!");
            }
        });

        // Get rid of the title bar
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);

    }

    void saveFiltersSettings(){
        mEditor = mSettings.edit();
        if(modifiedMagnitude){
            filter.setMinMagnitude(currentMagnitude);
            Log.d(TAG, "Modify filter minMagnitude and shared preferences = " + currentMagnitude);
            mEditor.putInt(sharedPrefMinMag, currentMagnitude);
        }

        if(modifiedDistance){
            filter.setDistance(currentDistance);
            Log.d(TAG, "Modify filter distance and shared preferences = " + currentDistance);
            mEditor.putInt(sharedPrefMinDist, currentDistance);
        }
        if(modifiedDepth){
            filter.setMaxDepth(currentDepth);
            Log.d(TAG, "Modify filter depth and shared preferences = " + currentDepth);
            mEditor.putInt(sharedPrefMaxDepth, currentDepth);
        }

        //save in sharedPref
        mEditor.apply();

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

    private void showDatePicker() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.setTargetFragment(FilterDialogFragment.this, 300);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSelected(int year, int month, int day){
        btnStartTime.setText(year + "/" + month + "/" + day);
        filter.setStartTime(year + "-" + month + "-" + day);
        btnStartTime.setText(filter.getStartTime());

        Log.d(TAG, year + "/" + month + "/" + day);

    }

    public void onClickUseMagnitude(boolean useMagnitude){
        filter.setUseMinMagnitude(useMagnitude);
        sbMagnitude.setEnabled(useMagnitude);
    }

    public void onClickUseDistance(boolean useDistance){
        filter.setUseDistance(useDistance);
        sbDistance.setEnabled(useDistance);
    }

    public void onClickUseDepth(boolean useDepth){
        filter.setUseDepth(useDepth);
        sbDepth.setEnabled(useDepth);
    }


}
