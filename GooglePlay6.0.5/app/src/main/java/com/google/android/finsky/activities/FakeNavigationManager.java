package com.google.android.finsky.activities;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.billing.lightpurchase.LightPurchaseFlowActivity;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.ConsumptionUtils;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.DocUtils.OfferFilter;
import com.google.android.finsky.utils.IntentUtils;

class FakeNavigationManager
  extends NavigationManager
{
  private Activity mActivity;
  
  public FakeNavigationManager(Activity paramActivity)
  {
    super(null);
    this.mActivity = paramActivity;
  }
  
  public final void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener paramOnBackStackChangedListener) {}
  
  public final void buy(Account paramAccount, Document paramDocument, int paramInt, DocUtils.OfferFilter paramOfferFilter, String paramString)
  {
    Intent localIntent = LightPurchaseFlowActivity.createIntent$202310a9(paramAccount, paramDocument, paramInt, paramOfferFilter, paramDocument.mDocument.serverLogsCookie, paramString, null);
    this.mActivity.startActivityForResult(localIntent, 33);
  }
  
  public final boolean canGoUp()
  {
    return true;
  }
  
  public final boolean canSearch()
  {
    return false;
  }
  
  public final PageFragment getActivePage()
  {
    return null;
  }
  
  protected final Activity getActivityForResolveLink()
  {
    return this.mActivity;
  }
  
  public final boolean goBack()
  {
    this.mActivity.onBackPressed();
    return true;
  }
  
  public final void goBrowse(String paramString1, String paramString2, int paramInt, DfeToc paramDfeToc, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    Intent localIntent = IntentUtils.createBrowseIntent(this.mActivity, paramString1, paramString2, paramInt, false);
    this.mActivity.startActivity(localIntent);
    this.mActivity.finish();
  }
  
  public final void goToDocPage(String paramString)
  {
    Intent localIntent = IntentUtils.createViewDocumentUrlIntent(this.mActivity, paramString);
    this.mActivity.startActivity(localIntent);
    this.mActivity.finish();
  }
  
  public final void goToImagesLightbox(Document paramDocument, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (this.mActivity == null) {
      return;
    }
    if (FinskyApp.get().getExperiments().isEnabled(12602819L))
    {
      ScreenshotsActivityV2.show(this.mActivity, paramDocument, paramInt1, paramInt2);
      return;
    }
    ScreenshotsActivity.show(this.mActivity, paramDocument, paramInt1, paramInt2, false);
  }
  
  public boolean goUp()
  {
    this.mActivity.onBackPressed();
    return true;
  }
  
  public final void init(MainActivity paramMainActivity) {}
  
  public final boolean isBackStackEmpty()
  {
    return false;
  }
  
  public final void openItem(Account paramAccount, Document paramDocument, boolean paramBoolean)
  {
    int i = 1;
    if (!ConsumptionUtils.openItem(this.mActivity, paramAccount, paramDocument, this.mFragmentManager, null, i, null)) {}
    for (;;)
    {
      if ((i != 0) && (paramBoolean)) {
        this.mActivity.finish();
      }
      return;
      i = 0;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.FakeNavigationManager
 * JD-Core Version:    0.7.0.1
 */