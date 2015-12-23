package com.google.android.finsky.detailspage;

import android.accounts.Account;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.SubscriptionView;
import com.google.android.finsky.layout.SubscriptionView.1;
import com.google.android.finsky.layout.SubscriptionView.2;
import com.google.android.finsky.layout.SubscriptionView.CancelListener;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.Libraries.Listener;
import com.google.android.finsky.library.LibraryEntry;
import com.google.android.finsky.library.LibraryInAppSubscriptionEntry;
import com.google.android.finsky.library.LibrarySubscriptionEntry;
import com.google.android.finsky.library.MusicLibrary;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.Common.SubscriptionTerms;
import com.google.android.finsky.protos.Details.BulkDetailsResponse;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.CancelSubscriptionDialog;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.DateUtils;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.ExpandableUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.SubscriptionDateInfo;
import com.google.android.play.layout.PlayActionButton;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SubscriptionsModule
  extends FinskyModule<Data>
  implements SubscriptionView.CancelListener, Libraries.Listener
{
  private boolean mDestroyed;
  private boolean mNeedsRefresh;
  
  private static void addAppSubscriptionsToMap(Document paramDocument, AccountLibrary paramAccountLibrary, Map<String, LibrarySubscriptionEntry> paramMap)
  {
    Iterator localIterator = paramAccountLibrary.getSubscriptionPurchasesForPackage(paramDocument.mDocument.backendDocid).iterator();
    while (localIterator.hasNext())
    {
      LibraryInAppSubscriptionEntry localLibraryInAppSubscriptionEntry = (LibraryInAppSubscriptionEntry)localIterator.next();
      paramMap.put(localLibraryInAppSubscriptionEntry.mDocId, localLibraryInAppSubscriptionEntry);
    }
  }
  
  private void fetchSubscriptionDocs(Collection<String> paramCollection, final Map<String, LibrarySubscriptionEntry> paramMap)
  {
    if (paramCollection.isEmpty())
    {
      if (FinskyLog.DEBUG) {
        FinskyLog.v("No active subscriptions.", new Object[0]);
      }
      return;
    }
    this.mDfeApi.getBulkDetails(paramCollection, false, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        FinskyLog.e("Could not retrieve subscription docs: %s", new Object[] { paramAnonymousVolleyError });
      }
    });
  }
  
  private void fetchSubscriptions()
  {
    Document localDocument1 = ((Data)this.mModuleData).detailsDoc;
    if (localDocument1.mDocument.docType == 1) {
      if ("com.google.android.music".equals(localDocument1.mDocument.backendDocid))
      {
        ArrayList localArrayList = new ArrayList();
        HashMap localHashMap1 = new HashMap();
        Iterator localIterator2 = ((MusicLibrary)this.mLibraries.getAccountLibrary(this.mDfeApi.getAccount()).getLibrary(AccountLibrary.getLibraryIdFromBackend(2))).getSubscriptionsList().iterator();
        while (localIterator2.hasNext())
        {
          LibrarySubscriptionEntry localLibrarySubscriptionEntry2 = (LibrarySubscriptionEntry)localIterator2.next();
          String str = localLibrarySubscriptionEntry2.mDocId;
          localHashMap1.put(str, localLibrarySubscriptionEntry2);
          localArrayList.add(DocUtils.getMusicSubscriptionDocid(localLibrarySubscriptionEntry2.mDocType, str));
        }
        fetchSubscriptionDocs(localArrayList, localHashMap1);
      }
    }
    Document localDocument2;
    do
    {
      do
      {
        return;
        Document localDocument4 = ((Data)this.mModuleData).detailsDoc;
        HashMap localHashMap2 = new HashMap();
        AccountLibrary localAccountLibrary1 = this.mLibraries.getAccountLibrary(this.mDfeApi.getAccount());
        Iterator localIterator3 = this.mLibraries.getAccountLibraries().iterator();
        while (localIterator3.hasNext())
        {
          AccountLibrary localAccountLibrary2 = (AccountLibrary)localIterator3.next();
          if (localAccountLibrary2 != localAccountLibrary1) {
            addAppSubscriptionsToMap(localDocument4, localAccountLibrary2, localHashMap2);
          }
        }
        addAppSubscriptionsToMap(localDocument4, localAccountLibrary1, localHashMap2);
        fetchSubscriptionDocs(localHashMap2.keySet(), localHashMap2);
        return;
      } while (localDocument1.mDocument.backendId != 6);
      localDocument2 = ((Data)this.mModuleData).detailsDoc;
      ((Data)this.mModuleData).subscriptionDocuments.clear();
      ((Data)this.mModuleData).subscriptionEntries.clear();
    } while (!localDocument2.hasSubscriptions());
    Account localAccount = this.mDfeApi.getAccount();
    Iterator localIterator1 = localDocument2.getSubscriptionsList().iterator();
    while (localIterator1.hasNext())
    {
      Document localDocument3 = (Document)localIterator1.next();
      LibrarySubscriptionEntry localLibrarySubscriptionEntry1 = this.mLibraries.getAccountLibrary(localAccount).getMagazinesSubscriptionEntry(localDocument3.mDocument.backendDocid);
      if (localLibrarySubscriptionEntry1 != null)
      {
        ((Data)this.mModuleData).subscriptionDocuments.add(localDocument3);
        ((Data)this.mModuleData).subscriptionEntries.add(localLibrarySubscriptionEntry1);
      }
    }
    ((Data)this.mModuleData).hasFinishedFetchingSubscriptions = true;
    refreshView();
  }
  
  private void refreshView()
  {
    if (readyForDisplay())
    {
      this.mNeedsRefresh = true;
      this.mFinskyModuleController.refreshModule(this, true);
      return;
    }
    this.mFinskyModuleController.removeModule(this);
  }
  
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    if (this.mModuleData == null)
    {
      this.mModuleData = new Data();
      ((Data)this.mModuleData).detailsDoc = paramDocument1;
      ((Data)this.mModuleData).savedInstanceState = new Bundle();
      ((Data)this.mModuleData).subscriptionDocuments = new ArrayList();
      ((Data)this.mModuleData).subscriptionEntries = new ArrayList();
      this.mLibraries.addListener(this);
      fetchSubscriptions();
    }
  }
  
  public final void bindView(View paramView)
  {
    SubscriptionsModuleLayout localSubscriptionsModuleLayout = (SubscriptionsModuleLayout)paramView;
    if ((!localSubscriptionsModuleLayout.mHasBinded) || (this.mNeedsRefresh))
    {
      List localList1 = ((Data)this.mModuleData).subscriptionDocuments;
      List localList2 = ((Data)this.mModuleData).subscriptionEntries;
      int i = ((Data)this.mModuleData).detailsDoc.mDocument.backendId;
      Bundle localBundle = ((Data)this.mModuleData).savedInstanceState;
      PlayStoreUiElementNode localPlayStoreUiElementNode = this.mParentNode;
      localSubscriptionsModuleLayout.mHasBinded = true;
      localSubscriptionsModuleLayout.removeAllViews();
      int j = 0;
      if (j < localList1.size())
      {
        Document localDocument = (Document)localList1.get(j);
        LibrarySubscriptionEntry localLibrarySubscriptionEntry = (LibrarySubscriptionEntry)localList2.get(j);
        SubscriptionView localSubscriptionView = (SubscriptionView)localSubscriptionsModuleLayout.mLayoutInflater.inflate(2130969123, localSubscriptionsModuleLayout, false);
        localSubscriptionView.mDocument = localDocument;
        localSubscriptionView.mCancelListener = this;
        localSubscriptionView.mSubscriptionTitle.setText(localSubscriptionView.mDocument.mDocument.title);
        Common.Offer localOffer = localDocument.getOffer(1);
        if ((localOffer != null) && (localOffer.subscriptionTerms != null))
        {
          String str2 = localOffer.subscriptionTerms.formattedPriceWithRecurrencePeriod;
          if (!TextUtils.isEmpty(str2))
          {
            localSubscriptionView.mSubscriptionPrice.setVisibility(0);
            localSubscriptionView.mSubscriptionPrice.setText(str2);
          }
        }
        SubscriptionDateInfo localSubscriptionDateInfo;
        Resources localResources;
        String str1;
        for (;;)
        {
          localSubscriptionView.mSubscriptionStatus.setTextColor(CorpusResourceUtils.getSecondaryTextColor(localSubscriptionView.getContext(), i));
          localSubscriptionDateInfo = localSubscriptionView.mDateInfo;
          localResources = localSubscriptionView.getContext().getResources();
          str1 = DateUtils.formatShortDisplayDate(localLibrarySubscriptionEntry.mValidUntilTimestampMs);
          int k = localLibrarySubscriptionEntry.getCurrentSubscriptionState();
          switch (k)
          {
          default: 
            throw new IllegalStateException("Unknown subscription state: " + k);
            localSubscriptionView.mSubscriptionPrice.setVisibility(8);
            Object[] arrayOfObject2 = new Object[1];
            arrayOfObject2[0] = localDocument.mDocument.docid;
            FinskyLog.wtf("Document for %s does not contain a formatted price.", arrayOfObject2);
            continue;
            localSubscriptionView.mSubscriptionPrice.setVisibility(8);
            Object[] arrayOfObject1 = new Object[1];
            arrayOfObject1[0] = localDocument.mDocument.docid;
            FinskyLog.wtf("Document for %s does not contain a subscription offer or terms.", arrayOfObject1);
          }
        }
        localSubscriptionDateInfo.cancelable = false;
        localSubscriptionDateInfo.statusResourceId = 2131362773;
        localSubscriptionDateInfo.renewalDescription = Html.fromHtml(localResources.getString(2131362775, new Object[] { str1 }));
        label467:
        if (TextUtils.isEmpty(localSubscriptionView.mDateInfo.renewalDescription))
        {
          localSubscriptionView.mSubscriptionRenewal.setVisibility(8);
          label491:
          localSubscriptionView.mSubscriptionStatus.setText(localSubscriptionView.mDateInfo.statusResourceId);
          if (!localSubscriptionView.mDateInfo.cancelable) {
            break label786;
          }
          localSubscriptionView.mCancelButton.setVisibility(0);
          localSubscriptionView.mCancelButton.configure(i, 2131361919, new SubscriptionView.1(localSubscriptionView, localDocument, localPlayStoreUiElementNode, localLibrarySubscriptionEntry));
          localSubscriptionView.setNextFocusRightId(localSubscriptionView.mCancelButton.getId());
          localSubscriptionView.mCancelButton.setNextFocusLeftId(localSubscriptionView.getId());
        }
        for (;;)
        {
          if (!TextUtils.isEmpty(localDocument.getDescription())) {
            break label805;
          }
          localSubscriptionView.mSubscriptionDescription.setVisibility(8);
          localSubscriptionView.mDescriptionCollapser.setVisibility(8);
          localSubscriptionView.mDescriptionExpander.setVisibility(8);
          localSubscriptionsModuleLayout.addView(localSubscriptionView);
          j++;
          break;
          localSubscriptionDateInfo.cancelable = true;
          localSubscriptionDateInfo.statusResourceId = 2131362778;
          localSubscriptionDateInfo.renewalDescription = Html.fromHtml(localResources.getString(2131362774, new Object[] { DateUtils.formatShortDisplayDate(localLibrarySubscriptionEntry.trialUntilTimestampMs) }));
          break label467;
          localSubscriptionDateInfo.cancelable = true;
          localSubscriptionDateInfo.statusResourceId = 2131362776;
          localSubscriptionDateInfo.renewalDescription = Html.fromHtml(localResources.getString(2131362777, new Object[] { str1 }));
          break label467;
          localSubscriptionDateInfo.cancelable = true;
          localSubscriptionDateInfo.statusResourceId = 2131362776;
          localSubscriptionDateInfo.renewalDescription = null;
          break label467;
          throw new UnsupportedOperationException("Unsupported subscription state: grace period");
          localSubscriptionView.mSubscriptionRenewal.setVisibility(0);
          localSubscriptionView.mSubscriptionRenewal.setText(localSubscriptionView.mDateInfo.renewalDescription);
          break label491;
          label786:
          localSubscriptionView.mCancelButton.setVisibility(8);
          localSubscriptionView.setNextFocusRightId(-1);
        }
        label805:
        localSubscriptionView.mSubscriptionDescription.setText(localDocument.getDescription());
        if (localSubscriptionView.mExpansionState < 0) {
          localSubscriptionView.mExpansionState = ExpandableUtils.getSavedExpansionState$1c580cd(localBundle, localDocument.mDocument.docid);
        }
        if (localSubscriptionView.mExpansionState == 2) {
          localSubscriptionView.expandDescription();
        }
        for (;;)
        {
          localSubscriptionView.setOnClickListener(new SubscriptionView.2(localSubscriptionView));
          break;
          localSubscriptionView.collapseDescription();
        }
      }
      ((Data)this.mModuleData).savedInstanceState.clear();
      this.mNeedsRefresh = false;
    }
  }
  
  public final int getLayoutResId()
  {
    return 2130969126;
  }
  
  public final void onAllLibrariesLoaded() {}
  
  public final void onCancel(Document paramDocument, LibrarySubscriptionEntry paramLibrarySubscriptionEntry)
  {
    CancelSubscriptionDialog.show(this.mContainerFragment.mFragmentManager, paramDocument, paramLibrarySubscriptionEntry);
  }
  
  public final void onDestroyModule()
  {
    this.mLibraries.removeListener(this);
    this.mDestroyed = true;
  }
  
  public final void onLibraryContentsChanged$40bdb4b1()
  {
    fetchSubscriptions();
  }
  
  public final void onRecycleView(View paramView)
  {
    SubscriptionsModuleLayout localSubscriptionsModuleLayout = (SubscriptionsModuleLayout)paramView;
    Bundle localBundle = ((Data)this.mModuleData).savedInstanceState;
    for (int i = 0; i < localSubscriptionsModuleLayout.getChildCount(); i++)
    {
      View localView = localSubscriptionsModuleLayout.getChildAt(i);
      if ((localView instanceof SubscriptionView))
      {
        SubscriptionView localSubscriptionView = (SubscriptionView)localView;
        String str = localSubscriptionView.mDocument.mDocument.docid;
        if (!TextUtils.isEmpty(str)) {
          ExpandableUtils.saveExpansionState(localBundle, str, localSubscriptionView.mExpansionState);
        }
      }
    }
  }
  
  public final void onRestoreModuleData(FinskyModule.ModuleData paramModuleData)
  {
    super.onRestoreModuleData(paramModuleData);
    if (this.mModuleData != null)
    {
      this.mLibraries.addListener(this);
      if (!((Data)this.mModuleData).hasFinishedFetchingSubscriptions) {
        fetchSubscriptions();
      }
    }
  }
  
  public final boolean readyForDisplay()
  {
    return (this.mModuleData != null) && (((Data)this.mModuleData).hasFinishedFetchingSubscriptions) && (!((Data)this.mModuleData).subscriptionDocuments.isEmpty()) && (!((Data)this.mModuleData).subscriptionEntries.isEmpty());
  }
  
  protected static final class Data
    extends FinskyModule.ModuleData
  {
    Document detailsDoc;
    boolean hasFinishedFetchingSubscriptions;
    Bundle savedInstanceState;
    List<Document> subscriptionDocuments;
    List<LibrarySubscriptionEntry> subscriptionEntries;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.SubscriptionsModule
 * JD-Core Version:    0.7.0.1
 */