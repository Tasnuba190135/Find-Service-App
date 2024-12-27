package EditProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.find_service.MainActivity;
import com.example.find_service.R;
import com.example.find_service.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

public class UserEditProfile extends AppCompatActivity {
    Spinner sp_profession, sp_parent, sp_child;
    boolean invalid = false;
    String Colector = "", email01;
    String profession = "0", division = "0", district = "0";
    TextView txtalertName;
    EditText userFullName, userEmail, userPassword, reEnterUserPassword, userContact, userAddress, userAbout;
    Button SubmitSave;
    RadioButton Malebtn, Femalbtn;

    View agreement01;

    //    Required object for sign up form to db
    public ObjRegistration objRegistration;
    private ObjTableManagement table = new ObjTableManagement();

    //    For password vaildation start
    String regex = "^(?=.*[a-z])(?=." + "*[A-Z])(?=.*\\d)" + "(?=.*[-+_!@#$%^&*., ?]).+$";
    //    Compile the ReGex
    Pattern p = Pattern.compile(regex);

//    For password vaidation end

    List<String> arrayList_profession;
    ArrayAdapter<String> arrayAdapter_profession;

    List<String> arrayList_parent;
    ArrayAdapter<String> arrayAdapter_parent;
    List<String> arrayList_default, arrayList_dhaka, arrayList_khulna, arrayList_rajshahi, arrayList_sylhet, arrayList_chittagong, arrayList_barisal;
    ArrayAdapter<String> arrayAdapter_child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_profile);



        userFullName = findViewById(R.id.UserFullName);
//        userEmail = findViewById(R.id.UserEmail);
        userPassword = findViewById(R.id.UserPassword);
//        reEnterUserPassword = findViewById(R.id.ReEnterUserPassword);
        userContact = findViewById(R.id.UserContactNo);
        userAddress = findViewById(R.id.UserAddress);
        userAbout = findViewById(R.id.UserAbout);

        SubmitSave = findViewById(R.id.btnSubmit);

        getDataFromDB();

    }

    private void getDataFromDB() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        email01 = sharedPreferences.getString("user_email", "0");
//        String email01 = "a@gmail.com";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tbl_user")
                .document(email01)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            objRegistration = documentSnapshot.toObject(ObjRegistration.class);
                            processForm();
                        } else {
                            Toast.makeText(UserEditProfile.this, "Error. Try again.", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(UserEditProfile.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserEditProfile.this, "Connection error. Try again.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(UserEditProfile.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
    }


    private void processForm() {
        userFullName.setText(objRegistration.getName());
        userContact.setText(objRegistration.getContact());
        userAddress.setText(objRegistration.getAddressDetails());
        userAbout.setText(objRegistration.getAbout());

        sp_profession = (Spinner) findViewById(R.id.SpProfession);
        arrayList_profession = new ArrayList<>();
        arrayList_profession.add(objRegistration.getProfession());
        arrayList_profession.add("Computer Software Engineer");
        arrayList_profession.add("Computer Hardware Engineer");
        arrayList_profession.add("Electrician");
        arrayList_profession.add("Plumber");
        arrayList_profession.add("Home wall painter");
        arrayList_profession.add("Tutor");
        arrayList_profession.add("Other");

        arrayAdapter_profession = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_profession);
        sp_profession.setAdapter(arrayAdapter_profession);

//        sp_profession.setSelection(2);

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
        arrayList_parent.add(objRegistration.getDivision());
        arrayList_parent.add("Dhaka");
        arrayList_parent.add("Khulna");
        arrayList_parent.add("Rajshahi");
        arrayList_parent.add("Sylhet");
        arrayList_parent.add("Barisal");
        arrayList_parent.add("Chittagong");

        arrayAdapter_parent = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_parent);
        sp_parent.setAdapter(arrayAdapter_parent);

//        sp_parent.setSelection(2);

//        Child start

        arrayList_default = new ArrayList<>();
        arrayList_default.add(objRegistration.getDistrict());

        arrayList_dhaka = new ArrayList<>();
        arrayList_dhaka.add("Dhaka city");
        arrayList_dhaka.add("Narayangonj");
        arrayList_dhaka.add("Gazipur");

        arrayList_khulna = new ArrayList<>();
        arrayList_khulna.add("Jashore");
        arrayList_khulna.add("Khulna city");

        arrayList_rajshahi = new ArrayList<>();
        arrayList_rajshahi.add("Natore");
        arrayList_rajshahi.add("Naogaon");

        arrayList_sylhet = new ArrayList<>();
        arrayList_sylhet.add("Habiganj");
        arrayList_sylhet.add("Moulvibazar");

        arrayList_barisal = new ArrayList<>();
        arrayList_barisal.add("Patuakhali");
        arrayList_barisal.add("Pirojpur");

        arrayList_chittagong = new ArrayList<>();
        arrayList_chittagong.add("Noakhali");
        arrayList_chittagong.add("Cumilla");


        sp_parent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                division = item;
//                Toast.makeText(SignUp.this, "Division: "+item, Toast.LENGTH_SHORT).show();

                if (i == 0) {
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_default);
                }
                if (i == 1) {
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_dhaka);
                }
                if (i == 2) {
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_khulna);
                }
                if (i == 3) {
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_rajshahi);
                }
                if (i == 4) {
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_sylhet);
                }
                if (i == 5) {
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_barisal);
                }
                if (i == 6) {
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


//        Child


        SubmitSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userFullName.getText().toString();
                String password = userPassword.getText().toString();
                String contact = userContact.getText().toString();
                String address = userAddress.getText().toString();
                String about = userAbout.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(UserEditProfile.this, "Please fill up full name", Toast.LENGTH_SHORT).show();
                } else if (contact.isEmpty()) {
                    Toast.makeText(UserEditProfile.this, "Please fill the Contact field", Toast.LENGTH_SHORT).show();
                } else if (contact.length() != 11) {
                    Toast.makeText(UserEditProfile.this, "Phone number have 11 digits start with 0", Toast.LENGTH_SHORT).show();
                } else if (profession == "0") {
                    Toast.makeText(UserEditProfile.this, "Please fill profession section ", Toast.LENGTH_SHORT).show();
                } else if (division == "0" || district == "0") {
                    Toast.makeText(UserEditProfile.this, "Please fill all field of address section ", Toast.LENGTH_SHORT).show();
                } else if (address.isEmpty()) {
                    Toast.makeText(UserEditProfile.this, "Address field is empty", Toast.LENGTH_SHORT).show();
                } else if (about.isEmpty() || about.length() < 10) {
                    Toast.makeText(UserEditProfile.this, "About field must contains at least 10 characters", Toast.LENGTH_SHORT).show();
                } else if( !password.equals(objRegistration.getPassword()) ){
                    Toast.makeText(UserEditProfile.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                } else {
                    objRegistration.setName(name);
                    objRegistration.setContact(contact);
                    objRegistration.setProfession(profession);
                    objRegistration.setAddressDetails(address);
                    objRegistration.setDistrict(district);
                    objRegistration.setDivision(division);
                    objRegistration.setAbout(about);

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection(table.tbl_user)
                            .document(email01)
                            .set(objRegistration)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(UserEditProfile.this, "Edit profile done..", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(UserEditProfile.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UserEditProfile.this, "Error. Try again.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(UserEditProfile.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            });
                }
            }
        });
    }
}