package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.HeroGraphicView;
import com.google.android.finsky.protos.DocV2;
import com.google.android.play.layout.PlayCardViewBase;

public class FeaturedWideCardView
  extends PlayCardViewBase
{
  private HeroGraphicView mFeaturedHeroImage;
  
  public FeaturedWideCardView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public FeaturedWideCardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public int getCardType()
  {
    return 20;
  }
  
  public HeroGraphicView getFeaturedHeroImage()
  {
    return this.mFeaturedHeroImage;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mFeaturedHeroImage = ((HeroGraphicView)findViewById(2131755511));
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    Document localDocument = (Document)getData();
    this.mFeaturedHeroImage.getLayoutParams().width = (View.MeasureSpec.getSize(paramInt1) / 2);
    this.mFeaturedHeroImage.getLayoutParams().height = ((int)(this.mFeaturedHeroImage.getLayoutParams().width * HeroGraphicView.getHeroAspectRatio(localDocument.mDocument.docType)));
    super.onMeasure(paramInt1, paramInt2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.FeaturedWideCardView
 * JD-Core Version:    0.7.0.1
 */