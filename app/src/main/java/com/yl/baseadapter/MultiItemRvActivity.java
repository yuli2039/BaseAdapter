package com.yl.baseadapter;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yl.baseadapter.adapter.ChatAdapterForRv;
import com.yl.baseadapter.entity.ChatMessage;
import com.yl.library.common.DefaultLoadMoreFooter;
import com.yl.library.recycler.MultiTypeRecyclerAdapter;
import com.yl.library.recycler.wrapper.HeaderAndFooterWrapper;
import com.yl.library.recycler.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

public class MultiItemRvActivity extends AppCompatActivity {
    private LoadMoreWrapper mLoadMoreWrapper;
    private List<ChatMessage> mDatas = new ArrayList<>();

    private int loadMoreTimes = 0;
    private ChatAdapterForRv adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatas.addAll(ChatMessage.MOCK_DATAS);

        adapter = new ChatAdapterForRv(this, mDatas);
        adapter.setEmptyView(findViewById(R.id.flEmpty));

        adapter.setOnItemClickListener(new MultiTypeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(MultiItemRvActivity.this, "Click:" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(MultiItemRvActivity.this, "LongClick:" + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        HeaderAndFooterWrapper hAndFAdapter = initHeader();

        initLoadmore(hAndFAdapter);


        mRecyclerView.setAdapter(mLoadMoreWrapper);
    }

    private void initLoadmore(HeaderAndFooterWrapper hAndFAdapter) {

        mLoadMoreWrapper = new LoadMoreWrapper(hAndFAdapter, new DefaultLoadMoreFooter(this));
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean coming = Math.random() > 0.5;
                        ChatMessage msg = new ChatMessage(coming ? R.drawable.renma : R.drawable.xiaohei, coming ? "人马" : "xiaohei", "where are you " + mDatas.size(),
                                null, coming);
                        mDatas.add(msg);

                        if (loadMoreTimes == 1) {
                            mLoadMoreWrapper.setNoMore();
                        } else {
                            mLoadMoreWrapper.loadMoreComplete();
                        }

                        loadMoreTimes++;
                    }
                }, 1500);
            }
        });
    }

    @NonNull
    private HeaderAndFooterWrapper initHeader() {
        HeaderAndFooterWrapper hAndFAdapter = new HeaderAndFooterWrapper(adapter);

        TextView h1 = new TextView(this);
        h1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
        h1.setBackgroundColor(Color.parseColor("#ff0000"));
        h1.setText("Header 01");
        TextView h2 = new TextView(this);
        h2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
        h2.setBackgroundColor(Color.parseColor("#ffff00"));
        h2.setText("Header 02");

        hAndFAdapter.addHeaderView(h1);
        hAndFAdapter.addHeaderView(h2);
        return hAndFAdapter;
    }

    public void btnRefresh(View v) {
        mLoadMoreWrapper.reset();
        loadMoreTimes = 0;

        mDatas.clear();
        mDatas.addAll(ChatMessage.MOCK_DATAS);
        adapter.notifyDataSetChanged();
    }

    public void btnClear(View v) {
        mDatas.clear();
        adapter.notifyDataSetChanged();
    }
}
