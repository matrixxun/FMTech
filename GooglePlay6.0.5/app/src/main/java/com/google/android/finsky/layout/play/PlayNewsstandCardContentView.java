package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.NewsstandArticleFragment;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.NewsArticleContainer;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.PlayTextView;

public class PlayNewsstandCardContentView
  extends LinearLayout
{
  public PlayTextView mArticleSnippet;
  public TextView mArticleTitle;
  public TextView mPublishedDate;
  public FifeImageView mPublisherImage;
  public TextView mPublisherName;
  
  public PlayNewsstandCardContentView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayNewsstandCardContentView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mArticleTitle = ((TextView)findViewById(2131755935));
    this.mArticleSnippet = ((PlayTextView)findViewById(2131755936));
    this.mPublisherName = ((TextView)findViewById(2131755938));
    this.mPublishedDate = ((TextView)findViewById(2131755939));
    this.mPublisherImage = ((FifeImageView)findViewById(2131755937));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayNewsstandCardContentView
 * JD-Core Version:    0.7.0.1
 */