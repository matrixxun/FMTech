package com.google.android.libraries.bind.data;

import android.content.res.Resources;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView.LayoutParams;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;
import com.google.android.libraries.bind.R.dimen;
import com.google.android.libraries.bind.async.AsyncUtil;
import com.google.android.libraries.bind.view.ViewHeap;
import java.util.Collections;
import java.util.List;

public abstract class DataAdapter
  implements ListAdapter, SpinnerAdapter
{
  protected DataList dataList;
  private boolean dataListRegistered;
  protected ViewProvider emptyViewProvider;
  protected ViewProvider errorViewProvider;
  protected ViewProvider loadingViewProvider;
  private final PriorityDataSetObservable observable;
  private final DataObserver observer;
  private boolean supportsEmptyView;
  private boolean supportsErrorView;
  private boolean supportsLoadingView;
  protected final ViewHeap viewHeap;
  
  private static ViewGroup.LayoutParams getFullScreenLayoutParams(ViewGroup paramViewGroup)
  {
    return new AbsListView.LayoutParams(paramViewGroup.getWidth() - paramViewGroup.getPaddingLeft() - paramViewGroup.getPaddingRight(), paramViewGroup.getHeight() - paramViewGroup.getPaddingTop() - paramViewGroup.getPaddingBottom() - 2 * paramViewGroup.getResources().getDimensionPixelSize(R.dimen.bind__card_list_view_padding));
  }
  
  private boolean showEmptyView()
  {
    return (this.supportsEmptyView) && ((this.dataList == null) || (this.dataList.isEmpty())) && (!showLoadingView()) && (!showErrorView());
  }
  
  private boolean showErrorView()
  {
    return (this.supportsErrorView) && (this.dataList != null) && (this.dataList.snapshot.hasException());
  }
  
  private boolean showLoadingView()
  {
    if (this.supportsLoadingView)
    {
      if (((this.dataList == null) || (!hasRefreshedOnce())) && (!showErrorView())) {}
      for (int i = 1; i != 0; i = 0) {
        return true;
      }
    }
    return false;
  }
  
  private boolean showOutOfBandView()
  {
    return (showLoadingView()) || (showEmptyView()) || (showErrorView());
  }
  
  private void updateDataListRegistration()
  {
    if ((this.dataList == null) || (this.observable.size() == 0)) {
      if (this.dataListRegistered)
      {
        this.dataList.unregisterDataObserver(this.observer);
        this.dataListRegistered = false;
      }
    }
    while (this.dataListRegistered) {
      return;
    }
    this.dataList.registerDataObserver(this.observer);
    this.dataListRegistered = true;
  }
  
  public boolean areAllItemsEnabled()
  {
    return true;
  }
  
  public int getCount()
  {
    if (showOutOfBandView()) {
      return 1;
    }
    if (this.dataList == null) {
      return 0;
    }
    return this.dataList.getCount();
  }
  
  public View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    return getView(paramInt, paramView, paramViewGroup);
  }
  
  public final Data getItem(int paramInt)
  {
    if (showOutOfBandView()) {
      return null;
    }
    return this.dataList.getData(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    long l1;
    if (showLoadingView()) {
      l1 = 9223372036854775807L;
    }
    for (;;)
    {
      return l1;
      if (showEmptyView()) {
        return 9223372036854775806L;
      }
      if (showErrorView()) {
        return 9223372036854775805L;
      }
      Object localObject = this.dataList.getItemId(paramInt);
      if ((localObject instanceof Long)) {
        return ((Long)localObject).longValue();
      }
      if ((localObject instanceof Integer)) {
        return ((Integer)localObject).intValue();
      }
      if (localObject == null) {
        break;
      }
      String str = localObject.toString();
      int i = str.length();
      l1 = 0L;
      int j = 0;
      while (j < i)
      {
        long l2 = l1 * 63L + str.charAt(j);
        j++;
        l1 = l2;
      }
    }
    return 0L;
  }
  
  public int getItemViewType(int paramInt)
  {
    return 0;
  }
  
  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView1;
    if (showLoadingView())
    {
      localView1 = this.loadingViewProvider.getView$70191498(paramViewGroup);
      localView1.setLayoutParams(getFullScreenLayoutParams(paramViewGroup));
    }
    ViewGroup.LayoutParams localLayoutParams;
    do
    {
      return localView1;
      if (showEmptyView())
      {
        View localView2 = this.emptyViewProvider.getView$70191498(paramViewGroup);
        localView2.setLayoutParams(getFullScreenLayoutParams(paramViewGroup));
        return localView2;
      }
      if (showErrorView()) {
        return this.errorViewProvider.getView$70191498(paramViewGroup);
      }
      DataList localDataList = this.dataList;
      AsyncUtil.checkMainThread();
      if (localDataList.snapshot.isInvalidPosition(paramInt)) {
        return null;
      }
      localView1 = getView$7f88da85(paramInt, paramView, this.dataList.getData(paramInt));
      localLayoutParams = localView1.getLayoutParams();
    } while ((localLayoutParams == null) || ((localLayoutParams instanceof AbsListView.LayoutParams)));
    localView1.setLayoutParams(new AbsListView.LayoutParams(localLayoutParams));
    return localView1;
  }
  
  public abstract View getView$7f88da85(int paramInt, View paramView, Data paramData);
  
  public int getViewTypeCount()
  {
    return 1;
  }
  
  public final boolean hasRefreshedOnce()
  {
    if (this.dataList == null) {
      return false;
    }
    return this.dataList.hasRefreshedOnce();
  }
  
  public boolean hasStableIds()
  {
    return true;
  }
  
  public boolean isEmpty()
  {
    return (!showOutOfBandView()) && ((this.dataList == null) || (this.dataList.isEmpty()));
  }
  
  public boolean isEnabled(int paramInt)
  {
    return true;
  }
  
  public void registerDataSetObserver(DataSetObserver paramDataSetObserver)
  {
    registerDataSetObserver(paramDataSetObserver, 0);
  }
  
  public final void registerDataSetObserver(DataSetObserver paramDataSetObserver, int paramInt)
  {
    PriorityDataSetObservable localPriorityDataSetObservable = this.observable;
    if (paramDataSetObserver == null) {
      throw new IllegalArgumentException("Observer is null");
    }
    localPriorityDataSetObservable.observerEntries.isEmpty();
    PriorityDataSetObservable.ObserverEntry localObserverEntry = new PriorityDataSetObservable.ObserverEntry(paramDataSetObserver, paramInt);
    localPriorityDataSetObservable.observerEntries.add(localObserverEntry);
    Collections.sort(localPriorityDataSetObservable.observerEntries);
    updateDataListRegistration();
  }
  
  public void unregisterDataSetObserver(DataSetObserver paramDataSetObserver)
  {
    PriorityDataSetObservable localPriorityDataSetObservable = this.observable;
    if (paramDataSetObserver == null) {
      throw new IllegalArgumentException("Observer is null");
    }
    if (!localPriorityDataSetObservable.observerEntries.isEmpty()) {}
    for (int i = 0;; i++) {
      if (i < localPriorityDataSetObservable.observerEntries.size())
      {
        if (((PriorityDataSetObservable.ObserverEntry)localPriorityDataSetObservable.observerEntries.get(i)).observer == paramDataSetObserver) {
          localPriorityDataSetObservable.observerEntries.remove(i);
        }
      }
      else
      {
        localPriorityDataSetObservable.observerEntries.isEmpty();
        updateDataListRegistration();
        return;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.data.DataAdapter
 * JD-Core Version:    0.7.0.1
 */