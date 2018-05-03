package com.example.ai.simpleimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 按照原图的尺寸进行绘制
 */
public class SimpleImageView extends View {

    /**
     *view的高宽
     */
    private int mWidth;
    private int mHeight;

    private Drawable mDrawable;
    private Bitmap mBitmap;

    private Paint mPaint;



    public SimpleImageView(Context context) {
        this(context,null);
    }

    public SimpleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SimpleImageView(Context context,
                           @Nullable AttributeSet attrs,
                           int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        /**
         * 获取属性，并进行初始化
         */

        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.SimpleImageView);


        mDrawable=array.getDrawable(R.styleable.SimpleImageView_img);

        if (mDrawable==null){
            throw new RuntimeException("mDrawable 不能为空");
        }
        BitmapDrawable bitmapDrawable=(BitmapDrawable)mDrawable;

        mBitmap=bitmapDrawable.getBitmap();

        measureDrawable(mDrawable);

        mPaint=new Paint();
        mPaint.setAntiAlias(true);

    }

    /**
     * 初始化宽高，即为图片自己的宽高
     * @param drawable
     */
    private void measureDrawable(Drawable drawable){

        if(drawable==null){
            throw new RuntimeException("drawable 不能为空");
        }

        mWidth=drawable.getIntrinsicWidth();
        mHeight=drawable.getIntrinsicHeight();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 图片本身大小
         */
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (mDrawable!=null){
          canvas.drawBitmap(mBitmap,getLeft(),getTop(),mPaint);
        }
    }
}
