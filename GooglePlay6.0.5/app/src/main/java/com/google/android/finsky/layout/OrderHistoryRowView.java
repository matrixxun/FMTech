package com.google.android.finsky.layout;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AppActionAnalyzer;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Annotations;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.PurchaseHistoryDetails;
import com.google.android.finsky.utils.DateUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.PlayActionButton;

public class OrderHistoryRowView
  extends MyAccountRowView
{
  private boolean mCanOpen;
  private boolean mCanRefund;
  private TextView mDateView;
  private boolean mHasPurchaseDetails;
  private PlayActionButton mOpenButton;
  private TextView mPriceView;
  private TextView mPurchaseDetailsView;
  private PlayActionButton mRefundButton;
  private TextView mStatusView;
  
  public OrderHistoryRowView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public OrderHistoryRowView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 2602);
  }
  
  public final void bind(final Document paramDocument, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, boolean paramBoolean, PlayStoreUiElementNode paramPlayStoreUiElementNode, int paramInt, final OnRefundActionListener paramOnRefundActionListener)
  {
    super.bind(paramDocument, paramDocument.mDocument.title, paramBitmapLoader, paramBoolean, paramPlayStoreUiElementNode, paramInt, paramNavigationManager);
    PurchaseHistoryDetails localPurchaseHistoryDetails;
    label86:
    int j;
    label129:
    label157:
    label212:
    label219:
    int i;
    if ((paramDocument.mDocument.annotations != null) && (paramDocument.mDocument.annotations.purchaseHistoryDetails != null))
    {
      localPurchaseHistoryDetails = paramDocument.mDocument.annotations.purchaseHistoryDetails;
      if (!localPurchaseHistoryDetails.hasPurchaseTimestampMsec) {
        break label455;
      }
      this.mDateView.setText(DateUtils.formatShortDisplayDate(localPurchaseHistoryDetails.purchaseTimestampMsec));
      this.mDateView.setVisibility(0);
      Common.Offer localOffer = localPurchaseHistoryDetails.offer;
      if ((localOffer == null) || (!localOffer.hasFormattedAmount)) {
        break label466;
      }
      this.mPriceView.setText(localPurchaseHistoryDetails.offer.formattedAmount);
      this.mPriceView.setVisibility(0);
      if (!localPurchaseHistoryDetails.hasPurchaseStatus) {
        break label477;
      }
      this.mStatusView.setText(localPurchaseHistoryDetails.purchaseStatus);
      this.mStatusView.setVisibility(0);
      this.mHasPurchaseDetails = localPurchaseHistoryDetails.hasPurchaseDetailsHtml;
      if (!this.mHasPurchaseDetails) {
        break label495;
      }
      this.mPurchaseDetailsView.setText(Html.fromHtml(localPurchaseHistoryDetails.purchaseDetailsHtml));
      this.mPurchaseDetailsView.setMovementMethod(LinkMovementMethod.getInstance());
      TextView localTextView = this.mPurchaseDetailsView;
      if (!paramBoolean) {
        break label488;
      }
      j = 0;
      localTextView.setVisibility(j);
      this.mCanOpen = NavigationManager.hasClickListener(paramDocument);
      if (!this.mCanOpen) {
        break label513;
      }
      GenericUiElementNode localGenericUiElementNode = new GenericUiElementNode(2605, null, null, this);
      this.mOpenButton.configure(10, 2131362839, paramNavigationManager.getClickListener(paramDocument, localGenericUiElementNode, null, -1, getThumbnailCover()));
      PlayActionButton localPlayActionButton = this.mOpenButton;
      if (!paramBoolean) {
        break label506;
      }
      i = 0;
      label287:
      localPlayActionButton.setVisibility(i);
      label294:
      this.mCanRefund = false;
      if (paramDocument.mDocument.docType == 1)
      {
        final AppActionAnalyzer localAppActionAnalyzer = new AppActionAnalyzer(paramDocument.mDocument.backendDocid, FinskyApp.get().mAppStates, FinskyApp.get().mLibraries);
        if (localAppActionAnalyzer.isRefundable)
        {
          this.mCanRefund = true;
          this.mRefundButton.configure(10, 2131362641, new View.OnClickListener()
          {
            public final void onClick(View paramAnonymousView)
            {
              FinskyApp.get().getEventLogger().logClickEvent(2603, null, OrderHistoryRowView.this);
              paramOnRefundActionListener.onRefundAction(paramDocument.mDocument.backendDocid, localAppActionAnalyzer.refundAccount);
            }
          });
        }
      }
      if ((!this.mCanRefund) && (FinskyApp.get().getExperiments().isEnabled(12603254L)))
      {
        this.mCanRefund = true;
        this.mRefundButton.configure(10, 2131362641, new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            FinskyApp.get().getEventLogger().logClickEvent(2603, null, OrderHistoryRowView.this);
            paramOnRefundActionListener.onRequestRefundAction(paramDocument);
          }
        });
      }
      if ((!this.mCanRefund) || (!paramBoolean)) {
        break label525;
      }
      this.mRefundButton.setVisibility(0);
    }
    for (;;)
    {
      setUpToggleAnimations();
      return;
      localPurchaseHistoryDetails = null;
      break;
      label455:
      this.mDateView.setVisibility(4);
      break label86;
      label466:
      this.mPriceView.setVisibility(4);
      break label129;
      label477:
      this.mStatusView.setVisibility(4);
      break label157;
      label488:
      j = 8;
      break label212;
      label495:
      this.mPurchaseDetailsView.setVisibility(4);
      break label219;
      label506:
      i = 8;
      break label287;
      label513:
      this.mOpenButton.setVisibility(8);
      break label294;
      label525:
      this.mRefundButton.setVisibility(8);
    }
  }
  
  public void onAnimationEnd(Animation paramAnimation)
  {
    if (paramAnimation == this.mExpandAnimation)
    {
      if (this.mHasPurchaseDetails) {
        animateFadeIn(this.mPurchaseDetailsView);
      }
      if (this.mCanOpen) {
        animateFadeIn(this.mOpenButton);
      }
      if (this.mCanRefund) {
        animateFadeIn(this.mRefundButton);
      }
    }
  }
  
  public void onAnimationStart(Animation paramAnimation)
  {
    if (paramAnimation == this.mCollapseAnimation)
    {
      this.mPurchaseDetailsView.setVisibility(8);
      this.mOpenButton.setVisibility(8);
      this.mRefundButton.setVisibility(8);
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mDateView = ((TextView)findViewById(2131755795));
    this.mPriceView = ((TextView)findViewById(2131755679));
    this.mStatusView = ((TextView)findViewById(2131755611));
    this.mPurchaseDetailsView = ((TextView)findViewById(2131755645));
    this.mOpenButton = ((PlayActionButton)findViewById(2131755797));
    this.mRefundButton = ((PlayActionButton)findViewById(2131755796));
  }
  
  public static abstract interface OnRefundActionListener
  {
    public abstract void onRefundAction(String paramString1, String paramString2);
    
    public abstract void onRequestRefundAction(Document paramDocument);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.OrderHistoryRowView
 * JD-Core Version:    0.7.0.1
 */