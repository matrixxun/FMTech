package com.google.android.gms.wearable;

import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.internal.DataItemAssetParcelable;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class PutDataRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<PutDataRequest> CREATOR = new zzc();
  private static final long zzceD = TimeUnit.MINUTES.toMillis(30L);
  private static final Random zzceE = new SecureRandom();
  public byte[] mData;
  public final Uri mUri;
  final int mVersionCode;
  final Bundle zzceF;
  public long zzceG;
  
  PutDataRequest(int paramInt, Uri paramUri, Bundle paramBundle, byte[] paramArrayOfByte, long paramLong)
  {
    this.mVersionCode = paramInt;
    this.mUri = paramUri;
    this.zzceF = paramBundle;
    this.zzceF.setClassLoader(DataItemAssetParcelable.class.getClassLoader());
    this.mData = paramArrayOfByte;
    this.zzceG = paramLong;
  }
  
  private PutDataRequest(Uri paramUri)
  {
    this(2, paramUri, new Bundle(), null, zzceD);
  }
  
  public static PutDataRequest create(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      throw new IllegalArgumentException("An empty path was supplied.");
    }
    if (!paramString.startsWith("/")) {
      throw new IllegalArgumentException("A path must start with a single / .");
    }
    if (paramString.startsWith("//")) {
      throw new IllegalArgumentException("A path must start with a single / .");
    }
    return zzt(new Uri.Builder().scheme("wear").path(paramString).build());
  }
  
  public static PutDataRequest zzt(Uri paramUri)
  {
    return new PutDataRequest(paramUri);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public final Map<String, Asset> getAssets()
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = this.zzceF.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localHashMap.put(str, (Asset)this.zzceF.getParcelable(str));
    }
    return Collections.unmodifiableMap(localHashMap);
  }
  
  public final PutDataRequest putAsset(String paramString, Asset paramAsset)
  {
    zzx.zzC(paramString);
    zzx.zzC(paramAsset);
    this.zzceF.putParcelable(paramString, paramAsset);
    return this;
  }
  
  public String toString()
  {
    boolean bool = Log.isLoggable("DataMap", 3);
    StringBuilder localStringBuilder1 = new StringBuilder("PutDataRequest[");
    StringBuilder localStringBuilder2 = new StringBuilder("dataSz=");
    if (this.mData == null) {}
    for (Object localObject = "null";; localObject = Integer.valueOf(this.mData.length))
    {
      localStringBuilder1.append(localObject);
      localStringBuilder1.append(", numAssets=" + this.zzceF.size());
      localStringBuilder1.append(", uri=" + this.mUri);
      localStringBuilder1.append(", syncDeadline=" + this.zzceG);
      if (bool) {
        break;
      }
      localStringBuilder1.append("]");
      return localStringBuilder1.toString();
    }
    localStringBuilder1.append("]\n  assets: ");
    Iterator localIterator = this.zzceF.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localStringBuilder1.append("\n    " + str + ": " + this.zzceF.getParcelable(str));
    }
    localStringBuilder1.append("\n  ]");
    return localStringBuilder1.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.PutDataRequest
 * JD-Core Version:    0.7.0.1
 */