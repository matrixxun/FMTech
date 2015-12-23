package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.util.Log;
import java.lang.reflect.Method;

public final class DrawableCompat
{
  static final DrawableImpl IMPL = new BaseDrawableImpl();
  
  static
  {
    int i = Build.VERSION.SDK_INT;
    if (i >= 23)
    {
      IMPL = new MDrawableImpl();
      return;
    }
    if (i >= 22)
    {
      IMPL = new LollipopMr1DrawableImpl();
      return;
    }
    if (i >= 21)
    {
      IMPL = new LollipopDrawableImpl();
      return;
    }
    if (i >= 19)
    {
      IMPL = new KitKatDrawableImpl();
      return;
    }
    if (i >= 17)
    {
      IMPL = new JellybeanMr1DrawableImpl();
      return;
    }
    if (i >= 11)
    {
      IMPL = new HoneycombDrawableImpl();
      return;
    }
  }
  
  public static int getLayoutDirection(Drawable paramDrawable)
  {
    return IMPL.getLayoutDirection(paramDrawable);
  }
  
  public static boolean isAutoMirrored(Drawable paramDrawable)
  {
    return IMPL.isAutoMirrored(paramDrawable);
  }
  
  public static void jumpToCurrentState(Drawable paramDrawable)
  {
    IMPL.jumpToCurrentState(paramDrawable);
  }
  
  public static void setAutoMirrored(Drawable paramDrawable, boolean paramBoolean)
  {
    IMPL.setAutoMirrored(paramDrawable, paramBoolean);
  }
  
  public static void setHotspot(Drawable paramDrawable, float paramFloat1, float paramFloat2)
  {
    IMPL.setHotspot(paramDrawable, paramFloat1, paramFloat2);
  }
  
