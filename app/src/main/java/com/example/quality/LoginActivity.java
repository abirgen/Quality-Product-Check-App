package com.example.quality;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername;
    EditText edtPassword;
    Button btnSignIn;

    View view;

    TextView txtCorrectDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        view = (View) findViewById(R.id.sign_in_layout);

        txtCorrectDetails = (TextView) findViewById(R.id.txt_correct_details);

        edtUsername = (EditText) findViewById(R.id.edt_username);
        edtPassword = (EditText) findViewById(R.id.edt_password);

        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUsername.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()){
                    Snackbar.make(view, "Please username and password is required", Snackbar.LENGTH_LONG).show();
                }else if ((edtUsername.getText().toString().equals("admin")) && (edtPassword.getText().toString().equals("admin"))){
//                    Snackbar.make(view, "Sign in successful", Snackbar.LENGTH_LONG).show();
//                    startActivity(new android.content.Intent(Login.this, Admin.class));
                }else {
                    txtCorrectDetails.setText("Invalid username or password");
                    txtCorrectDetails.setTextColor(getResources().getColor(com.google.android.material.R.color.design_default_color_primary));
                }
            }
        });

    }
}
