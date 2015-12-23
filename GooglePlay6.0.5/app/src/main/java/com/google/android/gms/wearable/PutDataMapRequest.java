package com.google.android.gms.wearable;

import android.util.Log;
import com.google.android.gms.internal.zzahi;
import com.google.android.gms.internal.zzahi.zza;
import com.google.android.gms.internal.zzahj;
import com.google.android.gms.internal.zzaik;
import java.util.ArrayList;
import java.util.List;

public final class PutDataMapRequest
{
  private final PutDataRequest zzceC;
  public final DataMap zzcew;
  
  private PutDataMapRequest(PutDataRequest paramPutDataRequest)
  {
    this.zzceC = paramPutDataRequest;
    this.zzcew = new DataMap();
  }
  
  public static PutDataMapRequest create(String paramString)
  {
    return new PutDataMapRequest(PutDataRequest.create(paramString));
  }
  
  public final PutDataRequest asPutDataRequest()
  {
    DataMap localDataMap = this.zzcew;
    zzahj localzzahj = new zzahj();
    ArrayList localArrayList = new ArrayList();
    localzzahj.zzchW = zzahi.zza(localDataMap, localArrayList);
    zzahi.zza localzza = new zzahi.zza(localzzahj, localArrayList);
    this.zzceC.mData = zzaik.toByteArray(localzza.zzchU);
    int i = localzza.zzchV.size();
    for (int j = 0; j < i; j++)
    {
      String str = Integer.toString(j);
      Asset localAsset = (Asset)localzza.zzchV.get(j);
      if (str == null) {
        throw new IllegalStateException("asset key cannot be null: " + localAsset);
      }
      if (localAsset == null) {
        throw new IllegalStateException("asset cannot be null: key=" + str);
      }
      if (Log.isLoggable("DataMap", 3)) {
        Log.d("DataMap", "asPutDataRequest: adding asset: " + str + " " + localAsset);
      }
      this.zzceC.putAsset(str, localAsset);
    }
    return this.zzceC;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.PutDataMapRequest
 * JD-Core Version:    0.7.0.1
 */