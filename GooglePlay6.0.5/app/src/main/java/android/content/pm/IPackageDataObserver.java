package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IPackageDataObserver
  extends IInterface
{
  public abstract void onRemoveCompleted(String paramString, boolean paramBoolean)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IPackageDataObserver
  {
    public Stub()
    {
      attachInterface(this, "android.content.pm.IPackageDataObserver");
    }
    
    public static IPackageDataObserver asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("android.content.pm.IPackageDataObserver");
      if ((localIInterface != null) && ((localIInterface instanceof IPackageDataObserver))) {
        return (IPackageDataObserver)localIInterface;
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
        paramParcel2.writeString("android.content.pm.IPackageDataObserver");
        return true;
      }
      paramParcel1.enforceInterface("android.content.pm.IPackageDataObserver");
      String str = paramParcel1.readString();
      if (paramParcel1.readInt() != 0) {}
      for (boolean bool = true;; bool = false)
      {
        onRemoveCompleted(str, bool);
        return true;
      }
    }
    
    private static final class Proxy
      implements IPackageDataObserver
    {
      private IBinder mRemote;
      
      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }
      
      public final IBinder asBinder()
      {
        return this.mRemote;
      }
      
      /* Error */
      public final void onRemoveCompleted(String paramString, boolean paramBoolean)
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
        //   16: aload_1
        //   17: invokevirtual 36	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   20: iload_2
        //   21: ifeq +30 -> 51
        //   24: aload 4
        //   26: iload_3
        //   27: invokevirtual 40	android/os/Parcel:writeInt	(I)V
        //   30: aload_0
        //   31: getfield 15	android/content/pm/IPackageDataObserver$Stub$Proxy:mRemote	Landroid/os/IBinder;
        //   34: iconst_1
        //   35: aload 4
        //   37: aconst_null
        //   38: iconst_1
        //   39: invokeinterface 46 5 0
        //   44: pop
        //   45: aload 4
        //   47: invokevirtual 49	android/os/Parcel:recycle	()V
        //   50: return
        //   51: iconst_0
        //   52: istore_3
        //   53: goto -29 -> 24
        //   56: astore 5
        //   58: aload 4
        //   60: invokevirtual 49	android/os/Parcel:recycle	()V
        //   63: aload 5
        //   65: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	66	0	this	Proxy
        //   0	66	1	paramString	String
        //   0	66	2	paramBoolean	boolean
        //   1	52	3	i	int
        //   5	54	4	localParcel	Parcel
        //   56	8	5	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   7	20	56	finally
        //   24	45	56	finally
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.content.pm.IPackageDataObserver
 * JD-Core Version:    0.7.0.1
 */