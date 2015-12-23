package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.BookDetails;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.FastHtmlParser;

public class AboutAuthorTextModule
  extends TextModule
{
  protected final TextModule.Data getData(Document paramDocument, boolean paramBoolean)
  {
    BookDetails localBookDetails = paramDocument.getBookDetails();
    if (localBookDetails != null) {}
    for (CharSequence localCharSequence = FastHtmlParser.fromHtml(localBookDetails.aboutTheAuthor); TextUtils.isEmpty(localCharSequence); localCharSequence = null) {
      return null;
    }
    TextModule.Data localData = new TextModule.Data();
    localData.backend = paramDocument.mDocument.backendId;
    localData.docType = paramDocument.mDocument.docType;
    localData.callout = null;
    localData.calloutGravity = 8388611;
    localData.restrictCalloutMaxLines = false;
    localData.bodyHeader = this.mContext.getResources().getString(2131362081).toUpperCase();
    localData.body = localCharSequence;
    localData.backgroundFillColor = Integer.valueOf(this.mContext.getResources().getColor(2131689682));
    return localData;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.AboutAuthorTextModule
 * JD-Core Version:    0.7.0.1
 */