package com.google.android.finsky.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.MainActivity;
import com.google.android.finsky.activities.TabbedBrowseFragment;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.config.G;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.feedback.FeedbackOptions;
import com.google.android.gms.feedback.FeedbackOptions.Builder;
import com.google.android.gms.googlehelp.GoogleHelp;
import com.google.android.gms.googlehelp.GoogleHelpLauncher;
import com.google.android.gms.googlehelp.GoogleHelpLauncher.1;
import com.google.android.gms.googlehelp.zzc;
import com.google.android.gms.internal.zzpq;
import com.google.android.play.utils.config.GservicesValue;

public final class HelpFeedbackUtil
{
  private static final Uri FALLBACK_SUPPORT_URL = Uri.parse((String)G.supportUrl.get());
  
  public static void launchGoogleHelp(Activity paramActivity, String paramString)
  {
    GoogleHelp localGoogleHelp = new GoogleHelp(paramString);
    localGoogleHelp.zzbaB = FinskyApp.get().getCurrentAccount();
    localGoogleHelp.zzbaO = FALLBACK_SUPPORT_URL;
    FeedbackOptions.Builder localBuilder = new FeedbackOptions.Builder();
    localBuilder.zzaLj = GoogleHelp.getScreenshot(paramActivity);
    localGoogleHelp.zzbaT = zzpq.zza(FeedbackOptions.zza(FeedbackOptions.zza(FeedbackOptions.zza(FeedbackOptions.zza(FeedbackOptions.zzc(FeedbackOptions.zza(FeedbackOptions.zzb(FeedbackOptions.zza(FeedbackOptions.zza(new FeedbackOptions((byte)0), localBuilder.zzaLj), localBuilder.mAccountInUse), localBuilder.mDescription), localBuilder.mPsdBundle), localBuilder.mCategoryTag), localBuilder.mFileTeleporters), localBuilder.mExcludePii), localBuilder.mThemeSettings), localBuilder.mLogOptions), paramActivity.getCacheDir());
    localGoogleHelp.zzbaT.launcher = "GoogleHelp";
    Intent localIntent = new Intent("com.google.android.gms.googlehelp.HELP").setPackage("com.google.android.gms").putExtra("EXTRA_GOOGLE_HELP", localGoogleHelp);
    GoogleHelpLauncher localGoogleHelpLauncher = new GoogleHelpLauncher(paramActivity);
    if ((!localIntent.getAction().equals("com.google.android.gms.googlehelp.HELP")) || (!localIntent.hasExtra("EXTRA_GOOGLE_HELP"))) {
      throw new IllegalArgumentException("The intent you are trying to launch is not GoogleHelp intent! This class only supports GoogleHelp intents.");
    }
    int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(localGoogleHelpLauncher.mActivity);
    if (i == 0)
    {
      zzc.zza(localGoogleHelpLauncher.mApiClient, new GoogleHelpLauncher.1(localGoogleHelpLauncher, localIntent));
      return;
    }
    localGoogleHelpLauncher.handlePlayServicesUnavailable(i, localIntent);
  }
  
  public static void launchHelpFeedback(MainActivity paramMainActivity)
  {
    NavigationManager localNavigationManager = paramMainActivity.mNavigationManager;
    if (FinskyApp.get().mToc != null)
    {
      String str2 = FinskyApp.get().mToc.mToc.helpUrl;
      if (!TextUtils.isEmpty(str2))
      {
        localNavigationManager.goToUrl(str2);
        return;
      }
    }
    String str1;
    switch (localNavigationManager.getCurrentPageType())
    {
    case 4: 
    case 6: 
    case 8: 
    case 9: 
    case 11: 
    default: 
      str1 = "mobile_store_default";
    case 1: 
    case 7: 
    case 3: 
    case 10: 
    case 12: 
      for (;;)
      {
        launchGoogleHelp(paramMainActivity, str1);
        return;
        str1 = "mobile_home";
        continue;
        str1 = "mobile_search";
        continue;
        str1 = "mobile_my_apps";
        continue;
        str1 = "mobile_wishlist";
        continue;
        str1 = "mobile_people";
      }
    case 2: 
      PageFragment localPageFragment = localNavigationManager.getActivePage();
      if ((localPageFragment instanceof TabbedBrowseFragment)) {}
      switch (((TabbedBrowseFragment)localPageFragment).mBackendId)
      {
      case 5: 
      default: 
        str1 = "mobile_store_default";
      }
      for (;;)
      {
        break;
        str1 = "mobile_books";
        continue;
        str1 = "mobile_music";
        continue;
        str1 = "mobile_apps";
        continue;
        str1 = "mobile_movies";
        continue;
        str1 = "mobile_newsstand";
      }
    }
    Document localDocument = localNavigationManager.getCurrentDocument();
    if (localDocument != null) {}
    switch (localDocument.mDocument.backendId)
    {
    case 5: 
    default: 
      str1 = "mobile_store_default";
    }
    for (;;)
    {
      break;
      str1 = "mobile_books_object";
      continue;
      str1 = "mobile_music_object";
      continue;
      str1 = "mobile_apps_object";
      continue;
      str1 = "mobile_movies_object";
      continue;
      str1 = "mobile_newsstand_object";
    }
  }
  
  public static void launchParentGuide(MainActivity paramMainActivity)
  {
    launchGoogleHelp(paramMainActivity, "mobile_parent_guide");
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.HelpFeedbackUtil
 * JD-Core Version:    0.7.0.1
 */