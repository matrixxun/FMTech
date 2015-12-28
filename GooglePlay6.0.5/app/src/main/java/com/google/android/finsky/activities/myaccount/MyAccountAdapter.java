package com.google.android.finsky.activities.myaccount;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObservable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.billing.lightpurchase.billingprofile.BillingProfileSidecar;
import com.google.android.finsky.billing.lightpurchase.billingprofile.FopActionEntry;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.fragments.SidecarFragment.Listener;
import com.google.android.finsky.layout.MyAccountCard;
import com.google.android.finsky.layout.MyAccountFamilyManagementCard;
import com.google.android.finsky.layout.MyAccountFamilyManagementCard.FamilyManagementCardCallback;
import com.google.android.finsky.layout.MyAccountNewsstandCard;
import com.google.android.finsky.layout.MyAccountOrderHistoryCard;
import com.google.android.finsky.layout.MyAccountPaymentMethodsCard;
import com.google.android.finsky.layout.MyAccountPaymentMethodsCard.1;
import com.google.android.finsky.layout.MyAccountPaymentMethodsCard.3;
import com.google.android.finsky.layout.MyAccountSubscriptionCard;
import com.google.android.finsky.layout.OrderHistoryRowView;
import com.google.android.finsky.layout.OrderHistoryRowView.OnRefundActionListener;
import com.google.android.finsky.layout.SubscriptionView;
import com.google.android.finsky.layout.SubscriptionView.CancelListener;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;
import com.google.android.finsky.layout.play.PlayRecyclerView.ViewHolder;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.BillingProfileProtos.BillingProfile;
import com.google.android.finsky.protos.BillingProfileProtos.BillingProfileOption;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DisabledInfo;
import com.google.android.finsky.protos.FamilyInfo;
import com.google.android.finsky.protos.FamilyMember;
import com.google.android.finsky.protos.Instrument;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FamilyUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.Lists;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.PlayActionButton;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class MyAccountAdapter
  extends RecyclerView.Adapter
{
  private BillingProfileSidecar mBillingProfileSidecar;
  private final BitmapLoader mBitmapLoader;
  private final SubscriptionView.CancelListener mCancelListener;
  List<Integer> mCardsShown;
  private final Context mContext;
  private final DfeApi mDfeApi;
  private final MyAccountFamilyManagementCard.FamilyManagementCardCallback mFamilyManagementCardCallback;
  int mLastBillingProfileStateInstance = -1;
  private final LayoutInflater mLayoutInflater;
  private final int mLeadingExtraSpacerHeight;
  private final int mLeadingSpacerHeight;
  private MyAccountLibrarySubscriptionEntries mMyAccountLibrarySubscriptionEntries;
  private MyAccountModel mMyAccountModel;
  private final NavigationManager mNavigationManager;
  int mNewsstandPositionInCardsShown;
  private final OrderHistoryRowView.OnRefundActionListener mOnRefundActionListener;
  private final PlayStoreUiElementNode mParentNode;
  private final RecyclerView mParentRecyclerView;
  private final Bundle mViewState;
  
  public MyAccountAdapter(Context paramContext, DfeApi paramDfeApi, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, OrderHistoryRowView.OnRefundActionListener paramOnRefundActionListener, SubscriptionView.CancelListener paramCancelListener, MyAccountFamilyManagementCard.FamilyManagementCardCallback paramFamilyManagementCardCallback, BillingProfileSidecar paramBillingProfileSidecar, RecyclerView paramRecyclerView, PlayStoreUiElementNode paramPlayStoreUiElementNode, Bundle paramBundle)
  {
    this.mContext = paramContext;
    this.mDfeApi = paramDfeApi;
    this.mBitmapLoader = paramBitmapLoader;
    this.mNavigationManager = paramNavigationManager;
    this.mOnRefundActionListener = paramOnRefundActionListener;
    this.mCancelListener = paramCancelListener;
    this.mFamilyManagementCardCallback = paramFamilyManagementCardCallback;
    this.mBillingProfileSidecar = paramBillingProfileSidecar;
    this.mParentRecyclerView = paramRecyclerView;
    this.mParentNode = paramPlayStoreUiElementNode;
    this.mLeadingSpacerHeight = FinskyHeaderListLayout.getMinimumHeaderHeight$3047fd86(paramContext, 2);
    Resources localResources = paramContext.getResources();
    this.mLeadingExtraSpacerHeight = (localResources.getDimensionPixelSize(2131492937) + localResources.getDimensionPixelSize(2131493068));
    this.mLayoutInflater = LayoutInflater.from(this.mContext);
    this.mViewState = paramBundle;
  }
  
  public final int getItemCount()
  {
    int i = 3;
    if (this.mCardsShown != null) {
      i = 3 + this.mCardsShown.size();
    }
    return i;
  }
  
  public final int getItemViewType(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      int i = paramInt - 3;
      if ((this.mCardsShown != null) && (this.mCardsShown.size() > i)) {
        return ((Integer)this.mCardsShown.get(i)).intValue();
      }
      break;
    case 0: 
      return 0;
    case 1: 
      return 1;
    case 2: 
      return 2;
    }
    throw new IndexOutOfBoundsException("Invalid position for getItemViewType" + paramInt);
  }
  
  public final void notifyFamilyCardChanged()
  {
    int i = this.mCardsShown.indexOf(Integer.valueOf(7));
    if (i < 0) {
      return;
    }
    if (FamilyUtils.shouldShowFamilyCard(this.mContext, this.mDfeApi.getAccountName()))
    {
      notifyItemChanged(i + 3);
      return;
    }
    this.mCardsShown.remove(i);
    notifyItemRemoved(i + 3);
  }
  
  public final void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, final int paramInt)
  {
    int i = paramViewHolder.mItemViewType;
    View localView1 = paramViewHolder.itemView;
    MyAccountPaymentMethodsCard localMyAccountPaymentMethodsCard;
    byte[] arrayOfByte1;
    ArrayList localArrayList;
    int i1;
    label380:
    int k;
    label519:
    int m;
    label711:
    Instrument localInstrument;
    switch (i)
    {
    default: 
      throw new IllegalStateException("Unknown type for getView " + i);
    case 0: 
      localView1.getLayoutParams().height = this.mLeadingSpacerHeight;
      localView1.setId(2131755059);
      return;
    case 1: 
      localView1.getLayoutParams().height = this.mLeadingExtraSpacerHeight;
      return;
    case 2: 
      localMyAccountPaymentMethodsCard = (MyAccountPaymentMethodsCard)localView1;
      Account localAccount = this.mDfeApi.getAccount();
      BillingProfileSidecar localBillingProfileSidecar = this.mBillingProfileSidecar;
      RecyclerView localRecyclerView = this.mParentRecyclerView;
      PlayStoreUiElementNode localPlayStoreUiElementNode2 = this.mParentNode;
      Bundle localBundle = this.mViewState;
      localMyAccountPaymentMethodsCard.mBillingProfileSidecar = localBillingProfileSidecar;
      localMyAccountPaymentMethodsCard.mBillingProfile = localMyAccountPaymentMethodsCard.mBillingProfileSidecar.getBillingProfile();
      localMyAccountPaymentMethodsCard.mEventLog = FinskyApp.get().getEventLogger(localAccount);
      localMyAccountPaymentMethodsCard.mParentRecyclerView = localRecyclerView;
      localMyAccountPaymentMethodsCard.mParentNode = localPlayStoreUiElementNode2;
      localMyAccountPaymentMethodsCard.mViewState = localBundle;
      localMyAccountPaymentMethodsCard.mAccountName = localAccount.name;
      switch (localMyAccountPaymentMethodsCard.mBillingProfileSidecar.mState)
      {
      case 2: 
      default: 
        localMyAccountPaymentMethodsCard.setVisibility(0);
        localMyAccountPaymentMethodsCard.mInstrumentsContainer.removeAllViews();
        localMyAccountPaymentMethodsCard.mActionsContainer.removeAllViews();
        Instrument[] arrayOfInstrument1 = localMyAccountPaymentMethodsCard.mBillingProfile.instrument;
        arrayOfByte1 = localMyAccountPaymentMethodsCard.mBillingProfile.paymentsIntegratorCommonToken;
        localMyAccountPaymentMethodsCard.mInstruments = arrayOfInstrument1;
        if (localMyAccountPaymentMethodsCard.mInstruments.length == 0)
        {
          localMyAccountPaymentMethodsCard.mInstrumentsSwitcherView.setVisibility(8);
          localMyAccountPaymentMethodsCard.switchToActionsView(false);
          localArrayList = Lists.newArrayList(localMyAccountPaymentMethodsCard.mBillingProfile.billingProfileOption.length);
          FragmentActivity localFragmentActivity = localMyAccountPaymentMethodsCard.mBillingProfileSidecar.getActivity();
          if (localFragmentActivity.getPackageManager().checkPermission("android.permission.SEND_SMS", localFragmentActivity.getPackageName()) != 0) {
            break label1142;
          }
          i1 = 1;
          for (BillingProfileProtos.BillingProfileOption localBillingProfileOption : localMyAccountPaymentMethodsCard.mBillingProfile.billingProfileOption) {
            if ((localBillingProfileOption.type != 2) || (i1 != 0))
            {
              FopActionEntry localFopActionEntry2 = localMyAccountPaymentMethodsCard.mBillingProfileSidecar.billingProfileOptionToActionEntry(localBillingProfileOption, localMyAccountPaymentMethodsCard.mBillingProfile.paymentsIntegratorCommonToken, localMyAccountPaymentMethodsCard);
              if (localFopActionEntry2 != null) {
                localArrayList.add(localFopActionEntry2);
              }
            }
          }
        }
        break;
      case 0: 
      case 1: 
        localMyAccountPaymentMethodsCard.mProfileView.setVisibility(8);
        localMyAccountPaymentMethodsCard.mProgressIndicatorView.setVisibility(0);
        localMyAccountPaymentMethodsCard.mErrorIndicatorView.setVisibility(8);
        localMyAccountPaymentMethodsCard.mViewState.clear();
        localMyAccountPaymentMethodsCard.mEventLog.logPathImpression$7d139cbf(213, localMyAccountPaymentMethodsCard);
        MyAccountCard.setMarginsForCardView(localMyAccountPaymentMethodsCard);
        return;
      case 3: 
        if (localMyAccountPaymentMethodsCard.mBillingProfileSidecar.mSubstate == 4) {}
        for (String str1 = ErrorStrings.get(localMyAccountPaymentMethodsCard.getContext(), localBillingProfileSidecar.mVolleyError);; str1 = localBillingProfileSidecar.mErrorMessageHtml)
        {
          TextView localTextView1 = (TextView)localMyAccountPaymentMethodsCard.mErrorIndicatorView.findViewById(2131755274);
          localTextView1.setMovementMethod(LinkMovementMethod.getInstance());
          localTextView1.setText(Html.fromHtml(str1));
          localMyAccountPaymentMethodsCard.mErrorIndicatorView.findViewById(2131755482).setOnClickListener(new MyAccountPaymentMethodsCard.1(localMyAccountPaymentMethodsCard));
          localMyAccountPaymentMethodsCard.mProgressIndicatorView.setVisibility(8);
          localMyAccountPaymentMethodsCard.mProfileView.setVisibility(8);
          localMyAccountPaymentMethodsCard.mErrorIndicatorView.setVisibility(0);
          localMyAccountPaymentMethodsCard.mEventLog.logPathImpression$7d139cbf(2627, localMyAccountPaymentMethodsCard);
          break;
        }
      }
      localMyAccountPaymentMethodsCard.mInstrumentsSwitcherView.setVisibility(0);
      if (localMyAccountPaymentMethodsCard.mViewState.getBoolean("PaymentMethodsCard.AddingPaymentMethod"))
      {
        localMyAccountPaymentMethodsCard.switchToActionsView(false);
        Instrument[] arrayOfInstrument2 = localMyAccountPaymentMethodsCard.mInstruments;
        int j = arrayOfInstrument2.length;
        k = 0;
        m = 0;
        if (k >= j) {
          break label1046;
        }
        localInstrument = arrayOfInstrument2[k];
        if (!localInstrument.eligibleForFamilyFop) {
          break label1841;
        }
      }
      break;
    }
    label810:
    label1841:
    for (int i5 = m + 1;; i5 = m)
    {
      View localView3 = LayoutInflater.from(localMyAccountPaymentMethodsCard.mInstrumentsContainer.getContext()).inflate(2130968899, localMyAccountPaymentMethodsCard.mInstrumentsContainer, false);
      FifeImageView localFifeImageView2 = (FifeImageView)localView3.findViewById(2131755290);
      Common.Image localImage2 = localInstrument.iconImage;
      if (localImage2 != null)
      {
        localFifeImageView2.setImage(localImage2.imageUrl, localImage2.supportsFifeUrlOptions, FinskyApp.get().mBitmapLoader);
        ((TextView)localView3.findViewById(2131755173)).setText(localInstrument.displayTitle);
        TextView localTextView2 = (TextView)localView3.findViewById(2131755291);
        if (localInstrument.hasDisplaySubtitle)
        {
          localTextView2.setText(localInstrument.displaySubtitle);
          localTextView2.setVisibility(0);
        }
        if (localInstrument.disabledInfo.length <= 0) {
          break label1040;
        }
      }
      for (String str2 = localInstrument.disabledInfo[0].disabledMessageHtml;; str2 = null)
      {
        if (!TextUtils.isEmpty(str2))
        {
          TextView localTextView4 = (TextView)localView3.findViewById(2131755292);
          localTextView4.setText(str2);
          localTextView4.setVisibility(0);
        }
        byte[] arrayOfByte2 = localInstrument.paymentsIntegratorEditToken;
        if ((!TextUtils.isEmpty(localInstrument.editButtonLabel)) && (arrayOfByte2 != null) && (arrayOfByte2.length > 0))
        {
          TextView localTextView3 = (TextView)localView3.findViewById(2131755293);
          localTextView3.setText(localInstrument.editButtonLabel.toUpperCase());
          localView3.setOnClickListener(new MyAccountPaymentMethodsCard.3(localMyAccountPaymentMethodsCard, localInstrument, arrayOfByte2, arrayOfByte1));
          localTextView3.setVisibility(0);
        }
        localMyAccountPaymentMethodsCard.mInstrumentsContainer.addView(localView3);
        k++;
        m = i5;
        break label711;
        localMyAccountPaymentMethodsCard.switchToInstrumentsView(false);
        break;
        localFifeImageView2.setVisibility(4);
        break label810;
      }
      if (m <= 1) {
        break;
      }
      FamilyInfo localFamilyInfo2 = FamilyUtils.getCachedFamilyInfo(localMyAccountPaymentMethodsCard.mAccountName);
      if (localFamilyInfo2 != null)
      {
        FamilyMember localFamilyMember = FamilyUtils.findSelfInFamily(localFamilyInfo2.family);
        if ((localFamilyMember == null) || (localFamilyMember.role != 1)) {}
      }
      for (int n = 1; n != 0; n = 0)
      {
        localMyAccountPaymentMethodsCard.mFamilyInstrumentSwitcher.setVisibility(0);
        localMyAccountPaymentMethodsCard.mFamilyInstrumentSwitcher.setOnClickListener(localMyAccountPaymentMethodsCard);
        localMyAccountPaymentMethodsCard.mEventLog.logPathImpression(0L, 2629, null, localMyAccountPaymentMethodsCard);
        break;
      }
      label1142:
      i1 = 0;
      break label380;
      localMyAccountPaymentMethodsCard.mActionEntries = localArrayList;
      int i4 = 0;
      if (i4 < localMyAccountPaymentMethodsCard.mActionEntries.size())
      {
        FopActionEntry localFopActionEntry1 = (FopActionEntry)localMyAccountPaymentMethodsCard.mActionEntries.get(i4);
        View localView2 = LayoutInflater.from(localMyAccountPaymentMethodsCard.mActionsContainer.getContext()).inflate(2130968900, localMyAccountPaymentMethodsCard.mActionsContainer, false);
        FifeImageView localFifeImageView1 = (FifeImageView)localView2.findViewById(2131755290);
        Common.Image localImage1 = localFopActionEntry1.iconImage;
        if (localImage1 != null) {
          localFifeImageView1.setImage(localImage1.imageUrl, localImage1.supportsFifeUrlOptions, FinskyApp.get().mBitmapLoader);
        }
        for (;;)
        {
          ((TextView)localView2.findViewById(2131755173)).setText(localFopActionEntry1.displayTitle);
          localView2.setOnClickListener(localFopActionEntry1.action);
          localMyAccountPaymentMethodsCard.mActionsContainer.addView(localView2);
          i4++;
          break;
          localFifeImageView1.setVisibility(4);
        }
      }
      if (localMyAccountPaymentMethodsCard.mInstruments.length == 0) {
        localMyAccountPaymentMethodsCard.logActionImpressions();
      }
      localMyAccountPaymentMethodsCard.mProgressIndicatorView.setVisibility(8);
      if (localMyAccountPaymentMethodsCard.mErrorIndicatorView != null) {
        localMyAccountPaymentMethodsCard.mErrorIndicatorView.setVisibility(8);
      }
      localMyAccountPaymentMethodsCard.mProfileView.setVisibility(0);
      localMyAccountPaymentMethodsCard.mProfileView.requestFocus();
      localMyAccountPaymentMethodsCard.mEventLog.logPathImpression(0L, localMyAccountPaymentMethodsCard);
      break label519;
      ((MyAccountOrderHistoryCard)localView1).bind(this.mMyAccountModel, this.mDfeApi.getAccount(), FinskyApp.get().mToc, this.mBitmapLoader, this.mOnRefundActionListener, this.mNavigationManager, this.mParentNode);
      return;
      ((MyAccountSubscriptionCard)localView1).bind(this.mMyAccountModel, this.mDfeApi.getAccount(), FinskyApp.get().mToc, this.mBitmapLoader, this.mCancelListener, this.mMyAccountLibrarySubscriptionEntries, this.mNavigationManager, this.mParentNode);
      ((MyAccountCard)localView1).bind(this.mMyAccountModel, this.mDfeApi.getAccount(), FinskyApp.get().mToc, this.mBitmapLoader, this.mNavigationManager, this.mParentNode);
      return;
      MyAccountNewsstandCard localMyAccountNewsstandCard = (MyAccountNewsstandCard)localView1;
      View.OnClickListener local1 = new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          FinskyPreferences.dismissedMyAccountNewsstandMessage.put(Boolean.valueOf(true));
          MyAccountAdapter.this.mCardsShown.remove(MyAccountAdapter.this.mNewsstandPositionInCardsShown);
          MyAccountAdapter.this.notifyItemRemoved(paramInt);
        }
      };
      localMyAccountNewsstandCard.mTitleView.setText(2131362350);
      localMyAccountNewsstandCard.mDescriptionView.setText(Html.fromHtml(localMyAccountNewsstandCard.getResources().getString(2131362349)));
      localMyAccountNewsstandCard.mDescriptionView.setMovementMethod(LinkMovementMethod.getInstance());
      localMyAccountNewsstandCard.mDismissButton.configure(10, 2131362915, local1);
      MyAccountCard.setMarginsForCardView(localMyAccountNewsstandCard);
      return;
      MyAccountFamilyManagementCard localMyAccountFamilyManagementCard = (MyAccountFamilyManagementCard)localView1;
      FamilyInfo localFamilyInfo1 = FamilyUtils.getCachedFamilyInfo(this.mDfeApi.getAccountName());
      if (localFamilyInfo1 == null)
      {
        localMyAccountFamilyManagementCard.setVisibility(8);
        FinskyLog.wtf("Updating family view with no family info.", new Object[0]);
        return;
      }
      MyAccountFamilyManagementCard.FamilyManagementCardCallback localFamilyManagementCardCallback = this.mFamilyManagementCardCallback;
      PlayStoreUiElementNode localPlayStoreUiElementNode1 = this.mParentNode;
      MyAccountCard.setMarginsForCardView(localMyAccountFamilyManagementCard);
      localMyAccountFamilyManagementCard.mParentNode = localPlayStoreUiElementNode1;
      localMyAccountFamilyManagementCard.mCallback = localFamilyManagementCardCallback;
      localMyAccountFamilyManagementCard.mParentNode.childImpression(localMyAccountFamilyManagementCard);
      if (localMyAccountFamilyManagementCard.mCallback.isLoading())
      {
        localMyAccountFamilyManagementCard.mCardContent.setVisibility(8);
        localMyAccountFamilyManagementCard.mLoadingIndicator.setVisibility(0);
        return;
      }
      localMyAccountFamilyManagementCard.mCardContent.setVisibility(0);
      localMyAccountFamilyManagementCard.mLoadingIndicator.setVisibility(8);
      switch (localFamilyInfo1.familyMembershipStatus)
      {
      default: 
        return;
      case 1: 
        localMyAccountFamilyManagementCard.updateViewWithUserInFamily(localFamilyInfo1.family);
        return;
      case 2: 
        localMyAccountFamilyManagementCard.updateViewWithUserInFamily(localFamilyInfo1.family);
        return;
      }
      localMyAccountFamilyManagementCard.mFamilyButtonMode = 0;
      localMyAccountFamilyManagementCard.mManageMyFamilyTextView.setText(2131362068);
      localMyAccountFamilyManagementCard.mShareSettingsView.setVisibility(8);
      localMyAccountFamilyManagementCard.mExtraLineSeparator.setVisibility(0);
      localMyAccountFamilyManagementCard.mLearnMoreView.setVisibility(0);
      return;
    }
  }
  
  public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    View localView;
    switch (paramInt)
    {
    default: 
      throw new IllegalStateException("Unknown type for getView " + paramInt);
    case 0: 
    case 1: 
      localView = this.mLayoutInflater.inflate(2130968994, paramViewGroup, false);
    }
    for (;;)
    {
      return new PlayRecyclerView.ViewHolder(localView);
      localView = this.mLayoutInflater.inflate(2130968836, paramViewGroup, false);
      continue;
      localView = this.mLayoutInflater.inflate(2130968835, paramViewGroup, false);
      continue;
      localView = this.mLayoutInflater.inflate(2130968839, paramViewGroup, false);
      continue;
      localView = this.mLayoutInflater.inflate(2130968837, paramViewGroup, false);
      continue;
      localView = this.mLayoutInflater.inflate(2130968832, paramViewGroup, false);
      continue;
      localView = this.mLayoutInflater.inflate(2130968833, paramViewGroup, false);
    }
  }
  
  public final void onDestroy()
  {
    if (this.mMyAccountModel != null)
    {
      Iterator localIterator = this.mMyAccountModel.mMyAccountCardDataMap.values().iterator();
      while (localIterator.hasNext()) {
        ((MyAccountModel.MyAccountCardData)localIterator.next()).removeDataChangedListener();
      }
    }
    this.mMyAccountModel = null;
  }
  
  public final void setModel(MyAccountModel paramMyAccountModel)
  {
    if (this.mMyAccountModel != null) {
      onDestroy();
    }
    this.mMyAccountModel = paramMyAccountModel;
    this.mCardsShown = new ArrayList();
    if (FamilyUtils.shouldShowFamilyCard(this.mContext, this.mDfeApi.getAccountName())) {
      this.mCardsShown.add(Integer.valueOf(7));
    }
    if (this.mMyAccountModel.isCardTypeEnabled(1))
    {
      this.mCardsShown.add(Integer.valueOf(4));
      this.mMyAccountLibrarySubscriptionEntries = new MyAccountLibrarySubscriptionEntries(FinskyApp.get().mLibraries.getAccountLibrary(this.mDfeApi.getAccount()));
      if ((this.mMyAccountLibrarySubscriptionEntries.mHasNewsstandSubscriptions) && (!((Boolean)FinskyPreferences.dismissedMyAccountNewsstandMessage.get()).booleanValue()) && (((Boolean)G.showMyAccountNewsstandsMessage.get()).booleanValue()))
      {
        this.mNewsstandPositionInCardsShown = this.mCardsShown.size();
        this.mCardsShown.add(Integer.valueOf(6));
      }
    }
    if (this.mMyAccountModel.isCardTypeEnabled(2)) {
      this.mCardsShown.add(Integer.valueOf(5));
    }
    if (this.mMyAccountModel.isCardTypeEnabled(0)) {
      this.mCardsShown.add(Integer.valueOf(3));
    }
    if (this.mCardsShown.size() > 0)
    {
      int i = this.mCardsShown.size();
      this.mObservable.notifyItemRangeInserted(3, i);
    }
  }
}



/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar

 * Qualified Name:     com.google.android.finsky.activities.myaccount.MyAccountAdapter

 * JD-Core Version:    0.7.0.1

 */