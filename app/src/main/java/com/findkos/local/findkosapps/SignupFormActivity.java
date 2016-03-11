package com.findkos.local.findkosapps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.findkos.local.findkosapps.utils.MyImageHandler;
import com.findkos.local.findkosapps.view.DatePickerFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

/**
 * Created by Sulalah Rugaya on 2/21/2016.
 */
public class SignupFormActivity extends FragmentActivity implements View.OnClickListener {
    EditText etFname, etUname, etEmail, etPass, etRetypePass;
    ImageView ivMale, ivFemale, ivSignUp, ivWrong, ivSimbol, ivAva, ivUpload, ivIcon, ivNO, ivYES;
    RelativeLayout layAva;
    TextView tvOpps, tvWrong, etBirth, tvTanya, tvChoose;
    String title, text1;
    int icon, button;
    final Context context = this;

    String gender = "M", email, username;
    View bingkai, bingkai2;

    public static int CAMERA = 100;
    public static int GALLERY = 200;
    public static int CROP = 300;
    public static int CAMERA_CHALLENGES = 400;
    public static int GALLERY_CHALLENGES = 500;
    public static int CROP_CHALLENGES = 600;
    public static String path = "";
    String hostpage = "";
    String tglborn;
    boolean gantiava = false;

    int year = 0, month = 0, day = 0;
    DatePickerFragment datePickerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_form_activity);
        etFname = (EditText) findViewById(R.id.etFname);
        etUname = (EditText) findViewById(R.id.etUname);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etBirth = (TextView) findViewById(R.id.etBirth);
        etPass = (EditText) findViewById(R.id.etPass);
        etRetypePass = (EditText) findViewById(R.id.etRetypePass);
        ivUpload = (ImageView) findViewById(R.id.ivUpload);
        ivMale = (ImageView) findViewById(R.id.ivMale);
        ivFemale = (ImageView) findViewById(R.id.ivFemale);
        ivSignUp = (ImageView) findViewById(R.id.ivSignUp);
        ivAva = (ImageView) findViewById(R.id.avatar);
        layAva = (RelativeLayout) findViewById(R.id.layAva);
        bingkai = (View) findViewById(R.id.bingkai);
        bingkai2 = (View) findViewById(R.id.bingkai2);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            email = extras.getString("email");
            username = extras.getString("username");
        }
        etEmail.setText(email);
        etUname.setText(username);

        ivMale.setOnClickListener(this);
        ivFemale.setOnClickListener(this);

        ivUpload.setOnClickListener(this);
        MyImageHandler myImageHandler = new MyImageHandler();
        Bitmap roundAva = BitmapFactory.decodeResource(SignupFormActivity.this.getResources(), R.drawable.default_female);
        ivAva.setImageBitmap(myImageHandler.circleBitmap(roundAva));

        etBirth.setOnClickListener(this);
        ivSignUp.setOnClickListener(this);

        Typeface font2 = Typeface.createFromAsset(this.getAssets(), "fonts/FiraSans-Light.otf");
        etFname.setTypeface(font2);
        etUname.setTypeface(font2);
        etEmail.setTypeface(font2);
        etBirth.setTypeface(font2);
        etPass.setTypeface(font2);
        etRetypePass.setTypeface(font2);

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
            case R.id.ivSignUp:
                if (etFname.getText().toString().equalsIgnoreCase("")
                        || etUname.getText().toString().equalsIgnoreCase("")
                        || etEmail.getText().toString().equalsIgnoreCase("")
                        || etPass.getText().toString().equalsIgnoreCase("")
                        || etRetypePass.getText().toString().equalsIgnoreCase("")
                        || path.equalsIgnoreCase("")) {
                    title = "Opps..";
                    text1 = "There is an empty form";
                    icon = R.drawable.alert_error;
                    custom_popup(title, text1, icon);

                } else {
                    String strPass1 = etPass.getText().toString();
                    String strPass2 = etRetypePass.getText().toString();
                    if (!strPass1.equals(strPass2)) {
                        title = "Opps..";
                        text1 = "Password doesn't match";
                        icon = R.drawable.alert_error;
                        custom_popup(title, text1, icon);

                    } else if (etPass.getText().toString().trim().length() < 6) {
                        title = "Opps..";
                        text1 = "Password required 6 letters or more";
                        icon = R.drawable.alert_error;
                        custom_popup(title, text1, icon);

                    } else {
                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.custom_dialog2);
                        dialog.show();
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog.getWindow().getAttributes());
                        DisplayMetrics metrics = getResources().getDisplayMetrics();
                        lp.width = (int) (metrics.widthPixels * 0.80);
