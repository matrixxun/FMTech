package com.google.android.finsky.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.RemoteViews;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.MainActivity;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.config.G;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.image.ThumbnailUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.utils.config.GservicesValue;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public abstract class FinskyWidgetProvider
  extends BaseWidgetProvider
{
  private static final HashMap<String, int[]> DOCUMENT_TYPES = new HashMap() {};
  private static final WidgetModel.ImageSelector mImageSelector = new WidgetModel.ImageSelector()
  {
    public final Common.Image getImage(Document paramAnonymousDocument, int paramAnonymousInt)
    {
      return ThumbnailUtils.getBestImage(paramAnonymousDocument.getImages(2), 0, paramAnonymousInt);
    }
  };
  private final BitmapLoader mBitmapLoader = FinskyApp.get().mBitmapLoader;
  private final HashMap<String, String> mUrls = new HashMap();
  
  public FinskyWidgetProvider()
  {
    this.mUrls.put("apps", G.appsWidgetUrl.get());
    this.mUrls.put("books", G.booksWidgetUrl.get());
    this.mUrls.put("movies", G.moviesWidgetUrl.get());
    this.mUrls.put("music", G.musicWidgetUrl.get());
  }
  
  private String getBackend(WidgetTypeMap paramWidgetTypeMap, int paramInt)
  {
    return paramWidgetTypeMap.get(getClass(), paramInt);
  }
  
  private static void rebindWidget$2888d781(Context paramContext, int paramInt, WidgetModel paramWidgetModel)
  {
    RemoteViews localRemoteViews1 = new RemoteViews(paramContext.getPackageName(), 2130969188);
    if (paramWidgetModel.mItems.isEmpty()) {
      showEmptyState(paramContext, localRemoteViews1);
    }
    for (;;)
    {
      AppWidgetManager.getInstance(paramContext).updateAppWidget(paramInt, localRemoteViews1);
      return;
      localRemoteViews1.removeAllViews(2131756012);
      localRemoteViews1.setViewVisibility(2131756233, 8);
      localRemoteViews1.setViewVisibility(2131756012, 0);
      Iterator localIterator = paramWidgetModel.mItems.iterator();
      while (localIterator.hasNext())
      {
        WidgetModel.PromotionalItem localPromotionalItem = (WidgetModel.PromotionalItem)localIterator.next();
        RemoteViews localRemoteViews2 = new RemoteViews(paramContext.getPackageName(), 2130969187);
        localRemoteViews2.setTextViewText(2131755778, localPromotionalItem.title);
        localRemoteViews2.setTextViewText(2131756021, localPromotionalItem.developer);
        localRemoteViews2.setImageViewBitmap(2131756019, localPromotionalItem.image);
        Intent localIntent = IntentUtils.createViewDocumentIntent(paramContext, localPromotionalItem.doc);
        localIntent.addFlags(268435456);
        localRemoteViews2.setOnClickPendingIntent(2131756231, PendingIntent.getActivity(paramContext, 0, localIntent, 134217728));
        localRemoteViews2.setImageViewResource(2131756232, 2130903045);
        localRemoteViews1.addView(2131756012, localRemoteViews2);
      }
    }
  }
  
  private static void showEmptyState(Context paramContext, RemoteViews paramRemoteViews)
  {
    paramRemoteViews.setImageViewResource(2131756234, 2130903045);
    paramRemoteViews.removeAllViews(2131756012);
    paramRemoteViews.setViewVisibility(2131756233, 0);
    paramRemoteViews.setViewVisibility(2131756012, 8);
    paramRemoteViews.setOnClickPendingIntent(2131756233, PendingIntent.getActivity(paramContext, 0, new Intent(paramContext, MainActivity.class), 134217728));
  }
  
  private static void showError(Context paramContext, int paramInt)
  {
    RemoteViews localRemoteViews = new RemoteViews(paramContext.getPackageName(), 2130969188);
    showEmptyState(paramContext, localRemoteViews);
    AppWidgetManager.getInstance(paramContext).updateAppWidget(paramInt, localRemoteViews);
  }
  
  protected final void updateWidgets(final Context paramContext, AppWidgetManager paramAppWidgetManager, int... paramVarArgs)
  {
    if (paramVarArgs == null) {
      return;
    }
    final WidgetTypeMap localWidgetTypeMap = WidgetTypeMap.get(paramContext);
    DfeApi localDfeApi1 = FinskyApp.get().getDfeApi(null);
    int i = paramVarArgs.length;
    int j = 0;
    label27:
    final int k;
    final WidgetModel localWidgetModel;
    if (j < i)
    {
      k = paramVarArgs[j];
      if (getBackend(localWidgetTypeMap, k) == null)
      {
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = Integer.valueOf(k);
        arrayOfObject2[1] = "apps";
        FinskyLog.d("Defaulting %d to %s", arrayOfObject2);
        localWidgetTypeMap.put(getClass(), k, "apps");
      }
      localWidgetModel = new WidgetModel(this.mBitmapLoader, (int[])DOCUMENT_TYPES.get(getBackend(localWidgetTypeMap, k)), mImageSelector);
      localWidgetModel.mUpdatePending = false;
      localWidgetModel.mItems.clear();
      rebindWidget$2888d781(paramContext, k, localWidgetModel);
      if (localDfeApi1 != null) {
        break label166;
      }
      showError(paramContext, k);
    }
    for (;;)
    {
      j++;
      break label27;
      break;
      label166:
      rebindWidget$2888d781(paramContext, k, localWidgetModel);
      DfeApi localDfeApi2 = FinskyApp.get().getDfeApi(null);
      String str = (String)this.mUrls.get(localWidgetTypeMap.get(getClass(), k));
      if (str == null)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(k);
        FinskyLog.d("%d has null URL", arrayOfObject1);
        showError(paramContext, k);
      }
      else
      {
        WidgetModel.RefreshListener local3 = new WidgetModel.RefreshListener()
        {
          public final void onData()
          {
            FinskyWidgetProvider.access$100$f9eafd1(FinskyWidgetProvider.this, paramContext, k, localWidgetModel);
          }
          
          public final void onError(String paramAnonymousString)
          {
            FinskyLog.e("Failed to load list for widget! %s", new Object[] { paramAnonymousString });
            FinskyWidgetProvider.showError(FinskyWidgetProvider.this, paramContext, k);
          }
        };
        if (!localWidgetModel.mUpdatePending)
        {
          localWidgetModel.mUpdatePending = true;
          localWidgetModel.mListener = local3;
          if (localWidgetModel.mList != null)
          {
            localWidgetModel.mList.removeDataChangedListener(localWidgetModel);
            localWidgetModel.mList.removeErrorListener(localWidgetModel);
          }
          localWidgetModel.mMaxHeight = paramContext.getResources().getDimensionPixelSize(localWidgetModel.mImageHeightResource);
          localWidgetModel.mList = new DfeList(localDfeApi2, str, false);
          localWidgetModel.mList.addErrorListener(localWidgetModel);
          localWidgetModel.mList.addDataChangedListener(localWidgetModel);
          localWidgetModel.mList.startLoadItems();
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.FinskyWidgetProvider
 * JD-Core Version:    0.7.0.1
 */