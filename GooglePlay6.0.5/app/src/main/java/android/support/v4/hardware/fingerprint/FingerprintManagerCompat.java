package android.support.v4.hardware.fingerprint;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build.VERSION;

public final class FingerprintManagerCompat
{
  public static final FingerprintManagerCompatImpl IMPL = new LegacyFingerprintManagerCompatImpl();
  public Context mContext;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 23)
    {
      IMPL = new Api23FingerprintManagerCompatImpl();
      return;
    }
  }
  
  private FingerprintManagerCompat(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public static FingerprintManagerCompat from(Context paramContext)
  {
    return new FingerprintManagerCompat(paramContext);
  }
  
  private static final class Api23FingerprintManagerCompatImpl
    implements FingerprintManagerCompat.FingerprintManagerCompatImpl
  {
    public final boolean hasEnrolledFingerprints(Context paramContext)
    {
      return FingerprintManagerCompatApi23.getFingerprintManager(paramContext).hasEnrolledFingerprints();
    }
    
    public final boolean isHardwareDetected(Context paramContext)
    {
      return FingerprintManagerCompatApi23.getFingerprintManager(paramContext).isHardwareDetected();
    }
  }
  
  private static abstract interface FingerprintManagerCompatImpl
  {
    public abstract boolean hasEnrolledFingerprints(Context paramContext);
    
    public abstract boolean isHardwareDetected(Context paramContext);
  }
  
  private static final class LegacyFingerprintManagerCompatImpl
    implements FingerprintManagerCompat.FingerprintManagerCompatImpl
  {
    public final boolean hasEnrolledFingerprints(Context paramContext)
    {
      return false;
    }
    
    public final boolean isHardwareDetected(Context paramContext)
    {
      return false;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.hardware.fingerprint.FingerprintManagerCompat
 * JD-Core Version:    0.7.0.1
 */