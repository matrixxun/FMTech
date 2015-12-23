package com.google.android.wallet.common.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Telephony.Sms.Intents;
import android.telephony.SmsMessage;
import android.util.Log;

public final class SmsReceiver
  extends BroadcastReceiver
{
  public OnSmsReceivedListener mListener;
  
  public final void onReceive(Context paramContext, Intent paramIntent)
  {
    if (!"android.provider.Telephony.SMS_RECEIVED".equals(paramIntent.getAction()))
    {
      Log.d("SmsReceiver", "Received intent with action: " + paramIntent.getAction());
      return;
    }
    if (this.mListener == null)
    {
      Log.v("SmsReceiver", "No listener to handle SMS broadcasts");
      return;
    }
    if (Build.VERSION.SDK_INT >= 19)
    {
      this.mListener.onSmsReceived(Telephony.Sms.Intents.getMessagesFromIntent(paramIntent));
      return;
    }
    Object[] arrayOfObject = (Object[])paramIntent.getExtras().get("pdus");
    int i = arrayOfObject.length;
    SmsMessage[] arrayOfSmsMessage = new SmsMessage[i];
    for (int j = 0; j < i; j++) {
      arrayOfSmsMessage[j] = SmsMessage.createFromPdu((byte[])(byte[])arrayOfObject[j]);
    }
    this.mListener.onSmsReceived(arrayOfSmsMessage);
  }
  
  public static abstract interface OnSmsReceivedListener
  {
    public abstract void onSmsReceived(SmsMessage[] paramArrayOfSmsMessage);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.util.SmsReceiver
 * JD-Core Version:    0.7.0.1
 */