package com.google.android.finsky.widget;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.finsky.utils.persistence.FileBasedKeyValueStore;
import com.google.android.finsky.utils.persistence.KeyValueStore;
import com.google.android.finsky.utils.persistence.WriteThroughKeyValueStore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WidgetTypeMap
{
  private static WidgetTypeMap sInstance;
  final WriteThroughKeyValueStore mKeyValueStore;
  
  private WidgetTypeMap(Context paramContext)
  {
    this(new FileBasedKeyValueStore(paramContext.getDir("widgets", 0), WidgetTypeMap.class.getSimpleName()));
  }
  
  private WidgetTypeMap(KeyValueStore paramKeyValueStore)
  {
    this.mKeyValueStore = new WriteThroughKeyValueStore(paramKeyValueStore);
    WriteThroughKeyValueStore localWriteThroughKeyValueStore = this.mKeyValueStore;
    WriteThroughKeyValueStore.ensureOnMainThread();
    localWriteThroughKeyValueStore.mDataMap = localWriteThroughKeyValueStore.mBackingStore.fetchAll();
    localWriteThroughKeyValueStore.mOnCompleteListeners.clear();
  }
  
  static String buildKey(Class<? extends BaseWidgetProvider> paramClass, int paramInt)
  {
    return paramClass.getSimpleName() + "#" + String.valueOf(paramInt);
  }
  
  public static WidgetTypeMap get(Context paramContext)
  {
    if (sInstance == null) {
      sInstance = new WidgetTypeMap(paramContext);
    }
    return sInstance;
  }
  
  public final String get(Class<? extends BaseWidgetProvider> paramClass, int paramInt)
  {
    String str = buildKey(paramClass, paramInt);
    Map localMap = this.mKeyValueStore.get(str);
    if (localMap != null) {
      return (String)localMap.get("type");
    }
    return null;
  }
  
  public final int[] getWidgets(Class<? extends BaseWidgetProvider> paramClass, String paramString, boolean paramBoolean)
  {
    ArrayList localArrayList = new ArrayList();
    String str1 = paramClass.getSimpleName();
    Iterator localIterator = this.mKeyValueStore.fetchAll().values().iterator();
    while (localIterator.hasNext())
    {
      Map localMap = (Map)localIterator.next();
      String str2 = (String)localMap.get("type");
      if (((paramString.equals(str2)) || ((paramBoolean) && ("all".equals(str2)))) && (str1.equals((String)localMap.get("widgetProvider")))) {
        localArrayList.add(localMap.get("appWidgetId"));
      }
    }
    int i = localArrayList.size();
    int[] arrayOfInt = new int[i];
    for (int j = 0; j < i; j++) {
      arrayOfInt[j] = Integer.valueOf((String)localArrayList.get(j)).intValue();
    }
    return arrayOfInt;
  }
  
  public final void put(Class<? extends BaseWidgetProvider> paramClass, int paramInt, String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      throw new IllegalArgumentException("data cannot be null or empty");
    }
    String str = buildKey(paramClass, paramInt);
    Map localMap = this.mKeyValueStore.get(str);
    if (localMap == null) {
      localMap = Collections.emptyMap();
    }
    HashMap localHashMap = new HashMap(localMap);
    localHashMap.put("type", paramString);
    localHashMap.put("appWidgetId", String.valueOf(paramInt));
    localHashMap.put("widgetProvider", paramClass.getSimpleName());
    this.mKeyValueStore.put(str, localHashMap);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.WidgetTypeMap
 * JD-Core Version:    0.7.0.1
 */