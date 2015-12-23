package com.google.android.vending.verifier.api;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.google.android.finsky.FinskyApp;
import com.google.protobuf.nano.MessageNano;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class BaseVerificationRequest<T, U extends MessageNano>
  extends Request<T>
{
  protected final U mRequest;
  
  public BaseVerificationRequest(String paramString, Response.ErrorListener paramErrorListener, U paramU)
  {
    super(1, paramString, paramErrorListener);
    this.mRequest = paramU;
  }
  
  private static String makeUserAgentString()
  {
    FinskyApp localFinskyApp = FinskyApp.get();
    String str1 = sanitizeHeaderValue(Build.DEVICE);
    String str2 = sanitizeHeaderValue(Build.HARDWARE);
    String str3 = sanitizeHeaderValue(Build.PRODUCT);
    String str4 = sanitizeHeaderValue(Build.TYPE);
    String str5 = sanitizeHeaderValue(Build.ID);
    try
    {
      PackageInfo localPackageInfo = localFinskyApp.getPackageManager().getPackageInfo(localFinskyApp.getPackageName(), 0);
      Locale localLocale = Locale.US;
      Object[] arrayOfObject = new Object[8];
      arrayOfObject[0] = localPackageInfo.versionName;
      arrayOfObject[1] = Integer.valueOf(localPackageInfo.versionCode);
      arrayOfObject[2] = Integer.valueOf(Build.VERSION.SDK_INT);
      arrayOfObject[3] = str1;
      arrayOfObject[4] = str2;
      arrayOfObject[5] = str3;
      arrayOfObject[6] = str5;
      arrayOfObject[7] = str4;
      return String.format(localLocale, "Android-Finsky/%s (versionCode=%d,sdk=%d,device=%s,hardware=%s,product=%s,build=%s:%s)", arrayOfObject);
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      throw new RuntimeException(localNameNotFoundException);
    }
  }
  
  private static String sanitizeHeaderValue(String paramString)
  {
    return Uri.encode(paramString).replace("(", "").replace(")", "");
  }
  
  public final byte[] getBody()
  {
    return MessageNano.toByteArray(this.mRequest);
  }
  
  public final String getBodyContentType()
  {
    return "application/x-protobuffer";
  }
  
  public final Map<String, String> getHeaders()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("User-Agent", makeUserAgentString());
    localHashMap.put("Connection", "close");
    return localHashMap;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.api.BaseVerificationRequest
 * JD-Core Version:    0.7.0.1
 */