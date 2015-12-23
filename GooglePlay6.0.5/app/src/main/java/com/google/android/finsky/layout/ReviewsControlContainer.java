package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.adapters.ReviewsAdapter.ChooseListingOptionsHandler;

public class ReviewsControlContainer
  extends LinearLayout
{
  public TextView mFilterBox;
  public TextView mSortBox;
  
  public ReviewsControlContainer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mSortBox = ((TextView)findViewById(2131756066));
    this.mFilterBox = ((TextView)findViewById(2131756067));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ReviewsControlContainer
 * JD-Core Version:    0.7.0.1
 */