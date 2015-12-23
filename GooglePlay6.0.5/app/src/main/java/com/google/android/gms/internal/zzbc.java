package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zzd.zza;

public abstract interface zzbc
  extends IInterface
{
  public abstract IBinder zza(String paramString, zzd paramzzd)
    throws RemoteException;
  
  public abstract IBinder zzb(String paramString, zzd paramzzd)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzbc
  {
    public static zzbc zzf(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.ads.adshield.internal.IAdShieldCreator");
      if ((localIInterface != null) && ((localIInterface instanceof zzbc))) {
        return (zzbc)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.ads.adshield.internal.IAdShieldCreator");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.ads.adshield.internal.IAdShieldCreator");
        IBinder localIBinder2 = zza(paramParcel1.readString(), zzd.zza.zzdb(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        paramParcel2.writeStrongBinder(localIBinder2);
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.ads.adshield.internal.IAdShieldCreator");
      IBinder localIBinder1 = zzb(paramParcel1.readString(), zzd.zza.zzdb(paramParcel1.readStrongBinder()));
      paramParcel2.writeNoException();
      paramParcel2.writeStrongBinder(localIBinder1);
      return true;
    }
    
    private static final class zza
      implements zzbc
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
      public final IBinder zza(String paramString, zzd paramzzd)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore 4
        //   9: aload_3
        //   10: ldc 29
        //   12: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   15: aload_3
        //   16: aload_1
        //   17: invokevirtual 36	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   20: aload_2
        //   21: ifnull +56 -> 77
        //   24: aload_2
        //   25: invokeinterface 40 1 0
        //   30: astore 6
        //   32: aload_3
        //   33: aload 6
        //   35: invokevirtual 43	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   38: aload_0
        //   39: getfield 15	com/google/android/gms/internal/zzbc$zza$zza:zzop	Landroid/os/IBinder;
        //   42: iconst_1
        //   43: aload_3
        //   44: aload 4
        //   46: iconst_0
        //   47: invokeinterface 49 5 0
        //   52: pop
        //   53: aload 4
        //   55: invokevirtual 52	android/os/Parcel:readException	()V
        //   58: aload 4
        //   60: invokevirtual 55	android/os/Parcel:readStrongBinder	()Landroid/os/IBinder;
        //   63: astore 8
        //   65: aload 4
        //   67: invokevirtual 58	android/os/Parcel:recycle	()V
        //   70: aload_3
        //   71: invokevirtual 58	android/os/Parcel:recycle	()V
        //   74: aload 8
        //   76: areturn
        //   77: aconst_null
        //   78: astore 6
        //   80: goto -48 -> 32
        //   83: astore 5
        //   85: aload 4
        //   87: invokevirtual 58	android/os/Parcel:recycle	()V
        //   90: aload_3
        //   91: invokevirtual 58	android/os/Parcel:recycle	()V
        //   94: aload 5
        //   96: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	97	0	this	zza
        //   0	97	1	paramString	String
        //   0	97	2	paramzzd	zzd
        //   3	88	3	localParcel1	Parcel
        //   7	79	4	localParcel2	Parcel
        //   83	12	5	localObject	Object
        //   30	49	6	localIBinder1	IBinder
        //   63	12	8	localIBinder2	IBinder
        // Exception table:
        //   from	to	target	type
        //   9	20	83	finally
        //   24	32	83	finally
        //   32	65	83	finally
      }
      
      /* Error */
      public final IBinder zzb(String paramString, zzd paramzzd)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore 4
        //   9: aload_3
        //   10: ldc 29
        //   12: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   15: aload_3
        //   16: aload_1
        //   17: invokevirtual 36	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   20: aload_2
        //   21: ifnull +56 -> 77
        //   24: aload_2
        //   25: invokeinterface 40 1 0
        //   30: astore 6
        //   32: aload_3
        //   33: aload 6
        //   35: invokevirtual 43	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   38: aload_0
        //   39: getfield 15	com/google/android/gms/internal/zzbc$zza$zza:zzop	Landroid/os/IBinder;
        //   42: iconst_2
        //   43: aload_3
        //   44: aload 4
        //   46: iconst_0
        //   47: invokeinterface 49 5 0
        //   52: pop
        //   53: aload 4
        //   55: invokevirtual 52	android/os/Parcel:readException	()V
        //   58: aload 4
        //   60: invokevirtual 55	android/os/Parcel:readStrongBinder	()Landroid/os/IBinder;
        //   63: astore 8
        //   65: aload 4
        //   67: invokevirtual 58	android/os/Parcel:recycle	()V
        //   70: aload_3
        //   71: invokevirtual 58	android/os/Parcel:recycle	()V
        //   74: aload 8
        //   76: areturn
        //   77: aconst_null
        //   78: astore 6
        //   80: goto -48 -> 32
        //   83: astore 5
        //   85: aload 4
        //   87: invokevirtual 58	android/os/Parcel:recycle	()V
        //   90: aload_3
        //   91: invokevirtual 58	android/os/Parcel:recycle	()V
        //   94: aload 5
        //   96: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	97	0	this	zza
        //   0	97	1	paramString	String
        //   0	97	2	paramzzd	zzd
        //   3	88	3	localParcel1	Parcel
        //   7	79	4	localParcel2	Parcel
        //   83	12	5	localObject	Object
        //   30	49	6	localIBinder1	IBinder
        //   63	12	8	localIBinder2	IBinder
        // Exception table:
        //   from	to	target	type
        //   9	20	83	finally
        //   24	32	83	finally
        //   32	65	83	finally
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzbc
 * JD-Core Version:    0.7.0.1
 */