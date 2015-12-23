package com.google.android.finsky.detailspage;

import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.config.G;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.library.Libraries.Listener;
import com.google.android.finsky.protos.Annotations;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.SectionMetadata;
import com.google.android.play.utils.config.GservicesValue;

public class PostPurchaseCrossSellClusterModule
  extends PostPurchaseClusterModule
  implements Libraries.Listener
{
  protected final SectionMetadata getSectionMetadata(Document paramDocument)
  {
    if (paramDocument.getPostPurchaseCrossSellSection() != null) {
      return paramDocument.getPostPurchaseCrossSellSection();
    }
    if (paramDocument.mDocument.annotations != null) {
      return paramDocument.mDocument.annotations.sectionCrossSell;
    }
    return null;
  }
  
  protected final boolean shouldEnable(Document paramDocument)
  {
    return (((Boolean)G.forcePostPurchaseCrossSell.get()).booleanValue()) || (paramDocument.mDocument.backendId == 3) || (paramDocument.mDocument.backendId == 1) || (FinskyApp.get().getExperiments().isEnabled(12603134L));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.PostPurchaseCrossSellClusterModule
 * JD-Core Version:    0.7.0.1
 */