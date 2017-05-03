package com.yl.baseadapter.adapter;

import android.content.Context;

import com.yl.baseadapter.delegate.MsgComingItemDelegate;
import com.yl.baseadapter.delegate.MsgSendItemDelegate;
import com.yl.baseadapter.entity.ChatMessage;
import com.yl.library.list.MultiTypeLvAdapter;

import java.util.List;

public class ChatAdapterForListView extends MultiTypeLvAdapter<ChatMessage> {
    public ChatAdapterForListView(Context context, List<ChatMessage> datas) {
        super(context, datas);

        addItemViewDelegate(new MsgSendItemDelegate());
        addItemViewDelegate(new MsgComingItemDelegate());
    }

}
