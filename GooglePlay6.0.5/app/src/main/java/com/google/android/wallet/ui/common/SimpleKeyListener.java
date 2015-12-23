package com.google.android.wallet.ui.common;

import android.text.method.NumberKeyListener;

public final class SimpleKeyListener
  extends NumberKeyListener
{
  private final char[] mAccepted;
  private final int mInputType;
  
  public SimpleKeyListener(String paramString)
  {
    this.mAccepted = new char[paramString.length()];
    paramString.getChars(0, paramString.length(), this.mAccepted, 0);
    this.mInputType = 20;
  }
  
  protected final char[] getAcceptedChars()
  {
    return this.mAccepted;
  }
  
  public final int getInputType()
  {
    return this.mInputType;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.SimpleKeyListener
 * JD-Core Version:    0.7.0.1
 */