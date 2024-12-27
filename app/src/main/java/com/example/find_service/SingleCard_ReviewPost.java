package com.example.find_service;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SingleCard_ReviewPost extends RecyclerView.ViewHolder {
    TextView category, description;
    Button btnReject, btnApprove, btnAdminHome;

    public SingleCard_ReviewPost(@NonNull View itemView) {
        super(itemView);

        category = (TextView) itemView.findViewById(R.id.PostCategoryAcc);
        description = (TextView) itemView.findViewById(R.id.PostDescriptionAcc);

        btnReject = (Button) itemView.findViewById(R.id.BtnPostRejectAcc);
        btnApprove = (Button) itemView.findViewById(R.id.BtnPostApproveAcc);
        btnAdminHome = (Button) itemView.findViewById(R.id.BtnAdminHomeAcc);
    }
}
