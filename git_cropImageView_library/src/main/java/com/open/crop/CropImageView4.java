// The idea is to do it with a resizable moving square

package com.open.crop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * ���������������������������
 * 
 * @author lolobosse
 * 
 */
public class CropImageView4 extends View {

	// ���������������������
	private float oldX = 0;
	private float oldY = 0;

	// ������
	private final int STATUS_Touch_SINGLE = 1;// ������
	private final int STATUS_TOUCH_MULTI_START = 2;// ������������
	private final int STATUS_TOUCH_MULTI_TOUCHING = 3;// ���������������

	private int mStatus = STATUS_Touch_SINGLE;

	// ������������������������������������
	private final int defaultCropWidth = 300;
	private final int defaultCropHeight = 300;
	private int cropWidth = defaultCropWidth;
	private int cropHeight = defaultCropHeight;

	private final int EDGE_LT = 1;// ������
	private final int EDGE_RT = 2;// ������
	private final int EDGE_LB = 3;// ������
	private final int EDGE_RB = 4;// ������
	private final int EDGE_MOVE_IN = 5;// ������������
	private final int EDGE_MOVE_OUT = 6;// ������������
	private final int EDGE_NONE = 7;// ������������

	public int currentEdge = EDGE_NONE;

	protected float oriRationWH = 0;// ������������������
	protected final float maxZoomOut = 5.0f;// ������������������������
	protected final float minZoomIn = 0.333333f;// ������������������������

	protected Drawable mDrawable;// ������
	protected FloatDrawable mFloatDrawable;// ������
	protected Rect mDrawableSrc = new Rect();
	protected Rect mDrawableDst = new Rect();
	protected Rect mDrawableFloat = new Rect();// ���������������������������������������
	protected boolean isFrist = true;
	private boolean isTouchInSquare = true;
	float _defleft = 0;
	float _deftop = 0;
	float _defright = 0;
	float _defbottom = 0;
	float _defHeight = 0;
	float _defWidth = 0;
	float _defTopHeader = 0;
	float _MAXWIDTH = 0;
	protected Context mContext;

	public CropImageView4(Context context) {
		super(context);
		init(context);
	}

	public CropImageView4(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CropImageView4(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);

	}

	@SuppressLint("NewApi")
	private void init(Context context) {
		this.mContext = context;
		this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		mFloatDrawable = new FloatDrawable(context);// ���������������
	}

	public void setDrawable(Drawable mDrawable, int cropWidth, int cropHeight,
			int top) {
		this.mDrawable = mDrawable;
		this.cropWidth = cropWidth;
		this.cropHeight = cropHeight;
		this.isFrist = true;
		this._defTopHeader = top;
		invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Log.v("helpX",String.valueOf(event.getX()));
		// Log.v("helpY",String.valueOf(event.getY()));
		if (event.getPointerCount() > 1) {
			if (mStatus == STATUS_Touch_SINGLE) {
				mStatus = STATUS_TOUCH_MULTI_START;
			} else if (mStatus == STATUS_TOUCH_MULTI_START) {
				mStatus = STATUS_TOUCH_MULTI_TOUCHING;
			}
		} else {
			if (mStatus == STATUS_TOUCH_MULTI_START
					|| mStatus == STATUS_TOUCH_MULTI_TOUCHING) {
				oldX = event.getX();
				oldY = event.getY();
			}

			mStatus = STATUS_Touch_SINGLE;
		}

		// Log.v("count currentTouch"+currentTouch, "-------");

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			oldX = event.getX();
			oldY = event.getY();
			currentEdge = getTouchEdge((int) oldX, (int) oldY);
			isTouchInSquare = mDrawableFloat.contains((int) event.getX(),
					(int) event.getY());

			// Log.v("currentEdge���" + currentEdge, "-------");
			break;

		case MotionEvent.ACTION_UP:
			checkBounds();
			break;

		case MotionEvent.ACTION_POINTER_1_DOWN:
			break;

		case MotionEvent.ACTION_POINTER_UP:
			currentEdge = EDGE_NONE;
		case MotionEvent.ACTION_MOVE:
			if (mStatus == STATUS_TOUCH_MULTI_TOUCHING) {

			} else if (mStatus == STATUS_Touch_SINGLE) {
				int dx = (int) (event.getX() - oldX);
				int dy = (int) (event.getY() - oldY);

				oldX = event.getX();
				oldY = event.getY();

				if (!(dx == 0 && dy == 0)) {
					int toMoveOf = 0;
					if (Math.abs(dx) >= Math.abs(dy)) {
						toMoveOf = dx;
					} else {
						toMoveOf = dy;
					}
					Log.v("Help", "toMove " + toMoveOf);
					;
					switch (currentEdge) {
					case EDGE_LT:
						Log.e("LT", "TRUE");

						if (mDrawableFloat.top <= (_deftop + 2)) {
							Log.e("TAG == ", "STOP");
							checkBounds();
						} else {
							Log.e("TAG == ", "START");
							mDrawableFloat
									.set(mDrawableFloat.left + toMoveOf,
											mDrawableFloat.top + toMoveOf,
											mDrawableFloat.right,
											mDrawableFloat.bottom);
							break;
						}

					case EDGE_RT:
						Log.e("RT", "TRUE");

						if (mDrawableFloat.top <= (_deftop + 2)) {
							Log.e("TAG == ", "STOP");
							checkBounds();
						} else {
							Log.e("TAG == ", "START");
							if (toMoveOf == dy) {
								Log.e("type = ", "1");
								mDrawableFloat.set(mDrawableFloat.left,
										mDrawableFloat.top + toMoveOf,
										mDrawableFloat.right - toMoveOf,
										mDrawableFloat.bottom);
								break;
							} else {
								Log.e("type = ", "2");
								mDrawableFloat.set(mDrawableFloat.left,
										mDrawableFloat.top - toMoveOf,
										mDrawableFloat.right + toMoveOf,
										mDrawableFloat.bottom);
								break;
							}

						}

					case EDGE_LB:
						Log.e("LB", "TRUE");
						if (mDrawableFloat.bottom >= (_defbottom - 2)) {
							Log.e("TAG == ", "STOP");
							checkBounds();
						} else {
							Log.e("TAG == ", "START");

							if (toMoveOf == dx) {
								mDrawableFloat.set(mDrawableFloat.left
										+ toMoveOf, mDrawableFloat.top,
										mDrawableFloat.right,
										mDrawableFloat.bottom - toMoveOf);
								break;
							} else {
								mDrawableFloat.set(mDrawableFloat.left
										- toMoveOf, mDrawableFloat.top,
										mDrawableFloat.right,
										mDrawableFloat.bottom + toMoveOf);
								break;
							}
						}
						// }

					case EDGE_RB:
						Log.e("RB", "TRUE");
						if (mDrawableFloat.bottom >= (_defbottom - 2)) {
							Log.e("TAG == ", "STOP");
							checkBounds();
						} else {
							Log.e("TAG == ", "START");

							mDrawableFloat.set(mDrawableFloat.left,
									mDrawableFloat.top, mDrawableFloat.right
											+ toMoveOf, mDrawableFloat.bottom
											+ toMoveOf);
							break;
						}

					case EDGE_MOVE_IN:
						if (isTouchInSquare) {
							mDrawableFloat.offset((int) dx, (int) dy);
						}
						break;

					case EDGE_MOVE_OUT:
						break;
					}
					mDrawableFloat.sort();
					invalidate();
				}
			}
			break;
		}

