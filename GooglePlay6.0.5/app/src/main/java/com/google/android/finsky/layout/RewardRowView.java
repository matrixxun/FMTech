package com.google.android.finsky.layout;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Annotations;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.MyRewardDetails;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.PlayActionButton;

public class RewardRowView
  extends MyAccountRowView
{
  private boolean mCanRedeem;
  private TextView mExpirationDescription;
  private boolean mHasRewardDescription;
  private PlayActionButton mRedeemButton;
  private TextView mRewardDescription;
  
  public RewardRowView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public RewardRowView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 2662);
  }
  
  public final void bind(final Document paramDocument, BitmapLoader paramBitmapLoader, final NavigationManager paramNavigationManager, boolean paramBoolean, PlayStoreUiElementNode paramPlayStoreUiElementNode, int paramInt, final DfeToc paramDfeToc)
  {
    super.bind(paramDocument, paramDocument.mDocument.title, paramBitmapLoader, paramBoolean, paramPlayStoreUiElementNode, paramInt, paramNavigationManager);
    final MyRewardDetails localMyRewardDetails;
    label67:
    int j;
    label123:
    int i;
    if (paramDocument.hasMyRewardDetails())
    {
      localMyRewardDetails = paramDocument.mDocument.annotations.myRewardDetails;
      if (!localMyRewardDetails.hasExpirationDescription) {
        break label206;
      }
      this.mExpirationDescription.setText(localMyRewardDetails.expirationDescription);
      this.mExpirationDescription.setVisibility(0);
      this.mHasRewardDescription = paramDocument.mDocument.hasPromotionalDescription;
      if (!this.mHasRewardDescription) {
        break label224;
      }
      this.mRewardDescription.setText(paramDocument.mDocument.promotionalDescription);
      this.mRewardDescription.setMovementMethod(LinkMovementMethod.getInstance());
      TextView localTextView = this.mRewardDescription;
      if (!paramBoolean) {
        break label217;
      }
      j = 0;
      localTextView.setVisibility(j);
      label130:
      this.mCanRedeem = localMyRewardDetails.hasButtonLabel;
      if (!this.mCanRedeem) {
        break label243;
      }
      this.mRedeemButton.configure(10, localMyRewardDetails.buttonLabel, new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          FinskyApp.get().getEventLogger().logClickEvent(RewardRowView.this);
          paramNavigationManager.resolveLink(localMyRewardDetails.linkAction, null, paramDfeToc, paramAnonymousView.getContext().getPackageManager(), paramDocument.mDocument.docid);
        }
      });
      PlayActionButton localPlayActionButton = this.mRedeemButton;
      if (!paramBoolean) {
        break label236;
      }
      i = 0;
      label188:
      localPlayActionButton.setVisibility(i);
    }
    for (;;)
    {
      setUpToggleAnimations();
      return;
      localMyRewardDetails = null;
      break;
      label206:
      this.mExpirationDescription.setVisibility(4);
      break label67;
      label217:
      j = 8;
      break label123;
      label224:
      this.mRewardDescription.setVisibility(8);
      break label130;
      label236:
      i = 8;
      break label188;
      label243:
      this.mRedeemButton.setVisibility(8);
    }
  }
  
  public void onAnimationEnd(Animation paramAnimation)
  {
    if (paramAnimation == this.mExpandAnimation)
    {
      if (this.mHasRewardDescription) {
        animateFadeIn(this.mRewardDescription);
      }
      if (this.mCanRedeem) {
        animateFadeIn(this.mRedeemButton);
      }
    }
  }
  
  public void onAnimationStart(Animation paramAnimation)
  {
    if (paramAnimation == this.mCollapseAnimation)
    {
      this.mRewardDescription.setVisibility(8);
      this.mRedeemButton.setVisibility(8);
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mExpirationDescription = ((TextView)findViewById(2131756078));
    this.mRewardDescription = ((TextView)findViewById(2131756079));
    this.mRedeemButton = ((PlayActionButton)findViewById(2131756080));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.RewardRowView
 * JD-Core Version:    0.7.0.1
 */