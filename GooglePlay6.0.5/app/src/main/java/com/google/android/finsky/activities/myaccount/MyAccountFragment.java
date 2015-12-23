package com.google.android.finsky.activities.myaccount;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerImpl;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObservable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.FamilyLibrarySettingsFragment;
import com.google.android.finsky.activities.FamilyMemberSettingsActivity;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Builder;
import com.google.android.finsky.activities.SimpleAlertDialog.Listener;
import com.google.android.finsky.adapters.EmptyRecyclerViewAdapter;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.billing.lightpurchase.billingprofile.BillingProfileSidecar;
import com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.RedeemCodeActivity;
import com.google.android.finsky.billing.refund.RequestRefundActivity;
import com.google.android.finsky.config.G;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.ContentFrame;
import com.google.android.finsky.layout.HeaderLayoutSwitcher;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.layout.MyAccountFamilyManagementCard.FamilyManagementCardCallback;
import com.google.android.finsky.layout.OrderHistoryRowView.OnRefundActionListener;
import com.google.android.finsky.layout.SubscriptionView.CancelListener;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout.FinskyConfigurator;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.Libraries.Listener;
import com.google.android.finsky.library.LibrarySubscriptionEntry;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.MyAccountResponse;
import com.google.android.finsky.utils.AppSupport;
import com.google.android.finsky.utils.AppSupport.RefundListener;
import com.google.android.finsky.utils.CancelSubscriptionDialog;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FamilyUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.MyAccountHelper;
import com.google.android.finsky.utils.UserSettingsCache;
import com.google.android.finsky.utils.UserSettingsCache.UserSettingsCacheListener;
import com.google.android.play.utils.config.GservicesValue;
import java.util.Map;

