package com.google.android.finsky.layout;

import android.accounts.Account;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.myaccount.MyAccountListPageFragment;
import com.google.android.finsky.activities.myaccount.MyAccountModel;
import com.google.android.finsky.activities.myaccount.MyAccountModel.MyAccountCardData;
import com.google.android.finsky.activities.myaccount.SelectionListener;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.CardLinearLayout;
import java.util.Map;

public abstract class MyAccountCard<V extends MyAccountRowView>
  extends CardLinearLayout
  implements View.OnClickListener, PlayStoreUiElementNode
{
  protected Account mAccount;
  protected BitmapLoader mBitmapLoader;
  private ViewGroup mCardHolder;
  protected int mCardType;
  private View mErrorIndicatorView;
  private FinskyEventLog mEventLogger;
  protected final LayoutInflater mLayoutInflater;
  private final int mMoreButtonUiElementId;
  private ViewGroup mMoreFooter;
  protected MyAccountModel mMyAccountModel;
  protected NavigationManager mNavigationManager;
  protected PlayStoreUiElementNode mParentNode;
  private View mProgressIndicator;
  private SelectionListener mSelectionListener;
  private TextView mTitleView;
  protected DfeToc mToc;
  protected final PlayStore.PlayStoreUiElement mUiElementProto;
  
  protected MyAccountCard(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramContext, paramAttributeSet);
    this.mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(paramInt1);
    this.mMoreButtonUiElementId = paramInt2;
    this.mLayoutInflater = LayoutInflater.from(paramContext);
    this.mCardType = paramInt3;
  }
  
  private int getItemCount()
  {
    if (this.mMyAccountModel.getDfeList(this.mCardType) == null) {
      return 0;
    }
    return this.mMyAccountModel.getDfeList(this.mCardType).getCount();
  }
  
  public static void setMarginsForCardView(View paramView)
  {
    int i = UiUtils.getGridHorizontalPadding(paramView.getResources());
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)paramView.getLayoutParams();
    localMarginLayoutParams.setMargins(i, localMarginLayoutParams.topMargin, i, localMarginLayoutParams.bottomMargin);
  }
  
  private void showLoadingMode()
  {
    setVisibility(0);
    setMarginsForCardView(this);
    this.mProgressIndicator.setVisibility(0);
    this.mCardHolder.setVisibility(8);
    this.mMoreFooter.setVisibility(8);
    this.mErrorIndicatorView.setVisibility(8);
  }
  
  public final void bind(MyAccountModel paramMyAccountModel, Account paramAccount, DfeToc paramDfeToc, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mAccount = paramAccount;
    this.mMyAccountModel = paramMyAccountModel;
    this.mToc = paramDfeToc;
    this.mBitmapLoader = paramBitmapLoader;
    this.mNavigationManager = paramNavigationManager;
    this.mParentNode = paramPlayStoreUiElementNode;
    this.mEventLogger = FinskyApp.get().getEventLogger(paramAccount);
    this.mSelectionListener = new SelectionListener(this.mEventLogger);
    if (this.mMyAccountModel == null) {
      return;
    }
    if (!this.mMyAccountModel.isCardTypeEnabled(this.mCardType))
    {
      setVisibility(8);
      return;
    }
    MyAccountModel localMyAccountModel1 = this.mMyAccountModel;
    int i = this.mCardType;
    int j;
    if ((localMyAccountModel1.mMyAccountCardDataMap.containsKey(Integer.valueOf(i))) && (localMyAccountModel1.getDfeList(i).isReady()))
    {
      j = 1;
      if (j != 0) {
        break label301;
      }
      MyAccountModel localMyAccountModel3 = this.mMyAccountModel;
      int i1 = this.mCardType;
      if ((!localMyAccountModel3.mMyAccountCardDataMap.containsKey(Integer.valueOf(i1))) || (!((MyAccountModel.MyAccountCardData)localMyAccountModel3.mMyAccountCardDataMap.get(Integer.valueOf(i1))).mFailedToLoad)) {
        break label290;
      }
    }
    label290:
    for (int i2 = 1;; i2 = 0)
    {
      if (i2 == 0) {
        break label296;
      }
      setVisibility(0);
      setMarginsForCardView(this);
      this.mTitleView.setText(this.mMyAccountModel.getHeader(this.mCardType));
      this.mProgressIndicator.setVisibility(8);
      this.mCardHolder.setVisibility(8);
      this.mMoreFooter.setVisibility(8);
      this.mErrorIndicatorView.setVisibility(0);
      this.mErrorIndicatorView.findViewById(2131755482).setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          MyAccountModel localMyAccountModel = MyAccountCard.this.mMyAccountModel;
          int i = MyAccountCard.this.mCardType;
          if (localMyAccountModel.mMyAccountCardDataMap.containsKey(Integer.valueOf(i)))
          {
            MyAccountModel.MyAccountCardData localMyAccountCardData = (MyAccountModel.MyAccountCardData)localMyAccountModel.mMyAccountCardDataMap.get(Integer.valueOf(i));
            localMyAccountCardData.startLoad(localMyAccountCardData.mOnDataChangedListener, true);
          }
          MyAccountCard.this.showLoadingMode();
        }
      });
      return;
      j = 0;
      break;
    }
    label296:
    showLoadingMode();
    return;
    label301:
    setVisibility(0);
    setMarginsForCardView(this);
    this.mTitleView.setText(this.mMyAccountModel.getHeader(this.mCardType));
    this.mProgressIndicator.setVisibility(8);
    this.mCardHolder.setVisibility(0);
    int k = getItemCount();
    int m = Math.min(3, k);
    this.mCardHolder.removeAllViews();
    int n = 0;
    if (n < m)
    {
      MyAccountRowView localMyAccountRowView = onCreateRowView(this.mCardHolder);
      this.mCardHolder.addView(localMyAccountRowView);
      Document localDocument = (Document)this.mMyAccountModel.getDfeList(this.mCardType).getItem(n);
      if (n == 0) {}
      for (boolean bool = true;; bool = false)
      {
        onBindRowView(localMyAccountRowView, localDocument, bool);
        this.mSelectionListener.setAsClickListenerOf(localMyAccountRowView, n);
        if (n == 0) {
          this.mSelectionListener.setDefaultRowView(localMyAccountRowView, n);
        }
        n++;
        break;
      }
    }
    if (m == 0)
    {
      ViewGroup localViewGroup = this.mCardHolder;
      TextView localTextView = (TextView)this.mLayoutInflater.inflate(2130968834, localViewGroup, false);
      if (localTextView != null)
      {
        this.mCardHolder.addView(localTextView);
        onBindNoDataView(localTextView);
      }
    }
    if (k <= 3) {
      this.mMoreFooter.setVisibility(8);
    }
    for (;;)
    {
      this.mParentNode.childImpression(this);
      MyAccountModel localMyAccountModel2 = this.mMyAccountModel;
      FinskyEventLog.setServerLogCookie(this.mUiElementProto, localMyAccountModel2.getDfeList(this.mCardType).mContainerDocument.mDocument.serverLogsCookie);
      return;
      this.mMoreFooter.setVisibility(0);
      this.mMoreFooter.setOnClickListener(this);
    }
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
  
  protected void onBindNoDataView(TextView paramTextView) {}
  
  protected abstract void onBindRowView(V paramV, Document paramDocument, boolean paramBoolean);
  
  public void onClick(View paramView)
  {
    NavigationManager localNavigationManager;
    String str1;
    String str2;
    DfeToc localDfeToc;
    if (paramView == this.mMoreFooter)
    {
      this.mEventLogger.logClickEvent(this.mMoreButtonUiElementId, null, this);
      localNavigationManager = this.mNavigationManager;
      str1 = this.mMyAccountModel.getDfeList(this.mCardType).mInitialListUrl;
      str2 = this.mMyAccountModel.getHeader(this.mCardType);
      localDfeToc = this.mToc;
      int i = this.mCardType;
      if (localNavigationManager.canNavigate()) {
        if (i != 0) {
          break label104;
        }
      }
    }
    label104:
    for (boolean bool = true;; bool = false)
    {
      localNavigationManager.showPage(15, str1, MyAccountListPageFragment.newInstance(str1, str2, localDfeToc, bool), false, new View[0]);
      return;
    }
  }
  
  protected abstract V onCreateRowView(ViewGroup paramViewGroup);
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mTitleView = ((TextView)findViewById(2131755173));
    this.mCardHolder = ((ViewGroup)findViewById(2131755710));
    this.mMoreFooter = ((ViewGroup)findViewById(2131755711));
    this.mProgressIndicator = findViewById(2131755289);
    this.mErrorIndicatorView = findViewById(2131755483);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.MyAccountCard
 * JD-Core Version:    0.7.0.1
 */