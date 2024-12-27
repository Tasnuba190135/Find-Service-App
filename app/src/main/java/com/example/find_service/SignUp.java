package com.example.find_service;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import input_form_objects.FirebaseDataBaseHelper1;
import input_form_objects.ObjRegistration;
import input_form_objects.ObjTableManagement;

public class SignUp extends AppCompatActivity {
    Spinner sp_profession, sp_parent, sp_child;
    boolean invalid = false;
    String Colector = "";
    String profession = "0", division = "0", district = "0";
    TextView txtalertName;
    EditText userFullName, userEmail, userPassword, reEnterUserPassword, userContact, userAddress, userAbout;
    Button SubmitSave;
    RadioButton Malebtn, Femalbtn;

    View agreement01;

//    Required object for sign up form to db
    private ObjRegistration objRegistration;
    private ObjTableManagement objTableManagement;

//    For password vaildation start
    String regex = "^(?=.*[a-z])(?=." + "*[A-Z])(?=.*\\d)" + "(?=.*[-+_!@#$%^&*., ?]).+$";
//    Compile the ReGex
    Pattern p = Pattern.compile(regex);

//    For password vaidation end

    List<String> arrayList_profession;
    ArrayAdapter<String> arrayAdapter_profession;

    List<String> arrayList_parent;
    ArrayAdapter<String> arrayAdapter_parent;
    List<String> arrayList_dhaka, arrayList_khulna,arrayList_rajshahi,arrayList_sylhet,arrayList_chittagong,arrayList_barisal;
    ArrayAdapter<String> arrayAdapter_child;

    /*CheckBox html,css,php;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userFullName = findViewById(R.id.UserFullName);
        userEmail = findViewById(R.id.UserEmail);
        userPassword = findViewById(R.id.UserPassword);
        reEnterUserPassword = findViewById(R.id.ReEnterUserPassword);
        userContact = findViewById(R.id.UserContactNo);
        userAddress = findViewById(R.id.UserAddress);
        userAbout = findViewById(R.id.UserAbout);
//        txtalertName = findViewById(R.id.userAlert);
        Malebtn = findViewById(R.id.Male);
        Femalbtn = findViewById(R.id.Female);
        /*html=findViewById(R.id.HTML);
        css=findViewById(R.id.CSS);
        php=findViewById(R.id.PHP);*/

        agreement01 = findViewById(R.id.agreement1);

        sp_profession = (Spinner) findViewById(R.id.SpProfession);
        arrayList_profession = new ArrayList<>();
        arrayList_profession.add("General User");
        arrayList_profession.add("Computer Software Engineer");
        arrayList_profession.add("Computer Hardware Engineer");
        arrayList_profession.add("Electrician");
        arrayList_profession.add("Plumber");
        arrayList_profession.add("Home wall painter");
        arrayList_profession.add("Tutor");
        arrayList_profession.add("Other");

        arrayAdapter_profession = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_profession);
        sp_profession.setAdapter(arrayAdapter_profession);

        sp_profession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                profession = item;
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

        arrayAdapter_parent = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_parent);
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
                String item = adapterView.getItemAtPosition(i).toString();
                division = item;
//                Toast.makeText(SignUp.this, "Division: "+item, Toast.LENGTH_SHORT).show();

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
                        String item = parent.getItemAtPosition(position).toString();
                        district = item;
//                        Toast.makeText(SignUp.this, "District: "+item, Toast.LENGTH_SHORT).show();
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


        agreement01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://terabox.com/s/1DmxCfuVe_91yDqSmscZ7Dg";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
