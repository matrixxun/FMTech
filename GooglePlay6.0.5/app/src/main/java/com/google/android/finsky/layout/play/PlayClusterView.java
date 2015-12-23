package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.adapters.Recyclable;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.IdentifiableRelativeLayout;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.play.image.BitmapLoader;

public abstract class PlayClusterView
  extends IdentifiableRelativeLayout
  implements Recyclable
{
  public boolean mHasCustomUiElementNode;
  protected PlayCardClusterViewHeader mHeader;
  private final boolean mIsH20Enabled;
  private PlayStoreUiElementNode mParentOfChildren;
  public GenericUiElementNode mPlayClusterUiElementNode;
  private final int mVerticalPadding;
  
  public PlayClusterView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayClusterView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mVerticalPadding = paramContext.getResources().getDimensionPixelSize(2131493401);
    this.mIsH20Enabled = FinskyApp.get().getExperiments().isH20StoreEnabled();
  }
  
  protected void configureLogging(byte[] paramArrayOfByte, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    if (this.mPlayClusterUiElementNode == null) {
      this.mPlayClusterUiElementNode = new GenericUiElementNode(0, null, null, null);
    }
    if (!this.mHasCustomUiElementNode) {
      this.mPlayClusterUiElementNode.reset(getPlayStoreUiElementType(), paramArrayOfByte, null, paramPlayStoreUiElementNode);
    }
    if (paramArrayOfByte != null)
    {
      this.mParentOfChildren = this.mPlayClusterUiElementNode;
      return;
    }
    this.mParentOfChildren = paramPlayStoreUiElementNode;
  }
  
  public PlayStoreUiElementNode getParentOfChildren()
  {
    return this.mParentOfChildren;
  }
  
  public PlayStoreUiElementNode getPlayStoreUiElementNode()
  {
    return this.mPlayClusterUiElementNode;
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 400;
  }
  
  public final boolean hasHeader()
  {
    return this.mHeader != null;
  }
  
  public final void hideHeader()
  {
    this.mHeader.setVisibility(8);
    ViewCompat.setPaddingRelative(this, 0, 0, 0, 0);
  }
  
  protected final void logEmptyClusterImpression()
  {
    if (this.mPlayClusterUiElementNode != null) {
      this.mPlayClusterUiElementNode.getParentNode().childImpression(this.mPlayClusterUiElementNode);
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mHeader = ((PlayCardClusterViewHeader)findViewById(2131755380));
  }
  
  public void onRecycle()
  {
    this.mParentOfChildren = null;
    if (this.mPlayClusterUiElementNode != null) {
      this.mPlayClusterUiElementNode.reset(0, null, null, null);
    }
    if (this.mHasCustomUiElementNode)
    {
      this.mHasCustomUiElementNode = false;
      this.mPlayClusterUiElementNode = null;
    }
  }
  
  public final void showHeader(int paramInt1, String paramString1, String paramString2, String paramString3, View.OnClickListener paramOnClickListener, int paramInt2)
  {
    showHeader(paramInt1, paramString1, paramString2, paramString3, paramOnClickListener, paramInt2, null, null);
  }
  
  public final void showHeader(int paramInt1, String paramString1, String paramString2, String paramString3, View.OnClickListener paramOnClickListener, int paramInt2, Common.Image paramImage, BitmapLoader paramBitmapLoader)
  {
    this.mHeader.setContent(paramInt1, paramString1, paramString2, paramString3, paramOnClickListener, paramImage, paramBitmapLoader);
    this.mHeader.setVisibility(0);
    this.mHeader.setExtraHorizontalPadding(paramInt2);
    ViewCompat.setPaddingRelative(this, 0, this.mVerticalPadding, 0, this.mVerticalPadding);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayClusterView
 * JD-Core Version:    0.7.0.1
 */