package com.google.android.wallet.common.address;

import android.content.Context;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.config.G;
import com.google.location.country.Postaladdress.PostalAddress;
import java.util.List;

public final class LocalAddressSource
  extends InMemoryAddressSource
{
  private final List<Postaladdress.PostalAddress> mRawAddresses;
  
  public LocalAddressSource(Context paramContext, List<Postaladdress.PostalAddress> paramList)
  {
    super("LocalAddressSource", paramContext);
    this.mRawAddresses = paramList;
  }
  
  protected final List<Postaladdress.PostalAddress> getAddresses()
    throws Throwable
  {
    return this.mRawAddresses;
  }
  
  protected final int getThresholdForField$132f9b()
  {
    return ((Integer)G.existingProfileAddressSourceThresholdDefault.get()).intValue();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.address.LocalAddressSource
 * JD-Core Version:    0.7.0.1
 */