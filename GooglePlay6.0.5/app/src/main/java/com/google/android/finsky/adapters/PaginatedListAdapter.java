package com.google.android.finsky.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.layout.ErrorFooter;
import com.google.android.finsky.navigationmanager.NavigationManager;

public abstract class PaginatedListAdapter
  extends BaseAdapter
  implements OnDataChangedListener
{
  public final Context mContext;
  public int mFooterMode;
  public final LayoutInflater mLayoutInflater;
  public final NavigationManager mNavigationManager;
  protected View.OnClickListener mRetryClickListener = new View.OnClickListener()
  {
    public final void onClick(View paramAnonymousView)
    {
      if (PaginatedListAdapter.this.mFooterMode == 2) {
        PaginatedListAdapter.this.retryLoadingItems();
      }
      PaginatedListAdapter.access$100$33f4544b(PaginatedListAdapter.this);
    }
  };
  
  public PaginatedListAdapter(Context paramContext, NavigationManager paramNavigationManager)
  {
    this.mContext = paramContext;
    this.mNavigationManager = paramNavigationManager;
    this.mLayoutInflater = LayoutInflater.from(paramContext);
    this.mFooterMode = 1;
  }
  
  private void setFooterMode(int paramInt)
  {
    this.mFooterMode = paramInt;
    notifyDataSetChanged();
  }
  
  public final View getErrorFooterView(View paramView, ViewGroup paramViewGroup)
  {
    if (paramView != null) {}
    for (View localView = paramView;; localView = inflate$42ccc377(2130968731, paramViewGroup))
    {
      ErrorFooter localErrorFooter = (ErrorFooter)localView;
      localErrorFooter.configure(getLastRequestError(), this.mRetryClickListener);
      localErrorFooter.setIdentifier("error_footer");
      return localErrorFooter;
    }
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public abstract String getLastRequestError();
  
  public final View inflate$42ccc377(int paramInt, ViewGroup paramViewGroup)
  {
    return this.mLayoutInflater.inflate(paramInt, paramViewGroup, false);
  }
  
  public abstract boolean isMoreDataAvailable();
  
  public final void onDataChanged()
  {
    if (isMoreDataAvailable())
    {
      setFooterMode(1);
      return;
    }
    setFooterMode(0);
  }
  
  public abstract void retryLoadingItems();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.PaginatedListAdapter
 * JD-Core Version:    0.7.0.1
 */