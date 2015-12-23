package com.google.android.finsky.utils;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.protos.Toc.TocResponse;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public final class GetTocHelper
{
  static void doGetToc(final boolean paramBoolean1, DfeApi paramDfeApi, final boolean paramBoolean2, final Listener paramListener)
  {
    paramDfeApi.getToc(paramBoolean2, DeviceConfigurationHelper.get().getToken(), new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        this.val$listener.onErrorResponse(paramAnonymousVolleyError);
      }
    });
  }
  
  public static void getToc(DfeApi paramDfeApi, boolean paramBoolean, Listener paramListener)
  {
    DeviceConfigurationHelper.get().getToken();
    GcmRegistrationIdHelper.uploadIfNotRegistered(FinskyApp.get(), paramDfeApi);
    doGetToc(true, paramDfeApi, paramBoolean, paramListener);
  }
  
  public static Toc.TocResponse getTocBlocking$207f3d71(DfeApi paramDfeApi)
  {
    Utils.ensureNotOnMainThread();
    final Semaphore localSemaphore = new Semaphore(0);
    Toc.TocResponse[] arrayOfTocResponse = new Toc.TocResponse[1];
    getToc(paramDfeApi, false, new Listener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        this.val$result[0] = null;
        localSemaphore.release();
      }
      
      public final void onResponse(Toc.TocResponse paramAnonymousTocResponse)
      {
        this.val$result[0] = paramAnonymousTocResponse;
        localSemaphore.release();
      }
    });
    try
    {
      boolean bool = localSemaphore.tryAcquire(999L, TimeUnit.SECONDS);
      if (!bool) {
        return null;
      }
      return arrayOfTocResponse[0];
    }
    catch (InterruptedException localInterruptedException) {}
    return null;
  }
  
  public static abstract interface Listener
  {
    public abstract void onErrorResponse(VolleyError paramVolleyError);
    
    public abstract void onResponse(Toc.TocResponse paramTocResponse);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.GetTocHelper
 * JD-Core Version:    0.7.0.1
 */