package com.takwolf.android.loopviewpager2;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;
import androidx.annotation.StyleRes;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class HackViewPager2 extends ViewGroup {
    final ViewPager2 viewPager2;
    final RecyclerView recyclerView;

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
        recyclerView.setPadding(super.getPaddingLeft(), super.getPaddingTop(), super.getPaddingRight(), super.getPaddingBottom());

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

    @NonNull
    public ViewPager2 getViewPager2() {
        return viewPager2;
    }

    @NonNull
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void onViewAdded(View child) {
        throw new IllegalStateException(getClass().getSimpleName() + " does not support direct child views");
    }

    @Override
    public void onViewRemoved(View child) {
        throw new IllegalStateException(getClass().getSimpleName() + " does not support direct child views");
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @Override
    public CharSequence getAccessibilityClassName() {
        return viewPager2.getAccessibilityClassName();
    }

    @SuppressWarnings("rawtypes")
    public void setAdapter(@Nullable RecyclerView.Adapter adapter) {
        viewPager2.setAdapter(adapter);
    }

    @SuppressWarnings("rawtypes")
    @Nullable
    public RecyclerView.Adapter getAdapter() {
        return viewPager2.getAdapter();
    }

    @ViewPager2.Orientation
    public int getOrientation() {
        return viewPager2.getOrientation();
    }

    public void setOrientation(@ViewPager2.Orientation int orientation) {
        viewPager2.setOrientation(orientation);
    }

    public void setCurrentItem(int item) {
        viewPager2.setCurrentItem(item);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        viewPager2.setCurrentItem(item, smoothScroll);
    }

    public int getCurrentItem() {
        return viewPager2.getCurrentItem();
    }

    @ViewPager2.ScrollState
    public int getScrollState() {
        return viewPager2.getScrollState();
    }

    public boolean beginFakeDrag() {
        return viewPager2.beginFakeDrag();
    }

    public boolean fakeDragBy(@Px float offsetPxFloat) {
        return viewPager2.fakeDragBy(offsetPxFloat);
    }

    public boolean endFakeDrag() {
        return viewPager2.endFakeDrag();
    }

    public boolean isFakeDragging() {
        return viewPager2.isFakeDragging();
    }

    public void setUserInputEnabled(boolean enabled) {
        viewPager2.setUserInputEnabled(enabled);
    }

    public boolean isUserInputEnabled() {
        return viewPager2.isUserInputEnabled();
    }

    public void setOffscreenPageLimit(@ViewPager2.OffscreenPageLimit int limit) {
        viewPager2.setOffscreenPageLimit(limit);
    }

    @ViewPager2.OffscreenPageLimit
    public int getOffscreenPageLimit() {
        return viewPager2.getOffscreenPageLimit();
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return viewPager2.canScrollHorizontally(direction);
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return viewPager2.canScrollVertically(direction);
    }

    public void registerOnPageChangeCallback(@NonNull ViewPager2.OnPageChangeCallback callback) {
        viewPager2.registerOnPageChangeCallback(callback);
    }

    public void unregisterOnPageChangeCallback(@NonNull ViewPager2.OnPageChangeCallback callback) {
        viewPager2.unregisterOnPageChangeCallback(callback);
    }

    public void setPageTransformer(@Nullable ViewPager2.PageTransformer transformer) {
        viewPager2.setPageTransformer(transformer);
    }

    public void requestTransform() {
        viewPager2.requestTransform();
    }

    @Override
    public void setLayoutDirection(int layoutDirection) {
        super.setLayoutDirection(layoutDirection);
        viewPager2.setLayoutDirection(layoutDirection);
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        viewPager2.onInitializeAccessibilityNodeInfo(info);
    }

    @Override
    public boolean performAccessibilityAction(int action, Bundle arguments) {
        return viewPager2.performAccessibilityAction(action, arguments);
    }

    public void addItemDecoration(@NonNull RecyclerView.ItemDecoration decor) {
        viewPager2.addItemDecoration(decor);
    }

    public void addItemDecoration(@NonNull RecyclerView.ItemDecoration decor, int index) {
        viewPager2.addItemDecoration(decor, index);
    }

    @NonNull
    public RecyclerView.ItemDecoration getItemDecorationAt(int index) {
        return viewPager2.getItemDecorationAt(index);
    }

    public int getItemDecorationCount() {
        return viewPager2.getItemDecorationCount();
    }

    public void invalidateItemDecorations() {
        viewPager2.invalidateItemDecorations();
    }

    public void removeItemDecorationAt(int index) {
        viewPager2.removeItemDecorationAt(index);
    }

    public void removeItemDecoration(@NonNull RecyclerView.ItemDecoration decor) {
        viewPager2.removeItemDecoration(decor);
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
    public int getPaddingTop() {
        return recyclerView.getPaddingTop();
    }

    @Override
    public int getPaddingBottom() {
        return recyclerView.getPaddingBottom();
    }

    @Override
    public int getPaddingLeft() {
        return recyclerView.getPaddingLeft();
    }

    @Override
    public int getPaddingStart() {
        return recyclerView.getPaddingStart();
    }

    @Override
    public int getPaddingRight() {
        return recyclerView.getPaddingRight();
    }

    @Override
    public int getPaddingEnd() {
        return recyclerView.getPaddingEnd();
    }

    @Override
    public boolean isPaddingRelative() {
        return recyclerView.isPaddingRelative();
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        recyclerView.setPadding(left, top, right, bottom);
    }

    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        super.setPaddingRelative(start, top, end, bottom);
        recyclerView.setPaddingRelative(start, top, end, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChild(recyclerView, widthMeasureSpec, heightMeasureSpec);
        int childWidth = recyclerView.getMeasuredWidth();
        int childHeight = recyclerView.getMeasuredHeight();
        int childState = recyclerView.getMeasuredState();
        setMeasuredDimension(resolveSizeAndState(childWidth, widthMeasureSpec, childState), resolveSizeAndState(childHeight, heightMeasureSpec, childState));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0;
        int right = r - l;
        int top = 0;
        int bottom = b - t;
        viewPager2.layout(left, top, right, bottom);
        recyclerView.layout(left, top, right, bottom);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.viewPagerId = viewPager2.getId();
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        Parcelable state = container.get(getId());
        if (state instanceof SavedState) {
            SavedState savedState = (SavedState) state;
            int previousVpId = savedState.viewPagerId;
            int currentVpId = viewPager2.getId();
            container.put(currentVpId, container.get(previousVpId));
            container.remove(previousVpId);
        }
        super.dispatchRestoreInstanceState(container);
    }

    private static class SavedState extends BaseSavedState {
        int viewPagerId;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);
            readValues(source, null);
        }

        @RequiresApi(Build.VERSION_CODES.N)
        public SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            readValues(source, loader);
        }

        private void readValues(@NonNull Parcel source, @Nullable ClassLoader loader) {
            viewPagerId = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(viewPagerId);
        }

        public static final Creator<SavedState> CREATOR = new ClassLoaderCreator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source, ClassLoader loader) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    return new SavedState(source, loader);
                } else {
                    return new SavedState(source);
                }
            }

            @Override
            public SavedState createFromParcel(Parcel source) {
                return createFromParcel(source, null);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
