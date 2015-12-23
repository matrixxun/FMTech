package com.google.android.libraries.bind.data;

import com.google.android.libraries.bind.async.AsyncUtil;
import com.google.android.libraries.bind.util.Util;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class RefreshTask
  implements Runnable
{
  final AtomicBoolean cancelled = new AtomicBoolean();
  protected final DataList dataList;
  boolean executed;
  final Executor executor;
  protected final Snapshot previousSnapshot;
  
  public RefreshTask(DataList paramDataList, Executor paramExecutor)
  {
    this.dataList = paramDataList;
    this.executor = paramExecutor;
    AsyncUtil.checkMainThread();
    this.previousSnapshot = paramDataList.snapshot;
  }
  
  protected abstract List<Data> getFreshData()
    throws DataException;
  
  protected void postRefresh(Snapshot paramSnapshot, DataChange paramDataChange)
  {
    this.dataList.postRefresh(this, paramSnapshot, paramDataChange, null);
  }
  
  public void run()
  {
    if (this.cancelled.get()) {
      return;
    }
    for (;;)
    {
      try
      {
        localList1 = getFreshData();
        if ((localList1 == null) || (this.cancelled.get())) {
          break;
        }
        arrayOfDataChange = new DataChange[1];
        if (this.previousSnapshot.list != Snapshot.INVALID_LIST) {
          continue;
        }
        i = 1;
        if (i == 0) {
          continue;
        }
        arrayOfDataChange[0] = DataChange.AFFECTS_PRIMARY_KEY;
        j = 1;
      }
      catch (DataException localDataException)
      {
        List localList1;
        DataChange[] arrayOfDataChange;
        int i;
        List localList2;
        int k;
        int m;
        int[] arrayOfInt;
        int n;
        Data localData1;
        Data localData2;
        boolean bool1;
        int i1;
        int i2;
        int i3;
        boolean bool2;
        Snapshot localSnapshot = new Snapshot(this.dataList.primaryKey, localDataException);
        DataChange localDataChange = new DataChange(localDataException);
        continue;
        m++;
        continue;
        if (bool1) {
          continue;
        }
        int j = 1;
        continue;
      }
      if (j == 0) {
        break;
      }
      localSnapshot = new Snapshot(this.dataList.primaryKey, localList1);
      localDataChange = arrayOfDataChange[0];
      if (this.cancelled.get()) {
        break;
      }
      postRefresh(localSnapshot, localDataChange);
      return;
      i = 0;
      continue;
      localList2 = this.previousSnapshot.list;
      if (localList1.size() != localList2.size())
      {
        arrayOfDataChange[0] = DataChange.AFFECTS_PRIMARY_KEY;
        j = 1;
      }
      else
      {
        k = localList2.size();
        m = 0;
        if (m < k)
        {
          if (Util.objectsEqual(((Data)localList2.get(m)).get(this.previousSnapshot.primaryKey), ((Data)localList1.get(m)).get(this.dataList.primaryKey))) {
            continue;
          }
          arrayOfDataChange[0] = DataChange.AFFECTS_PRIMARY_KEY;
          j = 1;
        }
        else
        {
          arrayOfDataChange[0] = DataChange.DOESNT_AFFECT_PRIMARY_KEY;
          arrayOfInt = this.dataList.equalityFields();
          n = 0;
          if (n < k)
          {
            localData1 = (Data)localList2.get(n);
            localData2 = (Data)localList1.get(n);
            if (arrayOfInt == null)
            {
              bool1 = localData1.equals(localData2);
              continue;
            }
            i1 = arrayOfInt.length;
            i2 = 0;
            if (i2 < i1)
            {
              i3 = arrayOfInt[i2];
              bool2 = Util.objectsEqual(localData1.get(i3), localData2.get(i3));
              if (!bool2)
              {
                bool1 = false;
                continue;
              }
              i2++;
            }
            else
            {
              bool1 = true;
              continue;
              n++;
            }
          }
          else
          {
            j = 0;
          }
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.data.RefreshTask
 * JD-Core Version:    0.7.0.1
 */