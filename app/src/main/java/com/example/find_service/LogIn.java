package com.example.find_service;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import admin_section.AdminHome;
import input_form_objects.ObjAdmin;
import input_form_objects.ObjRegistration;
import input_form_objects.ObjTableManagement;

public class LogIn extends AppCompatActivity {
    SharedPreferences sharedPreferences1, adminSharedPreferences;
    ObjTableManagement table = new ObjTableManagement();
    ObjRegistration objRegistration;
    int isSession, isSessionAdmin;

    boolean invalid = false;
    String Colector = "", accType, name, profession, email, password;
    EditText userEmail, userPassword;
    Spinner sp_accountType;
    Button logIn, SignUp;

    List<String> list_accountType;
    ArrayAdapter<String> adapter_accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        adminSharedPreferences = getSharedPreferences("admin", MODE_PRIVATE);
        isSessionAdmin = adminSharedPreferences.getInt("admin_session", 0);

        sharedPreferences1 = getSharedPreferences("user", MODE_PRIVATE);
        isSession = sharedPreferences1.getInt("user_session", 0);


        userEmail = findViewById(R.id.UserEmail);
        userPassword = findViewById(R.id.UserPassword);
        logIn = findViewById(R.id.btnSubmit);
        SignUp = findViewById(R.id.btnSignup);
        sp_accountType = findViewById(R.id.SpAccountType);

        list_accountType = new ArrayList<>();
        list_accountType.add("User");
        list_accountType.add("Admin");
        adapter_accountType = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, list_accountType);
        sp_accountType.setAdapter(adapter_accountType);

        sp_accountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                accType = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = userEmail.getText().toString();
                password = userPassword.getText().toString();

                if (email.isEmpty() || !email.contains("@")) {
                    Toast.makeText(LogIn.this, "Please fill email correctly", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(LogIn.this, "Please fill the password field", Toast.LENGTH_SHORT).show();
                } else {
                    Colector += accType + "\n";
                    Colector += email + "\n";
                    Colector += password + "\n";

//                    Toast.makeText(LogIn.this, "User Info \n:" + Colector, Toast.LENGTH_SHORT).show();

                    if(accType == "User"){
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection(table.tbl_user)
                                .document(email)
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if(documentSnapshot.exists()) {
                                            objRegistration = documentSnapshot.toObject(ObjRegistration.class);
                                            if(objRegistration.getApproval() == 0) {
                                                Toast.makeText(LogIn.this, "Account is not approved by admin. Please wait for a while.", Toast.LENGTH_LONG).show();
                                            } else {
//                                            if( email.equals(objRegistration.getEmail()) ) {
//                                                Log.d(TAG, email +" "+objRegistration.getEmail());
//                                            }
//                                            Log.d(TAG, email +" /"+objRegistration.getEmail());
//                                            if( password.equals(objRegistration.getPassword()) ) {
//                                                Log.d(TAG, password+" "+objRegistration.getPassword());
//                                            }
//                                            Log.d(TAG, password+" /"+objRegistration.getPassword());
                                                if( email.equals(objRegistration.getEmail()) && password.equals(objRegistration.getPassword()) ) {
                                                    name = objRegistration.getName();
                                                    profession = objRegistration.getProfession();
                                                    userSessionCreator();
                                                } else {
                                                    Toast.makeText(LogIn.this, "Incorrect log in information. Please fill up correctly.", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        } else {
                                            Toast.makeText(LogIn.this, "No account found using this email. Please fill up correctly.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LogIn.this, "Error fetching data. Please try again.", Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        Log.d(TAG, email +" "+password);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection(table.tbl_admin)
                                .document(email)
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if(documentSnapshot.exists()) {
                                            ObjAdmin objAdmin = documentSnapshot.toObject(ObjAdmin.class);
                                            if( email.equals(objAdmin.getEmail()) && password.equals(objAdmin.getPassword()) ) {
                                                adminSessionCreator();
                                            } else {
                                                Toast.makeText(LogIn.this, "Incorrect log in information. Please fill up correctly.", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Toast.makeText(LogIn.this, "No account found using this email. Please fill up correctly.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LogIn.this, "Error fetching data. Please try again.", Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                }
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, SignUp.class);
                startActivity(intent);
            }
        });

    }

    public void adminSessionCreator() {
        SharedPreferences.Editor editor = adminSharedPreferences.edit();
        editor.putString("admin_email", email);
        editor.putInt("admin_session", 1);
        editor.apply();

        Toast.makeText(LogIn.this, "Log in successful.\n"+email, Toast.LENGTH_LONG).show();
        goToAdminHome();
    }

    public void goToAdminHome() {
        Intent intent = new Intent(LogIn.this, AdminHome.class);
//        The next line means clear all previous activities in the stack of activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void userSessionCreator() {
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString("user_name", name);
        Log.d(TAG, "Ok --- " + profession);
        editor.putString("user_profession", profession);
        editor.putString("user_email", email);
        editor.putString("user_password", password);
        editor.putInt("user_session", 1);
        editor.putInt("user_post_quota", objRegistration.getPost_quota());
        editor.apply();
        Toast.makeText(LogIn.this, "Log in successful.\n"+email, Toast.LENGTH_LONG).show();
        goToHomePage();
    }

    public void goToHomePage() {
        Intent intent = new Intent(LogIn.this, MainActivity.class);
//        The next line means clear all previous activities in the stack of activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(isSession != 0) {
            Intent intent = new Intent(LogIn.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}