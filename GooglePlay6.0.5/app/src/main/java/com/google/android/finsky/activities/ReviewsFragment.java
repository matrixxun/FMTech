package com.google.android.finsky.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerImpl;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.adapters.ReviewsAdapter;
import com.google.android.finsky.adapters.ReviewsAdapter.ChooseListingOptionsHandler;
import com.google.android.finsky.adapters.ReviewsAdapter.ReviewFeedbackHandler;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.DfeRateReview;
import com.google.android.finsky.api.model.DfeReviews;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.play.PlayRecyclerView;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Review;
import com.google.android.finsky.utils.ClientMutationCache;

public final class ReviewsFragment
  extends PageFragment
  implements ReviewFeedbackListener, ReviewsFilterOptionsDialog.Listener, ReviewsSortingDialog.Listener, ReviewsAdapter.ChooseListingOptionsHandler, ReviewsAdapter.ReviewFeedbackHandler
{
  private ReviewsAdapter mAdapter;
  protected DfeDetails mDfeDetails;
  protected Document mDocument;
  private boolean mFilterByDevice;
  private boolean mFilterByVersion;
  private boolean mIsDestroyed;
  private boolean mIsRottenTomatoesReviews;
  private PlayRecyclerView mRecyclerView;
  private DfeReviews mReviewsData;
  private String mReviewsUrl;
  private PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(302);
  
  private boolean isDataReady()
  {
    return (this.mReviewsData != null) && (this.mReviewsData.isReady());
  }
  
  public static ReviewsFragment newInstance(Document paramDocument, String paramString, boolean paramBoolean)
  {
    ReviewsFragment localReviewsFragment = new ReviewsFragment();
    localReviewsFragment.setArgument("finsky.PageFragment.toc", FinskyApp.get().mToc);
    localReviewsFragment.setArgument("finsky.ReviewsFragment.document", paramDocument);
    if (paramString == null) {}
    for (String str = paramDocument.mDocument.reviewsUrl;; str = paramString)
    {
      localReviewsFragment.setArgument("finsky.ReviewsFragment.reviewsUrl", str);
      localReviewsFragment.setArgument("finsky.ReviewsFragment.isRottenTomatoesReviews", paramBoolean);
      return localReviewsFragment;
    }
  }
  
  protected final int getLayoutRes()
  {
    return 2130968780;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    if (this.mIsRottenTomatoesReviews) {}
    for (int i = -1;; i = 4)
    {
      this.mDfeDetails = new DfeDetails(this.mDfeApi, this.mDocument.mDocument.detailsUrl);
      if (this.mReviewsData == null)
      {
        this.mReviewsData = new DfeReviews(this.mDfeApi, this.mReviewsUrl, this.mDocument.getVersionCode(), true);
        this.mReviewsData.addDataChangedListener(this);
        this.mReviewsData.addErrorListener(this);
        this.mReviewsData.mSortType = i;
      }
      this.mReviewsData.setFilters(this.mFilterByVersion, this.mFilterByDevice);
      this.mAdapter = new ReviewsAdapter(getActivity(), this.mDocument, this.mReviewsData, this.mIsRottenTomatoesReviews, this.mDfeToc, this, this, this.mNavigationManager, this.mBitmapLoader, this, this);
      this.mRecyclerView.setAdapter(this.mAdapter);
      if (!isDataReady())
      {
        switchToLoading();
        this.mReviewsData.startLoadItems();
      }
      return;
    }
  }
  
  public final void onChooseFilterOptions()
  {
    if (this.mReviewsData == null) {}
    FragmentManagerImpl localFragmentManagerImpl;
    do
    {
      return;
      localFragmentManagerImpl = this.mFragmentManager;
    } while (localFragmentManagerImpl.findFragmentByTag("filter_options_dialog") != null);
    ReviewsFilterOptionsDialog localReviewsFilterOptionsDialog = ReviewsFilterOptionsDialog.newInstance(this.mReviewsData.mFilterByVersion, this.mReviewsData.mFilterByDevice);
    localReviewsFilterOptionsDialog.setTargetFragment(this, 0);
    localReviewsFilterOptionsDialog.show(localFragmentManagerImpl, "filter_options_dialog");
  }
  
  public final void onChooseSortingOptions()
  {
    if (this.mReviewsData == null) {}
    FragmentManagerImpl localFragmentManagerImpl;
    do
    {
      return;
      localFragmentManagerImpl = this.mFragmentManager;
    } while (localFragmentManagerImpl.findFragmentByTag("sorting_dialog") != null);
    ReviewsSortingDialog localReviewsSortingDialog = ReviewsSortingDialog.newInstance(this.mReviewsData);
    localReviewsSortingDialog.setTargetFragment(this, 0);
    localReviewsSortingDialog.show(localFragmentManagerImpl, "sorting_dialog");
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mDocument = ((Document)this.mArguments.getParcelable("finsky.ReviewsFragment.document"));
    this.mReviewsUrl = this.mArguments.getString("finsky.ReviewsFragment.reviewsUrl");
    this.mIsRottenTomatoesReviews = this.mArguments.getBoolean("finsky.ReviewsFragment.isRottenTomatoesReviews");
    setRetainInstance$1385ff();
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    this.mRecyclerView = ((PlayRecyclerView)this.mDataView.findViewById(2131755587));
    PlayRecyclerView localPlayRecyclerView = this.mRecyclerView;
    this.mRecyclerView.getContext();
    localPlayRecyclerView.setLayoutManager(new LinearLayoutManager());
    if (isDataReady()) {
      onDataChanged();
    }
    this.mIsDestroyed = false;
    return localView;
  }
  
  public final void onDataChanged()
  {
    FinskyEventLog.setServerLogCookie(this.mUiElementProto, this.mDocument.mDocument.serverLogsCookie);
    this.mReviewsData.removeDataChangedListener(this);
    this.mReviewsData.removeErrorListener(this);
    this.mReviewsData.mCurrentRequest = null;
    if (this.mRecyclerView != null) {
      this.mRecyclerView.setEmptyView(this.mDataView.findViewById(2131755730));
    }
    super.onDataChanged();
  }
  
  public final void onDestroyView()
  {
    if (this.mReviewsData != null)
    {
      this.mReviewsData.removeDataChangedListener(this);
      this.mReviewsData.removeErrorListener(this);
    }
    if (this.mAdapter != null)
    {
      ReviewsAdapter localReviewsAdapter = this.mAdapter;
      localReviewsAdapter.mData.removeDataChangedListener(localReviewsAdapter);
      localReviewsAdapter.mData.removeErrorListener(localReviewsAdapter);
    }
    this.mAdapter = null;
    this.mRecyclerView = null;
    this.mIsDestroyed = true;
    super.onDestroyView();
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    super.onErrorResponse(paramVolleyError);
    if ((this.mRecyclerView != null) && (this.mAdapter != null)) {
      this.mAdapter.triggerFooterErrorMode();
    }
  }
  
  public final void onReviewFeedback(Review paramReview)
  {
    FragmentManagerImpl localFragmentManagerImpl = this.mFragmentManager;
    if (localFragmentManagerImpl.findFragmentByTag("rate_review_dialog") != null) {
      return;
    }
    ReviewFeedbackDialog localReviewFeedbackDialog = ReviewFeedbackDialog.newInstance$47996a45(this.mDocument.mDocument.docid, paramReview);
    localReviewFeedbackDialog.setTargetFragment(this, 0);
    localReviewFeedbackDialog.show(localFragmentManagerImpl, "rate_review_dialog");
  }
  
  public final void onReviewFeedback(String paramString1, String paramString2, final ReviewFeedbackListener.ReviewFeedbackRating paramReviewFeedbackRating)
  {
    FragmentActivity localFragmentActivity = getActivity();
    if (localFragmentActivity != null) {
      localFragmentActivity.setResult(-1);
    }
    final boolean bool1 = ReviewsActivity.shouldUseReviewFeedbackV2();
    boolean bool2 = FinskyApp.get().getClientMutationCache(FinskyApp.get().getCurrentAccountName()).isReviewFeedbackCached(paramString1, paramString2, paramReviewFeedbackRating);
    if ((bool1) && (bool2)) {}
    DfeRateReview localDfeRateReview;
    do
    {
      return;
      localDfeRateReview = new DfeRateReview(FinskyApp.get().getDfeApi(null), paramString1, paramString2, paramReviewFeedbackRating.mRpcId);
      localDfeRateReview.addDataChangedListener(new OnDataChangedListener()
      {
        public final void onDataChanged()
        {
          if ((!bool1) && (paramReviewFeedbackRating == ReviewFeedbackListener.ReviewFeedbackRating.SPAM) && (!ReviewsFragment.this.mIsDestroyed)) {
            ReviewsFragment.access$100(ReviewsFragment.this);
          }
        }
      });
    } while (bool1);
    localDfeRateReview.addErrorListener(new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        Toast.makeText(ReviewsFragment.this.mContext, 2131362689, 0).show();
      }
    });
  }
  
  public final void onReviewFilterChanged(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mFilterByVersion = paramBoolean1;
    this.mFilterByDevice = paramBoolean2;
    this.mReviewsData.setFilters(paramBoolean1, paramBoolean2);
    this.mReviewsData.refetchReviews();
  }
  
  public final void onSortingChanged(int paramInt)
  {
    this.mReviewsData.mSortType = paramInt;
    this.mReviewsData.refetchReviews();
  }
  
  public final void rebindActionBar()
  {
    this.mPageFragmentHost.updateCurrentBackendId(this.mDocument.mDocument.backendId, false);
    this.mPageFragmentHost.updateActionBarTitle(this.mDocument.mDocument.title);
    this.mPageFragmentHost.switchToRegularActionBar();
  }
  
  protected final void rebindViews()
  {
    rebindActionBar();
  }
  
  protected final void requestData()
  {
    this.mReviewsData.startLoadItems();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.ReviewsFragment
 * JD-Core Version:    0.7.0.1
 */