package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup.MarginLayoutParams;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.PlayCardImageTypeSequence;
import com.google.android.play.layout.PlayCardThumbnail;
import com.google.android.play.layout.PlayCardViewBase;

public class PlayEditorialNonAppCardView
  extends PlayCardViewBase
  implements PlayCardImageTypeSequence
{
  private static final int[] IMAGE_TYPES = { 4, 0, 2 };
  
  public PlayEditorialNonAppCardView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayEditorialNonAppCardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public int getCardType()
  {
    return 6;
  }
  
  public int[] getImageTypePreference()
  {
    return IMAGE_TYPES;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    Document localDocument = (Document)getData();
    if (localDocument == null) {}
    for (int i = 6;; i = localDocument.mDocument.docType)
    {
      this.mThumbnailAspectRatio = PlayCardClusterMetadata.getAspectRatio(i);
      ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mThumbnail.getLayoutParams();
      localMarginLayoutParams.width = ((int)(localMarginLayoutParams.height / this.mThumbnailAspectRatio));
      super.onMeasure(paramInt1, paramInt2);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayEditorialNonAppCardView
 * JD-Core Version:    0.7.0.1
 */