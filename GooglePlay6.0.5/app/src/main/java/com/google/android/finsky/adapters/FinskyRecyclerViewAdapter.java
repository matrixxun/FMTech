package com.google.android.finsky.adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObservable;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.MultiDfeList;
import com.google.android.finsky.api.model.PaginatedList;
import com.google.android.finsky.layout.play.PlayRecyclerView;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.ErrorStrings;

public abstract class FinskyRecyclerViewAdapter<T extends ContainerList<?>>
  extends PaginatedRecyclerViewAdapter
{
  protected MultiDfeList<T> mMultiDfeList;
  
  public FinskyRecyclerViewAdapter(Context paramContext, NavigationManager paramNavigationManager, MultiDfeList<T> paramMultiDfeList)
  {
    super(paramContext, paramNavigationManager, paramMultiDfeList.mContainerList.inErrorState(), paramMultiDfeList.mContainerList.mMoreAvailable);
    this.mMultiDfeList = paramMultiDfeList;
    this.mMultiDfeList.mContainerList.addDataChangedListener(this);
  }
  
  protected final String getLastRequestError()
  {
    return ErrorStrings.get(this.mContext, this.mMultiDfeList.mContainerList.getVolleyError());
  }
  
  protected final boolean isMoreDataAvailable()
  {
    return this.mMultiDfeList.mContainerList.mMoreAvailable;
  }
  
  public void onDestroy()
  {
    this.mMultiDfeList.mContainerList.removeDataChangedListener(this);
  }
  
  public void onRestoreInstanceState(PlayRecyclerView paramPlayRecyclerView, Bundle paramBundle)
  {
    Parcelable localParcelable = paramBundle.getParcelable("ListTab.RecyclerViewParcelKey");
    if (localParcelable != null) {
      paramPlayRecyclerView.restoreInstanceState(localParcelable);
    }
  }
  
  public void onSaveInstanceState(PlayRecyclerView paramPlayRecyclerView, Bundle paramBundle)
  {
    paramBundle.putParcelable("ListTab.RecyclerViewParcelKey", paramPlayRecyclerView.saveInstanceState());
  }
  
  protected final void retryLoadingItems()
  {
    this.mMultiDfeList.mContainerList.retryLoadItems();
  }
  
  public void updateAdapterData(T paramT)
  {
    paramT.removeDataChangedListener(this);
    this.mMultiDfeList.mContainerList = paramT;
    paramT.addDataChangedListener(this);
    this.mObservable.notifyChanged();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.FinskyRecyclerViewAdapter
 * JD-Core Version:    0.7.0.1
 */