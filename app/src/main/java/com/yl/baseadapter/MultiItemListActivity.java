package com.yl.baseadapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yl.baseadapter.adapter.ChatAdapterForListView;
import com.yl.baseadapter.entity.ChatMessage;
import com.yl.library.list.AutoLoadListView;
import com.yl.library.common.refresh.RefreshLayout;

import java.util.ArrayList;
import java.util.List;


public class MultiItemListActivity extends AppCompatActivity {

    private int loadMoreTimes = 0;

    private AutoLoadListView mListView;
    private List<ChatMessage> mDatas = new ArrayList<>();
    private ChatAdapterForListView adapter;
    private RefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        mRefreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        mDatas.addAll(ChatMessage.MOCK_DATAS);

        adapter = new ChatAdapterForListView(this, mDatas);
        adapter.setEmptyView(findViewById(R.id.flEmpty));

        mListView = (AutoLoadListView) findViewById(R.id.autoListView);
        mListView.setOnLoadMoreListener(new AutoLoadListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        mListView.setAdapter(adapter);

    }

    // 模拟没有数据时显示emptyView
    public void btnClear(View v) {
        mDatas.clear();
        adapter.notifyDataSetChanged();
    }

    private void refresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListView.reset();
                loadMoreTimes = 0;

                mDatas.clear();
                mDatas.addAll(ChatMessage.MOCK_DATAS);
                adapter.notifyDataSetChanged();

                mRefreshLayout.refreshComplete();
            }
        }, 2000);
    }

    private void loadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas.add(new ChatMessage(R.drawable.xiaohei, "大表姐", "你们干哈呢", null, false));
                adapter.notifyDataSetChanged();

                if (loadMoreTimes < 1) {
                    mListView.loadMoreComplete();
                } else {
                    mListView.setNoMore();
                }
                loadMoreTimes++;
            }
        }, 1500);
    }

}
