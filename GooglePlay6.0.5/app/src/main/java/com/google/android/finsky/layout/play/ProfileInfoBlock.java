package com.google.android.finsky.layout.play;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.google.android.play.utils.PlayTouchDelegate;

public class ProfileInfoBlock
  extends LinearLayout
{
  private final Rect mActionButtonArea = new Rect();
  private final Rect mOldActionButtonArea = new Rect();
  private View mProfileActionButton;
  
  public ProfileInfoBlock(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ProfileInfoBlock(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mProfileActionButton = findViewById(2131755213);
  }
  
  @SuppressLint({"DrawAllocation"})
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    int i = this.mProfileActionButton.getHeight() / 2;
    int j = this.mProfileActionButton.getWidth() / 3;
    this.mProfileActionButton.getHitRect(this.mActionButtonArea);
    this.mActionButtonArea.inset(-j, -i);
    if (this.mActionButtonArea.equals(this.mOldActionButtonArea)) {
      return;
    }
    setTouchDelegate(new PlayTouchDelegate(this.mActionButtonArea, this.mProfileActionButton));
    this.mOldActionButtonArea.set(this.mActionButtonArea);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.ProfileInfoBlock
 * JD-Core Version:    0.7.0.1
 */