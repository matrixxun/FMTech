package com.google.android.finsky.utils;

import android.text.TextUtils;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.protos.SelfUpdateResponse;

public final class GetSelfUpdateHelper
{
  static void doGetSelfUpdate(boolean paramBoolean, final DfeApi paramDfeApi, final DeviceConfigurationHelper paramDeviceConfigurationHelper, final Listener paramListener)
  {
    paramDfeApi.getSelfUpdate(paramDeviceConfigurationHelper.getToken(), new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        this.val$listener.onErrorResponse(paramAnonymousVolleyError);
      }
    });
  }
  
  static void doRequestToken(boolean paramBoolean, final DfeApi paramDfeApi, final DeviceConfigurationHelper paramDeviceConfigurationHelper, final Listener paramListener)
  {
    paramDeviceConfigurationHelper.postUploadRequest(paramDfeApi, false, new DeviceConfigurationHelper.Listener()
    {
      public final void onError(VolleyError paramAnonymousVolleyError)
      {
        FinskyLog.w("Upload device configuration failed - try selfupdate anyway", new Object[0]);
        GetSelfUpdateHelper.doGetSelfUpdate(this.val$allowRetry, paramDfeApi, paramDeviceConfigurationHelper, paramListener);
      }
      
      public final void onSuccess()
      {
        GetSelfUpdateHelper.doGetSelfUpdate(this.val$allowRetry, paramDfeApi, paramDeviceConfigurationHelper, paramListener);
      }
    });
  }
  
  public static void getSelfUpdate(DfeApi paramDfeApi, DeviceConfigurationHelper paramDeviceConfigurationHelper, Listener paramListener)
  {
    if (TextUtils.isEmpty(paramDeviceConfigurationHelper.getToken()))
    {
      doRequestToken(true, paramDfeApi, paramDeviceConfigurationHelper, paramListener);
      return;
    }
    doGetSelfUpdate(true, paramDfeApi, paramDeviceConfigurationHelper, paramListener);
  }
  
  public static abstract interface Listener
  {
    public abstract void onErrorResponse(VolleyError paramVolleyError);
    
    public abstract void onResponse(SelfUpdateResponse paramSelfUpdateResponse);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.GetSelfUpdateHelper
 * JD-Core Version:    0.7.0.1
 */