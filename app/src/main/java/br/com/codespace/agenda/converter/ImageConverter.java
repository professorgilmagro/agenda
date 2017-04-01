package br.com.codespace.agenda.converter;

import android.content.Context;
import android.database.Cursor;
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
}
