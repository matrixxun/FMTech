package com.google.android.gms.car;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface zzz
  extends IInterface
{
  public abstract CarAudioConfiguration[] getAudioRecordConfigurations(int paramInt)
    throws RemoteException;
  
  public abstract int getAudioRecordMinBufferSize(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract int[] getAudioRecordStreams()
    throws RemoteException;
  
  public abstract CarAudioConfiguration[] getAudioTrackConfigurations(int paramInt)
    throws RemoteException;
  
  public abstract int getAudioTrackMinBufferSize(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract int[] getAudioTrackStreams()
    throws RemoteException;
  
  public abstract boolean waitForPlayback(long paramLong)
    throws RemoteException;
  
  public abstract boolean waitForSilence(long paramLong)
    throws RemoteException;
  
  public abstract zzad zza(zzaa paramzzaa, int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;
  
  public abstract zzab zzb(zzaa paramzzaa, int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;
  
  public abstract CarAudioConfiguration zzo(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract CarAudioConfiguration zzp(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzz
  {
    public static zzz zzbf(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.car.ICarAudio");
      if ((localIInterface != null) && ((localIInterface instanceof zzz))) {
        return (zzz)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.car.ICarAudio");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarAudio");
        int[] arrayOfInt2 = getAudioTrackStreams();
        paramParcel2.writeNoException();
        paramParcel2.writeIntArray(arrayOfInt2);
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarAudio");
        int[] arrayOfInt1 = getAudioRecordStreams();
        paramParcel2.writeNoException();
        paramParcel2.writeIntArray(arrayOfInt1);
        return true;
      case 3: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarAudio");
        CarAudioConfiguration[] arrayOfCarAudioConfiguration2 = getAudioTrackConfigurations(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeTypedArray(arrayOfCarAudioConfiguration2, 1);
        return true;
      case 4: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarAudio");
        CarAudioConfiguration localCarAudioConfiguration2 = zzo(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        if (localCarAudioConfiguration2 != null)
        {
          paramParcel2.writeInt(1);
          localCarAudioConfiguration2.writeToParcel(paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      case 5: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarAudio");
        CarAudioConfiguration[] arrayOfCarAudioConfiguration1 = getAudioRecordConfigurations(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeTypedArray(arrayOfCarAudioConfiguration1, 1);
        return true;
      case 6: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarAudio");
        CarAudioConfiguration localCarAudioConfiguration1 = zzp(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        if (localCarAudioConfiguration1 != null)
        {
          paramParcel2.writeInt(1);
          localCarAudioConfiguration1.writeToParcel(paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      case 7: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarAudio");
        int m = getAudioTrackMinBufferSize(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(m);
        return true;
      case 8: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarAudio");
        int k = getAudioRecordMinBufferSize(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(k);
        return true;
      case 9: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarAudio");
        zzad localzzad = zza(zzaa.zza.zzbg(paramParcel1.readStrongBinder()), paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        IBinder localIBinder2 = null;
        if (localzzad != null) {
          localIBinder2 = localzzad.asBinder();
        }
        paramParcel2.writeStrongBinder(localIBinder2);
        return true;
      case 10: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarAudio");
        zzab localzzab = zzb(zzaa.zza.zzbg(paramParcel1.readStrongBinder()), paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        IBinder localIBinder1 = null;
        if (localzzab != null) {
          localIBinder1 = localzzab.asBinder();
        }
        paramParcel2.writeStrongBinder(localIBinder1);
        return true;
      case 11: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarAudio");
        boolean bool2 = waitForSilence(paramParcel1.readLong());
        paramParcel2.writeNoException();
        if (bool2) {}
        for (int j = 1;; j = 0)
        {
          paramParcel2.writeInt(j);
          return true;
        }
      }
      paramParcel1.enforceInterface("com.google.android.gms.car.ICarAudio");
      boolean bool1 = waitForPlayback(paramParcel1.readLong());
      paramParcel2.writeNoException();
      int i = 0;
      if (bool1) {
        i = 1;
      }
      paramParcel2.writeInt(i);
      return true;
    }
    
    private static final class zza
      implements zzz
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
      
      public final CarAudioConfiguration[] getAudioRecordConfigurations(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarAudio");
          localParcel1.writeInt(paramInt);
          this.zzop.transact(5, localParcel1, localParcel2, 0);
          localParcel2.readException();
          CarAudioConfiguration[] arrayOfCarAudioConfiguration = (CarAudioConfiguration[])localParcel2.createTypedArray(CarAudioConfiguration.CREATOR);
          return arrayOfCarAudioConfiguration;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public final int getAudioRecordMinBufferSize(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarAudio");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.zzop.transact(8, localParcel1, localParcel2, 0);
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
      
      public final int[] getAudioRecordStreams()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarAudio");
          this.zzop.transact(2, localParcel1, localParcel2, 0);
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
      
      public final CarAudioConfiguration[] getAudioTrackConfigurations(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarAudio");
          localParcel1.writeInt(paramInt);
          this.zzop.transact(3, localParcel1, localParcel2, 0);
          localParcel2.readException();
          CarAudioConfiguration[] arrayOfCarAudioConfiguration = (CarAudioConfiguration[])localParcel2.createTypedArray(CarAudioConfiguration.CREATOR);
          return arrayOfCarAudioConfiguration;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public final int getAudioTrackMinBufferSize(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarAudio");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.zzop.transact(7, localParcel1, localParcel2, 0);
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
      
      public final int[] getAudioTrackStreams()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarAudio");
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
      
      public final boolean waitForPlayback(long paramLong)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarAudio");
          localParcel1.writeLong(paramLong);
          this.zzop.transact(12, localParcel1, localParcel2, 0);
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
      
      public final boolean waitForSilence(long paramLong)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarAudio");
          localParcel1.writeLong(paramLong);
          this.zzop.transact(11, localParcel1, localParcel2, 0);
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
      public final zzad zza(zzaa paramzzaa, int paramInt1, int paramInt2, int paramInt3)
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
        //   18: ifnull +82 -> 100
        //   21: aload_1
        //   22: invokeinterface 88 1 0
        //   27: astore 8
        //   29: aload 5
        //   31: aload 8
        //   33: invokevirtual 91	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   36: aload 5
        //   38: iload_2
        //   39: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   42: aload 5
        //   44: iload_3
        //   45: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   48: aload 5
        //   50: iload 4
        //   52: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   55: aload_0
        //   56: getfield 15	com/google/android/gms/car/zzz$zza$zza:zzop	Landroid/os/IBinder;
        //   59: bipush 9
        //   61: aload 5
        //   63: aload 6
        //   65: iconst_0
        //   66: invokeinterface 43 5 0
        //   71: pop
        //   72: aload 6
        //   74: invokevirtual 46	android/os/Parcel:readException	()V
        //   77: aload 6
        //   79: invokevirtual 94	android/os/Parcel:readStrongBinder	()Landroid/os/IBinder;
        //   82: invokestatic 100	com/google/android/gms/car/zzad$zza:zzbj	(Landroid/os/IBinder;)Lcom/google/android/gms/car/zzad;
        //   85: astore 10
        //   87: aload 6
        //   89: invokevirtual 61	android/os/Parcel:recycle	()V
        //   92: aload 5
        //   94: invokevirtual 61	android/os/Parcel:recycle	()V
        //   97: aload 10
        //   99: areturn
        //   100: aconst_null
        //   101: astore 8
        //   103: goto -74 -> 29
        //   106: astore 7
        //   108: aload 6
        //   110: invokevirtual 61	android/os/Parcel:recycle	()V
        //   113: aload 5
        //   115: invokevirtual 61	android/os/Parcel:recycle	()V
        //   118: aload 7
        //   120: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	121	0	this	zza
        //   0	121	1	paramzzaa	zzaa
        //   0	121	2	paramInt1	int
        //   0	121	3	paramInt2	int
        //   0	121	4	paramInt3	int
        //   3	111	5	localParcel1	Parcel
        //   8	101	6	localParcel2	Parcel
        //   106	13	7	localObject	Object
        //   27	75	8	localIBinder	IBinder
        //   85	13	10	localzzad	zzad
        // Exception table:
        //   from	to	target	type
        //   10	17	106	finally
        //   21	29	106	finally
        //   29	87	106	finally
      }
      
      /* Error */
      public final zzab zzb(zzaa paramzzaa, int paramInt1, int paramInt2, int paramInt3)
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
        //   18: ifnull +82 -> 100
        //   21: aload_1
        //   22: invokeinterface 88 1 0
        //   27: astore 8
        //   29: aload 5
        //   31: aload 8
        //   33: invokevirtual 91	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   36: aload 5
        //   38: iload_2
        //   39: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   42: aload 5
        //   44: iload_3
        //   45: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   48: aload 5
        //   50: iload 4
        //   52: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   55: aload_0
        //   56: getfield 15	com/google/android/gms/car/zzz$zza$zza:zzop	Landroid/os/IBinder;
        //   59: bipush 10
        //   61: aload 5
        //   63: aload 6
        //   65: iconst_0
        //   66: invokeinterface 43 5 0
        //   71: pop
        //   72: aload 6
        //   74: invokevirtual 46	android/os/Parcel:readException	()V
        //   77: aload 6
        //   79: invokevirtual 94	android/os/Parcel:readStrongBinder	()Landroid/os/IBinder;
        //   82: invokestatic 108	com/google/android/gms/car/zzab$zza:zzbh	(Landroid/os/IBinder;)Lcom/google/android/gms/car/zzab;
        //   85: astore 10
        //   87: aload 6
        //   89: invokevirtual 61	android/os/Parcel:recycle	()V
        //   92: aload 5
        //   94: invokevirtual 61	android/os/Parcel:recycle	()V
        //   97: aload 10
        //   99: areturn
        //   100: aconst_null
        //   101: astore 8
        //   103: goto -74 -> 29
        //   106: astore 7
        //   108: aload 6
        //   110: invokevirtual 61	android/os/Parcel:recycle	()V
        //   113: aload 5
        //   115: invokevirtual 61	android/os/Parcel:recycle	()V
        //   118: aload 7
        //   120: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	121	0	this	zza
        //   0	121	1	paramzzaa	zzaa
        //   0	121	2	paramInt1	int
        //   0	121	3	paramInt2	int
        //   0	121	4	paramInt3	int
        //   3	111	5	localParcel1	Parcel
        //   8	101	6	localParcel2	Parcel
        //   106	13	7	localObject	Object
        //   27	75	8	localIBinder	IBinder
        //   85	13	10	localzzab	zzab
        // Exception table:
        //   from	to	target	type
        //   10	17	106	finally
        //   21	29	106	finally
        //   29	87	106	finally
      }
      
      /* Error */
      public final CarAudioConfiguration zzo(int paramInt1, int paramInt2)
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
        //   20: aload_3
        //   21: iload_2
        //   22: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   25: aload_0
        //   26: getfield 15	com/google/android/gms/car/zzz$zza$zza:zzop	Landroid/os/IBinder;
        //   29: iconst_4
        //   30: aload_3
        //   31: aload 4
        //   33: iconst_0
        //   34: invokeinterface 43 5 0
        //   39: pop
        //   40: aload 4
        //   42: invokevirtual 46	android/os/Parcel:readException	()V
        //   45: aload 4
        //   47: invokevirtual 67	android/os/Parcel:readInt	()I
        //   50: ifeq +30 -> 80
        //   53: getstatic 52	com/google/android/gms/car/CarAudioConfiguration:CREATOR	Landroid/os/Parcelable$Creator;
        //   56: aload 4
        //   58: invokeinterface 116 2 0
        //   63: checkcast 48	com/google/android/gms/car/CarAudioConfiguration
        //   66: astore 7
        //   68: aload 4
        //   70: invokevirtual 61	android/os/Parcel:recycle	()V
        //   73: aload_3
        //   74: invokevirtual 61	android/os/Parcel:recycle	()V
        //   77: aload 7
        //   79: areturn
        //   80: aconst_null
        //   81: astore 7
        //   83: goto -15 -> 68
        //   86: astore 5
        //   88: aload 4
        //   90: invokevirtual 61	android/os/Parcel:recycle	()V
        //   93: aload_3
        //   94: invokevirtual 61	android/os/Parcel:recycle	()V
        //   97: aload 5
        //   99: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	100	0	this	zza
        //   0	100	1	paramInt1	int
        //   0	100	2	paramInt2	int
        //   3	91	3	localParcel1	Parcel
        //   7	82	4	localParcel2	Parcel
        //   86	12	5	localObject	Object
        //   66	16	7	localCarAudioConfiguration	CarAudioConfiguration
        // Exception table:
        //   from	to	target	type
        //   9	68	86	finally
      }
      
      /* Error */
      public final CarAudioConfiguration zzp(int paramInt1, int paramInt2)
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
        //   20: aload_3
        //   21: iload_2
        //   22: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   25: aload_0
        //   26: getfield 15	com/google/android/gms/car/zzz$zza$zza:zzop	Landroid/os/IBinder;
        //   29: bipush 6
        //   31: aload_3
        //   32: aload 4
        //   34: iconst_0
        //   35: invokeinterface 43 5 0
        //   40: pop
        //   41: aload 4
        //   43: invokevirtual 46	android/os/Parcel:readException	()V
        //   46: aload 4
        //   48: invokevirtual 67	android/os/Parcel:readInt	()I
        //   51: ifeq +30 -> 81
        //   54: getstatic 52	com/google/android/gms/car/CarAudioConfiguration:CREATOR	Landroid/os/Parcelable$Creator;
        //   57: aload 4
        //   59: invokeinterface 116 2 0
        //   64: checkcast 48	com/google/android/gms/car/CarAudioConfiguration
        //   67: astore 7
        //   69: aload 4
        //   71: invokevirtual 61	android/os/Parcel:recycle	()V
        //   74: aload_3
        //   75: invokevirtual 61	android/os/Parcel:recycle	()V
        //   78: aload 7
        //   80: areturn
        //   81: aconst_null
        //   82: astore 7
        //   84: goto -15 -> 69
        //   87: astore 5
        //   89: aload 4
        //   91: invokevirtual 61	android/os/Parcel:recycle	()V
        //   94: aload_3
        //   95: invokevirtual 61	android/os/Parcel:recycle	()V
        //   98: aload 5
        //   100: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	101	0	this	zza
        //   0	101	1	paramInt1	int
        //   0	101	2	paramInt2	int
        //   3	92	3	localParcel1	Parcel
        //   7	83	4	localParcel2	Parcel
        //   87	12	5	localObject	Object
        //   67	16	7	localCarAudioConfiguration	CarAudioConfiguration
        // Exception table:
        //   from	to	target	type
        //   9	69	87	finally
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzz
 * JD-Core Version:    0.7.0.1
 */