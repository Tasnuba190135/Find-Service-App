package com.example.find_service;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import input_form_objects.ObjPostAService;
import input_form_objects.ObjTableManagement;

public class test100 extends AppCompatActivity {

    ObjTableManagement table = new ObjTableManagement();
    String profession, district, division;
    ArrayList<ObjPostAService> listPost = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test100);


        getDataFromIntent();
        Log.d(TAG, profession + " " + district + " " + division);
        getDataFromDB();
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            profession = bundle.getString("profession");
            district = bundle.getString("district");
            division = bundle.getString("division");
        }
    }

    private void getDataFromDB() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(table.tbl_post_a_service)
//                .whereEqualTo("approve", 0)
//                .whereEqualTo("profession", profession)
                .whereEqualTo("division", division)
//                .whereEqualTo("district", district)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty()) {
//                            Log.d(TAG, "nothing");
                        } else {
                            int x = queryDocumentSnapshots.size();

                            for(QueryDocumentSnapshot row : queryDocumentSnapshots) {
                                ObjPostAService objPostAService = row.toObject(ObjPostAService.class);
                                if( objPostAService.getApprove()==0 && profession.equals(objPostAService.getProfession()) && division.equals(objPostAService.getDivision()) ) {
                                    Log.d(TAG, objPostAService.toString());
                                    listPost.add(objPostAService);
                                }
                            }
                            Log.d(TAG, listPost.toString());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"Error");
                        Toast.makeText(test100.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}