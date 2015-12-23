package com.google.android.wallet.instrumentmanager.ui.simple;

import com.google.android.wallet.instrumentmanager.ui.common.ImOtpFieldFragment;
import com.google.android.wallet.ui.common.BaseWalletUiComponentFragment;
import com.google.android.wallet.ui.common.OtpFieldFragment;
import com.google.android.wallet.ui.simple.SimpleFragment;
import com.google.commerce.payments.orchestration.proto.ui.common.components.OtpFieldOuterClass.OtpField;
import com.google.commerce.payments.orchestration.proto.ui.common.components.SimpleFormOuterClass.SimpleForm;

public final class ImSimpleFragment
  extends SimpleFragment
{
  public static ImSimpleFragment newInstance(SimpleFormOuterClass.SimpleForm paramSimpleForm, int paramInt)
  {
    ImSimpleFragment localImSimpleFragment = new ImSimpleFragment();
    localImSimpleFragment.setArguments(createArgsForFormFragment$179723a0(paramInt, paramSimpleForm));
    return localImSimpleFragment;
  }
  
  protected final OtpFieldFragment createOtpFieldFragment(OtpFieldOuterClass.OtpField paramOtpField)
  {
    return ImOtpFieldFragment.newInstance(paramOtpField, this.mThemeResourceId);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.simple.ImSimpleFragment
 * JD-Core Version:    0.7.0.1
 */