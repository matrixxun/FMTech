package com.google.android.finsky.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.MainActivity;
import com.google.android.finsky.utils.FinskyLog;

public class DownloadBroadcastReceiver
  extends BroadcastReceiver
{
  private static DownloadQueue sDownloadQueue;
  
  public static void initialize(DownloadQueue paramDownloadQueue)
  {
    sDownloadQueue = paramDownloadQueue;
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    FinskyLog.d("Intent received at DownloadBroadcastReceiver", new Object[0]);
    final Uri localUri = sDownloadQueue.getDownloadManager().getUriFromBroadcast(paramIntent);
    String str = paramIntent.getAction();
    final boolean bool1 = false;
    final boolean bool3;
    if ("android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED".equals(str)) {
      bool3 = true;
    }
    while (sDownloadQueue.getDownloadByContentUri(localUri) == null)
    {
      FinskyLog.d("DownloadBroadcastReceiver could not find %s in queue.", new Object[] { localUri });
      if (bool3)
      {
        FinskyApp localFinskyApp = FinskyApp.get();
        if (FinskyApp.get().getCurrentAccount() != null)
        {
          Intent localIntent = MainActivity.getMyDownloadsIntent(localFinskyApp);
          localIntent.setFlags(268435456);
          localFinskyApp.startActivity(localIntent);
        }
      }
      return;
      if ("android.intent.action.DOWNLOAD_COMPLETE".equals(str))
      {
        bool1 = true;
        bool3 = false;
      }
      else
      {
        boolean bool2 = "android.intent.action.DOWNLOAD_COMPLETED".equals(str);
        bool3 = false;
        bool1 = false;
        if (bool2)
        {
          bool1 = true;
          bool3 = false;
        }
      }
    }
    new AsyncTask() {}.execute(new Void[0]);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.DownloadBroadcastReceiver
 * JD-Core Version:    0.7.0.1
 */