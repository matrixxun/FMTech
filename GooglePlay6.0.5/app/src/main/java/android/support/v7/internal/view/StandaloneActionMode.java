package android.support.v7.internal.view;

import android.content.Context;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.view.menu.MenuBuilder.Callback;
import android.support.v7.internal.widget.ActionBarContextView;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.lang.ref.WeakReference;

public final class StandaloneActionMode
  extends ActionMode
  implements MenuBuilder.Callback
{
  private ActionMode.Callback mCallback;
  private Context mContext;
  private ActionBarContextView mContextView;
  private WeakReference<View> mCustomView;
  private boolean mFinished;
  private boolean mFocusable;
  private MenuBuilder mMenu;
  
  public StandaloneActionMode(Context paramContext, ActionBarContextView paramActionBarContextView, ActionMode.Callback paramCallback, boolean paramBoolean)
  {
    this.mContext = paramContext;
    this.mContextView = paramActionBarContextView;
    this.mCallback = paramCallback;
    MenuBuilder localMenuBuilder = new MenuBuilder(paramActionBarContextView.getContext());
    localMenuBuilder.mDefaultShowAsAction = 1;
    this.mMenu = localMenuBuilder;
    this.mMenu.setCallback(this);
    this.mFocusable = paramBoolean;
  }
  
  public final void finish()
  {
    if (this.mFinished) {
      return;
    }
    this.mFinished = true;
    this.mContextView.sendAccessibilityEvent(32);
    this.mCallback.onDestroyActionMode(this);
  }
  
  public final View getCustomView()
  {
    if (this.mCustomView != null) {
      return (View)this.mCustomView.get();
    }
    return null;
  }
  
  public final Menu getMenu()
  {
    return this.mMenu;
  }
  
  public final MenuInflater getMenuInflater()
  {
    return new MenuInflater(this.mContextView.getContext());
  }
  
  public final CharSequence getSubtitle()
  {
    return this.mContextView.getSubtitle();
  }
  
  public final CharSequence getTitle()
  {
    return this.mContextView.getTitle();
  }
  
  public final void invalidate()
  {
    this.mCallback.onPrepareActionMode(this, this.mMenu);
  }
  
  public final boolean isTitleOptional()
  {
    return this.mContextView.mTitleOptional;
  }
  
  public final boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
  {
    return this.mCallback.onActionItemClicked(this, paramMenuItem);
  }
  
  public final void onMenuModeChange(MenuBuilder paramMenuBuilder)
  {
    invalidate();
    this.mContextView.showOverflowMenu();
  }
  
  public final void setCustomView(View paramView)
  {
    this.mContextView.setCustomView(paramView);
    if (paramView != null) {}
    for (WeakReference localWeakReference = new WeakReference(paramView);; localWeakReference = null)
    {
      this.mCustomView = localWeakReference;
      return;
    }
  }
  
  public final void setSubtitle(int paramInt)
  {
    setSubtitle(this.mContext.getString(paramInt));
  }
  
  public final void setSubtitle(CharSequence paramCharSequence)
  {
    this.mContextView.setSubtitle(paramCharSequence);
  }
  
  public final void setTitle(int paramInt)
  {
    setTitle(this.mContext.getString(paramInt));
  }
  
  public final void setTitle(CharSequence paramCharSequence)
  {
    this.mContextView.setTitle(paramCharSequence);
  }
  
  public final void setTitleOptionalHint(boolean paramBoolean)
  {
    super.setTitleOptionalHint(paramBoolean);
    this.mContextView.setTitleOptional(paramBoolean);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.StandaloneActionMode
 * JD-Core Version:    0.7.0.1
 */