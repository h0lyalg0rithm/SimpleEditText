package com.surajms.simpleedittext;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by surajshirvankar on 2/14/16.
 */
public class SimpleEmailEditText extends SimpleEditText{
    public SimpleEmailEditText(Context context) {
        super(context);
    }

    public SimpleEmailEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleEmailEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(AttributeSet attrs, int defStyleAttr) {
        super.init(attrs, defStyleAttr);
        if(prependDrawable == null)
            setPrependDrawable(R.drawable.ic_local_post_office_black_24dp);
        drawDrawables();
    }
}
