package com.google.android.wallet.instrumentmanager.ui.dcb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.analytics.WalletUiElement;
import com.google.android.wallet.instrumentmanager.R.id;
import com.google.android.wallet.instrumentmanager.R.layout;
import com.google.android.wallet.instrumentmanager.ui.common.ImInfoMessageTextView;
import com.google.android.wallet.ui.common.BaseWalletUiComponentFragment;
import com.google.android.wallet.ui.common.ClickSpan.OnClickListener;
import com.google.android.wallet.ui.common.FormFragment;
import com.google.android.wallet.ui.common.FormFragment.FieldData;
import com.google.android.wallet.ui.common.InfoMessageTextView;
import com.google.android.wallet.ui.common.WebViewDialogFragment;
import com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass.FormFieldMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.Dcb.DcbVerifyAssociationForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageOuterClass.LegalMessage;
import java.util.Collections;
import java.util.List;

public final class VerifyAssociationFragment
  extends FormFragment<Dcb.DcbVerifyAssociationForm>
  implements ClickSpan.OnClickListener
{
  ImInfoMessageTextView mRequiredMessage;
  private final WalletUiElement mUiElement = new WalletUiElement(1700);
  
  public static VerifyAssociationFragment newInstance(Dcb.DcbVerifyAssociationForm paramDcbVerifyAssociationForm, int paramInt)
  {
    VerifyAssociationFragment localVerifyAssociationFragment = new VerifyAssociationFragment();
    localVerifyAssociationFragment.setArguments(createArgsForFormFragment$179723a0(paramInt, paramDcbVerifyAssociationForm));
    return localVerifyAssociationFragment;
  }
  
  public final boolean applyFormFieldMessage(UiErrorOuterClass.FormFieldMessage paramFormFieldMessage)
  {
    return false;
  }
  
  public final void doEnableUi()
  {
    if (this.mRequiredMessage != null) {
      this.mRequiredMessage.setEnabled(this.mUiEnabled);
    }
  }
  
  public final String getButtonBarExpandButtonText()
  {
    return this.mRequiredMessage.getExpandLabel();
  }
  
  public final List<UiNode> getChildren()
  {
    if (this.mRequiredMessage.getVisibility() == 0) {
      return Collections.singletonList(this.mRequiredMessage);
    }
    return null;
  }
  
  public final List<FormFragment.FieldData> getFieldsForValidationInOrder()
  {
    return Collections.EMPTY_LIST;
  }
  
  public final WalletUiElement getUiElement()
  {
    return this.mUiElement;
  }
  
  public final boolean isReadyToSubmit()
  {
    return true;
  }
  
  public final void onButtonBarExpandButtonClicked()
  {
    this.mRequiredMessage.expand(true);
  }
  
  public final void onClick(View paramView, String paramString)
  {
    if ((paramView != this.mRequiredMessage) || (this.mFragmentManager.findFragmentByTag("tagTosWebViewDialog") != null)) {
      return;
    }
    WebViewDialogFragment.newInstance(paramString, this.mThemeResourceId).show(this.mFragmentManager, "tagTosWebViewDialog");
  }
  
  protected final View onCreateThemedView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    ViewGroup localViewGroup = (ViewGroup)paramLayoutInflater.inflate(R.layout.fragment_dcb_verify_association, null);
    this.mRequiredMessage = ((ImInfoMessageTextView)localViewGroup.findViewById(R.id.required_message_text));
    if (((Dcb.DcbVerifyAssociationForm)this.mFormProto).requiredMessage != null)
    {
      this.mRequiredMessage.setVisibility(0);
      this.mRequiredMessage.setParentUiNode(this);
      this.mRequiredMessage.setUrlClickListener(this);
      this.mRequiredMessage.setInfoMessage(((Dcb.DcbVerifyAssociationForm)this.mFormProto).requiredMessage.messageText);
    }
    doEnableUi();
    notifyFormEvent(1, Bundle.EMPTY);
    return localViewGroup;
  }
  
  public final boolean shouldShowButtonBarExpandButton()
  {
    return (!this.mRequiredMessage.mInlineExpandLabel) && (!this.mRequiredMessage.mExpanded);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.dcb.VerifyAssociationFragment
 * JD-Core Version:    0.7.0.1
 */