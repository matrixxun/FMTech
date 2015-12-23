package com.google.android.libraries.bind.data;

import com.google.android.libraries.bind.async.Queue;
import com.google.android.libraries.bind.async.Queues;
import com.google.android.libraries.bind.util.Util;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public class FilteredDataList
  extends DataList
{
  private int currentSourceDataVersion;
  private final int[] equalityFields;
  protected final Filter filter;
  private final DataObserver refreshObserver;
  protected final DataList sourceList;
  
  public FilteredDataList(DataList paramDataList, Filter paramFilter, int[] paramArrayOfInt, int paramInt)
  {
    super(paramInt);
    if (paramDataList != null) {}
    for (boolean bool = true;; bool = false)
    {
      Util.checkPrecondition(bool);
      this.filter = paramFilter;
      this.sourceList = paramDataList;
      this.equalityFields = paramArrayOfInt;
      this.refreshObserver = new DataObserver()
      {
        public final void onDataChanged(DataChange paramAnonymousDataChange)
        {
          if ((paramAnonymousDataChange.isInvalidation) && (this.val$clearOnInvalidation) && (!FilteredDataList.this.stopAutoRefreshAfterRefresh)) {
            FilteredDataList.this.update$600ed7f(DataChange.INVALIDATION);
          }
          FilteredDataList.this.invalidateData();
        }
      };
      return;
    }
  }
  
  protected final int[] equalityFields()
  {
    return this.equalityFields;
  }
  
  public final boolean isDirty()
  {
    return (super.isDirty()) || (this.sourceList.dataVersion != this.currentSourceDataVersion);
  }
  
  protected RefreshTask makeRefreshTask()
  {
    if (this.sourceList.snapshot.hasException()) {}
    for (Queue localQueue = Queues.BIND_IMMEDIATE;; localQueue = Queues.BIND_CPU) {
      return new FilterRefreshTask(this, localQueue, this.filter, this.sourceList);
    }
  }
  
  protected final void onRegisterForInvalidation()
  {
    this.sourceList.registerDataObserver(this.refreshObserver);
  }
  
  protected final void onUnregisterForInvalidation()
  {
    this.sourceList.unregisterDataObserver(this.refreshObserver);
  }
  
  public final void refresh()
  {
    if ((this.sourceList.hasRefreshedOnce()) || (this.sourceList.snapshot.hasException()))
    {
      super.refresh();
      return;
    }
    update$600ed7f(DataChange.INVALIDATION);
  }
  
  public String toString()
  {
    return super.toString() + "\n\nParent:" + this.sourceList.toString();
  }
  
  protected static class FilterRefreshTask
    extends RefreshTask
  {
    protected final Filter filter;
    protected final int newSourceDataVersion;
    protected final DataException sourceException;
    protected final Snapshot sourceSnapshot;
    
    public FilterRefreshTask(DataList paramDataList1, Executor paramExecutor, Filter paramFilter, DataList paramDataList2) {}
    
    protected final List<Data> getFreshData()
      throws DataException
    {
      if (this.sourceException != null) {
        throw this.sourceException;
      }
      List localList = getSourceData();
      if (this.cancelled.get()) {
        localList = null;
      }
      while (this.filter == null) {
        return localList;
      }
      Object localObject = new ArrayList(localList.size());
      if (this.filter != null)
      {
        Iterator localIterator = localList.iterator();
        while (localIterator.hasNext()) {
          ((List)localObject).add((Data)localIterator.next());
        }
        if (this.cancelled.get()) {
          return null;
        }
        localObject = this.filter.transform$7ddb133f((List)localObject);
      }
      return localObject;
    }
    
    protected List<Data> getSourceData()
    {
      return this.sourceSnapshot.list;
    }
    
    protected final void postRefresh(Snapshot paramSnapshot, DataChange paramDataChange)
    {
      this.dataList.postRefresh(this, paramSnapshot, paramDataChange, Integer.valueOf(this.newSourceDataVersion));
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.data.FilteredDataList
 * JD-Core Version:    0.7.0.1
 */