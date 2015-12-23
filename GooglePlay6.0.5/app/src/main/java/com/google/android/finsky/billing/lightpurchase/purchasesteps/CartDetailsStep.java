package com.google.android.finsky.billing.lightpurchase.purchasesteps;

import android.accounts.Account;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.billing.lightpurchase.CheckoutPurchaseSidecar;
import com.google.android.finsky.billing.lightpurchase.GiftEmailParams;
import com.google.android.finsky.billing.lightpurchase.PurchaseFragment;
import com.google.android.finsky.billing.lightpurchase.PurchaseParams;
import com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.RedeemCodeActivity;
import com.google.android.finsky.billing.lightpurchase.multistep.MultiStepFragment;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.DisabledInfo;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Instrument;
import com.google.android.finsky.protos.Purchase.ApplicableVoucher;
import com.google.android.finsky.protos.Purchase.ClientCart;
import com.google.android.finsky.protos.Purchase.SplitTenderInfo;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.UiUtils;

public final class CartDetailsStep
  extends StepFragment<PurchaseFragment>
  implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, PlayStoreUiElementNode
{
  private int mBackend;
  private Purchase.ClientCart mCart;
  private boolean mContinueButtonConfirmsPurchase;
  private boolean mContinueButtonShowsInstrumentManager;
  private FinskyEventLog mEventLog;
  private boolean mExpanded;
  private GiftEmailParams mGiftEmailParams;
  private View mGiftView;
  private View mHeader;
  private TextView mPaymentSettingsView;
  private TextView mPriceView;
  private View mPurchaseDetailsView;
  private TextView mRedeemView;
  private View mSelectedVoucherView;
  private CheckBox mSplitTenderCheckBox;
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(710);
  
  public static CartDetailsStep newInstance(int paramInt, Purchase.ClientCart paramClientCart, boolean paramBoolean1, boolean paramBoolean2, GiftEmailParams paramGiftEmailParams)
  {
    Bundle localBundle = new Bundle();
    CartDetailsStep localCartDetailsStep = new CartDetailsStep();
    localBundle.putInt("CartDetailsStep.backend", paramInt);
    localBundle.putParcelable("CartDetailsStep.cart", ParcelableProto.forProto(paramClientCart));
    localBundle.putBoolean("CartDetailsStep.continueToInstrumentManager", paramBoolean1);
    localBundle.putBoolean("CartDetailsStep.expandedAtStart", paramBoolean2);
    localBundle.putParcelable("CartDetailsStep.giftEmailParams", paramGiftEmailParams);
    localCartDetailsStep.setArguments(localBundle);
    localCartDetailsStep.mCart = paramClientCart;
    return localCartDetailsStep;
  }
  
  private static void populateContainerWithTextViews(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, int paramInt1, String[] paramArrayOfString, int paramInt2)
  {
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
    {
      String str = paramArrayOfString[j];
      TextView localTextView = (TextView)paramLayoutInflater.inflate(paramInt1, paramViewGroup, false);
      localTextView.setText(BillingUtils.parseHtmlAndColorizeEm(str, paramInt2));
      localTextView.setMovementMethod(LinkMovementMethod.getInstance());
      localTextView.setLinkTextColor(localTextView.getTextColors());
      paramViewGroup.addView(localTextView);
    }
    int k = paramArrayOfString.length;
    int m = 0;
    if (k > 0) {}
    for (;;)
    {
      paramViewGroup.setVisibility(m);
      return;
      m = 8;
    }
  }
  
  private void toggleExpansion()
  {
    if (!this.mExpanded) {}
    for (boolean bool = true;; bool = false)
    {
      this.mExpanded = bool;
      if (this.mExpanded) {
        this.mEventLog.logPathImpression$7d139cbf(715, this);
      }
      updateExpansion();
      return;
    }
  }
  
