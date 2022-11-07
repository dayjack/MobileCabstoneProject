package com.example.hungry_student_login.mainPage.Board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hungry_student_login.R;
import com.example.hungry_student_login.data.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postList;

    public PostAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.postrecycler_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.onBind(postList.get(position));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView contents;
        TextView posted_date;
        TextView posted_username;
        TextView comment_count;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            contents = itemView.findViewById(R.id.contents);
            posted_date = itemView.findViewById(R.id.posted_date);
            posted_username = itemView.findViewById(R.id.posted_username);
            comment_count = itemView.findViewById(R.id.comment_count);
        }

        public void onBind(Post post) {
            title.setText(post.getTitle());
            contents.setText(post.getContents());
            posted_date.setText(post.getPostedDate());
            posted_username.setText("익명");
            comment_count.setText(post.getCommentCount().toString());
        }
    }
}
