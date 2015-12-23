package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface zzov
  extends IInterface
{
  public abstract void zza(zzou paramzzou)
    throws RemoteException;
  
  public abstract void zza(zzou paramzzou, String[] paramArrayOfString)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzov
  {
    public static zzov zzcD(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.deviceconnection.internal.IDeviceConnectionService");
      if ((localIInterface != null) && ((localIInterface instanceof zzov))) {
        return (zzov)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.deviceconnection.internal.IDeviceConnectionService");
        return true;
      case 1001: 
        paramParcel1.enforceInterface("com.google.android.gms.deviceconnection.internal.IDeviceConnectionService");
        zza(zzou.zza.zzcC(paramParcel1.readStrongBinder()), paramParcel1.createStringArray());
        paramParcel2.writeNoException();
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.deviceconnection.internal.IDeviceConnectionService");
      zza(zzou.zza.zzcC(paramParcel1.readStrongBinder()));
      paramParcel2.writeNoException();
      return true;
    }
    
    private static final class zza
      implements zzov
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
      public final void zza(zzou paramzzou)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_3
        //   8: aload_2
        //   9: ldc 29
        //   11: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_1
        //   15: ifnull +46 -> 61
        //   18: aload_1
        //   19: invokeinterface 37 1 0
        //   24: astore 5
        //   26: aload_2
        //   27: aload 5
        //   29: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   32: aload_0
        //   33: getfield 15	com/google/android/gms/internal/zzov$zza$zza:zzop	Landroid/os/IBinder;
        //   36: sipush 1002
        //   39: aload_2
        //   40: aload_3
        //   41: iconst_0
        //   42: invokeinterface 46 5 0
        //   47: pop
        //   48: aload_3
        //   49: invokevirtual 49	android/os/Parcel:readException	()V
        //   52: aload_3
        //   53: invokevirtual 52	android/os/Parcel:recycle	()V
        //   56: aload_2
        //   57: invokevirtual 52	android/os/Parcel:recycle	()V
        //   60: return
        //   61: aconst_null
        //   62: astore 5
        //   64: goto -38 -> 26
        //   67: astore 4
        //   69: aload_3
        //   70: invokevirtual 52	android/os/Parcel:recycle	()V
        //   73: aload_2
        //   74: invokevirtual 52	android/os/Parcel:recycle	()V
        //   77: aload 4
        //   79: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	80	0	this	zza
        //   0	80	1	paramzzou	zzou
        //   3	71	2	localParcel1	Parcel
        //   7	63	3	localParcel2	Parcel
        //   67	11	4	localObject	Object
        //   24	39	5	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   8	14	67	finally
        //   18	26	67	finally
        //   26	52	67	finally
      }
      
      /* Error */
      public final void zza(zzou paramzzou, String[] paramArrayOfString)
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
        //   15: aload_1
        //   16: ifnull +54 -> 70
        //   19: aload_1
        //   20: invokeinterface 37 1 0
        //   25: astore 6
        //   27: aload_3
        //   28: aload 6
        //   30: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   33: aload_3
        //   34: aload_2
        //   35: invokevirtual 57	android/os/Parcel:writeStringArray	([Ljava/lang/String;)V
        //   38: aload_0
        //   39: getfield 15	com/google/android/gms/internal/zzov$zza$zza:zzop	Landroid/os/IBinder;
        //   42: sipush 1001
        //   45: aload_3
        //   46: aload 4
        //   48: iconst_0
        //   49: invokeinterface 46 5 0
        //   54: pop
        //   55: aload 4
        //   57: invokevirtual 49	android/os/Parcel:readException	()V
        //   60: aload 4
        //   62: invokevirtual 52	android/os/Parcel:recycle	()V
        //   65: aload_3
        //   66: invokevirtual 52	android/os/Parcel:recycle	()V
        //   69: return
        //   70: aconst_null
        //   71: astore 6
        //   73: goto -46 -> 27
        //   76: astore 5
        //   78: aload 4
        //   80: invokevirtual 52	android/os/Parcel:recycle	()V
        //   83: aload_3
        //   84: invokevirtual 52	android/os/Parcel:recycle	()V
        //   87: aload 5
        //   89: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	90	0	this	zza
        //   0	90	1	paramzzou	zzou
        //   0	90	2	paramArrayOfString	String[]
        //   3	81	3	localParcel1	Parcel
        //   7	72	4	localParcel2	Parcel
        //   76	12	5	localObject	Object
        //   25	47	6	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   9	15	76	finally
        //   19	27	76	finally
        //   27	60	76	finally
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzov
 * JD-Core Version:    0.7.0.1
 */