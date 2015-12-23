package com.google.android.finsky.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

@TargetApi(11)
public class AdvanceFlipperReceiver
  extends BroadcastReceiver
{
  public static void advance(Context paramContext, int paramInt)
  {
    if (paramInt == 0) {
      return;
    }
    RemoteViews localRemoteViews = new RemoteViews(paramContext.getPackageName(), 2130969047);
    localRemoteViews.showNext(2131756012);
    AppWidgetManager.getInstance(paramContext).partiallyUpdateAppWidget(paramInt, localRemoteViews);
  }
  
  public static PendingIntent getIntent(Context paramContext, int paramInt)
  {
    Intent localIntent = new Intent(paramContext, AdvanceFlipperReceiver.class);
    localIntent.setData(Uri.parse("content://widget/advance/" + paramInt));
    localIntent.putExtra("appWidgetId", paramInt);
    return PendingIntent.getBroadcast(paramContext, 0, localIntent, 0);
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    advance(paramContext, paramIntent.getIntExtra("appWidgetId", 0));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.AdvanceFlipperReceiver
 * JD-Core Version:    0.7.0.1
 */