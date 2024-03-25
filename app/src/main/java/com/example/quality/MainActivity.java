package com.example.quality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtMatricNum;
    Button btnVerifyCert;
    TextView txtCertAvailable;

    TextView txtAdmin;
TextView  logout;
    FirebaseDatabase database;
    DatabaseReference myRef;

    View view;

    ArrayList<Certificate> certificates;
    Certificate certificate;

    String availableCertName;
    String matNum;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        database = DatabaseUtil.getDatabase();
        myRef = database.getReference("certificates");

        view = (View) findViewById(R.id.mainActivity_layout);

        edtMatricNum = (EditText) findViewById(R.id.edt_matric_number);
        matNum = edtMatricNum.getText().toString().toUpperCase();

        btnVerifyCert = (Button) findViewById(R.id.btn_verify);
        txtCertAvailable = (TextView) findViewById(R.id.txt_cert_available_info);

        txtAdmin = (TextView) findViewById(R.id.txt_admin);
        logout=(TextView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, SignIn.class));

            }
        });
        txtAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AdminActivity.class));
            }
        });

        certificates = new ArrayList<>();
        certificate = new Certificate();

        btnVerifyCert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnVerifyCert.getText().equals("Verify")) {
                    if (edtMatricNum.getText().toString().isEmpty()) {
                        Snackbar.make(view, "Please enter a product code Number", Snackbar.LENGTH_LONG).show();
                    } else {

                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                    certificate = ds.getValue(Certificate.class);
                                    certificates.add(certificate);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    for (int i = 0; i < certificates.size(); i++) {
                        Certificate cert = certificates.get(i);

                        if (edtMatricNum.getText().toString().toUpperCase().equals(cert.getMatNumber().toUpperCase())) {
                            availableCertName = cert.getFirstName();

                            Log.d("debugger", "Mat no: " + edtMatricNum.getText().toString().toUpperCase());
                            Log.d("debugger", "Mat no: " + cert.getMatNumber().toUpperCase());

                            txtCertAvailable.setText("Product Found Quality");
                            txtCertAvailable.setTextColor(getResources().getColor(com.google.android.material.R.color.design_default_color_primary));
                            btnVerifyCert.setText("View Certificate");

                            break;

                        } else {


                            Log.d("debugger", "Mat no: " + edtMatricNum.getText().toString().toUpperCase());
                            Log.d("debugger", "Mat no: " + cert.getMatNumber().toUpperCase());

                            txtCertAvailable.setText(R.string.no_certificate);
                            txtCertAvailable.setTextColor(getResources().getColor(R.color.red));
                            btnVerifyCert.setText("Verify");
                        }
                    }

                }
                else if (btnVerifyCert.getText().equals("View Certificate")){
                    Intent intent = new Intent(MainActivity.this, GenerateCertificate.class);
                    intent.putExtra("cert_name", availableCertName);
                    startActivity(intent);
                }
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        btnVerifyCert.setText("Verify");
    }
}
