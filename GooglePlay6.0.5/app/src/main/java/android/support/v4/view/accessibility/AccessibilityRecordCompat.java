package android.support.v4.view.accessibility;

import android.os.Build.VERSION;
import android.view.View;
import android.view.accessibility.AccessibilityRecord;

public final class AccessibilityRecordCompat
{
  public static final AccessibilityRecordImpl IMPL = new AccessibilityRecordStubImpl();
  public final Object mRecord;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      IMPL = new AccessibilityRecordJellyBeanImpl();
      return;
    }
    if (Build.VERSION.SDK_INT >= 15)
    {
      IMPL = new AccessibilityRecordIcsMr1Impl();
      return;
    }
    if (Build.VERSION.SDK_INT >= 14)
    {
      IMPL = new AccessibilityRecordIcsImpl();
      return;
    }
  }
  
  public AccessibilityRecordCompat(Object paramObject)
  {
    this.mRecord = paramObject;
  }
  
  public static AccessibilityRecordCompat obtain()
  {
    return new AccessibilityRecordCompat(IMPL.obtain());
  }
  
  public final boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    AccessibilityRecordCompat localAccessibilityRecordCompat;
    do
    {
      do
      {
        return true;
        if (paramObject == null) {
          return false;
        }
        if (getClass() != paramObject.getClass()) {
          return false;
        }
        localAccessibilityRecordCompat = (AccessibilityRecordCompat)paramObject;
        if (this.mRecord != null) {
          break;
        }
      } while (localAccessibilityRecordCompat.mRecord == null);
      return false;
    } while (this.mRecord.equals(localAccessibilityRecordCompat.mRecord));
    return false;
  }
  
  public final int hashCode()
  {
    if (this.mRecord == null) {
      return 0;
    }
    return this.mRecord.hashCode();
  }
  
  public final void setFromIndex(int paramInt)
  {
    IMPL.setFromIndex(this.mRecord, paramInt);
  }
  
  public final void setItemCount(int paramInt)
  {
    IMPL.setItemCount(this.mRecord, paramInt);
  }
  
  public final void setScrollable(boolean paramBoolean)
  {
    IMPL.setScrollable(this.mRecord, paramBoolean);
  }
  
  public final void setSource(View paramView)
  {
    IMPL.setSource(this.mRecord, paramView);
  }
  
  public final void setToIndex(int paramInt)
  {
    IMPL.setToIndex(this.mRecord, paramInt);
  }
  
  static class AccessibilityRecordIcsImpl
    extends AccessibilityRecordCompat.AccessibilityRecordStubImpl
  {
    public final Object obtain()
    {
      return AccessibilityRecord.obtain();
    }
    
    public final void setContentDescription(Object paramObject, CharSequence paramCharSequence)
    {
      ((AccessibilityRecord)paramObject).setContentDescription(null);
    }
    
    public final void setFromIndex(Object paramObject, int paramInt)
    {
      ((AccessibilityRecord)paramObject).setFromIndex(paramInt);
    }
    
    public final void setItemCount(Object paramObject, int paramInt)
    {
      ((AccessibilityRecord)paramObject).setItemCount(paramInt);
    }
    
    public final void setScrollX(Object paramObject, int paramInt)
    {
      ((AccessibilityRecord)paramObject).setScrollX(paramInt);
    }
    
    public final void setScrollY(Object paramObject, int paramInt)
    {
      ((AccessibilityRecord)paramObject).setScrollY(paramInt);
    }
    
    public final void setScrollable(Object paramObject, boolean paramBoolean)
    {
      ((AccessibilityRecord)paramObject).setScrollable(paramBoolean);
    }
    
    public final void setSource(Object paramObject, View paramView)
    {
      ((AccessibilityRecord)paramObject).setSource(paramView);
    }
    
    public final void setToIndex(Object paramObject, int paramInt)
    {
      ((AccessibilityRecord)paramObject).setToIndex(paramInt);
    }
  }
  
  static class AccessibilityRecordIcsMr1Impl
    extends AccessibilityRecordCompat.AccessibilityRecordIcsImpl
  {
    public final void setMaxScrollX(Object paramObject, int paramInt)
    {
      ((AccessibilityRecord)paramObject).setMaxScrollX(paramInt);
    }
    
    public final void setMaxScrollY(Object paramObject, int paramInt)
    {
      ((AccessibilityRecord)paramObject).setMaxScrollY(paramInt);
    }
  }
  
  public static abstract interface AccessibilityRecordImpl
  {
    public abstract Object obtain();
    
    public abstract void setContentDescription(Object paramObject, CharSequence paramCharSequence);
    
    public abstract void setFromIndex(Object paramObject, int paramInt);
    
    public abstract void setItemCount(Object paramObject, int paramInt);
    
    public abstract void setMaxScrollX(Object paramObject, int paramInt);
    
    public abstract void setMaxScrollY(Object paramObject, int paramInt);
    
    public abstract void setScrollX(Object paramObject, int paramInt);
    
    public abstract void setScrollY(Object paramObject, int paramInt);
    
    public abstract void setScrollable(Object paramObject, boolean paramBoolean);
    
    public abstract void setSource(Object paramObject, View paramView);
    
    public abstract void setToIndex(Object paramObject, int paramInt);
  }
  
  static final class AccessibilityRecordJellyBeanImpl
    extends AccessibilityRecordCompat.AccessibilityRecordIcsMr1Impl
  {}
  
  static class AccessibilityRecordStubImpl
    implements AccessibilityRecordCompat.AccessibilityRecordImpl
  {
    public Object obtain()
    {
      return null;
    }
    
    public void setContentDescription(Object paramObject, CharSequence paramCharSequence) {}
    
    public void setFromIndex(Object paramObject, int paramInt) {}
    
    public void setItemCount(Object paramObject, int paramInt) {}
    
    public void setMaxScrollX(Object paramObject, int paramInt) {}
    
    public void setMaxScrollY(Object paramObject, int paramInt) {}
    
    public void setScrollX(Object paramObject, int paramInt) {}
    
    public void setScrollY(Object paramObject, int paramInt) {}
    
    public void setScrollable(Object paramObject, boolean paramBoolean) {}
    
    public void setSource(Object paramObject, View paramView) {}
    
    public void setToIndex(Object paramObject, int paramInt) {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.view.accessibility.AccessibilityRecordCompat
 * JD-Core Version:    0.7.0.1
 */