package com.google.android.finsky.detailspage;

import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.library.Libraries.Listener;
import com.google.android.finsky.protos.Annotations;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.SectionMetadata;

public class PostPurchaseRelatedTopicsClusterModule
  extends PostPurchaseClusterModule
  implements Libraries.Listener
{
  protected final int getCardLayoutResId()
  {
    return 2130968919;
  }
  
  public final int getLayoutResId()
  {
    if (this.mShouldUseScrollableClusters) {
      return 2130968654;
    }
    return 2130968621;
  }
  
  protected final SectionMetadata getSectionMetadata(Document paramDocument)
  {
    if (paramDocument.mDocument.annotations != null) {
      return paramDocument.mDocument.annotations.sectionPurchaseRelatedTopics;
    }
    return null;
  }
  
  protected final boolean shouldEnable(Document paramDocument)
  {
    return FinskyApp.get().getExperiments().isEnabled(12602822L);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.PostPurchaseRelatedTopicsClusterModule
 * JD-Core Version:    0.7.0.1
 */