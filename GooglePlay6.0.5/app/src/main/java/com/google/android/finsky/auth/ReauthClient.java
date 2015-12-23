package com.google.android.finsky.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.android.volley.toolbox.RequestFuture;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.utils.config.GservicesValue;
import java.util.concurrent.ExecutionException;
import org.json.JSONException;
import org.json.JSONObject;

public final class ReauthClient
{
  private final AccountManager mAccountManager;
  private final Context mContext;
  private final FinskyEventLog mEventLogger;
  
  public ReauthClient(Context paramContext)
  {
    this.mContext = paramContext;
    this.mAccountManager = AccountManager.get(this.mContext);
    this.mEventLogger = FinskyApp.get().getEventLogger();
  }
  
  private String getAuthToken(Account paramAccount)
    throws AuthFailureError
  {
    return new AndroidAuthenticator(this.mContext, paramAccount, (String)G.reauthApiAuthTokenType.get(), (byte)0).getAuthToken();
  }
  
  private void logErrorEvent(int paramInt1, int paramInt2)
  {
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(paramInt1).setErrorCode(paramInt2);
    this.mEventLogger.sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
  }
  
  private void logErrorEvent(int paramInt1, int paramInt2, Throwable paramThrowable)
  {
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(paramInt1).setErrorCode(paramInt2).setExceptionType(paramThrowable);
    this.mEventLogger.sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
  }
  
  private void logEvent(int paramInt)
  {
    this.mEventLogger.sendBackgroundEventToSinks(new BackgroundEventBuilder(paramInt).event);
  }
  
  public final ReauthSettings getReauthSettingsFromDevice(Account paramAccount)
  {
    String str = this.mAccountManager.getUserData(paramAccount, (String)G.reauthApiAccountManagerKey.get());
    if (str == null)
    {
      logErrorEvent(900, 1);
      FinskyLog.v("Reauth settings not cached. Use getReauthSettingsFromServer instead.", new Object[0]);
      return null;
    }
    try
    {
      ReauthSettings localReauthSettings = ReauthSettings.deserializeReauthSettings(new JSONObject(str), false);
      logEvent(900);
      return localReauthSettings;
    }
    catch (JSONException localJSONException)
    {
      logErrorEvent(900, 2, localJSONException);
      FinskyLog.e("Error deserializing reauth settings response from device", new Object[] { localJSONException });
    }
    return new ReauthSettings(907);
  }
  
  public final ReauthSettings getReauthSettingsFromServer(Account paramAccount)
  {
    
    String str;
    try
    {
      str = getAuthToken(paramAccount);
      if (TextUtils.isEmpty(str))
      {
        logErrorEvent(901, 1);
        ReauthSettings localReauthSettings2 = new ReauthSettings(903);
        return localReauthSettings2;
      }
    }
    catch (AuthFailureError localAuthFailureError)
    {
      logErrorEvent(901, 2, localAuthFailureError);
      return new ReauthSettings(904);
    }
    logEvent(901);
    RequestFuture localRequestFuture = RequestFuture.newFuture();
    FetchReauthSettingsRequest localFetchReauthSettingsRequest = new FetchReauthSettingsRequest((String)G.reauthApiGetSettingsEndpoint.get(), str, localRequestFuture, localRequestFuture);
    JSONObject localJSONObject;
    try
    {
      FinskyApp.get().mRequestQueue.add(localFetchReauthSettingsRequest);
      logEvent(902);
      localJSONObject = (JSONObject)localRequestFuture.get();
      if (localJSONObject == null)
      {
        logErrorEvent(903, -1);
        return new ReauthSettings(901);
      }
    }
    catch (ExecutionException localExecutionException)
    {
      Throwable localThrowable = localExecutionException.getCause();
      if ((localThrowable instanceof ServerError))
      {
        int i = ReauthStatus.handleServerError((ServerError)localThrowable, false);
        logErrorEvent(903, i);
        return new ReauthSettings(i);
      }
      logErrorEvent(903, -2, localThrowable);
      FinskyLog.e("VolleyError with getReauthSettings: %s", new Object[] { localThrowable });
      return new ReauthSettings(902);
    }
    catch (InterruptedException localInterruptedException)
    {
      logErrorEvent(903, -2, localInterruptedException);
      FinskyLog.wtf("getReauthSettings request to LSO reauth api interrupted.", new Object[0]);
      return new ReauthSettings(902);
    }
    try
    {
      ReauthSettings localReauthSettings1 = ReauthSettings.deserializeReauthSettings(localJSONObject, true);
      logEvent(903);
      return localReauthSettings1;
    }
    catch (JSONException localJSONException)
    {
      logErrorEvent(903, -3, localJSONException);
      FinskyLog.e("Error deserializing reauth settings response over network: %s", new Object[] { localJSONException });
    }
    return new ReauthSettings(907);
  }
  
