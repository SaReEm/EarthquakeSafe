package com.codepath.earthquakemonitor.utils;


import com.codepath.earthquakemonitor.models.User;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.File;

public class ImageStorageTest {

    public static ParseObject save() {
        File file = new File("/data/user/0/com.codepath.earthquakemonitor/app_imageDir/", "profile.jpg");
        ParseFile parsefile = new ParseFile(file);
        try {
            parsefile.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ParseObject object = new ParseObject("TestObject");
        object.put("file", parsefile);
        try {
            object.save();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return object;
    }



}
