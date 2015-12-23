package com.android.i18n.addressinput;

public abstract interface DataSource
{
  public abstract AddressVerificationNodeData get(String paramString);
  
  public abstract AddressVerificationNodeData getDefaultData(String paramString);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.DataSource
 * JD-Core Version:    0.7.0.1
 */