		return true;
	}

	public int getTouchEdge(int eventX, int eventY) {
		if (mFloatDrawable.getBounds().left <= eventX
				&& eventX < (mFloatDrawable.getBounds().left + mFloatDrawable
						.getCirleWidth())
				&& mFloatDrawable.getBounds().top <= eventY
				&& eventY < (mFloatDrawable.getBounds().top + mFloatDrawable
						.getCirleHeight())) {
			return EDGE_LT;// ������
		} else if ((mFloatDrawable.getBounds().right - mFloatDrawable
				.getCirleWidth()) <= eventX
				&& eventX < mFloatDrawable.getBounds().right
				&& mFloatDrawable.getBounds().top <= eventY
				&& eventY < (mFloatDrawable.getBounds().top + mFloatDrawable
						.getCirleHeight())) {
			return EDGE_RT;// ������
		} else if (mFloatDrawable.getBounds().left <= eventX
				&& eventX < (mFloatDrawable.getBounds().left + mFloatDrawable
						.getCirleWidth())
				&& (mFloatDrawable.getBounds().bottom - mFloatDrawable
						.getCirleHeight()) <= eventY
				&& eventY < mFloatDrawable.getBounds().bottom) {
			return EDGE_LB;// ������
		} else if ((mFloatDrawable.getBounds().right - mFloatDrawable
				.getCirleWidth()) <= eventX
				&& eventX < mFloatDrawable.getBounds().right
				&& (mFloatDrawable.getBounds().bottom - mFloatDrawable
						.getCirleHeight()) <= eventY
				&& eventY < mFloatDrawable.getBounds().bottom) {
			return EDGE_RB;// ������
		} else if (mFloatDrawable.getBounds().contains(eventX, eventY)) {
			return EDGE_MOVE_IN;// ������������
		}
		return EDGE_MOVE_OUT;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// super.onDraw(canvas);
		if (mDrawable == null) {
			return; // couldn't resolve the URI
		}

		if (mDrawable.getIntrinsicWidth() == 0
				|| mDrawable.getIntrinsicHeight() == 0) {
			return; // nothing to draw (empty bounds)
		}

		configureBounds();

		mDrawable.draw(canvas);

		canvas.save();
		canvas.clipRect(mDrawableFloat, Region.Op.DIFFERENCE);
		canvas.drawColor(Color.parseColor("#a0000000"));
		canvas.restore();
		mFloatDrawable.draw(canvas);
	}

	protected void configureBounds() {
		if (isFrist) {
			oriRationWH = ((float) mDrawable.getIntrinsicWidth())
					/ ((float) mDrawable.getIntrinsicHeight());

			final float scale = mContext.getResources().getDisplayMetrics().density;
			float w = Math.min(getWidth(), (mDrawable.getIntrinsicWidth()
					* scale + 0.5f));
			float h = (w / oriRationWH);

			float left = ((getWidth() - w) / 2);
			float top = ((getHeight() - h) / 2);
			float right = left + w;
			float bottom = top + h;

			_defleft = left;
			_deftop = top;
			_defright = right;
			_defbottom = bottom;
			_defHeight = h;
			_defWidth = w;
			if (w < h) {
				_MAXWIDTH = w;
			} else {
				_MAXWIDTH = h;
			}
			Log.e("Width = ", "" + w);
			Log.e("Height = ", "" + h);

			mDrawableSrc.set((int) left, (int) top, (int) right, (int) bottom);
			mDrawableDst.set(mDrawableSrc);

			int floatWidth = dipTopx(mContext, cropWidth);
			int floatHeight = dipTopx(mContext, cropHeight);

			if (floatWidth > getWidth()) {
				floatWidth = getWidth();
				floatHeight = cropHeight * floatWidth / cropWidth;
			}

			if (floatHeight > getHeight()) {
				floatHeight = getHeight();
				floatWidth = cropWidth * floatHeight / cropHeight;
			}

			int floatLeft = (getWidth() - floatWidth) / 2;
			int floatTop = (getHeight() - floatHeight) / 2;
			mDrawableFloat.set(floatLeft, floatTop, floatLeft + floatWidth,
					floatTop + floatHeight);

			isFrist = false;
		}

		mDrawable.setBounds(mDrawableDst);
		mFloatDrawable.setBounds(mDrawableFloat);
	}

	protected void checkBounds() {
		int newLeft = mDrawableFloat.left;
		int newTop = mDrawableFloat.top;

		boolean isChange = false;
		if (mDrawableFloat.left < _defleft) {
			newLeft = (int) _defleft;
			isChange = true;
		}

		if (mDrawableFloat.top < _deftop) {
			newTop = (int) _deftop;
			isChange = true;
		}

		if (mDrawableFloat.right > _defright) {
			newLeft = (int) _defright - mDrawableFloat.width();
			isChange = true;
		}

		if (mDrawableFloat.bottom > _defbottom) {
			newTop = (int) _defbottom - mDrawableFloat.height();

			isChange = true;
		}
		// mDrawableFloat.set(mDrawableFloat.left, mDrawableFloat.top,
		// mDrawableFloat.right, mDrawableFloat.bottom);

		if (mDrawableFloat.right - mDrawableFloat.left > _MAXWIDTH) {

			mDrawableFloat.set(mDrawableFloat.left, mDrawableFloat.top,
					mDrawableFloat.left + (int) _MAXWIDTH, mDrawableFloat.top
							+ (int) _MAXWIDTH);
			if (mDrawableFloat.left < _defleft) {
				newLeft = (int) _defleft;
				isChange = true;
			}

			if (mDrawableFloat.top < _deftop) {
				newTop = (int) _deftop;
				isChange = true;
			}

			if (mDrawableFloat.right > _defright) {
				newLeft = (int) _defright - mDrawableFloat.width();
				isChange = true;
			}

			if (mDrawableFloat.bottom > _defbottom) {
				newTop = (int) _defbottom - mDrawableFloat.height();

				isChange = true;
			}
			mDrawableFloat.offsetTo(newLeft, newTop);
			if (isChange) {
				invalidate();

			}

		}
		mDrawableFloat.offsetTo(newLeft, newTop);

		if (isChange) {
			invalidate();

		}
	}

	public Bitmap getCropImage() {
		Bitmap tmpBitmap = Bitmap.createBitmap(getWidth(), getHeight(),
				Config.RGB_565);
		Canvas canvas = new Canvas(tmpBitmap);
		mDrawable.draw(canvas);

		Matrix matrix = new Matrix();
		float scale = (float) (mDrawableSrc.width())
				/ (float) (mDrawableDst.width());
		matrix.postScale(scale, scale);

		Bitmap ret = Bitmap.createBitmap(tmpBitmap, mDrawableFloat.left + 4,
				mDrawableFloat.top + 4, mDrawableFloat.width() - 4,
				mDrawableFloat.height() - 4, matrix, true);
		tmpBitmap.recycle();
		tmpBitmap = null;
		System.gc();

		return ret;
	}

	public int dipTopx(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	// @Override
	// protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	// Drawable d = getDrawable();
	//
	// if (d != null) {
	// // ceil not round - avoid thin vertical gaps along the left/right
	// // edges
	// int width = MeasureSpec.getSize(widthMeasureSpec);
	// int height = (int) Math.ceil((float) width
	// * (float) d.getIntrinsicHeight()
	// / (float) d.getIntrinsicWidth());
	// setMeasuredDimension(width, height);
	// } else {
	// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	// }
	// }
}
