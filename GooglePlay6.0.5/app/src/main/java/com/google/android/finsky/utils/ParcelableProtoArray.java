package com.google.android.finsky.utils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.protobuf.nano.MessageNano;
import java.lang.reflect.Array;

public class ParcelableProtoArray<T extends MessageNano>
  implements Parcelable
{
  public static final Parcelable.Creator<ParcelableProtoArray<MessageNano>> CREATOR = new Parcelable.Creator()
  {
    private static ParcelableProtoArray<MessageNano> createFromParcel(Parcel paramAnonymousParcel)
    {
      try
      {
        ParcelableProtoArray localParcelableProtoArray = new ParcelableProtoArray(paramAnonymousParcel, (byte)0);
        return localParcelableProtoArray;
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        throw new IllegalArgumentException("Exception when unmarshalling proto array", localClassNotFoundException);
      }
    }
  };
  private final T[] mPayload;
  
  private ParcelableProtoArray(Parcel paramParcel)
    throws ClassNotFoundException
  {
    int i = paramParcel.readInt();
    if (i == -1)
    {
      this.mPayload = null;
      return;
    }
    MessageNano[] arrayOfMessageNano = (MessageNano[])Array.newInstance(Class.forName(paramParcel.readString()), i);
    for (int j = 0; j < i; j++) {
      arrayOfMessageNano[j] = ParcelableProto.getProtoFromParcel(paramParcel);
    }
    this.mPayload = arrayOfMessageNano;
  }
  
  private ParcelableProtoArray(T[] paramArrayOfT)
  {
    this.mPayload = paramArrayOfT;
  }
  
  public static <T extends MessageNano> ParcelableProtoArray<T> forProtoArray(T[] paramArrayOfT)
  {
    return new ParcelableProtoArray(paramArrayOfT);
  }
  
  public static <T extends MessageNano> T[] getProtoArrayFromBundle(Bundle paramBundle, String paramString)
  {
    ParcelableProtoArray localParcelableProtoArray = (ParcelableProtoArray)paramBundle.getParcelable(paramString);
    if (localParcelableProtoArray != null) {
      return localParcelableProtoArray.mPayload;
    }
    return null;
  }
  
  public static <T extends MessageNano> T[] getProtoArrayFromIntent(Intent paramIntent, String paramString)
  {
    ParcelableProtoArray localParcelableProtoArray = (ParcelableProtoArray)paramIntent.getParcelableExtra(paramString);
    if (localParcelableProtoArray != null) {
      return localParcelableProtoArray.mPayload;
    }
    return null;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    if (this.mPayload == null) {
      paramParcel.writeInt(-1);
    }
    for (;;)
    {
      return;
      paramParcel.writeInt(this.mPayload.length);
      paramParcel.writeString(this.mPayload.getClass().getComponentType().getName());
      for (int i = 0; i < this.mPayload.length; i++) {
        paramParcel.writeParcelable(ParcelableProto.forProto(this.mPayload[i]), paramInt);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.ParcelableProtoArray
 * JD-Core Version:    0.7.0.1
 */