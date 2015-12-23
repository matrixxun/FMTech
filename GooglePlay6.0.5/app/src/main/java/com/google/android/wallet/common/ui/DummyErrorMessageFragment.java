package com.google.android.wallet.common.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.analytics.WalletUiElement;
import com.google.android.wallet.ui.common.FormFragment;
import com.google.android.wallet.ui.common.FormFragment.FieldData;
import com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass.FormFieldMessage;
import com.google.protobuf.nano.MessageNano;
import java.util.Collections;
import java.util.List;

public final class DummyErrorMessageFragment
  extends FormFragment<MessageNano>
{
  private boolean mErrorShown;
  private final WalletUiElement mUiElement = new WalletUiElement(1760);
  
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
    return true;
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null) {
      this.mErrorShown = paramBundle.getBoolean("errorShown");
    }
  }
  
  protected final View onCreateThemedView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return null;
  }
  
  public final void onResume()
  {
    super.onResume();
    if (!this.mErrorShown)
    {
      notifyFormEvent(5, this.mArguments.getBundle("errorDetails"));
      this.mErrorShown = true;
    }
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("errorShown", this.mErrorShown);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.ui.DummyErrorMessageFragment
 * JD-Core Version:    0.7.0.1
 */