package com.example.moonmayor.igclone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by moonmayor on 10/5/17.
 */

public class DownloadImageTask extends AsyncTask<Void, Void, Bitmap> {
    private Context mContext;
    private String mUrl;
    private ImageView mView;

    public DownloadImageTask(Context context, String url, ImageView view) {
        mContext = context;
        mUrl = url;
        mView = view;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Drawable placeholder = mContext.getResources().getDrawable(R.drawable.placeholder);
        mView.setImageDrawable(placeholder);
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        try {
            InputStream stream = new URL(mUrl).openConnection().getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            return bitmap;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            super.onPostExecute(bitmap);
            mView.setImageBitmap(bitmap);
        }
    }
}
