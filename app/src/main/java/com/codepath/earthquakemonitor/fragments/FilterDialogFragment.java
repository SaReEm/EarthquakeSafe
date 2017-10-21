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
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.earthquakemonitor.R;
import com.codepath.earthquakemonitor.models.Filters;

/**
 * Created by hezhang on 10/12/17.
 */

public class FilterDialogFragment extends DialogFragment implements DatePickerFragment.DateDialogListener
{
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
        filter = Filters.getInstance();
        View view = inflater.inflate(R.layout.fragment_filters, container);
        sbMagnitude = (SeekBar) view.findViewById(R.id.seekBarMagnitude);
        sbDistance = (SeekBar) view.findViewById(R.id.seekBarDistance);
        sbDepth = (SeekBar) view.findViewById(R.id.seekBarDepth);
        btnStartTime = (Button) view.findViewById(R.id.btnStartTimeValue);
        tvDistanceDisplay = view.findViewById(R.id.tvDistanceDisplayed);

        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
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
                tvDistanceDisplay.setText(progress + " km");
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
                tvDepthDisplay.setText(progress + " km");

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
        int minMagnitude = filter.getMinMagnitude();
        boolean useMagnitude = filter.isUseMinMagnitude();
        sbMagnitude.setProgress(minMagnitude);
        tvMagnitudeDisplay.setText(Integer.toString(minMagnitude));
        sbMagnitude.setActivated(useMagnitude);
        cbUseMagnitude.setChecked(useMagnitude);

        int dist = filter.getDistance();
        boolean useDistance = filter.isUseDistance();
        sbDistance.setProgress(dist);
        tvDistanceDisplay.setText(Integer.toString(dist) + " km");
        sbDistance.setActivated(useDistance);
        cbUseDistance.setChecked(useDistance);

        int depth = filter.getMaxDepth();
        boolean useDepth = filter.isUseDepth();
        sbDepth.setProgress(depth);
        tvDepthDisplay.setText(Integer.toString(depth) + " km");
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

        Toast.makeText(getContext(), year + "/" + month + "/" + day,Toast.LENGTH_SHORT).show();

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