  private void updateExpansion()
  {
    int i = 2130837786;
    int j = 8;
    View localView1 = this.mPurchaseDetailsView;
    int k;
    label82:
    int n;
    if (this.mExpanded)
    {
      k = 0;
      localView1.setVisibility(k);
      if (!this.mExpanded) {
        break label242;
      }
      switch (this.mBackend)
      {
      case 5: 
      default: 
        if (CorpusResourceUtils.isEnterpriseTheme())
        {
          TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds$30b381af(this.mPriceView, i);
          if (this.mSelectedVoucherView != null)
          {
            View localView3 = this.mSelectedVoucherView;
            if (!this.mExpanded) {
              break label344;
            }
            n = j;
            label113:
            localView3.setVisibility(n);
          }
          if (this.mGiftView != null)
          {
            View localView2 = this.mGiftView;
            if (!this.mExpanded) {
              break label350;
            }
            label140:
            localView2.setVisibility(j);
          }
          if (!this.mExpanded) {
            break label355;
          }
        }
        break;
      }
    }
    label344:
    label350:
    label355:
    for (int m = 2131361976;; m = 2131361977)
    {
      TextView localTextView = this.mPriceView;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mCart.formattedPrice;
      localTextView.setContentDescription(getString(m, arrayOfObject));
      return;
      k = j;
      break;
      if (CorpusResourceUtils.isEnterpriseTheme()) {
        break label82;
      }
      i = 2130837788;
      break label82;
      i = 2130837790;
      break label82;
      i = 2130837800;
      break label82;
      i = 2130837796;
      break label82;
      i = 2130837798;
      break label82;
      i = 2130837792;
      break label82;
      label242:
      switch (this.mBackend)
      {
      case 5: 
      default: 
        if (CorpusResourceUtils.isEnterpriseTheme()) {
          i = 2130837787;
        }
        break;
      case 3: 
        if (CorpusResourceUtils.isEnterpriseTheme())
        {
          i = 2130837787;
          break;
        }
        i = 2130837789;
        break;
      case 1: 
        i = 2130837791;
        break;
      case 6: 
        i = 2130837801;
        break;
      case 4: 
        i = 2130837797;
        break;
      case 2: 
        i = 2130837799;
        break;
        i = 2130837794;
        break;
        n = 0;
        break label113;
        j = 0;
        break label140;
      }
    }
  }
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    return this.mCart.buttonText;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public final void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
  {
    if (paramCompoundButton == this.mSplitTenderCheckBox) {
      ((PurchaseFragment)this.mParentFragment).preparePurchase(null, Boolean.valueOf(paramBoolean));
    }
  }
  
  public final void onClick(View paramView)
  {
    if (paramView == this.mHeader)
    {
      logClick(713, null);
      if (((PurchaseFragment)this.mParentFragment).mSidecar.mIsInstantPurchase)
      {
        ((PurchaseFragment)this.mParentFragment).mSidecar.mCartExpandedAtStart = true;
        ((PurchaseFragment)this.mParentFragment).preparePurchase(null, null);
      }
    }
    do
    {
      return;
      UiUtils.sendAccessibilityEventWithText(paramView.getContext(), getString(2131362492), paramView);
      toggleExpansion();
      return;
      if (paramView == this.mPaymentSettingsView)
      {
        logClick(714, null);
        ((PurchaseFragment)this.mParentFragment).launchBillingProfile();
        return;
      }
      if (paramView == this.mRedeemView)
      {
        logClick(716, null);
        PurchaseFragment localPurchaseFragment = (PurchaseFragment)this.mParentFragment;
        localPurchaseFragment.startActivityForResult(RedeemCodeActivity.createBuyFlowIntent(localPurchaseFragment.mAccount.name, localPurchaseFragment.mParams.docid.backend, localPurchaseFragment.mParams.docid, localPurchaseFragment.mParams.offerType), 3);
        return;
      }
    } while (paramView != this.mSelectedVoucherView);
    logClick(717, null);
    UiUtils.sendAccessibilityEventWithText(paramView.getContext(), getString(2131362842), paramView);
    toggleExpansion();
  }
  
