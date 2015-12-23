package android.support.v4.widget;

import android.os.Build.VERSION;
import android.view.View;
import android.widget.PopupWindow;
import java.lang.reflect.Method;

public final class PopupWindowCompat
{
  static final PopupWindowImpl IMPL = new BasePopupWindowImpl();
  
  static
  {
    int i = Build.VERSION.SDK_INT;
    if (i >= 23)
    {
      IMPL = new Api23PopupWindowImpl();
      return;
    }
    if (i >= 21)
    {
      IMPL = new Api21PopupWindowImpl();
      return;
    }
    if (i >= 19)
    {
      IMPL = new KitKatPopupWindowImpl();
      return;
    }
    if (i >= 9)
    {
      IMPL = new GingerbreadPopupWindowImpl();
      return;
    }
  }
  
  public static void setOverlapAnchor(PopupWindow paramPopupWindow, boolean paramBoolean)
  {
    IMPL.setOverlapAnchor(paramPopupWindow, paramBoolean);
  }
  
  public static void setWindowLayoutType(PopupWindow paramPopupWindow, int paramInt)
  {
    IMPL.setWindowLayoutType(paramPopupWindow, paramInt);
  }
  
  public static void showAsDropDown(PopupWindow paramPopupWindow, View paramView, int paramInt1, int paramInt2, int paramInt3)
  {
    IMPL.showAsDropDown(paramPopupWindow, paramView, paramInt1, paramInt2, paramInt3);
  }
  
  static class Api21PopupWindowImpl
    extends PopupWindowCompat.KitKatPopupWindowImpl
  {
    public void setOverlapAnchor(PopupWindow paramPopupWindow, boolean paramBoolean)
    {
      PopupWindowCompatApi21.setOverlapAnchor(paramPopupWindow, paramBoolean);
    }
  }
  
  static final class Api23PopupWindowImpl
    extends PopupWindowCompat.Api21PopupWindowImpl
  {
    public final void setOverlapAnchor(PopupWindow paramPopupWindow, boolean paramBoolean)
    {
      paramPopupWindow.setOverlapAnchor(paramBoolean);
    }
    
    public final void setWindowLayoutType(PopupWindow paramPopupWindow, int paramInt)
    {
      paramPopupWindow.setWindowLayoutType(paramInt);
    }
  }
  
  static class BasePopupWindowImpl
    implements PopupWindowCompat.PopupWindowImpl
  {
    public void setOverlapAnchor(PopupWindow paramPopupWindow, boolean paramBoolean) {}
    
    public void setWindowLayoutType(PopupWindow paramPopupWindow, int paramInt) {}
    
    public void showAsDropDown(PopupWindow paramPopupWindow, View paramView, int paramInt1, int paramInt2, int paramInt3)
    {
      paramPopupWindow.showAsDropDown(paramView, paramInt1, paramInt2);
    }
  }
  
  static class GingerbreadPopupWindowImpl
    extends PopupWindowCompat.BasePopupWindowImpl
  {
    public void setWindowLayoutType(PopupWindow paramPopupWindow, int paramInt)
    {
      if (!PopupWindowCompatGingerbread.sSetWindowLayoutTypeMethodAttempted) {}
      try
      {
        Class[] arrayOfClass = new Class[1];
        arrayOfClass[0] = Integer.TYPE;
        Method localMethod2 = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", arrayOfClass);
        PopupWindowCompatGingerbread.sSetWindowLayoutTypeMethod = localMethod2;
        localMethod2.setAccessible(true);
        label41:
        PopupWindowCompatGingerbread.sSetWindowLayoutTypeMethodAttempted = true;
        if (PopupWindowCompatGingerbread.sSetWindowLayoutTypeMethod != null) {}
        try
        {
          Method localMethod1 = PopupWindowCompatGingerbread.sSetWindowLayoutTypeMethod;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Integer.valueOf(paramInt);
          localMethod1.invoke(paramPopupWindow, arrayOfObject);
          return;
        }
        catch (Exception localException1) {}
      }
      catch (Exception localException2)
      {
        break label41;
      }
    }
  }
  
  static class KitKatPopupWindowImpl
    extends PopupWindowCompat.GingerbreadPopupWindowImpl
  {
    public final void showAsDropDown(PopupWindow paramPopupWindow, View paramView, int paramInt1, int paramInt2, int paramInt3)
    {
      paramPopupWindow.showAsDropDown(paramView, paramInt1, paramInt2, paramInt3);
    }
  }
  
  static abstract interface PopupWindowImpl
  {
    public abstract void setOverlapAnchor(PopupWindow paramPopupWindow, boolean paramBoolean);
    
    public abstract void setWindowLayoutType(PopupWindow paramPopupWindow, int paramInt);
    
    public abstract void showAsDropDown(PopupWindow paramPopupWindow, View paramView, int paramInt1, int paramInt2, int paramInt3);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.widget.PopupWindowCompat
 * JD-Core Version:    0.7.0.1
 */