package com.example.moonmayor.igclone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_IMAGE_CAPTURE = 1;

    private TextView mInfo;
    private ImageView mImageResult;

    // A spot to remember what file we told the camera to
    // save the last picture to.
    private String mCurrentPhotoFilepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInfo = (TextView) findViewById(R.id.info);
        mImageResult = (ImageView) findViewById(R.id.imageResult);

        try {
            File imageFile = getPictureFile();
            Uri photoUri = FileProvider.getUriForFile(this,
                    "com.example.moonmayor.igclone",
                    imageFile);

            // tell the camera to save the picture to a file
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } catch(IOException e) {
            String error = "Error: " + e.getMessage();
            Log.d("FILE ERROR", error);
            mInfo.setText(error);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {
            // read the Bitmap from the file we saved the picture to
            // and load it in the ImageView.
            setPictureFromFile();
        }
    }

    private File getPictureFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = "JPEG_" + timestamp + "_";
        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = File.createTempFile(filename, // prefix
                ".jpg",
                directory);

        // save the filename
        mCurrentPhotoFilepath = file.getAbsolutePath();

        return file;
    }

    private void setPictureFromFile() {
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoFilepath);
        mImageResult.setImageBitmap(bitmap);
    }
}
