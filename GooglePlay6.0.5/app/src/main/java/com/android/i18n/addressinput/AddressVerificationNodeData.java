package com.android.i18n.addressinput;

import java.util.Map;

public final class AddressVerificationNodeData
{
  private final Map<AddressDataKey, String> mMap;
  
  public AddressVerificationNodeData(Map<AddressDataKey, String> paramMap)
  {
    Util.checkNotNull("Cannot construct StandardNodeData with null map");
    this.mMap = paramMap;
  }
  
  public final boolean containsKey(AddressDataKey paramAddressDataKey)
  {
    return this.mMap.containsKey(paramAddressDataKey);
  }
  
  public final String get(AddressDataKey paramAddressDataKey)
  {
    return (String)this.mMap.get(paramAddressDataKey);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.AddressVerificationNodeData
 * JD-Core Version:    0.7.0.1
 */