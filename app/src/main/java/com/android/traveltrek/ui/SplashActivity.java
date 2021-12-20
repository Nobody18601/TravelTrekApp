package com.android.traveltrek.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.android.traveltrek.MainActivity;
import com.android.traveltrek.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        Thread thread = new Thread(){
            @Override
            public void run() {
                  try{
                       sleep(4000);
                  }
                  catch(Exception e){
                      e.printStackTrace();
                  }
                  finally {
                     Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                     startActivity(intent);
                  }

            }
        };

        thread.start();
    }
}