package com.yl.library.common;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;

public interface ViewHolder {

    <T extends View> T getView(int viewId);

    View getConvertView();


    /**
     * 以下为辅助方法
     */

    ViewHolder setText(int viewId, String text);

    ViewHolder setImageResource(int viewId, int resId);

    ViewHolder setImageBitmap(int viewId, Bitmap bitmap);

    ViewHolder setImageDrawable(int viewId, Drawable drawable);

    ViewHolder setBackgroundColor(int viewId, int color);

    ViewHolder setBackgroundRes(int viewId, int backgroundRes);

    ViewHolder setTextColor(int viewId, int textColor);

    ViewHolder setTextColorRes(int viewId, int textColorRes);

    ViewHolder setAlpha(int viewId, float value);

    ViewHolder setVisible(int viewId, boolean visible);

    ViewHolder linkify(int viewId);

    ViewHolder setTypeface(Typeface typeface, int... viewIds);

    ViewHolder setProgress(int viewId, int progress);

    ViewHolder setProgress(int viewId, int progress, int max);

    ViewHolder setMax(int viewId, int max);

    ViewHolder setRating(int viewId, float rating);

    ViewHolder setRating(int viewId, float rating, int max);

    ViewHolder setTag(int viewId, Object tag);

    ViewHolder setTag(int viewId, int key, Object tag);

    ViewHolder setChecked(int viewId, boolean checked);

    /**
     * 关于事件的
     */

    ViewHolder setOnClickListener(int viewId, View.OnClickListener listener);

    ViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener);

    ViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener);

}
