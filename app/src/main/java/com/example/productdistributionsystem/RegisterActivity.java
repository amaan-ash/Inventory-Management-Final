package com.example.productdistributionsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


public class RegisterActivity extends AppCompatActivity {
    //declaring the variables

    private ImageView inventoryImage;
    private TextInputEditText editEmail;
    private TextInputEditText editPassword;
    private TextInputEditText confirmPass;
    private TextInputEditText departmentName;
    private Button registerBtn;
    private TextView alreadyRegistered;
    private ProgressBar progressBar;
    private ProgressDialog processDialog;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //finding the views by Id so that i can provide logic to it
        inventoryImage=findViewById(R.id.inventoryImage);
        editEmail=findViewById(R.id.editEmail);
        editPassword=findViewById(R.id.editPassword);
        confirmPass=findViewById(R.id.confirmPassword);
        departmentName=findViewById(R.id.departmentName);
        registerBtn=findViewById(R.id.registerBtn);
        alreadyRegistered=findViewById(R.id.alreadyRegistered);
        progressBar=findViewById(R.id.progressBar);


        //getting the instance of the FireBaseAuth class
        auth = FirebaseAuth.getInstance();

        progressBar.setVisibility(View.GONE);

        //action to perform when register button is clicked
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        //action to perform when already registered textView is clicked
        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });





    }

    private void registerUser() {
        final String email = editEmail.getText().toString();
        String password = editPassword.getText().toString().trim();
        String confirmPassword =confirmPass.getText().toString().trim();
        final String deptName = departmentName.getText().toString().trim();


        if (email.isEmpty()) {
           editEmail.setError("It's empty");
            editEmail.requestFocus();
            return;
        }
        if (deptName.isEmpty()) {
            departmentName.setError("It's Empty");
            departmentName.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
           editEmail.setError("Not a valid email Address");
           editPassword.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editPassword.setError("Its empty");
            editPassword.requestFocus();
            return;
        }

        if (password.length() < 8) {
           editPassword.setError("Less length");
           editPassword.requestFocus();
            return;
        }
        if(!password.equals(confirmPassword)){
            confirmPass.setError("Password Do not Match");
            confirmPass.requestFocus();
            return;
        }



        progressBar.setVisibility(View.VISIBLE);

        //to register the user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            final User user = new User(
                                    deptName,
                                    email,
                                    password

                            );

                            //important to retrieve data and send data based on user email
                            FirebaseUser userNameInFirebase = auth.getCurrentUser();
                            String UserID=userNameInFirebase.getEmail();
                            String resultEmail = UserID.replace(".","");

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(resultEmail).child("UserDetails")
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressBar.setVisibility(View.GONE);
                                            if (task.isSuccessful()) {

                                                editEmail.setText("");
                                                editPassword.setText("");
                                                confirmPass.setText("");
                                                departmentName.setText("");

                                                Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                            }

                                            else {
                                                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }

                        else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

}