  public final VerifyCredentialsResponse verifyCredentials(Account paramAccount, String paramString1, boolean paramBoolean, String paramString2)
  {
    
    if (paramBoolean) {}
    String str1;
    for (int i = 904;; i = 907) {
      try
      {
        str1 = getAuthToken(paramAccount);
        if (!TextUtils.isEmpty(str1)) {
          break;
        }
        logErrorEvent(i, 1);
        VerifyCredentialsResponse localVerifyCredentialsResponse8 = new VerifyCredentialsResponse(903);
        return localVerifyCredentialsResponse8;
      }
      catch (AuthFailureError localAuthFailureError)
      {
        logErrorEvent(i, 2, localAuthFailureError);
        VerifyCredentialsResponse localVerifyCredentialsResponse1 = new VerifyCredentialsResponse(904);
        return localVerifyCredentialsResponse1;
      }
    }
    logEvent(i);
    int j;
    if (!TextUtils.isEmpty(paramString2)) {
      j = 1;
    }
    label121:
    int k;
    for (;;)
    {
      JSONObject localJSONObject1 = VerifyCredentialsRequest.createJsonPayload(paramString1, paramBoolean);
      RequestFuture localRequestFuture = RequestFuture.newFuture();
      String str2;
      VerifyCredentialsRequest localVerifyCredentialsRequest;
      if (j != 0)
      {
        str2 = paramString2;
        String str3 = ((String)G.reauthApiVerifyCredentialsEndpoint.get()).replace("%user_id%", str2);
        if (j != 0) {
          str3 = Uri.parse(str3).buildUpon().appendQueryParameter("delegationType", "unicorn").toString();
        }
        localVerifyCredentialsRequest = new VerifyCredentialsRequest(str3, str1, localJSONObject1, localRequestFuture, localRequestFuture);
        if (!paramBoolean) {
          break label269;
        }
        k = 906;
      }
      try
      {
        label192:
        FinskyApp.get().mRequestQueue.add(localVerifyCredentialsRequest);
        if (paramBoolean) {}
        JSONObject localJSONObject2;
        for (int n = 905;; n = 908)
        {
          logEvent(n);
          localJSONObject2 = (JSONObject)localRequestFuture.get();
          if (localJSONObject2 != null) {
            break label410;
          }
          logErrorEvent(k, -1);
          VerifyCredentialsResponse localVerifyCredentialsResponse7 = new VerifyCredentialsResponse(901);
          return localVerifyCredentialsResponse7;
          j = 0;
          break;
          str2 = "me";
          break label121;
          label269:
          k = 909;
          break label192;
        }
        try
        {
          Throwable localThrowable;
          int m;
          VerifyCredentialsResponse localVerifyCredentialsResponse4;
          VerifyCredentialsResponse localVerifyCredentialsResponse3;
          VerifyCredentialsResponse localVerifyCredentialsResponse2;
          if (TextUtils.isEmpty(localJSONObject2.getString("encodedRapt"))) {
            if (paramBoolean)
            {
              i1 = 1003;
              localVerifyCredentialsResponse6 = new VerifyCredentialsResponse(i1);
              logEvent(k);
              return localVerifyCredentialsResponse6;
            }
          }
        }
        catch (JSONException localJSONException)
        {
          logErrorEvent(k, -3, localJSONException);
          FinskyLog.e("Error deserializing verifyCredentials response: %s", new Object[] { localJSONException });
          localVerifyCredentialsResponse5 = new VerifyCredentialsResponse(907);
          return localVerifyCredentialsResponse5;
        }
      }
      catch (ExecutionException localExecutionException)
      {
        localThrowable = localExecutionException.getCause();
        if ((localThrowable instanceof ServerError))
        {
          m = ReauthStatus.handleServerError((ServerError)localThrowable, paramBoolean);
          logErrorEvent(k, m);
          localVerifyCredentialsResponse4 = new VerifyCredentialsResponse(m);
          return localVerifyCredentialsResponse4;
        }
        logErrorEvent(k, -2, localThrowable);
        FinskyLog.e("VolleyError with verifyCredentials: %s", new Object[] { localThrowable });
        localVerifyCredentialsResponse3 = new VerifyCredentialsResponse(902);
        return localVerifyCredentialsResponse3;
      }
      catch (InterruptedException localInterruptedException)
      {
        logErrorEvent(k, -2, localInterruptedException);
        FinskyLog.wtf("verifyCredentials request to LSO reauth api interrupted.", new Object[0]);
        localVerifyCredentialsResponse2 = new VerifyCredentialsResponse(902);
        return localVerifyCredentialsResponse2;
      }
    }
    for (;;)
    {
      label410:
      VerifyCredentialsResponse localVerifyCredentialsResponse5;
      int i1 = 1100;
      continue;
      VerifyCredentialsResponse localVerifyCredentialsResponse6 = new VerifyCredentialsResponse(0);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.auth.ReauthClient
 * JD-Core Version:    0.7.0.1
 */