package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.TextView;
import com.google.android.play.cardview.CardViewGroupDelegate;
import com.google.android.play.cardview.CardViewGroupDelegates;

public class EditorialDescriptionSection
  extends DetailsTextSection
{
  private boolean mIsExpanded;
  
  public EditorialDescriptionSection(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public EditorialDescriptionSection(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    getCardViewGroupDelegate().initialize(this, paramContext, paramAttributeSet, 0);
  }
  
  private boolean isExpansionNotNeeded()
  {
    return (!this.mBodyContainerView.hasBody()) || (this.mBodyContainerView.getBodyLineCount() <= this.mMaxCollapsedLines);
  }
  
  public CardViewGroupDelegate getCardViewGroupDelegate()
  {
    return CardViewGroupDelegates.IMPL;
  }
  
  protected final void handleClick()
  {
    scrollTo(0, 0);
    if (this.mUrlSpanClicked) {
      this.mUrlSpanClicked = false;
    }
    while (isExpansionNotNeeded()) {
      return;
    }
    if (this.mIsExpanded)
    {
      this.mIsExpanded = false;
      this.mBodyContainerView.setBodyMaxLines(this.mMaxCollapsedLines);
      this.mFooterLabel.setVisibility(0);
      return;
    }
    this.mIsExpanded = true;
    this.mBodyContainerView.setBodyMaxLines(2147483647);
    this.mFooterLabel.setVisibility(8);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
    {
      public final boolean onPreDraw()
      {
        if (EditorialDescriptionSection.this.isExpansionNotNeeded()) {
          EditorialDescriptionSection.this.mFooterLabel.setVisibility(8);
        }
        for (;;)
        {
          EditorialDescriptionSection.this.getViewTreeObserver().removeOnPreDrawListener(this);
          return true;
          EditorialDescriptionSection.this.mFooterLabel.setVisibility(0);
        }
      }
    });
  }
  
  public void setBackgroundColor(int paramInt)
  {
    getCardViewGroupDelegate().setBackgroundColor(this, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.EditorialDescriptionSection
 * JD-Core Version:    0.7.0.1
 */