package com.google.android.play.search;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.KeyEvent;
import android.view.KeyEvent.DispatcherState;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class PlaySearchEditText
  extends EditText
{
  public PlaySearchEditText(Context paramContext)
  {
    super(paramContext);
  }
  
  public PlaySearchEditText(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if ((Build.VERSION.SDK_INT >= 11) && (Build.VERSION.SDK_INT <= 22)) {
      setCustomSelectionActionModeCallback(new ActionMode.Callback()
      {
        public final boolean onActionItemClicked(ActionMode paramAnonymousActionMode, MenuItem paramAnonymousMenuItem)
        {
          return false;
        }
        
        public final boolean onCreateActionMode(ActionMode paramAnonymousActionMode, Menu paramAnonymousMenu)
        {
          return false;
        }
        
        public final void onDestroyActionMode(ActionMode paramAnonymousActionMode) {}
        
        public final boolean onPrepareActionMode(ActionMode paramAnonymousActionMode, Menu paramAnonymousMenu)
        {
          return false;
        }
      });
    }
  }
  
  public boolean onKeyPreIme(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 4) && (hasFocus()) && (paramKeyEvent.getAction() == 1))
    {
      KeyEvent.DispatcherState localDispatcherState = getKeyDispatcherState();
      if (localDispatcherState != null) {
        localDispatcherState.handleUpEvent(paramKeyEvent);
      }
      if (!paramKeyEvent.isCanceled())
      {
        clearFocus();
        return true;
      }
    }
    return super.onKeyPreIme(paramInt, paramKeyEvent);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.search.PlaySearchEditText
 * JD-Core Version:    0.7.0.1
 */