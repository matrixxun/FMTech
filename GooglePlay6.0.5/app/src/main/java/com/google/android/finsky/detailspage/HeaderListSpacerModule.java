package com.google.android.finsky.detailspage;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.HeroGraphicView;

public class HeaderListSpacerModule
  extends FinskyModule<Data>
  implements DisplayDuringTransition
{
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    if (this.mModuleData == null)
    {
      this.mModuleData = new Data();
      ((Data)this.mModuleData).doc = paramDocument1;
      ((Data)this.mModuleData).headerListSpacerHeight = HeroGraphicView.getDetailsHeroSpacerHeight(this.mContext, ((Data)this.mModuleData).doc, this.mUseWideLayout);
    }
    if (paramBoolean)
    {
      ((Data)this.mModuleData).doc = paramDocument1;
      int i = HeroGraphicView.getDetailsHeroSpacerHeight(this.mContext, ((Data)this.mModuleData).doc, this.mUseWideLayout);
      if (i != ((Data)this.mModuleData).headerListSpacerHeight)
      {
        ((Data)this.mModuleData).headerListSpacerHeight = i;
        this.mFinskyModuleController.refreshModule(this, true);
      }
    }
  }
  
  public final void bindView(View paramView)
  {
    HeaderListSpacerModuleLayout localHeaderListSpacerModuleLayout = (HeaderListSpacerModuleLayout)paramView;
    int i = ((Data)this.mModuleData).headerListSpacerHeight;
    ViewGroup.LayoutParams localLayoutParams = localHeaderListSpacerModuleLayout.getLayoutParams();
    if (localLayoutParams.height != i)
    {
      localLayoutParams.height = i;
      localHeaderListSpacerModuleLayout.requestLayout();
    }
  }
  
  public final int getLayoutResId()
  {
    return 2130968784;
  }
  
  public final void onRestoreModuleData(FinskyModule.ModuleData paramModuleData)
  {
    super.onRestoreModuleData(paramModuleData);
    if (this.mModuleData != null) {
      ((Data)this.mModuleData).headerListSpacerHeight = HeroGraphicView.getDetailsHeroSpacerHeight(this.mContext, ((Data)this.mModuleData).doc, this.mUseWideLayout);
    }
  }
  
  public final boolean readyForDisplay()
  {
    return this.mModuleData != null;
  }
  
  protected final class Data
    extends FinskyModule.ModuleData
  {
    Document doc;
    int headerListSpacerHeight;
    
    protected Data() {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.HeaderListSpacerModule
 * JD-Core Version:    0.7.0.1
 */