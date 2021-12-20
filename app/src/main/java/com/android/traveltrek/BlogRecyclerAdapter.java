package com.android.traveltrek;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogPost> {

    private Context context;
    private List<MyBlog> blog_list;

    public BlogRecyclerAdapter(Context context, List<MyBlog> blog_list) {
        this.context = context;
        this.blog_list = blog_list;
    }

    @NonNull
    @Override
    public BlogPost onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BlogPost(LayoutInflater.from(context).inflate(R.layout.blog_post_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BlogPost holder, int position) {

       holder.title.setText(blog_list.get(position).getTitle());
       holder.location.setText(blog_list.get(position).getLocation());
       holder.description.setText(blog_list.get(position).getDesc());
       Picasso.get().load(blog_list.get(position).getImageURL()).into(holder.imageURL);

       holder.whatsapp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               try {
                   BitmapDrawable drawable = (BitmapDrawable)holder.imageURL.getDrawable();
                   Bitmap bitmap = drawable.getBitmap();

                   String bitmapPath = MediaStore.Images.Media.insertImage(context.getContentResolver(),bitmap,"title",null);
                   Uri uri = Uri.parse(bitmapPath);

                   Intent intent = new Intent(Intent.ACTION_SEND);
                   intent.setType("image/*");
                   intent.putExtra(Intent.EXTRA_STREAM,uri);
                   intent.putExtra(Intent.EXTRA_TEXT,"This new post has uploaded on TravelTrek with the Caption: "+blog_list.get(position).getDesc());
                   context.startActivity(Intent.createChooser(intent,"Share"));
               } catch (Exception e) {
                   e.printStackTrace();
               }

           }
       });
    }

    @Override
    public int getItemCount() {
        return blog_list.size();
    }
}
