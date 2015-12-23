package com.google.android.finsky.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObservable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.ReviewFeedbackListener;
import com.google.android.finsky.activities.ReviewFeedbackListener.ReviewFeedbackRating;
import com.google.android.finsky.activities.ReviewsActivity;
import com.google.android.finsky.api.model.DfeReviews;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.PaginatedList;
import com.google.android.finsky.layout.HistogramView;
import com.google.android.finsky.layout.ReviewItemLayout;
import com.google.android.finsky.layout.ReviewItemLayout.ReviewFeedbackActionListener;
import com.google.android.finsky.layout.ReviewsControlContainer;
import com.google.android.finsky.layout.ReviewsControlContainer.1;
import com.google.android.finsky.layout.ReviewsControlContainer.2;
import com.google.android.finsky.layout.ReviewsTipHeaderLayout;
import com.google.android.finsky.layout.RottenTomatoesMeter;
import com.google.android.finsky.layout.RottenTomatoesReviewItem;
import com.google.android.finsky.layout.RottenTomatoesReviewItem.1;
import com.google.android.finsky.layout.RottenTomatoesReviewsHeader;
import com.google.android.finsky.layout.RottenTomatoesReviewsHeader.1;
import com.google.android.finsky.layout.play.PlayRecyclerView.ViewHolder;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.CriticReviewsResponse;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Review;
import com.google.android.finsky.protos.ReviewTip;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ReviewsSortingUtils;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public final class ReviewsAdapter
  extends PaginatedRecyclerViewAdapter
  implements Response.ErrorListener
{
  private final BitmapLoader mBitmapLoader;
  public final DfeReviews mData;
  final Document mDoc;
  private final boolean mIsRottenTomatoesReviews;
  final List<ListItem> mItems = new ArrayList();
  private final ChooseListingOptionsHandler mListingOptionsHandler;
  final ClientMutationCache mMutationCache;
  private final NavigationManager mNavigationManager;
  private final NumberFormat mNumberFormatter;
  private final PlayStoreUiElementNode mParentNode;
  final ReviewFeedbackHandler mReviewFeedbackHandler;
  private final ReviewFeedbackListener mReviewFeedbackListener;
  private final int mReviewTextMaxLines;
  private final DfeToc mToc;
  private final boolean mUseFeedbackV2;
  
  public ReviewsAdapter(Context paramContext, Document paramDocument, DfeReviews paramDfeReviews, boolean paramBoolean, DfeToc paramDfeToc, ReviewFeedbackHandler paramReviewFeedbackHandler, ChooseListingOptionsHandler paramChooseListingOptionsHandler, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, ReviewFeedbackListener paramReviewFeedbackListener, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super(paramContext, null, paramDfeReviews.inErrorState(), paramDfeReviews.mMoreAvailable);
    this.mDoc = paramDocument;
    this.mData = paramDfeReviews;
    this.mIsRottenTomatoesReviews = paramBoolean;
    this.mData.addDataChangedListener(this);
    this.mData.addErrorListener(this);
    this.mReviewTextMaxLines = 2147483647;
    this.mReviewFeedbackHandler = paramReviewFeedbackHandler;
    this.mListingOptionsHandler = paramChooseListingOptionsHandler;
    this.mNavigationManager = paramNavigationManager;
    this.mParentNode = paramPlayStoreUiElementNode;
    this.mUseFeedbackV2 = ReviewsActivity.shouldUseReviewFeedbackV2();
    this.mReviewFeedbackListener = paramReviewFeedbackListener;
    this.mMutationCache = FinskyApp.get().getClientMutationCache(FinskyApp.get().getCurrentAccountName());
    this.mNumberFormatter = NumberFormat.getIntegerInstance();
    this.mBitmapLoader = paramBitmapLoader;
    this.mToc = paramDfeToc;
    syncItems();
  }
  
  private boolean isReviewFeedbackHelpfulCached(Review paramReview)
  {
    return this.mMutationCache.isReviewFeedbackCached(this.mDoc.mDocument.docid, paramReview.commentId, ReviewFeedbackListener.ReviewFeedbackRating.HELPFUL);
  }
  
  private boolean isReviewFeedbackSpamCached(Review paramReview)
  {
    return this.mMutationCache.isReviewFeedbackCached(this.mDoc.mDocument.docid, paramReview.commentId, ReviewFeedbackListener.ReviewFeedbackRating.SPAM);
  }
  
  private void syncItems()
  {
    this.mItems.clear();
    if (!this.mData.isReady()) {
      return;
    }
    int i;
    int j;
    label123:
    int k;
    label172:
    int i1;
    label251:
    int m;
    label271:
    Review localReview;
    if ((!tipHeaderVisible()) && (this.mDoc != null) && (this.mDoc.hasReviewHistogramData()) && (!this.mIsRottenTomatoesReviews))
    {
      i = 1;
      if (i != 0) {
        this.mItems.add(new ListItem(2130969082));
      }
      if ((tipHeaderVisible()) || (this.mDoc == null) || (this.mDoc.mDocument.docType != 1) || (this.mIsRottenTomatoesReviews) || (GooglePlayServicesUtil.isSidewinderDevice(this.mContext))) {
        break label337;
      }
      j = 1;
      if (j != 0) {
        this.mItems.add(new ListItem(2130969078));
      }
      if ((tipHeaderVisible()) || (!this.mIsRottenTomatoesReviews) || (this.mData.mRottenTomatoesReviewData == null)) {
        break label342;
      }
      k = 1;
      if (k != 0) {
        this.mItems.add(new ListItem(2130969087));
      }
      if (tipHeaderVisible()) {
        this.mItems.add(new ListItem(2130969084));
      }
      if (this.mData.getCount() == 0)
      {
        List localList = this.mItems;
        if (!this.mData.mMoreAvailable) {
          break label347;
        }
        i1 = 2130968826;
        localList.add(new ListItem(i1));
      }
      m = 0;
      if (m >= this.mData.getCount()) {
        break label387;
      }
      localReview = (Review)this.mData.getItem(m, false);
      if (!this.mIsRottenTomatoesReviews) {
        break label354;
      }
      this.mItems.add(new ListItem(2130969086, m));
    }
    for (;;)
    {
      m++;
      break label271;
      i = 0;
      break;
      label337:
      j = 0;
      break label123;
      label342:
      k = 0;
      break label172;
      label347:
      i1 = 2130969079;
      break label251;
      label354:
      if (!isReviewFeedbackSpamCached(localReview)) {
        this.mItems.add(new ListItem(2130969073, m));
      }
    }
    label387:
    int n = getFooterMode();
    if (n != 0)
    {
      if (n != 1) {
        break label431;
      }
      this.mItems.add(new ListItem(2130968826));
    }
    for (;;)
    {
      this.mObservable.notifyChanged();
      return;
      label431:
      if (n == 2)
      {
        this.mItems.add(new ListItem(2130968731));
      }
      else
      {
        FinskyLog.e("No footer or item in last row", new Object[0]);
        this.mItems.add(new ListItem(2130968731));
      }
    }
  }
  
  private boolean tipHeaderVisible()
  {
    return this.mData.mTip != null;
  }
  
  public final int getItemCount()
  {
    return this.mItems.size();
  }
  
  public final int getItemViewType(int paramInt)
  {
    return ((ListItem)this.mItems.get(paramInt)).viewType;
  }
  
  protected final String getLastRequestError()
  {
    return ErrorStrings.get(this.mContext, this.mData.getVolleyError());
  }
  
  protected final boolean isMoreDataAvailable()
  {
    return this.mData.mMoreAvailable;
  }
  
  public final void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    View localView = paramViewHolder.itemView;
    int i = paramViewHolder.mItemViewType;
    switch (i)
    {
    default: 
      throw new IllegalStateException("Unknown type for onBindViewHolder " + i);
    case 2130969082: 
      HistogramView localHistogramView = (HistogramView)localView;
      if (!this.mDoc.hasReviewHistogramData())
      {
        FinskyLog.w("No histogram data received from server", new Object[0]);
        localHistogramView.setVisibility(8);
      }
      localHistogramView.bind(this.mDoc.getRatingCount(), this.mDoc.getStarRating(), this.mDoc.getRatingHistogram());
    case 2130969079: 
      return;
    case 2130969078: 
      ReviewsControlContainer localReviewsControlContainer = (ReviewsControlContainer)localView;
      DfeReviews localDfeReviews = this.mData;
      ChooseListingOptionsHandler localChooseListingOptionsHandler = this.mListingOptionsHandler;
      localReviewsControlContainer.mSortBox.setText(ReviewsSortingUtils.getDisplayString(localReviewsControlContainer.getContext(), localDfeReviews.mSortType));
      localReviewsControlContainer.mSortBox.setOnClickListener(new ReviewsControlContainer.1(localReviewsControlContainer, localChooseListingOptionsHandler));
      localReviewsControlContainer.mFilterBox.setOnClickListener(new ReviewsControlContainer.2(localReviewsControlContainer, localChooseListingOptionsHandler));
      return;
    case 2130969087: 
      RottenTomatoesReviewsHeader localRottenTomatoesReviewsHeader = (RottenTomatoesReviewsHeader)localView;
      CriticReviewsResponse localCriticReviewsResponse = this.mData.mRottenTomatoesReviewData;
      NavigationManager localNavigationManager = this.mNavigationManager;
      DfeToc localDfeToc = this.mToc;
      BitmapLoader localBitmapLoader2 = this.mBitmapLoader;
      localRottenTomatoesReviewsHeader.mTitle.setText(localCriticReviewsResponse.title.toUpperCase());
      localRottenTomatoesReviewsHeader.mSentimentImage.setImage(localCriticReviewsResponse.aggregateSentiment.imageUrl, localCriticReviewsResponse.aggregateSentiment.supportsFifeUrlOptions, localBitmapLoader2);
      localRottenTomatoesReviewsHeader.mFavorablePercentValue.setText(Integer.toString(localCriticReviewsResponse.percentFavorable));
      if (localCriticReviewsResponse.hasTotalNumReviews)
      {
        TextView localTextView = localRottenTomatoesReviewsHeader.mReviewsCount;
        Resources localResources2 = localRottenTomatoesReviewsHeader.getResources();
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(localCriticReviewsResponse.totalNumReviews);
        localTextView.setText(Html.fromHtml(localResources2.getString(2131362700, arrayOfObject2)));
        localRottenTomatoesReviewsHeader.mReviewsCount.setVisibility(0);
      }
      for (;;)
      {
        localRottenTomatoesReviewsHeader.mFavorablePercentBar.setPercentValue(localCriticReviewsResponse.percentFavorable);
        localRottenTomatoesReviewsHeader.mSource.setText(localCriticReviewsResponse.sourceText);
        if (localCriticReviewsResponse.source == null) {
          break;
        }
        localRottenTomatoesReviewsHeader.mSource.setOnClickListener(new RottenTomatoesReviewsHeader.1(localRottenTomatoesReviewsHeader, localNavigationManager, localCriticReviewsResponse, localDfeToc));
        return;
        localRottenTomatoesReviewsHeader.mReviewsCount.setVisibility(8);
      }
      localRottenTomatoesReviewsHeader.mSource.setOnClickListener(null);
      return;
    case 2130969073: 
      ReviewItemLayout localReviewItemLayout = (ReviewItemLayout)localView;
      final ListItem localListItem = (ListItem)this.mItems.get(paramInt);
      final Review localReview2 = (Review)this.mData.getItem(localListItem.reviewPosition);
      if (!TextUtils.isEmpty(localReview2.commentId)) {}
      for (int k = 1;; k = 0)
      {
        boolean bool2 = isReviewFeedbackHelpfulCached(localReview2);
        boolean bool3 = isReviewFeedbackSpamCached(localReview2);
        localReviewItemLayout.setReviewInfo(this.mDoc, localReview2, this.mReviewTextMaxLines, this.mNavigationManager, false, this.mUseFeedbackV2, bool2, bool3, this.mParentNode);
        if (k == 0) {
          break label651;
        }
        if (!this.mUseFeedbackV2) {
          break;
        }
        localReviewItemLayout.setReviewFeedbackActionListener(new ReviewItemLayout.ReviewFeedbackActionListener()
        {
          public final void onMarkAsHelpfulClick(ReviewItemLayout paramAnonymousReviewItemLayout)
          {
            ReviewsAdapter localReviewsAdapter = ReviewsAdapter.this;
            Review localReview = localReview2;
            localReviewsAdapter.updateReviewItem(paramAnonymousReviewItemLayout, ReviewFeedbackListener.ReviewFeedbackRating.HELPFUL, localReview);
            Toast.makeText(localReviewsAdapter.mContext, 2131362688, 0).show();
          }
          
          public final void onMarkAsSpamClick(ReviewItemLayout paramAnonymousReviewItemLayout)
          {
            ReviewsAdapter localReviewsAdapter = ReviewsAdapter.this;
            ReviewsAdapter.ListItem localListItem = localListItem;
            Review localReview = (Review)localReviewsAdapter.mData.getItem(localListItem.reviewPosition);
            int i = localReviewsAdapter.mItems.indexOf(localListItem);
            localReviewsAdapter.updateReviewItem(paramAnonymousReviewItemLayout, ReviewFeedbackListener.ReviewFeedbackRating.SPAM, localReview);
            localReviewsAdapter.mItems.remove(i);
            Snackbar.make$349db449(paramAnonymousReviewItemLayout).setAction$6ff2c59b(new ReviewsAdapter.3(localReviewsAdapter, localReview, i, localListItem)).show();
            localReviewsAdapter.notifyItemRemoved(i);
          }
        });
        return;
      }
      localReviewItemLayout.setActionClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          ReviewsAdapter.this.mReviewFeedbackHandler.onReviewFeedback(localReview2);
        }
      });
      return;
      localReviewItemLayout.setActionClickListener(null);
      return;
    case 2130969086: 
      RottenTomatoesReviewItem localRottenTomatoesReviewItem = (RottenTomatoesReviewItem)localView;
      Review localReview1 = (Review)this.mData.getItem(((ListItem)this.mItems.get(paramInt)).reviewPosition);
      BitmapLoader localBitmapLoader1 = this.mBitmapLoader;
      localRottenTomatoesReviewItem.mSentimentImage.setImage(localReview1.sentiment.imageUrl, localReview1.sentiment.supportsFifeUrlOptions, localBitmapLoader1);
      if (TextUtils.isEmpty(localReview1.url)) {
        localRottenTomatoesReviewItem.mExternalLinkAction.setVisibility(4);
      }
      for (;;)
      {
        localRottenTomatoesReviewItem.mComment.setText(localReview1.comment);
        localRottenTomatoesReviewItem.mAuthor.setText(localReview1.authorName);
        localRottenTomatoesReviewItem.mSource.setText(localReview1.source);
        return;
        localRottenTomatoesReviewItem.mExternalLinkAction.setVisibility(0);
        localRottenTomatoesReviewItem.mExternalLinkAction.setOnClickListener(new RottenTomatoesReviewItem.1(localRottenTomatoesReviewItem, localReview1));
      }
    case 2130968826: 
      bindLoadingFooterView(localView);
      return;
    case 2130968731: 
      label651:
      bindErrorFooterView(localView);
      return;
    }
    ReviewsTipHeaderLayout localReviewsTipHeaderLayout = (ReviewsTipHeaderLayout)localView;
    ReviewTip localReviewTip = this.mData.mTip;
    boolean bool1 = localReviewTip.hasReviewCount;
    String str = null;
    if (bool1)
    {
      Resources localResources1 = this.mContext.getResources();
      int j = (int)localReviewTip.reviewCount;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = this.mNumberFormatter.format(localReviewTip.reviewCount);
      str = localResources1.getQuantityString(2131296273, j, arrayOfObject1);
    }
    localReviewsTipHeaderLayout.setTipInfo(localReviewTip.text, str);
  }
  
  public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    View localView;
    if (paramInt == 2130968826) {
      localView = createLoadingFooterView(paramViewGroup);
    }
    for (;;)
    {
      return new PlayRecyclerView.ViewHolder(localView);
      if (paramInt == 2130968731) {
        localView = createErrorFooterView(paramViewGroup);
      } else {
        localView = inflate(paramInt, paramViewGroup, false);
      }
    }
  }
  
  public final void onDataChanged()
  {
    super.onDataChanged();
    syncItems();
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    triggerFooterErrorMode();
  }
  
  protected final void retryLoadingItems()
  {
    this.mData.retryLoadItems();
  }
  
  final void updateReviewItem(ReviewItemLayout paramReviewItemLayout, ReviewFeedbackListener.ReviewFeedbackRating paramReviewFeedbackRating, Review paramReview)
  {
    if (this.mReviewFeedbackListener != null) {
      this.mReviewFeedbackListener.onReviewFeedback(this.mDoc.mDocument.docid, paramReview.commentId, paramReviewFeedbackRating);
    }
    if (this.mMutationCache.isReviewFeedbackCached(this.mDoc.mDocument.docid, paramReview.commentId, paramReviewFeedbackRating)) {
      this.mMutationCache.removeReviewFeedback(this.mDoc.mDocument.docid, paramReview.commentId, paramReviewFeedbackRating);
    }
    for (;;)
    {
      paramReviewItemLayout.setReviewInfo(this.mDoc, paramReview, this.mReviewTextMaxLines, this.mNavigationManager, false, this.mUseFeedbackV2, isReviewFeedbackHelpfulCached(paramReview), isReviewFeedbackSpamCached(paramReview), this.mParentNode);
      return;
      this.mMutationCache.addReviewFeedback(this.mDoc.mDocument.docid, paramReview.commentId, paramReviewFeedbackRating);
    }
  }
  
  public static abstract interface ChooseListingOptionsHandler
  {
    public abstract void onChooseFilterOptions();
    
    public abstract void onChooseSortingOptions();
  }
  
  private static final class ListItem
  {
    final int reviewPosition;
    final int viewType;
    
    ListItem(int paramInt)
    {
      this(paramInt, -1);
    }
    
    ListItem(int paramInt1, int paramInt2)
    {
      this.viewType = paramInt1;
      this.reviewPosition = paramInt2;
    }
  }
  
  public static abstract interface ReviewFeedbackHandler
  {
    public abstract void onReviewFeedback(Review paramReview);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.ReviewsAdapter
 * JD-Core Version:    0.7.0.1
 */