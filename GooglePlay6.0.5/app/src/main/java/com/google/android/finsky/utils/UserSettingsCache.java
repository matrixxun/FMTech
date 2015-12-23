package com.google.android.finsky.utils;

import android.accounts.Account;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.LongSparseArray;
import android.text.TextUtils;
import android.util.Base64;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments.TargetsChangeListener;
import com.google.android.finsky.protos.GetUserSettingsResponse;
import com.google.android.finsky.protos.Onboarding;
import com.google.android.finsky.protos.Onboardings;
import com.google.android.finsky.protos.PlayAccountProto.PlayAccountUserPurchaseCache;
import com.google.android.finsky.protos.UpdateUserSettingRequest;
import com.google.android.finsky.protos.UpdateUserSettingResponse;
import com.google.android.finsky.protos.UserSettings;
import com.google.android.finsky.protos.UserSettingsConsistencyTokens;
import com.google.android.finsky.protos.UserSettingsConsistencyTokens.ConsistencyTokenInfo;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import java.util.ArrayList;
import java.util.List;

public final class UserSettingsCache
{
  private static final long[] REFRESH_TRIGGERING_EXPERIMENTS = { 12603428L };
  private static final List<UserSettingsCacheListener> mListeners = new ArrayList();
  private static FinskyExperiments.TargetsChangeListener sExperimentsChangeListener;
  
  public static void addListener(UserSettingsCacheListener paramUserSettingsCacheListener)
  {
    mListeners.add(paramUserSettingsCacheListener);
  }
  
  public static void clearUserSettings(String paramString)
  {
    FinskyPreferences.userSettingsCache.get(paramString).remove();
    FinskyPreferences.userSettingsCacheDirty.get(paramString).remove();
    FinskyPreferences.userSettingsConsistencyTokens.get(paramString).remove();
  }
  
  private static boolean decodeFromString(String paramString, MessageNano paramMessageNano)
  {
    try
    {
      byte[] arrayOfByte = Base64.decode(paramString, 3);
      MessageNano.mergeFrom$1ec43da(paramMessageNano, arrayOfByte, arrayOfByte.length);
      return true;
    }
    catch (InvalidProtocolBufferNanoException localInvalidProtocolBufferNanoException)
    {
      FinskyLog.e(localInvalidProtocolBufferNanoException, "Error parsing string into proto", new Object[0]);
    }
    return false;
  }
  
  private static String encodeToString(MessageNano paramMessageNano)
  {
    return Base64.encodeToString(MessageNano.toByteArray(paramMessageNano), 3);
  }
  
  public static UserSettings getCachedUserSettings(String paramString)
  {
    String str = (String)FinskyPreferences.userSettingsCache.get(paramString).get();
    UserSettings localUserSettings;
    if ((str == null) || (str.isEmpty())) {
      localUserSettings = null;
    }
    do
    {
      return localUserSettings;
      localUserSettings = new UserSettings();
    } while (decodeFromString(str, localUserSettings));
    return null;
  }
  
  public static UserSettingsConsistencyTokens getConsistencyTokens(String paramString)
  {
    Utils.ensureOnMainThread();
    String str = (String)FinskyPreferences.userSettingsConsistencyTokens.get(paramString).get();
    UserSettingsConsistencyTokens localUserSettingsConsistencyTokens = new UserSettingsConsistencyTokens();
    if (TextUtils.isEmpty(str)) {
      return localUserSettingsConsistencyTokens;
    }
    decodeFromString(str, localUserSettingsConsistencyTokens);
    return localUserSettingsConsistencyTokens;
  }
  
