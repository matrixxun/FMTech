package com.google.android.gms.measurement.internal;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.zzso.zza;
import com.google.android.gms.internal.zzso.zzb;
import com.google.android.gms.internal.zzso.zzc;
import com.google.android.gms.internal.zzso.zzd;
import com.google.android.gms.internal.zzso.zze;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.measurement.AppMeasurementReceiver;
import com.google.android.gms.measurement.AppMeasurementService;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class zzt
{
  private static zzx zzbnU;
  private static volatile zzt zzbnV;
  final Context mContext;
  private final boolean zzPl;
  final zzc zzbnW;
  private final zzr zzbnX;
  private final zzo zzbnY;
  final zzs zzbnZ;
  private final AppMeasurement zzboa;
  private final zzae zzbob;
  private final zzd zzboc;
  private final zzp zzbod;
  final zzz zzboe;
  private final zzf zzbof;
  private final zzy zzbog;
  private final zzm zzboh;
  private final zzq zzboi;
  private final zzab zzboj;
  private Boolean zzbok;
  private List<Long> zzbol;
  int zzbom;
  int zzbon;
  final Clock zzri;
  
  private zzt(zzx paramzzx)
  {
    com.google.android.gms.common.internal.zzx.zzC(paramzzx);
    this.mContext = paramzzx.mContext;
    this.zzri = com.google.android.gms.common.util.zzh.zzrs();
    this.zzbnW = new zzc(this);
    zzr localzzr = new zzr(this);
    localzzr.zza();
    this.zzbnX = localzzr;
    zzo localzzo = new zzo(this);
    localzzo.zza();
    this.zzbnY = localzzo;
    this.zzbob = new zzae(this);
    zzf localzzf = new zzf(this);
    localzzf.zza();
    this.zzbof = localzzf;
    zzm localzzm = new zzm(this);
    localzzm.zza();
    this.zzboh = localzzm;
    zzd localzzd = new zzd(this);
    localzzd.zza();
    this.zzboc = localzzd;
    zzp localzzp = new zzp(this);
    localzzp.zza();
    this.zzbod = localzzp;
    zzz localzzz = zzx.zzk(this);
    localzzz.zza();
    this.zzboe = localzzz;
    zzy localzzy1 = zzx.zzf(this);
    localzzy1.zza();
    this.zzbog = localzzy1;
    zzab localzzab = zzx.zzo(this);
    localzzab.zza();
    this.zzboj = localzzab;
    this.zzboi = new zzq(this);
    this.zzboa = new AppMeasurement(this);
    zzs localzzs = new zzs(this);
    localzzs.zza();
    this.zzbnZ = localzzs;
    if (this.zzbom != this.zzbon) {
      zzBh().zzbmW.zze("Not all components initialized", Integer.valueOf(this.zzbom), Integer.valueOf(this.zzbon));
    }
    this.zzPl = true;
    if (!zzc.isPackageSide())
    {
      if (!(this.mContext.getApplicationContext() instanceof Application)) {
        break label457;
      }
      if (Build.VERSION.SDK_INT < 14) {
        break label441;
      }
      zzy localzzy2 = zzCE();
      if ((localzzy2.getContext().getApplicationContext() instanceof Application))
      {
        Application localApplication = (Application)localzzy2.getContext().getApplicationContext();
        if (localzzy2.zzbov == null) {
          localzzy2.zzbov = new zzy.zza(localzzy2, (byte)0);
        }
        localApplication.unregisterActivityLifecycleCallbacks(localzzy2.zzbov);
        localApplication.registerActivityLifecycleCallbacks(localzzy2.zzbov);
        localzzy2.zzBh().zzbne.zzeB("Registered activity lifecycle callback");
      }
    }
    for (;;)
    {
      this.zzbnZ.zzg(new Runnable()
      {
        public final void run()
        {
          zzt.this.start();
        }
      });
      return;
      label441:
      zzBh().zzbnd.zzeB("Not tracking deep linking pre-ICS");
      continue;
      label457:
      zzBh().zzbmZ.zzeB("Application context is not an Application");
    }
  }
  
  private zzy zzCE()
  {
    zza(this.zzbog);
    return this.zzbog;
  }
  
  private zzf zzCI()
  {
    zza(this.zzbof);
    return this.zzbof;
  }
  
  private zzq zzCJ()
  {
    if (this.zzboi == null) {
      throw new IllegalStateException("Network broadcast receiver not created");
    }
    return this.zzboi;
  }
  
  private zzab zzCK()
  {
    zza(this.zzboj);
    return this.zzboj;
  }
  
  private boolean zzCN()
  {
    return !TextUtils.isEmpty(zzCG().zzCb());
  }
  
  private static void zza(zzv paramzzv)
  {
    if (paramzzv == null) {
      throw new IllegalStateException("Component not created");
    }
  }
  
  static void zza(zzw paramzzw)
  {
    if (paramzzw == null) {
      throw new IllegalStateException("Component not created");
    }
    if (!paramzzw.isInitialized()) {
      throw new IllegalStateException("Component not initialized");
    }
  }
  
  /* Error */
  public static zzt zzaT(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 61	com/google/android/gms/common/internal/zzx:zzC	(Ljava/lang/Object;)Ljava/lang/Object;
    //   4: pop
    //   5: aload_0
    //   6: invokevirtual 205	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   9: invokestatic 61	com/google/android/gms/common/internal/zzx:zzC	(Ljava/lang/Object;)Ljava/lang/Object;
    //   12: pop
    //   13: getstatic 396	com/google/android/gms/measurement/internal/zzt:zzbnV	Lcom/google/android/gms/measurement/internal/zzt;
    //   16: ifnonnull +38 -> 54
    //   19: ldc 2
    //   21: monitorenter
    //   22: getstatic 396	com/google/android/gms/measurement/internal/zzt:zzbnV	Lcom/google/android/gms/measurement/internal/zzt;
    //   25: ifnonnull +26 -> 51
    //   28: getstatic 398	com/google/android/gms/measurement/internal/zzt:zzbnU	Lcom/google/android/gms/measurement/internal/zzx;
    //   31: ifnull +27 -> 58
    //   34: getstatic 398	com/google/android/gms/measurement/internal/zzt:zzbnU	Lcom/google/android/gms/measurement/internal/zzx;
    //   37: astore 4
    //   39: new 2	com/google/android/gms/measurement/internal/zzt
    //   42: dup
    //   43: aload 4
    //   45: invokespecial 400	com/google/android/gms/measurement/internal/zzt:<init>	(Lcom/google/android/gms/measurement/internal/zzx;)V
    //   48: putstatic 396	com/google/android/gms/measurement/internal/zzt:zzbnV	Lcom/google/android/gms/measurement/internal/zzt;
    //   51: ldc 2
    //   53: monitorexit
    //   54: getstatic 396	com/google/android/gms/measurement/internal/zzt:zzbnV	Lcom/google/android/gms/measurement/internal/zzt;
    //   57: areturn
    //   58: new 63	com/google/android/gms/measurement/internal/zzx
    //   61: dup
    //   62: aload_0
    //   63: invokespecial 403	com/google/android/gms/measurement/internal/zzx:<init>	(Landroid/content/Context;)V
    //   66: astore 4
    //   68: goto -29 -> 39
    //   71: astore_3
    //   72: ldc 2
    //   74: monitorexit
    //   75: aload_3
    //   76: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	77	0	paramContext	Context
    //   71	5	3	localObject	Object
    //   37	30	4	localzzx	zzx
    // Exception table:
    //   from	to	target	type
    //   22	39	71	finally
    //   39	51	71	finally
    //   51	54	71	finally
    //   58	68	71	finally
    //   72	75	71	finally
  }
  
  protected final void start()
  {
    zzBY().checkOnWorkerThread();
    zzBh().zzbnc.zzeB("App measurement is starting up");
    zzBh().zzbnd.zzeB("Debug logging enabled");
    zzCG().zzCc();
    if (!zzCC())
    {
      if (!zzBX().zzaZ("android.permission.INTERNET")) {
        zzBh().zzbmW.zzeB("App is missing INTERNET permission");
      }
      if (!zzBX().zzaZ("android.permission.ACCESS_NETWORK_STATE")) {
        zzBh().zzbmW.zzeB("App is missing ACCESS_NETWORK_STATE permission");
      }
      if (!AppMeasurementReceiver.zzac(this.mContext)) {
        zzBh().zzbmW.zzeB("AppMeasurementReceiver not registered/enabled");
      }
      if (!AppMeasurementService.zzad(this.mContext)) {
        zzBh().zzbmW.zzeB("AppMeasurementService not registered/enabled");
      }
      zzBh().zzbmW.zzeB("Uploading is not possible. App measurement disabled");
    }
    for (;;)
    {
      zzCO();
      return;
      if ((!zzc.isPackageSide()) && (!TextUtils.isEmpty(zzBV().zzCl()))) {
        zzCE().zzCU();
      }
    }
  }
  
  public final zzm zzBV()
  {
    zza(this.zzboh);
    return this.zzboh;
  }
  
  public final zzae zzBX()
  {
    zza(this.zzbob);
    return this.zzbob;
  }
  
  public final zzs zzBY()
  {
    zza(this.zzbnZ);
    return this.zzbnZ;
  }
  
  public final zzr zzBZ()
  {
    zza(this.zzbnX);
    return this.zzbnX;
  }
  
  public final zzo zzBh()
  {
    zza(this.zzbnY);
    return this.zzbnY;
  }
  
  protected final boolean zzCC()
  {
    boolean bool1 = true;
    zziL();
    zzBY().checkOnWorkerThread();
    boolean bool2;
    if (this.zzbok == null)
    {
      if ((!zzBX().zzaZ("android.permission.INTERNET")) || (!zzBX().zzaZ("android.permission.ACCESS_NETWORK_STATE")) || (!AppMeasurementReceiver.zzac(this.mContext)) || (!AppMeasurementService.zzad(this.mContext))) {
        break label121;
      }
      bool2 = bool1;
      this.zzbok = Boolean.valueOf(bool2);
      if ((this.zzbok.booleanValue()) && (!zzc.isPackageSide())) {
        if (TextUtils.isEmpty(zzBV().zzCl())) {
          break label126;
        }
      }
    }
    for (;;)
    {
      this.zzbok = Boolean.valueOf(bool1);
      return this.zzbok.booleanValue();
      label121:
      bool2 = false;
      break;
      label126:
      bool1 = false;
    }
  }
  
  public final zzd zzCG()
  {
    zza(this.zzboc);
    return this.zzboc;
  }
  
  public final zzp zzCH()
  {
    zza(this.zzbod);
    return this.zzbod;
  }
  
  public final void zzCM()
  {
    zzBY().checkOnWorkerThread();
    zziL();
    Boolean localBoolean;
    if (!zzc.isPackageSide())
    {
      localBoolean = zzBZ().zzCA();
      if (localBoolean == null) {
        zzBh().zzbmZ.zzeB("Upload data called on the client side before use of service was decided");
      }
    }
    List localList1;
    do
    {
      String str1;
      do
      {
        return;
        if (localBoolean.booleanValue())
        {
          zzBh().zzbmW.zzeB("Upload called in the client side when service should be used");
          return;
        }
        zzBY().checkOnWorkerThread();
        if (this.zzbol != null) {}
        for (int i = 1; i != 0; i = 0)
        {
          zzBh().zzbmZ.zzeB("Uploading requested multiple times");
          return;
        }
        if (!zzCH().isNetworkConnected())
        {
          zzBh().zzbmZ.zzeB("Network not connected, ignoring upload request");
          zzCO();
          return;
        }
        long l1 = zzBZ().zzbnt.get();
        if (l1 != 0L) {
          zzBh().zzbnd.zzm("Uploading events. Elapsed time since last upload attempt (ms)", Long.valueOf(Math.abs(this.zzri.currentTimeMillis() - l1)));
        }
        str1 = zzCG().zzCb();
      } while (TextUtils.isEmpty(str1));
      int j = zzc.zzBL();
      int k = zzc.zzBM();
      localList1 = zzCG().zzf(str1, j, k);
    } while (localList1.isEmpty());
    Iterator localIterator = localList1.iterator();
    zzso.zzd localzzd2;
    do
    {
      if (!localIterator.hasNext()) {
        break;
      }
      localzzd2 = (zzso.zzd)((Pair)localIterator.next()).first;
    } while (TextUtils.isEmpty(localzzd2.zzbpr));
    for (String str2 = localzzd2.zzbpr;; str2 = null)
    {
      int n;
      if (str2 != null)
      {
        n = 0;
        if (n < localList1.size())
        {
          zzso.zzd localzzd1 = (zzso.zzd)((Pair)localList1.get(n)).first;
          if ((TextUtils.isEmpty(localzzd1.zzbpr)) || (localzzd1.zzbpr.equals(str2))) {}
        }
      }
      for (List localList2 = localList1.subList(0, n);; localList2 = localList1)
      {
        zzso.zzc localzzc = new zzso.zzc();
        localzzc.zzbpb = new zzso.zzd[localList2.size()];
        ArrayList localArrayList = new ArrayList(localList2.size());
        long l2 = this.zzri.currentTimeMillis();
        int m = 0;
        for (;;)
        {
          if (m < localzzc.zzbpb.length)
          {
            localzzc.zzbpb[m] = ((zzso.zzd)((Pair)localList2.get(m)).first);
            localArrayList.add(((Pair)localList2.get(m)).second);
            localzzc.zzbpb[m].zzbpq = Long.valueOf(zzc.zzBH());
            localzzc.zzbpb[m].zzbpg = Long.valueOf(l2);
            localzzc.zzbpb[m].zzbpw = Boolean.valueOf(zzc.isPackageSide());
            m++;
            continue;
            n++;
            break;
          }
        }
        byte[] arrayOfByte = zzBX().zza(localzzc);
        String str3 = zzc.zzBN();
        for (;;)
        {
          try
          {
            URL localURL = new URL(str3);
            boolean bool1 = localArrayList.isEmpty();
            boolean bool2 = false;
            if (!bool1) {
              bool2 = true;
            }
            com.google.android.gms.common.internal.zzx.zzab(bool2);
            if (this.zzbol != null)
            {
              zzBh().zzbmW.zzeB("Set uploading progress before finishing the previous upload");
              zzBZ().zzbnu.set(this.zzri.currentTimeMillis());
              zzCH().zza(localURL, arrayOfByte, new zzp.zza()
              {
                public final void zza(int paramAnonymousInt, Throwable paramAnonymousThrowable, byte[] paramAnonymousArrayOfByte)
                {
                  zzt.zza(zzt.this, paramAnonymousInt, paramAnonymousThrowable, paramAnonymousArrayOfByte);
                }
              });
              return;
            }
          }
          catch (MalformedURLException localMalformedURLException)
          {
            zzBh().zzbmW.zzm("Failed to parse upload URL. Not uploading", str3);
            return;
          }
          this.zzbol = new ArrayList(localArrayList);
        }
      }
    }
  }
  
  final void zzCO()
  {
    zzBY().checkOnWorkerThread();
    zziL();
    if ((!zzCC()) || (!zzCN()))
    {
      zzCJ().unregister();
      zzCK().cancel();
      return;
    }
    long l1 = this.zzri.currentTimeMillis();
    long l2 = zzc.zzBR();
    long l3 = zzc.zzBP();
    long l4 = zzBZ().zzbnt.get();
    long l5 = zzBZ().zzbnu.get();
    long l6 = zzCG().zza$6e791f8("select max(bundle_end_timestamp) from queue");
    long l9;
    if (l6 == 0L) {
      l9 = 0L;
    }
    while (l9 == 0L)
    {
      zzCJ().unregister();
      zzCK().cancel();
      return;
      long l7 = l1 - Math.abs(l6 - l1);
      long l8 = l2 + l7;
      if (!zzBX().zzh(l4, l3)) {
        l8 = l4 + l3;
      }
      if ((l5 != 0L) && (l5 >= l7))
      {
        for (int i = 0;; i++)
        {
          if (i >= zzc.zzBT()) {
            break label226;
          }
          l8 += (1 << i) * zzc.zzBS();
          if (l8 > l5)
          {
            l9 = l8;
            break;
          }
        }
        label226:
        l9 = 0L;
      }
      else
      {
        l9 = l8;
      }
    }
    if (!zzCH().isNetworkConnected())
    {
      zzq localzzq = zzCJ();
      localzzq.zzbkM.zziL();
      localzzq.zzbkM.zzBY().checkOnWorkerThread();
      if (!localzzq.zzQX)
      {
        localzzq.zzbkM.mContext.registerReceiver(localzzq, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        localzzq.zzQY = localzzq.zzbkM.zzCH().isNetworkConnected();
        localzzq.zzbkM.zzBh().zzbne.zzm("Registering connectivity change receiver. Network connected", Boolean.valueOf(localzzq.zzQY));
        localzzq.zzQX = true;
      }
      zzCK().cancel();
      return;
    }
    long l10 = zzBZ().zzbnv.get();
    long l11 = zzc.zzBO();
    if (!zzBX().zzh(l10, l11)) {
      l9 = Math.max(l9, l10 + l11);
    }
    zzCJ().unregister();
    long l12 = l9 - this.zzri.currentTimeMillis();
    if (l12 <= 0L)
    {
      zzCK().zzr(1L);
      return;
    }
    zzBh().zzbne.zzm("Upload scheduled in approximately ms", Long.valueOf(l12));
    zzCK().zzr(l12);
  }
  
  final void zzb(EventParcel paramEventParcel, AppMetadata paramAppMetadata)
  {
    zzBY().checkOnWorkerThread();
    zziL();
    com.google.android.gms.common.internal.zzx.zzcG(paramAppMetadata.packageName);
    if (TextUtils.isEmpty(paramAppMetadata.zzbmb)) {
      return;
    }
    zzBh().zzbne.zzm("Logging event", paramEventParcel);
    zzg localzzg1 = new zzg(this, paramEventParcel.zzbmA, paramAppMetadata.packageName, paramEventParcel.name, paramEventParcel.zzbmB, paramEventParcel.zzbmz.zzCk());
    zzCG().beginTransaction();
    for (;;)
    {
      zza localzza1;
      int j;
      try
      {
        zze(paramAppMetadata);
        zzh localzzh1 = zzCG().zzW(paramAppMetadata.packageName, localzzg1.mName);
        zzh localzzh2;
        Object localObject2;
        zzg[] arrayOfzzg;
        zzso.zzd localzzd;
        if (localzzh1 == null)
        {
          localzzh2 = new zzh(paramAppMetadata.packageName, localzzg1.mName, 1L, 1L, localzzg1.zzalM);
          localObject2 = localzzg1;
          zzCG().zza(localzzh2);
          arrayOfzzg = new zzg[] { localObject2 };
          com.google.android.gms.common.internal.zzx.zzC(paramAppMetadata);
          com.google.android.gms.common.internal.zzx.zzC(arrayOfzzg);
          zzBY().checkOnWorkerThread();
          localzzd = new zzso.zzd();
          localzzd.zzbpd = Integer.valueOf(1);
          localzzd.zzbpl = "android";
          localzzd.appId = paramAppMetadata.packageName;
          localzzd.zzbmc = paramAppMetadata.zzbmc;
          localzzd.zzbcL = paramAppMetadata.zzbcL;
          localzzd.zzbpp = Long.valueOf(paramAppMetadata.zzbmd);
          localzzd.zzbmb = paramAppMetadata.zzbmb;
          if (paramAppMetadata.zzbme == 0L)
          {
            localLong1 = null;
            localzzd.zzbpu = localLong1;
            Pair localPair = zzBZ().zzCw();
            if ((localPair.first != null) && (localPair.second != null))
            {
              localzzd.zzbpr = ((String)localPair.first);
              localzzd.zzbps = ((Boolean)localPair.second);
            }
            localzzd.zzbpm = zzCI().zzhj();
            localzzd.osVersion = zzCI().zzCh();
            localzzd.zzbpo = Integer.valueOf((int)zzCI().zzCi());
            localzzd.zzbpn = zzCI().zzCj();
            localzzd.zzbpq = null;
            localzzd.zzbpg = null;
            localzzd.zzbph = Long.valueOf(arrayOfzzg[0].zzalM);
            localzzd.zzbpi = Long.valueOf(arrayOfzzg[0].zzalM);
            localzza1 = zzCG().zzey(paramAppMetadata.packageName);
            if (localzza1 != null) {
              break label1185;
            }
            String str1 = paramAppMetadata.packageName;
            zzBZ();
            localzza2 = new zza(str1, zzr.zzCy(), paramAppMetadata.zzbmb, zzBZ().zzCx(), 0L, 0L, paramAppMetadata.zzbcL, paramAppMetadata.zzbmc, paramAppMetadata.zzbmd, paramAppMetadata.zzbme, paramAppMetadata.zzbmg);
            zzo localzzo = zzBh();
            long l1 = localzzd.zzbpi.longValue();
            com.google.android.gms.common.internal.zzx.zzC(localzzo);
            long l2 = 1L + localzza2.zzblV;
            if (l2 > 2147483647L)
            {
              localzzo.zzbmZ.zzeB("Bundle index overflow");
              l2 = 0L;
            }
            zza localzza3 = new zza(localzza2.zzbkS, localzza2.zzblS, localzza2.zzblT, localzza2.zzblU, l2, l1, localzza2.mAppVersion, localzza2.zzblX, localzza2.zzblY, localzza2.zzblZ, localzza2.zzbma);
            zzCG().zza(localzza3);
            localzzd.zzbpt = localzza3.zzblS;
            localzzd.zzbpv = Integer.valueOf((int)localzza3.zzblV);
            if (localzza2.zzblW != 0L) {
              continue;
            }
            localLong2 = null;
            localzzd.zzbpk = localLong2;
            localzzd.zzbpj = localzzd.zzbpk;
            List localList = zzCG().zzex(paramAppMetadata.packageName);
            localzzd.zzbpf = new zzso.zze[localList.size()];
            int i = 0;
            if (i >= localList.size()) {
              continue;
            }
            zzso.zze localzze = new zzso.zze();
            localzzd.zzbpf[i] = localzze;
            localzze.name = ((zzac)localList.get(i)).mName;
            localzze.zzbpy = Long.valueOf(((zzac)localList.get(i)).zzboR);
            zzBX().zza(localzze, ((zzac)localList.get(i)).zzMb);
            i++;
            continue;
          }
        }
        else
        {
          long l3 = localzzh1.zzbmv;
          zzg localzzg2 = new zzg(this, localzzg1.zzbmq, localzzg1.zzbkS, localzzg1.mName, localzzg1.zzalM, l3, localzzg1.zzbms);
          long l4 = localzzg2.zzalM;
          localzzh2 = new zzh(localzzh1.zzbkS, localzzh1.mName, 1L + localzzh1.zzbmt, 1L + localzzh1.zzbmu, l4);
          localObject2 = localzzg2;
          continue;
        }
        Long localLong1 = Long.valueOf(paramAppMetadata.zzbme);
        continue;
        Long localLong2 = Long.valueOf(localzza2.zzblW);
        continue;
        localzzd.zzbpe = new zzso.zza[1];
        j = 0;
        if (j <= 0)
        {
          zzso.zza localzza = new zzso.zza();
          localzzd.zzbpe[j] = localzza;
          localzza.name = arrayOfzzg[j].mName;
          localzza.zzboX = Long.valueOf(arrayOfzzg[j].zzalM);
          localzza.zzboW = new zzso.zzb[arrayOfzzg[j].zzbms.zzbmw.size()];
          Iterator localIterator = arrayOfzzg[j].zzbms.iterator();
          int k = 0;
          if (!localIterator.hasNext()) {
            break label1192;
          }
          String str2 = (String)localIterator.next();
          zzso.zzb localzzb = new zzso.zzb();
          zzso.zzb[] arrayOfzzb = localzza.zzboW;
          int m = k + 1;
          arrayOfzzb[k] = localzzb;
          localzzb.name = str2;
          Object localObject3 = arrayOfzzg[j].zzbms.zzbmw.get(str2);
          zzBX().zza(localzzb, localObject3);
          k = m;
          continue;
        }
        localzzd.zzbmf = zzBh().zzCv();
        zzCG().zza(localzzd);
        zzCG().setTransactionSuccessful();
        zzBh().zzbnd.zzm("Event logged", localObject2);
        zzCG().endTransaction();
        zzCO();
        return;
      }
      finally
      {
        zzCG().endTransaction();
      }
      label1185:
      zza localzza2 = localzza1;
      continue;
      label1192:
      j++;
    }
  }
  
  final void zzb(UserAttributeParcel paramUserAttributeParcel, AppMetadata paramAppMetadata)
  {
    zzBY().checkOnWorkerThread();
    zziL();
    if (TextUtils.isEmpty(paramAppMetadata.zzbmb)) {}
    Object localObject1;
    do
    {
      return;
      zzBX();
      zzae.zzeH(paramUserAttributeParcel.name);
      localObject1 = zzBX().zzp(paramUserAttributeParcel.name, paramUserAttributeParcel.getValue());
    } while (localObject1 == null);
    zzac localzzac = new zzac(paramAppMetadata.packageName, paramUserAttributeParcel.name, paramUserAttributeParcel.zzboS, localObject1);
    zzBh().zzbnd.zze("Setting user attribute", localzzac.mName, localObject1);
    zzCG().beginTransaction();
    try
    {
      zze(paramAppMetadata);
      zzCG().zza(localzzac);
      zzCG().setTransactionSuccessful();
      zzBh().zzbnd.zze("User attribute set", localzzac.mName, localzzac.zzMb);
      return;
    }
    finally
    {
      zzCG().endTransaction();
    }
  }
  
  final void zze(AppMetadata paramAppMetadata)
  {
    zzBY().checkOnWorkerThread();
    zziL();
    com.google.android.gms.common.internal.zzx.zzC(paramAppMetadata);
    com.google.android.gms.common.internal.zzx.zzcG(paramAppMetadata.packageName);
    Object localObject1 = zzCG().zzey(paramAppMetadata.packageName);
    String str1 = zzBZ().zzCx();
    int i;
    zza localzza2;
    if (localObject1 == null)
    {
      String str6 = paramAppMetadata.packageName;
      zzBZ();
      zza localzza5 = new zza(str6, zzr.zzCy(), paramAppMetadata.zzbmb, str1, 0L, 0L, paramAppMetadata.zzbcL, paramAppMetadata.zzbmc, paramAppMetadata.zzbmd, paramAppMetadata.zzbme, paramAppMetadata.zzbmg);
      i = 1;
      localObject1 = localzza5;
      if ((!TextUtils.isEmpty(paramAppMetadata.zzbmb)) && ((!paramAppMetadata.zzbmb.equals(((zza)localObject1).zzblT)) || (paramAppMetadata.zzbmd != ((zza)localObject1).zzblY)))
      {
        String str5 = paramAppMetadata.zzbmb;
        long l2 = paramAppMetadata.zzbmd;
        zza localzza4 = new zza(((zza)localObject1).zzbkS, ((zza)localObject1).zzblS, str5, ((zza)localObject1).zzblU, ((zza)localObject1).zzblV, ((zza)localObject1).zzblW, ((zza)localObject1).mAppVersion, ((zza)localObject1).zzblX, l2, ((zza)localObject1).zzblZ, ((zza)localObject1).zzbma);
        i = 1;
        localObject1 = localzza4;
      }
      if ((!TextUtils.isEmpty(paramAppMetadata.zzbcL)) && ((!paramAppMetadata.zzbcL.equals(((zza)localObject1).mAppVersion)) || (!paramAppMetadata.zzbmc.equals(((zza)localObject1).zzblX))))
      {
        String str3 = paramAppMetadata.zzbcL;
        String str4 = paramAppMetadata.zzbmc;
        zza localzza3 = new zza(((zza)localObject1).zzbkS, ((zza)localObject1).zzblS, ((zza)localObject1).zzblT, ((zza)localObject1).zzblU, ((zza)localObject1).zzblV, ((zza)localObject1).zzblW, str3, str4, ((zza)localObject1).zzblY, ((zza)localObject1).zzblZ, ((zza)localObject1).zzbma);
        i = 1;
        localObject1 = localzza3;
      }
      if (paramAppMetadata.zzbme == ((zza)localObject1).zzblZ) {
        break label632;
      }
      long l1 = paramAppMetadata.zzbme;
      localzza2 = new zza(((zza)localObject1).zzbkS, ((zza)localObject1).zzblS, ((zza)localObject1).zzblT, ((zza)localObject1).zzblU, ((zza)localObject1).zzblV, ((zza)localObject1).zzblW, ((zza)localObject1).mAppVersion, ((zza)localObject1).zzblX, ((zza)localObject1).zzblY, l1, ((zza)localObject1).zzbma);
      i = 1;
    }
    label632:
    for (Object localObject2 = localzza2;; localObject2 = localObject1)
    {
      Object localObject3;
      if (paramAppMetadata.zzbmg != ((zza)localObject2).zzbma)
      {
        boolean bool2 = paramAppMetadata.zzbmg;
        localObject3 = new zza(((zza)localObject2).zzbkS, ((zza)localObject2).zzblS, ((zza)localObject2).zzblT, ((zza)localObject2).zzblU, ((zza)localObject2).zzblV, ((zza)localObject2).zzblW, ((zza)localObject2).mAppVersion, ((zza)localObject2).zzblX, ((zza)localObject2).zzblY, ((zza)localObject2).zzblZ, bool2);
        i = 1;
      }
      for (;;)
      {
        if (i != 0) {
          zzCG().zza((zza)localObject3);
        }
        return;
        boolean bool1 = str1.equals(((zza)localObject1).zzblU);
        i = 0;
        if (bool1) {
          break;
        }
        zzBZ();
        String str2 = zzr.zzCy();
        zza localzza1 = new zza(((zza)localObject1).zzbkS, str2, ((zza)localObject1).zzblT, str1, ((zza)localObject1).zzblV, ((zza)localObject1).zzblW, ((zza)localObject1).mAppVersion, ((zza)localObject1).zzblX, ((zza)localObject1).zzblY, ((zza)localObject1).zzblZ, ((zza)localObject1).zzbma);
        i = 1;
        localObject1 = localzza1;
        break;
        localObject3 = localObject2;
      }
    }
  }
  
  final void zziL()
  {
    if (!this.zzPl) {
      throw new IllegalStateException("AppMeasurement is not initialized");
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzt
 * JD-Core Version:    0.7.0.1
 */