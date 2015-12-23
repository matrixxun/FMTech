package com.google.android.finsky.widget;

import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AuthenticatedActivity;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.WidgetEventData;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.persistence.WriteThroughKeyValueStore;
import com.google.android.play.utils.config.GservicesValue;

public abstract class BaseWidgetProvider
  extends AppWidgetProvider
{
  @SuppressLint({"InlinedApi"})
  private final String[] LOGGABLE_INTENT_ACTIONS = { "android.appwidget.action.APPWIDGET_ENABLED", "android.appwidget.action.APPWIDGET_DISABLED", "android.appwidget.action.APPWIDGET_UPDATE_OPTIONS", "android.appwidget.action.APPWIDGET_DELETED" };
  
  @TargetApi(14)
  public static PendingIntent getAddAccountIntent(Context paramContext)
  {
    return PendingIntent.getActivity(paramContext, 0, AccountManager.newChooseAccountIntent(null, null, AccountHandler.getAccountTypes(), true, null, "androidmarket", null, AuthenticatedActivity.createAddAccountOptions(paramContext)), 0);
  }
  
  @SuppressLint({"NewApi"})
  public static int[] getBoundingBoxes(Context paramContext, int paramInt)
  {
    int[] arrayOfInt = new int[4];
    arrayOfInt[0] = WidgetUtils.getDips(paramContext, 2131493351);
    arrayOfInt[1] = WidgetUtils.getDips(paramContext, 2131493350);
    arrayOfInt[2] = WidgetUtils.getDips(paramContext, 2131493351);
    arrayOfInt[3] = WidgetUtils.getDips(paramContext, 2131493350);
    if (Build.VERSION.SDK_INT < 16) {
      return arrayOfInt;
    }
    Bundle localBundle = AppWidgetManager.getInstance(paramContext).getAppWidgetOptions(paramInt);
    arrayOfInt[0] = localBundle.getInt("appWidgetMinWidth");
    arrayOfInt[1] = localBundle.getInt("appWidgetMinHeight");
    arrayOfInt[2] = localBundle.getInt("appWidgetMaxWidth");
    arrayOfInt[3] = localBundle.getInt("appWidgetMaxHeight");
    return arrayOfInt;
  }
  
  private ComponentName getComponentName(Context paramContext)
  {
    return new ComponentName(paramContext, getClass());
  }
  
  public static void update(Context paramContext, Class<?> paramClass, int... paramVarArgs)
  {
    Intent localIntent = new Intent(paramContext, paramClass);
    localIntent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
    localIntent.putExtra("appWidgetIds", paramVarArgs);
    paramContext.sendBroadcast(localIntent);
  }
  
  public abstract int getWidgetClassId();
  
  public void onDeleted(Context paramContext, int[] paramArrayOfInt)
  {
    WidgetTypeMap localWidgetTypeMap = WidgetTypeMap.get(paramContext);
    int i = paramArrayOfInt.length;
    for (int j = 0; j < i; j++)
    {
      int k = paramArrayOfInt[j];
      String str = WidgetTypeMap.buildKey(getClass(), k);
      localWidgetTypeMap.mKeyValueStore.delete(str);
    }
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    super.onReceive(paramContext, paramIntent);
    AppWidgetManager localAppWidgetManager1 = AppWidgetManager.getInstance(paramContext);
    String str1 = paramIntent.getAction();
    String[] arrayOfString = this.LOGGABLE_INTENT_ACTIONS;
    int i = arrayOfString.length;
    int j = 0;
    int i1;
    int i2;
    if (j < i)
    {
      if (!arrayOfString[j].equals(str1)) {
        break label522;
      }
      i1 = AppWidgetManager.getInstance(paramContext).getAppWidgetIds(getComponentName(paramContext)).length;
      String str3 = paramIntent.getAction();
      i2 = 0;
      if (i2 >= this.LOGGABLE_INTENT_ACTIONS.length) {
        break label671;
      }
      if (!this.LOGGABLE_INTENT_ACTIONS[i2].equals(str3)) {
        break label499;
      }
    }
    for (;;)
    {
      PlayStore.WidgetEventData localWidgetEventData = new PlayStore.WidgetEventData();
      localWidgetEventData.classId = getWidgetClassId();
      localWidgetEventData.hasClassId = true;
      localWidgetEventData.intentActionId = i2;
      localWidgetEventData.hasIntentActionId = true;
      localWidgetEventData.numWidgets = i1;
      localWidgetEventData.hasNumWidgets = true;
      int[] arrayOfInt2;
      String str4;
      if ("android.appwidget.action.APPWIDGET_UPDATE_OPTIONS".equals(paramIntent.getAction()))
      {
        int i3 = paramIntent.getIntExtra("appWidgetId", 0);
        arrayOfInt2 = getBoundingBoxes(paramContext, i3);
        str4 = WidgetTypeMap.get(paramContext).get(getClass(), i3);
      }
      int k;
      for (;;)
      {
        try
        {
          localWidgetEventData.backendId = WidgetUtils.translate(str4);
          localWidgetEventData.hasBackendId = true;
          localWidgetEventData.minWidth = arrayOfInt2[0];
          localWidgetEventData.hasMinWidth = true;
          localWidgetEventData.minHeight = arrayOfInt2[1];
          localWidgetEventData.hasMinHeight = true;
          localWidgetEventData.maxWidth = arrayOfInt2[2];
          localWidgetEventData.hasMaxWidth = true;
          localWidgetEventData.maxHeight = arrayOfInt2[3];
          localWidgetEventData.hasMaxHeight = true;
          BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(507);
          localBackgroundEventBuilder.event.widgetEventData = localWidgetEventData;
          FinskyApp.get().getEventLogger().sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
          if ("com.android.launcher.action.APPWIDGET_DEFAULT_WORKSPACE_CONFIGURE".equals(str1))
          {
            int n = paramIntent.getIntExtra("appWidgetId", 0);
            AppWidgetManager localAppWidgetManager2 = AppWidgetManager.getInstance(paramContext);
            WidgetTypeMap localWidgetTypeMap2 = WidgetTypeMap.get(paramContext);
            String str2 = paramIntent.getStringExtra("type");
            localWidgetTypeMap2.put(getClass(), n, str2);
            updateWidgets(paramContext, localAppWidgetManager2, new int[] { n });
          }
          if ("com.android.vending.action.APPWIDGET_UPDATE_CONSUMPTION_DATA".equals(str1))
          {
            if (!paramIntent.hasExtra("backendId")) {
              break label528;
            }
            WidgetTypeMap localWidgetTypeMap1 = WidgetTypeMap.get(paramContext);
            int m = paramIntent.getIntExtra("backendId", 0);
            updateWidgets(paramContext, localAppWidgetManager1, localWidgetTypeMap1.getWidgets(getClass(), WidgetUtils.translate(m), false));
            updateWidgets(paramContext, localAppWidgetManager1, localWidgetTypeMap1.getWidgets(getClass(), "all", false));
          }
          if ("android.appwidget.action.APPWIDGET_UPDATE".equals(paramIntent.getAction()))
          {
            if (!paramIntent.hasExtra("appWidgetId")) {
              break label579;
            }
            k = paramIntent.getIntExtra("appWidgetId", -1);
            if (k != -1) {
              break label541;
            }
            FinskyLog.d("Received ACTION_APPWIDGET_UPDATE, with invalid widget ID.", new Object[0]);
          }
          return;
          label499:
          i2++;
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          localWidgetEventData.backendId = -1;
          localWidgetEventData.hasBackendId = false;
          continue;
        }
        label522:
        j++;
        break;
        label528:
        FinskyLog.wtf("No backend specified for update!", new Object[0]);
      }
      label541:
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(k);
      FinskyLog.d("Received ACTION_APPWIDGET_UPDATE, updating widget %d.", arrayOfObject2);
      updateWidgets(paramContext, localAppWidgetManager1, new int[] { k });
      return;
      label579:
      if (paramIntent.hasExtra("appWidgetIds"))
      {
        int[] arrayOfInt1 = paramIntent.getIntArrayExtra("appWidgetIds");
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(arrayOfInt1.length);
        FinskyLog.d("Received ACTION_APPWIDGET_UPDATE, updating %d widgets.", arrayOfObject1);
        updateWidgets(paramContext, localAppWidgetManager1, arrayOfInt1);
        return;
      }
      if (((Boolean)G.debugOptionsEnabled.get()).booleanValue())
      {
        updateWidgets(paramContext, localAppWidgetManager1, localAppWidgetManager1.getAppWidgetIds(getComponentName(paramContext)));
        return;
      }
      FinskyLog.e("Refusing to update all widgets; need to narrow scope", new Object[0]);
      return;
      label671:
      i2 = 0;
    }
  }
  
  public void onUpdate(Context paramContext, AppWidgetManager paramAppWidgetManager, int[] paramArrayOfInt)
  {
    updateWidgets(paramContext, paramAppWidgetManager, paramArrayOfInt);
  }
  
  public abstract void updateWidgets(Context paramContext, AppWidgetManager paramAppWidgetManager, int... paramVarArgs);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.BaseWidgetProvider
 * JD-Core Version:    0.7.0.1
 */