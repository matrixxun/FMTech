package com.google.android.finsky.activities.myapps;

import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.RecyclerListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;
import com.google.android.finsky.layout.play.PlayCardViewMyApps;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.LibraryEntry;
import com.google.android.finsky.library.LibraryInAppSubscriptionEntry;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.Common.SubscriptionTerms;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.DateUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.PlayCardUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.PlayCardLabelView;
import com.google.android.play.layout.PlayCardViewBase;
import com.google.android.play.layout.PlayTextView;
import com.google.android.play.layout.StarRatingBar;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class MyAppsSubscriptionsAdapter
  extends BaseAdapter
  implements AbsListView.RecyclerListener, MyAppsListAdapter
{
  private static final Collator sSubscriptionAbcCollator = ;
  static final Comparator<MyAppsSubscriptionEntry> sSubscriptionAbcSorter = new Comparator() {};
  private final BitmapLoader mBitmapLoader;
  private final Context mContext;
  private final LayoutInflater mInflater;
  private final int mLeadingSpacerHeight;
  private final View.OnClickListener mListener;
  private PlayStoreUiElementNode mParentNode = null;
  final List<MyAppsSubscriptionEntry> mSubscriptions = new ArrayList();
  
  public MyAppsSubscriptionsAdapter(Context paramContext, LayoutInflater paramLayoutInflater, BitmapLoader paramBitmapLoader, View.OnClickListener paramOnClickListener, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mContext = paramContext;
    this.mInflater = paramLayoutInflater;
    this.mBitmapLoader = paramBitmapLoader;
    this.mListener = paramOnClickListener;
    this.mParentNode = paramPlayStoreUiElementNode;
    this.mLeadingSpacerHeight = FinskyHeaderListLayout.getMinimumHeaderHeight$3047fd86(this.mContext, 0);
  }
  
  public final int getCount()
  {
    return 1 + this.mSubscriptions.size();
  }
  
  public final Document getDocument(int paramInt)
  {
    if (paramInt == 0) {
      return null;
    }
    return ((MyAppsSubscriptionEntry)this.mSubscriptions.get(paramInt - 1)).parentDoc;
  }
  
  public final Object getItem(int paramInt)
  {
    if (paramInt == 0) {
      return null;
    }
    return ((MyAppsSubscriptionEntry)this.mSubscriptions.get(paramInt - 1)).subscriptionDoc;
  }
  
  public final long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public final int getItemViewType(int paramInt)
  {
    if (paramInt == 0) {
      return 1;
    }
    return 0;
  }
  
  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    switch (getItemViewType(paramInt))
    {
    default: 
      throw new IllegalStateException("Unknown type for getView " + paramInt);
    case 1: 
      if (paramView == null) {
        paramView = this.mInflater.inflate(2130968994, paramViewGroup, false);
      }
      paramView.getLayoutParams().height = this.mLeadingSpacerHeight;
      paramView.setId(2131755059);
      return paramView;
    }
    if (paramView == null) {}
    for (View localView = this.mInflater.inflate(2130968941, paramViewGroup, false);; localView = paramView)
    {
      MyAppsSubscriptionEntry localMyAppsSubscriptionEntry = (MyAppsSubscriptionEntry)this.mSubscriptions.get(paramInt - 1);
      LibraryInAppSubscriptionEntry localLibraryInAppSubscriptionEntry = localMyAppsSubscriptionEntry.subscriptionOwnership;
      Document localDocument1 = localMyAppsSubscriptionEntry.subscriptionDoc;
      Document localDocument2 = localMyAppsSubscriptionEntry.parentDoc;
      Resources localResources = this.mContext.getResources();
      PlayCardViewMyApps localPlayCardViewMyApps = (PlayCardViewMyApps)localView;
      PlayCardUtils.bindCard(localPlayCardViewMyApps, localDocument2, "my_apps:subscription", this.mBitmapLoader, null, this.mParentNode);
      localPlayCardViewMyApps.setArchivable(false, null);
      localPlayCardViewMyApps.getTitle().setText(localDocument1.mDocument.title);
      localPlayCardViewMyApps.getSubtitle().setText(localDocument2.mDocument.title);
      PlayCardLabelView localPlayCardLabelView = localPlayCardViewMyApps.getLabel();
      PlayTextView localPlayTextView = localPlayCardViewMyApps.getItemBadge();
      int i = localDocument1.mDocument.backendId;
      if (localLibraryInAppSubscriptionEntry.isAutoRenewing) {
        if (System.currentTimeMillis() < localLibraryInAppSubscriptionEntry.trialUntilTimestampMs)
        {
          localPlayCardLabelView.setText(2131362778, i);
          if (localPlayTextView != null)
          {
            Common.Offer localOffer = localDocument1.getOffer(1);
            if ((localOffer == null) || (localOffer.subscriptionTerms == null)) {
              break label416;
            }
            String str = localOffer.subscriptionTerms.formattedPriceWithRecurrencePeriod;
            if (TextUtils.isEmpty(str)) {
              break label380;
            }
            localPlayTextView.setVisibility(0);
            localPlayTextView.setText(str);
          }
        }
      }
      for (;;)
      {
        localPlayCardViewMyApps.getRatingBar().setVisibility(8);
        localView.setTag(localDocument2);
        localView.setOnClickListener(this.mListener);
        localView.findViewById(2131755330).setVisibility(8);
        return localView;
        localPlayCardLabelView.setText(2131362776, i);
        break;
        label380:
        localPlayTextView.setVisibility(8);
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = localDocument1.mDocument.docid;
        FinskyLog.wtf("Document for %s does not contain a formatted price.", arrayOfObject2);
        continue;
        label416:
        localPlayTextView.setVisibility(8);
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = localDocument1.mDocument.docid;
        FinskyLog.wtf("Document for %s does not contain a subscription offer or terms.", arrayOfObject1);
        continue;
        localPlayCardLabelView.setText(2131362773, i);
        if (localPlayTextView != null)
        {
          localPlayTextView.setText(Html.fromHtml(localResources.getString(2131362775, new Object[] { DateUtils.formatShortDisplayDate(localLibraryInAppSubscriptionEntry.mValidUntilTimestampMs) })));
          localPlayTextView.setVisibility(0);
        }
      }
    }
  }
  
  public final int getViewTypeCount()
  {
    return 2;
  }
  
  public final void onMovedToScrapHeap(View paramView)
  {
    if ((paramView instanceof PlayCardViewBase)) {
      PlayCardUtils.recycleLoggingData((PlayCardViewBase)paramView);
    }
  }
  
  private final class MyAppsSubscriptionEntry
  {
    public final Document parentDoc;
    public final Document subscriptionDoc;
    public final LibraryInAppSubscriptionEntry subscriptionOwnership;
    
    public MyAppsSubscriptionEntry(Document paramDocument1, Document paramDocument2, LibraryInAppSubscriptionEntry paramLibraryInAppSubscriptionEntry)
    {
      this.subscriptionDoc = paramDocument1;
      this.parentDoc = paramDocument2;
      this.subscriptionOwnership = paramLibraryInAppSubscriptionEntry;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.myapps.MyAppsSubscriptionsAdapter
 * JD-Core Version:    0.7.0.1
 */