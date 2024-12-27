package com.example.find_service;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SingleCard_ServiceRequest extends RecyclerView.ViewHolder {
    TextView postDetails;
    Button btnDeclineRequest, btnAcceptRequest, btnViewProfile;

    public SingleCard_ServiceRequest(@NonNull View itemView) {
        super(itemView);

        postDetails = (TextView) itemView.findViewById(R.id.PostDetails);

        btnDeclineRequest = (Button) itemView.findViewById(R.id.BtnDeclineRequest);
        btnAcceptRequest = (Button) itemView.findViewById(R.id.BtnAcceptRequest);
        btnViewProfile = (Button) itemView.findViewById(R.id.BtnViewProfileOfReceiver);

    }
}
