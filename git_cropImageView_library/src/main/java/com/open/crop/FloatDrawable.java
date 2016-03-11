package com.open.crop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * ������������������������������
 * 
 * @author Administrator
 * 
 */
public class FloatDrawable extends Drawable {

	private Context mContext;
	// private Drawable mCropPointDrawable;
	private Drawable mCropPointDrawableTL;
	private Drawable mCropPointDrawableTR;
	private Drawable mCropPointDrawableBL;
	private Drawable mCropPointDrawableBR;
	private Paint mLinePaint = new Paint();
	{
		mLinePaint.setARGB(200, 50, 50, 50);
		mLinePaint.setStrokeWidth(1F);
		mLinePaint.setStyle(Paint.Style.STROKE);
		mLinePaint.setAntiAlias(true);
		mLinePaint.setColor(Color.WHITE);
	}

	public FloatDrawable(Context context) {
		super();
		this.mContext = context;
		init();
	}

	private void init() {
		// mCropPointDrawable = mContext.getResources().getDrawable(
		// R.drawable.clip_point);
		mCropPointDrawableTL = mContext.getResources().getDrawable(
				R.drawable.crop_control_tl);
		mCropPointDrawableTR = mContext.getResources().getDrawable(
				R.drawable.crop_control_tr);
		mCropPointDrawableBL = mContext.getResources().getDrawable(
				R.drawable.crop_control_bl);
		mCropPointDrawableBR = mContext.getResources().getDrawable(
				R.drawable.crop_control_br);
	}

	public int getCirleWidth() {
		return mCropPointDrawableTL.getIntrinsicWidth();
	}

	public int getCirleHeight() {
		return mCropPointDrawableTL.getIntrinsicHeight();
	}

	@Override
	public void draw(Canvas canvas) {

		int left = getBounds().left;
		int top = getBounds().top;
		int right = getBounds().right;
		int bottom = getBounds().bottom;

		Rect mRect = new Rect(left + mCropPointDrawableTL.getIntrinsicWidth()
				/ 2, top + mCropPointDrawableTL.getIntrinsicHeight() / 2, right
				- mCropPointDrawableTL.getIntrinsicWidth() / 2, bottom
				- mCropPointDrawableTL.getIntrinsicHeight() / 2);
		// ������
		canvas.drawRect(mRect, mLinePaint);

		// ������
		mCropPointDrawableTL.setBounds(left, top,
				left + mCropPointDrawableTL.getIntrinsicWidth(), top
						+ mCropPointDrawableTL.getIntrinsicHeight());
		mCropPointDrawableTL.draw(canvas);

		// ������
		mCropPointDrawableTR.setBounds(
				right - mCropPointDrawableTR.getIntrinsicWidth(), top, right,
				top + mCropPointDrawableTR.getIntrinsicHeight());
		mCropPointDrawableTR.draw(canvas);

		// ������
		mCropPointDrawableBL.setBounds(left,
				bottom - mCropPointDrawableBL.getIntrinsicHeight(), left
						+ mCropPointDrawableBL.getIntrinsicWidth(), bottom);
		mCropPointDrawableBL.draw(canvas);

		// ������
		mCropPointDrawableBR.setBounds(
				right - mCropPointDrawableBR.getIntrinsicWidth(), bottom
						- mCropPointDrawableBR.getIntrinsicHeight(), right,
				bottom);
		mCropPointDrawableBR.draw(canvas);

	}

	@Override
	public void setBounds(Rect bounds) {
		super.setBounds(new Rect(bounds.left
				- mCropPointDrawableTL.getIntrinsicWidth() / 2, bounds.top
				- mCropPointDrawableTL.getIntrinsicHeight() / 2, bounds.right
				+ mCropPointDrawableTL.getIntrinsicWidth() / 2, bounds.bottom
				+ mCropPointDrawableTL.getIntrinsicHeight() / 2));
	}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

}
