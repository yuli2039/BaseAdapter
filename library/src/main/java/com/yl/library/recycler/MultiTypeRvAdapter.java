package com.yl.library.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yl.library.common.ItemViewDelegate;
import com.yl.library.common.ItemViewDelegateManager;

import java.util.List;

public class MultiTypeRvAdapter<T> extends RecyclerView.Adapter<ViewHolder4r> {
    protected Context mContext;
    protected List<T> mDatas;

    protected View mEmptyView;
    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;

    public MultiTypeRvAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
        registerAdapterDataObserver(new EmptyDataObserver());
    }

    public MultiTypeRvAdapter setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
        return this;
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager())
            return super.getItemViewType(position);
        return mItemViewDelegateManager.getItemViewType(getItem(position), position);
    }

    public int getItemViewTypeByDelegate(ItemViewDelegate itemViewDelegate) {
        return mItemViewDelegateManager.getItemViewType(itemViewDelegate);
    }

    @Override
    public ViewHolder4r onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder4r holder = ViewHolder4r.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder, holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }

    public void onViewHolderCreated(ViewHolder4r holder, View itemView) {

    }

    public void bindData(ViewHolder4r holder, T t) {
        mItemViewDelegateManager.bindData(holder, t, holder.getAdapterPosition());
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    protected void setListener(final ViewGroup parent, final ViewHolder4r viewHolder, final int viewType) {
        if (!isEnabled(viewType))
            return;

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder, position, viewType);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemLongClickListener.onItemLongClick(v, viewHolder, position, viewType);
                }
                return true;
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder4r holder, int position) {
        bindData(holder, getItem(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public MultiTypeRvAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position, int viewType);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, int viewType);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemClickListener) {
        this.mOnItemLongClickListener = onItemClickListener;
    }

    private class EmptyDataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            if (mEmptyView != null) {
                if (getItemCount() == 0) {
                    mEmptyView.setVisibility(View.VISIBLE);
                } else {
                    mEmptyView.setVisibility(View.GONE);
                }
            }
        }
    }
}
