package com.google.android.gms.ads.internal.client;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zzd.zza;
import com.google.android.gms.internal.zzex;
import com.google.android.gms.internal.zzex.zza;

public abstract interface zzt
  extends IInterface
{
  public abstract IBinder zza(zzd paramzzd, AdSizeParcel paramAdSizeParcel, String paramString, zzex paramzzex, int paramInt)
    throws RemoteException;
  
  public abstract IBinder zza(zzd paramzzd, AdSizeParcel paramAdSizeParcel, String paramString, zzex paramzzex, int paramInt1, int paramInt2)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzt
  {
    public static zzt zzp(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdManagerCreator");
      if ((localIInterface != null) && ((localIInterface instanceof zzt))) {
        return (zzt)localIInterface;
      }
      return new zza(paramIBinder);
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      default: 
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902: 
        paramParcel2.writeString("com.google.android.gms.ads.internal.client.IAdManagerCreator");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.ads.internal.client.IAdManagerCreator");
        zzd localzzd2 = zzd.zza.zzdb(paramParcel1.readStrongBinder());
        int j = paramParcel1.readInt();
        AdSizeParcel localAdSizeParcel2 = null;
        if (j != 0) {
          localAdSizeParcel2 = zzi.zzd(paramParcel1);
        }
        IBinder localIBinder2 = zza(localzzd2, localAdSizeParcel2, paramParcel1.readString(), zzex.zza.zzI(paramParcel1.readStrongBinder()), paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeStrongBinder(localIBinder2);
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.ads.internal.client.IAdManagerCreator");
      zzd localzzd1 = zzd.zza.zzdb(paramParcel1.readStrongBinder());
      int i = paramParcel1.readInt();
      AdSizeParcel localAdSizeParcel1 = null;
      if (i != 0) {
        localAdSizeParcel1 = zzi.zzd(paramParcel1);
      }
      IBinder localIBinder1 = zza(localzzd1, localAdSizeParcel1, paramParcel1.readString(), zzex.zza.zzI(paramParcel1.readStrongBinder()), paramParcel1.readInt(), paramParcel1.readInt());
      paramParcel2.writeNoException();
      paramParcel2.writeStrongBinder(localIBinder1);
      return true;
    }
    
    private static final class zza
      implements zzt
    {
      private IBinder zzop;
      
      zza(IBinder paramIBinder)
      {
        this.zzop = paramIBinder;
      }
      
      public final IBinder asBinder()
      {
        return this.zzop;
      }
      
      /* Error */
      public final IBinder zza(zzd paramzzd, AdSizeParcel paramAdSizeParcel, String paramString, zzex paramzzex, int paramInt)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore 6
        //   5: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   8: astore 7
        //   10: aload 6
        //   12: ldc 29
        //   14: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   17: aload_1
        //   18: ifnull +113 -> 131
        //   21: aload_1
        //   22: invokeinterface 37 1 0
        //   27: astore 9
        //   29: aload 6
        //   31: aload 9
        //   33: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   36: aload_2
        //   37: ifnull +100 -> 137
        //   40: aload 6
        //   42: iconst_1
        //   43: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   46: aload_2
        //   47: aload 6
        //   49: iconst_0
        //   50: invokevirtual 50	com/google/android/gms/ads/internal/client/AdSizeParcel:writeToParcel	(Landroid/os/Parcel;I)V
        //   53: aload 6
        //   55: aload_3
        //   56: invokevirtual 53	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   59: aconst_null
        //   60: astore 10
        //   62: aload 4
        //   64: ifnull +12 -> 76
        //   67: aload 4
        //   69: invokeinterface 56 1 0
        //   74: astore 10
        //   76: aload 6
        //   78: aload 10
        //   80: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   83: aload 6
        //   85: iload 5
        //   87: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   90: aload_0
        //   91: getfield 15	com/google/android/gms/ads/internal/client/zzt$zza$zza:zzop	Landroid/os/IBinder;
        //   94: iconst_1
        //   95: aload 6
        //   97: aload 7
        //   99: iconst_0
        //   100: invokeinterface 62 5 0
        //   105: pop
        //   106: aload 7
        //   108: invokevirtual 65	android/os/Parcel:readException	()V
        //   111: aload 7
        //   113: invokevirtual 68	android/os/Parcel:readStrongBinder	()Landroid/os/IBinder;
        //   116: astore 12
        //   118: aload 7
        //   120: invokevirtual 71	android/os/Parcel:recycle	()V
        //   123: aload 6
        //   125: invokevirtual 71	android/os/Parcel:recycle	()V
        //   128: aload 12
        //   130: areturn
        //   131: aconst_null
        //   132: astore 9
        //   134: goto -105 -> 29
        //   137: aload 6
        //   139: iconst_0
        //   140: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   143: goto -90 -> 53
        //   146: astore 8
        //   148: aload 7
        //   150: invokevirtual 71	android/os/Parcel:recycle	()V
        //   153: aload 6
        //   155: invokevirtual 71	android/os/Parcel:recycle	()V
        //   158: aload 8
        //   160: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	161	0	this	zza
        //   0	161	1	paramzzd	zzd
        //   0	161	2	paramAdSizeParcel	AdSizeParcel
        //   0	161	3	paramString	String
        //   0	161	4	paramzzex	zzex
        //   0	161	5	paramInt	int
        //   3	151	6	localParcel1	Parcel
        //   8	141	7	localParcel2	Parcel
        //   146	13	8	localObject	Object
        //   27	106	9	localIBinder1	IBinder
        //   60	19	10	localIBinder2	IBinder
        //   116	13	12	localIBinder3	IBinder
        // Exception table:
        //   from	to	target	type
        //   10	17	146	finally
        //   21	29	146	finally
        //   29	36	146	finally
        //   40	53	146	finally
        //   53	59	146	finally
        //   67	76	146	finally
        //   76	118	146	finally
        //   137	143	146	finally
      }
      
      /* Error */
      public final IBinder zza(zzd paramzzd, AdSizeParcel paramAdSizeParcel, String paramString, zzex paramzzex, int paramInt1, int paramInt2)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore 7
        //   5: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   8: astore 8
        //   10: aload 7
        //   12: ldc 29
        //   14: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   17: aload_1
        //   18: ifnull +120 -> 138
        //   21: aload_1
        //   22: invokeinterface 37 1 0
        //   27: astore 10
        //   29: aload 7
        //   31: aload 10
        //   33: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   36: aload_2
        //   37: ifnull +107 -> 144
        //   40: aload 7
        //   42: iconst_1
        //   43: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   46: aload_2
        //   47: aload 7
        //   49: iconst_0
        //   50: invokevirtual 50	com/google/android/gms/ads/internal/client/AdSizeParcel:writeToParcel	(Landroid/os/Parcel;I)V
        //   53: aload 7
        //   55: aload_3
        //   56: invokevirtual 53	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   59: aconst_null
        //   60: astore 11
        //   62: aload 4
        //   64: ifnull +12 -> 76
        //   67: aload 4
        //   69: invokeinterface 56 1 0
        //   74: astore 11
        //   76: aload 7
        //   78: aload 11
        //   80: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   83: aload 7
        //   85: iload 5
        //   87: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   90: aload 7
        //   92: iload 6
        //   94: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   97: aload_0
        //   98: getfield 15	com/google/android/gms/ads/internal/client/zzt$zza$zza:zzop	Landroid/os/IBinder;
        //   101: iconst_2
        //   102: aload 7
        //   104: aload 8
        //   106: iconst_0
        //   107: invokeinterface 62 5 0
        //   112: pop
        //   113: aload 8
        //   115: invokevirtual 65	android/os/Parcel:readException	()V
        //   118: aload 8
        //   120: invokevirtual 68	android/os/Parcel:readStrongBinder	()Landroid/os/IBinder;
        //   123: astore 13
        //   125: aload 8
        //   127: invokevirtual 71	android/os/Parcel:recycle	()V
        //   130: aload 7
        //   132: invokevirtual 71	android/os/Parcel:recycle	()V
        //   135: aload 13
        //   137: areturn
        //   138: aconst_null
        //   139: astore 10
        //   141: goto -112 -> 29
        //   144: aload 7
        //   146: iconst_0
        //   147: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   150: goto -97 -> 53
        //   153: astore 9
        //   155: aload 8
        //   157: invokevirtual 71	android/os/Parcel:recycle	()V
        //   160: aload 7
        //   162: invokevirtual 71	android/os/Parcel:recycle	()V
        //   165: aload 9
        //   167: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	168	0	this	zza
        //   0	168	1	paramzzd	zzd
        //   0	168	2	paramAdSizeParcel	AdSizeParcel
        //   0	168	3	paramString	String
        //   0	168	4	paramzzex	zzex
        //   0	168	5	paramInt1	int
        //   0	168	6	paramInt2	int
        //   3	158	7	localParcel1	Parcel
        //   8	148	8	localParcel2	Parcel
        //   153	13	9	localObject	Object
        //   27	113	10	localIBinder1	IBinder
        //   60	19	11	localIBinder2	IBinder
        //   123	13	13	localIBinder3	IBinder
        // Exception table:
        //   from	to	target	type
        //   10	17	153	finally
        //   21	29	153	finally
        //   29	36	153	finally
        //   40	53	153	finally
        //   53	59	153	finally
        //   67	76	153	finally
        //   76	125	153	finally
        //   144	150	153	finally
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.client.zzt
 * JD-Core Version:    0.7.0.1
 */