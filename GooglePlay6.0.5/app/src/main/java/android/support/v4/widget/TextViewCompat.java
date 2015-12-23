package android.support.v4.widget;

import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.widget.TextView;

public final class TextViewCompat
{
  static final TextViewCompatImpl IMPL = new BaseTextViewCompatImpl();
  
  static
  {
    int i = Build.VERSION.SDK_INT;
    if (i >= 18)
    {
      IMPL = new JbMr2TextViewCompatImpl();
      return;
    }
    if (i >= 17)
    {
      IMPL = new JbMr1TextViewCompatImpl();
      return;
    }
    if (i >= 16)
    {
      IMPL = new JbTextViewCompatImpl();
      return;
    }
  }
  
  public static void setCompoundDrawablesRelative$16207aff(TextView paramTextView, Drawable paramDrawable)
  {
    IMPL.setCompoundDrawablesRelative(paramTextView, paramDrawable, null, null, null);
  }
  
  public static void setCompoundDrawablesRelativeWithIntrinsicBounds$16207aff(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3)
  {
    IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds(paramTextView, paramDrawable1, paramDrawable2, paramDrawable3, null);
  }
  
  public static void setCompoundDrawablesRelativeWithIntrinsicBounds$30b381af(TextView paramTextView, int paramInt)
  {
    IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds(paramTextView, 0, 0, paramInt, 0);
  }
  
  static class BaseTextViewCompatImpl
    implements TextViewCompat.TextViewCompatImpl
  {
    public void setCompoundDrawablesRelative(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
    {
      paramTextView.setCompoundDrawables(paramDrawable1, null, null, null);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, paramInt3, 0);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
    {
      paramTextView.setCompoundDrawablesWithIntrinsicBounds(paramDrawable1, paramDrawable2, paramDrawable3, null);
    }
  }
  
  static class JbMr1TextViewCompatImpl
    extends TextViewCompat.JbTextViewCompatImpl
  {
    public void setCompoundDrawablesRelative(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
    {
      int i;
      Drawable localDrawable;
      if (paramTextView.getLayoutDirection() == 1)
      {
        i = 1;
        if (i == 0) {
          break label40;
        }
        localDrawable = null;
        label19:
        if (i == 0) {
          break label46;
        }
      }
      for (;;)
      {
        paramTextView.setCompoundDrawables(localDrawable, null, paramDrawable1, null);
        return;
        i = 0;
        break;
        label40:
        localDrawable = paramDrawable1;
        break label19;
        label46:
        paramDrawable1 = null;
      }
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      int i;
      if (paramTextView.getLayoutDirection() == 1)
      {
        i = 1;
        if (i == 0) {
          break label45;
        }
      }
      label45:
      for (int j = paramInt3;; j = 0)
      {
        if (i != 0) {
          paramInt3 = 0;
        }
        paramTextView.setCompoundDrawablesWithIntrinsicBounds(j, 0, paramInt3, 0);
        return;
        i = 0;
        break;
      }
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
    {
      int i;
      Drawable localDrawable;
      if (paramTextView.getLayoutDirection() == 1)
      {
        i = 1;
        if (i == 0) {
          break label41;
        }
        localDrawable = paramDrawable3;
        label20:
        if (i == 0) {
          break label47;
        }
      }
      for (;;)
      {
        paramTextView.setCompoundDrawablesWithIntrinsicBounds(localDrawable, paramDrawable2, paramDrawable1, null);
        return;
        i = 0;
        break;
        label41:
        localDrawable = paramDrawable1;
        break label20;
        label47:
        paramDrawable1 = paramDrawable3;
      }
    }
  }
  
  static final class JbMr2TextViewCompatImpl
    extends TextViewCompat.JbMr1TextViewCompatImpl
  {
    public final void setCompoundDrawablesRelative(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
    {
      paramTextView.setCompoundDrawablesRelative(paramDrawable1, null, null, null);
    }
    
    public final void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, paramInt3, 0);
    }
    
    public final void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
    {
      paramTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(paramDrawable1, paramDrawable2, paramDrawable3, null);
    }
  }
  
  static class JbTextViewCompatImpl
    extends TextViewCompat.BaseTextViewCompatImpl
  {}
  
  static abstract interface TextViewCompatImpl
  {
    public abstract void setCompoundDrawablesRelative(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4);
    
    public abstract void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
    
    public abstract void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.widget.TextViewCompat
 * JD-Core Version:    0.7.0.1
 */