package com.google.android.finsky.layout;

import android.accounts.Account;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.google.android.finsky.activities.myaccount.MyAccountLibrarySubscriptionEntries;
import com.google.android.finsky.activities.myaccount.MyAccountModel;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.play.image.BitmapLoader;

public class MyAccountSubscriptionCard
  extends MyAccountCard<SubscriptionRowView>
{
  private SubscriptionView.CancelListener mCancelListener;
  private MyAccountLibrarySubscriptionEntries mMyAccountLibrarySubscriptionEntries;
  
  public MyAccountSubscriptionCard(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MyAccountSubscriptionCard(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 2640, 2641, 1);
  }
  
  public final void bind(MyAccountModel paramMyAccountModel, Account paramAccount, DfeToc paramDfeToc, BitmapLoader paramBitmapLoader, SubscriptionView.CancelListener paramCancelListener, MyAccountLibrarySubscriptionEntries paramMyAccountLibrarySubscriptionEntries, NavigationManager paramNavigationManager, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mCancelListener = paramCancelListener;
    this.mMyAccountLibrarySubscriptionEntries = paramMyAccountLibrarySubscriptionEntries;
    super.bind(paramMyAccountModel, paramAccount, paramDfeToc, paramBitmapLoader, paramNavigationManager, paramPlayStoreUiElementNode);
  }
  
  protected final void onBindNoDataView(TextView paramTextView)
  {
    paramTextView.setText(2131362392);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.MyAccountSubscriptionCard
 * JD-Core Version:    0.7.0.1
 */