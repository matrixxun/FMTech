package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface IPackageStatsObserver
  extends IInterface
{
  public abstract void onGetStatsCompleted(PackageStats paramPackageStats, boolean paramBoolean)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IPackageStatsObserver
  {
    public Stub()
    {
      attachInterface(this, "android.content.pm.IPackageStatsObserver");
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
        paramParcel2.writeString("android.content.pm.IPackageStatsObserver");
        return true;
      }
      paramParcel1.enforceInterface("android.content.pm.IPackageStatsObserver");
      PackageStats localPackageStats;
      if (paramParcel1.readInt() != 0)
      {
        localPackageStats = (PackageStats)PackageStats.CREATOR.createFromParcel(paramParcel1);
        if (paramParcel1.readInt() == 0) {
          break label99;
        }
      }
      label99:
      for (boolean bool = true;; bool = false)
      {
        onGetStatsCompleted(localPackageStats, bool);
        return true;
        localPackageStats = null;
        break;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.content.pm.IPackageStatsObserver
 * JD-Core Version:    0.7.0.1
 */