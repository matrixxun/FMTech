package com.google.android.finsky.autoupdate;

import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.config.G;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.TosUtil;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.List;

public final class RapidAutoUpdatePolicy
  implements AutoUpdatePolicy
{
  private List<Config> mConfig;
  private final FinskyExperiments mExperiments;
  private Integer mMaxTosAcceptedVersion;
  
  public RapidAutoUpdatePolicy(FinskyExperiments paramFinskyExperiments)
  {
    this.mExperiments = paramFinskyExperiments;
  }
  
  public final void apply(AutoUpdateEntry paramAutoUpdateEntry)
  {
    if (this.mConfig == null) {
      this.mConfig = Config.getConfigs();
    }
    int i = this.mConfig.size();
    int j = 0;
    Config localConfig1;
    if (j < i)
    {
      Config localConfig2 = (Config)this.mConfig.get(j);
      if (localConfig2.mPackageName.equals(paramAutoUpdateEntry.mDocument.getAppDetails().packageName))
      {
        localConfig1 = localConfig2;
        label71:
        if (localConfig1 != null) {
          break label89;
        }
      }
    }
    label89:
    do
    {
      return;
      j++;
      break;
      localConfig1 = null;
      break label71;
      paramAutoUpdateEntry.mLoggingOptions = (0x1 | paramAutoUpdateEntry.mLoggingOptions);
    } while (!this.mExperiments.isEnabled(12603067L));
    int n;
    label179:
    int k;
    label182:
    int m;
    if ((localConfig1.mPackageName.equals(paramAutoUpdateEntry.mDocument.getAppDetails().packageName)) && ((0x4 & localConfig1.mFlags) != 0))
    {
      n = paramAutoUpdateEntry.mPackageState.installedVersion;
      if (n < 0)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = localConfig1.mPackageName;
        FinskyLog.w("Server thinks we have an asset that we don't have : %s", arrayOfObject);
      }
    }
    else
    {
      k = 0;
      if (k == 0) {
        break label310;
      }
      if ("com.android.chrome".equals(paramAutoUpdateEntry.mDocument.getAppDetails().packageName)) {
        break label312;
      }
      m = 1;
      label208:
      if (m == 0) {
        break label361;
      }
      paramAutoUpdateEntry.mHoldOffTrigger = (0xFFFFFFE3 & paramAutoUpdateEntry.mHoldOffTrigger);
      if ((0x2 & localConfig1.mFlags) != 0) {
        paramAutoUpdateEntry.mHoldOffTrigger = (0xFFFFFFFD & paramAutoUpdateEntry.mHoldOffTrigger);
      }
      paramAutoUpdateEntry.mInstallOptions = 0;
      if ((0x1 & localConfig1.mFlags) == 0) {
        break label363;
      }
    }
    label310:
    label312:
    label361:
    label363:
    for (paramAutoUpdateEntry.mInstallOptions = (0xFFFFFFFA & paramAutoUpdateEntry.mInstallOptions);; paramAutoUpdateEntry.mInstallOptions = (0x5 | paramAutoUpdateEntry.mInstallOptions))
    {
      paramAutoUpdateEntry.mInstallReason = "rapid_auto_update";
      return;
      if (localConfig1.mMask > 0) {
        n %= localConfig1.mMask;
      }
      if (n >= localConfig1.mMinVersionCode) {
        break label179;
      }
      k = 1;
      break label182;
      break;
      if (this.mMaxTosAcceptedVersion == null) {
        this.mMaxTosAcceptedVersion = Integer.valueOf(TosUtil.getMaxAcceptedTosVersion());
      }
      if (this.mMaxTosAcceptedVersion.intValue() >= ((Integer)G.rapidAutoUpdateTosMinVersion.get()).intValue())
      {
        m = 1;
        break label208;
      }
      m = 0;
      break label208;
      break;
    }
  }
  
  private static final class Config
  {
    final int mFlags;
    final int mMask;
    final int mMinVersionCode;
    final String mPackageName;
    
    private Config(String paramString)
    {
      String[] arrayOfString = paramString.split(",");
      if (arrayOfString.length != 4)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(arrayOfString.length);
        arrayOfObject[1] = paramString;
        throw new IllegalArgumentException(String.format("Invalid number of items for the Rapid Auto Update data (Expecting 4, got %d). Data: %s", arrayOfObject));
      }
      try
      {
        this.mPackageName = arrayOfString[0];
        this.mMinVersionCode = Integer.parseInt(arrayOfString[1]);
        this.mMask = Integer.parseInt(arrayOfString[2]);
        this.mFlags = Integer.parseInt(arrayOfString[3]);
        return;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw new IllegalArgumentException(String.format("Invalid format for the Rapid Auto Update data: %s", new Object[] { paramString }));
      }
    }
    
    static List<Config> getConfigs()
    {
      ArrayList localArrayList = new ArrayList();
      String[] arrayOfString = ((String)G.rapidAutoUpdateListing.get()).split(";");
      int i = 0;
      int j = arrayOfString.length;
      for (;;)
      {
        if (i < j) {
          try
          {
            localArrayList.add(new Config(arrayOfString[i]));
            i++;
          }
          catch (IllegalArgumentException localIllegalArgumentException)
          {
            for (;;)
            {
              FinskyLog.w(localIllegalArgumentException.getMessage(), new Object[0]);
            }
          }
        }
      }
      return localArrayList;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.autoupdate.RapidAutoUpdatePolicy
 * JD-Core Version:    0.7.0.1
 */