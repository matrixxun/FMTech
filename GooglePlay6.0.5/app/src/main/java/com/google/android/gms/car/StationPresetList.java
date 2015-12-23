package com.google.android.gms.car;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

public class StationPresetList
  implements SafeParcelable
{
  public static final Parcelable.Creator<StationPresetList> CREATOR = new zzbo();
  final int mVersionCode;
  public String name;
  public List<StationPreset> presets;
  public List<Integer> restrictedStationTypes;
  
  public StationPresetList(int paramInt, String paramString, List<Integer> paramList, List<StationPreset> paramList1)
  {
    this.mVersionCode = paramInt;
    this.name = paramString;
    this.restrictedStationTypes = paramList;
    this.presets = paramList1;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzbo.zza$740924ee(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.StationPresetList
 * JD-Core Version:    0.7.0.1
 */