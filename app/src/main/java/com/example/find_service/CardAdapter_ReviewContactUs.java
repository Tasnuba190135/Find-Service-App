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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import admin_section.AdminHome;
import input_form_objects.ObjContactUs;
import input_form_objects.ObjTableManagement;

public class CardAdapter_ReviewContactUs extends RecyclerView.Adapter<SingleCard_ReviewContactUs> {
    ArrayList<SingleCardEditor_ReviewContactUs> data;
    android.content.Context context;

    ObjTableManagement table = new ObjTableManagement();

    public CardAdapter_ReviewContactUs(ArrayList<SingleCardEditor_ReviewContactUs> data, android.content.Context context){
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public SingleCard_ReviewContactUs onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_single_card_review_contact_us, parent, false);
        return new SingleCard_ReviewContactUs(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleCard_ReviewContactUs holder, int position) {
        final SingleCardEditor_ReviewContactUs temp = data.get(position);

        holder.issueType.setText(data.get(position).getIssueType());
        holder.issuerEmail.setText(data.get(position).getIssuerEmail());
        holder.issuerContact.setText(data.get(position).getIssuerContact());
        holder.issuerMessage.setText(data.get(position).getIssuerMessage());
        holder.feedback.setText(data.get(position).getFeedback());

//        holder.btnQueue.setText("Remove as false issue");
        holder.btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = holder.feedback.getText().toString();

                FirebaseFirestore dbCheck = FirebaseFirestore.getInstance();
                dbCheck.collection(table.tbl_contact_us)
                        .document(temp.getIssueID())
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                ObjContactUs objContactUs = documentSnapshot.toObject(ObjContactUs.class);

                                if(objContactUs.getIsSolved() == 0) {
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    db.collection(table.tbl_contact_us)
                                            .document(temp.getIssueID())
                                            .update("feedback", s, "isSolved", 1)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(view.getContext(), "Use gmail app to continue give feedback.", Toast.LENGTH_SHORT).show();

                                                    String str;
                                                    Intent sendEmail=new Intent(Intent.ACTION_SEND);
                                                    sendEmail.putExtra(Intent.EXTRA_EMAIL,new String[]{temp.getIssuerEmail()});
                                                    str = "Find Service Contact Us - " + temp.getIssueType();
                                                    sendEmail.putExtra(Intent.EXTRA_SUBJECT, str);
                                                    str = holder.feedback.getText().toString();
                                                    sendEmail.putExtra(Intent.EXTRA_TEXT, str);
                                                    sendEmail.setType("message/rfc822");
                                                    context.startActivity(Intent.createChooser(sendEmail,"Give feedback"));
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(view.getContext(), "Error. Please try again.", Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(view.getContext(), AdminReviewContactUs.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    context.startActivity(intent);
                                                }
                                            });
                                } else {
                                    Toast.makeText(view.getContext(), "Already send feedback. Please tap on \"next\" button.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Error. Please try again.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(view.getContext(), AdminReviewContactUs.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });
            }
        });

        holder.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String s = holder.feedback.getText().toString();
                FirebaseFirestore dbCheck = FirebaseFirestore.getInstance();
                dbCheck.collection(table.tbl_contact_us)
                        .document(temp.getIssueID())
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                ObjContactUs objContactUs = documentSnapshot.toObject(ObjContactUs.class);
                                if(objContactUs.getIsSolved() == 0) {
                                    Toast.makeText(view.getContext(), "Please give feedback/solution. Otherwise, tap on \"Skip, delete and next\" button", Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intent = new Intent(view.getContext(), AdminReviewContactUs.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Error. Please try again.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(view.getContext(), AdminReviewContactUs.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });
            }
        });

        holder.btnSkipAndDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String s = holder.feedback.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection(table.tbl_contact_us)
                        .document(temp.getIssueID())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(view.getContext(), "Skipped and deleted successfully", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(view.getContext(), AdminReviewContactUs.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Error. Please try again.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(view.getContext(), AdminReviewContactUs.class);
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
