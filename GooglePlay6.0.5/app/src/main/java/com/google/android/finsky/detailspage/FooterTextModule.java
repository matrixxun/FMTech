package com.google.android.finsky.detailspage;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.Details.DetailsResponse;

public class FooterTextModule
  extends FinskyModule<Data>
{
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    if ((this.mModuleData == null) && (paramBoolean)) {
      if ((paramDfeDetails1.mDetailsResponse != null) && (paramDfeDetails1.mDetailsResponse.footerHtml.length() != 0)) {
        break label66;
      }
    }
    label66:
    for (Object localObject = null;; localObject = paramDfeDetails1.mDetailsResponse.footerHtml)
    {
      if (!TextUtils.isEmpty((CharSequence)localObject))
      {
        this.mModuleData = new Data();
        ((Data)this.mModuleData).footerHtml = ((String)localObject);
      }
      return;
    }
  }
  
  public final void bindView(View paramView)
  {
    FooterTextModuleLayout localFooterTextModuleLayout = (FooterTextModuleLayout)paramView;
    if (!localFooterTextModuleLayout.mBinded)
    {
      localFooterTextModuleLayout.setText(Html.fromHtml(((Data)this.mModuleData).footerHtml));
      localFooterTextModuleLayout.mBinded = true;
    }
  }
  
  public final int getLayoutResId()
  {
    return 2130968756;
  }
  
  public final boolean readyForDisplay()
  {
    return this.mModuleData != null;
  }
  
  protected static final class Data
    extends FinskyModule.ModuleData
  {
    String footerHtml;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.FooterTextModule
 * JD-Core Version:    0.7.0.1
 */