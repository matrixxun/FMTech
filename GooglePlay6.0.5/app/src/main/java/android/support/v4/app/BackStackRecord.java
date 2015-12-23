package android.support.v4.app;

import android.os.Build.VERSION;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LogWriter;
import android.support.v4.util.MapCollections;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

final class BackStackRecord
  extends FragmentTransaction
  implements FragmentManager.BackStackEntry, Runnable
{
  static final boolean SUPPORTS_TRANSITIONS;
  boolean mAddToBackStack;
  boolean mAllowAddToBackStack = true;
  int mBreadCrumbShortTitleRes;
  CharSequence mBreadCrumbShortTitleText;
  int mBreadCrumbTitleRes;
  CharSequence mBreadCrumbTitleText;
  boolean mCommitted;
  int mEnterAnim;
  int mExitAnim;
  Op mHead;
  int mIndex = -1;
  final FragmentManagerImpl mManager;
  String mName;
  int mNumOp;
  int mPopEnterAnim;
  int mPopExitAnim;
  ArrayList<String> mSharedElementSourceNames;
  ArrayList<String> mSharedElementTargetNames;
  Op mTail;
  int mTransition;
  int mTransitionStyle;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 21) {}
    for (boolean bool = true;; bool = false)
    {
      SUPPORTS_TRANSITIONS = bool;
      return;
    }
  }
  
  public BackStackRecord(FragmentManagerImpl paramFragmentManagerImpl)
  {
    this.mManager = paramFragmentManagerImpl;
  }
  
  private TransitionState beginTransition(SparseArray<Fragment> paramSparseArray1, SparseArray<Fragment> paramSparseArray2, boolean paramBoolean)
  {
    TransitionState localTransitionState = new TransitionState();
    localTransitionState.nonExistentView = new View(this.mManager.mHost.mContext);
    int i = 0;
    for (int j = 0; j < paramSparseArray1.size(); j++) {
      if (configureTransitions(paramSparseArray1.keyAt(j), localTransitionState, paramBoolean, paramSparseArray1, paramSparseArray2)) {
        i = 1;
      }
    }
    for (int k = 0; k < paramSparseArray2.size(); k++)
    {
      int m = paramSparseArray2.keyAt(k);
      if ((paramSparseArray1.get(m) == null) && (configureTransitions(m, localTransitionState, paramBoolean, paramSparseArray1, paramSparseArray2))) {
        i = 1;
      }
    }
    if (i == 0) {
      localTransitionState = null;
    }
    return localTransitionState;
  }
  
  private void calculateFragments(SparseArray<Fragment> paramSparseArray1, SparseArray<Fragment> paramSparseArray2)
  {
    if (!this.mManager.mContainer.onHasView()) {}
    Op localOp;
    do
    {
      return;
      localOp = this.mHead;
    } while (localOp == null);
    switch (localOp.cmd)
    {
    }
    for (;;)
    {
      localOp = localOp.next;
      break;
      setLastIn(paramSparseArray2, localOp.fragment);
      continue;
      Fragment localFragment1 = localOp.fragment;
      if (this.mManager.mAdded != null)
      {
        int i = 0;
        if (i < this.mManager.mAdded.size())
        {
          Fragment localFragment2 = (Fragment)this.mManager.mAdded.get(i);
          if ((localFragment1 == null) || (localFragment2.mContainerId == localFragment1.mContainerId))
          {
            if (localFragment2 != localFragment1) {
              break label172;
            }
            localFragment1 = null;
          }
          for (;;)
          {
            i++;
            break;
            label172:
            setFirstOut(paramSparseArray1, localFragment2);
          }
        }
      }
      setLastIn(paramSparseArray2, localFragment1);
      continue;
      setFirstOut(paramSparseArray1, localOp.fragment);
      continue;
      setFirstOut(paramSparseArray1, localOp.fragment);
      continue;
      setLastIn(paramSparseArray2, localOp.fragment);
      continue;
      setFirstOut(paramSparseArray1, localOp.fragment);
      continue;
      setLastIn(paramSparseArray2, localOp.fragment);
    }
  }
  
  private static Object captureExitingViews(Object paramObject, Fragment paramFragment, ArrayList<View> paramArrayList, ArrayMap<String, View> paramArrayMap, View paramView)
  {
    if (paramObject != null)
    {
      View localView = paramFragment.mView;
      if (paramObject != null)
      {
        FragmentTransitionCompat21.captureTransitioningViews(paramArrayList, localView);
        if (paramArrayMap != null) {
          paramArrayList.removeAll(paramArrayMap.values());
        }
        if (!paramArrayList.isEmpty()) {
          break label46;
        }
        paramObject = null;
      }
    }
    return paramObject;
    label46:
    paramArrayList.add(paramView);
    FragmentTransitionCompat21.addTargets((Transition)paramObject, paramArrayList);
    return paramObject;
  }
  
  private int commitInternal(boolean paramBoolean)
  {
    if (this.mCommitted) {
      throw new IllegalStateException("commit already called");
    }
    if (FragmentManagerImpl.DEBUG)
    {
      Log.v("FragmentManager", "Commit: " + this);
      dump$ec96877("  ", new PrintWriter(new LogWriter("FragmentManager")));
    }
    this.mCommitted = true;
    if (this.mAddToBackStack) {}
    for (this.mIndex = this.mManager.allocBackStackIndex(this);; this.mIndex = -1)
    {
      this.mManager.enqueueAction(this, paramBoolean);
      return this.mIndex;
    }
  }
  
  private boolean configureTransitions(final int paramInt, final TransitionState paramTransitionState, final boolean paramBoolean, SparseArray<Fragment> paramSparseArray1, SparseArray<Fragment> paramSparseArray2)
  {
    final ViewGroup localViewGroup = (ViewGroup)this.mManager.mContainer.onFindViewById(paramInt);
    if (localViewGroup == null) {
      return false;
    }
    final Fragment localFragment1 = (Fragment)paramSparseArray2.get(paramInt);
    final Fragment localFragment2 = (Fragment)paramSparseArray1.get(paramInt);
    Object localObject2;
    final TransitionSet localTransitionSet;
    if (localFragment1 == null)
    {
      localObject2 = null;
      if ((localFragment1 != null) && (localFragment2 != null)) {
        break label184;
      }
      localTransitionSet = null;
    }
    Object localObject4;
    final ArrayList localArrayList1;
    ArrayMap localArrayMap1;
    for (;;)
    {
      if (localFragment2 != null) {
        break label275;
      }
      localObject4 = null;
      localArrayList1 = new ArrayList();
      localArrayMap1 = null;
      if (localTransitionSet != null)
      {
        localArrayMap1 = remapSharedElements(paramTransitionState, localFragment2, paramBoolean);
        if (!localArrayMap1.isEmpty()) {
          break label327;
        }
        localTransitionSet = null;
        localArrayMap1 = null;
      }
      if ((localObject2 != null) || (localTransitionSet != null) || (localObject4 != null)) {
        break label421;
      }
      return false;
      Object localObject1;
      if (paramBoolean) {
        if (localFragment1.mReenterTransition == Fragment.USE_DEFAULT_TRANSITION) {
          localObject1 = localFragment1.mExitTransition;
        }
      }
      for (;;)
      {
        localObject2 = FragmentTransitionCompat21.cloneTransition(localObject1);
        break;
        localObject1 = localFragment1.mReenterTransition;
        continue;
        localObject1 = localFragment1.mEnterTransition;
      }
      label184:
      Object localObject9;
      if (paramBoolean) {
        if (localFragment2.mSharedElementReturnTransition == Fragment.USE_DEFAULT_TRANSITION) {
          localObject9 = localFragment2.mSharedElementEnterTransition;
        }
      }
      for (;;)
      {
        if (localObject9 != null) {
          break label237;
        }
        localTransitionSet = null;
        break;
        localObject9 = localFragment2.mSharedElementReturnTransition;
        continue;
        localObject9 = localFragment1.mSharedElementEnterTransition;
      }
      label237:
      Transition localTransition8 = (Transition)localObject9;
      if (localTransition8 == null)
      {
        localTransitionSet = null;
      }
      else
      {
        localTransitionSet = new TransitionSet();
        localTransitionSet.addTransition(localTransition8);
      }
    }
    label275:
    Object localObject3;
    if (paramBoolean) {
      if (localFragment2.mReturnTransition == Fragment.USE_DEFAULT_TRANSITION) {
        localObject3 = localFragment2.mEnterTransition;
      }
    }
    for (;;)
    {
      localObject4 = FragmentTransitionCompat21.cloneTransition(localObject3);
      break;
      localObject3 = localFragment2.mReturnTransition;
      continue;
      localObject3 = localFragment2.mExitTransition;
    }
    label327:
    if (paramBoolean) {}
    for (SharedElementCallback localSharedElementCallback = localFragment2.mEnterTransitionCallback;; localSharedElementCallback = localFragment1.mEnterTransitionCallback)
    {
      if (localSharedElementCallback != null)
      {
        ArrayList localArrayList5 = new ArrayList(localArrayMap1.keySet());
        ArrayList localArrayList6 = new ArrayList(localArrayMap1.values());
        localSharedElementCallback.onSharedElementStart$70d861b8(localArrayList5, localArrayList6);
      }
      localViewGroup.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
      {
        public final boolean onPreDraw()
        {
          localViewGroup.getViewTreeObserver().removeOnPreDrawListener(this);
          if (localTransitionSet != null)
          {
            FragmentTransitionCompat21.removeTargets(localTransitionSet, localArrayList1);
            localArrayList1.clear();
            ArrayMap localArrayMap = BackStackRecord.access$000(BackStackRecord.this, paramTransitionState, paramBoolean, localFragment1);
            FragmentTransitionCompat21.setSharedElementTargets(localTransitionSet, paramTransitionState.nonExistentView, localArrayMap, localArrayList1);
            BackStackRecord.access$100(BackStackRecord.this, localArrayMap, paramTransitionState);
            BackStackRecord.access$200$4a5f5891(localFragment1, localFragment2, paramBoolean, localArrayMap);
          }
          return true;
        }
      });
      break;
    }
    label421:
    ArrayList localArrayList2 = new ArrayList();
    View localView1 = paramTransitionState.nonExistentView;
    Object localObject5 = captureExitingViews(localObject4, localFragment2, localArrayList2, localArrayMap1, localView1);
    if ((this.mSharedElementTargetNames != null) && (localArrayMap1 != null))
    {
      Object localObject8 = this.mSharedElementTargetNames.get(0);
      View localView5 = (View)localArrayMap1.get(localObject8);
      if (localView5 != null)
      {
        if (localObject5 != null) {
          FragmentTransitionCompat21.setEpicenter(localObject5, localView5);
        }
        if (localTransitionSet != null) {
          FragmentTransitionCompat21.setEpicenter(localTransitionSet, localView5);
        }
      }
    }
    FragmentTransitionCompat21.ViewRetriever local1 = new FragmentTransitionCompat21.ViewRetriever()
    {
      public final View getView()
      {
        return localFragment1.mView;
      }
    };
    ArrayList localArrayList3 = new ArrayList();
    ArrayMap localArrayMap2 = new ArrayMap();
    boolean bool = true;
    Object localObject6;
    Transition localTransition1;
    Transition localTransition2;
    if (localFragment1 != null)
    {
      if (!paramBoolean) {
        break label924;
      }
      if (localFragment1.mAllowReturnTransitionOverlap == null) {
        bool = true;
      }
    }
    else
    {
      localObject6 = (Transition)localObject2;
      localTransition1 = (Transition)localObject5;
      localTransition2 = (Transition)localTransitionSet;
      if ((localObject6 == null) || (localTransition1 == null)) {
        break label1054;
      }
    }
    for (;;)
    {
      final Object localObject7;
      if (bool)
      {
        localObject7 = new TransitionSet();
        if (localObject6 != null) {
          ((TransitionSet)localObject7).addTransition((Transition)localObject6);
        }
        if (localTransition1 != null) {
          ((TransitionSet)localObject7).addTransition(localTransition1);
        }
        if (localTransition2 != null) {
          ((TransitionSet)localObject7).addTransition(localTransition2);
        }
      }
      for (;;)
      {
        if (localObject7 != null)
        {
          View localView2 = paramTransitionState.nonExistentView;
          FragmentTransitionCompat21.EpicenterView localEpicenterView = paramTransitionState.enteringEpicenterView;
          ArrayMap localArrayMap3 = paramTransitionState.nameOverrides;
          if ((localObject2 != null) || (localTransitionSet != null))
          {
            Transition localTransition3 = (Transition)localObject2;
            if (localTransition3 != null) {
              localTransition3.addTarget(localView2);
            }
            if (localTransitionSet != null) {
              FragmentTransitionCompat21.setSharedElementTargets(localTransitionSet, localView2, localArrayMap1, localArrayList1);
            }
            localViewGroup.getViewTreeObserver().addOnPreDrawListener(new FragmentTransitionCompat21.2(localViewGroup, localTransition3, localView2, local1, localArrayMap3, localArrayMap2, localArrayList3));
            if (localTransition3 != null) {
              localTransition3.setEpicenterCallback(new FragmentTransitionCompat21.3(localEpicenterView));
            }
          }
          localViewGroup.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
          {
            public final boolean onPreDraw()
            {
              localViewGroup.getViewTreeObserver().removeOnPreDrawListener(this);
              BackStackRecord.this.excludeHiddenFragments(paramTransitionState, paramInt, localObject7);
              return true;
            }
          });
          View localView3 = paramTransitionState.nonExistentView;
          FragmentTransitionCompat21.excludeTarget(localObject7, localView3, true);
          excludeHiddenFragments(paramTransitionState, paramInt, localObject7);
          TransitionManager.beginDelayedTransition(localViewGroup, (Transition)localObject7);
          View localView4 = paramTransitionState.nonExistentView;
          ArrayList localArrayList4 = paramTransitionState.hiddenFragmentViews;
          Transition localTransition4 = (Transition)localObject2;
          Transition localTransition5 = (Transition)localObject5;
          Transition localTransition6 = (Transition)localTransitionSet;
          Transition localTransition7 = (Transition)localObject7;
          if (localTransition7 != null) {
            localViewGroup.getViewTreeObserver().addOnPreDrawListener(new FragmentTransitionCompat21.4(localViewGroup, localTransition4, localArrayList3, localTransition5, localArrayList2, localTransition6, localArrayList1, localArrayMap2, localArrayList4, localTransition7, localView4));
          }
        }
        if (localObject7 == null) {
          break label1052;
        }
        return true;
        bool = localFragment1.mAllowReturnTransitionOverlap.booleanValue();
        break;
        label924:
        if (localFragment1.mAllowEnterTransitionOverlap == null)
        {
          bool = true;
          break;
        }
        bool = localFragment1.mAllowEnterTransitionOverlap.booleanValue();
        break;
        if ((localTransition1 != null) && (localObject6 != null)) {
          localObject6 = new TransitionSet().addTransition(localTransition1).addTransition((Transition)localObject6).setOrdering(1);
        }
        for (;;)
        {
          if (localTransition2 == null) {
            break label1045;
          }
          localObject7 = new TransitionSet();
          if (localObject6 != null) {
            ((TransitionSet)localObject7).addTransition((Transition)localObject6);
          }
          ((TransitionSet)localObject7).addTransition(localTransition2);
          break;
          if (localTransition1 != null) {
            localObject6 = localTransition1;
          } else if (localObject6 == null) {
            localObject6 = null;
          }
        }
        label1045:
        localObject7 = localObject6;
      }
      label1052:
      return false;
      label1054:
      bool = true;
    }
  }
  
  private void doAddOp(int paramInt1, Fragment paramFragment, String paramString, int paramInt2)
  {
    paramFragment.mFragmentManager = this.mManager;
    if (paramString != null)
    {
      if ((paramFragment.mTag != null) && (!paramString.equals(paramFragment.mTag))) {
        throw new IllegalStateException("Can't change tag of fragment " + paramFragment + ": was " + paramFragment.mTag + " now " + paramString);
      }
      paramFragment.mTag = paramString;
    }
    if (paramInt1 != 0)
    {
      if ((paramFragment.mFragmentId != 0) && (paramFragment.mFragmentId != paramInt1)) {
        throw new IllegalStateException("Can't change container ID of fragment " + paramFragment + ": was " + paramFragment.mFragmentId + " now " + paramInt1);
      }
      paramFragment.mFragmentId = paramInt1;
      paramFragment.mContainerId = paramInt1;
    }
    Op localOp = new Op();
    localOp.cmd = paramInt2;
    localOp.fragment = paramFragment;
    addOp(localOp);
  }
  
  private void excludeHiddenFragments(TransitionState paramTransitionState, int paramInt, Object paramObject)
  {
    if (this.mManager.mAdded != null)
    {
      int i = 0;
      if (i < this.mManager.mAdded.size())
      {
        Fragment localFragment = (Fragment)this.mManager.mAdded.get(i);
        if ((localFragment.mView != null) && (localFragment.mContainer != null) && (localFragment.mContainerId == paramInt))
        {
          if (!localFragment.mHidden) {
            break label122;
          }
          if (!paramTransitionState.hiddenFragmentViews.contains(localFragment.mView))
          {
            FragmentTransitionCompat21.excludeTarget(paramObject, localFragment.mView, true);
            paramTransitionState.hiddenFragmentViews.add(localFragment.mView);
          }
        }
        for (;;)
        {
          i++;
          break;
          label122:
          FragmentTransitionCompat21.excludeTarget(paramObject, localFragment.mView, false);
          paramTransitionState.hiddenFragmentViews.remove(localFragment.mView);
        }
      }
    }
  }
  
  private static ArrayMap<String, View> remapNames(ArrayList<String> paramArrayList1, ArrayList<String> paramArrayList2, ArrayMap<String, View> paramArrayMap)
  {
    if (paramArrayMap.isEmpty()) {
      return paramArrayMap;
    }
    ArrayMap localArrayMap = new ArrayMap();
    int i = paramArrayList1.size();
    for (int j = 0; j < i; j++)
    {
      View localView = (View)paramArrayMap.get(paramArrayList1.get(j));
      if (localView != null) {
        localArrayMap.put(paramArrayList2.get(j), localView);
      }
    }
    return localArrayMap;
  }
  
  private ArrayMap<String, View> remapSharedElements(TransitionState paramTransitionState, Fragment paramFragment, boolean paramBoolean)
  {
    ArrayMap localArrayMap = new ArrayMap();
    if (this.mSharedElementSourceNames != null)
    {
      FragmentTransitionCompat21.findNamedViews(localArrayMap, paramFragment.mView);
      if (!paramBoolean) {
        break label66;
      }
      MapCollections.retainAllHelper(localArrayMap, this.mSharedElementTargetNames);
    }
    while (paramBoolean)
    {
      if (paramFragment.mEnterTransitionCallback != null) {}
      setBackNameOverrides(paramTransitionState, localArrayMap, false);
      return localArrayMap;
      label66:
      localArrayMap = remapNames(this.mSharedElementSourceNames, this.mSharedElementTargetNames, localArrayMap);
    }
    if (paramFragment.mExitTransitionCallback != null) {}
    setNameOverrides(paramTransitionState, localArrayMap, false);
    return localArrayMap;
  }
  
  private void setBackNameOverrides(TransitionState paramTransitionState, ArrayMap<String, View> paramArrayMap, boolean paramBoolean)
  {
    int i;
    int j;
    label13:
    String str1;
    String str2;
    if (this.mSharedElementTargetNames == null)
    {
      i = 0;
      j = 0;
      if (j >= i) {
        return;
      }
      str1 = (String)this.mSharedElementSourceNames.get(j);
      View localView = (View)paramArrayMap.get((String)this.mSharedElementTargetNames.get(j));
      if (localView != null)
      {
        str2 = localView.getTransitionName();
        if (!paramBoolean) {
          break label100;
        }
        setNameOverride(paramTransitionState.nameOverrides, str1, str2);
      }
    }
    for (;;)
    {
      j++;
      break label13;
      i = this.mSharedElementTargetNames.size();
      break;
      label100:
      setNameOverride(paramTransitionState.nameOverrides, str2, str1);
    }
  }
  
  private static void setFirstOut(SparseArray<Fragment> paramSparseArray, Fragment paramFragment)
  {
    if (paramFragment != null)
    {
      int i = paramFragment.mContainerId;
      if ((i != 0) && (!paramFragment.mHidden) && (paramFragment.isAdded()) && (paramFragment.mView != null) && (paramSparseArray.get(i) == null)) {
        paramSparseArray.put(i, paramFragment);
      }
    }
  }
  
  private static void setLastIn(SparseArray<Fragment> paramSparseArray, Fragment paramFragment)
  {
    if (paramFragment != null)
    {
      int i = paramFragment.mContainerId;
      if (i != 0) {
        paramSparseArray.put(i, paramFragment);
      }
    }
  }
  
  private static void setNameOverride(ArrayMap<String, String> paramArrayMap, String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (paramString2 != null)) {}
    for (int i = 0; i < paramArrayMap.size(); i++) {
      if (paramString1.equals(paramArrayMap.valueAt(i)))
      {
        paramArrayMap.setValueAt(i, paramString2);
        return;
      }
    }
    paramArrayMap.put(paramString1, paramString2);
  }
  
  private static void setNameOverrides(TransitionState paramTransitionState, ArrayMap<String, View> paramArrayMap, boolean paramBoolean)
  {
    int i = paramArrayMap.size();
    int j = 0;
    if (j < i)
    {
      String str1 = (String)paramArrayMap.keyAt(j);
      String str2 = ((View)paramArrayMap.valueAt(j)).getTransitionName();
      if (paramBoolean) {
        setNameOverride(paramTransitionState.nameOverrides, str1, str2);
      }
      for (;;)
      {
        j++;
        break;
        setNameOverride(paramTransitionState.nameOverrides, str2, str1);
      }
    }
  }
  
  private static void setNameOverrides(TransitionState paramTransitionState, ArrayList<String> paramArrayList1, ArrayList<String> paramArrayList2)
  {
    if (paramArrayList1 != null) {
      for (int i = 0; i < paramArrayList1.size(); i++)
      {
        String str1 = (String)paramArrayList1.get(i);
        String str2 = (String)paramArrayList2.get(i);
        setNameOverride(paramTransitionState.nameOverrides, str1, str2);
      }
    }
  }
  
  public final FragmentTransaction add(int paramInt, Fragment paramFragment)
  {
    doAddOp(paramInt, paramFragment, null, 1);
    return this;
  }
  
  public final FragmentTransaction add(int paramInt, Fragment paramFragment, String paramString)
  {
    doAddOp(paramInt, paramFragment, paramString, 1);
    return this;
  }
  
  public final FragmentTransaction add(Fragment paramFragment, String paramString)
  {
    doAddOp(0, paramFragment, paramString, 1);
    return this;
  }
  
  final void addOp(Op paramOp)
  {
    if (this.mHead == null)
    {
      this.mTail = paramOp;
      this.mHead = paramOp;
    }
    for (;;)
    {
      paramOp.enterAnim = this.mEnterAnim;
      paramOp.exitAnim = this.mExitAnim;
      paramOp.popEnterAnim = this.mPopEnterAnim;
      paramOp.popExitAnim = this.mPopExitAnim;
      this.mNumOp = (1 + this.mNumOp);
      return;
      paramOp.prev = this.mTail;
      this.mTail.next = paramOp;
      this.mTail = paramOp;
    }
  }
  
  public final FragmentTransaction addSharedElement(View paramView, String paramString)
  {
    if (SUPPORTS_TRANSITIONS)
    {
      String str = paramView.getTransitionName();
      if (str == null) {
        throw new IllegalArgumentException("Unique transitionNames are required for all sharedElements");
      }
      if (this.mSharedElementSourceNames == null)
      {
        this.mSharedElementSourceNames = new ArrayList();
        this.mSharedElementTargetNames = new ArrayList();
      }
      this.mSharedElementSourceNames.add(str);
      this.mSharedElementTargetNames.add(paramString);
    }
    return this;
  }
  
  public final FragmentTransaction addToBackStack(String paramString)
  {
    if (!this.mAllowAddToBackStack) {
      throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
    }
    this.mAddToBackStack = true;
    this.mName = paramString;
    return this;
  }
  
  public final FragmentTransaction attach(Fragment paramFragment)
  {
    Op localOp = new Op();
    localOp.cmd = 7;
    localOp.fragment = paramFragment;
    addOp(localOp);
    return this;
  }
  
  final void bumpBackStackNesting(int paramInt)
  {
    if (!this.mAddToBackStack) {}
    for (;;)
    {
      return;
      if (FragmentManagerImpl.DEBUG) {
        Log.v("FragmentManager", "Bump nesting in " + this + " by " + paramInt);
      }
      for (Op localOp = this.mHead; localOp != null; localOp = localOp.next)
      {
        if (localOp.fragment != null)
        {
          Fragment localFragment2 = localOp.fragment;
          localFragment2.mBackStackNesting = (paramInt + localFragment2.mBackStackNesting);
          if (FragmentManagerImpl.DEBUG) {
            Log.v("FragmentManager", "Bump nesting of " + localOp.fragment + " to " + localOp.fragment.mBackStackNesting);
          }
        }
        if (localOp.removed != null) {
          for (int i = -1 + localOp.removed.size(); i >= 0; i--)
          {
            Fragment localFragment1 = (Fragment)localOp.removed.get(i);
            localFragment1.mBackStackNesting = (paramInt + localFragment1.mBackStackNesting);
            if (FragmentManagerImpl.DEBUG) {
              Log.v("FragmentManager", "Bump nesting of " + localFragment1 + " to " + localFragment1.mBackStackNesting);
            }
          }
        }
      }
    }
  }
  
  public final void calculateBackFragments(SparseArray<Fragment> paramSparseArray1, SparseArray<Fragment> paramSparseArray2)
  {
    if (!this.mManager.mContainer.onHasView()) {}
    Op localOp;
    do
    {
      return;
      localOp = this.mHead;
    } while (localOp == null);
    switch (localOp.cmd)
    {
    }
    for (;;)
    {
      localOp = localOp.next;
      break;
      setFirstOut(paramSparseArray1, localOp.fragment);
      continue;
      if (localOp.removed != null) {
        for (int i = -1 + localOp.removed.size(); i >= 0; i--) {
          setLastIn(paramSparseArray2, (Fragment)localOp.removed.get(i));
        }
      }
      setFirstOut(paramSparseArray1, localOp.fragment);
      continue;
      setLastIn(paramSparseArray2, localOp.fragment);
      continue;
      setLastIn(paramSparseArray2, localOp.fragment);
      continue;
      setFirstOut(paramSparseArray1, localOp.fragment);
      continue;
      setLastIn(paramSparseArray2, localOp.fragment);
      continue;
      setFirstOut(paramSparseArray1, localOp.fragment);
    }
  }
  
  public final int commit()
  {
    return commitInternal(false);
  }
  
  public final int commitAllowingStateLoss()
  {
    return commitInternal(true);
  }
  
  public final FragmentTransaction detach(Fragment paramFragment)
  {
    Op localOp = new Op();
    localOp.cmd = 6;
    localOp.fragment = paramFragment;
    addOp(localOp);
    return this;
  }
  
  public final void dump(String paramString, PrintWriter paramPrintWriter, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mName=");
      paramPrintWriter.print(this.mName);
      paramPrintWriter.print(" mIndex=");
      paramPrintWriter.print(this.mIndex);
      paramPrintWriter.print(" mCommitted=");
      paramPrintWriter.println(this.mCommitted);
      if (this.mTransition != 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mTransition=#");
        paramPrintWriter.print(Integer.toHexString(this.mTransition));
        paramPrintWriter.print(" mTransitionStyle=#");
        paramPrintWriter.println(Integer.toHexString(this.mTransitionStyle));
      }
      if ((this.mEnterAnim != 0) || (this.mExitAnim != 0))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mEnterAnim=#");
        paramPrintWriter.print(Integer.toHexString(this.mEnterAnim));
        paramPrintWriter.print(" mExitAnim=#");
        paramPrintWriter.println(Integer.toHexString(this.mExitAnim));
      }
      if ((this.mPopEnterAnim != 0) || (this.mPopExitAnim != 0))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mPopEnterAnim=#");
        paramPrintWriter.print(Integer.toHexString(this.mPopEnterAnim));
        paramPrintWriter.print(" mPopExitAnim=#");
        paramPrintWriter.println(Integer.toHexString(this.mPopExitAnim));
      }
      if ((this.mBreadCrumbTitleRes != 0) || (this.mBreadCrumbTitleText != null))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mBreadCrumbTitleRes=#");
        paramPrintWriter.print(Integer.toHexString(this.mBreadCrumbTitleRes));
        paramPrintWriter.print(" mBreadCrumbTitleText=");
        paramPrintWriter.println(this.mBreadCrumbTitleText);
      }
      if ((this.mBreadCrumbShortTitleRes != 0) || (this.mBreadCrumbShortTitleText != null))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mBreadCrumbShortTitleRes=#");
        paramPrintWriter.print(Integer.toHexString(this.mBreadCrumbShortTitleRes));
        paramPrintWriter.print(" mBreadCrumbShortTitleText=");
        paramPrintWriter.println(this.mBreadCrumbShortTitleText);
      }
    }
    if (this.mHead != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Operations:");
      String str1 = paramString + "    ";
      Op localOp = this.mHead;
      for (int i = 0; localOp != null; i++)
      {
        String str2;
        int j;
        switch (localOp.cmd)
        {
        default: 
          str2 = "cmd=" + localOp.cmd;
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("  Op #");
          paramPrintWriter.print(i);
          paramPrintWriter.print(": ");
          paramPrintWriter.print(str2);
          paramPrintWriter.print(" ");
          paramPrintWriter.println(localOp.fragment);
          if (paramBoolean)
          {
            if ((localOp.enterAnim != 0) || (localOp.exitAnim != 0))
            {
              paramPrintWriter.print(paramString);
              paramPrintWriter.print("enterAnim=#");
              paramPrintWriter.print(Integer.toHexString(localOp.enterAnim));
              paramPrintWriter.print(" exitAnim=#");
              paramPrintWriter.println(Integer.toHexString(localOp.exitAnim));
            }
            if ((localOp.popEnterAnim != 0) || (localOp.popExitAnim != 0))
            {
              paramPrintWriter.print(paramString);
              paramPrintWriter.print("popEnterAnim=#");
              paramPrintWriter.print(Integer.toHexString(localOp.popEnterAnim));
              paramPrintWriter.print(" popExitAnim=#");
              paramPrintWriter.println(Integer.toHexString(localOp.popExitAnim));
            }
          }
          if ((localOp.removed == null) || (localOp.removed.size() <= 0)) {
            break label801;
          }
          j = 0;
          label638:
          if (j >= localOp.removed.size()) {
            break label801;
          }
          paramPrintWriter.print(str1);
          if (localOp.removed.size() == 1) {
            paramPrintWriter.print("Removed: ");
          }
          break;
        }
        for (;;)
        {
          paramPrintWriter.println(localOp.removed.get(j));
          j++;
          break label638;
          str2 = "NULL";
          break;
          str2 = "ADD";
          break;
          str2 = "REPLACE";
          break;
          str2 = "REMOVE";
          break;
          str2 = "HIDE";
          break;
          str2 = "SHOW";
          break;
          str2 = "DETACH";
          break;
          str2 = "ATTACH";
          break;
          if (j == 0) {
            paramPrintWriter.println("Removed:");
          }
          paramPrintWriter.print(str1);
          paramPrintWriter.print("  #");
          paramPrintWriter.print(j);
          paramPrintWriter.print(": ");
        }
        label801:
        localOp = localOp.next;
      }
    }
  }
  
  public final void dump$ec96877(String paramString, PrintWriter paramPrintWriter)
  {
    dump(paramString, paramPrintWriter, true);
  }
  
  public final String getName()
  {
    return this.mName;
  }
  
  public final FragmentTransaction hide(Fragment paramFragment)
  {
    Op localOp = new Op();
    localOp.cmd = 4;
    localOp.fragment = paramFragment;
    addOp(localOp);
    return this;
  }
  
  public final TransitionState popFromBackStack(boolean paramBoolean, TransitionState paramTransitionState, SparseArray<Fragment> paramSparseArray1, SparseArray<Fragment> paramSparseArray2)
  {
    if (FragmentManagerImpl.DEBUG)
    {
      Log.v("FragmentManager", "popFromBackStack: " + this);
      dump$ec96877("  ", new PrintWriter(new LogWriter("FragmentManager")));
    }
    if (SUPPORTS_TRANSITIONS)
    {
      if (paramTransitionState != null) {
        break label209;
      }
      if ((paramSparseArray1.size() != 0) || (paramSparseArray2.size() != 0)) {
        paramTransitionState = beginTransition(paramSparseArray1, paramSparseArray2, true);
      }
    }
    label88:
    bumpBackStackNesting(-1);
    int i;
    label100:
    int j;
    label107:
    Op localOp;
    int m;
    if (paramTransitionState != null)
    {
      i = 0;
      if (paramTransitionState == null) {
        break label245;
      }
      j = 0;
      localOp = this.mTail;
      if (localOp == null) {
        break label570;
      }
      if (paramTransitionState == null) {
        break label254;
      }
      m = 0;
      label125:
      if (paramTransitionState == null) {
        break label264;
      }
    }
    label264:
    for (int n = 0;; n = localOp.popExitAnim) {
      switch (localOp.cmd)
      {
      default: 
        throw new IllegalArgumentException("Unknown cmd: " + localOp.cmd);
        label209:
        if (paramBoolean) {
          break label88;
        }
        ArrayList localArrayList1 = this.mSharedElementTargetNames;
        ArrayList localArrayList2 = this.mSharedElementSourceNames;
        setNameOverrides(paramTransitionState, localArrayList1, localArrayList2);
        break label88;
        i = this.mTransitionStyle;
        break label100;
        label245:
        j = this.mTransition;
        break label107;
        label254:
        m = localOp.popEnterAnim;
        break label125;
      }
    }
    Fragment localFragment8 = localOp.fragment;
    localFragment8.mNextAnim = n;
    this.mManager.removeFragment(localFragment8, FragmentManagerImpl.reverseTransit(j), i);
    for (;;)
    {
      localOp = localOp.prev;
      break;
      Fragment localFragment6 = localOp.fragment;
      if (localFragment6 != null)
      {
        localFragment6.mNextAnim = n;
        this.mManager.removeFragment(localFragment6, FragmentManagerImpl.reverseTransit(j), i);
      }
      if (localOp.removed != null)
      {
        for (int i1 = 0; i1 < localOp.removed.size(); i1++)
        {
          Fragment localFragment7 = (Fragment)localOp.removed.get(i1);
          localFragment7.mNextAnim = m;
          this.mManager.addFragment(localFragment7, false);
        }
        Fragment localFragment5 = localOp.fragment;
        localFragment5.mNextAnim = m;
        this.mManager.addFragment(localFragment5, false);
        continue;
        Fragment localFragment4 = localOp.fragment;
        localFragment4.mNextAnim = m;
        this.mManager.showFragment(localFragment4, FragmentManagerImpl.reverseTransit(j), i);
        continue;
        Fragment localFragment3 = localOp.fragment;
        localFragment3.mNextAnim = n;
        this.mManager.hideFragment(localFragment3, FragmentManagerImpl.reverseTransit(j), i);
        continue;
        Fragment localFragment2 = localOp.fragment;
        localFragment2.mNextAnim = m;
        this.mManager.attachFragment(localFragment2, FragmentManagerImpl.reverseTransit(j), i);
        continue;
        Fragment localFragment1 = localOp.fragment;
        localFragment1.mNextAnim = m;
        this.mManager.detachFragment(localFragment1, FragmentManagerImpl.reverseTransit(j), i);
      }
    }
    label570:
    if (paramBoolean)
    {
      this.mManager.moveToState(this.mManager.mCurState, FragmentManagerImpl.reverseTransit(j), i, true);
      paramTransitionState = null;
    }
    FragmentManagerImpl localFragmentManagerImpl;
    int k;
    if (this.mIndex >= 0)
    {
      localFragmentManagerImpl = this.mManager;
      k = this.mIndex;
    }
    try
    {
      localFragmentManagerImpl.mBackStackIndices.set(k, null);
      if (localFragmentManagerImpl.mAvailBackStackIndices == null) {
        localFragmentManagerImpl.mAvailBackStackIndices = new ArrayList();
      }
      if (FragmentManagerImpl.DEBUG) {
        Log.v("FragmentManager", "Freeing back stack index " + k);
      }
      localFragmentManagerImpl.mAvailBackStackIndices.add(Integer.valueOf(k));
      this.mIndex = -1;
      return paramTransitionState;
    }
    finally {}
  }
  
  public final FragmentTransaction remove(Fragment paramFragment)
  {
    Op localOp = new Op();
    localOp.cmd = 3;
    localOp.fragment = paramFragment;
    addOp(localOp);
    return this;
  }
  
  public final FragmentTransaction replace(int paramInt, Fragment paramFragment)
  {
    return replace(paramInt, paramFragment, null);
  }
  
  public final FragmentTransaction replace(int paramInt, Fragment paramFragment, String paramString)
  {
    if (paramInt == 0) {
      throw new IllegalArgumentException("Must use non-zero containerViewId");
    }
    doAddOp(paramInt, paramFragment, paramString, 2);
    return this;
  }
  
  public final void run()
  {
    if (FragmentManagerImpl.DEBUG) {
      Log.v("FragmentManager", "Run: " + this);
    }
    if ((this.mAddToBackStack) && (this.mIndex < 0)) {
      throw new IllegalStateException("addToBackStack() called after commit()");
    }
    bumpBackStackNesting(1);
    boolean bool = SUPPORTS_TRANSITIONS;
    TransitionState localTransitionState = null;
    if (bool)
    {
      SparseArray localSparseArray1 = new SparseArray();
      SparseArray localSparseArray2 = new SparseArray();
      calculateFragments(localSparseArray1, localSparseArray2);
      localTransitionState = beginTransition(localSparseArray1, localSparseArray2, false);
    }
    int i;
    label110:
    int j;
    label117:
    Op localOp;
    int k;
    if (localTransitionState != null)
    {
      i = 0;
      if (localTransitionState == null) {
        break label226;
      }
      j = 0;
      localOp = this.mHead;
      if (localOp == null) {
        break label715;
      }
      if (localTransitionState == null) {
        break label235;
      }
      k = 0;
      label135:
      if (localTransitionState == null) {
        break label245;
      }
    }
    label226:
    label235:
    label245:
    for (int m = 0;; m = localOp.exitAnim) {
      switch (localOp.cmd)
      {
      default: 
        throw new IllegalArgumentException("Unknown cmd: " + localOp.cmd);
        i = this.mTransitionStyle;
        break label110;
        j = this.mTransition;
        break label117;
        k = localOp.enterAnim;
        break label135;
      }
    }
    Fragment localFragment8 = localOp.fragment;
    localFragment8.mNextAnim = k;
    this.mManager.addFragment(localFragment8, false);
    for (;;)
    {
      localOp = localOp.next;
      break;
      Fragment localFragment6 = localOp.fragment;
      int n = localFragment6.mContainerId;
      if (this.mManager.mAdded != null)
      {
        int i1 = 0;
        if (i1 < this.mManager.mAdded.size())
        {
          Fragment localFragment7 = (Fragment)this.mManager.mAdded.get(i1);
          if (FragmentManagerImpl.DEBUG) {
            Log.v("FragmentManager", "OP_REPLACE: adding=" + localFragment6 + " old=" + localFragment7);
          }
          if (localFragment7.mContainerId == n)
          {
            if (localFragment7 != localFragment6) {
              break label422;
            }
            localFragment6 = null;
            localOp.fragment = null;
          }
          for (;;)
          {
            i1++;
            break;
            label422:
            if (localOp.removed == null) {
              localOp.removed = new ArrayList();
            }
            localOp.removed.add(localFragment7);
            localFragment7.mNextAnim = m;
            if (this.mAddToBackStack)
            {
              localFragment7.mBackStackNesting = (1 + localFragment7.mBackStackNesting);
              if (FragmentManagerImpl.DEBUG) {
                Log.v("FragmentManager", "Bump nesting of " + localFragment7 + " to " + localFragment7.mBackStackNesting);
              }
            }
            this.mManager.removeFragment(localFragment7, j, i);
          }
        }
      }
      if (localFragment6 != null)
      {
        localFragment6.mNextAnim = k;
        this.mManager.addFragment(localFragment6, false);
        continue;
        Fragment localFragment5 = localOp.fragment;
        localFragment5.mNextAnim = m;
        this.mManager.removeFragment(localFragment5, j, i);
        continue;
        Fragment localFragment4 = localOp.fragment;
        localFragment4.mNextAnim = m;
        this.mManager.hideFragment(localFragment4, j, i);
        continue;
        Fragment localFragment3 = localOp.fragment;
        localFragment3.mNextAnim = k;
        this.mManager.showFragment(localFragment3, j, i);
        continue;
        Fragment localFragment2 = localOp.fragment;
        localFragment2.mNextAnim = m;
        this.mManager.detachFragment(localFragment2, j, i);
        continue;
        Fragment localFragment1 = localOp.fragment;
        localFragment1.mNextAnim = k;
        this.mManager.attachFragment(localFragment1, j, i);
      }
    }
    label715:
    this.mManager.moveToState(this.mManager.mCurState, j, i, true);
    if (this.mAddToBackStack)
    {
      FragmentManagerImpl localFragmentManagerImpl = this.mManager;
      if (localFragmentManagerImpl.mBackStack == null) {
        localFragmentManagerImpl.mBackStack = new ArrayList();
      }
      localFragmentManagerImpl.mBackStack.add(this);
      localFragmentManagerImpl.reportBackStackChanged();
    }
  }
  
  public final FragmentTransaction setCustomAnimations(int paramInt1, int paramInt2)
  {
    this.mEnterAnim = paramInt1;
    this.mExitAnim = paramInt2;
    this.mPopEnterAnim = 0;
    this.mPopExitAnim = 0;
    return this;
  }
  
  public final FragmentTransaction setTransition$9d93138()
  {
    this.mTransition = 4099;
    return this;
  }
  
  public final FragmentTransaction show(Fragment paramFragment)
  {
    Op localOp = new Op();
    localOp.cmd = 5;
    localOp.fragment = paramFragment;
    addOp(localOp);
    return this;
  }
  
  public final String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append("BackStackEntry{");
    localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    if (this.mIndex >= 0)
    {
      localStringBuilder.append(" #");
      localStringBuilder.append(this.mIndex);
    }
    if (this.mName != null)
    {
      localStringBuilder.append(" ");
      localStringBuilder.append(this.mName);
    }
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
  
  static final class Op
  {
    int cmd;
    int enterAnim;
    int exitAnim;
    Fragment fragment;
    Op next;
    int popEnterAnim;
    int popExitAnim;
    Op prev;
    ArrayList<Fragment> removed;
  }
  
  public final class TransitionState
  {
    public FragmentTransitionCompat21.EpicenterView enteringEpicenterView = new FragmentTransitionCompat21.EpicenterView();
    public ArrayList<View> hiddenFragmentViews = new ArrayList();
    public ArrayMap<String, String> nameOverrides = new ArrayMap();
    public View nonExistentView;
    
    public TransitionState() {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.app.BackStackRecord
 * JD-Core Version:    0.7.0.1
 */