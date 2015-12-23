package com.google.android.finsky.detailspage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.layout.ReviewItemLayout;
import com.google.android.finsky.layout.play.PlayRatingBar;
import com.google.android.finsky.layout.play.PlayRatingBar.OnRatingChangeListener;
import com.google.android.play.image.FifeImageView;

public class RateReviewModuleLayout
  extends LinearLayout
{
  View mDisclaimer;
  boolean mHasBinded;
  FifeImageView mMyAvatar;
  TextView mMyDisplayName;
  PlayRatingBar mMyRatingBar;
  TextView mMyRatingText;
  RateReviewClickListener mRateReviewClickListener;
  LinearLayout mRatingLayout;
  ReviewItemLayout mReviewItemLayout;
  
  public RateReviewModuleLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public RateReviewModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mMyAvatar = ((FifeImageView)findViewById(2131755981));
    this.mMyDisplayName = ((TextView)findViewById(2131755982));
    this.mMyRatingText = ((TextView)findViewById(2131755983));
    this.mMyRatingBar = ((PlayRatingBar)findViewById(2131755996));
    this.mReviewItemLayout = ((ReviewItemLayout)findViewById(2131756044));
    this.mRatingLayout = ((LinearLayout)findViewById(2131755995));
    this.mDisclaimer = findViewById(2131755997);
  }
  
  public static abstract interface RateReviewClickListener
  {
    public abstract void onEditClicked();
    
    public abstract void onRatingClicked(int paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.RateReviewModuleLayout
 * JD-Core Version:    0.7.0.1
 */