package com.google.android.finsky.activities.onboard;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.layout.play.RootUiElementNode;
import com.google.android.finsky.utils.LoadSVGTask;

public final class AnimatedEntertainmentOnboardFragment
  extends Fragment
  implements RootUiElementNode
{
  private FinskyEventLog mEventLogger = FinskyApp.get().getEventLogger();
  private Handler mImpressionHandler = new Handler(Looper.getMainLooper());
  private long mImpressionId = FinskyEventLog.getNextImpressionId();
  private final boolean mOnboardingV2 = FinskyApp.get().getExperiments().isEnabled(12604142L);
  private final PlayStore.PlayStoreUiElement mRootUiElement = FinskyEventLog.obtainPlayStoreUiElement(5004);
  private AnimatedEntertainmentOnboardPage mRootView;
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyEventLog.rootImpression(this.mImpressionHandler, this.mImpressionId, this, paramPlayStoreUiElementNode);
  }
  
  public final void flushImpression()
  {
    FinskyEventLog.flushImpression(this.mImpressionHandler, this.mImpressionId, this);
  }
  
  public final PlayStoreUiElementNode getParentNode()
  {
    return null;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mRootUiElement;
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    if (this.mOnboardingV2) {}
    for (this.mRootView = ((AnimatedEntertainmentOnboardPage)paramLayoutInflater.inflate(2130968617, paramViewGroup, false));; this.mRootView = ((AnimatedEntertainmentOnboardPage)paramLayoutInflater.inflate(2130968616, paramViewGroup, false)))
    {
      this.mRootView.setParentUiElementNode(this);
      this.mRootView.setEventLogger(this.mEventLogger);
      this.mRootView.setOnboardingV2(this.mOnboardingV2);
      return this.mRootView;
    }
  }
  
  public final void onDestroyView()
  {
    AnimatedEntertainmentOnboardPage localAnimatedEntertainmentOnboardPage = this.mRootView;
    if (localAnimatedEntertainmentOnboardPage.mLoadAppsGamesImageTask != null) {
      localAnimatedEntertainmentOnboardPage.mLoadAppsGamesImageTask.cancel(true);
    }
    if (localAnimatedEntertainmentOnboardPage.mLoadEntertainmentImageTask != null) {
      localAnimatedEntertainmentOnboardPage.mLoadEntertainmentImageTask.cancel(true);
    }
    super.onDestroyView();
  }
  
  public final void onPause()
  {
    super.onPause();
    this.mRootView.setIsActivityResumed(false);
  }
  
  public final void onResume()
  {
    super.onResume();
    FinskyEventLog.startNewImpression(this);
    this.mRootView.setIsActivityResumed(true);
  }
  
  public final void startNewImpression()
  {
    this.mImpressionId = FinskyEventLog.getNextImpressionId();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.onboard.AnimatedEntertainmentOnboardFragment
 * JD-Core Version:    0.7.0.1
 */