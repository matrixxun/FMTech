package com.google.android.finsky.layout;

import android.view.View;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;

public final class HeaderLayoutSwitcher
  extends LayoutSwitcher
{
  public HeaderLayoutSwitcher(View paramView, LayoutSwitcher.RetryButtonListener paramRetryButtonListener)
  {
    super(paramView, 2131755586, 2131755806, 2131755289, paramRetryButtonListener, 2);
  }
  
  protected final void setDataVisible(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.mDataLayerId <= 0) {}
    FinskyHeaderListLayout localFinskyHeaderListLayout;
    do
    {
      do
      {
        do
        {
          return;
          localFinskyHeaderListLayout = (FinskyHeaderListLayout)this.mContentLayout.findViewById(this.mDataLayerId);
        } while (localFinskyHeaderListLayout == null);
        if (paramBoolean1) {
          break;
        }
        if (!paramBoolean2)
        {
          localFinskyHeaderListLayout.setVisibility(8);
          return;
        }
        localFinskyHeaderListLayout.setVisibility(0);
      } while (localFinskyHeaderListLayout.mContentView == null);
      localFinskyHeaderListLayout.mContentView.setVisibility(8);
      return;
      localFinskyHeaderListLayout.setVisibility(0);
    } while (localFinskyHeaderListLayout.mContentView == null);
    localFinskyHeaderListLayout.mContentView.setVisibility(0);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.HeaderLayoutSwitcher
 * JD-Core Version:    0.7.0.1
 */