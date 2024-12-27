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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import input_form_objects.ObjPostAService;
import input_form_objects.ObjTableManagement;

public class PostAService extends AppCompatActivity {

    ObjTableManagement objTableManagement = new ObjTableManagement();
    ObjPostAService objPostAService;

    boolean invalid = false;
    String Colector = "";
    String serviceType, name, division, district;
    TextView txtalertName;
    EditText UserServiceDetails, UserAddress1, UserContact, UserComment;
    Button SubmitSave;
    RadioButton Malebtn, Femalbtn;

    Spinner sp_parent, sp_child, sp_serviceType;

    List<String> arrayList_serviceType;
    ArrayAdapter<String> arrayAdapter_serviceType;

    List<String> arrayList_parent;
    ArrayAdapter<String> arrayAdapter_parent;

    List<String> arrayList_all, arrayList_dhaka, arrayList_khulna,arrayList_rajshahi,arrayList_sylhet,arrayList_chittagong,arrayList_barisal;
    ArrayAdapter<String> arrayAdapter_child;

    /*CheckBox html,css,php;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_aservice);

        UserServiceDetails = findViewById(R.id.serviceDetails);
        UserAddress1 = findViewById(R.id.address1);
//        UserContact = findViewById(R.id.userContact);
//        UserComment = findViewById(R.id.usercomment);
//        txtalertName = findViewById(R.id.userAlert);

        /*html=findViewById(R.id.HTML);
        css=findViewById(R.id.CSS);
        php=findViewById(R.id.PHP);*/


        sp_serviceType = (Spinner) findViewById(R.id.SpServiceType);
        arrayList_serviceType = new ArrayList<>();
        arrayList_serviceType.add("Computer Software Engineer");
        arrayList_serviceType.add("Computer Hardware Engineer");
        arrayList_serviceType.add("Electrician");
        arrayList_serviceType.add("Plumber");
        arrayList_serviceType.add("Home wall painter");
        arrayList_serviceType.add("Tutor");
        arrayList_serviceType.add("Other");

        arrayAdapter_serviceType = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_serviceType);
        sp_serviceType.setAdapter(arrayAdapter_serviceType);

        sp_serviceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item=adapterView.getItemAtPosition(i).toString();
                serviceType = item;
//                Toast.makeText(PostAService.this, "Service type: "+item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_parent = (Spinner) findViewById(R.id.SpDivision);
        sp_child = (Spinner) findViewById(R.id.SpDistrict);

        arrayList_parent = new ArrayList<>();
        arrayList_parent.add("Dhaka");
        arrayList_parent.add("Khulna");
        arrayList_parent.add("Rajshahi");
        arrayList_parent.add("Sylhet");
        arrayList_parent.add("Barisal");
        arrayList_parent.add("Chittagong");

        arrayAdapter_parent = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, arrayList_parent);
        sp_parent.setAdapter(arrayAdapter_parent);

//        Child start
        arrayList_dhaka = new ArrayList<>();
        arrayList_dhaka.add("Dhaka city");
        arrayList_dhaka.add("Narayangonj");
        arrayList_dhaka.add("Gazipur");

        arrayList_khulna = new ArrayList<>();
        arrayList_khulna.add("Jashore");
        arrayList_khulna.add("Khulna city");

        arrayList_rajshahi=new ArrayList<>();
        arrayList_rajshahi.add("Natore");
        arrayList_rajshahi.add("Naogaon");

        arrayList_sylhet=new ArrayList<>();
        arrayList_sylhet.add("Habiganj");
        arrayList_sylhet.add("Moulvibazar");

        arrayList_barisal=new ArrayList<>();
        arrayList_barisal.add("Patuakhali");
        arrayList_barisal.add("Pirojpur");

        arrayList_chittagong=new ArrayList<>();
        arrayList_chittagong.add("Noakhali");
        arrayList_chittagong.add("Cumilla");


        sp_parent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item=adapterView.getItemAtPosition(i).toString();
                division = item;
                Toast.makeText(PostAService.this, "Division: "+item, Toast.LENGTH_SHORT).show();

                if(i == 0){
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_dhaka);
                }
                if(i == 1){
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_khulna);
                }
                if(i == 2){
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_rajshahi);
                }
                if(i == 3){
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_sylhet);
                }
                if(i == 4){
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_barisal);
                }
                if(i == 5){
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_chittagong);
                }
                sp_child.setAdapter(arrayAdapter_child);


                sp_child.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                        String item=parent.getItemAtPosition(position).toString();
                        district = item;
                        Toast.makeText(PostAService.this, "District: "+item, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



//        Child end

        SubmitSave = findViewById(R.id.btnSubmit);


        //        --- need for debug in case of child section error found
//        sp_parent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                if(parent.getItemAtPosition(position).equals("Select Country")){
//                    //Do Nothing
//
//                }
//                else{
//                    String item=parent.getItemAtPosition(position).toString();
//                    division = item;
//                    Toast.makeText(MainActivity.this, "Selected: "+item, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });




        SubmitSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                String name1 = sharedPreferences.getString("user_name", "0");
                String profession = serviceType;
                String email = sharedPreferences.getString("user_email", "0");
                String userServicedetails = UserServiceDetails.getText().toString();
                String useraddress1 = UserAddress1.getText().toString();
//                String contact = UserContact.getText().toString();
//                String comment = UserComment.getText().toString();
                if (userServicedetails.isEmpty()) {
                    Toast.makeText(PostAService.this, "Pleas fill service details field", Toast.LENGTH_SHORT).show();
                } else if (useraddress1.isEmpty()) {
                    Toast.makeText(PostAService.this, "Please fill the address details field", Toast.LENGTH_SHORT).show();
                } else {
//                    Colector += "Service type " + serviceType + "\n";
                    Colector += userServicedetails + "\n";
                    Colector += "Division " + division + "\n";
                    Colector += "District " + district + "\n";
                    Colector += useraddress1 + "\n";

                    objPostAService = new ObjPostAService(name1, profession, email, userServicedetails, useraddress1, division, district);
                    addPostToDB();

                    /*if (html.isChecked()){
                        Colector+="HTML"+"\n";
                        if (css.isChecked()){
                            Colector+="CSS"+"\n";
                        }
                        if (php.isChecked()){
                            Colector+="PHP"+"\n";
                        }
                    }*/
                    Toast.makeText(PostAService.this, "Please wait...\n\n" + Colector, Toast.LENGTH_SHORT).show();
                    Colector = "";
                }
            }
        });
    }

    public void addPostToDB(){
//        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
//        String email = sharedPreferences.getString("user_email", "0");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(objTableManagement.tbl_post_a_service)
                .document()
                .set(objPostAService)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            updatePostQuota();
                        } else {
                            Toast.makeText(PostAService.this, "Submission failed. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void updatePostQuota() {
//        Updating both cloud and local db
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String email = sharedPreferences.getString("user_email", "0");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(objTableManagement.tbl_user)
                .document(email)
                .update("post_quota", FieldValue.increment(-1)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        int temp = sharedPreferences.getInt("user_post_quota", 0);
                        temp--;

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("user_post_quota", temp);
                        editor.apply();

                        Toast.makeText(PostAService.this, "Submission successful. Please wait for admin approval to publish.", Toast.LENGTH_LONG).show();
                        goToHome();
                    }
                });
    }

    public void goToHome() {
        Intent intent = new Intent(PostAService.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



}