package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.zznl;
import com.google.android.gms.internal.zznl.zzb;
import com.google.android.gms.wearable.DataApi.DataListener;
import java.util.List;

final class zzcf<T>
  extends zzba.zza
{
  zznl<Object> zzbpK;
  zznl<Object> zzchJ;
  zznl<Object> zzchK;
  zznl<DataApi.DataListener> zzchL;
  zznl<Object> zzchM;
  zznl<Object> zzchN;
  zznl<Object> zzchO;
  zznl<Object> zzchP;
  zznl<Object> zzchQ;
  final String zzchR;
  final IntentFilter[] zzchi;
  
  static void zzf(zznl<?> paramzznl)
  {
    if (paramzznl != null) {
      paramzznl.mListener = null;
    }
  }
  
  public final void onConnectedNodes(List<NodeParcelable> paramList)
  {
    if (this.zzchN != null) {
      this.zzchN.zza(new zznl.zzb()
      {
        public final void zzoR() {}
      });
    }
  }
  
  public final void zza(AmsEntityUpdateParcelable paramAmsEntityUpdateParcelable)
  {
    if (this.zzchJ != null) {
      this.zzchJ.zza(new zznl.zzb()
      {
        public final void zzoR() {}
      });
    }
  }
  
  public final void zza(AncsNotificationParcelable paramAncsNotificationParcelable)
  {
    if (this.zzchK != null) {
      this.zzchK.zza(new zznl.zzb()
      {
        public final void zzoR() {}
      });
    }
  }
  
  public final void zza(CapabilityInfoParcelable paramCapabilityInfoParcelable)
  {
    if (this.zzchQ != null) {
      this.zzchQ.zza(new zznl.zzb()
      {
        public final void zzoR() {}
      });
    }
  }
  
  public final void zza(ChannelEventParcelable paramChannelEventParcelable)
  {
    if (this.zzchO != null) {
      this.zzchO.zza(new zznl.zzb()
      {
        public final void zzoR() {}
      });
    }
  }
  
  public final void zza(LargeAssetQueueStateChangeParcelable paramLargeAssetQueueStateChangeParcelable)
  {
    if (this.zzchP != null)
    {
      this.zzchP.zza(new zznl.zzb()
      {
        public final void zzoR()
        {
          this.zzceJ.zzcgM.release();
        }
      });
      return;
    }
    paramLargeAssetQueueStateChangeParcelable.zzcgM.release();
  }
  
  public final void zza(LargeAssetSyncRequestPayload paramLargeAssetSyncRequestPayload, zzay paramzzay)
  {
    throw new UnsupportedOperationException("onLargeAssetSyncRequest not supported on live listener");
  }
  
  public final void zza(MessageEventParcelable paramMessageEventParcelable)
  {
    if (this.zzbpK != null) {
      this.zzbpK.zza(new zznl.zzb()
      {
        public final void zzoR() {}
      });
    }
  }
  
  public final void zza(NodeParcelable paramNodeParcelable)
  {
    if (this.zzchM != null) {
      this.zzchM.zza(new zznl.zzb()
      {
        public final void zzoR() {}
      });
    }
  }
  
  public final void zza(zzax paramzzax, String paramString, int paramInt)
  {
    throw new UnsupportedOperationException("openFileDescriptor not supported on live listener");
  }
  
  public final void zzar(DataHolder paramDataHolder)
  {
    if (this.zzchL != null)
    {
      this.zzchL.zza(new zznl.zzb()
      {
        public final void zzoR()
        {
          this.zzbGR.close();
        }
      });
      return;
    }
    paramDataHolder.close();
  }
  
  public final void zzb(NodeParcelable paramNodeParcelable)
  {
    if (this.zzchM != null) {
      this.zzchM.zza(new zznl.zzb()
      {
        public final void zzoR() {}
      });
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzcf
 * JD-Core Version:    0.7.0.1
 */