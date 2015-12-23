package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ButtonBar
  extends LinearLayout
  implements View.OnClickListener
{
  private static boolean SHOW_TOP_SEPARATOR;
  private ClickListener mClickListener;
  private Button mNegativeButton;
  private Button mPositiveButton;
  private Paint mTopSeparatorPaint;
  
  static
  {
    if ((Build.VERSION.SDK_INT >= 11) && (Build.VERSION.SDK_INT < 21)) {}
    for (boolean bool = true;; bool = false)
    {
      SHOW_TOP_SEPARATOR = bool;
      return;
    }
  }
  
  public ButtonBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (SHOW_TOP_SEPARATOR)
    {
      this.mTopSeparatorPaint = new Paint();
      this.mTopSeparatorPaint.setColor(paramContext.getResources().getColor(2131689644));
      this.mTopSeparatorPaint.setStrokeWidth(1.0F);
    }
  }
  
  public void onClick(View paramView)
  {
    if (this.mClickListener != null)
    {
      if (paramView != this.mPositiveButton) {
        break label25;
      }
      this.mClickListener.onPositiveButtonClick();
    }
    label25:
    while (paramView != this.mNegativeButton) {
      return;
    }
    this.mClickListener.onNegativeButtonClick();
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.mTopSeparatorPaint != null) {
      paramCanvas.drawLine(0.0F, 0.0F, getWidth(), 0.0F, this.mTopSeparatorPaint);
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mPositiveButton = ((Button)findViewById(2131755302));
    this.mNegativeButton = ((Button)findViewById(2131755301));
    this.mPositiveButton.setText(this.mPositiveButton.getText().toString().toUpperCase());
    this.mNegativeButton.setText(this.mNegativeButton.getText().toString().toUpperCase());
  }
  
  public void setClickListener(ClickListener paramClickListener)
  {
    this.mClickListener = paramClickListener;
    this.mPositiveButton.setOnClickListener(this);
    this.mNegativeButton.setOnClickListener(this);
  }
  
  public void setNegativeButtonEnabled(boolean paramBoolean)
  {
    this.mNegativeButton.setEnabled(paramBoolean);
  }
  
  public void setNegativeButtonTextColor(int paramInt)
  {
    this.mNegativeButton.setTextColor(paramInt);
  }
  
  public void setNegativeButtonTitle(int paramInt)
  {
    setNegativeButtonTitle(getResources().getString(paramInt));
  }
  
  public void setNegativeButtonTitle(String paramString)
  {
    this.mNegativeButton.setText(paramString.toUpperCase());
  }
  
  public void setNegativeButtonVisible(boolean paramBoolean)
  {
    Button localButton = this.mNegativeButton;
    if (paramBoolean) {}
    for (int i = 0;; i = 8)
    {
      localButton.setVisibility(i);
      return;
    }
  }
  
  public void setPositiveButtonEnabled(boolean paramBoolean)
  {
    this.mPositiveButton.setEnabled(paramBoolean);
  }
  
  public void setPositiveButtonTextColor(int paramInt)
  {
    this.mPositiveButton.setTextColor(paramInt);
  }
  
  public void setPositiveButtonTitle(int paramInt)
  {
    setPositiveButtonTitle(getResources().getString(paramInt));
  }
  
  public void setPositiveButtonTitle(String paramString)
  {
    this.mPositiveButton.setText(paramString.toUpperCase());
  }
  
  public void setPositiveButtonVisible(boolean paramBoolean)
  {
    Button localButton = this.mPositiveButton;
    if (paramBoolean) {}
    for (int i = 0;; i = 8)
    {
      localButton.setVisibility(i);
      return;
    }
  }
  
  public static abstract interface ClickListener
  {
    public abstract void onNegativeButtonClick();
    
    public abstract void onPositiveButtonClick();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ButtonBar
 * JD-Core Version:    0.7.0.1
 */