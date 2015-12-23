package com.google.android.wallet.ui.common;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.common.util.ParcelableProto;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.config.G.images;
import com.google.android.wallet.uicomponents.R.dimen;
import com.google.android.wallet.uicomponents.R.id;
import com.google.android.wallet.uicomponents.R.layout;
import com.google.android.wallet.uicomponents.R.string;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.ImageWithCaptionOuterClass.ImageWithCaption;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.TooltipOuterClass.Tooltip;

public final class TooltipDialogFragment
  extends BaseWalletUiComponentDialogFragment
{
  public static TooltipDialogFragment newInstance(TooltipOuterClass.Tooltip paramTooltip, int paramInt)
  {
    TooltipDialogFragment localTooltipDialogFragment = new TooltipDialogFragment();
    Bundle localBundle = createArgs(paramInt);
    localTooltipDialogFragment.setArguments(localBundle);
    localBundle.putParcelable("tooltipProto", ParcelableProto.forProto(paramTooltip));
    return localTooltipDialogFragment;
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    TooltipOuterClass.Tooltip localTooltip = (TooltipOuterClass.Tooltip)ParcelableProto.getProtoFromBundle(this.mArguments, "tooltipProto");
    View localView = getThemedLayoutInflater().inflate(R.layout.view_tooltip_dialog, null);
    ViewGroup localViewGroup = (ViewGroup)localView.findViewById(R.id.container);
    InfoMessageTextView localInfoMessageTextView = (InfoMessageTextView)localView.findViewById(R.id.info_message);
    localInfoMessageTextView.setInfoMessage(localTooltip.infoMessage);
    localInfoMessageTextView.setUrlClickListener((ClickSpan.OnClickListener)this.mTarget);
    ImageWithCaptionOuterClass.ImageWithCaption[] arrayOfImageWithCaption = localTooltip.image;
    int i = arrayOfImageWithCaption.length;
    int j = 0;
    if (j < i)
    {
      ImageWithCaptionOuterClass.ImageWithCaption localImageWithCaption = arrayOfImageWithCaption[j];
      ImageWithCaptionView localImageWithCaptionView = new ImageWithCaptionView(getThemedContext());
      LinearLayout.LayoutParams localLayoutParams;
      if (PaymentUtils.isEmbeddedImageUri(localImageWithCaption.imageUri)) {
        localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
      }
      for (;;)
      {
        localLayoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.wallet_uic_tooltip_image_top_margin);
        localImageWithCaptionView.setImageWithCaption(localImageWithCaption, PaymentUtils.getImageLoader(getActivity().getApplicationContext()), ((Boolean)G.images.useWebPForFife.get()).booleanValue());
        localImageWithCaptionView.setFadeIn(true);
        localImageWithCaptionView.setAdjustViewBounds(true);
        localViewGroup.addView(localImageWithCaptionView, localLayoutParams);
        j++;
        break;
        if ((localImageWithCaption.widthPixels > 0) && (localImageWithCaption.heightPixels > 0)) {
          localLayoutParams = new LinearLayout.LayoutParams(localImageWithCaption.widthPixels, localImageWithCaption.heightPixels);
        } else {
          localLayoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.wallet_uic_tooltip_dialog_image_default_width), -2);
        }
      }
    }
    return new AlertDialogBuilderCompat(getThemedContext()).setTitle(localTooltip.title).setView(localView).setPositiveButton(R.string.wallet_uic_close, null).create();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.TooltipDialogFragment
 * JD-Core Version:    0.7.0.1
 */