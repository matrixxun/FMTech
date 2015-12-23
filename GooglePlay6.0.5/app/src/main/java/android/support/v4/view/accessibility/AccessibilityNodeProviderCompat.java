package android.support.v4.view.accessibility;

import android.os.Build.VERSION;
import java.util.ArrayList;
import java.util.List;

public final class AccessibilityNodeProviderCompat
{
  private static final AccessibilityNodeProviderImpl IMPL = new AccessibilityNodeProviderStubImpl();
  public final Object mProvider;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 19)
    {
      IMPL = new AccessibilityNodeProviderKitKatImpl();
      return;
    }
    if (Build.VERSION.SDK_INT >= 16)
    {
      IMPL = new AccessibilityNodeProviderJellyBeanImpl();
      return;
    }
  }
  
  public AccessibilityNodeProviderCompat()
  {
    this.mProvider = IMPL.newAccessibilityNodeProviderBridge(this);
  }
  
  public AccessibilityNodeProviderCompat(Object paramObject)
  {
    this.mProvider = paramObject;
  }
  
  public static AccessibilityNodeInfoCompat createAccessibilityNodeInfo$f3a5639()
  {
    return null;
  }
  
  public static List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText$2393931d()
  {
    return null;
  }
  
  public static AccessibilityNodeInfoCompat findFocus$f3a5639()
  {
    return null;
  }
  
  public static boolean performAction$5985f823()
  {
    return false;
  }
  
  static abstract interface AccessibilityNodeProviderImpl
  {
    public abstract Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat paramAccessibilityNodeProviderCompat);
  }
  
  static final class AccessibilityNodeProviderJellyBeanImpl
    extends AccessibilityNodeProviderCompat.AccessibilityNodeProviderStubImpl
  {
    public final Object newAccessibilityNodeProviderBridge(final AccessibilityNodeProviderCompat paramAccessibilityNodeProviderCompat)
    {
      new AccessibilityNodeProviderCompatJellyBean.1(new AccessibilityNodeProviderCompatJellyBean.AccessibilityNodeInfoBridge()
      {
        public final Object createAccessibilityNodeInfo$54cf32c4()
        {
          AccessibilityNodeProviderCompat.createAccessibilityNodeInfo$f3a5639();
          return null;
        }
        
        public final List<Object> findAccessibilityNodeInfosByText$2393931d()
        {
          AccessibilityNodeProviderCompat.findAccessibilityNodeInfosByText$2393931d();
          ArrayList localArrayList = new ArrayList();
          int i = null.size();
          for (int j = 0; j < i; j++) {
            localArrayList.add(((AccessibilityNodeInfoCompat)null.get(j)).mInfo);
          }
          return localArrayList;
        }
        
        public final boolean performAction$5985f823()
        {
          return AccessibilityNodeProviderCompat.performAction$5985f823();
        }
      });
    }
  }
  
  static final class AccessibilityNodeProviderKitKatImpl
    extends AccessibilityNodeProviderCompat.AccessibilityNodeProviderStubImpl
  {
    public final Object newAccessibilityNodeProviderBridge(final AccessibilityNodeProviderCompat paramAccessibilityNodeProviderCompat)
    {
      new AccessibilityNodeProviderCompatKitKat.1(new AccessibilityNodeProviderCompatKitKat.AccessibilityNodeInfoBridge()
      {
        public final Object createAccessibilityNodeInfo$54cf32c4()
        {
          AccessibilityNodeProviderCompat.createAccessibilityNodeInfo$f3a5639();
          return null;
        }
        
        public final List<Object> findAccessibilityNodeInfosByText$2393931d()
        {
          AccessibilityNodeProviderCompat.findAccessibilityNodeInfosByText$2393931d();
          ArrayList localArrayList = new ArrayList();
          int i = null.size();
          for (int j = 0; j < i; j++) {
            localArrayList.add(((AccessibilityNodeInfoCompat)null.get(j)).mInfo);
          }
          return localArrayList;
        }
        
        public final Object findFocus$54cf32c4()
        {
          AccessibilityNodeProviderCompat.findFocus$f3a5639();
          return null;
        }
        
        public final boolean performAction$5985f823()
        {
          return AccessibilityNodeProviderCompat.performAction$5985f823();
        }
      });
    }
  }
  
  static class AccessibilityNodeProviderStubImpl
    implements AccessibilityNodeProviderCompat.AccessibilityNodeProviderImpl
  {
    public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat paramAccessibilityNodeProviderCompat)
    {
      return null;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.view.accessibility.AccessibilityNodeProviderCompat
 * JD-Core Version:    0.7.0.1
 */