package android.support.v4.hardware.fingerprint;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;

public final class FingerprintManagerCompatApi23
{
  static FingerprintManager getFingerprintManager(Context paramContext)
  {
    return (FingerprintManager)paramContext.getSystemService(FingerprintManager.class);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23
 * JD-Core Version:    0.7.0.1
 */