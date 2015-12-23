package com.google.android.wallet.instrumentmanager.ui.redirect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.analytics.WalletUiElement;
import com.google.android.wallet.ui.common.FormFragment;
import com.google.android.wallet.ui.common.FormFragment.FieldData;
import com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass.FormFieldMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.components.redirect.RedirectFormOuterClass.RedirectForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.redirect.RedirectFormOuterClass.RedirectFormValue;
import java.util.Collections;
import java.util.List;

public final class DummyFormFragment
  extends FormFragment<RedirectFormOuterClass.RedirectForm>
{
  public RedirectFormOuterClass.RedirectFormValue mRedirectFormValue;
  private final WalletUiElement mUiElement = new WalletUiElement(1745);
  
  public static DummyFormFragment newInstance(RedirectFormOuterClass.RedirectForm paramRedirectForm, int paramInt)
  {
    DummyFormFragment localDummyFormFragment = new DummyFormFragment();
    localDummyFormFragment.setArguments(createArgsForFormFragment$179723a0(paramInt, paramRedirectForm));
    return localDummyFormFragment;
  }
  
  public final boolean applyFormFieldMessage(UiErrorOuterClass.FormFieldMessage paramFormFieldMessage)
  {
    return false;
  }
  
  protected final void doEnableUi() {}
  
  public final List<UiNode> getChildren()
  {
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
    return this.mRedirectFormValue != null;
  }
  
  public final void notifyFormEvent(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    default: 
      super.notifyFormEvent(paramInt, paramBundle);
    }
  }
  
  protected final View onCreateThemedView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.redirect.DummyFormFragment
 * JD-Core Version:    0.7.0.1
 */