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
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.layout.actionbar.ActionBarHelper;
import com.google.android.finsky.layout.actionbar.FinskySearchToolbar;
import com.google.android.finsky.layout.actionbar.FinskySearchToolbar.Configurator;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Survey;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.play.image.BitmapLoader;

public class FlagItemDialog
  extends AuthenticatedActivity
  implements PageFragmentHost
{
  private NavigationManager mNavigationManager = new FakeNavigationManager(this);
  
  public static void show(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent(paramContext, FlagItemDialog.class);
    localIntent.putExtra("url", paramString);
    localIntent.setFlags(536936448);
    paramContext.startActivity(localIntent);
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
    return null;
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
  
  protected void onCreate(Bundle paramBundle)
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
    String str = getIntent().getStringExtra("url");
    this.mActionBarHelper = new ActionBarHelper(this.mNavigationManager, this);
    if (getSupportFragmentManager().findFragmentById(2131755234) != null) {
      return;
    }
    FlagItemFragment localFlagItemFragment = FlagItemFragment.newInstance(str);
    FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
    localFragmentTransaction.add(2131755234, localFlagItemFragment);
    localFragmentTransaction.commit();
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == 16908332)
    {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(paramMenuItem);
  }
  
  public final void overrideSearchBoxWidth(int paramInt)
  {
    this.mActionBarHelper.overrideSearchBoxWidth(paramInt);
  }
  
  public final void showErrorDialog(String paramString1, String paramString2, boolean paramBoolean)
  {
    ErrorDialog.show(getSupportFragmentManager(), paramString1, paramString2, paramBoolean);
  }
  
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
 * Qualified Name:     com.google.android.finsky.activities.FlagItemDialog
 * JD-Core Version:    0.7.0.1
 */