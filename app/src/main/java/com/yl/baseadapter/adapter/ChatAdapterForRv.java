package com.yl.baseadapter.adapter;

import android.content.Context;

import com.yl.baseadapter.delegate.MsgComingItemDelegate;
import com.yl.baseadapter.delegate.MsgSendItemDelegate;
import com.yl.baseadapter.entity.ChatMessage;
import com.yl.library.recycler.MultiTypeRvAdapter;

import java.util.List;


public class ChatAdapterForRv extends MultiTypeRvAdapter<ChatMessage> {
    public ChatAdapterForRv(Context context, List<ChatMessage> datas) {
        super(context, datas);

        addItemViewDelegate(new MsgSendItemDelegate());
        addItemViewDelegate(new MsgComingItemDelegate());
    }
}
