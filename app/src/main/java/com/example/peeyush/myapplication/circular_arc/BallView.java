package com.example.peeyush.myapplication.circular_arc;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.peeyush.myapplication.R;

/**
 * Created by peeyush on 15/10/15.
 */
public class BallView  extends View {

    int mComplete;
    int mColor;
    public BallView(Context context) {
        super(context);
        mComplete=0;
        mColor = Color.DKGRAY;
    }

    public BallView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.BallView,
                0, 0);
        mComplete = a.getInteger(R.styleable.BallView_complete, 0);
        mColor = a.getColor(R.styleable.BallView_arc_color, Color.DKGRAY);
    }

    public void setComplete(int complete) {
        this.mComplete = complete;
        invalidate();
        requestLayout();
    }

    public void setColor(int color) {
        this.mColor = color;
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        PaintArc(canvas);
    }

    private void PaintArc(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        if(mComplete>0)
            paint.setColor(mColor);
        else
            paint.setColor(Color.DKGRAY);

        RectF oval3 = new RectF(10, 10, getWidth()-10, getHeight()-10);

        float val = (float)mComplete/100;
        canvas.drawArc(oval3, -90, -(360*val), false, paint);
    }
}
