package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

public class RadioStationInfo
  implements SafeParcelable
{
  public static final Parcelable.Creator<RadioStationInfo> CREATOR = new zzbj();
  public int channel;
  final int mVersionCode;
  public MetaData metaData;
  public int subChannel;
  public int type;
  
  public RadioStationInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4, MetaData paramMetaData)
  {
    this.mVersionCode = paramInt1;
    this.type = paramInt2;
    this.channel = paramInt3;
    this.subChannel = paramInt4;
    this.metaData = paramMetaData;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzbj.zza(this, paramParcel, paramInt);
  }
  
  public static class HdData
    implements SafeParcelable
  {
    public static final Parcelable.Creator<HdData> CREATOR = new zzbk();
    final int mVersionCode;
    
    public HdData()
    {
      this(1);
    }
    
    public HdData(int paramInt)
    {
      this.mVersionCode = paramInt;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      zzbk.zza$71826f3c(this, paramParcel);
    }
  }
  
  public static class MetaData
    implements SafeParcelable
  {
    public static final Parcelable.Creator<MetaData> CREATOR = new zzbl();
    public int audioChannels;
    public RadioStationInfo.HdData hdData;
    final int mVersionCode;
    public RadioStationInfo.RdsData rdsData;
    public int signalQuality;
    
    public MetaData(int paramInt1, int paramInt2, int paramInt3, RadioStationInfo.RdsData paramRdsData, RadioStationInfo.HdData paramHdData)
    {
      this.mVersionCode = paramInt1;
      this.audioChannels = paramInt2;
      this.signalQuality = paramInt3;
      this.rdsData = paramRdsData;
      this.hdData = paramHdData;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      zzbl.zza(this, paramParcel, paramInt);
    }
  }
  
  public static class RdsData
    implements SafeParcelable
  {
    public static final Parcelable.Creator<RdsData> CREATOR = new zzbm();
    public List<Integer> alternativeFrequencies;
    final int mVersionCode;
    public int musicSpeechSwitch;
    public int programId;
    public String programServiceName;
    public int programType;
    public String programTypeName;
    public String radioText;
    public boolean trafficAnnouncementFlag;
    public boolean trafficProgramFlag;
    
    public RdsData(int paramInt1, List<Integer> paramList, int paramInt2, int paramInt3, String paramString1, int paramInt4, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2)
    {
      this.mVersionCode = paramInt1;
      this.alternativeFrequencies = paramList;
      this.programId = paramInt2;
      this.musicSpeechSwitch = paramInt3;
      this.programServiceName = paramString1;
      this.programType = paramInt4;
      this.programTypeName = paramString2;
      this.radioText = paramString3;
      this.trafficProgramFlag = paramBoolean1;
      this.trafficAnnouncementFlag = paramBoolean2;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      zzbm.zza$3345f68f(this, paramParcel);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.RadioStationInfo
 * JD-Core Version:    0.7.0.1
 */