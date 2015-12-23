package com.google.android.finsky.api.model;

import android.content.Context;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.RequestFuture;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeApiConfig;
import com.google.android.finsky.protos.ContentFilters.ContentFilterSettingsResponse;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.utils.config.GservicesValue;
import java.io.File;
import java.util.concurrent.ExecutionException;

public final class DfeContentFilters
  extends CachedDfeModel<ContentFilters.ContentFilterSettingsResponse>
{
  private final DfeApi mDfeApi;
  
  public DfeContentFilters(DfeApi paramDfeApi, Context paramContext)
  {
    super(paramContext, ContentFilters.ContentFilterSettingsResponse.class);
    this.mDfeApi = paramDfeApi;
  }
  
  private static void logException(Exception paramException)
  {
    Throwable localThrowable = paramException.getCause();
    if (localThrowable == null) {}
    for (String str = null;; str = localThrowable.getClass().getSimpleName())
    {
      FinskyLog.d("Unable to retrieve ContentFilterSettingsResponse: %s", new Object[] { str });
      return;
    }
  }
  
  public final ContentFilters.ContentFilterSettingsResponse fetchFromCache()
  {
    Utils.ensureNotOnMainThread();
    ContentFilters.ContentFilterSettingsResponse localContentFilterSettingsResponse = (ContentFilters.ContentFilterSettingsResponse)loadCachedModel();
    onModelLoaded(localContentFilterSettingsResponse);
    return localContentFilterSettingsResponse;
  }
  
  public final ContentFilters.ContentFilterSettingsResponse fetchOverNetwork$6f1d50b6()
  {
    Utils.ensureNotOnMainThread();
    RequestFuture localRequestFuture = RequestFuture.newFuture();
    fetchSettingsOverNetwork(localRequestFuture, localRequestFuture, true);
    try
    {
      ContentFilters.ContentFilterSettingsResponse localContentFilterSettingsResponse = (ContentFilters.ContentFilterSettingsResponse)localRequestFuture.get();
      onResponse(localContentFilterSettingsResponse);
      return localContentFilterSettingsResponse;
    }
    catch (InterruptedException localInterruptedException)
    {
      logException(localInterruptedException);
      return null;
    }
    catch (ExecutionException localExecutionException)
    {
      for (;;)
      {
        logException(localExecutionException);
      }
    }
  }
  
  public final void fetchSettingsOverNetwork(Response.Listener<ContentFilters.ContentFilterSettingsResponse> paramListener, Response.ErrorListener paramErrorListener, boolean paramBoolean)
  {
    this.mDfeApi.contentFilterSettings(paramListener, paramErrorListener, paramBoolean);
  }
  
  public final boolean isCacheStale()
  {
    if (!hasCachedData()) {
      return true;
    }
    return System.currentTimeMillis() > this.mCacheFile.lastModified() + ((Long)DfeApiConfig.contentFilterSettingsResponseTtlMs.get()).longValue();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.model.DfeContentFilters
 * JD-Core Version:    0.7.0.1
 */