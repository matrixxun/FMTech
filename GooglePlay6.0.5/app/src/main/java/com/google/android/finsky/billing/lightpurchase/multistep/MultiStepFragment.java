package com.google.android.finsky.billing.lightpurchase.multistep;

import android.accounts.Account;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElementInfo;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.setup.SetupWizardNavBar;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.PlayAnimationUtils.AnimationListenerAdapter;
import com.google.android.finsky.utils.SetupWizardUtils;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.layout.PlayActionButton;

public abstract class MultiStepFragment
  extends Fragment
{
  public Account mAccount;
  private boolean mAlwaysShowPlayLogo;
  private boolean mAnimateContinueButtonBar;
  private boolean mButtonBarVisible;
  protected View mContinueButton;
  private View mContinueButtonBar;
  private View.OnClickListener mContinueButtonBarOnClickListener;
  public StepFragment<?> mCurrentFragment;
  public FinskyEventLog mEventLog;
  private View mFragmentContainer;
  public boolean mIsLoading;
  public View mMainView;
  private View mProgressBar;
  private final Runnable mProgressBarToFragmentTransition = new Runnable()
  {
    public final void run()
    {
      if (MultiStepFragment.this.mCurrentFragment == null)
      {
        FinskyLog.w("Current fragment null.", new Object[0]);
        return;
      }
      MultiStepFragment.this.fadeOutProgressBar();
      MultiStepFragment.access$100(MultiStepFragment.this);
      MultiStepFragment.this.syncButtonBar(MultiStepFragment.this.mCurrentFragment);
    }
  };
  protected View mSecondaryButton;
  
  private void fadeOutProgressBar()
  {
    fadeOutView(this.mProgressBar);
  }
  
  private void fadeOutView(final View paramView)
  {
    Animation localAnimation = AnimationUtils.loadAnimation(getActivity(), 2131034127);
    localAnimation.setAnimationListener(new PlayAnimationUtils.AnimationListenerAdapter()
    {
      public final void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        paramView.setVisibility(4);
      }
    });
    paramView.startAnimation(localAnimation);
  }
  
  private void syncButtonBar(StepFragment<?> paramStepFragment)
  {
    if ((paramStepFragment != null) && (!paramStepFragment.allowButtonBar()))
    {
      this.mContinueButtonBar.setVisibility(8);
      this.mButtonBarVisible = false;
      return;
    }
    if ((!this.mButtonBarVisible) && ((this.mAlwaysShowPlayLogo) || (paramStepFragment != null)) && (!this.mButtonBarVisible))
    {
      this.mButtonBarVisible = true;
      if (!this.mAlwaysShowPlayLogo) {
        break label191;
      }
      if (this.mContinueButtonBar.getVisibility() != 0) {
        break label157;
      }
      this.mContinueButton.startAnimation(AnimationUtils.loadAnimation(getActivity(), 2131034129));
    }
    for (;;)
    {
      String str1 = null;
      String str2 = null;
      if (paramStepFragment != null)
      {
        boolean bool = this.mIsLoading;
        str1 = null;
        str2 = null;
        if (!bool)
        {
          str1 = paramStepFragment.getContinueButtonLabel(getResources());
          getResources();
          str2 = paramStepFragment.getSecondaryButtonLabel$469752d4();
        }
      }
      syncButtonLabel(this.mContinueButton, str1);
      if (this.mSecondaryButton == null) {
        break;
      }
      syncButtonLabel(this.mSecondaryButton, str2);
      return;
      label157:
      this.mContinueButtonBar.setVisibility(0);
      if (this.mAnimateContinueButtonBar)
      {
        this.mContinueButtonBar.startAnimation(AnimationUtils.loadAnimation(getActivity(), 2131034129));
        continue;
        label191:
        if (!this.mIsLoading)
        {
          this.mContinueButtonBar.setVisibility(0);
          if (this.mAnimateContinueButtonBar) {
            this.mContinueButtonBar.startAnimation(AnimationUtils.loadAnimation(getActivity(), 2131034129));
          }
        }
      }
    }
  }
  
  private void syncButtonLabel(View paramView, String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      paramView.setVisibility(8);
    }
    do
    {
      return;
      paramView.setVisibility(0);
      if ((paramView instanceof PlayActionButton))
      {
        ((PlayActionButton)paramView).configure(getBackendId(), paramString, this.mContinueButtonBarOnClickListener);
        return;
      }
    } while (!(paramView instanceof Button));
    ((Button)paramView).setText(paramString);
  }
  
  public abstract int getBackendId();
  
  public final void hideLoading()
  {
    if (this.mCurrentFragment == null)
    {
      FinskyLog.wtf("Illegal state: hideLoading called without fragment.", new Object[0]);
      return;
    }
    this.mFragmentContainer.setVisibility(0);
    this.mFragmentContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), 2131034129));
    fadeOutProgressBar();
    this.mIsLoading = false;
    syncButtonBar(this.mCurrentFragment);
    logImpression(this.mCurrentFragment);
  }
  
  public final boolean isContinueButtonEnabled()
  {
    return this.mContinueButton.isEnabled();
  }
  
  public final void logClick(int paramInt, PlayStore.PlayStoreUiElementInfo paramPlayStoreUiElementInfo, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mEventLog.logClickEventWithClientCookie(paramInt, paramPlayStoreUiElementInfo, paramPlayStoreUiElementNode);
  }
  
  public final void logClick(int paramInt, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mEventLog.logClickEvent(paramInt, null, paramPlayStoreUiElementNode);
  }
  
  public final void logImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mEventLog.logPathImpression(0L, paramPlayStoreUiElementNode);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (this.mArguments.containsKey("MultiStepFragment.account")) {
      this.mAccount = ((Account)this.mArguments.getParcelable("MultiStepFragment.account"));
    }
    while (this.mAccount == null)
    {
      throw new IllegalStateException("No account specified.");
      if (this.mArguments.containsKey("authAccount")) {
        this.mAccount = AccountHandler.findAccount(this.mArguments.getString("authAccount"), FinskyApp.get());
      }
    }
    this.mEventLog = FinskyApp.get().getEventLogger(this.mAccount);
    if (paramBundle != null) {
      this.mIsLoading = paramBundle.getBoolean("MultiStepFragment.isLoading");
    }
    this.mAlwaysShowPlayLogo = FinskyApp.get().getExperiments().isEnabled(12602631L);
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("MultiStepFragment.isLoading", this.mIsLoading);
  }
  
  public void onStart()
  {
    super.onStart();
    this.mCurrentFragment = ((StepFragment)getChildFragmentManager().findFragmentById(2131755622));
    restoreUi();
  }
  
  public void onStop()
  {
    this.mMainView.removeCallbacks(this.mProgressBarToFragmentTransition);
    super.onStop();
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mMainView = paramView;
    SetupWizardNavBar localSetupWizardNavBar = SetupWizardUtils.getNavBarIfPossible(getActivity());
    if (localSetupWizardNavBar != null)
    {
      this.mAnimateContinueButtonBar = false;
      this.mContinueButtonBar = localSetupWizardNavBar.getView();
      this.mContinueButton = localSetupWizardNavBar.mNextButton;
    }
    for (this.mSecondaryButton = null;; this.mSecondaryButton = this.mMainView.findViewById(2131755633))
    {
      this.mContinueButtonBar.setVisibility(8);
      this.mContinueButtonBarOnClickListener = new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          if ((MultiStepFragment.this.mCurrentFragment != null) && (MultiStepFragment.this.mButtonBarVisible))
          {
            UiUtils.hideKeyboard(MultiStepFragment.this.getActivity(), MultiStepFragment.this.mCurrentFragment.mView);
            if (paramAnonymousView != MultiStepFragment.this.mContinueButton) {
              break label62;
            }
            MultiStepFragment.this.mCurrentFragment.onContinueButtonClicked();
          }
          label62:
          while (paramAnonymousView != MultiStepFragment.this.mSecondaryButton) {
            return;
          }
          MultiStepFragment.this.mCurrentFragment.onSecondaryButtonClicked();
        }
      };
      if (!(this.mContinueButton instanceof PlayActionButton)) {
        this.mContinueButton.setOnClickListener(this.mContinueButtonBarOnClickListener);
      }
      if ((this.mSecondaryButton != null) && (!(this.mSecondaryButton instanceof PlayActionButton))) {
        this.mSecondaryButton.setOnClickListener(this.mContinueButtonBarOnClickListener);
      }
      this.mProgressBar = this.mMainView.findViewById(2131755277);
      this.mFragmentContainer = this.mMainView.findViewById(2131755622);
      return;
      this.mAnimateContinueButtonBar = true;
      this.mContinueButtonBar = this.mMainView.findViewById(2131755629);
      this.mContinueButton = this.mMainView.findViewById(2131755631);
    }
  }
  
  public final void restoreUi()
  {
    if (this.mIsLoading) {
      this.mProgressBar.setVisibility(0);
    }
    for (;;)
    {
      syncButtonBar(this.mCurrentFragment);
      return;
      if (this.mCurrentFragment != null) {
        this.mFragmentContainer.setVisibility(0);
      }
    }
  }
  
  public final void setContinueButtonEnabled(boolean paramBoolean)
  {
    this.mContinueButton.setEnabled(paramBoolean);
  }
  
  public final void showLoading()
  {
    if (this.mButtonBarVisible)
    {
      if (!this.mAlwaysShowPlayLogo) {
        break label63;
      }
      if (this.mContinueButton.getVisibility() == 0)
      {
        this.mButtonBarVisible = false;
        if (!this.mAnimateContinueButtonBar) {
          break label52;
        }
        fadeOutView(this.mContinueButton);
      }
    }
    while (this.mIsLoading)
    {
      return;
      label52:
      this.mContinueButton.setVisibility(0);
      continue;
      label63:
      if (this.mButtonBarVisible)
      {
        this.mButtonBarVisible = false;
        if (this.mAnimateContinueButtonBar) {
          fadeOutView(this.mContinueButtonBar);
        } else {
          this.mContinueButton.setVisibility(0);
        }
      }
    }
    if (this.mCurrentFragment != null)
    {
      Animation localAnimation = AnimationUtils.loadAnimation(getActivity(), 2131034142);
      localAnimation.setAnimationListener(new PlayAnimationUtils.AnimationListenerAdapter()
      {
        public final void onAnimationEnd(Animation paramAnonymousAnimation)
        {
          MultiStepFragment.this.mFragmentContainer.setVisibility(4);
        }
      });
      this.mFragmentContainer.startAnimation(localAnimation);
      this.mProgressBar.setVisibility(0);
      this.mProgressBar.startAnimation(AnimationUtils.loadAnimation(getActivity(), 2131034139));
    }
    for (;;)
    {
      this.mIsLoading = true;
      this.mEventLog.logPathImpression$7d139cbf(213, (PlayStoreUiElementNode)getActivity());
      return;
      this.mFragmentContainer.setVisibility(4);
      this.mProgressBar.setVisibility(0);
      this.mProgressBar.startAnimation(AnimationUtils.loadAnimation(getActivity(), 2131034129));
    }
  }
  
  public final void showStep(StepFragment<?> paramStepFragment)
  {
    FragmentTransaction localFragmentTransaction = getChildFragmentManager().beginTransaction();
    if (this.mIsLoading)
    {
      this.mFragmentContainer.setVisibility(4);
      this.mMainView.postDelayed(this.mProgressBarToFragmentTransition, 100L);
    }
    for (;;)
    {
      if (this.mCurrentFragment != null) {
        localFragmentTransaction.remove(this.mCurrentFragment);
      }
      localFragmentTransaction.add(2131755622, paramStepFragment);
      localFragmentTransaction.commit();
      this.mCurrentFragment = paramStepFragment;
      this.mIsLoading = false;
      return;
      if (this.mCurrentFragment != null) {
        localFragmentTransaction.setCustomAnimations(2131034139, 2131034142);
      }
      this.mFragmentContainer.setVisibility(0);
      syncButtonBar(paramStepFragment);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.multistep.MultiStepFragment
 * JD-Core Version:    0.7.0.1
 */