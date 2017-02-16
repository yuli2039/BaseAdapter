package com.yl.library.common;

import android.view.View;

/**
 * loadmore
 * Created by yu on 2017/2/15.
 */
public interface ILoadMore {
    enum Status {
        LOADING, COMPLETE, NOMORE
    }

    void setState(Status state);

    View getView();
}
