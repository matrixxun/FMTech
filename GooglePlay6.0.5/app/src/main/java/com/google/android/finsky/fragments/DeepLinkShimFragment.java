package com.google.android.finsky.fragments;

import android.accounts.Account;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeRequest.CancelListener;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.PingResponse;
import com.google.android.finsky.protos.ResolveLink.DirectPurchase;
import com.google.android.finsky.protos.ResolveLink.ResolvedLink;
import com.google.android.finsky.utils.ExternalReferrer;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Sha1Util;
import com.google.android.finsky.utils.Utils;
import java.util.List;

public final class DeepLinkShimFragment
  extends UrlBasedPageFragment
  implements Response.Listener<ResolveLink.ResolvedLink>, DfeRequest.CancelListener
{
  private String mExternalReferrer;
  private String mReferringPackage;
  private ResolveLink.ResolvedLink mResponse;
  private Uri mUri;
  
  private static String getContinueUrl(Uri paramUri)
  {
    String str = paramUri.getQueryParameter("url");
    if (!TextUtils.isEmpty(str)) {
      return Utils.urlDecode(str);
    }
    return null;
  }
  
  private void logVolleyErrorAndFlush(int paramInt, VolleyError paramVolleyError)
  {
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(paramInt).setExceptionType(paramVolleyError).setExternalReferrer(this.mExternalReferrer).setErrorCode(1);
    FinskyApp.get().getEventLogger().logBackgroundEventAndFlush(localBackgroundEventBuilder.event);
  }
  
  public static Fragment newInstance(Uri paramUri, String paramString)
  {
    DeepLinkShimFragment localDeepLinkShimFragment = new DeepLinkShimFragment();
    int j;
    label104:
    Account localAccount;
    if (("market".equals(paramUri.getScheme())) && ("webstoreredirect".equals(paramUri.getHost())))
    {
      String str3 = paramUri.getQueryParameter("uri");
      if (!TextUtils.isEmpty(str3))
      {
        Uri localUri = Uri.parse(Uri.decode(str3));
        if ("play.google.com".equals(localUri.getHost())) {
          paramUri = localUri;
        }
      }
    }
    else
    {
      Account[] arrayOfAccount = AccountHandler.getAccounts(FinskyApp.get());
      String str1 = paramUri.getQueryParameter("ah");
      if (TextUtils.isEmpty(str1)) {
        break label233;
      }
      int i = arrayOfAccount.length;
      j = 0;
      if (j >= i) {
        break label233;
      }
      localAccount = arrayOfAccount[j];
      if (!Sha1Util.secureHash(localAccount.name.getBytes()).equals(str1)) {
        break label227;
      }
    }
    label227:
    label233:
    for (String str2 = localAccount.name;; str2 = null)
    {
      if (!TextUtils.isEmpty(str2))
      {
        localDeepLinkShimFragment.setDfeAccount(str2);
        localDeepLinkShimFragment.setArgument("DeepLinkShimFragment.overrideAccount", str2);
      }
      localDeepLinkShimFragment.setDfeTocAndUrl(FinskyApp.get().mToc, paramUri.toString());
      localDeepLinkShimFragment.mReferringPackage = paramString;
      localDeepLinkShimFragment.mExternalReferrer = ExternalReferrer.getExternalReferrer(paramUri);
      return localDeepLinkShimFragment;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = FinskyLog.scrubPii(paramUri.toString());
      FinskyLog.w("Unrecognized redirect URI: %s", arrayOfObject);
      paramUri = Uri.parse("http://play.google.com/store");
      break;
      j++;
      break label104;
    }
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
    this.mUri = Uri.parse(this.mUrl);
    requestData();
  }
  
  public final void onDataChanged()
  {
    if (this.mResponse == null) {}
    while (!canChangeFragmentManagerState()) {
      return;
    }
    this.mNavigationManager.popBackStack();
    FinskyEventLog localFinskyEventLog = FinskyApp.get().getEventLogger();
    byte[] arrayOfByte = this.mResponse.serverLogsCookie;
    String str = ExternalReferrer.getExternalReferrer(this.mUri);
    if (this.mResponse.detailsUrl.length() > 0)
    {
      localFinskyEventLog.logDeepLinkEventAndFlush(1, this.mUrl, null, str, this.mReferringPackage, arrayOfByte);
      ExternalReferrer.saveExternalReferrerForUrl(str, this.mResponse.detailsUrl);
      this.mNavigationManager.goToDocPage(this.mResponse.detailsUrl, getContinueUrl(this.mUri), this.mArguments.getString("DeepLinkShimFragment.overrideAccount"), this.mUrl);
      return;
    }
    if (this.mResponse.browseUrl.length() > 0)
    {
      localFinskyEventLog.logDeepLinkEventAndFlush(2, this.mUrl, null, str, this.mReferringPackage, arrayOfByte);
      this.mNavigationManager.goBrowse(this.mResponse.browseUrl, null, -1, this.mDfeToc, null);
      return;
    }
    if (this.mResponse.searchUrl.length() > 0)
    {
      localFinskyEventLog.logDeepLinkEventAndFlush(3, this.mUrl, null, str, this.mReferringPackage, arrayOfByte);
      this.mNavigationManager.goToSearch(this.mResponse.searchUrl, this.mResponse.query, this.mResponse.backend, null);
      return;
    }
    if (this.mResponse.wishlistUrl.length() > 0)
    {
      localFinskyEventLog.logDeepLinkEventAndFlush(8, this.mUrl, null, str, this.mReferringPackage, arrayOfByte);
      this.mNavigationManager.goToMyWishlist();
      return;
    }
    if (this.mResponse.myAccountUrl.length() > 0)
    {
      localFinskyEventLog.logDeepLinkEventAndFlush(10, this.mUrl, null, str, this.mReferringPackage, arrayOfByte);
      this.mNavigationManager.goToMyAccount();
      return;
    }
    if (this.mResponse.directPurchase != null)
    {
      FinskyLog.d("Direct purchase deprecated.", new Object[0]);
      ResolveLink.DirectPurchase localDirectPurchase = this.mResponse.directPurchase;
      localFinskyEventLog.logDeepLinkEventAndFlush(4, this.mUrl, null, str, this.mReferringPackage, arrayOfByte);
      ExternalReferrer.saveExternalReferrerForDocId(str, this.mResponse.directPurchase.purchaseDocid);
      this.mNavigationManager.goToDocPage(localDirectPurchase.detailsUrl, getContinueUrl(this.mUri), this.mArguments.getString("DeepLinkShimFragment.overrideAccount"), this.mUrl);
      return;
    }
    if (this.mResponse.homeUrl.length() > 0)
    {
      localFinskyEventLog.logDeepLinkEventAndFlush(5, this.mUrl, null, str, this.mReferringPackage, arrayOfByte);
      this.mNavigationManager.goToAggregatedHome(this.mDfeToc, this.mResponse.homeUrl);
      return;
    }
    if (this.mResponse.redeemGiftCard != null)
    {
      localFinskyEventLog.logDeepLinkEventAndFlush(6, this.mUrl, null, str, this.mReferringPackage, arrayOfByte);
      this.mNavigationManager.goToAggregatedHome(this.mDfeToc);
      this.mNavigationManager.goRedeem(this.mDfeApi.getAccountName(), this.mResponse.redeemGiftCard);
      return;
    }
    localFinskyEventLog.logDeepLinkEventAndFlush(0, this.mUrl, null, str, this.mReferringPackage, arrayOfByte);
    Intent localIntent = new Intent("android.intent.action.VIEW");
    localIntent.setData(Uri.parse(this.mUrl));
    localIntent.addFlags(268435456);
    localIntent.putExtra("dont_resolve_again", true);
    List localList = getActivity().getPackageManager().queryIntentActivities(localIntent, 0);
    if (localList.size() == 2)
    {
      ActivityInfo localActivityInfo = ((ResolveInfo)localList.get(0)).activityInfo;
      if (localActivityInfo.packageName.equals(getActivity().getPackageName())) {
        localActivityInfo = ((ResolveInfo)localList.get(1)).activityInfo;
      }
      localIntent.setPackage(localActivityInfo.packageName);
    }
    startActivity(localIntent);
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    FinskyLog.e("Error on resolveLink: %s", new Object[] { paramVolleyError });
    logVolleyErrorAndFlush(1104, paramVolleyError);
    this.mNavigationManager.goToAggregatedHome(this.mDfeToc);
  }
  
  public final void onRequestCanceled()
  {
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(1104).setExternalReferrer(this.mExternalReferrer).setErrorCode(2);
    FinskyApp.get().getEventLogger().logBackgroundEventAndFlush(localBackgroundEventBuilder.event);
  }
  
  protected final void rebindViews() {}
  
  protected final void requestData()
  {
    FinskyEventLog localFinskyEventLog = FinskyApp.get().getEventLogger();
    this.mDfeApi.pingReferrer(this.mUrl, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        FinskyLog.e("Error pinging deeplink click: %s", new Object[] { paramAnonymousVolleyError });
        DeepLinkShimFragment.access$000$1eac0ed(DeepLinkShimFragment.this, paramAnonymousVolleyError);
      }
    });
    String str = ExternalReferrer.getExternalReferrer(this.mUri);
    localFinskyEventLog.logDeepLinkEventAndFlush(12, this.mUrl, null, str, this.mReferringPackage, null);
    this.mDfeApi.resolveLink(this.mUrl, this.mReferringPackage, this, this, this);
    switchToLoading();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.fragments.DeepLinkShimFragment
 * JD-Core Version:    0.7.0.1
 */