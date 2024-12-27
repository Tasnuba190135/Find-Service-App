package com.example.find_service;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import EditProfile.UserEditProfile;
import admin_section.AdminHome;
import input_form_objects.ObjRegistration;
import input_form_objects.ObjTableManagement;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences, adminSharedPreferences;
    int isSession, isSessionAdmin;
    String email;

    public ObjRegistration objRegistration;
    private ObjTableManagement table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adminSharedPreferences = getSharedPreferences("admin", MODE_PRIVATE);
        isSessionAdmin = adminSharedPreferences.getInt("admin_session", 0);

        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        isSession = sharedPreferences.getInt("user_session", 0);

//        Map<String, Object> msg = new HashMap<>();
//        msg.put("timestamp", FieldValue.serverTimestamp());
//        Log.d(TAG, FieldValue.serverTimestamp()+ "   ,erdknvjkm,revj ");

        Button ServiceRequest01 = (Button) findViewById(R.id.btnServiceRequest);
        Button PendingReview01 = (Button) findViewById(R.id.btnPendingReview);

        Button Profile01 = (Button) findViewById(R.id.btnLogin);
        Button FindService01 = (Button) findViewById(R.id.btnFindService);
        Button PostAService01 = (Button) findViewById(R.id.btnPostAService);
        Button AboutUs01 = (Button) findViewById(R.id.btnAboutUs);
        Button ContactUs01 = (Button) findViewById(R.id.btnContactUs);
        Button LogOut01 = (Button) findViewById(R.id.btnLogOut);

//        Button temp1 = (Button) findViewById(R.id.btnTempProfile);

        if (isSessionAdmin == 1) {
            goToAdminHome();
        } else if (isSession == 1) {
            Profile01.setText("Profile");
            Profile01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    email = sharedPreferences.getString("user_email", "0");
                    Intent intent = new Intent(MainActivity.this, UserProfile2.class);
                    intent.putExtra("email01", email);
                    startActivity(intent);
                }
            });

            LogOut01.setVisibility(View.VISIBLE);
            LogOut01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();

                    goToHome();
                }
            });

            ServiceRequest01.setVisibility(View.VISIBLE);
            PendingReview01.setVisibility(View.VISIBLE);

            ServiceRequest01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, ServiceRequest.class);
                    startActivity(intent);
                }
            });

            PendingReview01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, GiveRatingToTakenService.class);
                    startActivity(intent);
                }
            });
        } else {
            Profile01.setText("Log in");
            Profile01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, LogIn.class);
                    startActivity(intent);
                }
            });
            LogOut01.setVisibility(View.GONE);
            ServiceRequest01.setVisibility(View.GONE);
            PendingReview01.setVisibility(View.GONE);
        }


        FindService01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FindAService.class);
                startActivity(intent);
            }
        });

        PostAService01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSession == 0) {
                    Toast.makeText(MainActivity.this, "Please login to make post.", Toast.LENGTH_LONG).show();
                } else {
//                    Log.d(TAG, sharedPreferences.getString("user_profession", "0").toString());
                    if(sharedPreferences.getString("user_profession", "0").equals("General User")){
                        Toast.makeText(MainActivity.this, "Sorry, general user can not post", Toast.LENGTH_LONG).show();
                    } else {
                        if(sharedPreferences.getInt("user_post_quota", 0) == 0) {
                            Toast.makeText(MainActivity.this, "You have exceeded post quota (Maximum 2 post for a user). Check profile to view posts", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(MainActivity.this, PostAService.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });

        AboutUs01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutUs.class);
                startActivity(intent);
            }
        });

        ContactUs01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContactUs.class);
                startActivity(intent);
            }
        });

//        ContactUs01.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                String email01 = sharedPreferences.getString("user_email", "0");
//                String email01 = sharedPreferences.getString("user_email", "0");
//
//                FirebaseFirestore db = FirebaseFirestore.getInstance();
//                db.collection(table.tbl_user)
//                        .document(email01)
//                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                            @Override
//                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                if(documentSnapshot.exists()) {
//                                    ObjRegistration objRegistration = documentSnapshot.toObject(ObjRegistration.class);
////                            processForm();
//                                    List<ObjRegistration> clickedObj = new ArrayList<>();
//                                    clickedObj.add(objRegistration);
//                                    Intent intent = new Intent(MainActivity.this, UserEditProfile.class);
////                                    startActivity(intent);
//                                    intent.putExtra("Object", clickedObj);
//
//
//                                } else {
//                                    Toast.makeText(MainActivity.this, "Error. Try again.", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(MainActivity.this, "Connection error. Try again.", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//
//            }
//        });


//        Temporarily added
//        temp1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, AdminReviewContactUs.class);
//                startActivity(intent);
//            }
//        });


    }


    public void goToAdminHome() {
        Intent intent = new Intent(MainActivity.this, AdminHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void goToHome() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}