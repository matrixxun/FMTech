package com.google.android.finsky.wear;

import android.accounts.Account;
import android.content.Context;
import android.os.AsyncTask;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.api.DfeApiProvider;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.MultiWayUpdateController;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.GmsCoreHelper;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.config.G;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.utils.config.GservicesValue;
import java.util.List;
import java.util.Map;

public final class WearUpdateChecker
{
  private static final String[] GMS_CORE_ONLY_WHITELIST = { "com.google.android.gms" };
  final AppStates mAppStates;
  final Context mContext;
  private final DfeApiProvider mDfeApiProvider;
  private MultiWayUpdateController mDfeModel;
  private final WearInstaller mInstaller;
  final Libraries mLibraries;
  final String mNodeId;
  private final String mSelfUpdateAccountName;
  private final int mSelfUpdateVersionCode;
  
  public WearUpdateChecker(Context paramContext, String paramString1, Libraries paramLibraries, AppStates paramAppStates, DfeApiProvider paramDfeApiProvider, WearInstaller paramWearInstaller, int paramInt, String paramString2)
  {
    this.mContext = paramContext;
    this.mNodeId = paramString1;
    this.mLibraries = paramLibraries;
    this.mAppStates = paramAppStates;
    this.mDfeApiProvider = paramDfeApiProvider;
    this.mInstaller = paramWearInstaller;
    this.mSelfUpdateVersionCode = paramInt;
    this.mSelfUpdateAccountName = paramString2;
  }
  
  static void notifyListener$5487b8c4(AutoUpdateCompletionStatusListener paramAutoUpdateCompletionStatusListener, boolean paramBoolean)
  {
    if (paramAutoUpdateCompletionStatusListener != null) {
      paramAutoUpdateCompletionStatusListener.updateCheckComplete$25decb5(paramBoolean);
    }
  }
  
  public static abstract interface AutoUpdateCompletionStatusListener
  {
    public abstract void updateCheckComplete$25decb5(boolean paramBoolean);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.wear.WearUpdateChecker
 * JD-Core Version:    0.7.0.1
 */