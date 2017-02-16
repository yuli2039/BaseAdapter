package com.yl.baseadapter;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yl.library.common.DefaultLoadMoreFooter;
import com.yl.library.common.ViewHolder;
import com.yl.library.recycler.RecyclerAdapter;
import com.yl.library.recycler.ViewHolder4r;
import com.yl.library.recycler.wrapper.HeaderAndFooterWrapper;
import com.yl.library.recycler.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private int loadmoreTimes = 0;

    private RecyclerView mRecyclerView;
    private List<String> mDatas = new ArrayList<>();
    private RecyclerAdapter<String> mAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        initDatas();

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
//        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mAdapter = new RecyclerAdapter<String>(this, R.layout.item_list, mDatas) {
            @Override
            protected void bindData(ViewHolder holder, String s, int position) {
                ViewHolder4r h = (ViewHolder4r) holder;
                h.setText(R.id.id_item_list_title, s + " : " + h.getAdapterPosition() + " , " + h.getLayoutPosition());
            }
        };
        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(RecyclerViewActivity.this, "pos = " + position, Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter.setEmptyView(findViewById(R.id.flEmpty));

        initHeaderAndFooter();
        initLoadMore();

        mRecyclerView.setAdapter(mLoadMoreWrapper);
    }

    private void initLoadMore() {

        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper, new DefaultLoadMoreFooter(this));
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            mDatas.add("Add:" + i);
                        }
                        if (loadmoreTimes < 1) {
                            mLoadMoreWrapper.loadMoreComplete();
                        } else {
                            mLoadMoreWrapper.setNoMore();
                        }
                        loadmoreTimes++;
                    }
                }, 1500);
            }
        });
    }

    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);

        TextView h1 = new TextView(this);
        h1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
        h1.setBackgroundColor(Color.parseColor("#ff0000"));
        h1.setText("Header 01");
        TextView h2 = new TextView(this);
        h2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
        h2.setBackgroundColor(Color.parseColor("#ffff00"));
        h2.setText("Header 02");

        mHeaderAndFooterWrapper.addHeaderView(h1);
        mHeaderAndFooterWrapper.addHeaderView(h2);
    }

    private void initDatas() {
        for (int i = 'A'; i <= 'z'; i++) {
            mDatas.add((char) i + "");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycler_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_linear:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.action_grid:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                break;
            case R.id.action_staggered:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                break;
        }
        mRecyclerView.setAdapter(mLoadMoreWrapper);

        return super.onOptionsItemSelected(item);
    }

    // 模拟下拉刷新
    public void btnRefresh(View v) {
        mLoadMoreWrapper.reset();
        loadmoreTimes = 0;

        mDatas.clear();
        initDatas();
        mAdapter.notifyDataSetChanged();
    }

    // 模拟没有数据
    public void btnClear(View v) {
        mDatas.clear();
        mAdapter.notifyDataSetChanged();
    }
}
