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

import input_form_objects.ObjTableManagement;

public class CardAdapter_ServiceRequest extends RecyclerView.Adapter<SingleCard_ServiceRequest> {
    ArrayList<SingleCardEditor_ServiceRequest> data;
    android.content.Context context;

    ObjTableManagement table = new ObjTableManagement();

    public CardAdapter_ServiceRequest(ArrayList<SingleCardEditor_ServiceRequest> data, android.content.Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public SingleCard_ServiceRequest onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_single_card_service_request, parent, false);
        return new SingleCard_ServiceRequest(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleCard_ServiceRequest holder, int position) {
        final SingleCardEditor_ServiceRequest temp = data.get(position);

        holder.postDetails.setText(data.get(position).getServicedescription());

        holder.btnDeclineRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection(table.tbl_rating_management)
                        .document(temp.getRatingID())
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(view.getContext(), "Request declined.", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(view.getContext(), ServiceRequest.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Request decline failed.  Try again.", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(view.getContext(), ServiceRequest.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

            }
        });

        holder.btnAcceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection(table.tbl_rating_management)
                        .document(temp.getRatingID())
                        .update("acceptByServiceGiver", 1)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(view.getContext(), "Request accepted.", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(view.getContext(), ServiceRequest.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Request accept failed. Try again.", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(view.getContext(), ServiceRequest.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });
            }
        });

        holder.btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, UserProfile.class);
                intent.putExtra("email01",temp.getServiceReceiverEmail());
//                    intent.putExtra("access", sharedPreferences.getString("user_email", "0"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
