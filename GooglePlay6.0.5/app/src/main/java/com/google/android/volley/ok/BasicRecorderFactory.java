package com.google.android.volley.ok;

import android.net.TrafficStats;
import android.os.Process;
import android.os.SystemClock;
import com.google.android.volley.guava.NetworkStatsEntity;
import org.apache.http.HttpEntity;

public final class BasicRecorderFactory
  implements StatRecorderFactory
{
  int mUid = Process.myUid();
  
  public final StatRecorderFactory.StatRecorder createStatRecorder$6c5cba1d()
  {
    return new BasicStatRecorder();
  }
  
  private final class BasicStatRecorder
    implements StatRecorderFactory.StatRecorder
  {
    long mStart = SystemClock.elapsedRealtime();
    long mStartRxBytes = TrafficStats.getUidRxBytes(BasicRecorderFactory.this.mUid);
    long mStartTxBytes = TrafficStats.getUidTxBytes(BasicRecorderFactory.this.mUid);
    
    BasicStatRecorder() {}
    
    public final HttpEntity recordStats$58b453ee(String paramString, HttpEntity paramHttpEntity)
    {
      long l = SystemClock.elapsedRealtime() - this.mStart;
      return new NetworkStatsEntity(paramHttpEntity, paramString, BasicRecorderFactory.this.mUid, this.mStartTxBytes, this.mStartRxBytes, l, this.mStart);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.volley.ok.BasicRecorderFactory
 * JD-Core Version:    0.7.0.1
 */