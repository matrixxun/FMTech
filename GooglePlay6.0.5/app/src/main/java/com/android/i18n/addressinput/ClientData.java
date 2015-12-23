package com.android.i18n.addressinput;

import android.util.Log;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;

public final class ClientData
  implements DataSource
{
  final Map<String, JsoMap> mBootstrapMap = new HashMap();
  CacheData mCacheData;
  
  public ClientData(CacheData paramCacheData)
  {
    this.mCacheData = paramCacheData;
    buildRegionalData();
  }
  
  private void buildRegionalData()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = RegionDataConstants.getCountryFormatMap().keySet().iterator();
    while (localIterator.hasNext())
    {
      String str2 = (String)localIterator.next();
      localStringBuilder.append(str2 + "~");
      String str3 = (String)RegionDataConstants.getCountryFormatMap().get(str2);
      try
      {
        JsoMap localJsoMap4 = JsoMap.buildJsoMap(str3);
        localJsoMap3 = localJsoMap4;
      }
      catch (JSONException localJSONException2)
      {
        for (;;)
        {
          AddressData localAddressData;
          LookupKey localLookupKey;
          String str1;
          JsoMap localJsoMap2;
          JsoMap localJsoMap3 = null;
        }
      }
      localAddressData = new AddressData.Builder().setCountry(str2).build();
      localLookupKey = new LookupKey.Builder(LookupKey.KeyType.DATA).setAddressData(localAddressData).build();
      this.mBootstrapMap.put(localLookupKey.toString(), localJsoMap3);
    }
    localStringBuilder.setLength(-1 + localStringBuilder.length());
    str1 = "{\"id\":\"data\",\"" + AddressDataKey.COUNTRIES.toString().toLowerCase() + "\": \"" + localStringBuilder.toString() + "\"}";
    try
    {
      localJsoMap2 = JsoMap.buildJsoMap(str1);
      localJsoMap1 = localJsoMap2;
    }
    catch (JSONException localJSONException1)
    {
      for (;;)
      {
        JsoMap localJsoMap1 = null;
      }
    }
    this.mBootstrapMap.put("data", localJsoMap1);
  }
  
  private static AddressVerificationNodeData createNodeData(JsoMap paramJsoMap)
  {
    EnumMap localEnumMap = new EnumMap(AddressDataKey.class);
    JSONArray localJSONArray = paramJsoMap.getKeys();
    for (int i = 0;; i++) {
      if (i < localJSONArray.length()) {
        try
        {
          AddressDataKey localAddressDataKey = AddressDataKey.get(localJSONArray.getString(i));
          if (localAddressDataKey == null) {
            continue;
          }
          localEnumMap.put(localAddressDataKey, paramJsoMap.get(localAddressDataKey.toString().toLowerCase()));
        }
        catch (JSONException localJSONException) {}
      } else {
        return new AddressVerificationNodeData(localEnumMap);
      }
    }
  }
  
  private static boolean isCountryKey(String paramString)
  {
    Util.checkNotNull(paramString, "Cannot use null as a key");
    return paramString.split("/").length == 2;
  }
  
  public final AddressVerificationNodeData get(String paramString)
  {
    JsoMap localJsoMap1 = this.mCacheData.getObj(paramString);
    NotifyingListener localNotifyingListener;
    LookupKey localLookupKey1;
    if (localJsoMap1 == null) {
      if (this.mCacheData.getObj(paramString) == null)
      {
        JsoMap localJsoMap2 = (JsoMap)this.mBootstrapMap.get(paramString);
        localNotifyingListener = new NotifyingListener(this);
        if (LookupKey.hasValidKeyPrefix(paramString))
        {
          localLookupKey1 = new LookupKey.Builder(paramString).build();
          this.mCacheData.fetchDynamicData(localLookupKey1, localJsoMap2, localNotifyingListener);
        }
      }
    }
    try
    {
      localNotifyingListener.waitLoadingEnd();
      CacheData localCacheData;
      Map localMap;
      String str2;
      if ((this.mCacheData.getObj(paramString) == null) && (isCountryKey(paramString)))
      {
        Log.i("ClientData", "Server failure: looking up key in region data constants.");
        localCacheData = this.mCacheData;
        Util.checkNotNull(localLookupKey1, "null key not allowed.");
        localMap = RegionDataConstants.getCountryFormatMap();
        LookupKey localLookupKey2 = localLookupKey1.getKeyForUpperLevelField(AddressField.COUNTRY);
        if (localLookupKey2 == null) {
          break label250;
        }
        String str1 = localLookupKey2.toString();
        int i = str1.lastIndexOf("/");
        if ((i <= 0) || (i == str1.length())) {
          break label250;
        }
        str2 = str1.substring(i + 1);
      }
      for (;;)
      {
        String str3 = (String)localMap.get(str2);
        if (str3 != null) {}
        try
        {
          localCacheData.mCache.putObj(localLookupKey1.toString(), JsoMap.buildJsoMap(str3));
          localJsoMap1 = this.mCacheData.getObj(paramString);
          if ((localJsoMap1 != null) && (paramString.startsWith("data")))
          {
            return createNodeData(localJsoMap1);
            label250:
            str2 = "";
          }
        }
        catch (JSONException localJSONException)
        {
          for (;;)
          {
            Log.w("CacheData", "Failed to parse data for key " + localLookupKey1 + " from RegionDataConstants");
          }
        }
      }
      return null;
    }
    catch (InterruptedException localInterruptedException)
    {
      throw new RuntimeException(localInterruptedException);
    }
  }
  
  public final AddressVerificationNodeData getDefaultData(String paramString)
  {
    if (paramString.split("/").length == 1)
    {
      JsoMap localJsoMap2 = (JsoMap)this.mBootstrapMap.get(paramString);
      if ((localJsoMap2 == null) || (!paramString.startsWith("data"))) {
        throw new RuntimeException("key " + paramString + " does not have bootstrap data");
      }
      return createNodeData(localJsoMap2);
    }
    if (paramString.split("/").length <= 1) {
      throw new RuntimeException("Cannot get country key with key '" + paramString + "'");
    }
    if (isCountryKey(paramString)) {}
    JsoMap localJsoMap1;
    for (;;)
    {
      localJsoMap1 = (JsoMap)this.mBootstrapMap.get(paramString);
      if ((localJsoMap1 != null) && (paramString.startsWith("data"))) {
        break;
      }
      throw new RuntimeException("key " + paramString + " does not have bootstrap data");
      String[] arrayOfString = paramString.split("/");
      paramString = arrayOfString[0] + "/" + arrayOfString[1];
    }
    return createNodeData(localJsoMap1);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.ClientData
 * JD-Core Version:    0.7.0.1
 */