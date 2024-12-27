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

import input_form_objects.ObjPostAService;
import input_form_objects.ObjTableManagement;

public class ViewPosts extends AppCompatActivity {

    ObjTableManagement table = new ObjTableManagement();
    String profession, district, division;

    ArrayList<ObjPostAService> listPost = new ArrayList<>();
    ArrayList<CardEditor>holders=new ArrayList<>();

    //rcv object create
    RecyclerView rcv;
    CardAdapter cardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_posts);

        Toast.makeText(ViewPosts.this, "Please wait loading ...", Toast.LENGTH_LONG).show();

//        F1();
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
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String email1 = sharedPreferences.getString("user_email", "0");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(table.tbl_post_a_service)
//                .whereEqualTo("approve", 0)
//                .whereEqualTo("profession", profession)
//                .whereEqualTo("division", division)
                .whereEqualTo("district", district)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int x = queryDocumentSnapshots.size();
//                        Log.d(TAG, x + " ");
                        if(queryDocumentSnapshots.isEmpty()) {
//                            Log.d(TAG, "nothing");
//                            NoPostFound();
                            Toast.makeText(ViewPosts.this, "No post found", Toast.LENGTH_LONG).show();
                        } else {
                            for(QueryDocumentSnapshot row : queryDocumentSnapshots) {
                                ObjPostAService objPostAService = row.toObject(ObjPostAService.class);
                                CardEditor ob1 = new CardEditor();
                                if( objPostAService.getApprove()==1 && profession.equals(objPostAService.getProfession()) && division.equals(objPostAService.getDivision()) ) {
//                                    Log.d(TAG, objPostAService.toString());
                                    String addressTemp = objPostAService.getAddress() + ", " + objPostAService.getDistrict() + ", " + objPostAService.getDivision();
                                    ob1.setService_post_id(row.getId());
                                    ob1.setName(objPostAService.getName());
                                    ob1.setDetails(objPostAService.getDescription());
                                    ob1.setAddress(addressTemp);
                                    ob1.setButton("View profile");
                                    ob1.setEmail(objPostAService.getEmail());
                                    ob1.setRequest_sender_email(email1);

                                    holders.add(ob1);
                                }
                            }
//                            Log.d(TAG, holders.toString());
                            if(holders.isEmpty()) {
//                                NoPostFound();
                                Toast.makeText(ViewPosts.this, "No post found", Toast.LENGTH_LONG).show();
                            } else {
                                F1();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG,"Error");
                        Toast.makeText(ViewPosts.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void F1() {
        rcv = (RecyclerView)findViewById(R.id.recview);
        cardAdapter =new CardAdapter(holders, getApplicationContext());
        rcv.setAdapter(cardAdapter);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,1);
        rcv.setLayoutManager(gridLayoutManager);
    }

    public void NoPostFound() {
        Intent intent = new Intent(ViewPosts.this, NoPost.class);
        startActivity(intent);
    }

//    public ArrayList<CardEditor> dataqueue()
//    {
//        ArrayList<CardEditor>holder=new ArrayList<>();
//        //create object of model class
//        CardEditor ob1=new CardEditor();
//        ob1.setName("Name:AAAAAAA");
//        ob1.setAddress("Address:Gazipur,Dhaka");
//        ob1.setButton("View Details");
//        ob1.setEmail("Email Address:abc1@gmail.com");
//        holder.add(ob1);
//
//        CardEditor ob2=new CardEditor();
//        ob2.setName("Name:BBBBBBB");
//        ob2.setAddress("Address:Narayanganj,Dhaka");
//        ob2.setButton("View Details");
//        ob2.setEmail("Email Address:abc2@gmail.com");
//        holder.add(ob2);
//
//        CardEditor ob3=new CardEditor();
//        ob3.setName("Name:CCCCCCC");
//        ob3.setAddress("Address:Dhanmondi,Dhaka");
//        ob3.setButton("View Details");
//        ob3.setEmail("Email Address:abc3@gmail.com");
//        holder.add(ob3);
//
//        return holder;
//    }



}