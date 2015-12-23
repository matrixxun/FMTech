package com.google.android.gms.ads.internal.client;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzhb;

@zzhb
public final class SearchAdRequestParcel
  implements SafeParcelable
{
  public static final zzae CREATOR = new zzae();
  public final int backgroundColor;
  public final String query;
  public final int versionCode;
  public final int zzvn;
  public final int zzvo;
  public final int zzvp;
  public final int zzvq;
  public final int zzvr;
  public final int zzvs;
  public final int zzvt;
  public final String zzvu;
  public final int zzvv;
  public final String zzvw;
  public final int zzvx;
  public final int zzvy;
  
  SearchAdRequestParcel(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, String paramString1, int paramInt10, String paramString2, int paramInt11, int paramInt12, String paramString3)
  {
    this.versionCode = paramInt1;
    this.zzvn = paramInt2;
    this.backgroundColor = paramInt3;
    this.zzvo = paramInt4;
    this.zzvp = paramInt5;
    this.zzvq = paramInt6;
    this.zzvr = paramInt7;
    this.zzvs = paramInt8;
    this.zzvt = paramInt9;
    this.zzvu = paramString1;
    this.zzvv = paramInt10;
    this.zzvw = paramString2;
    this.zzvx = paramInt11;
    this.zzvy = paramInt12;
    this.query = paramString3;
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzae.zza$a7ba428(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.client.SearchAdRequestParcel
 * JD-Core Version:    0.7.0.1
 */