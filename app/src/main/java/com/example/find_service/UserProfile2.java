package com.example.find_service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import EditProfile.UserEditProfile;
import input_form_objects.ObjRegistration;
import input_form_objects.ObjTableManagement;

public class UserProfile2 extends AppCompatActivity {
    ObjTableManagement table = new ObjTableManagement();
    ObjRegistration objRegistration;
    String email1, access;

    TextView name, email, rating, contact, gender, profession, address, about;
    Button btnEditProfile, btnManagePost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile2);

        name = findViewById(R.id.profileNameUser);
        email = findViewById(R.id.profileEmailUser);
        rating = findViewById(R.id.profileRatingUser);
        profession = findViewById(R.id.profileProfessionUser);
        contact = findViewById(R.id.profileContactUser);
        gender = findViewById(R.id.profileGenderUser);
        address = findViewById(R.id.profileAddressUser);
        about = findViewById(R.id.profileAboutUser);

        btnEditProfile = findViewById(R.id.BtnEditProfileUser2);
        btnManagePost = findViewById(R.id.BtnManagePostUser);

        getDataFromIntent();
        getDataFromDB();

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile2.this, UserEditProfile.class);
                startActivity(intent);
            }
        });

        btnManagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile2.this, ManageMyPost.class);
                intent.putExtra("email01", email1);
                startActivity(intent);
            }
        });
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            email1 = bundle.getString("email01");
//            access = bundle.getString("access", "0");
//            Toast.makeText(UserProfile.this, bundle.getString("access", "0"), Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataFromDB() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(table.tbl_user)
                .document(email1)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        objRegistration = document.toObject(ObjRegistration.class);

                        int temp1;
                        if(objRegistration.getRating_count() > 0) {
                            temp1 = objRegistration.getRating() / objRegistration.getRating_count();
                        } else {
                            temp1 = 0;
                        }
                        String temp2 = Integer.toString(temp1);
                        String addressTemp = objRegistration.getAddressDetails() + ", " + objRegistration.getDistrict() + ", " + objRegistration.getDivision();

                        name.setText(objRegistration.getName());
                        email.setText(email1);
                        rating.setText(temp2);
                        profession.setText(objRegistration.getProfession());
                        contact.setText(objRegistration.getContact());
                        gender.setText(objRegistration.getGender());
                        address.setText(addressTemp);
                        about.setText(objRegistration.getAbout());
                    }
                });
    }
}