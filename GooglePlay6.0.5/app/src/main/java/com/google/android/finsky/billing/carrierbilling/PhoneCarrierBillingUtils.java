package com.google.android.finsky.billing.carrierbilling;

import com.google.android.finsky.billing.carrierbilling.model.SubscriberInfo;
import com.google.android.finsky.billing.carrierbilling.model.SubscriberInfo.Builder;
import com.google.android.finsky.protos.Address;

public final class PhoneCarrierBillingUtils
{
  public static SubscriberInfo getSubscriberInfo(Address paramAddress)
  {
    if (paramAddress == null) {
      return null;
    }
    SubscriberInfo.Builder localBuilder = new SubscriberInfo.Builder();
    localBuilder.name = paramAddress.name;
    localBuilder.address1 = paramAddress.addressLine1;
    localBuilder.address2 = paramAddress.addressLine2;
    localBuilder.city = paramAddress.city;
    localBuilder.state = paramAddress.state;
    localBuilder.postalCode = paramAddress.postalCode;
    localBuilder.identifier = paramAddress.phoneNumber;
    return localBuilder.build();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.carrierbilling.PhoneCarrierBillingUtils
 * JD-Core Version:    0.7.0.1
 */