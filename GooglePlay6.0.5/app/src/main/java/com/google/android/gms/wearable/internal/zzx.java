package com.google.android.gms.wearable.internal;

import android.net.Uri;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataApi.DataItemResult;
import com.google.android.gms.wearable.DataApi.DeleteDataItemsResult;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.PutDataRequest;

public final class zzx
  implements DataApi
{
  public final PendingResult<DataApi.DeleteDataItemsResult> deleteDataItems(GoogleApiClient paramGoogleApiClient, final Uri paramUri)
  {
    if (paramUri != null) {}
    for (boolean bool = true;; bool = false)
    {
      com.google.android.gms.common.internal.zzx.zzb(bool, "uri must not be null");
      com.google.android.gms.common.internal.zzx.zzb(true, "invalid filter type");
      paramGoogleApiClient.zza(new zzi(paramGoogleApiClient) {});
    }
  }
  
  public final PendingResult<DataApi.DataItemResult> getDataItem(GoogleApiClient paramGoogleApiClient, final Uri paramUri)
  {
    paramGoogleApiClient.zza(new zzi(paramGoogleApiClient) {});
  }
  
  public final PendingResult<DataItemBuffer> getDataItems(GoogleApiClient paramGoogleApiClient)
  {
    paramGoogleApiClient.zza(new zzi(paramGoogleApiClient) {});
  }
  
  public final PendingResult<DataItemBuffer> getDataItems(GoogleApiClient paramGoogleApiClient, Uri paramUri)
  {
    return getDataItems(paramGoogleApiClient, paramUri, 0);
  }
  
  public final PendingResult<DataItemBuffer> getDataItems(GoogleApiClient paramGoogleApiClient, final Uri paramUri, final int paramInt)
  {
    if (paramUri != null) {}
    for (boolean bool1 = true;; bool1 = false)
    {
      com.google.android.gms.common.internal.zzx.zzb(bool1, "uri must not be null");
      boolean bool2;
      if (paramInt != 0)
      {
        bool2 = false;
        if (paramInt != 1) {}
      }
      else
      {
        bool2 = true;
      }
      com.google.android.gms.common.internal.zzx.zzb(bool2, "invalid filter type");
      paramGoogleApiClient.zza(new zzi(paramGoogleApiClient) {});
    }
  }
  
  public final PendingResult<DataApi.DataItemResult> putDataItem(GoogleApiClient paramGoogleApiClient, final PutDataRequest paramPutDataRequest)
  {
    paramGoogleApiClient.zza(new zzi(paramGoogleApiClient) {});
  }
  
  public static final class zza
    implements DataApi.DataItemResult
  {
    private final Status zzUc;
    private final DataItem zzcgc;
    
    public zza(Status paramStatus, DataItem paramDataItem)
    {
      this.zzUc = paramStatus;
      this.zzcgc = paramDataItem;
    }
    
    public final DataItem getDataItem()
    {
      return this.zzcgc;
    }
    
    public final Status getStatus()
    {
      return this.zzUc;
    }
  }
  
  public static final class zzb
    implements DataApi.DeleteDataItemsResult
  {
    private final Status zzUc;
    private final int zzcgd;
    
    public zzb(Status paramStatus, int paramInt)
    {
      this.zzUc = paramStatus;
      this.zzcgd = paramInt;
    }
    
    public final Status getStatus()
    {
      return this.zzUc;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzx
 * JD-Core Version:    0.7.0.1
 */