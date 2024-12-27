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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import input_form_objects.ObjTableManagement;

public class CardAdapter_GiveRatingToTakenService extends RecyclerView.Adapter<SingleCard_GiveRatingToTakenService> {
    ArrayList<SingleCardEditor_GiveRatingToTakenService> data;
    android.content.Context context;

    ObjTableManagement table = new ObjTableManagement();

    public CardAdapter_GiveRatingToTakenService(ArrayList<SingleCardEditor_GiveRatingToTakenService> data, android.content.Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public SingleCard_GiveRatingToTakenService onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_single_card_give_rating_to_taken_service, parent, false);
        return new SingleCard_GiveRatingToTakenService(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleCard_GiveRatingToTakenService holder, int position) {
        final SingleCardEditor_GiveRatingToTakenService temp = data.get(position);

        holder.ratingServiceDetails.setText(data.get(position).getRatingServiceDetails());
        holder.ratingEmail.setText(data.get(position).getRatingEmail());

//        holder.ratingField.setText(data.get(position).getRatingField());

        holder.btnRatingSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection(table.tbl_rating_management)
                        .document(temp.getRatingID())
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(view.getContext(), "Skipped successfully.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(view.getContext(), GiveRatingToTakenService.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Error in skipping. Try again.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(view.getContext(), GiveRatingToTakenService.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });
            }
        });

        holder.btnRatingSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ratings = holder.ratingField.getText().toString();

                if(ratings.equals("0") || ratings.equals("1") || ratings.equals("2") || ratings.equals("3") || ratings.equals("4") || ratings.equals("5")) {

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection(table.tbl_rating_management)
                            .document(temp.getRatingID())
                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    int rating=0;
                                    if(ratings.equals("0")) {
                                        rating = 0;
                                    } else if(ratings.equals("1")){
                                        rating = 1;
                                    } else if(ratings.equals("2")){
                                        rating = 2;
                                    } else if(ratings.equals("3")){
                                        rating = 3;
                                    }else if(ratings.equals("4")){
                                        rating = 4;
                                    }else {
                                        rating = 5;
                                    }

                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    db.collection(table.tbl_user)
                                            .document(temp.getRatingEmail())
                                            .update("rating", FieldValue.increment(rating), "rating_count", FieldValue.increment(1))
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(view.getContext(), "Rating submitted successfully.", Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(view.getContext(), GiveRatingToTakenService.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    context.startActivity(intent);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(view.getContext(), "Error in submit rating. Try again.", Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(view.getContext(), GiveRatingToTakenService.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    context.startActivity(intent);
                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(view.getContext(), "Error in submit rating. Try again. (Check your network connection)", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(view.getContext(), GiveRatingToTakenService.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                }
                            });
                } else {
                    Toast.makeText(view.getContext(), "Type any number from 0 to 5", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
