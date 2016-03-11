package com.findkos.local.findkosapps.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.widget.ImageView;

import com.findkos.local.findkosapps.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;


public class MyImageHandler {
    public static int CIRCLE = 1;
    public static int NORMAL = 0;


    public DisplayImageOptions displayImageOptions(final int frame){
        int resdef;
        if (frame == CIRCLE) {
            resdef = R.drawable.ic_launcher;
        } else {
            resdef = R.drawable.ic_launcher;
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(resdef)
                        // Display Stub Image
                .showImageForEmptyUri(resdef)
                        // If Empty image found
                .cacheInMemory().cacheOnDisc()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new BitmapDisplayer() {

                    @Override
                    public Bitmap display(Bitmap bitmap, ImageView imageView) {
                        // TODO Auto-generated method stub
                        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                                bitmap.getHeight(), Bitmap.Config.ARGB_8888);

                        if (frame == CIRCLE) {
                            imageView.setImageBitmap(circleBitmap(bitmap));
                            output = circleBitmap(bitmap);
                        } else {
                            imageView.setImageBitmap(bitmap);
                            output = bitmap;
                        }

                        return output;
                    }
                }).build();

        return options;
    }

    public Bitmap circleBitmap(Bitmap bitmap) {
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

    public void initImageLoader(Context context) {
        int memoryCacheSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            int memClass = ((ActivityManager) context.getSystemService(
                    Context.ACTIVITY_SERVICE)).getMemoryClass();
            memoryCacheSize = (memClass / 8) * 1024 * 1024;
        } else {
            memoryCacheSize = 2 * 1024 * 1024;
        }

        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheSize(memoryCacheSize)
                .memoryCache(
                        new FIFOLimitedMemoryCache(memoryCacheSize - 1000000))
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging()
                .build();

        ImageLoader.getInstance().init(config);
    }

}
