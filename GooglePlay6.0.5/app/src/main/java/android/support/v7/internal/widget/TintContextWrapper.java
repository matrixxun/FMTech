package android.support.v7.internal.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;

public final class TintContextWrapper
  extends ContextWrapper
{
  private Resources mResources;
  
  private TintContextWrapper(Context paramContext)
  {
    super(paramContext);
  }
  
  public static Context wrap(Context paramContext)
  {
    if (!(paramContext instanceof TintContextWrapper)) {
      paramContext = new TintContextWrapper(paramContext);
    }
    return paramContext;
  }
  
  public final Resources getResources()
  {
    if (this.mResources == null) {
      this.mResources = new TintResources(super.getResources(), TintManager.get(this));
    }
    return this.mResources;
  }
  
  static final class TintResources
    extends ResourcesWrapper
  {
    private final TintManager mTintManager;
    
    public TintResources(Resources paramResources, TintManager paramTintManager)
    {
      super();
      this.mTintManager = paramTintManager;
    }
    
    public final Drawable getDrawable(int paramInt)
      throws Resources.NotFoundException
    {
      Drawable localDrawable = super.getDrawable(paramInt);
      if (localDrawable != null) {
        this.mTintManager.tintDrawableUsingColorFilter(paramInt, localDrawable);
      }
      return localDrawable;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.TintContextWrapper
 * JD-Core Version:    0.7.0.1
 */