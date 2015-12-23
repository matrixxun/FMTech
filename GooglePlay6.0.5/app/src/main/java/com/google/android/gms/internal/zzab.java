package com.google.android.gms.internal;

import java.io.UnsupportedEncodingException;

public class zzab
  extends zzk<String>
{
  private final zzm.zzb<String> zzaF;
  
  private zzab(String paramString, zzm.zzb<String> paramzzb, zzm.zza paramzza)
  {
    super(0, paramString, paramzza);
    this.zzaF = paramzzb;
  }
  
  public zzab(String paramString, zzm.zzb<String> paramzzb, zzm.zza paramzza, byte paramByte)
  {
    this(paramString, paramzzb, paramzza);
  }
  
  protected final zzm<String> zza(zzi paramzzi)
  {
    try
    {
      str = new String(paramzzi.data, zzx.zzb(paramzzi.headers, "ISO-8859-1"));
      return new zzm(str, zzx.zzb(paramzzi));
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      for (;;)
      {
        String str = new String(paramzzi.data);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzab
 * JD-Core Version:    0.7.0.1
 */