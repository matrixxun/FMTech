package android.support.v7.app;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.id;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.internal.view.StandaloneActionMode;
import android.support.v7.internal.view.SupportActionModeWrapper.CallbackWrapper;
import android.support.v7.internal.widget.ActionBarContextView;
import android.support.v7.internal.widget.ViewStubCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.Window.Callback;
import android.widget.PopupWindow;

class AppCompatDelegateImplV14
  extends AppCompatDelegateImplV11
{
  boolean mHandleNativeActionModes = true;
  
  AppCompatDelegateImplV14(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback)
  {
    super(paramContext, paramWindow, paramAppCompatCallback);
  }
  
  Window.Callback wrapWindowCallback(Window.Callback paramCallback)
  {
    return new AppCompatWindowCallbackV14(paramCallback);
  }
  
  class AppCompatWindowCallbackV14
    extends AppCompatDelegateImplBase.AppCompatWindowCallbackBase
  {
    AppCompatWindowCallbackV14(Window.Callback paramCallback)
    {
      super(paramCallback);
    }
    
    public android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback paramCallback)
    {
      if (AppCompatDelegateImplV14.this.mHandleNativeActionModes) {
        return startAsSupportActionMode(paramCallback);
      }
      return super.onWindowStartingActionMode(paramCallback);
    }
    
    final android.view.ActionMode startAsSupportActionMode(android.view.ActionMode.Callback paramCallback)
    {
      SupportActionModeWrapper.CallbackWrapper localCallbackWrapper = new SupportActionModeWrapper.CallbackWrapper(AppCompatDelegateImplV14.this.mContext, paramCallback);
      AppCompatDelegateImplV14 localAppCompatDelegateImplV14 = AppCompatDelegateImplV14.this;
      if (localAppCompatDelegateImplV14.mActionMode != null) {
        localAppCompatDelegateImplV14.mActionMode.finish();
      }
      AppCompatDelegateImplV7.ActionModeCallbackWrapperV7 localActionModeCallbackWrapperV71 = new AppCompatDelegateImplV7.ActionModeCallbackWrapperV7(localAppCompatDelegateImplV14, localCallbackWrapper);
      ActionBar localActionBar = localAppCompatDelegateImplV14.getSupportActionBar();
      if (localActionBar != null) {
        localAppCompatDelegateImplV14.mActionMode = localActionBar.startActionMode(localActionModeCallbackWrapperV71);
      }
      Object localObject;
      label331:
      boolean bool;
      if (localAppCompatDelegateImplV14.mActionMode == null)
      {
        localAppCompatDelegateImplV14.endOnGoingFadeAnimation();
        if (localAppCompatDelegateImplV14.mActionMode != null) {
          localAppCompatDelegateImplV14.mActionMode.finish();
        }
        AppCompatDelegateImplV7.ActionModeCallbackWrapperV7 localActionModeCallbackWrapperV72 = new AppCompatDelegateImplV7.ActionModeCallbackWrapperV7(localAppCompatDelegateImplV14, localActionModeCallbackWrapperV71);
        if (localAppCompatDelegateImplV14.mActionModeView == null)
        {
          if (!localAppCompatDelegateImplV14.mIsFloating) {
            break label524;
          }
          TypedValue localTypedValue = new TypedValue();
          Resources.Theme localTheme1 = localAppCompatDelegateImplV14.mContext.getTheme();
          localTheme1.resolveAttribute(R.attr.actionBarTheme, localTypedValue, true);
          if (localTypedValue.resourceId == 0) {
            break label515;
          }
          Resources.Theme localTheme2 = localAppCompatDelegateImplV14.mContext.getResources().newTheme();
          localTheme2.setTo(localTheme1);
          localTheme2.applyStyle(localTypedValue.resourceId, true);
          localObject = new ContextThemeWrapper(localAppCompatDelegateImplV14.mContext, 0);
          ((Context)localObject).getTheme().setTo(localTheme2);
          localAppCompatDelegateImplV14.mActionModeView = new ActionBarContextView((Context)localObject);
          localAppCompatDelegateImplV14.mActionModePopup = new PopupWindow((Context)localObject, null, R.attr.actionModePopupWindowStyle);
          PopupWindowCompat.setWindowLayoutType(localAppCompatDelegateImplV14.mActionModePopup, 2);
          localAppCompatDelegateImplV14.mActionModePopup.setContentView(localAppCompatDelegateImplV14.mActionModeView);
          localAppCompatDelegateImplV14.mActionModePopup.setWidth(-1);
          ((Context)localObject).getTheme().resolveAttribute(R.attr.actionBarSize, localTypedValue, true);
          int i = TypedValue.complexToDimensionPixelSize(localTypedValue.data, ((Context)localObject).getResources().getDisplayMetrics());
          localAppCompatDelegateImplV14.mActionModeView.setContentHeight(i);
          localAppCompatDelegateImplV14.mActionModePopup.setHeight(-2);
          localAppCompatDelegateImplV14.mShowActionModePopup = new AppCompatDelegateImplV7.5(localAppCompatDelegateImplV14);
        }
        if (localAppCompatDelegateImplV14.mActionModeView != null)
        {
          localAppCompatDelegateImplV14.endOnGoingFadeAnimation();
          localAppCompatDelegateImplV14.mActionModeView.killMode();
          Context localContext = localAppCompatDelegateImplV14.mActionModeView.getContext();
          ActionBarContextView localActionBarContextView = localAppCompatDelegateImplV14.mActionModeView;
          if (localAppCompatDelegateImplV14.mActionModePopup != null) {
            break label571;
          }
          bool = true;
          label374:
          StandaloneActionMode localStandaloneActionMode = new StandaloneActionMode(localContext, localActionBarContextView, localActionModeCallbackWrapperV72, bool);
          if (!localActionModeCallbackWrapperV71.onCreateActionMode(localStandaloneActionMode, localStandaloneActionMode.getMenu())) {
            break label577;
          }
          localStandaloneActionMode.invalidate();
          localAppCompatDelegateImplV14.mActionModeView.initForMode(localStandaloneActionMode);
          localAppCompatDelegateImplV14.mActionMode = localStandaloneActionMode;
          ViewCompat.setAlpha(localAppCompatDelegateImplV14.mActionModeView, 0.0F);
          localAppCompatDelegateImplV14.mFadeAnim = ViewCompat.animate(localAppCompatDelegateImplV14.mActionModeView).alpha(1.0F);
          localAppCompatDelegateImplV14.mFadeAnim.setListener(new AppCompatDelegateImplV7.6(localAppCompatDelegateImplV14));
          if (localAppCompatDelegateImplV14.mActionModePopup != null) {
            localAppCompatDelegateImplV14.mWindow.getDecorView().post(localAppCompatDelegateImplV14.mShowActionModePopup);
          }
        }
      }
      for (;;)
      {
        localAppCompatDelegateImplV14.mActionMode = localAppCompatDelegateImplV14.mActionMode;
        android.support.v7.view.ActionMode localActionMode = localAppCompatDelegateImplV14.mActionMode;
        if (localActionMode == null) {
          break label585;
        }
        return localCallbackWrapper.getActionModeWrapper(localActionMode);
        label515:
        localObject = localAppCompatDelegateImplV14.mContext;
        break;
        label524:
        ViewStubCompat localViewStubCompat = (ViewStubCompat)localAppCompatDelegateImplV14.mSubDecor.findViewById(R.id.action_mode_bar_stub);
        if (localViewStubCompat == null) {
          break label331;
        }
        localViewStubCompat.setLayoutInflater(LayoutInflater.from(localAppCompatDelegateImplV14.getActionBarThemedContext()));
        localAppCompatDelegateImplV14.mActionModeView = ((ActionBarContextView)localViewStubCompat.inflate());
        break label331;
        label571:
        bool = false;
        break label374;
        label577:
        localAppCompatDelegateImplV14.mActionMode = null;
      }
      label585:
      return null;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.app.AppCompatDelegateImplV14
 * JD-Core Version:    0.7.0.1
 */