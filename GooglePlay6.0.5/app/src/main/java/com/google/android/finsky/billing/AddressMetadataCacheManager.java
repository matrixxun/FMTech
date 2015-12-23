package com.google.android.finsky.billing;

import com.android.i18n.addressinput.ClientCacheManager;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.google.android.finsky.config.G;
import com.google.android.play.utils.config.GservicesValue;
import java.io.UnsupportedEncodingException;

public final class AddressMetadataCacheManager
  implements ClientCacheManager
{
  private final Cache mCache;
  
  public AddressMetadataCacheManager(Cache paramCache)
  {
    this.mCache = paramCache;
  }
  
  public final String get(String paramString)
  {
    Cache.Entry localEntry = this.mCache.get("AddressMetadataCacheManager-" + paramString);
    if ((localEntry == null) || (localEntry.isExpired())) {
      return "";
    }
    try
    {
      String str = new String(localEntry.data, "UTF-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException("UTF-8 not supported.");
    }
  }
  
  public final String getAddressServerUrl()
  {
    return (String)G.vendingAddressServerUrl.get();
  }
  
  public final void put(String paramString1, String paramString2)
  {
    Cache.Entry localEntry = new Cache.Entry();
    try
    {
      localEntry.data = paramString2.getBytes("UTF-8");
      localEntry.serverDate = System.currentTimeMillis();
      localEntry.ttl = (604800000L + localEntry.serverDate);
      this.mCache.put("AddressMetadataCacheManager-" + paramString1, localEntry);
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException("UTF-8 not supported.");
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.AddressMetadataCacheManager
 * JD-Core Version:    0.7.0.1
 */