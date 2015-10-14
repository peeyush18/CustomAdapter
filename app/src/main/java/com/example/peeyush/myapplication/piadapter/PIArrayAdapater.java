package com.example.peeyush.myapplication.piadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.peeyush.myapplication.piadapter.PIImage;
import com.example.peeyush.myapplication.piadapter.PIText;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by peeyush on 14/10/15.
 */
public class PIArrayAdapater extends ArrayAdapter{

    ArrayList mItemDataList;
    int mLayoutResource;
    Class mModelClass;


    public PIArrayAdapater(Context context, int resource, Class modelClass,
                           ArrayList dataList) {
        super(context, resource);
        mLayoutResource = resource;
//        mSourceIdArray = sourceIdArray;
//        mSourceTypeArray = sourceTypeArray;
        mItemDataList = dataList;
        mModelClass =modelClass;
    }

    @Override
    public int getCount() {
        return mItemDataList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        convertView = layoutInflater.inflate(mLayoutResource, parent, false);


        Field[] fields = mModelClass.getDeclaredFields();
       // View[] itemViews = new View[mSourceIdArray.length];
        for(Field field: fields){
            try {
                setupView(convertView, field, mItemDataList.get(position));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return convertView;
    }

    private void setupView(View parent, Field field, Object o) throws IllegalAccessException {
        Annotation[] annotations = field.getDeclaredAnnotations();
        for(Annotation annotation: annotations){
            if(annotation instanceof PIImage){
                ImageView imageView = (ImageView)parent.findViewById(((PIImage) annotation).id());
                ImageDowloader imageDowloader = new ImageDowloader(imageView, ((PIImage) annotation).width(),
                        ((PIImage) annotation).height());
                imageDowloader.execute((String)field.get(o));
            }else if(annotation instanceof PIText){
                TextView textView = (TextView)parent.findViewById(((PIText) annotation).id());
                textView.setText((String)field.get(o));
            }
        }
    }

    private View setView(View parent, int viewId, Class viewClass, String src) {
        View itemView=null;
        if(viewClass.equals(ImageView.class)){

        }else if(viewClass.equals(TextView.class)){
            TextView textView = (TextView)parent.findViewById(viewId);
            textView.setText(src);
            itemView= textView;
        }
        return itemView;
    }

    @Override
    public Object getItem(int position) {
        return mItemDataList.get(position);
    }


    class ImageDowloader extends AsyncTask<String, Integer ,Bitmap>{

        ImageView mImageView;
        int mWidth;
        int mHeight;

        ImageDowloader(ImageView imageView, int width, int height){
            mImageView = imageView;
            mWidth = width;
            mHeight = height;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String imageSource= params[0];
            Bitmap bit=null;
            try {
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                bit = BitmapFactory.decodeStream((InputStream) new URL(imageSource).getContent(),
                        null,
                        null);
//                bit =ImageUtil.decodeSampledBitmapFromResource(options,
//                        (InputStream) new URL(imageSource).getContent(),
//                        mWidth,
//                        mHeight);
                return bit;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap!=null)
                mImageView.setImageBitmap(bitmap);
        }
    }
}
