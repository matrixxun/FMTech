package com.google.android.finsky.receivers;

import android.content.Context;
import android.content.Intent;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.download.DownloadReceiver;

public class DownloadTickleReceiver
  extends DownloadReceiver
{
  public void onReceive(Context paramContext, final Intent paramIntent)
  {
    if (!paramIntent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {}
    do
    {
      return;
      setResultCode(-1);
    } while (!paramIntent.getStringExtra("from").equals("google.com"));
    FinskyApp.get().mAppStates.load(new Runnable()
    {
      public final void run()
      {
        DownloadTickleReceiver.access$000(DownloadTickleReceiver.this, paramIntent);
      }
    });
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.receivers.DownloadTickleReceiver
 * JD-Core Version:    0.7.0.1
 */