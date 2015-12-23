package com.google.android.finsky.billing;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.carrierbilling.CarrierBillingUtils;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.protos.VendingProtos.PurchaseMetadataResponseProto.Countries.Country;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public final class BillingLocator
{
  private static boolean isInitialized = false;
  private static String mSimIdentifier;
  private static Context sApplicationContext;
  
  public static List<VendingProtos.PurchaseMetadataResponseProto.Countries.Country> getBillingCountries()
  {
    Utils.ensureOnMainThread();
    ArrayList localArrayList = new ArrayList();
    String str1 = (String)BillingPreferences.BILLING_COUNTRIES.get();
    if (str1 == null)
    {
      localArrayList = null;
      return localArrayList;
    }
    String[] arrayOfString1 = str1.split("\\}\\{");
    int i = arrayOfString1.length;
    int j = 0;
    label42:
    String str2;
    if (j < i)
    {
      str2 = arrayOfString1[j];
      if (str2.length() != 0) {
        break label77;
      }
      FinskyLog.w("Got empty billing country string.", new Object[0]);
    }
    for (;;)
    {
      j++;
      break label42;
      break;
      label77:
      if (str2.charAt(0) == '{') {
        str2 = str2.substring(1);
      }
      if (str2.charAt(-1 + str2.length()) == '}') {
        str2 = str2.substring(0, -1 + str2.length());
      }
      String[] arrayOfString2 = str2.split(",");
      if (arrayOfString2.length < 2)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = str2;
        arrayOfObject[1] = Integer.valueOf(arrayOfString2.length);
        FinskyLog.w("Invalid country string: %s. Expected at least 2 parts, got %d.", arrayOfObject);
      }
      else
      {
        VendingProtos.PurchaseMetadataResponseProto.Countries.Country localCountry = new VendingProtos.PurchaseMetadataResponseProto.Countries.Country();
        localCountry.countryCode = arrayOfString2[0];
        localCountry.hasCountryCode = true;
        localCountry.countryName = arrayOfString2[1];
        localCountry.hasCountryName = true;
        if (arrayOfString2.length >= 3)
        {
          if ((!arrayOfString2[2].equals("1")) && (!arrayOfString2[2].equals("0")))
          {
            FinskyLog.w("Invalid reducedBillingAddress flag: " + arrayOfString2[2], new Object[0]);
          }
          else
          {
            localCountry.allowsReducedBillingAddress = arrayOfString2[2].equals("1");
            localCountry.hasAllowsReducedBillingAddress = true;
          }
        }
        else {
          localArrayList.add(localCountry);
        }
      }
    }
  }
  
  public static String getDeviceIdFromTelephony()
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)sApplicationContext.getSystemService("phone");
    if (localTelephonyManager == null) {
      return null;
    }
    try
    {
      String str = localTelephonyManager.getDeviceId();
      return str;
    }
    catch (SecurityException localSecurityException)
    {
      FinskyLog.d("Cannot read device Id: %s", new Object[] { localSecurityException });
    }
    return null;
  }
  
  public static String getLine1NumberFromTelephony()
  {
    
    if (sApplicationContext == null) {
      throw new IllegalStateException("BillingLocator has not been initialized.");
    }
    TelephonyManager localTelephonyManager = (TelephonyManager)sApplicationContext.getSystemService("phone");
    try
    {
      String str2 = localTelephonyManager.getLine1Number();
      str1 = str2;
    }
    catch (SecurityException localSecurityException)
    {
      for (;;)
      {
        FinskyLog.d("Cannot read Line 1 Number: %s", new Object[] { localSecurityException });
        String str1 = null;
      }
    }
    if (str1 == null) {
      str1 = "";
    }
    return str1;
  }
  
  public static String getSimIdentifier()
  {
    return mSimIdentifier;
  }
  
  public static String getSubscriberIdFromTelephony()
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)sApplicationContext.getSystemService("phone");
    if (localTelephonyManager == null) {
      return null;
    }
    try
    {
      String str = localTelephonyManager.getSubscriberId();
      return str;
    }
    catch (SecurityException localSecurityException)
    {
      FinskyLog.d("Cannot read subscriber Id: %s", new Object[] { localSecurityException });
    }
    return null;
  }
  
  public static void initSingleton()
  {
    if (!isInitialized)
    {
      isInitialized = true;
      sApplicationContext = FinskyApp.get();
      mSimIdentifier = CarrierBillingUtils.getSimIdentifier();
      return;
    }
    throw new IllegalStateException("BillingLocator already initialized.");
  }
  
  public static void setBillingCountries(VendingProtos.PurchaseMetadataResponseProto.Countries.Country[] paramArrayOfCountry)
  {
    Utils.ensureOnMainThread();
    StringBuilder localStringBuilder = new StringBuilder();
    int i = paramArrayOfCountry.length;
    int j = 0;
    if (j < i)
    {
      VendingProtos.PurchaseMetadataResponseProto.Countries.Country localCountry = paramArrayOfCountry[j];
      localStringBuilder.append('{').append(localCountry.countryCode).append(',');
      localStringBuilder.append(localCountry.countryName).append(',');
      if (localCountry.allowsReducedBillingAddress) {}
      for (char c = '1';; c = '0')
      {
        localStringBuilder.append(c).append('}');
        j++;
        break;
      }
    }
    BillingPreferences.BILLING_COUNTRIES.put(localStringBuilder.toString());
    BillingPreferences.LAST_BILLING_COUNTRIES_REFRESH_MILLIS.put(Long.valueOf(System.currentTimeMillis()));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.BillingLocator
 * JD-Core Version:    0.7.0.1
 */