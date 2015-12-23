package com.google.android.finsky.setup;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import com.google.android.finsky.layout.SeparatorLinearLayout;

public class SetupWizardRestoreAppsSelector
  extends SeparatorLinearLayout
{
  private TextView mMainText;
  private TextView mSecondaryText;
  
  public SetupWizardRestoreAppsSelector(Context paramContext)
  {
    super(paramContext);
  }
  
  public SetupWizardRestoreAppsSelector(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void syncContentDescription()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (!TextUtils.isEmpty(this.mMainText.getText())) {
      localStringBuilder.append(this.mMainText.getText()).append(", ");
    }
    if (!TextUtils.isEmpty(this.mSecondaryText.getText())) {
      localStringBuilder.append(this.mSecondaryText.getText()).append(", ");
    }
    localStringBuilder.append(getResources().getString(2131362025));
    setContentDescription(localStringBuilder.toString());
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mMainText = ((TextView)findViewById(2131756121));
    this.mSecondaryText = ((TextView)findViewById(2131756122));
    syncContentDescription();
  }
  
  public void setTexts(String paramString)
  {
    setTexts(paramString, "");
  }
  
  public final void setTexts(String paramString1, String paramString2)
  {
    this.mMainText.setText(paramString1);
    this.mSecondaryText.setText(paramString2);
    syncContentDescription();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.setup.SetupWizardRestoreAppsSelector
 * JD-Core Version:    0.7.0.1
 */