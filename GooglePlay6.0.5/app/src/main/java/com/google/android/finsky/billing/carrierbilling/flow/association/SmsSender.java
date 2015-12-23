package com.google.android.finsky.billing.carrierbilling.flow.association;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import com.google.android.finsky.utils.FinskyLog;

public final class SmsSender
{
  static SmsSendListener mListener;
  
  static void dispatch(int paramInt)
  {
    if (mListener == null) {
      return;
    }
    new Handler(Looper.getMainLooper()).post(new Runnable()
    {
      public final void run()
      {
        SmsSender.mListener.onResult(this.val$result);
      }
    });
  }
  
  public static abstract interface SmsSendListener
  {
    public abstract void onResult(int paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.carrierbilling.flow.association.SmsSender
 * JD-Core Version:    0.7.0.1
 */