package com.google.android.gms.people.internal;

import com.google.android.gms.common.internal.zzx;
import java.util.regex.Pattern;

public final class zzm
{
  public static final zzm zzbxd = new zzm();
  private Pattern[] zzbxe = new Pattern[0];
  private String[] zzbxf = new String[0];
  
  public final void zza(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    int i = 0;
    try
    {
      if (paramArrayOfString1.length == paramArrayOfString2.length) {}
      for (boolean bool = true;; bool = false)
      {
        zzx.zzab(bool);
        this.zzbxe = new Pattern[paramArrayOfString1.length];
        this.zzbxf = paramArrayOfString2;
        while (i < paramArrayOfString1.length)
        {
          this.zzbxe[i] = Pattern.compile(paramArrayOfString1[i]);
          i++;
        }
      }
      return;
    }
    finally {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.internal.zzm
 * JD-Core Version:    0.7.0.1
 */