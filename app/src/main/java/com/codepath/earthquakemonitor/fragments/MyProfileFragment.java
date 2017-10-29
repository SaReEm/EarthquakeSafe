package com.codepath.earthquakemonitor.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.codepath.earthquakemonitor.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import static android.R.attr.data;


public class MyProfileFragment extends DialogFragment {
    ImageView ivTest;

    public static MyProfileFragment getInstance(ParseObject parseObject) {
        MyProfileFragment myProfileFragment = new MyProfileFragment();
        Bundle args = new Bundle();
        try {
            args.putByteArray("pic", ((ParseFile) parseObject.get("file")).getData());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myProfileFragment.setArguments(args);
        return myProfileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_layout, container, false);
        ivTest = v.findViewById(R.id.ivProfileTest);
        byte[] data = getArguments().getByteArray("pic");
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
        ivTest.setImageBitmap(bmp);
        return v;
    }
}
