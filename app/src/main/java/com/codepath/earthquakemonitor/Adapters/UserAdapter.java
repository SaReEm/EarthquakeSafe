package com.codepath.earthquakemonitor.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codepath.earthquakemonitor.R;
import com.codepath.earthquakemonitor.models.User;
import com.codepath.earthquakemonitor.utils.ParseQueryClient;
import com.parse.ParseUser;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private List<ParseUser> mUsers;
    private Context context;
    private UserAdapterListener mListener;

    public interface  UserAdapterListener {
        public void onItemSelected(View view, int position);
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
        holder.tvName.setText(user.getUsername());
        holder.btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQueryClient.follow(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public Button btnAddFriend;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvUserName);
            btnAddFriend = itemView.findViewById(R.id.btn_AddFriend);

        }
    }

}
