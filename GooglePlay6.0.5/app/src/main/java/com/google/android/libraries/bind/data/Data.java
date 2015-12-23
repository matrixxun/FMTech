package com.google.android.libraries.bind.data;

import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.SparseArray;
import com.google.android.libraries.bind.util.Util;

public final class Data
  implements Parcelable
{
  public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator() {};
  boolean frozen;
  SparseArray<Object> values;
  
  public Data()
  {
    this((byte)0);
  }
  
  private Data(byte paramByte)
  {
    this.values = new SparseArray(8);
  }
  
  public Data(SparseArray<Object> paramSparseArray)
  {
    if (Build.VERSION.SDK_INT >= 14)
    {
      this.values = paramSparseArray.clone();
      return;
    }
    this.values = new SparseArray(paramSparseArray.size());
    putAll(paramSparseArray);
  }
  
  public static String keyName(int paramInt)
  {
    return Util.getResourceName(paramInt);
  }
  
  private void putAll(SparseArray<Object> paramSparseArray)
  {
    for (int i = 0; i < paramSparseArray.size(); i++) {
      this.values.put(paramSparseArray.keyAt(i), paramSparseArray.valueAt(i));
    }
  }
  
  public final boolean containsKey(int paramInt)
  {
    return this.values.get(paramInt) != null;
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Data)) {
      return false;
    }
    return this.values.equals(((Data)paramObject).values);
  }
  
  public final <T> T get(int paramInt)
  {
    Object localObject = this.values.get(paramInt);
    if ((localObject instanceof DataProperty)) {
      localObject = ((DataProperty)localObject).apply$34b94061();
    }
    return localObject;
  }
  
  public final Integer getAsInteger(int paramInt)
  {
    Object localObject = get(paramInt);
    if (localObject != null) {
      return Integer.valueOf(((Number)localObject).intValue());
    }
    return null;
  }
  
  public final int hashCode()
  {
    return this.values.hashCode();
  }
  
  public final String toString()
  {
    if (this.values.size() == 0) {
      return "Data is empty";
    }
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = 0; i < this.values.size(); i++)
    {
      int j = this.values.keyAt(i);
      Object localObject = this.values.valueAt(i);
      if (localStringBuilder.length() > 0) {
        localStringBuilder.append(" ");
      }
      localStringBuilder.append(Util.getResourceName(j) + "=" + localObject);
    }
    return localStringBuilder.toString();
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeSparseArray(this.values);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.data.Data
 * JD-Core Version:    0.7.0.1
 */