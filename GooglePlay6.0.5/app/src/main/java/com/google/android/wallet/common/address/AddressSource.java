package com.google.android.wallet.common.address;

import com.google.location.country.Postaladdress.PostalAddress;
import java.util.List;

public abstract interface AddressSource
{
  public abstract Postaladdress.PostalAddress getAddress(String paramString1, String paramString2);
  
  public abstract List<AddressSourceResult> getAddresses(CharSequence paramCharSequence, char paramChar, char[] paramArrayOfChar, int paramInt, String paramString);
  
  public abstract String getName();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.address.AddressSource
 * JD-Core Version:    0.7.0.1
 */