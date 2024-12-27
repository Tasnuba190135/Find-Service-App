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
import input_form_objects.ObjRegistration;
import input_form_objects.ObjTableManagement;

public class ManageMyPost extends AppCompatActivity {
    ArrayList<SingleCardEditor_ManageMyPost> holders = new ArrayList<>();
    String email1;

//    SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

    RecyclerView recyclerView;
    CardAdapter_ManageMyPost cardAdapter_manageMyPost;

    ObjTableManagement table = new ObjTableManagement();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_my_post);

        getDataFromIntent();
        getDataFromDB();
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            email1 = bundle.getString("email01");
        }
    }

    public void getDataFromDB() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(table.tbl_post_a_service)
                .whereEqualTo("email", email1)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putInt("takenDataFromDB", 1);
//                        editor.apply();

                        if(queryDocumentSnapshots.isEmpty()) {
                            Toast.makeText(ManageMyPost.this,"No data found", Toast.LENGTH_SHORT).show();
                        } else {
                            for(QueryDocumentSnapshot row : queryDocumentSnapshots) {
                                ObjPostAService objPostAService = row.toObject(ObjPostAService.class);
                                SingleCardEditor_ManageMyPost obj = new SingleCardEditor_ManageMyPost();

//                                Log.d(TAG, objRegistration.toString());

                                obj.setPostID(row.getId());
                                obj.setPostStatus(objPostAService.getApprove());
                                obj.setEmail(email1);
                                String addressTemp = objPostAService.getAddress() + ", " + objPostAService.getDistrict() + " " + objPostAService.getDivision();
                                obj.setCategory(objPostAService.getProfession());
                                obj.setAddress(addressTemp);
                                obj.setDescription(objPostAService.getDescription());

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
                        Toast.makeText(ManageMyPost.this,"Error fetching data.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void executeRecyclerView() {
        ArrayList<SingleCardEditor_ManageMyPost> temp = new ArrayList<>();
        temp.add(holders.get(0));
//        temp.add(holders.get(1));
//        Log.d(TAG, temp.toString() + " --hi");

        F1(temp);
    }

    public void F1(ArrayList<SingleCardEditor_ManageMyPost> t){
        recyclerView = (RecyclerView) findViewById(R.id.rvManageMyPost);
        cardAdapter_manageMyPost = new CardAdapter_ManageMyPost(holders, getApplicationContext());
        recyclerView.setAdapter(cardAdapter_manageMyPost);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
    }
}