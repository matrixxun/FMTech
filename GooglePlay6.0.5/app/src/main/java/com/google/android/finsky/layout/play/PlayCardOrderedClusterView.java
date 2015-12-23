package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.BucketRow;
import com.google.android.finsky.layout.IdentifiableLinearLayout;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.PlayCardUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.PlayCardViewBase;

public class PlayCardOrderedClusterView
  extends IdentifiableLinearLayout
  implements PlayStoreUiElementNode
{
  private int mCardContentHorizontalPadding;
  private int mColumnCount;
  protected LinearLayout mContent;
  protected PlayCardClusterViewHeader mHeader;
  public PlayStoreUiElementNode mParentNode = null;
  private int mRowCount;
  public PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(400);
  
  public PlayCardOrderedClusterView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardOrderedClusterView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private int getNumDisplayedRows(int paramInt)
  {
    return Math.min((int)Math.ceil(paramInt / this.mColumnCount), this.mRowCount);
  }
  
  public final void bindEntries(Document paramDocument, Document[] paramArrayOfDocument, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager)
  {
    int i = paramArrayOfDocument.length;
    int j = 0;
    while (j < this.mRowCount)
    {
      ViewGroup localViewGroup = (ViewGroup)this.mContent.getChildAt(j);
      if (j >= getNumDisplayedRows(i))
      {
        localViewGroup.setVisibility(8);
        j++;
      }
      else
      {
        localViewGroup.setVisibility(0);
        int k;
        label74:
        int m;
        label77:
        PlayCardViewBase localPlayCardViewBase;
        if (j >= getNumDisplayedRows(i))
        {
          k = 0;
          m = 0;
          if (m < this.mColumnCount)
          {
            localPlayCardViewBase = (PlayCardViewBase)localViewGroup.getChildAt(m);
            if (m >= k) {
              break label167;
            }
            PlayCardUtils.bindCard(localPlayCardViewBase, paramArrayOfDocument[(m + j * this.mColumnCount)], paramDocument.mDocument.docid, paramBitmapLoader, paramNavigationManager, this);
            localPlayCardViewBase.setVisibility(0);
          }
        }
        for (;;)
        {
          m++;
          break label77;
          break;
          k = Math.min(i - j * this.mColumnCount, this.mColumnCount);
          break label74;
          label167:
          localPlayCardViewBase.setVisibility(4);
        }
      }
    }
  }
  
  public final void bindHeader(int paramInt1, int paramInt2, String paramString, View.OnClickListener paramOnClickListener)
  {
    String str1;
    if (paramInt1 > 0)
    {
      Resources localResources = getResources();
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(paramInt1);
      String str2 = localResources.getString(2131362330, arrayOfObject1);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(paramInt1);
      str1 = String.format(str2, arrayOfObject2);
    }
    for (;;)
    {
      this.mHeader.setContent(paramInt2, paramString, null, str1, paramOnClickListener);
      this.mHeader.setExtraHorizontalPadding(this.mCardContentHorizontalPadding);
      return;
      str1 = null;
      if (paramOnClickListener != null) {
        str1 = getResources().getString(2131362331);
      }
    }
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyEventLog.childImpression(this, paramPlayStoreUiElementNode);
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public final void inflateGrid(int paramInt1, int paramInt2, int paramInt3)
  {
    this.mRowCount = paramInt1;
    this.mColumnCount = paramInt2;
    this.mCardContentHorizontalPadding = paramInt3;
    LayoutInflater localLayoutInflater = LayoutInflater.from(getContext());
    for (int i = 0; i < this.mRowCount; i++)
    {
      BucketRow localBucketRow = (BucketRow)localLayoutInflater.inflate(2130968646, null, false);
      localBucketRow.setContentHorizontalPadding(paramInt3);
      for (int j = 0; j < this.mColumnCount; j++)
      {
        View localView = localLayoutInflater.inflate(2130969190, localBucketRow, false);
        localView.setVisibility(8);
        localBucketRow.addView(localView);
      }
      this.mContent.addView(localBucketRow);
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mContent = ((LinearLayout)findViewById(2131755307));
    this.mHeader = ((PlayCardClusterViewHeader)findViewById(2131755380));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardOrderedClusterView
 * JD-Core Version:    0.7.0.1
 */