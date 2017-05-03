package com.yl.library.loadmore;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yl.library.R;


/**
 * 默认加载更多的脚布局
 *
 * @author yu
 */
public class DefaultLoadMoreFooter extends LinearLayout implements ILoadMore {

    private TextView mText;
    private ProgressBar mProgressBar;
    private Status mState;

    public DefaultLoadMoreFooter(Context context) {
        super(context);
        initView(context);
    }

    public void initView(Context context) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(60)));

        mProgressBar = new ProgressBar(context);
        mProgressBar.setIndeterminateDrawable(Resources.getSystem().getDrawable(R.drawable.ptr_loading_progress));
        LayoutParams progressBarParams = new LayoutParams(dp2px(25), dp2px(25));
        progressBarParams.rightMargin = dp2px(10);
        mProgressBar.setLayoutParams(progressBarParams);
        addView(mProgressBar);

        mText = new TextView(getContext());
        LayoutParams textViewParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mText.setLayoutParams(textViewParams);
        mText.setTextSize(14);
        addView(mText);

        setState(Status.COMPLETE);
    }

    @Override
    public View getView() {
        return this;
    }

    public Status getState() {
        return mState;
    }

    @Override
    public void setState(Status state) {
        switch (state) {
            case LOADING:
                this.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                mText.setText(R.string.lmf_loading);
                mState = Status.LOADING;
                break;
            case COMPLETE:
                this.setVisibility(View.GONE);
                mState = Status.COMPLETE;
                break;
            case NOMORE:
                this.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                mText.setText(R.string.lmf_no_more);
                mState = Status.NOMORE;
                break;
        }
    }

    public int dp2px(float dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density + 0.5f);
    }
}
