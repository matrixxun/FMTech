package com.google.android.finsky.activities.myaccount;

import android.accounts.Account;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.adapters.PaginatedRecyclerViewAdapter;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.PaginatedList;
import com.google.android.finsky.layout.OrderHistoryRowView;
import com.google.android.finsky.layout.OrderHistoryRowView.OnRefundActionListener;
import com.google.android.finsky.layout.RewardRowView;
import com.google.android.finsky.layout.SubscriptionRowView;
import com.google.android.finsky.layout.SubscriptionView.CancelListener;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;
import com.google.android.finsky.layout.play.PlayRecyclerView.ViewHolder;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.play.image.BitmapLoader;

public final class MyAccountListPageAdapter
  extends PaginatedRecyclerViewAdapter
{
  private final BitmapLoader mBitmapLoader;
  private final SubscriptionView.CancelListener mCancelListener;
  DfeList mDfeList;
  private final DfeToc mDfeToc;
  private final int mLeadingExtraSpacerHeight;
  private final int mLeadingSpacerHeight;
  private final MyAccountLibrarySubscriptionEntries mMyAccountLibrarySubscriptionEntries;
  private final OrderHistoryRowView.OnRefundActionListener mOnRefundActionListener;
  private final PlayStoreUiElementNode mParentNode;
  final SelectionListener mSelectionListener;
  
  public MyAccountListPageAdapter(Account paramAccount, Context paramContext, DfeList paramDfeList, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, OrderHistoryRowView.OnRefundActionListener paramOnRefundActionListener, SubscriptionView.CancelListener paramCancelListener, PlayStoreUiElementNode paramPlayStoreUiElementNode, DfeToc paramDfeToc)
  {
    super(paramContext, paramNavigationManager, paramDfeList.inErrorState(), paramDfeList.mMoreAvailable);
    this.mDfeToc = paramDfeToc;
    this.mDfeList = paramDfeList;
    this.mDfeList.addDataChangedListener(this);
    this.mLeadingSpacerHeight = FinskyHeaderListLayout.getMinimumHeaderHeight$3047fd86(paramContext, 2);
    Resources localResources = paramContext.getResources();
    this.mLeadingExtraSpacerHeight = (localResources.getDimensionPixelSize(2131492937) + localResources.getDimensionPixelSize(2131493068));
    this.mMyAccountLibrarySubscriptionEntries = new MyAccountLibrarySubscriptionEntries(FinskyApp.get().mLibraries.getAccountLibrary(paramAccount));
    this.mSelectionListener = new SelectionListener(FinskyApp.get().getEventLogger(paramAccount));
    this.mBitmapLoader = paramBitmapLoader;
    this.mCancelListener = paramCancelListener;
    this.mOnRefundActionListener = paramOnRefundActionListener;
    this.mParentNode = paramPlayStoreUiElementNode;
  }
  
  private static int convertRowPositionToDfeListPosition(int paramInt)
  {
    if (paramInt <= 1) {
      return -1;
    }
    return paramInt - 2;
  }
  
  private int getCollapsedBackgroundResourceId(int paramInt)
  {
    int i = convertRowPositionToDfeListPosition(paramInt);
    int j;
    if (i == 0)
    {
      j = 1;
      if (i != -1 + this.mDfeList.getCount()) {
        break label44;
      }
    }
    label44:
    for (int k = 1;; k = 0)
    {
      if ((j == 0) || (k == 0)) {
        break label50;
      }
      return 2130837910;
      j = 0;
      break;
    }
    label50:
    if (j != 0) {
      return 2130837908;
    }
    if (k != 0) {
      return 2130837909;
    }
    return 2131689607;
  }
  
  private boolean isSelected(int paramInt)
  {
    return paramInt == this.mSelectionListener.mSelectedPosition;
  }
  
  public final int getItemCount()
  {
    int i = this.mDfeList.getCount();
    int j = i + 2;
    if ((i == 0) && (!this.mDfeList.mMoreAvailable)) {
      j++;
    }
    if (getFooterMode() != 0) {
      j++;
    }
    return j;
  }
  
  public final int getItemViewType(int paramInt)
  {
    int i = 1;
    if (paramInt == 0) {
      i = 0;
    }
    while (paramInt == i) {
      return i;
    }
    if ((this.mDfeList.getCount() == 0) && (!this.mDfeList.mMoreAvailable)) {
      return 2;
    }
    if (paramInt == -1 + getItemCount())
    {
      if (getFooterMode() == i) {
        return 3;
      }
      if (getFooterMode() == 2) {
        return 4;
      }
    }
    int j = convertRowPositionToDfeListPosition(paramInt);
    if (this.mDfeList.hasItem(j))
    {
      Document localDocument = (Document)this.mDfeList.getItem(j);
      if (localDocument.hasMyRewardDetails()) {
        return 6;
      }
      if (localDocument.hasMySubscriptionDetails()) {
        return 7;
      }
      return 5;
    }
    return -1;
  }
  
  public final String getLastRequestError()
  {
    return ErrorStrings.get(this.mContext, this.mDfeList.getVolleyError());
  }
  
  public final boolean isMoreDataAvailable()
  {
    return this.mDfeList.mMoreAvailable;
  }
  
  public final void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    View localView = paramViewHolder.itemView;
    int i = paramViewHolder.mItemViewType;
    switch (i)
    {
    default: 
      throw new IllegalStateException("Unknown type for onBindView " + i);
    case 0: 
      localView.getLayoutParams().height = this.mLeadingSpacerHeight;
      localView.setId(2131755059);
      return;
    case 1: 
      localView.getLayoutParams().height = this.mLeadingExtraSpacerHeight;
      return;
    case 2: 
      ((TextView)localView).setText(2131362381);
      return;
    case 5: 
      OrderHistoryRowView localOrderHistoryRowView = (OrderHistoryRowView)localView;
      int m = convertRowPositionToDfeListPosition(paramInt);
      this.mSelectionListener.setAsClickListenerOf(localOrderHistoryRowView, paramInt);
      if (!this.mSelectionListener.hasSelection()) {
        this.mSelectionListener.setDefaultRowView(localOrderHistoryRowView, paramInt);
      }
      localOrderHistoryRowView.bind((Document)this.mDfeList.getItem(m), this.mBitmapLoader, this.mNavigationManager, isSelected(paramInt), this.mParentNode, getCollapsedBackgroundResourceId(paramInt), this.mOnRefundActionListener);
      return;
    case 6: 
      RewardRowView localRewardRowView = (RewardRowView)localView;
      int k = convertRowPositionToDfeListPosition(paramInt);
      this.mSelectionListener.setAsClickListenerOf(localRewardRowView, paramInt);
      if (!this.mSelectionListener.hasSelection()) {
        this.mSelectionListener.setDefaultRowView(localRewardRowView, paramInt);
      }
      localRewardRowView.bind((Document)this.mDfeList.getItem(k), this.mBitmapLoader, this.mNavigationManager, isSelected(paramInt), this.mParentNode, getCollapsedBackgroundResourceId(paramInt), this.mDfeToc);
      return;
    case 7: 
      SubscriptionRowView localSubscriptionRowView = (SubscriptionRowView)localView;
      int j = convertRowPositionToDfeListPosition(paramInt);
      this.mSelectionListener.setAsClickListenerOf(localSubscriptionRowView, paramInt);
      if (!this.mSelectionListener.hasSelection()) {
        this.mSelectionListener.setDefaultRowView(localSubscriptionRowView, paramInt);
      }
      Document localDocument = (Document)this.mDfeList.getItem(j);
      localSubscriptionRowView.bind(localDocument, this.mBitmapLoader, this.mNavigationManager, isSelected(paramInt), this.mParentNode, getCollapsedBackgroundResourceId(paramInt), this.mMyAccountLibrarySubscriptionEntries.getLibrarySubscriptionEntry(localDocument), this.mDfeToc, this.mCancelListener);
      return;
    case 3: 
      bindLoadingFooterView(localView);
      return;
    }
    bindErrorFooterView(localView);
  }
  
  public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    View localView;
    switch (paramInt)
    {
    default: 
      throw new IllegalStateException("Unknown type for onCreateView " + paramInt);
    case 0: 
    case 1: 
      localView = inflate(2130968994, paramViewGroup, false);
    }
    for (;;)
    {
      return new PlayRecyclerView.ViewHolder(localView);
      localView = inflate(2130968834, paramViewGroup, false);
      continue;
      localView = inflate(2130968889, paramViewGroup, false);
      continue;
      localView = inflate(2130969085, paramViewGroup, false);
      continue;
      localView = inflate(2130969125, paramViewGroup, false);
      continue;
      localView = createLoadingFooterView(paramViewGroup);
      continue;
      localView = createErrorFooterView(paramViewGroup);
    }
  }
  
  public final void retryLoadingItems()
  {
    this.mDfeList.retryLoadItems();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.myaccount.MyAccountListPageAdapter
 * JD-Core Version:    0.7.0.1
 */