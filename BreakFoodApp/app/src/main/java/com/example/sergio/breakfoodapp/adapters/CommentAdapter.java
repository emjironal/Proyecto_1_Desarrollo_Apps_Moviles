package com.example.sergio.breakfoodapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sergio.breakfoodapp.R;
import com.example.sergio.breakfoodapp.model.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {


    private List<Comment> comments;
    private Context context;
    private View view;

    public CommentAdapter() {
    }

    public CommentAdapter(List<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_layout, null, false);
        return new CommentViewHolder(this.view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {
        Comment currentComment = comments.get(i);
        commentViewHolder.username.setText(currentComment.getOwner());
        commentViewHolder.content.setText(currentComment.getContent());
        //commentViewHolder.date.setText(currentComment.getPublishDate().toString());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{

        TextView username, content, date;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.comment_owner);
            content =  itemView.findViewById(R.id.comment_content);
            date  = itemView.findViewById(R.id.comment_date);
        }
    }

}
