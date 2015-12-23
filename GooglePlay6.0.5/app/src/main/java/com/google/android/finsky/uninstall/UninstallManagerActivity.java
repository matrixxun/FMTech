package com.google.android.finsky.uninstall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AppActionAnalyzer;
import com.google.android.finsky.activities.AuthenticatedActivity;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Survey;
import com.google.android.finsky.receivers.Installer;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.play.image.BitmapLoader;

public class UninstallManagerActivity
  extends AuthenticatedActivity
  implements PageFragmentHost, UninstallManagerFragment.UninstallManagerContinueListener
{
  private Document mInstallingDoc;
  
  public static Intent createIntent(Document paramDocument)
  {
    Intent localIntent = new Intent(FinskyApp.get(), UninstallManagerActivity.class);
    localIntent.putExtra("uninstall_manager_activity_installing_doc", paramDocument);
    return localIntent;
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
    return null;
  }
  
  public final void goBack() {}
  
  public final void hideSatisfactionSurveyV2() {}
  
  public final void maybeShowSatisfactionSurvey$377e3174(PageFragment paramPageFragment) {}
  
  public final void maybeShowSatisfactionSurveyV2(Survey paramSurvey) {}
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130969142);
    this.mInstallingDoc = ((Document)getIntent().getParcelableExtra("uninstall_manager_activity_installing_doc"));
    if (getSupportFragmentManager().findFragmentByTag("uninstall_manager_activity_fragment") == null)
    {
      UninstallManagerFragment localUninstallManagerFragment = UninstallManagerFragment.newInstance(this.mInstallingDoc);
      FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
      localFragmentTransaction.setCustomAnimations(2131034129, 2131034127);
      localFragmentTransaction.add(2131756177, localUninstallManagerFragment, "uninstall_manager_activity_fragment");
      localFragmentTransaction.commit();
    }
  }
  
  public final void onUninstallManagerContinue()
  {
    FinskyApp localFinskyApp = FinskyApp.get();
    Installer localInstaller = localFinskyApp.mInstaller;
    AppDetails localAppDetails = this.mInstallingDoc.getAppDetails();
    String str1 = localAppDetails.packageName;
    String str2 = AppActionAnalyzer.getAppDetailsAccount(str1, localFinskyApp.getCurrentAccountName(), localFinskyApp.mAppStates, localFinskyApp.mLibraries);
    localInstaller.requestInstall(str1, localAppDetails.versionCode, str2, this.mInstallingDoc.mDocument.title, false, "single_install", 2, localInstaller.extractInstallLocation(this.mInstallingDoc));
    finish();
  }
  
  public final void overrideSearchBoxWidth(int paramInt) {}
  
  public final void showErrorDialog(String paramString1, String paramString2, boolean paramBoolean) {}
  
  public final void switchToRegularActionBar() {}
  
  public final void switchToSearchBoxOnlyActionBar(int paramInt) {}
  
  public final void updateActionBarTitle(String paramString) {}
  
  public final void updateCurrentBackendId(int paramInt, boolean paramBoolean) {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.uninstall.UninstallManagerActivity
 * JD-Core Version:    0.7.0.1
 */