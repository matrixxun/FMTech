package com.google.android.play.headerlist;

import android.database.DataSetObserver;
import android.widget.Adapter;

public final class PlayHeaderScrollableContentListener
  implements PlayScrollableContentView.OnScrollListener
{
  private int mAbsoluteY = -1;
  private Adapter mAdapter;
  final PlayHeaderListLayout mLayout;
  private final DataSetObserver mObserver;
  protected int mScrollState;
  
  public PlayHeaderScrollableContentListener(PlayHeaderListLayout paramPlayHeaderListLayout)
  {
    this.mLayout = paramPlayHeaderListLayout;
    this.mObserver = new DataSetObserver()
    {
      public final void onChanged()
      {
        PlayHeaderScrollableContentListener.access$000$3aa5f084(PlayHeaderScrollableContentListener.this);
        PlayHeaderScrollableContentListener.this.mLayout.mPendingListSync = 2;
      }
      
      public final void onInvalidated()
      {
        onChanged();
      }
    };
  }
  
  final void reset(boolean paramBoolean)
  {
    this.mAbsoluteY = -1;
    if ((paramBoolean) && (this.mAdapter != null))
    {
      if (this.mAdapter != null) {
        this.mAdapter.unregisterDataSetObserver(this.mObserver);
      }
      this.mAdapter = null;
      if (this.mAdapter != null) {
        this.mAdapter.registerDataSetObserver(this.mObserver);
      }
      reset(false);
    }
    this.mScrollState = 0;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.headerlist.PlayHeaderScrollableContentListener
 * JD-Core Version:    0.7.0.1
 */