package com.android.i18n.addressinput;

import android.util.Log;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import org.apache.http.client.HttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

public final class CacheData
{
  final HashSet<String> mBadKeys = new HashSet();
  final JsoMap mCache;
  final ClientCacheManager mClientCacheManager;
  final HashSet<String> mRequestedKeys = new HashSet();
  String mServiceUrl;
  private final HashMap<LookupKey, HashSet<CacheListener>> mTemporaryListenerStore = new HashMap();
  
  public CacheData()
  {
    this(new SimpleClientCacheManager());
  }
  
  public CacheData(ClientCacheManager paramClientCacheManager)
  {
    this.mClientCacheManager = paramClientCacheManager;
    String str = this.mClientCacheManager.getAddressServerUrl();
    Util.checkNotNull(str, "Cannot set URL of address data server to null.");
    this.mServiceUrl = str;
    this.mCache = JsoMap.createEmptyJsoMap();
  }
  
  static void triggerDataLoadingEndIfNotNull(DataLoadListener paramDataLoadListener)
  {
    if (paramDataLoadListener != null) {
      paramDataLoadListener.dataLoadingEnd();
    }
  }
  
  final void fetchDynamicData(final LookupKey paramLookupKey, JSONObject paramJSONObject, final DataLoadListener paramDataLoadListener)
  {
    Util.checkNotNull(paramLookupKey, "null key not allowed.");
    if (this.mCache.containsKey(paramLookupKey.toString()))
    {
      triggerDataLoadingEndIfNotNull(paramDataLoadListener);
      return;
    }
    if (this.mBadKeys.contains(paramLookupKey.toString()))
    {
      triggerDataLoadingEndIfNotNull(paramDataLoadListener);
      return;
    }
    if (!this.mRequestedKeys.add(paramLookupKey.toString()))
    {
      Log.d("CacheData", "data for key " + paramLookupKey + " requested but not cached yet");
      CacheListener local1 = new CacheListener()
      {
        public final void onAdd$552c4e01()
        {
          CacheData.triggerDataLoadingEndIfNotNull(paramDataLoadListener);
        }
      };
      Util.checkNotNull(paramLookupKey);
      Util.checkNotNull(local1);
      HashSet localHashSet = (HashSet)this.mTemporaryListenerStore.get(paramLookupKey);
      if (localHashSet == null)
      {
        localHashSet = new HashSet();
        this.mTemporaryListenerStore.put(paramLookupKey, localHashSet);
      }
      localHashSet.add(local1);
      return;
    }
    String str = this.mClientCacheManager.get(paramLookupKey.toString());
    if (str.length() > 0)
    {
      JsonHandler localJsonHandler1 = new JsonHandler(paramLookupKey.toString(), paramJSONObject, paramDataLoadListener, (byte)0);
      try
      {
        JsonHandler.access$500(localJsonHandler1, JsoMap.buildJsoMap(str));
        return;
      }
      catch (JSONException localJSONException)
      {
        Log.w("CacheData", "Data from client's cache is in the wrong format: " + str);
      }
    }
    new JsonpRequestBuilder();
    HttpParams localHttpParams = JsonpRequestBuilder.AsyncHttp.access$000().getParams();
    HttpConnectionParams.setConnectionTimeout(localHttpParams, 5000);
    HttpConnectionParams.setSoTimeout(localHttpParams, 5000);
    final JsonHandler localJsonHandler2 = new JsonHandler(paramLookupKey.toString(), paramJSONObject, paramDataLoadListener, (byte)0);
    JsonpRequestBuilder.requestObject(this.mServiceUrl + "/" + paramLookupKey.toString(), new JsonpRequestBuilder.AsyncCallback()
    {
      public final void onFailure$786b7c60()
      {
        Log.w("CacheData", "Request for key " + paramLookupKey + " failed");
        CacheData.this.mRequestedKeys.remove(paramLookupKey.toString());
        CacheData.access$100(CacheData.this, paramLookupKey.toString());
        CacheData.triggerDataLoadingEndIfNotNull(paramDataLoadListener);
      }
    });
  }
  
  public final JsoMap getObj(String paramString)
  {
    Util.checkNotNull(paramString, "null key not allowed");
    return this.mCache.getObj(paramString);
  }
  
  private static abstract interface CacheListener
    extends EventListener
  {
    public abstract void onAdd$552c4e01();
  }
  
  private final class JsonHandler
  {
    private final JSONObject mExistingJso;
    private final String mKey;
    private final DataLoadListener mListener;
    
    private JsonHandler(String paramString, JSONObject paramJSONObject, DataLoadListener paramDataLoadListener)
    {
      Util.checkNotNull(paramString);
      this.mKey = paramString;
      this.mExistingJso = paramJSONObject;
      this.mListener = paramDataLoadListener;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.CacheData
 * JD-Core Version:    0.7.0.1
 */