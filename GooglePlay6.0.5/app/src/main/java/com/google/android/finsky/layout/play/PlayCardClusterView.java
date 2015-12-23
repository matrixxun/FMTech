package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.PlayCardViewBase;
import java.util.List;

public class PlayCardClusterView
  extends PlayClusterView
{
  protected ClientMutationCache mClientMutationCache;
  protected PlayCardClusterViewContent mContent;
  protected DfeApi mDfeApi;
  protected PlayCardDismissListener mStreamDismissListener;
  
  public PlayCardClusterView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardClusterView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public void createContent(PlayCardClusterMetadata paramPlayCardClusterMetadata, ClientMutationCache paramClientMutationCache, DfeApi paramDfeApi, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, PlayCardDismissListener paramPlayCardDismissListener, PlayCardHeap paramPlayCardHeap, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mClientMutationCache = paramClientMutationCache;
    this.mDfeApi = paramDfeApi;
    this.mStreamDismissListener = paramPlayCardDismissListener;
    this.mContent.setMetadata(paramPlayCardClusterMetadata, paramClientMutationCache);
    Document localDocument = this.mContent.getClusterLoggingDocument();
    if (localDocument == null) {}
    for (byte[] arrayOfByte = null;; arrayOfByte = localDocument.mDocument.serverLogsCookie)
    {
      configureLogging(arrayOfByte, paramPlayStoreUiElementNode);
      paramPlayCardHeap.recycle(this);
      this.mContent.createContent(paramDfeApi, paramNavigationManager, paramBitmapLoader, paramPlayCardDismissListener, paramPlayCardHeap, getParentOfChildren());
      return;
    }
  }
  
  public final PlayCardViewBase getCardChildAt(int paramInt)
  {
    return this.mContent.getCardChildAt(paramInt);
  }
  
  public int getCardChildCount()
  {
    return this.mContent.getCardChildCount();
  }
  
  public PlayCardClusterMetadata getMetadata()
  {
    return this.mContent.getMetadata();
  }
  
  public final boolean hasCards()
  {
    return getCardChildCount() > 0;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mContent = ((PlayCardClusterViewContent)findViewById(2131755307));
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
    int k = j + ((ViewGroup.MarginLayoutParams)this.mContent.getLayoutParams()).topMargin;
    this.mContent.layout(0, k, i, k + this.mContent.getMeasuredHeight());
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
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mContent.getLayoutParams();
    this.mContent.measure(paramInt1, 0);
    setMeasuredDimension(i, j + (localMarginLayoutParams.topMargin + this.mContent.getMeasuredHeight() + localMarginLayoutParams.bottomMargin));
  }
  
  public void onRecycle()
  {
    super.onRecycle();
    this.mContent.onRecycle();
  }
  
  public final void removeAllCards()
  {
    this.mContent.removeAllCards();
  }
  
  public void setCardContentHorizontalPadding(int paramInt)
  {
    this.mContent.setCardContentHorizontalPadding(paramInt);
  }
  
  public PlayCardClusterView withClusterDocumentData(Document paramDocument)
  {
    this.mContent.setClusterDocumentData(paramDocument);
    return this;
  }
  
  public PlayCardClusterView withLooseDocumentsData(List<Document> paramList, String paramString)
  {
    this.mContent.setLooseDocumentsData(paramList, paramString);
    return this;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardClusterView
 * JD-Core Version:    0.7.0.1
 */