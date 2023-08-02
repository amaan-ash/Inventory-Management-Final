package com.example.productdistributionsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    //declaring the variables
    private Button loginButtonMain;
    private Button registerButtonMain;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //to set a layout file as UI for this java file
        setContentView(R.layout.activity_main);

        //getting the views from the layout file so that i can provide logic
        loginButtonMain=findViewById(R.id.loginButtonMain);
        registerButtonMain =findViewById(R.id.registerButtonMain);


        //first get the object of the FireBaseAuth Class
        auth = FirebaseAuth.getInstance();

        //get the current user
        FirebaseUser user = auth.getCurrentUser();

        //if the user is alrady logged in then destroy this activity and go to dashboard screen
        if(user != null){
            finish();
            startActivity(new Intent(MainActivity.this, DashBoardActivity.class));
        }

        //action to perform when login button is clicked
        loginButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

        //action to perform when register button is clicked
        registerButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

    }

}

