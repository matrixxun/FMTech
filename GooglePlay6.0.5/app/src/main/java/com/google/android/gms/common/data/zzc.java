package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzx;
import java.util.Arrays;

public abstract class zzc
{
  public final DataHolder zzapd;
  public int zzars;
  private int zzart;
  
  public zzc(DataHolder paramDataHolder, int paramInt)
  {
    this.zzapd = ((DataHolder)zzx.zzC(paramDataHolder));
    if ((paramInt >= 0) && (paramInt < this.zzapd.zzarB)) {}
    for (boolean bool = true;; bool = false)
    {
      zzx.zzaa(bool);
      this.zzars = paramInt;
      this.zzart = this.zzapd.zzcW(this.zzars);
      return;
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof zzc;
    boolean bool2 = false;
    if (bool1)
    {
      zzc localzzc = (zzc)paramObject;
      boolean bool3 = zzw.equal(Integer.valueOf(localzzc.zzars), Integer.valueOf(this.zzars));
      bool2 = false;
      if (bool3)
      {
        boolean bool4 = zzw.equal(Integer.valueOf(localzzc.zzart), Integer.valueOf(this.zzart));
        bool2 = false;
        if (bool4)
        {
          DataHolder localDataHolder1 = localzzc.zzapd;
          DataHolder localDataHolder2 = this.zzapd;
          bool2 = false;
          if (localDataHolder1 == localDataHolder2) {
            bool2 = true;
          }
        }
      }
    }
    return bool2;
  }
  
  public final byte[] getByteArray(String paramString)
  {
    DataHolder localDataHolder = this.zzapd;
    int i = this.zzars;
    int j = this.zzart;
    localDataHolder.zzi(paramString, i);
    return localDataHolder.zzary[j].getBlob(i, localDataHolder.zzarx.getInt(paramString));
  }
  
  public final int getInteger(String paramString)
  {
    DataHolder localDataHolder = this.zzapd;
    int i = this.zzars;
    int j = this.zzart;
    localDataHolder.zzi(paramString, i);
    return localDataHolder.zzary[j].getInt(i, localDataHolder.zzarx.getInt(paramString));
  }
  
  public final long getLong(String paramString)
  {
    return this.zzapd.getLong(paramString, this.zzars, this.zzart);
  }
  
  public final String getString(String paramString)
  {
    return this.zzapd.getString(paramString, this.zzars, this.zzart);
  }
  
  public int hashCode()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = Integer.valueOf(this.zzars);
    arrayOfObject[1] = Integer.valueOf(this.zzart);
    arrayOfObject[2] = this.zzapd;
    return Arrays.hashCode(arrayOfObject);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.data.zzc
 * JD-Core Version:    0.7.0.1
 */