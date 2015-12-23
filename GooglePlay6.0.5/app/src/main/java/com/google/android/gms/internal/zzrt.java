package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.herrevad.PredictedNetworkQuality;
import java.util.List;

public abstract interface zzrt
  extends IInterface
{
  public abstract void zza(Status paramStatus, List<PredictedNetworkQuality> paramList)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzrt
  {
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      default: 
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902: 
        paramParcel2.writeString("com.google.android.gms.herrevad.internal.IConnectedNetworksQualityCallbacks");
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.herrevad.internal.IConnectedNetworksQualityCallbacks");
      if (paramParcel1.readInt() != 0) {}
      for (Status localStatus = (Status)Status.CREATOR.createFromParcel(paramParcel1);; localStatus = null)
      {
        zza(localStatus, paramParcel1.createTypedArrayList(PredictedNetworkQuality.CREATOR));
        return true;
      }
    }
    
    private static final class zza
      implements zzrt
    {
      private IBinder zzop;
      
      public zza(IBinder paramIBinder)
      {
        this.zzop = paramIBinder;
      }
      
      public final IBinder asBinder()
      {
        return this.zzop;
      }
      
      /* Error */
      public final void zza(Status paramStatus, List<PredictedNetworkQuality> paramList)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: aload_3
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +38 -> 49
        //   14: aload_3
        //   15: iconst_1
        //   16: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_3
        //   21: iconst_0
        //   22: invokevirtual 43	com/google/android/gms/common/api/Status:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_3
        //   26: aload_2
        //   27: invokevirtual 47	android/os/Parcel:writeTypedList	(Ljava/util/List;)V
        //   30: aload_0
        //   31: getfield 15	com/google/android/gms/internal/zzrt$zza$zza:zzop	Landroid/os/IBinder;
        //   34: iconst_2
        //   35: aload_3
        //   36: aconst_null
        //   37: iconst_1
        //   38: invokeinterface 53 5 0
        //   43: pop
        //   44: aload_3
        //   45: invokevirtual 56	android/os/Parcel:recycle	()V
        //   48: return
        //   49: aload_3
        //   50: iconst_0
        //   51: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   54: goto -29 -> 25
        //   57: astore 4
        //   59: aload_3
        //   60: invokevirtual 56	android/os/Parcel:recycle	()V
        //   63: aload 4
        //   65: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	66	0	this	zza
        //   0	66	1	paramStatus	Status
        //   0	66	2	paramList	List<PredictedNetworkQuality>
        //   3	57	3	localParcel	Parcel
        //   57	7	4	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	10	57	finally
        //   14	25	57	finally
        //   25	44	57	finally
        //   49	54	57	finally
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzrt
 * JD-Core Version:    0.7.0.1
 */