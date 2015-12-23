package com.google.android.finsky.installer;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IMultiUserCoordinatorService
  extends IInterface
{
  public abstract boolean acquirePackage(String paramString)
    throws RemoteException;
  
  public abstract void registerListener(IMultiUserCoordinatorServiceListener paramIMultiUserCoordinatorServiceListener)
    throws RemoteException;
  
  public abstract void releaseAllPackages()
    throws RemoteException;
  
  public abstract void releasePackage(String paramString)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IMultiUserCoordinatorService
  {
    public Stub()
    {
      attachInterface(this, "com.google.android.finsky.installer.IMultiUserCoordinatorService");
    }
    
    public static IMultiUserCoordinatorService asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.finsky.installer.IMultiUserCoordinatorService");
      if ((localIInterface != null) && ((localIInterface instanceof IMultiUserCoordinatorService))) {
        return (IMultiUserCoordinatorService)localIInterface;
      }
      return new Proxy(paramIBinder);
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
        paramParcel2.writeString("com.google.android.finsky.installer.IMultiUserCoordinatorService");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.finsky.installer.IMultiUserCoordinatorService");
        IBinder localIBinder = paramParcel1.readStrongBinder();
        Object localObject;
        if (localIBinder == null) {
          localObject = null;
        }
        for (;;)
        {
          registerListener((IMultiUserCoordinatorServiceListener)localObject);
          paramParcel2.writeNoException();
          return true;
          IInterface localIInterface = localIBinder.queryLocalInterface("com.google.android.finsky.installer.IMultiUserCoordinatorServiceListener");
          if ((localIInterface != null) && ((localIInterface instanceof IMultiUserCoordinatorServiceListener))) {
            localObject = (IMultiUserCoordinatorServiceListener)localIInterface;
          } else {
            localObject = new IMultiUserCoordinatorServiceListener.Stub.Proxy(localIBinder);
          }
        }
      case 2: 
        paramParcel1.enforceInterface("com.google.android.finsky.installer.IMultiUserCoordinatorService");
        boolean bool = acquirePackage(paramParcel1.readString());
        paramParcel2.writeNoException();
        if (bool) {}
        for (int i = 1;; i = 0)
        {
          paramParcel2.writeInt(i);
          return true;
        }
      case 3: 
        paramParcel1.enforceInterface("com.google.android.finsky.installer.IMultiUserCoordinatorService");
        releasePackage(paramParcel1.readString());
        paramParcel2.writeNoException();
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.finsky.installer.IMultiUserCoordinatorService");
      releaseAllPackages();
      paramParcel2.writeNoException();
      return true;
    }
    
    private static final class Proxy
      implements IMultiUserCoordinatorService
    {
      private IBinder mRemote;
      
      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }
      
      public final boolean acquirePackage(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.finsky.installer.IMultiUserCoordinatorService");
          localParcel1.writeString(paramString);
          this.mRemote.transact(2, localParcel1, localParcel2, 0);
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
      
      public final IBinder asBinder()
      {
        return this.mRemote;
      }
      
      /* Error */
      public final void registerListener(IMultiUserCoordinatorServiceListener paramIMultiUserCoordinatorServiceListener)
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
        //   15: ifnull +44 -> 59
        //   18: aload_1
        //   19: invokeinterface 58 1 0
        //   24: astore 5
        //   26: aload_2
        //   27: aload 5
        //   29: invokevirtual 61	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   32: aload_0
        //   33: getfield 15	com/google/android/finsky/installer/IMultiUserCoordinatorService$Stub$Proxy:mRemote	Landroid/os/IBinder;
        //   36: iconst_1
        //   37: aload_2
        //   38: aload_3
        //   39: iconst_0
        //   40: invokeinterface 40 5 0
        //   45: pop
        //   46: aload_3
        //   47: invokevirtual 43	android/os/Parcel:readException	()V
        //   50: aload_3
        //   51: invokevirtual 50	android/os/Parcel:recycle	()V
        //   54: aload_2
        //   55: invokevirtual 50	android/os/Parcel:recycle	()V
        //   58: return
        //   59: aconst_null
        //   60: astore 5
        //   62: goto -36 -> 26
        //   65: astore 4
        //   67: aload_3
        //   68: invokevirtual 50	android/os/Parcel:recycle	()V
        //   71: aload_2
        //   72: invokevirtual 50	android/os/Parcel:recycle	()V
        //   75: aload 4
        //   77: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	78	0	this	Proxy
        //   0	78	1	paramIMultiUserCoordinatorServiceListener	IMultiUserCoordinatorServiceListener
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
      
      public final void releaseAllPackages()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.finsky.installer.IMultiUserCoordinatorService");
          this.mRemote.transact(4, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public final void releasePackage(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.finsky.installer.IMultiUserCoordinatorService");
          localParcel1.writeString(paramString);
          this.mRemote.transact(3, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
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
 * Qualified Name:     com.google.android.finsky.installer.IMultiUserCoordinatorService
 * JD-Core Version:    0.7.0.1
 */