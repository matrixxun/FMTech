package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

public class RadioState
  implements SafeParcelable
{
  public static final Parcelable.Creator<RadioState> CREATOR = new zzbi();
  public int activeRadioId;
  final int mVersionCode;
  public List<RadioStationInfo> programList;
  public boolean radioMuted;
  public boolean radioSourceEnabled;
  public RadioStationInfo stationInfo;
  public List<StationPresetList> stationPresetLists;
  
  public RadioState(int paramInt1, boolean paramBoolean1, boolean paramBoolean2, int paramInt2, RadioStationInfo paramRadioStationInfo, List<RadioStationInfo> paramList, List<StationPresetList> paramList1)
  {
    this.mVersionCode = paramInt1;
    this.radioSourceEnabled = paramBoolean1;
    this.radioMuted = paramBoolean2;
    this.activeRadioId = paramInt2;
    this.stationInfo = paramRadioStationInfo;
    this.programList = paramList;
    this.stationPresetLists = paramList1;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzbi.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.RadioState
 * JD-Core Version:    0.7.0.1
 */