package com.google.android.finsky.receivers;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.android.vending.MarketWidgetProvider;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.widget.consumption.NowPlayingWidgetProvider;
import com.google.android.finsky.widget.recommendation.RecommendedWidgetProvider;

public class UpdateWidgetsReceiver
  extends BroadcastReceiver
{
  private static Class<?>[] COMPONENTS = { MarketWidgetProvider.class, RecommendedWidgetProvider.class, NowPlayingWidgetProvider.class };
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    AppWidgetManager localAppWidgetManager = AppWidgetManager.getInstance(paramContext);
    for (Class localClass : COMPONENTS)
    {
      ComponentName localComponentName = new ComponentName(paramContext, localClass);
      int[] arrayOfInt = localAppWidgetManager.getAppWidgetIds(localComponentName);
      Intent localIntent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
      localIntent.putExtra("appWidgetIds", arrayOfInt);
      localIntent.setComponent(localComponentName);
      paramContext.sendBroadcast(localIntent);
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = Integer.valueOf(arrayOfInt.length);
      arrayOfObject[1] = localClass.getSimpleName();
      arrayOfObject[2] = paramIntent.getAction();
      FinskyLog.d("Updated %d %s widgets (%s)", arrayOfObject);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.receivers.UpdateWidgetsReceiver
 * JD-Core Version:    0.7.0.1
 */