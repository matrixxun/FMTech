package com.google.android.play.article;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.play.R.layout;

public class NewsstandArticleView
  extends FrameLayout
{
  private final NewsstandArticleRenderer articleRenderer = new NewsstandArticleRenderer(getContext());
  private final View errorView;
  private final View loadingView;
  
  public NewsstandArticleView(Context paramContext)
  {
    this(paramContext, null, 0);
  }
  
  public NewsstandArticleView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public NewsstandArticleView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    addView(this.articleRenderer);
    this.loadingView = createView(R.layout.play_article_loading_view);
    addView(this.loadingView);
    this.errorView = createView(R.layout.play_article_error_view);
    addView(this.errorView);
  }
  
  private View createView(int paramInt)
  {
    return LayoutInflater.from(getContext()).inflate(paramInt, this, false);
  }
  
  public void setArticleRendererVisible(boolean paramBoolean)
  {
    NewsstandArticleRenderer localNewsstandArticleRenderer = this.articleRenderer;
    if (paramBoolean) {}
    for (int i = 0;; i = 4)
    {
      localNewsstandArticleRenderer.setVisibility(i);
      return;
    }
  }
  
  public void setErrorViewVisible(boolean paramBoolean)
  {
    View localView = this.errorView;
    if (paramBoolean) {}
    for (int i = 0;; i = 4)
    {
      localView.setVisibility(i);
      return;
    }
  }
  
  public void setLoadingViewVisible(boolean paramBoolean)
  {
    View localView = this.loadingView;
    if (paramBoolean) {}
    for (int i = 0;; i = 4)
    {
      localView.setVisibility(i);
      return;
    }
  }
  
  public void setOnArticleScrollListener(OnArticleScrollListener paramOnArticleScrollListener)
  {
    this.articleRenderer.mOnArticleScrollListener = paramOnArticleScrollListener;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.article.NewsstandArticleView
 * JD-Core Version:    0.7.0.1
 */