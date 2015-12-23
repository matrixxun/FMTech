package com.google.android.gms.playlog.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzx;
import java.util.Arrays;

public class PlayLoggerContext
  implements SafeParcelable
{
  public static final PlayLoggerContextCreator CREATOR = new PlayLoggerContextCreator();
  public final boolean isAnonymous;
  public final boolean logAndroidId;
  public final int logSource;
  public final String logSourceName;
  public final String loggingId;
  public final String packageName;
  public final int packageVersionCode;
  public final int qosTier;
  public final String uploadAccountName;
  public final int versionCode;
  
  public PlayLoggerContext(int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2, String paramString3, boolean paramBoolean1, String paramString4, boolean paramBoolean2, int paramInt4)
  {
    this.versionCode = paramInt1;
    this.packageName = paramString1;
    this.packageVersionCode = paramInt2;
    this.logSource = paramInt3;
    this.uploadAccountName = paramString2;
    this.loggingId = paramString3;
    this.logAndroidId = paramBoolean1;
    this.logSourceName = paramString4;
    this.isAnonymous = paramBoolean2;
    this.qosTier = paramInt4;
  }
  
  public PlayLoggerContext(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, String paramString4, boolean paramBoolean, int paramInt3)
  {
    this.versionCode = 1;
    this.packageName = ((String)zzx.zzC(paramString1));
    this.packageVersionCode = paramInt1;
    this.logSource = paramInt2;
    this.logSourceName = paramString2;
    this.uploadAccountName = paramString3;
    this.loggingId = paramString4;
    if (!paramBoolean) {}
    for (boolean bool = true;; bool = false)
    {
      this.logAndroidId = bool;
      this.isAnonymous = paramBoolean;
      this.qosTier = paramInt3;
      return;
    }
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    PlayLoggerContext localPlayLoggerContext;
    do
    {
      return true;
      if (!(paramObject instanceof PlayLoggerContext)) {
        break;
      }
      localPlayLoggerContext = (PlayLoggerContext)paramObject;
    } while ((this.versionCode == localPlayLoggerContext.versionCode) && (this.packageName.equals(localPlayLoggerContext.packageName)) && (this.packageVersionCode == localPlayLoggerContext.packageVersionCode) && (this.logSource == localPlayLoggerContext.logSource) && (zzw.equal(this.logSourceName, localPlayLoggerContext.logSourceName)) && (zzw.equal(this.uploadAccountName, localPlayLoggerContext.uploadAccountName)) && (zzw.equal(this.loggingId, localPlayLoggerContext.loggingId)) && (this.logAndroidId == localPlayLoggerContext.logAndroidId) && (this.isAnonymous == localPlayLoggerContext.isAnonymous) && (this.qosTier == localPlayLoggerContext.qosTier));
    return false;
    return false;
  }
  
  public int hashCode()
  {
    Object[] arrayOfObject = new Object[10];
    arrayOfObject[0] = Integer.valueOf(this.versionCode);
    arrayOfObject[1] = this.packageName;
    arrayOfObject[2] = Integer.valueOf(this.packageVersionCode);
    arrayOfObject[3] = Integer.valueOf(this.logSource);
    arrayOfObject[4] = this.logSourceName;
    arrayOfObject[5] = this.uploadAccountName;
    arrayOfObject[6] = this.loggingId;
    arrayOfObject[7] = Boolean.valueOf(this.logAndroidId);
    arrayOfObject[8] = Boolean.valueOf(this.isAnonymous);
    arrayOfObject[9] = Integer.valueOf(this.qosTier);
    return Arrays.hashCode(arrayOfObject);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("PlayLoggerContext[");
    localStringBuilder.append("versionCode=").append(this.versionCode).append(',');
    localStringBuilder.append("package=").append(this.packageName).append(',');
    localStringBuilder.append("packageVersionCode=").append(this.packageVersionCode).append(',');
    localStringBuilder.append("logSource=").append(this.logSource).append(',');
    localStringBuilder.append("logSourceName=").append(this.logSourceName).append(',');
    localStringBuilder.append("uploadAccount=").append(this.uploadAccountName).append(',');
    localStringBuilder.append("loggingId=").append(this.loggingId).append(',');
    localStringBuilder.append("logAndroidId=").append(this.logAndroidId).append(',');
    localStringBuilder.append("isAnonymous=").append(this.isAnonymous).append(',');
    localStringBuilder.append("qosTier=").append(this.qosTier);
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    PlayLoggerContextCreator.zza$495264e0(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.playlog.internal.PlayLoggerContext
 * JD-Core Version:    0.7.0.1
 */