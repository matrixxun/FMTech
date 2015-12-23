package com.google.android.gms.car;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface zzar
  extends IInterface
{
  public abstract boolean isSupported()
    throws RemoteException;
  
  public abstract void zza(int paramInt1, String paramString, int paramInt2, int paramInt3, byte[] paramArrayOfByte, int paramInt4)
    throws RemoteException;
  
  public abstract boolean zza(zzas paramzzas)
    throws RemoteException;
  
  public abstract boolean zzb(zzas paramzzas)
    throws RemoteException;
  
  public abstract void zzbT(int paramInt)
    throws RemoteException;
  
  public abstract CarInstrumentClusterInfo zzmu()
    throws RemoteException;
  
  public abstract void zzq(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzar
  {
    public static zzar zzbx(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.car.ICarNavigationStatus");
      if ((localIInterface != null) && ((localIInterface instanceof zzar))) {
        return (zzar)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.car.ICarNavigationStatus");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarNavigationStatus");
        zzbT(paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarNavigationStatus");
        zza(paramParcel1.readInt(), paramParcel1.readString(), paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.createByteArray(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      case 3: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarNavigationStatus");
        zzq(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      case 4: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarNavigationStatus");
        boolean bool3 = isSupported();
        paramParcel2.writeNoException();
        int k = 0;
        if (bool3) {
          k = 1;
        }
        paramParcel2.writeInt(k);
        return true;
      case 5: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarNavigationStatus");
        CarInstrumentClusterInfo localCarInstrumentClusterInfo = zzmu();
        paramParcel2.writeNoException();
        if (localCarInstrumentClusterInfo != null)
        {
          paramParcel2.writeInt(1);
          localCarInstrumentClusterInfo.writeToParcel(paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      case 6: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarNavigationStatus");
        boolean bool2 = zza(zzas.zza.zzby(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        int j = 0;
        if (bool2) {
          j = 1;
        }
        paramParcel2.writeInt(j);
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.car.ICarNavigationStatus");
      boolean bool1 = zzb(zzas.zza.zzby(paramParcel1.readStrongBinder()));
      paramParcel2.writeNoException();
      int i = 0;
      if (bool1) {
        i = 1;
      }
      paramParcel2.writeInt(i);
      return true;
    }
    
    private static final class zza
      implements zzar
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
      
      public final boolean isSupported()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarNavigationStatus");
          this.zzop.transact(4, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          boolean bool = false;
          if (i != 0) {
            bool = true;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public final void zza(int paramInt1, String paramString, int paramInt2, int paramInt3, byte[] paramArrayOfByte, int paramInt4)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarNavigationStatus");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeString(paramString);
          localParcel1.writeInt(paramInt2);
          localParcel1.writeInt(paramInt3);
          localParcel1.writeByteArray(paramArrayOfByte);
          localParcel1.writeInt(paramInt4);
          this.zzop.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      /* Error */
      public final boolean zza(zzas paramzzas)
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
        //   15: ifnull +64 -> 79
        //   18: aload_1
        //   19: invokeinterface 67 1 0
        //   24: astore 5
        //   26: aload_2
        //   27: aload 5
        //   29: invokevirtual 70	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   32: aload_0
        //   33: getfield 15	com/google/android/gms/car/zzar$zza$zza:zzop	Landroid/os/IBinder;
        //   36: bipush 6
        //   38: aload_2
        //   39: aload_3
        //   40: iconst_0
        //   41: invokeinterface 39 5 0
        //   46: pop
        //   47: aload_3
        //   48: invokevirtual 42	android/os/Parcel:readException	()V
        //   51: aload_3
        //   52: invokevirtual 46	android/os/Parcel:readInt	()I
        //   55: istore 7
        //   57: iconst_0
        //   58: istore 8
        //   60: iload 7
        //   62: ifeq +6 -> 68
        //   65: iconst_1
        //   66: istore 8
        //   68: aload_3
        //   69: invokevirtual 49	android/os/Parcel:recycle	()V
        //   72: aload_2
        //   73: invokevirtual 49	android/os/Parcel:recycle	()V
        //   76: iload 8
        //   78: ireturn
        //   79: aconst_null
        //   80: astore 5
        //   82: goto -56 -> 26
        //   85: astore 4
        //   87: aload_3
        //   88: invokevirtual 49	android/os/Parcel:recycle	()V
        //   91: aload_2
        //   92: invokevirtual 49	android/os/Parcel:recycle	()V
        //   95: aload 4
        //   97: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	98	0	this	zza
        //   0	98	1	paramzzas	zzas
        //   3	89	2	localParcel1	Parcel
        //   7	81	3	localParcel2	Parcel
        //   85	11	4	localObject	Object
        //   24	57	5	localIBinder	IBinder
        //   55	6	7	i	int
        //   58	19	8	bool	boolean
        // Exception table:
        //   from	to	target	type
        //   8	14	85	finally
        //   18	26	85	finally
        //   26	57	85	finally
      }
      
      /* Error */
      public final boolean zzb(zzas paramzzas)
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
        //   15: ifnull +64 -> 79
        //   18: aload_1
        //   19: invokeinterface 67 1 0
        //   24: astore 5
        //   26: aload_2
        //   27: aload 5
        //   29: invokevirtual 70	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   32: aload_0
        //   33: getfield 15	com/google/android/gms/car/zzar$zza$zza:zzop	Landroid/os/IBinder;
        //   36: bipush 7
        //   38: aload_2
        //   39: aload_3
        //   40: iconst_0
        //   41: invokeinterface 39 5 0
        //   46: pop
        //   47: aload_3
        //   48: invokevirtual 42	android/os/Parcel:readException	()V
        //   51: aload_3
        //   52: invokevirtual 46	android/os/Parcel:readInt	()I
        //   55: istore 7
        //   57: iconst_0
        //   58: istore 8
        //   60: iload 7
        //   62: ifeq +6 -> 68
        //   65: iconst_1
        //   66: istore 8
        //   68: aload_3
        //   69: invokevirtual 49	android/os/Parcel:recycle	()V
        //   72: aload_2
        //   73: invokevirtual 49	android/os/Parcel:recycle	()V
        //   76: iload 8
        //   78: ireturn
        //   79: aconst_null
        //   80: astore 5
        //   82: goto -56 -> 26
        //   85: astore 4
        //   87: aload_3
        //   88: invokevirtual 49	android/os/Parcel:recycle	()V
        //   91: aload_2
        //   92: invokevirtual 49	android/os/Parcel:recycle	()V
        //   95: aload 4
        //   97: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	98	0	this	zza
        //   0	98	1	paramzzas	zzas
        //   3	89	2	localParcel1	Parcel
        //   7	81	3	localParcel2	Parcel
        //   85	11	4	localObject	Object
        //   24	57	5	localIBinder	IBinder
        //   55	6	7	i	int
        //   58	19	8	bool	boolean
        // Exception table:
        //   from	to	target	type
        //   8	14	85	finally
        //   18	26	85	finally
        //   26	57	85	finally
      }
      
      public final void zzbT(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarNavigationStatus");
          localParcel1.writeInt(paramInt);
          this.zzop.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      /* Error */
      public final CarInstrumentClusterInfo zzmu()
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_1
        //   4: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_2
        //   8: aload_1
        //   9: ldc 29
        //   11: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_0
        //   15: getfield 15	com/google/android/gms/car/zzar$zza$zza:zzop	Landroid/os/IBinder;
        //   18: iconst_5
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokeinterface 39 5 0
        //   27: pop
        //   28: aload_2
        //   29: invokevirtual 42	android/os/Parcel:readException	()V
        //   32: aload_2
        //   33: invokevirtual 46	android/os/Parcel:readInt	()I
        //   36: ifeq +28 -> 64
        //   39: getstatic 80	com/google/android/gms/car/CarInstrumentClusterInfo:CREATOR	Landroid/os/Parcelable$Creator;
        //   42: aload_2
        //   43: invokeinterface 86 2 0
        //   48: checkcast 76	com/google/android/gms/car/CarInstrumentClusterInfo
        //   51: astore 5
        //   53: aload_2
        //   54: invokevirtual 49	android/os/Parcel:recycle	()V
        //   57: aload_1
        //   58: invokevirtual 49	android/os/Parcel:recycle	()V
        //   61: aload 5
        //   63: areturn
        //   64: aconst_null
        //   65: astore 5
        //   67: goto -14 -> 53
        //   70: astore_3
        //   71: aload_2
        //   72: invokevirtual 49	android/os/Parcel:recycle	()V
        //   75: aload_1
        //   76: invokevirtual 49	android/os/Parcel:recycle	()V
        //   79: aload_3
        //   80: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	81	0	this	zza
        //   3	73	1	localParcel1	Parcel
        //   7	65	2	localParcel2	Parcel
        //   70	10	3	localObject	Object
        //   51	15	5	localCarInstrumentClusterInfo	CarInstrumentClusterInfo
        // Exception table:
        //   from	to	target	type
        //   8	53	70	finally
      }
      
      public final void zzq(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarNavigationStatus");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.zzop.transact(3, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzar
 * JD-Core Version:    0.7.0.1
 */