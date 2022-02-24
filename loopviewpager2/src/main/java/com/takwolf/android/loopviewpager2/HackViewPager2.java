package com.takwolf.android.loopviewpager2;

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
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class HackViewPager2 extends ViewGroup {
    final ViewPager2 viewPager2;
    final RecyclerView recyclerView;

    private final Rect tmpContainerRect = new Rect();
    private final Rect tmpChildRect = new Rect();

    public HackViewPager2(@NonNull Context context) {
        this(context, null);
    }

    public HackViewPager2(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HackViewPager2(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HackViewPager2(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        viewPager2 = new ViewPager2(context);
        viewPager2.setId(View.generateViewId());
        recyclerView = (RecyclerView) viewPager2.getChildAt(0);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HackViewPager2, defStyleAttr, defStyleRes);
        viewPager2.setOrientation(a.getInt(R.styleable.HackViewPager2_android_orientation, ViewPager2.ORIENTATION_HORIZONTAL));
        a.recycle();

        boolean clipChildren = getClipChildren();
        viewPager2.setClipChildren(clipChildren);
        recyclerView.setClipChildren(clipChildren);

        boolean clipToPadding = getClipToPadding();
        viewPager2.setClipToPadding(clipToPadding);
        recyclerView.setClipToPadding(clipToPadding);
        
        viewPager2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        attachViewToParent(viewPager2, 0, viewPager2.getLayoutParams());
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
    public ViewPager2 getViewPager2() {
        return viewPager2;
    }

    @NonNull
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @ViewPager2.Orientation
    public int getOrientation() {
        return viewPager2.getOrientation();
    }

    public void setOrientation(@ViewPager2.Orientation int orientation) {
        viewPager2.setOrientation(orientation);
    }

    @Override
    public void setClipChildren(boolean clipChildren) {
        super.setClipChildren(clipChildren);
        if (viewPager2 != null) {
            viewPager2.setClipChildren(clipChildren);
            recyclerView.setClipChildren(clipChildren);
        }
    }

    @Override
    public void setClipToPadding(boolean clipToPadding) {
        super.setClipToPadding(clipToPadding);
        if (viewPager2 != null) {
            viewPager2.setClipToPadding(clipToPadding);
            recyclerView.setClipToPadding(clipToPadding);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChild(viewPager2, widthMeasureSpec, heightMeasureSpec);

        int width = viewPager2.getMeasuredWidth();
        int height = viewPager2.getMeasuredHeight();
        int childState = viewPager2.getMeasuredState();

        width += getPaddingLeft() + getPaddingRight();
        height += getPaddingTop() + getPaddingBottom();

        width = Math.max(width, getSuggestedMinimumWidth());
        height = Math.max(height, getSuggestedMinimumHeight());

        setMeasuredDimension(resolveSizeAndState(width, widthMeasureSpec, childState), resolveSizeAndState(height, heightMeasureSpec, childState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = viewPager2.getMeasuredWidth();
        int height = viewPager2.getMeasuredHeight();

        tmpContainerRect.left = getPaddingLeft();
        tmpContainerRect.right = r - l - getPaddingRight();
        tmpContainerRect.top = getPaddingTop();
        tmpContainerRect.bottom = b - t - getPaddingBottom();

        Gravity.apply(Gravity.TOP | Gravity.START, width, height, tmpContainerRect, tmpChildRect);
        viewPager2.layout(tmpChildRect.left, tmpChildRect.top, tmpChildRect.right, tmpChildRect.bottom);
    }
}
