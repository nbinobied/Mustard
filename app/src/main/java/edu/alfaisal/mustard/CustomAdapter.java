package edu.alfaisal.mustard;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Post> {

    List<Post> postList;

    Activity context;

    public CustomAdapter(Activity context, List<Post> posts) {
        super(context, R.layout.post_layout, posts);
        this.context = context;
        postList = posts;
    }


    public View getView(int position,View view,ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(R.layout.post_layout, null,true);
        Post post = postList.get(position);

        ImageView image = (ImageView) row.findViewById(R.id.image);
        TextView comment = (TextView) row.findViewById(R.id.enterComment);
        TextView user = (TextView) row.findViewById(R.id.user);

        Picasso.with(context).load(post.getPost_image()).into(image);
        user.setText(post.getUser().getUsername());
        comment.setText(post.getPost_caption());

        return row;
    }



}
