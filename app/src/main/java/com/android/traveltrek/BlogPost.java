package com.android.traveltrek;

import android.text.style.IconMarginSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BlogPost extends RecyclerView.ViewHolder {
    public TextView title, description, location;
    public ImageView imageURL;
    public Button whatsapp;

    public BlogPost(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.username);
        location = itemView.findViewById(R.id.location);
        description = itemView.findViewById(R.id.desc);
        imageURL = itemView.findViewById(R.id.img_post);
        whatsapp = itemView.findViewById(R.id.whatsapp);
    }
}
