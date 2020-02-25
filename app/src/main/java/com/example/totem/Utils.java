package com.example.totem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

public class Utils {

    public static Intent getShareIntent(Bitmap bitmap, Context context) {
        String pathBmp = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap,
                "", null);
        Uri bmpUri = Uri.parse(pathBmp);
        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        intent.setType("image/png");
        return intent;
    }

}
