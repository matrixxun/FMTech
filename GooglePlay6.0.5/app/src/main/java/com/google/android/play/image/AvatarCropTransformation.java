package com.google.android.play.image;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.support.v4.util.LruCache;
import com.google.android.play.R.color;
import com.google.android.play.R.dimen;

public final class AvatarCropTransformation
  implements BitmapTransformation
{
  private static int mActiveCacheConfig;
  private static AvatarCropTransformation sInstanceForNoRingNoForceFill;
  private static AvatarCropTransformation sInstanceForRingNoForceFill;
  private static LruCache<Integer, AvatarCropTransformation> sInstancesForNoRingWithFill = new LruCache(10);
  private static LruCache<Integer, AvatarCropTransformation> sInstancesForRingWithFill = new LruCache(10);
  private final Paint mCropPaint;
  private final int mDecorationThresholdMax;
  private final int mDecorationThresholdMin;
  private final Paint mDefaultOutlinePaint;
  private final Paint mDefaultRingPaint;
  private final Rect mDestinationRectangle;
  private final boolean mDrawRingAboveThreshold;
  private final float mDropShadowSizeMax;
  private final float mDropShadowSizeMin;
  private final Paint mFillToSizePaint;
  private final int mFocusedOutlineColor;
  private boolean mForceFill;
  private final Paint mHighlightOutlinePaint;
  private final Paint mPressedFillPaint;
  private final int mPressedOutlineColor;
  private final RectF mRectangle;
  private final Paint mResizePaint;
  private final int mRingSizeMax;
  private final int mRingSizeMin;
  private final Rect mSourceRectangle;
  
  private AvatarCropTransformation(Resources paramResources, boolean paramBoolean1, int paramInt, boolean paramBoolean2)
  {
    this.mDecorationThresholdMin = paramResources.getDimensionPixelSize(R.dimen.play_avatar_decoration_threshold_min);
    this.mDecorationThresholdMax = paramResources.getDimensionPixelSize(R.dimen.play_avatar_decoration_threshold_max);
    this.mRingSizeMin = paramResources.getDimensionPixelSize(R.dimen.play_avatar_ring_size_min);
    this.mRingSizeMax = paramResources.getDimensionPixelSize(R.dimen.play_avatar_ring_size_max);
    this.mDropShadowSizeMin = (0.5F * paramResources.getDimensionPixelSize(R.dimen.play_avatar_drop_shadow_min));
    this.mDropShadowSizeMax = (0.5F * paramResources.getDimensionPixelSize(R.dimen.play_avatar_drop_shadow_max));
    int i = paramResources.getColor(R.color.play_avatar_outline);
    int j = paramResources.getColor(R.color.play_white);
    float f = 0.5F * paramResources.getDimensionPixelSize(R.dimen.play_avatar_noring_outline);
    this.mDefaultOutlinePaint = new Paint();
    this.mDefaultOutlinePaint.setColor(i);
    this.mDefaultOutlinePaint.setStrokeWidth(f);
    this.mDefaultOutlinePaint.setStyle(Paint.Style.STROKE);
    this.mDefaultOutlinePaint.setAntiAlias(true);
    this.mDefaultRingPaint = new Paint();
    this.mDefaultRingPaint.setColor(j);
    this.mDefaultRingPaint.setStyle(Paint.Style.STROKE);
    this.mDefaultRingPaint.setAntiAlias(true);
    this.mCropPaint = new Paint(2);
    this.mCropPaint.setAntiAlias(true);
    this.mResizePaint = new Paint(2);
    this.mRectangle = new RectF();
    this.mPressedFillPaint = new Paint();
    this.mPressedFillPaint.setColor(paramResources.getColor(R.color.play_avatar_pressed_fill));
    this.mPressedFillPaint.setAntiAlias(true);
    this.mPressedFillPaint.setStyle(Paint.Style.FILL);
    this.mPressedOutlineColor = paramResources.getColor(R.color.play_avatar_pressed_outline);
    this.mFocusedOutlineColor = paramResources.getColor(R.color.play_avatar_focused_outline);
    this.mHighlightOutlinePaint = new Paint();
    this.mHighlightOutlinePaint.setAntiAlias(true);
    this.mHighlightOutlinePaint.setStrokeWidth(f);
    this.mHighlightOutlinePaint.setStyle(Paint.Style.STROKE);
    this.mFillToSizePaint = new Paint();
    this.mFillToSizePaint.setColor(paramInt);
    this.mFillToSizePaint.setStyle(Paint.Style.FILL);
    this.mForceFill = paramBoolean2;
    this.mSourceRectangle = new Rect();
    this.mDestinationRectangle = new Rect();
    this.mDrawRingAboveThreshold = paramBoolean1;
  }
  
  private static void checkConfigurationChanges(Configuration paramConfiguration)
  {
    try
    {
      int i = paramConfiguration.hashCode();
      if (mActiveCacheConfig != i)
      {
        clearInstances();
        mActiveCacheConfig = i;
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private static void clearInstances()
  {
    try
    {
      sInstanceForRingNoForceFill = null;
      sInstanceForNoRingNoForceFill = null;
      sInstancesForRingWithFill.trimToSize(-1);
      sInstancesForNoRingWithFill.trimToSize(-1);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private void drawAvatar(Canvas paramCanvas, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f1 = this.mRectangle.left;
    float f2 = this.mRectangle.right;
    float f3 = this.mRectangle.top;
    float f4 = this.mRectangle.bottom;
    this.mRectangle.left = 0.0F;
    this.mRectangle.top = 0.0F;
    this.mRectangle.right = paramInt;
    this.mRectangle.bottom = paramInt;
    paramCanvas.save();
    paramCanvas.scale(paramFloat3, paramFloat3);
    paramCanvas.translate(paramFloat2, paramFloat2);
    paramCanvas.drawRoundRect(this.mRectangle, paramFloat1, paramFloat1, this.mCropPaint);
    paramCanvas.restore();
    this.mRectangle.left = f1;
    this.mRectangle.right = f2;
    this.mRectangle.top = f3;
    this.mRectangle.bottom = f4;
  }
  
  private void drawOutline(Canvas paramCanvas, float paramFloat, int paramInt)
  {
    float f1 = this.mRectangle.left;
    float f2 = this.mRectangle.right;
    float f3 = this.mRectangle.top;
    float f4 = this.mRectangle.bottom;
    this.mDefaultOutlinePaint.setStrokeWidth(2.0F * paramFloat);
    float f5 = this.mDefaultOutlinePaint.getStrokeWidth() / 2.0F;
    RectF localRectF1 = this.mRectangle;
    localRectF1.left += f5 - paramFloat / 3.0F;
    RectF localRectF2 = this.mRectangle;
    localRectF2.top += f5 + paramFloat;
    RectF localRectF3 = this.mRectangle;
    localRectF3.right -= f5 - paramFloat / 3.0F;
    RectF localRectF4 = this.mRectangle;
    localRectF4.bottom -= f5 - paramFloat;
    this.mDefaultOutlinePaint.setColor(paramInt);
    paramCanvas.drawOval(this.mRectangle, this.mDefaultOutlinePaint);
    this.mRectangle.left = f1;
    this.mRectangle.right = f2;
    this.mRectangle.top = f3;
    this.mRectangle.bottom = f4;
  }
  
  private float getDropShadowSize(int paramInt1, int paramInt2)
  {
    int i = Math.max(paramInt1, paramInt2);
    if (i < this.mDecorationThresholdMin) {
      return 0.0F;
    }
    return interpolate(this.mDecorationThresholdMin, this.mDecorationThresholdMax, this.mDropShadowSizeMin, this.mDropShadowSizeMax, i);
  }
  
  public static AvatarCropTransformation getFullAvatarCrop(Resources paramResources)
  {
    try
    {
      checkConfigurationChanges(paramResources.getConfiguration());
      if (sInstanceForRingNoForceFill == null) {
        sInstanceForRingNoForceFill = new AvatarCropTransformation(paramResources, true, paramResources.getColor(R.color.play_white), false);
      }
      AvatarCropTransformation localAvatarCropTransformation = sInstanceForRingNoForceFill;
      return localAvatarCropTransformation;
    }
    finally {}
  }
  
  public static AvatarCropTransformation getFullAvatarCrop(Resources paramResources, int paramInt)
  {
    try
    {
      checkConfigurationChanges(paramResources.getConfiguration());
      AvatarCropTransformation localAvatarCropTransformation = (AvatarCropTransformation)sInstancesForRingWithFill.get(Integer.valueOf(paramInt));
      if (localAvatarCropTransformation == null)
      {
        localAvatarCropTransformation = new AvatarCropTransformation(paramResources, true, paramInt, true);
        sInstancesForRingWithFill.put(Integer.valueOf(paramInt), localAvatarCropTransformation);
      }
      return localAvatarCropTransformation;
    }
    finally {}
  }
  
  public static AvatarCropTransformation getNoRingAvatarCrop(Resources paramResources)
  {
    try
    {
      checkConfigurationChanges(paramResources.getConfiguration());
      if (sInstanceForNoRingNoForceFill == null) {
        sInstanceForNoRingNoForceFill = new AvatarCropTransformation(paramResources, false, paramResources.getColor(R.color.play_white), false);
      }
      AvatarCropTransformation localAvatarCropTransformation = sInstanceForNoRingNoForceFill;
      return localAvatarCropTransformation;
    }
    finally {}
  }
  
  public static AvatarCropTransformation getNoRingAvatarCrop(Resources paramResources, int paramInt)
  {
    try
    {
      checkConfigurationChanges(paramResources.getConfiguration());
      AvatarCropTransformation localAvatarCropTransformation = (AvatarCropTransformation)sInstancesForNoRingWithFill.get(Integer.valueOf(paramInt));
      if (localAvatarCropTransformation == null)
      {
        localAvatarCropTransformation = new AvatarCropTransformation(paramResources, false, paramInt, true);
        sInstancesForNoRingWithFill.put(Integer.valueOf(paramInt), localAvatarCropTransformation);
      }
      return localAvatarCropTransformation;
    }
    finally {}
  }
  
  private float getRingOutlineSize(int paramInt1, int paramInt2)
  {
    int i = Math.max(paramInt1, paramInt2);
    return interpolate(this.mDecorationThresholdMin, this.mDecorationThresholdMax, this.mRingSizeMin, this.mRingSizeMax, i);
  }
  
  private static float interpolate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    if (paramFloat5 <= paramFloat1) {}
    do
    {
      return paramFloat3;
      if (paramFloat5 >= paramFloat2) {
        return paramFloat4;
      }
    } while (paramFloat1 == paramFloat2);
    return paramFloat3 + (paramFloat5 - paramFloat1) * (paramFloat4 - paramFloat3) / (paramFloat2 - paramFloat1);
  }
  
  public final void drawFocusedOverlay(Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    float f1 = getDropShadowSize(paramInt1, paramInt2);
    int i = (int)(paramInt1 - f1);
    int j = (int)(paramInt2 - f1);
    paramCanvas.save();
    paramCanvas.translate(f1 / 2.0F, 0.0F);
    float f2 = Math.min(i / 2.0F, j / 2.0F);
    float f3 = this.mHighlightOutlinePaint.getStrokeWidth();
    this.mHighlightOutlinePaint.setColor(this.mFocusedOutlineColor);
    paramCanvas.drawCircle(f2, f2, f2 - f3 / 2.0F, this.mHighlightOutlinePaint);
    paramCanvas.restore();
  }
  
  public final void drawPressedOverlay(Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    float f1 = getDropShadowSize(paramInt1, paramInt2);
    int i = (int)(paramInt1 - f1);
    int j = (int)(paramInt2 - f1);
    paramCanvas.save();
    paramCanvas.translate(f1 / 2.0F, 0.0F);
    float f2 = Math.min(i / 2.0F, j / 2.0F);
    paramCanvas.drawCircle(f2, f2, f2, this.mPressedFillPaint);
    float f3 = this.mHighlightOutlinePaint.getStrokeWidth();
    this.mHighlightOutlinePaint.setColor(this.mPressedOutlineColor);
    paramCanvas.drawCircle(f2, f2, f2 - f3 / 2.0F, this.mHighlightOutlinePaint);
    paramCanvas.restore();
  }
  
  public final int getTransformationInset(int paramInt1, int paramInt2)
  {
    if (Math.max(paramInt1, paramInt2) < this.mDecorationThresholdMin) {
      return 0;
    }
    return (int)(2.0F * getRingOutlineSize(paramInt1, paramInt2) + getDropShadowSize(paramInt1, paramInt2));
  }
  
  public final Bitmap transform(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    if ((!this.mForceFill) && (paramBitmap == null)) {
      return null;
    }
    int i = Math.max(paramInt1, paramInt2);
    int j;
    float f1;
    label47:
    float f2;
    int n;
    int i1;
    label102:
    int i2;
    label109:
    int i5;
    if (i >= this.mDecorationThresholdMin)
    {
      j = 1;
      if (!this.mDrawRingAboveThreshold) {
        break label690;
      }
      f1 = getRingOutlineSize(paramInt1, paramInt2);
      f2 = getDropShadowSize(paramInt1, paramInt2);
      int k = (int)interpolate(this.mDecorationThresholdMin, this.mDecorationThresholdMax, 48.0F, 64.0F, i);
      int m = getTransformationInset(paramInt1, paramInt2);
      n = k << 24;
      if (paramBitmap != null) {
        break label696;
      }
      i1 = paramInt1;
      if (paramBitmap != null) {
        break label705;
      }
      i2 = paramInt2;
      int i3 = Math.max(i1, i2);
      int i4 = Math.min(i1, i2);
      if ((Math.abs(i1 - i2) <= 1) && (i3 >= i - m) && (i4 <= i) && (!this.mForceFill)) {
        break label746;
      }
      Bitmap localBitmap1 = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
      Canvas localCanvas1 = new Canvas(localBitmap1);
      localCanvas1.drawRect(0.0F, 0.0F, i, i, this.mFillToSizePaint);
      if (paramBitmap != null)
      {
        this.mSourceRectangle.set(0, 0, i1, i2);
        float f11 = i / Math.max(i1, i2);
        int i6 = (int)(f11 * i1);
        int i7 = (int)(f11 * i2);
        int i8 = (i - i6) / 2;
        int i9 = (i - i7) / 2;
        this.mDestinationRectangle.set(i8, i9, i8 + i6, i9 + i7);
        Rect localRect1 = this.mSourceRectangle;
        Rect localRect2 = this.mDestinationRectangle;
        Paint localPaint = this.mResizePaint;
        localCanvas1.drawBitmap(paramBitmap, localRect1, localRect2, localPaint);
      }
      paramBitmap = localBitmap1;
      if (localBitmap1 != null) {
        break label714;
      }
      i5 = paramInt1;
    }
    for (;;)
    {
      label332:
      Shader.TileMode localTileMode1 = Shader.TileMode.CLAMP;
      Shader.TileMode localTileMode2 = Shader.TileMode.CLAMP;
      BitmapShader localBitmapShader = new BitmapShader(paramBitmap, localTileMode1, localTileMode2);
      this.mCropPaint.setShader(localBitmapShader);
      Bitmap localBitmap2 = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
      Canvas localCanvas2 = new Canvas(localBitmap2);
      float f3 = i / 2.0F;
      float f4 = 1.0F;
      if (j != 0)
      {
        f4 = 1.0F + f2;
        localCanvas2.translate(f2 / 2.0F, 0.0F);
      }
      this.mRectangle.set(0.0F, 0.0F, paramInt1 - f4, paramInt1 - f4);
      if (j != 0)
      {
        drawOutline(localCanvas2, f2, n);
        float f5 = (i - f2 - Math.max(1.0F, 2.0F * f1)) / i5;
        drawAvatar(localCanvas2, i5, f3, f1, f5);
        if (this.mDrawRingAboveThreshold)
        {
          float f6 = this.mRectangle.left;
          float f7 = this.mRectangle.right;
          float f8 = this.mRectangle.top;
          float f9 = this.mRectangle.bottom;
          this.mDefaultRingPaint.setStrokeWidth(f1);
          float f10 = f1 / 2.0F;
          RectF localRectF1 = this.mRectangle;
          localRectF1.left = (f10 + localRectF1.left);
          RectF localRectF2 = this.mRectangle;
          localRectF2.top = (f10 + localRectF2.top);
          RectF localRectF3 = this.mRectangle;
          localRectF3.right -= f10;
          RectF localRectF4 = this.mRectangle;
          localRectF4.bottom -= f10;
          localCanvas2.drawOval(this.mRectangle, this.mDefaultRingPaint);
          this.mRectangle.left = f6;
          this.mRectangle.right = f7;
          this.mRectangle.top = f8;
          this.mRectangle.bottom = f9;
        }
      }
      for (;;)
      {
        this.mCropPaint.setShader(null);
        return localBitmap2;
        j = 0;
        break;
        label690:
        f1 = 0.0F;
        break label47;
        label696:
        i1 = paramBitmap.getWidth();
        break label102;
        label705:
        i2 = paramBitmap.getHeight();
        break label109;
        label714:
        i5 = paramBitmap.getWidth();
        break label332;
        drawAvatar(localCanvas2, i5, f3, 0.0F, 1.0F);
        drawOutline(localCanvas2, 0.0F, 0);
      }
      label746:
      i5 = i1;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.image.AvatarCropTransformation
 * JD-Core Version:    0.7.0.1
 */