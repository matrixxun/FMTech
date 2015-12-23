package com.google.android.gms.clearcut;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import java.util.concurrent.TimeUnit;

public abstract interface ClearcutLoggerApi
{
  public abstract boolean flush$708acd79(long paramLong, TimeUnit paramTimeUnit);
  
  public abstract PendingResult<Status> logEvent(GoogleApiClient paramGoogleApiClient, LogEventParcelable paramLogEventParcelable);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.clearcut.ClearcutLoggerApi
 * JD-Core Version:    0.7.0.1
 */