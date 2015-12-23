package com.google.android.gms.internal;

import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public final class zzahi
{
  private static int zza(String paramString, zzahj.zza.zza[] paramArrayOfzza)
  {
    int i = paramArrayOfzza.length;
    int j = 0;
    int k = 14;
    if (j < i)
    {
      zzahj.zza.zza localzza = paramArrayOfzza[j];
      if (k == 14) {
        if ((localzza.type == 9) || (localzza.type == 2) || (localzza.type == 6)) {
          k = localzza.type;
        }
      }
      while (localzza.type == k)
      {
        do
        {
          j++;
          break;
        } while (localzza.type == 14);
        throw new IllegalArgumentException("Unexpected TypedValue type: " + localzza.type + " for key " + paramString);
      }
      throw new IllegalArgumentException("The ArrayList elements should all be the same type, but ArrayList with key " + paramString + " contains items of type " + k + " and " + localzza.type);
    }
    return k;
  }
  
  private static zzahj.zza.zza zza(List<Asset> paramList, Object paramObject)
  {
    zzahj.zza.zza localzza1 = new zzahj.zza.zza();
    if (paramObject == null)
    {
      localzza1.type = 14;
      return localzza1;
    }
    localzza1.zzcia = new zzahj.zza.zza.zza();
    if ((paramObject instanceof String))
    {
      localzza1.type = 2;
      localzza1.zzcia.zzcic = ((String)paramObject);
    }
    for (;;)
    {
      return localzza1;
      if ((paramObject instanceof Integer))
      {
        localzza1.type = 6;
        localzza1.zzcia.zzcig = ((Integer)paramObject).intValue();
      }
      else if ((paramObject instanceof Long))
      {
        localzza1.type = 5;
        localzza1.zzcia.zzcif = ((Long)paramObject).longValue();
      }
      else if ((paramObject instanceof Double))
      {
        localzza1.type = 3;
        localzza1.zzcia.zzcid = ((Double)paramObject).doubleValue();
      }
      else if ((paramObject instanceof Float))
      {
        localzza1.type = 4;
        localzza1.zzcia.zzcie = ((Float)paramObject).floatValue();
      }
      else if ((paramObject instanceof Boolean))
      {
        localzza1.type = 8;
        localzza1.zzcia.zzcii = ((Boolean)paramObject).booleanValue();
      }
      else if ((paramObject instanceof Byte))
      {
        localzza1.type = 7;
        localzza1.zzcia.zzcih = ((Byte)paramObject).byteValue();
      }
      else if ((paramObject instanceof byte[]))
      {
        localzza1.type = 1;
        localzza1.zzcia.zzcib = ((byte[])paramObject);
      }
      else if ((paramObject instanceof String[]))
      {
        localzza1.type = 11;
        localzza1.zzcia.zzcil = ((String[])paramObject);
      }
      else if ((paramObject instanceof long[]))
      {
        localzza1.type = 12;
        localzza1.zzcia.zzcim = ((long[])paramObject);
      }
      else if ((paramObject instanceof float[]))
      {
        localzza1.type = 15;
        localzza1.zzcia.zzcin = ((float[])paramObject);
      }
      else if ((paramObject instanceof Asset))
      {
        localzza1.type = 13;
        zzahj.zza.zza.zza localzza = localzza1.zzcia;
        paramList.add((Asset)paramObject);
        localzza.zzcio = (-1 + paramList.size());
      }
      else
      {
        if (!(paramObject instanceof DataMap)) {
          break;
        }
        localzza1.type = 9;
        DataMap localDataMap = (DataMap)paramObject;
        TreeSet localTreeSet = new TreeSet(localDataMap.zzoF.keySet());
        zzahj.zza[] arrayOfzza1 = new zzahj.zza[localTreeSet.size()];
        Iterator localIterator = localTreeSet.iterator();
        for (int n = 0; localIterator.hasNext(); n++)
        {
          String str = (String)localIterator.next();
          arrayOfzza1[n] = new zzahj.zza();
          arrayOfzza1[n].name = str;
          arrayOfzza1[n].zzchY = zza(paramList, localDataMap.get(str));
        }
        localzza1.zzcia.zzcij = arrayOfzza1;
      }
    }
    zzahj.zza.zza[] arrayOfzza;
    Object localObject1;
    int j;
    int k;
    label579:
    Object localObject2;
    zzahj.zza.zza localzza2;
    if ((paramObject instanceof ArrayList))
    {
      localzza1.type = 10;
      ArrayList localArrayList = (ArrayList)paramObject;
      arrayOfzza = new zzahj.zza.zza[localArrayList.size()];
      localObject1 = null;
      int i = localArrayList.size();
      j = 0;
      k = 14;
      if (j < i)
      {
        localObject2 = localArrayList.get(j);
        localzza2 = zza(paramList, localObject2);
        if ((localzza2.type != 14) && (localzza2.type != 2) && (localzza2.type != 6) && (localzza2.type != 9)) {
          throw new IllegalArgumentException("The only ArrayList element types supported by DataBundleUtil are String, Integer, Bundle, and null, but this ArrayList contains a " + localObject2.getClass());
        }
        if ((k != 14) || (localzza2.type == 14)) {}
      }
    }
    for (int m = localzza2.type;; m = k)
    {
      arrayOfzza[j] = localzza2;
      j++;
      k = m;
      localObject1 = localObject2;
      break label579;
      if (localzza2.type != k)
      {
        throw new IllegalArgumentException("ArrayList elements must all be of the sameclass, but this one contains a " + localObject1.getClass() + " and a " + localObject2.getClass());
        localzza1.zzcia.zzcik = arrayOfzza;
        break;
        throw new RuntimeException("newFieldValueFromValue: unexpected value " + paramObject.getClass().getSimpleName());
      }
      localObject2 = localObject1;
    }
  }
  
  public static DataMap zza(zza paramzza)
  {
    DataMap localDataMap = new DataMap();
    for (zzahj.zza localzza : paramzza.zzchU.zzchW) {
      zza(paramzza.zzchV, localDataMap, localzza.name, localzza.zzchY);
    }
    return localDataMap;
  }
  
  private static ArrayList zza(List<Asset> paramList, zzahj.zza.zza.zza paramzza, int paramInt)
  {
    ArrayList localArrayList = new ArrayList(paramzza.zzcik.length);
    zzahj.zza.zza[] arrayOfzza = paramzza.zzcik;
    int i = arrayOfzza.length;
    int j = 0;
    if (j < i)
    {
      zzahj.zza.zza localzza = arrayOfzza[j];
      if (localzza.type == 14) {
        localArrayList.add(null);
      }
      for (;;)
      {
        j++;
        break;
        if (paramInt == 9)
        {
          DataMap localDataMap = new DataMap();
          for (zzahj.zza localzza1 : localzza.zzcia.zzcij) {
            zza(paramList, localDataMap, localzza1.name, localzza1.zzchY);
          }
          localArrayList.add(localDataMap);
        }
        else if (paramInt == 2)
        {
          localArrayList.add(localzza.zzcia.zzcic);
        }
        else
        {
          if (paramInt != 6) {
            break label188;
          }
          localArrayList.add(Integer.valueOf(localzza.zzcia.zzcig));
        }
      }
      label188:
      throw new IllegalArgumentException("Unexpected typeOfArrayList: " + paramInt);
    }
    return localArrayList;
  }
  
  private static void zza(List<Asset> paramList, DataMap paramDataMap, String paramString, zzahj.zza.zza paramzza)
  {
    int i = paramzza.type;
    if (i == 14)
    {
      paramDataMap.putString(paramString, null);
      return;
    }
    zzahj.zza.zza.zza localzza = paramzza.zzcia;
    if (i == 1)
    {
      byte[] arrayOfByte = localzza.zzcib;
      paramDataMap.zzoF.put(paramString, arrayOfByte);
      return;
    }
    if (i == 11)
    {
      paramDataMap.putStringArray(paramString, localzza.zzcil);
      return;
    }
    if (i == 12)
    {
      long[] arrayOfLong = localzza.zzcim;
      paramDataMap.zzoF.put(paramString, arrayOfLong);
      return;
    }
    if (i == 15)
    {
      float[] arrayOfFloat = localzza.zzcin;
      paramDataMap.zzoF.put(paramString, arrayOfFloat);
      return;
    }
    if (i == 2)
    {
      paramDataMap.putString(paramString, localzza.zzcic);
      return;
    }
    if (i == 3)
    {
      double d = localzza.zzcid;
      paramDataMap.zzoF.put(paramString, Double.valueOf(d));
      return;
    }
    if (i == 4)
    {
      float f = localzza.zzcie;
      paramDataMap.zzoF.put(paramString, Float.valueOf(f));
      return;
    }
    if (i == 5)
    {
      paramDataMap.putLong(paramString, localzza.zzcif);
      return;
    }
    if (i == 6)
    {
      int n = localzza.zzcig;
      paramDataMap.zzoF.put(paramString, Integer.valueOf(n));
      return;
    }
    if (i == 7)
    {
      byte b = (byte)localzza.zzcih;
      paramDataMap.zzoF.put(paramString, Byte.valueOf(b));
      return;
    }
    if (i == 8)
    {
      boolean bool = localzza.zzcii;
      paramDataMap.zzoF.put(paramString, Boolean.valueOf(bool));
      return;
    }
    if (i == 13)
    {
      if (paramList == null) {
        throw new RuntimeException("populateBundle: unexpected type for: " + paramString);
      }
      paramDataMap.putAsset(paramString, (Asset)paramList.get((int)localzza.zzcio));
      return;
    }
    if (i == 9)
    {
      DataMap localDataMap = new DataMap();
      for (zzahj.zza localzza1 : localzza.zzcij) {
        zza(paramList, localDataMap, localzza1.name, localzza1.zzchY);
      }
      paramDataMap.zzoF.put(paramString, localDataMap);
      return;
    }
    if (i == 10)
    {
      int m = zza(paramString, localzza.zzcik);
      ArrayList localArrayList = zza(paramList, localzza, m);
      if (m == 14)
      {
        paramDataMap.putStringArrayList(paramString, localArrayList);
        return;
      }
      if (m == 9)
      {
        paramDataMap.zzoF.put(paramString, localArrayList);
        return;
      }
      if (m == 2)
      {
        paramDataMap.putStringArrayList(paramString, localArrayList);
        return;
      }
      if (m == 6)
      {
        paramDataMap.zzoF.put(paramString, localArrayList);
        return;
      }
      throw new IllegalStateException("Unexpected typeOfArrayList: " + m);
    }
    throw new RuntimeException("populateBundle: unexpected type " + i);
  }
  
  public static zzahj.zza[] zza(DataMap paramDataMap, List<Asset> paramList)
  {
    TreeSet localTreeSet = new TreeSet(paramDataMap.zzoF.keySet());
    zzahj.zza[] arrayOfzza = new zzahj.zza[localTreeSet.size()];
    Iterator localIterator = localTreeSet.iterator();
    for (int i = 0; localIterator.hasNext(); i++)
    {
      String str = (String)localIterator.next();
      Object localObject = paramDataMap.get(str);
      arrayOfzza[i] = new zzahj.zza();
      arrayOfzza[i].name = str;
      arrayOfzza[i].zzchY = zza(paramList, localObject);
    }
    return arrayOfzza;
  }
  
  public static final class zza
  {
    public final zzahj zzchU;
    public final List<Asset> zzchV;
    
    public zza(zzahj paramzzahj, List<Asset> paramList)
    {
      this.zzchU = paramzzahj;
      this.zzchV = paramList;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzahi
 * JD-Core Version:    0.7.0.1
 */