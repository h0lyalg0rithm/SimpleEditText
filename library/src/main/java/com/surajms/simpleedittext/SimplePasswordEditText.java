package com.surajms.simpleedittext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;

/**
 * Created by surajshirvankar on 2/14/16.
 */
public class SimplePasswordEditText extends SimpleEditText{

    private boolean passwordVisible = false;

    private Drawable visibleTextDrawable;
    private Drawable protectedTextDrawable;
    public SimplePasswordEditText(Context context) {
        super(context);
    }

    public SimplePasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimplePasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(AttributeSet attrs, int defStyleAttr) {
        super.init(attrs, defStyleAttr);
        visibleTextDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_black_24dp);
        protectedTextDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_off_black_24dp);
        appendDrawable = visibleTextDrawable;
        setDrawableVisibility(appendDrawable, false);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setDrawableVisibility(appendDrawable, s.length() > 0);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        appendTouchListener = new TouchListener() {
            @Override
            public void onTouch() {
                togglePasswordVisibility();
                setDrawableVisibility(appendDrawable, true);
            }
        };
        setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
    }

    private void togglePasswordVisibility() {
        if(passwordVisible){
            setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
            appendDrawable = visibleTextDrawable;
        }else{
            setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            appendDrawable = protectedTextDrawable;
        }
        passwordVisible = !passwordVisible;
    }

    private void setDrawableVisibility(Drawable drawable, boolean visible) {
        if(drawable != null){
            if(visible) {
                setCompoundDrawablesRelativeWithIntrinsicBounds(prependDrawable, null, appendDrawable, null);
            }else {
                setCompoundDrawablesRelativeWithIntrinsicBounds(prependDrawable, null, null, null);
            }
        }
    }



}
