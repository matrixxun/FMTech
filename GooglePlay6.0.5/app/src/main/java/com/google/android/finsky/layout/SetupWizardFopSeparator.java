package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;

public class SetupWizardFopSeparator
  extends SeparatorLinearLayout
{
  public SetupWizardFopSeparator(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SetupWizardFopSeparator(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final int getSeparatorColor(Resources paramResources)
  {
    return paramResources.getColor(2131689716);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.SetupWizardFopSeparator
 * JD-Core Version:    0.7.0.1
 */