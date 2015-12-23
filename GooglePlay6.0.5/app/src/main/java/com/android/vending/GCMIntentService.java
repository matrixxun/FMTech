package com.android.vending;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.GcmRegistrationIdHelper.1;
import com.google.android.finsky.utils.GcmRegistrationIdHelper.2;
import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService
  extends GCMBaseIntentService
{
  public GCMIntentService()
  {
    super(new String[] { "932144863878" });
  }
  
  protected final void onError$5ffc00fd(String paramString)
  {
    FinskyLog.d("%s", new Object[] { paramString });
  }
  
  protected final void onMessage$3b2d1350(Intent paramIntent)
  {
    if (FinskyLog.DEBUG)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramIntent.toString();
      FinskyLog.v("%s", arrayOfObject);
    }
  }
  
  protected final void onRegistered$5ffc00fd(String paramString)
  {
    if (FinskyLog.DEBUG) {
      FinskyLog.v("%s", new Object[] { paramString });
    }
    new Handler(Looper.getMainLooper()).post(new GcmRegistrationIdHelper.1());
  }
  
  protected final void onUnregistered$5ffc00fd(Context paramContext)
  {
    FinskyLog.d("GcmUnregistered - invalidating and requesting new id.", new Object[0]);
    new Handler(Looper.getMainLooper()).post(new GcmRegistrationIdHelper.2(paramContext));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.vending.GCMIntentService
 * JD-Core Version:    0.7.0.1
 */