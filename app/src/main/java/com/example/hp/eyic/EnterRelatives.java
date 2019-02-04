package com.example.hp.eyic;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnterRelatives extends AppCompatActivity {

    EditText rname, rphone;
    CardView register;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_relatives);

        rname = (EditText) findViewById(R.id.rname);
        rphone = (EditText) findViewById(R.id.rphone);
        register = (CardView) findViewById(R.id.cardView);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();

//        Intent i = getIntent();
//        Bundle b = i.getExtras();
//        final String uid = b.getString("CurrentUser");
        final String uid = mAuth.getCurrentUser().getUid();
        //final String uid = i.getStringExtra("CurrentUser");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = rname.getText().toString().trim();
                final String phone = rphone.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Enter phone no.!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Relative r = new Relative(name, phone);
                databaseReference.child(uid).child("Relatives").push().setValue(r);
                showDialogBox();
            }
        });
    }

    public void showDialogBox(){

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Add more emergency contacts??");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                        @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                rname.setText(null);
                                rphone.setText(null);

                            }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent i  = new Intent(EnterRelatives.this, Dashboard.class);
                startActivity(i);
            }
        });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}