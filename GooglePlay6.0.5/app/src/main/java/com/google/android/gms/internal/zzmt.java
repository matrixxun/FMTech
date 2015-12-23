package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.clearcut.LogEventParcelable;
import com.google.android.gms.clearcut.LogEventParcelableCreator;

public abstract interface zzmt
  extends IInterface
{
  public abstract void zza(zzms paramzzms, LogEventParcelable paramLogEventParcelable)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzmt
  {
    public static zzmt zzcb(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.clearcut.internal.IClearcutLoggerService");
      if ((localIInterface != null) && ((localIInterface instanceof zzmt))) {
        return (zzmt)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.clearcut.internal.IClearcutLoggerService");
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.clearcut.internal.IClearcutLoggerService");
      IBinder localIBinder = paramParcel1.readStrongBinder();
      Object localObject;
      if (localIBinder == null) {
        localObject = null;
      }
      for (;;)
      {
        int i = paramParcel1.readInt();
        LogEventParcelable localLogEventParcelable = null;
        if (i != 0) {
          localLogEventParcelable = LogEventParcelableCreator.createFromParcel(paramParcel1);
        }
        zza((zzms)localObject, localLogEventParcelable);
        return true;
        IInterface localIInterface = localIBinder.queryLocalInterface("com.google.android.gms.clearcut.internal.IClearcutLoggerCallbacks");
        if ((localIInterface != null) && ((localIInterface instanceof zzms))) {
          localObject = (zzms)localIInterface;
        } else {
          localObject = new zzms.zza.zza(localIBinder);
        }
      }
    }
    
    private static final class zza
      implements zzmt
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
      public final void zza(zzms paramzzms, LogEventParcelable paramLogEventParcelable)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: aload_3
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aconst_null
        //   11: astore 5
        //   13: aload_1
        //   14: ifnull +11 -> 25
        //   17: aload_1
        //   18: invokeinterface 37 1 0
        //   23: astore 5
        //   25: aload_3
        //   26: aload 5
        //   28: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   31: aload_2
        //   32: ifnull +33 -> 65
        //   35: aload_3
        //   36: iconst_1
        //   37: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   40: aload_2
        //   41: aload_3
        //   42: iconst_0
        //   43: invokevirtual 50	com/google/android/gms/clearcut/LogEventParcelable:writeToParcel	(Landroid/os/Parcel;I)V
        //   46: aload_0
        //   47: getfield 15	com/google/android/gms/internal/zzmt$zza$zza:zzop	Landroid/os/IBinder;
        //   50: iconst_1
        //   51: aload_3
        //   52: aconst_null
        //   53: iconst_1
        //   54: invokeinterface 56 5 0
        //   59: pop
        //   60: aload_3
        //   61: invokevirtual 59	android/os/Parcel:recycle	()V
        //   64: return
        //   65: aload_3
        //   66: iconst_0
        //   67: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   70: goto -24 -> 46
        //   73: astore 4
        //   75: aload_3
        //   76: invokevirtual 59	android/os/Parcel:recycle	()V
        //   79: aload 4
        //   81: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	82	0	this	zza
        //   0	82	1	paramzzms	zzms
        //   0	82	2	paramLogEventParcelable	LogEventParcelable
        //   3	73	3	localParcel	Parcel
        //   73	7	4	localObject	Object
        //   11	16	5	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   4	10	73	finally
        //   17	25	73	finally
        //   25	31	73	finally
        //   35	46	73	finally
        //   46	60	73	finally
        //   65	70	73	finally
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzmt
 * JD-Core Version:    0.7.0.1
 */