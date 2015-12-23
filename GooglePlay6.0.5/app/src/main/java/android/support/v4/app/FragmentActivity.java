package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.util.SparseArrayCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FragmentActivity
  extends BaseFragmentActivityHoneycomb
{
  boolean mCreated;
  final FragmentController mFragments = new FragmentController(new HostCallbacks());
  final Handler mHandler = new Handler()
  {
    public final void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        super.handleMessage(paramAnonymousMessage);
      case 1: 
        do
        {
          return;
        } while (!FragmentActivity.this.mStopped);
        FragmentActivity.this.doReallyStop(false);
        return;
      }
      FragmentActivity.this.onResumeFragments();
      FragmentActivity.this.mFragments.execPendingActions();
    }
  };
  boolean mOptionsMenuInvalidated;
  boolean mReallyStopped;
  boolean mResumed;
  boolean mRetaining;
  boolean mStopped;
  
  private void dumpViewHierarchy(String paramString, PrintWriter paramPrintWriter, View paramView)
  {
    paramPrintWriter.print(paramString);
    if (paramView == null) {
      paramPrintWriter.println("null");
    }
    for (;;)
    {
      return;
      paramPrintWriter.println(viewToString(paramView));
      if ((paramView instanceof ViewGroup))
      {
        ViewGroup localViewGroup = (ViewGroup)paramView;
        int i = localViewGroup.getChildCount();
        if (i > 0)
        {
          String str = paramString + "  ";
          for (int j = 0; j < i; j++) {
            dumpViewHierarchy(str, paramPrintWriter, localViewGroup.getChildAt(j));
          }
        }
      }
    }
  }
  
  private static String viewToString(View paramView)
  {
    char c1 = 'F';
    char c2 = '.';
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append(paramView.getClass().getName());
    localStringBuilder.append('{');
    localStringBuilder.append(Integer.toHexString(System.identityHashCode(paramView)));
    localStringBuilder.append(' ');
    char c3;
    label108:
    char c4;
    label126:
    char c5;
    label143:
    char c6;
    label161:
    char c7;
    label179:
    char c8;
    label197:
    char c9;
    label215:
    label236:
    char c10;
    label253:
    int i;
    Resources localResources;
    switch (paramView.getVisibility())
    {
    default: 
      localStringBuilder.append(c2);
      if (paramView.isFocusable())
      {
        c3 = c1;
        localStringBuilder.append(c3);
        if (!paramView.isEnabled()) {
          break label533;
        }
        c4 = 'E';
        localStringBuilder.append(c4);
        if (!paramView.willNotDraw()) {
          break label539;
        }
        c5 = c2;
        localStringBuilder.append(c5);
        if (!paramView.isHorizontalScrollBarEnabled()) {
          break label546;
        }
        c6 = 'H';
        localStringBuilder.append(c6);
        if (!paramView.isVerticalScrollBarEnabled()) {
          break label552;
        }
        c7 = 'V';
        localStringBuilder.append(c7);
        if (!paramView.isClickable()) {
          break label558;
        }
        c8 = 'C';
        localStringBuilder.append(c8);
        if (!paramView.isLongClickable()) {
          break label564;
        }
        c9 = 'L';
        localStringBuilder.append(c9);
        localStringBuilder.append(' ');
        if (!paramView.isFocused()) {
          break label570;
        }
        localStringBuilder.append(c1);
        if (!paramView.isSelected()) {
          break label575;
        }
        c10 = 'S';
        localStringBuilder.append(c10);
        if (paramView.isPressed()) {
          c2 = 'P';
        }
        localStringBuilder.append(c2);
        localStringBuilder.append(' ');
        localStringBuilder.append(paramView.getLeft());
        localStringBuilder.append(',');
        localStringBuilder.append(paramView.getTop());
        localStringBuilder.append('-');
        localStringBuilder.append(paramView.getRight());
        localStringBuilder.append(',');
        localStringBuilder.append(paramView.getBottom());
        i = paramView.getId();
        if (i != -1)
        {
          localStringBuilder.append(" #");
          localStringBuilder.append(Integer.toHexString(i));
          localResources = paramView.getResources();
          if ((i != 0) && (localResources != null)) {
            switch (0xFF000000 & i)
            {
            }
          }
        }
      }
      break;
    }
    for (;;)
    {
      try
      {
        str1 = localResources.getResourcePackageName(i);
        String str2 = localResources.getResourceTypeName(i);
        String str3 = localResources.getResourceEntryName(i);
        localStringBuilder.append(" ");
        localStringBuilder.append(str1);
        localStringBuilder.append(":");
        localStringBuilder.append(str2);
        localStringBuilder.append("/");
        localStringBuilder.append(str3);
      }
      catch (Resources.NotFoundException localNotFoundException)
      {
        String str1;
        label533:
        label539:
        label546:
        label552:
        label558:
        label564:
        label570:
        label575:
        continue;
      }
      localStringBuilder.append("}");
      return localStringBuilder.toString();
      localStringBuilder.append('V');
      break;
      localStringBuilder.append('I');
      break;
      localStringBuilder.append('G');
      break;
      c3 = c2;
      break label108;
      c4 = c2;
      break label126;
      c5 = 'D';
      break label143;
      c6 = c2;
      break label161;
      c7 = c2;
      break label179;
      c8 = c2;
      break label197;
      c9 = c2;
      break label215;
      c1 = c2;
      break label236;
      c10 = c2;
      break label253;
      str1 = "app";
      continue;
      str1 = "android";
    }
  }
  
  final View dispatchFragmentsOnCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    return this.mFragments.mHost.mFragmentManager.onCreateView(paramView, paramString, paramContext, paramAttributeSet);
  }
  
  final void doReallyStop(boolean paramBoolean)
  {
    if (!this.mReallyStopped)
    {
      this.mReallyStopped = true;
      this.mRetaining = paramBoolean;
      this.mHandler.removeMessages(1);
      FragmentController localFragmentController = this.mFragments;
      boolean bool = this.mRetaining;
      FragmentHostCallback localFragmentHostCallback = localFragmentController.mHost;
      if ((localFragmentHostCallback.mLoaderManager != null) && (localFragmentHostCallback.mLoadersStarted))
      {
        localFragmentHostCallback.mLoadersStarted = false;
        if (!bool) {
          break label140;
        }
        localFragmentHostCallback.mLoaderManager.doRetain();
      }
      for (;;)
      {
        FragmentManagerImpl localFragmentManagerImpl = localFragmentController.mHost.mFragmentManager;
        if (localFragmentManagerImpl.mActive == null) {
          break;
        }
        for (int i = 0; i < localFragmentManagerImpl.mActive.size(); i++)
        {
          Fragment localFragment = (Fragment)localFragmentManagerImpl.mActive.get(i);
          if (localFragment != null) {
            localFragment.mRetainLoader = bool;
          }
        }
        label140:
        localFragmentHostCallback.mLoaderManager.doStop();
      }
      this.mFragments.mHost.mFragmentManager.moveToState$2563266(2);
    }
  }
  
  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("Local FragmentActivity ");
    paramPrintWriter.print(Integer.toHexString(System.identityHashCode(this)));
    paramPrintWriter.println(" State:");
    String str = paramString + "  ";
    paramPrintWriter.print(str);
    paramPrintWriter.print("mCreated=");
    paramPrintWriter.print(this.mCreated);
    paramPrintWriter.print("mResumed=");
    paramPrintWriter.print(this.mResumed);
    paramPrintWriter.print(" mStopped=");
    paramPrintWriter.print(this.mStopped);
    paramPrintWriter.print(" mReallyStopped=");
    paramPrintWriter.println(this.mReallyStopped);
    FragmentHostCallback localFragmentHostCallback = this.mFragments.mHost;
    paramPrintWriter.print(str);
    paramPrintWriter.print("mLoadersStarted=");
    paramPrintWriter.println(localFragmentHostCallback.mLoadersStarted);
    if (localFragmentHostCallback.mLoaderManager != null)
    {
      paramPrintWriter.print(str);
      paramPrintWriter.print("Loader Manager ");
      paramPrintWriter.print(Integer.toHexString(System.identityHashCode(localFragmentHostCallback.mLoaderManager)));
      paramPrintWriter.println(":");
      localFragmentHostCallback.mLoaderManager.dump(str + "  ", paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    }
    this.mFragments.mHost.mFragmentManager.dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    paramPrintWriter.print(paramString);
    paramPrintWriter.println("View Hierarchy:");
    dumpViewHierarchy(paramString + "  ", paramPrintWriter, getWindow().getDecorView());
  }
  
  public final FragmentManager getSupportFragmentManager()
  {
    return this.mFragments.mHost.mFragmentManager;
  }
  
  public final LoaderManager getSupportLoaderManager()
  {
    FragmentHostCallback localFragmentHostCallback = this.mFragments.mHost;
    if (localFragmentHostCallback.mLoaderManager != null) {
      return localFragmentHostCallback.mLoaderManager;
    }
    localFragmentHostCallback.mCheckedForLoaderManager = true;
    localFragmentHostCallback.mLoaderManager = localFragmentHostCallback.getLoaderManager("(root)", localFragmentHostCallback.mLoadersStarted, true);
    return localFragmentHostCallback.mLoaderManager;
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    this.mFragments.noteStateNotSaved();
    int i = paramInt1 >> 16;
    if (i != 0)
    {
      int j = i - 1;
      int k = this.mFragments.getActiveFragmentsCount();
      if ((k == 0) || (j < 0) || (j >= k))
      {
        Log.w("FragmentActivity", "Activity result fragment index out of range: 0x" + Integer.toHexString(paramInt1));
        return;
      }
      Fragment localFragment = (Fragment)this.mFragments.getActiveFragments(new ArrayList(k)).get(j);
      if (localFragment == null)
      {
        Log.w("FragmentActivity", "Activity result no fragment exists for index: 0x" + Integer.toHexString(paramInt1));
        return;
      }
      localFragment.onActivityResult(0xFFFF & paramInt1, paramInt2, paramIntent);
      return;
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public void onBackPressed()
  {
    if (!this.mFragments.mHost.mFragmentManager.popBackStackImmediate())
    {
      if (Build.VERSION.SDK_INT >= 21) {
        finishAfterTransition();
      }
    }
    else {
      return;
    }
    finish();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    this.mFragments.mHost.mFragmentManager.dispatchConfigurationChanged(paramConfiguration);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    FragmentController localFragmentController1 = this.mFragments;
    localFragmentController1.mHost.mFragmentManager.attachController(localFragmentController1.mHost, localFragmentController1.mHost, null);
    super.onCreate(paramBundle);
    NonConfigurationInstances localNonConfigurationInstances = (NonConfigurationInstances)getLastNonConfigurationInstance();
    if (localNonConfigurationInstances != null)
    {
      FragmentController localFragmentController3 = this.mFragments;
      SimpleArrayMap localSimpleArrayMap = localNonConfigurationInstances.loaders;
      localFragmentController3.mHost.mAllLoaderManagers = localSimpleArrayMap;
    }
    if (paramBundle != null)
    {
      Parcelable localParcelable = paramBundle.getParcelable("android:support:fragments");
      FragmentController localFragmentController2 = this.mFragments;
      List localList = null;
      if (localNonConfigurationInstances != null) {
        localList = localNonConfigurationInstances.fragments;
      }
      localFragmentController2.mHost.mFragmentManager.restoreAllState(localParcelable, localList);
    }
    this.mFragments.mHost.mFragmentManager.dispatchCreate();
  }
  
  public boolean onCreatePanelMenu(int paramInt, Menu paramMenu)
  {
    if (paramInt == 0)
    {
      boolean bool1 = super.onCreatePanelMenu(paramInt, paramMenu);
      FragmentController localFragmentController = this.mFragments;
      MenuInflater localMenuInflater = getMenuInflater();
      boolean bool2 = bool1 | localFragmentController.mHost.mFragmentManager.dispatchCreateOptionsMenu(paramMenu, localMenuInflater);
      if (Build.VERSION.SDK_INT >= 11) {
        return bool2;
      }
      return true;
    }
    return super.onCreatePanelMenu(paramInt, paramMenu);
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    doReallyStop(false);
    this.mFragments.mHost.mFragmentManager.dispatchDestroy();
    FragmentHostCallback localFragmentHostCallback = this.mFragments.mHost;
    if (localFragmentHostCallback.mLoaderManager != null) {
      localFragmentHostCallback.mLoaderManager.doDestroy();
    }
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((Build.VERSION.SDK_INT < 5) && (paramInt == 4) && (paramKeyEvent.getRepeatCount() == 0))
    {
      onBackPressed();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public void onLowMemory()
  {
    super.onLowMemory();
    this.mFragments.mHost.mFragmentManager.dispatchLowMemory();
  }
  
  public boolean onMenuItemSelected(int paramInt, MenuItem paramMenuItem)
  {
    if (super.onMenuItemSelected(paramInt, paramMenuItem)) {
      return true;
    }
    switch (paramInt)
    {
    default: 
      return false;
    case 0: 
      return this.mFragments.mHost.mFragmentManager.dispatchOptionsItemSelected(paramMenuItem);
    }
    return this.mFragments.mHost.mFragmentManager.dispatchContextItemSelected(paramMenuItem);
  }
  
  public void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    this.mFragments.noteStateNotSaved();
  }
  
  public void onPanelClosed(int paramInt, Menu paramMenu)
  {
    switch (paramInt)
    {
    }
    for (;;)
    {
      super.onPanelClosed(paramInt, paramMenu);
      return;
      this.mFragments.mHost.mFragmentManager.dispatchOptionsMenuClosed(paramMenu);
    }
  }
  
  public void onPause()
  {
    super.onPause();
    this.mResumed = false;
    if (this.mHandler.hasMessages(2))
    {
      this.mHandler.removeMessages(2);
      onResumeFragments();
    }
    this.mFragments.mHost.mFragmentManager.moveToState$2563266(4);
  }
  
  public void onPostResume()
  {
    super.onPostResume();
    this.mHandler.removeMessages(2);
    onResumeFragments();
    this.mFragments.execPendingActions();
  }
  
  public boolean onPreparePanel(int paramInt, View paramView, Menu paramMenu)
  {
    if ((paramInt == 0) && (paramMenu != null))
    {
      if (this.mOptionsMenuInvalidated)
      {
        this.mOptionsMenuInvalidated = false;
        paramMenu.clear();
        onCreatePanelMenu(paramInt, paramMenu);
      }
      return super.onPreparePanel(0, paramView, paramMenu) | this.mFragments.mHost.mFragmentManager.dispatchPrepareOptionsMenu(paramMenu);
    }
    return super.onPreparePanel(paramInt, paramView, paramMenu);
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfInt)
  {
    int i = 0xFF & paramInt >> 8;
    int j;
    int k;
    if (i != 0)
    {
      j = i - 1;
      k = this.mFragments.getActiveFragmentsCount();
      if ((k == 0) || (j < 0) || (j >= k)) {
        Log.w("FragmentActivity", "Activity result fragment index out of range: 0x" + Integer.toHexString(paramInt));
      }
    }
    else
    {
      return;
    }
    if ((Fragment)this.mFragments.getActiveFragments(new ArrayList(k)).get(j) == null)
    {
      Log.w("FragmentActivity", "Activity result no fragment exists for index: 0x" + Integer.toHexString(paramInt));
      return;
    }
    Fragment.onRequestPermissionsResult$6f5af501();
  }
  
  public void onResume()
  {
    super.onResume();
    this.mHandler.sendEmptyMessage(2);
    this.mResumed = true;
    this.mFragments.execPendingActions();
  }
  
  public void onResumeFragments()
  {
    this.mFragments.mHost.mFragmentManager.dispatchResume();
  }
  
  public Object onRetainCustomNonConfigurationInstance()
  {
    return null;
  }
  
  public final Object onRetainNonConfigurationInstance()
  {
    if (this.mStopped) {
      doReallyStop(true);
    }
    Object localObject = onRetainCustomNonConfigurationInstance();
    FragmentManagerImpl localFragmentManagerImpl = this.mFragments.mHost.mFragmentManager;
    ArrayList localArrayList2;
    if (localFragmentManagerImpl.mActive != null)
    {
      int i = 0;
      localArrayList2 = null;
      if (i < localFragmentManagerImpl.mActive.size())
      {
        Fragment localFragment = (Fragment)localFragmentManagerImpl.mActive.get(i);
        if ((localFragment != null) && (localFragment.mRetainInstance))
        {
          if (localArrayList2 == null) {
            localArrayList2 = new ArrayList();
          }
          localArrayList2.add(localFragment);
          localFragment.mRetaining = true;
          if (localFragment.mTarget == null) {
            break label170;
          }
        }
        label170:
        for (int j = localFragment.mTarget.mIndex;; j = -1)
        {
          localFragment.mTargetIndex = j;
          if (FragmentManagerImpl.DEBUG) {
            Log.v("FragmentManager", "retainNonConfig: keeping retained " + localFragment);
          }
          i++;
          break;
        }
      }
    }
    for (ArrayList localArrayList1 = localArrayList2;; localArrayList1 = null)
    {
      SimpleArrayMap localSimpleArrayMap = this.mFragments.mHost.retainLoaderNonConfig();
      if ((localArrayList1 == null) && (localSimpleArrayMap == null) && (localObject == null)) {
        return null;
      }
      NonConfigurationInstances localNonConfigurationInstances = new NonConfigurationInstances();
      localNonConfigurationInstances.custom = localObject;
      localNonConfigurationInstances.fragments = localArrayList1;
      localNonConfigurationInstances.loaders = localSimpleArrayMap;
      return localNonConfigurationInstances;
    }
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    Parcelable localParcelable = this.mFragments.mHost.mFragmentManager.saveAllState();
    if (localParcelable != null) {
      paramBundle.putParcelable("android:support:fragments", localParcelable);
    }
  }
  
  public void onStart()
  {
    super.onStart();
    this.mStopped = false;
    this.mReallyStopped = false;
    this.mHandler.removeMessages(1);
    if (!this.mCreated)
    {
      this.mCreated = true;
      this.mFragments.mHost.mFragmentManager.dispatchActivityCreated();
    }
    this.mFragments.noteStateNotSaved();
    this.mFragments.execPendingActions();
    FragmentHostCallback localFragmentHostCallback1 = this.mFragments.mHost;
    if (!localFragmentHostCallback1.mLoadersStarted)
    {
      localFragmentHostCallback1.mLoadersStarted = true;
      if (localFragmentHostCallback1.mLoaderManager == null) {
        break label179;
      }
      localFragmentHostCallback1.mLoaderManager.doStart();
    }
    int i;
    LoaderManagerImpl[] arrayOfLoaderManagerImpl;
    for (;;)
    {
      localFragmentHostCallback1.mCheckedForLoaderManager = true;
      this.mFragments.mHost.mFragmentManager.dispatchStart();
      FragmentHostCallback localFragmentHostCallback2 = this.mFragments.mHost;
      if (localFragmentHostCallback2.mAllLoaderManagers == null) {
        return;
      }
      i = localFragmentHostCallback2.mAllLoaderManagers.size();
      arrayOfLoaderManagerImpl = new LoaderManagerImpl[i];
      for (int j = i - 1; j >= 0; j--) {
        arrayOfLoaderManagerImpl[j] = ((LoaderManagerImpl)localFragmentHostCallback2.mAllLoaderManagers.valueAt(j));
      }
      label179:
      if (!localFragmentHostCallback1.mCheckedForLoaderManager)
      {
        localFragmentHostCallback1.mLoaderManager = localFragmentHostCallback1.getLoaderManager("(root)", localFragmentHostCallback1.mLoadersStarted, false);
        if ((localFragmentHostCallback1.mLoaderManager != null) && (!localFragmentHostCallback1.mLoaderManager.mStarted)) {
          localFragmentHostCallback1.mLoaderManager.doStart();
        }
      }
    }
    for (int k = 0; k < i; k++)
    {
      LoaderManagerImpl localLoaderManagerImpl = arrayOfLoaderManagerImpl[k];
      if (localLoaderManagerImpl.mRetaining)
      {
        if (LoaderManagerImpl.DEBUG) {
          Log.v("LoaderManager", "Finished Retaining in " + localLoaderManagerImpl);
        }
        localLoaderManagerImpl.mRetaining = false;
        for (int m = -1 + localLoaderManagerImpl.mLoaders.size(); m >= 0; m--)
        {
          LoaderManagerImpl.LoaderInfo localLoaderInfo = (LoaderManagerImpl.LoaderInfo)localLoaderManagerImpl.mLoaders.valueAt(m);
          if (localLoaderInfo.mRetaining)
          {
            if (LoaderManagerImpl.DEBUG) {
              Log.v("LoaderManager", "  Finished Retaining: " + localLoaderInfo);
            }
            localLoaderInfo.mRetaining = false;
            if ((localLoaderInfo.mStarted != localLoaderInfo.mRetainingStarted) && (!localLoaderInfo.mStarted)) {
              localLoaderInfo.stop();
            }
          }
          if ((localLoaderInfo.mStarted) && (localLoaderInfo.mHaveData) && (!localLoaderInfo.mReportNextStart)) {
            localLoaderInfo.callOnLoadFinished(localLoaderInfo.mLoader, localLoaderInfo.mData);
          }
        }
      }
      localLoaderManagerImpl.doReportStart();
    }
  }
  
  public void onStateNotSaved()
  {
    this.mFragments.noteStateNotSaved();
  }
  
  public void onStop()
  {
    super.onStop();
    this.mStopped = true;
    this.mHandler.sendEmptyMessage(1);
    this.mFragments.mHost.mFragmentManager.dispatchStop();
  }
  
  public void startActivityForResult(Intent paramIntent, int paramInt)
  {
    if ((paramInt != -1) && ((0xFFFF0000 & paramInt) != 0)) {
      throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
    }
    super.startActivityForResult(paramIntent, paramInt);
  }
  
  public final void startActivityFromFragment(Fragment paramFragment, Intent paramIntent, int paramInt)
  {
    if (paramInt == -1)
    {
      super.startActivityForResult(paramIntent, -1);
      return;
    }
    if ((0xFFFF0000 & paramInt) != 0) {
      throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
    }
    super.startActivityForResult(paramIntent, (1 + paramFragment.mIndex << 16) + (0xFFFF & paramInt));
  }
  
  public void supportInvalidateOptionsMenu()
  {
    if (Build.VERSION.SDK_INT >= 11)
    {
      invalidateOptionsMenu();
      return;
    }
    this.mOptionsMenuInvalidated = true;
  }
  
  final class HostCallbacks
    extends FragmentHostCallback<FragmentActivity>
  {
    public HostCallbacks()
    {
      super();
    }
    
    public final void onDump$ec96877(String paramString, PrintWriter paramPrintWriter, String[] paramArrayOfString)
    {
      FragmentActivity.this.dump(paramString, null, paramPrintWriter, paramArrayOfString);
    }
    
    public final View onFindViewById(int paramInt)
    {
      return FragmentActivity.this.findViewById(paramInt);
    }
    
    public final LayoutInflater onGetLayoutInflater()
    {
      return FragmentActivity.this.getLayoutInflater().cloneInContext(FragmentActivity.this);
    }
    
    public final int onGetWindowAnimations()
    {
      Window localWindow = FragmentActivity.this.getWindow();
      if (localWindow == null) {
        return 0;
      }
      return localWindow.getAttributes().windowAnimations;
    }
    
    public final boolean onHasView()
    {
      Window localWindow = FragmentActivity.this.getWindow();
      return (localWindow != null) && (localWindow.peekDecorView() != null);
    }
    
    public final boolean onHasWindowAnimations()
    {
      return FragmentActivity.this.getWindow() != null;
    }
    
    public final boolean onShouldSaveFragmentState$6585081f()
    {
      return !FragmentActivity.this.isFinishing();
    }
    
    public final void onStartActivityFromFragment(Fragment paramFragment, Intent paramIntent, int paramInt)
    {
      FragmentActivity.this.startActivityFromFragment(paramFragment, paramIntent, paramInt);
    }
    
    public final void onSupportInvalidateOptionsMenu()
    {
      FragmentActivity.this.supportInvalidateOptionsMenu();
    }
  }
  
  static final class NonConfigurationInstances
  {
    Object custom;
    List<Fragment> fragments;
    SimpleArrayMap<String, LoaderManager> loaders;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.app.FragmentActivity
 * JD-Core Version:    0.7.0.1
 */