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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hp on 8/27/2015.
 */
public class SignupActivity extends Activity implements View.OnClickListener {
    EditText etUname, etEmail;
    ImageView ivLogin, ivFB, ivSignUp, ivWrong, ivSimbol;
    String title, text1;
    int icon, button;
    TextView tvOpps, tvWrong;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        etUname = (EditText) findViewById(R.id.etUname);
        etEmail = (EditText) findViewById(R.id.etEmail);

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
        etEmail.setTypeface(font2);


    }

    Pattern pattern;
    Matcher matcher;

    public boolean isEmailValid(String email) {
        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;
        pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
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
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
//                finish();
                break;
            case R.id.ivSignUp:

                if (etEmail.getText().toString().matches("") && etUname.getText().toString().matches("")) {
                    title = "Opps..";
                    text1 = "Username and Email Required";
                    icon = R.drawable.alert_error;
                    custom_popup(title, text1, icon);

                } else if (etUname.getText().toString().matches("")) {
                    title = "Opps..";
                    text1 = "Username Required";
                    icon = R.drawable.alert_error;
                    custom_popup(title, text1, icon);
                } else if (etEmail.getText().toString().matches("")) {
                    title = "Opps..";
                    text1 = "Email Required";
                    icon = R.drawable.alert_error;
                    custom_popup(title, text1, icon);
                } else {
                    if (!isEmailValid(etEmail.getText().toString())) {
                        title = "Opps..";
                        text1 = "Invalid Email";
                        icon = R.drawable.alert_error;
                        custom_popup(title, text1, icon);

                    } else {
                        Intent intent2 = new Intent(SignupActivity.this, SignupFormActivity.class);
                        intent2.putExtra("email", etEmail.getText().toString());
                        intent2.putExtra("username", etUname.getText().toString());
                        startActivity(intent2);
//                finish();
                    }


                }


                break;
            default:
                break;

        }


    }
}