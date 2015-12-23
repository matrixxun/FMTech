package android.support.v7.internal.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.internal.view.menu.MenuWrapperFactory;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;

@TargetApi(11)
public final class SupportActionModeWrapper
  extends android.view.ActionMode
{
  final Context mContext;
  final android.support.v7.view.ActionMode mWrappedObject;
  
  public SupportActionModeWrapper(Context paramContext, android.support.v7.view.ActionMode paramActionMode)
  {
    this.mContext = paramContext;
    this.mWrappedObject = paramActionMode;
  }
  
  public final void finish()
  {
    this.mWrappedObject.finish();
  }
  
  public final View getCustomView()
  {
    return this.mWrappedObject.getCustomView();
  }
  
  public final Menu getMenu()
  {
    return MenuWrapperFactory.wrapSupportMenu(this.mContext, (SupportMenu)this.mWrappedObject.getMenu());
  }
  
  public final MenuInflater getMenuInflater()
  {
    return this.mWrappedObject.getMenuInflater();
  }
  
  public final CharSequence getSubtitle()
  {
    return this.mWrappedObject.getSubtitle();
  }
  
  public final Object getTag()
  {
    return this.mWrappedObject.mTag;
  }
  
  public final CharSequence getTitle()
  {
    return this.mWrappedObject.getTitle();
  }
  
  public final boolean getTitleOptionalHint()
  {
    return this.mWrappedObject.mTitleOptionalHint;
  }
  
  public final void invalidate()
  {
    this.mWrappedObject.invalidate();
  }
  
  public final boolean isTitleOptional()
  {
    return this.mWrappedObject.isTitleOptional();
  }
  
  public final void setCustomView(View paramView)
  {
    this.mWrappedObject.setCustomView(paramView);
  }
  
  public final void setSubtitle(int paramInt)
  {
    this.mWrappedObject.setSubtitle(paramInt);
  }
  
  public final void setSubtitle(CharSequence paramCharSequence)
  {
    this.mWrappedObject.setSubtitle(paramCharSequence);
  }
  
  public final void setTag(Object paramObject)
  {
    this.mWrappedObject.mTag = paramObject;
  }
  
  public final void setTitle(int paramInt)
  {
    this.mWrappedObject.setTitle(paramInt);
  }
  
  public final void setTitle(CharSequence paramCharSequence)
  {
    this.mWrappedObject.setTitle(paramCharSequence);
  }
  
  public final void setTitleOptionalHint(boolean paramBoolean)
  {
    this.mWrappedObject.setTitleOptionalHint(paramBoolean);
  }
  
  public static final class CallbackWrapper
    implements android.support.v7.view.ActionMode.Callback
  {
    final ArrayList<SupportActionModeWrapper> mActionModes;
    final Context mContext;
    final SimpleArrayMap<Menu, Menu> mMenus;
    final android.view.ActionMode.Callback mWrappedCallback;
    
    public CallbackWrapper(Context paramContext, android.view.ActionMode.Callback paramCallback)
    {
      this.mContext = paramContext;
      this.mWrappedCallback = paramCallback;
      this.mActionModes = new ArrayList();
      this.mMenus = new SimpleArrayMap();
    }
    
    private Menu getMenuWrapper(Menu paramMenu)
    {
      Menu localMenu = (Menu)this.mMenus.get(paramMenu);
      if (localMenu == null)
      {
        localMenu = MenuWrapperFactory.wrapSupportMenu(this.mContext, (SupportMenu)paramMenu);
        this.mMenus.put(paramMenu, localMenu);
      }
      return localMenu;
    }
    
    public final android.view.ActionMode getActionModeWrapper(android.support.v7.view.ActionMode paramActionMode)
    {
      int i = 0;
      int j = this.mActionModes.size();
      while (i < j)
      {
        SupportActionModeWrapper localSupportActionModeWrapper2 = (SupportActionModeWrapper)this.mActionModes.get(i);
        if ((localSupportActionModeWrapper2 != null) && (localSupportActionModeWrapper2.mWrappedObject == paramActionMode)) {
          return localSupportActionModeWrapper2;
        }
        i++;
      }
      SupportActionModeWrapper localSupportActionModeWrapper1 = new SupportActionModeWrapper(this.mContext, paramActionMode);
      this.mActionModes.add(localSupportActionModeWrapper1);
      return localSupportActionModeWrapper1;
    }
    
    public final boolean onActionItemClicked(android.support.v7.view.ActionMode paramActionMode, MenuItem paramMenuItem)
    {
      return this.mWrappedCallback.onActionItemClicked(getActionModeWrapper(paramActionMode), MenuWrapperFactory.wrapSupportMenuItem(this.mContext, (SupportMenuItem)paramMenuItem));
    }
    
    public final boolean onCreateActionMode(android.support.v7.view.ActionMode paramActionMode, Menu paramMenu)
    {
      return this.mWrappedCallback.onCreateActionMode(getActionModeWrapper(paramActionMode), getMenuWrapper(paramMenu));
    }
    
    public final void onDestroyActionMode(android.support.v7.view.ActionMode paramActionMode)
    {
      this.mWrappedCallback.onDestroyActionMode(getActionModeWrapper(paramActionMode));
    }
    
    public final boolean onPrepareActionMode(android.support.v7.view.ActionMode paramActionMode, Menu paramMenu)
    {
      return this.mWrappedCallback.onPrepareActionMode(getActionModeWrapper(paramActionMode), getMenuWrapper(paramMenu));
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.SupportActionModeWrapper
 * JD-Core Version:    0.7.0.1
 */