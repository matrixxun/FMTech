package com.google.android.finsky.billing.refund;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.utils.FinskyLog;

public class RequestRefundActivity
  extends AppCompatActivity
  implements RequestRefundFragment.Listener, PlayStoreUiElementNode
{
  protected String mAccountName;
  protected Document mDocument;
  private FinskyEventLog mEventLog;
  private RequestRefundFragment mRequestRefundFragment;
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(1150);
  
  public static Intent createIntent(String paramString, Document paramDocument)
  {
    Intent localIntent = new Intent(FinskyApp.get(), RequestRefundActivity.class);
    localIntent.putExtra("RequestRefundActivity.accountName", paramString);
    localIntent.putExtra("RequestRefundActivity.document", paramDocument);
    return localIntent;
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyLog.wtf("Not using tree impressions.", new Object[0]);
  }
  
  public void finish()
  {
    Intent localIntent = new Intent();
    if (this.mRequestRefundFragment != null)
    {
      this.mEventLog.logPathImpression$7d139cbf(603, this);
      RequestRefundResult localRequestRefundResult = this.mRequestRefundFragment.mRequestRefundResult;
      if (localRequestRefundResult != null)
      {
        localIntent.putExtra("RequestRefundActivity.requestRefundResult", localRequestRefundResult);
        setResult(-1, localIntent);
        super.finish();
        return;
      }
    }
    setResult(0, localIntent);
    super.finish();
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return null;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public void onBackPressed()
  {
    this.mEventLog.logClickEvent(600, null, this);
    super.onBackPressed();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(getLayoutInflater().inflate(2130969067, null));
    Intent localIntent = getIntent();
    this.mAccountName = localIntent.getStringExtra("RequestRefundActivity.accountName");
    this.mDocument = ((Document)localIntent.getParcelableExtra("RequestRefundActivity.document"));
    this.mEventLog = FinskyApp.get().getEventLogger(this.mAccountName);
    if (paramBundle == null)
    {
      this.mEventLog.logPathImpression(0L, this);
      FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
      localFragmentTransaction.add(2131755234, RequestRefundFragment.newInstance(this.mAccountName, this.mDocument));
      localFragmentTransaction.commit();
    }
  }
  
  public final void onFinished()
  {
    finish();
  }
  
  protected void onStart()
  {
    super.onStart();
    this.mRequestRefundFragment = ((RequestRefundFragment)getSupportFragmentManager().findFragmentById(2131755234));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.refund.RequestRefundActivity
 * JD-Core Version:    0.7.0.1
 */