package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.AttestationData;
import com.google.android.gms.safetynet.SafeBrowsingData;

public abstract interface zzvj
  extends IInterface
{
  public abstract void onGetIdResults(String paramString)
    throws RemoteException;
  
  public abstract void zza(Status paramStatus, AttestationData paramAttestationData)
    throws RemoteException;
  
  public abstract void zza(Status paramStatus, SafeBrowsingData paramSafeBrowsingData)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzvj
  {
    public zza()
    {
      attachInterface(this, "com.google.android.gms.safetynet.internal.ISafetyNetCallbacks");
    }
    
    public static zzvj zzgr(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.safetynet.internal.ISafetyNetCallbacks");
      if ((localIInterface != null) && ((localIInterface instanceof zzvj))) {
        return (zzvj)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.safetynet.internal.ISafetyNetCallbacks");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.safetynet.internal.ISafetyNetCallbacks");
        Status localStatus2;
        if (paramParcel1.readInt() != 0)
        {
          localStatus2 = (Status)Status.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label126;
          }
        }
        for (AttestationData localAttestationData = (AttestationData)AttestationData.CREATOR.createFromParcel(paramParcel1);; localAttestationData = null)
        {
          zza(localStatus2, localAttestationData);
          return true;
          localStatus2 = null;
          break;
        }
      case 2: 
        label126:
        paramParcel1.enforceInterface("com.google.android.gms.safetynet.internal.ISafetyNetCallbacks");
        onGetIdResults(paramParcel1.readString());
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.safetynet.internal.ISafetyNetCallbacks");
      Status localStatus1;
      if (paramParcel1.readInt() != 0)
      {
        localStatus1 = (Status)Status.CREATOR.createFromParcel(paramParcel1);
        if (paramParcel1.readInt() == 0) {
          break label212;
        }
      }
      label212:
      for (SafeBrowsingData localSafeBrowsingData = (SafeBrowsingData)SafeBrowsingData.CREATOR.createFromParcel(paramParcel1);; localSafeBrowsingData = null)
      {
        zza(localStatus1, localSafeBrowsingData);
        return true;
        localStatus1 = null;
        break;
      }
    }
    
    private static final class zza
      implements zzvj
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
      
      public final void onGetIdResults(String paramString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.safetynet.internal.ISafetyNetCallbacks");
          localParcel.writeString(paramString);
          this.zzop.transact(2, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public final void zza(Status paramStatus, AttestationData paramAttestationData)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.safetynet.internal.ISafetyNetCallbacks");
            if (paramStatus != null)
            {
              localParcel.writeInt(1);
              paramStatus.writeToParcel(localParcel, 0);
              if (paramAttestationData != null)
              {
                localParcel.writeInt(1);
                paramAttestationData.writeToParcel(localParcel, 0);
                this.zzop.transact(1, localParcel, null, 1);
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
      
      public final void zza(Status paramStatus, SafeBrowsingData paramSafeBrowsingData)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.safetynet.internal.ISafetyNetCallbacks");
            if (paramStatus != null)
            {
              localParcel.writeInt(1);
              paramStatus.writeToParcel(localParcel, 0);
              if (paramSafeBrowsingData != null)
              {
                localParcel.writeInt(1);
                paramSafeBrowsingData.writeToParcel(localParcel, 0);
                this.zzop.transact(3, localParcel, null, 1);
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
 * Qualified Name:     com.google.android.gms.internal.zzvj
 * JD-Core Version:    0.7.0.1
 */