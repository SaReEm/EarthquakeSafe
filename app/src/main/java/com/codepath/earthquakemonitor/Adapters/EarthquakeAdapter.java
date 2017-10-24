package com.codepath.earthquakemonitor.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.earthquakemonitor.utils.ConversionsUtils;
import com.codepath.earthquakemonitor.R;
import com.codepath.earthquakemonitor.models.Earthquake;

import java.util.ArrayList;

/**
 * Created by hezhang on 10/14/17.
 */

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.ViewHolder>
{
    private ArrayList<Earthquake> mEarthquakes;
    Context context;
    private EarthquakeAdapterListener mListener;

    // Define an interface required by the ViewHolder
    public interface EarthquakeAdapterListener {
        public void onItemSelected(View view, int position);
        public void onItemLongClickSelected(View view, int position);
    }

    // Pass in the earthquake array in the constructor
    public EarthquakeAdapter(ArrayList<Earthquake> earthquakes, EarthquakeAdapterListener listener) {
        mEarthquakes = earthquakes;
        mListener = listener;
    }

//    public EarthquakeAdapter(ArrayList<Earthquake> earthquakes) {
//        mEarthquakes = earthquakes;
//    }

    // For each row, inflate the layout and cache references into ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.item_earthquake, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    // Bind the values based on the position of the element

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data according to position
        Earthquake earthquake = mEarthquakes.get(position);
        // Populate the views according to this data
        holder.tvPlace.setText(earthquake.getPlace());
        holder.tvMagnitude.setText(earthquake.getMag().toString());
        // Change the magnitude color based on the magnitude
        if (earthquake.getMag() <=4 ) {
            holder.tvMagnitude.setTextColor(Color.GREEN);
        } else if (earthquake.getMag() > 4 && earthquake.getMag() < 6) {
            holder.tvMagnitude.setTextColor(Color.YELLOW);
        } else {
            holder.tvMagnitude.setTextColor(Color.RED);
        }
        holder.tvTimeStamp.setText(ConversionsUtils.getRelativeTimeAgo(earthquake.getTime()));
    }

    @Override
    public int getItemCount() {
        return mEarthquakes.size();
    }

    // Create ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPlace;
        public TextView tvMagnitude;
        public TextView tvTimeStamp;

        public ViewHolder(View itemView)
        {
            super(itemView);

            // Perform findViewById lookups

            tvPlace = (TextView) itemView.findViewById(R.id.tvPlace);
            tvMagnitude = (TextView) itemView.findViewById(R.id.tvMagnitude);
            tvTimeStamp = (TextView) itemView.findViewById(R.id.tvTimeStamp);

            // Handle row click event
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // get the position of row element
                    if (mListener != null) {
                        // Get the position of row element
                        int position = getAdapterPosition();
                        // fire the listener callback
                        mListener.onItemSelected(v, position);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View view)
                {
                    // get the position of row element
                    if (mListener != null) {
                        // Get the position of row element
                        int position = getAdapterPosition();
                        // fire the listener callback
                        mListener.onItemLongClickSelected(view, position);
                    }
                    return true;
                }
            });
        }
    }


}
