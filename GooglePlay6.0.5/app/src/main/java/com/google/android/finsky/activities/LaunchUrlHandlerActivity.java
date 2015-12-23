package com.google.android.finsky.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.EventProtoCache;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreDeepLinkEvent;
import com.google.android.finsky.analytics.PlayStore.PlayStoreLogEvent;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStates.AppState;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.config.G;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.receivers.ExpireLaunchUrlReceiver;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.RestrictedDeviceHelper;
import com.google.android.finsky.utils.RestrictedDeviceHelper.OnCompleteListener;
import com.google.android.finsky.utils.StoreTypeValidator;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.utils.config.GservicesValue;

public class LaunchUrlHandlerActivity
  extends Activity
{
  private static final Uri BASE_DETAILS_URI = Uri.parse("http://market.android.com/details");
  
  private void continueOnCreate()
  {
    final Intent localIntent = getGoToMarketHomeIntent(this);
    if (!((Boolean)G.launchUrlsEnabled.get()).booleanValue())
    {
      startActivity(localIntent);
      finish();
      return;
    }
    final AppStates localAppStates = FinskyApp.get().mAppStates;
    localAppStates.load(new Runnable()
    {
      public final void run()
      {
        Intent localIntent1 = localIntent;
        try
        {
          Intent localIntent2 = LaunchUrlHandlerActivity.handleUrl(LaunchUrlHandlerActivity.this, LaunchUrlHandlerActivity.this.getIntent(), localAppStates, FinskyApp.get().getEventLogger());
          return;
        }
        catch (Exception localException)
        {
          FinskyLog.wtf(localException, "Error while processing launch URL", new Object[0]);
          return;
        }
        finally
        {
          LaunchUrlHandlerActivity.this.startActivity(localIntent1);
          LaunchUrlHandlerActivity.this.finish();
        }
      }
    });
  }
  
  private static Intent getGoToMarketHomeIntent(Context paramContext)
  {
    if (UiUtils.isAndroidTv()) {
      return paramContext.getPackageManager().getLeanbackLaunchIntentForPackage(paramContext.getPackageName());
    }
    return paramContext.getPackageManager().getLaunchIntentForPackage(paramContext.getPackageName());
  }
  
  static Intent handleUrl(Context paramContext, Intent paramIntent, AppStates paramAppStates, FinskyEventLog paramFinskyEventLog)
  {
    Uri localUri = paramIntent.getData();
    String str1 = localUri.getQueryParameter("url");
    Intent localIntent2;
    if (TextUtils.isEmpty(str1))
    {
      FinskyLog.e("Launch URL without continue URL", new Object[0]);
      paramIntent.setData(localUri.buildUpon().scheme("http").authority("market.android.com").path("details").build());
      localIntent2 = IntentUtils.createForwardToMainActivityIntent(paramContext, paramIntent);
      return localIntent2;
    }
    String str2 = localUri.getQueryParameter("id");
    if (TextUtils.isEmpty(str2))
    {
      FinskyLog.e("Launch URL without package name", new Object[0]);
      return getGoToMarketHomeIntent(paramContext);
    }
    String str3 = localUri.getQueryParameter("min_version");
    int i = -1;
    if (!TextUtils.isEmpty(str3)) {}
    try
    {
      int k = Integer.parseInt(str3);
      i = k;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      label132:
      int j;
      AppStates.AppState localAppState;
      boolean bool1;
      String str4;
      String str5;
      String str6;
      Uri.Builder localBuilder;
      Intent localIntent1;
      break label132;
    }
    j = -1;
    localAppState = paramAppStates.getApp(str2);
    if ((localAppState != null) && (localAppState.packageManagerState != null)) {
      j = localAppState.packageManagerState.installedVersion;
    }
    if ((j >= 0) && (j >= i)) {}
    for (bool1 = true;; bool1 = false)
    {
      str4 = Utils.urlDecode(str1);
      boolean bool2 = IntentUtils.canResolveUrl(paramContext.getPackageManager(), str2, str4);
      PlayStore.PlayStoreDeepLinkEvent localPlayStoreDeepLinkEvent = new PlayStore.PlayStoreDeepLinkEvent();
      localPlayStoreDeepLinkEvent.resolvedType = 7;
      localPlayStoreDeepLinkEvent.hasResolvedType = true;
      localPlayStoreDeepLinkEvent.externalUrl = str4;
      localPlayStoreDeepLinkEvent.hasExternalUrl = true;
      localPlayStoreDeepLinkEvent.packageName = str2;
      localPlayStoreDeepLinkEvent.hasPackageName = true;
      localPlayStoreDeepLinkEvent.minVersion = i;
      localPlayStoreDeepLinkEvent.hasMinVersion = true;
      localPlayStoreDeepLinkEvent.newEnough = bool1;
      localPlayStoreDeepLinkEvent.hasNewEnough = true;
      localPlayStoreDeepLinkEvent.canResolve = bool2;
      localPlayStoreDeepLinkEvent.hasCanResolve = true;
      if (FinskyEventLog.shouldSendEventToLogcat()) {
        FinskyEventLog.dumpDeepLinkEvent(localPlayStoreDeepLinkEvent);
      }
      PlayStore.PlayStoreLogEvent localPlayStoreLogEvent = paramFinskyEventLog.mProtoCache.obtainPlayStoreLogEvent();
      localPlayStoreLogEvent.deepLink = localPlayStoreDeepLinkEvent;
      paramFinskyEventLog.serializeAndWrite("6", localPlayStoreLogEvent);
      if ((!bool1) || (!bool2)) {
        break label405;
      }
      localIntent2 = new Intent("android.intent.action.VIEW");
      localIntent2.setData(Uri.parse(str4));
      localIntent2.setPackage(str2);
      if ((localAppState == null) || (localAppState.installerData == null)) {
        break;
      }
      ExpireLaunchUrlReceiver.cancel(paramContext, str2);
      paramAppStates.mStateStore.setContinueUrl(str2, null);
      return localIntent2;
    }
    label405:
    str5 = localUri.getQueryParameter("referrer");
    str6 = localUri.getQueryParameter("pcampaignid");
    localBuilder = BASE_DETAILS_URI.buildUpon();
    localBuilder.appendQueryParameter("id", str2);
    localBuilder.appendQueryParameter("url", str4);
    if (!TextUtils.isEmpty(str5)) {
      localBuilder.appendQueryParameter("referrer", str5);
    }
    if (!TextUtils.isEmpty(str6)) {
      localBuilder.appendQueryParameter("pcampaignid", str6);
    }
    localIntent1 = new Intent("android.intent.action.VIEW");
    localIntent1.setData(localBuilder.build());
    return IntentUtils.createForwardToMainActivityIntent(paramContext, localIntent1);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 20)
    {
      if (paramInt2 == -1)
      {
        RestrictedDeviceHelper.setUserPinValid$1385ff();
        continueOnCreate();
      }
    }
    else {
      return;
    }
    finish();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (!StoreTypeValidator.isValid(this))
    {
      AccessRestrictedActivity.showInvalidStoreTypeUI(this);
      finish();
      return;
    }
    RestrictedDeviceHelper.isLimitedOrSchoolEduUser(new RestrictedDeviceHelper.OnCompleteListener()
    {
      public final void onComplete(boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean) {
          if ((FinskyApp.get().getExperiments().isEnabled(12603770L)) && (Build.VERSION.SDK_INT >= 21))
          {
            Intent localIntent = RestrictedDeviceHelper.getIntentForChallengeUIIfRequired(LaunchUrlHandlerActivity.this);
            if (localIntent != null) {
              LaunchUrlHandlerActivity.this.startActivityForResult(localIntent, 20);
            }
          }
          else
          {
            AccessRestrictedActivity.showLimitedUserUI(LaunchUrlHandlerActivity.this);
            LaunchUrlHandlerActivity.this.finish();
            return;
          }
        }
        LaunchUrlHandlerActivity.this.continueOnCreate();
      }
    });
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.LaunchUrlHandlerActivity
 * JD-Core Version:    0.7.0.1
 */