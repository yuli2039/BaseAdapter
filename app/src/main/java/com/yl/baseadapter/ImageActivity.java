package com.yl.baseadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yl.library.common.ItemViewDelegate;
import com.yl.library.common.ViewHolder;
import com.yl.library.recycler.MultiTypeRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Object> mDatas = new ArrayList<>();
    private MultiTypeRecyclerAdapter<Object> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new MultiTypeRecyclerAdapter<Object>(this, mDatas) {

            @Override
            public Object getItem(int position) {
                if (position >= mDatas.size())
                    return null;
                return mDatas.get(position);
            }

            @Override
            public int getItemViewType(int position) {
                return super.getItemViewType(position);
            }

            @Override
            public int getItemCount() {
                if (mDatas.size() < 9)
                    return mDatas.size() + 1;
                return mDatas.size();
            }
        };
        mAdapter.addItemViewDelegate(new ItemViewDelegate<Object>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_image;
            }

            @Override
            public boolean isForThisViewType(Object item, int position) {
                return item != null;
            }

            @Override
            public void bindData(ViewHolder holder, Object o, int position) {

            }
        }).addItemViewDelegate(new ItemViewDelegate() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_image_add;
            }

            @Override
            public boolean isForThisViewType(Object item, int position) {
                return item == null;
            }

            @Override
            public void bindData(ViewHolder holder, Object o, int position) {

            }
        });
        mAdapter.setOnItemClickListener(new MultiTypeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, int viewType) {

                Log.e("xxx", position + "/ type = " + viewType);
                if (viewType == 1) {
                    mDatas.add("");
                    mAdapter.notifyDataSetChanged();
                } else {
                    mDatas.remove(position);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }


    public void btnRefresh(View v) {

    }

    // 模拟没有数据
    public void btnClear(View v) {
        mDatas.clear();
        mAdapter.notifyDataSetChanged();
    }
}
