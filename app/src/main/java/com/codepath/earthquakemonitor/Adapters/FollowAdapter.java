package com.codepath.earthquakemonitor.Adapters;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codepath.earthquakemonitor.R;
import com.parse.ParseUser;

import java.util.List;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.ViewHolder> {
    private List<ParseUser> mFollows;
    private Context context;
    private FollowAdapterListener mListener;

    public interface FollowAdapterListener{
        public void onUnFollow(View view, int position);
    }

    public FollowAdapter(List<ParseUser> users, FollowAdapterListener listener) {
        mFollows = users;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View userView = inflater.inflate(R.layout.item_follow, parent, false);
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    public interface BtnListener {
        public void onClick(View view, int position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ParseUser user = mFollows.get(position);
        String name = user.getUsername();
        holder.tvName.setText(name);
        String safeStatus = user.getString("safeStatus");
        holder.tvStatus.setText(safeStatus);
        if (safeStatus != null) {
            if (safeStatus.equals("safe")) {
                holder.tvStatus.setTextColor(Color.GREEN);
            } else {
                holder.tvStatus.setTextColor(Color.YELLOW);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mFollows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvStatus;
        public Button btnUnfollow;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvFollowName);
            tvStatus = itemView.findViewById(R.id.tvFollowStatus);
            btnUnfollow = itemView.findViewById(R.id.btn_unfollow);
            btnUnfollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        mListener.onUnFollow(view, position);
                    }
                }
            });

        }
    }
}
