package com.google.android.gms.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.google.android.gms.auth.RecoveryDecision;
import com.google.android.gms.auth.RecoveryDecisionCreator;
import com.google.android.gms.auth.RecoveryReadResponse;
import com.google.android.gms.auth.RecoveryWriteRequest;
import com.google.android.gms.auth.RecoveryWriteRequestCreator;
import com.google.android.gms.auth.RecoveryWriteResponse;
import com.google.android.gms.auth.RecoveryWriteResponseCreator;

public abstract interface zzaw
  extends IInterface
{
  public abstract RecoveryDecision zza(String paramString1, String paramString2, boolean paramBoolean, Bundle paramBundle)
    throws RemoteException;
  
  public abstract RecoveryWriteResponse zza(RecoveryWriteRequest paramRecoveryWriteRequest, String paramString)
    throws RemoteException;
  
  public abstract RecoveryReadResponse zzb(String paramString1, String paramString2)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzaw
  {
    public static zzaw zzb(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.auth.IRecoveryService");
      if ((localIInterface != null) && ((localIInterface instanceof zzaw))) {
        return (zzaw)localIInterface;
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
        paramParcel2.writeString("com.google.android.auth.IRecoveryService");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.auth.IRecoveryService");
        String str1 = paramParcel1.readString();
        String str2 = paramParcel1.readString();
        if (paramParcel1.readInt() != 0) {}
        for (boolean bool = true;; bool = false)
        {
          int j = paramParcel1.readInt();
          Bundle localBundle = null;
          if (j != 0) {
            localBundle = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
          }
          RecoveryDecision localRecoveryDecision = zza(str1, str2, bool, localBundle);
          paramParcel2.writeNoException();
          if (localRecoveryDecision == null) {
            break;
          }
          paramParcel2.writeInt(1);
          localRecoveryDecision.writeToParcel(paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.google.android.auth.IRecoveryService");
        RecoveryReadResponse localRecoveryReadResponse = zzb(paramParcel1.readString(), paramParcel1.readString());
        paramParcel2.writeNoException();
        if (localRecoveryReadResponse != null)
        {
          paramParcel2.writeInt(1);
          localRecoveryReadResponse.writeToParcel(paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.auth.IRecoveryService");
      int i = paramParcel1.readInt();
      RecoveryWriteRequest localRecoveryWriteRequest = null;
      if (i != 0) {
        localRecoveryWriteRequest = RecoveryWriteRequestCreator.createFromParcel(paramParcel1);
      }
      RecoveryWriteResponse localRecoveryWriteResponse = zza(localRecoveryWriteRequest, paramParcel1.readString());
      paramParcel2.writeNoException();
      if (localRecoveryWriteResponse != null)
      {
        paramParcel2.writeInt(1);
        localRecoveryWriteResponse.writeToParcel(paramParcel2, 1);
        return true;
      }
      paramParcel2.writeInt(0);
      return true;
    }
    
    private static final class zza
      implements zzaw
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
      
      public final RecoveryDecision zza(String paramString1, String paramString2, boolean paramBoolean, Bundle paramBundle)
        throws RemoteException
      {
        int i = 1;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        label149:
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.auth.IRecoveryService");
            localParcel1.writeString(paramString1);
            localParcel1.writeString(paramString2);
            if (paramBoolean)
            {
              localParcel1.writeInt(i);
              if (paramBundle != null)
              {
                localParcel1.writeInt(1);
                paramBundle.writeToParcel(localParcel1, 0);
                this.zzop.transact(1, localParcel1, localParcel2, 0);
                localParcel2.readException();
                if (localParcel2.readInt() == 0) {
                  break label149;
                }
                RecoveryDecision localRecoveryDecision2 = RecoveryDecisionCreator.createFromParcel(localParcel2);
                localRecoveryDecision1 = localRecoveryDecision2;
                return localRecoveryDecision1;
              }
            }
            else
            {
              i = 0;
              continue;
            }
            localParcel1.writeInt(0);
            continue;
            RecoveryDecision localRecoveryDecision1 = null;
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
        }
      }
      
      public final RecoveryWriteResponse zza(RecoveryWriteRequest paramRecoveryWriteRequest, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.auth.IRecoveryService");
            if (paramRecoveryWriteRequest != null)
            {
              localParcel1.writeInt(1);
              paramRecoveryWriteRequest.writeToParcel(localParcel1, 0);
              localParcel1.writeString(paramString);
              this.zzop.transact(3, localParcel1, localParcel2, 0);
              localParcel2.readException();
              if (localParcel2.readInt() != 0)
              {
                RecoveryWriteResponse localRecoveryWriteResponse2 = RecoveryWriteResponseCreator.createFromParcel(localParcel2);
                localRecoveryWriteResponse1 = localRecoveryWriteResponse2;
                return localRecoveryWriteResponse1;
              }
            }
            else
            {
              localParcel1.writeInt(0);
              continue;
            }
            RecoveryWriteResponse localRecoveryWriteResponse1 = null;
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
        }
      }
      
      /* Error */
      public final RecoveryReadResponse zzb(String paramString1, String paramString2)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore 4
        //   9: aload_3
        //   10: ldc 29
        //   12: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   15: aload_3
        //   16: aload_1
        //   17: invokevirtual 36	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   20: aload_3
        //   21: aload_2
        //   22: invokevirtual 36	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   25: aload_0
        //   26: getfield 15	com/google/android/gms/internal/zzaw$zza$zza:zzop	Landroid/os/IBinder;
        //   29: iconst_2
        //   30: aload_3
        //   31: aload 4
        //   33: iconst_0
        //   34: invokeinterface 52 5 0
        //   39: pop
        //   40: aload 4
        //   42: invokevirtual 55	android/os/Parcel:readException	()V
        //   45: aload 4
        //   47: invokevirtual 59	android/os/Parcel:readInt	()I
        //   50: ifeq +30 -> 80
        //   53: getstatic 95	com/google/android/gms/auth/RecoveryReadResponse:CREATOR	Lcom/google/android/gms/auth/RecoveryReadResponseCreator;
        //   56: pop
        //   57: aload 4
        //   59: invokestatic 100	com/google/android/gms/auth/RecoveryReadResponseCreator:createFromParcel	(Landroid/os/Parcel;)Lcom/google/android/gms/auth/RecoveryReadResponse;
        //   62: astore 9
        //   64: aload 9
        //   66: astore 7
        //   68: aload 4
        //   70: invokevirtual 74	android/os/Parcel:recycle	()V
        //   73: aload_3
        //   74: invokevirtual 74	android/os/Parcel:recycle	()V
        //   77: aload 7
        //   79: areturn
        //   80: aconst_null
        //   81: astore 7
        //   83: goto -15 -> 68
        //   86: astore 5
        //   88: aload 4
        //   90: invokevirtual 74	android/os/Parcel:recycle	()V
        //   93: aload_3
        //   94: invokevirtual 74	android/os/Parcel:recycle	()V
        //   97: aload 5
        //   99: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	100	0	this	zza
        //   0	100	1	paramString1	String
        //   0	100	2	paramString2	String
        //   3	91	3	localParcel1	Parcel
        //   7	82	4	localParcel2	Parcel
        //   86	12	5	localObject	Object
        //   66	16	7	localRecoveryReadResponse1	RecoveryReadResponse
        //   62	3	9	localRecoveryReadResponse2	RecoveryReadResponse
        // Exception table:
        //   from	to	target	type
        //   9	64	86	finally
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzaw
 * JD-Core Version:    0.7.0.1
 */