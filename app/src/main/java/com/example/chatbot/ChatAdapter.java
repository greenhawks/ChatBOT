package com.example.chatbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter{
    ArrayList<Message> messages;
    Context context;

    public ChatAdapter(ArrayList<Message> messages,Context context) {
        this.messages = messages;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType)
        {
            case 0:
            {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sending_message,parent,false);
                return new userMessage(view);
            }
            case 1:
            {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recive_layout,parent,false);
                return new reciveMessage(view);
            }
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message modal = messages.get(position);
        switch (modal.getSender())
        {
            case "user":
            {
                ((userMessage)holder).user_message.setText(modal.getContent());
                break;
            }
            case "bot":
            {
                ((reciveMessage)holder).Recive_Message.setText(modal.getContent());
                break;
            }
        }
    }
    @Override
    public int getItemViewType(int position)
    {
        switch (messages.get(position).getSender())
        {
            case "user":
            {
                return 0;
            }
            case "bot":
            {
                return 1;
            }
            default:
            {
                return -1;
            }
        }

    }
    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class userMessage extends RecyclerView.ViewHolder
    {
        TextView user_message;
        public userMessage(@NonNull View viewitem) {
            super(viewitem);
            user_message = viewitem.findViewById(R.id.send);

        }
    }
    public static class reciveMessage extends RecyclerView.ViewHolder
    {
        TextView Recive_Message;
        public reciveMessage(@NonNull View itemView) {
            super(itemView);
            Recive_Message = itemView.findViewById(R.id.recive);
        }
    }

}
