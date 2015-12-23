package com.google.android.wallet.common.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public final class SmsSender
{
  public final Context mAppContext;
  public SmsSendListener mListener;
  
  public SmsSender(Context paramContext, SmsSendListener paramSmsSendListener)
  {
    this.mAppContext = paramContext;
    this.mListener = paramSmsSendListener;
  }
  
  public final void dispatch(final int paramInt)
  {
    if (this.mListener == null) {
      return;
    }
    new Handler(Looper.getMainLooper()).post(new Runnable()
    {
      public final void run()
      {
        SmsSender.this.mListener.onResult(paramInt);
      }
    });
  }
  
  public static abstract interface SmsSendListener
  {
    public abstract void onResult(int paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.util.SmsSender
 * JD-Core Version:    0.7.0.1
 */