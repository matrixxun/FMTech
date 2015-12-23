package android.support.v4.view.accessibility;

import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityEvent;

public final class AccessibilityEventCompat
{
  private static final AccessibilityEventVersionImpl IMPL = new AccessibilityEventStubImpl();
  
  static
  {
    if (Build.VERSION.SDK_INT >= 19)
    {
      IMPL = new AccessibilityEventKitKatImpl();
      return;
    }
    if (Build.VERSION.SDK_INT >= 14)
    {
      IMPL = new AccessibilityEventIcsImpl();
      return;
    }
  }
  
  public static AccessibilityRecordCompat asRecord(AccessibilityEvent paramAccessibilityEvent)
  {
    return new AccessibilityRecordCompat(paramAccessibilityEvent);
  }
  
  public static int getContentChangeTypes(AccessibilityEvent paramAccessibilityEvent)
  {
    return IMPL.getContentChangeTypes(paramAccessibilityEvent);
  }
  
  public static void setContentChangeTypes(AccessibilityEvent paramAccessibilityEvent, int paramInt)
  {
    IMPL.setContentChangeTypes(paramAccessibilityEvent, paramInt);
  }
  
  static class AccessibilityEventIcsImpl
    extends AccessibilityEventCompat.AccessibilityEventStubImpl
  {}
  
  static final class AccessibilityEventKitKatImpl
    extends AccessibilityEventCompat.AccessibilityEventIcsImpl
  {
    public final int getContentChangeTypes(AccessibilityEvent paramAccessibilityEvent)
    {
      return paramAccessibilityEvent.getContentChangeTypes();
    }
    
    public final void setContentChangeTypes(AccessibilityEvent paramAccessibilityEvent, int paramInt)
    {
      paramAccessibilityEvent.setContentChangeTypes(paramInt);
    }
  }
  
  static class AccessibilityEventStubImpl
    implements AccessibilityEventCompat.AccessibilityEventVersionImpl
  {
    public int getContentChangeTypes(AccessibilityEvent paramAccessibilityEvent)
    {
      return 0;
    }
    
    public void setContentChangeTypes(AccessibilityEvent paramAccessibilityEvent, int paramInt) {}
  }
  
  static abstract interface AccessibilityEventVersionImpl
  {
    public abstract int getContentChangeTypes(AccessibilityEvent paramAccessibilityEvent);
    
    public abstract void setContentChangeTypes(AccessibilityEvent paramAccessibilityEvent, int paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.view.accessibility.AccessibilityEventCompat
 * JD-Core Version:    0.7.0.1
 */