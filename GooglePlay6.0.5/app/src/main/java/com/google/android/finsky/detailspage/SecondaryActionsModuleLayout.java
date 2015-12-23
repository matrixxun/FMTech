package com.google.android.finsky.detailspage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.layout.AccessibleLinearLayout;
import java.text.NumberFormat;

public class SecondaryActionsModuleLayout
  extends LinearLayout
  implements View.OnClickListener
{
  static final NumberFormat sCountFormatter = ;
  AccessibleLinearLayout mPlusOneButton;
  TextView mPlusOneIcon;
  SecondaryActionsClickListener mSecondaryActionsClickListener;
  AccessibleLinearLayout mShareButton;
  AccessibleLinearLayout mWishlistButton;
  ImageView mWishlistButtonIcon;
  
  public SecondaryActionsModuleLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SecondaryActionsModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public void onClick(View paramView)
  {
    if (this.mSecondaryActionsClickListener == null) {}
    do
    {
      return;
      if (paramView == this.mWishlistButton) {
        this.mSecondaryActionsClickListener.onWishlistClick(paramView);
      }
      if (paramView == this.mShareButton) {
        this.mSecondaryActionsClickListener.onShareClick$3c7ec8c3();
      }
    } while (paramView != this.mPlusOneButton);
    this.mSecondaryActionsClickListener.onPlusOneClick(paramView);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mWishlistButton = ((AccessibleLinearLayout)findViewById(2131756097));
    this.mWishlistButtonIcon = ((ImageView)findViewById(2131756099));
    this.mShareButton = ((AccessibleLinearLayout)findViewById(2131756100));
    this.mPlusOneButton = ((AccessibleLinearLayout)findViewById(2131756101));
    this.mPlusOneIcon = ((TextView)findViewById(2131756102));
    this.mWishlistButton.setOnClickListener(this);
    this.mShareButton.setOnClickListener(this);
    this.mPlusOneButton.setOnClickListener(this);
  }
  
  public static abstract interface SecondaryActionsClickListener
  {
    public abstract void onPlusOneClick(View paramView);
    
    public abstract void onShareClick$3c7ec8c3();
    
    public abstract void onWishlistClick(View paramView);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.SecondaryActionsModuleLayout
 * JD-Core Version:    0.7.0.1
 */