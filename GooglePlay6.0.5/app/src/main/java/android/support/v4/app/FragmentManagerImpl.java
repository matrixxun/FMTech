package android.support.v4.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.LogWriter;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class FragmentManagerImpl
  extends FragmentManager
  implements LayoutInflaterFactory
{
  static final Interpolator ACCELERATE_CUBIC = new AccelerateInterpolator(1.5F);
  static final Interpolator ACCELERATE_QUINT;
  static boolean DEBUG = false;
  static final Interpolator DECELERATE_CUBIC;
  static final Interpolator DECELERATE_QUINT;
  static final boolean HONEYCOMB;
  static Field sAnimationListenerField;
  ArrayList<Fragment> mActive;
  ArrayList<Fragment> mAdded;
  ArrayList<Integer> mAvailBackStackIndices;
  ArrayList<Integer> mAvailIndices;
  ArrayList<BackStackRecord> mBackStack;
  ArrayList<FragmentManager.OnBackStackChangedListener> mBackStackChangeListeners;
  ArrayList<BackStackRecord> mBackStackIndices;
  FragmentContainer mContainer;
  ArrayList<Fragment> mCreatedMenus;
  int mCurState = 0;
  boolean mDestroyed;
  Runnable mExecCommit = new Runnable()
  {
    public final void run()
    {
      FragmentManagerImpl.this.execPendingActions();
    }
  };
  boolean mExecutingActions;
  boolean mHavePendingDeferredStart;
  FragmentHostCallback mHost;
  boolean mNeedMenuInvalidate;
  String mNoTransactionsBecause;
  Fragment mParent;
  ArrayList<Runnable> mPendingActions;
  SparseArray<Parcelable> mStateArray = null;
  Bundle mStateBundle = null;
  boolean mStateSaved;
  Runnable[] mTmpActions;
  
  static
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool = false;
    if (i >= 11) {
      bool = true;
    }
    HONEYCOMB = bool;
    sAnimationListenerField = null;
    DECELERATE_QUINT = new DecelerateInterpolator(2.5F);
    DECELERATE_CUBIC = new DecelerateInterpolator(1.5F);
    ACCELERATE_QUINT = new AccelerateInterpolator(2.5F);
  }
  
  private void checkStateLoss()
  {
    if (this.mStateSaved) {
      throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
    }
    if (this.mNoTransactionsBecause != null) {
      throw new IllegalStateException("Can not perform this action inside of " + this.mNoTransactionsBecause);
    }
  }
  
  private Animation loadAnimation(Fragment paramFragment, int paramInt1, boolean paramBoolean, int paramInt2)
  {
    Fragment.onCreateAnimation$24236ca7();
    if (paramFragment.mNextAnim != 0)
    {
      Animation localAnimation = AnimationUtils.loadAnimation(this.mHost.mContext, paramFragment.mNextAnim);
      if (localAnimation != null) {
        return localAnimation;
      }
    }
    if (paramInt1 == 0) {
      return null;
    }
    int j;
    switch (paramInt1)
    {
    default: 
      j = -1;
      if (j < 0) {
        return null;
      }
      break;
    case 4097: 
      if (paramBoolean) {}
      for (int m = 1;; m = 2)
      {
        j = m;
        break;
      }
    case 8194: 
      if (paramBoolean) {}
      for (int k = 3;; k = 4)
      {
        j = k;
        break;
      }
    case 4099: 
      if (paramBoolean) {}
      for (int i = 5;; i = 6)
      {
        j = i;
        break;
      }
    }
    switch (j)
    {
    default: 
      if ((paramInt2 == 0) && (this.mHost.onHasWindowAnimations())) {
        paramInt2 = this.mHost.onGetWindowAnimations();
      }
      if (paramInt2 == 0) {
        return null;
      }
      break;
    case 1: 
      return makeOpenCloseAnimation$376f30fd(1.125F, 1.0F, 0.0F, 1.0F);
    case 2: 
      return makeOpenCloseAnimation$376f30fd(1.0F, 0.975F, 1.0F, 0.0F);
    case 3: 
      return makeOpenCloseAnimation$376f30fd(0.975F, 1.0F, 0.0F, 1.0F);
    case 4: 
      return makeOpenCloseAnimation$376f30fd(1.0F, 1.075F, 1.0F, 0.0F);
    case 5: 
      return makeFadeAnimation$424ea1bd(0.0F, 1.0F);
    case 6: 
      return makeFadeAnimation$424ea1bd(1.0F, 0.0F);
    }
    return null;
  }
  
  private static Animation makeFadeAnimation$424ea1bd(float paramFloat1, float paramFloat2)
  {
    AlphaAnimation localAlphaAnimation = new AlphaAnimation(paramFloat1, paramFloat2);
    localAlphaAnimation.setInterpolator(DECELERATE_CUBIC);
    localAlphaAnimation.setDuration(220L);
    return localAlphaAnimation;
  }
  
  private static Animation makeOpenCloseAnimation$376f30fd(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    AnimationSet localAnimationSet = new AnimationSet(false);
    ScaleAnimation localScaleAnimation = new ScaleAnimation(paramFloat1, paramFloat2, paramFloat1, paramFloat2, 1, 0.5F, 1, 0.5F);
    localScaleAnimation.setInterpolator(DECELERATE_QUINT);
    localScaleAnimation.setDuration(220L);
    localAnimationSet.addAnimation(localScaleAnimation);
    AlphaAnimation localAlphaAnimation = new AlphaAnimation(paramFloat3, paramFloat4);
    localAlphaAnimation.setInterpolator(DECELERATE_CUBIC);
    localAlphaAnimation.setDuration(220L);
    localAnimationSet.addAnimation(localAlphaAnimation);
    return localAnimationSet;
  }
  
  private void moveToState(Fragment paramFragment)
  {
    moveToState(paramFragment, this.mCurState, 0, 0, false);
  }
  
  public static int reverseTransit(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return 0;
    case 4097: 
      return 8194;
    case 8194: 
      return 4097;
    }
    return 4099;
  }
  
  private void saveFragmentViewState(Fragment paramFragment)
  {
    if (paramFragment.mInnerView == null) {
      return;
    }
    if (this.mStateArray == null) {
      this.mStateArray = new SparseArray();
    }
    for (;;)
    {
      paramFragment.mInnerView.saveHierarchyState(this.mStateArray);
      if (this.mStateArray.size() <= 0) {
        break;
      }
      paramFragment.mSavedViewState = this.mStateArray;
      this.mStateArray = null;
      return;
      this.mStateArray.clear();
    }
  }
  
  /* Error */
  private void setBackStackIndex(int paramInt, BackStackRecord paramBackStackRecord)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 236	android/support/v4/app/FragmentManagerImpl:mBackStackIndices	Ljava/util/ArrayList;
    //   6: ifnonnull +14 -> 20
    //   9: aload_0
    //   10: new 238	java/util/ArrayList
    //   13: dup
    //   14: invokespecial 239	java/util/ArrayList:<init>	()V
    //   17: putfield 236	android/support/v4/app/FragmentManagerImpl:mBackStackIndices	Ljava/util/ArrayList;
    //   20: aload_0
    //   21: getfield 236	android/support/v4/app/FragmentManagerImpl:mBackStackIndices	Ljava/util/ArrayList;
    //   24: invokevirtual 240	java/util/ArrayList:size	()I
    //   27: istore 4
    //   29: iload_1
    //   30: iload 4
    //   32: if_icmpge +53 -> 85
    //   35: getstatic 59	android/support/v4/app/FragmentManagerImpl:DEBUG	Z
    //   38: ifeq +34 -> 72
    //   41: ldc 242
    //   43: new 116	java/lang/StringBuilder
    //   46: dup
    //   47: ldc 244
    //   49: invokespecial 119	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   52: iload_1
    //   53: invokevirtual 247	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   56: ldc 249
    //   58: invokevirtual 123	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   61: aload_2
    //   62: invokevirtual 252	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   65: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   68: invokestatic 258	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   71: pop
    //   72: aload_0
    //   73: getfield 236	android/support/v4/app/FragmentManagerImpl:mBackStackIndices	Ljava/util/ArrayList;
    //   76: iload_1
    //   77: aload_2
    //   78: invokevirtual 262	java/util/ArrayList:set	(ILjava/lang/Object;)Ljava/lang/Object;
    //   81: pop
    //   82: aload_0
    //   83: monitorexit
    //   84: return
    //   85: iload 4
    //   87: iload_1
    //   88: if_icmpge +79 -> 167
    //   91: aload_0
    //   92: getfield 236	android/support/v4/app/FragmentManagerImpl:mBackStackIndices	Ljava/util/ArrayList;
    //   95: aconst_null
    //   96: invokevirtual 266	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   99: pop
    //   100: aload_0
    //   101: getfield 268	android/support/v4/app/FragmentManagerImpl:mAvailBackStackIndices	Ljava/util/ArrayList;
    //   104: ifnonnull +14 -> 118
    //   107: aload_0
    //   108: new 238	java/util/ArrayList
    //   111: dup
    //   112: invokespecial 239	java/util/ArrayList:<init>	()V
    //   115: putfield 268	android/support/v4/app/FragmentManagerImpl:mAvailBackStackIndices	Ljava/util/ArrayList;
    //   118: getstatic 59	android/support/v4/app/FragmentManagerImpl:DEBUG	Z
    //   121: ifeq +27 -> 148
    //   124: ldc 242
    //   126: new 116	java/lang/StringBuilder
    //   129: dup
    //   130: ldc_w 270
    //   133: invokespecial 119	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   136: iload 4
    //   138: invokevirtual 247	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   141: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   144: invokestatic 258	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   147: pop
    //   148: aload_0
    //   149: getfield 268	android/support/v4/app/FragmentManagerImpl:mAvailBackStackIndices	Ljava/util/ArrayList;
    //   152: iload 4
    //   154: invokestatic 276	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   157: invokevirtual 266	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   160: pop
    //   161: iinc 4 1
    //   164: goto -79 -> 85
    //   167: getstatic 59	android/support/v4/app/FragmentManagerImpl:DEBUG	Z
    //   170: ifeq +36 -> 206
    //   173: ldc 242
    //   175: new 116	java/lang/StringBuilder
    //   178: dup
    //   179: ldc_w 278
    //   182: invokespecial 119	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   185: iload_1
    //   186: invokevirtual 247	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   189: ldc_w 280
    //   192: invokevirtual 123	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   195: aload_2
    //   196: invokevirtual 252	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   199: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   202: invokestatic 258	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   205: pop
    //   206: aload_0
    //   207: getfield 236	android/support/v4/app/FragmentManagerImpl:mBackStackIndices	Ljava/util/ArrayList;
    //   210: aload_2
    //   211: invokevirtual 266	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   214: pop
    //   215: goto -133 -> 82
    //   218: astore_3
    //   219: aload_0
    //   220: monitorexit
    //   221: aload_3
    //   222: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	223	0	this	FragmentManagerImpl
    //   0	223	1	paramInt	int
    //   0	223	2	paramBackStackRecord	BackStackRecord
    //   218	4	3	localObject	Object
    //   27	135	4	i	int
    // Exception table:
    //   from	to	target	type
    //   2	20	218	finally
    //   20	29	218	finally
    //   35	72	218	finally
    //   72	82	218	finally
    //   82	84	218	finally
    //   91	118	218	finally
    //   118	148	218	finally
    //   148	161	218	finally
    //   167	206	218	finally
    //   206	215	218	finally
    //   219	221	218	finally
  }
  
  private static void setHWLayerAnimListenerIfAlpha(View paramView, Animation paramAnimation)
  {
    if ((paramView == null) || (paramAnimation == null)) {}
    while (!shouldRunOnHWLayer(paramView, paramAnimation)) {
      return;
    }
    try
    {
      if (sAnimationListenerField == null)
      {
        Field localField = Animation.class.getDeclaredField("mListener");
        sAnimationListenerField = localField;
        localField.setAccessible(true);
      }
      localAnimationListener = (Animation.AnimationListener)sAnimationListenerField.get(paramAnimation);
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      for (;;)
      {
        Log.e("FragmentManager", "No field with the name mListener is found in Animation class", localNoSuchFieldException);
        localAnimationListener = null;
      }
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      for (;;)
      {
        Log.e("FragmentManager", "Cannot access Animation's mListener field", localIllegalAccessException);
        Animation.AnimationListener localAnimationListener = null;
      }
    }
    paramAnimation.setAnimationListener(new AnimateOnHWLayerIfNeededListener(paramView, paramAnimation, localAnimationListener));
  }
  
  static boolean shouldRunOnHWLayer(View paramView, Animation paramAnimation)
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool1 = false;
    int k;
    if (i >= 19)
    {
      int j = ViewCompat.getLayerType(paramView);
      bool1 = false;
      if (j == 0)
      {
        boolean bool2 = ViewCompat.hasOverlappingRendering(paramView);
        bool1 = false;
        if (bool2)
        {
          if (!(paramAnimation instanceof AlphaAnimation)) {
            break label59;
          }
          k = 1;
        }
      }
    }
    for (;;)
    {
      bool1 = false;
      if (k != 0) {
        bool1 = true;
      }
      return bool1;
      label59:
      if ((paramAnimation instanceof AnimationSet))
      {
        List localList = ((AnimationSet)paramAnimation).getAnimations();
        for (int m = 0;; m++)
        {
          if (m >= localList.size()) {
            break label117;
          }
          if ((localList.get(m) instanceof AlphaAnimation))
          {
            k = 1;
            break;
          }
        }
      }
      label117:
      k = 0;
    }
  }
  
  private void throwException(RuntimeException paramRuntimeException)
  {
    Log.e("FragmentManager", paramRuntimeException.getMessage());
    Log.e("FragmentManager", "Activity state:");
    PrintWriter localPrintWriter = new PrintWriter(new LogWriter("FragmentManager"));
    if (this.mHost != null) {}
    for (;;)
    {
      try
      {
        this.mHost.onDump$ec96877("  ", localPrintWriter, new String[0]);
        throw paramRuntimeException;
      }
      catch (Exception localException2)
      {
        Log.e("FragmentManager", "Failed dumping state", localException2);
        continue;
      }
      try
      {
        dump("  ", null, localPrintWriter, new String[0]);
      }
      catch (Exception localException1)
      {
        Log.e("FragmentManager", "Failed dumping state", localException1);
      }
    }
  }
  
  public final void addFragment(Fragment paramFragment, boolean paramBoolean)
  {
    if (this.mAdded == null) {
      this.mAdded = new ArrayList();
    }
    if (DEBUG) {
      Log.v("FragmentManager", "add: " + paramFragment);
    }
    if (paramFragment.mIndex < 0)
    {
      if ((this.mAvailIndices != null) && (this.mAvailIndices.size() > 0)) {
        break label185;
      }
      if (this.mActive == null) {
        this.mActive = new ArrayList();
      }
      paramFragment.setIndex(this.mActive.size(), this.mParent);
      this.mActive.add(paramFragment);
    }
    for (;;)
    {
      if (DEBUG) {
        Log.v("FragmentManager", "Allocated fragment index " + paramFragment);
      }
      if (paramFragment.mDetached) {
        return;
      }
      if (!this.mAdded.contains(paramFragment)) {
        break;
      }
      throw new IllegalStateException("Fragment already added: " + paramFragment);
      label185:
      paramFragment.setIndex(((Integer)this.mAvailIndices.remove(-1 + this.mAvailIndices.size())).intValue(), this.mParent);
      this.mActive.set(paramFragment.mIndex, paramFragment);
    }
    this.mAdded.add(paramFragment);
    paramFragment.mAdded = true;
    paramFragment.mRemoving = false;
    if ((paramFragment.mHasMenu) && (paramFragment.mMenuVisible)) {
      this.mNeedMenuInvalidate = true;
    }
    if (paramBoolean) {
      moveToState(paramFragment);
    }
  }
  
  public final void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener paramOnBackStackChangedListener)
  {
    if (this.mBackStackChangeListeners == null) {
      this.mBackStackChangeListeners = new ArrayList();
    }
    this.mBackStackChangeListeners.add(paramOnBackStackChangedListener);
  }
  
  public final int allocBackStackIndex(BackStackRecord paramBackStackRecord)
  {
    try
    {
      if ((this.mAvailBackStackIndices == null) || (this.mAvailBackStackIndices.size() <= 0))
      {
        if (this.mBackStackIndices == null) {
          this.mBackStackIndices = new ArrayList();
        }
        int i = this.mBackStackIndices.size();
        if (DEBUG) {
          Log.v("FragmentManager", "Setting back stack index " + i + " to " + paramBackStackRecord);
        }
        this.mBackStackIndices.add(paramBackStackRecord);
        return i;
      }
      int j = ((Integer)this.mAvailBackStackIndices.remove(-1 + this.mAvailBackStackIndices.size())).intValue();
      if (DEBUG) {
        Log.v("FragmentManager", "Adding back stack index " + j + " with " + paramBackStackRecord);
      }
      this.mBackStackIndices.set(j, paramBackStackRecord);
      return j;
    }
    finally {}
  }
  
  public final void attachController(FragmentHostCallback paramFragmentHostCallback, FragmentContainer paramFragmentContainer, Fragment paramFragment)
  {
    if (this.mHost != null) {
      throw new IllegalStateException("Already attached");
    }
    this.mHost = paramFragmentHostCallback;
    this.mContainer = paramFragmentContainer;
    this.mParent = paramFragment;
  }
  
  public final void attachFragment(Fragment paramFragment, int paramInt1, int paramInt2)
  {
    if (DEBUG) {
      Log.v("FragmentManager", "attach: " + paramFragment);
    }
    if (paramFragment.mDetached)
    {
      paramFragment.mDetached = false;
      if (!paramFragment.mAdded)
      {
        if (this.mAdded == null) {
          this.mAdded = new ArrayList();
        }
        if (this.mAdded.contains(paramFragment)) {
          throw new IllegalStateException("Fragment already added: " + paramFragment);
        }
        if (DEBUG) {
          Log.v("FragmentManager", "add from attach: " + paramFragment);
        }
        this.mAdded.add(paramFragment);
        paramFragment.mAdded = true;
        if ((paramFragment.mHasMenu) && (paramFragment.mMenuVisible)) {
          this.mNeedMenuInvalidate = true;
        }
        moveToState(paramFragment, this.mCurState, paramInt1, paramInt2, false);
      }
    }
  }
  
  public final FragmentTransaction beginTransaction()
  {
    return new BackStackRecord(this);
  }
  
  public final void detachFragment(Fragment paramFragment, int paramInt1, int paramInt2)
  {
    if (DEBUG) {
      Log.v("FragmentManager", "detach: " + paramFragment);
    }
    if (!paramFragment.mDetached)
    {
      paramFragment.mDetached = true;
      if (paramFragment.mAdded)
      {
        if (this.mAdded != null)
        {
          if (DEBUG) {
            Log.v("FragmentManager", "remove from detach: " + paramFragment);
          }
          this.mAdded.remove(paramFragment);
        }
        if ((paramFragment.mHasMenu) && (paramFragment.mMenuVisible)) {
          this.mNeedMenuInvalidate = true;
        }
        paramFragment.mAdded = false;
        moveToState(paramFragment, 1, paramInt1, paramInt2, false);
      }
    }
  }
  
  public final void dispatchActivityCreated()
  {
    this.mStateSaved = false;
    moveToState$2563266(2);
  }
  
  public final void dispatchConfigurationChanged(Configuration paramConfiguration)
  {
    if (this.mAdded != null) {
      for (int i = 0; i < this.mAdded.size(); i++)
      {
        Fragment localFragment = (Fragment)this.mAdded.get(i);
        if (localFragment != null)
        {
          localFragment.onConfigurationChanged(paramConfiguration);
          if (localFragment.mChildFragmentManager != null) {
            localFragment.mChildFragmentManager.dispatchConfigurationChanged(paramConfiguration);
          }
        }
      }
    }
  }
  
  public final boolean dispatchContextItemSelected(MenuItem paramMenuItem)
  {
    if (this.mAdded != null) {
      for (int i = 0; i < this.mAdded.size(); i++)
      {
        Fragment localFragment = (Fragment)this.mAdded.get(i);
        if (localFragment != null)
        {
          if ((!localFragment.mHidden) && (localFragment.mChildFragmentManager != null) && (localFragment.mChildFragmentManager.dispatchContextItemSelected(paramMenuItem))) {}
          for (int j = 1; j != 0; j = 0) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  public final void dispatchCreate()
  {
    this.mStateSaved = false;
    moveToState$2563266(1);
  }
  
  public final boolean dispatchCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    ArrayList localArrayList1 = this.mAdded;
    ArrayList localArrayList2 = null;
    boolean bool1 = false;
    if (localArrayList1 != null) {
      for (int j = 0; j < this.mAdded.size(); j++)
      {
        Fragment localFragment2 = (Fragment)this.mAdded.get(j);
        if (localFragment2 != null)
        {
          boolean bool2 = localFragment2.mHidden;
          boolean bool3 = false;
          if (!bool2)
          {
            boolean bool4 = localFragment2.mHasMenu;
            bool3 = false;
            if (bool4)
            {
              boolean bool5 = localFragment2.mMenuVisible;
              bool3 = false;
              if (bool5)
              {
                bool3 = true;
                localFragment2.onCreateOptionsMenu(paramMenu, paramMenuInflater);
              }
            }
            if (localFragment2.mChildFragmentManager != null) {
              bool3 |= localFragment2.mChildFragmentManager.dispatchCreateOptionsMenu(paramMenu, paramMenuInflater);
            }
          }
          if (bool3)
          {
            bool1 = true;
            if (localArrayList2 == null) {
              localArrayList2 = new ArrayList();
            }
            localArrayList2.add(localFragment2);
          }
        }
      }
    }
    if (this.mCreatedMenus != null) {
      for (int i = 0; i < this.mCreatedMenus.size(); i++)
      {
        Fragment localFragment1 = (Fragment)this.mCreatedMenus.get(i);
        if ((localArrayList2 == null) || (!localArrayList2.contains(localFragment1))) {
          Fragment.onDestroyOptionsMenu();
        }
      }
    }
    this.mCreatedMenus = localArrayList2;
    return bool1;
  }
  
  public final void dispatchDestroy()
  {
    this.mDestroyed = true;
    execPendingActions();
    moveToState$2563266(0);
    this.mHost = null;
    this.mContainer = null;
    this.mParent = null;
  }
  
  public final void dispatchLowMemory()
  {
    if (this.mAdded != null) {
      for (int i = 0; i < this.mAdded.size(); i++)
      {
        Fragment localFragment = (Fragment)this.mAdded.get(i);
        if (localFragment != null)
        {
          localFragment.onLowMemory();
          if (localFragment.mChildFragmentManager != null) {
            localFragment.mChildFragmentManager.dispatchLowMemory();
          }
        }
      }
    }
  }
  
  public final boolean dispatchOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (this.mAdded != null) {
      for (int i = 0; i < this.mAdded.size(); i++)
      {
        Fragment localFragment = (Fragment)this.mAdded.get(i);
        if (localFragment != null)
        {
          int j;
          if (!localFragment.mHidden) {
            if ((localFragment.mHasMenu) && (localFragment.mMenuVisible) && (localFragment.onOptionsItemSelected(paramMenuItem))) {
              j = 1;
            }
          }
          while (j != 0)
          {
            return true;
            if ((localFragment.mChildFragmentManager != null) && (localFragment.mChildFragmentManager.dispatchOptionsItemSelected(paramMenuItem))) {
              j = 1;
            } else {
              j = 0;
            }
          }
        }
      }
    }
    return false;
  }
  
  public final void dispatchOptionsMenuClosed(Menu paramMenu)
  {
    if (this.mAdded != null) {
      for (int i = 0; i < this.mAdded.size(); i++)
      {
        Fragment localFragment = (Fragment)this.mAdded.get(i);
        if ((localFragment != null) && (!localFragment.mHidden) && (localFragment.mChildFragmentManager != null)) {
          localFragment.mChildFragmentManager.dispatchOptionsMenuClosed(paramMenu);
        }
      }
    }
  }
  
  public final boolean dispatchPrepareOptionsMenu(Menu paramMenu)
  {
    ArrayList localArrayList = this.mAdded;
    boolean bool1 = false;
    if (localArrayList != null) {
      for (int i = 0; i < this.mAdded.size(); i++)
      {
        Fragment localFragment = (Fragment)this.mAdded.get(i);
        if (localFragment != null)
        {
          boolean bool2 = localFragment.mHidden;
          boolean bool3 = false;
          if (!bool2)
          {
            boolean bool4 = localFragment.mHasMenu;
            bool3 = false;
            if (bool4)
            {
              boolean bool5 = localFragment.mMenuVisible;
              bool3 = false;
              if (bool5) {
                bool3 = true;
              }
            }
            if (localFragment.mChildFragmentManager != null) {
              bool3 |= localFragment.mChildFragmentManager.dispatchPrepareOptionsMenu(paramMenu);
            }
          }
          if (bool3) {
            bool1 = true;
          }
        }
      }
    }
    return bool1;
  }
  
  public final void dispatchResume()
  {
    this.mStateSaved = false;
    moveToState$2563266(5);
  }
  
  public final void dispatchStart()
  {
    this.mStateSaved = false;
    moveToState$2563266(4);
  }
  
  public final void dispatchStop()
  {
    this.mStateSaved = true;
    moveToState$2563266(3);
  }
  
  public final void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    String str = paramString + "    ";
    if (this.mActive != null)
    {
      int i6 = this.mActive.size();
      if (i6 > 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("Active Fragments in ");
        paramPrintWriter.print(Integer.toHexString(System.identityHashCode(this)));
        paramPrintWriter.println(":");
        for (int i7 = 0; i7 < i6; i7++)
        {
          Fragment localFragment3 = (Fragment)this.mActive.get(i7);
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("  #");
          paramPrintWriter.print(i7);
          paramPrintWriter.print(": ");
          paramPrintWriter.println(localFragment3);
          if (localFragment3 != null) {
            localFragment3.dump(str, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
          }
        }
      }
    }
    if (this.mAdded != null)
    {
      int i4 = this.mAdded.size();
      if (i4 > 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.println("Added Fragments:");
        for (int i5 = 0; i5 < i4; i5++)
        {
          Fragment localFragment2 = (Fragment)this.mAdded.get(i5);
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("  #");
          paramPrintWriter.print(i5);
          paramPrintWriter.print(": ");
          paramPrintWriter.println(localFragment2.toString());
        }
      }
    }
    if (this.mCreatedMenus != null)
    {
      int i2 = this.mCreatedMenus.size();
      if (i2 > 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.println("Fragments Created Menus:");
        for (int i3 = 0; i3 < i2; i3++)
        {
          Fragment localFragment1 = (Fragment)this.mCreatedMenus.get(i3);
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("  #");
          paramPrintWriter.print(i3);
          paramPrintWriter.print(": ");
          paramPrintWriter.println(localFragment1.toString());
        }
      }
    }
    if (this.mBackStack != null)
    {
      int n = this.mBackStack.size();
      if (n > 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.println("Back Stack:");
        for (int i1 = 0; i1 < n; i1++)
        {
          BackStackRecord localBackStackRecord2 = (BackStackRecord)this.mBackStack.get(i1);
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("  #");
          paramPrintWriter.print(i1);
          paramPrintWriter.print(": ");
          paramPrintWriter.println(localBackStackRecord2.toString());
          localBackStackRecord2.dump$ec96877(str, paramPrintWriter);
        }
      }
    }
    try
    {
      if (this.mBackStackIndices != null)
      {
        int k = this.mBackStackIndices.size();
        if (k > 0)
        {
          paramPrintWriter.print(paramString);
          paramPrintWriter.println("Back Stack Indices:");
          for (int m = 0; m < k; m++)
          {
            BackStackRecord localBackStackRecord1 = (BackStackRecord)this.mBackStackIndices.get(m);
            paramPrintWriter.print(paramString);
            paramPrintWriter.print("  #");
            paramPrintWriter.print(m);
            paramPrintWriter.print(": ");
            paramPrintWriter.println(localBackStackRecord1);
          }
        }
      }
      if ((this.mAvailBackStackIndices != null) && (this.mAvailBackStackIndices.size() > 0))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mAvailBackStackIndices: ");
        paramPrintWriter.println(Arrays.toString(this.mAvailBackStackIndices.toArray()));
      }
      if (this.mPendingActions != null)
      {
        int i = this.mPendingActions.size();
        if (i > 0)
        {
          paramPrintWriter.print(paramString);
          paramPrintWriter.println("Pending Actions:");
          for (int j = 0; j < i; j++)
          {
            Runnable localRunnable = (Runnable)this.mPendingActions.get(j);
            paramPrintWriter.print(paramString);
            paramPrintWriter.print("  #");
            paramPrintWriter.print(j);
            paramPrintWriter.print(": ");
            paramPrintWriter.println(localRunnable);
          }
        }
      }
      paramPrintWriter.print(paramString);
    }
    finally {}
    paramPrintWriter.println("FragmentManager misc state:");
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("  mHost=");
    paramPrintWriter.println(this.mHost);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("  mContainer=");
    paramPrintWriter.println(this.mContainer);
    if (this.mParent != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("  mParent=");
      paramPrintWriter.println(this.mParent);
    }
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("  mCurState=");
    paramPrintWriter.print(this.mCurState);
    paramPrintWriter.print(" mStateSaved=");
    paramPrintWriter.print(this.mStateSaved);
    paramPrintWriter.print(" mDestroyed=");
    paramPrintWriter.println(this.mDestroyed);
    if (this.mNeedMenuInvalidate)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("  mNeedMenuInvalidate=");
      paramPrintWriter.println(this.mNeedMenuInvalidate);
    }
    if (this.mNoTransactionsBecause != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("  mNoTransactionsBecause=");
      paramPrintWriter.println(this.mNoTransactionsBecause);
    }
    if ((this.mAvailIndices != null) && (this.mAvailIndices.size() > 0))
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("  mAvailIndices: ");
      paramPrintWriter.println(Arrays.toString(this.mAvailIndices.toArray()));
    }
  }
  
  /* Error */
  public final void enqueueAction(Runnable paramRunnable, boolean paramBoolean)
  {
    // Byte code:
    //   0: iload_2
    //   1: ifne +7 -> 8
    //   4: aload_0
    //   5: invokespecial 624	android/support/v4/app/FragmentManagerImpl:checkStateLoss	()V
    //   8: aload_0
    //   9: monitorenter
    //   10: aload_0
    //   11: getfield 504	android/support/v4/app/FragmentManagerImpl:mDestroyed	Z
    //   14: ifne +10 -> 24
    //   17: aload_0
    //   18: getfield 140	android/support/v4/app/FragmentManagerImpl:mHost	Landroid/support/v4/app/FragmentHostCallback;
    //   21: ifnonnull +19 -> 40
    //   24: new 107	java/lang/IllegalStateException
    //   27: dup
    //   28: ldc_w 626
    //   31: invokespecial 112	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   34: athrow
    //   35: astore_3
    //   36: aload_0
    //   37: monitorexit
    //   38: aload_3
    //   39: athrow
    //   40: aload_0
    //   41: getfield 592	android/support/v4/app/FragmentManagerImpl:mPendingActions	Ljava/util/ArrayList;
    //   44: ifnonnull +14 -> 58
    //   47: aload_0
    //   48: new 238	java/util/ArrayList
    //   51: dup
    //   52: invokespecial 239	java/util/ArrayList:<init>	()V
    //   55: putfield 592	android/support/v4/app/FragmentManagerImpl:mPendingActions	Ljava/util/ArrayList;
    //   58: aload_0
    //   59: getfield 592	android/support/v4/app/FragmentManagerImpl:mPendingActions	Ljava/util/ArrayList;
    //   62: aload_1
    //   63: invokevirtual 266	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   66: pop
    //   67: aload_0
    //   68: getfield 592	android/support/v4/app/FragmentManagerImpl:mPendingActions	Ljava/util/ArrayList;
    //   71: invokevirtual 240	java/util/ArrayList:size	()I
    //   74: iconst_1
    //   75: if_icmpne +32 -> 107
    //   78: aload_0
    //   79: getfield 140	android/support/v4/app/FragmentManagerImpl:mHost	Landroid/support/v4/app/FragmentHostCallback;
    //   82: getfield 630	android/support/v4/app/FragmentHostCallback:mHandler	Landroid/os/Handler;
    //   85: aload_0
    //   86: getfield 102	android/support/v4/app/FragmentManagerImpl:mExecCommit	Ljava/lang/Runnable;
    //   89: invokevirtual 636	android/os/Handler:removeCallbacks	(Ljava/lang/Runnable;)V
    //   92: aload_0
    //   93: getfield 140	android/support/v4/app/FragmentManagerImpl:mHost	Landroid/support/v4/app/FragmentHostCallback;
    //   96: getfield 630	android/support/v4/app/FragmentHostCallback:mHandler	Landroid/os/Handler;
    //   99: aload_0
    //   100: getfield 102	android/support/v4/app/FragmentManagerImpl:mExecCommit	Ljava/lang/Runnable;
    //   103: invokevirtual 640	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   106: pop
    //   107: aload_0
    //   108: monitorexit
    //   109: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	110	0	this	FragmentManagerImpl
    //   0	110	1	paramRunnable	Runnable
    //   0	110	2	paramBoolean	boolean
    //   35	4	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   10	24	35	finally
    //   24	35	35	finally
    //   36	38	35	finally
    //   40	58	35	finally
    //   58	107	35	finally
    //   107	109	35	finally
  }
  
  public final boolean execPendingActions()
  {
    if (this.mExecutingActions) {
      throw new IllegalStateException("Recursive entry to executePendingTransactions");
    }
    if (Looper.myLooper() != this.mHost.mHandler.getLooper()) {
      throw new IllegalStateException("Must be called from main thread of process");
    }
    boolean bool2;
    for (boolean bool1 = false;; bool1 = true) {
      try
      {
        if ((this.mPendingActions == null) || (this.mPendingActions.size() == 0))
        {
          if (!this.mHavePendingDeferredStart) {
            return bool1;
          }
          bool2 = false;
          for (int i = 0; i < this.mActive.size(); i++)
          {
            Fragment localFragment = (Fragment)this.mActive.get(i);
            if ((localFragment != null) && (localFragment.mLoaderManager != null)) {
              bool2 |= localFragment.mLoaderManager.hasRunningLoaders();
            }
          }
        }
        int j = this.mPendingActions.size();
        if ((this.mTmpActions == null) || (this.mTmpActions.length < j)) {
          this.mTmpActions = new Runnable[j];
        }
        this.mPendingActions.toArray(this.mTmpActions);
        this.mPendingActions.clear();
        this.mHost.mHandler.removeCallbacks(this.mExecCommit);
        this.mExecutingActions = true;
        for (int k = 0; k < j; k++)
        {
          this.mTmpActions[k].run();
          this.mTmpActions[k] = null;
        }
        this.mExecutingActions = false;
      }
      finally {}
    }
    if (!bool2)
    {
      this.mHavePendingDeferredStart = false;
      startPendingDeferredFragments();
    }
    return bool1;
  }
  
  public final boolean executePendingTransactions()
  {
    return execPendingActions();
  }
  
  public final Fragment findFragmentById(int paramInt)
  {
    Fragment localFragment;
    if (this.mAdded != null) {
      for (int j = -1 + this.mAdded.size(); j >= 0; j--)
      {
        localFragment = (Fragment)this.mAdded.get(j);
        if ((localFragment != null) && (localFragment.mFragmentId == paramInt)) {
          return localFragment;
        }
      }
    }
    if (this.mActive != null) {
      for (int i = -1 + this.mActive.size();; i--)
      {
        if (i < 0) {
          break label107;
        }
        localFragment = (Fragment)this.mActive.get(i);
        if ((localFragment != null) && (localFragment.mFragmentId == paramInt)) {
          break;
        }
      }
    }
    label107:
    return null;
  }
  
  public final Fragment findFragmentByTag(String paramString)
  {
    Fragment localFragment;
    if ((this.mAdded != null) && (paramString != null)) {
      for (int j = -1 + this.mAdded.size(); j >= 0; j--)
      {
        localFragment = (Fragment)this.mAdded.get(j);
        if ((localFragment != null) && (paramString.equals(localFragment.mTag))) {
          return localFragment;
        }
      }
    }
    if ((this.mActive != null) && (paramString != null)) {
      for (int i = -1 + this.mActive.size();; i--)
      {
        if (i < 0) {
          break label121;
        }
        localFragment = (Fragment)this.mActive.get(i);
        if ((localFragment != null) && (paramString.equals(localFragment.mTag))) {
          break;
        }
      }
    }
    label121:
    return null;
  }
  
  public final FragmentManager.BackStackEntry getBackStackEntryAt$71bc1da8()
  {
    return (FragmentManager.BackStackEntry)this.mBackStack.get(0);
  }
  
  public final int getBackStackEntryCount()
  {
    if (this.mBackStack != null) {
      return this.mBackStack.size();
    }
    return 0;
  }
  
  public final Fragment getFragment(Bundle paramBundle, String paramString)
  {
    int i = paramBundle.getInt(paramString, -1);
    Fragment localFragment;
    if (i == -1) {
      localFragment = null;
    }
    do
    {
      return localFragment;
      if (i >= this.mActive.size()) {
        throwException(new IllegalStateException("Fragment no longer exists for key " + paramString + ": index " + i));
      }
      localFragment = (Fragment)this.mActive.get(i);
    } while (localFragment != null);
    throwException(new IllegalStateException("Fragment no longer exists for key " + paramString + ": index " + i));
    return localFragment;
  }
  
  public final List<Fragment> getFragments()
  {
    return this.mActive;
  }
  
  public final void hideFragment(Fragment paramFragment, int paramInt1, int paramInt2)
  {
    if (DEBUG) {
      Log.v("FragmentManager", "hide: " + paramFragment);
    }
    if (!paramFragment.mHidden)
    {
      paramFragment.mHidden = true;
      if (paramFragment.mView != null)
      {
        Animation localAnimation = loadAnimation(paramFragment, paramInt1, false, paramInt2);
        if (localAnimation != null)
        {
          setHWLayerAnimListenerIfAlpha(paramFragment.mView, localAnimation);
          paramFragment.mView.startAnimation(localAnimation);
        }
        paramFragment.mView.setVisibility(8);
      }
      if ((paramFragment.mAdded) && (paramFragment.mHasMenu) && (paramFragment.mMenuVisible)) {
        this.mNeedMenuInvalidate = true;
      }
      Fragment.onHiddenChanged$1385ff();
    }
  }
  
  public final boolean isDestroyed()
  {
    return this.mDestroyed;
  }
  
  final void moveToState(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    if ((this.mHost == null) && (paramInt1 != 0)) {
      throw new IllegalStateException("No host");
    }
    if ((!paramBoolean) && (this.mCurState == paramInt1)) {}
    do
    {
      do
      {
        return;
        this.mCurState = paramInt1;
      } while (this.mActive == null);
      boolean bool = false;
      for (int i = 0; i < this.mActive.size(); i++)
      {
        Fragment localFragment = (Fragment)this.mActive.get(i);
        if (localFragment != null)
        {
          moveToState(localFragment, paramInt1, paramInt2, paramInt3, false);
          if (localFragment.mLoaderManager != null) {
            bool |= localFragment.mLoaderManager.hasRunningLoaders();
          }
        }
      }
      if (!bool) {
        startPendingDeferredFragments();
      }
    } while ((!this.mNeedMenuInvalidate) || (this.mHost == null) || (this.mCurState != 5));
    this.mHost.onSupportInvalidateOptionsMenu();
    this.mNeedMenuInvalidate = false;
  }
  
  final void moveToState(final Fragment paramFragment, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    if (((!paramFragment.mAdded) || (paramFragment.mDetached)) && (paramInt1 > 1)) {
      paramInt1 = 1;
    }
    if ((paramFragment.mRemoving) && (paramInt1 > paramFragment.mState)) {
      paramInt1 = paramFragment.mState;
    }
    if ((paramFragment.mDeferStart) && (paramFragment.mState < 4) && (paramInt1 > 3)) {
      paramInt1 = 3;
    }
    if (paramFragment.mState < paramInt1)
    {
      if ((paramFragment.mFromLayout) && (!paramFragment.mInLayout)) {
        return;
      }
      if (paramFragment.mAnimatingAway != null)
      {
        paramFragment.mAnimatingAway = null;
        moveToState(paramFragment, paramFragment.mStateAfterAnimating, 0, 0, true);
      }
      switch (paramFragment.mState)
      {
      }
    }
    for (;;)
    {
      paramFragment.mState = paramInt1;
      return;
      if (DEBUG) {
        Log.v("FragmentManager", "moveto CREATED: " + paramFragment);
      }
      if (paramFragment.mSavedFragmentState != null)
      {
        paramFragment.mSavedFragmentState.setClassLoader(this.mHost.mContext.getClassLoader());
        paramFragment.mSavedViewState = paramFragment.mSavedFragmentState.getSparseParcelableArray("android:view_state");
        paramFragment.mTarget = getFragment(paramFragment.mSavedFragmentState, "android:target_state");
        if (paramFragment.mTarget != null) {
          paramFragment.mTargetRequestCode = paramFragment.mSavedFragmentState.getInt("android:target_req_state", 0);
        }
        paramFragment.mUserVisibleHint = paramFragment.mSavedFragmentState.getBoolean("android:user_visible_hint", true);
        if (!paramFragment.mUserVisibleHint)
        {
          paramFragment.mDeferStart = true;
          if (paramInt1 > 3) {
            paramInt1 = 3;
          }
        }
      }
      paramFragment.mHost = this.mHost;
      paramFragment.mParentFragment = this.mParent;
      if (this.mParent != null) {}
      for (FragmentManagerImpl localFragmentManagerImpl = this.mParent.mChildFragmentManager;; localFragmentManagerImpl = this.mHost.mFragmentManager)
      {
        paramFragment.mFragmentManager = localFragmentManagerImpl;
        paramFragment.mCalled = false;
        paramFragment.onAttach(this.mHost.mContext);
        if (paramFragment.mCalled) {
          break;
        }
        throw new SuperNotCalledException("Fragment " + paramFragment + " did not call through to super.onAttach()");
      }
      if (!paramFragment.mRetaining)
      {
        Bundle localBundle3 = paramFragment.mSavedFragmentState;
        if (paramFragment.mChildFragmentManager != null) {
          paramFragment.mChildFragmentManager.mStateSaved = false;
        }
        paramFragment.mCalled = false;
        paramFragment.onCreate(localBundle3);
        if (!paramFragment.mCalled) {
          throw new SuperNotCalledException("Fragment " + paramFragment + " did not call through to super.onCreate()");
        }
        if (localBundle3 != null)
        {
          Parcelable localParcelable = localBundle3.getParcelable("android:support:fragments");
          if (localParcelable != null)
          {
            if (paramFragment.mChildFragmentManager == null) {
              paramFragment.instantiateChildFragmentManager();
            }
            paramFragment.mChildFragmentManager.restoreAllState(localParcelable, null);
            paramFragment.mChildFragmentManager.dispatchCreate();
          }
        }
      }
      paramFragment.mRetaining = false;
      if (paramFragment.mFromLayout)
      {
        paramFragment.mView = paramFragment.performCreateView(paramFragment.getLayoutInflater(paramFragment.mSavedFragmentState), null, paramFragment.mSavedFragmentState);
        if (paramFragment.mView == null) {
          break label982;
        }
        paramFragment.mInnerView = paramFragment.mView;
        if (Build.VERSION.SDK_INT >= 11)
        {
          ViewCompat.setSaveFromParentEnabled$53599cc9(paramFragment.mView);
          if (paramFragment.mHidden) {
            paramFragment.mView.setVisibility(8);
          }
          paramFragment.onViewCreated(paramFragment.mView, paramFragment.mSavedFragmentState);
        }
      }
      else
      {
        label617:
        if (paramInt1 <= 1) {
          break label1116;
        }
        if (DEBUG) {
          Log.v("FragmentManager", "moveto ACTIVITY_CREATED: " + paramFragment);
        }
        if (!paramFragment.mFromLayout)
        {
          int j = paramFragment.mContainerId;
          ViewGroup localViewGroup = null;
          if (j != 0)
          {
            localViewGroup = (ViewGroup)this.mContainer.onFindViewById(paramFragment.mContainerId);
            if ((localViewGroup == null) && (!paramFragment.mRestored)) {
              throwException(new IllegalArgumentException("No view found for id 0x" + Integer.toHexString(paramFragment.mContainerId) + " (" + paramFragment.getResources().getResourceName(paramFragment.mContainerId) + ") for fragment " + paramFragment));
            }
          }
          paramFragment.mContainer = localViewGroup;
          paramFragment.mView = paramFragment.performCreateView(paramFragment.getLayoutInflater(paramFragment.mSavedFragmentState), localViewGroup, paramFragment.mSavedFragmentState);
          if (paramFragment.mView == null) {
            break label1004;
          }
          paramFragment.mInnerView = paramFragment.mView;
          if (Build.VERSION.SDK_INT < 11) {
            break label990;
          }
          ViewCompat.setSaveFromParentEnabled$53599cc9(paramFragment.mView);
          label822:
          if (localViewGroup != null)
          {
            Animation localAnimation2 = loadAnimation(paramFragment, paramInt2, true, paramInt3);
            if (localAnimation2 != null)
            {
              setHWLayerAnimListenerIfAlpha(paramFragment.mView, localAnimation2);
              paramFragment.mView.startAnimation(localAnimation2);
            }
            localViewGroup.addView(paramFragment.mView);
          }
          if (paramFragment.mHidden) {
            paramFragment.mView.setVisibility(8);
          }
          paramFragment.onViewCreated(paramFragment.mView, paramFragment.mSavedFragmentState);
        }
      }
      for (;;)
      {
        Bundle localBundle1 = paramFragment.mSavedFragmentState;
        if (paramFragment.mChildFragmentManager != null) {
          paramFragment.mChildFragmentManager.mStateSaved = false;
        }
        paramFragment.mCalled = false;
        paramFragment.onActivityCreated(localBundle1);
        if (paramFragment.mCalled) {
          break label1012;
        }
        throw new SuperNotCalledException("Fragment " + paramFragment + " did not call through to super.onActivityCreated()");
        paramFragment.mView = NoSaveStateFrameLayout.wrap(paramFragment.mView);
        break;
        label982:
        paramFragment.mInnerView = null;
        break label617;
        label990:
        paramFragment.mView = NoSaveStateFrameLayout.wrap(paramFragment.mView);
        break label822;
        label1004:
        paramFragment.mInnerView = null;
      }
      label1012:
      if (paramFragment.mChildFragmentManager != null) {
        paramFragment.mChildFragmentManager.dispatchActivityCreated();
      }
      if (paramFragment.mView != null)
      {
        Bundle localBundle2 = paramFragment.mSavedFragmentState;
        if (paramFragment.mSavedViewState != null)
        {
          paramFragment.mInnerView.restoreHierarchyState(paramFragment.mSavedViewState);
          paramFragment.mSavedViewState = null;
        }
        paramFragment.mCalled = false;
        paramFragment.onViewStateRestored(localBundle2);
        if (!paramFragment.mCalled) {
          throw new SuperNotCalledException("Fragment " + paramFragment + " did not call through to super.onViewStateRestored()");
        }
      }
      paramFragment.mSavedFragmentState = null;
      label1116:
      if (paramInt1 > 3)
      {
        if (DEBUG) {
          Log.v("FragmentManager", "moveto STARTED: " + paramFragment);
        }
        if (paramFragment.mChildFragmentManager != null)
        {
          paramFragment.mChildFragmentManager.mStateSaved = false;
          paramFragment.mChildFragmentManager.execPendingActions();
        }
        paramFragment.mCalled = false;
        paramFragment.onStart();
        if (!paramFragment.mCalled) {
          throw new SuperNotCalledException("Fragment " + paramFragment + " did not call through to super.onStart()");
        }
        if (paramFragment.mChildFragmentManager != null) {
          paramFragment.mChildFragmentManager.dispatchStart();
        }
        if (paramFragment.mLoaderManager != null) {
          paramFragment.mLoaderManager.doReportStart();
        }
      }
      if (paramInt1 > 4)
      {
        if (DEBUG) {
          Log.v("FragmentManager", "moveto RESUMED: " + paramFragment);
        }
        paramFragment.mResumed = true;
        if (paramFragment.mChildFragmentManager != null)
        {
          paramFragment.mChildFragmentManager.mStateSaved = false;
          paramFragment.mChildFragmentManager.execPendingActions();
        }
        paramFragment.mCalled = false;
        paramFragment.onResume();
        if (!paramFragment.mCalled) {
          throw new SuperNotCalledException("Fragment " + paramFragment + " did not call through to super.onResume()");
        }
        if (paramFragment.mChildFragmentManager != null)
        {
          paramFragment.mChildFragmentManager.dispatchResume();
          paramFragment.mChildFragmentManager.execPendingActions();
        }
        paramFragment.mSavedFragmentState = null;
        paramFragment.mSavedViewState = null;
        continue;
        if (paramFragment.mState > paramInt1) {
          switch (paramFragment.mState)
          {
          default: 
            break;
          case 1: 
          case 5: 
          case 4: 
          case 3: 
          case 2: 
            while (paramInt1 <= 0)
            {
              if ((this.mDestroyed) && (paramFragment.mAnimatingAway != null))
              {
                View localView = paramFragment.mAnimatingAway;
                paramFragment.mAnimatingAway = null;
                localView.clearAnimation();
              }
              if (paramFragment.mAnimatingAway == null) {
                break label1996;
              }
              paramFragment.mStateAfterAnimating = paramInt1;
              paramInt1 = 1;
              break;
              if (paramInt1 < 5)
              {
                if (DEBUG) {
                  Log.v("FragmentManager", "movefrom RESUMED: " + paramFragment);
                }
                if (paramFragment.mChildFragmentManager != null) {
                  paramFragment.mChildFragmentManager.moveToState$2563266(4);
                }
                paramFragment.mCalled = false;
                paramFragment.onPause();
                if (!paramFragment.mCalled) {
                  throw new SuperNotCalledException("Fragment " + paramFragment + " did not call through to super.onPause()");
                }
                paramFragment.mResumed = false;
              }
              if (paramInt1 < 4)
              {
                if (DEBUG) {
                  Log.v("FragmentManager", "movefrom STARTED: " + paramFragment);
                }
                if (paramFragment.mChildFragmentManager != null) {
                  paramFragment.mChildFragmentManager.dispatchStop();
                }
                paramFragment.mCalled = false;
                paramFragment.onStop();
                if (!paramFragment.mCalled) {
                  throw new SuperNotCalledException("Fragment " + paramFragment + " did not call through to super.onStop()");
                }
              }
              if (paramInt1 < 3)
              {
                if (DEBUG) {
                  Log.v("FragmentManager", "movefrom STOPPED: " + paramFragment);
                }
                paramFragment.performReallyStop();
              }
              if (paramInt1 < 2)
              {
                if (DEBUG) {
                  Log.v("FragmentManager", "movefrom ACTIVITY_CREATED: " + paramFragment);
                }
                if ((paramFragment.mView != null) && (this.mHost.onShouldSaveFragmentState$6585081f()) && (paramFragment.mSavedViewState == null)) {
                  saveFragmentViewState(paramFragment);
                }
                if (paramFragment.mChildFragmentManager != null) {
                  paramFragment.mChildFragmentManager.moveToState$2563266(1);
                }
                paramFragment.mCalled = false;
                paramFragment.onDestroyView();
                if (!paramFragment.mCalled) {
                  throw new SuperNotCalledException("Fragment " + paramFragment + " did not call through to super.onDestroyView()");
                }
                if (paramFragment.mLoaderManager != null) {
                  paramFragment.mLoaderManager.doReportNextStart();
                }
                if ((paramFragment.mView != null) && (paramFragment.mContainer != null))
                {
                  int i = this.mCurState;
                  Animation localAnimation1 = null;
                  if (i > 0)
                  {
                    boolean bool = this.mDestroyed;
                    localAnimation1 = null;
                    if (!bool) {
                      localAnimation1 = loadAnimation(paramFragment, paramInt2, false, paramInt3);
                    }
                  }
                  if (localAnimation1 != null)
                  {
                    paramFragment.mAnimatingAway = paramFragment.mView;
                    paramFragment.mStateAfterAnimating = paramInt1;
                    localAnimation1.setAnimationListener(new AnimateOnHWLayerIfNeededListener(paramFragment.mView, localAnimation1)
                    {
                      public final void onAnimationEnd(Animation paramAnonymousAnimation)
                      {
                        super.onAnimationEnd(paramAnonymousAnimation);
                        if (paramFragment.mAnimatingAway != null)
                        {
                          paramFragment.mAnimatingAway = null;
                          FragmentManagerImpl.this.moveToState(paramFragment, paramFragment.mStateAfterAnimating, 0, 0, false);
                        }
                      }
                    });
                    paramFragment.mView.startAnimation(localAnimation1);
                  }
                  paramFragment.mContainer.removeView(paramFragment.mView);
                }
                paramFragment.mContainer = null;
                paramFragment.mView = null;
                paramFragment.mInnerView = null;
              }
            }
            label1996:
            if (DEBUG) {
              Log.v("FragmentManager", "movefrom CREATED: " + paramFragment);
            }
            if (!paramFragment.mRetaining)
            {
              if (paramFragment.mChildFragmentManager != null) {
                paramFragment.mChildFragmentManager.dispatchDestroy();
              }
              paramFragment.mCalled = false;
              paramFragment.onDestroy();
              if (!paramFragment.mCalled) {
                throw new SuperNotCalledException("Fragment " + paramFragment + " did not call through to super.onDestroy()");
              }
            }
            paramFragment.mCalled = false;
            paramFragment.onDetach();
            if (!paramFragment.mCalled) {
              throw new SuperNotCalledException("Fragment " + paramFragment + " did not call through to super.onDetach()");
            }
            if (!paramBoolean) {
              if (!paramFragment.mRetaining)
              {
                if (paramFragment.mIndex >= 0)
                {
                  if (DEBUG) {
                    Log.v("FragmentManager", "Freeing fragment index " + paramFragment);
                  }
                  this.mActive.set(paramFragment.mIndex, null);
                  if (this.mAvailIndices == null) {
                    this.mAvailIndices = new ArrayList();
                  }
                  this.mAvailIndices.add(Integer.valueOf(paramFragment.mIndex));
                  this.mHost.inactivateFragment(paramFragment.mWho);
                  paramFragment.mIndex = -1;
                  paramFragment.mWho = null;
                  paramFragment.mAdded = false;
                  paramFragment.mRemoving = false;
                  paramFragment.mResumed = false;
                  paramFragment.mFromLayout = false;
                  paramFragment.mInLayout = false;
                  paramFragment.mRestored = false;
                  paramFragment.mBackStackNesting = 0;
                  paramFragment.mFragmentManager = null;
                  paramFragment.mChildFragmentManager = null;
                  paramFragment.mHost = null;
                  paramFragment.mFragmentId = 0;
                  paramFragment.mContainerId = 0;
                  paramFragment.mTag = null;
                  paramFragment.mHidden = false;
                  paramFragment.mDetached = false;
                  paramFragment.mRetaining = false;
                  paramFragment.mLoaderManager = null;
                  paramFragment.mLoadersStarted = false;
                  paramFragment.mCheckedForLoaderManager = false;
                }
              }
              else
              {
                paramFragment.mHost = null;
                paramFragment.mParentFragment = null;
                paramFragment.mFragmentManager = null;
                paramFragment.mChildFragmentManager = null;
              }
            }
            break;
          }
        }
      }
    }
  }
  
  final void moveToState$2563266(int paramInt)
  {
    moveToState(paramInt, 0, 0, false);
  }
  
  public final View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    if (!"fragment".equals(paramString)) {}
    String str1;
    int i;
    String str2;
    do
    {
      return null;
      str1 = paramAttributeSet.getAttributeValue(null, "class");
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, FragmentTag.Fragment);
      if (str1 == null) {
        str1 = localTypedArray.getString(0);
      }
      i = localTypedArray.getResourceId(1, -1);
      str2 = localTypedArray.getString(2);
      localTypedArray.recycle();
    } while (!Fragment.isSupportFragmentClass(this.mHost.mContext, str1));
    if (paramView != null) {}
    for (int j = paramView.getId(); (j == -1) && (i == -1) && (str2 == null); j = 0) {
      throw new IllegalArgumentException(paramAttributeSet.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + str1);
    }
    Fragment localFragment;
    int k;
    if (i != -1)
    {
      localFragment = findFragmentById(i);
      if ((localFragment == null) && (str2 != null)) {
        localFragment = findFragmentByTag(str2);
      }
      if ((localFragment == null) && (j != -1)) {
        localFragment = findFragmentById(j);
      }
      if (DEBUG) {
        Log.v("FragmentManager", "onCreateView: id=0x" + Integer.toHexString(i) + " fname=" + str1 + " existing=" + localFragment);
      }
      if (localFragment != null) {
        break label430;
      }
      localFragment = Fragment.instantiate(paramContext, str1);
      localFragment.mFromLayout = true;
      if (i == 0) {
        break label423;
      }
      k = i;
      label292:
      localFragment.mFragmentId = k;
      localFragment.mContainerId = j;
      localFragment.mTag = str2;
      localFragment.mInLayout = true;
      localFragment.mFragmentManager = this;
      localFragment.mHost = this.mHost;
      localFragment.onInflate$2c1ed547$6a2adb45();
      addFragment(localFragment, true);
      label352:
      if ((this.mCurState > 0) || (!localFragment.mFromLayout)) {
        break label544;
      }
      moveToState(localFragment, 1, 0, 0, false);
    }
    for (;;)
    {
      if (localFragment.mView != null) {
        break label553;
      }
      throw new IllegalStateException("Fragment " + str1 + " did not create a view.");
      localFragment = null;
      break;
      label423:
      k = j;
      break label292;
      label430:
      if (localFragment.mInLayout) {
        throw new IllegalArgumentException(paramAttributeSet.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(i) + ", tag " + str2 + ", or parent id 0x" + Integer.toHexString(j) + " with another fragment for " + str1);
      }
      localFragment.mInLayout = true;
      if (localFragment.mRetaining) {
        break label352;
      }
      localFragment.onInflate$2c1ed547$6a2adb45();
      break label352;
      label544:
      moveToState(localFragment);
    }
    label553:
    if (i != 0) {
      localFragment.mView.setId(i);
    }
    if (localFragment.mView.getTag() == null) {
      localFragment.mView.setTag(str2);
    }
    return localFragment.mView;
  }
  
  public final void popBackStack()
  {
    enqueueAction(new Runnable()
    {
      public final void run()
      {
        FragmentManagerImpl localFragmentManagerImpl = FragmentManagerImpl.this;
        localFragmentManagerImpl.popBackStackState$68507953(null, -1, 0);
      }
    }, false);
  }
  
  public final void popBackStack(final String paramString, final int paramInt)
  {
    enqueueAction(new Runnable()
    {
      public final void run()
      {
        FragmentManagerImpl localFragmentManagerImpl = FragmentManagerImpl.this;
        localFragmentManagerImpl.popBackStackState$68507953(paramString, -1, paramInt);
      }
    }, false);
  }
  
  public final void popBackStack$255f295(final int paramInt)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException("Bad id: " + paramInt);
    }
    enqueueAction(new Runnable()
    {
      public final void run()
      {
        FragmentManagerImpl localFragmentManagerImpl = FragmentManagerImpl.this;
        localFragmentManagerImpl.popBackStackState$68507953(null, paramInt, this.val$flags);
      }
    }, false);
  }
  
  public final boolean popBackStackImmediate()
  {
    checkStateLoss();
    execPendingActions();
    return popBackStackState$68507953(null, -1, 0);
  }
  
  final boolean popBackStackState$68507953(String paramString, int paramInt1, int paramInt2)
  {
    if (this.mBackStack == null) {
      return false;
    }
    if ((paramString == null) && (paramInt1 < 0) && ((paramInt2 & 0x1) == 0))
    {
      int i1 = -1 + this.mBackStack.size();
      if (i1 < 0) {
        return false;
      }
      BackStackRecord localBackStackRecord4 = (BackStackRecord)this.mBackStack.remove(i1);
      SparseArray localSparseArray3 = new SparseArray();
      SparseArray localSparseArray4 = new SparseArray();
      localBackStackRecord4.calculateBackFragments(localSparseArray3, localSparseArray4);
      localBackStackRecord4.popFromBackStack(true, null, localSparseArray3, localSparseArray4);
      reportBackStackChanged();
      return true;
    }
    int i = -1;
    if ((paramString != null) || (paramInt1 >= 0))
    {
      for (i = -1 + this.mBackStack.size(); i >= 0; i--)
      {
        BackStackRecord localBackStackRecord3 = (BackStackRecord)this.mBackStack.get(i);
        if (((paramString != null) && (paramString.equals(localBackStackRecord3.mName))) || ((paramInt1 >= 0) && (paramInt1 == localBackStackRecord3.mIndex))) {
          break;
        }
      }
      if (i < 0) {
        return false;
      }
      if ((paramInt2 & 0x1) != 0)
      {
        i--;
        while (i >= 0)
        {
          BackStackRecord localBackStackRecord2 = (BackStackRecord)this.mBackStack.get(i);
          if (((paramString == null) || (!paramString.equals(localBackStackRecord2.mName))) && ((paramInt1 < 0) || (paramInt1 != localBackStackRecord2.mIndex))) {
            break;
          }
          i--;
        }
      }
    }
    if (i == -1 + this.mBackStack.size()) {
      return false;
    }
    ArrayList localArrayList = new ArrayList();
    for (int j = -1 + this.mBackStack.size(); j > i; j--) {
      localArrayList.add(this.mBackStack.remove(j));
    }
    int k = -1 + localArrayList.size();
    SparseArray localSparseArray1 = new SparseArray();
    SparseArray localSparseArray2 = new SparseArray();
    for (int m = 0; m <= k; m++) {
      ((BackStackRecord)localArrayList.get(m)).calculateBackFragments(localSparseArray1, localSparseArray2);
    }
    BackStackRecord.TransitionState localTransitionState = null;
    int n = 0;
    label376:
    BackStackRecord localBackStackRecord1;
    if (n <= k)
    {
      if (DEBUG) {
        Log.v("FragmentManager", "Popping back stack state: " + localArrayList.get(n));
      }
      localBackStackRecord1 = (BackStackRecord)localArrayList.get(n);
      if (n != k) {
        break label461;
      }
    }
    label461:
    for (boolean bool = true;; bool = false)
    {
      localTransitionState = localBackStackRecord1.popFromBackStack(bool, localTransitionState, localSparseArray1, localSparseArray2);
      n++;
      break label376;
      break;
    }
  }
  
  public final void putFragment(Bundle paramBundle, String paramString, Fragment paramFragment)
  {
    if (paramFragment.mIndex < 0) {
      throwException(new IllegalStateException("Fragment " + paramFragment + " is not currently in the FragmentManager"));
    }
    paramBundle.putInt(paramString, paramFragment.mIndex);
  }
  
  public final void removeFragment(Fragment paramFragment, int paramInt1, int paramInt2)
  {
    if (DEBUG) {
      Log.v("FragmentManager", "remove: " + paramFragment + " nesting=" + paramFragment.mBackStackNesting);
    }
    int i;
    int j;
    if (paramFragment.mBackStackNesting > 0)
    {
      i = 1;
      if (i != 0) {
        break label142;
      }
      j = 1;
      label60:
      if ((!paramFragment.mDetached) || (j != 0))
      {
        if (this.mAdded != null) {
          this.mAdded.remove(paramFragment);
        }
        if ((paramFragment.mHasMenu) && (paramFragment.mMenuVisible)) {
          this.mNeedMenuInvalidate = true;
        }
        paramFragment.mAdded = false;
        paramFragment.mRemoving = true;
        if (j == 0) {
          break label148;
        }
      }
    }
    label142:
    label148:
    for (int k = 0;; k = 1)
    {
      moveToState(paramFragment, k, paramInt1, paramInt2, false);
      return;
      i = 0;
      break;
      j = 0;
      break label60;
    }
  }
  
  public final void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener paramOnBackStackChangedListener)
  {
    if (this.mBackStackChangeListeners != null) {
      this.mBackStackChangeListeners.remove(paramOnBackStackChangedListener);
    }
  }
  
  final void reportBackStackChanged()
  {
    if (this.mBackStackChangeListeners != null) {
      for (int i = 0; i < this.mBackStackChangeListeners.size(); i++) {
        ((FragmentManager.OnBackStackChangedListener)this.mBackStackChangeListeners.get(i)).onBackStackChanged();
      }
    }
  }
  
  final void restoreAllState(Parcelable paramParcelable, List<Fragment> paramList)
  {
    if (paramParcelable == null) {}
    for (;;)
    {
      return;
      FragmentManagerState localFragmentManagerState = (FragmentManagerState)paramParcelable;
      if (localFragmentManagerState.mActive != null)
      {
        if (paramList != null) {
          for (int n = 0; n < paramList.size(); n++)
          {
            Fragment localFragment5 = (Fragment)paramList.get(n);
            if (DEBUG) {
              Log.v("FragmentManager", "restoreAllState: re-attaching retained " + localFragment5);
            }
            FragmentState localFragmentState2 = localFragmentManagerState.mActive[localFragment5.mIndex];
            localFragmentState2.mInstance = localFragment5;
            localFragment5.mSavedViewState = null;
            localFragment5.mBackStackNesting = 0;
            localFragment5.mInLayout = false;
            localFragment5.mAdded = false;
            localFragment5.mTarget = null;
            if (localFragmentState2.mSavedFragmentState != null)
            {
              localFragmentState2.mSavedFragmentState.setClassLoader(this.mHost.mContext.getClassLoader());
              localFragment5.mSavedViewState = localFragmentState2.mSavedFragmentState.getSparseParcelableArray("android:view_state");
              localFragment5.mSavedFragmentState = localFragmentState2.mSavedFragmentState;
            }
          }
        }
        this.mActive = new ArrayList(localFragmentManagerState.mActive.length);
        if (this.mAvailIndices != null) {
          this.mAvailIndices.clear();
        }
        int i = 0;
        if (i < localFragmentManagerState.mActive.length)
        {
          FragmentState localFragmentState1 = localFragmentManagerState.mActive[i];
          if (localFragmentState1 != null)
          {
            FragmentHostCallback localFragmentHostCallback = this.mHost;
            Fragment localFragment3 = this.mParent;
            if (localFragmentState1.mInstance == null)
            {
              Context localContext = localFragmentHostCallback.mContext;
              if (localFragmentState1.mArguments != null) {
                localFragmentState1.mArguments.setClassLoader(localContext.getClassLoader());
              }
              localFragmentState1.mInstance = Fragment.instantiate(localContext, localFragmentState1.mClassName, localFragmentState1.mArguments);
              if (localFragmentState1.mSavedFragmentState != null)
              {
                localFragmentState1.mSavedFragmentState.setClassLoader(localContext.getClassLoader());
                localFragmentState1.mInstance.mSavedFragmentState = localFragmentState1.mSavedFragmentState;
              }
              localFragmentState1.mInstance.setIndex(localFragmentState1.mIndex, localFragment3);
              localFragmentState1.mInstance.mFromLayout = localFragmentState1.mFromLayout;
              localFragmentState1.mInstance.mRestored = true;
              localFragmentState1.mInstance.mFragmentId = localFragmentState1.mFragmentId;
              localFragmentState1.mInstance.mContainerId = localFragmentState1.mContainerId;
              localFragmentState1.mInstance.mTag = localFragmentState1.mTag;
              localFragmentState1.mInstance.mRetainInstance = localFragmentState1.mRetainInstance;
              localFragmentState1.mInstance.mDetached = localFragmentState1.mDetached;
              localFragmentState1.mInstance.mFragmentManager = localFragmentHostCallback.mFragmentManager;
              if (DEBUG) {
                Log.v("FragmentManager", "Instantiated fragment " + localFragmentState1.mInstance);
              }
            }
            Fragment localFragment4 = localFragmentState1.mInstance;
            if (DEBUG) {
              Log.v("FragmentManager", "restoreAllState: active #" + i + ": " + localFragment4);
            }
            this.mActive.add(localFragment4);
            localFragmentState1.mInstance = null;
          }
          for (;;)
          {
            i++;
            break;
            this.mActive.add(null);
            if (this.mAvailIndices == null) {
              this.mAvailIndices = new ArrayList();
            }
            if (DEBUG) {
              Log.v("FragmentManager", "restoreAllState: avail #" + i);
            }
            this.mAvailIndices.add(Integer.valueOf(i));
          }
        }
        if (paramList != null)
        {
          int m = 0;
          if (m < paramList.size())
          {
            Fragment localFragment2 = (Fragment)paramList.get(m);
            if (localFragment2.mTargetIndex >= 0) {
              if (localFragment2.mTargetIndex >= this.mActive.size()) {
                break label715;
              }
            }
            for (localFragment2.mTarget = ((Fragment)this.mActive.get(localFragment2.mTargetIndex));; localFragment2.mTarget = null)
            {
              m++;
              break;
              label715:
              Log.w("FragmentManager", "Re-attaching retained fragment " + localFragment2 + " target no longer exists: " + localFragment2.mTargetIndex);
            }
          }
        }
        if (localFragmentManagerState.mAdded != null)
        {
          this.mAdded = new ArrayList(localFragmentManagerState.mAdded.length);
          for (int k = 0; k < localFragmentManagerState.mAdded.length; k++)
          {
            Fragment localFragment1 = (Fragment)this.mActive.get(localFragmentManagerState.mAdded[k]);
            if (localFragment1 == null) {
              throwException(new IllegalStateException("No instantiated fragment for index #" + localFragmentManagerState.mAdded[k]));
            }
            localFragment1.mAdded = true;
            if (DEBUG) {
              Log.v("FragmentManager", "restoreAllState: added #" + k + ": " + localFragment1);
            }
            if (this.mAdded.contains(localFragment1)) {
              throw new IllegalStateException("Already added!");
            }
            this.mAdded.add(localFragment1);
          }
        }
        this.mAdded = null;
        if (localFragmentManagerState.mBackStack == null) {
          break;
        }
        this.mBackStack = new ArrayList(localFragmentManagerState.mBackStack.length);
        for (int j = 0; j < localFragmentManagerState.mBackStack.length; j++)
        {
          BackStackRecord localBackStackRecord = localFragmentManagerState.mBackStack[j].instantiate(this);
          if (DEBUG)
          {
            Log.v("FragmentManager", "restoreAllState: back stack #" + j + " (index " + localBackStackRecord.mIndex + "): " + localBackStackRecord);
            localBackStackRecord.dump("  ", new PrintWriter(new LogWriter("FragmentManager")), false);
          }
          this.mBackStack.add(localBackStackRecord);
          if (localBackStackRecord.mIndex >= 0) {
            setBackStackIndex(localBackStackRecord.mIndex, localBackStackRecord);
          }
        }
      }
    }
    this.mBackStack = null;
  }
  
  final Parcelable saveAllState()
  {
    execPendingActions();
    if (HONEYCOMB) {
      this.mStateSaved = true;
    }
    if ((this.mActive == null) || (this.mActive.size() <= 0)) {
      return null;
    }
    int i = this.mActive.size();
    FragmentState[] arrayOfFragmentState = new FragmentState[i];
    int j = 0;
    int k = 0;
    Fragment localFragment;
    FragmentState localFragmentState;
    Bundle localBundle;
    if (k < i)
    {
      localFragment = (Fragment)this.mActive.get(k);
      if (localFragment != null)
      {
        if (localFragment.mIndex < 0) {
          throwException(new IllegalStateException("Failure saving state: active " + localFragment + " has cleared index: " + localFragment.mIndex));
        }
        j = 1;
        localFragmentState = new FragmentState(localFragment);
        arrayOfFragmentState[k] = localFragmentState;
        if ((localFragment.mState <= 0) || (localFragmentState.mSavedFragmentState != null)) {
          break label478;
        }
        if (this.mStateBundle == null) {
          this.mStateBundle = new Bundle();
        }
        localFragment.performSaveInstanceState(this.mStateBundle);
        if (this.mStateBundle.isEmpty()) {
          break label850;
        }
        localBundle = this.mStateBundle;
        this.mStateBundle = null;
      }
    }
    for (;;)
    {
      if (localFragment.mView != null) {
        saveFragmentViewState(localFragment);
      }
      if (localFragment.mSavedViewState != null)
      {
        if (localBundle == null) {
          localBundle = new Bundle();
        }
        localBundle.putSparseParcelableArray("android:view_state", localFragment.mSavedViewState);
      }
      if (!localFragment.mUserVisibleHint)
      {
        if (localBundle == null) {
          localBundle = new Bundle();
        }
        localBundle.putBoolean("android:user_visible_hint", localFragment.mUserVisibleHint);
      }
      localFragmentState.mSavedFragmentState = localBundle;
      if (localFragment.mTarget != null)
      {
        if (localFragment.mTarget.mIndex < 0) {
          throwException(new IllegalStateException("Failure saving state: " + localFragment + " has target not in fragment manager: " + localFragment.mTarget));
        }
        if (localFragmentState.mSavedFragmentState == null) {
          localFragmentState.mSavedFragmentState = new Bundle();
        }
        putFragment(localFragmentState.mSavedFragmentState, "android:target_state", localFragment.mTarget);
        if (localFragment.mTargetRequestCode != 0) {
          localFragmentState.mSavedFragmentState.putInt("android:target_req_state", localFragment.mTargetRequestCode);
        }
      }
      for (;;)
      {
        if (DEBUG) {
          Log.v("FragmentManager", "Saved state of " + localFragment + ": " + localFragmentState.mSavedFragmentState);
        }
        k++;
        break;
        label478:
        localFragmentState.mSavedFragmentState = localFragment.mSavedFragmentState;
      }
      if (j == 0)
      {
        if (!DEBUG) {
          break;
        }
        Log.v("FragmentManager", "saveAllState: no fragments!");
        return null;
      }
      ArrayList localArrayList1 = this.mAdded;
      int[] arrayOfInt = null;
      if (localArrayList1 != null)
      {
        int i1 = this.mAdded.size();
        arrayOfInt = null;
        if (i1 > 0)
        {
          arrayOfInt = new int[i1];
          for (int i2 = 0; i2 < i1; i2++)
          {
            arrayOfInt[i2] = ((Fragment)this.mAdded.get(i2)).mIndex;
            if (arrayOfInt[i2] < 0) {
              throwException(new IllegalStateException("Failure saving state: active " + this.mAdded.get(i2) + " has cleared index: " + arrayOfInt[i2]));
            }
            if (DEBUG) {
              Log.v("FragmentManager", "saveAllState: adding fragment #" + i2 + ": " + this.mAdded.get(i2));
            }
          }
        }
      }
      ArrayList localArrayList2 = this.mBackStack;
      BackStackState[] arrayOfBackStackState = null;
      if (localArrayList2 != null)
      {
        int m = this.mBackStack.size();
        arrayOfBackStackState = null;
        if (m > 0)
        {
          arrayOfBackStackState = new BackStackState[m];
          for (int n = 0; n < m; n++)
          {
            arrayOfBackStackState[n] = new BackStackState((BackStackRecord)this.mBackStack.get(n));
            if (DEBUG) {
              Log.v("FragmentManager", "saveAllState: adding back stack #" + n + ": " + this.mBackStack.get(n));
            }
          }
        }
      }
      FragmentManagerState localFragmentManagerState = new FragmentManagerState();
      localFragmentManagerState.mActive = arrayOfFragmentState;
      localFragmentManagerState.mAdded = arrayOfInt;
      localFragmentManagerState.mBackStack = arrayOfBackStackState;
      return localFragmentManagerState;
      label850:
      localBundle = null;
    }
  }
  
  public final void showFragment(Fragment paramFragment, int paramInt1, int paramInt2)
  {
    if (DEBUG) {
      Log.v("FragmentManager", "show: " + paramFragment);
    }
    if (paramFragment.mHidden)
    {
      paramFragment.mHidden = false;
      if (paramFragment.mView != null)
      {
        Animation localAnimation = loadAnimation(paramFragment, paramInt1, true, paramInt2);
        if (localAnimation != null)
        {
          setHWLayerAnimListenerIfAlpha(paramFragment.mView, localAnimation);
          paramFragment.mView.startAnimation(localAnimation);
        }
        paramFragment.mView.setVisibility(0);
      }
      if ((paramFragment.mAdded) && (paramFragment.mHasMenu) && (paramFragment.mMenuVisible)) {
        this.mNeedMenuInvalidate = true;
      }
      Fragment.onHiddenChanged$1385ff();
    }
  }
  
  final void startPendingDeferredFragments()
  {
    if (this.mActive == null) {
      return;
    }
    int i = 0;
    label10:
    Fragment localFragment;
    if (i < this.mActive.size())
    {
      localFragment = (Fragment)this.mActive.get(i);
      if ((localFragment != null) && (localFragment.mDeferStart))
      {
        if (!this.mExecutingActions) {
          break label62;
        }
        this.mHavePendingDeferredStart = true;
      }
    }
    for (;;)
    {
      i++;
      break label10;
      break;
      label62:
      localFragment.mDeferStart = false;
      moveToState(localFragment, this.mCurState, 0, 0, false);
    }
  }
  
  public final String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append("FragmentManager{");
    localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    localStringBuilder.append(" in ");
    if (this.mParent != null) {
      DebugUtils.buildShortClassTag(this.mParent, localStringBuilder);
    }
    for (;;)
    {
      localStringBuilder.append("}}");
      return localStringBuilder.toString();
      DebugUtils.buildShortClassTag(this.mHost, localStringBuilder);
    }
  }
  
  static class AnimateOnHWLayerIfNeededListener
    implements Animation.AnimationListener
  {
    private Animation.AnimationListener mOrignalListener = null;
    private boolean mShouldRunOnHWLayer = false;
    private View mView = null;
    
    public AnimateOnHWLayerIfNeededListener(View paramView, Animation paramAnimation)
    {
      if ((paramView == null) || (paramAnimation == null)) {
        return;
      }
      this.mView = paramView;
    }
    
    public AnimateOnHWLayerIfNeededListener(View paramView, Animation paramAnimation, Animation.AnimationListener paramAnimationListener)
    {
      if ((paramView == null) || (paramAnimation == null)) {
        return;
      }
      this.mOrignalListener = paramAnimationListener;
      this.mView = paramView;
    }
    
    public void onAnimationEnd(Animation paramAnimation)
    {
      if ((this.mView != null) && (this.mShouldRunOnHWLayer)) {
        this.mView.post(new Runnable()
        {
          public final void run()
          {
            ViewCompat.setLayerType(FragmentManagerImpl.AnimateOnHWLayerIfNeededListener.this.mView, 0, null);
          }
        });
      }
      if (this.mOrignalListener != null) {
        this.mOrignalListener.onAnimationEnd(paramAnimation);
      }
    }
    
    public void onAnimationRepeat(Animation paramAnimation)
    {
      if (this.mOrignalListener != null) {
        this.mOrignalListener.onAnimationRepeat(paramAnimation);
      }
    }
    
    public void onAnimationStart(Animation paramAnimation)
    {
      if (this.mView != null)
      {
        this.mShouldRunOnHWLayer = FragmentManagerImpl.shouldRunOnHWLayer(this.mView, paramAnimation);
        if (this.mShouldRunOnHWLayer) {
          this.mView.post(new Runnable()
          {
            public final void run()
            {
              ViewCompat.setLayerType(FragmentManagerImpl.AnimateOnHWLayerIfNeededListener.this.mView, 2, null);
            }
          });
        }
      }
      if (this.mOrignalListener != null) {
        this.mOrignalListener.onAnimationStart(paramAnimation);
      }
    }
  }
  
  static final class FragmentTag
  {
    public static final int[] Fragment = { 16842755, 16842960, 16842961 };
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.app.FragmentManagerImpl
 * JD-Core Version:    0.7.0.1
 */