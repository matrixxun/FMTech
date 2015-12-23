package com.google.android.finsky.detailspage;

import android.content.res.Resources;
import android.view.View;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.DetailsFlagItemSection;
import com.google.android.finsky.layout.DetailsFlagItemSection.1;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;

public class FlagContentModule
  extends FinskyModule<Data>
{
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    if ((this.mModuleData == null) && (paramBoolean) && ((paramDocument1.mDocument.docType == 1) || (paramDocument1.mDocument.backendId == 2)))
    {
      this.mModuleData = new Data();
      ((Data)this.mModuleData).detailsDoc = paramDocument1;
    }
  }
  
  public final void bindView(View paramView)
  {
    DetailsFlagItemSection localDetailsFlagItemSection = (DetailsFlagItemSection)paramView;
    if (!localDetailsFlagItemSection.mBinded)
    {
      Document localDocument = ((Data)this.mModuleData).detailsDoc;
      NavigationManager localNavigationManager = this.mNavigationManager;
      PlayStoreUiElementNode localPlayStoreUiElementNode = this.mParentNode;
      if ((localDocument.mDocument.backendId != 3) && (localDocument.mDocument.backendId != 2)) {
        break label98;
      }
      localDetailsFlagItemSection.setOnClickListener(new DetailsFlagItemSection.1(localDetailsFlagItemSection, localNavigationManager, localDocument, localPlayStoreUiElementNode));
      localDetailsFlagItemSection.setContentDescription(localDetailsFlagItemSection.getResources().getString(2131362177));
      localDetailsFlagItemSection.setVisibility(0);
    }
    for (;;)
    {
      localDetailsFlagItemSection.mBinded = true;
      return;
      label98:
      localDetailsFlagItemSection.setVisibility(8);
    }
  }
  
  public final int getLayoutResId()
  {
    return 2130968691;
  }
  
  public final boolean readyForDisplay()
  {
    return this.mModuleData != null;
  }
  
  protected static final class Data
    extends FinskyModule.ModuleData
  {
    Document detailsDoc;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.FlagContentModule
 * JD-Core Version:    0.7.0.1
 */