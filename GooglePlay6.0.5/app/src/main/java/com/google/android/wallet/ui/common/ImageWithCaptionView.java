package com.google.android.wallet.ui.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import com.android.volley.toolbox.ImageLoader;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.uicomponents.R.styleable;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.ImageWithCaptionOuterClass.ImageWithCaption;

public class ImageWithCaptionView
  extends FifeNetworkImageView
{
  private int mDefaultImageResId;
  
  public ImageWithCaptionView(Context paramContext)
  {
    super(paramContext);
    readAttributes(paramContext, null);
  }
  
  public ImageWithCaptionView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    readAttributes(paramContext, paramAttributeSet);
  }
  
  public ImageWithCaptionView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    readAttributes(paramContext, paramAttributeSet);
  }
  
  private void readAttributes(Context paramContext, AttributeSet paramAttributeSet)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.WalletUicImageWithCaptionView);
    setErrorImageResId(localTypedArray.getResourceId(R.styleable.WalletUicImageWithCaptionView_internalUicErrorImage, 0));
    setDefaultImageResId(localTypedArray.getResourceId(R.styleable.WalletUicImageWithCaptionView_internalUicDefaultImage, 0));
    localTypedArray.recycle();
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof Bundle))
    {
      Bundle localBundle = (Bundle)paramParcelable;
      this.mDefaultImageResId = localBundle.getInt("defaultImageResId");
      super.onRestoreInstanceState(localBundle.getParcelable("parentState"));
      return;
    }
    super.onRestoreInstanceState(paramParcelable);
  }
  
  public Parcelable onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("parentState", super.onSaveInstanceState());
    localBundle.putInt("defaultImageResId", this.mDefaultImageResId);
    return localBundle;
  }
  
  public void setDefaultImageResId(int paramInt)
  {
    super.setDefaultImageResId(paramInt);
    this.mDefaultImageResId = paramInt;
  }
  
  @TargetApi(11)
  public void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    if ((Build.VERSION.SDK_INT >= 11) && (getDrawable() != null) && (!getDrawable().isStateful())) {
      if (!paramBoolean) {
        break label42;
      }
    }
    label42:
    for (float f = 1.0F;; f = 0.4F)
    {
      setAlpha(f);
      return;
    }
  }
  
  public final void setImageWithCaption(ImageWithCaptionOuterClass.ImageWithCaption paramImageWithCaption, ImageLoader paramImageLoader, boolean paramBoolean)
  {
    if (PaymentUtils.isEmbeddedImageUri(paramImageWithCaption.imageUri)) {
      super.setDefaultImageResId(WalletUiUtils.embeddedImageUriToDrawableResourceId(getContext(), paramImageWithCaption.imageUri));
    }
    for (;;)
    {
      setContentDescription(paramImageWithCaption.altText);
      return;
      super.setDefaultImageResId(this.mDefaultImageResId);
      setFifeImageUrl(paramImageWithCaption.imageUri, paramImageLoader, paramBoolean);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.ImageWithCaptionView
 * JD-Core Version:    0.7.0.1
 */