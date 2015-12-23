package com.google.android.finsky.fragments;

import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Survey;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.play.image.BitmapLoader;

public abstract interface PageFragmentHost
{
  public abstract ActionBarController getActionBarController();
  
  public abstract BitmapLoader getBitmapLoader();
  
  public abstract DfeApi getDfeApi(String paramString);
  
  public abstract NavigationManager getNavigationManager();
  
  public abstract GoogleApiClient getPeopleClient();
  
  public abstract void goBack();
  
  public abstract void hideSatisfactionSurveyV2();
  
  public abstract void maybeShowSatisfactionSurvey$377e3174(PageFragment paramPageFragment);
  
  public abstract void maybeShowSatisfactionSurveyV2(Survey paramSurvey);
  
  public abstract void overrideSearchBoxWidth(int paramInt);
  
  public abstract void showErrorDialog(String paramString1, String paramString2, boolean paramBoolean);
  
  public abstract void switchToRegularActionBar();
  
  public abstract void switchToSearchBoxOnlyActionBar(int paramInt);
  
  public abstract void updateActionBarTitle(String paramString);
  
  public abstract void updateCurrentBackendId(int paramInt, boolean paramBoolean);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.fragments.PageFragmentHost
 * JD-Core Version:    0.7.0.1
 */