package com.google.android.finsky.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObservable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.layout.ErrorFooter;
import com.google.android.finsky.layout.IdentifiableLinearLayout;
import com.google.android.finsky.navigationmanager.NavigationManager;

public abstract class PaginatedRecyclerViewAdapter
  extends RecyclerView.Adapter
  implements OnDataChangedListener
{
  public final Context mContext;
  protected int mFooterMode;
  protected final LayoutInflater mLayoutInflater;
  public final NavigationManager mNavigationManager;
  protected View.OnClickListener mRetryClickListener = new View.OnClickListener()
  {
    public final void onClick(View paramAnonymousView)
    {
      if (PaginatedRecyclerViewAdapter.this.mFooterMode == 2) {
        PaginatedRecyclerViewAdapter.this.retryLoadingItems();
      }
      PaginatedRecyclerViewAdapter.this.setFooterMode(1);
    }
  };
  
  public PaginatedRecyclerViewAdapter(Context paramContext, NavigationManager paramNavigationManager, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mContext = paramContext;
    this.mNavigationManager = paramNavigationManager;
    this.mLayoutInflater = LayoutInflater.from(paramContext);
    if (paramBoolean1)
    {
      this.mFooterMode = 2;
      return;
    }
    if (paramBoolean2)
    {
      this.mFooterMode = 1;
      return;
    }
    this.mFooterMode = 0;
  }
  
  public static void bindLoadingFooterView(View paramView)
  {
    ((IdentifiableLinearLayout)paramView).setIdentifier("loading_footer");
  }
  
  public final View bindErrorFooterView(View paramView)
  {
    ErrorFooter localErrorFooter = (ErrorFooter)paramView;
    localErrorFooter.configure(getLastRequestError(), this.mRetryClickListener);
    localErrorFooter.setIdentifier("error_footer");
    return localErrorFooter;
  }
  
  public final View createErrorFooterView(ViewGroup paramViewGroup)
  {
    return inflate(2130968731, paramViewGroup, false);
  }
  
  protected View createLeadingSpacerView(ViewGroup paramViewGroup)
  {
    return inflate(2130968994, paramViewGroup, false);
  }
  
  public final View createLoadingFooterView(ViewGroup paramViewGroup)
  {
    return inflate(2130968826, paramViewGroup, false);
  }
  
  protected int getDefaultFooterMode()
  {
    return 0;
  }
  
  public final int getFooterMode()
  {
    return this.mFooterMode;
  }
  
  public final long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public abstract String getLastRequestError();
  
  public final View inflate(int paramInt, ViewGroup paramViewGroup, boolean paramBoolean)
  {
    return this.mLayoutInflater.inflate(paramInt, paramViewGroup, paramBoolean);
  }
  
  public abstract boolean isMoreDataAvailable();
  
  public void onDataChanged()
  {
    if (isMoreDataAvailable())
    {
      setFooterMode(1);
      return;
    }
    setFooterMode(getDefaultFooterMode());
  }
  
  public abstract void retryLoadingItems();
  
  protected void setFooterMode(int paramInt)
  {
    this.mFooterMode = paramInt;
    this.mObservable.notifyChanged();
  }
  
  public final void triggerFooterErrorMode()
  {
    setFooterMode(2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.PaginatedRecyclerViewAdapter
 * JD-Core Version:    0.7.0.1
 */