package com.google.android.finsky.detailspage;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerImpl;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.ReviewFeedbackDialog;
import com.google.android.finsky.activities.ReviewFeedbackListener.ReviewFeedbackRating;
import com.google.android.finsky.activities.ReviewsActivity;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.DfeRateReview;
import com.google.android.finsky.api.model.DfeReviews;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Review;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.FinskyLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReviewsSamplesModule
  extends FinskyModule<Data>
  implements OnDataChangedListener, ReviewsSamplesModuleLayout.ReviewSamplesClickListener, PlayStoreUiElementNode
{
  private boolean mIsDestroyed;
  private final ClientMutationCache mMutationCache = FinskyApp.get().getClientMutationCache(FinskyApp.get().getCurrentAccountName());
  private boolean mNeedsRefresh;
  private PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(1210);
  
  private boolean isReviewFeedbackHelpful(String paramString, Review paramReview)
  {
    return this.mMutationCache.isReviewFeedbackCached(paramString, paramReview.commentId, ReviewFeedbackListener.ReviewFeedbackRating.HELPFUL);
  }
  
  private boolean isReviewFeedbackSpam(String paramString, Review paramReview)
  {
    return this.mMutationCache.isReviewFeedbackCached(paramString, paramReview.commentId, ReviewFeedbackListener.ReviewFeedbackRating.SPAM);
  }
  
  private void refreshModule(boolean paramBoolean)
  {
    updateReviewItems();
    this.mNeedsRefresh = true;
    this.mFinskyModuleController.refreshModule(this, paramBoolean);
  }
  
  private void undoFeedback(String paramString1, String paramString2, ReviewFeedbackListener.ReviewFeedbackRating paramReviewFeedbackRating)
  {
    this.mMutationCache.removeReviewFeedback(paramString1, paramString2, paramReviewFeedbackRating);
    if (paramReviewFeedbackRating == ReviewFeedbackListener.ReviewFeedbackRating.SPAM) {}
    for (boolean bool = true;; bool = false)
    {
      refreshModule(bool);
      return;
    }
  }
  
  private void updateReviewItems()
  {
    if (((Data)this.mModuleData).reviewItems == null)
    {
      Data localData = (Data)this.mModuleData;
      ArrayList localArrayList = new ArrayList();
      DfeReviews localDfeReviews = ((Data)this.mModuleData).dfeReviews;
      String str2 = ((Data)this.mModuleData).detailsDoc.mDocument.docid;
      int i = localDfeReviews.getCount();
      for (int j = 0; j < i; j++)
      {
        Review localReview = (Review)localDfeReviews.getItem(j);
        localArrayList.add(new ReviewsSamplesModuleLayout.ReviewItem(localReview, isReviewFeedbackHelpful(str2, localReview), isReviewFeedbackSpam(str2, localReview)));
      }
      localData.reviewItems = localArrayList;
    }
    for (;;)
    {
      return;
      String str1 = ((Data)this.mModuleData).detailsDoc.mDocument.docid;
      Iterator localIterator = ((Data)this.mModuleData).reviewItems.iterator();
      while (localIterator.hasNext())
      {
        ReviewsSamplesModuleLayout.ReviewItem localReviewItem = (ReviewsSamplesModuleLayout.ReviewItem)localIterator.next();
        localReviewItem.isHelpful = isReviewFeedbackHelpful(str1, localReviewItem.review);
        localReviewItem.isSpam = isReviewFeedbackSpam(str1, localReviewItem.review);
      }
    }
  }
  
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    if ((paramDocument2 == null) || (TextUtils.isEmpty(paramDocument2.mDocument.reviewsUrl)) || (!TextUtils.isEmpty(paramDocument2.mDocument.snippetsUrl))) {}
    while ((!paramBoolean) && (FinskyApp.get().getExperiments().isEnabled(12603844L))) {
      return;
    }
    if (this.mModuleData == null)
    {
      this.mModuleData = new Data();
      DfeReviews localDfeReviews = new DfeReviews(this.mSocialDfeApi, paramDocument2.mDocument.reviewsUrl, paramDocument1.getVersionCode(), false);
      localDfeReviews.mSortType = 4;
      localDfeReviews.addDataChangedListener(this);
      localDfeReviews.startLoadItems();
      ((Data)this.mModuleData).dfeReviews = localDfeReviews;
    }
    ((Data)this.mModuleData).detailsDoc = paramDocument2;
  }
  
  public final void bindView(View paramView)
  {
    ReviewsSamplesModuleLayout localReviewsSamplesModuleLayout = (ReviewsSamplesModuleLayout)paramView;
    Object localObject;
    Document localDocument1;
    if ((!localReviewsSamplesModuleLayout.hasBinded()) || (this.mNeedsRefresh))
    {
      localReviewsSamplesModuleLayout.setReviewFeedbackListener(this);
      if (ReviewsActivity.shouldUseReviewFeedbackV2())
      {
        localObject = new ArrayList(((Data)this.mModuleData).reviewItems.size());
        for (int i = 0; i < ((Data)this.mModuleData).reviewItems.size(); i++)
        {
          ReviewsSamplesModuleLayout.ReviewItem localReviewItem = (ReviewsSamplesModuleLayout.ReviewItem)((Data)this.mModuleData).reviewItems.get(i);
          if (!localReviewItem.isSpam) {
            ((List)localObject).add(localReviewItem);
          }
        }
      }
      localObject = ((Data)this.mModuleData).reviewItems;
      localDocument1 = ((Data)this.mModuleData).detailsDoc;
      Document localDocument2 = ((Data)this.mModuleData).detailsDoc;
      if ((localDocument2.mDocument == null) || (localDocument2.mDocument.tip == null) || (localDocument2.mDocument.tip.length <= 0)) {
        break label214;
      }
    }
    label214:
    for (boolean bool = true;; bool = false)
    {
      localReviewsSamplesModuleLayout.bind((List)localObject, localDocument1, bool, this, this.mNavigationManager, this.mParentNode);
      this.mNeedsRefresh = false;
      return;
    }
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyEventLog.childImpression(this, paramPlayStoreUiElementNode);
  }
  
  public final int getLayoutResId()
  {
    return 2130969080;
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt1 == 45) && (paramInt2 == -1) && (readyForDisplay())) {
      refreshModule(false);
    }
  }
  
  public final void onDataChanged()
  {
    if (readyForDisplay())
    {
      updateReviewItems();
      this.mFinskyModuleController.refreshModule(this, true);
    }
  }
  
  public final void onDestroyModule()
  {
    if ((this.mModuleData != null) && (((Data)this.mModuleData).dfeReviews != null)) {
      ((Data)this.mModuleData).dfeReviews.removeDataChangedListener(this);
    }
    this.mIsDestroyed = true;
  }
  
  public final void onRestoreModuleData(FinskyModule.ModuleData paramModuleData)
  {
    super.onRestoreModuleData(paramModuleData);
    if ((this.mModuleData != null) && (((Data)this.mModuleData).dfeReviews != null))
    {
      if (!((Data)this.mModuleData).dfeReviews.isReady())
      {
        ((Data)this.mModuleData).dfeReviews.addDataChangedListener(this);
        ((Data)this.mModuleData).dfeReviews.startLoadItems();
      }
    }
    else {
      return;
    }
    updateReviewItems();
  }
  
  public final void onReviewFeedback(final String paramString1, final String paramString2, final ReviewFeedbackListener.ReviewFeedbackRating paramReviewFeedbackRating)
  {
    DfeRateReview localDfeRateReview = new DfeRateReview(FinskyApp.get().getDfeApi(null), paramString1, paramString2, paramReviewFeedbackRating.mRpcId);
    final boolean bool1 = ReviewsActivity.shouldUseReviewFeedbackV2();
    if (bool1)
    {
      if (paramReviewFeedbackRating == ReviewFeedbackListener.ReviewFeedbackRating.SPAM) {
        Snackbar.make$349db449(this.mContainerFragment.mView).setAction$6ff2c59b(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            ReviewsSamplesModule.this.undoFeedback(paramString1, paramString2, paramReviewFeedbackRating);
          }
        }).show();
      }
      while (this.mMutationCache.isReviewFeedbackCached(paramString1, paramString2, paramReviewFeedbackRating))
      {
        undoFeedback(paramString1, paramString2, paramReviewFeedbackRating);
        return;
        Toast.makeText(this.mContext, 2131362688, 0).show();
      }
      this.mMutationCache.addReviewFeedback(paramString1, paramString2, paramReviewFeedbackRating);
    }
    localDfeRateReview.addDataChangedListener(new OnDataChangedListener()
    {
      public final void onDataChanged()
      {
        if ((!bool1) && (paramReviewFeedbackRating == ReviewFeedbackListener.ReviewFeedbackRating.SPAM))
        {
          DfeReviews localDfeReviews1 = ((ReviewsSamplesModule.Data)ReviewsSamplesModule.this.mModuleData).dfeReviews;
          ReviewsSamplesModule.this.mSocialDfeApi.invalidateReviewsCache$13ffb93a(((ReviewsSamplesModule.Data)ReviewsSamplesModule.this.mModuleData).detailsDoc.mDocument.reviewsUrl, localDfeReviews1.mFilterByDevice, localDfeReviews1.getVersionFilter(), localDfeReviews1.mRating, localDfeReviews1.mSortType);
          DfeReviews localDfeReviews2 = new DfeReviews(ReviewsSamplesModule.this.mSocialDfeApi, ((ReviewsSamplesModule.Data)ReviewsSamplesModule.this.mModuleData).detailsDoc.mDocument.reviewsUrl, ((ReviewsSamplesModule.Data)ReviewsSamplesModule.this.mModuleData).detailsDoc.getVersionCode(), false);
          localDfeReviews2.mSortType = 4;
          ((ReviewsSamplesModule.Data)ReviewsSamplesModule.this.mModuleData).dfeReviews = localDfeReviews2;
          if (!ReviewsSamplesModule.this.mIsDestroyed)
          {
            localDfeReviews2.addDataChangedListener(ReviewsSamplesModule.this);
            localDfeReviews2.startLoadItems();
            ReviewsSamplesModule.access$202$69795fa3(ReviewsSamplesModule.this);
          }
        }
      }
    });
    boolean bool2;
    if (bool1) {
      if (paramReviewFeedbackRating == ReviewFeedbackListener.ReviewFeedbackRating.SPAM)
      {
        bool2 = true;
        label145:
        refreshModule(bool2);
      }
    }
    for (;;)
    {
      switch (4.$SwitchMap$com$google$android$finsky$activities$ReviewFeedbackListener$ReviewFeedbackRating[paramReviewFeedbackRating.ordinal()])
      {
      default: 
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramReviewFeedbackRating.toString();
        FinskyLog.wtf("Unknown review rating selected in reviews samples section: %d", arrayOfObject);
        return;
        bool2 = false;
        break label145;
        localDfeRateReview.addErrorListener(new Response.ErrorListener()
        {
          public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
          {
            Toast.makeText(ReviewsSamplesModule.this.mContext, 2131362689, 0).show();
          }
        });
      }
    }
    int i = 1212;
    for (;;)
    {
      FinskyApp.get().getEventLogger().logClickEvent(i, null, this);
      return;
      i = 1213;
      continue;
      i = 1214;
    }
  }
  
  public final void onReviewFeedbackClick(Review paramReview)
  {
    FragmentManagerImpl localFragmentManagerImpl = this.mContainerFragment.mFragmentManager;
    if (localFragmentManagerImpl.findFragmentByTag("rate_review_dialog") != null) {
      return;
    }
    FinskyApp.get().getEventLogger().logClickEvent(1211, null, this);
    ReviewFeedbackDialog localReviewFeedbackDialog = ReviewFeedbackDialog.newInstance$47996a45(((Data)this.mModuleData).detailsDoc.mDocument.docid, paramReview);
    localReviewFeedbackDialog.setTargetFragment(this.mContainerFragment, 0);
    localReviewFeedbackDialog.show(localFragmentManagerImpl, "rate_review_dialog");
  }
  
  public final boolean readyForDisplay()
  {
    return (this.mModuleData != null) && (((Data)this.mModuleData).dfeReviews != null) && (((Data)this.mModuleData).dfeReviews.isReady()) && (((Data)this.mModuleData).dfeReviews.getCount() != 0);
  }
  
  protected static final class Data
    extends FinskyModule.ModuleData
  {
    Document detailsDoc;
    DfeReviews dfeReviews;
    List<ReviewsSamplesModuleLayout.ReviewItem> reviewItems;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.ReviewsSamplesModule
 * JD-Core Version:    0.7.0.1
 */