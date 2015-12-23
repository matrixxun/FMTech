package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import org.json.JSONException;
import org.json.JSONObject;

@zzhb
public final class zzfq
{
  private final boolean zzCU;
  private final boolean zzCV;
  private final boolean zzCW;
  private final boolean zzCX;
  private final boolean zzCY;
  
  private zzfq(zza paramzza)
  {
    this.zzCU = paramzza.zzCU;
    this.zzCV = paramzza.zzCV;
    this.zzCW = paramzza.zzCW;
    this.zzCX = paramzza.zzCX;
    this.zzCY = paramzza.zzCY;
  }
  
  public final JSONObject toJson()
  {
    try
    {
      JSONObject localJSONObject = new JSONObject().put("sms", this.zzCU).put("tel", this.zzCV).put("calendar", this.zzCW).put("storePicture", this.zzCX).put("inlineVideo", this.zzCY);
      return localJSONObject;
    }
    catch (JSONException localJSONException)
    {
      zzb.e("Error occured while obtaining the MRAID capabilities.", localJSONException);
    }
    return null;
  }
  
  public static final class zza
  {
    boolean zzCU;
    boolean zzCV;
    boolean zzCW;
    boolean zzCX;
    boolean zzCY;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzfq
 * JD-Core Version:    0.7.0.1
 */