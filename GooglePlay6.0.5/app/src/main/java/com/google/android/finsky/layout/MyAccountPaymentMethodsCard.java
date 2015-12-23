package com.google.android.finsky.layout;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.billing.lightpurchase.billingprofile.BillingProfileSidecar;
import com.google.android.finsky.billing.lightpurchase.billingprofile.FopActionEntry;
import com.google.android.finsky.billing.switchfamilyinstrument.SwitchFamilyInstrumentActivity;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.protos.BillingProfileProtos.BillingProfile;
import com.google.android.finsky.protos.Instrument;
import com.google.android.finsky.utils.DotNotificationUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.PlayAnimationUtils;
import com.google.android.finsky.utils.PlayAnimationUtils.AnimationListenerAdapter;
import com.google.android.play.layout.CardLinearLayout;
import java.util.ArrayList;
import java.util.List;

public class MyAccountPaymentMethodsCard
  extends CardLinearLayout
  implements View.OnClickListener, PlayStoreUiElementNode
{
  public String mAccountName;
  public List<FopActionEntry> mActionEntries;
  public ViewGroup mActionsContainer;
  private View mActionsSwitcherView;
  private View mActionsView;
  public BillingProfileProtos.BillingProfile mBillingProfile;
  public BillingProfileSidecar mBillingProfileSidecar;
  private View mDotNotificationDescriptionView;
  private View mDotNotificationView;
  private View mEditFooterView;
  public View mErrorIndicatorView;
  public FinskyEventLog mEventLog;
  public View mFamilyInstrumentSwitcher;
  public Instrument[] mInstruments;
  public ViewGroup mInstrumentsContainer;
  public View mInstrumentsSwitcherView;
  private View mInstrumentsView;
  public PlayStoreUiElementNode mParentNode;
  public RecyclerView mParentRecyclerView;
  public View mProfileView;
  public View mProgressIndicatorView;
  private TextView mTitleView;
  private final PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(2620);
  public Bundle mViewState;
  
  public MyAccountPaymentMethodsCard(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MyAccountPaymentMethodsCard(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private Instrument[] getFamilyEligibleInstruments()
  {
    ArrayList localArrayList = new ArrayList();
    for (Instrument localInstrument : this.mInstruments) {
      if (localInstrument.eligibleForFamilyFop) {
        localArrayList.add(localInstrument);
      }
    }
    return (Instrument[])localArrayList.toArray(new Instrument[localArrayList.size()]);
  }
  
  private void showAccountCompletionNotificationViews()
  {
    this.mDotNotificationView.setVisibility(0);
    this.mDotNotificationDescriptionView.setVisibility(0);
    this.mTitleView.setText(2131362348);
  }
  
  private void switchToView(final View paramView1, View paramView2, boolean paramBoolean)
  {
    paramView2.setVisibility(0);
    if (paramBoolean)
    {
      paramView2.startAnimation(PlayAnimationUtils.getFadeInAnimation(getContext(), 250L, null));
      paramView1.startAnimation(PlayAnimationUtils.getFadeOutAnimation(getContext(), 250L, new PlayAnimationUtils.AnimationListenerAdapter()
      {
        public final void onAnimationEnd(Animation paramAnonymousAnimation)
        {
          paramView1.setVisibility(8);
        }
      }));
      return;
    }
    paramView1.setVisibility(8);
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyEventLog.childImpression(this, paramPlayStoreUiElementNode);
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public final void logActionImpressions()
  {
    for (int i = 0; i < this.mActionEntries.size(); i++)
    {
      FopActionEntry localFopActionEntry = (FopActionEntry)this.mActionEntries.get(i);
      this.mEventLog.logPathImpression(0L, localFopActionEntry.playStoreUiElementType, localFopActionEntry.serverLogsCookie, this);
    }
  }
  
  public void onClick(View paramView)
  {
    if (paramView == this.mInstrumentsSwitcherView)
    {
      this.mEventLog.logClickEvent(2625, null, this);
      switchToInstrumentsView(true);
    }
    do
    {
      return;
      if (paramView == this.mActionsSwitcherView)
      {
        this.mEventLog.logClickEvent(2624, null, this);
        switchToActionsView(true);
        return;
      }
      if (paramView == this.mEditFooterView)
      {
        this.mEventLog.logClickEvent(2623, null, this);
        this.mBillingProfileSidecar.startActivity(new Intent("android.intent.action.VIEW", BillingProfileSidecar.EDIT_FOOTER_URI));
        return;
      }
    } while (paramView != this.mFamilyInstrumentSwitcher);
    this.mEventLog.logClickEvent(2629, null, this);
    BillingProfileSidecar localBillingProfileSidecar = this.mBillingProfileSidecar;
    Instrument[] arrayOfInstrument = getFamilyEligibleInstruments();
    if (localBillingProfileSidecar.mSetupWizardParams == null)
    {
      localBillingProfileSidecar.startActivityForResult(SwitchFamilyInstrumentActivity.createIntent(localBillingProfileSidecar.mAccount.name, arrayOfInstrument), 11);
      return;
    }
    FinskyLog.wtf("Switching family instrument is not supported for Setup Wizard.", new Object[0]);
  }
  
  public void onFinishInflate()
  {
    super.onFinishInflate();
    this.mTitleView = ((TextView)findViewById(2131755173));
    this.mProfileView = findViewById(2131755284);
    this.mInstrumentsView = findViewById(2131755726);
    this.mActionsView = findViewById(2131755288);
    this.mInstrumentsSwitcherView = findViewById(2131755808);
    this.mInstrumentsSwitcherView.setOnClickListener(this);
    this.mActionsSwitcherView = findViewById(2131755810);
    this.mActionsSwitcherView.setOnClickListener(this);
    this.mFamilyInstrumentSwitcher = findViewById(2131755812);
    this.mProgressIndicatorView = findViewById(2131755289);
    this.mErrorIndicatorView = findViewById(2131755483);
    this.mEditFooterView = findViewById(2131755811);
    this.mEditFooterView.setOnClickListener(this);
    this.mDotNotificationView = findViewById(2131755724);
    this.mDotNotificationDescriptionView = findViewById(2131755725);
    this.mInstrumentsContainer = ((ViewGroup)findViewById(2131755809));
    this.mActionsContainer = ((ViewGroup)findViewById(2131755807));
  }
  
  public final void switchToActionsView(boolean paramBoolean)
  {
    this.mEventLog.logPathImpression$7d139cbf(2622, this);
    RecyclerView localRecyclerView;
    if (this.mViewState.getBoolean("PaymentMethodsCard.ShowingAccountCompletionNotification"))
    {
      showAccountCompletionNotificationViews();
      switchToView(this.mInstrumentsView, this.mActionsView, paramBoolean);
      if ((paramBoolean) && (this.mParentRecyclerView != null))
      {
        localRecyclerView = this.mParentRecyclerView;
        if (!localRecyclerView.mLayoutFrozen)
        {
          if (localRecyclerView.mLayout != null) {
            break label205;
          }
          Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        }
      }
    }
    for (;;)
    {
      if (this.mActionEntries != null) {
        logActionImpressions();
      }
      this.mViewState.putBoolean("PaymentMethodsCard.AddingPaymentMethod", true);
      return;
      if (DotNotificationUtils.shouldShowPaymentMethodsCardNotification())
      {
        showAccountCompletionNotificationViews();
        PreferenceFile.SharedPreference localSharedPreference = FinskyPreferences.accountCompletionPaymentsCardShowCount.get(FinskyApp.get().getCurrentAccountName());
        if (((Integer)localSharedPreference.get()).intValue() == 0) {
          localSharedPreference.put(Integer.valueOf(1));
        }
        GenericUiElementNode localGenericUiElementNode = new GenericUiElementNode(2622, null, null, this);
        this.mEventLog.logPathImpression$7d139cbf(299, localGenericUiElementNode);
        this.mViewState.putBoolean("PaymentMethodsCard.ShowingAccountCompletionNotification", true);
        break;
      }
      this.mTitleView.setText(2131361824);
      break;
      label205:
      localRecyclerView.mLayout.smoothScrollToPosition$7d69765f(localRecyclerView, 0);
    }
  }
  
  public final void switchToInstrumentsView(boolean paramBoolean)
  {
    this.mEventLog.logPathImpression$7d139cbf(2621, this);
    this.mTitleView.setText(2131362492);
    this.mDotNotificationView.setVisibility(8);
    this.mDotNotificationDescriptionView.setVisibility(8);
    switchToView(this.mActionsView, this.mInstrumentsView, paramBoolean);
    for (Instrument localInstrument : this.mInstruments) {
      this.mEventLog.logPathImpression(0L, 802, localInstrument.serverLogsCookie, this);
    }
    this.mViewState.putBoolean("PaymentMethodsCard.AddingPaymentMethod", false);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.MyAccountPaymentMethodsCard
 * JD-Core Version:    0.7.0.1
 */