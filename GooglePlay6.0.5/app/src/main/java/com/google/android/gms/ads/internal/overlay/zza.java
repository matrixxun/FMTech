package com.google.android.gms.ads.internal.overlay;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.internal.zzhb;
import com.google.android.gms.internal.zziq;

@zzhb
public final class zza
{
  public static boolean zza$1d97657b(Context paramContext, AdLauncherIntentInfoParcel paramAdLauncherIntentInfoParcel)
  {
    if (paramAdLauncherIntentInfoParcel == null)
    {
      zzb.w("No intent data for launcher overlay.");
      return false;
    }
    Intent localIntent = new Intent();
    if (TextUtils.isEmpty(paramAdLauncherIntentInfoParcel.url))
    {
      zzb.w("Open GMSG did not contain a URL.");
      return false;
    }
    if (!TextUtils.isEmpty(paramAdLauncherIntentInfoParcel.mimeType)) {
      localIntent.setDataAndType(Uri.parse(paramAdLauncherIntentInfoParcel.url), paramAdLauncherIntentInfoParcel.mimeType);
    }
    String[] arrayOfString;
    for (;;)
    {
      localIntent.setAction("android.intent.action.VIEW");
      if (!TextUtils.isEmpty(paramAdLauncherIntentInfoParcel.packageName)) {
        localIntent.setPackage(paramAdLauncherIntentInfoParcel.packageName);
      }
      if (TextUtils.isEmpty(paramAdLauncherIntentInfoParcel.zzDi)) {
        break label169;
      }
      arrayOfString = paramAdLauncherIntentInfoParcel.zzDi.split("/", 2);
      if (arrayOfString.length >= 2) {
        break;
      }
      zzb.w("Could not parse component name from open GMSG: " + paramAdLauncherIntentInfoParcel.zzDi);
      return false;
      localIntent.setData(Uri.parse(paramAdLauncherIntentInfoParcel.url));
    }
    localIntent.setClassName(arrayOfString[0], arrayOfString[1]);
    label169:
    String str = paramAdLauncherIntentInfoParcel.zzDj;
    if (!TextUtils.isEmpty(str)) {}
    try
    {
      int j = Integer.parseInt(str);
      i = j;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      for (;;)
      {
        try
        {
          zzb.v("Launching an intent: " + localIntent.toURI());
          zzp.zzbI();
          zziq.zzb(paramContext, localIntent);
          return true;
        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
          int i;
          zzb.w(localActivityNotFoundException.getMessage());
        }
        localNumberFormatException = localNumberFormatException;
        zzb.w("Could not parse intent flags.");
        i = 0;
      }
    }
    localIntent.addFlags(i);
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.overlay.zza
 * JD-Core Version:    0.7.0.1
 */