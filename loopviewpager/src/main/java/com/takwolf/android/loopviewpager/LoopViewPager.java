package com.takwolf.android.loopviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.viewpager2.widget.ViewPager2;

public class LoopViewPager extends ViewGroup {
    @NonNull
    private final ViewPager2 viewPager;

    private final Rect tmpContainerRect = new Rect();
    private final Rect tmpChildRect = new Rect();

    public LoopViewPager(@NonNull Context context) {
        this(context, null);
    }

    public LoopViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopViewPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LoopViewPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoopViewPager, defStyleAttr, defStyleRes);
        // TODO
        a.recycle();

        viewPager = new ViewPager2(context);
        viewPager.setId(View.generateViewId());
        viewPager.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        attachViewToParent(viewPager, 0, viewPager.getLayoutParams());
    }

    @Override
    public void onViewAdded(View child) {
        throw new IllegalStateException(getClass().getSimpleName() + " does not support direct child views");
    }

    @Override
    public void onViewRemoved(View child) {
        throw new IllegalStateException(getClass().getSimpleName() + " does not support direct child views");
    }

    @NonNull
    public ViewPager2 getViewPager() {
        return viewPager;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChild(viewPager, widthMeasureSpec, heightMeasureSpec);

        int width = viewPager.getMeasuredWidth();
        int height = viewPager.getMeasuredHeight();
        int childState = viewPager.getMeasuredState();

        width += getPaddingLeft() + getPaddingRight();
        height += getPaddingTop() + getPaddingBottom();

        width = Math.max(width, getSuggestedMinimumWidth());
        height = Math.max(height, getSuggestedMinimumHeight());

        setMeasuredDimension(resolveSizeAndState(width, widthMeasureSpec, childState), resolveSizeAndState(height, heightMeasureSpec, childState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = viewPager.getMeasuredWidth();
        int height = viewPager.getMeasuredHeight();

        tmpContainerRect.left = getPaddingLeft();
        tmpContainerRect.right = r - l - getPaddingRight();
        tmpContainerRect.top = getPaddingTop();
        tmpContainerRect.bottom = b - t - getPaddingBottom();

        Gravity.apply(Gravity.TOP | Gravity.START, width, height, tmpContainerRect, tmpChildRect);
        viewPager.layout(tmpChildRect.left, tmpChildRect.top, tmpChildRect.right, tmpChildRect.bottom);
    }
}
