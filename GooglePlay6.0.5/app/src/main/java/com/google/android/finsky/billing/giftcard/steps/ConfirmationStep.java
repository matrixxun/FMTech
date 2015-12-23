package com.google.android.finsky.billing.giftcard.steps;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.billing.giftcard.RedeemCodeFragment;
import com.google.android.finsky.billing.giftcard.RedeemCodeSidecar;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.config.G;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.PromoCode.UserConfirmationChallenge;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.image.FifeImageView.OnLoadedListener;
import com.google.android.play.utils.config.GservicesValue;

public final class ConfirmationStep
  extends StepFragment<RedeemCodeFragment>
{
  private int mBillingUiMode;
  public PromoCode.UserConfirmationChallenge mChallenge;
  private View mMainView;
  private PlayStore.PlayStoreUiElement mPlayStoreUiElement = FinskyEventLog.obtainPlayStoreUiElement(883);
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    return this.mChallenge.buttonLabel;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mPlayStoreUiElement;
  }
  
  public final void onContinueButtonClicked()
  {
    logClick(884, null);
    RedeemCodeSidecar localRedeemCodeSidecar = ((RedeemCodeFragment)this.mParentFragment).mSidecar;
    if (localRedeemCodeSidecar.mState != 5)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(localRedeemCodeSidecar.mState);
      FinskyLog.wtf("Invalid state: %d", arrayOfObject);
      return;
    }
    localRedeemCodeSidecar.mRequest.hasUserConfirmation = true;
    localRedeemCodeSidecar.sendRedemptionRequest();
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mChallenge = ((PromoCode.UserConfirmationChallenge)ParcelableProto.getProtoFromBundle(this.mArguments, "ConfirmationStep.challenge"));
    this.mBillingUiMode = this.mArguments.getInt("ui_mode");
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    int i;
    TextView localTextView1;
    label83:
    TextView localTextView2;
    label143:
    FifeImageView localFifeImageView;
    label205:
    TextView localTextView3;
    label244:
    TextView localTextView4;
    label413:
    TextView localTextView5;
    label474:
    TextView localTextView6;
    label535:
    TextView localTextView7;
    if (this.mBillingUiMode == 0)
    {
      i = 2130969062;
      this.mMainView = paramLayoutInflater.inflate(i, paramViewGroup, false);
      if (this.mBillingUiMode == 1) {
        getActivity().setTitle(getString(2131362744));
      }
      localTextView1 = (TextView)this.mMainView.findViewById(2131755173);
      if (TextUtils.isEmpty(this.mChallenge.title)) {
        break label629;
      }
      localTextView1.setText(this.mChallenge.title);
      localTextView2 = (TextView)this.mMainView.findViewById(2131755338);
      if (TextUtils.isEmpty(this.mChallenge.titleBylineHtml)) {
        break label639;
      }
      localTextView2.setText(Html.fromHtml(this.mChallenge.titleBylineHtml));
      localTextView2.setMovementMethod(LinkMovementMethod.getInstance());
      localTextView2.setLinkTextColor(localTextView2.getTextColors());
      localFifeImageView = (FifeImageView)this.mMainView.findViewById(2131755170);
      Common.Image localImage = this.mChallenge.thumbnailImage;
      if (localImage == null) {
        break label649;
      }
      localFifeImageView.setOnLoadedListener(new FifeImageView.OnLoadedListener()
      {
        public final void onLoaded(FifeImageView paramAnonymousFifeImageView, Bitmap paramAnonymousBitmap)
        {
          if (paramAnonymousBitmap != null)
          {
            ViewGroup.LayoutParams localLayoutParams = paramAnonymousFifeImageView.getLayoutParams();
            float f = paramAnonymousBitmap.getWidth() / paramAnonymousBitmap.getHeight();
            localLayoutParams.height = ((int)(localLayoutParams.width / f));
            ConfirmationStep.this.mMainView.requestLayout();
          }
        }
        
        public final void onLoadedAndFadedIn(FifeImageView paramAnonymousFifeImageView) {}
      });
      localFifeImageView.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, FinskyApp.get().mBitmapLoader);
      localTextView3 = (TextView)this.mMainView.findViewById(2131755679);
      if (TextUtils.isEmpty(this.mChallenge.formattedPrice)) {
        break label659;
      }
      localTextView3.setText(this.mChallenge.formattedPrice);
      localTextView4 = (TextView)this.mMainView.findViewById(2131755639);
      if (TextUtils.isEmpty(this.mChallenge.priceBylineHtml)) {
        break label669;
      }
      String str = this.mChallenge.priceBylineHtml;
      localTextView4.setText(Html.fromHtml(str));
      localTextView4.setMovementMethod(LinkMovementMethod.getInstance());
      localTextView4.setLinkTextColor(localTextView4.getTextColors());
      if ((str.startsWith("<strike>")) && (str.endsWith("</strike>"))) {
        localTextView4.setPaintFlags(0x10 | localTextView4.getPaintFlags());
      }
      Resources localResources1 = getResources();
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = localTextView4.getText();
      localTextView4.setContentDescription(localResources1.getString(2131361989, arrayOfObject1));
      Resources localResources2 = getResources();
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = localTextView3.getText();
      localTextView3.setContentDescription(localResources2.getString(2131361975, arrayOfObject2));
      localTextView5 = (TextView)this.mMainView.findViewById(2131755233);
      if (TextUtils.isEmpty(this.mChallenge.messageHtml)) {
        break label679;
      }
      localTextView5.setText(Html.fromHtml(this.mChallenge.messageHtml));
      localTextView5.setMovementMethod(LinkMovementMethod.getInstance());
      localTextView5.setLinkTextColor(localTextView5.getTextColors());
      localTextView6 = (TextView)this.mMainView.findViewById(2131756029);
      if (TextUtils.isEmpty(this.mChallenge.footerHtml)) {
        break label689;
      }
      localTextView6.setText(Html.fromHtml(this.mChallenge.footerHtml));
      localTextView6.setMovementMethod(LinkMovementMethod.getInstance());
      localTextView6.setLinkTextColor(localTextView6.getTextColors());
      localTextView7 = (TextView)this.mMainView.findViewById(2131755324);
      if (!this.mArguments.getBoolean("ConfirmationStep.code_screen_skipped", false)) {
        break label699;
      }
      localTextView7.setText(Html.fromHtml(getString(2131362639, new Object[] { BillingUtils.replaceLocale((String)G.redeemTermsAndConditionsUrl.get()) })));
      localTextView7.setMovementMethod(LinkMovementMethod.getInstance());
      localTextView7.setLinkTextColor(localTextView7.getTextColors());
    }
    for (;;)
    {
      return this.mMainView;
      i = 2130969111;
      break;
      label629:
      localTextView1.setVisibility(8);
      break label83;
      label639:
      localTextView2.setVisibility(8);
      break label143;
      label649:
      localFifeImageView.setVisibility(8);
      break label205;
      label659:
      localTextView3.setVisibility(8);
      break label244;
      label669:
      localTextView4.setVisibility(8);
      break label413;
      label679:
      localTextView5.setVisibility(8);
      break label474;
      label689:
      localTextView6.setVisibility(8);
      break label535;
      label699:
      localTextView7.setVisibility(8);
    }
  }
  
  public final void onResume()
  {
    super.onResume();
    Context localContext = this.mMainView.getContext();
    if (this.mBillingUiMode == 1) {}
    for (String str = getString(2131362744);; str = this.mChallenge.title)
    {
      UiUtils.sendAccessibilityEventWithText(localContext, str, this.mMainView);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.giftcard.steps.ConfirmationStep
 * JD-Core Version:    0.7.0.1
 */