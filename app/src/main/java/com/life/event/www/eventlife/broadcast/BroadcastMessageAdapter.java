package com.life.event.www.eventlife.broadcast;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.life.event.www.eventlife.R;

import java.util.List;

/**
 * Created by xu_s on 6/1/17.
 */

public class BroadcastMessageAdapter extends RecyclerView.Adapter<BroadcastMessageAdapter.ViewHolder>  {
    private static final int MESSAGE_SENT = 1;
    private static final int MESSAGE_RECEIVED = 2;
    private List<BroadcastMessage> broadcastMessages;
    private String mId;

    public BroadcastMessageAdapter(List<BroadcastMessage> chatList) {
        this.broadcastMessages = chatList;
    }

    @Override public BroadcastMessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_message_sent, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_message_received, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        BroadcastMessage message = broadcastMessages.get(position);
        holder.txtMessage.setText(message.getMessage());
    }

    @Override public int getItemCount() {
        return broadcastMessages.size();
    }

    @Override public int getItemViewType(int position) {
        if (broadcastMessages.get(position).getAuthor().equals(mId)) return MESSAGE_SENT;

        return MESSAGE_RECEIVED;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMessage;

        public ViewHolder(View view) {
            super(view);
            txtMessage = (TextView) view.findViewById(R.id.txt_message);
        }
    }
}
