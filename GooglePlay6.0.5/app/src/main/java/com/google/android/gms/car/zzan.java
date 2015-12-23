package com.google.android.gms.car;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface zzan
  extends IInterface
{
  public abstract void zza(zzao paramzzao, int paramInt1, String paramString, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws RemoteException;
  
  public abstract void zza(zzao paramzzao, String paramString1, String paramString2, String paramString3, byte[] paramArrayOfByte, String paramString4, int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract boolean zza(zzao paramzzao)
    throws RemoteException;
  
  public abstract boolean zzb(zzao paramzzao)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzan
  {
    public static zzan zzbt(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.car.ICarMediaPlaybackStatus");
      if ((localIInterface != null) && ((localIInterface instanceof zzan))) {
        return (zzan)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.car.ICarMediaPlaybackStatus");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarMediaPlaybackStatus");
        zzao localzzao = zzao.zza.zzbu(paramParcel1.readStrongBinder());
        int k = paramParcel1.readInt();
        String str = paramParcel1.readString();
        int m = paramParcel1.readInt();
        boolean bool3;
        boolean bool4;
        if (paramParcel1.readInt() != 0)
        {
          bool3 = true;
          if (paramParcel1.readInt() == 0) {
            break label163;
          }
          bool4 = true;
          if (paramParcel1.readInt() == 0) {
            break label169;
          }
        }
        for (boolean bool5 = true;; bool5 = false)
        {
          zza(localzzao, k, str, m, bool3, bool4, bool5);
          paramParcel2.writeNoException();
          return true;
          bool3 = false;
          break;
          bool4 = false;
          break label123;
        }
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarMediaPlaybackStatus");
        zza(zzao.zza.zzbu(paramParcel1.readStrongBinder()), paramParcel1.readString(), paramParcel1.readString(), paramParcel1.readString(), paramParcel1.createByteArray(), paramParcel1.readString(), paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      case 3: 
        label123:
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarMediaPlaybackStatus");
        label163:
        label169:
        boolean bool2 = zza(zzao.zza.zzbu(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        int j = 0;
        if (bool2) {
          j = 1;
        }
        paramParcel2.writeInt(j);
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.car.ICarMediaPlaybackStatus");
      boolean bool1 = zzb(zzao.zza.zzbu(paramParcel1.readStrongBinder()));
      paramParcel2.writeNoException();
      int i = 0;
      if (bool1) {
        i = 1;
      }
      paramParcel2.writeInt(i);
      return true;
    }
    
    private static final class zza
      implements zzan
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
      public final void zza(zzao paramzzao, int paramInt1, String paramString, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
        throws RemoteException
      {
        // Byte code:
        //   0: iconst_1
        //   1: istore 8
        //   3: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   6: astore 9
        //   8: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   11: astore 10
        //   13: aload 9
        //   15: ldc 29
        //   17: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   20: aload_1
        //   21: ifnull +113 -> 134
        //   24: aload_1
        //   25: invokeinterface 37 1 0
        //   30: astore 12
        //   32: aload 9
        //   34: aload 12
        //   36: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   39: aload 9
        //   41: iload_2
        //   42: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   45: aload 9
        //   47: aload_3
        //   48: invokevirtual 47	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   51: aload 9
        //   53: iload 4
        //   55: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   58: iload 5
        //   60: ifeq +80 -> 140
        //   63: iload 8
        //   65: istore 13
        //   67: aload 9
        //   69: iload 13
        //   71: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   74: iload 6
        //   76: ifeq +70 -> 146
        //   79: iload 8
        //   81: istore 14
        //   83: aload 9
        //   85: iload 14
        //   87: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   90: iload 7
        //   92: ifeq +60 -> 152
        //   95: aload 9
        //   97: iload 8
        //   99: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   102: aload_0
        //   103: getfield 15	com/google/android/gms/car/zzan$zza$zza:zzop	Landroid/os/IBinder;
        //   106: iconst_1
        //   107: aload 9
        //   109: aload 10
        //   111: iconst_0
        //   112: invokeinterface 53 5 0
        //   117: pop
        //   118: aload 10
        //   120: invokevirtual 56	android/os/Parcel:readException	()V
        //   123: aload 10
        //   125: invokevirtual 59	android/os/Parcel:recycle	()V
        //   128: aload 9
        //   130: invokevirtual 59	android/os/Parcel:recycle	()V
        //   133: return
        //   134: aconst_null
        //   135: astore 12
        //   137: goto -105 -> 32
        //   140: iconst_0
        //   141: istore 13
        //   143: goto -76 -> 67
        //   146: iconst_0
        //   147: istore 14
        //   149: goto -66 -> 83
        //   152: iconst_0
        //   153: istore 8
        //   155: goto -60 -> 95
        //   158: astore 11
        //   160: aload 10
        //   162: invokevirtual 59	android/os/Parcel:recycle	()V
        //   165: aload 9
        //   167: invokevirtual 59	android/os/Parcel:recycle	()V
        //   170: aload 11
        //   172: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	173	0	this	zza
        //   0	173	1	paramzzao	zzao
        //   0	173	2	paramInt1	int
        //   0	173	3	paramString	String
        //   0	173	4	paramInt2	int
        //   0	173	5	paramBoolean1	boolean
        //   0	173	6	paramBoolean2	boolean
        //   0	173	7	paramBoolean3	boolean
        //   1	153	8	i	int
        //   6	160	9	localParcel1	Parcel
        //   11	150	10	localParcel2	Parcel
        //   158	13	11	localObject	Object
        //   30	106	12	localIBinder	IBinder
        //   65	77	13	j	int
        //   81	67	14	k	int
        // Exception table:
        //   from	to	target	type
        //   13	20	158	finally
        //   24	32	158	finally
        //   32	58	158	finally
        //   67	74	158	finally
        //   83	90	158	finally
        //   95	123	158	finally
      }
      
      /* Error */
      public final void zza(zzao paramzzao, String paramString1, String paramString2, String paramString3, byte[] paramArrayOfByte, String paramString4, int paramInt1, int paramInt2)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore 9
        //   5: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   8: astore 10
        //   10: aload 9
        //   12: ldc 29
        //   14: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   17: aload_1
        //   18: ifnull +97 -> 115
        //   21: aload_1
        //   22: invokeinterface 37 1 0
        //   27: astore 12
        //   29: aload 9
        //   31: aload 12
        //   33: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   36: aload 9
        //   38: aload_2
        //   39: invokevirtual 47	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   42: aload 9
        //   44: aload_3
        //   45: invokevirtual 47	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   48: aload 9
        //   50: aload 4
        //   52: invokevirtual 47	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   55: aload 9
        //   57: aload 5
        //   59: invokevirtual 64	android/os/Parcel:writeByteArray	([B)V
        //   62: aload 9
        //   64: aload 6
        //   66: invokevirtual 47	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   69: aload 9
        //   71: iload 7
        //   73: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   76: aload 9
        //   78: iload 8
        //   80: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   83: aload_0
        //   84: getfield 15	com/google/android/gms/car/zzan$zza$zza:zzop	Landroid/os/IBinder;
        //   87: iconst_2
        //   88: aload 9
        //   90: aload 10
        //   92: iconst_0
        //   93: invokeinterface 53 5 0
        //   98: pop
        //   99: aload 10
        //   101: invokevirtual 56	android/os/Parcel:readException	()V
        //   104: aload 10
        //   106: invokevirtual 59	android/os/Parcel:recycle	()V
        //   109: aload 9
        //   111: invokevirtual 59	android/os/Parcel:recycle	()V
        //   114: return
        //   115: aconst_null
        //   116: astore 12
        //   118: goto -89 -> 29
        //   121: astore 11
        //   123: aload 10
        //   125: invokevirtual 59	android/os/Parcel:recycle	()V
        //   128: aload 9
        //   130: invokevirtual 59	android/os/Parcel:recycle	()V
        //   133: aload 11
        //   135: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	136	0	this	zza
        //   0	136	1	paramzzao	zzao
        //   0	136	2	paramString1	String
        //   0	136	3	paramString2	String
        //   0	136	4	paramString3	String
        //   0	136	5	paramArrayOfByte	byte[]
        //   0	136	6	paramString4	String
        //   0	136	7	paramInt1	int
        //   0	136	8	paramInt2	int
        //   3	126	9	localParcel1	Parcel
        //   8	116	10	localParcel2	Parcel
        //   121	13	11	localObject	Object
        //   27	90	12	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   10	17	121	finally
        //   21	29	121	finally
        //   29	104	121	finally
      }
      
      /* Error */
      public final boolean zza(zzao paramzzao)
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
        //   33: getfield 15	com/google/android/gms/car/zzan$zza$zza:zzop	Landroid/os/IBinder;
        //   36: iconst_3
        //   37: aload_2
        //   38: aload_3
        //   39: iconst_0
        //   40: invokeinterface 53 5 0
        //   45: pop
        //   46: aload_3
        //   47: invokevirtual 56	android/os/Parcel:readException	()V
        //   50: aload_3
        //   51: invokevirtual 69	android/os/Parcel:readInt	()I
        //   54: istore 7
        //   56: iconst_0
        //   57: istore 8
        //   59: iload 7
        //   61: ifeq +6 -> 67
        //   64: iconst_1
        //   65: istore 8
        //   67: aload_3
        //   68: invokevirtual 59	android/os/Parcel:recycle	()V
        //   71: aload_2
        //   72: invokevirtual 59	android/os/Parcel:recycle	()V
        //   75: iload 8
        //   77: ireturn
        //   78: aconst_null
        //   79: astore 5
        //   81: goto -55 -> 26
        //   84: astore 4
        //   86: aload_3
        //   87: invokevirtual 59	android/os/Parcel:recycle	()V
        //   90: aload_2
        //   91: invokevirtual 59	android/os/Parcel:recycle	()V
        //   94: aload 4
        //   96: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	97	0	this	zza
        //   0	97	1	paramzzao	zzao
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
      public final boolean zzb(zzao paramzzao)
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
        //   33: getfield 15	com/google/android/gms/car/zzan$zza$zza:zzop	Landroid/os/IBinder;
        //   36: iconst_4
        //   37: aload_2
        //   38: aload_3
        //   39: iconst_0
        //   40: invokeinterface 53 5 0
        //   45: pop
        //   46: aload_3
        //   47: invokevirtual 56	android/os/Parcel:readException	()V
        //   50: aload_3
        //   51: invokevirtual 69	android/os/Parcel:readInt	()I
        //   54: istore 7
        //   56: iconst_0
        //   57: istore 8
        //   59: iload 7
        //   61: ifeq +6 -> 67
        //   64: iconst_1
        //   65: istore 8
        //   67: aload_3
        //   68: invokevirtual 59	android/os/Parcel:recycle	()V
        //   71: aload_2
        //   72: invokevirtual 59	android/os/Parcel:recycle	()V
        //   75: iload 8
        //   77: ireturn
        //   78: aconst_null
        //   79: astore 5
        //   81: goto -55 -> 26
        //   84: astore 4
        //   86: aload_3
        //   87: invokevirtual 59	android/os/Parcel:recycle	()V
        //   90: aload_2
        //   91: invokevirtual 59	android/os/Parcel:recycle	()V
        //   94: aload 4
        //   96: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	97	0	this	zza
        //   0	97	1	paramzzao	zzao
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
 * Qualified Name:     com.google.android.gms.car.zzan
 * JD-Core Version:    0.7.0.1
 */