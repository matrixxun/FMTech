package com.google.android.finsky.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.NewsstandArticleHandler;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.layout.ContentFrame;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.play.article.NewsstandArticleLoader;
import com.google.android.play.article.NewsstandArticleLoader.OnArticleLoadedListener;
import com.google.android.play.article.NewsstandArticleView;
import com.google.android.play.article.NewsstandArticleView.1;

public final class NewsstandArticleFragment
  extends PageFragment
{
  private int mBackendId;
  private String mPostId;
  private String mTitle;
  private PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(15);
  
  public static NewsstandArticleFragment newInstance(DfeToc paramDfeToc, String paramString1, String paramString2, int paramInt)
  {
    NewsstandArticleFragment localNewsstandArticleFragment = new NewsstandArticleFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("NewsstandArticleFragment_postId", paramString1);
    localBundle.putString("NewsstandArticleFragment_article_title", paramString2);
    localBundle.putInt("NewsstandArticleFragment_backendId", paramInt);
    localNewsstandArticleFragment.setArguments(localBundle);
    localNewsstandArticleFragment.setArgument("finsky.PageFragment.toc", paramDfeToc);
    return localNewsstandArticleFragment;
  }
  
  protected final LayoutSwitcher createLayoutSwitcher(ContentFrame paramContentFrame)
  {
    return new LayoutSwitcher(paramContentFrame, 2131755586, 2131755806, 2131755289, this, 2);
  }
  
  public final int getActionBarTitleColor()
  {
    return this.mContext.getResources().getColor(2131689625);
  }
  
  protected final Transition getCustomExitTransition()
  {
    return null;
  }
  
  protected final int getLayoutRes()
  {
    return 2130968849;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mPostId = this.mArguments.getString("NewsstandArticleFragment_postId");
    this.mTitle = this.mArguments.getString("NewsstandArticleFragment_article_title");
    this.mBackendId = this.mArguments.getInt("NewsstandArticleFragment_backendId");
    rebindActionBar();
    rebindViews();
    final ViewTreeObserver localViewTreeObserver = this.mActionBarController.getToolbar().getViewTreeObserver();
    localViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
    {
      public final void onGlobalLayout()
      {
        localViewTreeObserver.removeOnGlobalLayoutListener(this);
        NewsstandArticleFragment.this.mActionBarController.disableActionBarOverlay();
      }
    });
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRetainInstance$1385ff();
  }
  
  public final void onDestroyView()
  {
    this.mActionBarController.exitActionBarSectionExpandedMode();
    super.onDestroyView();
  }
  
  public final void rebindActionBar()
  {
    this.mPageFragmentHost.updateActionBarTitle(this.mTitle);
    this.mPageFragmentHost.updateCurrentBackendId(this.mBackendId, false);
    this.mPageFragmentHost.switchToRegularActionBar();
  }
  
  protected final void rebindViews()
  {
    this.mActionBarController.enterActionBarSectionExpandedMode(this.mTitle, null);
    NewsstandArticleView localNewsstandArticleView = (NewsstandArticleView)this.mView.findViewById(2131755745);
    NewsstandArticleHandler localNewsstandArticleHandler = FinskyApp.get().getArticleHandler();
    String str = this.mPostId;
    NewsstandArticleLoader.OnArticleLoadedListener local2 = new NewsstandArticleLoader.OnArticleLoadedListener()
    {
      public final void onArticleLoaded(String paramAnonymousString)
      {
        FinskyApp.get().getEventLogger().logPathImpression(0L, NewsstandArticleFragment.this);
      }
      
      public final void onError()
      {
        FinskyApp.get().getEventLogger().sendBackgroundEventToSinks(new BackgroundEventBuilder(526).event);
      }
    };
    NewsstandArticleLoader localNewsstandArticleLoader = localNewsstandArticleHandler.mArticleLoader;
    localNewsstandArticleView.setErrorViewVisible(false);
    localNewsstandArticleView.setArticleRendererVisible(false);
    localNewsstandArticleView.setLoadingViewVisible(true);
    localNewsstandArticleLoader.load(str, new NewsstandArticleView.1(localNewsstandArticleView, local2));
  }
  
  protected final void requestData() {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.fragments.NewsstandArticleFragment
 * JD-Core Version:    0.7.0.1
 */