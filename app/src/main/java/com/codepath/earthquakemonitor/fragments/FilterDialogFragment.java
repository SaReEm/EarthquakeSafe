package com.codepath.earthquakemonitor.fragments;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;


import com.codepath.earthquakemonitor.R;

/**
 * Created by hezhang on 10/12/17.
 */

public class FilterDialogFragment extends DialogFragment
{
    public FilterDialogFragment() {
    }

    // constructor
    public static FilterDialogFragment newInstance() {
        FilterDialogFragment filterDialogFragment = new FilterDialogFragment();
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
                Toast.makeText(getActivity(),"TODO: save filters setting!", Toast.LENGTH_LONG ).show();
                dismiss();
            }
        });
    }
}
