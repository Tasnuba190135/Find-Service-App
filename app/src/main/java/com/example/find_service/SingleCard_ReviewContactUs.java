package com.example.find_service;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SingleCard_ReviewContactUs extends RecyclerView.ViewHolder {
    TextView issueType, issuerEmail, issuerContact, issuerMessage;
    EditText feedback;
    Button btnSkipAndDelete, btnFeedback, btnNext, btnAdminHome;

    public SingleCard_ReviewContactUs(@NonNull View itemView) {
        super(itemView);

        issueType = (TextView) itemView.findViewById(R.id.CUIssueType);
        issuerEmail = (TextView) itemView.findViewById(R.id.CUEmail);
        issuerContact = (TextView) itemView.findViewById(R.id.CUContactNo);
        issuerMessage = (TextView) itemView.findViewById(R.id.CUMessage);
        feedback = (EditText) itemView.findViewById(R.id.CUAdminFeedback);

        btnFeedback = (Button) itemView.findViewById(R.id.BtnCUFeedback);
        btnNext = (Button) itemView.findViewById(R.id.BtnCUNextIssue);
        btnSkipAndDelete = (Button) itemView.findViewById(R.id.BtnCUSkipAndDelete);
        btnAdminHome = (Button) itemView.findViewById(R.id.BtnCUAdminHome);
    }
}
