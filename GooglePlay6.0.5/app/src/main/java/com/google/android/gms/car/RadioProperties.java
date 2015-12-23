package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

public class RadioProperties
  implements SafeParcelable
{
  public static final Parcelable.Creator<RadioProperties> CREATOR = new zzbh();
  public boolean alternativeFrequencySwitch;
  public boolean audioLoopback;
  public boolean backgroundTuner;
  public List<ChannelRange> channelRangeList;
  public int channelSpacing;
  public List<Integer> channelSpacingList;
  final int mVersionCode;
  public int radioId;
  public boolean radioOnlyMuteCapability;
  public int radioType;
  public int rds;
  public int region;
  public int stationPresetsAccess;
  public boolean trafficAnnouncement;
  public int trafficService;
  
  public RadioProperties(int paramInt1, int paramInt2, int paramInt3, List<ChannelRange> paramList, List<Integer> paramList1, int paramInt4, boolean paramBoolean1, int paramInt5, int paramInt6, boolean paramBoolean2, boolean paramBoolean3, int paramInt7, boolean paramBoolean4, boolean paramBoolean5, int paramInt8)
  {
    this.mVersionCode = paramInt1;
    this.radioId = paramInt2;
    this.radioType = paramInt3;
    this.channelRangeList = paramList;
    this.channelSpacingList = paramList1;
    this.channelSpacing = paramInt4;
    this.backgroundTuner = paramBoolean1;
    this.region = paramInt5;
    this.rds = paramInt6;
    this.alternativeFrequencySwitch = paramBoolean2;
    this.trafficAnnouncement = paramBoolean3;
    this.trafficService = paramInt7;
    this.audioLoopback = paramBoolean4;
    this.radioOnlyMuteCapability = paramBoolean5;
    this.stationPresetsAccess = paramInt8;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzbh.zza$7bbcad71(this, paramParcel);
  }
  
  public static class ChannelRange
    implements SafeParcelable
  {
    public static final Parcelable.Creator<ChannelRange> CREATOR = new zzv();
    final int mVersionCode;
    public int max;
    public int min;
    
    public ChannelRange(int paramInt1, int paramInt2, int paramInt3)
    {
      this.mVersionCode = paramInt1;
      this.min = paramInt2;
      this.max = paramInt3;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      zzv.zza$40752d93(this, paramParcel);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.RadioProperties
 * JD-Core Version:    0.7.0.1
 */