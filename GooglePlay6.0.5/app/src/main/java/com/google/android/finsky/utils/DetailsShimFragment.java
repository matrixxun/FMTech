package com.google.android.finsky.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Builder;
import com.google.android.finsky.activities.SimpleAlertDialog.Listener;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeServerError;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.fragments.UrlBasedPageFragment;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.util.Collection;

public final class DetailsShimFragment
  extends UrlBasedPageFragment
  implements SimpleAlertDialog.Listener
{
  private DfeDetails mDetailsData;
  
  public static DetailsShimFragment newInstance(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    DetailsShimFragment localDetailsShimFragment = new DetailsShimFragment();
    localDetailsShimFragment.setDfeAccount(paramString3);
    localDetailsShimFragment.setDfeTocAndUrl(FinskyApp.get().mToc, paramString1);
    localDetailsShimFragment.setArgument("finsky.DetailsFragment.continueUrl", paramString2);
    localDetailsShimFragment.setArgument("finsky.DetailsFragment.overrideAccount", paramString3);
    localDetailsShimFragment.setArgument("finsky.DetailsShimFragment.originalUrl", paramString4);
    return localDetailsShimFragment;
  }
  
  protected final int getLayoutRes()
  {
    return 0;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return null;
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    requestData();
  }
  
  public final void onDataChanged()
  {
    if (this.mDetailsData.getDocument() == null)
    {
      this.mPageFragmentHost.showErrorDialog(null, this.mContext.getString(2131362086), true);
      return;
    }
    this.mNavigationManager.goToDocPage$6d245699(this.mDetailsData.getDocument(), this.mUrl, this.mArguments.getString("finsky.DetailsFragment.continueUrl"), this.mArguments.getString("finsky.DetailsFragment.overrideAccount"));
  }
  
  public final void onDestroyView()
  {
    super.onDestroyView();
    if (this.mDetailsData != null)
    {
      this.mDetailsData.removeDataChangedListener(this);
      this.mDetailsData.removeErrorListener(this);
    }
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    super.onErrorResponse(paramVolleyError);
    if ((!(paramVolleyError instanceof DfeServerError)) || (!GooglePlayServicesUtil.isSidewinderDevice(this.mContext))) {}
    String str1;
    String str2;
    do
    {
      return;
      str1 = Utils.getSysProperty("finsky.sw_home_url");
      str2 = Uri.parse(Uri.decode(this.mArguments.getString("finsky.DetailsShimFragment.originalUrl"))).getAuthority();
    } while ((TextUtils.isEmpty(str1)) || (!TextUtils.equals(str2, "play.google.com")));
    SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
    localBuilder.setMessageId(2131361851).setPositiveId(2131362937).setNegativeId(2131362370).setCanceledOnTouchOutside(true).setCallback(this, 1, null);
    localBuilder.build().show(this.mFragmentManager, "DetailsShimFragment.errorDialog");
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle) {}
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 1)
    {
      String str = Utils.getSysProperty("finsky.sw_home_url");
      this.mNavigationManager.goToUrl(str);
    }
  }
  
  protected final void rebindViews() {}
  
  protected final void requestData()
  {
    if (this.mDetailsData != null)
    {
      this.mDetailsData.removeDataChangedListener(this);
      this.mDetailsData.removeErrorListener(this);
    }
    Collection localCollection = VoucherUtils.getVoucherIds(FinskyApp.get().mLibraries.getAccountLibrary(this.mDfeApi.getAccount()));
    this.mDetailsData = new DfeDetails(this.mDfeApi, this.mUrl, false, localCollection);
    this.mDetailsData.addDataChangedListener(this);
    this.mDetailsData.addErrorListener(this);
    switchToBlank();
    switchToLoading();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.DetailsShimFragment
 * JD-Core Version:    0.7.0.1
 */