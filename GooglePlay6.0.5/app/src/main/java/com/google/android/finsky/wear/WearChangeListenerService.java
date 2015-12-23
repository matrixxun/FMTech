package com.google.android.finsky.wear;

import android.net.Uri;
import android.os.Binder;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.WearableListenerService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WearChangeListenerService
  extends WearableListenerService
{
  public final void onDataChanged(DataEventBuffer paramDataEventBuffer)
  {
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Integer.valueOf(paramDataEventBuffer.getCount());
    FinskyLog.d("Wear received %d events", arrayOfObject1);
    if (!FinskyApp.get().getExperiments().isEnabled(12603948L)) {
      FinskyLog.d("disabled", new Object[0]);
    }
    ArrayList localArrayList1;
    ArrayList localArrayList2;
    label57:
    label228:
    do
    {
      return;
      localArrayList1 = null;
      localArrayList2 = null;
      Iterator localIterator = paramDataEventBuffer.iterator();
      while (localIterator.hasNext())
      {
        DataEvent localDataEvent = (DataEvent)localIterator.next();
        Uri localUri = localDataEvent.getDataItem().getUri();
        if ((!"wear".equals(localUri.getScheme())) || (TextUtils.isEmpty(localUri.getHost())) || (localUri.getPathSegments().size() <= 0))
        {
          FinskyLog.w("Malformed DataEvent %s", new Object[] { localUri });
        }
        else
        {
          String str = (String)localUri.getPathSegments().get(0);
          int i;
          switch (str.hashCode())
          {
          default: 
            i = -1;
          }
          for (;;)
          {
            switch (i)
            {
            case 3: 
            case 4: 
            case 5: 
            default: 
              FinskyLog.w("Unexpected DataEvent %s", new Object[] { localUri });
              break label57;
              if (!str.equals("package_info")) {
                break label228;
              }
              i = 0;
              continue;
              if (!str.equals("device_configuration")) {
                break label228;
              }
              i = 1;
              continue;
              if (!str.equals("request_status")) {
                break label228;
              }
              i = 2;
              continue;
              if (!str.equals("wearable_info")) {
                break label228;
              }
              i = 3;
              continue;
              if (!str.equals("install_wearable")) {
                break label228;
              }
              i = 4;
              continue;
              if (!str.equals("uninstall_wearable")) {
                break label228;
              }
              i = 5;
            }
          }
          switch (localDataEvent.getType())
          {
          default: 
            Object[] arrayOfObject2 = new Object[2];
            arrayOfObject2[0] = Integer.valueOf(localDataEvent.getType());
            arrayOfObject2[1] = localUri;
            FinskyLog.w("Unexpected DataEvent type %d %s", arrayOfObject2);
            break;
          case 1: 
            if (localArrayList1 == null) {
              localArrayList1 = new ArrayList();
            }
            localArrayList1.add(localUri.toString());
            break;
          case 2: 
            if (localArrayList2 == null) {
              localArrayList2 = new ArrayList();
            }
            localArrayList2.add(localUri.toString());
          }
        }
      }
    } while ((localArrayList1 == null) && (localArrayList2 == null));
    long l = Binder.clearCallingIdentity();
    try
    {
      WearSupportService.startNodeUpdates(this, localArrayList1, localArrayList2);
      return;
    }
    finally
    {
      Binder.restoreCallingIdentity(l);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.wear.WearChangeListenerService
 * JD-Core Version:    0.7.0.1
 */