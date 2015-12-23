package com.google.android.gms.common.stats;

public abstract class zzf
{
  public abstract long getDurationMillis();
  
  public abstract int getEventType();
  
  public abstract long getTimeMillis();
  
  public String toString()
  {
    return getTimeMillis() + "\t" + getEventType() + "\t" + getDurationMillis() + zzre();
  }
  
  public abstract String zzre();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.stats.zzf
 * JD-Core Version:    0.7.0.1
 */