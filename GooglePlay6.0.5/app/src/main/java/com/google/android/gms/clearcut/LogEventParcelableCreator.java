package com.google.android.gms.clearcut;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.playlog.internal.PlayLoggerContext;

public final class LogEventParcelableCreator
  implements Parcelable.Creator<LogEventParcelable>
{
  public static LogEventParcelable createFromParcel(Parcel paramParcel)
  {
    int i = zza.zzbc(paramParcel);
    Object localObject = null;
    byte[] arrayOfByte = null;
    PlayLoggerContext localPlayLoggerContext = null;
    int j = 0;
    while (paramParcel.dataPosition() < i)
    {
      int k = paramParcel.readInt();
      switch (0xFFFF & k)
      {
      default: 
        zza.zzb(paramParcel, k);
        break;
      case 1: 
        j = zza.zzg(paramParcel, k);
        break;
      case 2: 
        localPlayLoggerContext = (PlayLoggerContext)zza.zza(paramParcel, k, PlayLoggerContext.CREATOR);
        break;
      case 3: 
        arrayOfByte = zza.zzt(paramParcel, k);
        break;
      case 4: 
        int m = zza.zza(paramParcel, k);
        int n = paramParcel.dataPosition();
        int[] arrayOfInt;
        if (m == 0) {
          arrayOfInt = null;
        }
        for (;;)
        {
          localObject = arrayOfInt;
          break;
          arrayOfInt = paramParcel.createIntArray();
          paramParcel.setDataPosition(m + n);
        }
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    }
    return new LogEventParcelable(j, localPlayLoggerContext, arrayOfByte, localObject);
  }
  
  static void zza(LogEventParcelable paramLogEventParcelable, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramLogEventParcelable.versionCode);
    zzb.zza$377a007(paramParcel, 2, paramLogEventParcelable.playLoggerContext, paramInt);
    zzb.zza$52910762(paramParcel, 3, paramLogEventParcelable.logEventBytes);
    int[] arrayOfInt = paramLogEventParcelable.testCodes;
    if (arrayOfInt != null)
    {
      int j = zzb.zzH(paramParcel, 4);
      paramParcel.writeIntArray(arrayOfInt);
      zzb.zzI(paramParcel, j);
    }
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.clearcut.LogEventParcelableCreator
 * JD-Core Version:    0.7.0.1
 */