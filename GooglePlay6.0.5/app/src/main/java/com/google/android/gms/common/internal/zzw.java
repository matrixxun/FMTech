package com.google.android.gms.common.internal;

import java.util.ArrayList;
import java.util.List;

public final class zzw
{
  public static boolean equal(Object paramObject1, Object paramObject2)
  {
    return (paramObject1 == paramObject2) || ((paramObject1 != null) && (paramObject1.equals(paramObject2)));
  }
  
  public static zza zzB(Object paramObject)
  {
    return new zza(paramObject, (byte)0);
  }
  
  public static final class zza
  {
    private final Object zzLQ;
    private final List<String> zzauB;
    
    private zza(Object paramObject)
    {
      this.zzLQ = zzx.zzC(paramObject);
      this.zzauB = new ArrayList();
    }
    
    public final String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder(100).append(this.zzLQ.getClass().getSimpleName()).append('{');
      int i = this.zzauB.size();
      for (int j = 0; j < i; j++)
      {
        localStringBuilder.append((String)this.zzauB.get(j));
        if (j < i - 1) {
          localStringBuilder.append(", ");
        }
      }
      return '}';
    }
    
    public final zza zzh(String paramString, Object paramObject)
    {
      this.zzauB.add((String)zzx.zzC(paramString) + "=" + String.valueOf(paramObject));
      return this;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzw
 * JD-Core Version:    0.7.0.1
 */