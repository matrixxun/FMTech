package com.google.android.gms.ads.measurement;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.internal.zzjy;
import com.google.android.gms.internal.zzjz;

public final class GmpMeasurement
{
  public static final class PlayStoreInAppPurchase
  {
    public final Bundle zzNm = new Bundle();
    
    public PlayStoreInAppPurchase(String paramString1, String paramString2, String paramString3, long paramLong1, long paramLong2, String paramString4)
    {
      this.zzNm.putString("ap", paramString1);
      this.zzNm.putString("pi", paramString2);
      this.zzNm.putString("pn", paramString3);
      this.zzNm.putLong("v", paramLong1);
      this.zzNm.putLong("pr", paramLong2);
      this.zzNm.putString("cu", paramString4);
      this.zzNm.putInt("qu", 1);
    }
  }
  
  private static final class zza
  {
    private static zzjz zzNn;
    private static final Object zzqK = new Object();
    
    private static zzjz zzY(Context paramContext)
      throws GooglePlayServicesNotAvailableException
    {
      synchronized (zzqK)
      {
        if (zzNn == null)
        {
          if (paramContext.getApplicationContext() != null) {
            paramContext = paramContext.getApplicationContext();
          }
          zzNn = zzjy.zzaa(paramContext);
        }
        zzjz localzzjz = zzNn;
        return localzzjz;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.measurement.GmpMeasurement
 * JD-Core Version:    0.7.0.1
 */