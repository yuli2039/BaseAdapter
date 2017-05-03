package com.yl.library.recycler;

import android.content.Context;

import com.yl.library.common.ItemViewDelegate;
import com.yl.library.common.ViewHolder;

import java.util.List;

public abstract class RecyclerAdapter<T> extends MultiTypeRvAdapter<T> {

    public RecyclerAdapter(final Context context, final int layoutId, List<T> datas) {
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
                RecyclerAdapter.this.bindData(holder, t, position);
            }
        });
    }

    protected abstract void bindData(ViewHolder holder, T t, int position);


}
