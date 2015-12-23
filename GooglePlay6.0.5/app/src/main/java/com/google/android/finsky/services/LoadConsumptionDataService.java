package com.google.android.finsky.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ParcelUtils;
import com.google.android.finsky.widget.consumption.ConsumptionAppDocList;
import java.io.File;

public class LoadConsumptionDataService
  extends IntentService
{
  public LoadConsumptionDataService()
  {
    super(LoadConsumptionDataService.class.getSimpleName());
  }
  
  public static boolean isBackendSupported(int paramInt)
  {
    switch (paramInt)
    {
    case 5: 
    default: 
      return false;
    }
    return true;
  }
  
  public static boolean isSupportedDataType(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return false;
    }
    return true;
  }
  
  public static void scheduleAlarmForUpdate(Context paramContext, int... paramVarArgs)
  {
    Intent localIntent = new Intent(paramContext, LoadConsumptionDataService.class);
    localIntent.setAction(String.valueOf(paramVarArgs.hashCode()));
    localIntent.putExtra("backendIds", paramVarArgs);
    paramContext.startService(localIntent);
  }
  
  protected void onHandleIntent(Intent paramIntent)
  {
    ConsumptionAppDataCache localConsumptionAppDataCache = ConsumptionAppDataCache.get();
    int[] arrayOfInt1 = paramIntent.getIntArrayExtra("backendIds");
    int[] arrayOfInt2 = new int[arrayOfInt1.length];
    int i = arrayOfInt1.length;
    int j = 0;
    int k = 0;
    int n;
    Object localObject;
    File localFile;
    label89:
    int i1;
    if (j < i)
    {
      n = arrayOfInt1[j];
      localObject = new ConsumptionAppDocList(n);
      localFile = ConsumptionAppDataCache.getCacheFile(this, n);
      if (!localFile.exists())
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = localFile.getAbsolutePath();
        FinskyLog.d("%s doesn't exist", arrayOfObject2);
        if (((ConsumptionAppDocList)localObject).isEmpty()) {
          break label204;
        }
        i1 = k + 1;
        arrayOfInt2[k] = n;
        localConsumptionAppDataCache.setConsumptionAppData(this, (ConsumptionAppDocList)localObject, false);
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(n);
        FinskyLog.d("Was able to read from cache for %d", arrayOfObject1);
      }
    }
    for (;;)
    {
      FetchConsumptionDataService.fetch(this, n);
      j++;
      k = i1;
      break;
      ConsumptionAppDocList localConsumptionAppDocList = (ConsumptionAppDocList)ParcelUtils.readFromDisk(localFile);
      if (localConsumptionAppDocList == null) {
        break label89;
      }
      localObject = localConsumptionAppDocList;
      break label89;
      for (int m = 0; m < k; m++) {
        ConsumptionAppDataCache.updateWidgets(this, arrayOfInt2[m]);
      }
      return;
      label204:
      i1 = k;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.services.LoadConsumptionDataService
 * JD-Core Version:    0.7.0.1
 */