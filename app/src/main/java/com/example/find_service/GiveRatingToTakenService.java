package com.example.find_service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import input_form_objects.ObjRatingManagement;
import input_form_objects.ObjTableManagement;

public class GiveRatingToTakenService extends AppCompatActivity {
    ArrayList<SingleCardEditor_GiveRatingToTakenService> holders = new ArrayList<>();

    RecyclerView recyclerView;
    CardAdapter_GiveRatingToTakenService cardAdapter_giveRatingToTakenService;

    ObjTableManagement table = new ObjTableManagement();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_rating_to_give);

        getDataFromDB();
    }

    public void getDataFromDB() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String email01 = sharedPreferences.getString("user_email", "0");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(table.tbl_rating_management)
                .whereEqualTo("serviceReceiverEmail", email01)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty()) {
                            Toast.makeText(GiveRatingToTakenService.this, "No taken service list to give rating.", Toast.LENGTH_SHORT).show();
                        } else {
                            for (QueryDocumentSnapshot row: queryDocumentSnapshots) {
                                ObjRatingManagement objRatingManagement = row.toObject(ObjRatingManagement.class);
                                if(objRatingManagement.getAcceptByServiceGiver() == 1) {
//                                    ObjRatingManagement objRatingManagement = row.toObject(ObjRatingManagement.class);
                                    SingleCardEditor_GiveRatingToTakenService obj = new SingleCardEditor_GiveRatingToTakenService();

                                    obj.setRatingID(row.getId());
                                    obj.setPostID(objRatingManagement.getServicePostID());
                                    obj.setRatingEmail(objRatingManagement.getServiceGiverEmail());
                                    obj.setRatingServiceDetails(objRatingManagement.getServiceDescription());

                                    holders.add(obj);
                                }
                            }
                            if(holders.isEmpty()) {
                                Toast.makeText(GiveRatingToTakenService.this, "No taken service list to give rating.", Toast.LENGTH_SHORT).show();
                            } else {
                                F1();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(GiveRatingToTakenService.this, "Error fetching data. Try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void F1() {
        recyclerView = (RecyclerView) findViewById(R.id.rvGiveRatingToTakenService);
        cardAdapter_giveRatingToTakenService = new CardAdapter_GiveRatingToTakenService(holders, getApplicationContext());
        recyclerView.setAdapter(cardAdapter_giveRatingToTakenService);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(GiveRatingToTakenService.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}