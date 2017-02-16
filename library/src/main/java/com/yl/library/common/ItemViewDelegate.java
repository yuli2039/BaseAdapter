package com.yl.library.common;


/**
 * 列表的view类型代理
 *
 * @author yl
 */
public interface ItemViewDelegate<T> {

    /**
     * 返回item布局id
     *
     * @return layout id
     */
    int getItemViewLayoutId();

    /**
     * 通过实体或者角标来判断当前列表项是否使用此ItemViewDelegate
     *
     * @param item     实体对象
     * @param position 角标
     * @return true
     */
    boolean isForThisViewType(T item, int position);

    /**
     * 绑定数据对象
     *
     * @param holder   ViewHolder
     * @param t        实体对象
     * @param position 角标
     */
    void bindData(ViewHolder holder, T t, int position);

}
