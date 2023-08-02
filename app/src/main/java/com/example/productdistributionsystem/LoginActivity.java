package com.example.productdistributionsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    //declaring the variables
    private TextInputEditText editEmail;
    private TextInputEditText editPassword;
    private Button loginBtn;
    private TextView forgotPassword;
    private ProgressBar progressBar;
    private TextView notRegistered;
    private ProgressDialog processDialog;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //finding views by Id
        editEmail=findViewById(R.id.editEmail);
        editPassword=findViewById(R.id.editPassword);
        loginBtn=findViewById(R.id.loginBtn);
        forgotPassword=findViewById(R.id.forgotPassword);
        progressBar=findViewById(R.id.progressBar);
        notRegistered=findViewById(R.id.notRegistered);

        progressBar.setVisibility(View.GONE);

        //creating the object of FirebaseAuth class
        auth = FirebaseAuth.getInstance();
        processDialog = new ProgressDialog(this);

        //action to perform when login button is clicked
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=editEmail.getText().toString();
                String password=editPassword.getText().toString();

                if(email.isEmpty()){
                    editEmail.setError("It's Empty");
                    editEmail.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    editPassword.setError("It's Empty");
                    editPassword.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editEmail.setError("Not a valid email Address");
                    editPassword.requestFocus();
                    return;
                }

                if (password.length() < 8) {
                    editPassword.setError("Less length");
                    editPassword.requestFocus();
                    return;
                }


                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                    validate(email, password);
                }
            }
        });


        //action to perform when forgotPassword textView is clicked
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

        //action to perform when notRegistered textView is clicked
        notRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });




    }

    public void validate(String userEmail, String userPassword){


        processDialog.setMessage("................Please Wait.............");
        processDialog.show();

        //using the firebase code to sign in with email and password
        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    processDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this,"Login Failed", Toast.LENGTH_SHORT).show();
                    processDialog.dismiss();
                }
            }
        });
    }

    public void resetPassword(){
        final String resetEmail = editEmail.getText().toString();

        if (resetEmail.isEmpty()) {
            editEmail.setError("It's empty");
            editEmail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        //using the firebase code to send email for resetting the password
        auth.sendPasswordResetEmail(resetEmail)

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }

                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}