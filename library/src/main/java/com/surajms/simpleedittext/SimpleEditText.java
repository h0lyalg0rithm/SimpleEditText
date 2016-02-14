package com.surajms.simpleedittext;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by surajshirvankar on 2/13/16.
 */
public class SimpleEditText extends AppCompatEditText {

    Drawable prependDrawable = null;
    Drawable appendDrawable = null;

    protected TouchListener appendTouchListener;
    protected TouchListener prependTouchListener;

    public SimpleEditText(Context context) {
        super(context);
    }

    public SimpleEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SimpleEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    protected void init(AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.SimpleEditText, defStyleAttr, 0);
            int appendIcon = styledAttributes.getResourceId(R.styleable.SimpleEditText_append_icon, 0);
            int prependIcon = styledAttributes.getResourceId(R.styleable.SimpleEditText_prepend_icon, 0);
            setAppendDrawable(appendIcon);
            setPrependDrawable(prependIcon);
            styledAttributes.recycle();
        }
        setTypeface(Typeface.DEFAULT);

        drawDrawables();
    }

    public void setAppendDrawable(int resource){
        appendDrawable = setDrawable(resource);
    }

    public void setPrependDrawable(int resource){
        prependDrawable = setDrawable(resource);
    }

    public void setPrependTouchListener(TouchListener listener){
        this.prependTouchListener = listener;
    }

    public void setAppendTouchListener(TouchListener listener){
        this.appendTouchListener = listener;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX(), y = (int) event.getY();

        //  TODO - Need to refactor
        if(isRTL()){

            if(appendDrawable != null && x < appendDrawable.getIntrinsicWidth()){
                callListener(appendTouchListener);
                return false;
            }
            if(prependDrawable != null && x > getRight() - prependDrawable.getIntrinsicWidth()){
                callListener(prependTouchListener);
                return false;
            }

        }else{
            if(prependDrawable != null && x < prependDrawable.getIntrinsicWidth()){
                callListener(prependTouchListener);
                return false;
            }
            if(appendDrawable != null && x > getRight() - appendDrawable.getIntrinsicWidth()){
                callListener(appendTouchListener);
                return false;
            }
        }
        return super.onTouchEvent(event);
    }

    private void callListener(TouchListener listener) {
        if(listener != null) listener.onTouch();
    }


    private boolean isRTL(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1){
            return false;
        }
        Configuration config = getResources().getConfiguration();
        return config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }


    private Drawable setDrawable(int icon) {
        Drawable drawable = null;
        if(icon != 0){
            drawable = ContextCompat.getDrawable(getContext(), icon);
        }
        return drawable;
    }

    public void drawDrawables() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(prependDrawable, null, appendDrawable, null);
    }

    public interface TouchListener{
        void onTouch();
    }

    private int getRelativeLeft(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getLeft();
        else
            return myView.getLeft() + getRelativeLeft((View) myView.getParent());
    }

    private int getRelativeTop(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getTop();
        else
            return myView.getTop() + getRelativeTop((View) myView.getParent());
    }
}
