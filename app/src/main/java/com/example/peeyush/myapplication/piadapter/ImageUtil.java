package com.example.peeyush.myapplication.piadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by peeyush on 6/4/15.
 */
public class ImageUtil {

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static byte[] convertFileToByteArray(File file) {
        if (file == null) {
            return new byte[0];
        }


        byte[] byteArray = null;
        try {
            InputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 8];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    public static void setScaledImage(Context context, ImageView imageView, String filePath, int width, int height)
    {
        // Get the PIImage and its bitmap
        Drawable drawing = imageView.getDrawable();
        if (drawing == null) {
            return; // Checking for null & return, as suggested in comments
        }
        ImageScaleTask imageScaleTask = new ImageScaleTask(context, imageView, filePath, width, height);
        imageScaleTask.execute(filePath);


    }

    public static int dpToPx(Context context, int dp)
    {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    public static void compressStoredImage(Context context, String filePath){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        Bitmap bitmap = null;
        if(options.outWidth>options.outHeight){
            bitmap = decodeSampledBitmapFromResource(options, filePath, 1024, 0);
        }else{
            bitmap = decodeSampledBitmapFromResource(options, filePath, 0, 1024);
        }

         //= BitmapFactory.decodeFile(file.getAbsolutePath());
        try {
            FileOutputStream fileOutStream = new FileOutputStream(new File(filePath));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fileOutStream);
            fileOutStream.flush();
            fileOutStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap.recycle();
        bitmap = null;
        System.gc();
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

    public static  Bitmap decodeSampledBitmapFromResource(BitmapFactory.Options options,  String filePath,
                                                          int reqWidth, int reqHeight){
        // Calculate inSampleSize
        int height = 0;
        int width = 0;
        double outWidth = options.outWidth;
        double outHeight = options.outHeight;
        double ascpectRatio = (outWidth / outHeight);
        if (reqHeight > 0) {
            height = reqHeight;
            double scaleDownFactor = height/outHeight;
            width = (int) (options.outWidth * ascpectRatio*scaleDownFactor);
        } else {
            width = reqWidth;
            double scaleDownFactor = width/outWidth;
            height = (int) (options.outHeight * ascpectRatio* scaleDownFactor);
        }


        options.inSampleSize = calculateInSampleSize(options, width, height);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static  Bitmap decodeSampledBitmapFromResource(BitmapFactory.Options options,  InputStream stream,
                                                          int reqWidth, int reqHeight){
        // Calculate inSampleSize
        int height = 0;
        int width = 0;
        double outWidth = options.outWidth;
        double outHeight = options.outHeight;
        double ascpectRatio = (outWidth / outHeight);
        if (reqHeight > 0) {
            height = reqHeight;
            double scaleDownFactor = height/outHeight;
            width = (int) (options.outWidth * ascpectRatio*scaleDownFactor);
        } else {
            width = reqWidth;
            double scaleDownFactor = width/outWidth;
            height = (int) (options.outHeight * ascpectRatio* scaleDownFactor);
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


    public static final Bitmap scaleImage(Context context , String filePath, int bound){
            BitmapFactory.Options opts=new BitmapFactory.Options();
            opts.inDither=false;                     //Disable Dithering mode
            opts.inTempStorage=new byte[32 * 1024];
//            opts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(filePath, opts);

         //Get current dimensions AND the desired bounding box
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int bounding = dpToPx(context, bound);
            Log.i("ImageUtil", "original width = " + Integer.toString(width));
            Log.i("ImageUtil", "original height = " + Integer.toString(height));
            Log.i("ImageUtil", "bounding = " + Integer.toString(bounding));

            // Determine how much to scale: the dimension requiring less scaling is
            // closer to the its side. This way the image always stays inside your
            // bounding box AND either x/y axis touches it.
            float xScale = ((float) bounding) / width;
            float yScale = ((float) bounding) / height;
            float scale = (xScale <= yScale) ? xScale : yScale;
            Log.i("ImageUtil", "xScale = " + Float.toString(xScale));
            Log.i("ImageUtil", "yScale = " + Float.toString(yScale));
            Log.i("ImageUtil", "scale = " + Float.toString(scale));

            // Create a matrix for the scaling and add the scaling data
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);

            // Create a new bitmap and convert it to a format understood by the PIImage
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return bitmap;
    }

//    public static String udateUploadHtmlImages(String htmlString, int width, int height){
//        Document document = Jsoup.parse(htmlString);
//        Elements elements = document.select("img");
//        String styleValue = "width:"+width+"px;height:"+height+"px;";
//        for(Element element: elements){
//            if(element.attr("style")==null || element.attr("style").isEmpty())
//                element.attr("style", styleValue);
//            if(element.parent()==null || !element.parent().tagName().equals("a")){
//                String link = "<a href='"+element.attr("src")+"'></a>";
//                element.wrap(link);
//            }
//
//        }
//        elements = document.select("body");
//        return elements.html();
//    }

    static class ImageScaleTask extends AsyncTask<String, Integer, Bitmap>{

        Context context;
        ImageView imageView;
        String filePath;
        int width;
        int height;

        ImageScaleTask(Context context, ImageView imageView, String filePath, int width, int height){
            this.context = context;
            this.imageView = imageView;
            this.filePath = filePath;
            this.width = dpToPx(context, width);
            this.height = dpToPx(context, height);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            System.gc();
            return decodeSampledBitmapFromResource(filePath, width, height);
            //this brings up bitmap in RAM and then processes it
//            return scaleImage(context, filePath, height);
        }
        @Override
        protected void onPostExecute(Bitmap resultBitmap) {
            super.onPostExecute(resultBitmap);
            BitmapDrawable result = new BitmapDrawable(resultBitmap);
            int width = resultBitmap.getWidth();
            int height = resultBitmap.getHeight();
            // Apply the scaled bitmap
            imageView.setImageDrawable(result);

            // Now change PIImage's dimensions to match the scaled image
            ViewGroup.LayoutParams params =  imageView.getLayoutParams();
            params.width = width;
            params.height = height;
            imageView.setLayoutParams(params);
            resultBitmap.isRecycled();
            resultBitmap=null;
            System.gc();
        }
    }
}
