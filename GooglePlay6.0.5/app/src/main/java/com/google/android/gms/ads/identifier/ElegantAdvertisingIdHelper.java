package com.google.android.gms.ads.identifier;

import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.utils.config.GservicesValue;

public final class ElegantAdvertisingIdHelper
  implements AdIdProvider
{
  private static String sCachedAdId = null;
  private static Boolean sIsLimitAdTrackingEnabled = null;
  private static String sPublicAndroidId = null;
  private int mAdIdFetchState = 1;
  private AsyncTask<Void, Object, AdvertisingIdClient.Info> mAdIdFetchTask;
  private final Context mContext;
  
  public ElegantAdvertisingIdHelper(ContentResolver paramContentResolver, Context paramContext)
  {
    this.mContext = paramContext;
    sPublicAndroidId = Settings.Secure.getString(paramContentResolver, "android_id");
    new Handler(Looper.getMainLooper()).post(new Runnable()
    {
      public final void run()
      {
        ElegantAdvertisingIdHelper.this.refreshCachedData(false);
      }
    });
  }
  
  private String getAdId()
  {
    try
    {
      String str = sCachedAdId;
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private int getAdIdFetchState()
  {
    try
    {
      int i = this.mAdIdFetchState;
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private static void logAdIdLookupResult(String paramString1, String paramString2)
  {
    if (FinskyApp.get().getExperiments().isEnabled(12602796L))
    {
      BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(6);
      localBackgroundEventBuilder.setReason(paramString2);
      if (!TextUtils.isEmpty(paramString1)) {
        localBackgroundEventBuilder.setExceptionType(paramString1);
      }
      FinskyApp.get().getEventLogger().sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
    }
  }
  
  private void logAndProcessAdInfo(AdvertisingIdClient.Info paramInfo, String paramString)
  {
    for (;;)
    {
      int i;
      Object localObject2;
      try
      {
        boolean bool1 = FinskyApp.get().getExperiments().isEnabled(12603406L);
        i = 0;
        if (bool1)
        {
          long l1 = ((Integer)G.adIdMinGmsCoreVersion.get()).intValue();
          boolean bool2 = l1 < 0L;
          i = 0;
          if (bool2)
          {
            PackageStateRepository.PackageState localPackageState = FinskyApp.get().mPackageStateRepository.get("com.google.android.gms");
            if ((localPackageState == null) || (localPackageState.isDisabledByUser) || (localPackageState.installedVersion < l1)) {
              continue;
            }
            i = 1;
          }
        }
        localObject2 = null;
        localBoolean = null;
        if (paramInfo == null)
        {
          FinskyLog.w("AdId result returned null. Refresh reason: [%s].", new Object[] { paramString });
          logAdIdLookupResult("null-result", paramString);
          if ((TextUtils.isEmpty((CharSequence)localObject2)) && (i != 0))
          {
            String str = (String)FinskyPreferences.adIdCachedValue.get();
            if (!TextUtils.isEmpty(str))
            {
              long l2 = ((Long)FinskyPreferences.adIdCachedTimestampMs.get()).longValue();
              long l3 = ((Long)G.adIdMaxCacheTimeMs.get()).longValue();
              if ((l2 == 0L) || (l3 == 0L) || (System.currentTimeMillis() - l2 >= l3)) {
                break label396;
              }
              localObject2 = str;
              localBoolean = (Boolean)FinskyPreferences.adIdLimitTrackingCachedValue.get();
            }
          }
          if (!TextUtils.isEmpty((CharSequence)localObject2)) {
            updateCachedInfo((String)localObject2, localBoolean);
          }
          return;
          i = 0;
          continue;
        }
        localObject2 = paramInfo.zzpp;
        if (localObject2 == null)
        {
          FinskyLog.w("AdId getId from ad listener returned null. Refresh reason: [%s].", new Object[] { paramString });
          logAdIdLookupResult("null-adid", paramString);
          localBoolean = null;
          continue;
        }
        if (((String)localObject2).length() != 0) {
          break label325;
        }
      }
      finally {}
      FinskyLog.w("AdId getId from ad listener returned empty string. Refresh reason: [%s].", new Object[] { paramString });
      logAdIdLookupResult("empty-adid", paramString);
      Boolean localBoolean = null;
      continue;
      label325:
      logAdIdLookupResult(null, paramString);
      localBoolean = Boolean.valueOf(paramInfo.zzpq);
      if (i != 0)
      {
        FinskyPreferences.adIdCachedValue.put(localObject2);
        FinskyPreferences.adIdLimitTrackingCachedValue.put(localBoolean);
        FinskyPreferences.adIdCachedTimestampMs.put(Long.valueOf(System.currentTimeMillis()));
      }
      else
      {
        FinskyPreferences.adIdCachedValue.remove();
        FinskyPreferences.adIdLimitTrackingCachedValue.remove();
        FinskyPreferences.adIdCachedTimestampMs.remove();
        continue;
        label396:
        FinskyPreferences.adIdCachedValue.remove();
        FinskyPreferences.adIdLimitTrackingCachedValue.remove();
        FinskyPreferences.adIdCachedTimestampMs.remove();
      }
    }
  }
  
  private void updateCachedInfo(String paramString, Boolean paramBoolean)
  {
    try
    {
      sCachedAdId = paramString;
      sIsLimitAdTrackingEnabled = paramBoolean;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  public final String getAdIdBlocking()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic 261	com/google/android/finsky/utils/Utils:ensureNotOnMainThread	()V
    //   5: aload_0
    //   6: invokespecial 263	com/google/android/gms/ads/identifier/ElegantAdvertisingIdHelper:getAdId	()Ljava/lang/String;
    //   9: invokestatic 118	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   12: ifne +18 -> 30
    //   15: aload_0
    //   16: invokespecial 263	com/google/android/gms/ads/identifier/ElegantAdvertisingIdHelper:getAdId	()Ljava/lang/String;
    //   19: astore 6
    //   21: aload 6
    //   23: astore 4
    //   25: aload_0
    //   26: monitorexit
    //   27: aload 4
    //   29: areturn
    //   30: aload_0
    //   31: iconst_2
    //   32: putfield 32	com/google/android/gms/ads/identifier/ElegantAdvertisingIdHelper:mAdIdFetchState	I
    //   35: aload_0
    //   36: getfield 81	com/google/android/gms/ads/identifier/ElegantAdvertisingIdHelper:mAdIdFetchTask	Landroid/os/AsyncTask;
    //   39: ifnull +17 -> 56
    //   42: aload_0
    //   43: getfield 81	com/google/android/gms/ads/identifier/ElegantAdvertisingIdHelper:mAdIdFetchTask	Landroid/os/AsyncTask;
    //   46: iconst_1
    //   47: invokevirtual 269	android/os/AsyncTask:cancel	(Z)Z
    //   50: pop
    //   51: aload_0
    //   52: aconst_null
    //   53: putfield 81	com/google/android/gms/ads/identifier/ElegantAdvertisingIdHelper:mAdIdFetchTask	Landroid/os/AsyncTask;
    //   56: iconst_1
    //   57: invokestatic 275	com/google/android/gms/ads/identifier/AdvertisingIdClient:setShouldSkipGmsCoreVersionCheck	(Z)V
    //   60: aload_0
    //   61: aload_0
    //   62: getfield 34	com/google/android/gms/ads/identifier/ElegantAdvertisingIdHelper:mContext	Landroid/content/Context;
    //   65: invokestatic 279	com/google/android/gms/ads/identifier/AdvertisingIdClient:getAdvertisingIdInfo	(Landroid/content/Context;)Lcom/google/android/gms/ads/identifier/AdvertisingIdClient$Info;
    //   68: ldc_w 281
    //   71: invokespecial 77	com/google/android/gms/ads/identifier/ElegantAdvertisingIdHelper:logAndProcessAdInfo	(Lcom/google/android/gms/ads/identifier/AdvertisingIdClient$Info;Ljava/lang/String;)V
    //   74: aload_0
    //   75: iconst_3
    //   76: putfield 32	com/google/android/gms/ads/identifier/ElegantAdvertisingIdHelper:mAdIdFetchState	I
    //   79: aload_0
    //   80: invokespecial 263	com/google/android/gms/ads/identifier/ElegantAdvertisingIdHelper:getAdId	()Ljava/lang/String;
    //   83: astore 4
    //   85: goto -60 -> 25
    //   88: astore_2
    //   89: aload_2
    //   90: invokevirtual 285	java/lang/Object:getClass	()Ljava/lang/Class;
    //   93: invokevirtual 290	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   96: astore_3
    //   97: aload_2
    //   98: invokevirtual 293	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   101: invokestatic 118	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   104: ifne +31 -> 135
    //   107: new 295	java/lang/StringBuilder
    //   110: dup
    //   111: invokespecial 296	java/lang/StringBuilder:<init>	()V
    //   114: aload_3
    //   115: invokevirtual 300	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   118: ldc_w 302
    //   121: invokevirtual 300	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: aload_2
    //   125: invokevirtual 293	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   128: invokevirtual 300	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   131: invokevirtual 305	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   134: astore_3
    //   135: aload_3
    //   136: ldc_w 281
    //   139: invokestatic 69	com/google/android/gms/ads/identifier/ElegantAdvertisingIdHelper:logAdIdLookupResult	(Ljava/lang/String;Ljava/lang/String;)V
    //   142: goto -68 -> 74
    //   145: astore_1
    //   146: aload_0
    //   147: monitorexit
    //   148: aload_1
    //   149: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	150	0	this	ElegantAdvertisingIdHelper
    //   145	4	1	localObject	Object
    //   88	37	2	localException	Exception
    //   96	40	3	str1	String
    //   23	61	4	str2	String
    //   19	3	6	str3	String
    // Exception table:
    //   from	to	target	type
    //   35	56	88	java/lang/Exception
    //   56	74	88	java/lang/Exception
    //   2	21	145	finally
    //   30	35	145	finally
    //   35	56	145	finally
    //   56	74	145	finally
    //   74	85	145	finally
    //   89	135	145	finally
    //   135	142	145	finally
  }
  
  public final String getPublicAndroidId()
  {
    return sPublicAndroidId;
  }
  
  public final Boolean isLimitAdTrackingEnabled()
  {
    try
    {
      Boolean localBoolean = sIsLimitAdTrackingEnabled;
      return localBoolean;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void refreshCachedData(boolean paramBoolean)
  {
    for (;;)
    {
      try
      {
        int i = getAdIdFetchState();
        if (i == 2) {
          return;
        }
        final String str;
        if (paramBoolean)
        {
          str = "changed_by_user";
          this.mAdIdFetchState = 2;
          if (FinskyApp.get().getExperiments().isEnabled(12602796L)) {
            FinskyApp.get().getEventLogger().sendBackgroundEventToSinks(new BackgroundEventBuilder(1102).event);
          }
          this.mAdIdFetchTask = new AsyncTask()
          {
            volatile boolean mExceptionThrown = false;
            
            private AdvertisingIdClient.Info doInBackground$5fcd39c7()
            {
              try
              {
                AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
                AdvertisingIdClient.Info localInfo = AdvertisingIdClient.getAdvertisingIdInfo(ElegantAdvertisingIdHelper.this.mContext);
                return localInfo;
              }
              catch (Exception localException)
              {
                String str = localException.getClass().getSimpleName();
                if (!TextUtils.isEmpty(localException.getMessage())) {
                  str = str + ": " + localException.getMessage();
                }
                FinskyLog.d("Wasn't able to fetch the adId: %s", new Object[] { str });
                ElegantAdvertisingIdHelper.access$100$3f43b8ac(str, str);
                this.mExceptionThrown = true;
              }
              return null;
            }
          };
          Utils.executeMultiThreaded(this.mAdIdFetchTask, new Void[0]);
        }
        else
        {
          str = "app";
        }
      }
      finally {}
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.identifier.ElegantAdvertisingIdHelper
 * JD-Core Version:    0.7.0.1
 */