  public static FinskyExperiments.TargetsChangeListener getExperimentsChangeListener()
  {
    if (sExperimentsChangeListener == null) {
      sExperimentsChangeListener = new FinskyExperiments.TargetsChangeListener()
      {
        public final void onProcessStableTargetsChanged() {}
        
        public final void onTargetsChanged(LongSparseArray<Object> paramAnonymousLongSparseArray1, LongSparseArray<Object> paramAnonymousLongSparseArray2)
        {
          final String str = FinskyApp.get().getCurrentAccountName();
          long[] arrayOfLong;
          int i;
          if (paramAnonymousLongSparseArray1.indexOfKey(12604495L) >= 0)
          {
            UserSettingsCache.clearUserSettings(str);
            arrayOfLong = UserSettingsCache.REFRESH_TRIGGERING_EXPERIMENTS;
            i = arrayOfLong.length;
          }
          for (int j = 0;; j++) {
            if (j < i)
            {
              long l = arrayOfLong[j];
              if ((paramAnonymousLongSparseArray1.indexOfKey(l) >= 0) || (paramAnonymousLongSparseArray2.indexOfKey(l) >= 0)) {
                new Handler(Looper.getMainLooper()).post(new Runnable()
                {
                  public final void run()
                  {
                    UserSettingsCache.updateUserSettings(str);
                  }
                });
              }
            }
            else
            {
              return;
              if (paramAnonymousLongSparseArray2.indexOfKey(12604495L) < 0) {
                break;
              }
              UserSettingsCache.updateUserSettings(str);
              break;
            }
          }
        }
      };
    }
    return sExperimentsChangeListener;
  }
  
  public static PlayAccountProto.PlayAccountUserPurchaseCache getPlayAccountUserPurchaseCache(String paramString)
  {
    UserSettings localUserSettings = getCachedUserSettings(paramString);
    if (localUserSettings == null) {
      return null;
    }
    return localUserSettings.playAccountUserPurchaseCache;
  }
  
  public static void removeListener(UserSettingsCacheListener paramUserSettingsCacheListener)
  {
    mListeners.remove(paramUserSettingsCacheListener);
  }
  
