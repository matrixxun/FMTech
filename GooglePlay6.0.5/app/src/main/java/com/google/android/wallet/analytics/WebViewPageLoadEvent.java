package com.google.android.wallet.analytics;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class WebViewPageLoadEvent
  implements Parcelable
{
  public static final Parcelable.Creator<WebViewPageLoadEvent> CREATOR = new Parcelable.Creator() {};
  public int errorCode;
  public String errorDescription = "";
  public int orientation;
  public int screenHeightPx;
  public int screenWidthPx;
  public float screenXDpi;
  public float screenYDpi;
  public long startTimestampMs;
  public String url;
  
  public WebViewPageLoadEvent() {}
  
  private WebViewPageLoadEvent(Parcel paramParcel)
  {
    this.url = paramParcel.readString();
    this.startTimestampMs = paramParcel.readLong();
    this.errorCode = paramParcel.readInt();
    this.errorDescription = paramParcel.readString();
    this.orientation = paramParcel.readInt();
    this.screenWidthPx = paramParcel.readInt();
    this.screenHeightPx = paramParcel.readInt();
    this.screenXDpi = paramParcel.readFloat();
    this.screenYDpi = paramParcel.readFloat();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(256);
    localStringBuilder.append("url: ").append(this.url).append("\nstartTimestampMs: ").append(this.startTimestampMs).append("\nerrorCode: ").append(this.errorCode).append("\nerrorDescription: ").append(this.errorDescription).append("\norientation: ").append(this.orientation).append("\nscreenWidthPx: ").append(this.screenWidthPx).append("\nscreenHeightPx: ").append(this.screenHeightPx).append("\nscreenXDpi: ").append(this.screenXDpi).append("\nscreenYDpi: ").append(this.screenYDpi);
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.url);
    paramParcel.writeLong(this.startTimestampMs);
    paramParcel.writeInt(this.errorCode);
    paramParcel.writeString(this.errorDescription);
    paramParcel.writeInt(this.orientation);
    paramParcel.writeInt(this.screenWidthPx);
    paramParcel.writeInt(this.screenHeightPx);
    paramParcel.writeFloat(this.screenXDpi);
    paramParcel.writeFloat(this.screenYDpi);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.analytics.WebViewPageLoadEvent
 * JD-Core Version:    0.7.0.1
 */