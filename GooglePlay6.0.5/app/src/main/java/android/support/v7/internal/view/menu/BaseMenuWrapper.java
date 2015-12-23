package android.support.v7.internal.view.menu;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.internal.view.SupportSubMenu;
import android.support.v4.util.ArrayMap;
import android.view.MenuItem;
import android.view.SubMenu;
import java.util.Map;

abstract class BaseMenuWrapper<T>
  extends BaseWrapper<T>
{
  final Context mContext;
  Map<SupportMenuItem, MenuItem> mMenuItems;
  Map<SupportSubMenu, SubMenu> mSubMenus;
  
  BaseMenuWrapper(Context paramContext, T paramT)
  {
    super(paramT);
    this.mContext = paramContext;
  }
  
  final MenuItem getMenuItemWrapper(MenuItem paramMenuItem)
  {
    if ((paramMenuItem instanceof SupportMenuItem))
    {
      SupportMenuItem localSupportMenuItem = (SupportMenuItem)paramMenuItem;
      if (this.mMenuItems == null) {
        this.mMenuItems = new ArrayMap();
      }
      MenuItem localMenuItem = (MenuItem)this.mMenuItems.get(paramMenuItem);
      if (localMenuItem == null)
      {
        localMenuItem = MenuWrapperFactory.wrapSupportMenuItem(this.mContext, localSupportMenuItem);
        this.mMenuItems.put(localSupportMenuItem, localMenuItem);
      }
      return localMenuItem;
    }
    return paramMenuItem;
  }
  
  final SubMenu getSubMenuWrapper(SubMenu paramSubMenu)
  {
    if ((paramSubMenu instanceof SupportSubMenu))
    {
      SupportSubMenu localSupportSubMenu = (SupportSubMenu)paramSubMenu;
      if (this.mSubMenus == null) {
        this.mSubMenus = new ArrayMap();
      }
      Object localObject = (SubMenu)this.mSubMenus.get(localSupportSubMenu);
      if (localObject == null)
      {
        Context localContext = this.mContext;
        if (Build.VERSION.SDK_INT >= 14)
        {
          localObject = new SubMenuWrapperICS(localContext, localSupportSubMenu);
          this.mSubMenus.put(localSupportSubMenu, localObject);
        }
      }
      else
      {
        return localObject;
      }
      throw new UnsupportedOperationException();
    }
    return paramSubMenu;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.menu.BaseMenuWrapper
 * JD-Core Version:    0.7.0.1
 */