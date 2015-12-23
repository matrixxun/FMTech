package com.google.android.wallet.instrumentmanager.ui.usernamepassword;

import android.app.Activity;
import com.google.android.wallet.analytics.AnalyticsClickListener;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.common.analytics.util.AnalyticsUtil;
import com.google.android.wallet.ui.usernamepassword.UsernamePasswordFragment;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.UsernamePassword.UsernamePasswordForm;

public final class ImUsernamePasswordFragment
  extends UsernamePasswordFragment
  implements AnalyticsClickListener
{
  public static ImUsernamePasswordFragment newInstance(UsernamePassword.UsernamePasswordForm paramUsernamePasswordForm, int paramInt)
  {
    ImUsernamePasswordFragment localImUsernamePasswordFragment = new ImUsernamePasswordFragment();
    localImUsernamePasswordFragment.setArguments(createArgsForFormFragment$179723a0(paramInt, paramUsernamePasswordForm));
    return localImUsernamePasswordFragment;
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
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.usernamepassword.ImUsernamePasswordFragment
 * JD-Core Version:    0.7.0.1
 */