package com.google.android.gms.people.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.DataHolderCreator;

public abstract interface zzf
  extends IInterface
{
  public abstract void zza(int paramInt, Bundle paramBundle1, Bundle paramBundle2)
    throws RemoteException;
  
  public abstract void zza(int paramInt, Bundle paramBundle, ParcelFileDescriptor paramParcelFileDescriptor)
    throws RemoteException;
  
  public abstract void zza(int paramInt, Bundle paramBundle1, ParcelFileDescriptor paramParcelFileDescriptor, Bundle paramBundle2)
    throws RemoteException;
  
  public abstract void zza(int paramInt, Bundle paramBundle, DataHolder paramDataHolder)
    throws RemoteException;
  
  public abstract void zza(int paramInt, Bundle paramBundle, DataHolder[] paramArrayOfDataHolder)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzf
  {
    public zza()
    {
      attachInterface(this, "com.google.android.gms.people.internal.IPeopleCallbacks");
    }
    
    public static zzf zzfS(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.people.internal.IPeopleCallbacks");
      if ((localIInterface != null) && ((localIInterface instanceof zzf))) {
        return (zzf)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.people.internal.IPeopleCallbacks");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.people.internal.IPeopleCallbacks");
        int i2 = paramParcel1.readInt();
        Bundle localBundle6;
        if (paramParcel1.readInt() != 0)
        {
          localBundle6 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label150;
          }
        }
        for (Bundle localBundle7 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);; localBundle7 = null)
        {
          zza(i2, localBundle6, localBundle7);
          return true;
          localBundle6 = null;
          break;
        }
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.people.internal.IPeopleCallbacks");
        int n = paramParcel1.readInt();
        if (paramParcel1.readInt() != 0) {}
        for (Bundle localBundle5 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);; localBundle5 = null)
        {
          int i1 = paramParcel1.readInt();
          DataHolder localDataHolder = null;
          if (i1 != 0) {
            localDataHolder = DataHolderCreator.createFromParcel(paramParcel1);
          }
          zza(n, localBundle5, localDataHolder);
          return true;
        }
      case 3: 
        paramParcel1.enforceInterface("com.google.android.gms.people.internal.IPeopleCallbacks");
        int m = paramParcel1.readInt();
        Bundle localBundle4;
        if (paramParcel1.readInt() != 0)
        {
          localBundle4 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label303;
          }
        }
        for (ParcelFileDescriptor localParcelFileDescriptor2 = (ParcelFileDescriptor)ParcelFileDescriptor.CREATOR.createFromParcel(paramParcel1);; localParcelFileDescriptor2 = null)
        {
          zza(m, localBundle4, localParcelFileDescriptor2);
          return true;
          localBundle4 = null;
          break;
        }
      case 4: 
        label150:
        label303:
        paramParcel1.enforceInterface("com.google.android.gms.people.internal.IPeopleCallbacks");
        int j = paramParcel1.readInt();
        int k = paramParcel1.readInt();
        Bundle localBundle3 = null;
        if (k != 0) {
          localBundle3 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
        }
        zza(j, localBundle3, (DataHolder[])paramParcel1.createTypedArray(DataHolder.CREATOR));
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.people.internal.IPeopleCallbacks");
      int i = paramParcel1.readInt();
      Bundle localBundle1;
      ParcelFileDescriptor localParcelFileDescriptor1;
      if (paramParcel1.readInt() != 0)
      {
        localBundle1 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
        if (paramParcel1.readInt() == 0) {
          break label464;
        }
        localParcelFileDescriptor1 = (ParcelFileDescriptor)ParcelFileDescriptor.CREATOR.createFromParcel(paramParcel1);
        label423:
        if (paramParcel1.readInt() == 0) {
          break label470;
        }
      }
      label464:
      label470:
      for (Bundle localBundle2 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);; localBundle2 = null)
      {
        zza(i, localBundle1, localParcelFileDescriptor1, localBundle2);
        return true;
        localBundle1 = null;
        break;
        localParcelFileDescriptor1 = null;
        break label423;
      }
    }
    
    private static final class zza
      implements zzf
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
      
      public final void zza(int paramInt, Bundle paramBundle1, Bundle paramBundle2)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleCallbacks");
            localParcel.writeInt(paramInt);
            if (paramBundle1 != null)
            {
              localParcel.writeInt(1);
              paramBundle1.writeToParcel(localParcel, 0);
              if (paramBundle2 != null)
              {
                localParcel.writeInt(1);
                paramBundle2.writeToParcel(localParcel, 0);
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
      
      public final void zza(int paramInt, Bundle paramBundle, ParcelFileDescriptor paramParcelFileDescriptor)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleCallbacks");
            localParcel.writeInt(paramInt);
            if (paramBundle != null)
            {
              localParcel.writeInt(1);
              paramBundle.writeToParcel(localParcel, 0);
              if (paramParcelFileDescriptor != null)
              {
                localParcel.writeInt(1);
                paramParcelFileDescriptor.writeToParcel(localParcel, 0);
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
      
      public final void zza(int paramInt, Bundle paramBundle1, ParcelFileDescriptor paramParcelFileDescriptor, Bundle paramBundle2)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleCallbacks");
            localParcel.writeInt(paramInt);
            if (paramBundle1 != null)
            {
              localParcel.writeInt(1);
              paramBundle1.writeToParcel(localParcel, 0);
              if (paramParcelFileDescriptor != null)
              {
                localParcel.writeInt(1);
                paramParcelFileDescriptor.writeToParcel(localParcel, 0);
                if (paramBundle2 == null) {
                  break label120;
                }
                localParcel.writeInt(1);
                paramBundle2.writeToParcel(localParcel, 0);
                this.zzop.transact(5, localParcel, null, 1);
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
          continue;
          label120:
          localParcel.writeInt(0);
        }
      }
      
      public final void zza(int paramInt, Bundle paramBundle, DataHolder paramDataHolder)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.people.internal.IPeopleCallbacks");
            localParcel.writeInt(paramInt);
            if (paramBundle != null)
            {
              localParcel.writeInt(1);
              paramBundle.writeToParcel(localParcel, 0);
              if (paramDataHolder != null)
              {
                localParcel.writeInt(1);
                paramDataHolder.writeToParcel(localParcel, 0);
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
      
      /* Error */
      public final void zza(int paramInt, Bundle paramBundle, DataHolder[] paramArrayOfDataHolder)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore 4
        //   5: aload 4
        //   7: ldc 29
        //   9: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   12: aload 4
        //   14: iload_1
        //   15: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   18: aload_2
        //   19: ifnull +44 -> 63
        //   22: aload 4
        //   24: iconst_1
        //   25: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   28: aload_2
        //   29: aload 4
        //   31: iconst_0
        //   32: invokevirtual 43	android/os/Bundle:writeToParcel	(Landroid/os/Parcel;I)V
        //   35: aload 4
        //   37: aload_3
        //   38: iconst_0
        //   39: invokevirtual 66	android/os/Parcel:writeTypedArray	([Landroid/os/Parcelable;I)V
        //   42: aload_0
        //   43: getfield 15	com/google/android/gms/people/internal/zzf$zza$zza:zzop	Landroid/os/IBinder;
        //   46: iconst_4
        //   47: aload 4
        //   49: aconst_null
        //   50: iconst_1
        //   51: invokeinterface 49 5 0
        //   56: pop
        //   57: aload 4
        //   59: invokevirtual 52	android/os/Parcel:recycle	()V
        //   62: return
        //   63: aload 4
        //   65: iconst_0
        //   66: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   69: goto -34 -> 35
        //   72: astore 5
        //   74: aload 4
        //   76: invokevirtual 52	android/os/Parcel:recycle	()V
        //   79: aload 5
        //   81: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	82	0	this	zza
        //   0	82	1	paramInt	int
        //   0	82	2	paramBundle	Bundle
        //   0	82	3	paramArrayOfDataHolder	DataHolder[]
        //   3	72	4	localParcel	Parcel
        //   72	8	5	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   5	18	72	finally
        //   22	35	72	finally
        //   35	57	72	finally
        //   63	69	72	finally
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.internal.zzf
 * JD-Core Version:    0.7.0.1
 */