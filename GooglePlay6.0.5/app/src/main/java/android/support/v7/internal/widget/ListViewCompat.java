package android.support.v7.internal.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.lang.reflect.Field;

public class ListViewCompat
  extends ListView
{
  private static final int[] STATE_SET_NOTHING = { 0 };
  public Field mIsChildViewEnabled;
  public int mMotionPosition;
  public int mSelectionBottomPadding = 0;
  public int mSelectionLeftPadding = 0;
  public int mSelectionRightPadding = 0;
  public int mSelectionTopPadding = 0;
  private GateKeeperDrawable mSelector;
  public final Rect mSelectorRect = new Rect();
  
  public ListViewCompat(Context paramContext, int paramInt)
  {
    super(paramContext, null, paramInt);
    try
    {
      this.mIsChildViewEnabled = AbsListView.class.getDeclaredField("mIsChildViewEnabled");
      this.mIsChildViewEnabled.setAccessible(true);
      return;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      localNoSuchFieldException.printStackTrace();
    }
  }
  
  protected void dispatchDraw(Canvas paramCanvas)
  {
    if (!this.mSelectorRect.isEmpty())
    {
      Drawable localDrawable = getSelector();
      if (localDrawable != null)
      {
        localDrawable.setBounds(this.mSelectorRect);
        localDrawable.draw(paramCanvas);
      }
    }
    super.dispatchDraw(paramCanvas);
  }
  
  public void drawableStateChanged()
  {
    boolean bool = true;
    super.drawableStateChanged();
    setSelectorEnabled(bool);
    Drawable localDrawable = getSelector();
    if (localDrawable != null) {
      if ((!touchModeDrawsInPressedStateCompat()) || (!isPressed())) {
        break label48;
      }
    }
    for (;;)
    {
      if (bool) {
        localDrawable.setState(getDrawableState());
      }
      return;
      label48:
      bool = false;
    }
  }
  
  public final int measureHeightOfChildrenCompat$2e71581f$4868d301(int paramInt1, int paramInt2)
  {
    int i = getListPaddingTop();
    int j = getListPaddingBottom();
    getListPaddingLeft();
    getListPaddingRight();
    int k = getDividerHeight();
    Drawable localDrawable = getDivider();
    ListAdapter localListAdapter = getAdapter();
    if (localListAdapter == null)
    {
      paramInt2 = i + j;
      return paramInt2;
    }
    int m = i + j;
    int n;
    label71:
    View localView;
    int i3;
    label89:
    ViewGroup.LayoutParams localLayoutParams;
    if ((k > 0) && (localDrawable != null))
    {
      n = k;
      localView = null;
      int i1 = 0;
      int i2 = localListAdapter.getCount();
      i3 = 0;
      if (i3 >= i2) {
        break label243;
      }
      int i4 = localListAdapter.getItemViewType(i3);
      if (i4 != i1)
      {
        localView = null;
        i1 = i4;
      }
      localView = localListAdapter.getView(i3, localView, this);
      localLayoutParams = localView.getLayoutParams();
      if (localLayoutParams == null)
      {
        localLayoutParams = generateDefaultLayoutParams();
        localView.setLayoutParams(localLayoutParams);
      }
      if (localLayoutParams.height <= 0) {
        break label233;
      }
    }
    label233:
    for (int i5 = View.MeasureSpec.makeMeasureSpec(localLayoutParams.height, 1073741824);; i5 = View.MeasureSpec.makeMeasureSpec(0, 0))
    {
      localView.measure(paramInt1, i5);
      localView.forceLayout();
      if (i3 > 0) {
        m += n;
      }
      m += localView.getMeasuredHeight();
      if (m >= paramInt2) {
        break;
      }
      i3++;
      break label89;
      n = 0;
      break label71;
    }
    label243:
    return m;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    }
    for (;;)
    {
      return super.onTouchEvent(paramMotionEvent);
      this.mMotionPosition = pointToPosition((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
    }
  }
  
  public void setSelector(Drawable paramDrawable)
  {
    if (paramDrawable != null) {}
    for (GateKeeperDrawable localGateKeeperDrawable = new GateKeeperDrawable(paramDrawable);; localGateKeeperDrawable = null)
    {
      this.mSelector = localGateKeeperDrawable;
      super.setSelector(this.mSelector);
      Rect localRect = new Rect();
      if (paramDrawable != null) {
        paramDrawable.getPadding(localRect);
      }
      this.mSelectionLeftPadding = localRect.left;
      this.mSelectionTopPadding = localRect.top;
      this.mSelectionRightPadding = localRect.right;
      this.mSelectionBottomPadding = localRect.bottom;
      return;
    }
  }
  
  public void setSelectorEnabled(boolean paramBoolean)
  {
    if (this.mSelector != null) {
      this.mSelector.mEnabled = paramBoolean;
    }
  }
  
  public boolean touchModeDrawsInPressedStateCompat()
  {
    return false;
  }
  
  private static final class GateKeeperDrawable
    extends DrawableWrapper
  {
    boolean mEnabled = true;
    
    public GateKeeperDrawable(Drawable paramDrawable)
    {
      super();
    }
    
    public final void draw(Canvas paramCanvas)
    {
      if (this.mEnabled) {
        super.draw(paramCanvas);
      }
    }
    
    public final void setHotspot(float paramFloat1, float paramFloat2)
    {
      if (this.mEnabled) {
        super.setHotspot(paramFloat1, paramFloat2);
      }
    }
    
    public final void setHotspotBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      if (this.mEnabled) {
        super.setHotspotBounds(paramInt1, paramInt2, paramInt3, paramInt4);
      }
    }
    
    public final boolean setState(int[] paramArrayOfInt)
    {
      if (this.mEnabled) {
        return super.setState(paramArrayOfInt);
      }
      return false;
    }
    
    public final boolean setVisible(boolean paramBoolean1, boolean paramBoolean2)
    {
      if (this.mEnabled) {
        return super.setVisible(paramBoolean1, paramBoolean2);
      }
      return false;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.ListViewCompat
 * JD-Core Version:    0.7.0.1
 */