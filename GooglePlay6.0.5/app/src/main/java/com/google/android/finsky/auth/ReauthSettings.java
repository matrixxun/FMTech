package com.google.android.finsky.auth;

import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.experiments.FinskyExperiments;
import org.json.JSONException;
import org.json.JSONObject;

public final class ReauthSettings
{
  public final String passwordStatus;
  public final PinSettings pin;
  public final int status;
  
  public ReauthSettings(int paramInt)
  {
    this(paramInt, null, null);
  }
  
  private ReauthSettings(int paramInt, String paramString, PinSettings paramPinSettings)
  {
    this.status = paramInt;
    this.passwordStatus = paramString;
    this.pin = paramPinSettings;
  }
  
  static ReauthSettings deserializeReauthSettings(JSONObject paramJSONObject, boolean paramBoolean)
    throws JSONException
  {
    JSONObject localJSONObject1 = paramJSONObject.getJSONObject("password");
    JSONObject localJSONObject2 = paramJSONObject.getJSONObject("pin");
    boolean bool = FinskyApp.get().getExperiments().isEnabled(12603105L);
    localObject = null;
    String str2;
    if (bool)
    {
      if (!paramBoolean) {
        break label110;
      }
      str2 = "recoveryUrl";
    }
    try
    {
      String str3 = localJSONObject2.getString(str2);
      localObject = str3;
    }
    catch (JSONException localJSONException)
    {
      for (;;)
      {
        String str1;
        label110:
        label117:
        localObject = null;
      }
    }
    if (TextUtils.isEmpty((CharSequence)localObject)) {
      if (!paramBoolean) {
        break label117;
      }
    }
    for (str1 = "resetUrl";; str1 = "reset_url")
    {
      localObject = localJSONObject2.getString(str1);
      return new ReauthSettings(0, localJSONObject1.getString("status"), new PinSettings(localJSONObject2.getString("status"), (String)localObject));
      str2 = "recovery_url";
      break;
    }
  }
  
  public static final class PinSettings
  {
    public final String recoveryUrl;
    public final String status;
    
    public PinSettings(String paramString1, String paramString2)
    {
      this.status = paramString1;
      this.recoveryUrl = paramString2;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.auth.ReauthSettings
 * JD-Core Version:    0.7.0.1
 */