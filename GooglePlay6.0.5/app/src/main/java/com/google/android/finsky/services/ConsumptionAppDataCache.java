package com.google.android.finsky.services;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.SparseArray;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Utils;
import com.google.android.finsky.widget.consumption.ConsumptionAppDocList;
import com.google.android.finsky.widget.consumption.NowPlayingWidgetProvider;
import com.google.android.play.utils.config.GservicesValue;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ConsumptionAppDataCache
{
  private static final String CACHE_FILE_PREFIX = ConsumptionAppDataCache.class.getSimpleName();
  private static ConsumptionAppDataCache mInstance;
  public SparseArray<List<ConsumptionAppDoc>> mConsumptionAppData = new SparseArray();
  public SparseArray<Integer> mDataLoadState = new SparseArray();
  private final Handler mHandler = new Handler(Looper.getMainLooper());
  
  public static ConsumptionAppDataCache get()
  {
    if (mInstance == null) {
      mInstance = new ConsumptionAppDataCache();
    }
    return mInstance;
  }
  
  public static File getCacheFile(Context paramContext, int paramInt)
  {
    File localFile = new File(paramContext.getCacheDir(), "consumption");
    localFile.mkdirs();
    return new File(localFile, CACHE_FILE_PREFIX + "_" + paramInt + ".cache");
  }
  
  public static void updateWidgets(Context paramContext, int paramInt)
  {
    Intent localIntent = new Intent("com.android.vending.action.APPWIDGET_UPDATE_CONSUMPTION_DATA");
    localIntent.setClass(paramContext, NowPlayingWidgetProvider.class);
    localIntent.putExtra("backendId", paramInt);
    paramContext.sendBroadcast(localIntent);
  }
  
  public final ConsumptionAppDocList getConsumptionAppData(int paramInt)
  {
    Utils.ensureOnMainThread();
    ConsumptionAppDocList localConsumptionAppDocList = new ConsumptionAppDocList(paramInt);
    if (hasConsumptionAppData(paramInt)) {
      localConsumptionAppDocList.addAll((Collection)this.mConsumptionAppData.get(paramInt));
    }
    return localConsumptionAppDocList;
  }
  
  public final int getDataStateForBackend(int paramInt)
  {
    
    if (this.mDataLoadState.get(paramInt) != null) {
      return ((Integer)this.mDataLoadState.get(paramInt)).intValue();
    }
    return 0;
  }
  
  public final boolean hasConsumptionAppData(int paramInt)
  {
    Utils.ensureOnMainThread();
    return getDataStateForBackend(paramInt) == 2;
  }
  
  public final void setConsumptionAppData(final Context paramContext, final int paramInt, final List<Bundle> paramList)
  {
    String str1 = (String)G.consumptionAppDataFilter.get();
    if ((!((Boolean)G.debugOptionsEnabled.get()).booleanValue()) || (TextUtils.isEmpty(str1))) {}
    label62:
    String[] arrayOfString3;
    int k;
    while (Looper.myLooper() != Looper.getMainLooper())
    {
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          ConsumptionAppDataCache.this.setConsumptionAppData(paramContext, paramInt, paramList);
        }
      });
      return;
      String[] arrayOfString1 = str1.split(";");
      String[] arrayOfString2 = null;
      int i = arrayOfString1.length;
      int j = 0;
      if (j < i)
      {
        String str2 = arrayOfString1[j];
        String[] arrayOfString4 = str2.trim().split(":");
        if (arrayOfString4.length != 2) {
          FinskyLog.d("Bad corpus filter expression %s", new Object[] { str2 });
        }
        int i2;
        do
        {
          j++;
          break;
          i2 = Integer.parseInt(arrayOfString4[0]);
          if ((i2 == 0) || (i2 == paramInt)) {
            arrayOfString2 = arrayOfString4[1].trim().split(",");
          }
        } while (i2 != paramInt);
      }
      arrayOfString3 = arrayOfString2;
      if (arrayOfString3 == null)
      {
        paramList.clear();
      }
      else
      {
        k = arrayOfString3.length;
        if (!arrayOfString3[(k - 1)].equals("...")) {}
        for (int m = 1; m != 0; m = 0) {
          while (paramList.size() > k) {
            paramList.remove(-1 + paramList.size());
          }
        }
      }
    }
    for (int n = k - 1;; n = k)
    {
      long l1 = System.currentTimeMillis();
      for (int i1 = 0; i1 < paramList.size(); i1++)
      {
        long l2 = ((float)l1 - 86400000.0F * Float.parseFloat(arrayOfString3[java.lang.Math.min(n - 1, i1)]));
        ((Bundle)paramList.get(i1)).putLong("Play.LastUpdateTimeMillis", l2);
      }
      if (!FinskyLog.DEBUG) {
        break;
      }
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(paramInt);
      FinskyLog.v("Filtered data for corpus %d:", arrayOfObject1);
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        Bundle localBundle = (Bundle)localIterator.next();
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = new ConsumptionAppDoc(localBundle).toString();
        FinskyLog.v("%s", arrayOfObject2);
      }
      break;
      if (paramList == null) {
        break label62;
      }
      setConsumptionAppData(paramContext, ConsumptionAppDocList.createFromBundles(paramInt, paramList), true);
      return;
    }
  }
  
  public final void setConsumptionAppData(final Context paramContext, final ConsumptionAppDocList paramConsumptionAppDocList, final boolean paramBoolean)
  {
    int i = paramConsumptionAppDocList.mBackend;
    if (Looper.myLooper() != Looper.getMainLooper()) {
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          ConsumptionAppDataCache.this.setConsumptionAppData(paramContext, paramConsumptionAppDocList, paramBoolean);
        }
      });
    }
    do
    {
      return;
      int k;
      if (hasConsumptionAppData(i)) {
        if (!getConsumptionAppData(i).equals(paramConsumptionAppDocList)) {
          k = 1;
        }
      }
      for (;;)
      {
        this.mConsumptionAppData.put(i, paramConsumptionAppDocList);
        this.mDataLoadState.put(i, Integer.valueOf(2));
        if (k != 0) {
          break;
        }
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(i);
        FinskyLog.d("data didn't change for backend=[%s], ignoring!", arrayOfObject);
        return;
        k = 0;
        continue;
        int j = paramConsumptionAppDocList.size();
        k = 0;
        if (j > 0) {
          k = 1;
        }
      }
    } while (!paramBoolean);
    updateWidgets(paramContext, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.services.ConsumptionAppDataCache
 * JD-Core Version:    0.7.0.1
 */