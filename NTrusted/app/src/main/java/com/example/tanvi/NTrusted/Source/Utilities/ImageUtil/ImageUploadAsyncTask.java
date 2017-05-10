package com.example.tanvi.NTrusted.Source.Utilities.ImageUtil;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by tanvi on 5/9/2017.
 */

public class ImageUploadAsyncTask extends AsyncTask<String,String,Integer> {

    private Context context;
    private Uri path;
    public String fileURL;

public ImageUploadAsyncTask(Context context, Uri path){

    this.context = context;
    this.path=path;

}



    @Override
    protected Integer doInBackground(String... strings) {

        Cloudinary cloudinary = new Cloudinary(Utils.cloudinaryUrlFromContext(context));

        try {
            Map uploadResult = cloudinary.uploader().upload(path.getPath(), ObjectUtils.asMap("public_id", ""));
            fileURL = uploadResult.get("url").toString();
            System.out.println("In async task , after upload -------------->"+fileURL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);



    }
}
