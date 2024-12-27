package com.example.find_service;

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
import io.grpc.Context;

public class CardAdapter_ReviewAccount extends RecyclerView.Adapter<SingleCard_ReviewAccount> {
    ArrayList<SingleCardEditor_ReviewAccount> data;
    android.content.Context context;

    ObjTableManagement table = new ObjTableManagement();

//    public CardAdapter_ReviewAccount(ArrayList<SingleCardEditor_ReviewAccount> data, Context context) {
//        this.data = data;
//        this.context = context;
//    }

    public CardAdapter_ReviewAccount(ArrayList<SingleCardEditor_ReviewAccount> data, android.content.Context applicationContext) {
        this.data = data;
        this.context = applicationContext;
    }

    @NonNull
    @Override
    public SingleCard_ReviewAccount onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_single_card_review_account, parent, false);
        return new SingleCard_ReviewAccount(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleCard_ReviewAccount holder, int position) {
        final SingleCardEditor_ReviewAccount temp = data.get(position);

        holder.email.setText(data.get(position).getEmail());
        holder.name.setText(data.get(position).getName());
        holder.profession.setText(data.get(position).getProfession());
        holder.contact.setText(data.get(position).getContact());
        holder.gender.setText(data.get(position).getGender());
        holder.address.setText(data.get(position).getAddress());
        holder.about.setText(data.get(position).getAbout());

        holder.btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection(table.tbl_user)
                        .document(temp.getEmail())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(view.getContext(), "Account rejected sucessfully", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(view.getContext(), AdminReviewAccount.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Account reject operation failed.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(view.getContext(), AdminReviewAccount.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });
            }
        });

        holder.btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection(table.tbl_user)
                        .document(temp.getEmail())
                        .update("approval", 1)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(view.getContext(), "Account approved sucessfully", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(view.getContext(), AdminReviewAccount.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Account approve operation failed.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(view.getContext(), AdminReviewAccount.class);
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
