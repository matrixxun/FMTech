package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

@zzhb
public final class zzih
{
  private Context mContext;
  private boolean zzIK = true;
  private boolean zzIL = true;
  Boolean zzKA = null;
  private String zzKB;
  private boolean zzKC = false;
  private boolean zzKD = false;
  private final String zzKq = zziq.zzhi();
  private final zzii zzKr = new zzii(this.zzKq);
  private BigInteger zzKs = BigInteger.ONE;
  private final HashSet<Object> zzKt = new HashSet();
  private final HashMap<String, Object> zzKu = new HashMap();
  private boolean zzKv = false;
  private int zzKw = 0;
  private zzcc zzKx = null;
  private zzbu zzKy = null;
  private final LinkedList<Thread> zzKz = new LinkedList();
  private boolean zzqM = false;
  private VersionInfoParcel zzqn;
  final Object zzqp = new Object();
  private zzbt zztu = null;
  private zzbs zztv = null;
  private final zzha zztw = null;
  
  public final void zzb(Throwable paramThrowable, boolean paramBoolean)
  {
    new zzha(this.mContext, this.zzqn).zza(paramThrowable, paramBoolean);
  }
  
  public final String zzf(int paramInt, String paramString)
  {
    if (this.zzqn.zzMa) {}
    for (Resources localResources = this.mContext.getResources(); localResources == null; localResources = GooglePlayServicesUtil.getRemoteResource(this.mContext)) {
      return paramString;
    }
    return localResources.getString(paramInt);
  }
  
  public final zzcc zzgU()
  {
    synchronized (this.zzqp)
    {
      zzcc localzzcc = this.zzKx;
      return localzzcc;
    }
  }
  
  public final String zzgX()
  {
    synchronized (this.zzqp)
    {
      String str = this.zzKB;
      return str;
    }
  }
  
  public final Boolean zzgY()
  {
    synchronized (this.zzqp)
    {
      Boolean localBoolean = this.zzKA;
      return localBoolean;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzih
 * JD-Core Version:    0.7.0.1
 */