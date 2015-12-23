package com.google.android.finsky.layout;

import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class LayoutSwitcher
{
  protected final View mContentLayout;
  protected int mDataLayerId;
  private final int mErrorLayerId;
  private final Handler mHandler = new Handler();
  private final int mLoadingLayerId;
  int mMode;
  volatile boolean mPendingLoad = false;
  final RetryButtonListener mRetryListener;
  private final View.OnClickListener retryClickListener = new View.OnClickListener()
  {
    public final void onClick(View paramAnonymousView)
    {
      LayoutSwitcher.this.switchToLoadingMode();
      LayoutSwitcher.this.mRetryListener.onRetry();
    }
  };
  
  public LayoutSwitcher(View paramView, int paramInt1, int paramInt2, int paramInt3, RetryButtonListener paramRetryButtonListener, int paramInt4)
  {
    this.mDataLayerId = paramInt1;
    this.mErrorLayerId = 2131755806;
    this.mLoadingLayerId = paramInt3;
    this.mContentLayout = paramView;
    this.mRetryListener = paramRetryButtonListener;
    this.mMode = paramInt4;
  }
  
  public LayoutSwitcher(View paramView, int paramInt, RetryButtonListener paramRetryButtonListener)
  {
    this.mDataLayerId = paramInt;
    this.mErrorLayerId = 2131755483;
    this.mLoadingLayerId = 2131755289;
    this.mContentLayout = paramView;
    this.mRetryListener = paramRetryButtonListener;
    this.mMode = 3;
    setLoadingVisible(false);
    setErrorVisible(false, null);
    setDataVisible(false, false);
  }
  
  private void setErrorVisible(boolean paramBoolean, String paramString)
  {
    View localView = this.mContentLayout.findViewById(this.mErrorLayerId);
    int i;
    Button localButton;
    if (paramBoolean)
    {
      i = 0;
      localView.setVisibility(i);
      if (paramBoolean) {
        ((TextView)localView.findViewById(2131755274)).setText(paramString);
      }
      localButton = (Button)localView.findViewById(2131755482);
      if (!paramBoolean) {
        break label78;
      }
    }
    label78:
    for (View.OnClickListener localOnClickListener = this.retryClickListener;; localOnClickListener = null)
    {
      localButton.setOnClickListener(localOnClickListener);
      return;
      i = 8;
      break;
    }
  }
  
  private void setLoadingVisible(boolean paramBoolean)
  {
    View localView = this.mContentLayout.findViewById(this.mLoadingLayerId);
    if (paramBoolean) {}
    for (int i = 0;; i = 8)
    {
      localView.setVisibility(i);
      return;
    }
  }
  
  public final void performSwitch(int paramInt, String paramString)
  {
    this.mPendingLoad = false;
    if (this.mMode == paramInt) {
      return;
    }
    switch (this.mMode)
    {
    default: 
    case 0: 
      do
      {
        switch (paramInt)
        {
        default: 
          throw new IllegalStateException("Invalid mode " + paramInt + "should be LOADING_MODE, ERROR_MODE, DATA_MODE, or BLANK_MODE");
          setLoadingVisible(false);
        }
      } while (paramInt == 2);
    }
    label180:
    for (LayoutSwitcher localLayoutSwitcher = this;; localLayoutSwitcher = this)
    {
      boolean bool = false;
      for (;;)
      {
        localLayoutSwitcher.setDataVisible(false, bool);
        break;
        setErrorVisible(false, null);
        break;
        if (paramInt != 0) {
          break label180;
        }
        bool = true;
        localLayoutSwitcher = this;
      }
      setLoadingVisible(true);
      for (;;)
      {
        this.mMode = paramInt;
        return;
        setErrorVisible(true, paramString);
        continue;
        setDataVisible(true, false);
      }
    }
  }
  
  protected void setDataVisible(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.mDataLayerId <= 0) {}
    ViewGroup localViewGroup;
    do
    {
      return;
      localViewGroup = (ViewGroup)this.mContentLayout.findViewById(this.mDataLayerId);
    } while (localViewGroup == null);
    if (paramBoolean1) {}
    for (int i = 0;; i = 8)
    {
      localViewGroup.setVisibility(i);
      return;
    }
  }
  
  public final void switchToDataMode()
  {
    performSwitch(2, null);
  }
  
  public final void switchToErrorMode(String paramString)
  {
    performSwitch(1, paramString);
  }
  
  public final void switchToLoadingDelayed(int paramInt)
  {
    this.mPendingLoad = true;
    this.mHandler.postDelayed(new Runnable()
    {
      public final void run()
      {
        if (LayoutSwitcher.this.mPendingLoad) {
          LayoutSwitcher.this.switchToLoadingMode();
        }
      }
    }, paramInt);
  }
  
  public final void switchToLoadingMode()
  {
    performSwitch(0, null);
  }
  
  public static abstract interface RetryButtonListener
  {
    public abstract void onRetry();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.LayoutSwitcher
 * JD-Core Version:    0.7.0.1
 */