package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;

public class FooterSpacerModule
  extends FinskyModule<Data>
  implements DisplayDuringTransition
{
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    if (this.mModuleData == null)
    {
      this.mModuleData = new Data();
      ((Data)this.mModuleData).isShowing = true;
    }
    if ((paramBoolean) && (((Data)this.mModuleData).isShowing))
    {
      ((Data)this.mModuleData).isShowing = false;
      this.mFinskyModuleController.removeModule(this);
    }
  }
  
  public final void bindView(View paramView)
  {
    FooterSpacerModuleLayout localFooterSpacerModuleLayout = (FooterSpacerModuleLayout)paramView;
    int i = this.mContext.getResources().getDimensionPixelSize(2131493025);
    int j = this.mContext.getResources().getDisplayMetrics().heightPixels - i;
    ViewGroup.LayoutParams localLayoutParams = localFooterSpacerModuleLayout.getLayoutParams();
    if (localLayoutParams.height != j)
    {
      localLayoutParams.height = j;
      localFooterSpacerModuleLayout.requestLayout();
    }
  }
  
  public final int getLayoutResId()
  {
    return 2130968755;
  }
  
  public final boolean readyForDisplay()
  {
    return (this.mModuleData != null) && (((Data)this.mModuleData).isShowing);
  }
  
  protected static final class Data
    extends FinskyModule.ModuleData
  {
    boolean isShowing;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.FooterSpacerModule
 * JD-Core Version:    0.7.0.1
 */