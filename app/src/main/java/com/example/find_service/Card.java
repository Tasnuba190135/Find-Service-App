package com.example.find_service;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Card extends RecyclerView.ViewHolder {
    TextView name01,address01,email01, details;
    Button btnSubmit01, btnRequest;

    public Card(@NonNull View itemView) {
        super(itemView);

        name01 = (TextView) itemView.findViewById(R.id.card_name);
        address01 = (TextView) itemView.findViewById(R.id.card_address);
        details = (TextView) itemView.findViewById(R.id.card_details);
        btnSubmit01 = (Button) itemView.findViewById(R.id.card_btnSubmit);
        btnRequest = (Button) itemView.findViewById(R.id.card_btnRequest);

    }

}
