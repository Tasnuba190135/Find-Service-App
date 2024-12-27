package com.example.find_service;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.transition.Hold;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import input_form_objects.ObjRegistration;
import input_form_objects.ObjTableManagement;

public class AdminReviewAccount extends AppCompatActivity {
    ArrayList<SingleCardEditor_ReviewAccount> holders = new ArrayList<>();

//    SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

    RecyclerView recyclerView;
    CardAdapter_ReviewAccount cardAdapter_reviewAccount;

    ObjTableManagement table = new ObjTableManagement();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_review_account);

        getDataFromDB();

//        TO-DO reduce DB read later
//        int hasData = sharedPreferences.getInt("takenDataFromDB", 0);
//        if(hasData == 0) {
//            getDataFromDB();
//        } else {
//            executeRecyclerView();
//        }
    }

    public void getDataFromDB() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(table.tbl_user)
                .whereEqualTo("approval", 0)
                .limit(1)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putInt("takenDataFromDB", 1);
//                        editor.apply();

                        if(queryDocumentSnapshots.isEmpty()) {
                            Toast.makeText(AdminReviewAccount.this,"No data found", Toast.LENGTH_SHORT).show();
                        } else {
                            for(QueryDocumentSnapshot row : queryDocumentSnapshots) {
                                ObjRegistration objRegistration = row.toObject(ObjRegistration.class);
                                SingleCardEditor_ReviewAccount obj = new SingleCardEditor_ReviewAccount();

                                Log.d(TAG, objRegistration.toString());

                                String addressTemp = objRegistration.getAddressDetails() + ", " + objRegistration.getDistrict() + ", " + objRegistration.getDivision();
                                obj.setName(objRegistration.getName());
                                obj.setEmail(objRegistration.getEmail());
                                obj.setProfession(objRegistration.getProfession());
                                obj.setContact(objRegistration.getContact());
                                obj.setGender(objRegistration.getGender());
                                obj.setAddress(addressTemp);
                                obj.setAbout(objRegistration.getAbout());

                                holders.add(obj);
                            }

                            Log.d(TAG, holders.size() + " ");

                            executeRecyclerView();

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminReviewAccount.this,"Error fetching data.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void executeRecyclerView() {
        ArrayList<SingleCardEditor_ReviewAccount> temp = new ArrayList<>();
        temp.add(holders.get(0));
//        temp.add(holders.get(1));

        Log.d(TAG, temp.toString() + " --hi");

        F1(temp);
    }

    public void F1(ArrayList<SingleCardEditor_ReviewAccount> t){
        recyclerView = (RecyclerView) findViewById(R.id.rvReviewAccount);
        cardAdapter_reviewAccount = new CardAdapter_ReviewAccount(t, getApplicationContext());
        recyclerView.setAdapter(cardAdapter_reviewAccount);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
    }
}