package com.yl.library.common;

import android.support.v4.util.SparseArrayCompat;

/**
 * 管理列表的所有ItemView类型
 *
 * @author yl
 */
public class ItemViewDelegateManager<T> {
    private SparseArrayCompat<ItemViewDelegate<T>> mDelegates = new SparseArrayCompat<>();

    public int getItemViewDelegateCount() {
        return mDelegates.size();
    }

    public void bindData(ViewHolder holder, T item, int position) {
        ItemViewDelegate<T> delegate;
        for (int i = 0; i < mDelegates.size(); i++) {
            delegate = mDelegates.valueAt(i);

            if (delegate.isForThisViewType(item, position)) {
                delegate.bindData(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }

    public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> delegate) {
        if (delegate != null) {
            int viewType = mDelegates.size();
            mDelegates.put(viewType, delegate);
        }
        return this;
    }

    public int getItemViewType(ItemViewDelegate itemViewDelegate) {
        return mDelegates.indexOfValue(itemViewDelegate);
    }

    public int getItemViewType(T item, int position) {
        int delegatesCount = mDelegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = mDelegates.valueAt(i);
            if (delegate.isForThisViewType(item, position)) {
                return mDelegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    public ItemViewDelegate getItemViewDelegate(int viewType) {
        return mDelegates.get(viewType);
    }

    public ItemViewDelegate getItemViewDelegate(T item, int position) {
        int delegatesCount = mDelegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = mDelegates.valueAt(i);
            if (delegate.isForThisViewType(item, position)) {
                return delegate;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    public int getItemViewLayoutId(int viewType) {
        return getItemViewDelegate(viewType).getItemViewLayoutId();
    }

}
