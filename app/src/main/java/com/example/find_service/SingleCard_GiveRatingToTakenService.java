package com.example.find_service;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SingleCard_GiveRatingToTakenService extends RecyclerView.ViewHolder {
    TextView ratingEmail, ratingServiceDetails;
    EditText ratingField;
    Button btnRatingSkip, btnRatingSubmit;

    public SingleCard_GiveRatingToTakenService(@NonNull View itemView) {
        super(itemView);

        ratingEmail = (TextView) itemView.findViewById(R.id.RatingEmail);
        ratingServiceDetails = (TextView) itemView.findViewById(R.id.RatingServiceDetails);
        ratingField = (EditText) itemView.findViewById(R.id.RatingField);

        btnRatingSkip = (Button) itemView.findViewById(R.id.BtnRatingSkip);
        btnRatingSubmit = (Button) itemView.findViewById(R.id.BtnRatingSubmit);

    }
}
