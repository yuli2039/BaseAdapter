package com.yl.baseadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yl.library.common.ItemViewDelegate;
import com.yl.library.common.ViewHolder;
import com.yl.library.recycler.MultiTypeRvAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是一个仿微信选择图片的例子
 * 总共能选9张，如果少于9张，最后就添加一个加号作为开始选择的入口，等于9张则没有加号；
 * 点击已选的图片可以预览删除，这里模拟了一下
 */
public class ImageSelectActivity extends AppCompatActivity {

    private List<Object> mDatas = new ArrayList<>();
    private MultiTypeRvAdapter<Object> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new MultiTypeRvAdapter<Object>(this, mDatas) {
            @Override
            public Object getItem(int position) {
                if (position >= mDatas.size())
                    return null;
                return mDatas.get(position);
            }

            @Override
            public int getItemCount() {
                if (mDatas.size() < 9)
                    return mDatas.size() + 1;
                return mDatas.size();
            }
        }.addItemViewDelegate(new ItemViewDelegate<Object>() {
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
                // 绑定item数据，显示图片
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
                // 加号item，不需要绑定数据
            }
        });

        mAdapter.setOnItemClickListener(new MultiTypeRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, int viewType) {
                Log.e("xxx", position + "/ type = " + viewType);
                if (viewType == 1) {// 这个viewType其实就是添加ItemViewDelegate的顺序角标
                    mDatas.add("");
                    mAdapter.notifyDataSetChanged();
                } else {
                    mDatas.remove(position);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        recyclerView.setAdapter(mAdapter);
    }

    public void btnRefresh(View v) {
    }

    // 模拟没有数据
    public void btnClear(View v) {
        mDatas.clear();
        mAdapter.notifyDataSetChanged();
    }
}
