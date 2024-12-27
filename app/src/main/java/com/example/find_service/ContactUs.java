package com.example.find_service;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import input_form_objects.ObjContactUs;
import input_form_objects.ObjTableManagement;

public class ContactUs extends AppCompatActivity {

    EditText userEmail, userContactNo, issueDetails;
    Button btnSubmit;
    Spinner spIssueType;
    String collect="", issueType01;

    List<String> arrayList_issueType;
    ArrayAdapter<String> arrayAdapter_issueType;

    private ObjContactUs objContactUs;
    private ObjTableManagement objTableManagement = new ObjTableManagement();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        userEmail = findViewById(R.id.UserEmail);
        userContactNo = findViewById(R.id.UserContactNo1);
        issueDetails = findViewById(R.id.UserIssueDetails);
        spIssueType = (Spinner) findViewById(R.id.SpIssueType);
        btnSubmit = findViewById(R.id.btnSubmitContactUs);

        arrayList_issueType = new ArrayList<>();
        arrayList_issueType.add("Suggestion");
        arrayList_issueType.add("Forget password");
        arrayList_issueType.add("Other");

        arrayAdapter_issueType = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_issueType);
        spIssueType.setAdapter(arrayAdapter_issueType);

        spIssueType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                issueType01 = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userEmail.getText().toString();
                String contact = userContactNo.getText().toString();
                String details = issueDetails.getText().toString();

                if(email.isEmpty()){
                    Toast.makeText(ContactUs.this, "email field is empty", Toast.LENGTH_SHORT).show();
                } else if(!email.contains("@")){
                    Toast.makeText(ContactUs.this, "email address must contains '@'", Toast.LENGTH_SHORT).show();
                } else if(contact.isEmpty()){
                    Toast.makeText(ContactUs.this, "Enter contact number", Toast.LENGTH_SHORT).show();
                } else if(contact.length() != 11){
                    Toast.makeText(ContactUs.this, "Contact number must have exactly 11 digits", Toast.LENGTH_SHORT).show();
                } else if(details.isEmpty()){
                    Toast.makeText(ContactUs.this, "Issue details can not be empty", Toast.LENGTH_SHORT).show();
                } else {
                    collect += email + "\n" + contact + "\n" + issueType01 + "\n" + details +"\n";
                    objContactUs = new ObjContactUs(email, contact, issueType01, details);

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection(objTableManagement.tbl_contact_us)
                            .document()
                            .set(objContactUs)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(ContactUs.this, "Submitted properly. Thank you.", Toast.LENGTH_LONG).show();
                                        goToHome();
                                    } else {
                                        Toast.makeText(ContactUs.this, "Submission failed. Please try again.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ContactUs.this, "Connection failed.", Toast.LENGTH_LONG).show();
                                }
                            });
                    Toast.makeText(ContactUs.this, "Please wait ...\n________\n" + collect, Toast.LENGTH_SHORT).show();
                    collect = "";
                }

            }
        });
    }

    public void goToHome(){
        Intent intent = new Intent(ContactUs.this, MainActivity.class);
        startActivity(intent);
    }
}