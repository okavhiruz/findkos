package com.findkos.local.findkosapps;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hp on 8/27/2015.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    EditText etUname, etPass;
    TextView tv1, tv2;
    ImageView ivLogin, ivFB, ivSignUp, ivWrong, ivSimbol;
    String title, text1;
    int icon, button;
    TextView tvOpps, tvWrong;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        etUname = (EditText) findViewById(R.id.etUname);
        etPass = (EditText) findViewById(R.id.etPass);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv2.setOnClickListener(this);
        ivLogin = (ImageView) findViewById(R.id.ivLogin);
        ivLogin.setOnClickListener(this);
        ivFB = (ImageView) findViewById(R.id.ivFB);
        ivSignUp = (ImageView) findViewById(R.id.ivSignUp);
        ivSignUp.setOnClickListener(this);

        //Typeface font = Typeface.createFromAsset(this.getAssets(), "FiraSans-Bold.otf");
        //Typeface font1 = Typeface.createFromAsset(this.getAssets(), "FiraSans-BoldItalic.otf");
        Typeface font2 = Typeface.createFromAsset(this.getAssets(), "fonts/FiraSans-Light.otf");
        Typeface font3 = Typeface.createFromAsset(this.getAssets(), "fonts/FiraSans-LightItalic.otf");
        //Typeface font4 = Typeface.createFromAsset(this.getAssets(), "FiraSans-Medium.otf");
        //Typeface font5 = Typeface.createFromAsset(this.getAssets(), "FiraSans-MediumItalic.otf");

        etUname.setTypeface(font2);
        etPass.setTypeface(font2);
        tv1.setTypeface(font3);
        tv2.setTypeface(font3);

    }


    public void custom_popup(String one, String two, int three) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog1);
        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        lp.width = (int) (metrics.widthPixels * 0.80);
//                    lp.height = 900;
        dialog.getWindow().setAttributes(lp);
        lp.dimAmount = 0.0f;
        Typeface font2 = Typeface.createFromAsset(this.getAssets(), "fonts/FiraSans-Light.otf");
        tvOpps = (TextView) dialog.findViewById(R.id.tvOpps);
        tvWrong = (TextView) dialog.findViewById(R.id.tvWrong);
        ivSimbol = (ImageView) dialog.findViewById(R.id.ivSimbol);
        ivWrong = (ImageView) dialog.findViewById(R.id.ivWrong);

        tvOpps.setTypeface(font2);
        tvWrong.setTypeface(font2);
        tvOpps.setText(one);
        tvWrong.setText(two);
        ivSimbol.setImageResource(three);
        ivWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivLogin:

                if ("admin".equals(etUname.getText().toString()) && "admin".equals(etPass.getText().toString())) {
                    Intent intent3 = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent3);
                } else {
                    title = "Opps..";
                    text1 = "Wrong Username or Password";
                    icon = R.drawable.alert_error;
                    custom_popup(title, text1, icon);
                }
                break;
            case R.id.tv2:
                Intent intent2 = new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(intent2);
//                finish();
                break;
            case R.id.ivSignUp:
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
//                finish();
                break;

            default:
                break;

        }


    }
}