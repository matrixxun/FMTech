package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface zzvk
  extends IInterface
{
  public abstract void zza(zzvj paramzzvj)
    throws RemoteException;
  
  public abstract void zza(zzvj paramzzvj, String paramString1, int[] paramArrayOfInt, int paramInt, String paramString2)
    throws RemoteException;
  
  public abstract void zza(zzvj paramzzvj, byte[] paramArrayOfByte)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzvk
  {
    public static zzvk zzgs(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.safetynet.internal.ISafetyNetService");
      if ((localIInterface != null) && ((localIInterface instanceof zzvk))) {
        return (zzvk)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.safetynet.internal.ISafetyNetService");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.safetynet.internal.ISafetyNetService");
        zza(zzvj.zza.zzgr(paramParcel1.readStrongBinder()), paramParcel1.createByteArray());
        paramParcel2.writeNoException();
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.safetynet.internal.ISafetyNetService");
        zza(zzvj.zza.zzgr(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.safetynet.internal.ISafetyNetService");
      zza(zzvj.zza.zzgr(paramParcel1.readStrongBinder()), paramParcel1.readString(), paramParcel1.createIntArray(), paramParcel1.readInt(), paramParcel1.readString());
      paramParcel2.writeNoException();
      return true;
    }
    
    private static final class zza
      implements zzvk
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
      public final void zza(zzvj paramzzvj)
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
        //   33: getfield 15	com/google/android/gms/internal/zzvk$zza$zza:zzop	Landroid/os/IBinder;
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
        //   0	78	1	paramzzvj	zzvj
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
      public final void zza(zzvj paramzzvj, String paramString1, int[] paramArrayOfInt, int paramInt, String paramString2)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore 6
        //   5: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   8: astore 7
        //   10: aload 6
        //   12: ldc 29
        //   14: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   17: aload_1
        //   18: ifnull +76 -> 94
        //   21: aload_1
        //   22: invokeinterface 37 1 0
        //   27: astore 9
        //   29: aload 6
        //   31: aload 9
        //   33: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   36: aload 6
        //   38: aload_2
        //   39: invokevirtual 56	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   42: aload 6
        //   44: aload_3
        //   45: invokevirtual 60	android/os/Parcel:writeIntArray	([I)V
        //   48: aload 6
        //   50: iload 4
        //   52: invokevirtual 64	android/os/Parcel:writeInt	(I)V
        //   55: aload 6
        //   57: aload 5
        //   59: invokevirtual 56	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   62: aload_0
        //   63: getfield 15	com/google/android/gms/internal/zzvk$zza$zza:zzop	Landroid/os/IBinder;
        //   66: iconst_3
        //   67: aload 6
        //   69: aload 7
        //   71: iconst_0
        //   72: invokeinterface 46 5 0
        //   77: pop
        //   78: aload 7
        //   80: invokevirtual 49	android/os/Parcel:readException	()V
        //   83: aload 7
        //   85: invokevirtual 52	android/os/Parcel:recycle	()V
        //   88: aload 6
        //   90: invokevirtual 52	android/os/Parcel:recycle	()V
        //   93: return
        //   94: aconst_null
        //   95: astore 9
        //   97: goto -68 -> 29
        //   100: astore 8
        //   102: aload 7
        //   104: invokevirtual 52	android/os/Parcel:recycle	()V
        //   107: aload 6
        //   109: invokevirtual 52	android/os/Parcel:recycle	()V
        //   112: aload 8
        //   114: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	115	0	this	zza
        //   0	115	1	paramzzvj	zzvj
        //   0	115	2	paramString1	String
        //   0	115	3	paramArrayOfInt	int[]
        //   0	115	4	paramInt	int
        //   0	115	5	paramString2	String
        //   3	105	6	localParcel1	Parcel
        //   8	95	7	localParcel2	Parcel
        //   100	13	8	localObject	Object
        //   27	69	9	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   10	17	100	finally
        //   21	29	100	finally
        //   29	83	100	finally
      }
      
      /* Error */
      public final void zza(zzvj paramzzvj, byte[] paramArrayOfByte)
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
        //   16: ifnull +52 -> 68
        //   19: aload_1
        //   20: invokeinterface 37 1 0
        //   25: astore 6
        //   27: aload_3
        //   28: aload 6
        //   30: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   33: aload_3
        //   34: aload_2
        //   35: invokevirtual 69	android/os/Parcel:writeByteArray	([B)V
        //   38: aload_0
        //   39: getfield 15	com/google/android/gms/internal/zzvk$zza$zza:zzop	Landroid/os/IBinder;
        //   42: iconst_1
        //   43: aload_3
        //   44: aload 4
        //   46: iconst_0
        //   47: invokeinterface 46 5 0
        //   52: pop
        //   53: aload 4
        //   55: invokevirtual 49	android/os/Parcel:readException	()V
        //   58: aload 4
        //   60: invokevirtual 52	android/os/Parcel:recycle	()V
        //   63: aload_3
        //   64: invokevirtual 52	android/os/Parcel:recycle	()V
        //   67: return
        //   68: aconst_null
        //   69: astore 6
        //   71: goto -44 -> 27
        //   74: astore 5
        //   76: aload 4
        //   78: invokevirtual 52	android/os/Parcel:recycle	()V
        //   81: aload_3
        //   82: invokevirtual 52	android/os/Parcel:recycle	()V
        //   85: aload 5
        //   87: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	88	0	this	zza
        //   0	88	1	paramzzvj	zzvj
        //   0	88	2	paramArrayOfByte	byte[]
        //   3	79	3	localParcel1	Parcel
        //   7	70	4	localParcel2	Parcel
        //   74	12	5	localObject	Object
        //   25	45	6	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   9	15	74	finally
        //   19	27	74	finally
        //   27	58	74	finally
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzvk
 * JD-Core Version:    0.7.0.1
 */