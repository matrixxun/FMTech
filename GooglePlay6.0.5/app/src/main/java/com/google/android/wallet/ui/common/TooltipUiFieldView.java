package com.google.android.wallet.ui.common;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.config.G.images;
import com.google.android.wallet.uicomponents.R.attr;
import com.google.android.wallet.uicomponents.R.dimen;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.TooltipOuterClass.Tooltip;

public final class TooltipUiFieldView
  extends LinearLayout
  implements View.OnClickListener, View.OnFocusChangeListener, FieldContainer
{
  private boolean mIconHiddenWhenUnfocused;
  private OnTooltipIconClickListener mOnTooltipIconClickListener;
  private TooltipOuterClass.Tooltip mTooltip;
  public ImageWithCaptionView mTooltipIcon;
  private View mUiFieldView;
  
  public TooltipUiFieldView(Context paramContext)
  {
    super(paramContext, null);
  }
  
  private void updateIconVisibility(boolean paramBoolean)
  {
    int i;
    if ((!this.mIconHiddenWhenUnfocused) || (this.mUiFieldView.hasFocus()))
    {
      i = 1;
      if (i == 0) {
        break label42;
      }
    }
    label42:
    for (int j = 0;; j = 4)
    {
      if (this.mTooltipIcon.getVisibility() != j) {
        break label47;
      }
      return;
      i = 0;
      break;
    }
    label47:
    if (paramBoolean)
    {
      if (i != 0)
      {
        WalletUiUtils.animateViewAppearing(this.mTooltipIcon, 0, 0);
        return;
      }
      WalletUiUtils.animateViewDisappearingToInvisible$17e143a3(this.mTooltipIcon, 0);
      return;
    }
    this.mTooltipIcon.setVisibility(j);
  }
  
  public final void addView(View paramView)
  {
    throw new UnsupportedOperationException("Do not directly add views to TooltipUiFieldView.");
  }
  
  public final View getInnerFieldView()
  {
    return this.mUiFieldView;
  }
  
  public final void moveIconToStart()
  {
    if (this.mUiFieldView == null) {
      throw new IllegalStateException("This should not be called before setting the contents.");
    }
    removeView(this.mTooltipIcon);
    super.addView(this.mTooltipIcon, 0);
    LinearLayout.LayoutParams localLayoutParams1 = (LinearLayout.LayoutParams)this.mUiFieldView.getLayoutParams();
    LinearLayout.LayoutParams localLayoutParams2 = (LinearLayout.LayoutParams)this.mTooltipIcon.getLayoutParams();
    MarginLayoutParamsCompat.setMarginEnd(localLayoutParams1, MarginLayoutParamsCompat.getMarginEnd(localLayoutParams2));
    MarginLayoutParamsCompat.setMarginEnd(localLayoutParams2, MarginLayoutParamsCompat.getMarginStart(localLayoutParams2));
    MarginLayoutParamsCompat.setMarginStart(localLayoutParams2, MarginLayoutParamsCompat.getMarginStart(localLayoutParams1));
    MarginLayoutParamsCompat.setMarginStart(localLayoutParams1, 0);
  }
  
  public final void onClick(View paramView)
  {
    if ((paramView == this.mTooltipIcon) && (this.mOnTooltipIconClickListener != null)) {
      this.mOnTooltipIconClickListener.onClick(this.mTooltip);
    }
  }
  
  public final void onFocusChange(View paramView, boolean paramBoolean)
  {
    if (paramView == this.mUiFieldView) {
      updateIconVisibility(true);
    }
  }
  
  public final void setContent(View paramView, TooltipOuterClass.Tooltip paramTooltip, ImageLoader paramImageLoader)
  {
    if (this.mUiFieldView != null) {
      throw new IllegalStateException("setContent should only be called once.");
    }
    if (!(paramView instanceof TextView)) {
      throw new IllegalArgumentException("Tooltips are only supported for text and date UI fields.");
    }
    this.mUiFieldView = paramView;
    super.addView(paramView);
    LinearLayout.LayoutParams localLayoutParams1 = (LinearLayout.LayoutParams)paramView.getLayoutParams();
    localLayoutParams1.width = 0;
    localLayoutParams1.weight = 1.0F;
    this.mUiFieldView.setOnFocusChangeListener(this);
    this.mTooltip = paramTooltip;
    Context localContext = getContext();
    int[] arrayOfInt = new int[2];
    arrayOfInt[0] = R.attr.uicFormTooltipIconSize;
    arrayOfInt[1] = R.attr.uicClickableBackground;
    TypedArray localTypedArray = localContext.obtainStyledAttributes(arrayOfInt);
    int i = localTypedArray.getDimensionPixelSize(0, 0);
    int j = localTypedArray.getResourceId(1, 0);
    localTypedArray.recycle();
    this.mTooltipIcon = new ImageWithCaptionView(getContext());
    LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(i, i);
    localLayoutParams2.gravity = 17;
    MarginLayoutParamsCompat.setMarginStart(localLayoutParams2, getResources().getDimensionPixelSize(R.dimen.wallet_uic_margin_touchable));
    MarginLayoutParamsCompat.setMarginEnd(localLayoutParams2, MarginLayoutParamsCompat.getMarginEnd(localLayoutParams1));
    MarginLayoutParamsCompat.setMarginEnd(localLayoutParams1, 0);
    addView(this.mTooltipIcon, localLayoutParams2);
    this.mTooltipIcon.setImageWithCaption(paramTooltip.icon, paramImageLoader, ((Boolean)G.images.useWebPForFife.get()).booleanValue());
    this.mTooltipIcon.setBackgroundResource(j);
    updateIconVisibility(false);
    this.mTooltipIcon.setOnClickListener(this);
  }
  
  public final void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    if (this.mUiFieldView != null) {
      this.mUiFieldView.setEnabled(paramBoolean);
    }
    this.mTooltipIcon.setEnabled(paramBoolean);
  }
  
  public final void setIconHiddenWhenUnfocused(boolean paramBoolean)
  {
    this.mIconHiddenWhenUnfocused = paramBoolean;
    if (this.mUiFieldView != null) {
      updateIconVisibility(false);
    }
  }
  
  public final void setOnTooltipIconClickListener(OnTooltipIconClickListener paramOnTooltipIconClickListener)
  {
    this.mOnTooltipIconClickListener = paramOnTooltipIconClickListener;
  }
  
  public static abstract interface OnTooltipIconClickListener
  {
    public abstract void onClick(TooltipOuterClass.Tooltip paramTooltip);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.TooltipUiFieldView
 * JD-Core Version:    0.7.0.1
 */