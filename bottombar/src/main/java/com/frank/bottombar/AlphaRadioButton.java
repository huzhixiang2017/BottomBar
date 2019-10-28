package com.frank.bottombar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.text.StaticLayout;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * Created by frank on 2016/12/12.
 */

public class AlphaRadioButton extends RadioButton {

    private Context mContext;

    private Paint mSelectedPaint;//选中paint
    private Paint mUnSelectedPaint;//取消选中paint
    private Paint mTextPaint;//文字paint
    private Paint mDotPaint;//红点paint
    private Paint mCountPaint;//未读数量paint

    private Bitmap mSelectedBitmap;//选中Bitmap
    private Bitmap mUnSelectedBitmap;//取消选中Bitmap

    private Drawable mSelectedDrawable;//选中Drawable
    private Drawable mUnSelectedDrawable;//取消选中Drawable

    private int mIconWidth;//图标宽度
    private int mIconHeight;//图标高度

    private int mAlpha;//透明度
    private int mColor;//颜色值
    private float mFontHeight;//字体高度
    private float mTextWidth;//文字宽度

    private boolean drawDot;//是否显示红点
    private boolean drawCount;//是否显示未读数量
    private int count;//未读数量

    public AlphaRadioButton(Context context) {
        super(context);
        this.mContext = context;
    }

