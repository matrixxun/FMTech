package com.google.android.gms.car;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface zzap
  extends IInterface
{
  public abstract void zza(zzaq paramzzaq)
    throws RemoteException;
  
  public abstract void zza(zzaq paramzzaq, int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;
  
  public abstract boolean zza(zzaq paramzzaq, int paramInt)
    throws RemoteException;
  
  public abstract int[] zza(zzaq paramzzaq, int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract void zzb(zzaq paramzzaq)
    throws RemoteException;
  
  public abstract void zzb(zzaq paramzzaq, int paramInt)
    throws RemoteException;
  
  public abstract void zzc(zzaq paramzzaq)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzap
  {
    public static zzap zzbv(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.car.ICarMessage");
      if ((localIInterface != null) && ((localIInterface instanceof zzap))) {
        return (zzap)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.car.ICarMessage");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarMessage");
        zza(zzaq.zza.zzbw(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarMessage");
        zzb(zzaq.zza.zzbw(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      case 3: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarMessage");
        boolean bool = zza(zzaq.zza.zzbw(paramParcel1.readStrongBinder()), paramParcel1.readInt());
        paramParcel2.writeNoException();
        if (bool) {}
        for (int i = 1;; i = 0)
        {
          paramParcel2.writeInt(i);
          return true;
        }
      case 4: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarMessage");
        zza(zzaq.zza.zzbw(paramParcel1.readStrongBinder()), paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      case 5: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarMessage");
        int[] arrayOfInt = zza(zzaq.zza.zzbw(paramParcel1.readStrongBinder()), paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeIntArray(arrayOfInt);
        return true;
      case 6: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarMessage");
        zzb(zzaq.zza.zzbw(paramParcel1.readStrongBinder()), paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.car.ICarMessage");
      zzc(zzaq.zza.zzbw(paramParcel1.readStrongBinder()));
      paramParcel2.writeNoException();
      return true;
    }
    
    private static final class zza
      implements zzap
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
      public final void zza(zzaq paramzzaq)
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
        //   15: ifnull +44 -> 59
        //   18: aload_1
        //   19: invokeinterface 37 1 0
        //   24: astore 5
        //   26: aload_2
        //   27: aload 5
        //   29: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   32: aload_0
        //   33: getfield 15	com/google/android/gms/car/zzap$zza$zza:zzop	Landroid/os/IBinder;
        //   36: iconst_1
        //   37: aload_2
        //   38: aload_3
        //   39: iconst_0
        //   40: invokeinterface 46 5 0
        //   45: pop
        //   46: aload_3
        //   47: invokevirtual 49	android/os/Parcel:readException	()V
        //   50: aload_3
        //   51: invokevirtual 52	android/os/Parcel:recycle	()V
        //   54: aload_2
        //   55: invokevirtual 52	android/os/Parcel:recycle	()V
        //   58: return
        //   59: aconst_null
        //   60: astore 5
        //   62: goto -36 -> 26
        //   65: astore 4
        //   67: aload_3
        //   68: invokevirtual 52	android/os/Parcel:recycle	()V
        //   71: aload_2
        //   72: invokevirtual 52	android/os/Parcel:recycle	()V
        //   75: aload 4
        //   77: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	78	0	this	zza
        //   0	78	1	paramzzaq	zzaq
        //   3	69	2	localParcel1	Parcel
        //   7	61	3	localParcel2	Parcel
        //   65	11	4	localObject	Object
        //   24	37	5	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   8	14	65	finally
        //   18	26	65	finally
        //   26	50	65	finally
      }
      
      /* Error */
      public final void zza(zzaq paramzzaq, int paramInt1, int paramInt2, int paramInt3)
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
        //   18: ifnull +69 -> 87
        //   21: aload_1
        //   22: invokeinterface 37 1 0
        //   27: astore 8
        //   29: aload 5
        //   31: aload 8
        //   33: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   36: aload 5
        //   38: iload_2
        //   39: invokevirtual 57	android/os/Parcel:writeInt	(I)V
        //   42: aload 5
        //   44: iload_3
        //   45: invokevirtual 57	android/os/Parcel:writeInt	(I)V
        //   48: aload 5
        //   50: iload 4
        //   52: invokevirtual 57	android/os/Parcel:writeInt	(I)V
        //   55: aload_0
        //   56: getfield 15	com/google/android/gms/car/zzap$zza$zza:zzop	Landroid/os/IBinder;
        //   59: iconst_4
        //   60: aload 5
        //   62: aload 6
        //   64: iconst_0
        //   65: invokeinterface 46 5 0
        //   70: pop
        //   71: aload 6
        //   73: invokevirtual 49	android/os/Parcel:readException	()V
        //   76: aload 6
        //   78: invokevirtual 52	android/os/Parcel:recycle	()V
        //   81: aload 5
        //   83: invokevirtual 52	android/os/Parcel:recycle	()V
        //   86: return
        //   87: aconst_null
        //   88: astore 8
        //   90: goto -61 -> 29
        //   93: astore 7
        //   95: aload 6
        //   97: invokevirtual 52	android/os/Parcel:recycle	()V
        //   100: aload 5
        //   102: invokevirtual 52	android/os/Parcel:recycle	()V
        //   105: aload 7
        //   107: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	108	0	this	zza
        //   0	108	1	paramzzaq	zzaq
        //   0	108	2	paramInt1	int
        //   0	108	3	paramInt2	int
        //   0	108	4	paramInt3	int
        //   3	98	5	localParcel1	Parcel
        //   8	88	6	localParcel2	Parcel
        //   93	13	7	localObject	Object
        //   27	62	8	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   10	17	93	finally
        //   21	29	93	finally
        //   29	76	93	finally
      }
      
      /* Error */
      public final boolean zza(zzaq paramzzaq, int paramInt)
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
        //   16: ifnull +72 -> 88
        //   19: aload_1
        //   20: invokeinterface 37 1 0
        //   25: astore 6
        //   27: aload_3
        //   28: aload 6
        //   30: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   33: aload_3
        //   34: iload_2
        //   35: invokevirtual 57	android/os/Parcel:writeInt	(I)V
        //   38: aload_0
        //   39: getfield 15	com/google/android/gms/car/zzap$zza$zza:zzop	Landroid/os/IBinder;
        //   42: iconst_3
        //   43: aload_3
        //   44: aload 4
        //   46: iconst_0
        //   47: invokeinterface 46 5 0
        //   52: pop
        //   53: aload 4
        //   55: invokevirtual 49	android/os/Parcel:readException	()V
        //   58: aload 4
        //   60: invokevirtual 62	android/os/Parcel:readInt	()I
        //   63: istore 8
        //   65: iconst_0
        //   66: istore 9
        //   68: iload 8
        //   70: ifeq +6 -> 76
        //   73: iconst_1
        //   74: istore 9
        //   76: aload 4
        //   78: invokevirtual 52	android/os/Parcel:recycle	()V
        //   81: aload_3
        //   82: invokevirtual 52	android/os/Parcel:recycle	()V
        //   85: iload 9
        //   87: ireturn
        //   88: aconst_null
        //   89: astore 6
        //   91: goto -64 -> 27
        //   94: astore 5
        //   96: aload 4
        //   98: invokevirtual 52	android/os/Parcel:recycle	()V
        //   101: aload_3
        //   102: invokevirtual 52	android/os/Parcel:recycle	()V
        //   105: aload 5
        //   107: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	108	0	this	zza
        //   0	108	1	paramzzaq	zzaq
        //   0	108	2	paramInt	int
        //   3	99	3	localParcel1	Parcel
        //   7	90	4	localParcel2	Parcel
        //   94	12	5	localObject	Object
        //   25	65	6	localIBinder	IBinder
        //   63	6	8	i	int
        //   66	20	9	bool	boolean
        // Exception table:
        //   from	to	target	type
        //   9	15	94	finally
        //   19	27	94	finally
        //   27	65	94	finally
      }
      
      /* Error */
      public final int[] zza(zzaq paramzzaq, int paramInt1, int paramInt2)
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
        //   17: aload_1
        //   18: ifnull +71 -> 89
        //   21: aload_1
        //   22: invokeinterface 37 1 0
        //   27: astore 7
        //   29: aload 4
        //   31: aload 7
        //   33: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   36: aload 4
        //   38: iload_2
        //   39: invokevirtual 57	android/os/Parcel:writeInt	(I)V
        //   42: aload 4
        //   44: iload_3
        //   45: invokevirtual 57	android/os/Parcel:writeInt	(I)V
        //   48: aload_0
        //   49: getfield 15	com/google/android/gms/car/zzap$zza$zza:zzop	Landroid/os/IBinder;
        //   52: iconst_5
        //   53: aload 4
        //   55: aload 5
        //   57: iconst_0
        //   58: invokeinterface 46 5 0
        //   63: pop
        //   64: aload 5
        //   66: invokevirtual 49	android/os/Parcel:readException	()V
        //   69: aload 5
        //   71: invokevirtual 67	android/os/Parcel:createIntArray	()[I
        //   74: astore 9
        //   76: aload 5
        //   78: invokevirtual 52	android/os/Parcel:recycle	()V
        //   81: aload 4
        //   83: invokevirtual 52	android/os/Parcel:recycle	()V
        //   86: aload 9
        //   88: areturn
        //   89: aconst_null
        //   90: astore 7
        //   92: goto -63 -> 29
        //   95: astore 6
        //   97: aload 5
        //   99: invokevirtual 52	android/os/Parcel:recycle	()V
        //   102: aload 4
        //   104: invokevirtual 52	android/os/Parcel:recycle	()V
        //   107: aload 6
        //   109: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	110	0	this	zza
        //   0	110	1	paramzzaq	zzaq
        //   0	110	2	paramInt1	int
        //   0	110	3	paramInt2	int
        //   3	100	4	localParcel1	Parcel
        //   8	90	5	localParcel2	Parcel
        //   95	13	6	localObject	Object
        //   27	64	7	localIBinder	IBinder
        //   74	13	9	arrayOfInt	int[]
        // Exception table:
        //   from	to	target	type
        //   10	17	95	finally
        //   21	29	95	finally
        //   29	76	95	finally
      }
      
      /* Error */
      public final void zzb(zzaq paramzzaq)
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
        //   15: ifnull +44 -> 59
        //   18: aload_1
        //   19: invokeinterface 37 1 0
        //   24: astore 5
        //   26: aload_2
        //   27: aload 5
        //   29: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   32: aload_0
        //   33: getfield 15	com/google/android/gms/car/zzap$zza$zza:zzop	Landroid/os/IBinder;
        //   36: iconst_2
        //   37: aload_2
        //   38: aload_3
        //   39: iconst_0
        //   40: invokeinterface 46 5 0
        //   45: pop
        //   46: aload_3
        //   47: invokevirtual 49	android/os/Parcel:readException	()V
        //   50: aload_3
        //   51: invokevirtual 52	android/os/Parcel:recycle	()V
        //   54: aload_2
        //   55: invokevirtual 52	android/os/Parcel:recycle	()V
        //   58: return
        //   59: aconst_null
        //   60: astore 5
        //   62: goto -36 -> 26
        //   65: astore 4
        //   67: aload_3
        //   68: invokevirtual 52	android/os/Parcel:recycle	()V
        //   71: aload_2
        //   72: invokevirtual 52	android/os/Parcel:recycle	()V
        //   75: aload 4
        //   77: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	78	0	this	zza
        //   0	78	1	paramzzaq	zzaq
        //   3	69	2	localParcel1	Parcel
        //   7	61	3	localParcel2	Parcel
        //   65	11	4	localObject	Object
        //   24	37	5	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   8	14	65	finally
        //   18	26	65	finally
        //   26	50	65	finally
      }
      
      /* Error */
      public final void zzb(zzaq paramzzaq, int paramInt)
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
        //   16: ifnull +53 -> 69
        //   19: aload_1
        //   20: invokeinterface 37 1 0
        //   25: astore 6
        //   27: aload_3
        //   28: aload 6
        //   30: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   33: aload_3
        //   34: iload_2
        //   35: invokevirtual 57	android/os/Parcel:writeInt	(I)V
        //   38: aload_0
        //   39: getfield 15	com/google/android/gms/car/zzap$zza$zza:zzop	Landroid/os/IBinder;
        //   42: bipush 6
        //   44: aload_3
        //   45: aload 4
        //   47: iconst_0
        //   48: invokeinterface 46 5 0
        //   53: pop
        //   54: aload 4
        //   56: invokevirtual 49	android/os/Parcel:readException	()V
        //   59: aload 4
        //   61: invokevirtual 52	android/os/Parcel:recycle	()V
        //   64: aload_3
        //   65: invokevirtual 52	android/os/Parcel:recycle	()V
        //   68: return
        //   69: aconst_null
        //   70: astore 6
        //   72: goto -45 -> 27
        //   75: astore 5
        //   77: aload 4
        //   79: invokevirtual 52	android/os/Parcel:recycle	()V
        //   82: aload_3
        //   83: invokevirtual 52	android/os/Parcel:recycle	()V
        //   86: aload 5
        //   88: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	89	0	this	zza
        //   0	89	1	paramzzaq	zzaq
        //   0	89	2	paramInt	int
        //   3	80	3	localParcel1	Parcel
        //   7	71	4	localParcel2	Parcel
        //   75	12	5	localObject	Object
        //   25	46	6	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   9	15	75	finally
        //   19	27	75	finally
        //   27	59	75	finally
      }
      
      /* Error */
      public final void zzc(zzaq paramzzaq)
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
        //   15: ifnull +45 -> 60
        //   18: aload_1
        //   19: invokeinterface 37 1 0
        //   24: astore 5
        //   26: aload_2
        //   27: aload 5
        //   29: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   32: aload_0
        //   33: getfield 15	com/google/android/gms/car/zzap$zza$zza:zzop	Landroid/os/IBinder;
        //   36: bipush 7
        //   38: aload_2
        //   39: aload_3
        //   40: iconst_0
        //   41: invokeinterface 46 5 0
        //   46: pop
        //   47: aload_3
        //   48: invokevirtual 49	android/os/Parcel:readException	()V
        //   51: aload_3
        //   52: invokevirtual 52	android/os/Parcel:recycle	()V
        //   55: aload_2
        //   56: invokevirtual 52	android/os/Parcel:recycle	()V
        //   59: return
        //   60: aconst_null
        //   61: astore 5
        //   63: goto -37 -> 26
        //   66: astore 4
        //   68: aload_3
        //   69: invokevirtual 52	android/os/Parcel:recycle	()V
        //   72: aload_2
        //   73: invokevirtual 52	android/os/Parcel:recycle	()V
        //   76: aload 4
        //   78: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	79	0	this	zza
        //   0	79	1	paramzzaq	zzaq
        //   3	70	2	localParcel1	Parcel
        //   7	62	3	localParcel2	Parcel
        //   66	11	4	localObject	Object
        //   24	38	5	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   8	14	66	finally
        //   18	26	66	finally
        //   26	51	66	finally
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzap
 * JD-Core Version:    0.7.0.1
 */