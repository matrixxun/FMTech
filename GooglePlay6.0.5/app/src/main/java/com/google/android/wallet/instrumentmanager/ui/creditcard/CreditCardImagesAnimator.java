package com.google.android.wallet.instrumentmanager.ui.creditcard;

import android.view.View;
import android.widget.ImageView;
import com.google.android.wallet.common.util.Objects;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.ImageWithCaptionOuterClass.ImageWithCaption;

public abstract class CreditCardImagesAnimator
{
  protected ImageWithCaptionOuterClass.ImageWithCaption mCurrentIcon;
  protected final ImageView[] mImages;
  protected boolean mInOneCardMode;
  protected final ImageView[] mResolvedOnlyImages;
  protected final View mUnresolvedImage;
  
  public CreditCardImagesAnimator(ImageView[] paramArrayOfImageView1, ImageView[] paramArrayOfImageView2, View paramView)
  {
    this.mImages = paramArrayOfImageView1;
    this.mResolvedOnlyImages = paramArrayOfImageView2;
    this.mUnresolvedImage = paramView;
  }
  
  private static int findIndex(ImageView[] paramArrayOfImageView, ImageWithCaptionOuterClass.ImageWithCaption paramImageWithCaption)
  {
    if ((paramArrayOfImageView == null) || (paramImageWithCaption == null))
    {
      i = -1;
      return i;
    }
    int i = 0;
    int j = paramArrayOfImageView.length;
    for (;;)
    {
      if (i >= j) {
        break label50;
      }
      if (Objects.equals(paramImageWithCaption.imageUri, ((ImageWithCaptionOuterClass.ImageWithCaption)paramArrayOfImageView[i].getTag()).imageUri)) {
        break;
      }
      i++;
    }
    label50:
    return -1;
  }
  
  public abstract void animateToIcon(ImageWithCaptionOuterClass.ImageWithCaption paramImageWithCaption);
  
  protected final int findImageIndex(ImageWithCaptionOuterClass.ImageWithCaption paramImageWithCaption)
  {
    return findIndex(this.mImages, paramImageWithCaption);
  }
  
  protected final int findResolvedOnlyImageIndex(ImageWithCaptionOuterClass.ImageWithCaption paramImageWithCaption)
  {
    return findIndex(this.mResolvedOnlyImages, paramImageWithCaption);
  }
  
  public abstract void initialize(ImageWithCaptionOuterClass.ImageWithCaption paramImageWithCaption);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.creditcard.CreditCardImagesAnimator
 * JD-Core Version:    0.7.0.1
 */