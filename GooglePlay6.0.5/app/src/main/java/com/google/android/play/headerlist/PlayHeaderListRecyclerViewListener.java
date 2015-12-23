package com.google.android.play.headerlist;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.support.v7.widget.RecyclerView.OnScrollListener;

public final class PlayHeaderListRecyclerViewListener
  extends RecyclerView.OnScrollListener
{
  private int mAbsoluteY = -1;
  private RecyclerView.Adapter<?> mAdapter;
  final PlayHeaderListLayout mLayout;
  private final RecyclerView.AdapterDataObserver mObserver;
  protected int mScrollState;
  
  public PlayHeaderListRecyclerViewListener(PlayHeaderListLayout paramPlayHeaderListLayout)
  {
    this.mLayout = paramPlayHeaderListLayout;
    this.mObserver = new RecyclerView.AdapterDataObserver()
    {
      public final void onChanged()
      {
        PlayHeaderListRecyclerViewListener.access$000$536d8310(PlayHeaderListRecyclerViewListener.this);
        PlayHeaderListRecyclerViewListener.this.mLayout.mPendingListSync = 2;
      }
      
      public final void onItemRangeChanged$255f295()
      {
        onChanged();
      }
      
      public final void onItemRangeInserted(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        onChanged();
      }
      
      public final void onItemRangeRemoved(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        onChanged();
      }
    };
  }
  
  private void updateAdapter(RecyclerView.Adapter<?> paramAdapter)
  {
    if (this.mAdapter == paramAdapter) {
      return;
    }
    if (this.mAdapter != null) {
      this.mAdapter.unregisterAdapterDataObserver(this.mObserver);
    }
    this.mAdapter = paramAdapter;
    if (this.mAdapter != null) {
      this.mAdapter.registerAdapterDataObserver(this.mObserver);
    }
    reset(false);
  }
  
  public final void onScrollStateChanged(RecyclerView paramRecyclerView, int paramInt)
  {
    updateAdapter(paramRecyclerView.getAdapter());
    this.mScrollState = paramInt;
    this.mLayout.onScrollStateChanged(paramInt);
    if (this.mLayout.mAppRecyclerViewOnScrollListener != null) {
      this.mLayout.mAppRecyclerViewOnScrollListener.onScrollStateChanged(paramRecyclerView, paramInt);
    }
  }
  
  public final void onScrolled(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    updateAdapter(paramRecyclerView.getAdapter());
    int i = this.mLayout.tryGetCollectionViewAbsoluteY(paramRecyclerView);
    PlayHeaderListLayout localPlayHeaderListLayout;
    int j;
    if (i == -1) {
      if (paramInt2 == 0)
      {
        this.mAbsoluteY = -1;
        localPlayHeaderListLayout = this.mLayout;
        j = this.mScrollState;
        if (paramRecyclerView.getChildCount() != 0) {
          break label119;
        }
      }
    }
    label119:
    for (int k = 0;; k = this.mAbsoluteY)
    {
      localPlayHeaderListLayout.onScroll(j, paramInt2, k);
      if (this.mLayout.mAppRecyclerViewOnScrollListener != null) {
        this.mLayout.mAppRecyclerViewOnScrollListener.onScrolled(paramRecyclerView, paramInt1, paramInt2);
      }
      return;
      if (this.mAbsoluteY == -1) {
        break;
      }
      this.mAbsoluteY = (paramInt2 + this.mAbsoluteY);
      break;
      this.mAbsoluteY = i;
      break;
    }
  }
  
  final void reset(boolean paramBoolean)
  {
    this.mAbsoluteY = -1;
    if (paramBoolean) {
      updateAdapter(null);
    }
    this.mScrollState = 0;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.headerlist.PlayHeaderListRecyclerViewListener
 * JD-Core Version:    0.7.0.1
 */