package com.google.android.finsky.billing;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.protos.VendingProtos.PurchaseMetadataRequestProto;
import com.google.android.finsky.protos.VendingProtos.PurchaseMetadataResponseProto;
import com.google.android.finsky.protos.VendingProtos.PurchaseMetadataResponseProto.Countries.Country;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.vending.remoting.api.VendingApi;
import com.google.android.vending.remoting.api.VendingApi.CountriesConverter;
import com.google.android.vending.remoting.api.VendingRequest;

public final class GetBillingCountriesAction
{
  public final void run(String paramString, final Runnable paramRunnable)
  {
    long l = ((Long)BillingPreferences.LAST_BILLING_COUNTRIES_REFRESH_MILLIS.get()).longValue();
    int i;
    if (System.currentTimeMillis() > l + ((Long)G.vendingBillingCountriesRefreshMs.get()).longValue())
    {
      i = 1;
      if (i != 0) {
        break label76;
      }
    }
    label76:
    for (int j = 1;; j = 0)
    {
      if (j == 0) {
        break label82;
      }
      if (paramRunnable != null) {
        paramRunnable.run();
      }
      FinskyLog.d("Skip getting fresh list of billing countries.", new Object[0]);
      return;
      i = 0;
      break;
    }
    label82:
    VendingApi localVendingApi = FinskyApp.get().getVendingApi(paramString);
    Response.Listener local1 = new Response.Listener() {};
    Response.ErrorListener local2 = new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        FinskyLog.w("PurchaseMetadataRequest failed: %s", new Object[] { paramAnonymousVolleyError });
        if (paramRunnable != null) {
          paramRunnable.run();
        }
      }
    };
    VendingRequest localVendingRequest = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.PurchaseMetadataRequestProto.class, new VendingProtos.PurchaseMetadataRequestProto(), VendingProtos.PurchaseMetadataResponseProto.class, new VendingApi.CountriesConverter(local1), localVendingApi.mApiContext, local2);
    localVendingApi.mRequestQueue.add(localVendingRequest);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.GetBillingCountriesAction
 * JD-Core Version:    0.7.0.1
 */