package com.google.android.vending.remoting.api;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.google.android.finsky.api.DfeApiConfig;
import com.google.android.finsky.protos.VendingProtos.PurchaseMetadataResponseProto;
import com.google.android.finsky.protos.VendingProtos.PurchaseMetadataResponseProto.Countries.Country;
import com.google.android.play.utils.config.GservicesValue;

public final class VendingApi
{
  public static final boolean SEND_AD_ID_FOR_CONTENT_SYNC = ((Boolean)DfeApiConfig.sendAdIdInRequestsForRads.get()).booleanValue();
  public final VendingApiContext mApiContext;
  public final RequestQueue mRequestQueue;
  
  public VendingApi(RequestQueue paramRequestQueue, VendingApiContext paramVendingApiContext)
  {
    this.mRequestQueue = paramRequestQueue;
    this.mApiContext = paramVendingApiContext;
  }
  
  private static final class CountriesConverter
    implements Response.Listener<VendingProtos.PurchaseMetadataResponseProto>
  {
    private final Response.Listener<VendingProtos.PurchaseMetadataResponseProto.Countries.Country[]> mListener;
    
    public CountriesConverter(Response.Listener<VendingProtos.PurchaseMetadataResponseProto.Countries.Country[]> paramListener)
    {
      this.mListener = paramListener;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.remoting.api.VendingApi
 * JD-Core Version:    0.7.0.1
 */