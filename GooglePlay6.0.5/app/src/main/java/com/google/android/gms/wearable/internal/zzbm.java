package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;

public final class zzbm
  implements MessageApi
{
  public final PendingResult<MessageApi.SendMessageResult> sendMessage(GoogleApiClient paramGoogleApiClient, final String paramString1, final String paramString2, final byte[] paramArrayOfByte)
  {
    paramGoogleApiClient.zza(new zzi(paramGoogleApiClient) {});
  }
  
  public static final class zzb
    implements MessageApi.SendMessageResult
  {
    private final Status zzUc;
    private final int zzaAY;
    
    public zzb(Status paramStatus, int paramInt)
    {
      this.zzUc = paramStatus;
      this.zzaAY = paramInt;
    }
    
    public final Status getStatus()
    {
      return this.zzUc;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzbm
 * JD-Core Version:    0.7.0.1
 */