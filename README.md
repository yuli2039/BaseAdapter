# BaseAdapter

## 大部分代码来自鸿阳大神的适配器，原作见https://github.com/hongyangAndroid/baseAdapter

* 1、修改了loadmore加载中的重复加载的bug;
* 2、修复了getItemViewType可能出现的bug;
* 3、内置默认的加载更多的布局，同时支持ListView（提供了一个AutoLoadListView）；
* 4、修改了emptyView的实现方式；
* 5、添加了一个通用的非入侵式的下拉刷新layout，没有修改原生的RecyclerView，更加灵活；

原作采用类似装饰者模式，提供HeaderAndFooterWrapper，LoadMoreWrapper等装饰类，需要哪些功能可以灵活的组合使用，
而不是像很多其他库一样大而全的揉在一起，修改起来十分复杂；

## 使用方法

```
	compile 'com.lyu:BaseAdapter:1.0.0'
```

1)ListView和RecyclerView的基本用法一致，只是使用的具体类不一样。
	ListView：ListAdapter和MultiTypeLvAdapter
	RecyclerView：RecyclerAdapter和MultiTypeRvAdapter
	这里用ListView举例：

```java
	// 单一条目类型
    ListAdapter adapter = new ListAdapter<ChatMessage>(this, R.layout.item_list, mDatas) {
        @Override
        protected void bindData(ViewHolder viewHolder, ChatMessage item, int position) {
        	// 绑定数据
        }
    };

    // 多条目类型
    new MultiTypeLvAdapter<ChatMessage>(this, mDatas)
        // 第一种条目类型
        .addItemViewDelegate(new ItemViewDelegate<ChatMessage>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.main_chat_from_msg;
            }

            @Override
            public boolean isForThisViewType(ChatMessage item, int position) {
                return item.isComMeg();
            }

            @Override
            public void bindData(ViewHolder holder, ChatMessage chatMessage, int position) {
                // 绑定数据
            }
        })
        // 第二种条目类型
        .addItemViewDelegate(new ItemViewDelegate<ChatMessage>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.main_chat_send_msg;
            }

            @Override
            public boolean isForThisViewType(ChatMessage item, int position) {
                return !item.isComMeg();
            }

            @Override
            public void bindData(ViewHolder holder, ChatMessage chatMessage, int position) {
                // 绑定数据
            }
        });

```

2)加载更多

```java
	// ListView直接使用AutoLoadListView设置监听则可
	mListView.setOnLoadMoreListener(new AutoLoadListView.OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
           //  完成后需要调用loadMoreComplete或者setNoMore
           //     mListView.loadMoreComplete();
           //     mListView.setNoMore();
        }
    });

    // RecyclerView, 传入原始adapter，可以使用默认加载更多也可以自定义
    mLoadMoreWrapper = new LoadMoreWrapper(adapter, new DefaultLoadMoreFooter(this));
    mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
           //  完成后需要调用loadMoreComplete或者setNoMore
           //     mLoadMoreWrapper.loadMoreComplete();
           //     mLoadMoreWrapper.setNoMore();
        }
    });

```

3)添加Header和Footer

```java
	// ListView自带，无修改

    // RecyclerView, 传入原始adapter，可以添加多个
    mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
    mHeaderAndFooterWrapper.addHeaderView(headerView);

```

4)设置EmptyView

```java
	// ListView和RecyclerView使用方法一致，没有数据时自动显示；
	adapter.setEmptyView(emptyView);

```


5)内置下拉刷新的使用

```
    <com.yl.library.refresh.RefreshLayout
        android:id="@+id/refreshLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <!-- 支持recycleView、listview、scrollview和普通view -->

        <com.yl.library.list.AutoLoadListView
            android:id="@+id/autoListView"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />

    </com.yl.library.refresh.RefreshLayout>

```
```java

	mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
	    @Override
	    public void onRefresh() {
	        // 加载完成后必须调用refreshComplete隐藏刷新头
            mRefreshLayout.refreshComplete();
	    }
	});

```