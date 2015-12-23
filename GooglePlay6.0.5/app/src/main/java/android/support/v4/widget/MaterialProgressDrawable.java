package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import java.util.ArrayList;

final class MaterialProgressDrawable
  extends Drawable
  implements Animatable
{
  private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
  private static final Interpolator MATERIAL_INTERPOLATOR = new FastOutSlowInInterpolator();
  private final int[] COLORS = { -16777216 };
  private Animation mAnimation;
  private final ArrayList<Animation> mAnimators = new ArrayList();
  private final Drawable.Callback mCallback = new Drawable.Callback()
  {
    public final void invalidateDrawable(Drawable paramAnonymousDrawable)
    {
      MaterialProgressDrawable.this.invalidateSelf();
    }
    
    public final void scheduleDrawable(Drawable paramAnonymousDrawable, Runnable paramAnonymousRunnable, long paramAnonymousLong)
    {
      MaterialProgressDrawable.this.scheduleSelf(paramAnonymousRunnable, paramAnonymousLong);
    }
    
    public final void unscheduleDrawable(Drawable paramAnonymousDrawable, Runnable paramAnonymousRunnable)
    {
      MaterialProgressDrawable.this.unscheduleSelf(paramAnonymousRunnable);
    }
  };
  boolean mFinishing;
  private double mHeight;
  private View mParent;
  private Resources mResources;
  final Ring mRing;
  private float mRotation;
  private float mRotationCount;
  private double mWidth;
  
  public MaterialProgressDrawable(Context paramContext, View paramView)
  {
    this.mParent = paramView;
    this.mResources = paramContext.getResources();
    this.mRing = new Ring(this.mCallback);
    this.mRing.setColors(this.COLORS);
    updateSizes(1);
    final Ring localRing = this.mRing;
    Animation local1 = new Animation()
    {
      public final void applyTransformation(float paramAnonymousFloat, Transformation paramAnonymousTransformation)
      {
        if (MaterialProgressDrawable.this.mFinishing)
        {
          MaterialProgressDrawable.access$000(MaterialProgressDrawable.this, paramAnonymousFloat, localRing);
          return;
        }
        float f1 = MaterialProgressDrawable.access$100$6f9943cf(localRing);
        float f2 = localRing.mStartingEndTrim;
        float f3 = localRing.mStartingStartTrim;
        float f4 = localRing.mStartingRotation;
        MaterialProgressDrawable.updateRingColor(MaterialProgressDrawable.this, paramAnonymousFloat, localRing);
        if (paramAnonymousFloat <= 0.5F)
        {
          float f10 = paramAnonymousFloat / 0.5F;
          float f11 = f3 + (0.8F - f1) * MaterialProgressDrawable.MATERIAL_INTERPOLATOR.getInterpolation(f10);
          localRing.setStartTrim(f11);
        }
        if (paramAnonymousFloat > 0.5F)
        {
          float f7 = 0.8F - f1;
          float f8 = (paramAnonymousFloat - 0.5F) / 0.5F;
          float f9 = f2 + f7 * MaterialProgressDrawable.MATERIAL_INTERPOLATOR.getInterpolation(f8);
          localRing.setEndTrim(f9);
        }
        float f5 = f4 + 0.25F * paramAnonymousFloat;
        localRing.setRotation(f5);
        float f6 = 216.0F * paramAnonymousFloat + 1080.0F * (MaterialProgressDrawable.this.mRotationCount / 5.0F);
        MaterialProgressDrawable.this.setRotation(f6);
      }
    };
    local1.setRepeatCount(-1);
    local1.setRepeatMode(1);
    local1.setInterpolator(LINEAR_INTERPOLATOR);
    local1.setAnimationListener(new Animation.AnimationListener()
    {
      public final void onAnimationEnd(Animation paramAnonymousAnimation) {}
      
      public final void onAnimationRepeat(Animation paramAnonymousAnimation)
      {
        localRing.storeOriginals();
        MaterialProgressDrawable.Ring localRing = localRing;
        localRing.setColorIndex(localRing.getNextColorIndex());
        localRing.setStartTrim(localRing.mEndTrim);
        if (MaterialProgressDrawable.this.mFinishing)
        {
          MaterialProgressDrawable.this.mFinishing = false;
          paramAnonymousAnimation.setDuration(1332L);
          localRing.setShowArrow(false);
          return;
        }
        MaterialProgressDrawable.access$402(MaterialProgressDrawable.this, (1.0F + MaterialProgressDrawable.this.mRotationCount) % 5.0F);
      }
      
      public final void onAnimationStart(Animation paramAnonymousAnimation)
      {
        MaterialProgressDrawable.access$402(MaterialProgressDrawable.this, 0.0F);
      }
    });
    this.mAnimation = local1;
  }
  
  private static float getMinProgressArc(Ring paramRing)
  {
    return (float)Math.toRadians(paramRing.mStrokeWidth / (6.283185307179586D * paramRing.mRingCenterRadius));
  }
  
  private void setSizeParameters(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, float paramFloat1, float paramFloat2)
  {
    Ring localRing = this.mRing;
    float f1 = this.mResources.getDisplayMetrics().density;
    this.mWidth = (paramDouble1 * f1);
    this.mHeight = (paramDouble2 * f1);
    float f2 = f1 * (float)paramDouble4;
    localRing.mStrokeWidth = f2;
    localRing.mPaint.setStrokeWidth(f2);
    localRing.invalidateSelf();
    localRing.mRingCenterRadius = (paramDouble3 * f1);
    localRing.setColorIndex(0);
    float f3 = paramFloat1 * f1;
    float f4 = paramFloat2 * f1;
    localRing.mArrowWidth = ((int)f3);
    localRing.mArrowHeight = ((int)f4);
    float f5 = Math.min((int)this.mWidth, (int)this.mHeight);
    if ((localRing.mRingCenterRadius <= 0.0D) || (f5 < 0.0F)) {}
    for (float f6 = (float)Math.ceil(localRing.mStrokeWidth / 2.0F);; f6 = (float)(f5 / 2.0F - localRing.mRingCenterRadius))
    {
      localRing.mStrokeInset = f6;
      return;
    }
  }
  
  private static void updateRingColor(float paramFloat, Ring paramRing)
  {
    if (paramFloat > 0.75F)
    {
      float f = (paramFloat - 0.75F) / 0.25F;
      int i = paramRing.mColors[paramRing.mColorIndex];
      int j = paramRing.mColors[paramRing.getNextColorIndex()];
      int k = Integer.valueOf(i).intValue();
      int m = 0xFF & k >> 24;
      int n = 0xFF & k >> 16;
      int i1 = 0xFF & k >> 8;
      int i2 = k & 0xFF;
      int i3 = Integer.valueOf(j).intValue();
      int i4 = 0xFF & i3 >> 24;
      int i5 = 0xFF & i3 >> 16;
      int i6 = 0xFF & i3 >> 8;
      int i7 = i3 & 0xFF;
      paramRing.mCurrentColor = (m + (int)(f * (i4 - m)) << 24 | n + (int)(f * (i5 - n)) << 16 | i1 + (int)(f * (i6 - i1)) << 8 | i2 + (int)(f * (i7 - i2)));
    }
  }
  
  public final void draw(Canvas paramCanvas)
  {
    Rect localRect = getBounds();
    int i = paramCanvas.save();
    paramCanvas.rotate(this.mRotation, localRect.exactCenterX(), localRect.exactCenterY());
    Ring localRing = this.mRing;
    RectF localRectF = localRing.mTempBounds;
    localRectF.set(localRect);
    localRectF.inset(localRing.mStrokeInset, localRing.mStrokeInset);
    float f1 = 360.0F * (localRing.mStartTrim + localRing.mRotation);
    float f2 = 360.0F * (localRing.mEndTrim + localRing.mRotation) - f1;
    localRing.mPaint.setColor(localRing.mCurrentColor);
    paramCanvas.drawArc(localRectF, f1, f2, false, localRing.mPaint);
    if (localRing.mShowArrow)
    {
      if (localRing.mArrow != null) {
        break label427;
      }
      localRing.mArrow = new Path();
      localRing.mArrow.setFillType(Path.FillType.EVEN_ODD);
    }
    for (;;)
    {
      float f3 = (int)localRing.mStrokeInset / 2 * localRing.mArrowScale;
      float f4 = (float)(localRing.mRingCenterRadius * Math.cos(0.0D) + localRect.exactCenterX());
      float f5 = (float)(localRing.mRingCenterRadius * Math.sin(0.0D) + localRect.exactCenterY());
      localRing.mArrow.moveTo(0.0F, 0.0F);
      localRing.mArrow.lineTo(localRing.mArrowWidth * localRing.mArrowScale, 0.0F);
      localRing.mArrow.lineTo(localRing.mArrowWidth * localRing.mArrowScale / 2.0F, localRing.mArrowHeight * localRing.mArrowScale);
      localRing.mArrow.offset(f4 - f3, f5);
      localRing.mArrow.close();
      localRing.mArrowPaint.setColor(localRing.mCurrentColor);
      paramCanvas.rotate(f1 + f2 - 5.0F, localRect.exactCenterX(), localRect.exactCenterY());
      paramCanvas.drawPath(localRing.mArrow, localRing.mArrowPaint);
      if (localRing.mAlpha < 255)
      {
        localRing.mCirclePaint.setColor(localRing.mBackgroundColor);
        localRing.mCirclePaint.setAlpha(255 - localRing.mAlpha);
        paramCanvas.drawCircle(localRect.exactCenterX(), localRect.exactCenterY(), localRect.width() / 2, localRing.mCirclePaint);
      }
      paramCanvas.restoreToCount(i);
      return;
      label427:
      localRing.mArrow.reset();
    }
  }
  
  public final int getAlpha()
  {
    return this.mRing.mAlpha;
  }
  
  public final int getIntrinsicHeight()
  {
    return (int)this.mHeight;
  }
  
  public final int getIntrinsicWidth()
  {
    return (int)this.mWidth;
  }
  
  public final int getOpacity()
  {
    return -3;
  }
  
  public final boolean isRunning()
  {
    ArrayList localArrayList = this.mAnimators;
    int i = localArrayList.size();
    for (int j = 0; j < i; j++)
    {
      Animation localAnimation = (Animation)localArrayList.get(j);
      if ((localAnimation.hasStarted()) && (!localAnimation.hasEnded())) {
        return true;
      }
    }
    return false;
  }
  
  public final void setAlpha(int paramInt)
  {
    this.mRing.mAlpha = paramInt;
  }
  
  public final void setArrowScale(float paramFloat)
  {
    Ring localRing = this.mRing;
    if (paramFloat != localRing.mArrowScale)
    {
      localRing.mArrowScale = paramFloat;
      localRing.invalidateSelf();
    }
  }
  
  public final void setBackgroundColor(int paramInt)
  {
    this.mRing.mBackgroundColor = paramInt;
  }
  
  public final void setColorFilter(ColorFilter paramColorFilter)
  {
    Ring localRing = this.mRing;
    localRing.mPaint.setColorFilter(paramColorFilter);
    localRing.invalidateSelf();
  }
  
  final void setRotation(float paramFloat)
  {
    this.mRotation = paramFloat;
    invalidateSelf();
  }
  
  public final void setStartEndTrim$2548a35(float paramFloat)
  {
    this.mRing.setStartTrim(0.0F);
    this.mRing.setEndTrim(paramFloat);
  }
  
  public final void showArrow(boolean paramBoolean)
  {
    this.mRing.setShowArrow(paramBoolean);
  }
  
  public final void start()
  {
    this.mAnimation.reset();
    this.mRing.storeOriginals();
    if (this.mRing.mEndTrim != this.mRing.mStartTrim)
    {
      this.mFinishing = true;
      this.mAnimation.setDuration(666L);
      this.mParent.startAnimation(this.mAnimation);
      return;
    }
    this.mRing.setColorIndex(0);
    this.mRing.resetOriginals();
    this.mAnimation.setDuration(1332L);
    this.mParent.startAnimation(this.mAnimation);
  }
  
  public final void stop()
  {
    this.mParent.clearAnimation();
    setRotation(0.0F);
    this.mRing.setShowArrow(false);
    this.mRing.setColorIndex(0);
    this.mRing.resetOriginals();
  }
  
  public final void updateSizes(int paramInt)
  {
    if (paramInt == 0)
    {
      setSizeParameters(56.0D, 56.0D, 12.5D, 3.0D, 12.0F, 6.0F);
      return;
    }
    setSizeParameters(40.0D, 40.0D, 8.75D, 2.5D, 10.0F, 5.0F);
  }
  
  private static final class Ring
  {
    int mAlpha;
    Path mArrow;
    int mArrowHeight;
    final Paint mArrowPaint = new Paint();
    float mArrowScale;
    int mArrowWidth;
    int mBackgroundColor;
    private final Drawable.Callback mCallback;
    final Paint mCirclePaint = new Paint(1);
    int mColorIndex;
    int[] mColors;
    int mCurrentColor;
    float mEndTrim = 0.0F;
    final Paint mPaint = new Paint();
    double mRingCenterRadius;
    float mRotation = 0.0F;
    boolean mShowArrow;
    float mStartTrim = 0.0F;
    float mStartingEndTrim;
    float mStartingRotation;
    float mStartingStartTrim;
    float mStrokeInset = 2.5F;
    float mStrokeWidth = 5.0F;
    final RectF mTempBounds = new RectF();
    
    public Ring(Drawable.Callback paramCallback)
    {
      this.mCallback = paramCallback;
      this.mPaint.setStrokeCap(Paint.Cap.SQUARE);
      this.mPaint.setAntiAlias(true);
      this.mPaint.setStyle(Paint.Style.STROKE);
      this.mArrowPaint.setStyle(Paint.Style.FILL);
      this.mArrowPaint.setAntiAlias(true);
    }
    
    final int getNextColorIndex()
    {
      return (1 + this.mColorIndex) % this.mColors.length;
    }
    
    final void invalidateSelf()
    {
      this.mCallback.invalidateDrawable(null);
    }
    
    public final void resetOriginals()
    {
      this.mStartingStartTrim = 0.0F;
      this.mStartingEndTrim = 0.0F;
      this.mStartingRotation = 0.0F;
      setStartTrim(0.0F);
      setEndTrim(0.0F);
      setRotation(0.0F);
    }
    
    public final void setColorIndex(int paramInt)
    {
      this.mColorIndex = paramInt;
      this.mCurrentColor = this.mColors[this.mColorIndex];
    }
    
    public final void setColors(int[] paramArrayOfInt)
    {
      this.mColors = paramArrayOfInt;
      setColorIndex(0);
    }
    
    public final void setEndTrim(float paramFloat)
    {
      this.mEndTrim = paramFloat;
      invalidateSelf();
    }
    
    public final void setRotation(float paramFloat)
    {
      this.mRotation = paramFloat;
      invalidateSelf();
    }
    
    public final void setShowArrow(boolean paramBoolean)
    {
      if (this.mShowArrow != paramBoolean)
      {
        this.mShowArrow = paramBoolean;
        invalidateSelf();
      }
    }
    
    public final void setStartTrim(float paramFloat)
    {
      this.mStartTrim = paramFloat;
      invalidateSelf();
    }
    
    public final void storeOriginals()
    {
      this.mStartingStartTrim = this.mStartTrim;
      this.mStartingEndTrim = this.mEndTrim;
      this.mStartingRotation = this.mRotation;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.widget.MaterialProgressDrawable
 * JD-Core Version:    0.7.0.1
 */