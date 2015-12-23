package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zzd.zza;

public abstract interface zzcr
  extends IInterface
{
  public abstract IBinder zza(zzd paramzzd1, zzd paramzzd2, zzd paramzzd3, int paramInt)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzcr
  {
    public static zzcr zzz(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.ads.internal.formats.client.INativeAdViewDelegateCreator");
      if ((localIInterface != null) && ((localIInterface instanceof zzcr))) {
        return (zzcr)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.ads.internal.formats.client.INativeAdViewDelegateCreator");
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.ads.internal.formats.client.INativeAdViewDelegateCreator");
      IBinder localIBinder = zza(zzd.zza.zzdb(paramParcel1.readStrongBinder()), zzd.zza.zzdb(paramParcel1.readStrongBinder()), zzd.zza.zzdb(paramParcel1.readStrongBinder()), paramParcel1.readInt());
      paramParcel2.writeNoException();
      paramParcel2.writeStrongBinder(localIBinder);
      return true;
    }
    
    private static final class zza
      implements zzcr
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
      public final IBinder zza(zzd paramzzd1, zzd paramzzd2, zzd paramzzd3, int paramInt)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore 5
        //   5: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   8: astore 6
        //   10: aload 5
        //   12: ldc 29
        //   14: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   17: aload_1
        //   18: ifnull +107 -> 125
        //   21: aload_1
        //   22: invokeinterface 37 1 0
        //   27: astore 8
        //   29: aload 5
        //   31: aload 8
        //   33: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   36: aload_2
        //   37: ifnull +94 -> 131
        //   40: aload_2
        //   41: invokeinterface 37 1 0
        //   46: astore 9
        //   48: aload 5
        //   50: aload 9
        //   52: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   55: aconst_null
        //   56: astore 10
        //   58: aload_3
        //   59: ifnull +11 -> 70
        //   62: aload_3
        //   63: invokeinterface 37 1 0
        //   68: astore 10
        //   70: aload 5
        //   72: aload 10
        //   74: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   77: aload 5
        //   79: iload 4
        //   81: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   84: aload_0
        //   85: getfield 15	com/google/android/gms/internal/zzcr$zza$zza:zzop	Landroid/os/IBinder;
        //   88: iconst_1
        //   89: aload 5
        //   91: aload 6
        //   93: iconst_0
        //   94: invokeinterface 50 5 0
        //   99: pop
        //   100: aload 6
        //   102: invokevirtual 53	android/os/Parcel:readException	()V
        //   105: aload 6
        //   107: invokevirtual 56	android/os/Parcel:readStrongBinder	()Landroid/os/IBinder;
        //   110: astore 12
        //   112: aload 6
        //   114: invokevirtual 59	android/os/Parcel:recycle	()V
        //   117: aload 5
        //   119: invokevirtual 59	android/os/Parcel:recycle	()V
        //   122: aload 12
        //   124: areturn
        //   125: aconst_null
        //   126: astore 8
        //   128: goto -99 -> 29
        //   131: aconst_null
        //   132: astore 9
        //   134: goto -86 -> 48
        //   137: astore 7
        //   139: aload 6
        //   141: invokevirtual 59	android/os/Parcel:recycle	()V
        //   144: aload 5
        //   146: invokevirtual 59	android/os/Parcel:recycle	()V
        //   149: aload 7
        //   151: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	152	0	this	zza
        //   0	152	1	paramzzd1	zzd
        //   0	152	2	paramzzd2	zzd
        //   0	152	3	paramzzd3	zzd
        //   0	152	4	paramInt	int
        //   3	142	5	localParcel1	Parcel
        //   8	132	6	localParcel2	Parcel
        //   137	13	7	localObject	Object
        //   27	100	8	localIBinder1	IBinder
        //   46	87	9	localIBinder2	IBinder
        //   56	17	10	localIBinder3	IBinder
        //   110	13	12	localIBinder4	IBinder
        // Exception table:
        //   from	to	target	type
        //   10	17	137	finally
        //   21	29	137	finally
        //   29	36	137	finally
        //   40	48	137	finally
        //   48	55	137	finally
        //   62	70	137	finally
        //   70	112	137	finally
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzcr
 * JD-Core Version:    0.7.0.1
 */