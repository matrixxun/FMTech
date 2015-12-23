package com.google.android.gms.wearable;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.internal.zzahi;
import com.google.android.gms.internal.zzahi.zza;
import com.google.android.gms.internal.zzahj;
import com.google.android.gms.internal.zzaij;
import com.google.android.gms.internal.zzaik;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public final class DataMap
{
  public final HashMap<String, Object> zzoF = new HashMap();
  
  public static DataMap fromByteArray(byte[] paramArrayOfByte)
  {
    try
    {
      DataMap localDataMap = zzahi.zza(new zzahi.zza((zzahj)zzaik.mergeFrom$44fe8ab2(new zzahj(), paramArrayOfByte, paramArrayOfByte.length), new ArrayList()));
      return localDataMap;
    }
    catch (zzaij localzzaij)
    {
      throw new IllegalArgumentException("Unable to convert data", localzzaij);
    }
  }
  
  private static void zza(String paramString1, Object paramObject, String paramString2, ClassCastException paramClassCastException)
  {
    zza(paramString1, paramObject, paramString2, "<null>", paramClassCastException);
  }
  
  private static void zza(String paramString1, Object paramObject1, String paramString2, Object paramObject2, ClassCastException paramClassCastException)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Key ");
    localStringBuilder.append(paramString1);
    localStringBuilder.append(" expected ");
    localStringBuilder.append(paramString2);
    localStringBuilder.append(" but value was a ");
    localStringBuilder.append(paramObject1.getClass().getName());
    localStringBuilder.append(".  The default value ");
    localStringBuilder.append(paramObject2);
    localStringBuilder.append(" was returned.");
    Log.w("DataMap", localStringBuilder.toString());
    Log.w("DataMap", "Attempt to cast generated internal exception:", paramClassCastException);
  }
  
  public final boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof DataMap)) {
      return false;
    }
    DataMap localDataMap = (DataMap)paramObject;
    if (this.zzoF.size() != localDataMap.zzoF.size()) {
      return false;
    }
    Iterator localIterator = this.zzoF.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject1 = get(str);
      Object localObject2 = localDataMap.get(str);
      if ((localObject1 instanceof Asset))
      {
        if (!(localObject2 instanceof Asset)) {
          return false;
        }
        Asset localAsset1 = (Asset)localObject1;
        Asset localAsset2 = (Asset)localObject2;
        boolean bool;
        if ((localAsset1 == null) || (localAsset2 == null)) {
          if (localAsset1 == localAsset2) {
            bool = true;
          }
        }
        while (!bool)
        {
          return false;
          bool = false;
          continue;
          if (!TextUtils.isEmpty(localAsset1.zzcem)) {
            bool = localAsset1.zzcem.equals(localAsset2.zzcem);
          } else {
            bool = Arrays.equals(localAsset1.mData, localAsset2.mData);
          }
        }
      }
      else if ((localObject1 instanceof String[]))
      {
        if (!(localObject2 instanceof String[])) {
          return false;
        }
        if (!Arrays.equals((String[])localObject1, (String[])localObject2)) {
          return false;
        }
      }
      else if ((localObject1 instanceof long[]))
      {
        if (!(localObject2 instanceof long[])) {
          return false;
        }
        if (!Arrays.equals((long[])localObject1, (long[])localObject2)) {
          return false;
        }
      }
      else if ((localObject1 instanceof float[]))
      {
        if (!(localObject2 instanceof float[])) {
          return false;
        }
        if (!Arrays.equals((float[])localObject1, (float[])localObject2)) {
          return false;
        }
      }
      else if ((localObject1 instanceof byte[]))
      {
        if (!(localObject2 instanceof byte[])) {
          return false;
        }
        if (!Arrays.equals((byte[])localObject1, (byte[])localObject2)) {
          return false;
        }
      }
      else if ((localObject1 == null) || (localObject2 == null))
      {
        if (localObject1 != localObject2) {
          return false;
        }
      }
      else if (!localObject1.equals(localObject2))
      {
        return false;
      }
    }
    return true;
  }
  
  public final <T> T get(String paramString)
  {
    return this.zzoF.get(paramString);
  }
  
  public final boolean getBoolean$505cbf47(String paramString)
  {
    Object localObject = this.zzoF.get(paramString);
    if (localObject == null) {
      return false;
    }
    try
    {
      boolean bool = ((Boolean)localObject).booleanValue();
      return bool;
    }
    catch (ClassCastException localClassCastException)
    {
      zza(paramString, localObject, "Boolean", Boolean.valueOf(false), localClassCastException);
    }
    return false;
  }
  
  public final byte[] getByteArray(String paramString)
  {
    Object localObject = this.zzoF.get(paramString);
    if (localObject == null) {
      return null;
    }
    try
    {
      byte[] arrayOfByte = (byte[])localObject;
      return arrayOfByte;
    }
    catch (ClassCastException localClassCastException)
    {
      zza(paramString, localObject, "byte[]", localClassCastException);
    }
    return null;
  }
  
  public final ArrayList<DataMap> getDataMapArrayList(String paramString)
  {
    Object localObject = this.zzoF.get(paramString);
    if (localObject == null) {
      return null;
    }
    try
    {
      ArrayList localArrayList = (ArrayList)localObject;
      return localArrayList;
    }
    catch (ClassCastException localClassCastException)
    {
      zza(paramString, localObject, "ArrayList<DataMap>", localClassCastException);
    }
    return null;
  }
  
  public final int getInt$505cff29(String paramString)
  {
    Object localObject = this.zzoF.get(paramString);
    if (localObject == null) {
      return 0;
    }
    try
    {
      int i = ((Integer)localObject).intValue();
      return i;
    }
    catch (ClassCastException localClassCastException)
    {
      zza(paramString, localObject, "Integer", localClassCastException);
    }
    return 0;
  }
  
  public final String getString(String paramString)
  {
    Object localObject = this.zzoF.get(paramString);
    if (localObject == null) {
      return null;
    }
    try
    {
      String str = (String)localObject;
      return str;
    }
    catch (ClassCastException localClassCastException)
    {
      zza(paramString, localObject, "String", localClassCastException);
    }
    return null;
  }
  
  public final String[] getStringArray(String paramString)
  {
    Object localObject = this.zzoF.get(paramString);
    if (localObject == null) {
      return null;
    }
    try
    {
      String[] arrayOfString = (String[])localObject;
      return arrayOfString;
    }
    catch (ClassCastException localClassCastException)
    {
      zza(paramString, localObject, "String[]", localClassCastException);
    }
    return null;
  }
  
  public final int hashCode()
  {
    return 29 * this.zzoF.hashCode();
  }
  
  public final void putAsset(String paramString, Asset paramAsset)
  {
    this.zzoF.put(paramString, paramAsset);
  }
  
  public final void putLong(String paramString, long paramLong)
  {
    this.zzoF.put(paramString, Long.valueOf(paramLong));
  }
  
  public final void putString(String paramString1, String paramString2)
  {
    this.zzoF.put(paramString1, paramString2);
  }
  
  public final void putStringArray(String paramString, String[] paramArrayOfString)
  {
    this.zzoF.put(paramString, paramArrayOfString);
  }
  
  public final void putStringArrayList(String paramString, ArrayList<String> paramArrayList)
  {
    this.zzoF.put(paramString, paramArrayList);
  }
  
  public final String toString()
  {
    return this.zzoF.toString();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.DataMap
 * JD-Core Version:    0.7.0.1
 */