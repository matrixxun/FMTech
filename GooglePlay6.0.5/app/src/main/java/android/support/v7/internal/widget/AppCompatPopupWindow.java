package android.support.v7.internal.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.appcompat.R.styleable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.PopupWindow;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public final class AppCompatPopupWindow
  extends PopupWindow
{
  private static final boolean COMPAT_OVERLAP_ANCHOR;
  private boolean mOverlapAnchor;
  
  static
  {
    if (Build.VERSION.SDK_INT < 21) {}
    for (boolean bool = true;; bool = false)
    {
      COMPAT_OVERLAP_ANCHOR = bool;
      return;
    }
  }
  
  public AppCompatPopupWindow(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes$1a6c1917(paramContext, paramAttributeSet, R.styleable.PopupWindow, paramInt);
    boolean bool;
    if (localTintTypedArray.hasValue(R.styleable.PopupWindow_overlapAnchor))
    {
      bool = localTintTypedArray.getBoolean(R.styleable.PopupWindow_overlapAnchor, false);
      if (!COMPAT_OVERLAP_ANCHOR) {
        break label136;
      }
      this.mOverlapAnchor = bool;
    }
    for (;;)
    {
      setBackgroundDrawable(localTintTypedArray.getDrawable(R.styleable.PopupWindow_android_popupBackground));
      localTintTypedArray.mWrapped.recycle();
      if (Build.VERSION.SDK_INT < 14) {}
      try
      {
        Field localField1 = PopupWindow.class.getDeclaredField("mAnchor");
        localField1.setAccessible(true);
        Field localField2 = PopupWindow.class.getDeclaredField("mOnScrollChangedListener");
        localField2.setAccessible(true);
        localField2.set(this, new ViewTreeObserver.OnScrollChangedListener()
        {
          public final void onScrollChanged()
          {
            try
            {
              WeakReference localWeakReference = (WeakReference)this.val$fieldAnchor.get(jdField_this);
              if (localWeakReference != null)
              {
                if (localWeakReference.get() == null) {
                  return;
                }
                this.val$originalListener.onScrollChanged();
                return;
              }
            }
            catch (IllegalAccessException localIllegalAccessException) {}
          }
        });
        return;
      }
      catch (Exception localException)
      {
        label136:
        Log.d("AppCompatPopupWindow", "Exception while installing workaround OnScrollChangedListener", localException);
      }
      PopupWindowCompat.setOverlapAnchor(this, bool);
    }
  }
  
  public final void showAsDropDown(View paramView, int paramInt1, int paramInt2)
  {
    if ((COMPAT_OVERLAP_ANCHOR) && (this.mOverlapAnchor)) {
      paramInt2 -= paramView.getHeight();
    }
    super.showAsDropDown(paramView, paramInt1, paramInt2);
  }
  
  @TargetApi(19)
  public final void showAsDropDown(View paramView, int paramInt1, int paramInt2, int paramInt3)
  {
    if ((COMPAT_OVERLAP_ANCHOR) && (this.mOverlapAnchor)) {
      paramInt2 -= paramView.getHeight();
    }
    super.showAsDropDown(paramView, paramInt1, paramInt2, paramInt3);
  }
  
  public final void update(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((COMPAT_OVERLAP_ANCHOR) && (this.mOverlapAnchor)) {
      paramInt2 -= paramView.getHeight();
    }
    super.update(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.AppCompatPopupWindow
 * JD-Core Version:    0.7.0.1
 */