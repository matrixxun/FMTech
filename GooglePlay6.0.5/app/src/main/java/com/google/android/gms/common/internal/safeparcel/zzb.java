package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public final class zzb
{
  public static int zzH(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(0xFFFF0000 | paramInt);
    paramParcel.writeInt(0);
    return paramParcel.dataPosition();
  }
  
  public static void zzI(Parcel paramParcel, int paramInt)
  {
    int i = paramParcel.dataPosition();
    int j = i - paramInt;
    paramParcel.setDataPosition(paramInt - 4);
    paramParcel.writeInt(j);
    paramParcel.setDataPosition(i);
  }
  
  public static void zza(Parcel paramParcel, int paramInt, byte paramByte)
  {
    zzb(paramParcel, paramInt, 4);
    paramParcel.writeInt(paramByte);
  }
  
  public static void zza(Parcel paramParcel, int paramInt, double paramDouble)
  {
    zzb(paramParcel, paramInt, 8);
    paramParcel.writeDouble(paramDouble);
  }
  
  public static void zza(Parcel paramParcel, int paramInt, long paramLong)
  {
    zzb(paramParcel, paramInt, 8);
    paramParcel.writeLong(paramLong);
  }
  
  public static void zza(Parcel paramParcel, int paramInt, boolean paramBoolean)
  {
    zzb(paramParcel, paramInt, 4);
    if (paramBoolean) {}
    for (int i = 1;; i = 0)
    {
      paramParcel.writeInt(i);
      return;
    }
  }
  
  private static <T extends Parcelable> void zza(Parcel paramParcel, T paramT, int paramInt)
  {
    int i = paramParcel.dataPosition();
    paramParcel.writeInt(1);
    int j = paramParcel.dataPosition();
    paramT.writeToParcel(paramParcel, paramInt);
    int k = paramParcel.dataPosition();
    paramParcel.setDataPosition(i);
    paramParcel.writeInt(k - j);
    paramParcel.setDataPosition(k);
  }
  
  public static void zza$2cfb68bf(Parcel paramParcel, int paramInt, String paramString)
  {
    if (paramString == null) {
      return;
    }
    int i = zzH(paramParcel, paramInt);
    paramParcel.writeString(paramString);
    zzI(paramParcel, i);
  }
  
  public static <T extends Parcelable> void zza$2d7953c6(Parcel paramParcel, int paramInt1, T[] paramArrayOfT, int paramInt2)
  {
    if (paramArrayOfT == null) {
      return;
    }
    int i = zzH(paramParcel, paramInt1);
    int j = paramArrayOfT.length;
    paramParcel.writeInt(j);
    int k = 0;
    if (k < j)
    {
      T ? = paramArrayOfT[k];
      if (? == null) {
        paramParcel.writeInt(0);
      }
      for (;;)
      {
        k++;
        break;
        zza(paramParcel, ?, paramInt2);
      }
    }
    zzI(paramParcel, i);
  }
  
  public static void zza$377a007(Parcel paramParcel, int paramInt1, Parcelable paramParcelable, int paramInt2)
  {
    if (paramParcelable == null) {
      return;
    }
    int i = zzH(paramParcel, paramInt1);
    paramParcelable.writeToParcel(paramParcel, paramInt2);
    zzI(paramParcel, i);
  }
  
  public static void zza$41b439c0(Parcel paramParcel, int paramInt, String[] paramArrayOfString)
  {
    if (paramArrayOfString == null) {
      return;
    }
    int i = zzH(paramParcel, paramInt);
    paramParcel.writeStringArray(paramArrayOfString);
    zzI(paramParcel, i);
  }
  
  public static void zza$52910762(Parcel paramParcel, int paramInt, byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      return;
    }
    int i = zzH(paramParcel, paramInt);
    paramParcel.writeByteArray(paramArrayOfByte);
    zzI(paramParcel, i);
  }
  
  public static void zza$62107c48(Parcel paramParcel, int paramInt, List<Integer> paramList)
  {
    if (paramList == null) {
      return;
    }
    int i = zzH(paramParcel, paramInt);
    int j = paramList.size();
    paramParcel.writeInt(j);
    for (int k = 0; k < j; k++) {
      paramParcel.writeInt(((Integer)paramList.get(k)).intValue());
    }
    zzI(paramParcel, i);
  }
  
  public static void zza$cdac282(Parcel paramParcel, int paramInt, IBinder paramIBinder)
  {
    if (paramIBinder == null) {
      return;
    }
    int i = zzH(paramParcel, paramInt);
    paramParcel.writeStrongBinder(paramIBinder);
    zzI(paramParcel, i);
  }
  
  public static void zza$f7bef55(Parcel paramParcel, int paramInt, Bundle paramBundle)
  {
    if (paramBundle == null) {
      return;
    }
    int i = zzH(paramParcel, paramInt);
    paramParcel.writeBundle(paramBundle);
    zzI(paramParcel, i);
  }
  
  public static void zzb(Parcel paramParcel, int paramInt1, int paramInt2)
  {
    if (paramInt2 >= 65535)
    {
      paramParcel.writeInt(0xFFFF0000 | paramInt1);
      paramParcel.writeInt(paramInt2);
      return;
    }
    paramParcel.writeInt(paramInt1 | paramInt2 << 16);
  }
  
  public static void zzb$62107c48(Parcel paramParcel, int paramInt, List<String> paramList)
  {
    if (paramList == null) {
      return;
    }
    int i = zzH(paramParcel, paramInt);
    paramParcel.writeStringList(paramList);
    zzI(paramParcel, i);
  }
  
  public static void zzc(Parcel paramParcel, int paramInt1, int paramInt2)
  {
    zzb(paramParcel, paramInt1, 4);
    paramParcel.writeInt(paramInt2);
  }
  
  public static <T extends Parcelable> void zzc$62107c48(Parcel paramParcel, int paramInt, List<T> paramList)
  {
    if (paramList == null) {
      return;
    }
    int i = zzH(paramParcel, paramInt);
    int j = paramList.size();
    paramParcel.writeInt(j);
    int k = 0;
    if (k < j)
    {
      Parcelable localParcelable = (Parcelable)paramList.get(k);
      if (localParcelable == null) {
        paramParcel.writeInt(0);
      }
      for (;;)
      {
        k++;
        break;
        zza(paramParcel, localParcelable, 0);
      }
    }
    zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.safeparcel.zzb
 * JD-Core Version:    0.7.0.1
 */