  public static void setCompletedOnboardingUserSetting$505cff1c(String paramString)
  {
    final UserSettings localUserSettings = getCachedUserSettings(paramString);
    if (localUserSettings != null)
    {
      if (localUserSettings.dismissedOnboardings == null) {
        localUserSettings.dismissedOnboardings = new Onboardings();
      }
      Onboarding localOnboarding1 = new Onboarding();
      localOnboarding1.type = 1;
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(localOnboarding1);
      for (Onboarding localOnboarding2 : localUserSettings.dismissedOnboardings.onboarding) {
        if (localOnboarding2.type != localOnboarding1.type) {
          localArrayList.add(localOnboarding2);
        }
      }
      Onboarding[] arrayOfOnboarding2 = new Onboarding[localArrayList.size()];
      localArrayList.toArray(arrayOfOnboarding2);
      localUserSettings.dismissedOnboardings.onboarding = arrayOfOnboarding2;
    }
    UpdateUserSettingRequest localUpdateUserSettingRequest = new UpdateUserSettingRequest();
    localUpdateUserSettingRequest.completedOnboarding = 1;
    localUpdateUserSettingRequest.hasCompletedOnboarding = true;
    final PreferenceFile.SharedPreference localSharedPreference = FinskyPreferences.userSettingsCache.get(paramString);
    FinskyApp.get().getDfeApi(null).updateUserSetting(localUpdateUserSettingRequest, getConsistencyTokens(paramString), new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramAnonymousVolleyError.toString();
        FinskyLog.e("Failed to write user settings: %s", arrayOfObject);
      }
    });
  }
  
  public static void updateConsistencyToken(String paramString, UserSettingsConsistencyTokens.ConsistencyTokenInfo paramConsistencyTokenInfo)
  {
    if ((paramConsistencyTokenInfo == null) || (paramConsistencyTokenInfo.requestHeader == null) || (paramConsistencyTokenInfo.consistencyToken == null))
    {
      FinskyLog.wtf("Invalid argument: updatedTokenInfo missing required field", new Object[0]);
      return;
    }
    Utils.ensureOnMainThread();
    UserSettingsConsistencyTokens localUserSettingsConsistencyTokens = getConsistencyTokens(paramString);
    UserSettingsConsistencyTokens.ConsistencyTokenInfo[] arrayOfConsistencyTokenInfo1 = localUserSettingsConsistencyTokens.consistencyTokenInfo;
    int i = 0;
    if (arrayOfConsistencyTokenInfo1 != null) {
      for (UserSettingsConsistencyTokens.ConsistencyTokenInfo localConsistencyTokenInfo : localUserSettingsConsistencyTokens.consistencyTokenInfo) {
        if (paramConsistencyTokenInfo.requestHeader.equals(localConsistencyTokenInfo.requestHeader))
        {
          localConsistencyTokenInfo.consistencyToken = paramConsistencyTokenInfo.consistencyToken;
          i = 1;
        }
      }
    }
    if (i == 0)
    {
      int j = localUserSettingsConsistencyTokens.consistencyTokenInfo.length;
      UserSettingsConsistencyTokens.ConsistencyTokenInfo[] arrayOfConsistencyTokenInfo2 = new UserSettingsConsistencyTokens.ConsistencyTokenInfo[j + 1];
      System.arraycopy(localUserSettingsConsistencyTokens.consistencyTokenInfo, 0, arrayOfConsistencyTokenInfo2, 0, j);
      arrayOfConsistencyTokenInfo2[j] = paramConsistencyTokenInfo;
      localUserSettingsConsistencyTokens.consistencyTokenInfo = arrayOfConsistencyTokenInfo2;
    }
    FinskyPreferences.userSettingsConsistencyTokens.get(paramString).put(encodeToString(localUserSettingsConsistencyTokens));
    FinskyLog.d("Updated user setting consistency token.", new Object[0]);
  }
  
  public static void updateConsistencyToken(String paramString, UserSettingsConsistencyTokens paramUserSettingsConsistencyTokens)
  {
    if ((paramUserSettingsConsistencyTokens != null) && (paramUserSettingsConsistencyTokens.consistencyTokenInfo != null)) {
      for (int i = 0; i < paramUserSettingsConsistencyTokens.consistencyTokenInfo.length; i++) {
        updateConsistencyToken(paramString, paramUserSettingsConsistencyTokens.consistencyTokenInfo[i]);
      }
    }
  }
  
  public static void updateUserSettings(final String paramString)
  {
    PreferenceFile.SharedPreference localSharedPreference1 = FinskyPreferences.userSettingsCache.get(paramString);
    final PreferenceFile.SharedPreference localSharedPreference2 = FinskyPreferences.userSettingsCacheDirty.get(paramString);
    localSharedPreference2.put(Boolean.valueOf(true));
    FinskyApp.get().getDfeApi(paramString).getUserSettings(getConsistencyTokens(paramString), new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        FinskyLog.w("GetUserSettings failed with error: %s", new Object[] { paramAnonymousVolleyError });
        UserSettingsCache.access$100(false);
      }
    });
  }
  
  public static void updateUserSettingsForAllAccounts()
  {
    Account[] arrayOfAccount = AccountHandler.getAccounts(FinskyApp.get());
    int i = arrayOfAccount.length;
    for (int j = 0; j < i; j++) {
      updateUserSettings(arrayOfAccount[j].name);
    }
  }
  
  public static void updateUserSettingsIfDirty(String paramString)
  {
    if ((FinskyPreferences.userSettingsCache.get(paramString).get() == null) || (((Boolean)FinskyPreferences.userSettingsCacheDirty.get(paramString).get()).booleanValue())) {
      updateUserSettings(paramString);
    }
  }
  
  public static abstract interface UserSettingsCacheListener
  {
    public abstract void onUserSettingsChanged();
    
    public abstract void onUserSettingsRefreshFailed();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.UserSettingsCache
 * JD-Core Version:    0.7.0.1
 */