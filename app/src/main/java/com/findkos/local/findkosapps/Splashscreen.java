package com.findkos.local.findkosapps;

/**
 * Created by Sulalah Rugaya on 2/18/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.widget.ImageView;




public class Splashscreen extends Activity {

    ImageView ivBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        ivBackground = (ImageView) findViewById(R.id.ivBackground);

        gantiHalaman();
    }
    void gantiHalaman(){

        //splashscreen
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                Intent intent = new Intent(Splashscreen.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        }, 1000);


    }
}