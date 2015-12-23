package com.google.android.finsky.download;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.gms.mdm.NetworkQualityUploader;

public final class DownloadNetworkQualityListener
  implements DownloadQueueListener
{
  private long mBytesStart;
  private ConnectivityManager mConnectivityManager = null;
  Download mCurrentDownload;
  private final Handler mNetworkQueryHandler;
  NetworkState mNetworkStart;
  private long mUptimeMillisStart;
  private WifiManager mWifiManager = null;
  
  public DownloadNetworkQualityListener()
  {
    HandlerThread localHandlerThread = new HandlerThread("NetworkQualityQueryThread");
    localHandlerThread.start();
    this.mNetworkQueryHandler = new Handler(localHandlerThread.getLooper());
    resetMeasurement();
  }
  
  private void resetMeasurement()
  {
    this.mBytesStart = -1L;
    this.mUptimeMillisStart = -1L;
    this.mNetworkStart = null;
    this.mCurrentDownload = null;
  }
  
  public final void onCancel(Download paramDownload)
  {
    resetMeasurement();
  }
  
  public final void onComplete(Download paramDownload)
  {
    if ((this.mBytesStart < 0L) || (this.mUptimeMillisStart < 0L)) {
      return;
    }
    final long l1 = paramDownload.getProgress().bytesCompleted - this.mBytesStart;
    long l2 = Math.max(1L, SystemClock.uptimeMillis() - this.mUptimeMillisStart);
    long l3 = 1000L * l1 / l2;
    final NetworkState localNetworkState = this.mNetworkStart;
    resetMeasurement();
    if ((l3 < 0L) || (l1 < 0L))
    {
      FinskyLog.w("Throughput or bytes transferred were calculated incorrectly or not at all", new Object[0]);
      return;
    }
    this.mNetworkQueryHandler.post(new Runnable()
    {
      public final void run()
      {
        int i = 0;
        DownloadNetworkQualityListener.NetworkState localNetworkState1 = DownloadNetworkQualityListener.access$000(DownloadNetworkQualityListener.this);
        DownloadNetworkQualityListener.NetworkState localNetworkState2 = localNetworkState;
        if ((localNetworkState2 == null) || (localNetworkState2.networkInfo == null) || (localNetworkState1.networkInfo == null)) {
          FinskyLog.w("Missing start or end network state", new Object[0]);
        }
        while (i == 0)
        {
          return;
          if (localNetworkState2.networkInfo.getType() != localNetworkState1.networkInfo.getType())
          {
            Object[] arrayOfObject2 = new Object[2];
            arrayOfObject2[0] = Integer.valueOf(localNetworkState2.networkInfo.getType());
            arrayOfObject2[1] = Integer.valueOf(localNetworkState1.networkInfo.getType());
            FinskyLog.d("Network type has changed (%d to %d)", arrayOfObject2);
            i = 0;
          }
          else
          {
            switch (localNetworkState2.networkInfo.getType())
            {
            default: 
              i = 1;
              break;
            case 0: 
              if (localNetworkState2.networkInfo.getSubtype() == localNetworkState1.networkInfo.getSubtype()) {}
              for (int j = 1;; j = 0)
              {
                if (j == 0)
                {
                  Object[] arrayOfObject1 = new Object[2];
                  arrayOfObject1[0] = Integer.valueOf(localNetworkState2.networkInfo.getSubtype());
                  arrayOfObject1[1] = Integer.valueOf(localNetworkState1.networkInfo.getSubtype());
                  FinskyLog.d("Network subtype has changed (%d to %d)", arrayOfObject1);
                }
                i = j;
                break;
              }
            case 1: 
              if ((localNetworkState2.wifiInfo == null) || (localNetworkState1.wifiInfo == null) || (!localNetworkState2.wifiInfo.getSSID().equals(localNetworkState1.wifiInfo.getSSID())))
              {
                FinskyLog.d("Wifi network changed", new Object[0]);
                i = 0;
              }
              else
              {
                i = 1;
              }
              break;
            }
          }
        }
        Bundle localBundle = new Bundle();
        localBundle.putString("bytes_transferred", Long.toString(l1));
        NetworkQualityUploader.logNetworkStats$5480c1b1(FinskyApp.get(), Long.valueOf(this.val$bytesPerSecond), localBundle);
      }
    });
  }
  
  public final void onError(Download paramDownload, int paramInt)
  {
    resetMeasurement();
  }
  
  public final void onNotificationClicked(Download paramDownload) {}
  
  public final void onProgress(final Download paramDownload, DownloadProgress paramDownloadProgress)
  {
    double d = paramDownloadProgress.bytesCompleted / paramDownloadProgress.bytesTotal;
    if ((this.mCurrentDownload != null) && (!this.mCurrentDownload.equals(paramDownload))) {
      resetMeasurement();
    }
    if ((this.mCurrentDownload == null) && (d >= 0.2D) && (d < 0.9D))
    {
      this.mCurrentDownload = paramDownload;
      this.mBytesStart = paramDownloadProgress.bytesCompleted;
      this.mUptimeMillisStart = SystemClock.uptimeMillis();
      this.mNetworkQueryHandler.post(new Runnable()
      {
        public final void run()
        {
          DownloadNetworkQualityListener.NetworkState localNetworkState = DownloadNetworkQualityListener.access$000(DownloadNetworkQualityListener.this);
          if (paramDownload.equals(DownloadNetworkQualityListener.this.mCurrentDownload)) {
            DownloadNetworkQualityListener.this.mNetworkStart = localNetworkState;
          }
        }
      });
    }
  }
  
  public final void onStart(Download paramDownload)
  {
    resetMeasurement();
  }
  
  public static final class NetworkState
  {
    public NetworkInfo networkInfo = null;
    public WifiInfo wifiInfo = null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.DownloadNetworkQualityListener
 * JD-Core Version:    0.7.0.1
 */