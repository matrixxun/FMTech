package com.google.android.gms.car;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.view.KeyEvent;
import java.util.List;

public abstract interface zzai
  extends IInterface
{
  public abstract void dispatchPhoneKeyEvent(KeyEvent paramKeyEvent)
    throws RemoteException;
  
  public abstract void onAudioStateChanged(boolean paramBoolean, int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract void onCallAdded(CarCall paramCarCall)
    throws RemoteException;
  
  public abstract void onCallDestroyed(CarCall paramCarCall)
    throws RemoteException;
  
  public abstract void onCallRemoved(CarCall paramCarCall)
    throws RemoteException;
  
  public abstract void onCannedTextResponsesLoaded(CarCall paramCarCall, List<String> paramList)
    throws RemoteException;
  
  public abstract void onChildrenChanged(CarCall paramCarCall, List<CarCall> paramList)
    throws RemoteException;
  
  public abstract void onConferenceableCallsChanged(CarCall paramCarCall, List<CarCall> paramList)
    throws RemoteException;
  
  public abstract void onDetailsChanged(CarCall paramCarCall, CarCall.Details paramDetails)
    throws RemoteException;
  
  public abstract void onParentChanged(CarCall paramCarCall1, CarCall paramCarCall2)
    throws RemoteException;
  
  public abstract void onPostDialWait(CarCall paramCarCall, String paramString)
    throws RemoteException;
  
  public abstract void onStateChanged(CarCall paramCarCall, int paramInt)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzai
  {
    public static zzai zzbo(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.car.ICarCallListener");
      if ((localIInterface != null) && ((localIInterface instanceof zzai))) {
        return (zzai)localIInterface;
      }
      return new zza(paramIBinder);
    }
    
    public IBinder asBinder()
    {
      return this;
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      default: 
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902: 
        paramParcel2.writeString("com.google.android.gms.car.ICarCallListener");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCallListener");
        if (paramParcel1.readInt() != 0) {}
        for (KeyEvent localKeyEvent = (KeyEvent)KeyEvent.CREATOR.createFromParcel(paramParcel1);; localKeyEvent = null)
        {
          dispatchPhoneKeyEvent(localKeyEvent);
          return true;
        }
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCallListener");
        if (paramParcel1.readInt() != 0) {}
        for (boolean bool = true;; bool = false)
        {
          onAudioStateChanged(bool, paramParcel1.readInt(), paramParcel1.readInt());
          return true;
        }
      case 3: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCallListener");
        if (paramParcel1.readInt() != 0) {}
        for (CarCall localCarCall11 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);; localCarCall11 = null)
        {
          onCallAdded(localCarCall11);
          return true;
        }
      case 4: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCallListener");
        if (paramParcel1.readInt() != 0) {}
        for (CarCall localCarCall10 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);; localCarCall10 = null)
        {
          onCallRemoved(localCarCall10);
          return true;
        }
      case 5: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCallListener");
        if (paramParcel1.readInt() != 0) {}
        for (CarCall localCarCall9 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);; localCarCall9 = null)
        {
          onStateChanged(localCarCall9, paramParcel1.readInt());
          return true;
        }
      case 6: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCallListener");
        CarCall localCarCall7;
        if (paramParcel1.readInt() != 0)
        {
          localCarCall7 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label404;
          }
        }
        for (CarCall localCarCall8 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);; localCarCall8 = null)
        {
          onParentChanged(localCarCall7, localCarCall8);
          return true;
          localCarCall7 = null;
          break;
        }
      case 7: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCallListener");
        if (paramParcel1.readInt() != 0) {}
        for (CarCall localCarCall6 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);; localCarCall6 = null)
        {
          onChildrenChanged(localCarCall6, paramParcel1.createTypedArrayList(CarCall.CREATOR));
          return true;
        }
      case 8: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCallListener");
        CarCall localCarCall5;
        if (paramParcel1.readInt() != 0)
        {
          localCarCall5 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label522;
          }
        }
        for (CarCall.Details localDetails = (CarCall.Details)CarCall.Details.CREATOR.createFromParcel(paramParcel1);; localDetails = null)
        {
          onDetailsChanged(localCarCall5, localDetails);
          return true;
          localCarCall5 = null;
          break;
        }
      case 9: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCallListener");
        if (paramParcel1.readInt() != 0) {}
        for (CarCall localCarCall4 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);; localCarCall4 = null)
        {
          onCannedTextResponsesLoaded(localCarCall4, paramParcel1.createStringArrayList());
          return true;
        }
      case 10: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCallListener");
        if (paramParcel1.readInt() != 0) {}
        for (CarCall localCarCall3 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);; localCarCall3 = null)
        {
          onPostDialWait(localCarCall3, paramParcel1.readString());
          return true;
        }
      case 11: 
        label404:
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarCallListener");
        label522:
        if (paramParcel1.readInt() != 0) {}
        for (CarCall localCarCall2 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);; localCarCall2 = null)
        {
          onCallDestroyed(localCarCall2);
          return true;
        }
      }
      paramParcel1.enforceInterface("com.google.android.gms.car.ICarCallListener");
      if (paramParcel1.readInt() != 0) {}
      for (CarCall localCarCall1 = (CarCall)CarCall.CREATOR.createFromParcel(paramParcel1);; localCarCall1 = null)
      {
        onConferenceableCallsChanged(localCarCall1, paramParcel1.createTypedArrayList(CarCall.CREATOR));
        return true;
      }
    }
    
    private static final class zza
      implements zzai
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
      public final void dispatchPhoneKeyEvent(KeyEvent paramKeyEvent)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +33 -> 44
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 43	android/view/KeyEvent:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 15	com/google/android/gms/car/zzai$zza$zza:zzop	Landroid/os/IBinder;
        //   29: iconst_1
        //   30: aload_2
        //   31: aconst_null
        //   32: iconst_1
        //   33: invokeinterface 49 5 0
        //   38: pop
        //   39: aload_2
        //   40: invokevirtual 52	android/os/Parcel:recycle	()V
        //   43: return
        //   44: aload_2
        //   45: iconst_0
        //   46: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   49: goto -24 -> 25
        //   52: astore_3
        //   53: aload_2
        //   54: invokevirtual 52	android/os/Parcel:recycle	()V
        //   57: aload_3
        //   58: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	59	0	this	zza
        //   0	59	1	paramKeyEvent	KeyEvent
        //   3	51	2	localParcel	Parcel
        //   52	6	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	10	52	finally
        //   14	25	52	finally
        //   25	39	52	finally
        //   44	49	52	finally
      }
      
      /* Error */
      public final void onAudioStateChanged(boolean paramBoolean, int paramInt1, int paramInt2)
        throws RemoteException
      {
        // Byte code:
        //   0: iconst_1
        //   1: istore 4
        //   3: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   6: astore 5
        //   8: aload 5
        //   10: ldc 29
        //   12: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   15: iload_1
        //   16: ifeq +43 -> 59
        //   19: aload 5
        //   21: iload 4
        //   23: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   26: aload 5
        //   28: iload_2
        //   29: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   32: aload 5
        //   34: iload_3
        //   35: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   38: aload_0
        //   39: getfield 15	com/google/android/gms/car/zzai$zza$zza:zzop	Landroid/os/IBinder;
        //   42: iconst_2
        //   43: aload 5
        //   45: aconst_null
        //   46: iconst_1
        //   47: invokeinterface 49 5 0
        //   52: pop
        //   53: aload 5
        //   55: invokevirtual 52	android/os/Parcel:recycle	()V
        //   58: return
        //   59: iconst_0
        //   60: istore 4
        //   62: goto -43 -> 19
        //   65: astore 6
        //   67: aload 5
        //   69: invokevirtual 52	android/os/Parcel:recycle	()V
        //   72: aload 6
        //   74: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	75	0	this	zza
        //   0	75	1	paramBoolean	boolean
        //   0	75	2	paramInt1	int
        //   0	75	3	paramInt2	int
        //   1	60	4	i	int
        //   6	62	5	localParcel	Parcel
        //   65	8	6	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   8	15	65	finally
        //   19	53	65	finally
      }
      
      /* Error */
      public final void onCallAdded(CarCall paramCarCall)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +33 -> 44
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 59	com/google/android/gms/car/CarCall:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 15	com/google/android/gms/car/zzai$zza$zza:zzop	Landroid/os/IBinder;
        //   29: iconst_3
        //   30: aload_2
        //   31: aconst_null
        //   32: iconst_1
        //   33: invokeinterface 49 5 0
        //   38: pop
        //   39: aload_2
        //   40: invokevirtual 52	android/os/Parcel:recycle	()V
        //   43: return
        //   44: aload_2
        //   45: iconst_0
        //   46: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   49: goto -24 -> 25
        //   52: astore_3
        //   53: aload_2
        //   54: invokevirtual 52	android/os/Parcel:recycle	()V
        //   57: aload_3
        //   58: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	59	0	this	zza
        //   0	59	1	paramCarCall	CarCall
        //   3	51	2	localParcel	Parcel
        //   52	6	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	10	52	finally
        //   14	25	52	finally
        //   25	39	52	finally
        //   44	49	52	finally
      }
      
      /* Error */
      public final void onCallDestroyed(CarCall paramCarCall)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +34 -> 45
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 59	com/google/android/gms/car/CarCall:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 15	com/google/android/gms/car/zzai$zza$zza:zzop	Landroid/os/IBinder;
        //   29: bipush 11
        //   31: aload_2
        //   32: aconst_null
        //   33: iconst_1
        //   34: invokeinterface 49 5 0
        //   39: pop
        //   40: aload_2
        //   41: invokevirtual 52	android/os/Parcel:recycle	()V
        //   44: return
        //   45: aload_2
        //   46: iconst_0
        //   47: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   50: goto -25 -> 25
        //   53: astore_3
        //   54: aload_2
        //   55: invokevirtual 52	android/os/Parcel:recycle	()V
        //   58: aload_3
        //   59: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	60	0	this	zza
        //   0	60	1	paramCarCall	CarCall
        //   3	52	2	localParcel	Parcel
        //   53	6	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	10	53	finally
        //   14	25	53	finally
        //   25	40	53	finally
        //   45	50	53	finally
      }
      
      /* Error */
      public final void onCallRemoved(CarCall paramCarCall)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +33 -> 44
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 59	com/google/android/gms/car/CarCall:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 15	com/google/android/gms/car/zzai$zza$zza:zzop	Landroid/os/IBinder;
        //   29: iconst_4
        //   30: aload_2
        //   31: aconst_null
        //   32: iconst_1
        //   33: invokeinterface 49 5 0
        //   38: pop
        //   39: aload_2
        //   40: invokevirtual 52	android/os/Parcel:recycle	()V
        //   43: return
        //   44: aload_2
        //   45: iconst_0
        //   46: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   49: goto -24 -> 25
        //   52: astore_3
        //   53: aload_2
        //   54: invokevirtual 52	android/os/Parcel:recycle	()V
        //   57: aload_3
        //   58: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	59	0	this	zza
        //   0	59	1	paramCarCall	CarCall
        //   3	51	2	localParcel	Parcel
        //   52	6	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	10	52	finally
        //   14	25	52	finally
        //   25	39	52	finally
        //   44	49	52	finally
      }
      
      /* Error */
      public final void onCannedTextResponsesLoaded(CarCall paramCarCall, List<String> paramList)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: aload_3
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +39 -> 50
        //   14: aload_3
        //   15: iconst_1
        //   16: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_3
        //   21: iconst_0
        //   22: invokevirtual 59	com/google/android/gms/car/CarCall:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_3
        //   26: aload_2
        //   27: invokevirtual 67	android/os/Parcel:writeStringList	(Ljava/util/List;)V
        //   30: aload_0
        //   31: getfield 15	com/google/android/gms/car/zzai$zza$zza:zzop	Landroid/os/IBinder;
        //   34: bipush 9
        //   36: aload_3
        //   37: aconst_null
        //   38: iconst_1
        //   39: invokeinterface 49 5 0
        //   44: pop
        //   45: aload_3
        //   46: invokevirtual 52	android/os/Parcel:recycle	()V
        //   49: return
        //   50: aload_3
        //   51: iconst_0
        //   52: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   55: goto -30 -> 25
        //   58: astore 4
        //   60: aload_3
        //   61: invokevirtual 52	android/os/Parcel:recycle	()V
        //   64: aload 4
        //   66: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	67	0	this	zza
        //   0	67	1	paramCarCall	CarCall
        //   0	67	2	paramList	List<String>
        //   3	58	3	localParcel	Parcel
        //   58	7	4	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	10	58	finally
        //   14	25	58	finally
        //   25	45	58	finally
        //   50	55	58	finally
      }
      
      /* Error */
      public final void onChildrenChanged(CarCall paramCarCall, List<CarCall> paramList)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: aload_3
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +39 -> 50
        //   14: aload_3
        //   15: iconst_1
        //   16: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_3
        //   21: iconst_0
        //   22: invokevirtual 59	com/google/android/gms/car/CarCall:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_3
        //   26: aload_2
        //   27: invokevirtual 71	android/os/Parcel:writeTypedList	(Ljava/util/List;)V
        //   30: aload_0
        //   31: getfield 15	com/google/android/gms/car/zzai$zza$zza:zzop	Landroid/os/IBinder;
        //   34: bipush 7
        //   36: aload_3
        //   37: aconst_null
        //   38: iconst_1
        //   39: invokeinterface 49 5 0
        //   44: pop
        //   45: aload_3
        //   46: invokevirtual 52	android/os/Parcel:recycle	()V
        //   49: return
        //   50: aload_3
        //   51: iconst_0
        //   52: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   55: goto -30 -> 25
        //   58: astore 4
        //   60: aload_3
        //   61: invokevirtual 52	android/os/Parcel:recycle	()V
        //   64: aload 4
        //   66: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	67	0	this	zza
        //   0	67	1	paramCarCall	CarCall
        //   0	67	2	paramList	List<CarCall>
        //   3	58	3	localParcel	Parcel
        //   58	7	4	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	10	58	finally
        //   14	25	58	finally
        //   25	45	58	finally
        //   50	55	58	finally
      }
      
      /* Error */
      public final void onConferenceableCallsChanged(CarCall paramCarCall, List<CarCall> paramList)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: aload_3
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +39 -> 50
        //   14: aload_3
        //   15: iconst_1
        //   16: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_3
        //   21: iconst_0
        //   22: invokevirtual 59	com/google/android/gms/car/CarCall:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_3
        //   26: aload_2
        //   27: invokevirtual 71	android/os/Parcel:writeTypedList	(Ljava/util/List;)V
        //   30: aload_0
        //   31: getfield 15	com/google/android/gms/car/zzai$zza$zza:zzop	Landroid/os/IBinder;
        //   34: bipush 12
        //   36: aload_3
        //   37: aconst_null
        //   38: iconst_1
        //   39: invokeinterface 49 5 0
        //   44: pop
        //   45: aload_3
        //   46: invokevirtual 52	android/os/Parcel:recycle	()V
        //   49: return
        //   50: aload_3
        //   51: iconst_0
        //   52: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   55: goto -30 -> 25
        //   58: astore 4
        //   60: aload_3
        //   61: invokevirtual 52	android/os/Parcel:recycle	()V
        //   64: aload 4
        //   66: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	67	0	this	zza
        //   0	67	1	paramCarCall	CarCall
        //   0	67	2	paramList	List<CarCall>
        //   3	58	3	localParcel	Parcel
        //   58	7	4	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	10	58	finally
        //   14	25	58	finally
        //   25	45	58	finally
        //   50	55	58	finally
      }
      
      public final void onDetailsChanged(CarCall paramCarCall, CarCall.Details paramDetails)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.car.ICarCallListener");
            if (paramCarCall != null)
            {
              localParcel.writeInt(1);
              paramCarCall.writeToParcel(localParcel, 0);
              if (paramDetails != null)
              {
                localParcel.writeInt(1);
                paramDetails.writeToParcel(localParcel, 0);
                this.zzop.transact(8, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
            localParcel.writeInt(0);
          }
          finally
          {
            localParcel.recycle();
          }
        }
      }
      
      public final void onParentChanged(CarCall paramCarCall1, CarCall paramCarCall2)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.car.ICarCallListener");
            if (paramCarCall1 != null)
            {
              localParcel.writeInt(1);
              paramCarCall1.writeToParcel(localParcel, 0);
              if (paramCarCall2 != null)
              {
                localParcel.writeInt(1);
                paramCarCall2.writeToParcel(localParcel, 0);
                this.zzop.transact(6, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
            localParcel.writeInt(0);
          }
          finally
          {
            localParcel.recycle();
          }
        }
      }
      
      /* Error */
      public final void onPostDialWait(CarCall paramCarCall, String paramString)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: aload_3
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +39 -> 50
        //   14: aload_3
        //   15: iconst_1
        //   16: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_3
        //   21: iconst_0
        //   22: invokevirtual 59	com/google/android/gms/car/CarCall:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_3
        //   26: aload_2
        //   27: invokevirtual 84	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   30: aload_0
        //   31: getfield 15	com/google/android/gms/car/zzai$zza$zza:zzop	Landroid/os/IBinder;
        //   34: bipush 10
        //   36: aload_3
        //   37: aconst_null
        //   38: iconst_1
        //   39: invokeinterface 49 5 0
        //   44: pop
        //   45: aload_3
        //   46: invokevirtual 52	android/os/Parcel:recycle	()V
        //   49: return
        //   50: aload_3
        //   51: iconst_0
        //   52: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   55: goto -30 -> 25
        //   58: astore 4
        //   60: aload_3
        //   61: invokevirtual 52	android/os/Parcel:recycle	()V
        //   64: aload 4
        //   66: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	67	0	this	zza
        //   0	67	1	paramCarCall	CarCall
        //   0	67	2	paramString	String
        //   3	58	3	localParcel	Parcel
        //   58	7	4	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	10	58	finally
        //   14	25	58	finally
        //   25	45	58	finally
        //   50	55	58	finally
      }
      
      /* Error */
      public final void onStateChanged(CarCall paramCarCall, int paramInt)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: aload_3
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +38 -> 49
        //   14: aload_3
        //   15: iconst_1
        //   16: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_3
        //   21: iconst_0
        //   22: invokevirtual 59	com/google/android/gms/car/CarCall:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_3
        //   26: iload_2
        //   27: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   30: aload_0
        //   31: getfield 15	com/google/android/gms/car/zzai$zza$zza:zzop	Landroid/os/IBinder;
        //   34: iconst_5
        //   35: aload_3
        //   36: aconst_null
        //   37: iconst_1
        //   38: invokeinterface 49 5 0
        //   43: pop
        //   44: aload_3
        //   45: invokevirtual 52	android/os/Parcel:recycle	()V
        //   48: return
        //   49: aload_3
        //   50: iconst_0
        //   51: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   54: goto -29 -> 25
        //   57: astore 4
        //   59: aload_3
        //   60: invokevirtual 52	android/os/Parcel:recycle	()V
        //   63: aload 4
        //   65: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	66	0	this	zza
        //   0	66	1	paramCarCall	CarCall
        //   0	66	2	paramInt	int
        //   3	57	3	localParcel	Parcel
        //   57	7	4	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	10	57	finally
        //   14	25	57	finally
        //   25	44	57	finally
        //   49	54	57	finally
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzai
 * JD-Core Version:    0.7.0.1
 */