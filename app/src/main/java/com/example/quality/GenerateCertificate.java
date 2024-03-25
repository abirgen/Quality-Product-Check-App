package com.example.quality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GenerateCertificate extends AppCompatActivity {

    TextView txtStudentName;
    Intent intent;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_certificate);

        database = DatabaseUtil.getDatabase();

        txtStudentName = (TextView) findViewById(R.id.student_name);

        intent = getIntent();
        String name = intent.getStringExtra("cert_name");

        myRef = database.getReference("certificates").child(name);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                txtStudentName.setText(dataSnapshot.child("lastName").getValue() + " " + dataSnapshot.child("firstName").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
