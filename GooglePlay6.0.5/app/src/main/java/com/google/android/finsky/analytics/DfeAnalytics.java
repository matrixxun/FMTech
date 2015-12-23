package com.google.android.finsky.analytics;

import android.net.Uri;
import android.os.Handler;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.config.G;
import com.google.android.finsky.protos.Log.ClickLogEvent;
import com.google.android.finsky.protos.Log.LogResponse;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.List;

public final class DfeAnalytics
  implements Analytics
{
  private static final boolean DFE_ADMOB_ENABLED = ((Boolean)G.dfeLogsAdMobEnabled.get()).booleanValue();
  private static final int DISPATCH_PERIOD_MS = 1000 * ((Integer)G.logsDispatchIntervalSeconds.get()).intValue();
  private static final int MAX_LOGS_BEFORE_FLUSH = ((Integer)G.maxLogQueueSizeBeforeFlush.get()).intValue();
  private DfeApi mDfeApi;
  private final Handler mHandler;
  private final Runnable mLogFlusher = new Runnable()
  {
    public final void run()
    {
      DfeAnalytics.access$000(DfeAnalytics.this);
    }
  };
  private List<Log.ClickLogEvent> mPendingEvents = new ArrayList();
  
  public DfeAnalytics(Handler paramHandler, DfeApi paramDfeApi)
  {
    this.mHandler = paramHandler;
    this.mDfeApi = paramDfeApi;
  }
  
  public static String buildAnalyticsUrl(String paramString1, String paramString2, String paramString3)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1).append("?url=").append(Uri.encode(paramString2)).append("&action=").append(Uri.encode(paramString3));
    return localStringBuilder.toString();
  }
  
  private void scheduleFlush$1385ff()
  {
    this.mHandler.removeCallbacks(this.mLogFlusher);
    this.mHandler.post(this.mLogFlusher);
  }
  
  public final void logAdMobPageView(String paramString)
  {
    if (!DFE_ADMOB_ENABLED) {
      return;
    }
    if (FinskyLog.DEBUG) {
      FinskyLog.v("Logging *ADMOB* page view: loggedUrl=[%s]", new Object[] { paramString });
    }
    Log.ClickLogEvent localClickLogEvent = new Log.ClickLogEvent();
    localClickLogEvent.eventTime = System.currentTimeMillis();
    localClickLogEvent.hasEventTime = true;
    localClickLogEvent.url = paramString;
    localClickLogEvent.hasUrl = true;
    this.mPendingEvents.add(localClickLogEvent);
    scheduleFlush$1385ff();
  }
  
  public final void reset()
  {
    DfeApi localDfeApi = this.mDfeApi;
    this.mDfeApi = FinskyApp.get().getDfeApi(null);
    if (localDfeApi != null)
    {
      this.mPendingEvents.clear();
      return;
    }
    scheduleFlush$1385ff();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.analytics.DfeAnalytics
 * JD-Core Version:    0.7.0.1
 */