package com.example.peeyush.myapplication.piadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * Created by peeyush on 6/4/15.
 */
public class ImageUtil {


    public static int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(BitmapFactory.Options options, String filePath,
                                                         int reqWidth, int reqHeight) {
        // Calculate inSampleSize
        int height = 0;
        int width = 0;
        double outWidth = options.outWidth;
        double outHeight = options.outHeight;
        double ascpectRatio = (outWidth / outHeight);
        if (reqHeight > 0) {
            height = reqHeight;
            double scaleDownFactor = height / outHeight;
            width = (int) (options.outWidth * ascpectRatio * scaleDownFactor);
        } else {
            width = reqWidth;
            double scaleDownFactor = width / outWidth;
            height = (int) (options.outHeight * ascpectRatio * scaleDownFactor);
        }


        options.inSampleSize = calculateInSampleSize(options, width, height);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap decodeSampledBitmapFromResource(BitmapFactory.Options options, InputStream stream,
                                                         int reqWidth, int reqHeight) {
        // Calculate inSampleSize
        int height = 0;
        int width = 0;
        double outWidth = options.outWidth;
        double outHeight = options.outHeight;
        double ascpectRatio = (outWidth / outHeight);
        if (reqHeight > 0) {
            height = reqHeight;
            double scaleDownFactor = height / outHeight;
            width = (int) (options.outWidth * ascpectRatio * scaleDownFactor);
        } else {
            width = reqWidth;
            double scaleDownFactor = width / outWidth;
            height = (int) (options.outHeight * ascpectRatio * scaleDownFactor);
        }


        options.inSampleSize = calculateInSampleSize(options, width, height);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(stream, null, options);
    }


    public static Bitmap decodeSampledBitmapFromResource(String filePath,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        return decodeSampledBitmapFromResource(options, filePath, reqWidth, reqHeight);
    }
}
