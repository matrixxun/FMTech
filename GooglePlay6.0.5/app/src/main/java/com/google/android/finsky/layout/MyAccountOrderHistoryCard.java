package com.google.android.finsky.layout;

import android.accounts.Account;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.google.android.finsky.activities.myaccount.MyAccountModel;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.play.image.BitmapLoader;

public class MyAccountOrderHistoryCard
  extends MyAccountCard<OrderHistoryRowView>
{
  private OrderHistoryRowView.OnRefundActionListener mOnRefundActionListener;
  
  public MyAccountOrderHistoryCard(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MyAccountOrderHistoryCard(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 2601, 2600, 0);
  }
  
  public final void bind(MyAccountModel paramMyAccountModel, Account paramAccount, DfeToc paramDfeToc, BitmapLoader paramBitmapLoader, OrderHistoryRowView.OnRefundActionListener paramOnRefundActionListener, NavigationManager paramNavigationManager, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mOnRefundActionListener = paramOnRefundActionListener;
    super.bind(paramMyAccountModel, paramAccount, paramDfeToc, paramBitmapLoader, paramNavigationManager, paramPlayStoreUiElementNode);
  }
  
  protected final void onBindNoDataView(TextView paramTextView)
  {
    paramTextView.setText(2131362381);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.MyAccountOrderHistoryCard
 * JD-Core Version:    0.7.0.1
 */