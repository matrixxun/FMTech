package com.google.android.gms.common.stats;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.Debug;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.gms.common.util.zzr;
import com.google.android.gms.internal.zznx;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class zzb
{
  private static final Object zzauk = new Object();
  private static zzb zzavX;
  private static Integer zzawd;
  private final List<String> zzavY;
  private final List<String> zzavZ;
  private final List<String> zzawa;
  private final List<String> zzawb;
  private zze zzawc;
  private zze zzawe;
  
  private zzb()
  {
    if (getLogLevel() == zzd.LOG_LEVEL_OFF)
    {
      this.zzavY = Collections.EMPTY_LIST;
      this.zzavZ = Collections.EMPTY_LIST;
      this.zzawa = Collections.EMPTY_LIST;
      this.zzawb = Collections.EMPTY_LIST;
      return;
    }
    String str1 = (String)zzc.zza.zzawi.get();
    List localList1;
    String str2;
    List localList2;
    label84:
    String str3;
    List localList3;
    label111:
    String str4;
    if (str1 == null)
    {
      localList1 = Collections.EMPTY_LIST;
      this.zzavY = localList1;
      str2 = (String)zzc.zza.zzawj.get();
      if (str2 != null) {
        break label204;
      }
      localList2 = Collections.EMPTY_LIST;
      this.zzavZ = localList2;
      str3 = (String)zzc.zza.zzawk.get();
      if (str3 != null) {
        break label218;
      }
      localList3 = Collections.EMPTY_LIST;
      this.zzawa = localList3;
      str4 = (String)zzc.zza.zzawl.get();
      if (str4 != null) {
        break label233;
      }
    }
    label204:
    label218:
    label233:
    for (List localList4 = Collections.EMPTY_LIST;; localList4 = Arrays.asList(str4.split(",")))
    {
      this.zzawb = localList4;
      this.zzawc = new zze(((Long)zzc.zza.zzawm.get()).longValue());
      this.zzawe = new zze(((Long)zzc.zza.zzawm.get()).longValue());
      return;
      localList1 = Arrays.asList(str1.split(","));
      break;
      localList2 = Arrays.asList(str2.split(","));
      break label84;
      localList3 = Arrays.asList(str3.split(","));
      break label111;
    }
  }
  
  private static int getLogLevel()
  {
    if (zzawd == null) {}
    for (;;)
    {
      try
      {
        if ((!com.google.android.gms.common.internal.zzd.zzasZ) || (!zznx.isInitialized()) || (zznx.zzpx() != Process.myUid())) {
          continue;
        }
        i = 1;
        if (i == 0) {
          continue;
        }
        j = ((Integer)zzc.zza.zzawh.get()).intValue();
        zzawd = Integer.valueOf(j);
      }
      catch (SecurityException localSecurityException)
      {
        int i;
        int j;
        zzawd = Integer.valueOf(zzd.LOG_LEVEL_OFF);
        continue;
      }
      return zzawd.intValue();
      i = 0;
      continue;
      j = zzd.LOG_LEVEL_OFF;
    }
  }
  
  public static String zzb(ServiceConnection paramServiceConnection)
  {
    return String.valueOf(Process.myPid() << 32 | System.identityHashCode(paramServiceConnection));
  }
  
  public static zzb zzrf()
  {
    synchronized (zzauk)
    {
      if (zzavX == null) {
        zzavX = new zzb();
      }
      return zzavX;
    }
  }
  
  public final void zza(Context paramContext, ServiceConnection paramServiceConnection)
  {
    paramContext.unbindService(paramServiceConnection);
    zza(paramContext, zzb(paramServiceConnection), null, null, 1);
  }
  
  public final void zza(Context paramContext, String paramString1, String paramString2, Intent paramIntent, int paramInt)
  {
    int i;
    if (!com.google.android.gms.common.internal.zzd.zzasZ)
    {
      i = 0;
      if ((i != 0) && (this.zzawc != null)) {
        break label43;
      }
    }
    label43:
    do
    {
      return;
      if (getLogLevel() == zzd.LOG_LEVEL_OFF)
      {
        i = 0;
        break;
      }
      i = 1;
      break;
      if ((paramInt != 4) && (paramInt != 1)) {
        break label201;
      }
    } while (!this.zzawc.zzcJ(paramString1));
    String str1 = null;
    String str2 = null;
    String str3 = null;
    long l1 = System.currentTimeMillis();
    int j = getLogLevel() & zzd.zzawr;
    String str4 = null;
    if (j != 0)
    {
      str4 = null;
      if (paramInt != 13) {
        str4 = zzr.zzx$13d12155(5);
      }
    }
    long l2 = 0L;
    if ((getLogLevel() & zzd.zzawt) != 0) {
      l2 = Debug.getNativeHeapAllocatedSize();
    }
    if ((paramInt == 1) || (paramInt == 4) || (paramInt == 14)) {}
    for (ConnectionEvent localConnectionEvent = new ConnectionEvent(l1, paramInt, null, null, null, null, str4, paramString1, SystemClock.elapsedRealtime(), l2);; localConnectionEvent = new ConnectionEvent(l1, paramInt, str3, paramString2, str1, str2, str4, paramString1, SystemClock.elapsedRealtime(), l2))
    {
      paramContext.startService(new Intent().setComponent(zzd.zzawn).putExtra("com.google.android.gms.common.stats.EXTRA_LOG_EVENT", localConnectionEvent));
      return;
      label201:
      List localList = paramContext.getPackageManager().queryIntentServices(paramIntent, 128);
      ServiceInfo localServiceInfo;
      if ((localList == null) || (localList.size() == 0))
      {
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = paramIntent.toUri(0);
        arrayOfObject1[1] = zzr.zzx$13d12155(20);
        Log.w("ConnectionTracker", String.format("There are no handler of this intent: %s\n Stack trace: %s", arrayOfObject1));
        localServiceInfo = null;
      }
      while (localServiceInfo == null)
      {
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = paramString2;
        arrayOfObject2[1] = paramIntent.toUri(0);
        Log.w("ConnectionTracker", String.format("Client %s made an invalid request %s", arrayOfObject2));
        return;
        if (localList.size() > 1)
        {
          Object[] arrayOfObject3 = new Object[2];
          arrayOfObject3[0] = paramIntent.toUri(0);
          arrayOfObject3[1] = zzr.zzx$13d12155(20);
          Log.w("ConnectionTracker", String.format("Multiple handlers found for this intent: %s\n Stack trace: %s", arrayOfObject3));
          Iterator localIterator = localList.iterator();
          if (localIterator.hasNext())
          {
            Log.w("ConnectionTracker", ((ResolveInfo)localIterator.next()).serviceInfo.name);
            localServiceInfo = null;
            continue;
          }
        }
        localServiceInfo = ((ResolveInfo)localList.get(0)).serviceInfo;
      }
      str1 = localServiceInfo.processName;
      str2 = localServiceInfo.name;
      str3 = zzr.zzi(paramContext, Binder.getCallingPid());
      int k = getLogLevel();
      if ((this.zzavY.contains(str3)) || (this.zzavZ.contains(paramString2)) || (this.zzawa.contains(str1)) || (this.zzawb.contains(str2)) || ((str1.equals(str3)) && ((k & zzd.zzaws) != 0))) {}
      for (int m = 0;; m = 1)
      {
        if (m == 0) {
          break label554;
        }
        this.zzawc.zzcI(paramString1);
        break;
      }
      label554:
      break;
    }
  }
  
  public final boolean zza(Context paramContext, Intent paramIntent, ServiceConnection paramServiceConnection, int paramInt)
  {
    return zza(paramContext, paramContext.getClass().getName(), paramIntent, paramServiceConnection, paramInt);
  }
  
  public final boolean zza(Context paramContext, String paramString, Intent paramIntent, ServiceConnection paramServiceConnection, int paramInt)
  {
    ComponentName localComponentName = paramIntent.getComponent();
    if ((localComponentName == null) || ((com.google.android.gms.common.internal.zzd.zzasZ) && ("com.google.android.gms".equals(localComponentName.getPackageName())))) {}
    for (boolean bool1 = false; bool1; bool1 = com.google.android.gms.common.util.zze.zzm(paramContext, localComponentName.getPackageName()))
    {
      Log.w("ConnectionTracker", "Attempted to bind to a service in a STOPPED package.");
      return false;
    }
    boolean bool2 = paramContext.bindService(paramIntent, paramServiceConnection, paramInt);
    if (bool2) {
      zza(paramContext, zzb(paramServiceConnection), paramString, paramIntent, 2);
    }
    return bool2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.stats.zzb
 * JD-Core Version:    0.7.0.1
 */