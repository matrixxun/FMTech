package com.google.android.finsky.layout;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.PlayAnimationUtils;
import com.google.android.finsky.utils.PlayCardImageTypeSequence;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.ForegroundRelativeLayout;
import com.google.android.play.layout.PlayCardThumbnail;

public abstract class MyAccountRowView
  extends ForegroundRelativeLayout
  implements Animation.AnimationListener, PlayStoreUiElementNode
{
  private final int mBaseRowHeight = getResources().getDimensionPixelSize(2131493176);
  public final int mBaseRowHeightExpanded = getResources().getDimensionPixelSize(2131493177);
  protected final HeightAnimation mCollapseAnimation;
  private int mCollapsedBackgroundResourceId;
  protected final HeightAnimation mExpandAnimation;
  public boolean mExpanded;
  private PlayStoreUiElementNode mParentNode;
  private int mRowPosition;
  private PlayCardThumbnail mThumbnail;
  private TextView mTitleView;
  private final PlayStore.PlayStoreUiElement mUiElementProto;
  
  protected MyAccountRowView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet);
    this.mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(paramInt);
    this.mExpandAnimation = new HeightAnimation(this);
    this.mExpandAnimation.setDuration(150L);
    this.mExpandAnimation.setAnimationListener(this);
    this.mCollapseAnimation = new HeightAnimation(this);
    this.mCollapseAnimation.setDuration(150L);
    this.mCollapseAnimation.setAnimationListener(this);
  }
  
  protected static void animateFadeIn(View paramView)
  {
    paramView.setVisibility(0);
    if (Build.VERSION.SDK_INT >= 11)
    {
      PlayAnimationUtils.getFadeAnimator$57852d9d(paramView, 0.0F, 150L).start();
      return;
    }
    paramView.startAnimation(PlayAnimationUtils.getFadeInAnimation(paramView.getContext(), 150L, null));
  }
  
  protected final void bind(Document paramDocument, String paramString, BitmapLoader paramBitmapLoader, boolean paramBoolean, PlayStoreUiElementNode paramPlayStoreUiElementNode, int paramInt, NavigationManager paramNavigationManager)
  {
    this.mExpanded = paramBoolean;
    this.mCollapsedBackgroundResourceId = paramInt;
    this.mParentNode = paramPlayStoreUiElementNode;
    this.mTitleView.setText(paramString);
    FinskyEventLog.setServerLogCookie(this.mUiElementProto, paramDocument.mDocument.serverLogsCookie);
    paramPlayStoreUiElementNode.childImpression(this);
    DocImageView localDocImageView = getThumbnailCover();
    if (localDocImageView == null) {}
    do
    {
      return;
      localDocImageView.bind(paramDocument, paramBitmapLoader, PlayCardImageTypeSequence.CORE_IMAGE_TYPES);
      if (NavigationManager.areTransitionsEnabled())
      {
        localDocImageView.setTransitionName("transition_card_details:cover:" + paramDocument.mDocument.docid);
        setTransitionGroup(true);
      }
    } while (!NavigationManager.hasClickListener(paramDocument));
    GenericUiElementNode localGenericUiElementNode = new GenericUiElementNode(2605, null, null, this);
    this.mThumbnail.setOnClickListener(paramNavigationManager.getClickListener(paramDocument, localGenericUiElementNode, null, -1, getThumbnailCover()));
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    throw new IllegalStateException("unexpected children");
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public int getRowPosition()
  {
    return this.mRowPosition;
  }
  
  protected DocImageView getThumbnailCover()
  {
    if (this.mThumbnail != null) {
      return (DocImageView)this.mThumbnail.getImageView();
    }
    return null;
  }
  
  public void onAnimationRepeat(Animation paramAnimation) {}
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mTitleView = ((TextView)findViewById(2131755173));
    this.mThumbnail = ((PlayCardThumbnail)findViewById(2131755456));
  }
  
  public void setRowPosition(int paramInt)
  {
    this.mRowPosition = paramInt;
  }
  
  protected final void setUpToggleAnimations()
  {
    ViewGroup.LayoutParams localLayoutParams = getLayoutParams();
    if (this.mExpanded) {}
    for (int i = this.mBaseRowHeightExpanded;; i = this.mBaseRowHeight)
    {
      localLayoutParams.height = i;
      this.mExpandAnimation.setHeights(this.mBaseRowHeight, this.mBaseRowHeightExpanded);
      this.mCollapseAnimation.setHeights(this.mBaseRowHeightExpanded, this.mBaseRowHeight);
      syncBackground();
      requestLayout();
      return;
    }
  }
  
  public final void syncBackground()
  {
    int i;
    float f;
    if (this.mExpanded)
    {
      i = 2131689753;
      setBackgroundResource(i);
      if (Build.VERSION.SDK_INT >= 21)
      {
        f = getResources().getDimensionPixelSize(2131493382);
        if (!this.mExpanded) {
          break label57;
        }
      }
    }
    for (;;)
    {
      setElevation(f);
      return;
      i = this.mCollapsedBackgroundResourceId;
      break;
      label57:
      f = 0.0F;
    }
  }
  
  public final void toggleExpansion()
  {
    boolean bool1;
    boolean bool3;
    if (!this.mExpanded)
    {
      bool1 = true;
      this.mExpanded = bool1;
      boolean bool2 = this.mExpanded;
      bool3 = false;
      if (!bool2) {
        break label59;
      }
      label25:
      setClickable(bool3);
      if (!this.mExpanded) {
        break label64;
      }
    }
    label59:
    label64:
    for (HeightAnimation localHeightAnimation = this.mExpandAnimation;; localHeightAnimation = this.mCollapseAnimation)
    {
      startAnimation(localHeightAnimation);
      syncBackground();
      return;
      bool1 = false;
      break;
      bool3 = true;
      break label25;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.MyAccountRowView
 * JD-Core Version:    0.7.0.1
 */