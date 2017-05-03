package com.yl.library.list;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yl.library.common.ItemViewDelegate;
import com.yl.library.common.ItemViewDelegateManager;

import java.util.List;

public class MultiTypeLvAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected View mEmptyView;

    private ItemViewDelegateManager<T> mItemViewDelegateManager;

    public MultiTypeLvAdapter(Context context, List<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager<>();
        registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                if (mEmptyView != null) {
                    if (getCount() == 0) {
                        mEmptyView.setVisibility(View.VISIBLE);
                    } else {
                        mEmptyView.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    public MultiTypeLvAdapter setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
        return this;
    }

    public MultiTypeLvAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    private boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    @Override
    public int getViewTypeCount() {
        if (useItemViewDelegateManager())
            return mItemViewDelegateManager.getItemViewDelegateCount();
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (useItemViewDelegateManager()) {
            return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(mDatas.get(position), position);
        int layoutId = itemViewDelegate.getItemViewLayoutId();

        ViewHolder4l viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            viewHolder = new ViewHolder4l(mContext, convertView, parent, position);
//            viewHolder.mLayoutId = layoutId;
            onViewHolderCreated(viewHolder, viewHolder.getConvertView());
        } else {
            viewHolder = (ViewHolder4l) convertView.getTag();
            viewHolder.mPosition = position;
        }
        convert(viewHolder, getItem(position), position);

        return viewHolder.getConvertView();
    }

    protected void convert(ViewHolder4l viewHolder, T item, int position) {
        mItemViewDelegateManager.bindData(viewHolder, item, position);
    }

    public void onViewHolderCreated(ViewHolder4l holder, View itemView) {
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
