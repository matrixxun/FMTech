package com.google.android.finsky.activities.myaccount;

import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.layout.MyAccountRowView;

public final class SelectionListener
  implements View.OnClickListener
{
  private final FinskyEventLog mEventLogger;
  int mSelectedPosition;
  private MyAccountRowView mSelectedRowView;
  
  public SelectionListener(FinskyEventLog paramFinskyEventLog)
  {
    this.mEventLogger = paramFinskyEventLog;
    this.mSelectedPosition = -1;
  }
  
  public final boolean hasSelection()
  {
    return this.mSelectedPosition != -1;
  }
  
  public final void onClick(View paramView)
  {
    MyAccountRowView localMyAccountRowView = (MyAccountRowView)paramView;
    if (localMyAccountRowView.mExpanded) {
      return;
    }
    this.mEventLogger.logClickEvent(localMyAccountRowView.getPlayStoreUiElement().type, null, localMyAccountRowView);
    localMyAccountRowView.toggleExpansion();
    if ((this.mSelectedPosition != -1) && (this.mSelectedRowView != null)) {
      this.mSelectedRowView.toggleExpansion();
    }
    this.mSelectedPosition = localMyAccountRowView.getRowPosition();
    this.mSelectedRowView = localMyAccountRowView;
  }
  
  public final void setAsClickListenerOf(MyAccountRowView paramMyAccountRowView, int paramInt)
  {
    if (this.mSelectedRowView == paramMyAccountRowView) {
      this.mSelectedRowView = null;
    }
    if (paramInt == this.mSelectedPosition) {
      this.mSelectedRowView = paramMyAccountRowView;
    }
    paramMyAccountRowView.setRowPosition(paramInt);
    paramMyAccountRowView.setOnClickListener(this);
    if (paramInt == this.mSelectedPosition) {
      paramMyAccountRowView.setClickable(false);
    }
  }
  
  public final void setDefaultRowView(MyAccountRowView paramMyAccountRowView, int paramInt)
  {
    if (this.mSelectedPosition == -1)
    {
      this.mSelectedRowView = paramMyAccountRowView;
      this.mSelectedPosition = paramInt;
      paramMyAccountRowView.mExpanded = true;
      paramMyAccountRowView.getLayoutParams().height = paramMyAccountRowView.mBaseRowHeightExpanded;
      paramMyAccountRowView.syncBackground();
      paramMyAccountRowView.setClickable(false);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.myaccount.SelectionListener
 * JD-Core Version:    0.7.0.1
 */