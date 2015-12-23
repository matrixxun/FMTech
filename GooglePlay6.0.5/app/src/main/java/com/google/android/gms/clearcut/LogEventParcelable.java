package com.google.android.gms.clearcut;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.internal.zzaip.zzd;
import com.google.android.gms.playlog.internal.PlayLoggerContext;
import java.util.Arrays;
import java.util.List;

public class LogEventParcelable
  implements SafeParcelable
{
  public static final LogEventParcelableCreator CREATOR = new LogEventParcelableCreator();
  public final ClearcutLogger.MessageProducer clientVisualElementsProducer;
  public final ClearcutLogger.MessageProducer extensionProducer;
  public final zzaip.zzd logEvent;
  public byte[] logEventBytes;
  public PlayLoggerContext playLoggerContext;
  public int[] testCodes;
  public final int versionCode;
  
  LogEventParcelable(int paramInt, PlayLoggerContext paramPlayLoggerContext, byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    this.versionCode = paramInt;
    this.playLoggerContext = paramPlayLoggerContext;
    this.logEventBytes = paramArrayOfByte;
    this.testCodes = paramArrayOfInt;
    this.logEvent = null;
    this.extensionProducer = null;
    this.clientVisualElementsProducer = null;
  }
  
  public LogEventParcelable(PlayLoggerContext paramPlayLoggerContext, zzaip.zzd paramzzd, ClearcutLogger.MessageProducer paramMessageProducer1, ClearcutLogger.MessageProducer paramMessageProducer2, int[] paramArrayOfInt)
  {
    this.versionCode = 1;
    this.playLoggerContext = paramPlayLoggerContext;
    this.logEvent = paramzzd;
    this.extensionProducer = paramMessageProducer1;
    this.clientVisualElementsProducer = paramMessageProducer2;
    this.testCodes = paramArrayOfInt;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    LogEventParcelable localLogEventParcelable;
    do
    {
      return true;
      if (!(paramObject instanceof LogEventParcelable)) {
        break;
      }
      localLogEventParcelable = (LogEventParcelable)paramObject;
    } while ((this.versionCode == localLogEventParcelable.versionCode) && (zzw.equal(this.playLoggerContext, localLogEventParcelable.playLoggerContext)) && (Arrays.equals(this.logEventBytes, localLogEventParcelable.logEventBytes)) && (Arrays.equals(this.testCodes, localLogEventParcelable.testCodes)) && (zzw.equal(this.logEvent, localLogEventParcelable.logEvent)) && (zzw.equal(this.extensionProducer, localLogEventParcelable.extensionProducer)) && (zzw.equal(this.clientVisualElementsProducer, localLogEventParcelable.clientVisualElementsProducer)));
    return false;
    return false;
  }
  
  public int hashCode()
  {
    Object[] arrayOfObject = new Object[7];
    arrayOfObject[0] = Integer.valueOf(this.versionCode);
    arrayOfObject[1] = this.playLoggerContext;
    arrayOfObject[2] = this.logEventBytes;
    arrayOfObject[3] = this.testCodes;
    arrayOfObject[4] = this.logEvent;
    arrayOfObject[5] = this.extensionProducer;
    arrayOfObject[6] = this.clientVisualElementsProducer;
    return Arrays.hashCode(arrayOfObject);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("LogEventParcelable[");
    localStringBuilder.append(this.versionCode);
    localStringBuilder.append(", ");
    localStringBuilder.append(this.playLoggerContext);
    localStringBuilder.append(", ");
    String str1;
    String str2;
    if (this.logEventBytes == null)
    {
      str1 = null;
      localStringBuilder.append(str1);
      localStringBuilder.append(", ");
      int[] arrayOfInt = this.testCodes;
      str2 = null;
      if (arrayOfInt != null) {
        break label163;
      }
    }
    for (;;)
    {
      localStringBuilder.append(str2);
      localStringBuilder.append(", ");
      localStringBuilder.append(this.logEvent);
      localStringBuilder.append(", ");
      localStringBuilder.append(this.extensionProducer);
      localStringBuilder.append(", ");
      localStringBuilder.append(this.clientVisualElementsProducer);
      localStringBuilder.append("]");
      return localStringBuilder.toString();
      str1 = new String(this.logEventBytes);
      break;
      label163:
      zzv localzzv = new zzv(", ");
      int[][] arrayOfInt1 = new int[1][];
      arrayOfInt1[0] = this.testCodes;
      List localList = Arrays.asList(arrayOfInt1);
      str2 = localzzv.zza(new StringBuilder(), localList).toString();
    }
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    LogEventParcelableCreator.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.clearcut.LogEventParcelable
 * JD-Core Version:    0.7.0.1
 */