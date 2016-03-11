package com.findkos.local.findkosapps;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sulalah Rugaya on 2/21/2016.
 */
public class ForgotPass extends Activity implements View.OnClickListener {
    EditText etEmail;
    TextView tvEnter, tvOpps, tvWrong;
    String editTextString;
    ImageView ivSignUp, ivWrong, ivEmail, ivSimbol;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pass);
        etEmail = (EditText) findViewById(R.id.etEmail);
        tvEnter = (TextView) findViewById(R.id.tvEnter);
        ivSignUp = (ImageView) findViewById(R.id.ivSignUp);
        ivSignUp.setOnClickListener(this);
        ivEmail = (ImageView) findViewById(R.id.ivEmail);
        ivEmail.setOnClickListener(this);

        Typeface font2 = Typeface.createFromAsset(this.getAssets(), "fonts/FiraSans-Light.otf");
        etEmail.setTypeface(font2);
        tvEnter.setTypeface(font2);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivEmail:
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog1);
                dialog.show();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = 900;
//                  lp.height = 900;
                dialog.getWindow().setAttributes(lp);
                lp.dimAmount = 0.0f;
                Typeface font2 = Typeface.createFromAsset(this.getAssets(), "fonts/FiraSans-Light.otf");
                tvOpps = (TextView) dialog.findViewById(R.id.tvOpps);
                tvWrong = (TextView) dialog.findViewById(R.id.tvWrong);
                ivSimbol = (ImageView) dialog.findViewById(R.id.ivSimbol);
                ivWrong = (ImageView) dialog.findViewById(R.id.ivWrong);
                tvOpps.setTypeface(font2);
                tvWrong.setTypeface(font2);
                if (etEmail.getText().toString().matches("")) {
                    tvOpps.setText("Opps..");
                    tvWrong.setText("Email required");
                    ivSimbol.setImageResource(R.drawable.alert_error);
                    ivWrong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {
                    if (!isEmailValid(etEmail.getText().toString())) {
                        tvOpps.setText("Opps..");
                        tvWrong.setText("Invalid Email");
                        ivSimbol.setImageResource(R.drawable.alert_error);
                        ivWrong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                    } else if (etEmail.getText().toString().equals("lula@gmail.com")) {
                        tvOpps.setText("Yeay!");
                        tvWrong.setText("Check your email for further step");
                        ivSimbol.setImageResource(R.drawable.alert_correct);
                        ivWrong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    } else {
                        tvOpps.setText("Opps..");
                        tvWrong.setText("We can't find your email");
                        ivSimbol.setImageResource(R.drawable.alert_error_2);
                        ivWrong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                }

                break;
            case R.id.ivSignUp:
                Intent intent = new Intent(ForgotPass.this, SignupActivity.class);
                startActivity(intent);
//                finish();
                break;

            default:
                break;

        }

    }
}
