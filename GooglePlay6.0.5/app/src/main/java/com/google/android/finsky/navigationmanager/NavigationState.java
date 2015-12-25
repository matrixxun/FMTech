package com.google.android.finsky.navigationmanager;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class NavigationState
  implements Parcelable
{
  public static final Parcelable.Creator<NavigationState> CREATOR = new Parcelable.Creator() {};
  public final String backstackName;
  public boolean canTriggerSearchSurvey;
  public int drawerIconStateSwitchType;
  public boolean isActionBarOverlayEnabled;
  public boolean isStatusBarOverlayEnabled;
  public final int pageType;
  public final String url;
  
  NavigationState(int paramInt, String paramString1, String url)
  {
    this.pageType = paramInt;
    if (paramString1 == null) {
      paramString1 = Integer.toString((int)(2147483646.0D * Math.random()));
    }
    this.backstackName = paramString1;
    this.url = url;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String toString()
  {
    return "[type: " + this.pageType + ", name: " + this.backstackName + "]";
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int i = 1;
    paramParcel.writeInt(this.pageType);
    paramParcel.writeString(this.backstackName);
    paramParcel.writeString(this.url);
    int j;
    int k;
    if (this.isActionBarOverlayEnabled)
    {
      j = i;
      paramParcel.writeByte((byte)j);
      if (!this.canTriggerSearchSurvey) {
        break label88;
      }
      k = i;
      label53:
      paramParcel.writeByte((byte)k);
      if (!this.isStatusBarOverlayEnabled) {
        break label94;
      }
    }
    for (;;)
    {
      paramParcel.writeByte((byte)i);
      paramParcel.writeInt(this.drawerIconStateSwitchType);
      return;
      j = 0;
      break;
      label88:
      k = 0;
      break label53;
      label94:
      i = 0;
    }
  }
}



/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar

 * Qualified Name:     com.google.android.finsky.navigationmanager.NavigationState

 * JD-Core Version:    0.7.0.1

 */