package com.google.android.finsky.receivers;

import android.content.Context;
import android.content.Intent;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.download.DownloadReceiver;
import com.google.android.finsky.utils.Notifier;
import java.util.Set;

public class RemoveAssetReceiver
  extends DownloadReceiver
{
  private static Notifier sNotificationHelper;
  
  public static void initialize(Notifier paramNotifier)
  {
    sNotificationHelper = paramNotifier;
  }
  
  public void onReceive(final Context paramContext, final Intent paramIntent)
  {
    if (!paramIntent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {}
    do
    {
      return;
      setResultCode(-1);
    } while ((!paramIntent.getStringExtra("from").equals("google.com")) || (!paramIntent.getCategories().contains("REMOVE_ASSET")));
    FinskyApp.get().mAppStates.load(new Runnable()
    {
      public final void run()
      {
        RemoveAssetReceiver.access$000(RemoveAssetReceiver.this, paramContext, paramIntent);
      }
    });
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.receivers.RemoveAssetReceiver
 * JD-Core Version:    0.7.0.1
 */