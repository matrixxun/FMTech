package com.google.android.finsky.layout.play;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.AttributeSet;
import android.view.View;

public class PlayRecyclerView
  extends RecyclerView
{
  private View mEmptyView;
  private RecyclerView.AdapterDataObserver mObserver;
  
  public PlayRecyclerView(Context paramContext)
  {
    super(paramContext);
  }
  
  public PlayRecyclerView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void setupEmptyViewObserver(RecyclerView.Adapter paramAdapter)
  {
    if (paramAdapter != null)
    {
      if (this.mObserver != null)
      {
        paramAdapter.unregisterAdapterDataObserver(this.mObserver);
        this.mObserver = null;
      }
      this.mObserver = new RecyclerView.AdapterDataObserver()
      {
        public final void onChanged()
        {
          PlayRecyclerView.this.updateEmptyStatus();
        }
      };
      paramAdapter.registerAdapterDataObserver(this.mObserver);
    }
  }
  
  private void updateEmptyStatus()
  {
    int i = 8;
    RecyclerView.Adapter localAdapter = getAdapter();
    int j;
    int k;
    if ((localAdapter == null) || (localAdapter.getItemCount() == 0))
    {
      j = 1;
      if (this.mEmptyView != null)
      {
        View localView = this.mEmptyView;
        if (j == 0) {
          break label70;
        }
        k = 0;
        label41:
        localView.setVisibility(k);
      }
      if ((j == 0) || (this.mEmptyView == null)) {
        break label76;
      }
    }
    for (;;)
    {
      setVisibility(i);
      return;
      j = 0;
      break;
      label70:
      k = i;
      break label41;
      label76:
      i = 0;
    }
  }
  
  public final void restoreInstanceState(Parcelable paramParcelable)
  {
    super.onRestoreInstanceState(paramParcelable);
  }
  
  public final Parcelable saveInstanceState()
  {
    return super.onSaveInstanceState();
  }
  
  public void setAdapter(RecyclerView.Adapter paramAdapter)
  {
    if ((getAdapter() != null) && (this.mObserver != null))
    {
      getAdapter().unregisterAdapterDataObserver(this.mObserver);
      this.mObserver = null;
    }
    super.setAdapter(paramAdapter);
    setupEmptyViewObserver(paramAdapter);
    updateEmptyStatus();
  }
  
  public void setEmptyView(View paramView)
  {
    this.mEmptyView = paramView;
    updateEmptyStatus();
    setupEmptyViewObserver(getAdapter());
  }
  
  public static final class ViewHolder
    extends RecyclerView.ViewHolder
  {
    public ViewHolder(View paramView)
    {
      super();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayRecyclerView
 * JD-Core Version:    0.7.0.1
 */