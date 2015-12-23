package com.google.android.wallet.instrumentmanager.ui.creditcard;

import android.annotation.TargetApi;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.ImageWithCaptionOuterClass.ImageWithCaption;

@TargetApi(14)
public final class CreditCardImagesAnimatorIcs
  extends CreditCardImagesAnimator
{
  public CreditCardImagesAnimatorIcs(ImageView[] paramArrayOfImageView1, ImageView[] paramArrayOfImageView2, View paramView)
  {
    super(paramArrayOfImageView1, paramArrayOfImageView2, paramView);
  }
  
  public final void animateToIcon(ImageWithCaptionOuterClass.ImageWithCaption paramImageWithCaption)
  {
    if (!PaymentUtils.imageWithCaptionEquals(paramImageWithCaption, this.mCurrentIcon))
    {
      int i = -1;
      int j = findImageIndex(paramImageWithCaption);
      if (j == -1) {
        i = findResolvedOnlyImageIndex(paramImageWithCaption);
      }
      float f;
      int k;
      if ((j == -1) && (i == -1) && (!this.mInOneCardMode))
      {
        f = 1.0F;
        k = 0;
        int m = this.mImages.length;
        label60:
        if (k >= m) {
          break label172;
        }
        if (k != j) {
          break label131;
        }
        this.mImages[k].animate().alpha(1.0F);
        if (!this.mInOneCardMode) {
          this.mImages[k].animate().x(this.mImages[0].getLeft());
        }
      }
      for (;;)
      {
        k++;
        break label60;
        f = 0.0F;
        break;
        label131:
        this.mImages[k].animate().alpha(f);
        if (!this.mInOneCardMode) {
          this.mImages[k].animate().translationX(0.0F);
        }
      }
      label172:
      int n = 0;
      int i1 = this.mResolvedOnlyImages.length;
      if (n < i1)
      {
        if (n == i) {
          this.mResolvedOnlyImages[n].animate().alpha(1.0F);
        }
        for (;;)
        {
          n++;
          break;
          this.mResolvedOnlyImages[n].animate().alpha(0.0F);
        }
      }
      if (this.mInOneCardMode)
      {
        if ((j != -1) || (i != -1)) {
          break label269;
        }
        this.mUnresolvedImage.animate().alpha(1.0F);
      }
    }
    for (;;)
    {
      this.mCurrentIcon = paramImageWithCaption;
      return;
      label269:
      this.mUnresolvedImage.animate().alpha(0.0F);
    }
  }
  
  public final void initialize(ImageWithCaptionOuterClass.ImageWithCaption paramImageWithCaption)
  {
    int i = -1;
    int j = findImageIndex(paramImageWithCaption);
    if (j == -1) {
      i = findResolvedOnlyImageIndex(paramImageWithCaption);
    }
    float f;
    int k;
    if ((j == -1) && (i == -1))
    {
      f = 1.0F;
      k = 0;
      int m = this.mImages.length;
      label42:
      if (k >= m) {
        break label137;
      }
      this.mImages[k].animate().cancel();
      if (k != j) {
        break label111;
      }
      this.mImages[k].setAlpha(1.0F);
      this.mImages[k].setX(this.mImages[0].getLeft());
    }
    for (;;)
    {
      k++;
      break label42;
      f = 0.0F;
      break;
      label111:
      this.mImages[k].setAlpha(f);
      this.mImages[k].setTranslationX(0.0F);
    }
    label137:
    int n = 0;
    int i1 = this.mResolvedOnlyImages.length;
    if (n < i1)
    {
      this.mResolvedOnlyImages[n].animate().cancel();
      if (n == i) {
        this.mResolvedOnlyImages[n].setAlpha(1.0F);
      }
      for (;;)
      {
        n++;
        break;
        this.mResolvedOnlyImages[n].setAlpha(0.0F);
      }
    }
    this.mCurrentIcon = paramImageWithCaption;
  }
  
  public final void switchToOneCardMode()
  {
    if (!this.mInOneCardMode)
    {
      int i = -1;
      int j = findImageIndex(this.mCurrentIcon);
      if (j == -1) {
        i = findResolvedOnlyImageIndex(this.mCurrentIcon);
      }
      int k = 0;
      int m = this.mImages.length;
      if (k < m)
      {
        this.mImages[k].animate().cancel();
        this.mImages[k].setX(this.mImages[0].getLeft());
        if (k == j) {
          this.mImages[k].setAlpha(1.0F);
        }
        for (;;)
        {
          k++;
          break;
          this.mImages[k].setAlpha(0.0F);
        }
      }
      int n = 0;
      int i1 = this.mResolvedOnlyImages.length;
      if (n < i1)
      {
        this.mResolvedOnlyImages[n].animate().cancel();
        if (n == i) {
          this.mResolvedOnlyImages[n].setAlpha(1.0F);
        }
        for (;;)
        {
          n++;
          break;
          this.mResolvedOnlyImages[n].setAlpha(0.0F);
        }
      }
      this.mUnresolvedImage.animate().cancel();
      this.mUnresolvedImage.setVisibility(0);
      if ((j != -1) || (i != -1)) {
        break label221;
      }
      this.mUnresolvedImage.setAlpha(1.0F);
    }
    for (;;)
    {
      this.mInOneCardMode = true;
      return;
      label221:
      this.mUnresolvedImage.setAlpha(0.0F);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.creditcard.CreditCardImagesAnimatorIcs
 * JD-Core Version:    0.7.0.1
 */