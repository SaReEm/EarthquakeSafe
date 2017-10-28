package com.codepath.earthquakemonitor;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.codepath.earthquakemonitor.utils.ParseQueryClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity
{
    private final String TAG = "ProfileActivity";
    private Button btnLogout;
    private Switch swMySafeStatus;
    private ImageView ivProfile;

    ProgressBar pb;
    // PICK_PHOTO_CODE is a constant integer
    public final static int PICK_PHOTO_CODE = 1046;
    private final String SAVING_DIR = "/data/user/0/com.codepath.earthquakemonitor/app_imageDir/";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Handle log out onClick
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        swMySafeStatus = findViewById(R.id.swMySafeStatus);
        swMySafeStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d(TAG, "send: " + b);
                ParseQueryClient.changeSafeStatus(b);
            }
        });

        // Load the selected image into a preview
        ivProfile = (ImageView) findViewById(R.id.ivProfilePic);
        pb = (ProgressBar) findViewById(R.id.pbLoading);
        loadImageFromStorage(SAVING_DIR);
    }

    public void onClickProfile(View view) {
        onPickPhoto(view);
    }

    // Trigger gallery selection for a photo
    public void onPickPhoto(View view) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }

    private void loadImageFromStorage(String path)
    {
        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img=(ImageView)findViewById(R.id.ivProfilePic);
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri photoUri = data.getData();
            // Do something with the photo based on Uri
            Bitmap selectedImage = null;
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveImageAsync(selectedImage);
            ivProfile.setImageBitmap(selectedImage);
        }
    }

    private void saveImageAsync(Bitmap bitmap){

        new SavePicture().execute(bitmap);
    }


    private class SavePicture extends AsyncTask<Bitmap, Integer, Bitmap> {
        protected void onPreExecute() {
            // Runs on the UI thread before doInBackground
            // Good for toggling visibility of a progress indicator
            pb.setVisibility(ProgressBar.VISIBLE);
        }

        protected Bitmap doInBackground(Bitmap... path) {
            // Some long-running task like downloading an image.
            Bitmap bitmap = saveImageInBackground(path[0]);
            return bitmap;
        }

        protected void onProgressUpdate() {
            // Executes whenever publishProgress is called from doInBackground
            // Used to update the progress indicator
        }

        protected void onPostExecute(Bitmap bitmap) {
            // This method is executed in the UIThread
            // with access to the result of the long running task
            // Hide the progress bar
            pb.setVisibility(ProgressBar.INVISIBLE);
        }
    }


    private Bitmap saveImageInBackground(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String path = directory.getAbsolutePath();
        Log.d(TAG, path);
        return bitmapImage;
    }


}
