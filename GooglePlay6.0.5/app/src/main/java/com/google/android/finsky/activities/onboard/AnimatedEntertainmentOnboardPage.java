package com.google.android.finsky.activities.onboard;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Property;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewOutlineProvider;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.layout.ControlsContainerBackground;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.LoadSVGTask;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.Utils;
import java.util.Locale;

public class AnimatedEntertainmentOnboardPage
  extends FrameLayout
{
  private static final boolean SUPPORT_ELEVATION;
  private final int mActiveTextColor;
  private final TransitionDrawable mAppsEntertainmentBackgroundTransition;
  private final Drawable mAppsGamesDrawable;
  private ImageView mAppsGamesImage;
  private TextView mAppsGamesTab;
  private TextView mAppsGamesWelcomeText;
  private ArgbEvaluator mButtonTextColorEvaluator;
  private final Drawable mEntertainmentDrawable;
  private ImageView mEntertainmentImage;
  private TextView mEntertainmentTab;
  private TextView mEntertainmentWelcomeText;
  private FinskyEventLog mEventLogger;
  private View mImageBox;
  private final float mImageHorizonRatio;
  private final int mInactiveTextColor;
  private Interpolator mInterpolatorFastOutSlowIn;
  private Interpolator mInterpolatorLinearOutSlowIn;
  private boolean mIsActivityResumed;
  private boolean mIsLaidOut;
  private boolean mIsRtL;
  private boolean mIsStateMachineRunning;
  LoadSVGTask mLoadAppsGamesImageTask;
  LoadSVGTask mLoadEntertainmentImageTask;
  private boolean mOnboardingV2;
  private Button mOpenButton;
  private PlayStoreUiElementNode mParentUiElementNode;
  private final TransitionDrawable mSplashAppsBackgroundTransition;
  private View mSplashImage;
  private View mSplashLogo;
  private int mState = 0;
  private final Handler mStateMachineHandler = new Handler();
  private ControlsContainerBackground mTabBackground;
  private View mTabBox;
  private View mTabRow;
  private View mTabShadow;
  private View mTabStrip;
  private final Property<TextView, Integer> mTextColorProperty;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 21) {}
    for (boolean bool = true;; bool = false)
    {
      SUPPORT_ELEVATION = bool;
      return;
    }
  }
  
  public AnimatedEntertainmentOnboardPage(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AnimatedEntertainmentOnboardPage(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public AnimatedEntertainmentOnboardPage(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Resources localResources = paramContext.getResources();
    if ((Build.VERSION.SDK_INT >= 17) && (getLayoutDirection() == 1)) {}
    for (boolean bool = true;; bool = false)
    {
      this.mIsRtL = bool;
      Drawable[] arrayOfDrawable1 = new Drawable[2];
      arrayOfDrawable1[0] = new ColorDrawable(localResources.getColor(2131689682));
      arrayOfDrawable1[1] = new ColorDrawable(localResources.getColor(2131689579));
      this.mSplashAppsBackgroundTransition = new TransitionDrawable(arrayOfDrawable1);
      Drawable[] arrayOfDrawable2 = new Drawable[2];
      arrayOfDrawable2[0] = new ColorDrawable(localResources.getColor(2131689579));
      arrayOfDrawable2[1] = new ColorDrawable(localResources.getColor(2131689578));
      this.mAppsEntertainmentBackgroundTransition = new TransitionDrawable(arrayOfDrawable2);
      setActivityBackground(this.mSplashAppsBackgroundTransition);
      this.mAppsGamesDrawable = new ColorDrawable(localResources.getColor(CorpusResourceUtils.getPrimaryColorResId(3)));
      this.mEntertainmentDrawable = new ColorDrawable(localResources.getColor(CorpusResourceUtils.getPrimaryColorResId(13)));
      this.mTextColorProperty = new Property(Integer.TYPE, "textColor") {};
      this.mActiveTextColor = localResources.getColor(2131689682);
      this.mInactiveTextColor = localResources.getColor(2131689683);
      TypedValue localTypedValue = new TypedValue();
      localResources.getValue(2131493356, localTypedValue, true);
      this.mImageHorizonRatio = localTypedValue.getFloat();
      return;
    }
  }
  
  private static void centerAbove(View paramView1, View paramView2)
  {
    float f = paramView2.getY() - paramView1.getBottom() - paramView1.getTop();
    if (f > 0.0F) {
      paramView1.setTranslationY(0.5F * f);
    }
  }
  
  private void changeState(int paramInt)
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(this.mState);
    arrayOfObject[1] = Integer.valueOf(paramInt);
    FinskyLog.d("Changing state from %d to %d", arrayOfObject);
    this.mState = paramInt;
    int i = this.mState;
    label68:
    label96:
    int j;
    label109:
    int k;
    label148:
    label161:
    label200:
    int m;
    label213:
    label252:
    int n;
    label266:
    label304:
    label332:
    TextView localTextView;
    Drawable localDrawable;
    label376:
    int i1;
    int i2;
    int i3;
    label463:
    int i4;
    int i5;
    int i6;
    label559:
    int i7;
    switch (i)
    {
    default: 
      switch (i)
      {
      default: 
        if (this.mIsRtL)
        {
          j = getWidth();
          switch (i)
          {
          default: 
            if (this.mIsRtL)
            {
              k = getWidth();
              switch (i)
              {
              case 2: 
              default: 
                if (this.mIsRtL)
                {
                  m = getWidth();
                  switch (i)
                  {
                  case 2: 
                  default: 
                    if (this.mIsRtL)
                    {
                      n = -getWidth();
                      switch (i)
                      {
                      case 2: 
                      case 3: 
                      default: 
                        switch (i)
                        {
                        case 2: 
                        default: 
                          localTextView = null;
                          localDrawable = null;
                          switch (i)
                          {
                          case 2: 
                          case 3: 
                          default: 
                            if ((localTextView != null) && (localDrawable != null))
                            {
                              int i9 = (int)localTextView.getX() + localTextView.getWidth() / 2;
                              this.mTabBackground.setBackgroundDrawable(localDrawable, i9, true);
                            }
                            i1 = this.mActiveTextColor;
                            switch (i)
                            {
                            case 3: 
                            default: 
                              i2 = i1;
                              i3 = 0;
                              if (i3 != 0)
                              {
                                ObjectAnimator localObjectAnimator2 = ObjectAnimator.ofInt(this.mAppsGamesTab, this.mTextColorProperty, new int[] { i2 }).setDuration(500L);
                                localObjectAnimator2.setEvaluator(this.mButtonTextColorEvaluator);
                                localObjectAnimator2.start();
                              }
                              i4 = this.mInactiveTextColor;
                              switch (i)
                              {
                              case 3: 
                              default: 
                                i5 = i4;
                                i6 = 0;
                                if (i6 != 0)
                                {
                                  ObjectAnimator localObjectAnimator1 = ObjectAnimator.ofInt(this.mEntertainmentTab, this.mTextColorProperty, new int[] { i5 }).setDuration(500L);
                                  localObjectAnimator1.setEvaluator(this.mButtonTextColorEvaluator);
                                  localObjectAnimator1.start();
                                }
                                i7 = this.mTabStrip.getWidth();
                                switch (i)
                                {
                                case 2: 
                                case 3: 
                                default: 
                                  label652:
                                  switch (i)
                                  {
                                  }
                                  break;
                                }
                                break;
                              }
                              break;
                            }
                            break;
                          }
                          break;
                        }
                        break;
                      }
                    }
                    break;
                  }
                }
                break;
              }
            }
            break;
          }
        }
        break;
      }
      break;
    }
    for (;;)
    {
      switch (this.mState)
      {
      default: 
        executeStateMachine();
        return;
        this.mSplashAppsBackgroundTransition.startTransition(500);
        break label68;
        setActivityBackground(this.mAppsEntertainmentBackgroundTransition);
        this.mAppsEntertainmentBackgroundTransition.startTransition(500);
        break label68;
        this.mAppsEntertainmentBackgroundTransition.reverseTransition(500);
        break label68;
        this.mSplashLogo.setAlpha(0.0F);
        break label96;
        this.mSplashLogo.animate().alpha(1.0F).setDuration(500L);
        break label96;
        this.mSplashLogo.animate().alpha(0.0F).setDuration(250L);
        break label96;
        j = -getWidth();
        break label109;
        float f2 = 0.5F * (getWidth() - this.mSplashImage.getWidth());
        float f3 = 0.5F * (getHeight() - this.mSplashImage.getHeight());
        int[] arrayOfInt = new int[2];
        this.mSplashImage.getLocationOnScreen(arrayOfInt);
        this.mSplashImage.setPivotX(0.5F * this.mSplashImage.getWidth());
        this.mSplashImage.setPivotY(0.5F * this.mSplashImage.getHeight());
        this.mSplashImage.setAlpha(0.0F);
        this.mSplashImage.setTranslationX(f2 - arrayOfInt[0]);
        this.mSplashImage.setTranslationY(f3 - arrayOfInt[1]);
        break label148;
        this.mSplashImage.animate().alpha(1.0F).setDuration(500L);
        break label148;
        float f1 = 0.24F * this.mAppsGamesImage.getWidth() / this.mSplashImage.getWidth();
        this.mSplashImage.setPivotY(this.mSplashImage.getHeight());
        this.mSplashImage.animate().scaleX(f1).scaleY(f1).translationX(0.0F).translationY(0.0F).setDuration(500L).setInterpolator(this.mInterpolatorLinearOutSlowIn);
        break label148;
        getFadeOutAnimation(this.mSplashImage).start();
        this.mSplashImage.animate().translationX(j).setDuration(500L).setInterpolator(this.mInterpolatorFastOutSlowIn);
        break label148;
        getFadeInAnimation(this.mSplashImage).start();
        this.mSplashImage.animate().translationX(0.0F);
        break label148;
        getFadeOutAnimation(this.mSplashImage).start();
        this.mSplashImage.animate().translationX(j);
        break label148;
        k = -getWidth();
        break label161;
        centerAbove(this.mAppsGamesWelcomeText, this.mImageBox);
        this.mAppsGamesWelcomeText.setAlpha(0.0F);
        break label200;
        this.mAppsGamesWelcomeText.animate().alpha(1.0F).setDuration(333L).setStartDelay(667L);
        if (!UiUtils.isAccessibilityEnabled(getContext())) {
          break label200;
        }
        this.mAppsGamesWelcomeText.sendAccessibilityEvent(32);
        break label200;
        getFadeOutAnimation(this.mAppsGamesWelcomeText).start();
        this.mAppsGamesWelcomeText.animate().translationX(k).setDuration(500L).setStartDelay(0L).setInterpolator(this.mInterpolatorFastOutSlowIn);
        break label200;
        getFadeInAnimation(this.mAppsGamesWelcomeText).start();
        this.mAppsGamesWelcomeText.animate().translationX(0.0F);
        break label200;
        getFadeOutAnimation(this.mAppsGamesWelcomeText).start();
        this.mAppsGamesWelcomeText.animate().translationX(k);
        break label200;
        m = -getWidth();
        break label213;
        this.mAppsGamesImage.setPivotX(0.5F * this.mAppsGamesImage.getWidth());
        this.mAppsGamesImage.setPivotY(this.mAppsGamesImage.getHeight() * (1.0F - this.mImageHorizonRatio));
        this.mAppsGamesImage.setScaleX(1.0F / this.mAppsGamesImage.getWidth());
        this.mAppsGamesImage.setScaleY(1.0F / this.mAppsGamesImage.getHeight());
        this.mAppsGamesImage.setAlpha(0.0F);
        this.mAppsGamesImage.animate().setInterpolator(this.mInterpolatorFastOutSlowIn);
        this.mLoadAppsGamesImageTask = new LoadSVGTask(getContext(), this.mAppsGamesImage.getWidth(), this.mAppsGamesImage.getHeight(), this.mAppsGamesImage);
        LoadSVGTask localLoadSVGTask2 = this.mLoadAppsGamesImageTask;
        Integer[] arrayOfInteger2 = new Integer[1];
        arrayOfInteger2[0] = Integer.valueOf(2131230725);
        Utils.executeMultiThreaded(localLoadSVGTask2, arrayOfInteger2);
        break label252;
        this.mAppsGamesImage.animate().alpha(1.0F).scaleX(1.0F).scaleY(1.0F).setDuration(500L).setStartDelay(333L);
        break label252;
        getFadeOutAnimation(this.mAppsGamesImage).start();
        this.mAppsGamesImage.animate().translationX(m).setStartDelay(0L);
        break label252;
        getFadeInAnimation(this.mAppsGamesImage).start();
        this.mAppsGamesImage.animate().translationX(0.0F);
        break label252;
        getFadeOutAnimation(this.mAppsGamesImage).start();
        this.mAppsGamesImage.animate().translationX(m);
        break label252;
        n = getWidth();
        break label266;
        centerAbove(this.mEntertainmentWelcomeText, this.mImageBox);
        this.mEntertainmentWelcomeText.setTranslationX(n);
        this.mEntertainmentImage.setTranslationX(n);
        this.mEntertainmentWelcomeText.setAlpha(0.0F);
        this.mEntertainmentImage.setAlpha(0.0F);
        this.mEntertainmentWelcomeText.animate().setInterpolator(this.mInterpolatorFastOutSlowIn);
        this.mEntertainmentImage.animate().setInterpolator(this.mInterpolatorFastOutSlowIn);
        this.mLoadEntertainmentImageTask = new LoadSVGTask(getContext(), this.mEntertainmentImage.getWidth(), this.mEntertainmentImage.getHeight(), this.mEntertainmentImage);
        LoadSVGTask localLoadSVGTask1 = this.mLoadEntertainmentImageTask;
        Integer[] arrayOfInteger1 = new Integer[1];
        arrayOfInteger1[0] = Integer.valueOf(2131230726);
        Utils.executeMultiThreaded(localLoadSVGTask1, arrayOfInteger1);
        break label304;
        getFadeInAnimation(this.mEntertainmentWelcomeText).start();
        this.mEntertainmentWelcomeText.animate().translationX(0.0F).setDuration(500L);
        getFadeInAnimation(this.mEntertainmentImage).start();
        this.mEntertainmentImage.animate().translationX(0.0F).setDuration(500L);
        if (!UiUtils.isAccessibilityEnabled(getContext())) {
          break label304;
        }
        this.mEntertainmentWelcomeText.sendAccessibilityEvent(32);
        break label304;
        getFadeOutAnimation(this.mEntertainmentWelcomeText).start();
        this.mEntertainmentWelcomeText.animate().translationX(n);
        getFadeOutAnimation(this.mEntertainmentImage).start();
        this.mEntertainmentImage.animate().translationX(n);
        break label304;
        getFadeInAnimation(this.mEntertainmentWelcomeText).start();
        this.mEntertainmentWelcomeText.animate().translationX(0.0F);
        getFadeInAnimation(this.mEntertainmentImage).start();
        this.mEntertainmentImage.animate().translationX(0.0F);
        break label304;
        this.mTabBox.setPivotX(0.5F * this.mTabBox.getWidth());
        this.mTabBox.setPivotY(0.0F);
        this.mTabBox.setScaleX(1.0F / this.mTabBox.getWidth());
        this.mTabBox.setScaleY(1.0F / this.mTabBox.getHeight());
        this.mTabBox.setAlpha(0.0F);
        this.mTabBox.animate().setInterpolator(this.mInterpolatorFastOutSlowIn);
        break label332;
        this.mTabBox.setAlpha(1.0F);
        this.mTabBox.animate().scaleX(1.0F).scaleY(1.0F).setStartDelay(500L).setDuration(500L);
        break label332;
        this.mTabBackground.setHeights(this.mTabRow.getHeight(), this.mTabRow.getHeight());
        this.mTabBackground.setBackgroundDrawable(this.mAppsGamesDrawable, 0, false);
        localTextView = null;
        localDrawable = null;
        break label376;
        localTextView = this.mEntertainmentTab;
        localDrawable = this.mEntertainmentDrawable;
        break label376;
        localTextView = this.mAppsGamesTab;
        localDrawable = this.mAppsGamesDrawable;
        break label376;
        localTextView = this.mEntertainmentTab;
        localDrawable = this.mEntertainmentDrawable;
        break label376;
        this.mAppsGamesTab.setTextColor(i1);
        i2 = i1;
        i3 = 0;
        break label463;
        i2 = this.mInactiveTextColor;
        i3 = 1;
        break label463;
        i2 = i1;
        i3 = 1;
        break label463;
        i2 = this.mInactiveTextColor;
        i3 = 1;
        break label463;
        this.mEntertainmentTab.setTextColor(i4);
        i5 = i4;
        i6 = 0;
        break label559;
        i5 = this.mActiveTextColor;
        i6 = 1;
        break label559;
        i5 = i4;
        i6 = 1;
        break label559;
        i5 = this.mActiveTextColor;
        i6 = 1;
        break label559;
        this.mTabStrip.animate().setInterpolator(this.mInterpolatorFastOutSlowIn);
        break label652;
        this.mTabStrip.animate().translationX(i7).setStartDelay(0L).setDuration(500L);
        break label652;
        this.mTabStrip.animate().translationX(0.0F);
        break label652;
        this.mTabStrip.animate().translationX(i7);
        break label652;
        this.mOpenButton.setEnabled(false);
        this.mOpenButton.setAlpha(0.0F);
        continue;
        if (this.mOnboardingV2)
        {
          showOpenButton();
          continue;
          if (!this.mOnboardingV2) {
            showOpenButton();
          }
        }
        break;
      }
    }
    int i8 = 5008;
    for (;;)
    {
      GenericUiElementNode localGenericUiElementNode = new GenericUiElementNode(i8, null, null, this.mParentUiElementNode);
      this.mParentUiElementNode.childImpression(localGenericUiElementNode);
      break;
      i8 = 5007;
      continue;
      i8 = 5006;
    }
  }
  
  private void executeStateMachine()
  {
    switch (this.mState)
    {
    default: 
    case 0: 
    case 1: 
    case 2: 
    case 3: 
    case 4: 
      do
      {
        return;
        changeState(1);
        return;
        scheduleStateChange(2, 500L);
        return;
        scheduleStateChange(3, 2333L);
        return;
        scheduleStateChange(4, 3167L);
        return;
      } while ((this.mOnboardingV2) || (UiUtils.isAccessibilityEnabled(getContext())));
      scheduleStateChange(5, 4500L);
      return;
    case 5: 
      scheduleStateChange(6, 4500L);
      return;
    }
    scheduleStateChange(5, 4500L);
  }
  
  private static ObjectAnimator getFadeInAnimation(View paramView)
  {
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramView, "alpha", new float[] { 0.0F, 1.0F }).setDuration(333L);
    localObjectAnimator.setStartDelay(167L);
    return localObjectAnimator;
  }
  
  private static ObjectAnimator getFadeOutAnimation(View paramView)
  {
    return ObjectAnimator.ofFloat(paramView, "alpha", new float[] { 1.0F, 0.0F }).setDuration(167L);
  }
  
  private void scheduleStateChange(final int paramInt, long paramLong)
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(paramInt);
    arrayOfObject[1] = Long.valueOf(paramLong);
    FinskyLog.d("scheduling state change to state %d in %d ms", arrayOfObject);
    this.mStateMachineHandler.postDelayed(new Runnable()
    {
      public final void run()
      {
        AnimatedEntertainmentOnboardPage.this.changeState(paramInt);
      }
    }, paramLong);
  }
  
  private void setActivityBackground(Drawable paramDrawable)
  {
    ((Activity)getContext()).getWindow().setBackgroundDrawable(paramDrawable);
  }
  
  private void showOpenButton()
  {
    int i;
    if (this.mOnboardingV2)
    {
      i = 333;
      if (!this.mOnboardingV2) {
        break label68;
      }
    }
    label68:
    for (int j = 667;; j = 1000)
    {
      this.mOpenButton.setEnabled(true);
      this.mOpenButton.animate().alpha(1.0F).setDuration(i).setStartDelay(j);
      this.mOpenButton.requestFocus();
      return;
      i = 500;
      break;
    }
  }
  
  private void updateStateMachine()
  {
    if ((this.mIsActivityResumed) && (this.mIsLaidOut)) {
      if (!this.mIsStateMachineRunning)
      {
        arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(this.mState);
        FinskyLog.d("resuming state machine from state %d", arrayOfObject2);
        this.mIsStateMachineRunning = true;
        executeStateMachine();
      }
    }
    while (!this.mIsStateMachineRunning)
    {
      Object[] arrayOfObject2;
      return;
    }
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Integer.valueOf(this.mState);
    FinskyLog.d("pausing state machine at state %d", arrayOfObject1);
    this.mStateMachineHandler.removeCallbacksAndMessages(null);
    this.mIsStateMachineRunning = false;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mImageBox = findViewById(2131755239);
    this.mSplashLogo = findViewById(2131755238);
    this.mSplashImage = findViewById(2131755243);
    this.mAppsGamesWelcomeText = ((TextView)findViewById(2131755251));
    this.mAppsGamesImage = ((ImageView)findViewById(2131755240));
    this.mEntertainmentImage = ((ImageView)findViewById(2131755241));
    this.mEntertainmentWelcomeText = ((TextView)findViewById(2131755252));
    this.mTabBackground = ((ControlsContainerBackground)findViewById(2131755245));
    this.mTabBox = findViewById(2131755244);
    this.mTabRow = findViewById(2131755247);
    this.mTabStrip = findViewById(2131755250);
    this.mTabShadow = findViewById(2131755246);
    this.mAppsGamesTab = ((TextView)findViewById(2131755248));
    this.mEntertainmentTab = ((TextView)findViewById(2131755249));
    this.mOpenButton = ((Button)findViewById(2131755254));
    this.mInterpolatorFastOutSlowIn = new FastOutSlowInInterpolator();
    this.mInterpolatorLinearOutSlowIn = new LinearOutSlowInInterpolator();
    this.mButtonTextColorEvaluator = new ArgbEvaluator();
    this.mOpenButton.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        AnimatedEntertainmentOnboardPage.this.mEventLogger.logClickEvent(5005, null, AnimatedEntertainmentOnboardPage.this.mParentUiElementNode);
        ((EntertainmentOnboardHostActivity)AnimatedEntertainmentOnboardPage.this.getContext()).finish();
      }
    });
    Locale localLocale = getResources().getConfiguration().locale;
    this.mOpenButton.setText(this.mOpenButton.getText().toString().toUpperCase(localLocale));
    if (SUPPORT_ELEVATION)
    {
      ViewOutlineProvider local3 = new ViewOutlineProvider()
      {
        public final void getOutline(View paramAnonymousView, Outline paramAnonymousOutline)
        {
          paramAnonymousOutline.setRect(0, 0, paramAnonymousView.getWidth(), paramAnonymousView.getHeight());
        }
      };
      this.mTabBackground.setOutlineProvider(local3);
      this.mTabShadow.setVisibility(8);
    }
    this.mEntertainmentWelcomeText.setText(CorpusResourceUtils.getEntertainmentOnboardingString(getContext(), FinskyApp.get().mToc));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mIsLaidOut = true;
    updateStateMachine();
  }
  
  public void onRtlPropertiesChanged(int paramInt)
  {
    int i = 1;
    super.onRtlPropertiesChanged(paramInt);
    if (paramInt == i) {}
    for (;;)
    {
      this.mIsRtL = i;
      return;
      i = 0;
    }
  }
  
  public void setEventLogger(FinskyEventLog paramFinskyEventLog)
  {
    this.mEventLogger = paramFinskyEventLog;
  }
  
  public void setIsActivityResumed(boolean paramBoolean)
  {
    this.mIsActivityResumed = paramBoolean;
    updateStateMachine();
  }
  
  public void setOnboardingV2(boolean paramBoolean)
  {
    this.mOnboardingV2 = paramBoolean;
  }
  
  public void setParentUiElementNode(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mParentUiElementNode = paramPlayStoreUiElementNode;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.onboard.AnimatedEntertainmentOnboardPage
 * JD-Core Version:    0.7.0.1
 */