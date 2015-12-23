package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.BucketRow;
import com.google.android.finsky.layout.ReviewSnippetLayout;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import java.util.ArrayList;
import java.util.List;

public class ReviewSnippetsModuleLayout
  extends BucketsLinearLayout<SnippetItem>
{
  private View.OnClickListener mAllReviewsClickListener;
  private ReviewSnippetClickListener mReviewSnippetClickListener;
  
  public ReviewSnippetsModuleLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ReviewSnippetsModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private List<ReviewSnippetLayout> getSnippetLayoutChildren()
  {
    ArrayList localArrayList = new ArrayList(getChildCount());
    for (int i = 0; i < getChildCount(); i++)
    {
      View localView1 = getChildAt(i);
      if ((localView1 instanceof BucketRow))
      {
        BucketRow localBucketRow = (BucketRow)localView1;
        for (int j = 0; j < localBucketRow.getChildCount(); j++)
        {
          View localView2 = localBucketRow.getChildAt(j);
          if ((localView2 instanceof ReviewSnippetLayout)) {
            localArrayList.add((ReviewSnippetLayout)localView2);
          }
        }
      }
    }
    return localArrayList;
  }
  
  public final void bind$ba0d3f4(List<SnippetItem> paramList, Document paramDocument, NavigationManager paramNavigationManager, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super.bindRows(paramList, paramDocument, true, paramNavigationManager, paramPlayStoreUiElementNode);
    this.mFooter.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        if (ReviewSnippetsModuleLayout.this.mAllReviewsClickListener != null) {
          ReviewSnippetsModuleLayout.this.mAllReviewsClickListener.onClick(paramAnonymousView);
        }
      }
    });
  }
  
  protected int getBucketRowLayout()
  {
    return 2130969074;
  }
  
  protected String getFooterText()
  {
    return getContext().getString(2131362698).toUpperCase();
  }
  
  protected TextView getFooterView()
  {
    return (TextView)findViewById(2131756063);
  }
  
  protected final int getItemsPerRow(Resources paramResources)
  {
    return paramResources.getInteger(2131623954);
  }
  
  protected final int getMaxRows(Resources paramResources)
  {
    return paramResources.getInteger(2131623953);
  }
  
  protected String getSectionTitleText()
  {
    return getContext().getString(2131362699).toUpperCase();
  }
  
  protected TextView getSectionTitleView()
  {
    return (TextView)findViewById(2131756062);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    boolean bool = false;
    List localList = getSnippetLayoutChildren();
    for (int i = 0; (i < localList.size()) && (!bool); i++) {
      bool = ((ReviewSnippetLayout)localList.get(i)).mIsContentCentered;
    }
    if (bool)
    {
      int j = 0;
      for (int k = 0; k < localList.size(); k++)
      {
        ReviewSnippetLayout localReviewSnippetLayout = (ReviewSnippetLayout)localList.get(k);
        if (!localReviewSnippetLayout.mIsContentCentered)
        {
          localReviewSnippetLayout.mHeader.mUseTwoLineLayout = true;
          localReviewSnippetLayout.mBody.setGravity(1);
          j = 1;
        }
      }
      if (j != 0) {
        super.onMeasure(paramInt1, paramInt2);
      }
    }
  }
  
  public void setAllReviewsClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mAllReviewsClickListener = paramOnClickListener;
  }
  
  public void setReviewSnippetClickListener(ReviewSnippetClickListener paramReviewSnippetClickListener)
  {
    this.mReviewSnippetClickListener = paramReviewSnippetClickListener;
  }
  
  protected final boolean shouldShowFooter(List<SnippetItem> paramList)
  {
    return true;
  }
  
  public static abstract interface ReviewSnippetClickListener
  {
    public abstract void onReviewSnippetClick(ReviewSnippetsModuleLayout.SnippetItem paramSnippetItem);
  }
  
  public static final class SnippetItem
  {
    public final String body;
    public final int count;
    public final String tipUrl;
    public final String title;
    public final boolean truncatedEnd;
    public final boolean truncatedStart;
    
    public SnippetItem(String paramString1, String paramString2, int paramInt, String paramString3, boolean paramBoolean1, boolean paramBoolean2)
    {
      this.title = paramString1;
      this.body = paramString2;
      this.count = paramInt;
      this.tipUrl = paramString3;
      this.truncatedStart = paramBoolean1;
      this.truncatedEnd = paramBoolean2;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.ReviewSnippetsModuleLayout
 * JD-Core Version:    0.7.0.1
 */