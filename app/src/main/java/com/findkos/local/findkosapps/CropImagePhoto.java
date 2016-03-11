package com.findkos.local.findkosapps;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;

import com.findkos.local.findkosapps.utils.FileUtil;
import com.findkos.local.findkosapps.utils.PixelUtil;
import com.open.crop.CropImageView4;

import java.io.File;
import java.io.IOException;

public class CropImagePhoto extends Activity {
    String _path = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cropimage);
        Bundle extra = getIntent().getExtras();
        _path = extra.getString("path");

        Drawable d = null;
        try {
            d = new BitmapDrawable(getResources(), getImage1(_path));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        final CropImageView4 mCropImage = (CropImageView4) findViewById(R.id.cropImg);
        mCropImage.setDrawable(d, 200, 200, PixelUtil.dp2px(104)); //error dimari
        findViewById(R.id.btnSaveCrop).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                FileUtil.writeImage(mCropImage.getCropImage(),
                                        FileUtil.SDCARD_PAHT + "/crop.jpg", 100);
                                Intent mIntent = new Intent();
                                mIntent.putExtra("cropImagePath",
                                        FileUtil.SDCARD_PAHT + "/crop.jpg");
                                setResult(RESULT_OK, mIntent);
                                finish();
                            }
                        }).start();
                    }
                });
        scanMedia(_path);
    }
    private void scanMedia(String path) {
        Log.d("pathnya", "" + path);
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        Intent scanFileIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        this.sendBroadcast(scanFileIntent);
    }
    public Bitmap getImage1(String path) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Config.RGB_565;
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
        // options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inPreferredConfig = Config.RGB_565;
        Bitmap sampledSrcBitmap = BitmapFactory.decodeFile(path, options);
        ExifInterface exif = new ExifInterface(path);
        String s = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        System.out.println("Orientation>>>>>>>>>>>>>>>>>>>>" + s);
        Matrix matrix = new Matrix();
        float rotation = rotationForImage(CropImagePhoto.this,
                Uri.fromFile(new File(path)));
        if (rotation != 0f) {
            matrix.preRotate(rotation);
        }

        Bitmap pqr = Bitmap.createBitmap(sampledSrcBitmap, 0, 0,
                sampledSrcBitmap.getWidth(), sampledSrcBitmap.getHeight(),
                matrix, true);

        return pqr;
    }

    public float rotationForImage(Context context, Uri uri) {
        if (uri.getScheme().equals("content")) {
            String[] projection = { Images.ImageColumns.ORIENTATION };
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

    public void back(View v) {
        finish();
    }

}