package com.takwolf.android.loopviewpager2;

import android.annotation.SuppressLint;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

final class ProxyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull
    private final LoopViewPager2 loopViewPager2;

    @SuppressWarnings("rawtypes")
    @Nullable
    private RecyclerView.Adapter adapter;

    private int lastDataItemCount = 0;

    ProxyAdapter(@NonNull LoopViewPager2 loopViewPager2) {
        this.loopViewPager2 = loopViewPager2;
    }

    @SuppressWarnings("rawtypes")
    @Nullable
    RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    @SuppressWarnings("rawtypes")
    void setAdapter(@Nullable RecyclerView.Adapter adapter) {
        if (this.adapter != null) {
            this.adapter.unregisterAdapterDataObserver(innerAdapterDataObserver);
            this.adapter.onDetachedFromRecyclerView(loopViewPager2.recyclerView);
        }
        this.adapter = adapter;
        boolean hasStableIds;
        StateRestorationPolicy stateRestorationPolicy;
        if (adapter != null) {
            lastDataItemCount = adapter.getItemCount();
            adapter.registerAdapterDataObserver(innerAdapterDataObserver);
            adapter.onAttachedToRecyclerView(loopViewPager2.recyclerView);
            hasStableIds = adapter.hasStableIds();
            stateRestorationPolicy = adapter.getStateRestorationPolicy();
        } else {
            hasStableIds = false;
            stateRestorationPolicy = StateRestorationPolicy.ALLOW;
        }
        if (hasStableIds() != hasStableIds) {
            super.setHasStableIds(hasStableIds);
        }
        if (getStateRestorationPolicy() != stateRestorationPolicy) {
            super.setStateRestorationPolicy(stateRestorationPolicy);
        }
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        throw new UnsupportedOperationException("Calling setHasStableIds is not allowed on the ProxyAdapter.");
    }

    @Override
    public void setStateRestorationPolicy(@NonNull StateRestorationPolicy strategy) {
        throw new UnsupportedOperationException("Calling setStateRestorationPolicy is not allowed on the ProxyAdapter.");
    }

    private int getDataItemCount() {
        return adapter == null ? 0 : adapter.getItemCount();
    }

    boolean isLoppingActually() {
        return loopViewPager2.lopping && loopViewPager2.fakeOffset > 0 && getDataItemCount() > 0;
    }

    int convertToDataPosition(int proxyPosition) {
        if (isLoppingActually()) {
            int dataPosition = (proxyPosition - loopViewPager2.fakeOffset) % getDataItemCount();
            if (dataPosition < 0) {
                dataPosition += getDataItemCount();
            }
            return dataPosition;
        } else {
            return proxyPosition;
        }
    }

    private final RecyclerView.AdapterDataObserver innerAdapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onChanged() {
            notifyDataSetChanged();
            checkAndUpdateCurrentItem();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            notifyDataSetChanged();
            checkAndUpdateCurrentItem();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            notifyDataSetChanged();
            checkAndUpdateCurrentItem();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            notifyDataSetChanged();
        }

        @Override
        public void onStateRestorationPolicyChanged() {
            if (adapter != null) {
                setStateRestorationPolicy(adapter.getStateRestorationPolicy());
            }
        }

        private void checkAndUpdateCurrentItem() {
            if (adapter != null) {
                int newDataItemCount = adapter.getItemCount();
                if (isLoppingActually()) {
                    if ((lastDataItemCount == 0 && newDataItemCount != 0) || loopViewPager2.lastPosition >= newDataItemCount) {
                        loopViewPager2.setCurrentItem(0, false);
                    }
                }
                lastDataItemCount = newDataItemCount;
            } else {
                lastDataItemCount = 0;
            }
        }
    };

    @Override
    public int getItemCount() {
        if (isLoppingActually()) {
            return getDataItemCount() + (loopViewPager2.fakeOffset * 2);
        } else {
            return getDataItemCount();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (adapter != null) {
            return adapter.getItemViewType(convertToDataPosition(position));
        } else {
            throw new IllegalStateException("Inner adapter has not been set.");
        }
    }

    @Override
    public long getItemId(int position) {
        if (adapter != null) {
            return adapter.getItemId(convertToDataPosition(position));
        } else {
            throw new IllegalStateException("Inner adapter has not been set.");
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (adapter != null) {
            return adapter.onCreateViewHolder(parent, viewType);
        } else {
            throw new IllegalStateException("Inner adapter has not been set.");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (adapter != null) {
            //noinspection unchecked
            adapter.bindViewHolder(holder, convertToDataPosition(position));
        } else {
            throw new IllegalStateException("Inner adapter has not been set.");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (adapter != null) {
            //noinspection unchecked
            adapter.bindViewHolder(holder, convertToDataPosition(position));
        } else {
            throw new IllegalStateException("Inner adapter has not been set.");
        }
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        if (adapter != null) {
            //noinspection unchecked
            adapter.onViewRecycled(holder);
        } else {
            throw new IllegalStateException("Inner adapter has not been set.");
        }
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder holder) {
        if (adapter != null) {
            //noinspection unchecked
            return adapter.onFailedToRecycleView(holder);
        } else {
            throw new IllegalStateException("Inner adapter has not been set.");
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        if (adapter != null) {
            //noinspection unchecked
            adapter.onViewAttachedToWindow(holder);
        } else {
            throw new IllegalStateException("Inner adapter has not been set.");
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        if (adapter != null) {
            //noinspection unchecked
            adapter.onViewDetachedFromWindow(holder);
        } else {
            throw new IllegalStateException("Inner adapter has not been set.");
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        if (this.loopViewPager2.recyclerView != recyclerView) {
            throw new IllegalStateException("ProxyAdapter can not be attached to other RecyclerView.");
        }
    }

    @Override
    public int findRelativeAdapterPositionIn(@NonNull RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter, @NonNull RecyclerView.ViewHolder viewHolder, int localPosition) {
        if (adapter == this.adapter) {
            return adapter.findRelativeAdapterPositionIn(adapter, viewHolder, convertToDataPosition(localPosition));
        } else {
            return RecyclerView.NO_POSITION;
        }
    }
}
