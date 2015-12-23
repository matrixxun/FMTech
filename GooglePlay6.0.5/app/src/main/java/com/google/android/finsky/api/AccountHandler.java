package com.google.android.finsky.api;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.utils.Utils;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.util.Locale;

public final class AccountHandler
{
  private static final String SIDEWINDER_INTERIM_ACCOUNT_TYPE = Utils.getSysProperty("finsky.sw_account_type");
  private static Boolean sIsSidewinderDevice;
  private static String[] sSupportedAccountTypes;
  
  public static Account findAccount(String paramString, Context paramContext)
  {
    Account localAccount;
    if (TextUtils.isEmpty(paramString))
    {
      localAccount = null;
      return localAccount;
    }
    Account[] arrayOfAccount = getAccounts(paramContext);
    int i = arrayOfAccount.length;
    for (int j = 0;; j++)
    {
      if (j >= i) {
        break label54;
      }
      localAccount = arrayOfAccount[j];
      if (localAccount.name.equalsIgnoreCase(paramString)) {
        break;
      }
    }
    label54:
    return null;
  }
  
  public static Account getAccountFromPreferences(Context paramContext, PreferenceFile.SharedPreference<String> paramSharedPreference)
  {
    Account localAccount1 = findAccount((String)paramSharedPreference.get(), paramContext);
    if (localAccount1 != null) {
      return localAccount1;
    }
    Account localAccount2 = getFirstAccount(paramContext);
    saveAccountToPreferences(localAccount2, paramSharedPreference);
    return localAccount2;
  }
  
  /* Error */
  public static String[] getAccountTypes()
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 67	com/google/android/finsky/api/AccountHandler:sSupportedAccountTypes	[Ljava/lang/String;
    //   6: ifnonnull +39 -> 45
    //   9: invokestatic 71	com/google/android/finsky/api/AccountHandler:isSidewinderDevice	()Z
    //   12: ifeq +42 -> 54
    //   15: iconst_3
    //   16: anewarray 41	java/lang/String
    //   19: astore 5
    //   21: aload 5
    //   23: iconst_0
    //   24: getstatic 22	com/google/android/finsky/api/AccountHandler:SIDEWINDER_INTERIM_ACCOUNT_TYPE	Ljava/lang/String;
    //   27: aastore
    //   28: aload 5
    //   30: iconst_1
    //   31: ldc 73
    //   33: aastore
    //   34: aload 5
    //   36: iconst_2
    //   37: ldc 75
    //   39: aastore
    //   40: aload 5
    //   42: putstatic 67	com/google/android/finsky/api/AccountHandler:sSupportedAccountTypes	[Ljava/lang/String;
    //   45: getstatic 67	com/google/android/finsky/api/AccountHandler:sSupportedAccountTypes	[Ljava/lang/String;
    //   48: astore_1
    //   49: ldc 2
    //   51: monitorexit
    //   52: aload_1
    //   53: areturn
    //   54: getstatic 81	com/google/android/finsky/config/G:supportedAccountTypes	Lcom/google/android/play/utils/config/GservicesValue;
    //   57: invokevirtual 84	com/google/android/play/utils/config/GservicesValue:get	()Ljava/lang/Object;
    //   60: checkcast 41	java/lang/String
    //   63: invokestatic 88	com/google/android/finsky/utils/Utils:commaUnpackStrings	(Ljava/lang/String;)[Ljava/lang/String;
    //   66: astore_2
    //   67: aload_2
    //   68: arraylength
    //   69: istore_3
    //   70: iload_3
    //   71: iconst_1
    //   72: iadd
    //   73: anewarray 41	java/lang/String
    //   76: astore 4
    //   78: aload 4
    //   80: putstatic 67	com/google/android/finsky/api/AccountHandler:sSupportedAccountTypes	[Ljava/lang/String;
    //   83: aload 4
    //   85: iconst_0
    //   86: ldc 75
    //   88: aastore
    //   89: aload_2
    //   90: iconst_0
    //   91: getstatic 67	com/google/android/finsky/api/AccountHandler:sSupportedAccountTypes	[Ljava/lang/String;
    //   94: iconst_1
    //   95: iload_3
    //   96: invokestatic 94	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   99: goto -54 -> 45
    //   102: astore_0
    //   103: ldc 2
    //   105: monitorexit
    //   106: aload_0
    //   107: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   102	5	0	localObject	Object
    //   48	5	1	arrayOfString1	String[]
    //   66	24	2	arrayOfString2	String[]
    //   69	27	3	i	int
    //   76	8	4	arrayOfString3	String[]
    //   19	22	5	arrayOfString4	String[]
    // Exception table:
    //   from	to	target	type
    //   3	45	102	finally
    //   45	49	102	finally
    //   54	99	102	finally
  }
  
