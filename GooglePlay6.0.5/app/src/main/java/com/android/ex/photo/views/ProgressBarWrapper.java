package com.android.ex.photo.views;

import android.widget.ProgressBar;

public final class ProgressBarWrapper
{
  private final ProgressBar mDeterminate;
  private final ProgressBar mIndeterminate;
  private boolean mIsIndeterminate;
  
  public ProgressBarWrapper(ProgressBar paramProgressBar1, ProgressBar paramProgressBar2)
  {
    this.mDeterminate = paramProgressBar1;
    this.mIndeterminate = paramProgressBar2;
    this.mIsIndeterminate = true;
    setVisibility(this.mIsIndeterminate);
  }
  
  private void setVisibility(boolean paramBoolean)
  {
    int i = 8;
    ProgressBar localProgressBar1 = this.mIndeterminate;
    int j;
    ProgressBar localProgressBar2;
    if (paramBoolean)
    {
      j = 0;
      localProgressBar1.setVisibility(j);
      localProgressBar2 = this.mDeterminate;
      if (!paramBoolean) {
        break label44;
      }
    }
    for (;;)
    {
      localProgressBar2.setVisibility(i);
      return;
      j = i;
      break;
      label44:
      i = 0;
    }
  }
  
  public final void setVisibility(int paramInt)
  {
    if ((paramInt == 4) || (paramInt == 8))
    {
      this.mIndeterminate.setVisibility(paramInt);
      this.mDeterminate.setVisibility(paramInt);
      return;
    }
    setVisibility(this.mIsIndeterminate);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.ex.photo.views.ProgressBarWrapper
 * JD-Core Version:    0.7.0.1
 */