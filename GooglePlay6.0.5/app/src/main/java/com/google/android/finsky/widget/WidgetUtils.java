package com.google.android.finsky.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.SparseIntArray;
import com.google.android.finsky.config.G;
import com.google.android.finsky.library.LibraryReplicators;
import com.google.android.finsky.library.LibraryReplicators.Listener;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.widget.recommendation.RecommendationsStore;
import com.google.android.finsky.widget.recommendation.RecommendationsViewFactory;
import com.google.android.finsky.widget.recommendation.RecommendedWidgetProvider;
import com.google.android.play.utils.config.GservicesValue;

public final class WidgetUtils
{
  private static int[] SUPPORTED_BACKENDS = { 0, 3, 2, 6, 1, 4 };
  
  public static int getAwarenessThreshold(int paramInt)
  {
    switch (paramInt)
    {
    case 3: 
    case 5: 
    default: 
      throw new IllegalArgumentException("Unsupported backend ID (" + paramInt + ")");
    case 2: 
      return ((Integer)G.corpusAwarenessThresholdMusic.get()).intValue();
    case 1: 
      return ((Integer)G.corpusAwarenessThresholdBooks.get()).intValue();
    case 6: 
      return ((Integer)G.corpusAwarenessThresholdMagazines.get()).intValue();
    }
    return ((Integer)G.corpusAwarenessThresholdMovies.get()).intValue();
  }
  
  public static int getBackendIcon(int paramInt)
  {
    switch (paramInt)
    {
    case 5: 
    default: 
      return 2130903046;
    case 3: 
      return 2130903040;
    case 2: 
      return 2130903043;
    case 1: 
      return 2130903041;
    case 6: 
      return 2130903044;
    }
    return 2130903042;
  }
  
  public static int getDips(Context paramContext, int paramInt)
  {
    Resources localResources = paramContext.getResources();
    return (int)(localResources.getDimensionPixelSize(paramInt) / localResources.getDisplayMetrics().density);
  }
  
  public static int getHotseatCheckIcon(int paramInt)
  {
    switch (paramInt)
    {
    case 3: 
    case 5: 
    default: 
      throw new IllegalArgumentException("Unsupported backend ID (" + paramInt + ")");
    case 2: 
      return 2130837779;
    case 1: 
      return 2130837777;
    case 6: 
      return 2130837780;
    }
    return 2130837778;
  }
  
  public static void notifyAccountSwitch(Context paramContext)
  {
    int[] arrayOfInt1 = SUPPORTED_BACKENDS;
    int i = arrayOfInt1.length;
    for (int j = 0; j < i; j++) {
      RecommendationsStore.deleteCachedRecommendations(paramContext, arrayOfInt1[j]);
    }
    int[] arrayOfInt2 = AppWidgetManager.getInstance(paramContext).getAppWidgetIds(new ComponentName(paramContext, RecommendedWidgetProvider.class));
    if (arrayOfInt2.length > 0) {
      RecommendationsViewFactory.notifyDataSetChanged(paramContext, arrayOfInt2);
    }
  }
  
  public static SparseIntArray parseSparseIntArray(String paramString)
  {
    String[] arrayOfString = paramString.split(",");
    SparseIntArray localSparseIntArray = new SparseIntArray(arrayOfString.length);
    int i = arrayOfString.length;
    int j = 0;
    if (j < i)
    {
      String str = arrayOfString[j];
      int k = str.indexOf(':');
      if (k < 0) {
        FinskyLog.w("Invalid tuple: map=%s, tuple=%s", new Object[] { paramString, str });
      }
      for (;;)
      {
        j++;
        break;
        try
        {
          localSparseIntArray.put(Integer.parseInt(str.substring(0, k)), Integer.parseInt(str.substring(k + 1, str.length())));
        }
        catch (NumberFormatException localNumberFormatException)
        {
          FinskyLog.w("Malformed key or value: map=%s, tuple=%s", new Object[] { paramString, str });
        }
      }
    }
    return localSparseIntArray;
  }
  
  public static void registerLibraryMutationsListener(Context paramContext, LibraryReplicators paramLibraryReplicators)
  {
    paramLibraryReplicators.addListener(new LibraryReplicators.Listener()
    {
      public final void onMutationsApplied$12348bc5(String paramAnonymousString)
      {
        String str = WidgetUtils.access$000(paramAnonymousString);
        if (str != null)
        {
          int[] arrayOfInt = WidgetTypeMap.get(this.val$context).getWidgets(RecommendedWidgetProvider.class, str, true);
          if (arrayOfInt.length > 0) {
            RecommendationsViewFactory.notifyDataSetChanged(this.val$context, arrayOfInt);
          }
        }
      }
    });
  }
  
  public static String serializeSparseIntArray(SparseIntArray paramSparseIntArray)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 1;
    for (int j = 0; j < paramSparseIntArray.size(); j++)
    {
      int k = paramSparseIntArray.keyAt(j);
      if (k >= 0)
      {
        if (i == 0) {
          localStringBuilder.append(',');
        }
        i = 0;
        localStringBuilder.append(k).append(':').append(paramSparseIntArray.get(k));
      }
    }
    return localStringBuilder.toString();
  }
  
  public static int translate(String paramString)
  {
    if ("all".equals(paramString)) {
      return 0;
    }
    if ("apps".equals(paramString)) {
      return 3;
    }
    if ("books".equals(paramString)) {
      return 1;
    }
    if ("movies".equals(paramString)) {
      return 4;
    }
    if ("music".equals(paramString)) {
      return 2;
    }
    if ("magazines".equals(paramString)) {
      return 6;
    }
    throw new IllegalArgumentException("Invalid backend type: " + paramString);
  }
  
  public static String translate(int paramInt)
  {
    switch (paramInt)
    {
    case 5: 
    default: 
      throw new IllegalArgumentException("Invalid backend ID: " + paramInt);
    case 3: 
      return "apps";
    case 1: 
      return "books";
    case 4: 
      return "movies";
    case 2: 
      return "music";
    case 6: 
      return "magazines";
    }
    return "all";
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.WidgetUtils
 * JD-Core Version:    0.7.0.1
 */