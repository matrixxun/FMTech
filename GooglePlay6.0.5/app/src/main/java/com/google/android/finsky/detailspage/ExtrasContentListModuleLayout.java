package com.google.android.finsky.detailspage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.ExtrasItemSnippet;
import com.google.android.finsky.layout.ExtrasItemSnippet.OnCollapsedStateChanged;

public class ExtrasContentListModuleLayout
  extends LinearLayout
  implements ExtrasItemSnippet.OnCollapsedStateChanged
{
  ExtrasItemSelectionListener mExtrasItemSelectionListener;
  boolean mHasBinded;
  View mHeader;
  TextView mSubtitle;
  TextView mTitle;
  
  public ExtrasContentListModuleLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public ExtrasContentListModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public ExtrasContentListModuleLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public final void onCollapsedStateChanged$2f40e1a3(ExtrasItemSnippet paramExtrasItemSnippet)
  {
    int i = getChildCount();
    for (int j = 1; j < i; j++)
    {
      ExtrasItemSnippet localExtrasItemSnippet = (ExtrasItemSnippet)getChildAt(j);
      if ((localExtrasItemSnippet != paramExtrasItemSnippet) && (localExtrasItemSnippet.mExpandedContent != null)) {
        localExtrasItemSnippet.mExpandedContent.setVisibility(8);
      }
    }
    if (paramExtrasItemSnippet.isExpanded())
    {
      this.mExtrasItemSelectionListener.onExtrasItemSelected(paramExtrasItemSnippet.getDocument());
      return;
    }
    this.mExtrasItemSelectionListener.onExtrasItemSelected(null);
  }
  
  public void onFinishInflate()
  {
    this.mHeader = findViewById(2131755495);
    this.mTitle = ((TextView)findViewById(2131755496));
    this.mSubtitle = ((TextView)findViewById(2131755497));
  }
  
  public static abstract interface ExtrasItemSelectionListener
  {
    public abstract void onExtrasItemSelected(Document paramDocument);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.ExtrasContentListModuleLayout
 * JD-Core Version:    0.7.0.1
 */