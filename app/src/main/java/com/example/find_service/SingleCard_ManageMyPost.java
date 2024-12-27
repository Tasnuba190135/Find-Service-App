package com.example.find_service;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SingleCard_ManageMyPost extends RecyclerView.ViewHolder {
    TextView postStatus, category, address, description;
    Button btnDeletePost;

    public SingleCard_ManageMyPost(@NonNull View itemView) {
        super(itemView);

        postStatus = (TextView) itemView.findViewById(R.id.PostStatusUser);
        category = (TextView) itemView.findViewById(R.id.PostCategoryUser);
        address = (TextView) itemView.findViewById(R.id.PostAddressUser);
        description = (TextView) itemView.findViewById(R.id.PostDescriptionUser);

        btnDeletePost = (Button) itemView.findViewById(R.id.BtnDeletePostUser);
    }
}
