package com.google.android.gms.ads.internal.reward.client;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zzd.zza;
import com.google.android.gms.internal.zzex;
import com.google.android.gms.internal.zzex.zza;

public abstract interface zzc
  extends IInterface
{
  public abstract IBinder zza(zzd paramzzd, zzex paramzzex, int paramInt)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzc
  {
    public static zzc zzaf(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.ads.internal.reward.client.IRewardedVideoAdCreator");
      if ((localIInterface != null) && ((localIInterface instanceof zzc))) {
        return (zzc)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.ads.internal.reward.client.IRewardedVideoAdCreator");
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.ads.internal.reward.client.IRewardedVideoAdCreator");
      IBinder localIBinder = zza(zzd.zza.zzdb(paramParcel1.readStrongBinder()), zzex.zza.zzI(paramParcel1.readStrongBinder()), paramParcel1.readInt());
      paramParcel2.writeNoException();
      paramParcel2.writeStrongBinder(localIBinder);
      return true;
    }
    
    private static final class zza
      implements zzc
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
      public final IBinder zza(zzd paramzzd, zzex paramzzex, int paramInt)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore 4
        //   5: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   8: astore 5
        //   10: aload 4
        //   12: ldc 29
        //   14: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   17: aload_1
        //   18: ifnull +87 -> 105
        //   21: aload_1
        //   22: invokeinterface 37 1 0
        //   27: astore 7
        //   29: aload 4
        //   31: aload 7
        //   33: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   36: aconst_null
        //   37: astore 8
        //   39: aload_2
        //   40: ifnull +11 -> 51
        //   43: aload_2
        //   44: invokeinterface 43 1 0
        //   49: astore 8
        //   51: aload 4
        //   53: aload 8
        //   55: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   58: aload 4
        //   60: iload_3
        //   61: invokevirtual 47	android/os/Parcel:writeInt	(I)V
        //   64: aload_0
        //   65: getfield 15	com/google/android/gms/ads/internal/reward/client/zzc$zza$zza:zzop	Landroid/os/IBinder;
        //   68: iconst_1
        //   69: aload 4
        //   71: aload 5
        //   73: iconst_0
        //   74: invokeinterface 53 5 0
        //   79: pop
        //   80: aload 5
        //   82: invokevirtual 56	android/os/Parcel:readException	()V
        //   85: aload 5
        //   87: invokevirtual 59	android/os/Parcel:readStrongBinder	()Landroid/os/IBinder;
        //   90: astore 10
        //   92: aload 5
        //   94: invokevirtual 62	android/os/Parcel:recycle	()V
        //   97: aload 4
        //   99: invokevirtual 62	android/os/Parcel:recycle	()V
        //   102: aload 10
        //   104: areturn
        //   105: aconst_null
        //   106: astore 7
        //   108: goto -79 -> 29
        //   111: astore 6
        //   113: aload 5
        //   115: invokevirtual 62	android/os/Parcel:recycle	()V
        //   118: aload 4
        //   120: invokevirtual 62	android/os/Parcel:recycle	()V
        //   123: aload 6
        //   125: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	126	0	this	zza
        //   0	126	1	paramzzd	zzd
        //   0	126	2	paramzzex	zzex
        //   0	126	3	paramInt	int
        //   3	116	4	localParcel1	Parcel
        //   8	106	5	localParcel2	Parcel
        //   111	13	6	localObject	Object
        //   27	80	7	localIBinder1	IBinder
        //   37	17	8	localIBinder2	IBinder
        //   90	13	10	localIBinder3	IBinder
        // Exception table:
        //   from	to	target	type
        //   10	17	111	finally
        //   21	29	111	finally
        //   29	36	111	finally
        //   43	51	111	finally
        //   51	92	111	finally
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.reward.client.zzc
 * JD-Core Version:    0.7.0.1
 */