package com.google.android.gms.googlehelp.internal.common;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.google.android.gms.googlehelp.GoogleHelp;

public abstract interface zzf
  extends IInterface
{
  public abstract void zza(Bitmap paramBitmap, zze paramzze)
    throws RemoteException;
  
  public abstract void zza(GoogleHelp paramGoogleHelp, Bitmap paramBitmap, zze paramzze)
    throws RemoteException;
  
  public abstract void zza(GoogleHelp paramGoogleHelp, zze paramzze)
    throws RemoteException;
  
  public abstract void zza(zze paramzze)
    throws RemoteException;
  
  public abstract void zzb(zze paramzze)
    throws RemoteException;
  
  public abstract void zzc(zze paramzze)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzf
  {
    public static zzf zzdO(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.googlehelp.internal.common.IGoogleHelpService");
      if ((localIInterface != null) && ((localIInterface instanceof zzf))) {
        return (zzf)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.googlehelp.internal.common.IGoogleHelpService");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.googlehelp.internal.common.IGoogleHelpService");
        if (paramParcel1.readInt() != 0) {}
        for (GoogleHelp localGoogleHelp2 = (GoogleHelp)GoogleHelp.CREATOR.createFromParcel(paramParcel1);; localGoogleHelp2 = null)
        {
          zza(localGoogleHelp2, zze.zza.zzdN(paramParcel1.readStrongBinder()));
          paramParcel2.writeNoException();
          return true;
        }
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.googlehelp.internal.common.IGoogleHelpService");
        GoogleHelp localGoogleHelp1;
        if (paramParcel1.readInt() != 0)
        {
          localGoogleHelp1 = (GoogleHelp)GoogleHelp.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label213;
          }
        }
        for (Bitmap localBitmap2 = (Bitmap)Bitmap.CREATOR.createFromParcel(paramParcel1);; localBitmap2 = null)
        {
          zza(localGoogleHelp1, localBitmap2, zze.zza.zzdN(paramParcel1.readStrongBinder()));
          paramParcel2.writeNoException();
          return true;
          localGoogleHelp1 = null;
          break;
        }
      case 3: 
        paramParcel1.enforceInterface("com.google.android.gms.googlehelp.internal.common.IGoogleHelpService");
        if (paramParcel1.readInt() != 0) {}
        for (Bitmap localBitmap1 = (Bitmap)Bitmap.CREATOR.createFromParcel(paramParcel1);; localBitmap1 = null)
        {
          zza(localBitmap1, zze.zza.zzdN(paramParcel1.readStrongBinder()));
          paramParcel2.writeNoException();
          return true;
        }
      case 4: 
        paramParcel1.enforceInterface("com.google.android.gms.googlehelp.internal.common.IGoogleHelpService");
        zza(zze.zza.zzdN(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      case 5: 
        label213:
        paramParcel1.enforceInterface("com.google.android.gms.googlehelp.internal.common.IGoogleHelpService");
        zzb(zze.zza.zzdN(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.googlehelp.internal.common.IGoogleHelpService");
      zzc(zze.zza.zzdN(paramParcel1.readStrongBinder()));
      paramParcel2.writeNoException();
      return true;
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
      
      public final void zza(Bitmap paramBitmap, zze paramzze)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.gms.googlehelp.internal.common.IGoogleHelpService");
            if (paramBitmap != null)
            {
              localParcel1.writeInt(1);
              paramBitmap.writeToParcel(localParcel1, 0);
              if (paramzze != null)
              {
                localIBinder = paramzze.asBinder();
                localParcel1.writeStrongBinder(localIBinder);
                this.zzop.transact(3, localParcel1, localParcel2, 0);
                localParcel2.readException();
              }
            }
            else
            {
              localParcel1.writeInt(0);
              continue;
            }
            IBinder localIBinder = null;
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
        }
      }
      
      public final void zza(GoogleHelp paramGoogleHelp, Bitmap paramBitmap, zze paramzze)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.gms.googlehelp.internal.common.IGoogleHelpService");
            if (paramGoogleHelp != null)
            {
              localParcel1.writeInt(1);
              paramGoogleHelp.writeToParcel(localParcel1, 0);
              if (paramBitmap != null)
              {
                localParcel1.writeInt(1);
                paramBitmap.writeToParcel(localParcel1, 0);
                if (paramzze == null) {
                  break label135;
                }
                localIBinder = paramzze.asBinder();
                localParcel1.writeStrongBinder(localIBinder);
                this.zzop.transact(2, localParcel1, localParcel2, 0);
                localParcel2.readException();
              }
            }
            else
            {
              localParcel1.writeInt(0);
              continue;
            }
            localParcel1.writeInt(0);
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          continue;
          label135:
          IBinder localIBinder = null;
        }
      }
      
      public final void zza(GoogleHelp paramGoogleHelp, zze paramzze)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.gms.googlehelp.internal.common.IGoogleHelpService");
            if (paramGoogleHelp != null)
            {
              localParcel1.writeInt(1);
              paramGoogleHelp.writeToParcel(localParcel1, 0);
              if (paramzze != null)
              {
                localIBinder = paramzze.asBinder();
                localParcel1.writeStrongBinder(localIBinder);
                this.zzop.transact(1, localParcel1, localParcel2, 0);
                localParcel2.readException();
              }
            }
            else
            {
              localParcel1.writeInt(0);
              continue;
            }
            IBinder localIBinder = null;
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
        }
      }
      
      /* Error */
      public final void zza(zze paramzze)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_3
        //   8: aload_2
        //   9: ldc 29
        //   11: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_1
        //   15: ifnull +44 -> 59
        //   18: aload_1
        //   19: invokeinterface 47 1 0
        //   24: astore 5
        //   26: aload_2
        //   27: aload 5
        //   29: invokevirtual 50	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   32: aload_0
        //   33: getfield 15	com/google/android/gms/googlehelp/internal/common/zzf$zza$zza:zzop	Landroid/os/IBinder;
        //   36: iconst_4
        //   37: aload_2
        //   38: aload_3
        //   39: iconst_0
        //   40: invokeinterface 56 5 0
        //   45: pop
        //   46: aload_3
        //   47: invokevirtual 59	android/os/Parcel:readException	()V
        //   50: aload_3
        //   51: invokevirtual 62	android/os/Parcel:recycle	()V
        //   54: aload_2
        //   55: invokevirtual 62	android/os/Parcel:recycle	()V
        //   58: return
        //   59: aconst_null
        //   60: astore 5
        //   62: goto -36 -> 26
        //   65: astore 4
        //   67: aload_3
        //   68: invokevirtual 62	android/os/Parcel:recycle	()V
        //   71: aload_2
        //   72: invokevirtual 62	android/os/Parcel:recycle	()V
        //   75: aload 4
        //   77: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	78	0	this	zza
        //   0	78	1	paramzze	zze
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
      
      /* Error */
      public final void zzb(zze paramzze)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_3
        //   8: aload_2
        //   9: ldc 29
        //   11: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_1
        //   15: ifnull +44 -> 59
        //   18: aload_1
        //   19: invokeinterface 47 1 0
        //   24: astore 5
        //   26: aload_2
        //   27: aload 5
        //   29: invokevirtual 50	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   32: aload_0
        //   33: getfield 15	com/google/android/gms/googlehelp/internal/common/zzf$zza$zza:zzop	Landroid/os/IBinder;
        //   36: iconst_5
        //   37: aload_2
        //   38: aload_3
        //   39: iconst_0
        //   40: invokeinterface 56 5 0
        //   45: pop
        //   46: aload_3
        //   47: invokevirtual 59	android/os/Parcel:readException	()V
        //   50: aload_3
        //   51: invokevirtual 62	android/os/Parcel:recycle	()V
        //   54: aload_2
        //   55: invokevirtual 62	android/os/Parcel:recycle	()V
        //   58: return
        //   59: aconst_null
        //   60: astore 5
        //   62: goto -36 -> 26
        //   65: astore 4
        //   67: aload_3
        //   68: invokevirtual 62	android/os/Parcel:recycle	()V
        //   71: aload_2
        //   72: invokevirtual 62	android/os/Parcel:recycle	()V
        //   75: aload 4
        //   77: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	78	0	this	zza
        //   0	78	1	paramzze	zze
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
      
      /* Error */
      public final void zzc(zze paramzze)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_3
        //   8: aload_2
        //   9: ldc 29
        //   11: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_1
        //   15: ifnull +45 -> 60
        //   18: aload_1
        //   19: invokeinterface 47 1 0
        //   24: astore 5
        //   26: aload_2
        //   27: aload 5
        //   29: invokevirtual 50	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   32: aload_0
        //   33: getfield 15	com/google/android/gms/googlehelp/internal/common/zzf$zza$zza:zzop	Landroid/os/IBinder;
        //   36: bipush 6
        //   38: aload_2
        //   39: aload_3
        //   40: iconst_0
        //   41: invokeinterface 56 5 0
        //   46: pop
        //   47: aload_3
        //   48: invokevirtual 59	android/os/Parcel:readException	()V
        //   51: aload_3
        //   52: invokevirtual 62	android/os/Parcel:recycle	()V
        //   55: aload_2
        //   56: invokevirtual 62	android/os/Parcel:recycle	()V
        //   59: return
        //   60: aconst_null
        //   61: astore 5
        //   63: goto -37 -> 26
        //   66: astore 4
        //   68: aload_3
        //   69: invokevirtual 62	android/os/Parcel:recycle	()V
        //   72: aload_2
        //   73: invokevirtual 62	android/os/Parcel:recycle	()V
        //   76: aload 4
        //   78: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	79	0	this	zza
        //   0	79	1	paramzze	zze
        //   3	70	2	localParcel1	Parcel
        //   7	62	3	localParcel2	Parcel
        //   66	11	4	localObject	Object
        //   24	38	5	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   8	14	66	finally
        //   18	26	66	finally
        //   26	51	66	finally
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.googlehelp.internal.common.zzf
 * JD-Core Version:    0.7.0.1
 */