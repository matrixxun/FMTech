package com.google.android.finsky.billing.refund.steps;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.billing.refund.RequestRefundFragment;
import com.google.android.finsky.billing.refund.RequestRefundSidecar;
import com.google.android.finsky.layout.ButtonBar;
import com.google.android.finsky.layout.MultiLineDoneButtonEditText;

public final class RefundSurveyStep
  extends RefundBaseStep
  implements RadioGroup.OnCheckedChangeListener
{
  private final PlayStore.PlayStoreUiElement mPlayStoreUiElement = FinskyEventLog.obtainPlayStoreUiElement(1153);
  private RadioGroup mReasonChoices;
  private int mUserCommentSetting;
  private MultiLineDoneButtonEditText mUserInput;
  
  private String getUserComment()
  {
    if (this.mUserInput != null) {
      return this.mUserInput.getText().toString().trim();
    }
    return null;
  }
  
  public static RefundSurveyStep newInstance(String paramString, int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("RefundSurveyStep.title", paramString);
    localBundle.putInt("RefundSurveyStep.allowUserInput", paramInt);
    RefundSurveyStep localRefundSurveyStep = new RefundSurveyStep();
    localRefundSurveyStep.setArguments(localBundle);
    return localRefundSurveyStep;
  }
  
  private void syncButtonEnabledState()
  {
    boolean bool1;
    int i;
    label32:
    boolean bool2;
    if (this.mReasonChoices.getCheckedRadioButtonId() >= 0)
    {
      bool1 = true;
      if (this.mUserCommentSetting != 2) {
        break label66;
      }
      if (TextUtils.isEmpty(getUserComment())) {
        break label56;
      }
      i = 1;
      if ((!bool1) || (i == 0)) {
        break label61;
      }
      bool2 = true;
    }
    for (;;)
    {
      this.mButtonBar.setPositiveButtonEnabled(bool2);
      return;
      bool1 = false;
      break;
      label56:
      i = 0;
      break label32;
      label61:
      bool2 = false;
      continue;
      label66:
      bool2 = bool1;
    }
  }
  
  protected final String getAnnouncementString()
  {
    return this.mArguments.getString("RefundSurveyStep.title");
  }
  
  protected final int getLayoutRes()
  {
    return 2130969066;
  }
  
  public final String getNegativeButtonLabel(Resources paramResources)
  {
    return paramResources.getString(2131361915);
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mPlayStoreUiElement;
  }
  
  public final String getPositiveButtonLabel(Resources paramResources)
  {
    return paramResources.getString(2131362769);
  }
  
  public final void onCheckedChanged(RadioGroup paramRadioGroup, int paramInt)
  {
    logClick(1155, null);
    syncButtonEnabledState();
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mUserCommentSetting = this.mArguments.getInt("RefundSurveyStep.allowUserInput");
  }
  
  public final void onPositiveButtonClick()
  {
    ((RadioButton)this.mReasonChoices.findViewById(this.mReasonChoices.getCheckedRadioButtonId())).getTag();
    RequestRefundFragment localRequestRefundFragment = (RequestRefundFragment)this.mParentFragment;
    getUserComment();
    localRequestRefundFragment.mSidecar.setState(7, 0);
  }
  
  public final void onStart()
  {
    super.onStart();
    syncButtonEnabledState();
  }
  
  public final void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    ((TextView)paramView.findViewById(2131755173)).setText(this.mArguments.getString("RefundSurveyStep.title"));
    this.mReasonChoices = ((RadioGroup)paramView.findViewById(2131756032));
    this.mReasonChoices.setOnCheckedChangeListener(this);
    this.mUserInput = ((MultiLineDoneButtonEditText)paramView.findViewById(2131756039));
    if (this.mUserCommentSetting == 0) {
      this.mUserInput.setVisibility(8);
    }
    do
    {
      return;
      this.mUserInput.setVisibility(0);
    } while (this.mUserCommentSetting != 2);
    this.mUserInput.addTextChangedListener(new TextWatcher()
    {
      public final void afterTextChanged(Editable paramAnonymousEditable) {}
      
      public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      
      public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        RefundSurveyStep.this.syncButtonEnabledState();
      }
    });
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.refund.steps.RefundSurveyStep
 * JD-Core Version:    0.7.0.1
 */