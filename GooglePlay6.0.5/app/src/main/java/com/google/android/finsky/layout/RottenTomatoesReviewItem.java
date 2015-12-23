package com.google.android.finsky.layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.finsky.protos.Review;
import com.google.android.play.image.FifeImageView;

public class RottenTomatoesReviewItem
  extends RelativeLayout
{
  public TextView mAuthor;
  public TextView mComment;
  public View mExternalLinkAction;
  public FifeImageView mSentimentImage;
  public TextView mSource;
  
  public RottenTomatoesReviewItem(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public RottenTomatoesReviewItem(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mSentimentImage = ((FifeImageView)findViewById(2131756081));
    this.mExternalLinkAction = findViewById(2131756082);
    this.mComment = ((TextView)findViewById(2131755990));
    this.mAuthor = ((TextView)findViewById(2131756052));
    this.mSource = ((TextView)findViewById(2131756055));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.RottenTomatoesReviewItem
 * JD-Core Version:    0.7.0.1
 */