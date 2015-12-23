package com.google.android.wallet.common.api.http;

import android.content.Context;
import android.net.Uri;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.google.moneta.orchestration.ui.common.ClientEnvironmentConfig.AndroidEnvironmentConfig;

public final class ApiContext
{
  public final Context applicationContext;
  public final Uri baseUrl;
  public final Uri eesBaseUrl;
  private final String mAuthTokenType;
  private final AndroidAuthenticator mAuthenticator;
  private String mLastAuthToken;
  
  public ApiContext(Context paramContext, ClientEnvironmentConfig.AndroidEnvironmentConfig paramAndroidEnvironmentConfig, AndroidAuthenticator paramAndroidAuthenticator)
  {
    this.applicationContext = paramContext;
    this.baseUrl = Uri.parse(paramAndroidEnvironmentConfig.serverBasePath);
    this.eesBaseUrl = Uri.parse(paramAndroidEnvironmentConfig.serverEesBasePath);
    this.mAuthTokenType = paramAndroidEnvironmentConfig.authTokenType;
    this.mAuthenticator = paramAndroidAuthenticator;
  }
  
  /* Error */
  public final java.util.Map<String, String> getHeaders()
    throws com.android.volley.AuthFailureError
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_0
    //   4: getfield 46	com/google/android/wallet/common/api/http/ApiContext:mAuthenticator	Lcom/android/volley/toolbox/AndroidAuthenticator;
    //   7: invokevirtual 56	com/android/volley/toolbox/AndroidAuthenticator:getAuthToken	()Ljava/lang/String;
    //   10: putfield 58	com/google/android/wallet/common/api/http/ApiContext:mLastAuthToken	Ljava/lang/String;
    //   13: new 60	android/support/v4/util/ArrayMap
    //   16: dup
    //   17: iconst_2
    //   18: invokespecial 63	android/support/v4/util/ArrayMap:<init>	(I)V
    //   21: astore_2
    //   22: aload_0
    //   23: getfield 44	com/google/android/wallet/common/api/http/ApiContext:mAuthTokenType	Ljava/lang/String;
    //   26: ldc 65
    //   28: invokevirtual 71	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   31: ifeq +47 -> 78
    //   34: aload_2
    //   35: ldc 73
    //   37: new 75	java/lang/StringBuilder
    //   40: dup
    //   41: ldc 77
    //   43: invokespecial 80	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   46: aload_0
    //   47: getfield 58	com/google/android/wallet/common/api/http/ApiContext:mLastAuthToken	Ljava/lang/String;
    //   50: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   53: invokevirtual 87	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   56: invokeinterface 93 3 0
    //   61: pop
    //   62: aload_2
    //   63: ldc 95
    //   65: getstatic 100	com/google/android/wallet/common/api/WalletRequestQueue:USER_AGENT_VALUE	Ljava/lang/String;
    //   68: invokeinterface 93 3 0
    //   73: pop
    //   74: aload_0
    //   75: monitorexit
    //   76: aload_2
    //   77: areturn
    //   78: aload_2
    //   79: ldc 73
    //   81: new 75	java/lang/StringBuilder
    //   84: dup
    //   85: ldc 102
    //   87: invokespecial 80	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   90: aload_0
    //   91: getfield 58	com/google/android/wallet/common/api/http/ApiContext:mLastAuthToken	Ljava/lang/String;
    //   94: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   97: invokevirtual 87	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   100: invokeinterface 93 3 0
    //   105: pop
    //   106: goto -44 -> 62
    //   109: astore_1
    //   110: aload_0
    //   111: monitorexit
    //   112: aload_1
    //   113: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	114	0	this	ApiContext
    //   109	4	1	localObject	Object
    //   21	58	2	localArrayMap	android.support.v4.util.ArrayMap
    // Exception table:
    //   from	to	target	type
    //   2	62	109	finally
    //   62	74	109	finally
    //   78	106	109	finally
  }
  
  public final void invalidateAuthToken()
  {
    try
    {
      if (this.mLastAuthToken != null)
      {
        this.mAuthenticator.invalidateAuthToken(this.mLastAuthToken);
        this.mLastAuthToken = null;
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.api.http.ApiContext
 * JD-Core Version:    0.7.0.1
 */