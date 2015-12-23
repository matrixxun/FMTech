package com.google.android.wallet.common.address;

import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.Request.Priority;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.wallet.common.api.WalletRequestQueue;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public final class CountryMetadataRetrievalTask
  implements Response.Listener<JSONObject>
{
  private static final Uri PUBLIC_ADDRESS_DATA_SERVER = Uri.parse("https://payments.google.com/payments/data/address");
  private final String mDesiredLanguageCode;
  private final Response.ErrorListener mErrorListener;
  private final int mRegionCode;
  private final RequestQueue mRequestQueue;
  private final Response.Listener<JSONObject> mResponseListener;
  
  public CountryMetadataRetrievalTask(RequestQueue paramRequestQueue, int paramInt, String paramString, Response.Listener<JSONObject> paramListener, Response.ErrorListener paramErrorListener)
  {
    this.mRequestQueue = paramRequestQueue;
    this.mRegionCode = paramInt;
    this.mDesiredLanguageCode = paramString;
    this.mResponseListener = paramListener;
    this.mErrorListener = paramErrorListener;
  }
  
  private static String buildCountryId(int paramInt, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder("data/").append(RegionCode.toCountryCode(paramInt));
    if (!TextUtils.isEmpty(paramString)) {
      localStringBuilder.append("--").append(paramString);
    }
    return localStringBuilder.toString();
  }
  
  public final void run(String paramString)
  {
    CountryMetadataRetrievalRequest localCountryMetadataRetrievalRequest = new CountryMetadataRetrievalRequest(this.mRegionCode, paramString, this, this.mErrorListener);
    this.mRequestQueue.add(localCountryMetadataRetrievalRequest);
  }
  
  public static final class AdminAreaMetadataRetrievalRequest
    extends JsonObjectRequest
  {
    public AdminAreaMetadataRetrievalRequest(int paramInt, String paramString, Response.Listener<JSONObject> paramListener, Response.ErrorListener paramErrorListener)
    {
      super(CountryMetadataRetrievalTask.access$000(paramInt, null).buildUpon().appendPath(paramString).toString(), null, paramListener, paramErrorListener);
    }
    
    public final Map<String, String> getHeaders()
      throws AuthFailureError
    {
      return Collections.singletonMap("User-Agent", WalletRequestQueue.USER_AGENT_VALUE);
    }
    
    public final Request.Priority getPriority()
    {
      return Request.Priority.HIGH;
    }
  }
  
  public static final class CountryMetadataRetrievalRequest
    extends JsonObjectRequest
  {
    private static final HashMap<String, String> COUNTRY_DATA_DEFAULT;
    private final String mId;
    
    static
    {
      HashMap localHashMap = new HashMap();
      COUNTRY_DATA_DEFAULT = localHashMap;
      localHashMap.put("upper", "C");
      COUNTRY_DATA_DEFAULT.put("zip_name_type", "postal");
      COUNTRY_DATA_DEFAULT.put("fmt", "%N%n%O%n%A%n%C");
      COUNTRY_DATA_DEFAULT.put("require", "AC");
      COUNTRY_DATA_DEFAULT.put("state_name_type", "province");
      COUNTRY_DATA_DEFAULT.put("id", "data/ZZ");
      COUNTRY_DATA_DEFAULT.put("dir", "ltr");
    }
    
    public CountryMetadataRetrievalRequest(int paramInt, String paramString, Response.Listener<JSONObject> paramListener, Response.ErrorListener paramErrorListener)
    {
      super(CountryMetadataRetrievalTask.access$000(paramInt, paramString).toString(), null, paramListener, paramErrorListener);
      this.mId = CountryMetadataRetrievalTask.buildCountryId(paramInt, paramString);
    }
    
    public final Map<String, String> getHeaders()
      throws AuthFailureError
    {
      return Collections.singletonMap("User-Agent", WalletRequestQueue.USER_AGENT_VALUE);
    }
    
    public final Request.Priority getPriority()
    {
      return Request.Priority.HIGH;
    }
    
    /* Error */
    protected final com.android.volley.Response<JSONObject> parseNetworkResponse(com.android.volley.NetworkResponse paramNetworkResponse)
    {
      // Byte code:
      //   0: aload_0
      //   1: aload_1
      //   2: invokespecial 103	com/android/volley/toolbox/JsonObjectRequest:parseNetworkResponse	(Lcom/android/volley/NetworkResponse;)Lcom/android/volley/Response;
      //   5: astore_2
      //   6: aload_2
      //   7: invokevirtual 109	com/android/volley/Response:isSuccess	()Z
      //   10: ifne +5 -> 15
      //   13: aload_2
      //   14: areturn
      //   15: aload_2
      //   16: getfield 113	com/android/volley/Response:result	Ljava/lang/Object;
      //   19: checkcast 115	org/json/JSONObject
      //   22: astore_3
      //   23: aload_3
      //   24: ldc 44
      //   26: invokestatic 121	com/google/android/wallet/common/address/AddressUtils:getAddressData	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
      //   29: astore 4
      //   31: aload 4
      //   33: invokestatic 127	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   36: ifeq +18 -> 54
      //   39: aload_0
      //   40: getfield 72	com/google/android/wallet/common/address/CountryMetadataRetrievalTask$CountryMetadataRetrievalRequest:mId	Ljava/lang/String;
      //   43: astore 4
      //   45: aload_3
      //   46: ldc 44
      //   48: aload 4
      //   50: invokevirtual 130	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      //   53: pop
      //   54: aload_3
      //   55: ldc 132
      //   57: invokestatic 121	com/google/android/wallet/common/address/AddressUtils:getAddressData	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
      //   60: invokestatic 127	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   63: ifeq +36 -> 99
      //   66: aload 4
      //   68: invokestatic 127	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   71: ifne +28 -> 99
      //   74: aload 4
      //   76: iconst_1
      //   77: aload 4
      //   79: ldc 134
      //   81: invokevirtual 140	java/lang/String:lastIndexOf	(Ljava/lang/String;)I
      //   84: iadd
      //   85: invokevirtual 144	java/lang/String:substring	(I)Ljava/lang/String;
      //   88: astore 9
      //   90: aload_3
      //   91: ldc 132
      //   93: aload 9
      //   95: invokevirtual 130	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      //   98: pop
      //   99: getstatic 18	com/google/android/wallet/common/address/CountryMetadataRetrievalTask$CountryMetadataRetrievalRequest:COUNTRY_DATA_DEFAULT	Ljava/util/HashMap;
      //   102: invokevirtual 148	java/util/HashMap:keySet	()Ljava/util/Set;
      //   105: invokeinterface 154 1 0
      //   110: astore 5
      //   112: aload 5
      //   114: invokeinterface 159 1 0
      //   119: ifeq +144 -> 263
      //   122: aload 5
      //   124: invokeinterface 163 1 0
      //   129: checkcast 136	java/lang/String
      //   132: astore 6
      //   134: aload_3
      //   135: aload 6
      //   137: invokevirtual 167	org/json/JSONObject:has	(Ljava/lang/String;)Z
      //   140: ifne -28 -> 112
      //   143: aload_3
      //   144: aload 6
      //   146: getstatic 18	com/google/android/wallet/common/address/CountryMetadataRetrievalTask$CountryMetadataRetrievalRequest:COUNTRY_DATA_DEFAULT	Ljava/util/HashMap;
      //   149: aload 6
      //   151: invokevirtual 171	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
      //   154: invokevirtual 130	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      //   157: pop
      //   158: goto -46 -> 112
      //   161: astore 7
      //   163: new 173	java/lang/RuntimeException
      //   166: dup
      //   167: new 175	java/lang/StringBuilder
      //   170: dup
      //   171: ldc 177
      //   173: invokespecial 180	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   176: aload 6
      //   178: invokevirtual 184	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   181: ldc 186
      //   183: invokevirtual 184	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   186: invokevirtual 187	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   189: aload 7
      //   191: invokespecial 190	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
      //   194: athrow
      //   195: astore 12
      //   197: new 173	java/lang/RuntimeException
      //   200: dup
      //   201: new 175	java/lang/StringBuilder
      //   204: dup
      //   205: ldc 192
      //   207: invokespecial 180	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   210: aload 4
      //   212: invokevirtual 184	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   215: ldc 186
      //   217: invokevirtual 184	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   220: invokevirtual 187	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   223: aload 12
      //   225: invokespecial 190	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
      //   228: athrow
      //   229: astore 10
      //   231: new 173	java/lang/RuntimeException
      //   234: dup
      //   235: new 175	java/lang/StringBuilder
      //   238: dup
      //   239: ldc 194
      //   241: invokespecial 180	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   244: aload 9
      //   246: invokevirtual 184	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   249: ldc 186
      //   251: invokevirtual 184	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   254: invokevirtual 187	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   257: aload 10
      //   259: invokespecial 190	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
      //   262: athrow
      //   263: aload_3
      //   264: aload_2
      //   265: getfield 198	com/android/volley/Response:cacheEntry	Lcom/android/volley/Cache$Entry;
      //   268: invokestatic 202	com/android/volley/Response:success	(Ljava/lang/Object;Lcom/android/volley/Cache$Entry;)Lcom/android/volley/Response;
      //   271: areturn
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	272	0	this	CountryMetadataRetrievalRequest
      //   0	272	1	paramNetworkResponse	com.android.volley.NetworkResponse
      //   5	260	2	localResponse	com.android.volley.Response
      //   22	242	3	localJSONObject	JSONObject
      //   29	182	4	str1	String
      //   110	13	5	localIterator	java.util.Iterator
      //   132	45	6	str2	String
      //   161	29	7	localJSONException1	org.json.JSONException
      //   88	157	9	str3	String
      //   229	29	10	localJSONException2	org.json.JSONException
      //   195	29	12	localJSONException3	org.json.JSONException
      // Exception table:
      //   from	to	target	type
      //   143	158	161	org/json/JSONException
      //   45	54	195	org/json/JSONException
      //   90	99	229	org/json/JSONException
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.address.CountryMetadataRetrievalTask
 * JD-Core Version:    0.7.0.1
 */