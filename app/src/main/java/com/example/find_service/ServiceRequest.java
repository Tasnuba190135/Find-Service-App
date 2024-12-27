package com.example.find_service;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import input_form_objects.ObjRatingManagement;
import input_form_objects.ObjTableManagement;

public class ServiceRequest extends AppCompatActivity {
    ObjTableManagement table = new ObjTableManagement();

    ArrayList<SingleCardEditor_ServiceRequest> holders = new ArrayList<>();

    RecyclerView recyclerView;
    CardAdapter_ServiceRequest cardAdapter_serviceRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request);

        Toast.makeText(ServiceRequest.this, "Please wait loading ...", Toast.LENGTH_SHORT).show();

        getDataFromDB();
    }

    private void getDataFromDB() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String email = sharedPreferences.getString("user_email", "0");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(table.tbl_rating_management)
                .whereEqualTo("serviceGiverEmail", email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty()) {
//                            Log.d(TAG, "nothing");
//                            NoPostFound();
                            Toast.makeText(ServiceRequest.this, "No request found", Toast.LENGTH_LONG).show();
                        } else {
                            for (QueryDocumentSnapshot row : queryDocumentSnapshots) {
                                ObjRatingManagement objRatingManagement = row.toObject(ObjRatingManagement.class);
                                SingleCardEditor_ServiceRequest obj = new SingleCardEditor_ServiceRequest();

                                if (objRatingManagement.getAcceptByServiceGiver() == 0) {
                                    obj.setRatingID(row.getId());
                                    obj.setServicedescription(objRatingManagement.getServiceDescription());
//                                    Log.d(TAG, obj.getServicedescription() + " Arafat");
                                    obj.setServicePostID(objRatingManagement.getServicePostID());
                                    obj.setServiceReceiverEmail(objRatingManagement.getServiceReceiverEmail());
                                    obj.setServiceGiverEmail(objRatingManagement.getServiceGiverEmail());
                                    obj.setAcceptByServiceGiver(objRatingManagement.getAcceptByServiceGiver());

                                    holders.add(obj);
                                }
                            }
                            if(holders.isEmpty()) {
                                Toast.makeText(ServiceRequest.this, "No request found", Toast.LENGTH_LONG).show();
                            } else {
                                F1();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ServiceRequest.this, "Error", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void F1(){
        recyclerView = (RecyclerView) findViewById(R.id.rvServiceRequest);
        cardAdapter_serviceRequest = new CardAdapter_ServiceRequest(holders, getApplicationContext());
        recyclerView.setAdapter(cardAdapter_serviceRequest);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent=new Intent(ServiceRequest.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}