package com.yl.library.list;

import android.content.Context;

import com.yl.library.common.ItemViewDelegate;
import com.yl.library.common.ViewHolder;

import java.util.List;

public abstract class ListAdapter<T> extends MultiTypeListAdapter<T> {

    public ListAdapter(Context context, final int layoutId, List<T> datas) {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForThisViewType(T item, int position) {
                return true;
            }

            @Override
            public void bindData(ViewHolder holder, T t, int position) {
                ListAdapter.this.bindData(holder, t, position);
            }
        });
    }

    protected abstract void bindData(ViewHolder viewHolder, T item, int position);

}