  public final void onContinueButtonClicked()
  {
    if (this.mContinueButtonShowsInstrumentManager)
    {
      logClick(712, null);
      CheckoutPurchaseSidecar localCheckoutPurchaseSidecar = ((PurchaseFragment)this.mParentFragment).mSidecar;
      if (localCheckoutPurchaseSidecar.mState != 6)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(localCheckoutPurchaseSidecar.mState);
        FinskyLog.wtf("switchToInstrumentManager() called in state %d", arrayOfObject);
      }
      localCheckoutPurchaseSidecar.setState(9, 0);
      return;
    }
    if (this.mContinueButtonConfirmsPurchase)
    {
      logClick(711, null);
      PurchaseFragment localPurchaseFragment = (PurchaseFragment)this.mParentFragment;
      if (localPurchaseFragment.mParams.docid.type == 1) {
        FinskyApp.get().getAnalytics(localPurchaseFragment.mAccount.name).logAdMobPageView("completePurchase?doc=" + localPurchaseFragment.mParams.docidStr);
      }
      localPurchaseFragment.showAppDownloadWarningAndContinue();
      return;
    }
    logClick(712);
    ((PurchaseFragment)this.mParentFragment).launchBillingProfile();
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = this.mArguments;
    this.mBackend = localBundle.getInt("CartDetailsStep.backend");
    this.mCart = ((Purchase.ClientCart)ParcelableProto.getProtoFromBundle(localBundle, "CartDetailsStep.cart"));
    this.mContinueButtonShowsInstrumentManager = localBundle.getBoolean("CartDetailsStep.continueToInstrumentManager");
    if (paramBundle == null) {}
    for (this.mExpanded = localBundle.getBoolean("CartDetailsStep.expandedAtStart");; this.mExpanded = paramBundle.getBoolean("CartDetailsStep.expanded"))
    {
      this.mGiftEmailParams = ((GiftEmailParams)localBundle.getParcelable("CartDetailsStep.giftEmailParams"));
      this.mEventLog = FinskyApp.get().getEventLogger(((PurchaseFragment)this.mParentFragment).mAccount);
      return;
    }
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    ColorStateList localColorStateList = CorpusResourceUtils.getSecondaryTextColor(getActivity(), this.mBackend);
    int i = localColorStateList.getDefaultColor();
    FinskyExperiments localFinskyExperiments = FinskyApp.get().getExperiments(((PurchaseFragment)this.mParentFragment).mAccount.name);
    View localView1 = paramLayoutInflater.inflate(2130968810, paramViewGroup, false);
    TextView localTextView1 = (TextView)localView1.findViewById(2131755635);
    TextView localTextView2 = (TextView)localView1.findViewById(2131755636);
    this.mPriceView = ((TextView)localView1.findViewById(2131755638));
    TextView localTextView3 = (TextView)localView1.findViewById(2131755637);
    TextView localTextView4 = (TextView)localView1.findViewById(2131755657);
    TextView localTextView5 = (TextView)localView1.findViewById(2131755621);
    localTextView5.setVisibility(0);
    localTextView5.setText(((PurchaseFragment)this.mParentFragment).mAccount.name);
    this.mPurchaseDetailsView = localView1.findViewById(2131755645);
    TextView localTextView6 = (TextView)localView1.findViewById(2131755232);
    this.mPaymentSettingsView = ((TextView)localView1.findViewById(2131755646));
    this.mPaymentSettingsView.setOnClickListener(this);
    this.mPaymentSettingsView.setTextColor(i);
    View localView2 = localView1.findViewById(2131755647);
    this.mRedeemView = ((TextView)localView1.findViewById(2131755648));
    this.mRedeemView.setOnClickListener(this);
    this.mRedeemView.setTextColor(i);
    this.mHeader = localView1.findViewById(2131755487);
    localTextView1.setText(this.mCart.title);
    if (this.mCart.hasSubtitle)
    {
      localTextView2.setText(this.mCart.subtitle);
      localTextView2.setVisibility(0);
    }
    this.mPriceView.setText(this.mCart.formattedPrice);
    this.mPriceView.setTextColor(localColorStateList);
    TextView localTextView7 = (TextView)localView1.findViewById(2131755639);
    label429:
    Instrument localInstrument;
    if (localFinskyExperiments.isEnabled(12603787L)) {
      if (this.mCart.hasPriceByline)
      {
        localTextView7.setText(this.mCart.priceByline);
        localTextView7.setVisibility(0);
        if (this.mCart.hasPriceByline2)
        {
          TextView localTextView16 = (TextView)localView1.findViewById(2131755640);
          localTextView16.setText(this.mCart.priceByline2);
          localTextView16.setVisibility(0);
        }
        localInstrument = this.mCart.instrument;
        if (localInstrument == null) {
          break label933;
        }
        localTextView3.setText(localInstrument.displayTitle);
        localTextView3.setVisibility(0);
        if (localInstrument.disabledInfo.length <= 0) {
          break label860;
        }
        TextView localTextView15 = (TextView)localView1.findViewById(2131755642);
        localTextView15.setText(Html.fromHtml(localInstrument.disabledInfo[0].disabledMessageHtml));
        localTextView15.setMovementMethod(LinkMovementMethod.getInstance());
        localTextView15.setVisibility(0);
        label513:
        populateContainerWithTextViews(paramLayoutInflater, (ViewGroup)localView1.findViewById(2131755641), 2130968811, this.mCart.detailHtml, i);
        if (!this.mCart.hasFooterHtml) {
          break label974;
        }
        localTextView6.setVisibility(0);
        localTextView6.setText(BillingUtils.parseHtmlAndColorizeEm(this.mCart.footerHtml, i));
        localTextView6.setMovementMethod(LinkMovementMethod.getInstance());
        localTextView6.setLinkTextColor(localTextView6.getTextColors());
      }
    }
    ViewGroup localViewGroup;
    Object localObject1;
    int m;
    Purchase.ApplicableVoucher localApplicableVoucher;
    View localView3;
    TextView localTextView14;
    for (;;)
    {
      if (this.mCart.splitTenderInfo != null)
      {
        this.mSplitTenderCheckBox = ((CheckBox)localView1.findViewById(2131755644));
        this.mSplitTenderCheckBox.setText(this.mCart.splitTenderInfo.splitTenderMessage);
        this.mSplitTenderCheckBox.setChecked(this.mCart.splitTenderInfo.splitApplied);
        this.mSplitTenderCheckBox.setOnCheckedChangeListener(this);
        this.mSplitTenderCheckBox.setVisibility(0);
      }
      if ((this.mCart.applicableVouchers == null) || (this.mCart.applicableVouchers.length <= 0)) {
        break label1282;
      }
      localViewGroup = (ViewGroup)localView1.findViewById(2131755650);
      localViewGroup.setVisibility(0);
      localObject1 = null;
      Purchase.ApplicableVoucher[] arrayOfApplicableVoucher = this.mCart.applicableVouchers;
      int k = arrayOfApplicableVoucher.length;
      m = 0;
      if (m >= k) {
        break label1081;
      }
      localApplicableVoucher = arrayOfApplicableVoucher[m];
      localView3 = paramLayoutInflater.inflate(2130968813, localViewGroup, false);
      localTextView14 = (TextView)localView3.findViewById(2131755173);
      localTextView14.setText(localApplicableVoucher.doc.title);
      if (!localApplicableVoucher.applied) {
        break label1045;
      }
      if (localObject1 == null) {
        break label984;
      }
      throw new IllegalArgumentException("Multiple applied vouchers is not supported");
      localTextView7.setVisibility(8);
      break;
      if (this.mCart.hasPriceByline)
      {
        localTextView7.setText(this.mCart.priceByline);
        localTextView7.setTextColor(localColorStateList);
        localTextView7.setVisibility(0);
        break label429;
      }
      localTextView7.setVisibility(8);
      break label429;
      label860:
      populateContainerWithTextViews(paramLayoutInflater, (ViewGroup)localView1.findViewById(2131755652), 2130968812, this.mCart.extendedDetailHtml, i);
      localTextView5.setText(((PurchaseFragment)this.mParentFragment).mAccount.name);
      if (!this.mContinueButtonShowsInstrumentManager) {}
      for (boolean bool = true;; bool = false)
      {
        this.mContinueButtonConfirmsPurchase = bool;
        break;
      }
      label933:
      localTextView3.setVisibility(8);
      localTextView4.setText(BillingUtils.parseHtmlAndColorizeEm(this.mCart.addInstrumentPromptHtml, i));
      localTextView4.setMovementMethod(LinkMovementMethod.getInstance());
      localTextView4.setVisibility(0);
      break label513;
      label974:
      localTextView6.setVisibility(8);
    }
    label984:
    localTextView14.setTypeface(localTextView14.getTypeface(), 1);
    localView3.findViewById(2131755294).setVisibility(0);
    View.OnClickListener local1 = new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        CartDetailsStep.this.mEventLog.logClickEvent(719, null, CartDetailsStep.this);
        UiUtils.sendAccessibilityEventWithText(paramAnonymousView.getContext(), CartDetailsStep.this.getString(2131362035), paramAnonymousView);
        ((PurchaseFragment)CartDetailsStep.access$100(CartDetailsStep.this)).switchVoucher(null);
      }
    };
    localView3.setOnClickListener(local1);
    for (Object localObject2 = localApplicableVoucher;; localObject2 = localObject1)
    {
      localViewGroup.addView(localView3);
      m++;
      localObject1 = localObject2;
      break;
      label1045:
      final String str = localApplicableVoucher.doc.docid;
      View.OnClickListener local2 = new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          CartDetailsStep.this.mEventLog.logClickEvent(718, null, CartDetailsStep.this);
          UiUtils.sendAccessibilityEventWithText(paramAnonymousView.getContext(), CartDetailsStep.this.getString(2131362012), paramAnonymousView);
          ((PurchaseFragment)CartDetailsStep.access$200(CartDetailsStep.this)).switchVoucher(str);
        }
      };
      localView3.setOnClickListener(local2);
    }
    label1081:
    TextView localTextView11 = (TextView)localView1.findViewById(2131755649);
    localTextView11.setVisibility(0);
    localTextView11.setTextColor(i);
    int n;
    TextView localTextView12;
    label1226:
    TextView localTextView13;
    label1282:
    int j;
    if (localObject1 == null)
    {
      n = 2131362843;
      localTextView11.setText(n);
      localView1.findViewById(2131755651).setVisibility(0);
      if (localObject1 != null)
      {
        this.mSelectedVoucherView = localView1.findViewById(2131755643);
        this.mSelectedVoucherView.setVisibility(0);
        this.mSelectedVoucherView.setOnClickListener(this);
        ((TextView)this.mSelectedVoucherView.findViewById(2131756104)).setText(localObject1.doc.title);
        localTextView12 = (TextView)this.mSelectedVoucherView.findViewById(2131756105);
        if (!TextUtils.isEmpty(localObject1.formattedDiscountAmount)) {
          break label1518;
        }
        localTextView12.setVisibility(8);
        if (!TextUtils.isEmpty(localObject1.discountAmountDescription)) {
          localTextView12.setContentDescription(localObject1.discountAmountDescription);
        }
        localTextView13 = (TextView)this.mSelectedVoucherView.findViewById(2131756106);
        if (!TextUtils.isEmpty(this.mCart.formattedOriginalPrice)) {
          break label1550;
        }
        localTextView13.setVisibility(8);
      }
      if (this.mGiftEmailParams != null)
      {
        TextView localTextView8 = (TextView)localView1.findViewById(2131755654);
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = this.mGiftEmailParams.recipientEmailAddress;
        localTextView8.setText(getString(2131362795, arrayOfObject1));
        TextView localTextView9 = (TextView)localView1.findViewById(2131755655);
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = this.mGiftEmailParams.senderName;
        localTextView9.setText(getString(2131362181, arrayOfObject2));
        TextView localTextView10 = (TextView)localView1.findViewById(2131755656);
        if (!TextUtils.isEmpty(this.mGiftEmailParams.giftMessage))
        {
          Object[] arrayOfObject3 = new Object[1];
          arrayOfObject3[0] = this.mGiftEmailParams.giftMessage;
          localTextView10.setText(getString(2131362325, arrayOfObject3));
        }
        this.mGiftView = localView1.findViewById(2131755653);
      }
      j = 1;
      if (localFinskyExperiments.isEnabled(12603124L))
      {
        if ((localInstrument == null) || (localInstrument.disabledInfo.length != 0)) {
          break label1584;
        }
        localView2.setVisibility(8);
        this.mRedeemView.setVisibility(8);
      }
    }
    for (;;)
    {
      if (j != 0)
      {
        this.mHeader.setOnClickListener(this);
        updateExpansion();
      }
      return localView1;
      n = 2131362842;
      break;
      label1518:
      Object[] arrayOfObject4 = new Object[1];
      arrayOfObject4[0] = localObject1.formattedDiscountAmount;
      localTextView12.setText(getString(2131361924, arrayOfObject4));
      break label1226;
      label1550:
      Object[] arrayOfObject5 = new Object[1];
      arrayOfObject5[0] = this.mCart.formattedOriginalPrice;
      localTextView13.setText(getString(2131361925, arrayOfObject5));
      break label1282;
      label1584:
      j = 0;
    }
  }
  
  public final void onResume()
  {
    super.onResume();
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = this.mCart.formattedPrice;
    String str = getString(2131361975, arrayOfObject1);
    Context localContext = this.mHeader.getContext();
    Object[] arrayOfObject2 = new Object[2];
    arrayOfObject2[0] = this.mCart.title;
    arrayOfObject2[1] = str;
    UiUtils.sendAccessibilityEventWithText(localContext, getString(2131362614, arrayOfObject2), this.mHeader);
    if (this.mSelectedVoucherView != null) {
      UiUtils.sendAccessibilityEventWithText(this.mSelectedVoucherView.getContext(), ((TextView)this.mSelectedVoucherView.findViewById(2131756104)).getText(), this.mSelectedVoucherView);
    }
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("CartDetailsStep.expanded", this.mExpanded);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.purchasesteps.CartDetailsStep
 * JD-Core Version:    0.7.0.1
 */