package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

@zzhb
public final class zzbr
{
  final Object zzqp = new Object();
  private final int zzth;
  private final int zzti;
  private final int zztj;
  private final zzbw zztk;
  private ArrayList<String> zztl = new ArrayList();
  int zztm = 0;
  int zztn = 0;
  int zzto = 0;
  private int zztp;
  String zztq = "";
  
  public zzbr(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.zzth = paramInt1;
    this.zzti = paramInt2;
    this.zztj = paramInt3;
    this.zztk = new zzbw(paramInt4);
  }
  
  private static String zza$19d919ee(ArrayList<String> paramArrayList)
  {
    String str;
    if (paramArrayList.isEmpty()) {
      str = "";
    }
    do
    {
      return str;
      StringBuffer localStringBuffer = new StringBuffer();
      Iterator localIterator = paramArrayList.iterator();
      do
      {
        if (!localIterator.hasNext()) {
          break;
        }
        localStringBuffer.append((String)localIterator.next());
        localStringBuffer.append(' ');
      } while (localStringBuffer.length() <= 200);
      localStringBuffer.deleteCharAt(-1 + localStringBuffer.length());
      str = localStringBuffer.toString();
    } while (str.length() < 200);
    return str.substring(0, 200);
  }
  
  public final boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof zzbr)) {}
    zzbr localzzbr;
    do
    {
      return false;
      if (paramObject == this) {
        return true;
      }
      localzzbr = (zzbr)paramObject;
    } while ((localzzbr.zztq == null) || (!localzzbr.zztq.equals(this.zztq)));
    return true;
  }
  
  public final int hashCode()
  {
    return this.zztq.hashCode();
  }
  
  public final String toString()
  {
    return "ActivityContent fetchId: " + this.zztn + " score:" + this.zztp + " total_length:" + this.zztm + "\n text: " + zza$19d919ee(this.zztl) + "\n signture: " + this.zztq;
  }
  
  public final boolean zzcE()
  {
    for (;;)
    {
      synchronized (this.zzqp)
      {
        if (this.zzto == 0)
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  public final void zzcH()
  {
    synchronized (this.zzqp)
    {
      this.zzto = (1 + this.zzto);
      return;
    }
  }
  
  public final void zzcI()
  {
    zzbw localzzbw;
    StringBuffer localStringBuffer;
    synchronized (this.zzqp)
    {
      int i = this.zztm;
      int j = this.zztn;
      int k = i * this.zzth + j * this.zzti;
      if (k <= this.zztp) {
        break label158;
      }
      this.zztp = k;
      localzzbw = this.zztk;
      ArrayList localArrayList = this.zztl;
      localStringBuffer = new StringBuffer();
      Iterator localIterator = localArrayList.iterator();
      if (localIterator.hasNext())
      {
        localStringBuffer.append(((String)localIterator.next()).toLowerCase(Locale.US));
        localStringBuffer.append('\n');
      }
    }
    switch (localzzbw.zztL)
    {
    }
    for (;;)
    {
      this.zztq = ((String)localObject3);
      label158:
      return;
      Object localObject3 = localzzbw.zzB(localStringBuffer.toString());
      continue;
      String str = localzzbw.zzA(localStringBuffer.toString());
      localObject3 = str;
      continue;
      localObject3 = "";
    }
  }
  
  public final void zzv(String paramString)
  {
    zzx(paramString);
    synchronized (this.zzqp)
    {
      if (this.zzto < 0) {
        zzb.d("ActivityContent: negative number of WebViews.");
      }
      zzcI();
      return;
    }
  }
  
  final void zzx(String paramString)
  {
    if ((paramString == null) || (paramString.length() < this.zztj)) {
      return;
    }
    synchronized (this.zzqp)
    {
      this.zztl.add(paramString);
      this.zztm += paramString.length();
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzbr
 * JD-Core Version:    0.7.0.1
 */