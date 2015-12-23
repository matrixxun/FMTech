package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.PlayCardUtils;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.CardLinearLayout;
import com.google.android.play.layout.PlayCardViewBase;

public abstract class PlayCardPromoClusterBaseView
  extends PlayClusterView
{
  protected CardLinearLayout mClusterContentView;
  protected final int mColumnCount;
  protected PlayCardViewBase mPlayCardView;
  private int mPlayStoreUiElementType;
  protected FrameLayout mPromoContentView;
  
  public PlayCardPromoClusterBaseView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardPromoClusterBaseView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mColumnCount = UiUtils.getFeaturedGridColumnCount(paramContext.getResources(), 1.0D);
  }
  
  public final void bindData$5dbdb9dd(Document paramDocument, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, PlayStoreUiElementNode paramPlayStoreUiElementNode, PlayCardDismissListener paramPlayCardDismissListener, int paramInt1, int paramInt2, Integer paramInteger, boolean paramBoolean)
  {
    this.mPlayStoreUiElementType = paramInt1;
    configureLogging(paramDocument.mDocument.serverLogsCookie, paramPlayStoreUiElementNode);
    PlayCardUtils.bindCard(this.mPlayCardView, paramDocument.getChildAt(0), paramDocument.mDocument.docid, paramBitmapLoader, paramNavigationManager, false, paramPlayCardDismissListener, getParentOfChildren(), true, -1, false);
    this.mHeader.setContent(paramDocument.mDocument.backendId, paramDocument.mDocument.title, paramDocument.mDocument.subtitle, null, null, paramBitmapLoader, null, paramInt2, null, paramInteger);
    View localView = this.mClusterContentView.findViewById(2131755325);
    if (paramBoolean) {}
    for (int i = 0;; i = 8)
    {
      localView.setVisibility(i);
      return;
    }
  }
  
  protected int getPlayStoreUiElementType()
  {
    return this.mPlayStoreUiElementType;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mHeader = ((PlayCardClusterViewHeader)findViewById(2131755874));
    this.mClusterContentView = ((CardLinearLayout)findViewById(2131755875));
    this.mPromoContentView = ((FrameLayout)findViewById(2131755876));
    this.mPlayCardView = ((PlayCardViewBase)findViewById(2131755454));
  }
  
  public void setContentHorizontalPadding(int paramInt)
  {
    setPadding(paramInt, getPaddingTop(), paramInt, getPaddingBottom());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardPromoClusterBaseView
 * JD-Core Version:    0.7.0.1
 */