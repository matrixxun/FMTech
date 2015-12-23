package com.google.android.gms.car;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface zzaz
  extends IInterface
{
  public abstract CarSensorEvent getLatestSensorEvent(int paramInt)
    throws RemoteException;
  
  public abstract int[] getSupportedSensors()
    throws RemoteException;
  
  public abstract void zza(int paramInt, zzba paramzzba)
    throws RemoteException;
  
  public abstract boolean zza(int paramInt1, int paramInt2, zzba paramzzba)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzaz
  {
    public static zzaz zzbG(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.car.ICarSensor");
      if ((localIInterface != null) && ((localIInterface instanceof zzaz))) {
        return (zzaz)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.car.ICarSensor");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarSensor");
        int[] arrayOfInt = getSupportedSensors();
        paramParcel2.writeNoException();
        paramParcel2.writeIntArray(arrayOfInt);
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarSensor");
        boolean bool = zza(paramParcel1.readInt(), paramParcel1.readInt(), zzba.zza.zzbH(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        int i = 0;
        if (bool) {
          i = 1;
        }
        paramParcel2.writeInt(i);
        return true;
      case 3: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarSensor");
        CarSensorEvent localCarSensorEvent = getLatestSensorEvent(paramParcel1.readInt());
        paramParcel2.writeNoException();
        if (localCarSensorEvent != null)
        {
          paramParcel2.writeInt(1);
          localCarSensorEvent.writeToParcel(paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.car.ICarSensor");
      zza(paramParcel1.readInt(), zzba.zza.zzbH(paramParcel1.readStrongBinder()));
      paramParcel2.writeNoException();
      return true;
    }
    
    private static final class zza
      implements zzaz
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
      public final CarSensorEvent getLatestSensorEvent(int paramInt)
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
        //   14: aload_2
        //   15: iload_1
        //   16: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   19: aload_0
        //   20: getfield 15	com/google/android/gms/car/zzaz$zza$zza:zzop	Landroid/os/IBinder;
        //   23: iconst_3
        //   24: aload_2
        //   25: aload_3
        //   26: iconst_0
        //   27: invokeinterface 43 5 0
        //   32: pop
        //   33: aload_3
        //   34: invokevirtual 46	android/os/Parcel:readException	()V
        //   37: aload_3
        //   38: invokevirtual 50	android/os/Parcel:readInt	()I
        //   41: ifeq +28 -> 69
        //   44: getstatic 56	com/google/android/gms/car/CarSensorEvent:CREATOR	Landroid/os/Parcelable$Creator;
        //   47: aload_3
        //   48: invokeinterface 62 2 0
        //   53: checkcast 52	com/google/android/gms/car/CarSensorEvent
        //   56: astore 6
        //   58: aload_3
        //   59: invokevirtual 65	android/os/Parcel:recycle	()V
        //   62: aload_2
        //   63: invokevirtual 65	android/os/Parcel:recycle	()V
        //   66: aload 6
        //   68: areturn
        //   69: aconst_null
        //   70: astore 6
        //   72: goto -14 -> 58
        //   75: astore 4
        //   77: aload_3
        //   78: invokevirtual 65	android/os/Parcel:recycle	()V
        //   81: aload_2
        //   82: invokevirtual 65	android/os/Parcel:recycle	()V
        //   85: aload 4
        //   87: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	88	0	this	zza
        //   0	88	1	paramInt	int
        //   3	79	2	localParcel1	Parcel
        //   7	71	3	localParcel2	Parcel
        //   75	11	4	localObject	Object
        //   56	15	6	localCarSensorEvent	CarSensorEvent
        // Exception table:
        //   from	to	target	type
        //   8	58	75	finally
      }
      
      public final int[] getSupportedSensors()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarSensor");
          this.zzop.transact(1, localParcel1, localParcel2, 0);
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
      
      /* Error */
      public final void zza(int paramInt, zzba paramzzba)
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
        //   16: iload_1
        //   17: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   20: aload_2
        //   21: ifnull +47 -> 68
        //   24: aload_2
        //   25: invokeinterface 76 1 0
        //   30: astore 6
        //   32: aload_3
        //   33: aload 6
        //   35: invokevirtual 79	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   38: aload_0
        //   39: getfield 15	com/google/android/gms/car/zzaz$zza$zza:zzop	Landroid/os/IBinder;
        //   42: iconst_4
        //   43: aload_3
        //   44: aload 4
        //   46: iconst_0
        //   47: invokeinterface 43 5 0
        //   52: pop
        //   53: aload 4
        //   55: invokevirtual 46	android/os/Parcel:readException	()V
        //   58: aload 4
        //   60: invokevirtual 65	android/os/Parcel:recycle	()V
        //   63: aload_3
        //   64: invokevirtual 65	android/os/Parcel:recycle	()V
        //   67: return
        //   68: aconst_null
        //   69: astore 6
        //   71: goto -39 -> 32
        //   74: astore 5
        //   76: aload 4
        //   78: invokevirtual 65	android/os/Parcel:recycle	()V
        //   81: aload_3
        //   82: invokevirtual 65	android/os/Parcel:recycle	()V
        //   85: aload 5
        //   87: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	88	0	this	zza
        //   0	88	1	paramInt	int
        //   0	88	2	paramzzba	zzba
        //   3	79	3	localParcel1	Parcel
        //   7	70	4	localParcel2	Parcel
        //   74	12	5	localObject	Object
        //   30	40	6	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   9	20	74	finally
        //   24	32	74	finally
        //   32	58	74	finally
      }
      
      /* Error */
      public final boolean zza(int paramInt1, int paramInt2, zzba paramzzba)
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
        //   17: aload 4
        //   19: iload_1
        //   20: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   23: aload 4
        //   25: iload_2
        //   26: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   29: aload_3
        //   30: ifnull +70 -> 100
        //   33: aload_3
        //   34: invokeinterface 76 1 0
        //   39: astore 7
        //   41: aload 4
        //   43: aload 7
        //   45: invokevirtual 79	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   48: aload_0
        //   49: getfield 15	com/google/android/gms/car/zzaz$zza$zza:zzop	Landroid/os/IBinder;
        //   52: iconst_2
        //   53: aload 4
        //   55: aload 5
        //   57: iconst_0
        //   58: invokeinterface 43 5 0
        //   63: pop
        //   64: aload 5
        //   66: invokevirtual 46	android/os/Parcel:readException	()V
        //   69: aload 5
        //   71: invokevirtual 50	android/os/Parcel:readInt	()I
        //   74: istore 9
        //   76: iconst_0
        //   77: istore 10
        //   79: iload 9
        //   81: ifeq +6 -> 87
        //   84: iconst_1
        //   85: istore 10
        //   87: aload 5
        //   89: invokevirtual 65	android/os/Parcel:recycle	()V
        //   92: aload 4
        //   94: invokevirtual 65	android/os/Parcel:recycle	()V
        //   97: iload 10
        //   99: ireturn
        //   100: aconst_null
        //   101: astore 7
        //   103: goto -62 -> 41
        //   106: astore 6
        //   108: aload 5
        //   110: invokevirtual 65	android/os/Parcel:recycle	()V
        //   113: aload 4
        //   115: invokevirtual 65	android/os/Parcel:recycle	()V
        //   118: aload 6
        //   120: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	121	0	this	zza
        //   0	121	1	paramInt1	int
        //   0	121	2	paramInt2	int
        //   0	121	3	paramzzba	zzba
        //   3	111	4	localParcel1	Parcel
        //   8	101	5	localParcel2	Parcel
        //   106	13	6	localObject	Object
        //   39	63	7	localIBinder	IBinder
        //   74	6	9	i	int
        //   77	21	10	bool	boolean
        // Exception table:
        //   from	to	target	type
        //   10	29	106	finally
        //   33	41	106	finally
        //   41	76	106	finally
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzaz
 * JD-Core Version:    0.7.0.1
 */