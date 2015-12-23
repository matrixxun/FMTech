package com.google.android.wallet.instrumentmanager.ui.creditcard;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.common.util.ParcelableProto;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.config.G.images;
import com.google.android.wallet.instrumentmanager.R.dimen;
import com.google.android.wallet.instrumentmanager.R.drawable;
import com.google.android.wallet.instrumentmanager.R.id;
import com.google.android.wallet.instrumentmanager.R.styleable;
import com.google.android.wallet.ui.common.ImageWithCaptionView;
import com.google.android.wallet.ui.common.WalletUiUtils;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.ImageWithCaptionOuterClass.ImageWithCaption;
import java.util.ArrayList;
import java.util.HashSet;

public class CreditCardImagesView
  extends RelativeLayout
{
  private boolean mAllImagesStaticOnly;
  ImageView[] mCardImages;
  CreditCardImagesAnimator mCardImagesAnimator;
  ImageWithCaptionOuterClass.ImageWithCaption mCurrentIcon;
  public boolean mOneCardMode;
  ImageView[] mResolvedOnlyImages;
  private int mSpacingBetweenCardImages;
  private boolean mSuspendAnimations = true;
  View mUnresolvedCardImage;
  
  public CreditCardImagesView(Context paramContext)
  {
    super(paramContext);
  }
  
  public CreditCardImagesView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    readAttributes(paramContext, paramAttributeSet);
  }
  
  @TargetApi(11)
  public CreditCardImagesView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    readAttributes(paramContext, paramAttributeSet);
  }
  
  private ImageView[] loadImages(ImageWithCaptionOuterClass.ImageWithCaption[] paramArrayOfImageWithCaption, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
  {
    ArrayList localArrayList = new ArrayList(paramArrayOfImageWithCaption.length);
    HashSet localHashSet = new HashSet(paramArrayOfImageWithCaption.length);
    int i = paramInt1;
    int j = paramArrayOfImageWithCaption.length;
    for (int k = 0; k < j; k++)
    {
      ImageWithCaptionOuterClass.ImageWithCaption localImageWithCaption = paramArrayOfImageWithCaption[k];
      if (!localHashSet.contains(localImageWithCaption.imageUri))
      {
        localHashSet.add(localImageWithCaption.imageUri);
        ImageWithCaptionView localImageWithCaptionView = new ImageWithCaptionView(getContext());
        if (Build.VERSION.SDK_INT >= 11) {
          localImageWithCaptionView.setLayerType(2, null);
        }
        localImageWithCaptionView.setId(i);
        RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(paramInt2, paramInt3);
        if ((!paramBoolean1) && (!paramBoolean2) && (i > paramInt1))
        {
          MarginLayoutParamsCompat.setMarginStart(localLayoutParams, this.mSpacingBetweenCardImages);
          localLayoutParams.addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(17), i - 1);
        }
        addView(localImageWithCaptionView, localLayoutParams);
        if ((PaymentUtils.isEmbeddedImageUri(localImageWithCaption.imageUri)) && (PaymentUtils.getEmbeddedImageId(localImageWithCaption.imageUri) == 27)) {
          throw new IllegalArgumentException("Requesting placeholder network logo when not supported");
        }
        localImageWithCaptionView.setImageWithCaption(localImageWithCaption, PaymentUtils.getImageLoader(getContext().getApplicationContext()), ((Boolean)G.images.useWebPForFife.get()).booleanValue());
        localImageWithCaptionView.setFadeIn(true);
        localImageWithCaptionView.setErrorImageResId(R.drawable.wallet_im_card_resolved_generic);
        localImageWithCaptionView.setTag(localImageWithCaption);
        localArrayList.add(localImageWithCaptionView);
        i++;
      }
    }
    return (ImageView[])localArrayList.toArray(new ImageView[localArrayList.size()]);
  }
  
  private void readAttributes(Context paramContext, AttributeSet paramAttributeSet)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.WalletImCardImagesView);
    this.mSpacingBetweenCardImages = localTypedArray.getDimensionPixelSize(R.styleable.WalletImCardImagesView_spacingBetweenCardImages, 0);
    this.mAllImagesStaticOnly = localTypedArray.getBoolean(R.styleable.WalletImCardImagesView_allImagesStaticOnly, false);
    localTypedArray.recycle();
  }
  
  public void onFinishInflate()
  {
    super.onFinishInflate();
    if (!this.mAllImagesStaticOnly) {
      this.mUnresolvedCardImage = findViewById(R.id.unresolved_logo);
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if ((!this.mAllImagesStaticOnly) && (this.mSuspendAnimations))
    {
      this.mSuspendAnimations = false;
      this.mCardImagesAnimator.initialize(this.mCurrentIcon);
      if (this.mOneCardMode) {
        switchToOneCardMode();
      }
    }
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof Bundle))
    {
      Bundle localBundle = (Bundle)paramParcelable;
      this.mCurrentIcon = ((ImageWithCaptionOuterClass.ImageWithCaption)ParcelableProto.getProtoFromBundle(localBundle, "currentIcon"));
      this.mOneCardMode = localBundle.getBoolean("oneCardMode");
      super.onRestoreInstanceState(localBundle.getParcelable("parentState"));
      return;
    }
    super.onRestoreInstanceState(paramParcelable);
  }
  
  public Parcelable onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("parentState", super.onSaveInstanceState());
    localBundle.putParcelable("currentIcon", ParcelableProto.forProto(this.mCurrentIcon));
    localBundle.putBoolean("oneCardMode", this.mOneCardMode);
    return localBundle;
  }
  
  public void setCardIcon(ImageWithCaptionOuterClass.ImageWithCaption paramImageWithCaption)
  {
    if (this.mAllImagesStaticOnly) {
      throw new IllegalStateException("setCardIcon cannot be used if allImagesStaticOnly is true.");
    }
    this.mCurrentIcon = paramImageWithCaption;
    if (!this.mSuspendAnimations) {
      this.mCardImagesAnimator.animateToIcon(paramImageWithCaption);
    }
  }
  
  @TargetApi(11)
  public final int setCardIcons(ImageWithCaptionOuterClass.ImageWithCaption[] paramArrayOfImageWithCaption1, ImageWithCaptionOuterClass.ImageWithCaption[] paramArrayOfImageWithCaption2, boolean paramBoolean, int paramInt)
  {
    int i = getResources().getDimensionPixelSize(R.dimen.wallet_im_credit_card_icon_width);
    int j = getResources().getDimensionPixelSize(R.dimen.wallet_im_credit_card_icon_height);
    this.mCardImages = loadImages(paramArrayOfImageWithCaption1, paramInt, i, j, paramBoolean, false);
    int k = paramInt + this.mCardImages.length;
    this.mResolvedOnlyImages = loadImages(paramArrayOfImageWithCaption2, k, i, j, paramBoolean, true);
    int m = k + this.mResolvedOnlyImages.length;
    this.mOneCardMode = paramBoolean;
    ImageView[] arrayOfImageView1;
    ImageView[] arrayOfImageView2;
    boolean bool;
    if (this.mAllImagesStaticOnly)
    {
      if (paramBoolean) {
        throw new IllegalArgumentException("One card mode cannot be used if allImagesStaticOnly is true.");
      }
    }
    else
    {
      arrayOfImageView1 = this.mCardImages;
      arrayOfImageView2 = this.mResolvedOnlyImages;
      bool = this.mOneCardMode;
      if (Build.VERSION.SDK_INT < 14) {
        break label157;
      }
    }
    label157:
    for (Object localObject = new CreditCardImagesAnimatorIcs(arrayOfImageView1, arrayOfImageView2, this.mUnresolvedCardImage);; localObject = new CreditCardImagesAnimatorGingerbread(getContext(), arrayOfImageView1, arrayOfImageView2, this.mUnresolvedCardImage, bool))
    {
      this.mCardImagesAnimator = ((CreditCardImagesAnimator)localObject);
      return m;
    }
  }
  
  public final void switchToOneCardMode()
  {
    if (this.mAllImagesStaticOnly) {
      throw new IllegalStateException("One card mode cannot be used if allImagesStaticOnly is true.");
    }
    if ((this.mCardImagesAnimator instanceof CreditCardImagesAnimatorIcs))
    {
      this.mOneCardMode = true;
      if (!this.mSuspendAnimations) {
        ((CreditCardImagesAnimatorIcs)this.mCardImagesAnimator).switchToOneCardMode();
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.creditcard.CreditCardImagesView
 * JD-Core Version:    0.7.0.1
 */