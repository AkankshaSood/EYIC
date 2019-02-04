package com.example.hp.eyic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MedicalHistory extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String item;
    Spinner bg;
    EditText allergies,otherDetails;
    TextView buttonConti;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);
        allergies = (EditText)findViewById(R.id.allergy);
        bg = (Spinner)findViewById(R.id.spinner);
        otherDetails = (EditText)findViewById(R.id.history);
        buttonConti = (TextView) findViewById(R.id.continuee);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();

        String uid = mAuth.getCurrentUser().getUid();

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        List <String> categories = new ArrayList <String>();
        categories.add("A+");
        categories.add("A-");
        categories.add("B+");
        categories.add("B-");
        categories.add("O+");
        categories.add("O-");
        categories.add("AB+");
        categories.add("AB-");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        buttonConti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String allergy = allergies.getText().toString().trim();
                final String history = otherDetails.getText().toString().trim();
                final String bloodGroup = bg.getSelectedItem().toString().trim();

                Medical m = new Medical(bloodGroup, allergy, history);
                databaseReference.child(uid).child("MedicalHistory").push().setValue(m);

                Intent i = new Intent(MedicalHistory.this, EnterRelatives.class);
                startActivity(i);

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}