  public static Account[] getAccounts(Context paramContext)
  {
    int i = 0;
    Account[] arrayOfAccount1 = AccountManager.get(paramContext).getAccounts();
    String[] arrayOfString = getAccountTypes();
    boolean bool1 = isSidewinderDevice();
    int j = arrayOfAccount1.length;
    int k = 0;
    int i6;
    if (k < arrayOfAccount1.length)
    {
      int i5 = arrayOfString.length;
      i6 = 0;
      label40:
      if (i6 >= i5) {
        break label355;
      }
      String str = arrayOfString[i6];
      if (!arrayOfAccount1[k].type.equals(str)) {}
    }
    label142:
    label349:
    label355:
    for (int i7 = 1;; i7 = 0)
    {
      Account localAccount4;
      if (i7 != 0)
      {
        localAccount4 = arrayOfAccount1[k];
        if ((bool1) && (!"cn.google".equals(localAccount4.type)) && (!SIDEWINDER_INTERIM_ACCOUNT_TYPE.equals(localAccount4.type))) {
          break label142;
        }
      }
      for (boolean bool2 = true;; bool2 = localAccount4.name.toLowerCase(Locale.ENGLISH).endsWith("@google.com"))
      {
        if (!bool2)
        {
          arrayOfAccount1[k] = null;
          j--;
        }
        k++;
        break;
        i6++;
        break label40;
      }
      if (j > 1) {
        for (int i2 = 0; i2 < arrayOfAccount1.length; i2++)
        {
          Account localAccount2 = arrayOfAccount1[i2];
          if ((localAccount2 != null) && (localAccount2.type.equals("com.google.work")))
          {
            int i3 = j;
            for (int i4 = 0; i4 < arrayOfAccount1.length; i4++) {
              if (i2 != i4)
              {
                Account localAccount3 = arrayOfAccount1[i4];
                if ((localAccount3 != null) && (localAccount2.name.equalsIgnoreCase(localAccount3.name)))
                {
                  arrayOfAccount1[i4] = null;
                  i3--;
                }
              }
            }
            j = i3;
          }
        }
      }
      if (j == arrayOfAccount1.length) {
        return arrayOfAccount1;
      }
      if (j == 0) {
        return new Account[0];
      }
      Account[] arrayOfAccount2 = new Account[j];
      int m = arrayOfAccount1.length;
      int n = 0;
      int i1;
      if (n < m)
      {
        Account localAccount1 = arrayOfAccount1[n];
        if (localAccount1 == null) {
          break label349;
        }
        i1 = i + 1;
        arrayOfAccount2[i] = localAccount1;
      }
      for (;;)
      {
        n++;
        i = i1;
        break;
        return arrayOfAccount2;
        i1 = i;
      }
    }
  }
  
  public static Account getFirstAccount(Context paramContext)
  {
    Account[] arrayOfAccount = getAccounts(paramContext);
    if (arrayOfAccount.length > 0) {
      return arrayOfAccount[0];
    }
    return null;
  }
  
  public static boolean hasAccount(String paramString, Context paramContext)
  {
    return findAccount(paramString, paramContext) != null;
  }
  
  public static boolean isAvengerDevice(Context paramContext)
  {
    return AccountManager.get(paramContext).getAccountsByType("com.google.work").length > 0;
  }
  
  private static boolean isSidewinderDevice()
  {
    try
    {
      if (sIsSidewinderDevice == null) {
        sIsSidewinderDevice = Boolean.valueOf(GooglePlayServicesUtil.isSidewinderDevice(FinskyApp.get()));
      }
      boolean bool = sIsSidewinderDevice.booleanValue();
      return bool;
    }
    finally {}
  }
  
  public static void saveAccountToPreferences(Account paramAccount, PreferenceFile.SharedPreference<String> paramSharedPreference)
  {
    if (paramAccount == null) {}
    while ((isSidewinderDevice()) && (!"cn.google".equals(paramAccount.type)) && (!SIDEWINDER_INTERIM_ACCOUNT_TYPE.equals(paramAccount.type))) {
      return;
    }
    paramSharedPreference.put(paramAccount.name);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.AccountHandler
 * JD-Core Version:    0.7.0.1
 */