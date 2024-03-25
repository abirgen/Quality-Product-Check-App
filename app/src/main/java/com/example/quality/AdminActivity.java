package com.example.quality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    EditText edtFirstName;
    EditText edtLastName;
    EditText edtMatricNum;
    EditText edtDepartment;

    Button btnAddStudent;

    static FirebaseDatabase database;
    DatabaseReference myRef;

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        database = DatabaseUtil.getDatabase();

        view = (View) findViewById(R.id.admin_layout);

        edtFirstName = (EditText) findViewById(R.id.edt_first_name);
        edtLastName = (EditText) findViewById(R.id.edt_last_name);
        edtDepartment = (EditText) findViewById(R.id.edt_department);
        edtMatricNum = (EditText) findViewById(R.id.edt_mat_number);

        btnAddStudent = (Button) findViewById(R.id.btn_add_certificate);
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtDepartment.getText().toString().isEmpty() || edtFirstName.getText().toString().isEmpty()
                        || edtLastName.getText().toString().isEmpty()
                        || edtMatricNum.getText().toString().isEmpty()) {
                    Snackbar.make(view, "Please all fields are required", Snackbar.LENGTH_LONG).show();
                } else {

                    addNewCertificate(edtDepartment.getText().toString(),
                            edtFirstName.getText().toString(),
                            edtLastName.getText().toString(),
                            edtMatricNum.getText().toString().toUpperCase());

                    startActivity(new Intent(AdminActivity.this, MainActivity.class));
                    Toast.makeText(AdminActivity.this, "Certificate Added Successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

    }




    private void addNewCertificate (String department, String firstName,
                String lastName, String matNumber){

            //get reference to the medications key in the database
            DatabaseReference myRef = database.getReference("certificates");

            //create a new Medication object
            Certificate certificate = new Certificate(department, firstName, lastName, matNumber.toUpperCase());
            Map<String, Object> userValues = certificate.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put(firstName, userValues);

            //update the medications with the new values
            myRef.updateChildren(childUpdates);

        }

    }
