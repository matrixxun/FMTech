package com.google.android.vending.remoting.api;

import android.accounts.Account;
import android.content.Context;
import android.net.Uri;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.google.android.finsky.config.G;
import com.google.android.finsky.protos.VendingProtos.RequestPropertiesProto;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.volley.UrlTools;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class VendingApiContext
{
  public final AndroidAuthenticator mAuthenticator;
  private final Context mContext;
  private final Map<String, String> mHeaders = new HashMap();
  private String mLastAuthToken;
  private String mLastSecureAuthToken;
  public boolean mReauthenticate = false;
  private VendingProtos.RequestPropertiesProto mRequestProperties;
  private final AndroidAuthenticator mSecureAuthenticator;
  
  public VendingApiContext(Context paramContext, Account paramAccount, Locale paramLocale, String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10)
  {
    this.mContext = paramContext;
    addHeader("User-Agent", paramString10);
    this.mAuthenticator = new AndroidAuthenticator(paramContext, paramAccount, (String)G.vendingAuthTokenType.get());
    this.mSecureAuthenticator = new AndroidAuthenticator(paramContext, paramAccount, (String)G.vendingSecureAuthTokenType.get());
    this.mRequestProperties = new VendingProtos.RequestPropertiesProto();
    this.mRequestProperties.aid = paramString1;
    this.mRequestProperties.hasAid = true;
    this.mRequestProperties.userCountry = paramLocale.getCountry();
    this.mRequestProperties.hasUserCountry = true;
    this.mRequestProperties.userLanguage = paramLocale.getLanguage();
    this.mRequestProperties.hasUserLanguage = true;
    this.mRequestProperties.softwareVersion = paramInt;
    this.mRequestProperties.hasSoftwareVersion = true;
    if (paramString2 != null)
    {
      this.mRequestProperties.operatorName = paramString2;
      this.mRequestProperties.hasOperatorName = true;
    }
    if (paramString3 != null)
    {
      this.mRequestProperties.simOperatorName = paramString3;
      this.mRequestProperties.hasSimOperatorName = true;
    }
    if (paramString4 != null)
    {
      this.mRequestProperties.operatorNumericName = paramString4;
      this.mRequestProperties.hasOperatorNumericName = true;
    }
    if (paramString5 != null)
    {
      this.mRequestProperties.simOperatorNumericName = paramString5;
      this.mRequestProperties.hasSimOperatorNumericName = true;
    }
    this.mRequestProperties.productNameAndVersion = (paramString6 + ":" + paramString7);
    this.mRequestProperties.hasProductNameAndVersion = true;
    this.mRequestProperties.clientId = paramString8;
    this.mRequestProperties.hasClientId = true;
    this.mRequestProperties.loggingId = paramString9;
    this.mRequestProperties.hasLoggingId = true;
    String str = UrlTools.rewrite(this.mContext, "https://android.clients.google.com/vending/api/ApiRequest");
    if (str == null) {
      throw new RuntimeException("URL blocked: " + "https://android.clients.google.com/vending/api/ApiRequest");
    }
    Utils.checkUrlIsSecure(str);
  }
  
  public static String makeUserAgentString(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, boolean paramBoolean)
  {
    String str1 = sanitizeHeaderValue(paramString2);
    String str2 = sanitizeHeaderValue(paramString3);
    String str3 = sanitizeHeaderValue(paramString4);
    String str4 = sanitizeHeaderValue(paramString5);
    String str5 = sanitizeHeaderValue(paramString6);
    String str6 = sanitizeHeaderValue(paramString7);
    if (paramBoolean) {}
    for (int i = 1;; i = 0)
    {
      Locale localLocale = Locale.US;
      Object[] arrayOfObject = new Object[11];
      arrayOfObject[0] = paramString1;
      arrayOfObject[1] = "2";
      arrayOfObject[2] = Integer.valueOf(paramInt1);
      arrayOfObject[3] = Integer.valueOf(paramInt2);
      arrayOfObject[4] = str1;
      arrayOfObject[5] = str2;
      arrayOfObject[6] = str3;
      arrayOfObject[7] = str4;
      arrayOfObject[8] = str5;
      arrayOfObject[9] = str6;
      arrayOfObject[10] = Integer.valueOf(i);
      return String.format(localLocale, "Android-Finsky/%s (api=%s,versionCode=%d,sdk=%d,device=%s,hardware=%s,product=%s,platformVersionRelease=%s,model=%s,buildId=%s,isWideScreen=%d)", arrayOfObject);
    }
  }
  
  private static String sanitizeHeaderValue(String paramString)
  {
    return Uri.encode(paramString).replace("(", "").replace(")", "");
  }
  
  public final void addHeader(String paramString1, String paramString2)
  {
    try
    {
      this.mHeaders.put(paramString1, paramString2);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final Map<String, String> getHeaders()
  {
    try
    {
      Map localMap = this.mHeaders;
      return localMap;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  public final VendingProtos.RequestPropertiesProto getRequestProperties(boolean paramBoolean)
    throws com.android.volley.AuthFailureError
  {
    // Byte code:
    //   0: new 67	com/google/android/finsky/protos/VendingProtos$RequestPropertiesProto
    //   3: dup
    //   4: invokespecial 68	com/google/android/finsky/protos/VendingProtos$RequestPropertiesProto:<init>	()V
    //   7: astore_2
    //   8: aload_0
    //   9: getfield 70	com/google/android/vending/remoting/api/VendingApiContext:mRequestProperties	Lcom/google/android/finsky/protos/VendingProtos$RequestPropertiesProto;
    //   12: invokestatic 238	com/google/protobuf/nano/MessageNano:toByteArray	(Lcom/google/protobuf/nano/MessageNano;)[B
    //   15: astore 4
    //   17: aload_2
    //   18: aload 4
    //   20: aload 4
    //   22: arraylength
    //   23: invokestatic 242	com/google/protobuf/nano/MessageNano:mergeFrom$1ec43da	(Lcom/google/protobuf/nano/MessageNano;[BI)Lcom/google/protobuf/nano/MessageNano;
    //   26: pop
    //   27: iload_1
    //   28: ifeq +66 -> 94
    //   31: aload_0
    //   32: getfield 26	com/google/android/vending/remoting/api/VendingApiContext:mReauthenticate	Z
    //   35: ifeq +19 -> 54
    //   38: aload_0
    //   39: getfield 65	com/google/android/vending/remoting/api/VendingApiContext:mSecureAuthenticator	Lcom/android/volley/toolbox/AndroidAuthenticator;
    //   42: aload_0
    //   43: getfield 244	com/google/android/vending/remoting/api/VendingApiContext:mLastAuthToken	Ljava/lang/String;
    //   46: invokevirtual 247	com/android/volley/toolbox/AndroidAuthenticator:invalidateAuthToken	(Ljava/lang/String;)V
    //   49: aload_0
    //   50: iconst_0
    //   51: putfield 26	com/google/android/vending/remoting/api/VendingApiContext:mReauthenticate	Z
    //   54: aload_0
    //   55: aload_0
    //   56: getfield 65	com/google/android/vending/remoting/api/VendingApiContext:mSecureAuthenticator	Lcom/android/volley/toolbox/AndroidAuthenticator;
    //   59: invokevirtual 250	com/android/volley/toolbox/AndroidAuthenticator:getAuthToken	()Ljava/lang/String;
    //   62: putfield 252	com/google/android/vending/remoting/api/VendingApiContext:mLastSecureAuthToken	Ljava/lang/String;
    //   65: aload_0
    //   66: getfield 252	com/google/android/vending/remoting/api/VendingApiContext:mLastSecureAuthToken	Ljava/lang/String;
    //   69: astore 6
    //   71: aload_2
    //   72: aload 6
    //   74: putfield 255	com/google/android/finsky/protos/VendingProtos$RequestPropertiesProto:userAuthToken	Ljava/lang/String;
    //   77: aload_2
    //   78: iconst_1
    //   79: putfield 258	com/google/android/finsky/protos/VendingProtos$RequestPropertiesProto:hasUserAuthToken	Z
    //   82: aload_2
    //   83: iload_1
    //   84: putfield 261	com/google/android/finsky/protos/VendingProtos$RequestPropertiesProto:userAuthTokenSecure	Z
    //   87: aload_2
    //   88: iconst_1
    //   89: putfield 264	com/google/android/finsky/protos/VendingProtos$RequestPropertiesProto:hasUserAuthTokenSecure	Z
    //   92: aload_2
    //   93: areturn
    //   94: aload_0
    //   95: getfield 26	com/google/android/vending/remoting/api/VendingApiContext:mReauthenticate	Z
    //   98: ifeq +19 -> 117
    //   101: aload_0
    //   102: getfield 60	com/google/android/vending/remoting/api/VendingApiContext:mAuthenticator	Lcom/android/volley/toolbox/AndroidAuthenticator;
    //   105: aload_0
    //   106: getfield 244	com/google/android/vending/remoting/api/VendingApiContext:mLastAuthToken	Ljava/lang/String;
    //   109: invokevirtual 247	com/android/volley/toolbox/AndroidAuthenticator:invalidateAuthToken	(Ljava/lang/String;)V
    //   112: aload_0
    //   113: iconst_0
    //   114: putfield 26	com/google/android/vending/remoting/api/VendingApiContext:mReauthenticate	Z
    //   117: aload_0
    //   118: aload_0
    //   119: getfield 60	com/google/android/vending/remoting/api/VendingApiContext:mAuthenticator	Lcom/android/volley/toolbox/AndroidAuthenticator;
    //   122: invokevirtual 250	com/android/volley/toolbox/AndroidAuthenticator:getAuthToken	()Ljava/lang/String;
    //   125: putfield 244	com/google/android/vending/remoting/api/VendingApiContext:mLastAuthToken	Ljava/lang/String;
    //   128: aload_0
    //   129: getfield 244	com/google/android/vending/remoting/api/VendingApiContext:mLastAuthToken	Ljava/lang/String;
    //   132: astore 6
    //   134: goto -63 -> 71
    //   137: astore_3
    //   138: new 266	java/lang/IllegalStateException
    //   141: dup
    //   142: ldc_w 268
    //   145: invokespecial 269	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   148: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	149	0	this	VendingApiContext
    //   0	149	1	paramBoolean	boolean
    //   7	86	2	localRequestPropertiesProto	VendingProtos.RequestPropertiesProto
    //   137	1	3	localInvalidProtocolBufferNanoException	com.google.protobuf.nano.InvalidProtocolBufferNanoException
    //   15	6	4	arrayOfByte	byte[]
    //   69	64	6	str	String
    // Exception table:
    //   from	to	target	type
    //   8	27	137	com/google/protobuf/nano/InvalidProtocolBufferNanoException
    //   31	54	137	com/google/protobuf/nano/InvalidProtocolBufferNanoException
    //   54	71	137	com/google/protobuf/nano/InvalidProtocolBufferNanoException
    //   71	92	137	com/google/protobuf/nano/InvalidProtocolBufferNanoException
    //   94	117	137	com/google/protobuf/nano/InvalidProtocolBufferNanoException
    //   117	134	137	com/google/protobuf/nano/InvalidProtocolBufferNanoException
  }
  
  public final void invalidateAuthToken(boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (String str = this.mLastSecureAuthToken;; str = this.mLastAuthToken)
    {
      if (str != null) {
        this.mAuthenticator.invalidateAuthToken(str);
      }
      if (!paramBoolean) {
        break;
      }
      this.mLastSecureAuthToken = null;
      return;
    }
    this.mLastAuthToken = null;
  }
  
  public final String peekHeader(String paramString)
  {
    try
    {
      String str = (String)this.mHeaders.get(paramString);
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.remoting.api.VendingApiContext
 * JD-Core Version:    0.7.0.1
 */