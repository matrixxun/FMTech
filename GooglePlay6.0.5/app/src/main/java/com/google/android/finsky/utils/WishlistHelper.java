package com.google.android.finsky.utils;

import android.accounts.Account;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.LibraryEntry;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.ModifyLibrary.ModifyLibraryResponse;
import java.util.ArrayList;
import java.util.List;

public final class WishlistHelper
{
  private static long sLastWishlistMutationTimeMs;
  private static List<WishlistStatusListener> sWishlistStatusListeners = new ArrayList();
  
  public static void addWishlistStatusListener(WishlistStatusListener paramWishlistStatusListener)
  {
    sWishlistStatusListeners.add(paramWishlistStatusListener);
  }
  
  public static boolean hasMutationOccurredSince(long paramLong)
  {
    return paramLong < sLastWishlistMutationTimeMs;
  }
  
  private static void invokeWishlistStatusListeners(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    for (int i = -1 + sWishlistStatusListeners.size(); i >= 0; i--) {
      ((WishlistStatusListener)sWishlistStatusListeners.get(i)).onWishlistStatusChanged(paramString, paramBoolean1, paramBoolean2);
    }
  }
  
  public static boolean isInWishlist(Document paramDocument, Account paramAccount)
  {
    LibraryEntry localLibraryEntry = LibraryEntry.fromDocument(paramAccount.name, "u-wl", paramDocument, 1);
    return FinskyApp.get().mLibraries.getAccountLibrary(paramAccount).contains(localLibraryEntry);
  }
  
  public static void processWishlistClick(final View paramView, Document paramDocument, DfeApi paramDfeApi)
  {
    boolean bool1 = true;
    if (paramDocument == null)
    {
      FinskyLog.w("Tried to wishlist an item but there is no document active", new Object[0]);
      return;
    }
    Context localContext = paramView.getContext();
    final boolean bool2 = isInWishlist(paramDocument, paramDfeApi.getAccount());
    final String str1 = paramDocument.mDocument.docid;
    final String str2 = paramDocument.mDocument.title;
    final Resources localResources = localContext.getResources();
    Response.Listener local1 = new Response.Listener() {};
    Response.ErrorListener local2 = new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        int i;
        if (this.val$wasInWishlist)
        {
          i = 2131362935;
          Resources localResources = localResources;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = str2;
          String str = localResources.getString(i, arrayOfObject);
          Toast.makeText(FinskyApp.get(), str, 1).show();
          if (!this.val$wasInWishlist) {
            break label88;
          }
          FinskyLog.e("Unable to remove from wishlist: %s", new Object[] { paramAnonymousVolleyError });
        }
        for (;;)
        {
          WishlistHelper.access$100$44c588bf(str1, this.val$wasInWishlist);
          return;
          i = 2131362932;
          break;
          label88:
          FinskyLog.e("Unable to add to wishlist: %s", new Object[] { paramAnonymousVolleyError });
        }
      }
    };
    boolean bool3 = UiUtils.isAccessibilityEnabled(localContext);
    if (bool2)
    {
      String[] arrayOfString2 = new String[bool1];
      arrayOfString2[0] = str1;
      paramDfeApi.removeFromLibrary(Lists.newArrayList(arrayOfString2), "u-wl", local1, local2);
      label132:
      if (bool2) {
        break label225;
      }
    }
    for (;;)
    {
      invokeWishlistStatusListeners(str1, bool1, false);
      if (!bool3) {
        break;
      }
      new Handler(Looper.myLooper()).post(new Runnable()
      {
        public final void run()
        {
          Context localContext1 = this.val$context;
          Context localContext2 = this.val$context;
          if (bool2) {}
          for (int i = 2131362936;; i = 2131362933)
          {
            UiUtils.sendAccessibilityEventWithText(localContext1, localContext2.getString(i), paramView);
            return;
          }
        }
      });
      return;
      if (!bool3) {
        Toast.makeText(localContext, 2131362933, 0).show();
      }
      String[] arrayOfString1 = new String[bool1];
      arrayOfString1[0] = str1;
      paramDfeApi.addToLibrary(Lists.newArrayList(arrayOfString1), "u-wl", local1, local2);
      break label132;
      label225:
      bool1 = false;
    }
  }
  
  public static void removeWishlistStatusListener(WishlistStatusListener paramWishlistStatusListener)
  {
    sWishlistStatusListeners.remove(paramWishlistStatusListener);
  }
  
  public static boolean shouldHideWishlistAction(Document paramDocument, DfeApi paramDfeApi)
  {
    int i = 1;
    if (isInWishlist(paramDocument, FinskyApp.get().getCurrentAccount())) {
      i = 0;
    }
    for (;;)
    {
      return i;
      if ((!paramDocument.isPreregistration()) && (!paramDocument.hasDealOfTheDayInfo()) && (paramDocument.mDocument.docType != 20) && (paramDocument.mDocument.docType != 30) && (paramDocument.mDocument.docType != 8) && (paramDocument.mDocument.docType != 34) && (paramDocument.mDocument.docType != 44) && (paramDocument.mDocument.docType != 3))
      {
        Libraries localLibraries = FinskyApp.get().mLibraries;
        Account localAccount = LibraryUtils.getOwnerWithCurrentAccount(paramDocument, localLibraries, paramDfeApi.getAccount());
        if ((localAccount == null) && (paramDocument.mDocument.backendId == 6) && (paramDocument.hasSubscriptions())) {
          localAccount = LibraryUtils.getOwnerWithCurrentAccount(paramDocument.getSubscriptionsList(), localLibraries, FinskyApp.get().getCurrentAccount());
        }
        int j = paramDocument.mDocument.docType;
        int k = 0;
        if (j == i)
        {
          String str = paramDocument.getAppDetails().packageName;
          if (FinskyApp.get().mPackageStateRepository.get(str) == null) {
            break label222;
          }
        }
        label222:
        for (k = i; (localAccount == null) && (k == 0); k = 0) {
          return false;
        }
      }
    }
  }
  
  public static abstract interface WishlistStatusListener
  {
    public abstract void onWishlistStatusChanged(String paramString, boolean paramBoolean1, boolean paramBoolean2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.WishlistHelper
 * JD-Core Version:    0.7.0.1
 */