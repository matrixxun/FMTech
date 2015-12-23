package com.android.i18n.addressinput;

public abstract interface ClientCacheManager
{
  public abstract String get(String paramString);
  
  public abstract String getAddressServerUrl();
  
  public abstract void put(String paramString1, String paramString2);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.ClientCacheManager
 * JD-Core Version:    0.7.0.1
 */