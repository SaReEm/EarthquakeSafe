package com.codepath.earthquakemonitor.Adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.earthquakemonitor.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.ViewHolder> {
    private final String TAG = "FollowAdapterTAG";
    private List<ParseUser> mFollows;
    private Context context;
    private FollowAdapterListener mListener;

    private int mRadiusImageProfile = 30; // corner radius, higher value = more rounded
    private int mMarginImageProfile = 5; // crop margin, set to 0 for corners with no crop

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
        Bitmap bmp = loadImageFromServer(user);
        if(bmp != null){
            holder.ivFollowProfilePic.setImageBitmap(bmp);
        }
    }

    private Bitmap loadImageFromServer(ParseUser user) {
        Bitmap bmp = null;
        try {
            byte[] data = new byte[0];
            try {
                ParseFile file = (ParseFile) user.get("file");
                if (file != null) {
                    data = file.getData();
                    bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                } else {
                    Log.d(TAG, "No file on server");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    @Override
    public int getItemCount() {
        return mFollows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivFollowProfilePic;
        public TextView tvName;
        public TextView tvStatus;
        public Button btnUnfollow;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFollowProfilePic = itemView.findViewById(R.id.ivFollowProfilePic);
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
