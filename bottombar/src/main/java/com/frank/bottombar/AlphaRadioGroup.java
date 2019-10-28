package com.frank.bottombar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

/**
 * Created by frank on 2016/12/12.
 */

public class AlphaRadioGroup extends RadioGroup implements ViewPager.OnPageChangeListener{
    private ViewPager mViewPager;

    public AlphaRadioGroup(Context context) {
        super(context);
    }

    public AlphaRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewPager(ViewPager viewPager){
        this.mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);//设置页面切换监听
        registerListener();//button点击监听
    }

    private void registerListener(){
        for(int i=0; i<getChildCount(); i++){
            final int position = i;
            getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setClickViewChecked(position);//切换RadioButton
                    if(mViewPager != null)
                       mViewPager.setCurrentItem(position, false);//切换fragment
                }
            });
        }
    }

    private void setClickViewChecked(int position){
        for(int i=0; i<getChildCount(); i++){
            ((AlphaRadioButton)getChildAt(i)).setAlphaButtonChecked(false);
        }
        ((AlphaRadioButton)getChildAt(position)).setAlphaButtonChecked(true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(positionOffset > 0)//offset发生变化，根据offset改变child的alpha
            updateOffsetAlpha(position, positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        setSelectedViewChecked(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setSelectedViewChecked(int position){
        for(int i=0; i<getChildCount(); i++){
            ((AlphaRadioButton)getChildAt(i)).setChecked(false);
        }
        ((AlphaRadioButton)getChildAt(position)).setChecked(true);
    }

    private void updateOffsetAlpha(int position, float positionOffset){
        ((AlphaRadioButton)getChildAt(position)).updateAlpha(255 * (1 - positionOffset));
        ((AlphaRadioButton)getChildAt(position + 1)).updateAlpha(255 * positionOffset);
    }

    /**
     * 显示红点
     * @param position position
     */
    public void showDot(int position){
        checkPosition(position);
        ((AlphaRadioButton)getChildAt(position)).showDot(true);
    }

    /**
     *
     * @param position position
     */
    public void hideDot(int position){
        checkPosition(position);
        ((AlphaRadioButton)getChildAt(position)).showDot(false);
    }

    /**
     * 显示未读数量
     * @param position position
     * @param count count
     */
    public void showUnreadCount(int position, int count){
        checkPosition(position);
        ((AlphaRadioButton)getChildAt(position)).showUnreadCount(true, count);
    }

    /**
     * 隐藏未读数量
     * @param position position
     */
    public void hideUnreadCount(int position){
        checkPosition(position);
        ((AlphaRadioButton)getChildAt(position)).showUnreadCount(false, 0);
    }

    /**
     * 检查绘制的position合法性
     * @param position position
     */
    protected void checkPosition(int position){
        if(position < 0 || position >= getChildCount())
            throw new IndexOutOfBoundsException("The position to show dot should not be smaller than 0 " +
                    "or greater than childCount");
    }
}
