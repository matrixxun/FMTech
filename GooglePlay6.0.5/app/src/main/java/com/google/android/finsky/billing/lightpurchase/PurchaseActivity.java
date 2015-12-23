package com.google.android.finsky.billing.lightpurchase;

import android.accounts.Account;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AccessRestrictedActivity;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElementInfo;
import com.google.android.finsky.billing.lightpurchase.purchasesteps.SuccessStepWithAuthChoices;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.layout.play.RootUiElementNode;
import com.google.android.finsky.receivers.FlushLogsReceiver;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.RestrictedDeviceHelper;
import com.google.android.finsky.utils.RestrictedDeviceHelper.OnCompleteListener;
import com.google.android.finsky.utils.StoreTypeValidator;

public class PurchaseActivity
  extends AppCompatActivity
  implements PurchaseFragment.Listener, RootUiElementNode
{
  private static final boolean DISABLE_DISMISS_ON_TOUCH_OUTSIDE;
  protected Account mAccount;
  private FinskyEventLog mEventLog;
  private final Rect mHitRect = new Rect();
  private byte[] mInitialServerLogsCookie;
  protected PurchaseParams mParams;
  
  static
  {
    if (Build.VERSION.SDK_INT < 11) {}
    for (boolean bool = true;; bool = false)
    {
      DISABLE_DISMISS_ON_TOUCH_OUTSIDE = bool;
      return;
    }
  }
  
  public static Intent createIntent(Account paramAccount, PurchaseParams paramPurchaseParams, byte[] paramArrayOfByte, Bundle paramBundle)
  {
    Intent localIntent = new Intent(FinskyApp.get(), PurchaseActivity.class);
    localIntent.putExtra("PurchaseActivity.account", paramAccount);
    localIntent.putExtra("PurchaseActivity.params", paramPurchaseParams);
    localIntent.putExtra("PurchaseActivity.appDownloadSizeWarningArgs", paramBundle);
    localIntent.putExtra("PurchaseActivity.serverLogsCookie", paramArrayOfByte);
    return localIntent;
  }
  
  private void dismissIfPossible(int paramInt, String paramString)
  {
    PurchaseFragment localPurchaseFragment = getPurchaseFragment();
    int i;
    if (localPurchaseFragment != null)
    {
      if (!(localPurchaseFragment.mCurrentFragment instanceof SuccessStepWithAuthChoices)) {
        break label37;
      }
      i = 0;
    }
    while (i != 0)
    {
      setResult(0);
      finish();
      return;
      label37:
      if (localPurchaseFragment.mSidecar != null) {
        if ((localPurchaseFragment.mSidecar.mState == 7) || (localPurchaseFragment.mSidecar.mState == 12))
        {
          if (paramInt != 1) {
            i = 0;
          }
        }
        else
        {
          if ((localPurchaseFragment.mSidecar.mState == 1) && (localPurchaseFragment.mSidecar.mSubstate == 2))
          {
            i = 0;
            continue;
          }
          if ((localPurchaseFragment.mSidecar.mState == 5) && (paramInt == 1) && (localPurchaseFragment.mSidecar.mGiftEmailParams != null))
          {
            localPurchaseFragment.mSidecar.updateGiftEmailParams(null);
            i = 0;
            continue;
          }
        }
      }
      i = 1;
    }
    FinskyLog.d("PurchaseFragment not dismissable by %s, ignore.", new Object[] { paramString });
  }
  
  public static boolean extractPostSuccessItemOpened(Intent paramIntent)
  {
    return paramIntent.getBooleanExtra("PurchaseActivity.postSuccessItemOpened", false);
  }
  
  private PurchaseFragment getPurchaseFragment()
  {
    return (PurchaseFragment)getSupportFragmentManager().findFragmentById(2131755234);
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyLog.wtf("Not using tree impressions.", new Object[0]);
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if (DISABLE_DISMISS_ON_TOUCH_OUTSIDE) {
      return super.dispatchTouchEvent(paramMotionEvent);
    }
    getWindow().getDecorView().getHitRect(this.mHitRect);
    if ((paramMotionEvent.getAction() == 0) && (!this.mHitRect.contains((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY())))
    {
      this.mEventLog.logClickEvent(601, null, this);
      dismissIfPossible(2, "click outside");
    }
    return super.dispatchTouchEvent(paramMotionEvent);
  }
  
  public void finish()
  {
    PurchaseFragment localPurchaseFragment = getPurchaseFragment();
    if (localPurchaseFragment != null)
    {
      onFinish(localPurchaseFragment);
      this.mEventLog.logPathImpression$7d139cbf(603, this);
    }
    for (;;)
    {
      super.finish();
      return;
      FinskyLog.d("Purchase fragment null.", new Object[0]);
    }
  }
  
  public final void flushImpression()
  {
    FinskyLog.wtf("Not using tree impressions.", new Object[0]);
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return null;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    PlayStore.PlayStoreUiElement localPlayStoreUiElement = FinskyEventLog.obtainPlayStoreUiElement(700);
    PlayStore.PlayStoreUiElementInfo localPlayStoreUiElementInfo = new PlayStore.PlayStoreUiElementInfo();
    localPlayStoreUiElementInfo.document = this.mParams.docidStr;
    localPlayStoreUiElementInfo.hasDocument = true;
    localPlayStoreUiElementInfo.offerType = this.mParams.offerType;
    localPlayStoreUiElementInfo.hasOfferType = true;
    localPlayStoreUiElement.clientLogsCookie = localPlayStoreUiElementInfo;
    PurchaseFragment localPurchaseFragment = getPurchaseFragment();
    byte[] arrayOfByte;
    if (localPurchaseFragment != null) {
      if ((localPurchaseFragment.mSidecar != null) && (localPurchaseFragment.mSidecar.mServerLogsCookie != null)) {
        arrayOfByte = localPurchaseFragment.mSidecar.mServerLogsCookie;
      }
    }
    for (;;)
    {
      FinskyEventLog.setServerLogCookie(localPlayStoreUiElement, arrayOfByte);
      return localPlayStoreUiElement;
      arrayOfByte = localPurchaseFragment.mDocServerLogsCookie;
      continue;
      arrayOfByte = this.mInitialServerLogsCookie;
    }
  }
  
  protected void handleAccessRestriction()
  {
    setResult(0);
  }
  
  public void onBackPressed()
  {
    this.mEventLog.logClickEvent(600, null, this);
    dismissIfPossible(1, "back press");
  }
  
  protected void onCreate(final Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(getLayoutInflater().inflate(2130968805, null));
    final Intent localIntent = getIntent();
    this.mParams = ((PurchaseParams)localIntent.getParcelableExtra("PurchaseActivity.params"));
    this.mAccount = ((Account)localIntent.getParcelableExtra("PurchaseActivity.account"));
    this.mEventLog = FinskyApp.get().getEventLogger(this.mAccount);
    this.mInitialServerLogsCookie = localIntent.getByteArrayExtra("PurchaseActivity.serverLogsCookie");
    if (!StoreTypeValidator.isValid(this))
    {
      AccessRestrictedActivity.showInvalidStoreTypeUI(this);
      finish();
      return;
    }
    RestrictedDeviceHelper.isLimitedOrSchoolEduUser(new RestrictedDeviceHelper.OnCompleteListener()
    {
      public final void onComplete(boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
        {
          AccessRestrictedActivity.showLimitedPurchaseUI(PurchaseActivity.this);
          PurchaseActivity.this.handleAccessRestriction();
          PurchaseActivity.this.finish();
          return;
        }
        PurchaseActivity.access$000(PurchaseActivity.this, paramBundle, localIntent);
      }
    });
  }
  
  protected void onFinish(PurchaseFragment paramPurchaseFragment)
  {
    int i = -1;
    if (paramPurchaseFragment.mSucceeded)
    {
      Intent localIntent = new Intent();
      localIntent.putExtra("PurchaseActivity.postSuccessItemOpened", paramPurchaseFragment.mPostSuccessItemOpened);
      setResult(i, localIntent);
      return;
    }
    int j;
    label45:
    PurchaseFragment.PurchaseError localPurchaseError;
    if (paramPurchaseFragment.mError != null)
    {
      j = 1;
      if (j == 0) {
        break label120;
      }
      localPurchaseError = paramPurchaseFragment.mError;
      if (localPurchaseError == null) {
        break label122;
      }
    }
    label120:
    label122:
    for (int k = localPurchaseError.errorType;; k = i)
    {
      if (localPurchaseError != null) {
        i = localPurchaseError.errorSubtype;
      }
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(k);
      arrayOfObject[1] = Integer.valueOf(i);
      FinskyLog.e("Purchase failed: %d / %d", arrayOfObject);
      setResult(0);
      return;
      j = 0;
      break label45;
      break;
    }
  }
  
  public final void onFinished()
  {
    finish();
  }
  
  protected void onPause()
  {
    super.onPause();
    FlushLogsReceiver.scheduleLogsFlushOnAppExit();
  }
  
  protected void onResume()
  {
    super.onResume();
    FlushLogsReceiver.cancelLogsFlush();
  }
  
  public final void startNewImpression()
  {
    FinskyLog.wtf("Not using impression id's.", new Object[0]);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.PurchaseActivity
 * JD-Core Version:    0.7.0.1
 */