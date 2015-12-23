package com.google.android.volley.guava;

import android.net.TrafficStats;
import android.os.SystemClock;
import android.util.EventLog;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;

@Deprecated
public final class NetworkStatsEntity
  extends HttpEntityWrapper
{
  private final long mProcessingStartTime;
  private final long mResponseLatency;
  private final long mStartRx;
  private final long mStartTx;
  private final String mUa;
  private final int mUid;
  
  public NetworkStatsEntity(HttpEntity paramHttpEntity, String paramString, int paramInt, long paramLong1, long paramLong2, long paramLong3, long paramLong4)
  {
    super(paramHttpEntity);
    this.mUa = paramString;
    this.mUid = paramInt;
    this.mStartTx = paramLong1;
    this.mStartRx = paramLong2;
    this.mResponseLatency = paramLong3;
    this.mProcessingStartTime = paramLong4;
  }
  
  public final InputStream getContent()
    throws IOException
  {
    return new NetworkStatsInputStream(super.getContent());
  }
  
  private final class NetworkStatsInputStream
    extends FilterInputStream
  {
    public NetworkStatsInputStream(InputStream paramInputStream)
    {
      super();
    }
    
    public final void close()
      throws IOException
    {
      try
      {
        super.close();
        long l4;
        long l5;
        long l6;
        Object[] arrayOfObject2;
        return;
      }
      finally
      {
        long l1 = SystemClock.elapsedRealtime() - NetworkStatsEntity.this.mProcessingStartTime;
        long l2 = TrafficStats.getUidTxBytes(NetworkStatsEntity.this.mUid);
        long l3 = TrafficStats.getUidRxBytes(NetworkStatsEntity.this.mUid);
        Object[] arrayOfObject1 = new Object[5];
        arrayOfObject1[0] = NetworkStatsEntity.this.mUa;
        arrayOfObject1[1] = Long.valueOf(NetworkStatsEntity.this.mResponseLatency);
        arrayOfObject1[2] = Long.valueOf(l1);
        arrayOfObject1[3] = Long.valueOf(l2 - NetworkStatsEntity.this.mStartTx);
        arrayOfObject1[4] = Long.valueOf(l3 - NetworkStatsEntity.this.mStartRx);
        EventLog.writeEvent(52001, arrayOfObject1);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.volley.guava.NetworkStatsEntity
 * JD-Core Version:    0.7.0.1
 */