package com.google.android.gms.common.data;

import java.util.ArrayList;

public abstract class zze<T>
  extends AbstractDataBuffer<T>
{
  private boolean zzarL = false;
  private ArrayList<Integer> zzarM;
  
  public zze(DataHolder paramDataHolder)
  {
    super(paramDataHolder);
  }
  
  private int zzcX(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.zzarM.size())) {
      throw new IllegalArgumentException("Position " + paramInt + " is out of bounds for this buffer");
    }
    return ((Integer)this.zzarM.get(paramInt)).intValue();
  }
  
  private void zzpL()
  {
    for (;;)
    {
      int k;
      Object localObject3;
      try
      {
        if (this.zzarL) {
          break label195;
        }
        int i = this.zzapd.zzarB;
        this.zzarM = new ArrayList();
        if (i <= 0) {
          break label190;
        }
        this.zzarM.add(Integer.valueOf(0));
        String str = zzpK();
        int j = this.zzapd.zzcW(0);
        localObject2 = this.zzapd.getString(str, 0, j);
        k = 1;
        if (k >= i) {
          break label190;
        }
        int m = this.zzapd.zzcW(k);
        localObject3 = this.zzapd.getString(str, k, m);
        if (localObject3 == null) {
          throw new NullPointerException("Missing value for markerColumn: " + str + ", at row: " + k + ", for window: " + m);
        }
      }
      finally {}
      if (!((String)localObject3).equals(localObject2))
      {
        this.zzarM.add(Integer.valueOf(k));
        break label202;
        label190:
        this.zzarL = true;
      }
      else
      {
        label195:
        localObject3 = localObject2;
      }
      label202:
      k++;
      Object localObject2 = localObject3;
    }
  }
  
  public final T get(int paramInt)
  {
    zzpL();
    int i = zzcX(paramInt);
    int j;
    if ((paramInt < 0) || (paramInt == this.zzarM.size())) {
      j = 0;
    }
    label128:
    for (;;)
    {
      return zzv(i, j);
      if (paramInt == -1 + this.zzarM.size()) {}
      for (j = this.zzapd.zzarB - ((Integer)this.zzarM.get(paramInt)).intValue();; j = ((Integer)this.zzarM.get(paramInt + 1)).intValue() - ((Integer)this.zzarM.get(paramInt)).intValue())
      {
        if (j != 1) {
          break label128;
        }
        int k = zzcX(paramInt);
        this.zzapd.zzcW(k);
        break;
      }
    }
  }
  
  public final int getCount()
  {
    zzpL();
    return this.zzarM.size();
  }
  
  public abstract String zzpK();
  
  public abstract T zzv(int paramInt1, int paramInt2);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.data.zze
 * JD-Core Version:    0.7.0.1
 */