package com.google.android.gms.internal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class zzbv
{
  private static MessageDigest zztI = null;
  protected Object zzqp = new Object();
  
  protected final MessageDigest zzcQ()
  {
    for (;;)
    {
      int i;
      synchronized (this.zzqp)
      {
        if (zztI != null)
        {
          MessageDigest localMessageDigest2 = zztI;
          return localMessageDigest2;
        }
        i = 0;
        if (i >= 2) {}
      }
      try
      {
        zztI = MessageDigest.getInstance("MD5");
        label38:
        i++;
        continue;
        MessageDigest localMessageDigest1 = zztI;
        return localMessageDigest1;
        localObject2 = finally;
        throw localObject2;
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        break label38;
      }
    }
  }
  
  abstract byte[] zzz(String paramString);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzbv
 * JD-Core Version:    0.7.0.1
 */