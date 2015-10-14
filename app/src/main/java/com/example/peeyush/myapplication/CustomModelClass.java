package com.example.peeyush.myapplication;

import com.example.peeyush.myapplication.piadapter.PIImage;
import com.example.peeyush.myapplication.piadapter.PIText;

/**
 * Created by peeyush on 14/10/15.
 */
public class CustomModelClass {

    @PIImage(id=R.id.iv_test, width = 50, height = 50)
    public String mImagePath;
    @PIText(id=R.id.tv_test)
    public String mName;
}
