package com.google.android.finsky.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Survey;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.play.image.BitmapLoader;

public class FamilyMemberSettingsActivity
  extends AppCompatActivity
  implements PageFragmentHost
{
  private FakeNavigationManager mNavigationManager;
  
  public final ActionBarController getActionBarController()
  {
    return null;
  }
  
  public final BitmapLoader getBitmapLoader()
  {
    return null;
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
    return null;
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
    setContentView(2130968740);
    getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    this.mNavigationManager = new FakeNavigationManager(this);
    this.mNavigationManager.mFragmentManager = getSupportFragmentManager();
    if ((FamilyMemberSettingsFragment)getSupportFragmentManager().findFragmentById(2131755503) != null) {
      return;
    }
    FamilyMemberSettingsFragment localFamilyMemberSettingsFragment = new FamilyMemberSettingsFragment();
    getSupportFragmentManager().beginTransaction().add(2131755503, localFamilyMemberSettingsFragment).commit();
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    }
    onBackPressed();
    return true;
  }
  
  public final void overrideSearchBoxWidth(int paramInt) {}
  
  public final void showErrorDialog(String paramString1, String paramString2, boolean paramBoolean) {}
  
  public final void switchToRegularActionBar() {}
  
  public final void switchToSearchBoxOnlyActionBar(int paramInt) {}
  
  public final void updateActionBarTitle(String paramString) {}
  
  public final void updateCurrentBackendId(int paramInt, boolean paramBoolean) {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.FamilyMemberSettingsActivity
 * JD-Core Version:    0.7.0.1
 */