package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class AncsNotificationParcelable
  implements SafeParcelable
{
  public static final Parcelable.Creator<AncsNotificationParcelable> CREATOR = new zzh();
  int mId;
  final int mVersionCode;
  String zzUe;
  final String zzaBy;
  final String zzaSe;
  final String zzahX;
  final String zzbkS;
  final String zzcfi;
  byte zzcfj;
  byte zzcfk;
  byte zzcfl;
  byte zzcfm;
  
  AncsNotificationParcelable(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4)
  {
    this.mId = paramInt2;
    this.mVersionCode = paramInt1;
    this.zzbkS = paramString1;
    this.zzcfi = paramString2;
    this.zzahX = paramString3;
    this.zzaBy = paramString4;
    this.zzaSe = paramString5;
    this.zzUe = paramString6;
    this.zzcfj = paramByte1;
    this.zzcfk = paramByte2;
    this.zzcfl = paramByte3;
    this.zzcfm = paramByte4;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    AncsNotificationParcelable localAncsNotificationParcelable;
    do
    {
      return true;
      if ((paramObject == null) || (getClass() != paramObject.getClass())) {
        return false;
      }
      localAncsNotificationParcelable = (AncsNotificationParcelable)paramObject;
      if (this.zzcfm != localAncsNotificationParcelable.zzcfm) {
        return false;
      }
      if (this.zzcfl != localAncsNotificationParcelable.zzcfl) {
        return false;
      }
      if (this.zzcfk != localAncsNotificationParcelable.zzcfk) {
        return false;
      }
      if (this.zzcfj != localAncsNotificationParcelable.zzcfj) {
        return false;
      }
      if (this.mId != localAncsNotificationParcelable.mId) {
        return false;
      }
      if (this.mVersionCode != localAncsNotificationParcelable.mVersionCode) {
        return false;
      }
      if (!this.zzbkS.equals(localAncsNotificationParcelable.zzbkS)) {
        return false;
      }
      if (this.zzcfi != null)
      {
        if (this.zzcfi.equals(localAncsNotificationParcelable.zzcfi)) {}
      }
      else {
        while (localAncsNotificationParcelable.zzcfi != null) {
          return false;
        }
      }
      if (!this.zzUe.equals(localAncsNotificationParcelable.zzUe)) {
        return false;
      }
      if (!this.zzahX.equals(localAncsNotificationParcelable.zzahX)) {
        return false;
      }
      if (!this.zzaSe.equals(localAncsNotificationParcelable.zzaSe)) {
        return false;
      }
    } while (this.zzaBy.equals(localAncsNotificationParcelable.zzaBy));
    return false;
  }
  
  public int hashCode()
  {
    int i = 31 * (31 * (31 * this.mVersionCode + this.mId) + this.zzbkS.hashCode());
    if (this.zzcfi != null) {}
    for (int j = this.zzcfi.hashCode();; j = 0) {
      return 31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (j + i) + this.zzahX.hashCode()) + this.zzaBy.hashCode()) + this.zzaSe.hashCode()) + this.zzUe.hashCode()) + this.zzcfj) + this.zzcfk) + this.zzcfl) + this.zzcfm;
    }
  }
  
  public String toString()
  {
    return "AncsNotificationParcelable{mVersionCode=" + this.mVersionCode + ", mId=" + this.mId + ", mAppId='" + this.zzbkS + '\'' + ", mDateTime='" + this.zzcfi + '\'' + ", mNotificationText='" + this.zzahX + '\'' + ", mTitle='" + this.zzaBy + '\'' + ", mSubtitle='" + this.zzaSe + '\'' + ", mDisplayName='" + this.zzUe + '\'' + ", mEventId=" + this.zzcfj + ", mEventFlags=" + this.zzcfk + ", mCategoryId=" + this.zzcfl + ", mCategoryCount=" + this.zzcfm + '}';
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzh.zza$4e9d8cf1(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.AncsNotificationParcelable
 * JD-Core Version:    0.7.0.1
 */