//                Intent intent = new Intent(SignUp.this, MainActivity2.class);
//                startActivity(intent);
            }
        });


        SubmitSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userFullName.getText().toString();
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();
                String reEnterPassword = reEnterUserPassword.getText().toString();
                String gender = "0";
                Matcher m = p.matcher(password);
                String contact = userContact.getText().toString();
                String address = userAddress.getText().toString();
                String about = userAbout.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please fill up full name", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please fill email field", Toast.LENGTH_SHORT).show();
                } else if (!email.contains("@")) {
                    Toast.makeText(SignUp.this, "Email must conatains @", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty() || password.length() < 6 || m.matches() == false) {
                    Toast.makeText(SignUp.this, "Enter password in correct format", Toast.LENGTH_SHORT).show();
                } else if (!reEnterPassword.equals(password)) {
                    Toast.makeText(SignUp.this, "Password did not match", Toast.LENGTH_SHORT).show();
                } else if (contact.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please fill the Contact field", Toast.LENGTH_SHORT).show();
                } else if (contact.length() != 11) {
                    Toast.makeText(SignUp.this, "Phone number have 11 digits start with 0", Toast.LENGTH_SHORT).show();
                } else if (Malebtn.isChecked() == false && Femalbtn.isChecked() == false) {
                    Toast.makeText(SignUp.this, "Please fill gender ", Toast.LENGTH_SHORT).show();
                } else if (profession == "0") {
                    Toast.makeText(SignUp.this, "Please fill profession section ", Toast.LENGTH_SHORT).show();
                } else if (division == "0" || district == "0") {
                    Toast.makeText(SignUp.this, "Please fill all field of address section ", Toast.LENGTH_SHORT).show();
                } else if (address.isEmpty()) {
                    Toast.makeText(SignUp.this, "Address field is empty", Toast.LENGTH_SHORT).show();
                } else if (about.isEmpty() || about.length() < 10) {
                    Toast.makeText(SignUp.this, "About field must contains at least 10 characters", Toast.LENGTH_SHORT).show();
                } else {
                    if (Malebtn.isChecked() == true) {
                        gender = "Male";
                    } else {
                        gender = "Female";
                    }

//                    Creating object
                    objRegistration = new ObjRegistration(name, email, password, contact, gender, profession, district, division, address, about);
                    objTableManagement = new ObjTableManagement();
//                    Log.d(TAG, objRegistration.toString());

                    FirebaseDataBaseHelper1 dataBaseHelper1 = new FirebaseDataBaseHelper1();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection(objTableManagement.tbl_user).document(objRegistration.getEmail())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.getResult().exists()) {
                                        Toast.makeText(SignUp.this, "Account already available in given email. Please log in.", Toast.LENGTH_LONG).show();
//                                        Log.d(TAG, "not inserted. ac: "+accountAvailable[0]);
                                    } else {
                                        insertNewAccount();
                                    }
                                }
                            });
                    Toast.makeText(SignUp.this, "Submitted. please wait.", Toast.LENGTH_SHORT).show();

//                    Colector += name + "\n";
//                    Colector += email + "\n";
//                    Colector += password + "\n";
//                    Colector += contact + "\n";
//                    if (Malebtn.isChecked() == true) {
//                        Colector += "Male \n";
//                    } else {
//                        Colector += "Female \n";
//                    }
//
//                    Colector += "Profession " + profession +"\n";
//                    Colector += "Division" + division + "\n";
//                    Colector += "District" + district + "\n";
//
//                    Colector += about + "\n";
//                    if (html.isChecked()){
//                        Colector+="HTML"+"\n";
//                        if (css.isChecked()){
//                            Colector+="CSS"+"\n";
//                        }
//                        if (php.isChecked()){
//                            Colector+="PHP"+"\n";
//                        }
//                    }
//                    Toast.makeText(SignUp.this, "User Info \n" + Colector, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

//    ****** Insert into db START
    public void insertNewAccount(){
        FirebaseFirestore db2 = FirebaseFirestore.getInstance();
        db2.collection(objTableManagement.tbl_user).document(objRegistration.getEmail())
                .set(objRegistration)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Registration successful. Please wait for admin approval.", Toast.LENGTH_LONG).show();

//                            Now forwarding to homepage
                            Intent intent = new Intent(SignUp.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
//    ****** Insert into db END



}