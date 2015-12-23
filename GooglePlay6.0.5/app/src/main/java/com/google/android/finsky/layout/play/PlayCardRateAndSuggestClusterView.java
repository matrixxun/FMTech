package com.google.android.finsky.layout.play;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.animation.Animation;
import android.widget.TextView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.config.G;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Review;
import com.google.android.finsky.protos.SectionMetadata;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.FastHtmlParser;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.PlayAnimationUtils;
import com.google.android.finsky.utils.PlayAnimationUtils.AnimationListenerAdapter;
import com.google.android.finsky.utils.PlayCardUtils;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.UiUtils.ClusterFadeOutListener;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.PlayCardViewBase;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.List;

public class PlayCardRateAndSuggestClusterView
  extends PlayCardClusterView
  implements Response.ErrorListener, OnDataChangedListener, PlayCardDismissListener
{
  private static final boolean IS_JB_OR_ABOVE;
  private Document mClusterDoc;
  private UiUtils.ClusterFadeOutListener mClusterFadeOutListener;
  private PlayCardRateAndSuggestContentScroller mContentScroller;
  private TextView mEmptySadface;
  private int mIndexOfItemToRate = 0;
  private DfeList mRecommendedItems;
  private boolean mShouldScrollRateCardOnDataLoad;
  private int mState = 0;
  private PlayCardDismissListener mSuggestionsDismissListener;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 16) {}
    for (boolean bool = true;; bool = false)
    {
      IS_JB_OR_ABOVE = bool;
      return;
    }
  }
  
  public PlayCardRateAndSuggestClusterView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardRateAndSuggestClusterView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void clearRecommendedItemsList()
  {
    if (this.mRecommendedItems != null)
    {
      this.mRecommendedItems.removeDataChangedListener(this);
      this.mRecommendedItems.removeErrorListener(this);
      this.mRecommendedItems = null;
    }
  }
  
  private void handleErrorOrNoSuggestionsResponse()
  {
    Document localDocument = this.mClusterDoc.getChildAt(this.mIndexOfItemToRate);
    if ((localDocument != null) && (!TextUtils.isEmpty(localDocument.mDocument.docid))) {
      this.mClientMutationCache.dismissRecommendation(localDocument.mDocument.docid);
    }
    syncIndexOfItemToRate(false);
    syncState(false);
  }
  
  private void setState(int paramInt, boolean paramBoolean)
  {
    this.mState = paramInt;
    PlayCardRateAndSuggestClusterViewContent localPlayCardRateAndSuggestClusterViewContent = (PlayCardRateAndSuggestClusterViewContent)this.mContent;
    localPlayCardRateAndSuggestClusterViewContent.mState = this.mState;
    if (!paramBoolean) {}
    for (boolean bool = true;; bool = false)
    {
      localPlayCardRateAndSuggestClusterViewContent.refreshCards(bool);
      return;
    }
  }
  
  private void syncIndexOfItemToRate(boolean paramBoolean)
  {
    boolean bool;
    int i;
    if (!paramBoolean)
    {
      bool = true;
      this.mShouldScrollRateCardOnDataLoad = bool;
      i = this.mClusterDoc.getChildCount();
    }
    for (;;)
    {
      if (this.mIndexOfItemToRate < i)
      {
        Document localDocument = this.mClusterDoc.getChildAt(this.mIndexOfItemToRate);
        if (localDocument == null)
        {
          FinskyLog.wtf("Got a null document at index " + this.mIndexOfItemToRate, new Object[0]);
          this.mIndexOfItemToRate = (1 + this.mIndexOfItemToRate);
          continue;
          bool = false;
          break;
        }
        String str = localDocument.mDocument.docid;
        if (this.mClientMutationCache.isDismissedRecommendation(str))
        {
          this.mIndexOfItemToRate = (1 + this.mIndexOfItemToRate);
        }
        else
        {
          Review localReview = this.mClientMutationCache.getCachedReview(str, null);
          if (localReview != null) {
            if (localReview.starRating < ((Integer)G.positiveRateThreshold.get()).intValue())
            {
              this.mIndexOfItemToRate = (1 + this.mIndexOfItemToRate);
            }
            else
            {
              SectionMetadata localSectionMetadata = localDocument.getSuggestForRatingSection();
              if ((localSectionMetadata != null) && (!TextUtils.isEmpty(localSectionMetadata.listUrl))) {}
              for (int j = 1;; j = 0)
              {
                if (j != 0) {
                  return;
                }
                this.mIndexOfItemToRate = (1 + this.mIndexOfItemToRate);
                break;
              }
            }
          }
        }
      }
    }
  }
  
  private void syncState(boolean paramBoolean)
  {
    if (getCardChildCount() == 0) {}
    final PlayCardViewRate localPlayCardViewRate;
    final float f;
    do
    {
      int i;
      do
      {
        return;
        i = this.mState;
        if (this.mIndexOfItemToRate < this.mClusterDoc.getChildCount()) {
          break;
        }
        setState(3, paramBoolean);
      } while (i == this.mState);
      if (!paramBoolean) {}
      for (int j = 1; j == 0; j = 0)
      {
        this.mContentScroller.setVisibility(8);
        this.mEmptySadface.setVisibility(0);
        return;
      }
      Animation localAnimation1 = PlayAnimationUtils.getFadeOutAnimation(getContext(), 250L, new PlayAnimationUtils.AnimationListenerAdapter()
      {
        public final void onAnimationEnd(Animation paramAnonymousAnimation)
        {
          PlayCardRateAndSuggestClusterView.this.mContentScroller.setVisibility(8);
          if (PlayListView.ENABLE_ANIMATION) {
            UiUtils.fadeOutCluster(PlayCardRateAndSuggestClusterView.this, PlayCardRateAndSuggestClusterView.this.mClusterFadeOutListener, 2500L);
          }
        }
      });
      this.mContentScroller.startAnimation(localAnimation1);
      Animation localAnimation2 = PlayAnimationUtils.getFadeInAnimation(getContext(), 250L, null);
      this.mEmptySadface.setVisibility(0);
      UiUtils.sendAccessibilityEventWithText(getContext(), this.mEmptySadface.getText(), this.mEmptySadface, true);
      this.mEmptySadface.startAnimation(localAnimation2);
      return;
      Document localDocument = this.mClusterDoc.getChildAt(this.mIndexOfItemToRate);
      if (this.mClientMutationCache.getCachedReview(localDocument.mDocument.docid, null) != null)
      {
        setState(2, paramBoolean);
        SectionMetadata localSectionMetadata = this.mClusterDoc.getChildAt(this.mIndexOfItemToRate).getSuggestForRatingSection();
        if (paramBoolean)
        {
          this.mContentScroller.hideRateCard();
          this.mHeader.replaceTitles(localSectionMetadata.header, FastHtmlParser.fromHtml(localSectionMetadata.descriptionHtml));
        }
        clearRecommendedItemsList();
        this.mRecommendedItems = new DfeList(this.mDfeApi, localSectionMetadata.listUrl, false);
        this.mRecommendedItems.addDataChangedListener(this);
        this.mRecommendedItems.addErrorListener(this);
        this.mRecommendedItems.startLoadItems();
        return;
      }
      setState(0, paramBoolean);
      PlayCardClusterViewContent localPlayCardClusterViewContent = this.mContent;
      Document[] arrayOfDocument = new Document[1];
      arrayOfDocument[0] = this.mClusterDoc.getChildAt(this.mIndexOfItemToRate);
      localPlayCardClusterViewContent.setLooseDocumentsData(Lists.newArrayList(arrayOfDocument), this.mClusterDoc.mDocument.docid);
      localPlayCardViewRate = (PlayCardViewRate)getCardChildAt(0);
      f = PlayCardClusterMetadata.getAspectRatio(localDocument.mDocument.docType);
    } while (paramBoolean);
    localPlayCardViewRate.startAnimation(PlayAnimationUtils.getFadeOutAnimation(getContext(), 150L, new PlayAnimationUtils.AnimationListenerAdapter()
    {
      public final void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        localPlayCardViewRate.setState(0);
        PlayCardRateAndSuggestClusterView.this.mContent.bindCardAt(0, 0, PlayCardRateAndSuggestClusterView.this);
        localPlayCardViewRate.setThumbnailAspectRatio(f);
        Animation localAnimation = PlayAnimationUtils.getFadeInAnimation(PlayCardRateAndSuggestClusterView.this.getContext(), 150L, new PlayAnimationUtils.AnimationListenerAdapter()
        {
          public final void onAnimationEnd(Animation paramAnonymous2Animation)
          {
            if ((!PlayCardRateAndSuggestClusterView.IS_JB_OR_ABOVE) || (!UiUtils.isAccessibilityEnabled(PlayCardRateAndSuggestClusterView.this.getContext()))) {
              return;
            }
            PlayCardRateAndSuggestClusterView.1.this.val$rateCard.performAccessibilityAction(64, null);
          }
        });
        localPlayCardViewRate.startAnimation(localAnimation);
        PlayCardRateAndSuggestClusterView.access$100$48f30656(PlayCardRateAndSuggestClusterView.this, 0);
      }
    }));
  }
  
  public final void createContent(PlayCardClusterMetadata paramPlayCardClusterMetadata, ClientMutationCache paramClientMutationCache, DfeApi paramDfeApi, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, PlayCardDismissListener paramPlayCardDismissListener, PlayCardHeap paramPlayCardHeap, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mClientMutationCache = paramClientMutationCache;
    syncIndexOfItemToRate(true);
    ArrayList localArrayList = new ArrayList();
    if (this.mIndexOfItemToRate < this.mClusterDoc.getChildCount()) {
      localArrayList.add(this.mClusterDoc.getChildAt(this.mIndexOfItemToRate));
    }
    this.mContent.setLooseDocumentsData(localArrayList, this.mClusterDoc.mDocument.docid);
    this.mSuggestionsDismissListener = paramPlayCardDismissListener;
    super.createContent(paramPlayCardClusterMetadata, paramClientMutationCache, paramDfeApi, paramNavigationManager, paramBitmapLoader, this, paramPlayCardHeap, paramPlayStoreUiElementNode);
    syncState(true);
    final PlayCardViewRate localPlayCardViewRate = (PlayCardViewRate)getCardChildAt(0);
    localPlayCardViewRate.setRateListener(new PlayCardViewRate.RateListener()
    {
      public final void onRate$2563266(boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
        {
          PlayCardRateAndSuggestClusterView.access$200$166b8bf1(PlayCardRateAndSuggestClusterView.this);
          PlayCardRateAndSuggestClusterView.access$300$166b8bf1(PlayCardRateAndSuggestClusterView.this);
          return;
        }
        PlayCardRateAndSuggestClusterView.access$100$48f30656(PlayCardRateAndSuggestClusterView.this, 1);
      }
      
      public final void onRateCleared()
      {
        PlayCardRateAndSuggestClusterView.access$100$48f30656(PlayCardRateAndSuggestClusterView.this, 0);
      }
    });
    localPlayCardViewRate.setSkipListener(new PlayCardViewRate.SkipListener()
    {
      public final void onSkip()
      {
        PlayCardUtils.dismissCard(localPlayCardViewRate, (Document)localPlayCardViewRate.getData(), FinskyApp.get().getDfeApi(null), PlayCardRateAndSuggestClusterView.this, (PlayStoreUiElementNode)localPlayCardViewRate.getLoggingData());
      }
    });
    localPlayCardViewRate.setState(0);
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 413;
  }
  
  public final void onDataChanged()
  {
    int i = this.mRecommendedItems.getCount();
    if (i == 0)
    {
      handleErrorOrNoSuggestionsResponse();
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(this.mClusterDoc.getChildAt(this.mIndexOfItemToRate));
    int j = Math.min(-1 + getCardChildCount(), i);
    for (int k = 0; k < j; k++) {
      localArrayList.add(this.mRecommendedItems.getItem(k));
    }
    this.mContent.setLooseDocumentsData(localArrayList, this.mClusterDoc.mDocument.docid);
    for (int m = 1; m <= j; m++) {
      this.mContent.bindCardAt(m, m, this);
    }
    for (int n = j + 1; n < getCardChildCount(); n++) {
      this.mContent.hideCardAt(n);
    }
    final SectionMetadata localSectionMetadata = this.mClusterDoc.getChildAt(this.mIndexOfItemToRate).getSuggestForRatingSection();
    if (this.mShouldScrollRateCardOnDataLoad) {
      if (this.mContent.mMetadata.mWidth <= 2)
      {
        this.mContentScroller.scrollAwayRateCard();
        this.mHeader.replaceTitles(localSectionMetadata.header, FastHtmlParser.fromHtml(localSectionMetadata.descriptionHtml));
      }
    }
    for (;;)
    {
      setState(2, false);
      return;
      new Handler(Looper.myLooper()).postDelayed(new Runnable()
      {
        public final void run()
        {
          PlayCardRateAndSuggestClusterView.this.mContentScroller.scrollAwayRateCard();
          PlayCardRateAndSuggestClusterView.this.mHeader.replaceTitles(localSectionMetadata.header, FastHtmlParser.fromHtml(localSectionMetadata.descriptionHtml));
        }
      }, 750L);
      continue;
      this.mContentScroller.hideRateCard();
      this.mHeader.replaceTitles(localSectionMetadata.header, FastHtmlParser.fromHtml(localSectionMetadata.descriptionHtml));
    }
  }
  
  protected void onDetachedFromWindow()
  {
    if (hasCards()) {
      ((PlayCardViewRate)getCardChildAt(0)).setRateListener(null);
    }
    clearRecommendedItemsList();
    super.onDetachedFromWindow();
  }
  
  public final void onDismissDocument(Document paramDocument, PlayCardViewBase paramPlayCardViewBase)
  {
    if (paramPlayCardViewBase.getCardType() == 13)
    {
      this.mClientMutationCache.dismissRecommendation(paramDocument.mDocument.docid);
      syncIndexOfItemToRate(false);
      syncState(false);
      return;
    }
    this.mSuggestionsDismissListener.onDismissDocument(paramDocument, paramPlayCardViewBase);
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    handleErrorOrNoSuggestionsResponse();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mContentScroller = ((PlayCardRateAndSuggestContentScroller)findViewById(2131755447));
    this.mEmptySadface = ((TextView)findViewById(2131755882));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getWidth();
    int j = getPaddingTop();
    if ((this.mHeader != null) && (this.mHeader.getVisibility() != 8))
    {
      this.mHeader.layout(0, j, i, j + this.mHeader.getMeasuredHeight());
      j += this.mHeader.getMeasuredHeight();
    }
    int k = this.mContentScroller.getMeasuredHeight();
    this.mContentScroller.layout(0, j, i, j + k);
    int m = j + (k - this.mEmptySadface.getMeasuredHeight()) / 2;
    this.mEmptySadface.layout(0, m, i, m + this.mEmptySadface.getMeasuredHeight());
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = getPaddingTop() + getPaddingBottom();
    if ((this.mHeader != null) && (this.mHeader.getVisibility() != 8))
    {
      this.mHeader.measure(paramInt1, 0);
      j += this.mHeader.getMeasuredHeight();
    }
    this.mContentScroller.measure(paramInt1, 0);
    int k = j + this.mContentScroller.getMeasuredHeight();
    this.mEmptySadface.measure(View.MeasureSpec.makeMeasureSpec(this.mContentScroller.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(this.mContentScroller.getMeasuredHeight(), -2147483648));
    setMeasuredDimension(i, k);
  }
  
  public void setClusterFadeOutListener(UiUtils.ClusterFadeOutListener paramClusterFadeOutListener)
  {
    this.mClusterFadeOutListener = paramClusterFadeOutListener;
  }
  
  public final PlayCardClusterView withClusterDocumentData(Document paramDocument)
  {
    this.mClusterDoc = paramDocument;
    this.mContent.setClusterLoggingDocument(this.mClusterDoc);
    return this;
  }
  
  public final PlayCardClusterView withLooseDocumentsData(List<Document> paramList, String paramString)
  {
    throw new UnsupportedOperationException("This cluster does not support loose data");
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardRateAndSuggestClusterView
 * JD-Core Version:    0.7.0.1
 */