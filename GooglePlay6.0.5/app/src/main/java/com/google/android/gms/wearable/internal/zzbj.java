package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.LargeAssetSyncRequest;

public final class zzbj
  implements LargeAssetSyncRequest
{
  private final LargeAssetSyncRequestPayload zzcgW;
  public final zzay zzcgX;
  public boolean zzcgY;
  public ParcelFileDescriptor zzcgZ;
  public long zzcha;
  public int zzchb;
  
  public zzbj(LargeAssetSyncRequestPayload paramLargeAssetSyncRequestPayload, zzay paramzzay)
  {
    this.zzcgW = ((LargeAssetSyncRequestPayload)zzx.zzC(paramLargeAssetSyncRequestPayload));
    this.zzcgX = ((zzay)zzx.zzC(paramzzay));
    zzx.zzC(paramLargeAssetSyncRequestPayload.path);
    if (paramLargeAssetSyncRequestPayload.zzchc >= 0L) {}
    for (boolean bool = true;; bool = false)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Long.valueOf(paramLargeAssetSyncRequestPayload.zzchc);
      zzx.zzb(bool, "Got negative offset: %s", arrayOfObject);
      return;
    }
  }
  
  public final void refuse$13462e()
  {
    if (!this.zzcgY) {}
    for (boolean bool = true;; bool = false)
    {
      zzx.zza(bool, "createOutputFileDescriptor called when response already set");
      this.zzchb = 0;
      this.zzcgY = true;
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzbj
 * JD-Core Version:    0.7.0.1
 */