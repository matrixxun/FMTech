package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import org.json.JSONException;
import org.json.JSONObject;

@zzhb
public class zzfs
{
  private final String zzDh;
  final zzjo zzpX;
  
  public zzfs(zzjo paramzzjo)
  {
    this(paramzzjo, "");
  }
  
  public zzfs(zzjo paramzzjo, String paramString)
  {
    this.zzpX = paramzzjo;
    this.zzDh = paramString;
  }
  
  public final void zza(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat, int paramInt5)
  {
    try
    {
      JSONObject localJSONObject = new JSONObject().put("width", paramInt1).put("height", paramInt2).put("maxSizeWidth", paramInt3).put("maxSizeHeight", paramInt4).put("density", paramFloat).put("rotation", paramInt5);
      this.zzpX.zzb("onScreenInfoChanged", localJSONObject);
      return;
    }
    catch (JSONException localJSONException)
    {
      zzb.e("Error occured while obtaining screen information.", localJSONException);
    }
  }
  
  public final void zzal(String paramString)
  {
    try
    {
      JSONObject localJSONObject = new JSONObject().put("message", paramString).put("action", this.zzDh);
      this.zzpX.zzb("onError", localJSONObject);
      return;
    }
    catch (JSONException localJSONException)
    {
      zzb.e("Error occurred while dispatching error event.", localJSONException);
    }
  }
  
  public final void zzan(String paramString)
  {
    try
    {
      JSONObject localJSONObject = new JSONObject().put("state", paramString);
      this.zzpX.zzb("onStateChanged", localJSONObject);
      return;
    }
    catch (JSONException localJSONException)
    {
      zzb.e("Error occured while dispatching state change.", localJSONException);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzfs
 * JD-Core Version:    0.7.0.1
 */