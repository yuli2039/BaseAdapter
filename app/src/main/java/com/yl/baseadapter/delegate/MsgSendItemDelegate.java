package com.yl.baseadapter.delegate;


import com.yl.baseadapter.R;
import com.yl.baseadapter.entity.ChatMessage;
import com.yl.library.common.ItemViewDelegate;
import com.yl.library.common.ViewHolder;

public class MsgSendItemDelegate implements ItemViewDelegate<ChatMessage> {

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
        holder.setText(R.id.chat_to_content, chatMessage.getContent())
                .setText(R.id.chat_to_name, chatMessage.getName())
                .setImageResource(R.id.chat_to_icon, chatMessage.getIcon());
    }
}