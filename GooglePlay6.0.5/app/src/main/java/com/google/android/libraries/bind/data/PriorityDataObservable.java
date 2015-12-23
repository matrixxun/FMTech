package com.google.android.libraries.bind.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public final class PriorityDataObservable
{
  final List<ObserverEntry> observerEntries = new ArrayList();
  
  public final int size()
  {
    return this.observerEntries.size();
  }
  
  public final String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(size());
    localStringBuilder.append(String.format(localLocale, "  observers count: %d\n", arrayOfObject));
    Iterator localIterator = this.observerEntries.iterator();
    while (localIterator.hasNext())
    {
      ObserverEntry localObserverEntry = (ObserverEntry)localIterator.next();
      localStringBuilder.append("    ");
      localStringBuilder.append(localObserverEntry.toString());
      localStringBuilder.append("\n");
    }
    return localStringBuilder.toString();
  }
  
  private static final class ObserverEntry
    implements Comparable<ObserverEntry>
  {
    public final DataObserver observer;
    public final int priority;
    
    public ObserverEntry(DataObserver paramDataObserver, int paramInt)
    {
      this.observer = paramDataObserver;
      this.priority = 0;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.data.PriorityDataObservable
 * JD-Core Version:    0.7.0.1
 */