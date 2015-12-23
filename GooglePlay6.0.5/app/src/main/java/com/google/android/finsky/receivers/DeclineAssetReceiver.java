package com.google.android.finsky.receivers;

import android.content.Context;
import android.content.Intent;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.download.DownloadReceiver;
import com.google.android.finsky.utils.FinskyLog;

public class DeclineAssetReceiver
  extends DownloadReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (!paramIntent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {}
    do
    {
      return;
      setResultCode(-1);
    } while (!paramIntent.getStringExtra("from").equals("google.com"));
    String str1 = paramIntent.getStringExtra("asset_package");
    str2 = paramIntent.getStringExtra("decline_reason");
    int i = -1;
    if (str2 != null) {}
    try
    {
      int j = Integer.valueOf(str2).intValue();
      i = j;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      for (;;)
      {
        Object[] arrayOfObject;
        FinskyLog.w("Non-numeric decline reason: %s", new Object[] { str2 });
      }
    }
    arrayOfObject = new Object[2];
    arrayOfObject[0] = str1;
    arrayOfObject[1] = Integer.valueOf(i);
    FinskyLog.d("Received PURCHASE_DECLINED tickle for %s reason=%d", arrayOfObject);
    FinskyApp.get().getEventLogger().logBackgroundEvent(200, str1, String.valueOf(i), 0, null, null);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.receivers.DeclineAssetReceiver
 * JD-Core Version:    0.7.0.1
 */