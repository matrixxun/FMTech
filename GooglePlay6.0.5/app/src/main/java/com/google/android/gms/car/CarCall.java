package com.google.android.gms.car;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

public class CarCall
  implements SafeParcelable
{
  public static final Parcelable.Creator<CarCall> CREATOR = new zzd();
  public List<String> cannedTextResponses;
  public Details details;
  public boolean hasChildren;
  public final int id;
  final int mVersionCode;
  public CarCall parent;
  public String remainingPostDialSequence;
  public int state;
  
  public CarCall(int paramInt1, int paramInt2, CarCall paramCarCall, List<String> paramList, String paramString, int paramInt3, Details paramDetails, boolean paramBoolean)
  {
    this.mVersionCode = paramInt1;
    this.id = paramInt2;
    this.parent = paramCarCall;
    this.cannedTextResponses = paramList;
    this.remainingPostDialSequence = paramString;
    this.state = paramInt3;
    this.details = paramDetails;
    this.hasChildren = paramBoolean;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    return ((paramObject instanceof CarCall)) && (this.id == ((CarCall)paramObject).id);
  }
  
  public int hashCode()
  {
    return this.id;
  }
  
  public String toString()
  {
    return "CarCall{id=" + this.id + ", parent=" + this.parent + ", cannedTextResponses=" + this.cannedTextResponses + ", remainingPostDialSequence='" + this.remainingPostDialSequence + '\'' + ", state=" + this.state + ", details=" + this.details + ", hasChildren=" + this.hasChildren + '}';
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzd.zza(this, paramParcel, paramInt);
  }
  
  public static final class Details
    implements SafeParcelable
  {
    public static final Parcelable.Creator<Details> CREATOR = new zze();
    public String callerDisplayName;
    public long connectTimeMillis;
    public String disconnectCause;
    public Uri gatewayInfoGatewayAddress;
    public Uri gatewayInfoOriginalAddress;
    public Uri handle;
    final int mVersionCode;
    
    public Details(int paramInt, Uri paramUri1, String paramString1, String paramString2, long paramLong, Uri paramUri2, Uri paramUri3)
    {
      this.mVersionCode = paramInt;
      this.handle = paramUri1;
      this.callerDisplayName = paramString1;
      this.disconnectCause = paramString2;
      this.connectTimeMillis = paramLong;
      this.gatewayInfoOriginalAddress = paramUri2;
      this.gatewayInfoGatewayAddress = paramUri3;
    }
    
    public final int describeContents()
    {
      return 0;
    }
    
    public final String toString()
    {
      return "Details{handle=" + this.handle + ", callerDisplayName='" + this.callerDisplayName + '\'' + ", disconnectCause='" + this.disconnectCause + '\'' + ", connectTimeMillis=" + this.connectTimeMillis + ", gatewayInfoOriginal=" + this.gatewayInfoOriginalAddress + ", gatewayInfoGateway=" + this.gatewayInfoGatewayAddress + '}';
    }
    
    public final void writeToParcel(Parcel paramParcel, int paramInt)
    {
      zze.zza(this, paramParcel, paramInt);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.CarCall
 * JD-Core Version:    0.7.0.1
 */