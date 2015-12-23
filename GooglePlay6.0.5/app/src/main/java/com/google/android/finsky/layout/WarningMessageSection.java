package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.detailspage.ModuleDividerItemDecoration.NoBottomDivider;
import com.google.android.finsky.detailspage.ModuleDividerItemDecoration.NoTopDivider;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.LibraryUtils;

public class WarningMessageSection
  extends LinearLayout
  implements ModuleDividerItemDecoration.NoBottomDivider, ModuleDividerItemDecoration.NoTopDivider
{
  public boolean mBinded;
  public TextView mDetailsWarningInfoFirstLineText;
  public ImageView mDetailsWarningInfoIcon;
  public TextView mDetailsWarningInfoSecondLineText;
  
  public WarningMessageSection(Context paramContext)
  {
    super(paramContext);
  }
  
  public WarningMessageSection(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public static boolean shouldShowExternallyHostedMessage(Document paramDocument)
  {
    return (paramDocument.getAppDetails() != null) && (paramDocument.getAppDetails().externallyHosted);
  }
  
  public static boolean shouldShowLicenseMessage(Document paramDocument, AccountLibrary paramAccountLibrary)
  {
    if (paramDocument.mDocument.docType != 1) {}
    label79:
    for (;;)
    {
      return false;
      Common.Offer[] arrayOfOffer = paramDocument.mDocument.offer;
      int i = arrayOfOffer.length;
      for (int j = 0;; j++)
      {
        if (j >= i) {
          break label79;
        }
        Common.Offer localOffer = arrayOfOffer[j];
        if ((localOffer.licenseTerms != null) && ((localOffer.micros > 0L) || (localOffer.temporarilyFree)))
        {
          if (LibraryUtils.isOwned(paramDocument, paramAccountLibrary)) {
            break;
          }
          return true;
        }
      }
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mDetailsWarningInfoFirstLineText = ((TextView)findViewById(2131755433));
    this.mDetailsWarningInfoSecondLineText = ((TextView)findViewById(2131755434));
    this.mDetailsWarningInfoIcon = ((ImageView)findViewById(2131755432));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.WarningMessageSection
 * JD-Core Version:    0.7.0.1
 */