package com.android.ex.photo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnSystemUiVisibilityChangeListener;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import com.android.ex.photo.adapters.PhotoPagerAdapter;
import com.android.ex.photo.fragments.PhotoViewFragment;
import com.android.ex.photo.loaders.PhotoBitmapLoader;
import com.android.ex.photo.loaders.PhotoBitmapLoaderInterface.BitmapResult;
import com.android.ex.photo.loaders.PhotoPagerLoader;
import com.android.ex.photo.util.ImageUtils;
import com.android.ex.photo.util.ImageUtils.ImageSize;
import com.android.ex.photo.util.Util;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PhotoViewController
  implements LoaderManager.LoaderCallbacks<Cursor>, ViewPager.OnPageChangeListener, ActionBarInterface.OnMenuVisibilityListener, PhotoViewCallbacks, PhotoViewPager.OnInterceptTouchListener
{
  public static int sMaxPhotoSize;
  public static int sMemoryClass;
  private boolean isTitleAnnounced;
  private final AccessibilityManager mAccessibilityManager;
  protected boolean mActionBarHiddenInitially;
  protected String mActionBarSubtitle;
  protected String mActionBarTitle;
  public final ActivityInterface mActivity;
  protected PhotoPagerAdapter mAdapter;
  protected int mAlbumCount = -1;
  protected int mAnimationStartHeight;
  protected int mAnimationStartWidth;
  protected int mAnimationStartX;
  protected int mAnimationStartY;
  protected View mBackground;
  protected BitmapCallback mBitmapCallback;
  private int mCurrentPhotoIndex;
  private String mCurrentPhotoUri;
  private final Set<PhotoViewCallbacks.CursorChangedListener> mCursorListeners = new HashSet();
  protected boolean mDisplayThumbsFullScreen;
  private boolean mEnterAnimationFinished;
  private long mEnterFullScreenDelayTime;
  private final Runnable mEnterFullScreenRunnable = new Runnable()
  {
    public final void run()
    {
      PhotoViewController.this.setFullScreen(true, true);
    }
  };
  protected boolean mFullScreen;
  protected final Handler mHandler = new Handler();
  private String mInitialPhotoUri;
  boolean mIsDestroyedCompat;
  protected boolean mIsEmpty;
  protected boolean mIsPaused = true;
  protected boolean mIsTimerLightsOutEnabled;
  private boolean mKickLoader;
  int mLastFlags;
  protected float mMaxInitialScale;
  private String mPhotosUri;
  private String[] mProjection;
  protected View mRootView;
  protected boolean mScaleAnimationEnabled;
  private final Map<Integer, PhotoViewCallbacks.OnScreenListener> mScreenListeners = new HashMap();
  private final View.OnSystemUiVisibilityChangeListener mSystemUiVisibilityChangeListener;
  protected ImageView mTemporaryImage;
  protected PhotoViewPager mViewPager;
  
  public PhotoViewController(ActivityInterface paramActivityInterface)
  {
    this.mActivity = paramActivityInterface;
    if (Build.VERSION.SDK_INT < 11) {}
    for (this.mSystemUiVisibilityChangeListener = null;; this.mSystemUiVisibilityChangeListener = new View.OnSystemUiVisibilityChangeListener()
        {
          public final void onSystemUiVisibilityChange(int paramAnonymousInt)
          {
            if ((Build.VERSION.SDK_INT >= 19) && (paramAnonymousInt == 0) && (PhotoViewController.this.mLastFlags == 3846)) {
              PhotoViewController.this.setFullScreen(false, true);
            }
          }
        })
    {
      this.mAccessibilityManager = ((AccessibilityManager)paramActivityInterface.getContext().getSystemService("accessibility"));
      return;
    }
  }
  
  private static int calculateTranslate(int paramInt1, int paramInt2, int paramInt3, float paramFloat)
  {
    int i = Math.round((paramInt3 - paramFloat * paramInt3) / 2.0F);
    int j = Math.round((paramFloat * paramInt3 - paramInt2) / 2.0F);
    return paramInt1 - i - j;
  }
  
  private void cancelEnterFullScreenRunnable()
  {
    this.mHandler.removeCallbacks(this.mEnterFullScreenRunnable);
  }
  
  private View findViewById(int paramInt)
  {
    return this.mActivity.findViewById(paramInt);
  }
  
  private Cursor getCursorAtProperPosition()
  {
    if (this.mViewPager == null) {
      return null;
    }
    int i = this.mViewPager.getCurrentItem();
    Cursor localCursor = this.mAdapter.getCursor();
    if (localCursor == null) {
      return null;
    }
    localCursor.moveToPosition(i);
    return localCursor;
  }
  
  private static final String getInputOrEmpty(String paramString)
  {
    if (paramString == null) {
      paramString = "";
    }
    return paramString;
  }
  
  private void notifyCursorListeners(Cursor paramCursor)
  {
    try
    {
      Iterator localIterator = this.mCursorListeners.iterator();
      while (localIterator.hasNext()) {
        ((PhotoViewCallbacks.CursorChangedListener)localIterator.next()).onCursorChanged(paramCursor);
      }
    }
    finally {}
  }
  
  private void postEnterFullScreenRunnableWithDelay()
  {
    if (this.mIsTimerLightsOutEnabled) {
      this.mHandler.postDelayed(this.mEnterFullScreenRunnable, this.mEnterFullScreenDelayTime);
    }
  }
  
  private void runEnterAnimation()
  {
    int i = this.mRootView.getMeasuredWidth();
    int j = this.mRootView.getMeasuredHeight();
    this.mTemporaryImage.setVisibility(0);
    float f = Math.max(this.mAnimationStartWidth / i, this.mAnimationStartHeight / j);
    int k = calculateTranslate(this.mAnimationStartX, this.mAnimationStartWidth, i, f);
    int m = calculateTranslate(this.mAnimationStartY, this.mAnimationStartHeight, j, f);
    int n = Build.VERSION.SDK_INT;
    if (n >= 14)
    {
      this.mBackground.setAlpha(0.0F);
      this.mBackground.animate().alpha(1.0F).setDuration(250L).start();
      this.mBackground.setVisibility(0);
      this.mTemporaryImage.setScaleX(f);
      this.mTemporaryImage.setScaleY(f);
      this.mTemporaryImage.setTranslationX(k);
      this.mTemporaryImage.setTranslationY(m);
      Runnable local3 = new Runnable()
      {
        public final void run()
        {
          PhotoViewController.this.onEnterAnimationComplete();
        }
      };
      ViewPropertyAnimator localViewPropertyAnimator = this.mTemporaryImage.animate().scaleX(1.0F).scaleY(1.0F).translationX(0.0F).translationY(0.0F).setDuration(250L);
      if (n >= 16) {
        localViewPropertyAnimator.withEndAction(local3);
      }
      for (;;)
      {
        localViewPropertyAnimator.start();
        return;
        this.mHandler.postDelayed(local3, 250L);
      }
    }
    AlphaAnimation localAlphaAnimation = new AlphaAnimation(0.0F, 1.0F);
    localAlphaAnimation.setDuration(250L);
    this.mBackground.startAnimation(localAlphaAnimation);
    this.mBackground.setVisibility(0);
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(k, m, 0.0F, 0.0F);
    localTranslateAnimation.setDuration(250L);
    ScaleAnimation localScaleAnimation = new ScaleAnimation(f, f, 0.0F, 0.0F);
    localScaleAnimation.setDuration(250L);
    AnimationSet localAnimationSet = new AnimationSet(true);
    localAnimationSet.addAnimation(localTranslateAnimation);
    localAnimationSet.addAnimation(localScaleAnimation);
    localAnimationSet.setAnimationListener(new Animation.AnimationListener()
    {
      public final void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        PhotoViewController.this.onEnterAnimationComplete();
      }
      
      public final void onAnimationRepeat(Animation paramAnonymousAnimation) {}
      
      public final void onAnimationStart(Animation paramAnonymousAnimation) {}
    });
    this.mTemporaryImage.startAnimation(localAnimationSet);
  }
  
  private void setActionBarTitles(ActionBarInterface paramActionBarInterface)
  {
    if (paramActionBarInterface == null) {
      return;
    }
    paramActionBarInterface.setTitle(getInputOrEmpty(this.mActionBarTitle));
    paramActionBarInterface.setSubtitle(getInputOrEmpty(this.mActionBarSubtitle));
  }
  
  private void setLightsOutMode(boolean paramBoolean)
  {
    int i = Build.VERSION.SDK_INT;
    int j;
    if (i < 16) {
      j = 1;
    }
    int m;
    int k;
    for (;;)
    {
      if ((paramBoolean) && ((!this.mScaleAnimationEnabled) || (this.mEnterAnimationFinished))) {
        if (i <= 19)
        {
          if (i != 19) {
            break label124;
          }
          if (Build.VERSION.SDK_INT != 19)
          {
            throw new IllegalStateException("kitkatIsSecondary user is only callable on KitKat");
            j = 0;
            continue;
          }
          if (Process.myUid() > 100000)
          {
            m = 1;
            if (m != 0) {
              break label124;
            }
          }
        }
        else
        {
          k = 3846;
          label88:
          if (j != 0) {
            hideActionBar();
          }
        }
      }
    }
    label223:
    for (;;)
    {
      if (i >= 11)
      {
        this.mLastFlags = k;
        this.mRootView.setSystemUiVisibility(k);
      }
      return;
      m = 0;
      break;
      label124:
      if (i >= 16)
      {
        k = 1285;
        break label88;
      }
      if (i >= 14)
      {
        k = 1;
        break label88;
      }
      k = 0;
      if (i < 11) {
        break label88;
      }
      k = 1;
      break label88;
      if (i >= 19) {
        k = 1792;
      }
      for (;;)
      {
        if (j == 0) {
          break label223;
        }
        showActionBar();
        break;
        if (i >= 16)
        {
          k = 1280;
        }
        else
        {
          k = 0;
          if (i < 14)
          {
            k = 0;
            if (i >= 11) {
              k = 0;
            }
          }
        }
      }
    }
  }
  
  private void setViewActivated(int paramInt)
  {
    PhotoViewCallbacks.OnScreenListener localOnScreenListener = (PhotoViewCallbacks.OnScreenListener)this.mScreenListeners.get(Integer.valueOf(paramInt));
    if (localOnScreenListener != null) {
      localOnScreenListener.onViewActivated();
    }
    Cursor localCursor1 = getCursorAtProperPosition();
    this.mCurrentPhotoIndex = paramInt;
    this.mCurrentPhotoUri = localCursor1.getString(localCursor1.getColumnIndex("uri"));
    int i = 1 + this.mViewPager.getCurrentItem();
    int j;
    label109:
    String str;
    label131:
    View localView;
    AccessibilityManager localAccessibilityManager;
    if (this.mAlbumCount >= 0)
    {
      j = 1;
      Cursor localCursor2 = getCursorAtProperPosition();
      if (localCursor2 == null) {
        break label278;
      }
      this.mActionBarTitle = localCursor2.getString(localCursor2.getColumnIndex("_display_name"));
      if ((!this.mIsEmpty) && (j != 0) && (i > 0)) {
        break label286;
      }
      this.mActionBarSubtitle = null;
      setActionBarTitles(this.mActivity.getActionBarInterface());
      if ((this.mAccessibilityManager.isEnabled()) && (!this.isTitleAnnounced))
      {
        str = this.mActionBarTitle;
        if (this.mActionBarSubtitle != null)
        {
          Resources localResources1 = this.mActivity.getContext().getResources();
          int k = R.string.titles;
          Object[] arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = this.mActionBarTitle;
          arrayOfObject1[1] = this.mActionBarSubtitle;
          str = localResources1.getString(k, arrayOfObject1);
        }
        if (str != null)
        {
          localView = this.mRootView;
          localAccessibilityManager = this.mAccessibilityManager;
          if (Build.VERSION.SDK_INT < 16) {
            break label344;
          }
          localView.announceForAccessibility(str);
        }
      }
    }
    for (;;)
    {
      this.isTitleAnnounced = true;
      cancelEnterFullScreenRunnable();
      postEnterFullScreenRunnableWithDelay();
      return;
      j = 0;
      break;
      label278:
      this.mActionBarTitle = null;
      break label109;
      label286:
      Resources localResources2 = this.mActivity.getResources();
      int m = R.string.photo_view_count;
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = Integer.valueOf(i);
      arrayOfObject2[1] = Integer.valueOf(this.mAlbumCount);
      this.mActionBarSubtitle = localResources2.getString(m, arrayOfObject2);
      break label131;
      label344:
      Context localContext = localView.getContext().getApplicationContext();
      if (localAccessibilityManager == null) {
        localAccessibilityManager = (AccessibilityManager)localContext.getSystemService("accessibility");
      }
      if (localAccessibilityManager.isEnabled())
      {
        AccessibilityEvent localAccessibilityEvent = AccessibilityEvent.obtain(8);
        localAccessibilityEvent.getText().add(str);
        localAccessibilityEvent.setEnabled(localView.isEnabled());
        localAccessibilityEvent.setClassName(localView.getClass().getName());
        localAccessibilityEvent.setPackageName(localContext.getPackageName());
        AccessibilityEventCompat.asRecord(localAccessibilityEvent).setSource(localView);
        if (Build.VERSION.SDK_INT >= 14) {
          localView.getParent().requestSendAccessibilityEvent(localView, localAccessibilityEvent);
        } else {
          localAccessibilityManager.sendAccessibilityEvent(localAccessibilityEvent);
        }
      }
    }
  }
  
  public final void addCursorListener(PhotoViewCallbacks.CursorChangedListener paramCursorChangedListener)
  {
    try
    {
      this.mCursorListeners.add(paramCursorChangedListener);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void addScreenListener(int paramInt, PhotoViewCallbacks.OnScreenListener paramOnScreenListener)
  {
    this.mScreenListeners.put(Integer.valueOf(paramInt), paramOnScreenListener);
  }
  
  public final PhotoPagerAdapter getAdapter()
  {
    return this.mAdapter;
  }
  
  public void hideActionBar()
  {
    this.mActivity.getActionBarInterface().hide();
  }
  
  public final boolean isFragmentActive(Fragment paramFragment)
  {
    if ((this.mViewPager == null) || (this.mAdapter == null)) {}
    while (this.mViewPager.getCurrentItem() != this.mAdapter.getItemPosition(paramFragment)) {
      return false;
    }
    return true;
  }
  
  public final boolean isFragmentFullScreen(Fragment paramFragment)
  {
    if ((this.mViewPager == null) || (this.mAdapter == null) || (this.mAdapter.getCount() == 0)) {
      return this.mFullScreen;
    }
    return (this.mFullScreen) || (this.mViewPager.getCurrentItem() != this.mAdapter.getItemPosition(paramFragment));
  }
  
  public final boolean onBackPressed()
  {
    if ((this.mFullScreen) && (!this.mActionBarHiddenInitially)) {
      toggleFullScreen();
    }
    for (;;)
    {
      boolean bool2 = true;
      boolean bool1;
      do
      {
        return bool2;
        bool1 = this.mScaleAnimationEnabled;
        bool2 = false;
      } while (!bool1);
      this.mActivity.getIntent();
      int i = this.mRootView.getMeasuredWidth();
      int j = this.mRootView.getMeasuredHeight();
      float f = Math.max(this.mAnimationStartWidth / i, this.mAnimationStartHeight / j);
      int k = calculateTranslate(this.mAnimationStartX, this.mAnimationStartWidth, i, f);
      int m = calculateTranslate(this.mAnimationStartY, this.mAnimationStartHeight, j, f);
      int n = Build.VERSION.SDK_INT;
      if (n >= 14)
      {
        this.mBackground.animate().alpha(0.0F).setDuration(250L).start();
        this.mBackground.setVisibility(0);
        Runnable local5 = new Runnable()
        {
          public final void run()
          {
            PhotoViewController.access$200(PhotoViewController.this);
          }
        };
        ViewPropertyAnimator localViewPropertyAnimator;
        if (this.mTemporaryImage.getVisibility() == 0)
        {
          localViewPropertyAnimator = this.mTemporaryImage.animate().scaleX(f).scaleY(f).translationX(k).translationY(m).setDuration(250L);
          label215:
          if (!this.mInitialPhotoUri.equals(this.mCurrentPhotoUri)) {
            localViewPropertyAnimator.alpha(0.0F);
          }
          if (n < 16) {
            break label299;
          }
          localViewPropertyAnimator.withEndAction(local5);
        }
        for (;;)
        {
          localViewPropertyAnimator.start();
          break;
          localViewPropertyAnimator = this.mViewPager.animate().scaleX(f).scaleY(f).translationX(k).translationY(m).setDuration(250L);
          break label215;
          label299:
          this.mHandler.postDelayed(local5, 250L);
        }
      }
      AlphaAnimation localAlphaAnimation = new AlphaAnimation(1.0F, 0.0F);
      localAlphaAnimation.setDuration(250L);
      this.mBackground.startAnimation(localAlphaAnimation);
      this.mBackground.setVisibility(0);
      ScaleAnimation localScaleAnimation = new ScaleAnimation(1.0F, 1.0F, f, f);
      localScaleAnimation.setDuration(250L);
      localScaleAnimation.setAnimationListener(new Animation.AnimationListener()
      {
        public final void onAnimationEnd(Animation paramAnonymousAnimation)
        {
          PhotoViewController.access$200(PhotoViewController.this);
        }
        
        public final void onAnimationRepeat(Animation paramAnonymousAnimation) {}
        
        public final void onAnimationStart(Animation paramAnonymousAnimation) {}
      });
      if (this.mTemporaryImage.getVisibility() == 0) {
        this.mTemporaryImage.startAnimation(localScaleAnimation);
      } else {
        this.mViewPager.startAnimation(localScaleAnimation);
      }
    }
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    boolean bool1 = true;
    DisplayMetrics localDisplayMetrics;
    ImageUtils.ImageSize localImageSize;
    if (sMaxPhotoSize == 0)
    {
      localDisplayMetrics = new DisplayMetrics();
      WindowManager localWindowManager = (WindowManager)this.mActivity.getContext().getSystemService("window");
      localImageSize = ImageUtils.sUseImageSize;
      localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
    }
    boolean bool2;
    label259:
    label301:
    label442:
    label481:
    Resources localResources;
    switch (8.$SwitchMap$com$android$ex$photo$util$ImageUtils$ImageSize[localImageSize.ordinal()])
    {
    default: 
      sMaxPhotoSize = Math.min(localDisplayMetrics.heightPixels, localDisplayMetrics.widthPixels);
      sMemoryClass = ((ActivityManager)this.mActivity.getApplicationContext().getSystemService("activity")).getMemoryClass();
      Intent localIntent = this.mActivity.getIntent();
      if (localIntent.hasExtra("photos_uri")) {
        this.mPhotosUri = localIntent.getStringExtra("photos_uri");
      }
      this.mIsTimerLightsOutEnabled = localIntent.getBooleanExtra("enable_timer_lights_out", bool1);
      if (localIntent.getBooleanExtra("scale_up_animation", false))
      {
        this.mScaleAnimationEnabled = bool1;
        this.mAnimationStartX = localIntent.getIntExtra("start_x_extra", 0);
        this.mAnimationStartY = localIntent.getIntExtra("start_y_extra", 0);
        this.mAnimationStartWidth = localIntent.getIntExtra("start_width_extra", 0);
        this.mAnimationStartHeight = localIntent.getIntExtra("start_height_extra", 0);
      }
      if ((localIntent.getBooleanExtra("action_bar_hidden_initially", false)) && (!Util.isTouchExplorationEnabled(this.mAccessibilityManager)))
      {
        bool2 = bool1;
        this.mActionBarHiddenInitially = bool2;
        this.mDisplayThumbsFullScreen = localIntent.getBooleanExtra("display_thumbs_fullscreen", false);
        if (!localIntent.hasExtra("projection")) {
          break label809;
        }
        this.mProjection = localIntent.getStringArrayExtra("projection");
        this.mMaxInitialScale = localIntent.getFloatExtra("max_scale", 1.0F);
        this.mCurrentPhotoUri = null;
        this.mCurrentPhotoIndex = -1;
        if (localIntent.hasExtra("photo_index")) {
          this.mCurrentPhotoIndex = localIntent.getIntExtra("photo_index", -1);
        }
        if (localIntent.hasExtra("initial_photo_uri"))
        {
          this.mInitialPhotoUri = localIntent.getStringExtra("initial_photo_uri");
          this.mCurrentPhotoUri = this.mInitialPhotoUri;
        }
        this.mIsEmpty = bool1;
        if (paramBundle == null) {
          break label822;
        }
        this.mInitialPhotoUri = paramBundle.getString("com.android.ex.PhotoViewFragment.INITIAL_URI");
        this.mCurrentPhotoUri = paramBundle.getString("com.android.ex.PhotoViewFragment.CURRENT_URI");
        this.mCurrentPhotoIndex = paramBundle.getInt("com.android.ex.PhotoViewFragment.CURRENT_INDEX");
        if ((!paramBundle.getBoolean("com.android.ex.PhotoViewFragment.FULLSCREEN", false)) || (Util.isTouchExplorationEnabled(this.mAccessibilityManager))) {
          break label817;
        }
        this.mFullScreen = bool1;
        this.mActionBarTitle = paramBundle.getString("com.android.ex.PhotoViewFragment.ACTIONBARTITLE");
        this.mActionBarSubtitle = paramBundle.getString("com.android.ex.PhotoViewFragment.ACTIONBARSUBTITLE");
        this.mEnterAnimationFinished = paramBundle.getBoolean("com.android.ex.PhotoViewFragment.SCALEANIMATIONFINISHED", false);
        this.mActivity.setContentView(R.layout.photo_activity_view);
        this.mAdapter = new PhotoPagerAdapter(this.mActivity.getContext(), this.mActivity.getSupportFragmentManager(), null, this.mMaxInitialScale, this.mDisplayThumbsFullScreen);
        localResources = this.mActivity.getResources();
        this.mRootView = findViewById(R.id.photo_activity_root_view);
        if (Build.VERSION.SDK_INT >= 11) {
          this.mRootView.setOnSystemUiVisibilityChangeListener(this.mSystemUiVisibilityChangeListener);
        }
        this.mBackground = findViewById(R.id.photo_activity_background);
        this.mTemporaryImage = ((ImageView)findViewById(R.id.photo_activity_temporary_image));
        this.mViewPager = ((PhotoViewPager)findViewById(R.id.photo_view_pager));
        this.mViewPager.setAdapter(this.mAdapter);
        this.mViewPager.setOnPageChangeListener(this);
        this.mViewPager.setOnInterceptTouchListener(this);
        this.mViewPager.setPageMargin(localResources.getDimensionPixelSize(R.dimen.photo_page_margin));
        this.mBitmapCallback = new BitmapCallback((byte)0);
        if ((this.mScaleAnimationEnabled) && (!this.mEnterAnimationFinished)) {
          break label833;
        }
        this.mActivity.getSupportLoaderManager().initLoader(100, null, this);
        this.mBackground.setVisibility(0);
      }
      break;
    }
    for (;;)
    {
      this.mEnterFullScreenDelayTime = localResources.getInteger(R.integer.reenter_fullscreen_delay_time_in_millis);
      ActionBarInterface localActionBarInterface = this.mActivity.getActionBarInterface();
      if (localActionBarInterface != null)
      {
        localActionBarInterface.setDisplayHomeAsUpEnabled$1385ff();
        localActionBarInterface.addOnMenuVisibilityListener(this);
        localActionBarInterface.setDisplayOptionsShowTitle();
        setActionBarTitles(localActionBarInterface);
      }
      if (this.mScaleAnimationEnabled) {
        break label886;
      }
      setLightsOutMode(this.mFullScreen);
      return;
      sMaxPhotoSize = 800 * Math.min(localDisplayMetrics.heightPixels, localDisplayMetrics.widthPixels) / 1000;
      break;
      bool2 = false;
      break label259;
      label809:
      this.mProjection = null;
      break label301;
      label817:
      bool1 = false;
      break label442;
      label822:
      this.mFullScreen = this.mActionBarHiddenInitially;
      break label481;
      label833:
      this.mViewPager.setVisibility(8);
      Bundle localBundle = new Bundle();
      localBundle.putString("image_uri", this.mInitialPhotoUri);
      this.mActivity.getSupportLoaderManager().initLoader(2, localBundle, this.mBitmapCallback);
    }
    label886:
    setLightsOutMode(false);
  }
  
  public Loader<PhotoBitmapLoaderInterface.BitmapResult> onCreateBitmapLoader$21c6b1c7(int paramInt, String paramString)
  {
    switch (paramInt)
    {
    default: 
      return null;
    }
    return new PhotoBitmapLoader(this.mActivity.getContext(), paramString);
  }
  
  public Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 100) {
      return new PhotoPagerLoader(this.mActivity.getContext(), Uri.parse(this.mPhotosUri), this.mProjection);
    }
    return null;
  }
  
  public final void onEnterAnimationComplete()
  {
    this.mEnterAnimationFinished = true;
    this.mViewPager.setVisibility(0);
    setLightsOutMode(this.mFullScreen);
  }
  
  public final void onFragmentPhotoLoadComplete(PhotoViewFragment paramPhotoViewFragment, boolean paramBoolean)
  {
    if ((this.mTemporaryImage.getVisibility() != 8) && (TextUtils.equals(paramPhotoViewFragment.getPhotoUri(), this.mCurrentPhotoUri)))
    {
      if (!paramBoolean) {
        break label60;
      }
      this.mTemporaryImage.setVisibility(8);
      this.mViewPager.setVisibility(0);
    }
    for (;;)
    {
      this.mActivity.getSupportLoaderManager().destroyLoader$13462e();
      return;
      label60:
      Log.w("PhotoViewController", "Failed to load fragment image");
      this.mTemporaryImage.setVisibility(8);
      this.mViewPager.setVisibility(0);
    }
  }
  
  public final void onLoaderReset$5dda1f52()
  {
    if (!this.mIsDestroyedCompat) {
      this.mAdapter.swapCursor(null);
    }
  }
  
  public final void onMenuVisibilityChanged(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      cancelEnterFullScreenRunnable();
      return;
    }
    postEnterFullScreenRunnableWithDelay();
  }
  
  public final void onPageScrollStateChanged(int paramInt) {}
  
  public final void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
    if (paramFloat < 0.0001D)
    {
      PhotoViewCallbacks.OnScreenListener localOnScreenListener1 = (PhotoViewCallbacks.OnScreenListener)this.mScreenListeners.get(Integer.valueOf(paramInt1 - 1));
      if (localOnScreenListener1 != null) {
        localOnScreenListener1.onViewUpNext();
      }
      PhotoViewCallbacks.OnScreenListener localOnScreenListener2 = (PhotoViewCallbacks.OnScreenListener)this.mScreenListeners.get(Integer.valueOf(paramInt1 + 1));
      if (localOnScreenListener2 != null) {
        localOnScreenListener2.onViewUpNext();
      }
    }
  }
  
  public final void onPageSelected(int paramInt)
  {
    this.mCurrentPhotoIndex = paramInt;
    setViewActivated(paramInt);
  }
  
  public final void onPause()
  {
    this.mIsPaused = true;
  }
  
  public final void onResume()
  {
    setFullScreen(this.mFullScreen, false);
    this.mIsPaused = false;
    if (this.mKickLoader)
    {
      this.mKickLoader = false;
      this.mActivity.getSupportLoaderManager().initLoader(100, null, this);
    }
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putString("com.android.ex.PhotoViewFragment.INITIAL_URI", this.mInitialPhotoUri);
    paramBundle.putString("com.android.ex.PhotoViewFragment.CURRENT_URI", this.mCurrentPhotoUri);
    paramBundle.putInt("com.android.ex.PhotoViewFragment.CURRENT_INDEX", this.mCurrentPhotoIndex);
    paramBundle.putBoolean("com.android.ex.PhotoViewFragment.FULLSCREEN", this.mFullScreen);
    paramBundle.putString("com.android.ex.PhotoViewFragment.ACTIONBARTITLE", this.mActionBarTitle);
    paramBundle.putString("com.android.ex.PhotoViewFragment.ACTIONBARSUBTITLE", this.mActionBarSubtitle);
    paramBundle.putBoolean("com.android.ex.PhotoViewFragment.SCALEANIMATIONFINISHED", this.mEnterAnimationFinished);
  }
  
  public final PhotoViewPager.InterceptType onTouchIntercept(float paramFloat1, float paramFloat2)
  {
    boolean bool1 = false;
    boolean bool2 = false;
    Iterator localIterator = this.mScreenListeners.values().iterator();
    while (localIterator.hasNext())
    {
      PhotoViewCallbacks.OnScreenListener localOnScreenListener = (PhotoViewCallbacks.OnScreenListener)localIterator.next();
      if (!bool1) {
        bool1 = localOnScreenListener.onInterceptMoveLeft$2548a39();
      }
      if (!bool2) {
        bool2 = localOnScreenListener.onInterceptMoveRight$2548a39();
      }
    }
    if (bool1)
    {
      if (bool2) {
        return PhotoViewPager.InterceptType.BOTH;
      }
      return PhotoViewPager.InterceptType.LEFT;
    }
    if (bool2) {
      return PhotoViewPager.InterceptType.RIGHT;
    }
    return PhotoViewPager.InterceptType.NONE;
  }
  
  public final void removeCursorListener(PhotoViewCallbacks.CursorChangedListener paramCursorChangedListener)
  {
    try
    {
      this.mCursorListeners.remove(paramCursorChangedListener);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void removeScreenListener(int paramInt)
  {
    this.mScreenListeners.remove(Integer.valueOf(paramInt));
  }
  
  protected final void setFullScreen(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (Util.isTouchExplorationEnabled(this.mAccessibilityManager))
    {
      paramBoolean1 = false;
      paramBoolean2 = false;
    }
    int i;
    if (paramBoolean1 != this.mFullScreen)
    {
      i = 1;
      this.mFullScreen = paramBoolean1;
      if (!this.mFullScreen) {
        break label98;
      }
      setLightsOutMode(true);
      cancelEnterFullScreenRunnable();
    }
    for (;;)
    {
      if (i == 0) {
        return;
      }
      Iterator localIterator = this.mScreenListeners.values().iterator();
      while (localIterator.hasNext()) {
        ((PhotoViewCallbacks.OnScreenListener)localIterator.next()).onFullScreenChanged$1385ff();
      }
      i = 0;
      break;
      label98:
      setLightsOutMode(false);
      if (paramBoolean2) {
        postEnterFullScreenRunnableWithDelay();
      }
    }
  }
  
  public void showActionBar()
  {
    this.mActivity.getActionBarInterface().show();
  }
  
  public final void toggleFullScreen()
  {
    if (!this.mFullScreen) {}
    for (boolean bool = true;; bool = false)
    {
      setFullScreen(bool, true);
      return;
    }
  }
  
  public static abstract interface ActivityInterface
  {
    public abstract View findViewById(int paramInt);
    
    public abstract void finish();
    
    public abstract ActionBarInterface getActionBarInterface();
    
    public abstract Context getApplicationContext();
    
    public abstract Context getContext();
    
    public abstract PhotoViewController getController();
    
    public abstract Intent getIntent();
    
    public abstract Resources getResources();
    
    public abstract FragmentManager getSupportFragmentManager();
    
    public abstract LoaderManager getSupportLoaderManager();
    
    public abstract void overridePendingTransition(int paramInt1, int paramInt2);
    
    public abstract void setContentView(int paramInt);
  }
  
  private final class BitmapCallback
    implements LoaderManager.LoaderCallbacks<PhotoBitmapLoaderInterface.BitmapResult>
  {
    private BitmapCallback() {}
    
    public final Loader<PhotoBitmapLoaderInterface.BitmapResult> onCreateLoader(int paramInt, Bundle paramBundle)
    {
      String str = paramBundle.getString("image_uri");
      switch (paramInt)
      {
      default: 
        return null;
      case 2: 
        return PhotoViewController.this.onCreateBitmapLoader$21c6b1c7(2, str);
      }
      return PhotoViewController.this.onCreateBitmapLoader$21c6b1c7(1, str);
    }
    
    public final void onLoaderReset$5dda1f52() {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.ex.photo.PhotoViewController
 * JD-Core Version:    0.7.0.1
 */