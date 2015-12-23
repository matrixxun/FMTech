package com.google.android.play.onboard;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;
import com.google.android.play.R.dimen;
import com.google.android.play.R.id;
import com.google.android.play.utils.PlayTouchDelegate;

public class OnboardCenterButton
  extends FrameLayout
{
  private Button mButton;
  private Rect mCurrentTouchRect = new Rect();
  private Rect mNewTouchRect = new Rect();
  private final int mTouchExtension = getResources().getDimensionPixelSize(R.dimen.play_onboard__onboard_v2_touch_extension);
  
  public OnboardCenterButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mButton = ((Button)findViewById(R.id.play_onboard_center_button));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mButton.getVisibility() == 8) {}
    do
    {
      return;
      this.mButton.getHitRect(this.mNewTouchRect);
      Rect localRect1 = this.mNewTouchRect;
      localRect1.left -= this.mTouchExtension;
      Rect localRect2 = this.mNewTouchRect;
      localRect2.top -= this.mTouchExtension;
      Rect localRect3 = this.mNewTouchRect;
      localRect3.right += this.mTouchExtension;
      Rect localRect4 = this.mNewTouchRect;
      localRect4.bottom += this.mTouchExtension;
    } while (this.mCurrentTouchRect.equals(this.mNewTouchRect));
    setTouchDelegate(new PlayTouchDelegate(this.mNewTouchRect, this.mButton));
    this.mCurrentTouchRect.set(this.mNewTouchRect);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.onboard.OnboardCenterButton
 * JD-Core Version:    0.7.0.1
 */