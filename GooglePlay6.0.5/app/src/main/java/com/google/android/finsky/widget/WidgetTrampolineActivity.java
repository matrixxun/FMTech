package com.google.android.finsky.widget;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Build.VERSION;

public abstract class WidgetTrampolineActivity
  extends TrampolineActivity
{
  @SuppressLint({"NewApi"})
  public final void finish(int paramInt, String paramString)
  {
    int i = getIntent().getIntExtra("appWidgetId", 0);
    if (paramString != null) {
      WidgetTypeMap.get(this).put(getWidgetClass(), i, paramString);
    }
    Intent localIntent1 = new Intent();
    localIntent1.putExtra("appWidgetId", i);
    setResult(paramInt, localIntent1);
    finish();
    int[] arrayOfInt = { i };
    Intent localIntent2 = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
    localIntent2.putExtra("appWidgetIds", arrayOfInt);
    localIntent2.setClass(this, getWidgetClass());
    sendBroadcast(localIntent2);
    if (Build.VERSION.SDK_INT >= 11) {
      AppWidgetManager.getInstance(this).notifyAppWidgetViewDataChanged(arrayOfInt, 2131756012);
    }
  }
  
  public abstract Class<? extends BaseWidgetProvider> getWidgetClass();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.WidgetTrampolineActivity
 * JD-Core Version:    0.7.0.1
 */