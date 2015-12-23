package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.SeriesAntenna;
import com.google.android.finsky.protos.Template;
import com.google.android.finsky.utils.UiUtils;

public class AntennaDescriptionTextModule
  extends TextModule
{
  protected final TextModule.Data getData(Document paramDocument, boolean paramBoolean)
  {
    CharSequence localCharSequence = paramDocument.getDescription();
    if (TextUtils.isEmpty(localCharSequence)) {
      return null;
    }
    SeriesAntenna localSeriesAntenna = paramDocument.getTemplate().seriesAntenna;
    if (localSeriesAntenna == null) {
      return null;
    }
    TextModule.Data localData = new TextModule.Data();
    localData.backend = paramDocument.mDocument.backendId;
    localData.docType = paramDocument.mDocument.docType;
    localData.callout = null;
    localData.calloutGravity = 8388611;
    localData.restrictCalloutMaxLines = false;
    Resources localResources = this.mContext.getResources();
    localData.bodyHeader = null;
    localData.body = localCharSequence;
    int i = localResources.getColor(2131689682);
    if (paramDocument.hasImages(1)) {
      i = UiUtils.getFillColor(localSeriesAntenna, i);
    }
    localData.backgroundFillColor = Integer.valueOf(i);
    return localData;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.AntennaDescriptionTextModule
 * JD-Core Version:    0.7.0.1
 */