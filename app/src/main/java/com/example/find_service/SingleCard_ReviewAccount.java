package com.example.find_service;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SingleCard_ReviewAccount extends RecyclerView.ViewHolder {
    TextView email, name, profession, contact, gender, address, about;
    Button btnAdminHome, btnDecline, btnApprove;

    public SingleCard_ReviewAccount(@NonNull View itemView) {
        super(itemView);

        email = (TextView) itemView.findViewById(R.id.profileEmailAcc);
        name = (TextView) itemView.findViewById(R.id.profileNameAcc);
        profession = (TextView) itemView.findViewById(R.id.profileProfessionAcc);
        contact = (TextView) itemView.findViewById(R.id.profileContactAcc);
        gender = (TextView) itemView.findViewById(R.id.profileGenderAcc);
        address = (TextView) itemView.findViewById(R.id.profileAddressAcc);
        about = (TextView) itemView.findViewById(R.id.profileAboutAcc);

        btnDecline = (Button) itemView.findViewById(R.id.BtnDeclineAcc);
        btnApprove = (Button) itemView.findViewById(R.id.BtnApproveAcc);
        btnAdminHome = (Button) itemView.findViewById(R.id.BtnAdminHomeAcc);
    }
}
