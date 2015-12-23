package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.zzd;

public final class zzc
  extends zzv
{
  static final String zzbmh = String.valueOf(GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE / 1000).replaceAll("(\\d+)(\\d)(\\d\\d)", "$1.$2.$3");
  private Boolean zzQr;
  
  zzc(zzt paramzzt)
  {
    super(paramzzt);
  }
  
  static long getConnectionCacheTimeMillis()
  {
    return ((Long)zzk.zzbmS.get()).longValue();
  }
  
  public static long getMonitoringSamplePeriodMillis()
  {
    return Math.max(0L, ((Long)zzk.zzbmG.get()).longValue());
  }
  
  public static String getStoreDatabaseFileName()
  {
    return "google_app_measurement.db";
  }
  
  public static String getStoreDatabaseSecondaryFileName()
  {
    return "google_app_measurement2.db";
  }
  
  public static boolean isPackageSide()
  {
    return zzd.zzasZ;
  }
  
  static int zzBA()
  {
    return 36;
  }
  
  static int zzBB()
  {
    return 2048;
  }
  
  static int zzBC()
  {
    return 20;
  }
  
  static long zzBD()
  {
    return 3600000L;
  }
  
  static long zzBE()
  {
    return 60000L;
  }
  
  static long zzBF()
  {
    return 61000L;
  }
  
  static long zzBG()
  {
    return ((Long)zzk.zzbmF.get()).longValue();
  }
  
  public static long zzBH()
  {
    return GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE / 1000;
  }
  
  public static long zzBI()
  {
    return ((Long)zzk.zzbmR.get()).longValue();
  }
  
  public static long zzBJ()
  {
    return ((Long)zzk.zzbmN.get()).longValue();
  }
  
  public static long zzBK()
  {
    return 20L;
  }
  
  public static int zzBL()
  {
    return ((Integer)zzk.zzbmH.get()).intValue();
  }
  
  public static int zzBM()
  {
    return Math.max(0, ((Integer)zzk.zzbmI.get()).intValue());
  }
  
  public static String zzBN()
  {
    return (String)zzk.zzbmJ.get();
  }
  
  public static long zzBO()
  {
    return Math.max(0L, ((Long)zzk.zzbmK.get()).longValue());
  }
  
  public static long zzBP()
  {
    return Math.max(0L, ((Long)zzk.zzbmM.get()).longValue());
  }
  
  public static long zzBQ()
  {
    return ((Long)zzk.zzbmL.get()).longValue();
  }
  
  public static long zzBR()
  {
    return Math.max(0L, ((Long)zzk.zzbmO.get()).longValue());
  }
  
  public static long zzBS()
  {
    return Math.max(0L, ((Long)zzk.zzbmP.get()).longValue());
  }
  
  public static int zzBT()
  {
    return Math.min(20, Math.max(0, ((Integer)zzk.zzbmQ.get()).intValue()));
  }
  
  static String zzBv()
  {
    return (String)zzk.zzbmE.get();
  }
  
  public static int zzBx()
  {
    return 24;
  }
  
  static int zzBy()
  {
    return 36;
  }
  
  static int zzBz()
  {
    return 256;
  }
  
  /* Error */
  public final boolean isMainProcess()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 177	com/google/android/gms/measurement/internal/zzc:zzQr	Ljava/lang/Boolean;
    //   4: ifnonnull +95 -> 99
    //   7: aload_0
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield 177	com/google/android/gms/measurement/internal/zzc:zzQr	Ljava/lang/Boolean;
    //   13: ifnonnull +84 -> 97
    //   16: aload_0
    //   17: invokespecial 174	com/google/android/gms/measurement/internal/zzv:getContext	()Landroid/content/Context;
    //   20: invokevirtual 183	android/content/Context:getApplicationInfo	()Landroid/content/pm/ApplicationInfo;
    //   23: astore_2
    //   24: aload_0
    //   25: invokespecial 174	com/google/android/gms/measurement/internal/zzv:getContext	()Landroid/content/Context;
    //   28: invokestatic 188	android/os/Process:myPid	()I
    //   31: invokestatic 194	com/google/android/gms/common/util/zzr:zzi	(Landroid/content/Context;I)Ljava/lang/String;
    //   34: astore_3
    //   35: aload_2
    //   36: ifnull +35 -> 71
    //   39: aload_2
    //   40: getfield 199	android/content/pm/ApplicationInfo:processName	Ljava/lang/String;
    //   43: astore 4
    //   45: aload 4
    //   47: ifnull +60 -> 107
    //   50: aload 4
    //   52: aload_3
    //   53: invokevirtual 203	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   56: ifeq +51 -> 107
    //   59: iconst_1
    //   60: istore 5
    //   62: aload_0
    //   63: iload 5
    //   65: invokestatic 208	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   68: putfield 177	com/google/android/gms/measurement/internal/zzc:zzQr	Ljava/lang/Boolean;
    //   71: aload_0
    //   72: getfield 177	com/google/android/gms/measurement/internal/zzc:zzQr	Ljava/lang/Boolean;
    //   75: ifnonnull +22 -> 97
    //   78: aload_0
    //   79: getstatic 211	java/lang/Boolean:TRUE	Ljava/lang/Boolean;
    //   82: putfield 177	com/google/android/gms/measurement/internal/zzc:zzQr	Ljava/lang/Boolean;
    //   85: aload_0
    //   86: invokespecial 215	com/google/android/gms/measurement/internal/zzv:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   89: getfield 221	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   92: ldc 223
    //   94: invokevirtual 229	com/google/android/gms/measurement/internal/zzo$zza:zzeB	(Ljava/lang/String;)V
    //   97: aload_0
    //   98: monitorexit
    //   99: aload_0
    //   100: getfield 177	com/google/android/gms/measurement/internal/zzc:zzQr	Ljava/lang/Boolean;
    //   103: invokevirtual 232	java/lang/Boolean:booleanValue	()Z
    //   106: ireturn
    //   107: iconst_0
    //   108: istore 5
    //   110: goto -48 -> 62
    //   113: astore_1
    //   114: aload_0
    //   115: monitorexit
    //   116: aload_1
    //   117: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	118	0	this	zzc
    //   113	4	1	localObject	java.lang.Object
    //   23	17	2	localApplicationInfo	android.content.pm.ApplicationInfo
    //   34	19	3	str1	String
    //   43	8	4	str2	String
    //   60	49	5	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   9	35	113	finally
    //   39	45	113	finally
    //   50	59	113	finally
    //   62	71	113	finally
    //   71	97	113	finally
    //   97	99	113	finally
    //   114	116	113	finally
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzc
 * JD-Core Version:    0.7.0.1
 */