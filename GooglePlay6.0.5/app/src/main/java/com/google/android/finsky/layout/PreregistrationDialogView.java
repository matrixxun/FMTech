package com.google.android.finsky.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SimpleAlertDialog.ConfigurableView;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.PlayCardImageTypeSequence;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.PlayCardThumbnail;
import com.google.android.play.layout.PlayTextView;

public class PreregistrationDialogView
  extends LinearLayout
  implements SimpleAlertDialog.ConfigurableView
{
  private Document mDoc;
  
  public PreregistrationDialogView(Context paramContext)
  {
    super(paramContext);
  }
  
  public PreregistrationDialogView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  @SuppressLint({"NewApi"})
  public PreregistrationDialogView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public final void configureView(Bundle paramBundle)
  {
    this.mDoc = ((Document)paramBundle.getParcelable("PreregistrationDialogView.document"));
    if (this.mDoc == null)
    {
      FinskyLog.wtf("Preregistration dialog not passed a document", new Object[0]);
      return;
    }
    Context localContext = getContext();
    PlayCardThumbnail localPlayCardThumbnail = (PlayCardThumbnail)findViewById(2131755412);
    localPlayCardThumbnail.updateThumbnailPadding(this.mDoc.mDocument.backendId);
    int i = this.mDoc.mDocument.docType;
    ViewGroup.LayoutParams localLayoutParams = localPlayCardThumbnail.getLayoutParams();
    localLayoutParams.width = CorpusResourceUtils.getRegularDetailsIconWidth(localContext, i);
    localLayoutParams.height = CorpusResourceUtils.getRegularDetailsIconHeight(localContext, i);
    BitmapLoader localBitmapLoader = FinskyApp.get().mBitmapLoader;
    ((DocImageView)localPlayCardThumbnail.getImageView()).bind(this.mDoc, localBitmapLoader, PlayCardImageTypeSequence.CORE_IMAGE_TYPES);
    PlayTextView localPlayTextView = (PlayTextView)findViewById(2131755262);
    localPlayTextView.setText(this.mDoc.mDocument.title);
    localPlayTextView.setSelected(true);
  }
  
  public Document getDocument()
  {
    return this.mDoc;
  }
  
  public Bundle getResult()
  {
    return null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.PreregistrationDialogView
 * JD-Core Version:    0.7.0.1
 */