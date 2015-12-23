package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.view.MotionEvent;

public final class zzao
{
  static final String[] zzoi = { "/aclk", "/pcs/click" };
  String zzoe = "googleads.g.doubleclick.net";
  String zzof = "/pagead/ads";
  private String zzog = "ad.doubleclick.net";
  String[] zzoh = { ".doubleclick.net", ".googleadservices.com", ".googlesyndication.com" };
  zzaj zzoj;
  
  public zzao(zzaj paramzzaj)
  {
    this.zzoj = paramzzaj;
  }
  
  private boolean zzb(Uri paramUri)
  {
    if (paramUri == null) {
      throw new NullPointerException();
    }
    try
    {
      boolean bool = paramUri.getHost().equals(this.zzog);
      return bool;
    }
    catch (NullPointerException localNullPointerException) {}
    return false;
  }
  
  public final void addTouchEvent(MotionEvent paramMotionEvent)
  {
    this.zzoj.addTouchEvent(paramMotionEvent);
  }
  
  public final boolean isGoogleAdUrl(Uri paramUri)
  {
    if (paramUri == null) {
      throw new NullPointerException();
    }
    try
    {
      String str = paramUri.getHost();
      String[] arrayOfString = this.zzoh;
      int i = arrayOfString.length;
      for (int j = 0;; j++)
      {
        boolean bool1 = false;
        if (j < i)
        {
          boolean bool2 = str.endsWith(arrayOfString[j]);
          if (bool2) {
            bool1 = true;
          }
        }
        else
        {
          return bool1;
        }
      }
      return false;
    }
    catch (NullPointerException localNullPointerException) {}
  }
  
  final Uri zza(Uri paramUri, Context paramContext, String paramString, boolean paramBoolean)
    throws zzap
  {
    boolean bool;
    try
    {
      bool = zzb(paramUri);
      if (bool)
      {
        if (!paramUri.toString().contains("dc_ms=")) {
          break label65;
        }
        throw new zzap("Parameter already exists: dc_ms");
      }
    }
    catch (UnsupportedOperationException localUnsupportedOperationException)
    {
      throw new zzap("Provided Uri is not in a valid state");
    }
    if (paramUri.getQueryParameter("ms") != null) {
      throw new zzap("Query parameter already exists: ms");
    }
    label65:
    String str1;
    if (paramBoolean) {
      str1 = this.zzoj.zzb(paramContext, paramString);
    }
    while (bool)
    {
      String str2 = paramUri.toString();
      int i = str2.indexOf(";adurl");
      if (i != -1)
      {
        return Uri.parse(str2.substring(0, i + 1) + "dc_ms" + "=" + str1 + ";" + str2.substring(i + 1));
        str1 = this.zzoj.zzb(paramContext);
      }
      else
      {
        String str3 = paramUri.getEncodedPath();
        int j = str2.indexOf(str3);
        return Uri.parse(str2.substring(0, j + str3.length()) + ";" + "dc_ms" + "=" + str1 + ";" + str2.substring(j + str3.length()));
      }
    }
    String str4 = paramUri.toString();
    int k = str4.indexOf("&adurl");
    if (k == -1) {
      k = str4.indexOf("?adurl");
    }
    if (k != -1) {
      return Uri.parse(str4.substring(0, k + 1) + "ms" + "=" + str1 + "&" + str4.substring(k + 1));
    }
    Uri localUri = paramUri.buildUpon().appendQueryParameter("ms", str1).build();
    return localUri;
  }
  
  public final boolean zza(Uri paramUri)
  {
    if (paramUri == null) {
      throw new NullPointerException();
    }
    try
    {
      boolean bool1 = paramUri.getHost().equals(this.zzoe);
      boolean bool2 = false;
      if (bool1)
      {
        boolean bool3 = paramUri.getPath().equals(this.zzof);
        bool2 = false;
        if (bool3) {
          bool2 = true;
        }
      }
      return bool2;
    }
    catch (NullPointerException localNullPointerException) {}
    return false;
  }
  
  public final Uri zzb(Uri paramUri, Context paramContext)
    throws zzap
  {
    try
    {
      Uri localUri = zza(paramUri, paramContext, paramUri.getQueryParameter("ai"), true);
      return localUri;
    }
    catch (UnsupportedOperationException localUnsupportedOperationException)
    {
      throw new zzap("Provided Uri is not in a valid state");
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzao
 * JD-Core Version:    0.7.0.1
 */