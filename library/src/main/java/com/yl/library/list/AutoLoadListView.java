package com.yl.library.list;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

import com.yl.library.loadmore.DefaultLoadMoreFooter;
import com.yl.library.loadmore.ILoadMore;

import static com.yl.library.loadmore.ILoadMore.Status.COMPLETE;
import static com.yl.library.loadmore.ILoadMore.Status.LOADING;
import static com.yl.library.loadmore.ILoadMore.Status.NOMORE;

/**
 * 加载更多的listview
 *
 * @author yu
 *         Create on 16/8/28.
 */
public class AutoLoadListView extends ListView implements AbsListView.OnScrollListener {

    private OnLoadMoreListener listener;
    private ILoadMore mLoadMoreView;
    private boolean isNoMore;
    private boolean isLoading;

    public AutoLoadListView(Context context) {
        this(context, null);
    }

    public AutoLoadListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoLoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreFooter(ILoadMore loadmoreView) {
        mLoadMoreView = loadmoreView;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.listener = listener;
        if (mLoadMoreView == null) {
            mLoadMoreView = new DefaultLoadMoreFooter(getContext());
            addFooterView(mLoadMoreView.getView());
        }
        setOnScrollListener(this);
    }

    public void reset() {
        isLoading = false;
        isNoMore = false;

        if (mLoadMoreView != null)
            mLoadMoreView.setState(COMPLETE);
    }

    public void loadMoreComplete() {
        reset();
    }

    public void setNoMore() {
        isLoading = false;
        isNoMore = true;
        if (mLoadMoreView != null)
            mLoadMoreView.setState(NOMORE);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        switch (scrollState) {
            case OnScrollListener.SCROLL_STATE_IDLE:
                if (listener != null
                        && !isLoading
                        && !isNoMore
                        && absListView.getLastVisiblePosition() == (absListView.getCount() - 1)) {

                    isLoading = true;
                    mLoadMoreView.setState(LOADING);
                    listener.onLoadMore();
                }
                break;
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    private int dp2px(float dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density + 0.5f);
    }

}
