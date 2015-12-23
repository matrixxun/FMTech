package com.google.android.finsky.widget.recommendation;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.protos.Toc.CorpusMetadata;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.services.LoadRecommendationsService;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.widget.BaseWidgetProvider;
import com.google.android.finsky.widget.TrampolineActivity;
import com.google.android.finsky.widget.WidgetTypeMap;
import com.google.android.finsky.widget.WidgetUtils;

@TargetApi(16)
public class RecommendedWidgetProvider
  extends BaseWidgetProvider
{
  private static final int[] VIEW_IDS = { 2131755806, 2131756012, 2131755775, 2131756014, 2131756013, 2131756016 };
  
  private static RemoteViews getBaseWithVisibleViews(Context paramContext, int... paramVarArgs)
  {
    RemoteViews localRemoteViews = new RemoteViews(paramContext.getPackageName(), 2130969047);
    localRemoteViews.setImageViewResource(2131755777, 2130837865);
    localRemoteViews.setTextViewText(2131755778, "");
    localRemoteViews.setContentDescription(2131755777, "");
    int[] arrayOfInt = VIEW_IDS;
    int i = arrayOfInt.length;
    for (int j = 0; j < i; j++) {
      localRemoteViews.setViewVisibility(arrayOfInt[j], 8);
    }
    int k = paramVarArgs.length;
    for (int m = 0; m < k; m++) {
      localRemoteViews.setViewVisibility(paramVarArgs[m], 0);
    }
    return localRemoteViews;
  }
  
  public static void showData(Context paramContext, int paramInt1, String paramString, int paramInt2)
  {
    RemoteViews localRemoteViews = getBaseWithVisibleViews(paramContext, new int[] { 2131756012 });
    localRemoteViews.setEmptyView(2131756012, 2131756013);
    int[] arrayOfInt = getBoundingBoxes(paramContext, paramInt1);
    int i = WidgetUtils.getDips(paramContext, 2131493056);
    int j = arrayOfInt[1] - i;
    int k = arrayOfInt[3] - i;
    Intent localIntent = new Intent(paramContext, RecommendationsViewService.class);
    localIntent.setData(Uri.parse("content://market/factory/for/" + paramInt1));
    localIntent.putExtra("appWidgetId", paramInt1);
    localIntent.putExtra("RecWidget.heightLandscape", j);
    localIntent.putExtra("RecWidget.heightPortrait", k);
    localRemoteViews.setRemoteAdapter(2131756012, localIntent);
    DfeToc localDfeToc;
    Object localObject;
    if (!TextUtils.isEmpty(paramString))
    {
      localRemoteViews.setTextViewText(2131755778, paramString.toUpperCase());
      localDfeToc = FinskyApp.get().mToc;
      localObject = null;
      if (localDfeToc != null) {
        break label232;
      }
      if (TextUtils.isEmpty((CharSequence)localObject)) {
        break label275;
      }
      localRemoteViews.setOnClickPendingIntent(2131755779, PendingIntent.getActivity(paramContext, 0, IntentUtils.createBrowseIntent(paramContext, (String)localObject, paramString, paramInt2, true), 0));
      localRemoteViews.setViewVisibility(2131755779, 0);
      localRemoteViews.setContentDescription(2131755779, paramString);
    }
    for (;;)
    {
      AppWidgetManager.getInstance(paramContext).updateAppWidget(paramInt1, localRemoteViews);
      AppWidgetManager.getInstance(paramContext).notifyAppWidgetViewDataChanged(paramInt1, 2131756012);
      return;
      label232:
      if (paramInt2 == 0)
      {
        localObject = localDfeToc.mToc.homeUrl;
        break;
      }
      Toc.CorpusMetadata localCorpusMetadata = localDfeToc.getCorpus(paramInt2);
      localObject = null;
      if (localCorpusMetadata == null) {
        break;
      }
      localObject = localCorpusMetadata.landingUrl;
      break;
      label275:
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt2);
      FinskyLog.e("No browse URL found for backend=%s", arrayOfObject);
    }
  }
  
  public static void showError(Context paramContext, int paramInt, String paramString)
  {
    RemoteViews localRemoteViews = getBaseWithVisibleViews(paramContext, new int[] { 2131755806 });
    localRemoteViews.setTextViewText(2131755274, paramString);
    AppWidgetManager.getInstance(paramContext).updateAppWidget(paramInt, localRemoteViews);
  }
  
  private static void showInteractiveError(Context paramContext, int paramInt1, int paramInt2, PendingIntent paramPendingIntent)
  {
    RemoteViews localRemoteViews = getBaseWithVisibleViews(paramContext, new int[] { 2131756014, 2131755775 });
    localRemoteViews.setTextViewText(2131756015, paramContext.getString(paramInt2));
    localRemoteViews.setOnClickPendingIntent(2131755775, paramPendingIntent);
    AppWidgetManager.getInstance(paramContext).updateAppWidget(paramInt1, localRemoteViews);
  }
  
  protected final int getWidgetClassId()
  {
    return 2;
  }
  
  public void onAppWidgetOptionsChanged(Context paramContext, AppWidgetManager paramAppWidgetManager, int paramInt, Bundle paramBundle)
  {
    updateWidgets(paramContext, paramAppWidgetManager, new int[] { paramInt });
    paramAppWidgetManager.notifyAppWidgetViewDataChanged(paramInt, 2131756012);
  }
  
  protected final void updateWidgets(Context paramContext, AppWidgetManager paramAppWidgetManager, int... paramVarArgs)
  {
    DfeApi localDfeApi = FinskyApp.get().getDfeApi(null);
    int i = paramVarArgs.length;
    int j = 0;
    if (j < i)
    {
      int k = paramVarArgs[j];
      if (localDfeApi == null) {
        showInteractiveError(paramContext, k, 2131361869, getAddAccountIntent(paramContext));
      }
      for (;;)
      {
        j++;
        break;
        String str = WidgetTypeMap.get(paramContext).get(RecommendedWidgetProvider.class, k);
        if (str == null)
        {
          showInteractiveError(paramContext, k, 2131362919, TrampolineActivity.getPendingLaunchIntent(paramContext, RecommendedTrampoline.class, k));
        }
        else
        {
          int m = WidgetUtils.translate(str);
          paramAppWidgetManager.updateAppWidget(k, getBaseWithVisibleViews(paramContext, new int[] { 2131756016 }));
          LoadRecommendationsService.load(paramContext, k, m);
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.recommendation.RecommendedWidgetProvider
 * JD-Core Version:    0.7.0.1
 */