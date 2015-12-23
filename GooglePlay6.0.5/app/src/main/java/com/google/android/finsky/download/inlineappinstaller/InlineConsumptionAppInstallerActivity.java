package com.google.android.finsky.download.inlineappinstaller;

import android.accounts.Account;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.layout.play.RootUiElementNode;
import com.google.android.finsky.navigationmanager.ConsumptionUtils;
import com.google.android.finsky.protos.Acquisition.SuccessInfo;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.ParcelableProto;

public class InlineConsumptionAppInstallerActivity
  extends AppCompatActivity
  implements InlineConsumptionAppInstallerFragment.Listener, RootUiElementNode
{
  private static final boolean ENABLE_DISMISS_ON_TOUCH_OUTSIDE;
  private Account mAccount;
  private FinskyEventLog mEventLog;
  private final Rect mHitRect = new Rect();
  private DocV2 mMediaDoc;
  private boolean mSucceeded;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 11) {}
    for (boolean bool = true;; bool = false)
    {
      ENABLE_DISMISS_ON_TOUCH_OUTSIDE = bool;
      return;
    }
  }
  
  public static Intent createIntent$6a617ce3(Account paramAccount, DocV2 paramDocV2)
  {
    Intent localIntent = new Intent(FinskyApp.get(), InlineConsumptionAppInstallerActivity.class);
    localIntent.putExtra("account", paramAccount);
    localIntent.putExtra("mediaDoc", ParcelableProto.forProto(paramDocV2));
    return localIntent;
  }
  
  private void dismiss()
  {
    setResult(0);
    finish();
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyLog.wtf("Not using tree impressions.", new Object[0]);
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if (ENABLE_DISMISS_ON_TOUCH_OUTSIDE)
    {
      getWindow().getDecorView().getHitRect(this.mHitRect);
      if ((paramMotionEvent.getAction() == 0) && (!this.mHitRect.contains((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY())))
      {
        this.mEventLog.logClickEvent(601, null, this);
        dismiss();
      }
    }
    return super.dispatchTouchEvent(paramMotionEvent);
  }
  
  public void finish()
  {
    InlineConsumptionAppInstallerFragment localInlineConsumptionAppInstallerFragment = (InlineConsumptionAppInstallerFragment)getSupportFragmentManager().findFragmentById(2131755234);
    if (localInlineConsumptionAppInstallerFragment != null)
    {
      if (!this.mSucceeded) {
        break label45;
      }
      setResult(-1);
    }
    for (;;)
    {
      this.mEventLog.logPathImpression$7d139cbf(603, this);
      super.finish();
      return;
      label45:
      if (localInlineConsumptionAppInstallerFragment.mShouldGoToAppDetailsUponExit) {
        startActivity(IntentUtils.createViewDocumentUrlIntent(this, DfeUtils.createDetailsUrlFromId(IntentUtils.getPackageName(this.mMediaDoc.backendId))));
      }
      setResult(0);
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
    return FinskyEventLog.obtainPlayStoreUiElement(5100);
  }
  
  public void onBackPressed()
  {
    this.mEventLog.logClickEvent(600, null, this);
    dismiss();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(getLayoutInflater().inflate(2130968805, null));
    Intent localIntent = getIntent();
    this.mAccount = ((Account)localIntent.getParcelableExtra("account"));
    this.mEventLog = FinskyApp.get().getEventLogger(this.mAccount);
    this.mMediaDoc = ((DocV2)ParcelableProto.getProtoFromIntent(localIntent, "mediaDoc"));
    Acquisition.SuccessInfo localSuccessInfo = (Acquisition.SuccessInfo)ParcelableProto.getProtoFromIntent(localIntent, "successInfo");
    if (paramBundle == null)
    {
      this.mEventLog.logPathImpression(0L, this);
      FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
      localFragmentTransaction.add(2131755234, InlineConsumptionAppInstallerFragment.newInstance(this.mAccount, this.mMediaDoc, localSuccessInfo));
      localFragmentTransaction.commit();
    }
  }
  
  public final void onFinished(boolean paramBoolean)
  {
    this.mSucceeded = paramBoolean;
    if (this.mSucceeded)
    {
      Document localDocument = new Document(this.mMediaDoc);
      ConsumptionUtils.openItem(this, this.mAccount, localDocument, getSupportFragmentManager(), null, 2, null);
    }
    finish();
  }
  
  public final void startNewImpression()
  {
    FinskyLog.wtf("Not using impression id's.", new Object[0]);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.inlineappinstaller.InlineConsumptionAppInstallerActivity
 * JD-Core Version:    0.7.0.1
 */