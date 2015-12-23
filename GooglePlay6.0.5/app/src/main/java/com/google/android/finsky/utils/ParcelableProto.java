package com.google.android.finsky.utils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.lang.reflect.Constructor;

public class ParcelableProto<T extends MessageNano>
  implements Parcelable
{
  public static final Parcelable.Creator<ParcelableProto<MessageNano>> CREATOR = new Parcelable.Creator()
  {
    private static ParcelableProto<MessageNano> createFromParcel(Parcel paramAnonymousParcel)
    {
      int i = paramAnonymousParcel.readInt();
      if (i == -1) {
        return new ParcelableProto(null, (byte)0);
      }
      byte[] arrayOfByte = new byte[i];
      paramAnonymousParcel.readByteArray(arrayOfByte);
      String str = paramAnonymousParcel.readString();
      try
      {
        MessageNano localMessageNano = (MessageNano)Class.forName(str).getConstructor(null).newInstance(null);
        localMessageNano.mergeFrom(CodedInputByteBufferNano.newInstance(arrayOfByte, 0, i));
        ParcelableProto localParcelableProto = new ParcelableProto(localMessageNano, (byte)0);
        return localParcelableProto;
      }
      catch (Exception localException)
      {
        throw new IllegalArgumentException("Exception when unmarshalling: " + str, localException);
      }
    }
  };
  public final T mPayload;
  private byte[] mSerialized = null;
  
  private ParcelableProto(T paramT)
  {
    this.mPayload = paramT;
  }
  
  public static <T extends MessageNano> ParcelableProto<T> forProto(T paramT)
  {
    return new ParcelableProto(paramT);
  }
  
  public static <T extends MessageNano> T getProtoFromBundle(Bundle paramBundle, String paramString)
  {
    ParcelableProto localParcelableProto = (ParcelableProto)paramBundle.getParcelable(paramString);
    if (localParcelableProto != null) {
      return localParcelableProto.mPayload;
    }
    return null;
  }
  
  public static <T extends MessageNano> T getProtoFromIntent(Intent paramIntent, String paramString)
  {
    ParcelableProto localParcelableProto = (ParcelableProto)paramIntent.getParcelableExtra(paramString);
    if (localParcelableProto != null) {
      return localParcelableProto.mPayload;
    }
    return null;
  }
  
  public static <T extends MessageNano> T getProtoFromParcel(Parcel paramParcel)
  {
    ParcelableProto localParcelableProto = (ParcelableProto)paramParcel.readParcelable(ParcelableProto.class.getClassLoader());
    if (localParcelableProto != null) {
      return localParcelableProto.mPayload;
    }
    return null;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    if (this.mPayload == null)
    {
      paramParcel.writeInt(-1);
      return;
    }
    if (this.mSerialized == null) {
      this.mSerialized = MessageNano.toByteArray(this.mPayload);
    }
    paramParcel.writeInt(this.mSerialized.length);
    paramParcel.writeByteArray(this.mSerialized);
    paramParcel.writeString(this.mPayload.getClass().getName());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.ParcelableProto
 * JD-Core Version:    0.7.0.1
 */