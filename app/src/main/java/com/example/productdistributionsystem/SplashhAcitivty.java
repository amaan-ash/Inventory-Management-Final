package com.example.productdistributionsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
//this activity is created to show user a splash screen
public class SplashhAcitivty extends AppCompatActivity {
    //declaring the variables
    private ImageView logo;
    private static int splashTimeOut=3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashh_acitivty);

        //finding views by id
        logo= findViewById(R.id.logo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashhAcitivty.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },splashTimeOut);

        //Creating the object of Animation class and Setting up the Animation
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mysplashanimation);

        logo.startAnimation(myanim);
}}