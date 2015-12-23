package com.google.android.gms.googlehelp.internal.common;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class OverflowMenuItem
  implements SafeParcelable
{
  public static final Parcelable.Creator<OverflowMenuItem> CREATOR = new zzh();
  final int mId;
  final Intent mIntent;
  final int mVersionCode;
  final String zzaBy;
  
  OverflowMenuItem(int paramInt1, int paramInt2, String paramString, Intent paramIntent)
  {
    this.mVersionCode = paramInt1;
    this.mId = paramInt2;
    this.zzaBy = paramString;
    this.mIntent = paramIntent;
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzh.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.googlehelp.internal.common.OverflowMenuItem
 * JD-Core Version:    0.7.0.1
 */