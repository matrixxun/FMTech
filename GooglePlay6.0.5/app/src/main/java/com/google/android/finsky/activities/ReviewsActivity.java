package com.google.android.finsky.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.layout.actionbar.ActionBarHelper;
import com.google.android.finsky.layout.actionbar.FinskySearchToolbar;
import com.google.android.finsky.layout.actionbar.FinskySearchToolbar.Configurator;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Survey;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.play.image.BitmapLoader;

public class ReviewsActivity
  extends AuthenticatedActivity
  implements PageFragmentHost
{
  private Document mDocument;
  private boolean mIsRottenTomatoesReviews;
  private NavigationManager mNavigationManager;
  private String mReviewsUrl;
  
  public static Intent getIntent(Context paramContext, Document paramDocument, String paramString, boolean paramBoolean)
  {
    Intent localIntent = new Intent(paramContext, ReviewsActivity.class);
    localIntent.putExtra("finsky.ReviewsActivity.document", paramDocument);
    localIntent.putExtra("finsky.ReviewsActivity.reviewsUrl", paramString);
    localIntent.putExtra("finsky.ReviewsActivity.isRottenTomatoesReviews", paramBoolean);
    localIntent.setFlags(536870912);
    return localIntent;
  }
  
  public static boolean shouldUseReviewFeedbackV2()
  {
    return FinskyApp.get().getExperiments().isEnabled(12602624L);
  }
  
  public final ActionBarController getActionBarController()
  {
    return null;
  }
  
  public final BitmapLoader getBitmapLoader()
  {
    return FinskyApp.get().mBitmapLoader;
  }
  
  public final DfeApi getDfeApi(String paramString)
  {
    return FinskyApp.get().getDfeApi(paramString);
  }
  
  public final NavigationManager getNavigationManager()
  {
    return this.mNavigationManager;
  }
  
  public final GoogleApiClient getPeopleClient()
  {
    throw new UnsupportedOperationException();
  }
  
  public final void goBack()
  {
    finish();
  }
  
  public final void hideSatisfactionSurveyV2() {}
  
  public final void maybeShowSatisfactionSurvey$377e3174(PageFragment paramPageFragment) {}
  
  public final void maybeShowSatisfactionSurveyV2(Survey paramSurvey) {}
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968710);
    Toolbar localToolbar = (Toolbar)findViewById(2131755196);
    if (localToolbar != null)
    {
      if ((localToolbar instanceof FinskySearchToolbar)) {
        ((FinskySearchToolbar)localToolbar).configure(new FinskySearchToolbar.Configurator(this));
      }
      setSupportActionBar(localToolbar);
    }
    Intent localIntent = getIntent();
    this.mDocument = ((Document)localIntent.getParcelableExtra("finsky.ReviewsActivity.document"));
    this.mReviewsUrl = localIntent.getStringExtra("finsky.ReviewsActivity.reviewsUrl");
    this.mIsRottenTomatoesReviews = localIntent.getBooleanExtra("finsky.ReviewsActivity.isRottenTomatoesReviews", false);
    this.mNavigationManager = new FakeNavigationManager(this);
    this.mActionBarHelper = new ActionBarHelper(this.mNavigationManager, this);
    this.mActionBarHelper.updateDefaultTitle(this.mDocument.mDocument.title);
    this.mActionBarHelper.updateCurrentBackendId(this.mDocument.mDocument.backendId, false);
    this.mActionBarHelper.updateActionBarMode(false, -1);
    FragmentManager localFragmentManager = getSupportFragmentManager();
    if (localFragmentManager.findFragmentById(2131755234) == null)
    {
      ReviewsFragment localReviewsFragment = ReviewsFragment.newInstance(this.mDocument, this.mReviewsUrl, this.mIsRottenTomatoesReviews);
      FragmentTransaction localFragmentTransaction = localFragmentManager.beginTransaction();
      localFragmentTransaction.replace(2131755234, localReviewsFragment);
      localFragmentTransaction.commit();
    }
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return false;
    }
    if (!this.mNavigationManager.goUp()) {
      super.onBackPressed();
    }
    return true;
  }
  
  public final void overrideSearchBoxWidth(int paramInt)
  {
    this.mActionBarHelper.overrideSearchBoxWidth(paramInt);
  }
  
  public final void showErrorDialog(String paramString1, String paramString2, boolean paramBoolean) {}
  
  public final void switchToRegularActionBar()
  {
    this.mActionBarHelper.updateActionBarMode(false, -1);
  }
  
  public final void switchToSearchBoxOnlyActionBar(int paramInt)
  {
    this.mActionBarHelper.updateActionBarMode(true, paramInt);
  }
  
  public final void updateActionBarTitle(String paramString)
  {
    this.mActionBarHelper.updateDefaultTitle(paramString);
  }
  
  public final void updateCurrentBackendId(int paramInt, boolean paramBoolean)
  {
    this.mActionBarHelper.updateCurrentBackendId(paramInt, paramBoolean);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.ReviewsActivity
 * JD-Core Version:    0.7.0.1
 */