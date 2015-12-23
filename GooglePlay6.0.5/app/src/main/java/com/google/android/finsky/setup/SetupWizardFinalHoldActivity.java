package com.google.android.finsky.setup;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreBackgroundActionEvent;
import com.google.android.finsky.config.G;
import com.google.android.finsky.services.RestoreService;
import com.google.android.finsky.services.SetupHoldListener;
import com.google.android.finsky.services.VpaService;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.utils.config.GservicesValue;

public class SetupWizardFinalHoldActivity
  extends Activity
  implements SetupHoldListener
{
  public static final String SETUP_WIZARD_RESTORE_FINAL_HOLD_HEADER_LANDSCAPE_URL;
  public static final String SETUP_WIZARD_RESTORE_FINAL_HOLD_HEADER_TABLET_URL;
  public static final String SETUP_WIZARD_RESTORE_FINAL_HOLD_HEADER_URL = (String)G.setupWizardRestoreFinalHoldBlueHeaderUrl.get();
  private static final long SETUP_WIZARD_RESTORE_FINAL_HOLD_LONG_MS = ((Long)G.setupWizardRestoreFinalHoldLongMs.get()).longValue();
  private static final long SETUP_WIZARD_RESTORE_FINAL_HOLD_SHORT_MS;
  private RestoringTitleAnimator mAnimator;
  private Handler mHandler = new Handler(Looper.getMainLooper());
  private boolean mRegisteredWithRestoreService;
  private boolean mRegisteredWithVpaService;
  private long mWatchdogExpirationRealtimeMs;
  private String mWatchdogLastPackage;
  private Runnable mWatchdogRunnable = new Runnable()
  {
    public final void run()
    {
      FinskyLog.w("Watchdog fired, skipping hold.", new Object[0]);
      SetupWizardFinalHoldActivity.this.setResultAndFinish(1);
      PlayStore.PlayStoreBackgroundActionEvent localPlayStoreBackgroundActionEvent = FinskyEventLog.obtainPlayStoreBackgroundActionEvent();
      localPlayStoreBackgroundActionEvent.type = 126;
      localPlayStoreBackgroundActionEvent.hasType = true;
      if (SetupWizardFinalHoldActivity.this.mWatchdogLastPackage != null)
      {
        localPlayStoreBackgroundActionEvent.document = SetupWizardFinalHoldActivity.this.mWatchdogLastPackage;
        localPlayStoreBackgroundActionEvent.hasDocument = true;
      }
      FinskyApp.get().getEventLogger().sendBackgroundEventToSinks(localPlayStoreBackgroundActionEvent);
    }
  };
  
  static
  {
    SETUP_WIZARD_RESTORE_FINAL_HOLD_HEADER_LANDSCAPE_URL = (String)G.setupWizardRestoreFinalHoldHeaderBlueLandscapeUrl.get();
    SETUP_WIZARD_RESTORE_FINAL_HOLD_HEADER_TABLET_URL = (String)G.setupWizardRestoreFinalHoldBlueHeaderTabletUrl.get();
    SETUP_WIZARD_RESTORE_FINAL_HOLD_SHORT_MS = ((Long)G.setupWizardRestoreFinalHoldShortMs.get()).longValue();
  }
  
  public static Intent createIntent()
  {
    return new Intent(FinskyApp.get(), SetupWizardFinalHoldActivity.class);
  }
  
  private boolean finishIfTimeout()
  {
    if (SETUP_WIZARD_RESTORE_FINAL_HOLD_SHORT_MS == -1L) {}
    while (SystemClock.elapsedRealtime() <= this.mWatchdogExpirationRealtimeMs) {
      return false;
    }
    this.mWatchdogRunnable.run();
    return true;
  }
  
  private boolean registerListener()
  {
    if (RestoreService.registerHoldListener(this))
    {
      this.mRegisteredWithRestoreService = true;
      return true;
    }
    if (VpaService.registerListener(this))
    {
      this.mRegisteredWithVpaService = true;
      return true;
    }
    return false;
  }
  
  private void setResultAndFinish(int paramInt)
  {
    this.mHandler.removeCallbacks(this.mWatchdogRunnable);
    setResult(paramInt);
    finish();
  }
  
  private void setWatchdog(long paramLong, String paramString)
  {
    if (SETUP_WIZARD_RESTORE_FINAL_HOLD_SHORT_MS == -1L)
    {
      FinskyLog.d("Watchdog disabled", new Object[0]);
      return;
    }
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Long.valueOf(paramLong / 1000L);
    arrayOfObject[1] = paramString;
    FinskyLog.d("Set watchdog to %d sec (package %s)", arrayOfObject);
    this.mWatchdogExpirationRealtimeMs = (paramLong + SystemClock.elapsedRealtime());
    this.mWatchdogLastPackage = paramString;
    this.mHandler.removeCallbacks(this.mWatchdogRunnable);
    this.mHandler.postDelayed(this.mWatchdogRunnable, paramLong);
  }
  
  private void unregisterListeners()
  {
    if (this.mRegisteredWithVpaService)
    {
      VpaService.registerListener(null);
      this.mRegisteredWithVpaService = false;
    }
    if (this.mRegisteredWithRestoreService)
    {
      RestoreService.registerHoldListener(null);
      this.mRegisteredWithRestoreService = false;
    }
  }
  
  @TargetApi(19)
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView((ViewGroup)LayoutInflater.from(this).inflate(2130969104, null));
    final View localView = getWindow().getDecorView();
    localView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
    {
      public final boolean onPreDraw()
      {
        int i = 0x1602 | localView.getSystemUiVisibility();
        localView.setSystemUiVisibility(i);
        return true;
      }
    });
    localView.setSystemUiVisibility(0x1602 | localView.getSystemUiVisibility());
    FifeImageView localFifeImageView = (FifeImageView)findViewById(2131756112);
    this.mAnimator = new RestoringTitleAnimator((TextView)findViewById(2131756114));
    localFifeImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    String str;
    if (getResources().getBoolean(2131427344))
    {
      str = SETUP_WIZARD_RESTORE_FINAL_HOLD_HEADER_TABLET_URL;
      localFifeImageView.setImage(str, true, FinskyApp.get().mBitmapLoader);
      if (paramBundle != null) {
        break label169;
      }
      setWatchdog(SETUP_WIZARD_RESTORE_FINAL_HOLD_SHORT_MS, null);
    }
    label169:
    do
    {
      return;
      if (getResources().getConfiguration().orientation == 2)
      {
        str = SETUP_WIZARD_RESTORE_FINAL_HOLD_HEADER_LANDSCAPE_URL;
        break;
      }
      str = SETUP_WIZARD_RESTORE_FINAL_HOLD_HEADER_URL;
      break;
      this.mWatchdogExpirationRealtimeMs = paramBundle.getLong("watchdog_expiration_ms");
      this.mWatchdogLastPackage = paramBundle.getString("watchdog_package");
    } while (finishIfTimeout());
    setWatchdog(this.mWatchdogExpirationRealtimeMs - SystemClock.elapsedRealtime(), this.mWatchdogLastPackage);
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    this.mHandler.removeCallbacks(this.mWatchdogRunnable);
  }
  
  protected void onPause()
  {
    super.onPause();
    RestoringTitleAnimator localRestoringTitleAnimator = this.mAnimator;
    localRestoringTitleAnimator.mRunning = false;
    localRestoringTitleAnimator.mEllipseView.removeCallbacks(localRestoringTitleAnimator.mCallback);
    unregisterListeners();
  }
  
  protected void onResume()
  {
    super.onResume();
    finishIfTimeout();
    RestoringTitleAnimator localRestoringTitleAnimator = this.mAnimator;
    localRestoringTitleAnimator.mRunning = true;
    localRestoringTitleAnimator.mEllipseView.removeCallbacks(localRestoringTitleAnimator.mCallback);
    localRestoringTitleAnimator.mEllipseView.postDelayed(localRestoringTitleAnimator.mCallback, 500L);
    if (!registerListener()) {
      setResultAndFinish(-1);
    }
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putLong("watchdog_expiration_ms", this.mWatchdogExpirationRealtimeMs);
    paramBundle.putString("watchdog_package", this.mWatchdogLastPackage);
  }
  
  public final void onStatusChange$4a6d67e4(int paramInt, String paramString1, boolean paramBoolean, String paramString2)
  {
    Object[] arrayOfObject = new Object[5];
    String str;
    if (this.mRegisteredWithVpaService)
    {
      str = "VPA";
      arrayOfObject[0] = str;
      arrayOfObject[1] = Integer.valueOf(paramInt);
      arrayOfObject[2] = paramString1;
      arrayOfObject[3] = Boolean.valueOf(paramBoolean);
      arrayOfObject[4] = paramString2;
      FinskyLog.d("Final hold status change: listener=%s code=%d package=%s cancelable=%b (%s)", arrayOfObject);
      switch (paramInt)
      {
      default: 
        FinskyLog.wtf("Unknown event code - finishing early", new Object[0]);
        setResultAndFinish(-1);
      }
    }
    do
    {
      return;
      str = "Restore";
      break;
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          SetupWizardFinalHoldActivity.this.unregisterListeners();
          if (!SetupWizardFinalHoldActivity.this.registerListener()) {
            SetupWizardFinalHoldActivity.this.setResultAndFinish(-1);
          }
        }
      });
      return;
      setWatchdog(SETUP_WIZARD_RESTORE_FINAL_HOLD_SHORT_MS, null);
      return;
    } while ((this.mWatchdogLastPackage != null) && (this.mWatchdogLastPackage.equals(paramString1)));
    setWatchdog(SETUP_WIZARD_RESTORE_FINAL_HOLD_LONG_MS, paramString1);
  }
  
  private static final class RestoringTitleAnimator
  {
    private static final int[] ELLIPSES = { 2131363020, 2131363019, 2131363021 };
    Runnable mCallback = new Runnable()
    {
      public final void run()
      {
        SetupWizardFinalHoldActivity.RestoringTitleAnimator.access$400(SetupWizardFinalHoldActivity.RestoringTitleAnimator.this);
        if (SetupWizardFinalHoldActivity.RestoringTitleAnimator.this.mRunning) {
          SetupWizardFinalHoldActivity.RestoringTitleAnimator.this.mEllipseView.postDelayed(this, 500L);
        }
      }
    };
    TextView mEllipseView;
    private int mPosition = 0;
    boolean mRunning = false;
    
    public RestoringTitleAnimator(TextView paramTextView)
    {
      this.mEllipseView = paramTextView;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.setup.SetupWizardFinalHoldActivity
 * JD-Core Version:    0.7.0.1
 */