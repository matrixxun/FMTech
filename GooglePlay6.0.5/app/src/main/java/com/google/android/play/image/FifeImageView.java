package com.google.android.play.image;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.google.android.play.R.anim;
import com.google.android.play.R.styleable;

public class FifeImageView
  extends ImageView
  implements BitmapLoader.BitmapLoadedHandler
{
  private static boolean IS_HC_OR_ABOVE;
  private static boolean IS_ICS_OR_ABOVE;
  BitmapLoader mBitmapLoader;
  public BitmapTransformation mBitmapTransformation;
  private boolean mBlockLayout;
  private Drawable mDefaultDrawable;
  private float mDefaultZoom;
  private int mDesiredHeight;
  private int mDesiredWidth;
  private final float mDpiScaleFactor;
  private Animation mFadeInAnimation;
  private Animation.AnimationListener mFadeInAnimationListener;
  private Animator.AnimatorListener mFadeInAnimatorListener;
  private int mFadeInDuration;
  private final PointF mFocusPoint = new PointF(0.5F, 0.5F);
  private boolean mForegroundBoundsChanged = false;
  private Drawable mForegroundDrawable;
  private Handler mHandler;
  private boolean mHasDefaultZoom;
  public boolean mIsFrozen;
  boolean mIsLoaded;
  private Bitmap mLocalBitmap;
  private final Matrix mMatrix = new Matrix();
  private boolean mMayBlockLayout;
  private OnLoadedListener mOnLoadedListener;
  private float mRequestScaleFactor;
  private final Rect mSelfBounds = new Rect();
  private boolean mSupportsFifeUrlOptions;
  private boolean mToFadeInAfterLoad;
  private String mUrl;
  private boolean mUseCachedPlaceholder;
  
  static
  {
    boolean bool1 = true;
    boolean bool2;
    if (Build.VERSION.SDK_INT >= 11)
    {
      bool2 = bool1;
      IS_HC_OR_ABOVE = bool2;
      if (Build.VERSION.SDK_INT < 14) {
        break label34;
      }
    }
    for (;;)
    {
      IS_ICS_OR_ABOVE = bool1;
      return;
      bool2 = false;
      break;
      label34:
      bool1 = false;
    }
  }
  
  public FifeImageView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public FifeImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public FifeImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray localTypedArray1 = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.FifeImageView);
    this.mToFadeInAfterLoad = localTypedArray1.getBoolean(R.styleable.FifeImageView_fade_in_after_load, true);
    this.mMayBlockLayout = localTypedArray1.getBoolean(R.styleable.FifeImageView_fixed_bounds, false);
    int i = getResources().getDisplayMetrics().densityDpi;
    label174:
    Resources localResources;
    if (localTypedArray1.hasValue(R.styleable.FifeImageView_cap_dpi))
    {
      this.mDpiScaleFactor = Math.min(localTypedArray1.getInt(R.styleable.FifeImageView_cap_dpi, i) / i, FifeUrlUtils.getDpiScaleFactor());
      this.mHasDefaultZoom = localTypedArray1.hasValue(R.styleable.FifeImageView_zoom);
      if (!this.mHasDefaultZoom) {
        break label301;
      }
      this.mDefaultZoom = localTypedArray1.getFraction(R.styleable.FifeImageView_zoom, 1, 1, 1.0F);
      super.setScaleType(ImageView.ScaleType.MATRIX);
      localResources = paramContext.getResources();
      if (!localTypedArray1.getBoolean(R.styleable.FifeImageView_is_avatar, false)) {
        break label309;
      }
      this.mBitmapTransformation = AvatarCropTransformation.getFullAvatarCrop(localResources);
    }
    for (;;)
    {
      this.mRequestScaleFactor = localTypedArray1.getFraction(R.styleable.FifeImageView_request_scale_factor, 1, 1, 1.0F);
      localTypedArray1.recycle();
      TypedArray localTypedArray2 = paramContext.obtainStyledAttributes(paramAttributeSet, new int[] { 16843017 });
      Drawable localDrawable = localTypedArray2.getDrawable(0);
      if (localDrawable != null) {
        setForeground(localDrawable);
      }
      localTypedArray2.recycle();
      this.mFadeInDuration = localResources.getInteger(17694720);
      this.mUseCachedPlaceholder = true;
      this.mHandler = new Handler(Looper.getMainLooper());
      return;
      this.mDpiScaleFactor = FifeUrlUtils.getDpiScaleFactor();
      break;
      label301:
      this.mDefaultZoom = 1.0F;
      break label174;
      label309:
      if (localTypedArray1.getBoolean(R.styleable.FifeImageView_is_avatar_no_ring, false)) {
        this.mBitmapTransformation = AvatarCropTransformation.getNoRingAvatarCrop(localResources);
      }
    }
  }
  
  private void blockLayoutIfPossible()
  {
    if (this.mMayBlockLayout) {
      this.mBlockLayout = true;
    }
  }
  
  private void invokeOnFadeInDone()
  {
    if (this.mOnLoadedListener != null) {
      this.mOnLoadedListener.onLoadedAndFadedIn(this);
    }
  }
  
  private void loadFromCache(Bitmap paramBitmap, boolean paramBoolean)
  {
    if (this.mIsFrozen) {
      return;
    }
    setImageBitmap(paramBitmap);
    setLoaded(paramBoolean, paramBitmap);
  }
  
  private void loadImageIfNecessary(boolean paramBoolean)
  {
    if (this.mIsFrozen) {}
    label66:
    label89:
    label115:
    label378:
    int i1;
    label320:
    label326:
    int i2;
    label397:
    label409:
    BitmapLoader.BitmapContainer localBitmapContainer2;
    do
    {
      ViewGroup.LayoutParams localLayoutParams;
      int k;
      int m;
      int n;
      boolean bool1;
      do
      {
        int i;
        int j;
        do
        {
          return;
          i = getWidth();
          j = getHeight();
        } while ((i == 0) && (j == 0));
        this.mDesiredWidth = 0;
        this.mDesiredHeight = 0;
        localLayoutParams = getLayoutParams();
        if (localLayoutParams.width != -1) {
          break;
        }
        k = 1;
        if (localLayoutParams.height != -1) {
          break label320;
        }
        m = 1;
        if ((k != 0) && (m != 0)) {
          break label378;
        }
        if (k == 0) {
          break label326;
        }
        this.mDesiredWidth = getWidth();
        if (this.mBitmapTransformation == null) {
          break label397;
        }
        n = this.mBitmapTransformation.getTransformationInset(this.mDesiredWidth, this.mDesiredHeight);
        if (this.mDesiredWidth > 0) {
          this.mDesiredWidth -= n;
        }
        if (this.mDesiredHeight > 0) {
          this.mDesiredHeight -= n;
        }
        float f1 = FifeUrlUtils.getNetworkScaleFactor(getContext());
        float f2 = this.mRequestScaleFactor;
        float f3 = this.mDpiScaleFactor * (f1 * f2);
        this.mDesiredWidth = ((int)(f3 * this.mDesiredWidth));
        this.mDesiredHeight = ((int)(f3 * this.mDesiredHeight));
        bool1 = TextUtils.isEmpty(this.mUrl);
        if ((!bool1) && ((this.mDesiredWidth > 0) || (this.mDesiredHeight > 0))) {
          break label409;
        }
        BitmapLoader.BitmapContainer localBitmapContainer1 = (BitmapLoader.BitmapContainer)getTag();
        if (localBitmapContainer1 != null)
        {
          localBitmapContainer1.cancelRequest();
          setImageBitmap(null);
        }
      } while ((!bool1) || (this.mBitmapTransformation == null));
      Bitmap localBitmap1 = this.mBitmapTransformation.transform(this.mLocalBitmap, getWidth(), getHeight());
      setImageBitmap(localBitmap1);
      if (localBitmap1 != null) {}
      for (boolean bool2 = true;; bool2 = false)
      {
        setLoaded(bool2, localBitmap1);
        return;
        k = 0;
        break;
        m = 0;
        break label66;
        if (localLayoutParams.width > 0)
        {
          this.mDesiredWidth = getWidth();
          if (localLayoutParams.height <= 0) {
            break label89;
          }
          this.mDesiredHeight = localLayoutParams.height;
          break label89;
        }
        if (m != 0)
        {
          this.mDesiredHeight = getHeight();
          break label89;
        }
        this.mDesiredWidth = getWidth();
        this.mDesiredHeight = getHeight();
        break label89;
        n = 0;
        break label115;
      }
      boolean bool3 = this.mSupportsFifeUrlOptions;
      i1 = 0;
      i2 = 0;
      if (bool3)
      {
        i1 = this.mDesiredWidth;
        i2 = this.mDesiredHeight;
      }
      localBitmapContainer2 = (BitmapLoader.BitmapContainer)getTag();
      if ((localBitmapContainer2 == null) || (localBitmapContainer2.mRequestUrl == null)) {
        break;
      }
    } while ((localBitmapContainer2.mRequestUrl.equals(this.mUrl)) && (localBitmapContainer2.mRequestWidth == i1) && (localBitmapContainer2.mRequestHeight == i2));
    localBitmapContainer2.cancelRequest();
    BitmapLoader.BitmapContainer localBitmapContainer3 = this.mBitmapLoader.get(this.mUrl, i1, i2, this.mUseCachedPlaceholder, this, false);
    setTag(localBitmapContainer3);
    Bitmap localBitmap2 = localBitmapContainer3.mBitmap;
    if (localBitmap2 != null)
    {
      if (this.mBitmapTransformation != null) {
        localBitmap2 = this.mBitmapTransformation.transform(localBitmap2, getWidth(), getHeight());
      }
      final Bitmap localBitmap3 = localBitmap2;
      if (localBitmap2 != null) {}
      for (final boolean bool4 = true; (!paramBoolean) || (this.mMayBlockLayout); bool4 = false)
      {
        loadFromCache(localBitmap2, bool4);
        return;
      }
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          FifeImageView.this.loadFromCache(localBitmap3, bool4);
        }
      });
      return;
    }
    setImageDrawable(this.mDefaultDrawable);
  }
  
  private void setLoaded(boolean paramBoolean, Bitmap paramBitmap)
  {
    try
    {
      this.mIsLoaded = paramBoolean;
      if ((this.mIsLoaded) && (this.mOnLoadedListener != null)) {
        this.mOnLoadedListener.onLoaded(this, paramBitmap);
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private void updateMatrix()
  {
    Drawable localDrawable = getDrawable();
    float f1 = getWidth();
    float f2 = getHeight();
    if ((localDrawable == null) || (f1 == 0.0F) || (f2 == 0.0F)) {
      return;
    }
    float f3 = localDrawable.getIntrinsicWidth();
    float f4 = localDrawable.getIntrinsicHeight();
    if ((f3 <= 0.0F) || (f4 <= 0.0F)) {
      this.mMatrix.reset();
    }
    for (;;)
    {
      super.setImageMatrix(this.mMatrix);
      return;
      float f5 = Math.max(f1 / f3, f2 / f4);
      float f6 = Math.max(f3 - f1 / f5, 0.0F);
      float f7 = Math.max(f4 - f2 / f5, 0.0F);
      float f8 = f6 * this.mFocusPoint.x;
      float f9 = f6 - f8;
      float f10 = f7 * this.mFocusPoint.y;
      float f11 = f7 - f10;
      float f12 = f3 * (this.mDefaultZoom - 1.0F);
      float f13 = f4 * (this.mDefaultZoom - 1.0F);
      RectF localRectF1 = new RectF(f8 + f12 / 2.0F, f10 + f13 / 2.0F, f3 - f9 - f12 / 2.0F, f4 - f11 - f13 / 2.0F);
      RectF localRectF2 = new RectF(0.0F, 0.0F, f1, f2);
      this.mMatrix.setRectToRect(localRectF1, localRectF2, Matrix.ScaleToFit.FILL);
    }
  }
  
  public void clearImage()
  {
    this.mDefaultDrawable = null;
    this.mUrl = null;
    BitmapLoader.BitmapContainer localBitmapContainer = (BitmapLoader.BitmapContainer)getTag();
    if (localBitmapContainer != null)
    {
      localBitmapContainer.cancelRequest();
      setTag(null);
    }
    setImageBitmap(null);
    setLoaded(false, null);
  }
  
  public void drawableHotspotChanged(float paramFloat1, float paramFloat2)
  {
    super.drawableHotspotChanged(paramFloat1, paramFloat2);
    if (this.mForegroundDrawable != null) {
      this.mForegroundDrawable.setHotspot(paramFloat1, paramFloat2);
    }
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    if ((this.mForegroundDrawable != null) && (this.mForegroundDrawable.isStateful())) {
      this.mForegroundDrawable.setState(getDrawableState());
    }
    if (this.mBitmapTransformation != null) {
      invalidate();
    }
  }
  
  public final boolean isLoaded()
  {
    try
    {
      boolean bool = this.mIsLoaded;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  @TargetApi(11)
  public void jumpDrawablesToCurrentState()
  {
    super.jumpDrawablesToCurrentState();
    if ((IS_HC_OR_ABOVE) && (this.mForegroundDrawable != null)) {
      this.mForegroundDrawable.jumpToCurrentState();
    }
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = getWidth();
    int j = getHeight();
    if (this.mBitmapTransformation == null) {
      if (this.mForegroundDrawable != null)
      {
        if (this.mForegroundBoundsChanged)
        {
          this.mForegroundBoundsChanged = false;
          Rect localRect = this.mSelfBounds;
          localRect.set(0, 0, getWidth(), getHeight());
          this.mForegroundDrawable.setBounds(localRect);
        }
        this.mForegroundDrawable.draw(paramCanvas);
      }
    }
    do
    {
      return;
      boolean bool1 = isPressed();
      int k = 0;
      if (bool1) {
        if (!isDuplicateParentStateEnabled())
        {
          boolean bool2 = isClickable();
          k = 0;
          if (!bool2) {}
        }
        else
        {
          k = 1;
        }
      }
      if (k != 0) {
        this.mBitmapTransformation.drawPressedOverlay(paramCanvas, i, j);
      }
    } while (!isFocused());
    this.mBitmapTransformation.drawFocusedOverlay(paramCanvas, i, j);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    loadImageIfNecessary(true);
    this.mForegroundBoundsChanged = true;
  }
  
  public final void onResponse(BitmapLoader.BitmapContainer paramBitmapContainer)
  {
    boolean bool1 = true;
    if (this.mIsFrozen) {
      return;
    }
    Bitmap localBitmap = paramBitmapContainer.mBitmap;
    if (localBitmap == null)
    {
      setLoaded(false, null);
      return;
    }
    if (this.mBitmapTransformation != null) {
      localBitmap = this.mBitmapTransformation.transform(localBitmap, getWidth(), getHeight());
    }
    boolean bool2;
    if (!this.mIsLoaded)
    {
      bool2 = bool1;
      setImageBitmap(localBitmap);
      if (localBitmap == null) {
        break label157;
      }
    }
    for (;;)
    {
      setLoaded(bool1, localBitmap);
      if ((!bool2) || (!this.mToFadeInAfterLoad)) {
        break label229;
      }
      if (!IS_ICS_OR_ABOVE) {
        break label162;
      }
      if ((this.mFadeInAnimatorListener == null) && (this.mOnLoadedListener != null)) {
        this.mFadeInAnimatorListener = new AnimatorListenerAdapter()
        {
          public final void onAnimationEnd(Animator paramAnonymousAnimator)
          {
            FifeImageView.this.invokeOnFadeInDone();
          }
        };
      }
      setAlpha(0.0F);
      animate().alpha(1.0F).setDuration(this.mFadeInDuration).setListener(this.mFadeInAnimatorListener);
      return;
      bool2 = false;
      break;
      label157:
      bool1 = false;
    }
    label162:
    if (this.mFadeInAnimation == null) {
      this.mFadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.play_fade_in);
    }
    if ((this.mFadeInAnimationListener == null) && (this.mOnLoadedListener != null)) {
      this.mFadeInAnimationListener = new Animation.AnimationListener()
      {
        public final void onAnimationEnd(Animation paramAnonymousAnimation)
        {
          FifeImageView.this.invokeOnFadeInDone();
        }
        
        public final void onAnimationRepeat(Animation paramAnonymousAnimation) {}
        
        public final void onAnimationStart(Animation paramAnonymousAnimation) {}
      };
    }
    this.mFadeInAnimation.setAnimationListener(this.mFadeInAnimationListener);
    startAnimation(this.mFadeInAnimation);
    return;
    label229:
    invokeOnFadeInDone();
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mHasDefaultZoom) {
      updateMatrix();
    }
    this.mForegroundBoundsChanged = true;
  }
  
  public void requestLayout()
  {
    if (!this.mBlockLayout) {
      super.requestLayout();
    }
  }
  
  public void setBitmapTransformation(BitmapTransformation paramBitmapTransformation)
  {
    this.mBitmapTransformation = paramBitmapTransformation;
    setWillNotDraw(false);
  }
  
  public void setDefaultDrawable(Drawable paramDrawable)
  {
    this.mDefaultDrawable = paramDrawable;
  }
  
  public void setDefaultZoom(float paramFloat)
  {
    if ((!this.mHasDefaultZoom) || (this.mDefaultZoom != paramFloat))
    {
      this.mHasDefaultZoom = true;
      this.mDefaultZoom = paramFloat;
      super.setScaleType(ImageView.ScaleType.MATRIX);
      updateMatrix();
    }
  }
  
  public void setForeground(Drawable paramDrawable)
  {
    if (this.mForegroundDrawable != paramDrawable)
    {
      if (this.mForegroundDrawable != null)
      {
        this.mForegroundDrawable.setCallback(null);
        unscheduleDrawable(this.mForegroundDrawable);
      }
      this.mForegroundDrawable = paramDrawable;
      if (paramDrawable == null) {
        break label75;
      }
      setWillNotDraw(false);
      paramDrawable.setCallback(this);
      if (paramDrawable.isStateful()) {
        paramDrawable.setState(getDrawableState());
      }
    }
    for (;;)
    {
      requestLayout();
      invalidate();
      return;
      label75:
      setWillNotDraw(true);
    }
  }
  
  public void setHasFixedBounds(boolean paramBoolean)
  {
    this.mMayBlockLayout = paramBoolean;
  }
  
  public final void setImage(String paramString, boolean paramBoolean, BitmapLoader paramBitmapLoader)
  {
    if (!paramString.equals(this.mUrl))
    {
      this.mUrl = paramString;
      this.mSupportsFifeUrlOptions = paramBoolean;
      setLoaded(false, null);
    }
    this.mBitmapLoader = paramBitmapLoader;
    loadImageIfNecessary(false);
  }
  
  public void setImageDrawable(Drawable paramDrawable)
  {
    blockLayoutIfPossible();
    super.setImageDrawable(paramDrawable);
    this.mBlockLayout = false;
    if (this.mHasDefaultZoom) {
      updateMatrix();
    }
  }
  
  public void setImageMatrix(Matrix paramMatrix)
  {
    if (this.mHasDefaultZoom) {
      throw new UnsupportedOperationException("Can't mix scale type and custom zoom");
    }
    super.setImageMatrix(paramMatrix);
  }
  
  public void setImageResource(int paramInt)
  {
    blockLayoutIfPossible();
    super.setImageResource(paramInt);
    this.mBlockLayout = false;
    if (this.mHasDefaultZoom) {
      updateMatrix();
    }
  }
  
  public void setImageURI(Uri paramUri)
  {
    blockLayoutIfPossible();
    super.setImageURI(paramUri);
    this.mBlockLayout = false;
  }
  
  public void setLocalImageBitmap(Bitmap paramBitmap)
  {
    this.mUrl = null;
    this.mLocalBitmap = paramBitmap;
    loadImageIfNecessary(false);
  }
  
  public void setOnLoadedListener(OnLoadedListener paramOnLoadedListener)
  {
    this.mOnLoadedListener = paramOnLoadedListener;
  }
  
  public void setScaleType(ImageView.ScaleType paramScaleType)
  {
    if (this.mHasDefaultZoom) {
      throw new UnsupportedOperationException("Can't mix scale type and custom zoom");
    }
    super.setScaleType(paramScaleType);
  }
  
  public void setToFadeInAfterLoad(boolean paramBoolean)
  {
    this.mToFadeInAfterLoad = paramBoolean;
  }
  
  public void setUseCachedPlaceholder(boolean paramBoolean)
  {
    this.mUseCachedPlaceholder = paramBoolean;
  }
  
  public void setVisibility(int paramInt)
  {
    super.setVisibility(paramInt);
    if (this.mForegroundDrawable != null) {
      if (paramInt != 0) {
        break label29;
      }
    }
    label29:
    for (boolean bool = true;; bool = false)
    {
      this.mForegroundDrawable.setVisible(bool, false);
      return;
    }
  }
  
  public final void unfreezeImage(boolean paramBoolean)
  {
    this.mIsFrozen = false;
    setTag(null);
    loadImageIfNecessary(paramBoolean);
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    return (super.verifyDrawable(paramDrawable)) || (paramDrawable == this.mForegroundDrawable);
  }
  
  public static abstract interface OnLoadedListener
  {
    public abstract void onLoaded(FifeImageView paramFifeImageView, Bitmap paramBitmap);
    
    public abstract void onLoadedAndFadedIn(FifeImageView paramFifeImageView);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.image.FifeImageView
 * JD-Core Version:    0.7.0.1
 */