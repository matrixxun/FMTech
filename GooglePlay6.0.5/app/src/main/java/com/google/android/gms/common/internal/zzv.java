package com.google.android.gms.common.internal;

import java.util.Iterator;

public final class zzv
{
  private final String separator;
  
  public zzv(String paramString)
  {
    this.separator = paramString;
  }
  
  private static CharSequence zzA(Object paramObject)
  {
    if ((paramObject instanceof CharSequence)) {
      return (CharSequence)paramObject;
    }
    return paramObject.toString();
  }
  
  public final StringBuilder zza(StringBuilder paramStringBuilder, Iterable<?> paramIterable)
  {
    Iterator localIterator = paramIterable.iterator();
    if (localIterator.hasNext())
    {
      paramStringBuilder.append(zzA(localIterator.next()));
      while (localIterator.hasNext())
      {
        paramStringBuilder.append(this.separator);
        paramStringBuilder.append(zzA(localIterator.next()));
      }
    }
    return paramStringBuilder;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzv
 * JD-Core Version:    0.7.0.1
 */