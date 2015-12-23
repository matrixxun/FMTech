package com.google.android.gms.common.oob;

import android.content.Intent;
import com.google.android.gms.common.internal.zzj;

public final class SignUp
{
  public static final String[] GOOGLE_PLUS_REQUIRED_FEATURES = zzj.GOOGLE_PLUS_REQUIRED_FEATURES;
  
  public static Intent newSignUpIntent$d24fdd6(String paramString1, String paramString2)
  {
    Intent localIntent = new Intent();
    localIntent.setPackage("com.google.android.gms");
    localIntent.setAction("com.google.android.gms.common.oob.OOB_SIGN_UP");
    localIntent.putExtra("com.google.android.gms.common.oob.EXTRA_ACCOUNT_NAME", paramString1);
    localIntent.putExtra("com.google.android.gms.common.oob.EXTRA_BACK_BUTTON_NAME", null);
    localIntent.putExtra("com.google.android.gms.common.oob.EXTRAS_PROMO_APP_PACKAGE", null);
    localIntent.putExtra("com.google.android.gms.common.oob.EXTRAS_PROMO_APP_TEXT", paramString2);
    return localIntent;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.oob.SignUp
 * JD-Core Version:    0.7.0.1
 */