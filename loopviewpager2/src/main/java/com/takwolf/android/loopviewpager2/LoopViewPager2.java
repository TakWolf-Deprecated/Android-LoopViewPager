package com.takwolf.android.loopviewpager2;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.StyleRes;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class LoopViewPager2 extends HackViewPager2 {
    boolean lopping;
    int fakeOffset;

    private final ProxyAdapter proxyAdapter = new ProxyAdapter(this);
    private final CompositeOnPageChangeCallback pageChangeEventDispatcher = new CompositeOnPageChangeCallback(3);

    int lastPosition;

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

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            private int position = 0;
            private int positionOffsetPixels = 0;
            private int state = ViewPager2.SCROLL_STATE_IDLE;

            @Override
            public void onPageScrolled(int position, float positionOffset, @Px int positionOffsetPixels) {
                this.position = position;
                this.positionOffsetPixels = positionOffsetPixels;
                if (state == ViewPager2.SCROLL_STATE_DRAGGING || state == ViewPager2.SCROLL_STATE_SETTLING) {
                    fixFakePagePosition();
                }
                int dataPosition = proxyAdapter.convertToDataPosition(position);
                pageChangeEventDispatcher.onPageScrolled(dataPosition, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                int dataPosition = proxyAdapter.convertToDataPosition(position);
                lastPosition = dataPosition;
                pageChangeEventDispatcher.onPageSelected(dataPosition);
            }

            @Override
            public void onPageScrollStateChanged(@ViewPager2.ScrollState int state) {
                this.state = state;
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    fixFakePagePosition();
                }
                pageChangeEventDispatcher.onPageScrollStateChanged(state);
            }

            private void fixFakePagePosition() {
                if (proxyAdapter.isLoppingActually() && positionOffsetPixels == 0) {
                    int targetPosition = proxyAdapter.convertToDataPosition(position) + fakeOffset;
                    if (viewPager2.getCurrentItem() != targetPosition) {
                        viewPager2.setCurrentItem(targetPosition, false);
                    }
                }
            }
        });
    }

    public boolean isLopping() {
        return lopping;
    }

    public void setLopping(boolean lopping) {
        if (this.lopping != lopping) {
            int currentItem = getCurrentItem();
            this.lopping = lopping;
            if (proxyAdapter.getAdapter() != null) {
                super.setAdapter(null);
                super.setAdapter(proxyAdapter);
                setCurrentItem(currentItem, false);
            }
        }
    }

    public int getFakeOffset() {
        return fakeOffset;
    }

    public void setFakeOffset(int fakeOffset) {
        if (this.fakeOffset != fakeOffset) {
            int currentItem = getCurrentItem();
            this.fakeOffset = fakeOffset;
            if (proxyAdapter.getAdapter() != null) {
                super.setAdapter(null);
                super.setAdapter(proxyAdapter);
                setCurrentItem(currentItem, false);
            }
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
        if (adapter != null) {
            super.setAdapter(proxyAdapter);
            setCurrentItem(0, false);
        }
    }

    @Override
    public int getCurrentItem() {
        return proxyAdapter.convertToDataPosition(viewPager2.getCurrentItem());
    }

    @Override
    public void setCurrentItem(int item) {
        if (proxyAdapter.isLoppingActually()) {
            viewPager2.setCurrentItem(item + fakeOffset);
        } else {
            viewPager2.setCurrentItem(item);
        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        if (proxyAdapter.isLoppingActually()) {
            viewPager2.setCurrentItem(item + fakeOffset, smoothScroll);
        } else {
            viewPager2.setCurrentItem(item, smoothScroll);
        }
    }

    @Override
    public void registerOnPageChangeCallback(@NonNull ViewPager2.OnPageChangeCallback callback) {
        pageChangeEventDispatcher.addOnPageChangeCallback(callback);
    }

    @Override
    public void unregisterOnPageChangeCallback(@NonNull ViewPager2.OnPageChangeCallback callback) {
        pageChangeEventDispatcher.removeOnPageChangeCallback(callback);
    }

    public void registerRawOnPageChangeCallback(@NonNull ViewPager2.OnPageChangeCallback callback) {
        viewPager2.registerOnPageChangeCallback(callback);
    }

    public void unregisterRawOnPageChangeCallback(@NonNull ViewPager2.OnPageChangeCallback callback) {
        viewPager2.unregisterOnPageChangeCallback(callback);
    }
}
