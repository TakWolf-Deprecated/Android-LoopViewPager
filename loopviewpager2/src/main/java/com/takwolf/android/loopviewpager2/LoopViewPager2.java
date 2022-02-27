package com.takwolf.android.loopviewpager2;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.recyclerview.widget.RecyclerView;

public class LoopViewPager2 extends HackViewPager2 {
    boolean lopping;
    int fakeOffset;

    private final ProxyAdapter proxyAdapter = new ProxyAdapter(this);

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

    public boolean isLopping() {
        return lopping;
    }

    public void setLopping(boolean lopping) {
        if (this.lopping != lopping) {
            this.lopping = lopping;
            super.setAdapter(null);
            super.setAdapter(proxyAdapter);
        }
    }

    public int getFakeOffset() {
        return fakeOffset;
    }

    public void setFakeOffset(int fakeOffset) {
        if (this.fakeOffset != fakeOffset) {
            this.fakeOffset = fakeOffset;
            super.setAdapter(null);
            super.setAdapter(proxyAdapter);
        }
    }

    @SuppressWarnings("rawtypes")
    @Nullable
    @Override
    public RecyclerView.Adapter getAdapter() {
        return proxyAdapter.getAdapter();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void setAdapter(@Nullable RecyclerView.Adapter adapter) {
        super.setAdapter(null);
        proxyAdapter.setAdapter(adapter);
        super.setAdapter(proxyAdapter);
    }
}
