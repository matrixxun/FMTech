package com.google.android.wallet.common.address;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.common.api.WalletRequestQueue;
import com.google.android.wallet.common.api.http.JsonObjectWithHeadersRequest;
import com.google.android.wallet.common.util.AndroidUtils;
import com.google.android.wallet.common.util.PermissionDelegate;
import com.google.android.wallet.config.G.googleplaces;
import com.google.location.country.Postaladdress.PostalAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class GooglePlacesAddressSource
  implements AddressSource
{
  private final Activity mActivity;
  private final RequestQueue mRequestQueue;
  
  public GooglePlacesAddressSource(Activity paramActivity, RequestQueue paramRequestQueue)
  {
    this.mActivity = paramActivity;
    this.mRequestQueue = paramRequestQueue;
  }
  
  private static ArrayList<AddressSourceResult> convertJsonObjectToAddressSourceResults(JSONObject paramJSONObject, CharSequence paramCharSequence, char paramChar)
  {
    Object localObject1;
    if (paramJSONObject == null)
    {
      localObject1 = null;
      return localObject1;
    }
    String str1 = paramJSONObject.optString("status");
    if (!"OK".equalsIgnoreCase(str1))
    {
      Log.w("GooglePlacesAddressSour", "Response has invalid status: " + str1);
      return null;
    }
    label134:
    label196:
    do
    {
      do
      {
        do
        {
          for (;;)
          {
            JSONObject localJSONObject;
            Object localObject2;
            try
            {
              localJSONArray = paramJSONObject.getJSONArray("predictions");
              localObject1 = new ArrayList();
              i = 0;
              int j = localJSONArray.length();
              if (i >= j) {
                break;
              }
            }
            catch (JSONException localJSONException1)
            {
              JSONArray localJSONArray;
              int i;
              int k;
              Log.w("GooglePlacesAddressSour", "Response does not contain predictions");
              return null;
            }
            try
            {
              localJSONObject = localJSONArray.getJSONObject(i);
              localObject2 = localJSONObject.getString("description");
              k = ((CharSequence)localObject2).length();
              if (k != 0) {
                break label134;
              }
              i++;
            }
            catch (JSONException localJSONException2)
            {
              for (;;)
              {
                String str2;
                String str4;
                int m;
                break;
                String str3 = "route";
                continue;
                str3 = "locality";
                continue;
                str3 = "administrative_area_level_1";
              }
            }
          }
          str2 = localJSONObject.getString("reference");
        } while (str2.length() == 0);
        switch (paramChar)
        {
        }
      } while (!hasType(localJSONObject, str3));
      str4 = getMatchingTerm(localJSONObject);
    } while (str4 == null);
    switch (paramChar)
    {
    }
    while ((m == 0) || (str4.toLowerCase().startsWith(paramCharSequence.toString().toLowerCase())))
    {
      List localList = getMatchedSubstrings(localJSONObject);
      if (!localList.isEmpty()) {
        localObject2 = SourceUtils.createSpannableForMatchedSubstrings(localList, (CharSequence)localObject2);
      }
      ((ArrayList)localObject1).add(new AddressSourceResult(str4, (CharSequence)localObject2, "GooglePlacesAddressSource", str2));
      break;
      str3 = "locality";
      break label196;
      m = 0;
      continue;
      str3 = null;
      break label196;
      m = 1;
    }
  }
  
  /* Error */
  private static Postaladdress.PostalAddress convertJsonObjectToPostalAddress(JSONObject paramJSONObject, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +9 -> 10
    //   4: aconst_null
    //   5: astore 10
    //   7: aload 10
    //   9: areturn
    //   10: aload_0
    //   11: ldc 25
    //   13: invokevirtual 31	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   16: astore_2
    //   17: ldc 33
    //   19: aload_2
    //   20: invokevirtual 39	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   23: ifne +27 -> 50
    //   26: ldc 41
    //   28: new 43	java/lang/StringBuilder
    //   31: dup
    //   32: ldc 45
    //   34: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   37: aload_2
    //   38: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   41: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   44: invokestatic 62	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   47: pop
    //   48: aconst_null
    //   49: areturn
    //   50: aload_0
    //   51: ldc 146
    //   53: invokevirtual 149	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   56: astore 4
    //   58: aload 4
    //   60: ldc 151
    //   62: invokevirtual 68	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   65: astore 6
    //   67: new 153	android/support/v4/util/SimpleArrayMap
    //   70: dup
    //   71: invokespecial 154	android/support/v4/util/SimpleArrayMap:<init>	()V
    //   74: astore 7
    //   76: iconst_0
    //   77: istore 8
    //   79: aload 6
    //   81: invokevirtual 77	org/json/JSONArray:length	()I
    //   84: istore 9
    //   86: iload 8
    //   88: iload 9
    //   90: if_icmpge +111 -> 201
    //   93: aload 6
    //   95: iload 8
    //   97: invokevirtual 81	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   100: astore 15
    //   102: aload 15
    //   104: ldc 156
    //   106: invokestatic 98	com/google/android/wallet/common/address/GooglePlacesAddressSource:hasType	(Lorg/json/JSONObject;Ljava/lang/String;)Z
    //   109: ifeq +6 -> 115
    //   112: goto +352 -> 464
    //   115: aload 15
    //   117: ldc 142
    //   119: invokestatic 98	com/google/android/wallet/common/address/GooglePlacesAddressSource:hasType	(Lorg/json/JSONObject;Ljava/lang/String;)Z
    //   122: ifne +355 -> 477
    //   125: aload 15
    //   127: ldc 158
    //   129: invokestatic 98	com/google/android/wallet/common/address/GooglePlacesAddressSource:hasType	(Lorg/json/JSONObject;Ljava/lang/String;)Z
    //   132: ifeq +62 -> 194
    //   135: goto +342 -> 477
    //   138: aload 15
    //   140: aload 16
    //   142: invokevirtual 86	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   145: astore 17
    //   147: aload 15
    //   149: ldc 160
    //   151: invokevirtual 68	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   154: astore 18
    //   156: iconst_0
    //   157: istore 19
    //   159: aload 18
    //   161: invokevirtual 77	org/json/JSONArray:length	()I
    //   164: istore 20
    //   166: iload 19
    //   168: iload 20
    //   170: if_icmpge +294 -> 464
    //   173: aload 7
    //   175: aload 18
    //   177: iload 19
    //   179: invokevirtual 163	org/json/JSONArray:getString	(I)Ljava/lang/String;
    //   182: aload 17
    //   184: invokevirtual 167	android/support/v4/util/SimpleArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   187: pop
    //   188: iinc 19 1
    //   191: goto -25 -> 166
    //   194: ldc 169
    //   196: astore 16
    //   198: goto -60 -> 138
    //   201: new 171	com/google/location/country/Postaladdress$PostalAddress
    //   204: dup
    //   205: invokespecial 172	com/google/location/country/Postaladdress$PostalAddress:<init>	()V
    //   208: astore 10
    //   210: aload 7
    //   212: ldc 174
    //   214: invokevirtual 177	android/support/v4/util/SimpleArrayMap:containsKey	(Ljava/lang/Object;)Z
    //   217: ifne +13 -> 230
    //   220: aload 7
    //   222: ldc 140
    //   224: invokevirtual 177	android/support/v4/util/SimpleArrayMap:containsKey	(Ljava/lang/Object;)Z
    //   227: ifeq +49 -> 276
    //   230: aload 7
    //   232: ldc 174
    //   234: invokevirtual 181	android/support/v4/util/SimpleArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   237: checkcast 35	java/lang/String
    //   240: astore 11
    //   242: aload 7
    //   244: ldc 140
    //   246: invokevirtual 181	android/support/v4/util/SimpleArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   249: checkcast 35	java/lang/String
    //   252: astore 12
    //   254: aload 11
    //   256: invokestatic 186	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   259: ifeq +133 -> 392
    //   262: aload 10
    //   264: iconst_1
    //   265: anewarray 35	java/lang/String
    //   268: dup
    //   269: iconst_0
    //   270: aload 12
    //   272: aastore
    //   273: putfield 190	com/google/location/country/Postaladdress$PostalAddress:addressLine	[Ljava/lang/String;
    //   276: aload 7
    //   278: ldc 138
    //   280: invokevirtual 177	android/support/v4/util/SimpleArrayMap:containsKey	(Ljava/lang/Object;)Z
    //   283: ifeq +18 -> 301
    //   286: aload 10
    //   288: aload 7
    //   290: ldc 138
    //   292: invokevirtual 181	android/support/v4/util/SimpleArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   295: checkcast 35	java/lang/String
    //   298: putfield 194	com/google/location/country/Postaladdress$PostalAddress:localityName	Ljava/lang/String;
    //   301: aload 7
    //   303: ldc 142
    //   305: invokevirtual 177	android/support/v4/util/SimpleArrayMap:containsKey	(Ljava/lang/Object;)Z
    //   308: ifeq +18 -> 326
    //   311: aload 10
    //   313: aload 7
    //   315: ldc 142
    //   317: invokevirtual 181	android/support/v4/util/SimpleArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   320: checkcast 35	java/lang/String
    //   323: putfield 197	com/google/location/country/Postaladdress$PostalAddress:administrativeAreaName	Ljava/lang/String;
    //   326: aload 7
    //   328: ldc 199
    //   330: invokevirtual 177	android/support/v4/util/SimpleArrayMap:containsKey	(Ljava/lang/Object;)Z
    //   333: ifeq +18 -> 351
    //   336: aload 10
    //   338: aload 7
    //   340: ldc 199
    //   342: invokevirtual 181	android/support/v4/util/SimpleArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   345: checkcast 35	java/lang/String
    //   348: putfield 202	com/google/location/country/Postaladdress$PostalAddress:postalCodeNumber	Ljava/lang/String;
    //   351: aload 7
    //   353: ldc 158
    //   355: invokevirtual 177	android/support/v4/util/SimpleArrayMap:containsKey	(Ljava/lang/Object;)Z
    //   358: ifeq +18 -> 376
    //   361: aload 10
    //   363: aload 7
    //   365: ldc 158
    //   367: invokevirtual 181	android/support/v4/util/SimpleArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   370: checkcast 35	java/lang/String
    //   373: putfield 205	com/google/location/country/Postaladdress$PostalAddress:countryNameCode	Ljava/lang/String;
    //   376: aload_1
    //   377: invokestatic 186	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   380: ifne -373 -> 7
    //   383: aload 10
    //   385: aload_1
    //   386: putfield 208	com/google/location/country/Postaladdress$PostalAddress:languageCode	Ljava/lang/String;
    //   389: aload 10
    //   391: areturn
    //   392: aload 12
    //   394: invokestatic 186	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   397: ifeq +20 -> 417
    //   400: aload 10
    //   402: iconst_1
    //   403: anewarray 35	java/lang/String
    //   406: dup
    //   407: iconst_0
    //   408: aload 11
    //   410: aastore
    //   411: putfield 190	com/google/location/country/Postaladdress$PostalAddress:addressLine	[Ljava/lang/String;
    //   414: goto -138 -> 276
    //   417: iconst_1
    //   418: anewarray 35	java/lang/String
    //   421: astore 13
    //   423: aload 13
    //   425: iconst_0
    //   426: new 43	java/lang/StringBuilder
    //   429: dup
    //   430: invokespecial 209	java/lang/StringBuilder:<init>	()V
    //   433: aload 11
    //   435: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   438: ldc 211
    //   440: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   443: aload 12
    //   445: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   448: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   451: aastore
    //   452: aload 10
    //   454: aload 13
    //   456: putfield 190	com/google/location/country/Postaladdress$PostalAddress:addressLine	[Ljava/lang/String;
    //   459: goto -183 -> 276
    //   462: astore 14
    //   464: iinc 8 1
    //   467: goto -381 -> 86
    //   470: astore_3
    //   471: aconst_null
    //   472: areturn
    //   473: astore 5
    //   475: aconst_null
    //   476: areturn
    //   477: ldc 213
    //   479: astore 16
    //   481: goto -343 -> 138
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	484	0	paramJSONObject	JSONObject
    //   0	484	1	paramString	String
    //   16	22	2	str1	String
    //   470	1	3	localJSONException1	JSONException
    //   56	3	4	localJSONObject1	JSONObject
    //   473	1	5	localJSONException2	JSONException
    //   65	29	6	localJSONArray1	JSONArray
    //   74	290	7	localSimpleArrayMap	android.support.v4.util.SimpleArrayMap
    //   77	388	8	i	int
    //   84	7	9	j	int
    //   5	448	10	localPostalAddress	Postaladdress.PostalAddress
    //   240	194	11	str2	String
    //   252	192	12	str3	String
    //   421	34	13	arrayOfString	String[]
    //   462	1	14	localJSONException3	JSONException
    //   100	48	15	localJSONObject2	JSONObject
    //   140	340	16	str4	String
    //   145	38	17	str5	String
    //   154	22	18	localJSONArray2	JSONArray
    //   157	32	19	k	int
    //   164	7	20	m	int
    // Exception table:
    //   from	to	target	type
    //   93	112	462	org/json/JSONException
    //   115	135	462	org/json/JSONException
    //   138	156	462	org/json/JSONException
    //   159	166	462	org/json/JSONException
    //   173	188	462	org/json/JSONException
    //   50	58	470	org/json/JSONException
    //   58	67	473	org/json/JSONException
  }
  
  private JSONObject fetchJsonObject(String paramString)
  {
    try
    {
      RequestFuture localRequestFuture = RequestFuture.newFuture();
      JsonObjectWithHeadersRequest localJsonObjectWithHeadersRequest = new JsonObjectWithHeadersRequest(paramString, Collections.singletonMap("User-Agent", WalletRequestQueue.USER_AGENT_VALUE), localRequestFuture, localRequestFuture);
      this.mRequestQueue.add(localJsonObjectWithHeadersRequest);
      JSONObject localJSONObject = (JSONObject)localRequestFuture.get(5000L, TimeUnit.MILLISECONDS);
      return localJSONObject;
    }
    catch (TimeoutException localTimeoutException)
    {
      Log.w("GooglePlacesAddressSour", "TimeoutException while retrieving addresses from GooglePlaces", localTimeoutException);
      return null;
    }
    catch (InterruptedException localInterruptedException)
    {
      Log.w("GooglePlacesAddressSour", "InterruptedException while retrieving addresses from GooglePlaces", localInterruptedException);
      return null;
    }
    catch (ExecutionException localExecutionException)
    {
      Log.w("GooglePlacesAddressSour", "ExecutionException while retrieving addresses from GooglePlaces", localExecutionException);
    }
    return null;
  }
  
  private Location getLastKnownLocation()
  {
    Activity localActivity = this.mActivity;
    if (PermissionDelegate.selfHasAnyLocationPermission(localActivity))
    {
      PermissionDelegate.getInstance();
      PermissionDelegate.getInstance();
      if (!PermissionDelegate.hasAnyLocationPermission(localActivity, AndroidUtils.getCallingPackage(localActivity))) {}
    }
    for (int i = 1; i == 0; i = 0) {
      return null;
    }
    return ((LocationManager)this.mActivity.getSystemService("location")).getLastKnownLocation("network");
  }
  
  private static List<Pair<Integer, Integer>> getMatchedSubstrings(JSONObject paramJSONObject)
  {
    localArrayList = new ArrayList();
    try
    {
      JSONArray localJSONArray = paramJSONObject.getJSONArray("matched_substrings");
      int i = 0;
      int j = localJSONArray.length();
      while (i < j)
      {
        JSONObject localJSONObject = localJSONArray.getJSONObject(i);
        int k = localJSONObject.getInt("offset");
        int m = localJSONObject.getInt("length");
        localArrayList.add(Pair.create(Integer.valueOf(k), Integer.valueOf(m)));
        i++;
      }
      return localArrayList;
    }
    catch (JSONException localJSONException) {}
  }
  
  private static String getMatchingTerm(JSONObject paramJSONObject)
    throws JSONException
  {
    int i = paramJSONObject.getJSONArray("matched_substrings").getJSONObject(0).getInt("offset");
    JSONArray localJSONArray = paramJSONObject.getJSONArray("terms");
    int j = 0;
    int k = localJSONArray.length();
    while (j < k)
    {
      JSONObject localJSONObject = localJSONArray.getJSONObject(j);
      if (i < localJSONObject.getInt("offset") + localJSONObject.getString("value").length()) {
        return localJSONObject.getString("value");
      }
      j++;
    }
    return null;
  }
  
  private static String getRequestTypeForField(char paramChar)
  {
    switch (paramChar)
    {
    default: 
      return null;
    case '1': 
      return "geocode";
    case 'C': 
      return "(cities)";
    }
    return "(regions)";
  }
  
  private static boolean hasType(JSONObject paramJSONObject, String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {}
    for (;;)
    {
      return false;
      try
      {
        JSONArray localJSONArray = paramJSONObject.getJSONArray("types");
        int i = 0;
        int j = localJSONArray.length();
        while (i < j)
        {
          boolean bool = paramString.equalsIgnoreCase(localJSONArray.getString(i));
          if (bool) {
            return true;
          }
          i++;
        }
        return false;
      }
      catch (JSONException localJSONException) {}
    }
  }
  
  public static boolean isCountrySupported(int paramInt)
  {
    String str = RegionCode.toCountryCode(paramInt);
    return ((String)G.googleplaces.supportedCountries.get()).contains(str);
  }
  
  public final Postaladdress.PostalAddress getAddress(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1)) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new BasicNameValuePair("reference", paramString1));
    if (getLastKnownLocation() != null) {}
    for (String str1 = "true";; str1 = "false")
    {
      localArrayList.add(new BasicNameValuePair("sensor", str1));
      localArrayList.add(new BasicNameValuePair("key", "AIzaSyCgACP5TTubzmLhxFL5ONXq6B5l2eH_EXc"));
      if (!TextUtils.isEmpty(paramString2)) {
        localArrayList.add(new BasicNameValuePair("language", paramString2));
      }
      String str2 = URLEncodedUtils.format(localArrayList, "utf-8");
      return convertJsonObjectToPostalAddress(fetchJsonObject("https://maps.googleapis.com/maps/api/place/details/json?" + str2), paramString2);
    }
  }
  
  public final List<AddressSourceResult> getAddresses(CharSequence paramCharSequence, char paramChar, char[] paramArrayOfChar, int paramInt, String paramString)
  {
    int i;
    if (paramCharSequence != null)
    {
      i = paramCharSequence.length();
      switch (paramChar)
      {
      }
    }
    for (int j = ((Integer)G.googleplaces.thresholdDefault.get()).intValue(); i < j; j = ((Integer)G.googleplaces.thresholdAddressLine1.get()).intValue()) {
      return null;
    }
    if ((isCountrySupported(paramInt)) && (getRequestTypeForField(paramChar) != null)) {}
    for (int k = 1; k == 0; k = 0) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new BasicNameValuePair("input", paramCharSequence.toString()));
    localArrayList.add(new BasicNameValuePair("key", "AIzaSyCgACP5TTubzmLhxFL5ONXq6B5l2eH_EXc"));
    localArrayList.add(new BasicNameValuePair("types", getRequestTypeForField(paramChar)));
    Location localLocation = getLastKnownLocation();
    if (localLocation != null)
    {
      localArrayList.add(new BasicNameValuePair("location", localLocation.getLatitude() + "," + localLocation.getLongitude()));
      localArrayList.add(new BasicNameValuePair("radius", "80000"));
    }
    if (localLocation != null) {}
    for (String str1 = "true";; str1 = "false")
    {
      localArrayList.add(new BasicNameValuePair("sensor", str1));
      localArrayList.add(new BasicNameValuePair("components", "country:" + RegionCode.toCountryCode(paramInt).toLowerCase(Locale.US)));
      if (!TextUtils.isEmpty(paramString)) {
        localArrayList.add(new BasicNameValuePair("language", paramString));
      }
      String str2 = URLEncodedUtils.format(localArrayList, "utf-8");
      return convertJsonObjectToAddressSourceResults(fetchJsonObject("https://maps.googleapis.com/maps/api/place/autocomplete/json?" + str2), paramCharSequence, paramChar);
    }
  }
  
  public final String getName()
  {
    return "GooglePlacesAddressSource";
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.address.GooglePlacesAddressSource
 * JD-Core Version:    0.7.0.1
 */