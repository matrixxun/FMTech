package com.google.android.finsky.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.android.volley.toolbox.StringRequest;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.DfeAnalytics;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeApiConfig;
import com.google.android.finsky.api.DfeApiContext;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.auth.AuthTokenUtils;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.PingResponse;
import com.google.android.gms.ads.adshield.AdShieldClient;
import com.google.android.gms.ads.adshield.UrlParseException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzbb;
import com.google.android.play.utils.config.GservicesValue;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class AdUtils
{
  static AdShieldClient sAdShieldClient;
  private static float sScreenDensity;
  
  public static void addTouchEventForAdShield(Context paramContext, MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent == null) {
      return;
    }
    instantiateAdShieldIfNeededAndRun(paramContext, new Runnable()
    {
      public final void run()
      {
        if (!AdUtils.isAdShieldClientAvailable()) {
          return;
        }
        try
        {
          AdShieldClient localAdShieldClient = AdUtils.sAdShieldClient;
          MotionEvent localMotionEvent = this.val$motionEvent;
          localAdShieldClient.zzoT.zzd(zze.zzI(localMotionEvent));
          return;
        }
        catch (RemoteException localRemoteException)
        {
          FinskyLog.e("Error accessing AdShield: %s", new Object[] { localRemoteException });
        }
      }
    });
  }
  
  static String getAdShieldedClickUrl(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    if (isAdShieldClientAvailable()) {}
    try
    {
      Uri localUri = Uri.parse(paramString1).buildUpon().appendQueryParameter("nb", paramString2).appendQueryParameter("dim", paramString3).build();
      AdShieldClient localAdShieldClient = sAdShieldClient;
      zzd localzzd1 = zze.zzI(localUri);
      zzd localzzd2 = zze.zzI(paramContext);
      localzzd3 = localAdShieldClient.zzoT.zzb(localzzd1, localzzd2);
      if (localzzd3 == null) {
        throw new UrlParseException();
      }
    }
    catch (RemoteException localRemoteException)
    {
      zzd localzzd3;
      FinskyLog.e("Error accessing AdShield: %s", new Object[] { localRemoteException });
      return paramString1;
      String str = ((Uri)zze.zzu(localzzd3)).toString();
      return str;
    }
    catch (UrlParseException localUrlParseException)
    {
      FinskyLog.e("Error parsing the ad click URL: %s", new Object[] { localUrlParseException });
    }
    return paramString1;
  }
  
  public static void instantiateAdShieldIfNeededAndRun(Context paramContext, final Runnable paramRunnable)
  {
    if (isAdShieldClientAvailable())
    {
      if (paramRunnable != null) {
        paramRunnable.run();
      }
      return;
    }
    new AsyncTask() {}.execute(new Void[0]);
  }
  
  /* Error */
  static boolean isAdShieldClientAvailable()
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 56	com/google/android/finsky/utils/AdUtils:sAdShieldClient	Lcom/google/android/gms/ads/adshield/AdShieldClient;
    //   6: astore_1
    //   7: aload_1
    //   8: ifnull +10 -> 18
    //   11: iconst_1
    //   12: istore_2
    //   13: ldc 2
    //   15: monitorexit
    //   16: iload_2
    //   17: ireturn
    //   18: iconst_0
    //   19: istore_2
    //   20: goto -7 -> 13
    //   23: astore_0
    //   24: ldc 2
    //   26: monitorexit
    //   27: aload_0
    //   28: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   23	5	0	localObject	Object
    //   6	2	1	localAdShieldClient	AdShieldClient
    //   12	8	2	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   3	7	23	finally
  }
  
  /* Error */
  static void setAdShieldClient(Context paramContext)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: invokestatic 123	com/google/android/finsky/utils/Utils:ensureNotOnMainThread	()V
    //   6: invokestatic 30	com/google/android/finsky/utils/AdUtils:isAdShieldClientAvailable	()Z
    //   9: istore_2
    //   10: iload_2
    //   11: ifeq +7 -> 18
    //   14: ldc 2
    //   16: monitorexit
    //   17: return
    //   18: ldc 125
    //   20: astore_3
    //   21: aload_0
    //   22: invokevirtual 131	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   25: aload_0
    //   26: invokevirtual 134	android/content/Context:getPackageName	()Ljava/lang/String;
    //   29: iconst_0
    //   30: invokevirtual 140	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   33: getfield 146	android/content/pm/PackageInfo:versionName	Ljava/lang/String;
    //   36: astore_3
    //   37: new 64	com/google/android/gms/ads/adshield/AdShieldClient
    //   40: dup
    //   41: getstatic 152	java/util/Locale:US	Ljava/util/Locale;
    //   44: ldc 154
    //   46: iconst_1
    //   47: anewarray 4	java/lang/Object
    //   50: dup
    //   51: iconst_0
    //   52: aload_3
    //   53: aastore
    //   54: invokestatic 160	java/lang/String:format	(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   57: aload_0
    //   58: invokespecial 163	com/google/android/gms/ads/adshield/AdShieldClient:<init>	(Ljava/lang/String;Landroid/content/Context;)V
    //   61: putstatic 56	com/google/android/finsky/utils/AdUtils:sAdShieldClient	Lcom/google/android/gms/ads/adshield/AdShieldClient;
    //   64: goto -50 -> 14
    //   67: astore 5
    //   69: ldc 165
    //   71: iconst_0
    //   72: anewarray 4	java/lang/Object
    //   75: invokestatic 168	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   78: goto -64 -> 14
    //   81: astore_1
    //   82: ldc 2
    //   84: monitorexit
    //   85: aload_1
    //   86: athrow
    //   87: astore 4
    //   89: ldc 170
    //   91: iconst_0
    //   92: anewarray 4	java/lang/Object
    //   95: invokestatic 173	com/google/android/finsky/utils/FinskyLog:wtf	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   98: goto -61 -> 37
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	101	0	paramContext	Context
    //   81	5	1	localObject	Object
    //   9	2	2	bool	boolean
    //   20	33	3	str	String
    //   87	1	4	localNameNotFoundException	PackageManager.NameNotFoundException
    //   67	1	5	localException	java.lang.Exception
    // Exception table:
    //   from	to	target	type
    //   37	64	67	java/lang/Exception
    //   3	10	81	finally
    //   21	37	81	finally
    //   37	64	81	finally
    //   69	78	81	finally
    //   89	98	81	finally
    //   21	37	87	android/content/pm/PackageManager$NameNotFoundException
  }
  
  public static void trackCardClick(Context paramContext, final Document paramDocument, final String paramString, int paramInt1, int paramInt2)
  {
    if ((paramDocument == null) || (!paramDocument.isAdvertisement())) {
      return;
    }
    final String str = paramDocument.getClickUrl();
    if (TextUtils.isEmpty(str))
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramDocument.mDocument.docid;
      FinskyLog.e("Empty ad click URL for docid: %s", arrayOfObject);
      return;
    }
    if (sScreenDensity == 0.0F) {
      sScreenDensity = paramContext.getResources().getDisplayMetrics().density;
    }
    instantiateAdShieldIfNeededAndRun(paramContext, new Runnable()
    {
      public final void run()
      {
        Context localContext = this.val$context;
        Document localDocument = paramDocument;
        String str = AdUtils.getAdShieldedClickUrl(this.val$context, str, paramString, this.val$encodedCardDimensions);
        if (TextUtils.isEmpty(str))
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = localDocument.mDocument.docid;
          FinskyLog.e("Empty URL for docid: %s", arrayOfObject);
          return;
        }
        AdUtils.AdClickRequest localAdClickRequest = new AdUtils.AdClickRequest(localContext, str, new AdUtils.4(str), new AdUtils.5(localDocument, str));
        FinskyApp.get().mRequestQueue.add(localAdClickRequest);
      }
    });
  }
  
  private static final class AdClickRequest
    extends StringRequest
  {
    private final Context mContext;
    
    public AdClickRequest(Context paramContext, String paramString, Response.Listener<String> paramListener, Response.ErrorListener paramErrorListener)
    {
      super(paramListener, paramErrorListener);
      this.mContext = paramContext;
    }
    
    public final Map<String, String> getHeaders()
      throws AuthFailureError
    {
      HashMap localHashMap = new HashMap();
      for (;;)
      {
        try
        {
          PackageInfo localPackageInfo = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0);
          boolean bool = this.mContext.getResources().getBoolean(2131427334);
          String str1 = DfeApiContext.sanitizeHeaderValue(Build.VERSION.RELEASE);
          String str2 = DfeApiContext.sanitizeHeaderValue(Build.MODEL);
          String str3 = DfeApiContext.sanitizeHeaderValue(Build.ID);
          String str4 = DfeApiContext.sanitizeHeaderValue(localPackageInfo.versionName);
          if (!bool) {
            continue;
          }
          str5 = "";
          localHashMap.put("User-Agent", String.format(Locale.US, "Mozilla/5.0 (Linux; Android %s; %s Build/%s) AppleWebKit/537.36 Finsky/%s%s", new Object[] { str1, str2, str3, str4, str5 }));
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException)
        {
          String str5;
          FinskyLog.wtf("Can't find our own package", new Object[0]);
          continue;
        }
        if (FinskyApp.get().getExperiments().isEnabled(12604203L)) {
          AuthTokenUtils.putGoogleLoginAuthTokenInHeader(localHashMap, new AndroidAuthenticator(this.mContext, FinskyApp.get().getCurrentAccount(), (String)DfeApiConfig.authTokenType.get(), (byte)0).getAuthToken());
        }
        return localHashMap;
        str5 = " Mobile";
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.AdUtils
 * JD-Core Version:    0.7.0.1
 */