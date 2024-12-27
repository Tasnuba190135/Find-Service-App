package com.example.find_service;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import input_form_objects.ObjPostAService;
import input_form_objects.ObjTableManagement;

public class AdminReviewPost extends AppCompatActivity {
    ArrayList<SingleCardEditor_ReviewPost> holders = new ArrayList<>();

    RecyclerView recyclerView;
    CardAdapter_ReviewPost cardAdapter_reviewPost;

    ObjTableManagement table = new ObjTableManagement();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_review_post);

        getDataFromDB();
    }

    public void getDataFromDB() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(table.tbl_post_a_service)
                .whereEqualTo("approve", 0)
                .limit(1)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty()) {
                            Toast.makeText(AdminReviewPost.this,"No data found", Toast.LENGTH_SHORT).show();
                        } else {
                            for (QueryDocumentSnapshot row : queryDocumentSnapshots) {
                                ObjPostAService objPostAService = row.toObject(ObjPostAService.class);
                                SingleCardEditor_ReviewPost obj = new SingleCardEditor_ReviewPost();

//                                Log.d(TAG, row.getId());

                                obj.setDocumentID(row.getId());
                                obj.setCategory(objPostAService.getProfession());
                                obj.setDescription(objPostAService.getDescription());

                                holders.add(obj);
                            }
                            executeRecyclerView();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminReviewPost.this, "Error fetching data", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void executeRecyclerView(){
        ArrayList<SingleCardEditor_ReviewPost> temp = new ArrayList<>();
        temp.add(holders.get(0));
//        Log.d(TAG, temp.toString() + " --hi");

        F1(temp);
    }

    public void F1(ArrayList<SingleCardEditor_ReviewPost> t){
        recyclerView = (RecyclerView) findViewById(R.id.rvReviewPost);
        cardAdapter_reviewPost = new CardAdapter_ReviewPost(t, getApplicationContext());
        recyclerView.setAdapter(cardAdapter_reviewPost);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

}