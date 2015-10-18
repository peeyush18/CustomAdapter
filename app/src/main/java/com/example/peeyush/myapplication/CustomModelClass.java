package com.example.peeyush.myapplication;

import com.example.peeyush.myapplication.piadapter.PIBall;
import com.example.peeyush.myapplication.piadapter.PIDatePickerDialog;
import com.example.peeyush.myapplication.piadapter.PIImage;
import com.example.peeyush.myapplication.piadapter.PIText;
import com.example.peeyush.myapplication.piadapter.PiBallColor;

/**
 * Created by peeyush on 14/10/15.
 */
public class CustomModelClass {

//    @PIImage(id=R.id.iv_test, width = 50, height = 50, isSourceFile = false)
//    public String mImagePath;
    @PIText(id=R.id.tv_test)
    public String mName;
    @PIBall(id=R.id.bv_test)
    public int mComplete;
    @PiBallColor()
    public int mArcColor;
    @PIDatePickerDialog(id = R.id.tv_test_date, isDeafultDateSet = true, allowFutureDate = true)
    public String mDateStr="2015-10-18";

}
