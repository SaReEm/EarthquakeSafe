package com.codepath.earthquakemonitor.Adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private final String TAG = "UserAdapterTAG";
    private List<ParseUser> mUsers;
    private Context context;
    private UserAdapterListener mListener;

    public interface  UserAdapterListener {
        public void onFollow(View view, int position);
    }

    public UserAdapter(List<ParseUser> users, UserAdapterListener listener) {
        mUsers = users;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View userView = inflater.inflate(R.layout.item_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ParseUser user = mUsers.get(position);
        String name = user.getUsername();
        holder.tvName.setText(name);

        Bitmap bmp = loadImageFromServer(user);
        if(bmp != null){
            holder.ivUserProfilePic.setImageBitmap(bmp);
        }
        else{
            holder.ivUserProfilePic.setImageResource(R.drawable.profile_png);
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
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivUserProfilePic;
        public TextView tvName;
        public Button btnAddFriend;

        public ViewHolder(View itemView) {
            super(itemView);
            ivUserProfilePic = itemView.findViewById(R.id.ivFollowProfilePic);
            tvName = itemView.findViewById(R.id.tvUserName);
            btnAddFriend = itemView.findViewById(R.id.btn_AddFriend);
            btnAddFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        mListener.onFollow(view, position);
                    }
                }
            });

        }
    }

}
