package com.google.android.libraries.bind.widget;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class MulticastOnScrollListener
  implements AbsListView.OnScrollListener
{
  public final Set<AbsListView.OnScrollListener> listeners = new HashSet();
  
  public final void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext()) {
      ((AbsListView.OnScrollListener)localIterator.next()).onScroll(paramAbsListView, paramInt1, paramInt2, paramInt3);
    }
  }
  
  public final void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext()) {
      ((AbsListView.OnScrollListener)localIterator.next()).onScrollStateChanged(paramAbsListView, paramInt);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.widget.MulticastOnScrollListener
 * JD-Core Version:    0.7.0.1
 */