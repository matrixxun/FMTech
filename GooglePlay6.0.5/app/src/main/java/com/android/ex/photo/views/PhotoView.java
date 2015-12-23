package com.android.ex.photo.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ScaleGestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import com.android.ex.photo.R.color;
import com.android.ex.photo.R.dimen;

public class PhotoView
  extends View
  implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener
{
  private static Paint sCropDimPaint;
  private static Paint sCropPaint;
  private static int sCropSize;
  private static boolean sInitialized;
  private static int sTouchSlopSquare;
  private static Bitmap sVideoImage;
  private static Bitmap sVideoNotReadyImage;
  private boolean mAllowCrop;
  private Rect mCropRect = new Rect();
  private int mCropSize;
  private boolean mDoubleTapDebounce;
  public boolean mDoubleTapOccurred;
  private boolean mDoubleTapToZoomEnabled = true;
  private float mDownFocusX;
  private float mDownFocusY;
  private Matrix mDrawMatrix;
  public Drawable mDrawable;
  public View.OnClickListener mExternalClickListener;
  private int mFixedHeight = -1;
  public boolean mFullScreen;
  public GestureDetectorCompat mGestureDetector;
  private boolean mHaveLayout;
  private boolean mIsDoubleTouch;
  public Matrix mMatrix = new Matrix();
  private float mMaxInitialScaleFactor;
  private float mMaxScale;
  public float mMinScale;
  private Matrix mOriginalMatrix = new Matrix();
  private boolean mQuickScaleEnabled;
  public RotateRunnable mRotateRunnable;
  private float mRotation;
  public ScaleGestureDetector mScaleGetureDetector;
  public ScaleRunnable mScaleRunnable;
  public SnapRunnable mSnapRunnable;
  private RectF mTempDst = new RectF();
  public RectF mTempSrc = new RectF();
  public boolean mTransformsEnabled;
  public RectF mTranslateRect = new RectF();
  public TranslateRunnable mTranslateRunnable;
  public float[] mValues = new float[9];
  private byte[] mVideoBlob;
  private boolean mVideoReady;
  
  public PhotoView(Context paramContext)
  {
    super(paramContext);
    initialize();
  }
  