public final class MyAccountFragment
  extends PageFragment
  implements SimpleAlertDialog.Listener, MyAccountFamilyManagementCard.FamilyManagementCardCallback, OrderHistoryRowView.OnRefundActionListener, SubscriptionView.CancelListener, Libraries.Listener, UserSettingsCache.UserSettingsCacheListener
{
  private MyAccountAdapter mAdapter;
  private String mBreadcrumb;
  private boolean mIsDestroyed;
  private long mLastRequestTimeMs;
  private Libraries mLibraries;
  private MyAccountModel mMyAccountModel;
  private RecyclerView mMyAccountRecyclerView;
  private boolean mRefreshingUserSetting;
  private MyAccountResponse mResponse;
  private final PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(10);
  private final Bundle mViewState = new Bundle();
  
  private void handleMyAccountResponse()
  {
    this.mLayoutSwitcher.switchToDataMode();
    rebindViews();
    this.mMyAccountModel = new MyAccountModel(this.mResponse, this.mDfeApi);
    this.mAdapter.setModel(this.mMyAccountModel);
    loadMyAccountModel();
    this.mLastRequestTimeMs = System.currentTimeMillis();
  }
  
  private void loadMyAccountCard(int paramInt)
  {
    if ((this.mMyAccountModel.isCardTypeEnabled(paramInt)) && (!this.mMyAccountModel.getDfeList(paramInt).isReady()))
    {
      MyAccountModel localMyAccountModel = this.mMyAccountModel;
      MyAccountAdapter.3 local3 = new MyAccountAdapter.3(this.mAdapter, paramInt);
      if (localMyAccountModel.mMyAccountCardDataMap.containsKey(Integer.valueOf(paramInt))) {
        ((MyAccountModel.MyAccountCardData)localMyAccountModel.mMyAccountCardDataMap.get(Integer.valueOf(paramInt))).startLoad(local3, false);
      }
    }
  }
  
  private void loadMyAccountModel()
  {
    loadMyAccountCard(0);
    loadMyAccountCard(1);
    loadMyAccountCard(2);
  }
  
  public static MyAccountFragment newInstance()
  {
    MyAccountFragment localMyAccountFragment = new MyAccountFragment();
    localMyAccountFragment.setArgument("finsky.PageFragment.toc", FinskyApp.get().mToc);
    return localMyAccountFragment;
  }
  
  private void notifyDataSetChanged()
  {
    if (this.mAdapter != null) {
      this.mAdapter.mObservable.notifyChanged();
    }
  }
  
  protected final LayoutSwitcher createLayoutSwitcher(ContentFrame paramContentFrame)
  {
    return new HeaderLayoutSwitcher(paramContentFrame, this);
  }
  
  protected final int getDefaultHeaderShadowMode()
  {
    return 3;
  }
  
  protected final int getLayoutRes()
  {
    return 2130968781;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public final boolean isLoading()
  {
    return this.mRefreshingUserSetting;
  }
  
  public final void launchFamilyCreation()
  {
    String str = FinskyApp.get().getCurrentAccountName();
    startActivityForResult(new Intent("com.google.android.gms.kids.familymanagement.CREATE").setPackage((String)G.familyManagementPackage.get()).putExtra("accountName", str).putExtra("appId", "play.and"), 3);
  }
  
  public final void launchFamilyLibrarySettings()
  {
    NavigationManager localNavigationManager = this.mNavigationManager;
    if (localNavigationManager.canNavigate()) {
      localNavigationManager.showPage(18, null, new FamilyLibrarySettingsFragment(), false, new View[0]);
    }
  }
  
  public final void launchFamilyManagement()
  {
    FragmentActivity localFragmentActivity = getActivity();
    String str1 = FinskyApp.get().getCurrentAccountName();
    Intent localIntent1 = localFragmentActivity.getIntent();
    String str2;
    if (localIntent1 == null)
    {
      str2 = null;
      if (str2 != null) {
        break label115;
      }
    }
    label115:
    for (String str3 = "play.and";; str3 = str2)
    {
      Intent localIntent2 = new Intent(localFragmentActivity, FamilyMemberSettingsActivity.class).putExtra("accountName", str1);
      startActivityForResult(new Intent("com.google.android.gms.kids.familymanagement.MANAGE").setPackage((String)G.familyManagementPackage.get()).putExtra("accountName", str1).putExtra("appId", str3).putExtra("manageMemberIntent", localIntent2), 2);
      return;
      str2 = localIntent1.getStringExtra("family_app_id");
      break;
    }
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    rebindActionBar();
    if (MyAccountHelper.hasMutationOccurredSince(this.mLastRequestTimeMs)) {
      this.mResponse = null;
    }
    int i;
    if (this.mResponse != null)
    {
      i = 1;
      if (i != 0) {
        break label63;
      }
      requestData();
      this.mLayoutSwitcher.switchToLoadingMode();
    }
    for (;;)
    {
      this.mActionBarController.enableActionBarOverlay();
      return;
      i = 0;
      break;
      label63:
      rebindViews();
    }
  }
  
  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt1 == 2) || (paramInt1 == 3))
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt2);
      FinskyLog.d("Family activity result, resultCode: %d", arrayOfObject);
      if (paramIntent == null) {
        FinskyLog.d("Family activity returned null data from early exit.", new Object[0]);
      }
      label212:
      for (;;)
      {
        int j = 0;
        if (j != 0)
        {
          FinskyLog.d("Requerying family state by refreshing user settings.", new Object[0]);
          this.mRefreshingUserSetting = true;
          UserSettingsCache.updateUserSettings(FinskyApp.get().getCurrentAccountName());
          this.mAdapter.notifyFamilyCardChanged();
          BillingProfileSidecar localBillingProfileSidecar = (BillingProfileSidecar)this.mFragmentManager.findFragmentByTag("billing_profile_sidecar");
          if (localBillingProfileSidecar != null) {
            localBillingProfileSidecar.requestBillingProfile();
          }
        }
        else
        {
          return;
          String str1 = paramIntent.getStringExtra("accountName");
          if (TextUtils.isEmpty(str1))
          {
            FinskyLog.wtf("Family activity result expected to return an account name.", new Object[0]);
            continue;
          }
          if ((paramInt2 == 8) || (paramInt2 == 9) || (paramIntent.getBooleanExtra("familyChanged", false))) {}
          for (int i = 1;; i = 0)
          {
            if (i == 0) {
              break label212;
            }
            String str2 = paramIntent.getStringExtra("consistencyToken");
            if (str2 != null)
            {
              FinskyLog.d("Saving consistency token from family creation.", new Object[0]);
              FamilyUtils.saveConsistencyToken(str1, str2);
            }
            j = 1;
            break;
          }
        }
      }
      FinskyLog.wtf("Billing profile sidecar not found!", new Object[0]);
      return;
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public final void onAllLibrariesLoaded() {}
  
  public final void onCancel(Document paramDocument, LibrarySubscriptionEntry paramLibrarySubscriptionEntry)
  {
    CancelSubscriptionDialog.show(this.mFragmentManager, paramDocument, paramLibrarySubscriptionEntry);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRetainInstance$1385ff();
    FragmentManagerImpl localFragmentManagerImpl = this.mFragmentManager;
    Fragment localFragment = localFragmentManagerImpl.findFragmentByTag("billing_profile_sidecar");
    if (localFragment != null) {
      localFragmentManagerImpl.beginTransaction().remove(localFragment).commit();
    }
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    this.mIsDestroyed = false;
    FinskyHeaderListLayout localFinskyHeaderListLayout = (FinskyHeaderListLayout)this.mDataView;
    localFinskyHeaderListLayout.configure(new FinskyHeaderListLayout.FinskyConfigurator(localFinskyHeaderListLayout.getContext())
    {
      protected final void addContentView(LayoutInflater paramAnonymousLayoutInflater, ViewGroup paramAnonymousViewGroup)
      {
        paramAnonymousLayoutInflater.inflate(2130968783, paramAnonymousViewGroup);
      }
      
      protected final int getListViewId()
      {
        return 2131755590;
      }
    });
    localFinskyHeaderListLayout.setContentViewId(2131755590);
    this.mBreadcrumb = paramViewGroup.getContext().getString(2131362759);
    this.mMyAccountRecyclerView = ((RecyclerView)this.mDataView.findViewById(2131755590));
    LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager();
    this.mMyAccountRecyclerView.setLayoutManager(localLinearLayoutManager);
    this.mMyAccountRecyclerView.setAdapter(new EmptyRecyclerViewAdapter());
    this.mLibraries = FinskyApp.get().mLibraries;
    this.mLibraries.addListener(this);
    UserSettingsCache.addListener(this);
    return localView;
  }
  
  public final void onDestroyView()
  {
    this.mIsDestroyed = true;
    if (this.mMyAccountRecyclerView != null) {
      this.mMyAccountRecyclerView = null;
    }
    if (this.mAdapter != null)
    {
      this.mAdapter.onDestroy();
      this.mAdapter = null;
    }
    this.mLibraries.removeListener(this);
    UserSettingsCache.removeListener(this);
    super.onDestroyView();
  }
  
  public final void onLibraryContentsChanged$40bdb4b1()
  {
    notifyDataSetChanged();
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle) {}
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 1)
    {
      String str1 = paramBundle.getString("package_name");
      String str2 = paramBundle.getString("account_name");
      AppSupport.silentRefund(this.mFragmentManager, str1, str2, true, new AppSupport.RefundListener()
      {
        public final void onRefundComplete(boolean paramAnonymousBoolean)
        {
          if (paramAnonymousBoolean)
          {
            MyAccountFragment.this.notifyDataSetChanged();
            boolean bool1 = FinskyApp.get().getExperiments().isEnabled(12603718L);
            boolean bool2 = FinskyApp.get().getExperiments().isEnabled(12604495L);
            if ((bool1) && (!bool2)) {
              UserSettingsCache.updateUserSettings(MyAccountFragment.this.mDfeApi.getAccountName());
            }
          }
        }
        
        public final void onRefundStart()
        {
          Toast.makeText(MyAccountFragment.this.mContext, 2131362651, 1).show();
        }
      });
    }
  }
  
  public final void onRefundAction(String paramString1, String paramString2)
  {
    FragmentManagerImpl localFragmentManagerImpl = this.mFragmentManager;
    if (localFragmentManagerImpl.findFragmentByTag("refund_confirm") != null) {
      return;
    }
    SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
    localBuilder.setMessageId(2131362812).setPositiveId(2131362937).setNegativeId(2131362370);
    Bundle localBundle = new Bundle();
    localBundle.putString("package_name", paramString1);
    localBundle.putString("account_name", paramString2);
    localBuilder.setCallback(this, 1, localBundle);
    localBuilder.build().show(localFragmentManagerImpl, "refund_confirm");
  }
  
  public final void onRequestRefundAction(Document paramDocument)
  {
    startActivity(RequestRefundActivity.createIntent(this.mDfeApi.getAccountName(), paramDocument));
  }
  
  public final void onUserSettingsChanged()
  {
    this.mRefreshingUserSetting = false;
    if (this.mAdapter != null) {
      this.mAdapter.notifyFamilyCardChanged();
    }
  }
  
  public final void onUserSettingsRefreshFailed()
  {
    this.mRefreshingUserSetting = false;
    if (this.mAdapter != null) {
      this.mAdapter.notifyFamilyCardChanged();
    }
  }
  
  public final void rebindActionBar()
  {
    this.mPageFragmentHost.updateActionBarTitle(this.mBreadcrumb);
    this.mPageFragmentHost.updateCurrentBackendId(0, true);
    this.mPageFragmentHost.switchToRegularActionBar();
  }
  
  protected final void rebindViews()
  {
    BillingProfileSidecar localBillingProfileSidecar = (BillingProfileSidecar)this.mFragmentManager.findFragmentByTag("billing_profile_sidecar");
    if (localBillingProfileSidecar == null)
    {
      Account localAccount = this.mDfeApi.getAccount();
      localBillingProfileSidecar = BillingProfileSidecar.newInstance(localAccount, null, null, RedeemCodeActivity.createIntent(localAccount.name, 5), 4, 380, 381, 384, 382, 383, 385, null);
      getActivity().getSupportFragmentManager().beginTransaction().add(localBillingProfileSidecar, "billing_profile_sidecar").commit();
    }
    if (this.mAdapter == null)
    {
      this.mAdapter = new MyAccountAdapter(this.mContext, this.mDfeApi, this.mBitmapLoader, this.mNavigationManager, this, this, this, localBillingProfileSidecar, this.mMyAccountRecyclerView, this, this.mViewState);
      localBillingProfileSidecar.setListener(new MyAccountAdapter.2(this.mAdapter));
      this.mMyAccountRecyclerView.setAdapter(this.mAdapter);
    }
    if (this.mMyAccountModel != null)
    {
      this.mAdapter.setModel(this.mMyAccountModel);
      loadMyAccountModel();
    }
  }
  
  protected final void requestData()
  {
    if (this.mResponse == null)
    {
      this.mDfeApi.getMyAccount(new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
      {
        public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
        {
          FinskyLog.e("Could not retrieve my account content: %s", new Object[] { paramAnonymousVolleyError });
          if (!MyAccountFragment.this.mIsDestroyed)
          {
            String str = ErrorStrings.get(MyAccountFragment.this.mContext, paramAnonymousVolleyError);
            MyAccountFragment.this.switchToError(str);
          }
        }
      });
      return;
    }
    handleMyAccountResponse();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.myaccount.MyAccountFragment
 * JD-Core Version:    0.7.0.1
 */