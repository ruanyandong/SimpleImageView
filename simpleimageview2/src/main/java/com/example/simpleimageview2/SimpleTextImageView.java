package com.example.simpleimageview2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class SimpleTextImageView extends View {

    private Drawable mDrawable;
    private Bitmap mBitmap;

    /**
     * 图片的宽高
     */
    private int mWidth;
    private int mHeight;

    /**
     *文字属性
     */
    private String mText="SimpleImageView";
    private int mTextColor=0xFF45C01A;//赋予默认值;
    private int mTextSize=(int)TypedValue.
            applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    16,
                    getResources().getDisplayMetrics());//赋予默认值16sp
    /**
     * 画笔
     */
    private Paint mBitmapPaint;
    private Paint mTextPaint;

    public SimpleTextImageView(Context context) {
        this(context,null);
    }

    public SimpleTextImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleTextImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.SimpleTextImageView);

        int total=array.getIndexCount();
        for (int i=0;i<total;i++){
            int attr=array.getIndex(i);
            switch (attr){
                case R.styleable.SimpleTextImageView_image:
                    mDrawable=array.getDrawable(attr);
                    break;
                case R.styleable.SimpleTextImageView_text:
                    mText=array.getString(attr);
                    break;
                case R.styleable.SimpleTextImageView_textColor:
                    mTextColor=array.getColor(attr,0xFF45C01A);
                    break;
                case R.styleable.SimpleTextImageView_textSize:
                    mTextSize=(int)array.getDimension(attr,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            16,
                            getResources().getDisplayMetrics()));
                    break;
                default:
                    break;

            }
        }

        measureDrawable();

        mBitmapPaint=new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setDither(true);

        mTextPaint=new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);


    }

    private void measureDrawable(){
        if(mDrawable==null){
            throw new RuntimeException("mDrawable 不能为空");
        }
        mWidth=mDrawable.getIntrinsicWidth();
        mHeight=mDrawable.getIntrinsicHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int modeWidth=MeasureSpec.getMode(widthMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);

        int modeHeight=MeasureSpec.getMode(heightMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(measureWidth(modeWidth,width),measureHeight(modeHeight,height));

    }

    private int measureWidth(int mode,int width){
        switch (mode){
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mWidth=width;
                break;
        }
        return mWidth;
    }

    private int measureHeight(int mode,int height){
        switch (mode){
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mHeight=height;
                break;
        }
        return mHeight;
    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * Android Bitmap 改变的时候可能会抛出  java.lang.IllegalStateException: only mutable bitmaps may be reconfigured

         bitmap.setHeight(12);

         bitmap.setWidth(12);

         然后就报出 java.lang.IllegalStateException 这个错误，

         only mutable bitmaps may be reconfigured

         mutable : 易变的，不定的

         mutable 作用 ：  控制bitmap的setPixel方法能否使用，也就是外界能否修改bitmap的像素。

         会先判断bitmap.isMutable()是否为true，如果为false，也就是是不是易变的图片，如果是true，则可以更改，否者
         就会抛出  java.lang.IllegalStateException异常。为什么呢？
         isMutable( )是易变的、可以修改的意思，而

         bitmap.setHeight(12);

         Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888) 为 mutable 为true

         BimapFactory.decodeResource() 得到的mutable 为false, 要想其为true

         一般会

         BimtapFactory.decodeResource().copy(configu_argb_8888, true);
         mBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
         */

        if (mDrawable!=null){
            BitmapDrawable bitmapDrawable=(BitmapDrawable)mDrawable;
            mBitmap=bitmapDrawable.getBitmap();
            mBitmap=mBitmap.copy(Bitmap.Config.ARGB_8888, true);
            mBitmap.setWidth(getMeasuredWidth());
            mBitmap.setHeight(getMeasuredHeight());
        }

        canvas.drawBitmap(mBitmap,getLeft(),getTop(),mBitmapPaint);

        canvas.save();

        canvas.rotate(90);
        canvas.drawText(mText,getLeft()+50,getTop()-50,mTextPaint);
    }
}
