package com.example.hungry_student_login.mainPage.Board;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hungry_student_login.R;
import com.example.hungry_student_login.data.Post;
import com.example.hungry_student_login.mainPage.fragment.BoardFragment;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postList;

    public PostAdapter(List<Post> postList) {
        this.postList = postList;
    }

    public PostAdapter() {

    }

    /**
     * update list
     * @param update
     */
    public void setPostList(List<Post> update) {
        this.postList = update;
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
        if (postList == null) {
            return 0;
        }
        return postList.size();
    }
    public void addItem(Post post) {
        postList.add(post);
        notifyDataSetChanged();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView contents;
        TextView posted_date;
        TextView posted_username;
        TextView pnum;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            contents = itemView.findViewById(R.id.contents);
            posted_date = itemView.findViewById(R.id.posted_date);
            posted_username = itemView.findViewById(R.id.posted_username);
            pnum = itemView.findViewById(R.id.pnum);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), PostActicity.class);
                    intent.putExtra("pnum", postList.get(getAdapterPosition()).getPnum());
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        public void onBind(Post post) {
            title.setText(post.getPtitle());
            contents.setText(post.getPcontent());
            posted_date.setText(post.getPtime());
            posted_username.setText(post.getNickname());
            pnum.setText(""+post.getPnum());
        }
    }
}
