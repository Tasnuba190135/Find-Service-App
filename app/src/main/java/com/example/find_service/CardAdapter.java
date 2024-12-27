package com.example.find_service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import input_form_objects.ObjRatingManagement;
import input_form_objects.ObjTableManagement;

public class CardAdapter extends RecyclerView.Adapter<Card>{
    ArrayList<CardEditor> data;
    Context context;

    ObjTableManagement tableManagement = new ObjTableManagement();

    public CardAdapter(ArrayList<CardEditor> dataqueue, Context applicationContext) {
        this.data = dataqueue;
        this.context = applicationContext;
    }

    @NonNull
    @Override
    public Card onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.activity_single_card,parent,false);
        //create blank layout then pass viewholder reference pass
        return new Card(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Card holder, int position) {
        final CardEditor temp= data.get(position);

        holder.name01.setText(data.get(position).getName());
        holder.address01.setText(data.get(position).getAddress());
        holder.details.setText(data.get(position).getDetails());
        holder.btnSubmit01.setText(data.get(position).getButton());

        //OnBindViewHolder will decide ClickEvent will where work and where to redirect
        holder.btnSubmit01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getApplocationContext() is working here
                SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
                String x = sharedPreferences.getString("user_email", "0");

                if(x.equals("0")) {
                    Toast.makeText(view.getContext(), "Please log in to view profile", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(context, LogIn.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Intent intent=new Intent(context, UserProfile.class);
                    intent.putExtra("email01",temp.getEmail());
//                    intent.putExtra("access", sharedPreferences.getString("user_email", "0"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

        holder.btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
                String x = sharedPreferences.getString("user_email", "0");

                if(x.equals("0")) {
                    Toast.makeText(view.getContext(), "Please log in to send request", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(context, LogIn.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    if(temp.getEmail().equals(x)) {
                        Toast.makeText(view.getContext(), "You can't request in your own post", Toast.LENGTH_SHORT).show();
                    } else {
                        ObjRatingManagement objRatingManagement = new ObjRatingManagement(temp.getService_post_id(), temp.getDetails(), x, temp.getEmail(), 0);

                        FirebaseFirestore dbCheck = FirebaseFirestore.getInstance();
                        dbCheck.collection(tableManagement.tbl_rating_management)
                                .whereEqualTo("serviceReceiverEmail", temp.getRequest_sender_email())
                                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        if(queryDocumentSnapshots.isEmpty()) {
                                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                                            db.collection(tableManagement.tbl_rating_management)
                                                    .document()
                                                    .set(objRatingManagement)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()) {
                                                                Toast.makeText(view.getContext(), "Request send successfully.", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(view.getContext(), "Request did not send. Try again.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(view.getContext(), "Error in connection. Try again.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        } else {
                                            int alreadySendRequest = 0;
                                            for(QueryDocumentSnapshot row: queryDocumentSnapshots) {
                                                ObjRatingManagement objRatingManagement1 = row.toObject(ObjRatingManagement.class);
                                                if(objRatingManagement1.getServicePostID().equals(temp.getService_post_id())) {
                                                    alreadySendRequest = 1;
                                                    break;
                                                }
                                            }

                                            if(alreadySendRequest == 0){
                                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                db.collection(tableManagement.tbl_rating_management)
                                                        .document()
                                                        .set(objRatingManagement)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()) {
                                                                    Toast.makeText(view.getContext(), "Request send successfully.", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    Toast.makeText(view.getContext(), "Request did not send. Try again.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(view.getContext(), "Error in connection. Try again.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            } else {
                                                Toast.makeText(view.getContext(), "You have already send request.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(view.getContext(), "Error in connection. Try again.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        //data size
        return data.size();
    }

}