  public static void setHotspotBounds(Drawable paramDrawable, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    IMPL.setHotspotBounds(paramDrawable, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static void setLayoutDirection(Drawable paramDrawable, int paramInt)
  {
    IMPL.setLayoutDirection(paramDrawable, paramInt);
  }
  
  public static void setTint(Drawable paramDrawable, int paramInt)
  {
    IMPL.setTint(paramDrawable, paramInt);
  }
  
  public static void setTintList(Drawable paramDrawable, ColorStateList paramColorStateList)
  {
    IMPL.setTintList(paramDrawable, paramColorStateList);
  }
  
  public static void setTintMode(Drawable paramDrawable, PorterDuff.Mode paramMode)
  {
    IMPL.setTintMode(paramDrawable, paramMode);
  }
  
  public static <T extends Drawable> T unwrap(Drawable paramDrawable)
  {
    if ((paramDrawable instanceof DrawableWrapper)) {
      paramDrawable = ((DrawableWrapper)paramDrawable).getWrappedDrawable();
    }
    return paramDrawable;
  }
  
  public static Drawable wrap(Drawable paramDrawable)
  {
    return IMPL.wrap(paramDrawable);
  }
  
  static class BaseDrawableImpl
    implements DrawableCompat.DrawableImpl
  {
    public int getLayoutDirection(Drawable paramDrawable)
    {
      return 0;
    }
    
    public boolean isAutoMirrored(Drawable paramDrawable)
    {
      return false;
    }
    
    public void jumpToCurrentState(Drawable paramDrawable) {}
    
    public void setAutoMirrored(Drawable paramDrawable, boolean paramBoolean) {}
    
    public void setHotspot(Drawable paramDrawable, float paramFloat1, float paramFloat2) {}
    
    public void setHotspotBounds(Drawable paramDrawable, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
    
    public void setLayoutDirection(Drawable paramDrawable, int paramInt) {}
    
    public void setTint(Drawable paramDrawable, int paramInt)
    {
      DrawableCompatBase.setTint(paramDrawable, paramInt);
    }
    
    public void setTintList(Drawable paramDrawable, ColorStateList paramColorStateList)
    {
      DrawableCompatBase.setTintList(paramDrawable, paramColorStateList);
    }
    
    public void setTintMode(Drawable paramDrawable, PorterDuff.Mode paramMode)
    {
      DrawableCompatBase.setTintMode(paramDrawable, paramMode);
    }
    
    public Drawable wrap(Drawable paramDrawable)
    {
      if (!(paramDrawable instanceof DrawableWrapperDonut)) {
        paramDrawable = new DrawableWrapperDonut(paramDrawable);
      }
      return paramDrawable;
    }
  }
  
  static abstract interface DrawableImpl
  {
    public abstract int getLayoutDirection(Drawable paramDrawable);
    
    public abstract boolean isAutoMirrored(Drawable paramDrawable);
    
    public abstract void jumpToCurrentState(Drawable paramDrawable);
    
    public abstract void setAutoMirrored(Drawable paramDrawable, boolean paramBoolean);
    
    public abstract void setHotspot(Drawable paramDrawable, float paramFloat1, float paramFloat2);
    
    public abstract void setHotspotBounds(Drawable paramDrawable, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
    
    public abstract void setLayoutDirection(Drawable paramDrawable, int paramInt);
    
    public abstract void setTint(Drawable paramDrawable, int paramInt);
    
    public abstract void setTintList(Drawable paramDrawable, ColorStateList paramColorStateList);
    
    public abstract void setTintMode(Drawable paramDrawable, PorterDuff.Mode paramMode);
    
    public abstract Drawable wrap(Drawable paramDrawable);
  }
  
  static class HoneycombDrawableImpl
    extends DrawableCompat.BaseDrawableImpl
  {
    public final void jumpToCurrentState(Drawable paramDrawable)
    {
      paramDrawable.jumpToCurrentState();
    }
    
    public Drawable wrap(Drawable paramDrawable)
    {
      if (!(paramDrawable instanceof DrawableWrapperHoneycomb)) {
        paramDrawable = new DrawableWrapperHoneycomb(paramDrawable);
      }
      return paramDrawable;
    }
  }
  
  static class JellybeanMr1DrawableImpl
    extends DrawableCompat.HoneycombDrawableImpl
  {
    public int getLayoutDirection(Drawable paramDrawable)
    {
      int i = DrawableCompatJellybeanMr1.getLayoutDirection(paramDrawable);
      if (i >= 0) {
        return i;
      }
      return 0;
    }
    
    public void setLayoutDirection(Drawable paramDrawable, int paramInt)
    {
      if (!DrawableCompatJellybeanMr1.sSetLayoutDirectionMethodFetched) {}
      try
      {
        Class[] arrayOfClass = new Class[1];
        arrayOfClass[0] = Integer.TYPE;
        Method localMethod2 = Drawable.class.getDeclaredMethod("setLayoutDirection", arrayOfClass);
        DrawableCompatJellybeanMr1.sSetLayoutDirectionMethod = localMethod2;
        localMethod2.setAccessible(true);
        DrawableCompatJellybeanMr1.sSetLayoutDirectionMethodFetched = true;
        if (DrawableCompatJellybeanMr1.sSetLayoutDirectionMethod == null) {}
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        for (;;)
        {
          try
          {
            Method localMethod1 = DrawableCompatJellybeanMr1.sSetLayoutDirectionMethod;
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Integer.valueOf(paramInt);
            localMethod1.invoke(paramDrawable, arrayOfObject);
            return;
          }
          catch (Exception localException)
          {
            Log.i("DrawableCompatJellybeanMr1", "Failed to invoke setLayoutDirection(int) via reflection", localException);
            DrawableCompatJellybeanMr1.sSetLayoutDirectionMethod = null;
          }
          localNoSuchMethodException = localNoSuchMethodException;
          Log.i("DrawableCompatJellybeanMr1", "Failed to retrieve setLayoutDirection(int) method", localNoSuchMethodException);
        }
      }
    }
  }
  
  static class KitKatDrawableImpl
    extends DrawableCompat.JellybeanMr1DrawableImpl
  {
    public final boolean isAutoMirrored(Drawable paramDrawable)
    {
      return paramDrawable.isAutoMirrored();
    }
    
    public final void setAutoMirrored(Drawable paramDrawable, boolean paramBoolean)
    {
      paramDrawable.setAutoMirrored(paramBoolean);
    }
    
    public Drawable wrap(Drawable paramDrawable)
    {
      if (!(paramDrawable instanceof DrawableWrapperKitKat)) {
        paramDrawable = new DrawableWrapperKitKat(paramDrawable);
      }
      return paramDrawable;
    }
  }
  
  static class LollipopDrawableImpl
    extends DrawableCompat.KitKatDrawableImpl
  {
    public final void setHotspot(Drawable paramDrawable, float paramFloat1, float paramFloat2)
    {
      paramDrawable.setHotspot(paramFloat1, paramFloat2);
    }
    
    public final void setHotspotBounds(Drawable paramDrawable, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramDrawable.setHotspotBounds(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public final void setTint(Drawable paramDrawable, int paramInt)
    {
      if ((paramDrawable instanceof DrawableWrapperLollipop))
      {
        DrawableCompatBase.setTint(paramDrawable, paramInt);
        return;
      }
      paramDrawable.setTint(paramInt);
    }
    
    public final void setTintList(Drawable paramDrawable, ColorStateList paramColorStateList)
    {
      if ((paramDrawable instanceof DrawableWrapperLollipop))
      {
        DrawableCompatBase.setTintList(paramDrawable, paramColorStateList);
        return;
      }
      paramDrawable.setTintList(paramColorStateList);
    }
    
    public final void setTintMode(Drawable paramDrawable, PorterDuff.Mode paramMode)
    {
      if ((paramDrawable instanceof DrawableWrapperLollipop))
      {
        DrawableCompatBase.setTintMode(paramDrawable, paramMode);
        return;
      }
      paramDrawable.setTintMode(paramMode);
    }
    
    public Drawable wrap(Drawable paramDrawable)
    {
      if (((paramDrawable instanceof GradientDrawable)) || ((paramDrawable instanceof DrawableContainer))) {
        paramDrawable = new DrawableWrapperLollipop(paramDrawable);
      }
      return paramDrawable;
    }
  }
  
  static class LollipopMr1DrawableImpl
    extends DrawableCompat.LollipopDrawableImpl
  {
    public final Drawable wrap(Drawable paramDrawable)
    {
      return paramDrawable;
    }
  }
  
  static final class MDrawableImpl
    extends DrawableCompat.LollipopMr1DrawableImpl
  {
    public final int getLayoutDirection(Drawable paramDrawable)
    {
      return paramDrawable.getLayoutDirection();
    }
    
    public final void setLayoutDirection(Drawable paramDrawable, int paramInt)
    {
      paramDrawable.setLayoutDirection(paramInt);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.graphics.drawable.DrawableCompat
 * JD-Core Version:    0.7.0.1
 */