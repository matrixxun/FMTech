package com.google.android.finsky.billing.giftcard.steps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.billing.SuccessStep;
import com.google.android.finsky.billing.giftcard.RedeemCodeFragment;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.protos.Acquisition.PostSuccessAction;
import com.google.android.finsky.protos.Acquisition.SetupWizardTemplate;
import com.google.android.finsky.protos.Acquisition.TwoIconMessagesTemplate;
import com.google.android.finsky.utils.FinskyLog;

public final class RedeemSuccessStep
  extends SuccessStep<RedeemCodeFragment>
{
  private final PlayStore.PlayStoreUiElement mPlayStoreUiElement = FinskyEventLog.obtainPlayStoreUiElement(885);
  
  protected final void bindSetupWizardTemplate(Acquisition.SetupWizardTemplate paramSetupWizardTemplate)
  {
    if (!this.mArguments.getBoolean("RedeemSuccessStep.in_setup_wizard")) {
      throw new UnsupportedOperationException("Setup Wizard template is not supported outside of the Setup Wizard.");
    }
    Button localButton = (Button)((RedeemCodeFragment)this.mParentFragment).mMainView.findViewById(2131756119);
    if (localButton != null) {
      localButton.setVisibility(8);
    }
    getActivity().setTitle(paramSetupWizardTemplate.title);
    bindHtmlMessage(Html.fromHtml(paramSetupWizardTemplate.messageHtml), 2131755233);
    this.mTextToAnnounce = paramSetupWizardTemplate.title;
    this.mContinueButtonLabel = paramSetupWizardTemplate.buttonLabel;
  }
  
  protected final void bindTwoIconMessagesTemplate(Acquisition.TwoIconMessagesTemplate paramTwoIconMessagesTemplate)
  {
    super.bindTwoIconMessagesTemplate(paramTwoIconMessagesTemplate);
    TextView localTextView = (TextView)this.mMainView.findViewById(2131756170);
    localTextView.setLinkTextColor(ContextCompat.getColor(localTextView.getContext(), 2131689573));
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mPlayStoreUiElement;
  }
  
  public final void onContinueButtonClicked()
  {
    if (!TextUtils.isEmpty(this.mSecondaryButtonLabel)) {
      logClick(1109, null);
    }
    for (;;)
    {
      performSuccessActionAndFinish();
      return;
      logClick(886, null);
    }
  }
  
  public final void onSecondaryButtonClicked()
  {
    logClick(1108, null);
    RedeemCodeFragment localRedeemCodeFragment = (RedeemCodeFragment)this.mParentFragment;
    Acquisition.PostSuccessAction localPostSuccessAction = this.mSecondaryPostSuccessAction;
    if (localPostSuccessAction != null)
    {
      if (localRedeemCodeFragment.mBillingUiMode != 1) {
        break label50;
      }
      FinskyLog.wtf("Cannot perform success action during Setup Wizard", new Object[0]);
    }
    label50:
    while (!localRedeemCodeFragment.performSuccessAction(localPostSuccessAction))
    {
      localRedeemCodeFragment.finish();
      return;
    }
    FinskyLog.d("Dialog shown, waiting for user input.", new Object[0]);
  }
  
  protected final void performSuccessActionAndFinish()
  {
    ((RedeemCodeFragment)this.mParentFragment).performSuccessActionAndFinish();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.giftcard.steps.RedeemSuccessStep
 * JD-Core Version:    0.7.0.1
 */