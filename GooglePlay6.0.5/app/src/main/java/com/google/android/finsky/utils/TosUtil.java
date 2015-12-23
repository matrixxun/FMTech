package com.google.android.finsky.utils;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.TosActivity;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.protos.AcceptTosResponse;
import com.google.android.finsky.protos.Toc.TocResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TosUtil
{
  private static final Pattern PATTERN_TOS_VERSION = Pattern.compile("version:(\\d+)");
  private static Map<String, String> sLastTosAcceptedInProcessLife = new HashMap();
  
  public static void ackTos(String paramString, final DfeToc paramDfeToc, final Boolean paramBoolean)
  {
    FinskyApp.get().getDfeApi(null).acceptTos(paramDfeToc.mToc.tosToken, paramBoolean, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        FinskyLog.e("Error sending TOS acceptance: %s", new Object[] { paramAnonymousVolleyError });
      }
    });
    sLastTosAcceptedInProcessLife.put(paramString, paramDfeToc.mToc.tosToken);
  }
  
  private static Intent getIntent(Context paramContext, String paramString, DfeToc paramDfeToc, Class<? extends Activity> paramClass)
  {
    Intent localIntent = new Intent(paramContext, paramClass);
    Bundle localBundle = new Bundle();
    localBundle.putString("finsky.TosActivity.account", paramString);
    localBundle.putParcelable("finsky.TosActivity.toc", paramDfeToc);
    localIntent.putExtras(localBundle);
    return localIntent;
  }
  
  public static int getMaxAcceptedTosVersion()
  {
    i = -1;
    try
    {
      Account[] arrayOfAccount = AccountHandler.getAccounts(FinskyApp.get());
      int j = arrayOfAccount.length;
      int k = 0;
      for (;;)
      {
        if (k >= j) {
          return i;
        }
        Account localAccount = arrayOfAccount[k];
        String str1 = new String(Base64.decode((String)FinskyPreferences.acceptedTosToken.get(localAccount.name).get(), 0));
        Matcher localMatcher = PATTERN_TOS_VERSION.matcher(str1);
        String str2;
        if (localMatcher.find()) {
          str2 = localMatcher.group(1);
        }
        try
        {
          int m = Math.max(i, Integer.parseInt(str2));
          i = m;
          k++;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          for (;;)
          {
            FinskyLog.e("Cannot convert TOS version %s to integer", new Object[] { str2 });
          }
        }
      }
      return i;
    }
    catch (Throwable localThrowable)
    {
      FinskyLog.e("Max accepted TOS version failed with exception: %s", new Object[] { localThrowable });
    }
  }
  
  public static Intent getTosIntent(Activity paramActivity, String paramString, DfeToc paramDfeToc)
  {
    if (UiUtils.isAndroidTv())
    {
      Class localClass = TvIntentUtils.getClass("com.google.android.finsky.activities.TvTosActivity");
      if (localClass != null) {}
      for (Intent localIntent = getIntent(paramActivity, paramString, paramDfeToc, localClass); localIntent != null; localIntent = null) {
        return localIntent;
      }
    }
    return getIntent(paramActivity, paramString, paramDfeToc, TosActivity.class);
  }
  
  public static boolean requiresAcceptance(String paramString, DfeToc paramDfeToc)
  {
    if ((sLastTosAcceptedInProcessLife.containsKey(paramString)) && (((String)sLastTosAcceptedInProcessLife.get(paramString)).equals(paramDfeToc.mToc.tosToken))) {
      return false;
    }
    String str = (String)FinskyPreferences.acceptedTosToken.get(paramString).get();
    return (TextUtils.isEmpty(str)) || (!str.equals(paramDfeToc.mToc.tosToken));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.TosUtil
 * JD-Core Version:    0.7.0.1
 */