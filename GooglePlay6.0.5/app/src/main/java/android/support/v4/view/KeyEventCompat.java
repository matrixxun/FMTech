package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.KeyEvent;

public final class KeyEventCompat
{
  static final KeyEventVersionImpl IMPL = new BaseKeyEventVersionImpl();
  
  static
  {
    if (Build.VERSION.SDK_INT >= 11)
    {
      IMPL = new HoneycombKeyEventVersionImpl();
      return;
    }
  }
  
  public static boolean hasModifiers$79c6ddc0(KeyEvent paramKeyEvent)
  {
    return IMPL.metaStateHasModifiers(paramKeyEvent.getMetaState(), 1);
  }
  
  public static boolean hasNoModifiers(KeyEvent paramKeyEvent)
  {
    return IMPL.metaStateHasNoModifiers(paramKeyEvent.getMetaState());
  }
  
  public static void startTracking(KeyEvent paramKeyEvent)
  {
    IMPL.startTracking(paramKeyEvent);
  }
  
  static class BaseKeyEventVersionImpl
    implements KeyEventCompat.KeyEventVersionImpl
  {
    private static int metaStateFilterDirectionalModifiers(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      int i = 1;
      int j;
      int k;
      if ((paramInt2 & paramInt3) != 0)
      {
        j = i;
        k = paramInt4 | paramInt5;
        if ((paramInt2 & k) == 0) {
          break label52;
        }
      }
      for (;;)
      {
        if (j != 0)
        {
          if (i != 0)
          {
            throw new IllegalArgumentException("bad arguments");
            j = 0;
            break;
            label52:
            i = 0;
            continue;
          }
          paramInt1 &= (k ^ 0xFFFFFFFF);
        }
      }
      while (i == 0) {
        return paramInt1;
      }
      return paramInt1 & (paramInt3 ^ 0xFFFFFFFF);
    }
    
    public boolean metaStateHasModifiers(int paramInt1, int paramInt2)
    {
      return metaStateFilterDirectionalModifiers(metaStateFilterDirectionalModifiers(0xF7 & normalizeMetaState(paramInt1), 1, 1, 64, 128), 1, 2, 16, 32) == 1;
    }
    
    public boolean metaStateHasNoModifiers(int paramInt)
    {
      return (0xF7 & normalizeMetaState(paramInt)) == 0;
    }
    
    public int normalizeMetaState(int paramInt)
    {
      if ((paramInt & 0xC0) != 0) {
        paramInt |= 0x1;
      }
      if ((paramInt & 0x30) != 0) {
        paramInt |= 0x2;
      }
      return paramInt & 0xF7;
    }
    
    public void startTracking(KeyEvent paramKeyEvent) {}
  }
  
  static class EclairKeyEventVersionImpl
    extends KeyEventCompat.BaseKeyEventVersionImpl
  {
    public final void startTracking(KeyEvent paramKeyEvent)
    {
      paramKeyEvent.startTracking();
    }
  }
  
  static final class HoneycombKeyEventVersionImpl
    extends KeyEventCompat.EclairKeyEventVersionImpl
  {
    public final boolean metaStateHasModifiers(int paramInt1, int paramInt2)
    {
      return KeyEvent.metaStateHasModifiers(paramInt1, 1);
    }
    
    public final boolean metaStateHasNoModifiers(int paramInt)
    {
      return KeyEvent.metaStateHasNoModifiers(paramInt);
    }
    
    public final int normalizeMetaState(int paramInt)
    {
      return KeyEvent.normalizeMetaState(paramInt);
    }
  }
  
  static abstract interface KeyEventVersionImpl
  {
    public abstract boolean metaStateHasModifiers(int paramInt1, int paramInt2);
    
    public abstract boolean metaStateHasNoModifiers(int paramInt);
    
    public abstract void startTracking(KeyEvent paramKeyEvent);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.view.KeyEventCompat
 * JD-Core Version:    0.7.0.1
 */