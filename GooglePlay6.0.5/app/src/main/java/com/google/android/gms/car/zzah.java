package com.google.android.gms.car;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public abstract interface zzah
  extends IInterface
{
  public abstract void answerCall(CarCall paramCarCall)
    throws RemoteException;
  
  public abstract void conference(CarCall paramCarCall1, CarCall paramCarCall2)
    throws RemoteException;
  
  public abstract void disconnectCall(CarCall paramCarCall)
    throws RemoteException;
  
  public abstract int getAudioRoute()
    throws RemoteException;
  
  public abstract List<CarCall> getCalls()
    throws RemoteException;
  
  public abstract boolean getMuted()
    throws RemoteException;
  
  public abstract int getSupportedAudioRouteMask()
    throws RemoteException;
  
  public abstract void holdCall(CarCall paramCarCall)
    throws RemoteException;
  
  public abstract void placeCall(String paramString)
    throws RemoteException;
  
  public abstract void playDtmfTone(CarCall paramCarCall, char paramChar)
    throws RemoteException;
  
  public abstract void postDialContinue(CarCall paramCarCall, boolean paramBoolean)
    throws RemoteException;
  
  public abstract void rejectCall(CarCall paramCarCall, boolean paramBoolean, String paramString)
    throws RemoteException;
  
  public abstract void setAudioRoute(int paramInt)
    throws RemoteException;
  
  public abstract void setMuted(boolean paramBoolean)
    throws RemoteException;
  
  public abstract void splitFromConference(CarCall paramCarCall)
    throws RemoteException;
  
  public abstract void stopDtmfTone(CarCall paramCarCall)
    throws RemoteException;
  
  public abstract void unholdCall(CarCall paramCarCall)
    throws RemoteException;
  
  public abstract boolean zza(zzai paramzzai)
    throws RemoteException;
  
  public abstract boolean zzb(zzai paramzzai)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzah
  {
    public static zzah zzbn(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.car.ICarCall");
      if ((localIInterface != null) && ((localIInterface instanceof zzah))) {
        return (zzah)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.car.ICarCall");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCall");
        List localList = getCalls();
        paramParcel2.writeNoException();
        paramParcel2.writeTypedList(localList);
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCall");
        boolean bool6 = getMuted();
        paramParcel2.writeNoException();
        if (bool6) {}
        for (int i3 = 1;; i3 = 0)
        {
          paramParcel2.writeInt(i3);
          return true;
        }
      case 3: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCall");
        int i2 = paramParcel1.readInt();
        boolean bool5 = false;
        if (i2 != 0) {
          bool5 = true;
        }
        setMuted(bool5);
        paramParcel2.writeNoException();
        return true;
      case 4: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCall");
        int i1 = getSupportedAudioRouteMask();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i1);
        return true;
      case 5: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCall");
        int n = getAudioRoute();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(n);
        return true;
      case 6: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCall");
        setAudioRoute(paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      case 7: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCall");
        boolean bool4 = zza(zzai.zza.zzbo(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        int m = 0;
        if (bool4) {
          m = 1;
        }
        paramParcel2.writeInt(m);
        return true;
      case 8: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCall");
        boolean bool3 = zzb(zzai.zza.zzbo(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        int k = 0;
        if (bool3) {
          k = 1;
        }
        paramParcel2.writeInt(k);
        return true;
      case 9: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCall");
        if (paramParcel1.readInt() != 0) {}
        for (CarCall localCarCall11 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);; localCarCall11 = null)
        {
          answerCall(localCarCall11);
          paramParcel2.writeNoException();
          return true;
        }
      case 10: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCall");
        if (paramParcel1.readInt() != 0) {}
        for (CarCall localCarCall10 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);; localCarCall10 = null)
        {
          int j = paramParcel1.readInt();
          boolean bool2 = false;
          if (j != 0) {
            bool2 = true;
          }
          rejectCall(localCarCall10, bool2, paramParcel1.readString());
          paramParcel2.writeNoException();
          return true;
        }
      case 11: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCall");
        if (paramParcel1.readInt() != 0) {}
        for (CarCall localCarCall9 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);; localCarCall9 = null)
        {
          disconnectCall(localCarCall9);
          paramParcel2.writeNoException();
          return true;
        }
      case 12: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCall");
        if (paramParcel1.readInt() != 0) {}
        for (CarCall localCarCall8 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);; localCarCall8 = null)
        {
          holdCall(localCarCall8);
          paramParcel2.writeNoException();
          return true;
        }
      case 13: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCall");
        if (paramParcel1.readInt() != 0) {}
        for (CarCall localCarCall7 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);; localCarCall7 = null)
        {
          unholdCall(localCarCall7);
          paramParcel2.writeNoException();
          return true;
        }
      case 14: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCall");
        if (paramParcel1.readInt() != 0) {}
        for (CarCall localCarCall6 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);; localCarCall6 = null)
        {
          playDtmfTone(localCarCall6, (char)paramParcel1.readInt());
          paramParcel2.writeNoException();
          return true;
        }
      case 15: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCall");
        if (paramParcel1.readInt() != 0) {}
        for (CarCall localCarCall5 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);; localCarCall5 = null)
        {
          stopDtmfTone(localCarCall5);
          paramParcel2.writeNoException();
          return true;
        }
      case 16: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCall");
        if (paramParcel1.readInt() != 0) {}
        for (CarCall localCarCall4 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);; localCarCall4 = null)
        {
          int i = paramParcel1.readInt();
          boolean bool1 = false;
          if (i != 0) {
            bool1 = true;
          }
          postDialContinue(localCarCall4, bool1);
          paramParcel2.writeNoException();
          return true;
        }
      case 17: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCall");
        CarCall localCarCall2;
        if (paramParcel1.readInt() != 0)
        {
          localCarCall2 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label914;
          }
        }
        for (CarCall localCarCall3 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);; localCarCall3 = null)
        {
          conference(localCarCall2, localCarCall3);
          paramParcel2.writeNoException();
          return true;
          localCarCall2 = null;
          break;
        }
      case 18: 
        label914:
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCall");
        if (paramParcel1.readInt() != 0) {}
        for (CarCall localCarCall1 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);; localCarCall1 = null)
        {
          splitFromConference(localCarCall1);
          paramParcel2.writeNoException();
          return true;
        }
      }
      paramParcel1.enforceInterface("com.google.android.gms.car.ICarCall");
      placeCall(paramParcel1.readString());
      paramParcel2.writeNoException();
      return true;
    }
    
    private static final class zza
      implements zzah
    {
      private IBinder zzop;
      
      zza(IBinder paramIBinder)
      {
        this.zzop = paramIBinder;
      }
      
      /* Error */
      public final void answerCall(CarCall paramCarCall)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 25	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: invokestatic 25	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_3
        //   8: aload_2
        //   9: ldc 27
        //   11: invokevirtual 31	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_1
        //   15: ifnull +42 -> 57
        //   18: aload_2
        //   19: iconst_1
        //   20: invokevirtual 35	android/os/Parcel:writeInt	(I)V
        //   23: aload_1
        //   24: aload_2
        //   25: iconst_0
        //   26: invokevirtual 41	com/google/android/gms/car/CarCall:writeToParcel	(Landroid/os/Parcel;I)V
        //   29: aload_0
        //   30: getfield 15	com/google/android/gms/car/zzah$zza$zza:zzop	Landroid/os/IBinder;
        //   33: bipush 9
        //   35: aload_2
        //   36: aload_3
        //   37: iconst_0
        //   38: invokeinterface 47 5 0
        //   43: pop
        //   44: aload_3
        //   45: invokevirtual 50	android/os/Parcel:readException	()V
        //   48: aload_3
        //   49: invokevirtual 53	android/os/Parcel:recycle	()V
        //   52: aload_2
        //   53: invokevirtual 53	android/os/Parcel:recycle	()V
        //   56: return
        //   57: aload_2
        //   58: iconst_0
        //   59: invokevirtual 35	android/os/Parcel:writeInt	(I)V
        //   62: goto -33 -> 29
        //   65: astore 4
        //   67: aload_3
        //   68: invokevirtual 53	android/os/Parcel:recycle	()V
        //   71: aload_2
        //   72: invokevirtual 53	android/os/Parcel:recycle	()V
        //   75: aload 4
        //   77: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	78	0	this	zza
        //   0	78	1	paramCarCall	CarCall
        //   3	69	2	localParcel1	Parcel
        //   7	61	3	localParcel2	Parcel
        //   65	11	4	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   8	14	65	finally
        //   18	29	65	finally
        //   29	48	65	finally
        //   57	62	65	finally
      }
      
      public final IBinder asBinder()
      {
        return this.zzop;
      }
      
      public final void conference(CarCall paramCarCall1, CarCall paramCarCall2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarCall");
            if (paramCarCall1 != null)
            {
              localParcel1.writeInt(1);
              paramCarCall1.writeToParcel(localParcel1, 0);
              if (paramCarCall2 != null)
              {
                localParcel1.writeInt(1);
                paramCarCall2.writeToParcel(localParcel1, 0);
                this.zzop.transact(17, localParcel1, localParcel2, 0);
                localParcel2.readException();
              }
            }
            else
            {
              localParcel1.writeInt(0);
              continue;
            }
            localParcel1.writeInt(0);
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
        }
      }
      
      /* Error */
      public final void disconnectCall(CarCall paramCarCall)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 25	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: invokestatic 25	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_3
        //   8: aload_2
        //   9: ldc 27
        //   11: invokevirtual 31	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_1
        //   15: ifnull +42 -> 57
        //   18: aload_2
        //   19: iconst_1
        //   20: invokevirtual 35	android/os/Parcel:writeInt	(I)V
        //   23: aload_1
        //   24: aload_2
        //   25: iconst_0
        //   26: invokevirtual 41	com/google/android/gms/car/CarCall:writeToParcel	(Landroid/os/Parcel;I)V
        //   29: aload_0
        //   30: getfield 15	com/google/android/gms/car/zzah$zza$zza:zzop	Landroid/os/IBinder;
        //   33: bipush 11
        //   35: aload_2
        //   36: aload_3
        //   37: iconst_0
        //   38: invokeinterface 47 5 0
        //   43: pop
        //   44: aload_3
        //   45: invokevirtual 50	android/os/Parcel:readException	()V
        //   48: aload_3
        //   49: invokevirtual 53	android/os/Parcel:recycle	()V
        //   52: aload_2
        //   53: invokevirtual 53	android/os/Parcel:recycle	()V
        //   56: return
        //   57: aload_2
        //   58: iconst_0
        //   59: invokevirtual 35	android/os/Parcel:writeInt	(I)V
        //   62: goto -33 -> 29
        //   65: astore 4
        //   67: aload_3
        //   68: invokevirtual 53	android/os/Parcel:recycle	()V
        //   71: aload_2
        //   72: invokevirtual 53	android/os/Parcel:recycle	()V
        //   75: aload 4
        //   77: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	78	0	this	zza
        //   0	78	1	paramCarCall	CarCall
        //   3	69	2	localParcel1	Parcel
        //   7	61	3	localParcel2	Parcel
        //   65	11	4	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   8	14	65	finally
        //   18	29	65	finally
        //   29	48	65	finally
        //   57	62	65	finally
      }
      
      public final int getAudioRoute()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarCall");
          this.zzop.transact(5, localParcel1, localParcel2, 0);
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
      
      public final List<CarCall> getCalls()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarCall");
          this.zzop.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          ArrayList localArrayList = localParcel2.createTypedArrayList(CarCall.CREATOR);
          return localArrayList;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public final boolean getMuted()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarCall");
          this.zzop.transact(2, localParcel1, localParcel2, 0);
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
      
      public final int getSupportedAudioRouteMask()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarCall");
          this.zzop.transact(4, localParcel1, localParcel2, 0);
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
      
      /* Error */
      public final void holdCall(CarCall paramCarCall)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 25	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: invokestatic 25	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_3
        //   8: aload_2
        //   9: ldc 27
        //   11: invokevirtual 31	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_1
        //   15: ifnull +42 -> 57
        //   18: aload_2
        //   19: iconst_1
        //   20: invokevirtual 35	android/os/Parcel:writeInt	(I)V
        //   23: aload_1
        //   24: aload_2
        //   25: iconst_0
        //   26: invokevirtual 41	com/google/android/gms/car/CarCall:writeToParcel	(Landroid/os/Parcel;I)V
        //   29: aload_0
        //   30: getfield 15	com/google/android/gms/car/zzah$zza$zza:zzop	Landroid/os/IBinder;
        //   33: bipush 12
        //   35: aload_2
        //   36: aload_3
        //   37: iconst_0
        //   38: invokeinterface 47 5 0
        //   43: pop
        //   44: aload_3
        //   45: invokevirtual 50	android/os/Parcel:readException	()V
        //   48: aload_3
        //   49: invokevirtual 53	android/os/Parcel:recycle	()V
        //   52: aload_2
        //   53: invokevirtual 53	android/os/Parcel:recycle	()V
        //   56: return
        //   57: aload_2
        //   58: iconst_0
        //   59: invokevirtual 35	android/os/Parcel:writeInt	(I)V
        //   62: goto -33 -> 29
        //   65: astore 4
        //   67: aload_3
        //   68: invokevirtual 53	android/os/Parcel:recycle	()V
        //   71: aload_2
        //   72: invokevirtual 53	android/os/Parcel:recycle	()V
        //   75: aload 4
        //   77: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	78	0	this	zza
        //   0	78	1	paramCarCall	CarCall
        //   3	69	2	localParcel1	Parcel
        //   7	61	3	localParcel2	Parcel
        //   65	11	4	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   8	14	65	finally
        //   18	29	65	finally
        //   29	48	65	finally
        //   57	62	65	finally
      }
      
      public final void placeCall(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarCall");
          localParcel1.writeString(paramString);
          this.zzop.transact(19, localParcel1, localParcel2, 0);
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
      public final void playDtmfTone(CarCall paramCarCall, char paramChar)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 25	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: invokestatic 25	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore 4
        //   9: aload_3
        //   10: ldc 27
        //   12: invokevirtual 31	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   15: aload_1
        //   16: ifnull +50 -> 66
        //   19: aload_3
        //   20: iconst_1
        //   21: invokevirtual 35	android/os/Parcel:writeInt	(I)V
        //   24: aload_1
        //   25: aload_3
        //   26: iconst_0
        //   27: invokevirtual 41	com/google/android/gms/car/CarCall:writeToParcel	(Landroid/os/Parcel;I)V
        //   30: aload_3
        //   31: iload_2
        //   32: invokevirtual 35	android/os/Parcel:writeInt	(I)V
        //   35: aload_0
        //   36: getfield 15	com/google/android/gms/car/zzah$zza$zza:zzop	Landroid/os/IBinder;
        //   39: bipush 14
        //   41: aload_3
        //   42: aload 4
        //   44: iconst_0
        //   45: invokeinterface 47 5 0
        //   50: pop
        //   51: aload 4
        //   53: invokevirtual 50	android/os/Parcel:readException	()V
        //   56: aload 4
        //   58: invokevirtual 53	android/os/Parcel:recycle	()V
        //   61: aload_3
        //   62: invokevirtual 53	android/os/Parcel:recycle	()V
        //   65: return
        //   66: aload_3
        //   67: iconst_0
        //   68: invokevirtual 35	android/os/Parcel:writeInt	(I)V
        //   71: goto -41 -> 30
        //   74: astore 5
        //   76: aload 4
        //   78: invokevirtual 53	android/os/Parcel:recycle	()V
        //   81: aload_3
        //   82: invokevirtual 53	android/os/Parcel:recycle	()V
        //   85: aload 5
        //   87: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	88	0	this	zza
        //   0	88	1	paramCarCall	CarCall
        //   0	88	2	paramChar	char
        //   3	79	3	localParcel1	Parcel
        //   7	70	4	localParcel2	Parcel
        //   74	12	5	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   9	15	74	finally
        //   19	30	74	finally
        //   30	56	74	finally
        //   66	71	74	finally
      }
      
      public final void postDialContinue(CarCall paramCarCall, boolean paramBoolean)
        throws RemoteException
      {
        int i = 1;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarCall");
            if (paramCarCall != null)
            {
              localParcel1.writeInt(1);
              paramCarCall.writeToParcel(localParcel1, 0);
              break label107;
              localParcel1.writeInt(i);
              this.zzop.transact(16, localParcel1, localParcel2, 0);
              localParcel2.readException();
            }
            else
            {
              localParcel1.writeInt(0);
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          label107:
          while (!paramBoolean)
          {
            i = 0;
            break;
          }
        }
      }
      
      public final void rejectCall(CarCall paramCarCall, boolean paramBoolean, String paramString)
        throws RemoteException
      {
        int i = 1;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarCall");
            if (paramCarCall != null)
            {
              localParcel1.writeInt(1);
              paramCarCall.writeToParcel(localParcel1, 0);
              break label116;
              localParcel1.writeInt(i);
              localParcel1.writeString(paramString);
              this.zzop.transact(10, localParcel1, localParcel2, 0);
              localParcel2.readException();
            }
            else
            {
              localParcel1.writeInt(0);
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          label116:
          while (!paramBoolean)
          {
            i = 0;
            break;
          }
        }
      }
      
      public final void setAudioRoute(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarCall");
          localParcel1.writeInt(paramInt);
          this.zzop.transact(6, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public final void setMuted(boolean paramBoolean)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.car.ICarCall");
          int i = 0;
          if (paramBoolean) {
            i = 1;
          }
          localParcel1.writeInt(i);
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
      
      /* Error */
      public final void splitFromConference(CarCall paramCarCall)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 25	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: invokestatic 25	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_3
        //   8: aload_2
        //   9: ldc 27
        //   11: invokevirtual 31	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_1
        //   15: ifnull +42 -> 57
        //   18: aload_2
        //   19: iconst_1
        //   20: invokevirtual 35	android/os/Parcel:writeInt	(I)V
        //   23: aload_1
        //   24: aload_2
        //   25: iconst_0
        //   26: invokevirtual 41	com/google/android/gms/car/CarCall:writeToParcel	(Landroid/os/Parcel;I)V
        //   29: aload_0
        //   30: getfield 15	com/google/android/gms/car/zzah$zza$zza:zzop	Landroid/os/IBinder;
        //   33: bipush 18
        //   35: aload_2
        //   36: aload_3
        //   37: iconst_0
        //   38: invokeinterface 47 5 0
        //   43: pop
        //   44: aload_3
        //   45: invokevirtual 50	android/os/Parcel:readException	()V
        //   48: aload_3
        //   49: invokevirtual 53	android/os/Parcel:recycle	()V
        //   52: aload_2
        //   53: invokevirtual 53	android/os/Parcel:recycle	()V
        //   56: return
        //   57: aload_2
        //   58: iconst_0
        //   59: invokevirtual 35	android/os/Parcel:writeInt	(I)V
        //   62: goto -33 -> 29
        //   65: astore 4
        //   67: aload_3
        //   68: invokevirtual 53	android/os/Parcel:recycle	()V
        //   71: aload_2
        //   72: invokevirtual 53	android/os/Parcel:recycle	()V
        //   75: aload 4
        //   77: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	78	0	this	zza
        //   0	78	1	paramCarCall	CarCall
        //   3	69	2	localParcel1	Parcel
        //   7	61	3	localParcel2	Parcel
        //   65	11	4	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   8	14	65	finally
        //   18	29	65	finally
        //   29	48	65	finally
        //   57	62	65	finally
      }
      
      /* Error */
      public final void stopDtmfTone(CarCall paramCarCall)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 25	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: invokestatic 25	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_3
        //   8: aload_2
        //   9: ldc 27
        //   11: invokevirtual 31	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_1
        //   15: ifnull +42 -> 57
        //   18: aload_2
        //   19: iconst_1
        //   20: invokevirtual 35	android/os/Parcel:writeInt	(I)V
        //   23: aload_1
        //   24: aload_2
        //   25: iconst_0
        //   26: invokevirtual 41	com/google/android/gms/car/CarCall:writeToParcel	(Landroid/os/Parcel;I)V
        //   29: aload_0
        //   30: getfield 15	com/google/android/gms/car/zzah$zza$zza:zzop	Landroid/os/IBinder;
        //   33: bipush 15
        //   35: aload_2
        //   36: aload_3
        //   37: iconst_0
        //   38: invokeinterface 47 5 0
        //   43: pop
        //   44: aload_3
        //   45: invokevirtual 50	android/os/Parcel:readException	()V
        //   48: aload_3
        //   49: invokevirtual 53	android/os/Parcel:recycle	()V
        //   52: aload_2
        //   53: invokevirtual 53	android/os/Parcel:recycle	()V
        //   56: return
        //   57: aload_2
        //   58: iconst_0
        //   59: invokevirtual 35	android/os/Parcel:writeInt	(I)V
        //   62: goto -33 -> 29
        //   65: astore 4
        //   67: aload_3
        //   68: invokevirtual 53	android/os/Parcel:recycle	()V
        //   71: aload_2
        //   72: invokevirtual 53	android/os/Parcel:recycle	()V
        //   75: aload 4
        //   77: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	78	0	this	zza
        //   0	78	1	paramCarCall	CarCall
        //   3	69	2	localParcel1	Parcel
        //   7	61	3	localParcel2	Parcel
        //   65	11	4	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   8	14	65	finally
        //   18	29	65	finally
        //   29	48	65	finally
        //   57	62	65	finally
      }
      
      /* Error */
      public final void unholdCall(CarCall paramCarCall)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 25	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: invokestatic 25	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_3
        //   8: aload_2
        //   9: ldc 27
        //   11: invokevirtual 31	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_1
        //   15: ifnull +42 -> 57
        //   18: aload_2
        //   19: iconst_1
        //   20: invokevirtual 35	android/os/Parcel:writeInt	(I)V
        //   23: aload_1
        //   24: aload_2
        //   25: iconst_0
        //   26: invokevirtual 41	com/google/android/gms/car/CarCall:writeToParcel	(Landroid/os/Parcel;I)V
        //   29: aload_0
        //   30: getfield 15	com/google/android/gms/car/zzah$zza$zza:zzop	Landroid/os/IBinder;
        //   33: bipush 13
        //   35: aload_2
        //   36: aload_3
        //   37: iconst_0
        //   38: invokeinterface 47 5 0
        //   43: pop
        //   44: aload_3
        //   45: invokevirtual 50	android/os/Parcel:readException	()V
        //   48: aload_3
        //   49: invokevirtual 53	android/os/Parcel:recycle	()V
        //   52: aload_2
        //   53: invokevirtual 53	android/os/Parcel:recycle	()V
        //   56: return
        //   57: aload_2
        //   58: iconst_0
        //   59: invokevirtual 35	android/os/Parcel:writeInt	(I)V
        //   62: goto -33 -> 29
        //   65: astore 4
        //   67: aload_3
        //   68: invokevirtual 53	android/os/Parcel:recycle	()V
        //   71: aload_2
        //   72: invokevirtual 53	android/os/Parcel:recycle	()V
        //   75: aload 4
        //   77: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	78	0	this	zza
        //   0	78	1	paramCarCall	CarCall
        //   3	69	2	localParcel1	Parcel
        //   7	61	3	localParcel2	Parcel
        //   65	11	4	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   8	14	65	finally
        //   18	29	65	finally
        //   29	48	65	finally
        //   57	62	65	finally
      }
      
      /* Error */
      public final boolean zza(zzai paramzzai)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 25	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: invokestatic 25	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_3
        //   8: aload_2
        //   9: ldc 27
        //   11: invokevirtual 31	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_1
        //   15: ifnull +64 -> 79
        //   18: aload_1
        //   19: invokeinterface 99 1 0
        //   24: astore 5
        //   26: aload_2
        //   27: aload 5
        //   29: invokevirtual 102	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   32: aload_0
        //   33: getfield 15	com/google/android/gms/car/zzah$zza$zza:zzop	Landroid/os/IBinder;
        //   36: bipush 7
        //   38: aload_2
        //   39: aload_3
        //   40: iconst_0
        //   41: invokeinterface 47 5 0
        //   46: pop
        //   47: aload_3
        //   48: invokevirtual 50	android/os/Parcel:readException	()V
        //   51: aload_3
        //   52: invokevirtual 63	android/os/Parcel:readInt	()I
        //   55: istore 7
        //   57: iconst_0
        //   58: istore 8
        //   60: iload 7
        //   62: ifeq +6 -> 68
        //   65: iconst_1
        //   66: istore 8
        //   68: aload_3
        //   69: invokevirtual 53	android/os/Parcel:recycle	()V
        //   72: aload_2
        //   73: invokevirtual 53	android/os/Parcel:recycle	()V
        //   76: iload 8
        //   78: ireturn
        //   79: aconst_null
        //   80: astore 5
        //   82: goto -56 -> 26
        //   85: astore 4
        //   87: aload_3
        //   88: invokevirtual 53	android/os/Parcel:recycle	()V
        //   91: aload_2
        //   92: invokevirtual 53	android/os/Parcel:recycle	()V
        //   95: aload 4
        //   97: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	98	0	this	zza
        //   0	98	1	paramzzai	zzai
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
      public final boolean zzb(zzai paramzzai)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 25	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: invokestatic 25	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_3
        //   8: aload_2
        //   9: ldc 27
        //   11: invokevirtual 31	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_1
        //   15: ifnull +64 -> 79
        //   18: aload_1
        //   19: invokeinterface 99 1 0
        //   24: astore 5
        //   26: aload_2
        //   27: aload 5
        //   29: invokevirtual 102	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   32: aload_0
        //   33: getfield 15	com/google/android/gms/car/zzah$zza$zza:zzop	Landroid/os/IBinder;
        //   36: bipush 8
        //   38: aload_2
        //   39: aload_3
        //   40: iconst_0
        //   41: invokeinterface 47 5 0
        //   46: pop
        //   47: aload_3
        //   48: invokevirtual 50	android/os/Parcel:readException	()V
        //   51: aload_3
        //   52: invokevirtual 63	android/os/Parcel:readInt	()I
        //   55: istore 7
        //   57: iconst_0
        //   58: istore 8
        //   60: iload 7
        //   62: ifeq +6 -> 68
        //   65: iconst_1
        //   66: istore 8
        //   68: aload_3
        //   69: invokevirtual 53	android/os/Parcel:recycle	()V
        //   72: aload_2
        //   73: invokevirtual 53	android/os/Parcel:recycle	()V
        //   76: iload 8
        //   78: ireturn
        //   79: aconst_null
        //   80: astore 5
        //   82: goto -56 -> 26
        //   85: astore 4
        //   87: aload_3
        //   88: invokevirtual 53	android/os/Parcel:recycle	()V
        //   91: aload_2
        //   92: invokevirtual 53	android/os/Parcel:recycle	()V
        //   95: aload 4
        //   97: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	98	0	this	zza
        //   0	98	1	paramzzai	zzai
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
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzah
 * JD-Core Version:    0.7.0.1
 */