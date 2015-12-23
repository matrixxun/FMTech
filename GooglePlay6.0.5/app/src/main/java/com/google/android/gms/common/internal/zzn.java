package com.google.android.gms.common.internal;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;

public final class zzn
{
  private static final Uri zzauv;
  private static final Uri zzauw;
  
  static
  {
    Uri localUri = Uri.parse("http://plus.google.com/");
    zzauv = localUri;
    zzauw = localUri.buildUpon().appendPath("circles").appendPath("find").build();
  }
  
  public static Intent zzG(String paramString1, String paramString2)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW");
    Uri.Builder localBuilder = Uri.parse("market://details").buildUpon().appendQueryParameter("id", paramString1);
    if (!TextUtils.isEmpty(paramString2)) {
      localBuilder.appendQueryParameter("pcampaignid", paramString2);
    }
    localIntent.setData(localBuilder.build());
    localIntent.setPackage("com.android.vending");
    localIntent.addFlags(524288);
    return localIntent;
  }
  
  public static boolean zza(PackageManager paramPackageManager, Intent paramIntent)
  {
    return paramPackageManager.resolveActivity(paramIntent, 65536) != null;
  }
  
  public static Intent zzcD(String paramString)
  {
    Uri localUri = Uri.fromParts("package", paramString, null);
    Intent localIntent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
    localIntent.setData(localUri);
    return localIntent;
  }
  
  public static Intent zzqz()
  {
    Intent localIntent = new Intent("com.google.android.clockwork.home.UPDATE_ANDROID_WEAR_ACTION");
    localIntent.setPackage("com.google.android.wearable.app");
    return localIntent;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzn
 * JD-Core Version:    0.7.0.1
 */