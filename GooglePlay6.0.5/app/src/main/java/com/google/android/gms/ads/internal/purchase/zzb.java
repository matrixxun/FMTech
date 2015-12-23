package com.google.android.gms.ads.internal.purchase;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.internal.zzhb;
import java.lang.reflect.Method;

@zzhb
public final class zzb
{
  final Context mContext;
  Object zzEV;
  final boolean zzEW;
  
  public zzb(Context paramContext)
  {
    this(paramContext, (byte)0);
  }
  
  private zzb(Context paramContext, byte paramByte)
  {
    this.mContext = paramContext;
    this.zzEW = true;
  }
  
  public final Bundle zzb(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      Class localClass = this.mContext.getClassLoader().loadClass("com.android.vending.billing.IInAppBillingService");
      Class[] arrayOfClass = new Class[5];
      arrayOfClass[0] = Integer.TYPE;
      arrayOfClass[1] = String.class;
      arrayOfClass[2] = String.class;
      arrayOfClass[3] = String.class;
      arrayOfClass[4] = String.class;
      Method localMethod = localClass.getDeclaredMethod("getBuyIntent", arrayOfClass);
      Object localObject = localClass.cast(this.zzEV);
      Object[] arrayOfObject = new Object[5];
      arrayOfObject[0] = Integer.valueOf(3);
      arrayOfObject[1] = paramString1;
      arrayOfObject[2] = paramString2;
      arrayOfObject[3] = "inapp";
      arrayOfObject[4] = paramString3;
      Bundle localBundle = (Bundle)localMethod.invoke(localObject, arrayOfObject);
      return localBundle;
    }
    catch (Exception localException)
    {
      if (this.zzEW) {
        com.google.android.gms.ads.internal.util.client.zzb.w("IInAppBillingService is not available, please add com.android.vending.billing.IInAppBillingService to project.", localException);
      }
    }
    return null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.purchase.zzb
 * JD-Core Version:    0.7.0.1
 */