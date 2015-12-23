package com.google.android.play.dfe.api;

import android.accounts.Account;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.google.android.play.utils.PlayCommonLog;
import com.google.android.play.utils.PlayUtils;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.play.utils.config.PlayG;
import com.google.android.volley.UrlTools;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class PlayDfeApiContext
{
  final AndroidAuthenticator mAuthenticator;
  final Cache mCache;
  private final Context mContext;
  private final Map<String, String> mHeaders = new HashMap();
  private String mLastAuthToken;
  
  private PlayDfeApiContext(Context paramContext, AndroidAuthenticator paramAndroidAuthenticator, Cache paramCache, String paramString1, String paramString2, int paramInt, Locale paramLocale, String paramString3, String paramString4, String paramString5)
  {
    this.mContext = paramContext;
    this.mAuthenticator = paramAndroidAuthenticator;
    this.mCache = paramCache;
    this.mHeaders.put("X-DFE-Device-Id", Long.toHexString(((Long)PlayG.androidId.get()).longValue()));
    this.mHeaders.put("Accept-Language", paramLocale.getLanguage() + "-" + paramLocale.getCountry());
    if (!TextUtils.isEmpty(paramString3)) {
      this.mHeaders.put("X-DFE-MCCMNC", paramString3);
    }
    if (!TextUtils.isEmpty(paramString4)) {
      this.mHeaders.put("X-DFE-Client-Id", paramString4);
    }
    if (!TextUtils.isEmpty(paramString4)) {
      this.mHeaders.put("X-DFE-Logging-Id", paramString5);
    }
    Map localMap = this.mHeaders;
    String str1 = sanitizeHeaderValue(Build.DEVICE);
    String str2 = sanitizeHeaderValue(Build.HARDWARE);
    String str3 = sanitizeHeaderValue(Build.PRODUCT);
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[8];
    arrayOfObject[0] = paramString1;
    arrayOfObject[1] = paramString2;
    arrayOfObject[2] = Integer.valueOf(4);
    arrayOfObject[3] = Integer.valueOf(paramInt);
    arrayOfObject[4] = Integer.valueOf(Build.VERSION.SDK_INT);
    arrayOfObject[5] = str1;
    arrayOfObject[6] = str2;
    arrayOfObject[7] = str3;
    localMap.put("User-Agent", String.format(localLocale, "Android-%s/%s (api=%d,versionCode=%d,sdk=%d,device=%s,hardware=%s,product=%s)", arrayOfObject));
    String str4 = PlayDfeApi.BASE_URI.toString();
    String str5 = UrlTools.rewrite(this.mContext, str4);
    if (str5 == null) {
      throw new RuntimeException("BASE_URI blocked by UrlRules: " + str4);
    }
    try
    {
      URL localURL = new URL(str5);
      if ((!localURL.getProtocol().toLowerCase().equals("https")) && (!localURL.getHost().toLowerCase().endsWith("corp.google.com")) && (!localURL.getHost().startsWith("192.168.0")))
      {
        if (localURL.getHost().startsWith("127.0.0"))
        {
          boolean bool = PlayUtils.isTestDevice();
          if (!bool) {}
        }
      }
      else {
        return;
      }
    }
    catch (MalformedURLException localMalformedURLException)
    {
      PlayCommonLog.d("Cannot parse URL: " + str5, new Object[0]);
      throw new RuntimeException("Insecure URL: " + str5);
    }
  }
  
  public static PlayDfeApiContext create(Context paramContext, String paramString, Cache paramCache, Account paramAccount)
  {
    AndroidAuthenticator localAndroidAuthenticator = new AndroidAuthenticator(paramContext, paramAccount, (String)PlayG.authTokenType.get());
    try
    {
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramString, 0);
      String str = localPackageInfo.versionName;
      int i = localPackageInfo.versionCode;
      TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
      PlayDfeApiContext localPlayDfeApiContext = new PlayDfeApiContext(paramContext, localAndroidAuthenticator, paramCache, paramString, str, i, Locale.getDefault(), localTelephonyManager.getSimOperator(), (String)PlayG.clientId.get(), (String)PlayG.loggingId.get());
      return localPlayDfeApiContext;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      throw new RuntimeException("Can't find our own package", localNameNotFoundException);
    }
  }
  
  private static String sanitizeHeaderValue(String paramString)
  {
    return Uri.encode(paramString).replace("(", "").replace(")", "");
  }
  
  public final Map<String, String> getHeaders()
    throws AuthFailureError
  {
    try
    {
      this.mLastAuthToken = this.mAuthenticator.getAuthToken();
      HashMap localHashMap = new HashMap();
      localHashMap.putAll(this.mHeaders);
      localHashMap.put("Authorization", "GoogleLogin auth=" + this.mLastAuthToken);
      return localHashMap;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void invalidateAuthToken()
  {
    if (this.mLastAuthToken != null)
    {
      this.mAuthenticator.invalidateAuthToken(this.mLastAuthToken);
      this.mLastAuthToken = null;
    }
  }
  
  public final String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[PlayDfeApiContext headers={");
    int i = 1;
    Iterator localIterator = this.mHeaders.keySet().iterator();
    if (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (i != 0) {
        i = 0;
      }
      for (;;)
      {
        localStringBuilder.append(str).append(": ").append((String)this.mHeaders.get(str));
        break;
        localStringBuilder.append(", ");
      }
    }
    localStringBuilder.append("}]");
    return localStringBuilder.toString();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.dfe.api.PlayDfeApiContext
 * JD-Core Version:    0.7.0.1
 */