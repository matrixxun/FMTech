package com.google.android.wallet.redirect;

import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class HtmlFormContent
{
  final JSONArray mEntries;
  final String mMethod;
  private String mUrlEncodedString;
  
  /* Error */
  public HtmlFormContent(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 16	java/lang/Object:<init>	()V
    //   4: aload_0
    //   5: aload_1
    //   6: putfield 18	com/google/android/wallet/redirect/HtmlFormContent:mMethod	Ljava/lang/String;
    //   9: aload_2
    //   10: invokestatic 24	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   13: istore 4
    //   15: iload 4
    //   17: ifeq +13 -> 30
    //   20: aconst_null
    //   21: astore 5
    //   23: aload_0
    //   24: aload 5
    //   26: putfield 26	com/google/android/wallet/redirect/HtmlFormContent:mEntries	Lorg/json/JSONArray;
    //   29: return
    //   30: new 28	org/json/JSONArray
    //   33: dup
    //   34: aload_2
    //   35: invokespecial 31	org/json/JSONArray:<init>	(Ljava/lang/String;)V
    //   38: astore 5
    //   40: goto -17 -> 23
    //   43: astore_3
    //   44: new 33	java/lang/IllegalArgumentException
    //   47: dup
    //   48: ldc 35
    //   50: aload_3
    //   51: invokespecial 38	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   54: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	55	0	this	HtmlFormContent
    //   0	55	1	paramString1	String
    //   0	55	2	paramString2	String
    //   43	8	3	localJSONException	JSONException
    //   13	3	4	bool	boolean
    //   21	18	5	localJSONArray	JSONArray
    // Exception table:
    //   from	to	target	type
    //   9	15	43	org/json/JSONException
    //   30	40	43	org/json/JSONException
  }
  
  private String buildUrlEncodedString()
  {
    if (this.mEntries == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList(this.mEntries.length());
    int i = 0;
    int j = this.mEntries.length();
    for (;;)
    {
      if (i >= j) {
        break label101;
      }
      try
      {
        JSONObject localJSONObject = this.mEntries.optJSONObject(i);
        if (localJSONObject != null) {
          localArrayList.add(new BasicNameValuePair(localJSONObject.getString("name"), localJSONObject.getString("value")));
        }
        i++;
      }
      catch (JSONException localJSONException)
      {
        for (;;)
        {
          Log.e("HtmlFormContent", "Failed to parse JSON object", localJSONException);
        }
      }
    }
    label101:
    return URLEncodedUtils.format(localArrayList, "UTF-8");
  }
  
  public final boolean isPost()
  {
    return "POST".equalsIgnoreCase(this.mMethod);
  }
  
  public final boolean isValid()
  {
    if (("GET".equalsIgnoreCase(this.mMethod)) || (TextUtils.isEmpty(this.mMethod))) {}
    for (int i = 1;; i = 0)
    {
      boolean bool1;
      if (i == 0)
      {
        boolean bool2 = isPost();
        bool1 = false;
        if (bool2)
        {
          JSONArray localJSONArray = this.mEntries;
          bool1 = false;
          if (localJSONArray == null) {}
        }
      }
      else
      {
        bool1 = true;
      }
      return bool1;
    }
  }
  
  public final String toUrlEncodedString()
  {
    if (this.mUrlEncodedString == null) {
      this.mUrlEncodedString = buildUrlEncodedString();
    }
    return this.mUrlEncodedString;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.redirect.HtmlFormContent
 * JD-Core Version:    0.7.0.1
 */