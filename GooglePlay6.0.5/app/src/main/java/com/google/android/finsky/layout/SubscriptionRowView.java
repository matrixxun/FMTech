package com.google.android.finsky.layout;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.LibrarySubscriptionEntry;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Annotations;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.MySubscriptionDetails;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.PlayActionButton;

public class SubscriptionRowView
  extends MyAccountRowView
{
  private PlayActionButton mActionButton;
  private boolean mHasAction;
  private boolean mHasInstrumentDeclined;
  private boolean mHasStatusPrice;
  private TextView mPriceView;
  private TextView mStatusPriceExpandedView;
  private TextView mStatusView;
  private TextView mSubscriptionInstrumentDeclinedView;
  private TextView mSubscriptionStatusView;
  private FifeImageView mTitleBylineImageView;
  
  public SubscriptionRowView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SubscriptionRowView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 2642);
  }
  
  public final void bind(final Document paramDocument, BitmapLoader paramBitmapLoader, final NavigationManager paramNavigationManager, boolean paramBoolean, PlayStoreUiElementNode paramPlayStoreUiElementNode, int paramInt, final LibrarySubscriptionEntry paramLibrarySubscriptionEntry, final DfeToc paramDfeToc, final SubscriptionView.CancelListener paramCancelListener)
  {
    final MySubscriptionDetails localMySubscriptionDetails;
    int n;
    label72:
    label79:
    label110:
    int m;
    label148:
    label155:
    label195:
    int k;
    label248:
    label255:
    int i;
    if (paramDocument.hasMySubscriptionDetails())
    {
      localMySubscriptionDetails = paramDocument.mDocument.annotations.mySubscriptionDetails;
      super.bind(paramDocument, localMySubscriptionDetails.title, paramBitmapLoader, paramBoolean, paramPlayStoreUiElementNode, paramInt, paramNavigationManager);
      if (!localMySubscriptionDetails.hasFormattedPrice) {
        break label352;
      }
      this.mPriceView.setText(localMySubscriptionDetails.formattedPrice);
      TextView localTextView4 = this.mPriceView;
      if (!paramBoolean) {
        break label346;
      }
      n = 8;
      localTextView4.setVisibility(n);
      if (!localMySubscriptionDetails.hasSubscriptionStatusHtml) {
        break label363;
      }
      this.mSubscriptionStatusView.setText(Html.fromHtml(localMySubscriptionDetails.subscriptionStatusHtml));
      this.mSubscriptionStatusView.setVisibility(0);
      if (!localMySubscriptionDetails.hasPriceBylineHtml) {
        break label381;
      }
      this.mStatusView.setText(Html.fromHtml(localMySubscriptionDetails.priceBylineHtml));
      TextView localTextView3 = this.mStatusView;
      if (!paramBoolean) {
        break label375;
      }
      m = 8;
      localTextView3.setVisibility(m);
      if (localMySubscriptionDetails.titleBylineIcon == null) {
        break label392;
      }
      this.mTitleBylineImageView.setVisibility(0);
      this.mTitleBylineImageView.setImage(localMySubscriptionDetails.titleBylineIcon.imageUrl, localMySubscriptionDetails.titleBylineIcon.supportsFifeUrlOptions, paramBitmapLoader);
      if ((!localMySubscriptionDetails.hasTitleBylineHtml) || (localMySubscriptionDetails.titleBylineHtml.isEmpty())) {
        break label411;
      }
      this.mHasStatusPrice = true;
      this.mStatusPriceExpandedView.setText(Html.fromHtml(localMySubscriptionDetails.titleBylineHtml));
      TextView localTextView2 = this.mStatusPriceExpandedView;
      if (!paramBoolean) {
        break label404;
      }
      k = 0;
      localTextView2.setVisibility(k);
      this.mHasAction = true;
      PlayActionButton localPlayActionButton = this.mActionButton;
      if (!paramBoolean) {
        break label423;
      }
      i = 0;
      label274:
      localPlayActionButton.setVisibility(i);
      if ((!localMySubscriptionDetails.hasCancelSubscription) || (!localMySubscriptionDetails.cancelSubscription) || (paramLibrarySubscriptionEntry == null) || (paramLibrarySubscriptionEntry.getCurrentSubscriptionState() == 3)) {
        break label430;
      }
      this.mActionButton.configure(10, 2131361919, new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          FinskyApp.get().getEventLogger().logClickEvent(2643, null, SubscriptionRowView.this);
          paramCancelListener.onCancel(paramDocument, paramLibrarySubscriptionEntry);
        }
      });
    }
    for (;;)
    {
      setUpToggleAnimations();
      return;
      localMySubscriptionDetails = null;
      break;
      label346:
      n = 0;
      break label72;
      label352:
      this.mPriceView.setVisibility(4);
      break label79;
      label363:
      this.mSubscriptionStatusView.setVisibility(8);
      break label110;
      label375:
      m = 0;
      break label148;
      label381:
      this.mStatusView.setVisibility(4);
      break label155;
      label392:
      this.mTitleBylineImageView.setVisibility(8);
      break label195;
      label404:
      k = 8;
      break label248;
      label411:
      this.mStatusPriceExpandedView.setVisibility(8);
      break label255;
      label423:
      i = 8;
      break label274;
      label430:
      if (localMySubscriptionDetails.paymentDeclinedLearnMoreLink != null)
      {
        this.mActionButton.configure(10, 2131362491, new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            FinskyApp.get().getEventLogger().logClickEvent(2645, null, SubscriptionRowView.this);
            paramNavigationManager.resolveLink(localMySubscriptionDetails.paymentDeclinedLearnMoreLink, paramDfeToc, paramAnonymousView.getContext().getPackageManager());
          }
        });
        this.mHasInstrumentDeclined = true;
        this.mSubscriptionInstrumentDeclinedView.setText(2131362490);
        TextView localTextView1 = this.mSubscriptionInstrumentDeclinedView;
        if (paramBoolean) {}
        for (int j = 0;; j = 8)
        {
          localTextView1.setVisibility(j);
          break;
        }
      }
      if (NavigationManager.hasClickListener(paramDocument))
      {
        GenericUiElementNode localGenericUiElementNode = new GenericUiElementNode(2644, null, null, this);
        this.mActionButton.configure(10, 2131362839, paramNavigationManager.getClickListener(paramDocument, localGenericUiElementNode, null, -1, getThumbnailCover()));
      }
      else
      {
        this.mHasAction = false;
        this.mActionButton.setVisibility(8);
      }
    }
  }
  
  public void onAnimationEnd(Animation paramAnimation)
  {
    if (paramAnimation == this.mExpandAnimation)
    {
      if (this.mHasAction) {
        animateFadeIn(this.mActionButton);
      }
      if (this.mHasInstrumentDeclined) {
        animateFadeIn(this.mSubscriptionInstrumentDeclinedView);
      }
      if (this.mHasStatusPrice)
      {
        animateFadeIn(this.mStatusPriceExpandedView);
        this.mPriceView.setVisibility(8);
        this.mStatusView.setVisibility(8);
      }
    }
  }
  
  public void onAnimationStart(Animation paramAnimation)
  {
    if (paramAnimation == this.mCollapseAnimation)
    {
      this.mStatusPriceExpandedView.setVisibility(8);
      this.mSubscriptionInstrumentDeclinedView.setVisibility(8);
      this.mActionButton.setVisibility(8);
      if (this.mHasStatusPrice)
      {
        animateFadeIn(this.mPriceView);
        animateFadeIn(this.mStatusView);
      }
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mPriceView = ((TextView)findViewById(2131755679));
    this.mStatusView = ((TextView)findViewById(2131755611));
    this.mStatusPriceExpandedView = ((TextView)findViewById(2131756149));
    this.mSubscriptionStatusView = ((TextView)findViewById(2131756146));
    this.mTitleBylineImageView = ((FifeImageView)findViewById(2131756148));
    this.mSubscriptionInstrumentDeclinedView = ((TextView)findViewById(2131756147));
    this.mActionButton = ((PlayActionButton)findViewById(2131755213));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.SubscriptionRowView
 * JD-Core Version:    0.7.0.1
 */