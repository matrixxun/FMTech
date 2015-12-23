package android.support.v4.app;

import java.util.ArrayList;
import java.util.List;

public final class FragmentController
{
  final FragmentHostCallback<?> mHost;
  
  FragmentController(FragmentHostCallback<?> paramFragmentHostCallback)
  {
    this.mHost = paramFragmentHostCallback;
  }
  
  public final boolean execPendingActions()
  {
    return this.mHost.mFragmentManager.execPendingActions();
  }
  
  public final List<Fragment> getActiveFragments(List<Fragment> paramList)
  {
    if (this.mHost.mFragmentManager.mActive == null) {
      return null;
    }
    paramList.addAll(this.mHost.mFragmentManager.mActive);
    return paramList;
  }
  
  public final int getActiveFragmentsCount()
  {
    ArrayList localArrayList = this.mHost.mFragmentManager.mActive;
    if (localArrayList == null) {
      return 0;
    }
    return localArrayList.size();
  }
  
  public final void noteStateNotSaved()
  {
    this.mHost.mFragmentManager.mStateSaved = false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.app.FragmentController
 * JD-Core Version:    0.7.0.1
 */