package com.google.android.play.headerlist;

import android.database.DataSetObserver;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;

public final class PlayHeaderListOnScrollListener
  implements AbsListView.OnScrollListener
{
  private int mAbsoluteY;
  private Adapter mAdapter;
  private final PlayHeaderListLayout mLayout;
  private final DataSetObserver mObserver;
  private int mPreviousRelativeTopIndex;
  private final SparseIntArray[] mRelativeTops;
  protected int mScrollState;
  
  public PlayHeaderListOnScrollListener(PlayHeaderListLayout paramPlayHeaderListLayout)
  {
    SparseIntArray[] arrayOfSparseIntArray = new SparseIntArray[2];
    arrayOfSparseIntArray[0] = new SparseIntArray();
    arrayOfSparseIntArray[1] = new SparseIntArray();
    this.mRelativeTops = arrayOfSparseIntArray;
    this.mAbsoluteY = -1;
    this.mLayout = paramPlayHeaderListLayout;
    this.mObserver = new DataSetObserver()
    {
      public final void onChanged()
      {
        PlayHeaderListOnScrollListener.access$000$6e23d318(PlayHeaderListOnScrollListener.this);
        PlayHeaderListOnScrollListener.this.mLayout.mPendingListSync = 2;
      }
      
      public final void onInvalidated()
      {
        onChanged();
      }
    };
  }
  
  private SparseIntArray previousRelativeTops()
  {
    return this.mRelativeTops[this.mPreviousRelativeTopIndex];
  }
  
  private void updateAdapter(Adapter paramAdapter)
  {
    if (this.mAdapter == paramAdapter) {
      return;
    }
    if (this.mAdapter != null) {
      this.mAdapter.unregisterDataSetObserver(this.mObserver);
    }
    this.mAdapter = paramAdapter;
    if (this.mAdapter != null) {
      this.mAdapter.registerDataSetObserver(this.mObserver);
    }
    reset(false);
  }
  
  public final void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = Math.min(paramInt2, paramInt3);
    updateAdapter(paramAbsListView.getAdapter());
    int j = -1;
    SparseIntArray localSparseIntArray1 = previousRelativeTops();
    SparseIntArray localSparseIntArray2 = this.mRelativeTops[((1 + this.mPreviousRelativeTopIndex) % 2)];
    localSparseIntArray2.clear();
    for (int k = paramInt1; k < paramInt1 + i; k++) {
      localSparseIntArray2.put(k, paramAbsListView.getChildAt(k - paramInt1).getTop());
    }
    int m = paramInt1;
    int n = paramInt1 + i;
    int i1 = 0;
    label166:
    PlayHeaderListLayout localPlayHeaderListLayout;
    int i2;
    if (m < n)
    {
      j = localSparseIntArray1.get(m, -1);
      if (j != -1) {
        i1 = j - localSparseIntArray2.get(m);
      }
    }
    else
    {
      this.mPreviousRelativeTopIndex = ((1 + this.mPreviousRelativeTopIndex) % 2);
      if ((this.mAbsoluteY != -1) && (j != -1)) {
        break label234;
      }
      this.mAbsoluteY = this.mLayout.tryGetCollectionViewAbsoluteY(paramAbsListView);
      localPlayHeaderListLayout = this.mLayout;
      i2 = this.mScrollState;
      if (paramAbsListView.getChildCount() != 0) {
        break label248;
      }
    }
    label234:
    label248:
    for (int i3 = 0;; i3 = this.mAbsoluteY)
    {
      localPlayHeaderListLayout.onScroll(i2, i1, i3);
      if (this.mLayout.mAppListViewOnScrollListener != null) {
        this.mLayout.mAppListViewOnScrollListener.onScroll(paramAbsListView, paramInt1, i, paramInt3);
      }
      return;
      m++;
      break;
      this.mAbsoluteY = (i1 + this.mAbsoluteY);
      break label166;
    }
  }
  
  public final void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
  {
    updateAdapter(paramAbsListView.getAdapter());
    this.mScrollState = paramInt;
    this.mLayout.onScrollStateChanged(paramInt);
    if (this.mLayout.mAppListViewOnScrollListener != null) {
      this.mLayout.mAppListViewOnScrollListener.onScrollStateChanged(paramAbsListView, this.mScrollState);
    }
  }
  
  final void reset(boolean paramBoolean)
  {
    previousRelativeTops().clear();
    this.mAbsoluteY = -1;
    if (paramBoolean) {
      updateAdapter(null);
    }
    this.mScrollState = 0;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.headerlist.PlayHeaderListOnScrollListener
 * JD-Core Version:    0.7.0.1
 */