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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button signIn;
    private EditText emailTxt;
    private EditText passwordTxt;
    private TextView textViewSignUp;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signIn = (Button) findViewById(R.id.btnSignIn);
        emailTxt = (EditText) findViewById(R.id.editTextEmail);
        firebaseAuth = FirebaseAuth.getInstance();
        passwordTxt = (EditText) findViewById(R.id.editTextPassword);
        textViewSignUp = (TextView) findViewById(R.id.txtViewSignUp);
        progressDialog = new ProgressDialog(this);
        signIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
//        if(firebaseAuth.getCurrentUser() != null){
//            finish();
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        }
    }

    @Override
    public void onClick(View v) {
        if(v == signIn){
            userLogin();
        } else if(v ==textViewSignUp){
            finish();
            startActivity(new Intent(this, AuthActivity.class));
        }
    }

    private void userLogin(){
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
        progressDialog.setMessage("Signing in user...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(), "Sign in failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
