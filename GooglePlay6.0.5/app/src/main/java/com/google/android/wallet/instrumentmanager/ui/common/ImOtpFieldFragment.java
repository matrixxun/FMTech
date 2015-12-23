package com.google.android.wallet.instrumentmanager.ui.common;

import android.app.Activity;
import com.google.android.wallet.analytics.AnalyticsClickListener;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.common.analytics.util.AnalyticsUtil;
import com.google.android.wallet.ui.common.OtpFieldFragment;
import com.google.commerce.payments.orchestration.proto.ui.common.components.OtpFieldOuterClass.OtpField;

public final class ImOtpFieldFragment
  extends OtpFieldFragment
  implements AnalyticsClickListener
{
  public static ImOtpFieldFragment newInstance(OtpFieldOuterClass.OtpField paramOtpField, int paramInt)
  {
    ImOtpFieldFragment localImOtpFieldFragment = new ImOtpFieldFragment();
    localImOtpFieldFragment.setArguments(createArgsForOtpFieldFragment(paramOtpField, paramInt));
    return localImOtpFieldFragment;
  }
  
  public final void onAnalyticsClickEvent(UiNode paramUiNode, int paramInt)
  {
    AnalyticsUtil.createAndSendClickEvent(paramUiNode, -1, paramInt);
  }
  
  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mAnalyticsClickListener = this;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.common.ImOtpFieldFragment
 * JD-Core Version:    0.7.0.1
 */