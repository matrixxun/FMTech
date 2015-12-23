package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.zzmu.zzb;
import com.google.android.gms.wearable.DataApi.DataItemResult;
import com.google.android.gms.wearable.DataApi.DeleteDataItemsResult;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.FutureTask;

final class zzcd
{
  static final class zzad
    extends zzcd.zzc<MessageApi.SendMessageResult>
  {
    public zzad(zzmu.zzb<MessageApi.SendMessageResult> paramzzb)
    {
      super();
    }
    
    public final void zza(SendMessageResponse paramSendMessageResponse)
    {
      zzaj(new zzbm.zzb(zzbz.zzhO(paramSendMessageResponse.statusCode), paramSendMessageResponse.zzbcZ));
    }
  }
  
  static abstract class zzc<T>
    extends zza
  {
    private zzmu.zzb<T> zzTG;
    
    public zzc(zzmu.zzb<T> paramzzb)
    {
      this.zzTG = paramzzb;
    }
    
    public final void zzaj(T paramT)
    {
      zzmu.zzb localzzb = this.zzTG;
      if (localzzb != null)
      {
        localzzb.zzu(paramT);
        this.zzTG = null;
      }
    }
  }
  
  static final class zzf
    extends zzcd.zzc<DataApi.DeleteDataItemsResult>
  {
    public zzf(zzmu.zzb<DataApi.DeleteDataItemsResult> paramzzb)
    {
      super();
    }
    
    public final void zza(DeleteDataItemsResponse paramDeleteDataItemsResponse)
    {
      zzaj(new zzx.zzb(zzbz.zzhO(paramDeleteDataItemsResponse.statusCode), paramDeleteDataItemsResponse.zzcgh));
    }
  }
  
  static final class zzr
    extends zzcd.zzc<DataApi.DataItemResult>
  {
    public zzr(zzmu.zzb<DataApi.DataItemResult> paramzzb)
    {
      super();
    }
    
    public final void zza(GetDataItemResponse paramGetDataItemResponse)
    {
      zzaj(new zzx.zza(zzbz.zzhO(paramGetDataItemResponse.statusCode), paramGetDataItemResponse.zzcgs));
    }
  }
  
  static final class zzs
    extends zzcd.zzc<DataItemBuffer>
  {
    public zzs(zzmu.zzb<DataItemBuffer> paramzzb)
    {
      super();
    }
    
    public final void zzas(DataHolder paramDataHolder)
    {
      zzaj(new DataItemBuffer(paramDataHolder));
    }
  }
  
  static final class zzw
    extends zza
  {
    public final void zzc(Status paramStatus) {}
  }
  
  static final class zzy
    extends zzcd.zzc<DataApi.DataItemResult>
  {
    private final List<FutureTask<Boolean>> zzzM;
    
    zzy(zzmu.zzb<DataApi.DataItemResult> paramzzb, List<FutureTask<Boolean>> paramList)
    {
      super();
      this.zzzM = paramList;
    }
    
    public final void zza(PutDataResponse paramPutDataResponse)
    {
      zzaj(new zzx.zza(zzbz.zzhO(paramPutDataResponse.statusCode), paramPutDataResponse.zzcgs));
      if (paramPutDataResponse.statusCode != 0)
      {
        Iterator localIterator = this.zzzM.iterator();
        while (localIterator.hasNext()) {
          ((FutureTask)localIterator.next()).cancel(true);
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzcd
 * JD-Core Version:    0.7.0.1
 */