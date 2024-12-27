package com.example.find_service;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import input_form_objects.ObjTableManagement;

public class CardAdapter_ManageMyPost extends RecyclerView.Adapter<SingleCard_ManageMyPost> {
    ArrayList<SingleCardEditor_ManageMyPost> data;
    android.content.Context context;

    ObjTableManagement table = new ObjTableManagement();

    public CardAdapter_ManageMyPost(ArrayList<SingleCardEditor_ManageMyPost> data, android.content.Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public SingleCard_ManageMyPost onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_single_card_manage_post, parent, false);
        return new SingleCard_ManageMyPost(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleCard_ManageMyPost holder, int position) {
        final SingleCardEditor_ManageMyPost temp = data.get(position);

        holder.category.setText(data.get(position).getCategory());
        holder.address.setText(data.get(position).getAddress());
        holder.description.setText(data.get(position).getDescription());
        
        if(data.get(position).getPostStatus() == 1) {
            holder.postStatus.setText("Published");
        } else {
            holder.postStatus.setText("Unpublished");
        }


        holder.btnDeletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), temp.getPostID()+" ", Toast.LENGTH_SHORT).show();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection(table.tbl_post_a_service)
                        .document(temp.getPostID())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                FirebaseFirestore db2 = FirebaseFirestore.getInstance();
                                db2.collection(table.tbl_user)
                                        .document(temp.getEmail())
                                        .update("post_quota", FieldValue.increment(1))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(view.getContext(), "Delete successful.", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(view.getContext(), UserProfile2.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                context.startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(view.getContext(), "Delete unsuccessful. Try again.", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(view.getContext(), UserProfile2.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                context.startActivity(intent);
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Delete unsuccessful. Try again.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(view.getContext(), UserProfile2.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
