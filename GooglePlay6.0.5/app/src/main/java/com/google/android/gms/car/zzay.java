package com.google.android.gms.car;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import java.util.List;

public abstract interface zzay
  extends IInterface
{
  public abstract void onActiveRadioSelected(int paramInt1, int paramInt2, RadioStationInfo paramRadioStationInfo)
    throws RemoteException;
  
  public abstract void onCancel(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract void onChannelSpacingConfig(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;
  
  public abstract void onMute(int paramInt1, int paramInt2, boolean paramBoolean)
    throws RemoteException;
  
  public abstract void onProgramList(int paramInt1, int paramInt2, boolean paramBoolean, List<RadioStationInfo> paramList)
    throws RemoteException;
  
  public abstract void onRadioSource(int paramInt, boolean paramBoolean)
    throws RemoteException;
  
  public abstract void onScan(int paramInt1, int paramInt2, boolean paramBoolean)
    throws RemoteException;
  
  public abstract void onSeek(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract void onStationInfoUpdate(int paramInt, RadioStationInfo paramRadioStationInfo)
    throws RemoteException;
  
  public abstract void onStationPresets(List<StationPresetList> paramList)
    throws RemoteException;
  
  public abstract void onStep(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract void onTrafficUpdate(int paramInt1, int paramInt2, List<TrafficIncident> paramList)
    throws RemoteException;
  
  public abstract void onTune(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzay
  {
    public static zzay zzbD(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.car.ICarRadioCallback");
      if ((localIInterface != null) && ((localIInterface instanceof zzay))) {
        return (zzay)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.car.ICarRadioCallback");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarRadioCallback");
        onStep(paramParcel1.readInt(), paramParcel1.readInt());
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarRadioCallback");
        onSeek(paramParcel1.readInt(), paramParcel1.readInt());
        return true;
      case 3: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarRadioCallback");
        int i7 = paramParcel1.readInt();
        int i8 = paramParcel1.readInt();
        int i9 = paramParcel1.readInt();
        boolean bool4 = false;
        if (i9 != 0) {
          bool4 = true;
        }
        onScan(i7, i8, bool4);
        return true;
      case 4: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarRadioCallback");
        onTune(paramParcel1.readInt(), paramParcel1.readInt());
        return true;
      case 5: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarRadioCallback");
        int i6 = paramParcel1.readInt();
        if (paramParcel1.readInt() != 0) {}
        for (RadioStationInfo localRadioStationInfo2 = (RadioStationInfo)RadioStationInfo.CREATOR.createFromParcel(paramParcel1);; localRadioStationInfo2 = null)
        {
          onStationInfoUpdate(i6, localRadioStationInfo2);
          return true;
        }
      case 6: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarRadioCallback");
        int i3 = paramParcel1.readInt();
        int i4 = paramParcel1.readInt();
        int i5 = paramParcel1.readInt();
        boolean bool3 = false;
        if (i5 != 0) {
          bool3 = true;
        }
        onMute(i3, i4, bool3);
        return true;
      case 7: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarRadioCallback");
        int i1 = paramParcel1.readInt();
        int i2 = paramParcel1.readInt();
        boolean bool2 = false;
        if (i2 != 0) {
          bool2 = true;
        }
        onRadioSource(i1, bool2);
        return true;
      case 8: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarRadioCallback");
        int m = paramParcel1.readInt();
        int n = paramParcel1.readInt();
        if (paramParcel1.readInt() != 0) {}
        for (RadioStationInfo localRadioStationInfo1 = (RadioStationInfo)RadioStationInfo.CREATOR.createFromParcel(paramParcel1);; localRadioStationInfo1 = null)
        {
          onActiveRadioSelected(m, n, localRadioStationInfo1);
          return true;
        }
      case 9: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarRadioCallback");
        onStationPresets(paramParcel1.createTypedArrayList(StationPresetList.CREATOR));
        return true;
      case 10: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarRadioCallback");
        int i = paramParcel1.readInt();
        int j = paramParcel1.readInt();
        int k = paramParcel1.readInt();
        boolean bool1 = false;
        if (k != 0) {
          bool1 = true;
        }
        onProgramList(i, j, bool1, paramParcel1.createTypedArrayList(RadioStationInfo.CREATOR));
        return true;
      case 11: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarRadioCallback");
        onTrafficUpdate(paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.createTypedArrayList(TrafficIncident.CREATOR));
        return true;
      case 12: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarRadioCallback");
        onChannelSpacingConfig(paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt());
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.car.ICarRadioCallback");
      onCancel(paramParcel1.readInt(), paramParcel1.readInt());
      return true;
    }
    
    private static final class zza
      implements zzay
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
      public final void onActiveRadioSelected(int paramInt1, int paramInt2, RadioStationInfo paramRadioStationInfo)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore 4
        //   5: aload 4
        //   7: ldc 29
        //   9: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   12: aload 4
        //   14: iload_1
        //   15: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   18: aload 4
        //   20: iload_2
        //   21: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   24: aload_3
        //   25: ifnull +38 -> 63
        //   28: aload 4
        //   30: iconst_1
        //   31: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   34: aload_3
        //   35: aload 4
        //   37: iconst_0
        //   38: invokevirtual 43	com/google/android/gms/car/RadioStationInfo:writeToParcel	(Landroid/os/Parcel;I)V
        //   41: aload_0
        //   42: getfield 15	com/google/android/gms/car/zzay$zza$zza:zzop	Landroid/os/IBinder;
        //   45: bipush 8
        //   47: aload 4
        //   49: aconst_null
        //   50: iconst_1
        //   51: invokeinterface 49 5 0
        //   56: pop
        //   57: aload 4
        //   59: invokevirtual 52	android/os/Parcel:recycle	()V
        //   62: return
        //   63: aload 4
        //   65: iconst_0
        //   66: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   69: goto -28 -> 41
        //   72: astore 5
        //   74: aload 4
        //   76: invokevirtual 52	android/os/Parcel:recycle	()V
        //   79: aload 5
        //   81: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	82	0	this	zza
        //   0	82	1	paramInt1	int
        //   0	82	2	paramInt2	int
        //   0	82	3	paramRadioStationInfo	RadioStationInfo
        //   3	72	4	localParcel	Parcel
        //   72	8	5	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   5	24	72	finally
        //   28	41	72	finally
        //   41	57	72	finally
        //   63	69	72	finally
      }
      
      public final void onCancel(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarRadioCallback");
          localParcel.writeInt(paramInt1);
          localParcel.writeInt(paramInt2);
          this.zzop.transact(13, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public final void onChannelSpacingConfig(int paramInt1, int paramInt2, int paramInt3)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarRadioCallback");
          localParcel.writeInt(paramInt1);
          localParcel.writeInt(paramInt2);
          localParcel.writeInt(paramInt3);
          this.zzop.transact(12, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      /* Error */
      public final void onMute(int paramInt1, int paramInt2, boolean paramBoolean)
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
        //   15: aload 5
        //   17: iload_1
        //   18: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   21: aload 5
        //   23: iload_2
        //   24: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   27: iload_3
        //   28: ifeq +32 -> 60
        //   31: aload 5
        //   33: iload 4
        //   35: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   38: aload_0
        //   39: getfield 15	com/google/android/gms/car/zzay$zza$zza:zzop	Landroid/os/IBinder;
        //   42: bipush 6
        //   44: aload 5
        //   46: aconst_null
        //   47: iconst_1
        //   48: invokeinterface 49 5 0
        //   53: pop
        //   54: aload 5
        //   56: invokevirtual 52	android/os/Parcel:recycle	()V
        //   59: return
        //   60: iconst_0
        //   61: istore 4
        //   63: goto -32 -> 31
        //   66: astore 6
        //   68: aload 5
        //   70: invokevirtual 52	android/os/Parcel:recycle	()V
        //   73: aload 6
        //   75: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	76	0	this	zza
        //   0	76	1	paramInt1	int
        //   0	76	2	paramInt2	int
        //   0	76	3	paramBoolean	boolean
        //   1	61	4	i	int
        //   6	63	5	localParcel	Parcel
        //   66	8	6	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   8	27	66	finally
        //   31	54	66	finally
      }
      
      /* Error */
      public final void onProgramList(int paramInt1, int paramInt2, boolean paramBoolean, List<RadioStationInfo> paramList)
        throws RemoteException
      {
        // Byte code:
        //   0: iconst_1
        //   1: istore 5
        //   3: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   6: astore 6
        //   8: aload 6
        //   10: ldc 29
        //   12: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   15: aload 6
        //   17: iload_1
        //   18: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   21: aload 6
        //   23: iload_2
        //   24: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   27: iload_3
        //   28: ifeq +39 -> 67
        //   31: aload 6
        //   33: iload 5
        //   35: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   38: aload 6
        //   40: aload 4
        //   42: invokevirtual 64	android/os/Parcel:writeTypedList	(Ljava/util/List;)V
        //   45: aload_0
        //   46: getfield 15	com/google/android/gms/car/zzay$zza$zza:zzop	Landroid/os/IBinder;
        //   49: bipush 10
        //   51: aload 6
        //   53: aconst_null
        //   54: iconst_1
        //   55: invokeinterface 49 5 0
        //   60: pop
        //   61: aload 6
        //   63: invokevirtual 52	android/os/Parcel:recycle	()V
        //   66: return
        //   67: iconst_0
        //   68: istore 5
        //   70: goto -39 -> 31
        //   73: astore 7
        //   75: aload 6
        //   77: invokevirtual 52	android/os/Parcel:recycle	()V
        //   80: aload 7
        //   82: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	83	0	this	zza
        //   0	83	1	paramInt1	int
        //   0	83	2	paramInt2	int
        //   0	83	3	paramBoolean	boolean
        //   0	83	4	paramList	List<RadioStationInfo>
        //   1	68	5	i	int
        //   6	70	6	localParcel	Parcel
        //   73	8	7	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   8	27	73	finally
        //   31	61	73	finally
      }
      
      /* Error */
      public final void onRadioSource(int paramInt, boolean paramBoolean)
        throws RemoteException
      {
        // Byte code:
        //   0: iconst_1
        //   1: istore_3
        //   2: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   5: astore 4
        //   7: aload 4
        //   9: ldc 29
        //   11: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload 4
        //   16: iload_1
        //   17: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   20: iload_2
        //   21: ifeq +31 -> 52
        //   24: aload 4
        //   26: iload_3
        //   27: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   30: aload_0
        //   31: getfield 15	com/google/android/gms/car/zzay$zza$zza:zzop	Landroid/os/IBinder;
        //   34: bipush 7
        //   36: aload 4
        //   38: aconst_null
        //   39: iconst_1
        //   40: invokeinterface 49 5 0
        //   45: pop
        //   46: aload 4
        //   48: invokevirtual 52	android/os/Parcel:recycle	()V
        //   51: return
        //   52: iconst_0
        //   53: istore_3
        //   54: goto -30 -> 24
        //   57: astore 5
        //   59: aload 4
        //   61: invokevirtual 52	android/os/Parcel:recycle	()V
        //   64: aload 5
        //   66: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	67	0	this	zza
        //   0	67	1	paramInt	int
        //   0	67	2	paramBoolean	boolean
        //   1	53	3	i	int
        //   5	55	4	localParcel	Parcel
        //   57	8	5	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   7	20	57	finally
        //   24	46	57	finally
      }
      
      /* Error */
      public final void onScan(int paramInt1, int paramInt2, boolean paramBoolean)
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
        //   15: aload 5
        //   17: iload_1
        //   18: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   21: aload 5
        //   23: iload_2
        //   24: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   27: iload_3
        //   28: ifeq +31 -> 59
        //   31: aload 5
        //   33: iload 4
        //   35: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   38: aload_0
        //   39: getfield 15	com/google/android/gms/car/zzay$zza$zza:zzop	Landroid/os/IBinder;
        //   42: iconst_3
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
        //   62: goto -31 -> 31
        //   65: astore 6
        //   67: aload 5
        //   69: invokevirtual 52	android/os/Parcel:recycle	()V
        //   72: aload 6
        //   74: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	75	0	this	zza
        //   0	75	1	paramInt1	int
        //   0	75	2	paramInt2	int
        //   0	75	3	paramBoolean	boolean
        //   1	60	4	i	int
        //   6	62	5	localParcel	Parcel
        //   65	8	6	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   8	27	65	finally
        //   31	53	65	finally
      }
      
      public final void onSeek(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarRadioCallback");
          localParcel.writeInt(paramInt1);
          localParcel.writeInt(paramInt2);
          this.zzop.transact(2, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      /* Error */
      public final void onStationInfoUpdate(int paramInt, RadioStationInfo paramRadioStationInfo)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: aload_3
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_3
        //   11: iload_1
        //   12: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   15: aload_2
        //   16: ifnull +33 -> 49
        //   19: aload_3
        //   20: iconst_1
        //   21: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   24: aload_2
        //   25: aload_3
        //   26: iconst_0
        //   27: invokevirtual 43	com/google/android/gms/car/RadioStationInfo:writeToParcel	(Landroid/os/Parcel;I)V
        //   30: aload_0
        //   31: getfield 15	com/google/android/gms/car/zzay$zza$zza:zzop	Landroid/os/IBinder;
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
        //   54: goto -24 -> 30
        //   57: astore 4
        //   59: aload_3
        //   60: invokevirtual 52	android/os/Parcel:recycle	()V
        //   63: aload 4
        //   65: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	66	0	this	zza
        //   0	66	1	paramInt	int
        //   0	66	2	paramRadioStationInfo	RadioStationInfo
        //   3	57	3	localParcel	Parcel
        //   57	7	4	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	15	57	finally
        //   19	30	57	finally
        //   30	44	57	finally
        //   49	54	57	finally
      }
      
      public final void onStationPresets(List<StationPresetList> paramList)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarRadioCallback");
          localParcel.writeTypedList(paramList);
          this.zzop.transact(9, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public final void onStep(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarRadioCallback");
          localParcel.writeInt(paramInt1);
          localParcel.writeInt(paramInt2);
          this.zzop.transact(1, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public final void onTrafficUpdate(int paramInt1, int paramInt2, List<TrafficIncident> paramList)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarRadioCallback");
          localParcel.writeInt(paramInt1);
          localParcel.writeInt(paramInt2);
          localParcel.writeTypedList(paramList);
          this.zzop.transact(11, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public final void onTune(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarRadioCallback");
          localParcel.writeInt(paramInt1);
          localParcel.writeInt(paramInt2);
          this.zzop.transact(4, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzay
 * JD-Core Version:    0.7.0.1
 */