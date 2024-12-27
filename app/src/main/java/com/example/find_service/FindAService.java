package com.example.find_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindAService extends AppCompatActivity {

    Spinner sp_profession, sp_parent, sp_child;

    String Colector = "";
    String profession="0", division="0", district="0";
    Button SubmitSave;

    List<String> arrayList_profession;
    ArrayAdapter<String> arrayAdapter_profession;

    List<String> arrayList_parent;
    ArrayAdapter<String> arrayAdapter_parent;
    List<String> arrayList_all, arrayList_dhaka, arrayList_khulna,arrayList_rajshahi,arrayList_sylhet,arrayList_chittagong,arrayList_barisal;
    ArrayAdapter<String> arrayAdapter_child;

    /*CheckBox html,css,php;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_aservice);

        sp_profession = (Spinner) findViewById(R.id.SpProfession);
        arrayList_profession = new ArrayList<>();
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
                String item=adapterView.getItemAtPosition(i).toString();
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

//        arrayList_parent.add("All over country");

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

//        arrayList_all = new ArrayList<>();
//        arrayList_all.add("All");

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
//                Toast.makeText(SignUp.this, "Division: "+item, Toast.LENGTH_SHORT).show();

                if(i == 0){
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_dhaka);
                }
                if(i == 1){
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_khulna);
                }
//                if(i == 2){
//                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_all);
//                }
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
        SubmitSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(profession=="0"){
                    Toast.makeText(FindAService.this, "Please fill profession section ", Toast.LENGTH_SHORT).show();
                } else if(division=="0" || district=="0"){
                    Toast.makeText(FindAService.this, "Please fill all field of address section ", Toast.LENGTH_SHORT).show();
                } else {
                    Colector += "Profession " + profession +"\n";
                    Colector += "Division" + division + "\n";
                    Colector += "District" + district + "\n";

//                    Toast.makeText(FindAService.this, "" + Colector, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FindAService.this, ViewPosts.class);
                    intent.putExtra("profession", profession);
                    intent.putExtra("division", division);
                    intent.putExtra("district", district);
                    startActivity(intent);

                }
            }
        });



    }
}