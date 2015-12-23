package com.google.android.gms.ocr;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.zzn;
import com.google.android.gms.common.internal.zzx;

public final class CreditCardOcrIntentBuilder
{
  private final Context mContext;
  private final Intent mIntent;
  
  public CreditCardOcrIntentBuilder(Context paramContext)
  {
    this.mContext = paramContext;
    this.mIntent = new Intent("com.google.android.gms.ocr.ACTION_CREDIT_CARD_OCR");
    this.mIntent.setPackage("com.google.android.gms");
  }
  
  public final Intent build()
  {
    if (this.mContext.getPackageManager().checkPermission("android.permission.CAMERA", "com.google.android.gms") != 0) {
      return null;
    }
    if (zzn.zza(this.mContext.getPackageManager(), this.mIntent))
    {
      GoogleApiAvailability.getInstance();
      int i = GoogleApiAvailability.isGooglePlayServicesAvailable(this.mContext);
      if (i != 0)
      {
        Log.w("CreditCardOcrIntentBuilder", "Google Play services is unavailable. Result=" + i);
        return null;
      }
      return this.mIntent;
    }
    Log.d("CreditCardOcrIntentBuilder", "Google Play services OCR activity is disabled or not available");
    return null;
  }
  
  public final CreditCardOcrIntentBuilder setTheme(int paramInt)
  {
    if ((paramInt == 0) || (paramInt == 1)) {}
    for (boolean bool = true;; bool = false)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      zzx.zzb(bool, "Unexpected value for theme=%d", arrayOfObject);
      this.mIntent.putExtra("com.google.android.gms.ocr.THEME", paramInt);
      return this;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ocr.CreditCardOcrIntentBuilder
 * JD-Core Version:    0.7.0.1
 */