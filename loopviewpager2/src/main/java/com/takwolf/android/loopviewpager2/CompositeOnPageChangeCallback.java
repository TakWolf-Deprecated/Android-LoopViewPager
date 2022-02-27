package com.takwolf.android.loopviewpager2;

import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

final class CompositeOnPageChangeCallback extends ViewPager2.OnPageChangeCallback {
    @NonNull
    private final List<ViewPager2.OnPageChangeCallback> callbacks;

    CompositeOnPageChangeCallback(int initialCapacity) {
        callbacks = new ArrayList<>(initialCapacity);
    }

    void addOnPageChangeCallback(ViewPager2.OnPageChangeCallback callback) {
        callbacks.add(callback);
    }

    void removeOnPageChangeCallback(ViewPager2.OnPageChangeCallback callback) {
        callbacks.remove(callback);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, @Px int positionOffsetPixels) {
        try {
            for (ViewPager2.OnPageChangeCallback callback : callbacks) {
                callback.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        } catch (ConcurrentModificationException ex) {
            throwCallbackListModifiedWhileInUse(ex);
        }
    }

    @Override
    public void onPageSelected(int position) {
        try {
            for (ViewPager2.OnPageChangeCallback callback : callbacks) {
                callback.onPageSelected(position);
            }
        } catch (ConcurrentModificationException ex) {
            throwCallbackListModifiedWhileInUse(ex);
        }
    }

    @Override
    public void onPageScrollStateChanged(@ViewPager2.ScrollState int state) {
        try {
            for (ViewPager2.OnPageChangeCallback callback : callbacks) {
                callback.onPageScrollStateChanged(state);
            }
        } catch (ConcurrentModificationException ex) {
            throwCallbackListModifiedWhileInUse(ex);
        }
    }

    private void throwCallbackListModifiedWhileInUse(ConcurrentModificationException parent) {
        throw new IllegalStateException("Adding and removing callbacks during dispatch to callbacks is not supported", parent);
    }
}
