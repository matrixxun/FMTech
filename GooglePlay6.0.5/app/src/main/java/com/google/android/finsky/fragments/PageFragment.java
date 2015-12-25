package com.google.android.finsky.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.activities.AuthenticatedActivity;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.layout.ContentFrame;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.layout.LayoutSwitcher.RetryButtonListener;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.layout.actionbar.ActionBarController.ActionBarSearchModeListener;
import com.google.android.finsky.layout.actionbar.FinskySearchToolbar;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.layout.play.RootUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.navigationmanager.NavigationState;
import com.google.android.finsky.transition.PageFade;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.headerlist.PlayHeaderListLayout;
import com.google.android.play.image.BitmapLoader;
import java.util.Stack;

public abstract class PageFragment
  extends Fragment
  implements Response.ErrorListener, OnDataChangedListener, LayoutSwitcher.RetryButtonListener, ActionBarController.ActionBarSearchModeListener, RootUiElementNode
{
  public ActionBarController mActionBarController;
  public Runnable mAttachToFrameRunnable;
  public BitmapLoader mBitmapLoader;
  public Context mContext;
  public ViewGroup mDataView;
  private String mDfeAccount;
  public DfeApi mDfeApi;
  public DfeToc mDfeToc;
  private Handler mImpressionHandler;
  private long mImpressionId = FinskyEventLog.getNextImpressionId();
  public LayoutSwitcher mLayoutSwitcher;
  public NavigationManager mNavigationManager;
  public PageFragmentHost mPageFragmentHost;
  protected boolean mSaveInstanceStateCalled;
  
  public PageFragment()
  {
    setArguments(new Bundle());
  }
  
  public final boolean canChangeFragmentManagerState()
  {
    FragmentActivity localFragmentActivity = getActivity();
    return (!this.mSaveInstanceStateCalled) && (localFragmentActivity != null) && ((!(localFragmentActivity instanceof AuthenticatedActivity)) || (!((AuthenticatedActivity)localFragmentActivity).mStateSaved));
  }
  
  public void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyEventLog.rootImpression(this.mImpressionHandler, this.mImpressionId, this, paramPlayStoreUiElementNode);
  }
  
  public LayoutSwitcher createLayoutSwitcher(ContentFrame paramContentFrame)
  {
    return new LayoutSwitcher(paramContentFrame, 2131755586, 2131755806, 2131755289, this, 2);
  }
  
  public void flushImpression()
  {
    FinskyEventLog.flushImpression(this.mImpressionHandler, this.mImpressionId, this);
  }
  
  public int getActionBarColor()
  {
    return CorpusResourceUtils.getPrimaryColor(this.mContext, 0);
  }
  
  public int getActionBarTitleColor()
  {
    return this.mContext.getResources().getColor(2131689682);
  }
  
  @TargetApi(22)
  public Transition getCustomExitTransition()
  {
    return new PageFade(0);
  }
  
  public int getDefaultHeaderShadowMode()
  {
    return 1;
  }
  
  public abstract int getLayoutRes();
  
  public final PlayStoreUiElementNode getParentNode()
  {
    return null;
  }
  
  @TargetApi(19)
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    if ((PageFragmentHost)getActivity() != this.mPageFragmentHost)
    {
      this.mPageFragmentHost = ((PageFragmentHost)getActivity());
      this.mContext = getActivity();
      this.mNavigationManager = this.mPageFragmentHost.getNavigationManager();
      this.mActionBarController = this.mPageFragmentHost.getActionBarController();
      this.mDfeApi = this.mPageFragmentHost.getDfeApi(this.mDfeAccount);
      this.mBitmapLoader = this.mPageFragmentHost.getBitmapLoader();
    }
    this.mSaveInstanceStateCalled = false;
    if (this.mActionBarController != null) {
      this.mActionBarController.setActionBarSearchModeListener(this);
    }
    if (this.mFragmentManager.findFragmentByTag("hats-survey") == null) {
      this.mPageFragmentHost.maybeShowSatisfactionSurvey$377e3174(this);
    }
    if (NavigationManager.areTransitionsEnabled())
    {
      Transition localTransition = getCustomExitTransition();
      if (localTransition != null) {
        this.mExitTransition = localTransition.setDuration(400L);
      }
    }
    FinskyLog.logTiming("Views bound", new Object[0]);
  }
  
  public void onAttach(Activity paramActivity)
  {
    this.mImpressionHandler = new Handler(paramActivity.getMainLooper());
    super.onAttach(paramActivity);
  }
  
  public boolean onBackPressed()
  {
    return false;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mDfeAccount = this.mArguments.getString("finsky.PageFragment.dfeAccount");
    this.mDfeToc = ((DfeToc)this.mArguments.getParcelable("finsky.PageFragment.toc"));
    this.mSaveInstanceStateCalled = false;
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    final ContentFrame localContentFrame = (ContentFrame)paramLayoutInflater.inflate(2130968779, paramViewGroup, false);
    this.mDataView = localContentFrame.inflateDataLayout(paramLayoutInflater, getLayoutRes(), 2131755586);
    this.mAttachToFrameRunnable = new Runnable()
    {
      public final void run()
      {
        if (PageFragment.this.mDataView != null) {
          localContentFrame.addView(PageFragment.this.mDataView);
        }
      }
    };
    if (!shouldDelayAttachingDataView()) {
      this.mAttachToFrameRunnable.run();
    }
    this.mSaveInstanceStateCalled = false;
    this.mLayoutSwitcher = createLayoutSwitcher(localContentFrame);
    FinskyLog.logTiming("Views inflated", new Object[0]);
    return localContentFrame;
  }
  
  public void onDataChanged()
  {
    if (isAdded())
    {
      this.mLayoutSwitcher.switchToDataMode();
      rebindViews();
      FinskyLog.logTiming("Views rebound", new Object[0]);
    }
    if (this.mPageFragmentHost != null) {
      this.mPageFragmentHost.maybeShowSatisfactionSurvey$377e3174(this);
    }
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    this.mDataView = null;
    this.mLayoutSwitcher = null;
    this.mAttachToFrameRunnable = null;
    if (this.mActionBarController != null) {
      this.mActionBarController.setActionBarSearchModeListener(null);
    }
  }
  
  public void onEnterActionBarSearchMode()
  {
    if ((this.mDataView instanceof PlayHeaderListLayout))
    {
      PlayHeaderListLayout localPlayHeaderListLayout = (PlayHeaderListLayout)this.mDataView;
      localPlayHeaderListLayout.setFloatingControlsBackground(new ColorDrawable(0), true);
      localPlayHeaderListLayout.setHeaderShadowMode(2);
    }
  }
  
  public void onErrorResponse(VolleyError paramVolleyError)
  {
    if (canChangeFragmentManagerState()) {
      switchToError(ErrorStrings.get(this.mContext, paramVolleyError));
    }
  }
  
  public void onExitActionBarSearchMode()
  {
    if ((this.mDataView instanceof PlayHeaderListLayout))
    {
      final PlayHeaderListLayout localPlayHeaderListLayout = (PlayHeaderListLayout)this.mDataView;
      localPlayHeaderListLayout.setFloatingControlsBackground(new ColorDrawable(getActionBarColor()), true);
      localPlayHeaderListLayout.postDelayed(new Runnable()
      {
        public final void run()
        {
          localPlayHeaderListLayout.setHeaderShadowMode(PageFragment.this.getDefaultHeaderShadowMode());
        }
      }, 200L);
    }
  }
  
  public void onResume()
  {
    super.onResume();
    FinskyEventLog.startNewImpression(this);
    this.mSaveInstanceStateCalled = false;
  }
  
  public final void onRetry()
  {
    requestData();
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    this.mSaveInstanceStateCalled = true;
  }
  
  public final void onStop()
  {
    int i = 0;
    FinskyHeaderListLayout localFinskyHeaderListLayout;
    Object localObject;
    if ((this.mDataView instanceof FinskyHeaderListLayout))
    {
      localFinskyHeaderListLayout = (FinskyHeaderListLayout)this.mDataView;
      FinskySearchToolbar localFinskySearchToolbar = (FinskySearchToolbar)localFinskyHeaderListLayout.getToolbar();
      if ((localFinskySearchToolbar != null) && (!localFinskySearchToolbar.isSearchBoxPresent())) {
        break label85;
      }
      localObject = this.mNavigationManager;
    }
    for (;;)
    {
      int j = ((NavigationManager)localObject).mBackStack.size();
      if (j >= 2) {
        ((NavigationState)((NavigationManager)localObject).mBackStack.elementAt(j - 2)).drawerIconStateSwitchType = i;
      }
      super.onStop();
      return;
      label85:
      boolean bool = localFinskyHeaderListLayout.getActionBarTranslationY() < 0.0F;
      int k = 0;
      if (!bool) {
        k = 1;
      }
      NavigationManager localNavigationManager = this.mNavigationManager;
      if (k != 0)
      {
        i = 2;
        localObject = localNavigationManager;
      }
      else
      {
        i = 1;
        localObject = localNavigationManager;
      }
    }
  }
  
  public void rebindActionBar() {}
  
  public abstract void rebindViews();
  
  public abstract void requestData();
  
  public final void setArgument(String paramString, Parcelable paramParcelable)
  {
    this.mArguments.putParcelable(paramString, paramParcelable);
  }
  
  public final void setArgument(String paramString1, String paramString2)
  {
    this.mArguments.putString(paramString1, paramString2);
  }
  
  public final void setArgument(String paramString, boolean paramBoolean)
  {
    this.mArguments.putBoolean(paramString, paramBoolean);
  }
  
  public final void setDfeAccount(String paramString)
  {
    if (!TextUtils.isEmpty(paramString)) {
      setArgument("finsky.PageFragment.dfeAccount", paramString);
    }
  }
  
  public boolean shouldDelayAttachingDataView()
  {
    return false;
  }
  
  public void startNewImpression()
  {
    this.mImpressionId = FinskyEventLog.getNextImpressionId();
  }
  
  public final void switchToBlank()
  {
    this.mLayoutSwitcher.performSwitch(3, null);
  }
  
  public final void switchToError(String paramString)
  {
    if (this.mLayoutSwitcher == null)
    {
      FragmentActivity localFragmentActivity = getActivity();
      if (localFragmentActivity == null) {}
      for (boolean bool1 = true;; bool1 = false)
      {
        boolean bool2 = false;
        boolean bool3 = false;
        if (!bool1)
        {
          bool2 = localFragmentActivity instanceof AuthenticatedActivity;
          bool3 = false;
          if (bool2) {
            bool3 = ((AuthenticatedActivity)localFragmentActivity).mStateSaved;
          }
        }
        Object[] arrayOfObject = new Object[4];
        arrayOfObject[0] = Boolean.valueOf(this.mSaveInstanceStateCalled);
        arrayOfObject[1] = Boolean.valueOf(bool1);
        arrayOfObject[2] = Boolean.valueOf(bool2);
        arrayOfObject[3] = Boolean.valueOf(bool3);
        FinskyLog.wtf("mSaveInstanceStateCalled=[%s], activityNull=[%s], isAuthenticatedActivity=[%s], isStateSaved=[%s]", arrayOfObject);
        return;
      }
    }
    this.mLayoutSwitcher.switchToErrorMode(paramString);
  }
  
  public final void switchToLoading()
  {
    this.mLayoutSwitcher.switchToLoadingDelayed(350);
  }
}



/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar

 * Qualified Name:     com.google.android.finsky.fragments.PageFragment

 * JD-Core Version:    0.7.0.1

 */