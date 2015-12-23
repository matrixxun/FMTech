package com.google.android.finsky.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.widget.BaseWidgetProvider;
import com.google.android.finsky.widget.recommendation.RecommendationList;
import com.google.android.finsky.widget.recommendation.RecommendationsStore;
import com.google.android.finsky.widget.recommendation.RecommendedWidgetProvider;

public class LoadRecommendationsService
  extends IntentService
{
  public LoadRecommendationsService()
  {
    super(LoadRecommendationsService.class.getSimpleName());
  }
  
  public static void load(Context paramContext, int paramInt1, int paramInt2)
  {
    Intent localIntent = new Intent(paramContext, LoadRecommendationsService.class);
    localIntent.setData(Uri.parse("content://market/load/for/" + paramInt1));
    localIntent.putExtra("backendId", paramInt2);
    localIntent.putExtra("appWidgetId", paramInt1);
    paramContext.startService(localIntent);
  }
  
  protected void onHandleIntent(Intent paramIntent)
  {
    int i = paramIntent.getIntExtra("backendId", -1);
    int j = paramIntent.getIntExtra("appWidgetId", 0);
    DfeApi localDfeApi = FinskyApp.get().getDfeApi(null);
    if (localDfeApi == null) {
      BaseWidgetProvider.update(this, RecommendedWidgetProvider.class, new int[] { j });
    }
    RecommendationList localRecommendationList;
    do
    {
      return;
      localRecommendationList = RecommendationsStore.getRecommendationsOrShowError(this, localDfeApi, i, j, FinskyApp.get().mLibraries);
    } while (localRecommendationList == null);
    RecommendedWidgetProvider.showData(this, j, localRecommendationList.mTitle, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.services.LoadRecommendationsService
 * JD-Core Version:    0.7.0.1
 */