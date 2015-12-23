package com.google.android.finsky.activities;

import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.DfeAnalytics;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.config.G;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.PingResponse;
import com.google.android.finsky.protos.Survey;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.utils.config.GservicesValue;

public class InlineAppDetailsDialog
  extends AuthenticatedActivity
  implements PageFragmentHost
{
  View mDialog;
  private InlineAppDetailsFragment mFragment;
  private FakeNavigationManager mNavigationManager = new FakeNavigationManager(this);
  
  public static Intent createIntent(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Intent localIntent = new Intent(FinskyApp.get(), InlineAppDetailsDialog.class);
    localIntent.putExtra("docid", paramString1);
    if (!TextUtils.isEmpty(paramString2)) {
      localIntent.putExtra("referrer", paramString2);
    }
    if (!TextUtils.isEmpty(paramString3)) {
      localIntent.putExtra("referrer_package", paramString3);
    }
    if (!TextUtils.isEmpty(paramString4)) {
      localIntent.putExtra("referrer_url", paramString4);
    }
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
  
  protected final void handleAuthenticationError(VolleyError paramVolleyError)
  {
    InlineAppDetailsFragment localInlineAppDetailsFragment = this.mFragment;
    localInlineAppDetailsFragment.switchToError(ErrorStrings.get(localInlineAppDetailsFragment.mContext, paramVolleyError));
  }
  
  public final void hideSatisfactionSurveyV2() {}
  
  public final void maybeShowSatisfactionSurvey$377e3174(PageFragment paramPageFragment) {}
  
  public final void maybeShowSatisfactionSurveyV2(Survey paramSurvey) {}
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt1 == 33) && (paramInt2 == -1)) {
      setResult(-1);
    }
    finish();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mDialog = View.inflate(this, 2130968793, null);
    setContentView(this.mDialog);
    this.mNavigationManager.mFragmentManager = getSupportFragmentManager();
    this.mFragment = ((InlineAppDetailsFragment)getSupportFragmentManager().findFragmentById(2131755234));
    if (this.mFragment != null) {}
    Intent localIntent;
    String str1;
    String str2;
    do
    {
      return;
      localIntent = getIntent();
      str1 = localIntent.getStringExtra("docid");
      str2 = localIntent.getStringExtra("referrer");
      String str3 = AppActionAnalyzer.getAppDetailsAccount(str1, FinskyApp.get().getCurrentAccountName(), FinskyApp.get().mAppStates, FinskyApp.get().mLibraries);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = FinskyLog.scrubPii(str3);
      arrayOfObject[1] = str1;
      FinskyLog.d("Select %s for details of %s", arrayOfObject);
      this.mFragment = InlineAppDetailsFragment.newInstance(str1, str2, str3);
      FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
      localFragmentTransaction.add(2131755234, this.mFragment);
      localFragmentTransaction.commit();
    } while ((paramBundle != null) || (!((Boolean)G.logInlineAppInstallReferrerEnabled.get()).booleanValue()));
    FinskyEventLog localFinskyEventLog = FinskyApp.get().getEventLogger();
    String str4 = localIntent.getStringExtra("referrer_package");
    localFinskyEventLog.logDeepLinkEventAndFlush(11, localIntent.getStringExtra("referrer_url"), str1, str2, str4, null);
    if (!TextUtils.isEmpty(str2)) {
      FinskyApp.get().getDfeApi(null).pingReferrer(str1, str2, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
      {
        public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = paramAnonymousVolleyError.toString();
          FinskyLog.e("Error pinging inline app: %s", arrayOfObject);
          BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(522);
          localBackgroundEventBuilder.setExceptionType(paramAnonymousVolleyError);
          FinskyApp.get().getEventLogger().logBackgroundEventAndFlush(localBackgroundEventBuilder.event);
        }
      });
    }
    String str5 = DfeAnalytics.buildAnalyticsUrl("deepLink", Uri.parse("https://play.google.com/store/apps/details").buildUpon().appendQueryParameter("id", str1).appendQueryParameter("referrer", str2).build().toString(), "android.intent.action.VIEW");
    FinskyApp.get().mAnalytics.logAdMobPageView(str5);
  }
  
  protected final void onReady(boolean paramBoolean)
  {
    super.onReady(paramBoolean);
    this.mFragment.setHasBeenAuthenticated();
  }
  
  public final void overrideSearchBoxWidth(int paramInt) {}
  
  public final void showErrorDialog(String paramString1, String paramString2, boolean paramBoolean) {}
  
  public final void switchToRegularActionBar() {}
  
  public final void switchToSearchBoxOnlyActionBar(int paramInt) {}
  
  public final void updateActionBarTitle(String paramString) {}
  
  public final void updateCurrentBackendId(int paramInt, boolean paramBoolean) {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.InlineAppDetailsDialog
 * JD-Core Version:    0.7.0.1
 */