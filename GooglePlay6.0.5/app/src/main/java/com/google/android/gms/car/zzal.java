package com.google.android.gms.car;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface zzal
  extends IInterface
{
  public abstract void zza(zzam paramzzam, CarMediaBrowserListNode paramCarMediaBrowserListNode)
    throws RemoteException;
  
  public abstract void zza(zzam paramzzam, CarMediaBrowserRootNode paramCarMediaBrowserRootNode)
    throws RemoteException;
  
  public abstract void zza(zzam paramzzam, CarMediaBrowserSongNode paramCarMediaBrowserSongNode)
    throws RemoteException;
  
  public abstract void zza(zzam paramzzam, CarMediaBrowserSourceNode paramCarMediaBrowserSourceNode)
    throws RemoteException;
  
  public abstract boolean zza(zzam paramzzam)
    throws RemoteException;
  
  public abstract boolean zzb(zzam paramzzam)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzal
  {
    public static zzal zzbr(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.car.ICarMediaBrowser");
      if ((localIInterface != null) && ((localIInterface instanceof zzal))) {
        return (zzal)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.car.ICarMediaBrowser");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarMediaBrowser");
        zzam localzzam4 = zzam.zza.zzbs(paramParcel1.readStrongBinder());
        int i1 = paramParcel1.readInt();
        CarMediaBrowserListNode localCarMediaBrowserListNode = null;
        if (i1 != 0) {
          localCarMediaBrowserListNode = (CarMediaBrowserListNode)CarMediaBrowserListNode.CREATOR.createFromParcel(paramParcel1);
        }
        zza(localzzam4, localCarMediaBrowserListNode);
        paramParcel2.writeNoException();
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarMediaBrowser");
        zzam localzzam3 = zzam.zza.zzbs(paramParcel1.readStrongBinder());
        int n = paramParcel1.readInt();
        CarMediaBrowserRootNode localCarMediaBrowserRootNode = null;
        if (n != 0) {
          localCarMediaBrowserRootNode = (CarMediaBrowserRootNode)CarMediaBrowserRootNode.CREATOR.createFromParcel(paramParcel1);
        }
        zza(localzzam3, localCarMediaBrowserRootNode);
        paramParcel2.writeNoException();
        return true;
      case 3: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarMediaBrowser");
        zzam localzzam2 = zzam.zza.zzbs(paramParcel1.readStrongBinder());
        int m = paramParcel1.readInt();
        CarMediaBrowserSongNode localCarMediaBrowserSongNode = null;
        if (m != 0) {
          localCarMediaBrowserSongNode = (CarMediaBrowserSongNode)CarMediaBrowserSongNode.CREATOR.createFromParcel(paramParcel1);
        }
        zza(localzzam2, localCarMediaBrowserSongNode);
        paramParcel2.writeNoException();
        return true;
      case 4: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarMediaBrowser");
        zzam localzzam1 = zzam.zza.zzbs(paramParcel1.readStrongBinder());
        int k = paramParcel1.readInt();
        CarMediaBrowserSourceNode localCarMediaBrowserSourceNode = null;
        if (k != 0) {
          localCarMediaBrowserSourceNode = (CarMediaBrowserSourceNode)CarMediaBrowserSourceNode.CREATOR.createFromParcel(paramParcel1);
        }
        zza(localzzam1, localCarMediaBrowserSourceNode);
        paramParcel2.writeNoException();
        return true;
      case 5: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarMediaBrowser");
        boolean bool2 = zza(zzam.zza.zzbs(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        if (bool2) {}
        for (int j = 1;; j = 0)
        {
          paramParcel2.writeInt(j);
          return true;
        }
      }
      paramParcel1.enforceInterface("com.google.android.gms.car.ICarMediaBrowser");
      boolean bool1 = zzb(zzam.zza.zzbs(paramParcel1.readStrongBinder()));
      paramParcel2.writeNoException();
      int i = 0;
      if (bool1) {
        i = 1;
      }
      paramParcel2.writeInt(i);
      return true;
    }
    
    private static final class zza
      implements zzal
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
      public final void zza(zzam paramzzam, CarMediaBrowserListNode paramCarMediaBrowserListNode)
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
        //   16: ifnull +62 -> 78
        //   19: aload_1
        //   20: invokeinterface 37 1 0
        //   25: astore 6
        //   27: aload_3
        //   28: aload 6
        //   30: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   33: aload_2
        //   34: ifnull +50 -> 84
        //   37: aload_3
        //   38: iconst_1
        //   39: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   42: aload_2
        //   43: aload_3
        //   44: iconst_0
        //   45: invokevirtual 50	com/google/android/gms/car/CarMediaBrowserListNode:writeToParcel	(Landroid/os/Parcel;I)V
        //   48: aload_0
        //   49: getfield 15	com/google/android/gms/car/zzal$zza$zza:zzop	Landroid/os/IBinder;
        //   52: iconst_1
        //   53: aload_3
        //   54: aload 4
        //   56: iconst_0
        //   57: invokeinterface 56 5 0
        //   62: pop
        //   63: aload 4
        //   65: invokevirtual 59	android/os/Parcel:readException	()V
        //   68: aload 4
        //   70: invokevirtual 62	android/os/Parcel:recycle	()V
        //   73: aload_3
        //   74: invokevirtual 62	android/os/Parcel:recycle	()V
        //   77: return
        //   78: aconst_null
        //   79: astore 6
        //   81: goto -54 -> 27
        //   84: aload_3
        //   85: iconst_0
        //   86: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   89: goto -41 -> 48
        //   92: astore 5
        //   94: aload 4
        //   96: invokevirtual 62	android/os/Parcel:recycle	()V
        //   99: aload_3
        //   100: invokevirtual 62	android/os/Parcel:recycle	()V
        //   103: aload 5
        //   105: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	106	0	this	zza
        //   0	106	1	paramzzam	zzam
        //   0	106	2	paramCarMediaBrowserListNode	CarMediaBrowserListNode
        //   3	97	3	localParcel1	Parcel
        //   7	88	4	localParcel2	Parcel
        //   92	12	5	localObject	Object
        //   25	55	6	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   9	15	92	finally
        //   19	27	92	finally
        //   27	33	92	finally
        //   37	48	92	finally
        //   48	68	92	finally
        //   84	89	92	finally
      }
      
      /* Error */
      public final void zza(zzam paramzzam, CarMediaBrowserRootNode paramCarMediaBrowserRootNode)
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
        //   16: ifnull +62 -> 78
        //   19: aload_1
        //   20: invokeinterface 37 1 0
        //   25: astore 6
        //   27: aload_3
        //   28: aload 6
        //   30: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   33: aload_2
        //   34: ifnull +50 -> 84
        //   37: aload_3
        //   38: iconst_1
        //   39: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   42: aload_2
        //   43: aload_3
        //   44: iconst_0
        //   45: invokevirtual 66	com/google/android/gms/car/CarMediaBrowserRootNode:writeToParcel	(Landroid/os/Parcel;I)V
        //   48: aload_0
        //   49: getfield 15	com/google/android/gms/car/zzal$zza$zza:zzop	Landroid/os/IBinder;
        //   52: iconst_2
        //   53: aload_3
        //   54: aload 4
        //   56: iconst_0
        //   57: invokeinterface 56 5 0
        //   62: pop
        //   63: aload 4
        //   65: invokevirtual 59	android/os/Parcel:readException	()V
        //   68: aload 4
        //   70: invokevirtual 62	android/os/Parcel:recycle	()V
        //   73: aload_3
        //   74: invokevirtual 62	android/os/Parcel:recycle	()V
        //   77: return
        //   78: aconst_null
        //   79: astore 6
        //   81: goto -54 -> 27
        //   84: aload_3
        //   85: iconst_0
        //   86: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   89: goto -41 -> 48
        //   92: astore 5
        //   94: aload 4
        //   96: invokevirtual 62	android/os/Parcel:recycle	()V
        //   99: aload_3
        //   100: invokevirtual 62	android/os/Parcel:recycle	()V
        //   103: aload 5
        //   105: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	106	0	this	zza
        //   0	106	1	paramzzam	zzam
        //   0	106	2	paramCarMediaBrowserRootNode	CarMediaBrowserRootNode
        //   3	97	3	localParcel1	Parcel
        //   7	88	4	localParcel2	Parcel
        //   92	12	5	localObject	Object
        //   25	55	6	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   9	15	92	finally
        //   19	27	92	finally
        //   27	33	92	finally
        //   37	48	92	finally
        //   48	68	92	finally
        //   84	89	92	finally
      }
      
      /* Error */
      public final void zza(zzam paramzzam, CarMediaBrowserSongNode paramCarMediaBrowserSongNode)
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
        //   16: ifnull +62 -> 78
        //   19: aload_1
        //   20: invokeinterface 37 1 0
        //   25: astore 6
        //   27: aload_3
        //   28: aload 6
        //   30: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   33: aload_2
        //   34: ifnull +50 -> 84
        //   37: aload_3
        //   38: iconst_1
        //   39: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   42: aload_2
        //   43: aload_3
        //   44: iconst_0
        //   45: invokevirtual 70	com/google/android/gms/car/CarMediaBrowserSongNode:writeToParcel	(Landroid/os/Parcel;I)V
        //   48: aload_0
        //   49: getfield 15	com/google/android/gms/car/zzal$zza$zza:zzop	Landroid/os/IBinder;
        //   52: iconst_3
        //   53: aload_3
        //   54: aload 4
        //   56: iconst_0
        //   57: invokeinterface 56 5 0
        //   62: pop
        //   63: aload 4
        //   65: invokevirtual 59	android/os/Parcel:readException	()V
        //   68: aload 4
        //   70: invokevirtual 62	android/os/Parcel:recycle	()V
        //   73: aload_3
        //   74: invokevirtual 62	android/os/Parcel:recycle	()V
        //   77: return
        //   78: aconst_null
        //   79: astore 6
        //   81: goto -54 -> 27
        //   84: aload_3
        //   85: iconst_0
        //   86: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   89: goto -41 -> 48
        //   92: astore 5
        //   94: aload 4
        //   96: invokevirtual 62	android/os/Parcel:recycle	()V
        //   99: aload_3
        //   100: invokevirtual 62	android/os/Parcel:recycle	()V
        //   103: aload 5
        //   105: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	106	0	this	zza
        //   0	106	1	paramzzam	zzam
        //   0	106	2	paramCarMediaBrowserSongNode	CarMediaBrowserSongNode
        //   3	97	3	localParcel1	Parcel
        //   7	88	4	localParcel2	Parcel
        //   92	12	5	localObject	Object
        //   25	55	6	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   9	15	92	finally
        //   19	27	92	finally
        //   27	33	92	finally
        //   37	48	92	finally
        //   48	68	92	finally
        //   84	89	92	finally
      }
      
      /* Error */
      public final void zza(zzam paramzzam, CarMediaBrowserSourceNode paramCarMediaBrowserSourceNode)
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
        //   16: ifnull +62 -> 78
        //   19: aload_1
        //   20: invokeinterface 37 1 0
        //   25: astore 6
        //   27: aload_3
        //   28: aload 6
        //   30: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   33: aload_2
        //   34: ifnull +50 -> 84
        //   37: aload_3
        //   38: iconst_1
        //   39: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   42: aload_2
        //   43: aload_3
        //   44: iconst_0
        //   45: invokevirtual 74	com/google/android/gms/car/CarMediaBrowserSourceNode:writeToParcel	(Landroid/os/Parcel;I)V
        //   48: aload_0
        //   49: getfield 15	com/google/android/gms/car/zzal$zza$zza:zzop	Landroid/os/IBinder;
        //   52: iconst_4
        //   53: aload_3
        //   54: aload 4
        //   56: iconst_0
        //   57: invokeinterface 56 5 0
        //   62: pop
        //   63: aload 4
        //   65: invokevirtual 59	android/os/Parcel:readException	()V
        //   68: aload 4
        //   70: invokevirtual 62	android/os/Parcel:recycle	()V
        //   73: aload_3
        //   74: invokevirtual 62	android/os/Parcel:recycle	()V
        //   77: return
        //   78: aconst_null
        //   79: astore 6
        //   81: goto -54 -> 27
        //   84: aload_3
        //   85: iconst_0
        //   86: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   89: goto -41 -> 48
        //   92: astore 5
        //   94: aload 4
        //   96: invokevirtual 62	android/os/Parcel:recycle	()V
        //   99: aload_3
        //   100: invokevirtual 62	android/os/Parcel:recycle	()V
        //   103: aload 5
        //   105: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	106	0	this	zza
        //   0	106	1	paramzzam	zzam
        //   0	106	2	paramCarMediaBrowserSourceNode	CarMediaBrowserSourceNode
        //   3	97	3	localParcel1	Parcel
        //   7	88	4	localParcel2	Parcel
        //   92	12	5	localObject	Object
        //   25	55	6	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   9	15	92	finally
        //   19	27	92	finally
        //   27	33	92	finally
        //   37	48	92	finally
        //   48	68	92	finally
        //   84	89	92	finally
      }
      
      /* Error */
      public final boolean zza(zzam paramzzam)
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
        //   19: invokeinterface 37 1 0
        //   24: astore 5
        //   26: aload_2
        //   27: aload 5
        //   29: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   32: aload_0
        //   33: getfield 15	com/google/android/gms/car/zzal$zza$zza:zzop	Landroid/os/IBinder;
        //   36: iconst_5
        //   37: aload_2
        //   38: aload_3
        //   39: iconst_0
        //   40: invokeinterface 56 5 0
        //   45: pop
        //   46: aload_3
        //   47: invokevirtual 59	android/os/Parcel:readException	()V
        //   50: aload_3
        //   51: invokevirtual 79	android/os/Parcel:readInt	()I
        //   54: istore 7
        //   56: iconst_0
        //   57: istore 8
        //   59: iload 7
        //   61: ifeq +6 -> 67
        //   64: iconst_1
        //   65: istore 8
        //   67: aload_3
        //   68: invokevirtual 62	android/os/Parcel:recycle	()V
        //   71: aload_2
        //   72: invokevirtual 62	android/os/Parcel:recycle	()V
        //   75: iload 8
        //   77: ireturn
        //   78: aconst_null
        //   79: astore 5
        //   81: goto -55 -> 26
        //   84: astore 4
        //   86: aload_3
        //   87: invokevirtual 62	android/os/Parcel:recycle	()V
        //   90: aload_2
        //   91: invokevirtual 62	android/os/Parcel:recycle	()V
        //   94: aload 4
        //   96: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	97	0	this	zza
        //   0	97	1	paramzzam	zzam
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
      
      /* Error */
      public final boolean zzb(zzam paramzzam)
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
        //   19: invokeinterface 37 1 0
        //   24: astore 5
        //   26: aload_2
        //   27: aload 5
        //   29: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   32: aload_0
        //   33: getfield 15	com/google/android/gms/car/zzal$zza$zza:zzop	Landroid/os/IBinder;
        //   36: bipush 6
        //   38: aload_2
        //   39: aload_3
        //   40: iconst_0
        //   41: invokeinterface 56 5 0
        //   46: pop
        //   47: aload_3
        //   48: invokevirtual 59	android/os/Parcel:readException	()V
        //   51: aload_3
        //   52: invokevirtual 79	android/os/Parcel:readInt	()I
        //   55: istore 7
        //   57: iconst_0
        //   58: istore 8
        //   60: iload 7
        //   62: ifeq +6 -> 68
        //   65: iconst_1
        //   66: istore 8
        //   68: aload_3
        //   69: invokevirtual 62	android/os/Parcel:recycle	()V
        //   72: aload_2
        //   73: invokevirtual 62	android/os/Parcel:recycle	()V
        //   76: iload 8
        //   78: ireturn
        //   79: aconst_null
        //   80: astore 5
        //   82: goto -56 -> 26
        //   85: astore 4
        //   87: aload_3
        //   88: invokevirtual 62	android/os/Parcel:recycle	()V
        //   91: aload_2
        //   92: invokevirtual 62	android/os/Parcel:recycle	()V
        //   95: aload 4
        //   97: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	98	0	this	zza
        //   0	98	1	paramzzam	zzam
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
 * Qualified Name:     com.google.android.gms.car.zzal
 * JD-Core Version:    0.7.0.1
 */