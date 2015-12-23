package com.google.android.gms.car;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface zzaf
  extends IInterface
{
  public abstract String getCarBluetoothAddress()
    throws RemoteException;
  
  public abstract int getInitializationStatus()
    throws RemoteException;
  
  public abstract int[] getSupportedPairingMethods()
    throws RemoteException;
  
  public abstract boolean isEnabled()
    throws RemoteException;
  
  public abstract boolean isHfpConnected()
    throws RemoteException;
  
  public abstract boolean isHfpConnecting()
    throws RemoteException;
  
  public abstract boolean isPaired()
    throws RemoteException;
  
  public abstract boolean isPairing()
    throws RemoteException;
  
  public abstract boolean zza(zzag paramzzag)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzaf
  {
    public static zzaf zzbl(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.car.ICarBluetooth");
      if ((localIInterface != null) && ((localIInterface instanceof zzaf))) {
        return (zzaf)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.car.ICarBluetooth");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarBluetooth");
        int i2 = getInitializationStatus();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i2);
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarBluetooth");
        IBinder localIBinder = paramParcel1.readStrongBinder();
        Object localObject;
        if (localIBinder == null)
        {
          localObject = null;
          boolean bool6 = zza((zzag)localObject);
          paramParcel2.writeNoException();
          if (!bool6) {
            break label230;
          }
        }
        for (int i1 = 1;; i1 = 0)
        {
          paramParcel2.writeInt(i1);
          return true;
          IInterface localIInterface = localIBinder.queryLocalInterface("com.google.android.gms.car.ICarBluetoothClient");
          if ((localIInterface != null) && ((localIInterface instanceof zzag)))
          {
            localObject = (zzag)localIInterface;
            break;
          }
          localObject = new zzag.zza.zza(localIBinder);
          break;
        }
      case 3: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarBluetooth");
        boolean bool5 = isEnabled();
        paramParcel2.writeNoException();
        int n = 0;
        if (bool5) {
          n = 1;
        }
        paramParcel2.writeInt(n);
        return true;
      case 4: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarBluetooth");
        boolean bool4 = isPairing();
        paramParcel2.writeNoException();
        int m = 0;
        if (bool4) {
          m = 1;
        }
        paramParcel2.writeInt(m);
        return true;
      case 5: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarBluetooth");
        boolean bool3 = isPaired();
        paramParcel2.writeNoException();
        int k = 0;
        if (bool3) {
          k = 1;
        }
        paramParcel2.writeInt(k);
        return true;
      case 6: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarBluetooth");
        boolean bool2 = isHfpConnecting();
        paramParcel2.writeNoException();
        int j = 0;
        if (bool2) {
          j = 1;
        }
        paramParcel2.writeInt(j);
        return true;
      case 7: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarBluetooth");
        boolean bool1 = isHfpConnected();
        paramParcel2.writeNoException();
        int i = 0;
        if (bool1) {
          i = 1;
        }
        paramParcel2.writeInt(i);
        return true;
      case 8: 
        label230:
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarBluetooth");
        String str = getCarBluetoothAddress();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str);
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.car.ICarBluetooth");
      int[] arrayOfInt = getSupportedPairingMethods();
      paramParcel2.writeNoException();
      paramParcel2.writeIntArray(arrayOfInt);
      return true;
    }
    
    private static final class zza
      implements zzaf
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
      
      public final String getCarBluetoothAddress()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarBluetooth");
          this.zzop.transact(8, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public final int getInitializationStatus()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarBluetooth");
          this.zzop.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public final int[] getSupportedPairingMethods()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarBluetooth");
          this.zzop.transact(9, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int[] arrayOfInt = localParcel2.createIntArray();
          return arrayOfInt;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public final boolean isEnabled()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarBluetooth");
          this.zzop.transact(3, localParcel1, localParcel2, 0);
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
      
      public final boolean isHfpConnected()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarBluetooth");
          this.zzop.transact(7, localParcel1, localParcel2, 0);
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
      
      public final boolean isHfpConnecting()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarBluetooth");
          this.zzop.transact(6, localParcel1, localParcel2, 0);
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
      
      public final boolean isPaired()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarBluetooth");
          this.zzop.transact(5, localParcel1, localParcel2, 0);
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
      
      public final boolean isPairing()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarBluetooth");
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
      
      /* Error */
      public final boolean zza(zzag paramzzag)
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
        //   15: ifnull +63 -> 78
        //   18: aload_1
        //   19: invokeinterface 70 1 0
        //   24: astore 5
        //   26: aload_2
        //   27: aload 5
        //   29: invokevirtual 73	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   32: aload_0
        //   33: getfield 15	com/google/android/gms/car/zzaf$zza$zza:zzop	Landroid/os/IBinder;
        //   36: iconst_2
        //   37: aload_2
        //   38: aload_3
        //   39: iconst_0
        //   40: invokeinterface 39 5 0
        //   45: pop
        //   46: aload_3
        //   47: invokevirtual 42	android/os/Parcel:readException	()V
        //   50: aload_3
        //   51: invokevirtual 53	android/os/Parcel:readInt	()I
        //   54: istore 7
        //   56: iconst_0
        //   57: istore 8
        //   59: iload 7
        //   61: ifeq +6 -> 67
        //   64: iconst_1
        //   65: istore 8
        //   67: aload_3
        //   68: invokevirtual 48	android/os/Parcel:recycle	()V
        //   71: aload_2
        //   72: invokevirtual 48	android/os/Parcel:recycle	()V
        //   75: iload 8
        //   77: ireturn
        //   78: aconst_null
        //   79: astore 5
        //   81: goto -55 -> 26
        //   84: astore 4
        //   86: aload_3
        //   87: invokevirtual 48	android/os/Parcel:recycle	()V
        //   90: aload_2
        //   91: invokevirtual 48	android/os/Parcel:recycle	()V
        //   94: aload 4
        //   96: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	97	0	this	zza
        //   0	97	1	paramzzag	zzag
        //   3	88	2	localParcel1	Parcel
        //   7	80	3	localParcel2	Parcel
        //   84	11	4	localObject	Object
        //   24	56	5	localIBinder	IBinder
        //   54	6	7	i	int
        //   57	19	8	bool	boolean
        // Exception table:
        //   from	to	target	type
        //   8	14	84	finally
        //   18	26	84	finally
        //   26	56	84	finally
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzaf
 * JD-Core Version:    0.7.0.1
 */