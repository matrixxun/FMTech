package com.google.android.finsky.utils;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.protos.DeviceConfiguration.DeviceConfigurationProto;
import com.google.android.finsky.protos.UploadDeviceConfig.UploadDeviceConfigResponse;
import java.util.ArrayList;

public abstract class DeviceConfigurationHelper
{
  private static DeviceConfigurationHelper sDeviceConfiguration;
  private ArrayList<RequestRecord> mRequests;
  
  private void doUploadDeviceConfiguration(final RequestRecord paramRequestRecord)
  {
    final DfeApi localDfeApi = paramRequestRecord.dfeApi;
    final Listener localListener = paramRequestRecord.listener;
    final FinskyEventLog localFinskyEventLog = FinskyApp.get().getEventLogger();
    Object localObject;
    if (paramRequestRecord.gcmOnly) {
      localObject = null;
    }
    for (;;)
    {
      try
      {
        String str4 = GcmRegistrationIdHelper.getRegistrationId(FinskyApp.get());
        str1 = str4;
      }
      catch (Exception localException2)
      {
        final String str2;
        String str3;
        Response.Listener local1;
        Response.ErrorListener local2;
        FinskyLog.wtf(localException2, "Exception while getting gcm registration id.", new Object[0]);
        String str1 = null;
        continue;
      }
      str2 = str1;
      str3 = getToken();
      local1 = new Response.Listener() {};
      local2 = new Response.ErrorListener()
      {
        public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
        {
          localFinskyEventLog.logBackgroundEvent(120, null, null, 0, paramAnonymousVolleyError.getClass().getSimpleName(), null);
          FinskyLog.e("Couldn't upload device config", new Object[0]);
          if (localListener != null) {
            localListener.onError(paramAnonymousVolleyError);
          }
          DeviceConfigurationHelper.this.doNextRequest(paramRequestRecord);
        }
      };
      localDfeApi.uploadDeviceConfig((DeviceConfiguration.DeviceConfigurationProto)localObject, str2, str3, local1, local2);
      return;
      try
      {
        DeviceConfiguration.DeviceConfigurationProto localDeviceConfigurationProto = getDeviceConfiguration();
        localObject = localDeviceConfigurationProto;
      }
      catch (Exception localException1)
      {
        FinskyLog.wtf(localException1, "Exception while getting device configuration.", new Object[0]);
        if (localListener != null) {
          localListener.onError(new ServerError());
        }
        doNextRequest(paramRequestRecord);
        return;
      }
    }
  }
  
  public static DeviceConfigurationHelper get()
  {
    try
    {
      if (sDeviceConfiguration == null) {
        sDeviceConfiguration = new PhoneDeviceConfigurationHelper();
      }
      DeviceConfigurationHelper localDeviceConfigurationHelper = sDeviceConfiguration;
      return localDeviceConfigurationHelper;
    }
    finally {}
  }
  
  public static int getKeyboardConfigId(int paramInt)
  {
    switch (paramInt)
    {
    case 0: 
    default: 
      return 0;
    case 1: 
      return 1;
    case 2: 
      return 2;
    }
    return 3;
  }
  
  public static int getNavigationId(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return 0;
    case 1: 
      return 1;
    case 2: 
      return 2;
    case 3: 
      return 3;
    }
    return 4;
  }
  
  public static int getScreenLayoutSizeId(int paramInt)
  {
    switch (paramInt & 0xF)
    {
    default: 
      return 0;
    case 1: 
      return 1;
    case 2: 
      return 2;
    case 3: 
      return 3;
    }
    return 4;
  }
  
  public static int getTouchScreenId(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return 0;
    case 1: 
      return 1;
    case 2: 
      return 2;
    }
    return 3;
  }
  
  final void doNextRequest(RequestRecord paramRequestRecord)
  {
    if ((this.mRequests == null) || (this.mRequests.size() == 0)) {
      FinskyLog.wtf("Empty request queue", new Object[0]);
    }
    do
    {
      return;
      if ((RequestRecord)this.mRequests.remove(0) != paramRequestRecord) {
        FinskyLog.wtf("Completed request mismatch", new Object[0]);
      }
    } while (this.mRequests.size() <= 0);
    doUploadDeviceConfiguration((RequestRecord)this.mRequests.get(0));
  }
  
  public abstract DeviceConfiguration.DeviceConfigurationProto getDeviceConfiguration();
  
  public abstract String getToken();
  
  public abstract void invalidateToken();
  
  public final void postUploadRequest(DfeApi paramDfeApi, boolean paramBoolean, Listener paramListener)
  {
    RequestRecord localRequestRecord = new RequestRecord(paramDfeApi, paramBoolean, paramListener);
    if (this.mRequests == null) {
      this.mRequests = new ArrayList();
    }
    this.mRequests.add(localRequestRecord);
    if (this.mRequests.size() == 1) {
      doUploadDeviceConfiguration(localRequestRecord);
    }
  }
  
  public abstract void setToken(String paramString);
  
  public static abstract interface Listener
  {
    public abstract void onError(VolleyError paramVolleyError);
    
    public abstract void onSuccess();
  }
  
  private static final class RequestRecord
  {
    public final DfeApi dfeApi;
    public final boolean gcmOnly;
    public final DeviceConfigurationHelper.Listener listener;
    
    public RequestRecord(DfeApi paramDfeApi, boolean paramBoolean, DeviceConfigurationHelper.Listener paramListener)
    {
      this.dfeApi = paramDfeApi;
      this.gcmOnly = paramBoolean;
      this.listener = paramListener;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.DeviceConfigurationHelper
 * JD-Core Version:    0.7.0.1
 */