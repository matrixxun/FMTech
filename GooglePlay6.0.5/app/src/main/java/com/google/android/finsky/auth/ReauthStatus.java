package com.google.android.finsky.auth;

import com.android.volley.NetworkResponse;
import com.android.volley.ServerError;
import com.android.volley.toolbox.HttpHeaderParser;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

public final class ReauthStatus
{
  public static int handleServerError(ServerError paramServerError, boolean paramBoolean)
  {
    NetworkResponse localNetworkResponse = paramServerError.networkResponse;
    int i;
    try
    {
      localJSONObject = new JSONObject(new String(localNetworkResponse.data, HttpHeaderParser.parseCharset(localNetworkResponse.headers))).getJSONObject("error");
      str = localJSONObject.optString("message");
      i = -1;
      switch (str.hashCode())
      {
      case 1880971961: 
        if (!str.equals("PIN_NOTSET")) {
          break label210;
        }
        i = 0;
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      JSONObject localJSONObject;
      String str;
      int k;
      return 908;
    }
    catch (JSONException localJSONException)
    {
      return 907;
    }
    if (str.equals("PIN_LOCKED"))
    {
      i = 1;
      break label210;
      if (str.equals("INVALID_GRANT"))
      {
        i = 2;
        break label210;
        if (str.equals("FORBIDDEN"))
        {
          i = 3;
          break label210;
          if (str.equals("UNAUTHORIZED_CLIENT"))
          {
            i = 4;
            break label210;
            k = localJSONObject.optInt("code", 900);
            return k;
          }
        }
      }
    }
    label210:
    int j;
    switch (i)
    {
    default: 
      j = 900;
    }
    while (j != 900)
    {
      return j;
      j = 1001;
      continue;
      j = 1002;
      continue;
      if (paramBoolean)
      {
        j = 1003;
      }
      else
      {
        j = 1100;
        continue;
        j = 906;
        continue;
        j = 905;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.auth.ReauthStatus
 * JD-Core Version:    0.7.0.1
 */