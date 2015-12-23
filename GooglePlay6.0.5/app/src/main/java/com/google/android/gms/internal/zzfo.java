package com.google.android.gms.internal;

import java.util.Map;

@zzhb
public final class zzfo
{
  final boolean zzCP;
  final String zzCQ;
  final zzjo zzpX;
  
  public zzfo(zzjo paramzzjo, Map<String, String> paramMap)
  {
    this.zzpX = paramzzjo;
    this.zzCQ = ((String)paramMap.get("forceOrientation"));
    if (paramMap.containsKey("allowOrientationChange"))
    {
      this.zzCP = Boolean.parseBoolean((String)paramMap.get("allowOrientationChange"));
      return;
    }
    this.zzCP = true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzfo
 * JD-Core Version:    0.7.0.1
 */