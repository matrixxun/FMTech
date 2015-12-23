package com.google.android.finsky.autoupdate;

import com.google.android.finsky.appstate.GmsCoreHelper;
import com.google.android.finsky.config.G;
import com.google.android.play.utils.config.GservicesValue;

public final class GmsCoreAutoUpdatePolicy
  implements AutoUpdatePolicy
{
  public final void apply(AutoUpdateEntry paramAutoUpdateEntry)
  {
    if (!GmsCoreHelper.isGmsCore(paramAutoUpdateEntry.mDocument)) {
      return;
    }
    paramAutoUpdateEntry.mLoggingOptions = (0x1 | paramAutoUpdateEntry.mLoggingOptions);
    paramAutoUpdateEntry.mPendingUpdateNotificationOptions = (0x1 | paramAutoUpdateEntry.mPendingUpdateNotificationOptions);
    if (!((Boolean)G.gmsCoreAutoUpdateEnabled.get()).booleanValue())
    {
      paramAutoUpdateEntry.mHoldOffReason = (0x100 | paramAutoUpdateEntry.mHoldOffReason);
      return;
    }
    paramAutoUpdateEntry.mHoldOffTrigger = 4;
    if (((Boolean)G.gmsCoreAutoUpdateProjectionHoldoff.get()).booleanValue()) {
      paramAutoUpdateEntry.mHoldOffTrigger = (0x40 | paramAutoUpdateEntry.mHoldOffTrigger);
    }
    paramAutoUpdateEntry.mInstallOptions = 0;
    paramAutoUpdateEntry.mInstallPriority = 2;
    paramAutoUpdateEntry.mInstallOrder = -1000;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.autoupdate.GmsCoreAutoUpdatePolicy
 * JD-Core Version:    0.7.0.1
 */