    public AlphaRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initAttrs(context, attrs);
        initPaint();
        initBitmap();
    }

    //初始化自定义属性
    private void initAttrs(Context context, AttributeSet attrs){
        TypedArray mArray = context.obtainStyledAttributes(attrs, R.styleable.BottomBar);
        mSelectedDrawable = mArray.getDrawable(R.styleable.BottomBar_icon_selected);
        mUnSelectedDrawable = mArray.getDrawable(R.styleable.BottomBar_icon_unselected);
        mColor = mArray.getColor(R.styleable.BottomBar_color_selected, Color.BLUE);
        mIconWidth = mArray.getDimensionPixelOffset(R.styleable.BottomBar_icon_width_default, sp2px(14));
        mIconHeight = mArray.getDimensionPixelOffset(R.styleable.BottomBar_icon_height_default, sp2px(14));
        mArray.recycle();
    }

    //初始化画笔
    private void initPaint(){
        mDotPaint = new Paint();
        mTextPaint = new Paint();
        mCountPaint = new Paint();
        mSelectedPaint = new Paint();
        mUnSelectedPaint = new Paint();
        mDotPaint.setAntiAlias(true);
        mTextPaint.setAntiAlias(true);
        mSelectedPaint.setAntiAlias(true);
        mUnSelectedPaint.setAntiAlias(true);
        mTextPaint.setTextSize(getTextSize());
    }

    //初始化bitmap
    private void initBitmap(){
        if(mSelectedDrawable == null || mUnSelectedDrawable == null)
            throw new IllegalArgumentException("SelectedDrawable or UnSelectedDrawable should not be null");

        setButtonDrawable(null);
        setCompoundDrawablesWithIntrinsicBounds(null, mUnSelectedDrawable, null, null);

        mSelectedDrawable.setBounds(0, 0, mIconWidth, mIconHeight);
        mUnSelectedDrawable.setBounds(0, 0, mIconWidth, mIconHeight);

        mUnSelectedDrawable = getCompoundDrawables()[1];

        Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        mFontHeight = (float) Math.ceil(fontMetrics.descent - fontMetrics.ascent);
        mTextWidth = StaticLayout.getDesiredWidth(getText(), getPaint());

        mSelectedBitmap = getBitmapFromDrawable(mSelectedDrawable);
        mUnSelectedBitmap = getBitmapFromDrawable(mUnSelectedDrawable);
        if(isChecked())
            mAlpha = 255;
    }

    //drawable转换为bitmap
    private Bitmap getBitmapFromDrawable(Drawable mDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(mIconWidth, mIconHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        if(mDrawable instanceof BitmapDrawable) {
            mDrawable.draw(canvas);
            return  bitmap;
        }else {
            throw new IllegalArgumentException("Drawable does not instanceof BitmapDrawable!");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawIcon(canvas);
        drawUnSelectedText(canvas);
        drawSelectedText(canvas);
        if(drawDot)
            drawDot(canvas);
        if(drawCount)
            drawCount(canvas, count);
    }

    //绘制图标：当前页面对应图标透明度变小，也就是颜色变淡；将要切换到的页面对应图标透明度变大，也就是颜色变深
    private void drawIcon(Canvas canvas) {
        mSelectedPaint.setAlpha(mAlpha);
        canvas.drawBitmap(mSelectedBitmap, (getWidth() - mIconWidth) / 2, getPaddingTop(), mSelectedPaint);

        mUnSelectedPaint.setAlpha(255 -mAlpha);
        canvas.drawBitmap(mUnSelectedBitmap, (getWidth() - mIconWidth) / 2, getPaddingTop(), mUnSelectedPaint);
    }

    //根据alpha值判断button处于选中或未选中（包括从选中到取消选择）状态，使用画布绘制Text
    private void drawText(Canvas canvas){
        mTextPaint.setAlpha(255 - mAlpha);
        mTextPaint.setColor(getCurrentTextColor());
        canvas.drawText(getText().toString(), (getWidth() - mTextWidth)/2,
                mIconHeight  + getPaddingTop() + mFontHeight, mTextPaint);

        mTextPaint.setAlpha(mAlpha);
        mTextPaint.setColor(mColor);
        canvas.drawText(getText().toString(), (getWidth() - mTextWidth)/2,
                mIconHeight  + getPaddingTop() + mFontHeight, mTextPaint);

    }

    private void drawUnSelectedText(Canvas canvas) {
        mTextPaint.setColor(getCurrentTextColor());
        mTextPaint.setAlpha(255 - mAlpha);
        canvas.drawText(getText().toString(), getWidth() / 2 - mTextWidth / 2
                , mIconHeight + getPaddingTop() + mFontHeight, mTextPaint);
    }

    private void drawSelectedText(Canvas canvas) {
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(mAlpha);
        canvas.drawText(getText().toString(), getWidth() / 2 - mTextWidth / 2
                , mIconHeight + getPaddingTop() + mFontHeight, mTextPaint);
    }

    //在图标右上角画红点
    public void drawDot(Canvas canvas){
        mDotPaint.setColor(Color.RED);
        canvas.drawCircle((getWidth() + mIconWidth)/2, getPaddingTop() + 10, dp2px(4), mDotPaint);
    }

    //在图标右上角画未读数量
    public void drawCount(Canvas canvas, int count){
        mDotPaint.setColor(Color.RED);
        mCountPaint.setColor(Color.WHITE);

        if(count < 10){//1-9
            mCountPaint.setTextSize(sp2px(12));
            canvas.drawCircle((getWidth() + mIconWidth)/2, getPaddingTop() + dp2px(7), dp2px(9), mDotPaint);
            canvas.drawText(String.valueOf(count), (getWidth() + mIconWidth)/2 - dp2px(3),
                getPaddingTop() + dp2px(11), mCountPaint);
        }else if(count < 100){//10-100
            mCountPaint.setTextSize(sp2px(10));
            canvas.drawCircle((getWidth() + mIconWidth)/2, getPaddingTop() + dp2px(7), dp2px(9), mDotPaint);
            canvas.drawText(String.valueOf(count), (getWidth() + mIconWidth)/2 - dp2px(6),
                getPaddingTop() + dp2px(10), mCountPaint);
        }else {//大于100
            mCountPaint.setTextSize(sp2px(8));
            canvas.drawCircle((getWidth() + mIconWidth)/2, getPaddingTop() + dp2px(7), dp2px(9), mDotPaint);
            canvas.drawText("99+", (getWidth() + mIconWidth)/2 - dp2px(6),
                getPaddingTop() + dp2px(11), mCountPaint);
        }
    }

    /**
     * 更新alpha
     * @param alpha alpha
     */
    public void updateAlpha(float alpha){
        this.mAlpha = (int) alpha;
        if(isMainThread())
            invalidate();//主线程调用，触发onDraw方法
        else
            postInvalidate();//子线程调用，触发onDraw方法
    }

    /**
     * 设置Button是否被选中
     * @param isChecked isChecked
     */
    public void setAlphaButtonChecked(boolean isChecked){
        setChecked(isChecked);
        mAlpha = (isChecked ? 255 : 0);
        if(isMainThread())
            invalidate();
        else
            postInvalidate();
    }

    /**
     * 显示红点
     * @param drawDot 是否显示红点
     */
    public void showDot(boolean drawDot){
        this.drawDot = drawDot;
        if(isMainThread())
            invalidate();
        else
            postInvalidate();
    }

    /**
     * 显示未读数量
     * @param drawCount 是否显示未读
     */
    public void showUnreadCount(boolean drawCount, int count){
        this.drawCount = drawCount;
        this.count = count;
        if(isMainThread())
            invalidate();
        else
            postInvalidate();
    }

    /**
     * 判断是否为主线程
     * @return boolean
     */
    private boolean isMainThread(){
        return Looper.getMainLooper() == Looper.myLooper();
    }

    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    protected int sp2px(float sp) {
        final float scale = this.mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }
}
