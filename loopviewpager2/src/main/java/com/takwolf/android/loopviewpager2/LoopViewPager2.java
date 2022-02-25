package com.takwolf.android.loopviewpager2;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

public class LoopViewPager2 extends HackViewPager2 {
    private boolean lopping;
    private int fakeOffset;

    public LoopViewPager2(@NonNull Context context) {
        this(context, null);
    }

    public LoopViewPager2(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopViewPager2(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LoopViewPager2(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoopViewPager2, defStyleAttr, defStyleRes);
        lopping = a.getBoolean(R.styleable.LoopViewPager2_lvp_lopping, true);
        fakeOffset = a.getInt(R.styleable.LoopViewPager2_lvp_fakeOffset, 1);
        a.recycle();
    }
}
