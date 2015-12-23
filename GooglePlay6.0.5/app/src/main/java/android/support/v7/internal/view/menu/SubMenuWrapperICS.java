package android.support.v7.internal.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.internal.view.SupportSubMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

final class SubMenuWrapperICS
  extends MenuWrapperICS
  implements SubMenu
{
  SubMenuWrapperICS(Context paramContext, SupportSubMenu paramSupportSubMenu)
  {
    super(paramContext, paramSupportSubMenu);
  }
  
  public final void clearHeader()
  {
    ((SupportSubMenu)this.mWrappedObject).clearHeader();
  }
  
  public final MenuItem getItem()
  {
    return getMenuItemWrapper(((SupportSubMenu)this.mWrappedObject).getItem());
  }
  
  public final SubMenu setHeaderIcon(int paramInt)
  {
    ((SupportSubMenu)this.mWrappedObject).setHeaderIcon(paramInt);
    return this;
  }
  
  public final SubMenu setHeaderIcon(Drawable paramDrawable)
  {
    ((SupportSubMenu)this.mWrappedObject).setHeaderIcon(paramDrawable);
    return this;
  }
  
  public final SubMenu setHeaderTitle(int paramInt)
  {
    ((SupportSubMenu)this.mWrappedObject).setHeaderTitle(paramInt);
    return this;
  }
  
  public final SubMenu setHeaderTitle(CharSequence paramCharSequence)
  {
    ((SupportSubMenu)this.mWrappedObject).setHeaderTitle(paramCharSequence);
    return this;
  }
  
  public final SubMenu setHeaderView(View paramView)
  {
    ((SupportSubMenu)this.mWrappedObject).setHeaderView(paramView);
    return this;
  }
  
  public final SubMenu setIcon(int paramInt)
  {
    ((SupportSubMenu)this.mWrappedObject).setIcon(paramInt);
    return this;
  }
  
  public final SubMenu setIcon(Drawable paramDrawable)
  {
    ((SupportSubMenu)this.mWrappedObject).setIcon(paramDrawable);
    return this;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.menu.SubMenuWrapperICS
 * JD-Core Version:    0.7.0.1
 */