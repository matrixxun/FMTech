package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.layout.ForegroundLinearLayout;
import com.google.android.finsky.protos.Browse.QuickLink;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.utils.PlayAccessibilityHelper;
import java.util.List;

public abstract class PlayQuickLinksBannerItemBaseView
  extends ForegroundLinearLayout
  implements PlayStoreUiElementNode
{
  protected int mFillColor;
  protected FifeImageView mIconView;
  private PlayStoreUiElementNode mParentNode;
  protected TextView mTitleView;
  private PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(101);
  
  public PlayQuickLinksBannerItemBaseView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayQuickLinksBannerItemBaseView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PlayQuickLinksBannerItemBaseView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void bindData(Browse.QuickLink paramQuickLink, BitmapLoader paramBitmapLoader, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    Context localContext = getContext();
    int i = paramQuickLink.backendId;
    this.mFillColor = localContext.getResources().getColor(CorpusResourceUtils.getSecondaryColorResId(i));
    if ((this.mIconView != null) && (paramQuickLink.image != null)) {
      this.mIconView.setImage(paramQuickLink.image.imageUrl, paramQuickLink.image.supportsFifeUrlOptions, paramBitmapLoader);
    }
    if (paramQuickLink.hasName)
    {
      this.mTitleView.setText(paramQuickLink.name);
      setContentDescription(paramQuickLink.name);
    }
    FinskyEventLog.setServerLogCookie(this.mUiElementProto, paramQuickLink.serverLogsCookie);
    this.mParentNode = paramPlayStoreUiElementNode;
    getParentNode().childImpression(this);
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    throw new IllegalStateException("unwanted children");
  }
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    boolean bool = super.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent);
    paramAccessibilityEvent.getText().clear();
    return bool;
  }
  
  public int getIconWidth()
  {
    if (this.mIconView == null) {
      return 0;
    }
    return this.mIconView.getLayoutParams().width;
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mTitleView = ((TextView)findViewById(2131755458));
    this.mIconView = ((FifeImageView)findViewById(2131755950));
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    PlayAccessibilityHelper.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo, Button.class.getName());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayQuickLinksBannerItemBaseView
 * JD-Core Version:    0.7.0.1
 */