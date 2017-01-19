package com.cicioflaviu.wikicar.wikicar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener{

    private Button registerBtn;
    private EditText emailTxt;
    private EditText passwordTxt;
    private TextView signInTextView;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        progressDialog = new ProgressDialog(this);
        registerBtn = (Button) findViewById(R.id.btnRegister);
        emailTxt = (EditText) findViewById(R.id.editTextEmail);
        passwordTxt = (EditText) findViewById(R.id.editTextPassword);
        signInTextView = (TextView) findViewById(R.id.txtViewSignIn);
        registerBtn.setOnClickListener(this);
        signInTextView.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();

//        if(firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser() != null){
//            finish();
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        }
    }

    @Override
    public void onClick(View v) {
        if(v == registerBtn){
            registerUser();
        }
        else if(v == signInTextView){
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }

    private void registerUser(){
        String email = emailTxt.getText().toString().trim();
        String password = passwordTxt.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            //email is empty;
            Toast.makeText(this, "Please enter email.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty;
            Toast.makeText(this, "Please enter password.", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(AuthActivity.this, "Registered succesfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AuthActivity.this, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