//                    lp.height = 900;
                        dialog.getWindow().setAttributes(lp);
                        lp.dimAmount = 0.0f;
                        Typeface font2 = Typeface.createFromAsset(this.getAssets(), "fonts/FiraSans-Light.otf");
                        tvChoose = (TextView) dialog.findViewById(R.id.tvChoose);
                        tvTanya = (TextView) dialog.findViewById(R.id.tvTanya);
                        ivIcon = (ImageView) dialog.findViewById(R.id.ivIcon);
                        ivYES = (ImageView) dialog.findViewById(R.id.ivYES);
                        ivNO = (ImageView) dialog.findViewById(R.id.ivNO);
                        tvChoose.setTypeface(font2);
                        tvTanya.setTypeface(font2);
                        tvChoose.setText("Warning");
                        tvTanya.setText("Are you sure want to proceed");
                        ivIcon.setImageResource(R.drawable.alert_warning);
                        ivYES.setImageResource(R.drawable.bt_alert_yes);
                        ivNO.setImageResource(R.drawable.bt_alert_no);

                        ivYES.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SignupFormActivity.this, LoginActivity.class);
                                Toast.makeText(SignupFormActivity.this, "Berhasil Signup", Toast.LENGTH_SHORT).show();
                                intent.putExtra("uname", etUname.getText().toString());
                                intent.putExtra("photoPath", path);
                                startActivity(intent);
                                finish();
                            }
                        });
                        ivNO.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });


                        dialog.show();



                    }
                }
                break;
            case R.id.ivFemale:
                if (path.equalsIgnoreCase("")) {
                    MyImageHandler myImageHandler = new MyImageHandler();
                    Bitmap roundAva = BitmapFactory.decodeResource(SignupFormActivity.this.getResources(), R.drawable.default_female);
                    ivAva.setImageBitmap(myImageHandler.circleBitmap(roundAva));
                    ivFemale.setImageResource(R.drawable.bt_female_selected);
                    ivMale.setImageResource(R.drawable.bt_male_idle);

                    gender = "F";
                } else {
                    ivFemale.setImageResource(R.drawable.bt_female_selected);
                    ivMale.setImageResource(R.drawable.bt_male_idle);
                    gender = "F";
                }
                break;
            case R.id.ivMale:
                if (path.equalsIgnoreCase("")) {
                    MyImageHandler myImageHandler = new MyImageHandler();
                    Bitmap roundAva = BitmapFactory.decodeResource(SignupFormActivity.this.getResources(), R.drawable.default_male);
                    ivAva.setImageBitmap(myImageHandler.circleBitmap(roundAva));
                    ivMale.setImageResource(R.drawable.bt_male_selected);
                    ivFemale.setImageResource(R.drawable.bt_female_idle);
                    gender = "M";
                } else {
                    ivMale.setImageResource(R.drawable.bt_male_selected);
                    ivFemale.setImageResource(R.drawable.bt_female_idle);
                    gender = "M";
                }
                break;
            case R.id.ivUpload:
                selectImage();
                break;
            case R.id.etBirth:
                Log.e("Tag =", "" + v.getTag());
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                datePickerFragment = new DatePickerFragment(year, month, day);
                datePickerFragment.show(getSupportFragmentManager(), "datepicker");
                datePickerFragment
                        .setListener(new DatePickerFragment.onDateInterFace() {

                            @Override
                            public void onDateListener(String valueClicked,
                                                       String toValue) {
                                // TODO Auto-generated method stub
                                etBirth.setText(valueClicked);
                                tglborn = toValue;
                                Log.e("tgl Lahir", "" + tglborn);

                            }
                        });
                break;

            default:
                break;
        }
    }

    public void selectImage() {
        final String[] items = new String[]{"Take a picture", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SignupFormActivity.this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SignupFormActivity.this,
                android.R.layout.select_dialog_item, items);
        builder.setTitle("Choose your fav pic");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment
                            .getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, CAMERA);
                } else {
                    Intent choosePictureIntent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    choosePictureIntent.setType("image/*");
                    startActivityForResult(
                            choosePictureIntent, GALLERY);
                }
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == -1) {
            if (requestCode == CAMERA) {
                File f = new File(Environment.getExternalStorageDirectory()
                        .toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }

                try {
                    Bitmap bm;
                    BitmapFactory.Options btmapOptions = new BitmapFactory.Options();

                    bm = getImage1(f.getAbsolutePath());

                    // bm = Bitmap.createScaledBitmap(bm, 70, 70, true);

                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Strepsils" + File.separator + "default";
                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    f.delete();
                    OutputStream fOut = null;
                    File file = new File(path, String.valueOf(System
                            .currentTimeMillis()) + ".jpg");
                    try {
                        file.createNewFile();
                        fOut = new FileOutputStream(file);
                        bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                        fOut.flush();
                        fOut.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String tempPath = file.getAbsolutePath();
                    Intent intentCamera = new Intent(SignupFormActivity.this, CropImagePhoto.class);
                    intentCamera.putExtra("path", tempPath);
                    startActivityForResult(intentCamera, CROP);
                    Log.e("path = ", tempPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (requestCode == GALLERY) {
                Uri selectedImageUri = data.getData();
                String tempPath = getPath(selectedImageUri, SignupFormActivity.this);
                Intent intentAlbum = new Intent(SignupFormActivity.this, CropImagePhoto.class);
                intentAlbum.putExtra("path", tempPath);
                startActivityForResult(intentAlbum, CROP);
                Log.e("path = ", tempPath);
            } else if (requestCode == CROP) {
                String path = data.getStringExtra("cropImagePath");

                try {
                    Bitmap bmp = getImage1(path);
                    ivAva.setImageBitmap(cicrleBitmap(bmp));
                    bingkai.setVisibility(View.VISIBLE);
                    bingkai2.setVisibility(View.VISIBLE);
                    this.path = path;

                    if (hostpage.equalsIgnoreCase("loginfb")) {
                        gantiava = true;
                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }

    public static String getPath(Uri uri, Activity activity) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public Bitmap getImage1(String path) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int srcWidth = options.outWidth;
        int srcHeight = options.outHeight;
        int[] newWH = new int[2];
        newWH[0] = srcWidth / 2;
        newWH[1] = (newWH[0] * srcHeight) / srcWidth;

        int inSampleSize = 1;
        while (srcWidth / 2 >= newWH[0]) {
            srcWidth /= 2;
            srcHeight /= 2;
            inSampleSize *= 2;
        }

        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inSampleSize = inSampleSize;
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap sampledSrcBitmap = BitmapFactory.decodeFile(path, options);
        ExifInterface exif = new ExifInterface(path);
        String s = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        System.out.println("Orientation>>>>>>>>>>>>>>>>>>>>" + s);
        Matrix matrix = new Matrix();
        float rotation = rotationForImage(SignupFormActivity.this, Uri.fromFile(new File(path)));
        if (rotation != 0f) {
            matrix.preRotate(rotation);
        }

        Bitmap pqr = Bitmap.createBitmap(sampledSrcBitmap, 0, 0,
                sampledSrcBitmap.getWidth(), sampledSrcBitmap.getHeight(),
                matrix, true);

        return pqr;
    }

    public Bitmap cicrleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        // bitmap.recycle();

        return output;
    }

    public float rotationForImage(Context context, Uri uri) {
        if (uri.getScheme().equals("content")) {
            String[] projection = {MediaStore.Images.ImageColumns.ORIENTATION};
            Cursor c = context.getContentResolver().query(uri, projection,
                    null, null, null);
            if (c.moveToFirst()) {
                return c.getInt(0);
            }
        } else if (uri.getScheme().equals("file")) {
            try {
                ExifInterface exif = new ExifInterface(uri.getPath());
                int rotation = (int) exifOrientationToDegrees(exif
                        .getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_NORMAL));
                return rotation;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return 0f;
    }

    private static float exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }


}
