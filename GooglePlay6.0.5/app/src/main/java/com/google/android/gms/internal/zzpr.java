package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.google.android.gms.feedback.ErrorReport;

public abstract interface zzpr
  extends IInterface
{
  public abstract boolean zza(ErrorReport paramErrorReport)
    throws RemoteException;
  
  public abstract boolean zzb(ErrorReport paramErrorReport)
    throws RemoteException;
  
  public abstract void zzy(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzpr
  {
    public static zzpr zzdd(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.feedback.internal.IFeedbackService");
      if ((localIInterface != null) && ((localIInterface instanceof zzpr))) {
        return (zzpr)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.feedback.internal.IFeedbackService");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.feedback.internal.IFeedbackService");
        int k = paramParcel1.readInt();
        ErrorReport localErrorReport2 = null;
        if (k != 0) {
          localErrorReport2 = (ErrorReport)ErrorReport.CREATOR.createFromParcel(paramParcel1);
        }
        boolean bool2 = zza(localErrorReport2);
        paramParcel2.writeNoException();
        if (bool2) {}
        for (int m = 1;; m = 0)
        {
          paramParcel2.writeInt(m);
          return true;
        }
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.feedback.internal.IFeedbackService");
        zzy(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.feedback.internal.IFeedbackService");
      int i = paramParcel1.readInt();
      ErrorReport localErrorReport1 = null;
      if (i != 0) {
        localErrorReport1 = (ErrorReport)ErrorReport.CREATOR.createFromParcel(paramParcel1);
      }
      boolean bool1 = zzb(localErrorReport1);
      paramParcel2.writeNoException();
      int j = 0;
      if (bool1) {
        j = 1;
      }
      paramParcel2.writeInt(j);
      return true;
    }
    
    private static final class zza
      implements zzpr
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
      
      public final boolean zza(ErrorReport paramErrorReport)
        throws RemoteException
      {
        boolean bool = true;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.gms.feedback.internal.IFeedbackService");
            if (paramErrorReport != null)
            {
              localParcel1.writeInt(1);
              paramErrorReport.writeToParcel(localParcel1, 0);
              this.zzop.transact(1, localParcel1, localParcel2, 0);
              localParcel2.readException();
              int i = localParcel2.readInt();
              if (i != 0) {
                return bool;
              }
            }
            else
            {
              localParcel1.writeInt(0);
              continue;
            }
            bool = false;
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
        }
      }
      
      public final boolean zzb(ErrorReport paramErrorReport)
        throws RemoteException
      {
        boolean bool = true;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.gms.feedback.internal.IFeedbackService");
            if (paramErrorReport != null)
            {
              localParcel1.writeInt(1);
              paramErrorReport.writeToParcel(localParcel1, 0);
              this.zzop.transact(3, localParcel1, localParcel2, 0);
              localParcel2.readException();
              int i = localParcel2.readInt();
              if (i != 0) {
                return bool;
              }
            }
            else
            {
              localParcel1.writeInt(0);
              continue;
            }
            bool = false;
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
        }
      }
      
      public final void zzy(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.feedback.internal.IFeedbackService");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.zzop.transact(2, localParcel1, localParcel2, 0);
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
 * Qualified Name:     com.google.android.gms.internal.zzpr
 * JD-Core Version:    0.7.0.1
 */