package com.google.android.finsky.widget.consumption;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.google.android.finsky.utils.IntentUtils;

public class EnableAppReceiver
  extends BroadcastReceiver
{
  public static PendingIntent getEnableIntent(Context paramContext, int paramInt)
  {
    Intent localIntent = new Intent(paramContext, EnableAppReceiver.class);
    localIntent.setData(Uri.parse("content://" + paramContext.getPackageName() + "/enable/" + paramInt));
    localIntent.putExtra("backendId", paramInt);
    return PendingIntent.getBroadcast(paramContext, 0, localIntent, 0);
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    int i = paramIntent.getIntExtra("backendId", 0);
    String str = IntentUtils.getPackageName(i);
    paramContext.getPackageManager().setApplicationEnabledSetting(str, 1, 0);
    Intent localIntent = new Intent("com.android.vending.action.APPWIDGET_UPDATE_CONSUMPTION_DATA");
    localIntent.setClass(paramContext, NowPlayingWidgetProvider.class);
    localIntent.putExtra("backendId", i);
    paramContext.sendBroadcast(localIntent);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.consumption.EnableAppReceiver
 * JD-Core Version:    0.7.0.1
 */