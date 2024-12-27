package com.example.find_service;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import admin_section.AdminHome;
import input_form_objects.ObjTableManagement;

public class CardAdapter_ReviewPost extends RecyclerView.Adapter<SingleCard_ReviewPost> {
    ArrayList<SingleCardEditor_ReviewPost> data;
    android.content.Context context;

    ObjTableManagement table = new ObjTableManagement();

    public CardAdapter_ReviewPost(ArrayList<SingleCardEditor_ReviewPost> data, android.content.Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public SingleCard_ReviewPost onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_review_service_post, parent, false);
        return new SingleCard_ReviewPost(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleCard_ReviewPost holder, int position) {
        final SingleCardEditor_ReviewPost temp = data.get(position);

        holder.category.setText(data.get(position).getCategory());
        holder.description.setText(data.get(position).getDescription());

        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(), temp.getDocumentID(), Toast.LENGTH_SHORT).show();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection(table.tbl_post_a_service)
                        .document(temp.getDocumentID())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(view.getContext(), "Rejected ", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(view.getContext(), AdminReviewPost.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Failed to do action: Reject", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(view.getContext(), AdminReviewPost.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });
            }
        });

        holder.btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), temp.getDocumentID(), Toast.LENGTH_SHORT).show();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection(table.tbl_post_a_service)
                        .document(temp.getDocumentID())
                        .update("approve", 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(view.getContext(), "Approve", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(view.getContext(), AdminReviewPost.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Failed to do action: Approve", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(view.getContext(), AdminReviewPost.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });
            }
        });

        holder.btnAdminHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AdminHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
