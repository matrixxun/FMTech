package com.google.android.libraries.bind.data;

import android.os.Handler;
import com.google.android.libraries.bind.async.AsyncUtil;
import com.google.android.libraries.bind.logging.Logd;
import com.google.android.libraries.bind.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public class DataList
{
  protected RefreshTask currentRefreshTask;
  private boolean dataDirty;
  private CopyOnWriteArraySet<Object> dataListListeners = new CopyOnWriteArraySet();
  int dataVersion;
  private final Snapshot invalidSnapshot;
  private boolean isAutoRefreshing;
  protected Logd logd;
  private final PriorityDataObservable observable = new PriorityDataObservable();
  protected final int primaryKey;
  private boolean registeredForInvalidation;
  Snapshot snapshot;
  protected boolean stopAutoRefreshAfterRefresh;
  
  public DataList(int paramInt)
  {
    this(paramInt, (byte)0);
  }
  
  private DataList(int paramInt, byte paramByte)
  {
    this.primaryKey = paramInt;
    this.invalidSnapshot = new Snapshot(paramInt);
    this.snapshot = this.invalidSnapshot;
  }
  
  private boolean hasDataSetObservers()
  {
    return this.observable.size() > 0;
  }
  
  private Logd logd()
  {
    if (this.logd == null) {
      this.logd = Logd.get(getClass());
    }
    return this.logd;
  }
  
  private void registerForInvalidation()
  {
    logd().d("registerForInvalidation", new Object[0]);
    boolean bool1 = this.registeredForInvalidation;
    boolean bool2 = false;
    if (!bool1) {
      bool2 = true;
    }
    Util.checkPrecondition(bool2);
    this.registeredForInvalidation = true;
    onRegisterForInvalidation();
    if ((isDirty()) && ((this.isAutoRefreshing) || (!hasRefreshedOnce()))) {
      refresh();
    }
    Iterator localIterator = this.dataListListeners.iterator();
    while (localIterator.hasNext()) {
      localIterator.next();
    }
  }
  
  private void stopRefreshTask()
  {
    logd().d("stopRefreshTask", new Object[0]);
    if (this.currentRefreshTask != null)
    {
      this.currentRefreshTask.cancelled.set(true);
      this.currentRefreshTask = null;
    }
  }
  
  private void unregisterForInvalidation()
  {
    logd().d("unregisterForInvalidation", new Object[0]);
    Util.checkPrecondition(this.registeredForInvalidation);
    this.registeredForInvalidation = false;
    onUnregisterForInvalidation();
    Iterator localIterator = this.dataListListeners.iterator();
    while (localIterator.hasNext()) {
      localIterator.next();
    }
  }
  
  protected int[] equalityFields()
  {
    return null;
  }
  
  protected void finalize()
    throws Throwable
  {
    if ((this.registeredForInvalidation) || (this.observable.size() > 0))
    {
      logd().e("Leaked datalist", new Object[0]);
      Logd localLogd = logd();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.observable;
      localLogd.e("  Observables: %s", arrayOfObject);
    }
    super.finalize();
  }
  
  public final int getCount()
  {
    AsyncUtil.checkMainThread();
    return this.snapshot.getCount();
  }
  
  public final Data getData(int paramInt)
  {
    AsyncUtil.checkMainThread();
    Snapshot localSnapshot = this.snapshot;
    if (localSnapshot.isInvalidPosition(paramInt)) {
      return null;
    }
    return (Data)localSnapshot.list.get(paramInt);
  }
  
  public final Object getItemId(int paramInt)
  {
    AsyncUtil.checkMainThread();
    Snapshot localSnapshot = this.snapshot;
    if (localSnapshot.isInvalidPosition(paramInt)) {
      return null;
    }
    return ((Data)localSnapshot.list.get(paramInt)).get(localSnapshot.primaryKey);
  }
  
  public final boolean hasRefreshedOnce()
  {
    AsyncUtil.checkMainThread();
    return this.snapshot != this.invalidSnapshot;
  }
  
  public final void invalidateData()
  {
    AsyncUtil.checkMainThread();
    Logd localLogd = logd();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Boolean.valueOf(false);
    localLogd.i("invalidateData(clearList=%b)", arrayOfObject);
    this.dataDirty = true;
    if ((this.isAutoRefreshing) && (hasDataSetObservers())) {
      refresh();
    }
  }
  
  public boolean isDirty()
  {
    return this.dataDirty;
  }
  
  public final boolean isEmpty()
  {
    AsyncUtil.checkMainThread();
    return this.snapshot.list.isEmpty();
  }
  
  protected RefreshTask makeRefreshTask()
  {
    return null;
  }
  
  protected void onRegisterForInvalidation() {}
  
  protected void onUnregisterForInvalidation() {}
  
  public final void postRefresh(final RefreshTask paramRefreshTask, final Snapshot paramSnapshot, final DataChange paramDataChange, final Integer paramInteger)
  {
    Runnable local1 = new Runnable()
    {
      public final void run()
      {
        if (paramRefreshTask == DataList.this.currentRefreshTask)
        {
          DataList.this.update(paramSnapshot, paramDataChange, paramInteger);
          DataList.this.currentRefreshTask = null;
        }
      }
    };
    if (AsyncUtil.isMainThread())
    {
      local1.run();
      return;
    }
    AsyncUtil.mainThreadHandler().post(local1);
  }
  
  public void refresh()
  {
    boolean bool1 = true;
    AsyncUtil.checkMainThread();
    logd().d("refresh", new Object[0]);
    logd().d("startRefreshTask", new Object[0]);
    stopRefreshTask();
    this.currentRefreshTask = makeRefreshTask();
    if (this.currentRefreshTask == null)
    {
      logd().d("no refresh task", new Object[0]);
      if (!hasRefreshedOnce()) {}
      for (;;)
      {
        this.dataDirty = bool1;
        return;
        bool1 = false;
      }
    }
    RefreshTask localRefreshTask = this.currentRefreshTask;
    boolean bool2 = localRefreshTask.executed;
    boolean bool3 = false;
    if (!bool2) {
      bool3 = bool1;
    }
    Util.checkPrecondition(bool3);
    localRefreshTask.executor.execute(localRefreshTask);
    localRefreshTask.executed = bool1;
  }
  
  public final void registerDataObserver(DataObserver paramDataObserver)
  {
    AsyncUtil.checkMainThread();
    PriorityDataObservable localPriorityDataObservable = this.observable;
    if (paramDataObserver == null) {
      throw new IllegalArgumentException("Observer is null");
    }
    boolean bool = localPriorityDataObservable.observerEntries.isEmpty();
    PriorityDataObservable.ObserverEntry localObserverEntry = new PriorityDataObservable.ObserverEntry(paramDataObserver, 0);
    localPriorityDataObservable.observerEntries.add(localObserverEntry);
    Collections.sort(localPriorityDataObservable.observerEntries);
    if (bool) {
      registerForInvalidation();
    }
    Logd localLogd = logd();
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(this.observable.size());
    arrayOfObject[1] = Boolean.valueOf(this.registeredForInvalidation);
    localLogd.d("registerDataSetObserver - count: %d, registeredForInvalidation: %b", arrayOfObject);
  }
  
  public final DataList startAutoRefresh()
  {
    logd().d("startAutoRefresh", new Object[0]);
    this.stopAutoRefreshAfterRefresh = false;
    if (!this.isAutoRefreshing)
    {
      this.isAutoRefreshing = true;
      if ((isDirty()) && (hasDataSetObservers())) {
        refresh();
      }
    }
    return this;
  }
  
  public String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = getClass().getSimpleName();
    arrayOfObject[1] = Data.keyName(this.primaryKey);
    arrayOfObject[2] = Integer.valueOf(getCount());
    if (this.snapshot.hasException()) {}
    for (String str = this.snapshot.exception.getMessage();; str = "no")
    {
      arrayOfObject[3] = str;
      return String.format(localLocale, "%s - primaryKey: %s, size: %d, exception: %s", arrayOfObject);
    }
  }
  
  public final void unregisterDataObserver(DataObserver paramDataObserver)
  {
    AsyncUtil.checkMainThread();
    PriorityDataObservable localPriorityDataObservable = this.observable;
    if (paramDataObserver == null) {
      throw new IllegalArgumentException("Observer is null");
    }
    boolean bool;
    if (localPriorityDataObservable.observerEntries.isEmpty())
    {
      bool = false;
      if (bool)
      {
        unregisterForInvalidation();
        stopRefreshTask();
      }
      Logd localLogd = logd();
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(this.observable.size());
      arrayOfObject[1] = Boolean.valueOf(this.registeredForInvalidation);
      localLogd.d("unregisterDataSetObserver - count: %d, registeredForInvalidation: %b", arrayOfObject);
      return;
    }
    for (int i = 0;; i++) {
      if (i < localPriorityDataObservable.observerEntries.size())
      {
        if (((PriorityDataObservable.ObserverEntry)localPriorityDataObservable.observerEntries.get(i)).observer == paramDataObserver) {
          localPriorityDataObservable.observerEntries.remove(i);
        }
      }
      else
      {
        bool = localPriorityDataObservable.observerEntries.isEmpty();
        break;
      }
    }
  }
  
  protected final void update(Snapshot paramSnapshot, DataChange paramDataChange, Integer paramInteger)
  {
    
    if (paramSnapshot == null) {
      paramSnapshot = this.invalidSnapshot;
    }
    boolean bool;
    int i;
    label39:
    int j;
    if (!paramSnapshot.hasException())
    {
      bool = true;
      this.dataDirty = bool;
      if (paramSnapshot != this.invalidSnapshot) {
        break label87;
      }
      i = 1;
      if ((!this.snapshot.hasException()) && (!paramSnapshot.hasException())) {
        break label93;
      }
      j = 1;
      label59:
      if ((i == 0) || (this.snapshot != this.invalidSnapshot) || (j != 0)) {
        break label99;
      }
    }
    label87:
    label93:
    label99:
    do
    {
      return;
      bool = false;
      break;
      i = 0;
      break label39;
      j = 0;
      break label59;
      this.snapshot = paramSnapshot;
      if (paramInteger == null) {}
      for (int k = 1 + this.dataVersion;; k = paramInteger.intValue())
      {
        this.dataVersion = k;
        logd().d("notifyDataChanged", new Object[0]);
        Iterator localIterator = new ArrayList(this.observable.observerEntries).iterator();
        while (localIterator.hasNext()) {
          ((PriorityDataObservable.ObserverEntry)localIterator.next()).observer.onDataChanged(paramDataChange);
        }
      }
    } while ((i != 0) || (!this.stopAutoRefreshAfterRefresh));
    AsyncUtil.checkMainThread();
    logd().d("stopAutoRefresh", new Object[0]);
    this.isAutoRefreshing = false;
  }
  
  public final void update$600ed7f(DataChange paramDataChange)
  {
    update(null, paramDataChange, null);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.data.DataList
 * JD-Core Version:    0.7.0.1
 */