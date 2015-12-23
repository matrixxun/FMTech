package com.google.android.wallet.instrumentmanager.ui.common;

import android.graphics.drawable.Drawable;

public final class DropDownItem
{
  public final int eventType;
  public final int iconResId;
  Drawable mIconDrawable;
  public final String text;
  
  public DropDownItem(int paramInt1, String paramString, int paramInt2)
  {
    this.iconResId = paramInt1;
    this.text = paramString;
    this.eventType = paramInt2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.common.DropDownItem
 * JD-Core Version:    0.7.0.1
 */