package com.example.find_service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import input_form_objects.ObjContactUs;
import input_form_objects.ObjTableManagement;

public class AdminReviewContactUs extends AppCompatActivity {
    ArrayList<SingleCardEditor_ReviewContactUs> holders = new ArrayList<>();

    RecyclerView recyclerView;
    CardAdapter_ReviewContactUs cardAdapter_reviewContactUs;

    ObjTableManagement table = new ObjTableManagement();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_review_contact_us);

        getDataFromDB();
    }

    public void getDataFromDB(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(table.tbl_contact_us)
                .whereEqualTo("isSolved", 0)
                .limit(1)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty()) {
                            Toast.makeText(AdminReviewContactUs.this, "No data remaining", Toast.LENGTH_SHORT).show();
                        } else {
                            for(QueryDocumentSnapshot row : queryDocumentSnapshots) {
                                ObjContactUs objContactUs = row.toObject(ObjContactUs.class);
                                SingleCardEditor_ReviewContactUs obj = new SingleCardEditor_ReviewContactUs();

                                obj.setIssueID(row.getId());
                                obj.setIssueType(objContactUs.getIssueType());
                                obj.setIssuerEmail(objContactUs.getEmail());
                                obj.setIssuerContact(objContactUs.getContactNo());
                                obj.setIssuerMessage(objContactUs.getDescription());
                                obj.setFeedback(objContactUs.getFeedback());

                                holders.add(obj);
                            }
                            executeRecyclerView();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminReviewContactUs.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void executeRecyclerView() {
//        SingleCardEditor_ReviewContactUs x = new SingleCardEditor_ReviewContactUs();

        ArrayList<SingleCardEditor_ReviewContactUs> temp = new ArrayList<>();
        temp.add(holders.get(0));
//        temp.add(x);
//        Log.d(TAG, temp.toString() + " --hi");

        F1(temp);
    }

    public void F1(ArrayList<SingleCardEditor_ReviewContactUs> t) {
        recyclerView = (RecyclerView) findViewById(R.id.rvReviewContactUs);
        cardAdapter_reviewContactUs = new CardAdapter_ReviewContactUs(t, getApplicationContext());
        recyclerView.setAdapter(cardAdapter_reviewContactUs);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
    }
}