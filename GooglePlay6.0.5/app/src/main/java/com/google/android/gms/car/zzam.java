package com.google.android.gms.car;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface zzam
  extends IInterface
{
  public abstract void onGetNode(String paramString, int paramInt, boolean paramBoolean)
    throws RemoteException;
  
  public abstract void zzg(String paramString, int paramInt)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzam
  {
    public static zzam zzbs(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.car.ICarMediaBrowserEventListener");
      if ((localIInterface != null) && ((localIInterface instanceof zzam))) {
        return (zzam)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.car.ICarMediaBrowserEventListener");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarMediaBrowserEventListener");
        String str = paramParcel1.readString();
        int i = paramParcel1.readInt();
        if (paramParcel1.readInt() != 0) {}
        for (boolean bool = true;; bool = false)
        {
          onGetNode(str, i, bool);
          return true;
        }
      }
      paramParcel1.enforceInterface("com.google.android.gms.car.ICarMediaBrowserEventListener");
      zzg(paramParcel1.readString(), paramParcel1.readInt());
      return true;
    }
    
    private static final class zza
      implements zzam
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
      public final void onGetNode(String paramString, int paramInt, boolean paramBoolean)
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
        //   17: aload_1
        //   18: invokevirtual 36	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   21: aload 5
        //   23: iload_2
        //   24: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   27: iload_3
        //   28: ifeq +31 -> 59
        //   31: aload 5
        //   33: iload 4
        //   35: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   38: aload_0
        //   39: getfield 15	com/google/android/gms/car/zzam$zza$zza:zzop	Landroid/os/IBinder;
        //   42: iconst_1
        //   43: aload 5
        //   45: aconst_null
        //   46: iconst_1
        //   47: invokeinterface 46 5 0
        //   52: pop
        //   53: aload 5
        //   55: invokevirtual 49	android/os/Parcel:recycle	()V
        //   58: return
        //   59: iconst_0
        //   60: istore 4
        //   62: goto -31 -> 31
        //   65: astore 6
        //   67: aload 5
        //   69: invokevirtual 49	android/os/Parcel:recycle	()V
        //   72: aload 6
        //   74: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	75	0	this	zza
        //   0	75	1	paramString	String
        //   0	75	2	paramInt	int
        //   0	75	3	paramBoolean	boolean
        //   1	60	4	i	int
        //   6	62	5	localParcel	Parcel
        //   65	8	6	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   8	27	65	finally
        //   31	53	65	finally
      }
      
      public final void zzg(String paramString, int paramInt)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarMediaBrowserEventListener");
          localParcel.writeString(paramString);
          localParcel.writeInt(paramInt);
          this.zzop.transact(2, localParcel, null, 1);
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
 * Qualified Name:     com.google.android.gms.car.zzam
 * JD-Core Version:    0.7.0.1
 */