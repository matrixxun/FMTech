package com.google.android.finsky.widget.recommendation;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.library.Library;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Reason;
import com.google.android.finsky.protos.SuggestionReasons;
import com.google.android.finsky.services.DismissRecommendationService;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.widget.AdvanceFlipperReceiver;
import com.google.android.finsky.widget.BaseWidgetProvider;
import com.google.android.finsky.widget.WidgetTypeMap;
import com.google.android.finsky.widget.WidgetUtils;
import com.google.android.play.image.BitmapLoader;
import java.util.List;
import java.util.concurrent.Semaphore;

@TargetApi(16)
public final class RecommendationsViewFactory
  implements RemoteViewsService.RemoteViewsFactory
{
  private final int mAppWidgetId;
  private final BitmapLoader mBitmapLoader;
  private final int mContentHeightLandDp;
  private final int mContentHeightPortDp;
  private final Context mContext;
  private DfeApi mDfeApi;
  private RecommendationList mItems;
  private final Library mLibrary;
  private final int mMaxImageHeight;
  private final WidgetTypeMap mTypeMap;
  
  public RecommendationsViewFactory(Context paramContext, int paramInt1, int paramInt2, int paramInt3)
  {
    this.mContext = paramContext;
    this.mAppWidgetId = paramInt1;
    this.mContentHeightLandDp = paramInt2;
    this.mContentHeightPortDp = paramInt3;
    this.mLibrary = FinskyApp.get().mLibraries;
    this.mMaxImageHeight = paramContext.getResources().getDimensionPixelSize(2131492892);
    this.mBitmapLoader = FinskyApp.get().mBitmapLoader;
    this.mTypeMap = WidgetTypeMap.get(paramContext);
  }
  
  private RemoteViews getChildViewAt(int paramInt1, int paramInt2, int paramInt3)
  {
    Recommendation localRecommendation = (Recommendation)this.mItems.mRecommendations.get(paramInt1);
    Bitmap localBitmap = RecommendationsStore.getBitmap(this.mBitmapLoader, localRecommendation, this.mMaxImageHeight);
    int i;
    label51:
    String str1;
    label76:
    int j;
    if (localRecommendation.mImage != null)
    {
      i = localRecommendation.mImage.imageType;
      Bundle localBundle = AppWidgetManager.getInstance(this.mContext).getAppWidgetOptions(this.mAppWidgetId);
      if (paramInt2 != 1) {
        break label194;
      }
      str1 = "appWidgetMinWidth";
      j = localBundle.getInt(str1);
      if (paramInt2 != 2) {
        break label201;
      }
    }
    int m;
    int n;
    label194:
    label201:
    for (int k = this.mContentHeightLandDp;; k = this.mContentHeightPortDp)
    {
      m = localRecommendation.mDocument.mDocument.backendId;
      n = (int)(2.5D * k);
      switch (paramInt3)
      {
      case 5: 
      default: 
        throw new IllegalArgumentException("Invalid backend: " + paramInt3);
        i = 0;
        break label51;
        str1 = "appWidgetMaxWidth";
        break label76;
      }
    }
    int i1;
    label227:
    RemoteViews localRemoteViews;
    Context localContext1;
    Document localDocument;
    SuggestionReasons localSuggestionReasons;
    if (i == 2) {
      if (j < n)
      {
        i1 = 2130969052;
        localRemoteViews = new RemoteViews(this.mContext.getPackageName(), i1);
        localContext1 = this.mContext;
        localDocument = localRecommendation.mDocument;
        localRemoteViews.setTextViewText(2131755778, localDocument.mDocument.title);
        localRemoteViews.setTextViewText(2131756021, localDocument.mDocument.creator);
        localSuggestionReasons = localDocument.getSuggestionReasons();
        if ((localSuggestionReasons == null) || (localSuggestionReasons.reason.length <= 0)) {
          break label552;
        }
      }
    }
    label552:
    for (String str2 = localSuggestionReasons.reason[0].descriptionHtml;; str2 = "")
    {
      localRemoteViews.setTextViewText(2131756022, str2);
      localRemoteViews.setImageViewBitmap(2131756019, localBitmap);
      int i2 = localDocument.mDocument.backendId;
      switch (i2)
      {
      case 5: 
      default: 
        throw new IllegalArgumentException("Unsupported backend ID (" + i2 + ")");
        i1 = 2130969057;
        break label227;
        i1 = 2130969058;
        break label227;
        switch (m)
        {
        case 3: 
        case 5: 
        default: 
          switch (i)
          {
          default: 
            i1 = 2130969058;
          }
          break;
        case 2: 
          i1 = 2130969058;
          break;
        case 1: 
        case 4: 
        case 6: 
          i1 = 2130969056;
          break;
          if (j < n)
          {
            i1 = 2130969052;
            break;
          }
          i1 = 2130969057;
          break;
          i1 = 2130969058;
          break;
          i1 = 2130969056;
          break;
        }
        break;
      }
    }
    int i3 = 2130838091;
    for (;;)
    {
      localRemoteViews.setImageViewResource(2131756017, i3);
      int i4 = CorpusResourceUtils.getTitleContentDescriptionResourceId(localDocument.mDocument.docType);
      if (i4 > 0)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = localDocument.mDocument.title;
        localRemoteViews.setContentDescription(2131755778, localContext1.getString(i4, arrayOfObject));
      }
      Context localContext2 = this.mContext;
      int i5 = this.mAppWidgetId;
      String str3 = localRecommendation.mDocument.mDocument.title;
      localRemoteViews.setOnClickPendingIntent(2131756024, OpenRecommendationReceiver.getIntent(localContext2, localRecommendation.mDocument, paramInt3, i5));
      localRemoteViews.setContentDescription(2131756024, localContext2.getString(2131362006, new Object[] { str3 }));
      localRemoteViews.setOnClickPendingIntent(2131756025, AdvanceFlipperReceiver.getIntent(localContext2, i5));
      localRemoteViews.setOnClickPendingIntent(2131756020, DismissRecommendationService.getDismissPendingIntent(localContext2, i5, localRecommendation.mDocument, paramInt3, paramInt1));
      localRemoteViews.setContentDescription(2131756020, localContext2.getString(2131362005, new Object[] { str3 }));
      return localRemoteViews;
      i3 = 2130838093;
      continue;
      i3 = 2130838092;
      continue;
      i3 = 2130838094;
      continue;
      if (CorpusResourceUtils.isEnterpriseTheme()) {
        i3 = 2130838090;
      } else {
        i3 = 2130838089;
      }
    }
  }
  
  private RecommendationList getRecommendationItems(int paramInt)
  {
    return RecommendationsStore.getRecommendationsOrShowError(this.mContext, this.mDfeApi, paramInt, this.mAppWidgetId, this.mLibrary);
  }
  
  private int getWidgetBackend()
  {
    final Semaphore localSemaphore = new Semaphore(0);
    final int[] arrayOfInt = new int[1];
    Runnable local1 = new Runnable()
    {
      public final void run()
      {
        String str = RecommendationsViewFactory.this.mTypeMap.get(RecommendedWidgetProvider.class, RecommendationsViewFactory.this.mAppWidgetId);
        if (str == null) {
          arrayOfInt[0] = -1;
        }
        for (;;)
        {
          localSemaphore.release();
          return;
          arrayOfInt[0] = WidgetUtils.translate(str);
        }
      }
    };
    if (Looper.getMainLooper() != Looper.myLooper())
    {
      new Handler(Looper.getMainLooper()).post(local1);
      localSemaphore.acquireUninterruptibly();
    }
    return arrayOfInt[0];
  }
  
  public static void notifyDataSetChanged(Context paramContext, int... paramVarArgs)
  {
    AppWidgetManager localAppWidgetManager = AppWidgetManager.getInstance(paramContext);
    if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
      paramVarArgs = localAppWidgetManager.getAppWidgetIds(new ComponentName(paramContext, RecommendedWidgetProvider.class));
    }
    localAppWidgetManager.notifyAppWidgetViewDataChanged(paramVarArgs, 2131756012);
  }
  
  public final int getCount()
  {
    if (this.mItems != null) {
      return this.mItems.size();
    }
    return 0;
  }
  
  public final long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public final RemoteViews getLoadingView()
  {
    return new RemoteViews(this.mContext.getPackageName(), 2130969053);
  }
  
  public final RemoteViews getViewAt(int paramInt)
  {
    int i = getWidgetBackend();
    if (i == -1)
    {
      Context localContext = this.mContext;
      int[] arrayOfInt = new int[1];
      arrayOfInt[0] = this.mAppWidgetId;
      BaseWidgetProvider.update(localContext, RecommendedWidgetProvider.class, arrayOfInt);
    }
    do
    {
      return null;
      if (this.mItems != null) {
        break;
      }
      this.mItems = getRecommendationItems(i);
    } while (this.mItems == null);
    if (paramInt >= this.mItems.size())
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      arrayOfObject[1] = Integer.valueOf(this.mItems.size());
      FinskyLog.d("Item out of bounds (pos = %d, size = %d)", arrayOfObject);
      return null;
    }
    return new RemoteViews(getChildViewAt(paramInt, 2, i), getChildViewAt(paramInt, 1, i));
  }
  
  public final int getViewTypeCount()
  {
    return 4;
  }
  
  public final boolean hasStableIds()
  {
    return true;
  }
  
  public final void onCreate() {}
  
  public final void onDataSetChanged()
  {
    int i = getWidgetBackend();
    if (i == -1)
    {
      Context localContext = this.mContext;
      int[] arrayOfInt = new int[1];
      arrayOfInt[0] = this.mAppWidgetId;
      BaseWidgetProvider.update(localContext, RecommendedWidgetProvider.class, arrayOfInt);
      return;
    }
    this.mDfeApi = FinskyApp.get().getDfeApi(null);
    this.mItems = getRecommendationItems(i);
  }
  
  public final void onDestroy() {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.recommendation.RecommendationsViewFactory
 * JD-Core Version:    0.7.0.1
 */