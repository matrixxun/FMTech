package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.herrevad.PredictedNetworkQuality;

public abstract interface zzrs
  extends IInterface
{
  public abstract void zza(Status paramStatus, PredictedNetworkQuality paramPredictedNetworkQuality)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzrs
  {
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      default: 
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902: 
        paramParcel2.writeString("com.google.android.gms.herrevad.internal.IActiveNetworkQualityCallbacks");
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.herrevad.internal.IActiveNetworkQualityCallbacks");
      Status localStatus;
      if (paramParcel1.readInt() != 0)
      {
        localStatus = (Status)Status.CREATOR.createFromParcel(paramParcel1);
        if (paramParcel1.readInt() == 0) {
          break label110;
        }
      }
      label110:
      for (PredictedNetworkQuality localPredictedNetworkQuality = (PredictedNetworkQuality)PredictedNetworkQuality.CREATOR.createFromParcel(paramParcel1);; localPredictedNetworkQuality = null)
      {
        zza(localStatus, localPredictedNetworkQuality);
        return true;
        localStatus = null;
        break;
      }
    }
    
    private static final class zza
      implements zzrs
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
      
      public final void zza(Status paramStatus, PredictedNetworkQuality paramPredictedNetworkQuality)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.herrevad.internal.IActiveNetworkQualityCallbacks");
            if (paramStatus != null)
            {
              localParcel.writeInt(1);
              paramStatus.writeToParcel(localParcel, 0);
              if (paramPredictedNetworkQuality != null)
              {
                localParcel.writeInt(1);
                paramPredictedNetworkQuality.writeToParcel(localParcel, 0);
                this.zzop.transact(2, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
            localParcel.writeInt(0);
          }
          finally
          {
            localParcel.recycle();
          }
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzrs
 * JD-Core Version:    0.7.0.1
 */