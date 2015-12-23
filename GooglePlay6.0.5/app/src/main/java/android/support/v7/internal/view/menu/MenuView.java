package android.support.v7.internal.view.menu;

public abstract interface MenuView
{
  public abstract void initialize(MenuBuilder paramMenuBuilder);
  
  public static abstract interface ItemView
  {
    public abstract MenuItemImpl getItemData();
    
    public abstract void initialize$6b732f7b(MenuItemImpl paramMenuItemImpl);
    
    public abstract boolean prefersCondensedTitle();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.menu.MenuView
 * JD-Core Version:    0.7.0.1
 */