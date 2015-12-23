package com.google.android.gms.ads.internal.client;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.internal.zzhb;
import java.util.Arrays;
import java.util.List;

@zzhb
public final class AdRequestParcel
  implements SafeParcelable
{
  public static final zzg CREATOR = new zzg();
  public final Bundle extras;
  public final int versionCode;
  public final long zztV;
  public final int zztW;
  public final List<String> zztX;
  public final boolean zztY;
  public final int zztZ;
  public final boolean zzua;
  public final String zzub;
  public final SearchAdRequestParcel zzuc;
  public final Location zzud;
  public final String zzue;
  public final Bundle zzuf;
  public final Bundle zzug;
  public final List<String> zzuh;
  public final String zzui;
  public final String zzuj;
  public final boolean zzuk;
  
  public AdRequestParcel(int paramInt1, long paramLong, Bundle paramBundle1, int paramInt2, List<String> paramList1, boolean paramBoolean1, int paramInt3, boolean paramBoolean2, String paramString1, SearchAdRequestParcel paramSearchAdRequestParcel, Location paramLocation, String paramString2, Bundle paramBundle2, Bundle paramBundle3, List<String> paramList2, String paramString3, String paramString4, boolean paramBoolean3)
  {
    this.versionCode = paramInt1;
    this.zztV = paramLong;
    if (paramBundle1 == null) {
      paramBundle1 = new Bundle();
    }
    this.extras = paramBundle1;
    this.zztW = paramInt2;
    this.zztX = paramList1;
    this.zztY = paramBoolean1;
    this.zztZ = paramInt3;
    this.zzua = paramBoolean2;
    this.zzub = paramString1;
    this.zzuc = paramSearchAdRequestParcel;
    this.zzud = paramLocation;
    this.zzue = paramString2;
    this.zzuf = paramBundle2;
    this.zzug = paramBundle3;
    this.zzuh = paramList2;
    this.zzui = paramString3;
    this.zzuj = paramString4;
    this.zzuk = paramBoolean3;
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof AdRequestParcel)) {}
    AdRequestParcel localAdRequestParcel;
    do
    {
      return false;
      localAdRequestParcel = (AdRequestParcel)paramObject;
    } while ((this.versionCode != localAdRequestParcel.versionCode) || (this.zztV != localAdRequestParcel.zztV) || (!zzw.equal(this.extras, localAdRequestParcel.extras)) || (this.zztW != localAdRequestParcel.zztW) || (!zzw.equal(this.zztX, localAdRequestParcel.zztX)) || (this.zztY != localAdRequestParcel.zztY) || (this.zztZ != localAdRequestParcel.zztZ) || (this.zzua != localAdRequestParcel.zzua) || (!zzw.equal(this.zzub, localAdRequestParcel.zzub)) || (!zzw.equal(this.zzuc, localAdRequestParcel.zzuc)) || (!zzw.equal(this.zzud, localAdRequestParcel.zzud)) || (!zzw.equal(this.zzue, localAdRequestParcel.zzue)) || (!zzw.equal(this.zzuf, localAdRequestParcel.zzuf)) || (!zzw.equal(this.zzug, localAdRequestParcel.zzug)) || (!zzw.equal(this.zzuh, localAdRequestParcel.zzuh)) || (!zzw.equal(this.zzui, localAdRequestParcel.zzui)) || (!zzw.equal(this.zzuj, localAdRequestParcel.zzuj)) || (this.zzuk != localAdRequestParcel.zzuk));
    return true;
  }
  
  public final int hashCode()
  {
    Object[] arrayOfObject = new Object[18];
    arrayOfObject[0] = Integer.valueOf(this.versionCode);
    arrayOfObject[1] = Long.valueOf(this.zztV);
    arrayOfObject[2] = this.extras;
    arrayOfObject[3] = Integer.valueOf(this.zztW);
    arrayOfObject[4] = this.zztX;
    arrayOfObject[5] = Boolean.valueOf(this.zztY);
    arrayOfObject[6] = Integer.valueOf(this.zztZ);
    arrayOfObject[7] = Boolean.valueOf(this.zzua);
    arrayOfObject[8] = this.zzub;
    arrayOfObject[9] = this.zzuc;
    arrayOfObject[10] = this.zzud;
    arrayOfObject[11] = this.zzue;
    arrayOfObject[12] = this.zzuf;
    arrayOfObject[13] = this.zzug;
    arrayOfObject[14] = this.zzuh;
    arrayOfObject[15] = this.zzui;
    arrayOfObject[16] = this.zzuj;
    arrayOfObject[17] = Boolean.valueOf(this.zzuk);
    return Arrays.hashCode(arrayOfObject);
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzg.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.client.AdRequestParcel
 * JD-Core Version:    0.7.0.1
 */