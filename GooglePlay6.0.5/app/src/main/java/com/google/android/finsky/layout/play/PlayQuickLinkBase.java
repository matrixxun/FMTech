package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Browse.QuickLink;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.play.cardview.CardViewGroupDelegate;
import com.google.android.play.cardview.CardViewGroupDelegates;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.ForegroundRelativeLayout;
import com.google.android.play.utils.PlayAccessibilityHelper;
import com.google.android.play.utils.PlayUtils;

public class PlayQuickLinkBase
  extends ForegroundRelativeLayout
  implements PlayStoreUiElementNode
{
  protected FifeImageView mLinkIcon;
  protected TextView mLinkText;
  private PlayStoreUiElementNode mParentNode;
  private PlayStore.PlayStoreUiElement mUiElementProto;
  
  public PlayQuickLinkBase(Context paramContext)
  {
    this(paramContext, null, 0);
  }
  
  public PlayQuickLinkBase(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PlayQuickLinkBase(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    getCardViewGroupDelegate().initialize(this, paramContext, paramAttributeSet, paramInt);
  }
  
  public final void bind(final Browse.QuickLink paramQuickLink, final int paramInt, final NavigationManager paramNavigationManager, final DfeToc paramDfeToc, BitmapLoader paramBitmapLoader, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mLinkIcon.setVisibility(8);
    String str = paramQuickLink.name;
    if (!TextUtils.isEmpty(str)) {
      this.mLinkText.setText(str.toUpperCase());
    }
    Common.Image localImage = paramQuickLink.image;
    Context localContext;
    Resources localResources;
    CardViewGroupDelegate localCardViewGroupDelegate;
    if (localImage != null)
    {
      this.mLinkIcon.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, paramBitmapLoader);
      this.mLinkIcon.setVisibility(0);
      this.mLinkText.setGravity(8388627);
      localContext = getContext();
      localResources = getResources();
      localCardViewGroupDelegate = getCardViewGroupDelegate();
      if ((!paramQuickLink.hasPrismStyle) || (!paramQuickLink.prismStyle)) {
        break label275;
      }
      localCardViewGroupDelegate.setBackgroundColor(this, CorpusResourceUtils.getPrimaryColor(localContext, paramQuickLink.backendId));
      localCardViewGroupDelegate.setCardElevation(this, 0.0F);
      this.mLinkText.setTextColor(localResources.getColor(2131689753));
      setForeground(ContextCompat.getDrawable(localContext, 2130837965));
      label165:
      int i = localResources.getDimensionPixelSize(2131493068);
      setForegroundPadding(i, i, i, i);
      if (paramQuickLink.link == null) {
        break label334;
      }
      setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          PlayQuickLinkBase localPlayQuickLinkBase = PlayQuickLinkBase.this;
          paramNavigationManager.resolveLink(paramQuickLink.link, paramQuickLink.name, paramInt, paramDfeToc, localPlayQuickLinkBase);
        }
      });
      this.mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(101);
      FinskyEventLog.setServerLogCookie(this.mUiElementProto, paramQuickLink.serverLogsCookie);
      this.mParentNode = paramPlayStoreUiElementNode;
      getParentNode().childImpression(this);
    }
    for (;;)
    {
      setContentDescription(str);
      return;
      this.mLinkIcon.setVisibility(8);
      this.mLinkText.setGravity(17);
      break;
      label275:
      localCardViewGroupDelegate.setBackgroundColor(this, localResources.getColor(2131689607));
      localCardViewGroupDelegate.setCardElevation(this, localResources.getDimensionPixelSize(2131493382));
      this.mLinkText.setTextColor(localResources.getColor(2131689625));
      setForeground(ContextCompat.getDrawable(localContext, 2130837967));
      break label165;
      label334:
      setOnClickListener(null);
      setClickable(false);
    }
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    throw new IllegalStateException("unwanted children");
  }
  
  public CardViewGroupDelegate getCardViewGroupDelegate()
  {
    return CardViewGroupDelegates.IMPL;
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
    this.mLinkIcon = ((FifeImageView)findViewById(2131755950));
    this.mLinkText = ((TextView)findViewById(2131755458));
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    PlayAccessibilityHelper.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo, Button.class.getName());
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool;
    int i;
    int j;
    int k;
    if (ViewCompat.getLayoutDirection(this) == 0)
    {
      bool = true;
      i = ViewCompat.getPaddingStart(this);
      j = getPaddingTop();
      k = getWidth();
      if (this.mLinkIcon.getVisibility() != 0) {
        break label205;
      }
    }
    label205:
    for (int m = 1;; m = 0)
    {
      int n = i;
      if (m != 0)
      {
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mLinkIcon.getLayoutParams();
        int i3 = this.mLinkIcon.getMeasuredWidth();
        int i4 = MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams);
        int i5 = MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams);
        int i6 = j + localMarginLayoutParams.topMargin;
        int i7 = PlayUtils.getViewLeftFromParentStart(k, i3, bool, i + i4);
        this.mLinkIcon.layout(i7, i6, i7 + i3, i6 + this.mLinkIcon.getMeasuredHeight());
        n += i5 + (i4 + i3);
      }
      int i1 = this.mLinkText.getMeasuredWidth();
      int i2 = PlayUtils.getViewLeftFromParentStart(k, i1, bool, n);
      this.mLinkText.layout(i2, j, i2 + i1, j + this.mLinkText.getMeasuredHeight());
      return;
      bool = false;
      break;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    if (this.mLinkIcon.getVisibility() == 0) {}
    for (int k = 1;; k = 0)
    {
      int m = i - getPaddingLeft() - getPaddingRight();
      int n = j - getPaddingTop() - getPaddingBottom();
      int i1 = m;
      if (k != 0)
      {
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mLinkIcon.getLayoutParams();
        int i5 = Math.max(n - localMarginLayoutParams.topMargin - localMarginLayoutParams.bottomMargin, 0);
        int i6 = View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
        this.mLinkIcon.measure(i6, i6);
        i1 = m - (i5 + localMarginLayoutParams.leftMargin + localMarginLayoutParams.rightMargin);
      }
      int i2 = Math.max(n, 0);
      int i3 = Math.max(i1, 0);
      int i4 = View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
      this.mLinkText.measure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), i4);
      setMeasuredDimension(i, j);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayQuickLinkBase
 * JD-Core Version:    0.7.0.1
 */