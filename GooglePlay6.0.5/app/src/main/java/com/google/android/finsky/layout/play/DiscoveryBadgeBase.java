package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.AccessibleLinearLayout;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Details.DiscoveryBadge;
import com.google.android.finsky.protos.Details.DiscoveryBadgeLink;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;

public abstract class DiscoveryBadgeBase
  extends AccessibleLinearLayout
  implements View.OnClickListener, PlayStoreUiElementNode
{
  private Details.DiscoveryBadge mBadge;
  protected int mBadgeRadius;
  protected int mCurrentFillColor;
  private DfeToc mDfeToc;
  private Document mDoc;
  protected FifeImageView mIcon;
  private NavigationManager mNavigationManager;
  private PackageManager mPackageManager;
  private PlayStoreUiElementNode mParentNode;
  protected TextView mTitle;
  protected PlayStore.PlayStoreUiElement mUiElement;
  
  public DiscoveryBadgeBase(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DiscoveryBadgeBase(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mBadgeRadius = paramContext.getResources().getDimensionPixelSize(2131493009);
    setWillNotDraw(false);
  }
  
  public void bind(Details.DiscoveryBadge paramDiscoveryBadge, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, Document paramDocument, DfeToc paramDfeToc, PackageManager paramPackageManager, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mBadge = paramDiscoveryBadge;
    this.mNavigationManager = paramNavigationManager;
    this.mDfeToc = paramDfeToc;
    this.mPackageManager = paramPackageManager;
    this.mDoc = paramDocument;
    this.mParentNode = paramPlayStoreUiElementNode;
    if (paramDiscoveryBadge.hasBackgroundColor)
    {
      this.mCurrentFillColor = paramDiscoveryBadge.backgroundColor;
      bindIconImage(paramDiscoveryBadge, paramBitmapLoader);
      this.mTitle.setText(paramDiscoveryBadge.title);
      Details.DiscoveryBadgeLink localDiscoveryBadgeLink = this.mBadge.discoveryBadgeLink;
      if ((localDiscoveryBadgeLink != null) && ((localDiscoveryBadgeLink.hasUserReviewsUrl) || (localDiscoveryBadgeLink.hasCriticReviewsUrl) || (localDiscoveryBadgeLink.link != null))) {
        setOnClickListener(this);
      }
      this.mUiElement = FinskyEventLog.obtainPlayStoreUiElement(getPlayStoreUiElementType());
      FinskyEventLog.setServerLogCookie(this.mUiElement, paramDiscoveryBadge.serverLogsCookie);
      this.mTitle.setContentDescription(null);
      if (!TextUtils.isEmpty(paramDiscoveryBadge.contentDescription)) {
        break label194;
      }
      setContentDescription(this.mTitle.getText());
    }
    for (;;)
    {
      this.mParentNode.childImpression(this);
      return;
      this.mCurrentFillColor = CorpusResourceUtils.getPrimaryColor(getContext(), paramDocument.mDocument.backendId);
      break;
      label194:
      setContentDescription(paramDiscoveryBadge.contentDescription);
    }
  }
  
  protected void bindIconImage(Details.DiscoveryBadge paramDiscoveryBadge, BitmapLoader paramBitmapLoader)
  {
    if ((paramDiscoveryBadge.image != null) && (this.mIcon != null))
    {
      onPreImageLoad();
      this.mIcon.setImage(paramDiscoveryBadge.image.imageUrl, paramDiscoveryBadge.image.supportsFifeUrlOptions, paramBitmapLoader);
    }
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    throw new IllegalStateException("unwanted children");
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  protected abstract int getPlayStoreUiElementType();
  
  public void onClick(View paramView)
  {
    Details.DiscoveryBadgeLink localDiscoveryBadgeLink = this.mBadge.discoveryBadgeLink;
    if (localDiscoveryBadgeLink.hasUserReviewsUrl) {
      this.mNavigationManager.goToAllReviews(this.mDoc, localDiscoveryBadgeLink.userReviewsUrl, false);
    }
    for (;;)
    {
      FinskyApp.get().getEventLogger().logClickEvent(this);
      return;
      if (localDiscoveryBadgeLink.hasCriticReviewsUrl) {
        this.mNavigationManager.goToAllReviews(this.mDoc, localDiscoveryBadgeLink.criticReviewsUrl, true);
      } else if (localDiscoveryBadgeLink.link != null) {
        this.mNavigationManager.resolveLink(localDiscoveryBadgeLink.link, this.mBadge.title, this.mDfeToc, this.mPackageManager);
      }
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mIcon = ((FifeImageView)findViewById(2131755150));
    this.mTitle = ((TextView)findViewById(2131755173));
  }
  
  protected void onPreImageLoad() {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.DiscoveryBadgeBase
 * JD-Core Version:    0.7.0.1
 */