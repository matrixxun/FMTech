package com.google.android.gms.ads.internal.purchase;

import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzhb;
import org.json.JSONException;
import org.json.JSONObject;

@zzhb
public final class zzi
{
  public static String zzap(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    try
    {
      String str = new JSONObject(paramString).getString("developerPayload");
      return str;
    }
    catch (JSONException localJSONException)
    {
      zzb.w("Fail to parse purchase data");
    }
    return null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.purchase.zzi
 * JD-Core Version:    0.7.0.1
 */