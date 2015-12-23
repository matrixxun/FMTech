package com.google.android.gms.common;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import java.util.Set;

public final class GoogleSignatureVerifier
{
  private static final GoogleSignatureVerifier zzanO = new GoogleSignatureVerifier();
  
  public static GoogleSignatureVerifier getInstance()
  {
    return zzanO;
  }
  
  static zzc.zza zza(PackageInfo paramPackageInfo, zzc.zza... paramVarArgs)
  {
    if (paramPackageInfo.signatures.length != 1)
    {
      Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
      return null;
    }
    zzc.zzb localzzb = new zzc.zzb(paramPackageInfo.signatures[0].toByteArray());
    for (int i = 0; i < paramVarArgs.length; i++) {
      if (paramVarArgs[i].equals(localzzb)) {
        return paramVarArgs[i];
      }
    }
    if (Log.isLoggable("GoogleSignatureVerifier", 2)) {
      Log.v("GoogleSignatureVerifier", "Signature not valid.  Found: \n" + Base64.encodeToString(localzzb.getBytes(), 0));
    }
    return null;
  }
  
  private static boolean zza(PackageInfo paramPackageInfo, boolean paramBoolean)
  {
    if (paramPackageInfo.signatures.length != 1)
    {
      Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
      return false;
    }
    zzc.zzb localzzb = new zzc.zzb(paramPackageInfo.signatures[0].toByteArray());
    if (paramBoolean) {}
    for (Set localSet = zzc.zzok(); localSet.contains(localzzb); localSet = zzc.zzol()) {
      return true;
    }
    if (Log.isLoggable("GoogleSignatureVerifier", 2)) {
      Log.v("GoogleSignatureVerifier", "Signature not valid.  Found: \n" + Base64.encodeToString(localzzb.getBytes(), 0));
    }
    return false;
  }
  
  public final boolean isPackageGoogleSigned(PackageManager paramPackageManager, String paramString)
  {
    PackageInfo localPackageInfo;
    boolean bool2;
    label66:
    do
    {
      try
      {
        localPackageInfo = paramPackageManager.getPackageInfo(paramString, 64);
        bool2 = false;
        if (localPackageInfo != null) {
          break label66;
        }
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        boolean bool1;
        do
        {
          bool1 = Log.isLoggable("GoogleSignatureVerifier", 3);
          bool2 = false;
        } while (!bool1);
        Log.d("GoogleSignatureVerifier", "Package manager can't find package " + paramString + ", defaulting to false");
        return false;
      }
      return bool2;
      if (GooglePlayServicesUtil.honorsDebugCertificates(paramPackageManager)) {
        return zza(localPackageInfo, true);
      }
      bool2 = zza(localPackageInfo, false);
    } while ((bool2) || (!zza(localPackageInfo, true)));
    Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
    return bool2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.GoogleSignatureVerifier
 * JD-Core Version:    0.7.0.1
 */