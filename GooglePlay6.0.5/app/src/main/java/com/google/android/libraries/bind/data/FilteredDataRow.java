package com.google.android.libraries.bind.data;

import com.google.android.libraries.bind.async.AsyncUtil;
import com.google.android.libraries.bind.async.Queues;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public final class FilteredDataRow
  extends FilteredDataList
{
  boolean emptyWhenNone;
  private final Object rowId;
  
  public FilteredDataRow(DataList paramDataList, Object paramObject, Filter paramFilter, int[] paramArrayOfInt)
  {
    super(paramDataList, paramFilter, paramArrayOfInt, paramDataList.primaryKey);
    this.rowId = paramObject;
  }
  
  protected final RefreshTask makeRefreshTask()
  {
    DataList localDataList = this.sourceList;
    Object localObject = this.rowId;
    AsyncUtil.checkMainThread();
    Integer localInteger = (Integer)localDataList.snapshot.primaryKeyIndex.get(localObject);
    if (localInteger == null) {}
    for (int i = -1; (i == -1) && (!this.emptyWhenNone); i = localInteger.intValue()) {
      return null;
    }
    final Data localData = this.sourceList.getData(i);
    new FilteredDataList.FilterRefreshTask(this, Queues.BIND_IMMEDIATE, this.filter, this.sourceList)
    {
      protected final List<Data> getSourceData()
      {
        if (localData == null)
        {
          if (FilteredDataRow.this.emptyWhenNone) {
            return new ArrayList(0);
          }
          return null;
        }
        ArrayList localArrayList = new ArrayList(1);
        localArrayList.add(localData);
        return localArrayList;
      }
    };
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.data.FilteredDataRow
 * JD-Core Version:    0.7.0.1
 */