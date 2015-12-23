package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.animation.Animation;
import com.android.vending.R.styleable;
import com.google.android.finsky.adapters.Recyclable;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.PlayAnimationUtils;
import com.google.android.finsky.utils.PlayAnimationUtils.AnimationListenerAdapter;
import com.google.android.finsky.utils.PlayCardUtils;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.PlayCardThumbnail;
import com.google.android.play.layout.PlayCardViewBase;
import com.google.android.play.utils.PlayUtils;
import java.util.ArrayList;
import java.util.List;

public class PlayCardClusterViewContent
  extends ViewGroup
  implements Recyclable
{
  private BitmapLoader mBitmapLoader;
  protected int mCardContentHorizontalPadding;
  protected int mCardContentPaddingBottom;
  protected int mCardContentPaddingTop;
  private final int mCardInset;
  protected ClientMutationCache mClientMutationCache;
  private Document mClusterDocumentData;
  private Document mClusterLoggingDocument;
  private LayoutInflater mInflater;
  private List<Document> mLooseDocumentsData;
  protected PlayCardClusterMetadata mMetadata;
  private NavigationManager mNavigationManager;
  private String mParentId;
  private PlayStoreUiElementNode mParentNode;
  private final int mSmallCardContentMinHeight;
  
  public PlayCardClusterViewContent(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardClusterViewContent(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mInflater = LayoutInflater.from(paramContext);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PlayCardClusterViewContent);
    this.mCardContentPaddingTop = localTypedArray.getDimensionPixelSize(0, 0);
    this.mCardContentPaddingBottom = localTypedArray.getDimensionPixelSize(1, 0);
    localTypedArray.recycle();
    Resources localResources = paramContext.getResources();
    this.mSmallCardContentMinHeight = localResources.getDimensionPixelSize(2131493128);
    this.mCardInset = localResources.getDimensionPixelSize(2131493068);
  }
  
  private void bindCardContent(PlayCardViewBase paramPlayCardViewBase, int paramInt1, int paramInt2, PlayCardDismissListener paramPlayCardDismissListener)
  {
    Document localDocument = getDoc(paramInt2);
    if (localDocument == null)
    {
      paramPlayCardViewBase.clearCardState();
      return;
    }
    boolean bool1 = localDocument.hasReasons();
    PlayCardClusterMetadata.CardMetadata localCardMetadata = this.mMetadata.getTileMetadata(paramInt1).mCardMetadata;
    PlayCardClusterMetadata localPlayCardClusterMetadata = this.mMetadata;
    int i;
    float f;
    label92:
    boolean bool2;
    label143:
    String str;
    BitmapLoader localBitmapLoader;
    NavigationManager localNavigationManager;
    if ((localPlayCardClusterMetadata.mRespectChildThumbnailAspectRatio) || (((PlayCardClusterMetadata.ClusterTileMetadata)localPlayCardClusterMetadata.mTiles.get(paramInt1)).mRespectChildThumbnailAspectRatio))
    {
      i = 1;
      if (i == 0) {
        break label200;
      }
      f = PlayCardClusterMetadata.getAspectRatio(localDocument.mDocument.docType);
      paramPlayCardViewBase.setThumbnailAspectRatio(f);
      ((FifeImageView)paramPlayCardViewBase.getThumbnail().getImageView()).mIsFrozen = true;
      if ((!bool1) || (paramPlayCardDismissListener == null) || (!this.mClientMutationCache.isDismissedRecommendation(localDocument.mDocument.docid))) {
        break label210;
      }
      bool2 = true;
      str = this.mParentId;
      localBitmapLoader = this.mBitmapLoader;
      localNavigationManager = this.mNavigationManager;
      if (!bool1) {
        break label216;
      }
    }
    label200:
    label210:
    label216:
    for (PlayCardDismissListener localPlayCardDismissListener = paramPlayCardDismissListener;; localPlayCardDismissListener = null)
    {
      PlayCardUtils.bindCard(paramPlayCardViewBase, localDocument, str, localBitmapLoader, localNavigationManager, bool2, localPlayCardDismissListener, this.mParentNode, false, -1, false);
      return;
      i = 0;
      break;
      f = localCardMetadata.mThumbnailAspectRatio;
      break label92;
      bool2 = false;
      break label143;
    }
  }
  
  private float getCellHeight(float paramFloat)
  {
    PlayCardClusterMetadata.CardMetadata localCardMetadata = this.mMetadata.mCardMetadataForMinCellHeight;
    if (localCardMetadata == null) {
      return paramFloat;
    }
    int i = localCardMetadata.mHSpan;
    int j = localCardMetadata.mVSpan;
    return (localCardMetadata.mThumbnailAspectRatio * (paramFloat * i - 2 * this.mCardInset) + this.mSmallCardContentMinHeight + 2 * this.mCardInset) / j;
  }
  
  public final void bindCardAt(int paramInt1, int paramInt2, PlayCardDismissListener paramPlayCardDismissListener)
  {
    bindCardContent(getCardChildAt(paramInt1), paramInt1, paramInt2, paramPlayCardDismissListener);
  }
  
  public void createContent(DfeApi paramDfeApi, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, PlayCardDismissListener paramPlayCardDismissListener, PlayCardHeap paramPlayCardHeap, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mNavigationManager = paramNavigationManager;
    this.mBitmapLoader = paramBitmapLoader;
    this.mParentNode = paramPlayStoreUiElementNode;
    int i = getNumberOfTilesToBind();
    int j = 0;
    if (j < this.mMetadata.getTileCount())
    {
      PlayCardClusterMetadata.CardMetadata localCardMetadata = this.mMetadata.getTileMetadata(j).mCardMetadata;
      LayoutInflater localLayoutInflater = this.mInflater;
      Utils.ensureOnMainThread();
      int k = localCardMetadata.mLayoutId;
      Object localObject = (List)paramPlayCardHeap.mHeap.get(k);
      if (localObject == null)
      {
        localObject = new ArrayList();
        paramPlayCardHeap.mHeap.put(k, localObject);
      }
      PlayCardViewBase localPlayCardViewBase;
      if (((List)localObject).isEmpty())
      {
        localPlayCardViewBase = (PlayCardViewBase)localLayoutInflater.inflate(k, null, false);
        label132:
        localPlayCardViewBase.setThumbnailAspectRatio(localCardMetadata.mThumbnailAspectRatio);
        if (j >= i) {
          break label197;
        }
      }
      label197:
      for (int m = tileIndexToDocumentIndex(j);; m = -1)
      {
        bindCardContent(localPlayCardViewBase, j, m, paramPlayCardDismissListener);
        addView(localPlayCardViewBase);
        j++;
        break;
        localPlayCardViewBase = (PlayCardViewBase)((List)localObject).remove(0);
        break label132;
      }
    }
  }
  
  public final PlayCardViewBase getCardChildAt(int paramInt)
  {
    return (PlayCardViewBase)getChildAt(paramInt + getIndexOfFirstCard());
  }
  
  public int getCardChildCount()
  {
    return getChildCount() - getIndexOfFirstCard();
  }
  
  public final int getCardChildIndex(View paramView)
  {
    for (int i = 0; i < getChildCount(); i++) {
      if (getChildAt(i) == paramView) {
        return i - getIndexOfFirstCard();
      }
    }
    return -1;
  }
  
  public int getCardContentHorizontalPadding()
  {
    return this.mCardContentHorizontalPadding;
  }
  
  protected final float getCellSize(int paramInt)
  {
    int i = this.mMetadata.mWidth;
    return (paramInt - 2 * this.mCardContentHorizontalPadding) / i;
  }
  
  public Document getClusterLoggingDocument()
  {
    if (this.mClusterLoggingDocument == null) {
      return this.mClusterDocumentData;
    }
    return this.mClusterLoggingDocument;
  }
  
  protected final Document getDoc(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= getDocCount())) {
      return null;
    }
    if (this.mClusterDocumentData != null) {
      return this.mClusterDocumentData.getChildAt(paramInt);
    }
    return (Document)this.mLooseDocumentsData.get(paramInt);
  }
  
  protected final int getDocCount()
  {
    if (this.mClusterDocumentData != null) {
      return this.mClusterDocumentData.getChildCount();
    }
    return this.mLooseDocumentsData.size();
  }
  
  protected int getExtraColumnOffset()
  {
    int i = this.mMetadata.getTileCount();
    int j = getIndexOfFirstCard();
    if (!this.mMetadata.mAlignToParentEndIfNecessary) {
      return 0;
    }
    int k = this.mMetadata.mWidth;
    int m = 0;
    for (int n = 0; n < i; n++) {
      if (getChildAt(j + n).getVisibility() != 4)
      {
        PlayCardClusterMetadata.ClusterTileMetadata localClusterTileMetadata = this.mMetadata.getTileMetadata(n);
        m = Math.max(m, localClusterTileMetadata.mXStart + localClusterTileMetadata.mCardMetadata.mHSpan);
      }
    }
    if (m == 0) {
      return k - this.mMetadata.mLeadingGap;
    }
    return k - m;
  }
  
  protected int getIndexOfFirstCard()
  {
    return 0;
  }
  
  public final int getLeadingGap(int paramInt)
  {
    float f = getCellSize(paramInt);
    return getPaddingLeft() + (int)(f * this.mMetadata.mLeadingGap);
  }
  
  public PlayCardClusterMetadata getMetadata()
  {
    return this.mMetadata;
  }
  
  protected int getNumberOfTilesToBind()
  {
    return this.mMetadata.getTileCount();
  }
  
  public final int getTrailingGap(int paramInt)
  {
    float f = getCellSize(paramInt);
    return getPaddingRight() + (int)(f * this.mMetadata.getTrailingGap());
  }
  
  public final void hideCardAt(int paramInt)
  {
    final PlayCardViewBase localPlayCardViewBase = getCardChildAt(paramInt);
    localPlayCardViewBase.startAnimation(PlayAnimationUtils.getFadeOutAnimation(getContext(), 250L, new PlayAnimationUtils.AnimationListenerAdapter()
    {
      public final void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        localPlayCardViewBase.clearCardState();
      }
    }));
  }
  
  protected final void layoutContent(boolean paramBoolean)
  {
    int i = getHeight();
    int j = getWidth();
    float f1 = getCellSize(j);
    float f2 = getCellHeight(f1);
    int k = this.mCardContentHorizontalPadding;
    int m = this.mMetadata.getTileCount();
    int n = this.mMetadata.mHeight;
    int i1 = getIndexOfFirstCard();
    int i2 = getExtraColumnOffset();
    for (int i3 = 0; i3 < m; i3++)
    {
      PlayCardClusterMetadata.ClusterTileMetadata localClusterTileMetadata = this.mMetadata.getTileMetadata(i3);
      int i4 = i2 + localClusterTileMetadata.mXStart;
      int i5 = localClusterTileMetadata.mYStart;
      int i6 = k + (int)(f1 * i4);
      PlayCardViewBase localPlayCardViewBase = (PlayCardViewBase)getChildAt(i1 + i3);
      int i7 = i - this.mCardContentPaddingBottom;
      if (!this.mMetadata.mRespectChildHeight) {
        i7 -= (int)(f2 * (n - (i5 + localClusterTileMetadata.mCardMetadata.mVSpan)));
      }
      int i8 = localPlayCardViewBase.getMeasuredWidth();
      int i9 = PlayUtils.getViewLeftFromParentStart(j, i8, paramBoolean, i6);
      localPlayCardViewBase.layout(i9, i7 - localPlayCardViewBase.getMeasuredHeight(), i9 + i8, i7);
      ((FifeImageView)localPlayCardViewBase.getThumbnail().getImageView()).unfreezeImage(true);
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    for (boolean bool = true;; bool = false)
    {
      layoutContent(bool);
      return;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = this.mMetadata.mHeight;
    float f1 = getCellSize(i);
    float f2 = getCellHeight(f1);
    boolean bool = this.mMetadata.mRespectChildHeight;
    int k = this.mMetadata.getTileCount();
    int m = getIndexOfFirstCard();
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    if (i2 < k)
    {
      PlayCardClusterMetadata.ClusterTileMetadata localClusterTileMetadata = this.mMetadata.getTileMetadata(i2);
      int i5 = localClusterTileMetadata.mCardMetadata.mHSpan;
      int i6 = localClusterTileMetadata.mCardMetadata.mVSpan;
      View localView = getChildAt(m + i2);
      int i7 = (int)(f1 * i5);
      int i8 = (int)(f2 * i6);
      int i9 = View.MeasureSpec.makeMeasureSpec(i7, 1073741824);
      if (bool)
      {
        localView.measure(i9, 0);
        n = Math.max(n, localView.getMeasuredHeight());
      }
      for (;;)
      {
        if (localView.getVisibility() == 0) {
          i1++;
        }
        i2++;
        break;
        localView.measure(i9, View.MeasureSpec.makeMeasureSpec(i8, 1073741824));
      }
    }
    if (i1 == 0)
    {
      setMeasuredDimension(i, 0);
      return;
    }
    int i3 = this.mCardContentPaddingTop + this.mCardContentPaddingBottom;
    if (bool) {}
    for (int i4 = i3 + n;; i4 = i3 + (int)(f2 * j))
    {
      setMeasuredDimension(i, i4);
      return;
    }
  }
  
  public void onRecycle()
  {
    this.mClusterDocumentData = null;
    this.mLooseDocumentsData = null;
  }
  
  public final void removeAllCards()
  {
    int i = getIndexOfFirstCard();
    if (i == 0) {
      removeAllViews();
    }
    for (;;)
    {
      return;
      while (getChildCount() > i) {
        removeViewAt(i);
      }
    }
  }
  
  public void setCardContentHorizontalPadding(int paramInt)
  {
    if (this.mCardContentHorizontalPadding != paramInt)
    {
      this.mCardContentHorizontalPadding = paramInt;
      requestLayout();
    }
  }
  
  public void setCardContentVerticalPadding(int paramInt)
  {
    this.mCardContentPaddingTop = paramInt;
    this.mCardContentPaddingBottom = paramInt;
    requestLayout();
  }
  
  public void setClusterDocumentData(Document paramDocument)
  {
    if (this.mLooseDocumentsData != null) {
      throw new IllegalStateException("Already initialized with loose documents");
    }
    this.mClusterDocumentData = paramDocument;
    this.mParentId = this.mClusterDocumentData.mDocument.docid;
  }
  
  public void setClusterLoggingDocument(Document paramDocument)
  {
    this.mClusterLoggingDocument = paramDocument;
  }
  
  public final void setLooseDocumentsData(List<Document> paramList, String paramString)
  {
    if (this.mClusterDocumentData != null) {
      throw new IllegalStateException("Already initialized with cluster document");
    }
    this.mLooseDocumentsData = paramList;
    this.mParentId = paramString;
  }
  
  public void setMetadata(PlayCardClusterMetadata paramPlayCardClusterMetadata, ClientMutationCache paramClientMutationCache)
  {
    this.mMetadata = paramPlayCardClusterMetadata;
    this.mClientMutationCache = paramClientMutationCache;
  }
  
  protected int tileIndexToDocumentIndex(int paramInt)
  {
    return paramInt;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardClusterViewContent
 * JD-Core Version:    0.7.0.1
 */