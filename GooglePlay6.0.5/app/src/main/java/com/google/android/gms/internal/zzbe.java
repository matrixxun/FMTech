package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface zzbe
  extends IInterface
{
  public abstract String getId()
    throws RemoteException;
  
  public abstract void zzc(String paramString, boolean paramBoolean)
    throws RemoteException;
  
  public abstract boolean zzc(boolean paramBoolean)
    throws RemoteException;
  
  public abstract String zzn(String paramString)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzbe
  {
    public static zzbe zzg(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
      if ((localIInterface != null) && ((localIInterface instanceof zzbe))) {
        return (zzbe)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
        String str3 = getId();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str3);
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
        if (paramParcel1.readInt() != 0) {}
        for (boolean bool2 = true;; bool2 = false)
        {
          boolean bool3 = zzc(bool2);
          paramParcel2.writeNoException();
          int j = 0;
          if (bool3) {
            j = 1;
          }
          paramParcel2.writeInt(j);
          return true;
        }
      case 3: 
        paramParcel1.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
        String str2 = zzn(paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str2);
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
      String str1 = paramParcel1.readString();
      int i = paramParcel1.readInt();
      boolean bool1 = false;
      if (i != 0) {
        bool1 = true;
      }
      zzc(str1, bool1);
      paramParcel2.writeNoException();
      return true;
    }
    
    private static final class zza
      implements zzbe
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
      
      public final String getId()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
          this.zzop.transact(1, localParcel1, localParcel2, 0);
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
      
      public final void zzc(String paramString, boolean paramBoolean)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
          localParcel1.writeString(paramString);
          int i = 0;
          if (paramBoolean) {
            i = 1;
          }
          localParcel1.writeInt(i);
          this.zzop.transact(4, localParcel1, localParcel2, 0);
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
      public final boolean zzc(boolean paramBoolean)
        throws RemoteException
      {
        // Byte code:
        //   0: iconst_1
        //   1: istore_2
        //   2: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   5: astore_3
        //   6: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   9: astore 4
        //   11: aload_3
        //   12: ldc 29
        //   14: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   17: iload_1
        //   18: ifeq +55 -> 73
        //   21: iload_2
        //   22: istore 6
        //   24: aload_3
        //   25: iload 6
        //   27: invokevirtual 57	android/os/Parcel:writeInt	(I)V
        //   30: aload_0
        //   31: getfield 15	com/google/android/gms/internal/zzbe$zza$zza:zzop	Landroid/os/IBinder;
        //   34: iconst_2
        //   35: aload_3
        //   36: aload 4
        //   38: iconst_0
        //   39: invokeinterface 39 5 0
        //   44: pop
        //   45: aload 4
        //   47: invokevirtual 42	android/os/Parcel:readException	()V
        //   50: aload 4
        //   52: invokevirtual 62	android/os/Parcel:readInt	()I
        //   55: istore 8
        //   57: iload 8
        //   59: ifeq +20 -> 79
        //   62: aload 4
        //   64: invokevirtual 48	android/os/Parcel:recycle	()V
        //   67: aload_3
        //   68: invokevirtual 48	android/os/Parcel:recycle	()V
        //   71: iload_2
        //   72: ireturn
        //   73: iconst_0
        //   74: istore 6
        //   76: goto -52 -> 24
        //   79: iconst_0
        //   80: istore_2
        //   81: goto -19 -> 62
        //   84: astore 5
        //   86: aload 4
        //   88: invokevirtual 48	android/os/Parcel:recycle	()V
        //   91: aload_3
        //   92: invokevirtual 48	android/os/Parcel:recycle	()V
        //   95: aload 5
        //   97: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	98	0	this	zza
        //   0	98	1	paramBoolean	boolean
        //   1	80	2	i	int
        //   5	87	3	localParcel1	Parcel
        //   9	78	4	localParcel2	Parcel
        //   84	12	5	localObject	Object
        //   22	4	6	j	int
        //   74	1	6	k	int
        //   55	3	8	m	int
        // Exception table:
        //   from	to	target	type
        //   11	17	84	finally
        //   24	57	84	finally
      }
      
      public final String zzn(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
          localParcel1.writeString(paramString);
          this.zzop.transact(3, localParcel1, localParcel2, 0);
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
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzbe
 * JD-Core Version:    0.7.0.1
 */