  public PhotoView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initialize();
  }
  
  public PhotoView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initialize();
  }
  
  private int getCropSize()
  {
    if (this.mCropSize > 0) {
      return this.mCropSize;
    }
    return sCropSize;
  }
  
  private float getScale()
  {
    this.mMatrix.getValues(this.mValues);
    return this.mValues[0];
  }
  
  private void initialize()
  {
    Context localContext = getContext();
    if (!sInitialized)
    {
      sInitialized = true;
      Resources localResources = localContext.getApplicationContext().getResources();
      sCropSize = localResources.getDimensionPixelSize(R.dimen.photo_crop_width);
      Paint localPaint1 = new Paint();
      sCropDimPaint = localPaint1;
      localPaint1.setAntiAlias(true);
      sCropDimPaint.setColor(localResources.getColor(R.color.photo_crop_dim_color));
      sCropDimPaint.setStyle(Paint.Style.FILL);
      Paint localPaint2 = new Paint();
      sCropPaint = localPaint2;
      localPaint2.setAntiAlias(true);
      sCropPaint.setColor(localResources.getColor(R.color.photo_crop_highlight_color));
      sCropPaint.setStyle(Paint.Style.STROKE);
      sCropPaint.setStrokeWidth(localResources.getDimension(R.dimen.photo_crop_stroke_width));
      int i = ViewConfiguration.get(localContext).getScaledTouchSlop();
      sTouchSlopSquare = i * i;
    }
    this.mGestureDetector = new GestureDetectorCompat(localContext, this, (byte)0);
    this.mScaleGetureDetector = new ScaleGestureDetector(localContext, this);
    this.mQuickScaleEnabled = ScaleGestureDetectorCompat.isQuickScaleEnabled(this.mScaleGetureDetector);
    this.mScaleRunnable = new ScaleRunnable(this);
    this.mTranslateRunnable = new TranslateRunnable(this);
    this.mSnapRunnable = new SnapRunnable(this);
    this.mRotateRunnable = new RotateRunnable(this);
  }
  
  private void scale(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.mMatrix.postRotate(-this.mRotation, getWidth() / 2, getHeight() / 2);
    float f = Math.min(Math.max(paramFloat1, this.mMinScale), 1.5F * this.mMaxScale) / getScale();
    this.mMatrix.postScale(f, f, paramFloat2, paramFloat3);
    this.mMatrix.postRotate(this.mRotation, getWidth() / 2, getHeight() / 2);
    invalidate();
  }
  
  private boolean scale(MotionEvent paramMotionEvent)
  {
    boolean bool1 = this.mDoubleTapToZoomEnabled;
    boolean bool2 = false;
    float f1;
    float f4;
    float f8;
    float f9;
    if (bool1)
    {
      boolean bool3 = this.mTransformsEnabled;
      bool2 = false;
      if (bool3)
      {
        boolean bool4 = this.mDoubleTapOccurred;
        bool2 = false;
        if (bool4)
        {
          boolean bool5 = this.mDoubleTapDebounce;
          bool2 = false;
          if (!bool5)
          {
            f1 = getScale();
            if (f1 <= this.mMinScale) {
              break label159;
            }
            f4 = this.mMinScale;
            float f10 = f4 / f1;
            f8 = (getWidth() / 2 - f10 * this.mTranslateRect.centerX()) / (1.0F - f10);
            f9 = (getHeight() / 2 - f10 * this.mTranslateRect.centerY()) / (1.0F - f10);
          }
        }
      }
    }
    for (;;)
    {
      this.mScaleRunnable.start(f1, f4, f8, f9);
      bool2 = true;
      this.mDoubleTapDebounce = false;
      this.mDoubleTapOccurred = false;
      return bool2;
      label159:
      float f2 = f1 * 2.0F;
      float f3 = Math.max(this.mMinScale, f2);
      f4 = Math.min(this.mMaxScale, f3);
      float f5 = f4 / f1;
      float f6 = (getWidth() - this.mTranslateRect.width()) / f5;
      float f7 = (getHeight() - this.mTranslateRect.height()) / f5;
      if (this.mTranslateRect.width() <= f6 * 2.0F) {}
      for (f8 = this.mTranslateRect.centerX();; f8 = Math.min(Math.max(f6 + this.mTranslateRect.left, paramMotionEvent.getX()), this.mTranslateRect.right - f6))
      {
        if (this.mTranslateRect.height() > f7 * 2.0F) {
          break label316;
        }
        f9 = this.mTranslateRect.centerY();
        break;
      }
      label316:
      f9 = Math.min(Math.max(f7 + this.mTranslateRect.top, paramMotionEvent.getY()), this.mTranslateRect.bottom - f7);
    }
  }
  
  private void snap()
  {
    this.mTranslateRect.set(this.mTempSrc);
    this.mMatrix.mapRect(this.mTranslateRect);
    float f1;
    float f2;
    label55:
    float f3;
    float f4;
    float f5;
    label100:
    float f6;
    label117:
    float f7;
    label134:
    float f8;
    float f9;
    float f10;
    if (this.mAllowCrop)
    {
      f1 = this.mCropRect.left;
      if (!this.mAllowCrop) {
        break label276;
      }
      f2 = this.mCropRect.right;
      f3 = this.mTranslateRect.left;
      f4 = this.mTranslateRect.right;
      if (f4 - f3 >= f2 - f1) {
        break label285;
      }
      f5 = f1 + (f2 - f1 - (f4 + f3)) / 2.0F;
      if (!this.mAllowCrop) {
        break label323;
      }
      f6 = this.mCropRect.top;
      if (!this.mAllowCrop) {
        break label329;
      }
      f7 = this.mCropRect.bottom;
      f8 = this.mTranslateRect.top;
      f9 = this.mTranslateRect.bottom;
      if (f9 - f8 >= f7 - f6) {
        break label339;
      }
      f10 = f6 + (f7 - f6 - (f9 + f8)) / 2.0F;
    }
    for (;;)
    {
      if ((Math.abs(f5) <= 20.0F) && (Math.abs(f10) <= 20.0F)) {
        break label381;
      }
      SnapRunnable localSnapRunnable = this.mSnapRunnable;
      if (!localSnapRunnable.mRunning)
      {
        localSnapRunnable.mStartRunTime = -1L;
        localSnapRunnable.mTranslateX = f5;
        localSnapRunnable.mTranslateY = f10;
        localSnapRunnable.mStop = false;
        localSnapRunnable.mRunning = true;
        localSnapRunnable.mHeader.postDelayed(localSnapRunnable, 250L);
      }
      return;
      f1 = 0.0F;
      break;
      label276:
      f2 = getWidth();
      break label55;
      label285:
      if (f3 > f1)
      {
        f5 = f1 - f3;
        break label100;
      }
      if (f4 < f2)
      {
        f5 = f2 - f4;
        break label100;
      }
      f5 = 0.0F;
      break label100;
      label323:
      f6 = 0.0F;
      break label117;
      label329:
      f7 = getHeight();
      break label134;
      label339:
      if (f8 > f6) {
        f10 = f6 - f8;
      } else if (f9 < f7) {
        f10 = f7 - f9;
      } else {
        f10 = 0.0F;
      }
    }
    label381:
    this.mMatrix.postTranslate(f5, f10);
    invalidate();
  }
  
  private int translate(float paramFloat1, float paramFloat2)
  {
    this.mTranslateRect.set(this.mTempSrc);
    this.mMatrix.mapRect(this.mTranslateRect);
    float f1;
    float f2;
    label57:
    float f3;
    float f4;
    float f5;
    label111:
    float f6;
    label128:
    float f7;
    label145:
    float f8;
    float f9;
    float f10;
    label199:
    int i;
    if (this.mAllowCrop)
    {
      f1 = this.mCropRect.left;
      if (!this.mAllowCrop) {
        break label253;
      }
      f2 = this.mCropRect.right;
      f3 = this.mTranslateRect.left;
      f4 = this.mTranslateRect.right;
      if (!this.mAllowCrop) {
        break label263;
      }
      f5 = Math.max(f1 - this.mTranslateRect.right, Math.min(f2 - this.mTranslateRect.left, paramFloat1));
      if (!this.mAllowCrop) {
        break label320;
      }
      f6 = this.mCropRect.top;
      if (!this.mAllowCrop) {
        break label326;
      }
      f7 = this.mCropRect.bottom;
      f8 = this.mTranslateRect.top;
      f9 = this.mTranslateRect.bottom;
      if (!this.mAllowCrop) {
        break label336;
      }
      f10 = Math.max(f6 - this.mTranslateRect.bottom, Math.min(f7 - this.mTranslateRect.top, paramFloat2));
      this.mMatrix.postTranslate(f5, f10);
      invalidate();
      if (f5 != paramFloat1) {
        break label393;
      }
      i = 1;
      label225:
      if (f10 != paramFloat2) {
        break label399;
      }
    }
    label263:
    label393:
    label399:
    for (int j = 1;; j = 0)
    {
      if ((i == 0) || (j == 0)) {
        break label405;
      }
      return 3;
      f1 = 0.0F;
      break;
      label253:
      f2 = getWidth();
      break label57;
      if (f4 - f3 < f2 - f1)
      {
        f5 = f1 + (f2 - f1 - (f4 + f3)) / 2.0F;
        break label111;
      }
      f5 = Math.max(f2 - f4, Math.min(f1 - f3, paramFloat1));
      break label111;
      label320:
      f6 = 0.0F;
      break label128;
      label326:
      f7 = getHeight();
      break label145;
      label336:
      if (f9 - f8 < f7 - f6)
      {
        f10 = f6 + (f7 - f6 - (f9 + f8)) / 2.0F;
        break label199;
      }
      f10 = Math.max(f7 - f9, Math.min(f6 - f8, paramFloat2));
      break label199;
      i = 0;
      break label225;
    }
    label405:
    if (i != 0) {
      return 1;
    }
    if (j != 0) {
      return 2;
    }
    return 0;
  }
  
  public final void configureBounds(boolean paramBoolean)
  {
    if ((this.mDrawable == null) || (!this.mHaveLayout)) {
      return;
    }
    int i = this.mDrawable.getIntrinsicWidth();
    int j = this.mDrawable.getIntrinsicHeight();
    int k = getWidth();
    int m = getHeight();
    int n;
    int i1;
    int i2;
    int i3;
    label134:
    int i4;
    label146:
    int i5;
    label173:
    label192:
    int i8;
    label234:
    int i9;
    if (((i < 0) || (k == i)) && ((j < 0) || (m == j)))
    {
      n = 1;
      this.mDrawable.setBounds(0, 0, i, j);
      if ((paramBoolean) || ((this.mMinScale == 0.0F) && (this.mDrawable != null) && (this.mHaveLayout)))
      {
        i1 = this.mDrawable.getIntrinsicWidth();
        i2 = this.mDrawable.getIntrinsicHeight();
        if (!this.mAllowCrop) {
          break label318;
        }
        i3 = sCropSize;
        if (!this.mAllowCrop) {
          break label327;
        }
        i4 = sCropSize;
        if (((i1 >= 0) && (i3 != i1)) || ((i2 >= 0) && (i4 != i2))) {
          break label336;
        }
        i5 = 1;
        if ((i5 == 0) || (this.mAllowCrop)) {
          break label342;
        }
        this.mMatrix.reset();
        this.mOriginalMatrix.set(this.mMatrix);
        int i6 = this.mDrawable.getIntrinsicWidth();
        int i7 = this.mDrawable.getIntrinsicHeight();
        if (!this.mAllowCrop) {
          break label520;
        }
        i8 = getCropSize();
        if (!this.mAllowCrop) {
          break label529;
        }
        i9 = getCropSize();
        label247:
        if ((i6 >= i8) || (i7 >= i9) || (this.mAllowCrop)) {
          break label538;
        }
      }
    }
    label520:
    label529:
    label538:
    for (this.mMinScale = 1.0F;; this.mMinScale = getScale())
    {
      this.mMaxScale = Math.max(4.0F * this.mMinScale, 4.0F);
      if ((n == 0) && (!this.mMatrix.isIdentity())) {
        break label549;
      }
      this.mDrawMatrix = null;
      return;
      n = 0;
      break;
      label318:
      i3 = getWidth();
      break label134;
      label327:
      i4 = getHeight();
      break label146;
      label336:
      i5 = 0;
      break label173;
      label342:
      this.mTempSrc.set(0.0F, 0.0F, i1, i2);
      if (this.mAllowCrop) {
        this.mTempDst.set(this.mCropRect);
      }
      for (;;)
      {
        RectF localRectF = new RectF(i3 / 2 - i1 * this.mMaxInitialScaleFactor / 2.0F, i4 / 2 - i2 * this.mMaxInitialScaleFactor / 2.0F, i3 / 2 + i1 * this.mMaxInitialScaleFactor / 2.0F, i4 / 2 + i2 * this.mMaxInitialScaleFactor / 2.0F);
        if (!this.mTempDst.contains(localRectF)) {
          break label498;
        }
        this.mMatrix.setRectToRect(this.mTempSrc, localRectF, Matrix.ScaleToFit.CENTER);
        break;
        this.mTempDst.set(0.0F, 0.0F, i3, i4);
      }
      label498:
      this.mMatrix.setRectToRect(this.mTempSrc, this.mTempDst, Matrix.ScaleToFit.CENTER);
      break label192;
      i8 = getWidth();
      break label234;
      i9 = getHeight();
      break label247;
    }
    label549:
    this.mDrawMatrix = this.mMatrix;
  }
  
  public final void enableImageTransforms(boolean paramBoolean)
  {
    this.mTransformsEnabled = paramBoolean;
    if (!this.mTransformsEnabled) {
      resetTransformations();
    }
  }
  
  public Bitmap getCroppedPhoto()
  {
    Bitmap localBitmap;
    if (!this.mAllowCrop) {
      localBitmap = null;
    }
    Canvas localCanvas;
    Matrix localMatrix;
    do
    {
      return localBitmap;
      localBitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
      localCanvas = new Canvas(localBitmap);
      float f = 256.0F / (this.mCropRect.right - this.mCropRect.left);
      localMatrix = new Matrix(this.mDrawMatrix);
      localMatrix.postTranslate(-this.mCropRect.left, -this.mCropRect.top);
      localMatrix.postScale(f, f);
    } while (this.mDrawable == null);
    localCanvas.concat(localMatrix);
    this.mDrawable.draw(localCanvas);
    return localBitmap;
  }
  
  public Drawable getDrawable()
  {
    return this.mDrawable;
  }
  
  public Bitmap getPhoto()
  {
    if ((this.mDrawable != null) && ((this.mDrawable instanceof BitmapDrawable))) {
      return ((BitmapDrawable)this.mDrawable).getBitmap();
    }
    return null;
  }
  
  public byte[] getVideoData()
  {
    return this.mVideoBlob;
  }
  
  public void invalidateDrawable(Drawable paramDrawable)
  {
    if (this.mDrawable == paramDrawable)
    {
      invalidate();
      return;
    }
    super.invalidateDrawable(paramDrawable);
  }
  
  public boolean onDoubleTap(MotionEvent paramMotionEvent)
  {
    this.mDoubleTapOccurred = true;
    if (!this.mQuickScaleEnabled) {
      return scale(paramMotionEvent);
    }
    return false;
  }
  
  public boolean onDoubleTapEvent(MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    }
    int i;
    int j;
    do
    {
      do
      {
        do
        {
          do
          {
            return false;
          } while (!this.mQuickScaleEnabled);
          this.mDownFocusX = paramMotionEvent.getX();
          this.mDownFocusY = paramMotionEvent.getY();
          return false;
        } while (!this.mQuickScaleEnabled);
        return scale(paramMotionEvent);
      } while ((!this.mQuickScaleEnabled) || (!this.mDoubleTapOccurred));
      i = (int)(paramMotionEvent.getX() - this.mDownFocusX);
      j = (int)(paramMotionEvent.getY() - this.mDownFocusY);
    } while (i * i + j * j <= sTouchSlopSquare);
    this.mDoubleTapOccurred = false;
    return false;
  }
  
  public boolean onDown(MotionEvent paramMotionEvent)
  {
    if (this.mTransformsEnabled)
    {
      this.mTranslateRunnable.stop();
      this.mSnapRunnable.stop();
    }
    return true;
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.mDrawable != null)
    {
      int i = paramCanvas.getSaveCount();
      paramCanvas.save();
      if (this.mDrawMatrix != null) {
        paramCanvas.concat(this.mDrawMatrix);
      }
      this.mDrawable.draw(paramCanvas);
      paramCanvas.restoreToCount(i);
      if (this.mVideoBlob != null) {
        if (!this.mVideoReady) {
          break label230;
        }
      }
    }
    label230:
    for (Bitmap localBitmap = sVideoImage;; localBitmap = sVideoNotReadyImage)
    {
      int k = (getWidth() - localBitmap.getWidth()) / 2;
      int m = (getHeight() - localBitmap.getHeight()) / 2;
      paramCanvas.drawBitmap(localBitmap, k, m, null);
      this.mTranslateRect.set(this.mDrawable.getBounds());
      if (this.mDrawMatrix != null) {
        this.mDrawMatrix.mapRect(this.mTranslateRect);
      }
      if (this.mAllowCrop)
      {
        int j = paramCanvas.getSaveCount();
        paramCanvas.drawRect(0.0F, 0.0F, getWidth(), getHeight(), sCropDimPaint);
        paramCanvas.save();
        paramCanvas.clipRect(this.mCropRect);
        if (this.mDrawMatrix != null) {
          paramCanvas.concat(this.mDrawMatrix);
        }
        this.mDrawable.draw(paramCanvas);
        paramCanvas.restoreToCount(j);
        paramCanvas.drawRect(this.mCropRect, sCropPaint);
      }
      return;
    }
  }
  
  public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    if ((this.mTransformsEnabled) && (!this.mScaleRunnable.mRunning))
    {
      TranslateRunnable localTranslateRunnable = this.mTranslateRunnable;
      if (!localTranslateRunnable.mRunning)
      {
        localTranslateRunnable.mLastRunTime = -1L;
        localTranslateRunnable.mVelocityX = paramFloat1;
        localTranslateRunnable.mVelocityY = paramFloat2;
        float f = (float)Math.atan2(localTranslateRunnable.mVelocityY, localTranslateRunnable.mVelocityX);
        localTranslateRunnable.mDecelerationX = ((float)(1000.0D * Math.cos(f)));
        localTranslateRunnable.mDecelerationY = ((float)(1000.0D * Math.sin(f)));
        localTranslateRunnable.mStop = false;
        localTranslateRunnable.mRunning = true;
        localTranslateRunnable.mHeader.post(localTranslateRunnable);
      }
    }
    return true;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mHaveLayout = true;
    int i = getWidth();
    int j = getHeight();
    if (this.mAllowCrop)
    {
      this.mCropSize = Math.min(sCropSize, Math.min(i, j));
      int k = (i - this.mCropSize) / 2;
      int m = (j - this.mCropSize) / 2;
      int n = k + this.mCropSize;
      int i1 = m + this.mCropSize;
      this.mCropRect.set(k, m, n, i1);
    }
    configureBounds(paramBoolean);
  }
  
  public void onLongPress(MotionEvent paramMotionEvent) {}
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mFixedHeight != -1)
    {
      super.onMeasure(paramInt1, View.MeasureSpec.makeMeasureSpec(this.mFixedHeight, -2147483648));
      setMeasuredDimension(getMeasuredWidth(), this.mFixedHeight);
      return;
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public boolean onScale(ScaleGestureDetector paramScaleGestureDetector)
  {
    if (this.mTransformsEnabled)
    {
      this.mIsDoubleTouch = false;
      scale(getScale() * paramScaleGestureDetector.getScaleFactor(), paramScaleGestureDetector.getFocusX(), paramScaleGestureDetector.getFocusY());
    }
    return true;
  }
  
  public boolean onScaleBegin(ScaleGestureDetector paramScaleGestureDetector)
  {
    if (this.mTransformsEnabled)
    {
      this.mScaleRunnable.stop();
      this.mIsDoubleTouch = true;
    }
    return true;
  }
  
  public void onScaleEnd(ScaleGestureDetector paramScaleGestureDetector)
  {
    float f1 = getScale();
    float f4;
    float f5;
    float f6;
    float f7;
    float f8;
    float f9;
    float f10;
    if (f1 > this.mMaxScale)
    {
      float f2 = 1.0F / (1.0F - this.mMaxScale / f1);
      float f3 = 1.0F - f2;
      f4 = getWidth() / 2;
      f5 = getHeight() / 2;
      f6 = f3 * this.mTranslateRect.left;
      f7 = f3 * this.mTranslateRect.top;
      f8 = f2 * getWidth() + f3 * this.mTranslateRect.right;
      f9 = f2 * getHeight() + f3 * this.mTranslateRect.bottom;
      if (f8 <= f6) {
        break label187;
      }
      f10 = (f8 + f6) / 2.0F;
      if (f9 <= f7) {
        break label204;
      }
    }
    label187:
    label204:
    for (float f11 = (f9 + f7) / 2.0F;; f11 = Math.min(Math.max(f9, f5), f7))
    {
      this.mScaleRunnable.start(f1, this.mMaxScale, f10, f11);
      if ((this.mTransformsEnabled) && (this.mIsDoubleTouch))
      {
        this.mDoubleTapDebounce = true;
        resetTransformations();
      }
      return;
      f10 = Math.min(Math.max(f8, f4), f6);
      break;
    }
  }
  
  public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    if ((this.mTransformsEnabled) && (!this.mScaleRunnable.mRunning)) {
      translate(-paramFloat1, -paramFloat2);
    }
    return true;
  }
  
  public void onShowPress(MotionEvent paramMotionEvent) {}
  
  public boolean onSingleTapConfirmed(MotionEvent paramMotionEvent)
  {
    if ((this.mExternalClickListener != null) && (!this.mIsDoubleTouch)) {
      this.mExternalClickListener.onClick(this);
    }
    this.mIsDoubleTouch = false;
    return true;
  }
  
  public boolean onSingleTapUp(MotionEvent paramMotionEvent)
  {
    return false;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((this.mScaleGetureDetector == null) || (this.mGestureDetector == null)) {}
    do
    {
      return true;
      this.mScaleGetureDetector.onTouchEvent(paramMotionEvent);
      this.mGestureDetector.onTouchEvent(paramMotionEvent);
      switch (paramMotionEvent.getAction())
      {
      case 2: 
      default: 
        return true;
      }
    } while (TranslateRunnable.access$000(this.mTranslateRunnable));
    snap();
    return true;
  }
  
  public final void resetTransformations()
  {
    this.mMatrix.set(this.mOriginalMatrix);
    invalidate();
  }
  
  public void setFixedHeight(int paramInt)
  {
    if (paramInt != this.mFixedHeight) {}
    for (int i = 1;; i = 0)
    {
      this.mFixedHeight = paramInt;
      setMeasuredDimension(getMeasuredWidth(), this.mFixedHeight);
      if (i != 0)
      {
        configureBounds(true);
        requestLayout();
      }
      return;
    }
  }
  
  public final void setFullScreen$25decb5(boolean paramBoolean)
  {
    if (paramBoolean != this.mFullScreen)
    {
      this.mFullScreen = paramBoolean;
      requestLayout();
      invalidate();
    }
  }
  
  public void setMaxInitialScale(float paramFloat)
  {
    this.mMaxInitialScaleFactor = paramFloat;
  }
  
  public void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mExternalClickListener = paramOnClickListener;
  }
  
  public boolean verifyDrawable(Drawable paramDrawable)
  {
    return (this.mDrawable == paramDrawable) || (super.verifyDrawable(paramDrawable));
  }
  
  private static final class RotateRunnable
    implements Runnable
  {
    float mAppliedRotation;
    final PhotoView mHeader;
    long mLastRuntime;
    boolean mRunning;
    boolean mStop;
    float mTargetRotation;
    float mVelocity;
    
    public RotateRunnable(PhotoView paramPhotoView)
    {
      this.mHeader = paramPhotoView;
    }
    
    public final void run()
    {
      if (this.mStop) {
        return;
      }
      long l1;
      if (this.mAppliedRotation != this.mTargetRotation)
      {
        l1 = System.currentTimeMillis();
        if (this.mLastRuntime == -1L) {
          break label176;
        }
      }
      label176:
      for (long l2 = l1 - this.mLastRuntime;; l2 = 0L)
      {
        float f = this.mVelocity * (float)l2;
        if (((this.mAppliedRotation < this.mTargetRotation) && (f + this.mAppliedRotation > this.mTargetRotation)) || ((this.mAppliedRotation > this.mTargetRotation) && (f + this.mAppliedRotation < this.mTargetRotation))) {
          f = this.mTargetRotation - this.mAppliedRotation;
        }
        PhotoView.access$500$2b122c6d(this.mHeader, f);
        this.mAppliedRotation = (f + this.mAppliedRotation);
        if (this.mAppliedRotation == this.mTargetRotation) {
          stop();
        }
        this.mLastRuntime = l1;
        if (this.mStop) {
          break;
        }
        this.mHeader.post(this);
        return;
      }
    }
    
    public final void stop()
    {
      this.mRunning = false;
      this.mStop = true;
    }
  }
  
  private static final class ScaleRunnable
    implements Runnable
  {
    private float mCenterX;
    private float mCenterY;
    private final PhotoView mHeader;
    private boolean mRunning;
    private float mStartScale;
    private long mStartTime;
    private boolean mStop;
    private float mTargetScale;
    private float mVelocity;
    private boolean mZoomingIn;
    
    public ScaleRunnable(PhotoView paramPhotoView)
    {
      this.mHeader = paramPhotoView;
    }
    
    public final void run()
    {
      if (this.mStop) {
        return;
      }
      long l = System.currentTimeMillis() - this.mStartTime;
      float f = this.mStartScale + this.mVelocity * (float)l;
      this.mHeader.scale(f, this.mCenterX, this.mCenterY);
      boolean bool1;
      if (f != this.mTargetScale)
      {
        bool1 = this.mZoomingIn;
        if (f <= this.mTargetScale) {
          break label120;
        }
      }
      label120:
      for (boolean bool2 = true;; bool2 = false)
      {
        if (bool1 == bool2)
        {
          this.mHeader.scale(this.mTargetScale, this.mCenterX, this.mCenterY);
          stop();
        }
        if (this.mStop) {
          break;
        }
        this.mHeader.post(this);
        return;
      }
    }
    
    public final boolean start(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      if (this.mRunning) {
        return false;
      }
      this.mCenterX = paramFloat3;
      this.mCenterY = paramFloat4;
      this.mTargetScale = paramFloat2;
      this.mStartTime = System.currentTimeMillis();
      this.mStartScale = paramFloat1;
      if (this.mTargetScale > this.mStartScale) {}
      for (boolean bool = true;; bool = false)
      {
        this.mZoomingIn = bool;
        this.mVelocity = ((this.mTargetScale - this.mStartScale) / 200.0F);
        this.mRunning = true;
        this.mStop = false;
        this.mHeader.post(this);
        return true;
      }
    }
    
    public final void stop()
    {
      this.mRunning = false;
      this.mStop = true;
    }
  }
  
  private static final class SnapRunnable
    implements Runnable
  {
    final PhotoView mHeader;
    boolean mRunning;
    long mStartRunTime = -1L;
    boolean mStop;
    float mTranslateX;
    float mTranslateY;
    
    public SnapRunnable(PhotoView paramPhotoView)
    {
      this.mHeader = paramPhotoView;
    }
    
    public final void run()
    {
      if (this.mStop) {
        return;
      }
      long l = System.currentTimeMillis();
      float f1;
      label31:
      float f2;
      if (this.mStartRunTime != -1L)
      {
        f1 = (float)(l - this.mStartRunTime);
        if (this.mStartRunTime == -1L) {
          this.mStartRunTime = l;
        }
        if (f1 < 100.0F) {
          break label144;
        }
        f2 = this.mTranslateX;
      }
      for (;;)
      {
        label60:
        float f3 = this.mTranslateY;
        label144:
        do
        {
          this.mHeader.translate(f2, f3);
          this.mTranslateX -= f2;
          this.mTranslateY -= f3;
          if ((this.mTranslateX == 0.0F) && (this.mTranslateY == 0.0F)) {
            stop();
          }
          if (this.mStop) {
            break;
          }
          this.mHeader.post(this);
          return;
          f1 = 0.0F;
          break label31;
          f2 = 10.0F * (this.mTranslateX / (100.0F - f1));
          f3 = 10.0F * (this.mTranslateY / (100.0F - f1));
          if ((Math.abs(f2) > Math.abs(this.mTranslateX)) || (Float.isNaN(f2))) {
            f2 = this.mTranslateX;
          }
          if (Math.abs(f3) > Math.abs(this.mTranslateY)) {
            break label60;
          }
        } while (!Float.isNaN(f3));
      }
    }
    
    public final void stop()
    {
      this.mRunning = false;
      this.mStop = true;
    }
  }
  
  private static final class TranslateRunnable
    implements Runnable
  {
    float mDecelerationX;
    float mDecelerationY;
    final PhotoView mHeader;
    long mLastRunTime = -1L;
    boolean mRunning;
    boolean mStop;
    float mVelocityX;
    float mVelocityY;
    
    public TranslateRunnable(PhotoView paramPhotoView)
    {
      this.mHeader = paramPhotoView;
    }
    
    public final void run()
    {
      float f1 = 1000.0F;
      if (this.mStop) {
        return;
      }
      long l = System.currentTimeMillis();
      float f2;
      int i;
      if (this.mLastRunTime != -1L)
      {
        f2 = (float)(l - this.mLastRunTime) / f1;
        i = this.mHeader.translate(f2 * this.mVelocityX, f2 * this.mVelocityY);
        this.mLastRunTime = l;
        float f3 = f2 * this.mDecelerationX;
        if (Math.abs(this.mVelocityX) <= Math.abs(f3)) {
          break label194;
        }
        this.mVelocityX -= f3;
        label101:
        float f4 = f2 * this.mDecelerationY;
        if (Math.abs(this.mVelocityY) <= Math.abs(f4)) {
          break label202;
        }
        this.mVelocityY -= f4;
        label137:
        if (((this.mVelocityX != 0.0F) || (this.mVelocityY != 0.0F)) && (i != 0)) {
          break label210;
        }
        stop();
        this.mHeader.snap();
      }
      for (;;)
      {
        label171:
        if (!this.mStop)
        {
          this.mHeader.post(this);
          return;
          f2 = 0.0F;
          break;
          label194:
          this.mVelocityX = 0.0F;
          break label101;
          label202:
          this.mVelocityY = 0.0F;
          break label137;
          label210:
          if (i == 1)
          {
            if (this.mVelocityX > 0.0F) {}
            for (;;)
            {
              this.mDecelerationX = f1;
              this.mDecelerationY = 0.0F;
              this.mVelocityY = 0.0F;
              break;
              f1 = -1000.0F;
            }
          }
          if (i == 2)
          {
            this.mDecelerationX = 0.0F;
            if (this.mVelocityY <= 0.0F) {
              break label282;
            }
          }
        }
      }
      for (;;)
      {
        this.mDecelerationY = f1;
        this.mVelocityX = 0.0F;
        break label171;
        break;
        label282:
        f1 = -1000.0F;
      }
    }
    
    public final void stop()
    {
      this.mRunning = false;
      this.mStop = true;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.ex.photo.views.PhotoView
 * JD-Core Version:    0.7.0.1
 */