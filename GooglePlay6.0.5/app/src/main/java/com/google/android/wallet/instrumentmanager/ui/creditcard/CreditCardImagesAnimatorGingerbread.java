package com.google.android.wallet.instrumentmanager.ui.creditcard;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.instrumentmanager.R.anim;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.ImageWithCaptionOuterClass.ImageWithCaption;
import java.util.Arrays;

public final class CreditCardImagesAnimatorGingerbread
  extends CreditCardImagesAnimator
{
  private final Animation mFadeInAnimation;
  private final Animation mFadeInCompletelyAnimation;
  private final Animation mFadeOutAnimation;
  private final Animation mFadeOutCompletelyAnimation;
  private final Animation mSetOpaqueAnimation;
  private final Animation mSetTransparentAnimation;
  private final Animation mStayTransparentAnimation;
  private final int mUnresolvedImageIndex;
  private final boolean[] mVisible;
  
  public CreditCardImagesAnimatorGingerbread(Context paramContext, ImageView[] paramArrayOfImageView1, ImageView[] paramArrayOfImageView2, View paramView, boolean paramBoolean)
  {
    super(paramArrayOfImageView1, paramArrayOfImageView2, paramView);
    this.mInOneCardMode = paramBoolean;
    this.mVisible = new boolean[1 + (paramArrayOfImageView1.length + paramArrayOfImageView2.length)];
    this.mUnresolvedImageIndex = (-1 + this.mVisible.length);
    Arrays.fill(this.mVisible, true);
    int i;
    int j;
    label76:
    int k;
    label118:
    int m;
    if (paramBoolean)
    {
      i = R.anim.wallet_im_fade_in;
      this.mFadeInAnimation = AnimationUtils.loadAnimation(paramContext, i);
      if (!paramBoolean) {
        break label235;
      }
      j = R.anim.wallet_im_fade_out;
      this.mFadeOutAnimation = AnimationUtils.loadAnimation(paramContext, j);
      this.mFadeInCompletelyAnimation = AnimationUtils.loadAnimation(paramContext, R.anim.wallet_im_fade_in);
      this.mFadeOutCompletelyAnimation = AnimationUtils.loadAnimation(paramContext, R.anim.wallet_im_fade_out);
      if (!paramBoolean) {
        break label243;
      }
      k = R.anim.wallet_im_stay_transparent;
      this.mStayTransparentAnimation = AnimationUtils.loadAnimation(paramContext, k);
      if (!paramBoolean) {
        break label251;
      }
      m = R.anim.wallet_im_fade_in;
      label138:
      this.mSetOpaqueAnimation = AnimationUtils.loadAnimation(paramContext, m);
      this.mSetOpaqueAnimation.setDuration(0L);
      if (!paramBoolean) {
        break label259;
      }
    }
    label259:
    for (int n = R.anim.wallet_im_fade_out;; n = R.anim.wallet_im_fade_out_to_translucent)
    {
      this.mSetTransparentAnimation = AnimationUtils.loadAnimation(paramContext, n);
      this.mSetTransparentAnimation.setDuration(0L);
      int i1 = 0;
      int i2 = paramArrayOfImageView2.length;
      while (i1 < i2)
      {
        paramArrayOfImageView2[i1].setVisibility(8);
        this.mVisible[(i1 + this.mImages.length)] = false;
        i1++;
      }
      i = R.anim.wallet_im_fade_in_from_translucent;
      break;
      label235:
      j = R.anim.wallet_im_fade_out_to_translucent;
      break label76;
      label243:
      k = R.anim.wallet_im_stay_translucent;
      break label118;
      label251:
      m = R.anim.wallet_im_fade_in_from_translucent;
      break label138;
    }
    if (paramBoolean)
    {
      this.mUnresolvedImage.setVisibility(0);
      return;
    }
    this.mUnresolvedImage.setVisibility(8);
    this.mVisible[this.mUnresolvedImageIndex] = false;
  }
  
  public final void animateToIcon(ImageWithCaptionOuterClass.ImageWithCaption paramImageWithCaption)
  {
    if (PaymentUtils.imageWithCaptionEquals(paramImageWithCaption, this.mCurrentIcon)) {
      return;
    }
    int i = -1;
    int j = findImageIndex(paramImageWithCaption);
    if (j == -1) {
      i = findResolvedOnlyImageIndex(paramImageWithCaption);
    }
    int k = 0;
    int m = this.mImages.length;
    if (k < m)
    {
      if ((j == k) || ((!this.mInOneCardMode) && (j == -1) && (i == -1)))
      {
        this.mImages[k].setVisibility(0);
        if (this.mVisible[k] == 0)
        {
          this.mImages[k].startAnimation(this.mFadeInAnimation);
          this.mVisible[k] = true;
        }
        for (;;)
        {
          k++;
          break;
          this.mImages[k].setAnimation(null);
        }
      }
      if (i != -1) {
        if (this.mInOneCardMode) {
          this.mImages[k].setVisibility(4);
        }
      }
      for (;;)
      {
        this.mVisible[k] = false;
        break;
        this.mImages[k].startAnimation(this.mFadeOutCompletelyAnimation);
        continue;
        if (this.mVisible[k] != 0) {
          this.mImages[k].startAnimation(this.mFadeOutAnimation);
        } else {
          this.mImages[k].startAnimation(this.mStayTransparentAnimation);
        }
      }
    }
    int n = 0;
    int i1 = this.mResolvedOnlyImages.length;
    if (n < i1)
    {
      int i2 = n + this.mImages.length;
      if (i == n) {
        if (this.mVisible[i2] == 0)
        {
          this.mResolvedOnlyImages[n].startAnimation(this.mFadeInCompletelyAnimation);
          this.mResolvedOnlyImages[n].setVisibility(0);
          this.mVisible[i2] = true;
        }
      }
      for (;;)
      {
        n++;
        break;
        this.mResolvedOnlyImages[n].setAnimation(null);
        continue;
        if (this.mVisible[i2] != 0)
        {
          this.mResolvedOnlyImages[n].setAnimation(null);
          this.mResolvedOnlyImages[n].setVisibility(8);
          this.mVisible[i2] = false;
        }
      }
    }
    if (this.mInOneCardMode)
    {
      if ((j != -1) || (i != -1)) {
        break label444;
      }
      this.mUnresolvedImage.setVisibility(0);
      if (this.mVisible[this.mUnresolvedImageIndex] != 0) {
        break label433;
      }
      this.mUnresolvedImage.startAnimation(this.mFadeInAnimation);
      this.mVisible[this.mUnresolvedImageIndex] = true;
    }
    for (;;)
    {
      this.mCurrentIcon = paramImageWithCaption;
      return;
      label433:
      this.mUnresolvedImage.setAnimation(null);
    }
    label444:
    if (i != -1) {
      this.mUnresolvedImage.setVisibility(4);
    }
    for (;;)
    {
      this.mVisible[this.mUnresolvedImageIndex] = false;
      break;
      if (this.mVisible[this.mUnresolvedImageIndex] != 0) {
        this.mUnresolvedImage.startAnimation(this.mFadeOutAnimation);
      } else {
        this.mUnresolvedImage.startAnimation(this.mStayTransparentAnimation);
      }
    }
  }
  
  public final void initialize(ImageWithCaptionOuterClass.ImageWithCaption paramImageWithCaption)
  {
    int i = -1;
    int j = findImageIndex(paramImageWithCaption);
    if (j == -1) {
      i = findResolvedOnlyImageIndex(paramImageWithCaption);
    }
    int k = 0;
    int m = this.mImages.length;
    if (k < m)
    {
      if ((j == k) || ((!this.mInOneCardMode) && (j == -1) && (i == -1))) {
        if (this.mVisible[k] == 0)
        {
          this.mImages[k].startAnimation(this.mSetOpaqueAnimation);
          this.mVisible[k] = true;
        }
      }
      for (;;)
      {
        k++;
        break;
        if (this.mVisible[k] != 0)
        {
          this.mImages[k].startAnimation(this.mSetTransparentAnimation);
          this.mVisible[k] = false;
        }
      }
    }
    int n = 0;
    int i1 = this.mResolvedOnlyImages.length;
    if (n < i1)
    {
      int i2 = n + this.mImages.length;
      if (i == n) {
        if (this.mVisible[i2] == 0)
        {
          this.mResolvedOnlyImages[n].setVisibility(0);
          this.mVisible[i2] = true;
        }
      }
      for (;;)
      {
        n++;
        break;
        if (this.mVisible[i2] != 0)
        {
          this.mResolvedOnlyImages[n].setVisibility(8);
          this.mVisible[i2] = false;
        }
      }
    }
    if (this.mInOneCardMode)
    {
      if ((j != -1) || (i != -1)) {
        break label289;
      }
      if (this.mVisible[this.mUnresolvedImageIndex] == 0)
      {
        this.mUnresolvedImage.startAnimation(this.mSetOpaqueAnimation);
        this.mVisible[this.mUnresolvedImageIndex] = true;
      }
    }
    for (;;)
    {
      this.mCurrentIcon = paramImageWithCaption;
      return;
      label289:
      if (this.mVisible[this.mUnresolvedImageIndex] != 0)
      {
        this.mUnresolvedImage.startAnimation(this.mSetTransparentAnimation);
        this.mVisible[this.mUnresolvedImageIndex] = false;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.creditcard.CreditCardImagesAnimatorGingerbread
 * JD-Core Version:    0.7.0.1
 */