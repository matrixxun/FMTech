package com.google.android.finsky.services;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Preloads.Preload;
import com.google.android.finsky.protos.Preloads.PreloadsResponse;
import com.google.android.finsky.utils.DeviceConfigurationHelper;
import com.google.android.finsky.utils.DeviceConfigurationHelper.Listener;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.play.utils.config.GservicesValue;

public class VpaService
  extends Service
{
  private static VpaService sInstance = null;
  private SetupHoldListener mListener;
  private int mServiceStartId;
  private int mStartupRefCount = 0;
  private boolean mVpaRunning;
  
  private void downloadPackages(String paramString, Preloads.Preload[] paramArrayOfPreload, int paramInt, boolean paramBoolean)
  {
    int i = paramArrayOfPreload.length;
    String[] arrayOfString1 = new String[i];
    int[] arrayOfInt1 = new int[i];
    String[] arrayOfString2 = new String[i];
    int[] arrayOfInt2 = new int[i];
    String[] arrayOfString3 = new String[i];
    String[] arrayOfString4 = new String[i];
    int j = 0;
    if (j < paramArrayOfPreload.length)
    {
      Preloads.Preload localPreload = paramArrayOfPreload[j];
      arrayOfString1[j] = localPreload.docid.backendDocid;
      arrayOfInt1[j] = localPreload.versionCode;
      arrayOfString2[j] = localPreload.title;
      arrayOfInt2[j] = paramInt;
      arrayOfString3[j] = localPreload.deliveryToken;
      if ((localPreload.icon != null) && (localPreload.icon.hasImageUrl) && (localPreload.icon.hasSupportsFifeUrlOptions) && (localPreload.icon.supportsFifeUrlOptions)) {
        arrayOfString4[j] = localPreload.icon.imageUrl;
      }
      for (;;)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = localPreload.docid.backendDocid;
        arrayOfObject[1] = Integer.valueOf(localPreload.versionCode);
        FinskyLog.d("Requesting preload of %s:%d", arrayOfObject);
        j++;
        break;
        arrayOfString4[j] = null;
      }
    }
    RestoreService.restorePackages(getApplicationContext(), false, paramString, paramBoolean, arrayOfString1, arrayOfInt1, arrayOfString2, arrayOfInt2, arrayOfString3, arrayOfString4, true);
  }
  
  private void notifyListener(int paramInt, String paramString, boolean paramBoolean)
  {
    if (this.mListener != null)
    {
      this.mListener.onStatusChange$4a6d67e4(paramInt, null, false, "VpaService");
      if (paramInt == 1) {
        this.mListener = null;
      }
    }
  }
  
  public static boolean registerListener(SetupHoldListener paramSetupHoldListener)
  {
    if (paramSetupHoldListener == null)
    {
      if (sInstance != null) {
        sInstance.mListener = null;
      }
      return true;
    }
    if ((sInstance == null) || (!sInstance.mVpaRunning)) {
      return false;
    }
    VpaService localVpaService = sInstance;
    localVpaService.mListener = paramSetupHoldListener;
    new Handler(localVpaService.getMainLooper()).post(new Runnable()
    {
      public final void run()
      {
        if (VpaService.this.mVpaRunning)
        {
          VpaService.access$800$78ea2085(VpaService.this, 2);
          return;
        }
        VpaService.access$800$78ea2085(VpaService.this, 1);
      }
    });
    return true;
  }
  
  public static boolean shouldHold()
  {
    return (sInstance != null) && (sInstance.mVpaRunning);
  }
  
  public static void startVpa()
  {
    FinskyApp localFinskyApp = FinskyApp.get();
    Intent localIntent = new Intent(localFinskyApp, VpaService.class);
    localIntent.setData(Uri.parse("playsetupservice://startvpa"));
    localFinskyApp.startService(localIntent);
  }
  
  public static void startVpaForNewAccount()
  {
    if (!((Boolean)G.setupWizardAdditionalAccountVpaEnable.get()).booleanValue())
    {
      FinskyLog.d("Skipping additional VPA because disabled.", new Object[0]);
      return;
    }
    if (((Boolean)FinskyPreferences.vpaTriggered.get()).booleanValue())
    {
      FinskyLog.d("Skipping additional VPA because already handled VPA.", new Object[0]);
      return;
    }
    if (!DailyHygiene.isProvisioned())
    {
      FinskyLog.d("Skipping additional VPA because not provisioned.", new Object[0]);
      return;
    }
    if (!((Boolean)FinskyPreferences.setupWizardStartDownloads.get()).booleanValue())
    {
      FinskyLog.d("Setting start-downloads flag, which was cleared", new Object[0]);
      FinskyPreferences.setupWizardStartDownloads.put(Boolean.valueOf(true));
    }
    FinskyApp localFinskyApp = FinskyApp.get();
    Intent localIntent = new Intent(localFinskyApp, VpaService.class);
    localIntent.setData(Uri.parse("playsetupservice://startvpafornewaccount"));
    localFinskyApp.startService(localIntent);
  }
  
  private void vpaGetPreloads(String paramString1, String paramString2)
  {
    DfeApi localDfeApi = FinskyApp.get().getDfeApi(null);
    final String str = localDfeApi.getAccountName();
    localDfeApi.preloads(paramString2, paramString1, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        FinskyLog.w("Failed to retrieve preloads: %s", new Object[] { paramAnonymousVolleyError });
        VpaService.access$302$7ba4ed7a(VpaService.this);
        BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(130).setOperationSuccess(false).setExceptionType(paramAnonymousVolleyError);
        FinskyApp.get().getEventLogger(str).sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
        VpaService.access$200(VpaService.this);
      }
    });
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    sInstance = this;
  }
  
  public void onDestroy()
  {
    notifyListener(1, null, false);
    sInstance = null;
  }
  
  public int onStartCommand(final Intent paramIntent, int paramInt1, int paramInt2)
  {
    this.mServiceStartId = paramInt2;
    this.mStartupRefCount = (1 + this.mStartupRefCount);
    FinskyApp.get().mAppStates.load(new Runnable()
    {
      public final void run()
      {
        VpaService.access$010(VpaService.this);
        if (!VpaService.access$100$3e4d8921(VpaService.this)) {
          VpaService.access$200(VpaService.this);
        }
      }
    });
    return 3;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.services.VpaService
 * JD-Core Version:    0.7.0.1
 */