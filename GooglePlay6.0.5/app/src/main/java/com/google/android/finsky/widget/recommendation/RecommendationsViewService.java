package com.google.android.finsky.widget.recommendation;

import android.annotation.TargetApi;
import android.content.Intent;
import android.widget.RemoteViewsService;
import android.widget.RemoteViewsService.RemoteViewsFactory;

@TargetApi(11)
public class RecommendationsViewService
  extends RemoteViewsService
{
  public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent paramIntent)
  {
    return new RecommendationsViewFactory(this, paramIntent.getIntExtra("appWidgetId", 0), paramIntent.getIntExtra("RecWidget.heightLandscape", 0), paramIntent.getIntExtra("RecWidget.heightPortrait", 0));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.recommendation.RecommendationsViewService
 * JD-Core Version:    0.7.0.1
 */