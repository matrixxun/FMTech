package com.google.android.gms.ads.internal.client;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zzd.zza;

public abstract interface zzx
  extends IInterface
{
  public abstract IBinder zza(zzd paramzzd, int paramInt)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzx
  {
    public static zzx zzt(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IMobileAdsSettingManagerCreator");
      if ((localIInterface != null) && ((localIInterface instanceof zzx))) {
        return (zzx)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.ads.internal.client.IMobileAdsSettingManagerCreator");
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.ads.internal.client.IMobileAdsSettingManagerCreator");
      IBinder localIBinder = zza(zzd.zza.zzdb(paramParcel1.readStrongBinder()), paramParcel1.readInt());
      paramParcel2.writeNoException();
      paramParcel2.writeStrongBinder(localIBinder);
      return true;
    }
    
    private static final class zza
      implements zzx
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
      public final IBinder zza(zzd paramzzd, int paramInt)
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
        //   15: aload_1
        //   16: ifnull +61 -> 77
        //   19: aload_1
        //   20: invokeinterface 37 1 0
        //   25: astore 6
        //   27: aload_3
        //   28: aload 6
        //   30: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   33: aload_3
        //   34: iload_2
        //   35: invokevirtual 44	android/os/Parcel:writeInt	(I)V
        //   38: aload_0
        //   39: getfield 15	com/google/android/gms/ads/internal/client/zzx$zza$zza:zzop	Landroid/os/IBinder;
        //   42: iconst_1
        //   43: aload_3
        //   44: aload 4
        //   46: iconst_0
        //   47: invokeinterface 50 5 0
        //   52: pop
        //   53: aload 4
        //   55: invokevirtual 53	android/os/Parcel:readException	()V
        //   58: aload 4
        //   60: invokevirtual 56	android/os/Parcel:readStrongBinder	()Landroid/os/IBinder;
        //   63: astore 8
        //   65: aload 4
        //   67: invokevirtual 59	android/os/Parcel:recycle	()V
        //   70: aload_3
        //   71: invokevirtual 59	android/os/Parcel:recycle	()V
        //   74: aload 8
        //   76: areturn
        //   77: aconst_null
        //   78: astore 6
        //   80: goto -53 -> 27
        //   83: astore 5
        //   85: aload 4
        //   87: invokevirtual 59	android/os/Parcel:recycle	()V
        //   90: aload_3
        //   91: invokevirtual 59	android/os/Parcel:recycle	()V
        //   94: aload 5
        //   96: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	97	0	this	zza
        //   0	97	1	paramzzd	zzd
        //   0	97	2	paramInt	int
        //   3	88	3	localParcel1	Parcel
        //   7	79	4	localParcel2	Parcel
        //   83	12	5	localObject	Object
        //   25	54	6	localIBinder1	IBinder
        //   63	12	8	localIBinder2	IBinder
        // Exception table:
        //   from	to	target	type
        //   9	15	83	finally
        //   19	27	83	finally
        //   27	65	83	finally
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.client.zzx
 * JD-Core Version:    0.7.0.1
 */