package br.com.codespace.agenda.converter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by gilma on 01/04/2017.
 */

public class ImageConverter {
    private final Context context;

    public ImageConverter(Context context) {
        this.context = context;
    }

    public String getRealPathFromURI (Uri contentUri) {
        String res = null;
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }

        cursor.close();
        return res;
    }

    public static ImageConverter create(Context context) {
        return new ImageConverter(context);
    }

    public static Bitmap resizeBitmap(final Bitmap temp, final int size) {
        if (size > 0) {
            int width = temp.getWidth();
            int height = temp.getHeight();
            float ratioBitmap = (float) width / (float) height;
            int finalWidth = size;
            int finalHeight = size;
            if (ratioBitmap < 1) {
                finalWidth = (int) ((float) size * ratioBitmap);
            } else {
                finalHeight = (int) ((float) size / ratioBitmap);
            }
            return Bitmap.createScaledBitmap(temp, finalWidth, finalHeight, true);
        } else {
            return temp;
        }
    }
}
