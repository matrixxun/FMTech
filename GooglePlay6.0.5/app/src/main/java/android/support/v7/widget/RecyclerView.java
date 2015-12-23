package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.v4.os.TraceCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerView
  extends ViewGroup
  implements NestedScrollingChild, ScrollingView
{
  private static final boolean FORCE_INVALIDATE_DISPLAY_LIST;
  private static final Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE;
  private static final Interpolator sQuinticInterpolator;
  private RecyclerViewAccessibilityDelegate mAccessibilityDelegate;
  private final AccessibilityManager mAccessibilityManager;
  private OnItemTouchListener mActiveOnItemTouchListener;
  private Adapter mAdapter;
  AdapterHelper mAdapterHelper;
  private boolean mAdapterUpdateDuringMeasure;
  EdgeEffectCompat mBottomGlow;
  private ChildDrawingOrderCallback mChildDrawingOrderCallback;
  public ChildHelper mChildHelper;
  private boolean mClipToPadding;
  boolean mDataSetHasChangedAfterLayout;
  private boolean mEatRequestLayout;
  private int mEatenAccessibilityChangeFlags;
  boolean mFirstLayoutComplete;
  private boolean mHasFixedSize;
  private boolean mIgnoreMotionEventTillDown;
  private int mInitialTouchX;
  private int mInitialTouchY;
  private boolean mIsAttached;
  ItemAnimator mItemAnimator;
  private RecyclerView.ItemAnimator.ItemAnimatorListener mItemAnimatorListener;
  private Runnable mItemAnimatorRunner;
  private final ArrayList<ItemDecoration> mItemDecorations;
  boolean mItemsAddedOrRemoved;
  boolean mItemsChanged;
  private int mLastTouchX;
  private int mLastTouchY;
  public LayoutManager mLayout;
  public boolean mLayoutFrozen;
  private int mLayoutOrScrollCounter;
  private boolean mLayoutRequestEaten;
  EdgeEffectCompat mLeftGlow;
  private final int mMaxFlingVelocity;
  private final int mMinFlingVelocity;
  private final int[] mMinMaxLayoutPositions;
  private final int[] mNestedOffsets;
  private final RecyclerViewDataObserver mObserver;
  private List<Object> mOnChildAttachStateListeners;
  private final ArrayList<OnItemTouchListener> mOnItemTouchListeners;
  private SavedState mPendingSavedState;
  private final boolean mPostUpdatesOnAnimation;
  private boolean mPostedAnimatorRunner;
  final Recycler mRecycler;
  private RecyclerListener mRecyclerListener;
  EdgeEffectCompat mRightGlow;
  private final int[] mScrollConsumed;
  private float mScrollFactor;
  private OnScrollListener mScrollListener;
  private List<OnScrollListener> mScrollListeners;
  private final int[] mScrollOffset;
  private int mScrollPointerId;
  private int mScrollState;
  private final NestedScrollingChildHelper mScrollingChildHelper;
  final State mState;
  private final Rect mTempRect;
  EdgeEffectCompat mTopGlow;
  private int mTouchSlop;
  private final Runnable mUpdateChildViewsRunnable;
  private VelocityTracker mVelocityTracker;
  private final ViewFlinger mViewFlinger;
  private final ViewInfoStore.ProcessCallback mViewInfoProcessCallback;
  final ViewInfoStore mViewInfoStore;
  
  static
  {
    if ((Build.VERSION.SDK_INT == 18) || (Build.VERSION.SDK_INT == 19) || (Build.VERSION.SDK_INT == 20)) {}
    for (boolean bool = true;; bool = false)
    {
      FORCE_INVALIDATE_DISPLAY_LIST = bool;
      Class[] arrayOfClass = new Class[4];
      arrayOfClass[0] = Context.class;
      arrayOfClass[1] = AttributeSet.class;
      arrayOfClass[2] = Integer.TYPE;
      arrayOfClass[3] = Integer.TYPE;
      LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = arrayOfClass;
      sQuinticInterpolator = new Interpolator()
      {
        public final float getInterpolation(float paramAnonymousFloat)
        {
          float f = paramAnonymousFloat - 1.0F;
          return 1.0F + f * (f * (f * (f * f)));
        }
      };
      return;
    }
  }
  
  public RecyclerView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public RecyclerView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  /* Error */
  public RecyclerView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_2
    //   3: iload_3
    //   4: invokespecial 160	android/view/ViewGroup:<init>	(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    //   7: aload_0
    //   8: new 162	android/support/v7/widget/RecyclerView$RecyclerViewDataObserver
    //   11: dup
    //   12: aload_0
    //   13: iconst_0
    //   14: invokespecial 165	android/support/v7/widget/RecyclerView$RecyclerViewDataObserver:<init>	(Landroid/support/v7/widget/RecyclerView;B)V
    //   17: putfield 167	android/support/v7/widget/RecyclerView:mObserver	Landroid/support/v7/widget/RecyclerView$RecyclerViewDataObserver;
    //   20: aload_0
    //   21: new 169	android/support/v7/widget/RecyclerView$Recycler
    //   24: dup
    //   25: aload_0
    //   26: invokespecial 172	android/support/v7/widget/RecyclerView$Recycler:<init>	(Landroid/support/v7/widget/RecyclerView;)V
    //   29: putfield 174	android/support/v7/widget/RecyclerView:mRecycler	Landroid/support/v7/widget/RecyclerView$Recycler;
    //   32: aload_0
    //   33: new 176	android/support/v7/widget/ViewInfoStore
    //   36: dup
    //   37: invokespecial 177	android/support/v7/widget/ViewInfoStore:<init>	()V
    //   40: putfield 179	android/support/v7/widget/RecyclerView:mViewInfoStore	Landroid/support/v7/widget/ViewInfoStore;
    //   43: aload_0
    //   44: new 181	android/support/v7/widget/RecyclerView$1
    //   47: dup
    //   48: aload_0
    //   49: invokespecial 182	android/support/v7/widget/RecyclerView$1:<init>	(Landroid/support/v7/widget/RecyclerView;)V
    //   52: putfield 184	android/support/v7/widget/RecyclerView:mUpdateChildViewsRunnable	Ljava/lang/Runnable;
    //   55: aload_0
    //   56: new 186	android/graphics/Rect
    //   59: dup
    //   60: invokespecial 187	android/graphics/Rect:<init>	()V
    //   63: putfield 189	android/support/v7/widget/RecyclerView:mTempRect	Landroid/graphics/Rect;
    //   66: aload_0
    //   67: new 191	java/util/ArrayList
    //   70: dup
    //   71: invokespecial 192	java/util/ArrayList:<init>	()V
    //   74: putfield 194	android/support/v7/widget/RecyclerView:mItemDecorations	Ljava/util/ArrayList;
    //   77: aload_0
    //   78: new 191	java/util/ArrayList
    //   81: dup
    //   82: invokespecial 192	java/util/ArrayList:<init>	()V
    //   85: putfield 196	android/support/v7/widget/RecyclerView:mOnItemTouchListeners	Ljava/util/ArrayList;
    //   88: aload_0
    //   89: iconst_0
    //   90: putfield 198	android/support/v7/widget/RecyclerView:mDataSetHasChangedAfterLayout	Z
    //   93: aload_0
    //   94: iconst_0
    //   95: putfield 200	android/support/v7/widget/RecyclerView:mLayoutOrScrollCounter	I
    //   98: aload_0
    //   99: new 202	android/support/v7/widget/DefaultItemAnimator
    //   102: dup
    //   103: invokespecial 203	android/support/v7/widget/DefaultItemAnimator:<init>	()V
    //   106: putfield 205	android/support/v7/widget/RecyclerView:mItemAnimator	Landroid/support/v7/widget/RecyclerView$ItemAnimator;
    //   109: aload_0
    //   110: iconst_0
    //   111: putfield 207	android/support/v7/widget/RecyclerView:mScrollState	I
    //   114: aload_0
    //   115: iconst_m1
    //   116: putfield 209	android/support/v7/widget/RecyclerView:mScrollPointerId	I
    //   119: aload_0
    //   120: ldc 210
    //   122: putfield 212	android/support/v7/widget/RecyclerView:mScrollFactor	F
    //   125: aload_0
    //   126: new 214	android/support/v7/widget/RecyclerView$ViewFlinger
    //   129: dup
    //   130: aload_0
    //   131: invokespecial 215	android/support/v7/widget/RecyclerView$ViewFlinger:<init>	(Landroid/support/v7/widget/RecyclerView;)V
    //   134: putfield 217	android/support/v7/widget/RecyclerView:mViewFlinger	Landroid/support/v7/widget/RecyclerView$ViewFlinger;
    //   137: aload_0
    //   138: new 219	android/support/v7/widget/RecyclerView$State
    //   141: dup
    //   142: invokespecial 220	android/support/v7/widget/RecyclerView$State:<init>	()V
    //   145: putfield 222	android/support/v7/widget/RecyclerView:mState	Landroid/support/v7/widget/RecyclerView$State;
    //   148: aload_0
    //   149: iconst_0
    //   150: putfield 224	android/support/v7/widget/RecyclerView:mItemsAddedOrRemoved	Z
    //   153: aload_0
    //   154: iconst_0
    //   155: putfield 226	android/support/v7/widget/RecyclerView:mItemsChanged	Z
    //   158: aload_0
    //   159: new 228	android/support/v7/widget/RecyclerView$ItemAnimatorRestoreListener
    //   162: dup
    //   163: aload_0
    //   164: iconst_0
    //   165: invokespecial 229	android/support/v7/widget/RecyclerView$ItemAnimatorRestoreListener:<init>	(Landroid/support/v7/widget/RecyclerView;B)V
    //   168: putfield 231	android/support/v7/widget/RecyclerView:mItemAnimatorListener	Landroid/support/v7/widget/RecyclerView$ItemAnimator$ItemAnimatorListener;
    //   171: aload_0
    //   172: iconst_0
    //   173: putfield 233	android/support/v7/widget/RecyclerView:mPostedAnimatorRunner	Z
    //   176: aload_0
    //   177: iconst_2
    //   178: newarray int
    //   180: putfield 235	android/support/v7/widget/RecyclerView:mMinMaxLayoutPositions	[I
    //   183: aload_0
    //   184: iconst_2
    //   185: newarray int
    //   187: putfield 237	android/support/v7/widget/RecyclerView:mScrollOffset	[I
    //   190: aload_0
    //   191: iconst_2
    //   192: newarray int
    //   194: putfield 239	android/support/v7/widget/RecyclerView:mScrollConsumed	[I
    //   197: aload_0
    //   198: iconst_2
    //   199: newarray int
    //   201: putfield 241	android/support/v7/widget/RecyclerView:mNestedOffsets	[I
    //   204: aload_0
    //   205: new 243	android/support/v7/widget/RecyclerView$2
    //   208: dup
    //   209: aload_0
    //   210: invokespecial 244	android/support/v7/widget/RecyclerView$2:<init>	(Landroid/support/v7/widget/RecyclerView;)V
    //   213: putfield 246	android/support/v7/widget/RecyclerView:mItemAnimatorRunner	Ljava/lang/Runnable;
    //   216: aload_0
    //   217: new 248	android/support/v7/widget/RecyclerView$4
    //   220: dup
    //   221: aload_0
    //   222: invokespecial 249	android/support/v7/widget/RecyclerView$4:<init>	(Landroid/support/v7/widget/RecyclerView;)V
    //   225: putfield 251	android/support/v7/widget/RecyclerView:mViewInfoProcessCallback	Landroid/support/v7/widget/ViewInfoStore$ProcessCallback;
    //   228: aload_0
    //   229: iconst_1
    //   230: invokevirtual 255	android/support/v7/widget/RecyclerView:setScrollContainer	(Z)V
    //   233: aload_0
    //   234: iconst_1
    //   235: invokevirtual 258	android/support/v7/widget/RecyclerView:setFocusableInTouchMode	(Z)V
    //   238: getstatic 117	android/os/Build$VERSION:SDK_INT	I
    //   241: bipush 16
    //   243: if_icmplt +353 -> 596
    //   246: iconst_1
    //   247: istore 4
    //   249: aload_0
    //   250: iload 4
    //   252: putfield 260	android/support/v7/widget/RecyclerView:mPostUpdatesOnAnimation	Z
    //   255: aload_1
    //   256: invokestatic 266	android/view/ViewConfiguration:get	(Landroid/content/Context;)Landroid/view/ViewConfiguration;
    //   259: astore 5
    //   261: aload_0
    //   262: aload 5
    //   264: invokevirtual 270	android/view/ViewConfiguration:getScaledTouchSlop	()I
    //   267: putfield 272	android/support/v7/widget/RecyclerView:mTouchSlop	I
    //   270: aload_0
    //   271: aload 5
    //   273: invokevirtual 275	android/view/ViewConfiguration:getScaledMinimumFlingVelocity	()I
    //   276: putfield 277	android/support/v7/widget/RecyclerView:mMinFlingVelocity	I
    //   279: aload_0
    //   280: aload 5
    //   282: invokevirtual 280	android/view/ViewConfiguration:getScaledMaximumFlingVelocity	()I
    //   285: putfield 282	android/support/v7/widget/RecyclerView:mMaxFlingVelocity	I
    //   288: aload_0
    //   289: invokestatic 288	android/support/v4/view/ViewCompat:getOverScrollMode	(Landroid/view/View;)I
    //   292: iconst_2
    //   293: if_icmpne +309 -> 602
    //   296: iconst_1
    //   297: istore 6
    //   299: aload_0
    //   300: iload 6
    //   302: invokevirtual 291	android/support/v7/widget/RecyclerView:setWillNotDraw	(Z)V
    //   305: aload_0
    //   306: getfield 205	android/support/v7/widget/RecyclerView:mItemAnimator	Landroid/support/v7/widget/RecyclerView$ItemAnimator;
    //   309: aload_0
    //   310: getfield 231	android/support/v7/widget/RecyclerView:mItemAnimatorListener	Landroid/support/v7/widget/RecyclerView$ItemAnimator$ItemAnimatorListener;
    //   313: putfield 296	android/support/v7/widget/RecyclerView$ItemAnimator:mListener	Landroid/support/v7/widget/RecyclerView$ItemAnimator$ItemAnimatorListener;
    //   316: aload_0
    //   317: new 298	android/support/v7/widget/AdapterHelper
    //   320: dup
    //   321: new 300	android/support/v7/widget/RecyclerView$6
    //   324: dup
    //   325: aload_0
    //   326: invokespecial 301	android/support/v7/widget/RecyclerView$6:<init>	(Landroid/support/v7/widget/RecyclerView;)V
    //   329: invokespecial 304	android/support/v7/widget/AdapterHelper:<init>	(Landroid/support/v7/widget/AdapterHelper$Callback;)V
    //   332: putfield 306	android/support/v7/widget/RecyclerView:mAdapterHelper	Landroid/support/v7/widget/AdapterHelper;
    //   335: aload_0
    //   336: new 308	android/support/v7/widget/ChildHelper
    //   339: dup
    //   340: new 310	android/support/v7/widget/RecyclerView$5
    //   343: dup
    //   344: aload_0
    //   345: invokespecial 311	android/support/v7/widget/RecyclerView$5:<init>	(Landroid/support/v7/widget/RecyclerView;)V
    //   348: invokespecial 314	android/support/v7/widget/ChildHelper:<init>	(Landroid/support/v7/widget/ChildHelper$Callback;)V
    //   351: putfield 316	android/support/v7/widget/RecyclerView:mChildHelper	Landroid/support/v7/widget/ChildHelper;
    //   354: aload_0
    //   355: invokestatic 319	android/support/v4/view/ViewCompat:getImportantForAccessibility	(Landroid/view/View;)I
    //   358: ifne +8 -> 366
    //   361: aload_0
    //   362: iconst_1
    //   363: invokestatic 323	android/support/v4/view/ViewCompat:setImportantForAccessibility	(Landroid/view/View;I)V
    //   366: aload_0
    //   367: aload_0
    //   368: invokevirtual 327	android/support/v7/widget/RecyclerView:getContext	()Landroid/content/Context;
    //   371: ldc_w 329
    //   374: invokevirtual 333	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   377: checkcast 335	android/view/accessibility/AccessibilityManager
    //   380: putfield 337	android/support/v7/widget/RecyclerView:mAccessibilityManager	Landroid/view/accessibility/AccessibilityManager;
    //   383: aload_0
    //   384: new 339	android/support/v7/widget/RecyclerViewAccessibilityDelegate
    //   387: dup
    //   388: aload_0
    //   389: invokespecial 340	android/support/v7/widget/RecyclerViewAccessibilityDelegate:<init>	(Landroid/support/v7/widget/RecyclerView;)V
    //   392: invokevirtual 344	android/support/v7/widget/RecyclerView:setAccessibilityDelegateCompat	(Landroid/support/v7/widget/RecyclerViewAccessibilityDelegate;)V
    //   395: aload_2
    //   396: ifnull +182 -> 578
    //   399: aload_1
    //   400: aload_2
    //   401: getstatic 349	android/support/v7/recyclerview/R$styleable:RecyclerView	[I
    //   404: iload_3
    //   405: iconst_0
    //   406: invokevirtual 353	android/content/Context:obtainStyledAttributes	(Landroid/util/AttributeSet;[III)Landroid/content/res/TypedArray;
    //   409: astore 7
    //   411: aload 7
    //   413: getstatic 356	android/support/v7/recyclerview/R$styleable:RecyclerView_layoutManager	I
    //   416: invokevirtual 362	android/content/res/TypedArray:getString	(I)Ljava/lang/String;
    //   419: astore 8
    //   421: aload 7
    //   423: invokevirtual 365	android/content/res/TypedArray:recycle	()V
    //   426: aload 8
    //   428: ifnull +150 -> 578
    //   431: aload 8
    //   433: invokevirtual 371	java/lang/String:trim	()Ljava/lang/String;
    //   436: astore 9
    //   438: aload 9
    //   440: invokevirtual 374	java/lang/String:length	()I
    //   443: ifeq +135 -> 578
    //   446: aload 9
    //   448: iconst_0
    //   449: invokevirtual 378	java/lang/String:charAt	(I)C
    //   452: bipush 46
    //   454: if_icmpne +154 -> 608
    //   457: new 380	java/lang/StringBuilder
    //   460: dup
    //   461: invokespecial 381	java/lang/StringBuilder:<init>	()V
    //   464: aload_1
    //   465: invokevirtual 384	android/content/Context:getPackageName	()Ljava/lang/String;
    //   468: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   471: aload 9
    //   473: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   476: invokevirtual 391	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   479: astore 10
    //   481: aload_0
    //   482: invokevirtual 395	android/support/v7/widget/RecyclerView:isInEditMode	()Z
    //   485: ifeq +177 -> 662
    //   488: aload_0
    //   489: invokevirtual 401	java/lang/Object:getClass	()Ljava/lang/Class;
    //   492: invokevirtual 405	java/lang/Class:getClassLoader	()Ljava/lang/ClassLoader;
    //   495: astore 17
    //   497: aload 17
    //   499: aload 10
    //   501: invokevirtual 411	java/lang/ClassLoader:loadClass	(Ljava/lang/String;)Ljava/lang/Class;
    //   504: ldc_w 413
    //   507: invokevirtual 417	java/lang/Class:asSubclass	(Ljava/lang/Class;)Ljava/lang/Class;
    //   510: astore 18
    //   512: aload 18
    //   514: getstatic 133	android/support/v7/widget/RecyclerView:LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE	[Ljava/lang/Class;
    //   517: invokevirtual 421	java/lang/Class:getConstructor	([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
    //   520: astore 25
    //   522: iconst_4
    //   523: anewarray 397	java/lang/Object
    //   526: astore 24
    //   528: aload 24
    //   530: iconst_0
    //   531: aload_1
    //   532: aastore
    //   533: aload 24
    //   535: iconst_1
    //   536: aload_2
    //   537: aastore
    //   538: aload 24
    //   540: iconst_2
    //   541: iload_3
    //   542: invokestatic 425	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   545: aastore
    //   546: aload 24
    //   548: iconst_3
    //   549: iconst_0
    //   550: invokestatic 425	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   553: aastore
    //   554: aload 25
    //   556: astore 23
    //   558: aload 23
    //   560: iconst_1
    //   561: invokevirtual 430	java/lang/reflect/Constructor:setAccessible	(Z)V
    //   564: aload_0
    //   565: aload 23
    //   567: aload 24
    //   569: invokevirtual 434	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
    //   572: checkcast 413	android/support/v7/widget/RecyclerView$LayoutManager
    //   575: invokevirtual 438	android/support/v7/widget/RecyclerView:setLayoutManager	(Landroid/support/v7/widget/RecyclerView$LayoutManager;)V
    //   578: aload_0
    //   579: new 440	android/support/v4/view/NestedScrollingChildHelper
    //   582: dup
    //   583: aload_0
    //   584: invokespecial 443	android/support/v4/view/NestedScrollingChildHelper:<init>	(Landroid/view/View;)V
    //   587: putfield 445	android/support/v7/widget/RecyclerView:mScrollingChildHelper	Landroid/support/v4/view/NestedScrollingChildHelper;
    //   590: aload_0
    //   591: iconst_1
    //   592: invokevirtual 448	android/support/v7/widget/RecyclerView:setNestedScrollingEnabled	(Z)V
    //   595: return
    //   596: iconst_0
    //   597: istore 4
    //   599: goto -350 -> 249
    //   602: iconst_0
    //   603: istore 6
    //   605: goto -306 -> 299
    //   608: aload 9
    //   610: ldc_w 450
    //   613: invokevirtual 454	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   616: ifeq +10 -> 626
    //   619: aload 9
    //   621: astore 10
    //   623: goto -142 -> 481
    //   626: new 380	java/lang/StringBuilder
    //   629: dup
    //   630: invokespecial 381	java/lang/StringBuilder:<init>	()V
    //   633: ldc 2
    //   635: invokevirtual 458	java/lang/Class:getPackage	()Ljava/lang/Package;
    //   638: invokevirtual 463	java/lang/Package:getName	()Ljava/lang/String;
    //   641: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   644: bipush 46
    //   646: invokevirtual 466	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   649: aload 9
    //   651: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   654: invokevirtual 391	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   657: astore 10
    //   659: goto -178 -> 481
    //   662: aload_1
    //   663: invokevirtual 467	android/content/Context:getClassLoader	()Ljava/lang/ClassLoader;
    //   666: astore 16
    //   668: aload 16
    //   670: astore 17
    //   672: goto -175 -> 497
    //   675: astore 19
    //   677: aload 18
    //   679: iconst_0
    //   680: anewarray 121	java/lang/Class
    //   683: invokevirtual 421	java/lang/Class:getConstructor	([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
    //   686: astore 22
    //   688: aload 22
    //   690: astore 23
    //   692: aconst_null
    //   693: astore 24
    //   695: goto -137 -> 558
    //   698: astore 20
    //   700: aload 20
    //   702: aload 19
    //   704: invokevirtual 471	java/lang/NoSuchMethodException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   707: pop
    //   708: new 473	java/lang/IllegalStateException
    //   711: dup
    //   712: new 380	java/lang/StringBuilder
    //   715: dup
    //   716: invokespecial 381	java/lang/StringBuilder:<init>	()V
    //   719: aload_2
    //   720: invokeinterface 476 1 0
    //   725: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   728: ldc_w 478
    //   731: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   734: aload 10
    //   736: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   739: invokevirtual 391	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   742: aload 20
    //   744: invokespecial 481	java/lang/IllegalStateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   747: athrow
    //   748: astore 15
    //   750: new 473	java/lang/IllegalStateException
    //   753: dup
    //   754: new 380	java/lang/StringBuilder
    //   757: dup
    //   758: invokespecial 381	java/lang/StringBuilder:<init>	()V
    //   761: aload_2
    //   762: invokeinterface 476 1 0
    //   767: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   770: ldc_w 483
    //   773: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   776: aload 10
    //   778: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   781: invokevirtual 391	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   784: aload 15
    //   786: invokespecial 481	java/lang/IllegalStateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   789: athrow
    //   790: astore 14
    //   792: new 473	java/lang/IllegalStateException
    //   795: dup
    //   796: new 380	java/lang/StringBuilder
    //   799: dup
    //   800: invokespecial 381	java/lang/StringBuilder:<init>	()V
    //   803: aload_2
    //   804: invokeinterface 476 1 0
    //   809: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   812: ldc_w 485
    //   815: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   818: aload 10
    //   820: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   823: invokevirtual 391	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   826: aload 14
    //   828: invokespecial 481	java/lang/IllegalStateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   831: athrow
    //   832: astore 13
    //   834: new 473	java/lang/IllegalStateException
    //   837: dup
    //   838: new 380	java/lang/StringBuilder
    //   841: dup
    //   842: invokespecial 381	java/lang/StringBuilder:<init>	()V
    //   845: aload_2
    //   846: invokeinterface 476 1 0
    //   851: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   854: ldc_w 485
    //   857: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   860: aload 10
    //   862: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   865: invokevirtual 391	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   868: aload 13
    //   870: invokespecial 481	java/lang/IllegalStateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   873: athrow
    //   874: astore 12
    //   876: new 473	java/lang/IllegalStateException
    //   879: dup
    //   880: new 380	java/lang/StringBuilder
    //   883: dup
    //   884: invokespecial 381	java/lang/StringBuilder:<init>	()V
    //   887: aload_2
    //   888: invokeinterface 476 1 0
    //   893: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   896: ldc_w 487
    //   899: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   902: aload 10
    //   904: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   907: invokevirtual 391	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   910: aload 12
    //   912: invokespecial 481	java/lang/IllegalStateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   915: athrow
    //   916: astore 11
    //   918: new 473	java/lang/IllegalStateException
    //   921: dup
    //   922: new 380	java/lang/StringBuilder
    //   925: dup
    //   926: invokespecial 381	java/lang/StringBuilder:<init>	()V
    //   929: aload_2
    //   930: invokeinterface 476 1 0
    //   935: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   938: ldc_w 489
    //   941: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   944: aload 10
    //   946: invokevirtual 388	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   949: invokevirtual 391	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   952: aload 11
    //   954: invokespecial 481	java/lang/IllegalStateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   957: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	958	0	this	RecyclerView
    //   0	958	1	paramContext	Context
    //   0	958	2	paramAttributeSet	AttributeSet
    //   0	958	3	paramInt	int
    //   247	351	4	bool1	boolean
    //   259	22	5	localViewConfiguration	ViewConfiguration
    //   297	307	6	bool2	boolean
    //   409	13	7	localTypedArray	android.content.res.TypedArray
    //   419	13	8	str1	String
    //   436	214	9	str2	String
    //   479	466	10	str3	String
    //   916	37	11	localClassCastException	java.lang.ClassCastException
    //   874	37	12	localIllegalAccessException	java.lang.IllegalAccessException
    //   832	37	13	localInstantiationException	java.lang.InstantiationException
    //   790	37	14	localInvocationTargetException	java.lang.reflect.InvocationTargetException
    //   748	37	15	localClassNotFoundException	java.lang.ClassNotFoundException
    //   666	3	16	localClassLoader	java.lang.ClassLoader
    //   495	176	17	localObject1	Object
    //   510	168	18	localClass	Class
    //   675	28	19	localNoSuchMethodException1	java.lang.NoSuchMethodException
    //   698	45	20	localNoSuchMethodException2	java.lang.NoSuchMethodException
    //   686	3	22	localConstructor1	java.lang.reflect.Constructor
    //   556	135	23	localObject2	Object
    //   526	168	24	arrayOfObject	Object[]
    //   520	35	25	localConstructor2	java.lang.reflect.Constructor
    // Exception table:
    //   from	to	target	type
    //   512	554	675	java/lang/NoSuchMethodException
    //   677	688	698	java/lang/NoSuchMethodException
    //   481	497	748	java/lang/ClassNotFoundException
    //   497	512	748	java/lang/ClassNotFoundException
    //   512	554	748	java/lang/ClassNotFoundException
    //   558	578	748	java/lang/ClassNotFoundException
    //   662	668	748	java/lang/ClassNotFoundException
    //   677	688	748	java/lang/ClassNotFoundException
    //   700	748	748	java/lang/ClassNotFoundException
    //   481	497	790	java/lang/reflect/InvocationTargetException
    //   497	512	790	java/lang/reflect/InvocationTargetException
    //   512	554	790	java/lang/reflect/InvocationTargetException
    //   558	578	790	java/lang/reflect/InvocationTargetException
    //   662	668	790	java/lang/reflect/InvocationTargetException
    //   677	688	790	java/lang/reflect/InvocationTargetException
    //   700	748	790	java/lang/reflect/InvocationTargetException
    //   481	497	832	java/lang/InstantiationException
    //   497	512	832	java/lang/InstantiationException
    //   512	554	832	java/lang/InstantiationException
    //   558	578	832	java/lang/InstantiationException
    //   662	668	832	java/lang/InstantiationException
    //   677	688	832	java/lang/InstantiationException
    //   700	748	832	java/lang/InstantiationException
    //   481	497	874	java/lang/IllegalAccessException
    //   497	512	874	java/lang/IllegalAccessException
    //   512	554	874	java/lang/IllegalAccessException
    //   558	578	874	java/lang/IllegalAccessException
    //   662	668	874	java/lang/IllegalAccessException
    //   677	688	874	java/lang/IllegalAccessException
    //   700	748	874	java/lang/IllegalAccessException
    //   481	497	916	java/lang/ClassCastException
    //   497	512	916	java/lang/ClassCastException
    //   512	554	916	java/lang/ClassCastException
    //   558	578	916	java/lang/ClassCastException
    //   662	668	916	java/lang/ClassCastException
    //   677	688	916	java/lang/ClassCastException
    //   700	748	916	java/lang/ClassCastException
  }
  
  private void addAnimatingView(ViewHolder paramViewHolder)
  {
    View localView = paramViewHolder.itemView;
    if (localView.getParent() == this) {}
    for (int i = 1;; i = 0)
    {
      this.mRecycler.unscrapView(getChildViewHolder(localView));
      if (!paramViewHolder.isTmpDetached()) {
        break;
      }
      this.mChildHelper.attachViewToParent(localView, -1, localView.getLayoutParams(), true);
      return;
    }
    if (i == 0)
    {
      this.mChildHelper.addView(localView, -1, true);
      return;
    }
    ChildHelper localChildHelper = this.mChildHelper;
    int j = localChildHelper.mCallback.indexOfChild(localView);
    if (j < 0) {
      throw new IllegalArgumentException("view is not a child, cannot hide " + localView);
    }
    localChildHelper.mBucket.set(j);
    localChildHelper.hideViewInternal(localView);
  }
  
  private void cancelTouch()
  {
    resetTouch();
    setScrollState(0);
  }
  
  private void clearOldPositions()
  {
    int i = 0;
    int j = this.mChildHelper.getUnfilteredChildCount();
    for (int k = 0; k < j; k++)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(k));
      if (!localViewHolder.shouldIgnore()) {
        localViewHolder.clearOldPosition();
      }
    }
    Recycler localRecycler = this.mRecycler;
    int m = localRecycler.mCachedViews.size();
    for (int n = 0; n < m; n++) {
      ((ViewHolder)localRecycler.mCachedViews.get(n)).clearOldPosition();
    }
    int i1 = localRecycler.mAttachedScrap.size();
    for (int i2 = 0; i2 < i1; i2++) {
      ((ViewHolder)localRecycler.mAttachedScrap.get(i2)).clearOldPosition();
    }
    if (localRecycler.mChangedScrap != null)
    {
      int i3 = localRecycler.mChangedScrap.size();
      while (i < i3)
      {
        ((ViewHolder)localRecycler.mChangedScrap.get(i)).clearOldPosition();
        i++;
      }
    }
  }
  
  private void considerReleasingGlowsOnScroll(int paramInt1, int paramInt2)
  {
    EdgeEffectCompat localEdgeEffectCompat = this.mLeftGlow;
    boolean bool1 = false;
    if (localEdgeEffectCompat != null)
    {
      boolean bool2 = this.mLeftGlow.isFinished();
      bool1 = false;
      if (!bool2)
      {
        bool1 = false;
        if (paramInt1 > 0) {
          bool1 = this.mLeftGlow.onRelease();
        }
      }
    }
    if ((this.mRightGlow != null) && (!this.mRightGlow.isFinished()) && (paramInt1 < 0)) {
      bool1 |= this.mRightGlow.onRelease();
    }
    if ((this.mTopGlow != null) && (!this.mTopGlow.isFinished()) && (paramInt2 > 0)) {
      bool1 |= this.mTopGlow.onRelease();
    }
    if ((this.mBottomGlow != null) && (!this.mBottomGlow.isFinished()) && (paramInt2 < 0)) {
      bool1 |= this.mBottomGlow.onRelease();
    }
    if (bool1) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  private void consumePendingUpdateOperations()
  {
    if (!this.mFirstLayoutComplete) {}
    label156:
    label162:
    do
    {
      do
      {
        return;
        if (this.mDataSetHasChangedAfterLayout)
        {
          TraceCompat.beginSection("RV FullInvalidate");
          dispatchLayout();
          TraceCompat.endSection();
          return;
        }
      } while (!this.mAdapterHelper.hasPendingUpdates());
      if ((this.mAdapterHelper.hasAnyUpdateTypes(4)) && (!this.mAdapterHelper.hasAnyUpdateTypes(11)))
      {
        TraceCompat.beginSection("RV PartialInvalidate");
        eatRequestLayout();
        this.mAdapterHelper.preProcess();
        int j;
        if (!this.mLayoutRequestEaten)
        {
          int i = this.mChildHelper.getChildCount();
          j = 0;
          int k = 0;
          if (j < i)
          {
            ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.getChildAt(j));
            if ((localViewHolder == null) || (localViewHolder.shouldIgnore()) || (!localViewHolder.isUpdated())) {
              break label156;
            }
            k = 1;
          }
          if (k == 0) {
            break label162;
          }
          dispatchLayout();
        }
        for (;;)
        {
          resumeRequestLayout(true);
          TraceCompat.endSection();
          return;
          j++;
          break;
          this.mAdapterHelper.consumePostponedUpdates();
        }
      }
    } while (!this.mAdapterHelper.hasPendingUpdates());
    TraceCompat.beginSection("RV FullInvalidate");
    dispatchLayout();
    TraceCompat.endSection();
  }
  
  private void defaultOnMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getMode(paramInt2);
    int k = View.MeasureSpec.getSize(paramInt1);
    int m = View.MeasureSpec.getSize(paramInt2);
    int n;
    switch (i)
    {
    default: 
      n = ViewCompat.getMinimumWidth(this);
      switch (j)
      {
      }
      break;
    }
    for (int i1 = ViewCompat.getMinimumHeight(this);; i1 = m)
    {
      setMeasuredDimension(n, i1);
      return;
      n = k;
      break;
    }
  }
  
  private void dispatchChildDetached(View paramView)
  {
    getChildViewHolderInt(paramView);
    if (this.mOnChildAttachStateListeners != null) {
      for (int i = -1 + this.mOnChildAttachStateListeners.size(); i >= 0; i--) {
        this.mOnChildAttachStateListeners.get(i);
      }
    }
  }
  
  private void dispatchLayout()
  {
    if (this.mAdapter == null)
    {
      Log.e("RecyclerView", "No adapter attached; skipping layout");
      return;
    }
    if (this.mLayout == null)
    {
      Log.e("RecyclerView", "No layout manager attached; skipping layout");
      return;
    }
    this.mViewInfoStore.clear();
    eatRequestLayout();
    onEnterLayoutOrScroll();
    processAdapterUpdatesAndSetAnimationFlags();
    State localState1 = this.mState;
    if ((this.mState.mRunSimpleAnimations) && (this.mItemsChanged)) {}
    int[] arrayOfInt;
    int i;
    for (boolean bool1 = true;; bool1 = false)
    {
      localState1.mTrackOldChangeHolders = bool1;
      this.mItemsChanged = false;
      this.mItemsAddedOrRemoved = false;
      this.mState.mInPreLayout = this.mState.mRunPredictiveAnimations;
      this.mState.mItemCount = this.mAdapter.getItemCount();
      arrayOfInt = this.mMinMaxLayoutPositions;
      i = this.mChildHelper.getChildCount();
      if (i != 0) {
        break;
      }
      arrayOfInt[0] = 0;
      arrayOfInt[1] = 0;
      label149:
      if (!this.mState.mRunSimpleAnimations) {
        break label425;
      }
      int i14 = this.mChildHelper.getChildCount();
      for (int i15 = 0; i15 < i14; i15++)
      {
        ViewHolder localViewHolder7 = getChildViewHolderInt(this.mChildHelper.getChildAt(i15));
        if ((!localViewHolder7.shouldIgnore()) && ((!localViewHolder7.isInvalid()) || (this.mAdapter.mHasStableIds)))
        {
          ItemAnimator.buildAdapterChangeFlagsForAnimations(localViewHolder7);
          localViewHolder7.getUnmodifiedPayloads();
          RecyclerView.ItemAnimator.ItemHolderInfo localItemHolderInfo4 = new RecyclerView.ItemAnimator.ItemHolderInfo().setFrom(localViewHolder7);
          this.mViewInfoStore.addToPreLayout(localViewHolder7, localItemHolderInfo4);
          if ((this.mState.mTrackOldChangeHolders) && (localViewHolder7.isUpdated()) && (!localViewHolder7.isRemoved()) && (!localViewHolder7.shouldIgnore()) && (!localViewHolder7.isInvalid()))
          {
            long l2 = getChangedHolderKey(localViewHolder7);
            this.mViewInfoStore.addToOldChangeHolders(l2, localViewHolder7);
          }
        }
      }
    }
    int j = 2147483647;
    int k = -2147483648;
    int m = 0;
    label340:
    int i16;
    if (m < i)
    {
      ViewHolder localViewHolder8 = getChildViewHolderInt(this.mChildHelper.getChildAt(m));
      if (localViewHolder8.shouldIgnore()) {
        break label1694;
      }
      i16 = localViewHolder8.getLayoutPosition();
      if (i16 < j) {
        j = i16;
      }
      if (i16 <= k) {
        break label1694;
      }
    }
    for (int i17 = j;; i17 = j)
    {
      m++;
      j = i17;
      k = i16;
      break label340;
      arrayOfInt[0] = j;
      arrayOfInt[1] = k;
      break label149;
      label425:
      label614:
      boolean bool2;
      label674:
      label680:
      label841:
      int i7;
      label870:
      ViewHolder localViewHolder3;
      RecyclerView.ItemAnimator.ItemHolderInfo localItemHolderInfo1;
      RecyclerView.ItemAnimator.ItemHolderInfo localItemHolderInfo2;
      if (this.mState.mRunPredictiveAnimations)
      {
        int i10 = this.mChildHelper.getUnfilteredChildCount();
        for (int i11 = 0; i11 < i10; i11++)
        {
          ViewHolder localViewHolder6 = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i11));
          if ((!localViewHolder6.shouldIgnore()) && (localViewHolder6.mOldPosition == -1)) {
            localViewHolder6.mOldPosition = localViewHolder6.mPosition;
          }
        }
        boolean bool3 = this.mState.mStructureChanged;
        this.mState.mStructureChanged = false;
        this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
        this.mState.mStructureChanged = bool3;
        int i12 = 0;
        if (i12 < this.mChildHelper.getChildCount())
        {
          ViewHolder localViewHolder5 = getChildViewHolderInt(this.mChildHelper.getChildAt(i12));
          int i13;
          RecyclerView.ItemAnimator.ItemHolderInfo localItemHolderInfo3;
          if (!localViewHolder5.shouldIgnore())
          {
            ViewInfoStore.InfoRecord localInfoRecord4 = (ViewInfoStore.InfoRecord)this.mViewInfoStore.mLayoutHolderMap.get(localViewHolder5);
            if ((localInfoRecord4 == null) || ((0x4 & localInfoRecord4.flags) == 0)) {
              break label674;
            }
            i13 = 1;
            if (i13 == 0)
            {
              ItemAnimator.buildAdapterChangeFlagsForAnimations(localViewHolder5);
              boolean bool4 = localViewHolder5.hasAnyOfTheFlags(8192);
              localViewHolder5.getUnmodifiedPayloads();
              localItemHolderInfo3 = new RecyclerView.ItemAnimator.ItemHolderInfo().setFrom(localViewHolder5);
              if (!bool4) {
                break label680;
              }
              recordAnimationInfoIfBouncedHiddenView(localViewHolder5, localItemHolderInfo3);
            }
          }
          for (;;)
          {
            i12++;
            break;
            i13 = 0;
            break label614;
            ViewInfoStore localViewInfoStore4 = this.mViewInfoStore;
            ViewInfoStore.InfoRecord localInfoRecord5 = (ViewInfoStore.InfoRecord)localViewInfoStore4.mLayoutHolderMap.get(localViewHolder5);
            if (localInfoRecord5 == null)
            {
              localInfoRecord5 = ViewInfoStore.InfoRecord.obtain();
              localViewInfoStore4.mLayoutHolderMap.put(localViewHolder5, localInfoRecord5);
            }
            localInfoRecord5.flags = (0x2 | localInfoRecord5.flags);
            localInfoRecord5.preInfo = localItemHolderInfo3;
          }
        }
        clearOldPositions();
        this.mAdapterHelper.consumePostponedUpdates();
        this.mState.mItemCount = this.mAdapter.getItemCount();
        this.mState.mDeletedInvisibleItemCountSincePreviousLayout = 0;
        this.mState.mInPreLayout = false;
        this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
        this.mState.mStructureChanged = false;
        this.mPendingSavedState = null;
        State localState2 = this.mState;
        if ((!this.mState.mRunSimpleAnimations) || (this.mItemAnimator == null)) {
          break label1131;
        }
        bool2 = true;
        localState2.mRunSimpleAnimations = bool2;
        if (!this.mState.mRunSimpleAnimations) {
          break label1472;
        }
        int i6 = this.mChildHelper.getChildCount();
        i7 = 0;
        if (i7 >= i6) {
          break label1210;
        }
        localViewHolder3 = getChildViewHolderInt(this.mChildHelper.getChildAt(i7));
        if (!localViewHolder3.shouldIgnore())
        {
          long l1 = getChangedHolderKey(localViewHolder3);
          localItemHolderInfo1 = new RecyclerView.ItemAnimator.ItemHolderInfo().setFrom(localViewHolder3);
          ViewHolder localViewHolder4 = (ViewHolder)this.mViewInfoStore.mOldChangedHolders.get(l1);
          if ((localViewHolder4 == null) || (localViewHolder4.shouldIgnore())) {
            break label1143;
          }
          ViewInfoStore localViewInfoStore3 = this.mViewInfoStore;
          int i9 = localViewInfoStore3.mLayoutHolderMap.indexOfKey(localViewHolder4);
          if (i9 < 0) {
            break label1137;
          }
          ViewInfoStore.InfoRecord localInfoRecord3 = (ViewInfoStore.InfoRecord)localViewInfoStore3.mLayoutHolderMap.valueAt(i9);
          if ((localInfoRecord3 == null) || ((0x4 & localInfoRecord3.flags) == 0)) {
            break label1137;
          }
          localInfoRecord3.flags = (0xFFFFFFFB & localInfoRecord3.flags);
          localItemHolderInfo2 = localInfoRecord3.preInfo;
          if (localInfoRecord3.flags == 0)
          {
            localViewInfoStore3.mLayoutHolderMap.removeAt(i9);
            ViewInfoStore.InfoRecord.recycle(localInfoRecord3);
          }
          label1048:
          localViewHolder4.setIsRecyclable(false);
          if (localViewHolder4 != localViewHolder3)
          {
            localViewHolder4.mShadowedHolder = localViewHolder3;
            addAnimatingView(localViewHolder4);
            this.mRecycler.unscrapView(localViewHolder4);
            localViewHolder3.setIsRecyclable(false);
            localViewHolder3.mShadowingHolder = localViewHolder4;
          }
          if (this.mItemAnimator.animateChange(localViewHolder4, localViewHolder3, localItemHolderInfo2, localItemHolderInfo1)) {
            postAnimationRunner();
          }
        }
      }
      for (;;)
      {
        i7++;
        break label870;
        clearOldPositions();
        break;
        label1131:
        bool2 = false;
        break label841;
        label1137:
        localItemHolderInfo2 = null;
        break label1048;
        label1143:
        ViewInfoStore localViewInfoStore2 = this.mViewInfoStore;
        ViewInfoStore.InfoRecord localInfoRecord2 = (ViewInfoStore.InfoRecord)localViewInfoStore2.mLayoutHolderMap.get(localViewHolder3);
        if (localInfoRecord2 == null)
        {
          localInfoRecord2 = ViewInfoStore.InfoRecord.obtain();
          localViewInfoStore2.mLayoutHolderMap.put(localViewHolder3, localInfoRecord2);
        }
        localInfoRecord2.postInfo = localItemHolderInfo1;
        localInfoRecord2.flags = (0x8 | localInfoRecord2.flags);
      }
      label1210:
      ViewInfoStore localViewInfoStore1 = this.mViewInfoStore;
      ViewInfoStore.ProcessCallback localProcessCallback = this.mViewInfoProcessCallback;
      int i8 = -1 + localViewInfoStore1.mLayoutHolderMap.size();
      if (i8 >= 0)
      {
        ViewHolder localViewHolder2 = (ViewHolder)localViewInfoStore1.mLayoutHolderMap.keyAt(i8);
        ViewInfoStore.InfoRecord localInfoRecord1 = (ViewInfoStore.InfoRecord)localViewInfoStore1.mLayoutHolderMap.removeAt(i8);
        if ((0x3 & localInfoRecord1.flags) == 3) {
          localProcessCallback.unused(localViewHolder2);
        }
        for (;;)
        {
          ViewInfoStore.InfoRecord.recycle(localInfoRecord1);
          i8--;
          break;
          if ((0x1 & localInfoRecord1.flags) != 0) {
            localProcessCallback.processDisappeared(localViewHolder2, localInfoRecord1.preInfo, localInfoRecord1.postInfo);
          } else if ((0xE & localInfoRecord1.flags) == 14) {
            localProcessCallback.processAppeared(localViewHolder2, localInfoRecord1.preInfo, localInfoRecord1.postInfo);
          } else if ((0xC & localInfoRecord1.flags) == 12) {
            localProcessCallback.processPersistent(localViewHolder2, localInfoRecord1.preInfo, localInfoRecord1.postInfo);
          } else if ((0x4 & localInfoRecord1.flags) != 0) {
            localProcessCallback.processDisappeared(localViewHolder2, localInfoRecord1.preInfo, null);
          } else if ((0x8 & localInfoRecord1.flags) != 0) {
            localProcessCallback.processAppeared(localViewHolder2, localInfoRecord1.preInfo, localInfoRecord1.postInfo);
          }
        }
      }
      label1472:
      resumeRequestLayout(false);
      this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
      this.mState.mPreviousLayoutItemCount = this.mState.mItemCount;
      this.mDataSetHasChangedAfterLayout = false;
      this.mState.mRunSimpleAnimations = false;
      this.mState.mRunPredictiveAnimations = false;
      onExitLayoutOrScroll();
      LayoutManager.access$2402$7217d4c(this.mLayout);
      if (this.mRecycler.mChangedScrap != null) {
        this.mRecycler.mChangedScrap.clear();
      }
      this.mViewInfoStore.clear();
      int n = this.mMinMaxLayoutPositions[0];
      int i1 = this.mMinMaxLayoutPositions[1];
      int i2 = this.mChildHelper.getChildCount();
      int i4;
      if (i2 == 0) {
        if ((n != 0) || (i1 != 0)) {
          i4 = 1;
        }
      }
      while (i4 != 0)
      {
        dispatchOnScrolled(0, 0);
        return;
        i4 = 0;
        continue;
        for (int i3 = 0;; i3++)
        {
          if (i3 >= i2) {
            break label1688;
          }
          ViewHolder localViewHolder1 = getChildViewHolderInt(this.mChildHelper.getChildAt(i3));
          if (!localViewHolder1.shouldIgnore())
          {
            int i5 = localViewHolder1.getLayoutPosition();
            if ((i5 < n) || (i5 > i1))
            {
              i4 = 1;
              break;
            }
          }
        }
        label1688:
        i4 = 0;
      }
      break;
      label1694:
      i16 = k;
    }
  }
  
  private long getChangedHolderKey(ViewHolder paramViewHolder)
  {
    if (this.mAdapter.mHasStableIds) {
      return paramViewHolder.mItemId;
    }
    return paramViewHolder.mPosition;
  }
  
  public static int getChildAdapterPosition(View paramView)
  {
    ViewHolder localViewHolder = getChildViewHolderInt(paramView);
    if (localViewHolder != null) {
      return localViewHolder.getAdapterPosition();
    }
    return -1;
  }
  
  public static int getChildLayoutPosition(View paramView)
  {
    ViewHolder localViewHolder = getChildViewHolderInt(paramView);
    if (localViewHolder != null) {
      return localViewHolder.getLayoutPosition();
    }
    return -1;
  }
  
  static ViewHolder getChildViewHolderInt(View paramView)
  {
    if (paramView == null) {
      return null;
    }
    return ((LayoutParams)paramView.getLayoutParams()).mViewHolder;
  }
  
  private float getScrollFactor()
  {
    if (this.mScrollFactor == 1.4E-45F)
    {
      TypedValue localTypedValue = new TypedValue();
      if (getContext().getTheme().resolveAttribute(16842829, localTypedValue, true)) {
        this.mScrollFactor = localTypedValue.getDimension(getContext().getResources().getDisplayMetrics());
      }
    }
    else
    {
      return this.mScrollFactor;
    }
    return 0.0F;
  }
  
  private void invalidateGlows()
  {
    this.mBottomGlow = null;
    this.mTopGlow = null;
    this.mRightGlow = null;
    this.mLeftGlow = null;
  }
  
  private boolean isComputingLayout()
  {
    return this.mLayoutOrScrollCounter > 0;
  }
  
  private void markItemDecorInsetsDirty()
  {
    int i = this.mChildHelper.getUnfilteredChildCount();
    for (int j = 0; j < i; j++) {
      ((LayoutParams)this.mChildHelper.getUnfilteredChildAt(j).getLayoutParams()).mInsetsDirty = true;
    }
    Recycler localRecycler = this.mRecycler;
    int k = localRecycler.mCachedViews.size();
    for (int m = 0; m < k; m++)
    {
      LayoutParams localLayoutParams = (LayoutParams)((ViewHolder)localRecycler.mCachedViews.get(m)).itemView.getLayoutParams();
      if (localLayoutParams != null) {
        localLayoutParams.mInsetsDirty = true;
      }
    }
  }
  
  private void markKnownViewsInvalid()
  {
    int i = this.mChildHelper.getUnfilteredChildCount();
    for (int j = 0; j < i; j++)
    {
      ViewHolder localViewHolder2 = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(j));
      if ((localViewHolder2 != null) && (!localViewHolder2.shouldIgnore())) {
        localViewHolder2.addFlags(6);
      }
    }
    markItemDecorInsetsDirty();
    Recycler localRecycler = this.mRecycler;
    int k;
    int m;
    if ((localRecycler.this$0.mAdapter != null) && (localRecycler.this$0.mAdapter.mHasStableIds))
    {
      k = localRecycler.mCachedViews.size();
      m = 0;
    }
    while (m < k)
    {
      ViewHolder localViewHolder1 = (ViewHolder)localRecycler.mCachedViews.get(m);
      if (localViewHolder1 != null)
      {
        localViewHolder1.addFlags(6);
        localViewHolder1.addChangePayload(null);
      }
      m++;
      continue;
      localRecycler.recycleAndClearCachedViews();
    }
  }
  
  private void onEnterLayoutOrScroll()
  {
    this.mLayoutOrScrollCounter = (1 + this.mLayoutOrScrollCounter);
  }
  
  private void onExitLayoutOrScroll()
  {
    this.mLayoutOrScrollCounter = (-1 + this.mLayoutOrScrollCounter);
    if (this.mLayoutOrScrollCounter <= 0)
    {
      this.mLayoutOrScrollCounter = 0;
      int i = this.mEatenAccessibilityChangeFlags;
      this.mEatenAccessibilityChangeFlags = 0;
      if ((i != 0) && (isAccessibilityEnabled()))
      {
        AccessibilityEvent localAccessibilityEvent = AccessibilityEvent.obtain();
        localAccessibilityEvent.setEventType(2048);
        AccessibilityEventCompat.setContentChangeTypes(localAccessibilityEvent, i);
        sendAccessibilityEventUnchecked(localAccessibilityEvent);
      }
    }
  }
  
  private void onPointerUp(MotionEvent paramMotionEvent)
  {
    int i = MotionEventCompat.getActionIndex(paramMotionEvent);
    if (MotionEventCompat.getPointerId(paramMotionEvent, i) == this.mScrollPointerId) {
      if (i != 0) {
        break label81;
      }
    }
    label81:
    for (int j = 1;; j = 0)
    {
      this.mScrollPointerId = MotionEventCompat.getPointerId(paramMotionEvent, j);
      int k = (int)(0.5F + MotionEventCompat.getX(paramMotionEvent, j));
      this.mLastTouchX = k;
      this.mInitialTouchX = k;
      int m = (int)(0.5F + MotionEventCompat.getY(paramMotionEvent, j));
      this.mLastTouchY = m;
      this.mInitialTouchY = m;
      return;
    }
  }
  
  private void postAnimationRunner()
  {
    if ((!this.mPostedAnimatorRunner) && (this.mIsAttached))
    {
      ViewCompat.postOnAnimation(this, this.mItemAnimatorRunner);
      this.mPostedAnimatorRunner = true;
    }
  }
  
  private void processAdapterUpdatesAndSetAnimationFlags()
  {
    boolean bool1 = true;
    if (this.mDataSetHasChangedAfterLayout)
    {
      this.mAdapterHelper.reset();
      markKnownViewsInvalid();
      this.mLayout.onItemsChanged$57043c5d();
    }
    int i;
    label67:
    boolean bool2;
    label127:
    State localState2;
    int j;
    if ((this.mItemAnimator != null) && (this.mLayout.supportsPredictiveItemAnimations()))
    {
      this.mAdapterHelper.preProcess();
      if ((!this.mItemsAddedOrRemoved) && (!this.mItemsChanged)) {
        break label202;
      }
      i = bool1;
      State localState1 = this.mState;
      if ((!this.mFirstLayoutComplete) || (this.mItemAnimator == null) || ((!this.mDataSetHasChangedAfterLayout) && (i == 0) && (!this.mLayout.mRequestedSimpleAnimations)) || ((this.mDataSetHasChangedAfterLayout) && (!this.mAdapter.mHasStableIds))) {
        break label207;
      }
      bool2 = bool1;
      localState1.mRunSimpleAnimations = bool2;
      localState2 = this.mState;
      if ((!this.mState.mRunSimpleAnimations) || (i == 0) || (this.mDataSetHasChangedAfterLayout)) {
        break label219;
      }
      if ((this.mItemAnimator == null) || (!this.mLayout.supportsPredictiveItemAnimations())) {
        break label213;
      }
      j = bool1;
      label180:
      if (j == 0) {
        break label219;
      }
    }
    for (;;)
    {
      localState2.mRunPredictiveAnimations = bool1;
      return;
      this.mAdapterHelper.consumeUpdatesInOnePass();
      break;
      label202:
      i = 0;
      break label67;
      label207:
      bool2 = false;
      break label127;
      label213:
      j = 0;
      break label180;
      label219:
      bool1 = false;
    }
  }
  
  private void recordAnimationInfoIfBouncedHiddenView(ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo)
  {
    paramViewHolder.setFlags(0, 8192);
    if ((this.mState.mTrackOldChangeHolders) && (paramViewHolder.isUpdated()) && (!paramViewHolder.isRemoved()) && (!paramViewHolder.shouldIgnore()))
    {
      long l = getChangedHolderKey(paramViewHolder);
      this.mViewInfoStore.addToOldChangeHolders(l, paramViewHolder);
    }
    this.mViewInfoStore.addToPreLayout(paramViewHolder, paramItemHolderInfo);
  }
  
  private void repositionShadowingViews()
  {
    int i = this.mChildHelper.getChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView1 = this.mChildHelper.getChildAt(j);
      ViewHolder localViewHolder = getChildViewHolder(localView1);
      if ((localViewHolder != null) && (localViewHolder.mShadowingHolder != null))
      {
        View localView2 = localViewHolder.mShadowingHolder.itemView;
        int k = localView1.getLeft();
        int m = localView1.getTop();
        if ((k != localView2.getLeft()) || (m != localView2.getTop())) {
          localView2.layout(k, m, k + localView2.getWidth(), m + localView2.getHeight());
        }
      }
    }
  }
  
  private void resetTouch()
  {
    if (this.mVelocityTracker != null) {
      this.mVelocityTracker.clear();
    }
    stopNestedScroll();
    EdgeEffectCompat localEdgeEffectCompat = this.mLeftGlow;
    boolean bool = false;
    if (localEdgeEffectCompat != null) {
      bool = this.mLeftGlow.onRelease();
    }
    if (this.mTopGlow != null) {
      bool |= this.mTopGlow.onRelease();
    }
    if (this.mRightGlow != null) {
      bool |= this.mRightGlow.onRelease();
    }
    if (this.mBottomGlow != null) {
      bool |= this.mBottomGlow.onRelease();
    }
    if (bool) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  private boolean scrollByInternal(int paramInt1, int paramInt2, MotionEvent paramMotionEvent)
  {
    consumePendingUpdateOperations();
    Adapter localAdapter = this.mAdapter;
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    if (localAdapter != null)
    {
      eatRequestLayout();
      onEnterLayoutOrScroll();
      TraceCompat.beginSection("RV Scroll");
      i = 0;
      k = 0;
      if (paramInt1 != 0)
      {
        i = this.mLayout.scrollHorizontallyBy(paramInt1, this.mRecycler, this.mState);
        k = paramInt1 - i;
      }
      j = 0;
      m = 0;
      if (paramInt2 != 0)
      {
        j = this.mLayout.scrollVerticallyBy(paramInt2, this.mRecycler, this.mState);
        m = paramInt2 - j;
      }
      TraceCompat.endSection();
      repositionShadowingViews();
      onExitLayoutOrScroll();
      resumeRequestLayout(false);
    }
    if (!this.mItemDecorations.isEmpty()) {
      invalidate();
    }
    if (dispatchNestedScroll(i, j, k, m, this.mScrollOffset))
    {
      this.mLastTouchX -= this.mScrollOffset[0];
      this.mLastTouchY -= this.mScrollOffset[1];
      if (paramMotionEvent != null) {
        paramMotionEvent.offsetLocation(this.mScrollOffset[0], this.mScrollOffset[1]);
      }
      arrayOfInt1 = this.mNestedOffsets;
      arrayOfInt1[0] += this.mScrollOffset[0];
      arrayOfInt2 = this.mNestedOffsets;
      arrayOfInt2[1] += this.mScrollOffset[1];
    }
    while (ViewCompat.getOverScrollMode(this) == 2)
    {
      int[] arrayOfInt1;
      int[] arrayOfInt2;
      if ((i != 0) || (j != 0)) {
        dispatchOnScrolled(i, j);
      }
      if (!awakenScrollBars()) {
        invalidate();
      }
      if ((i == 0) && (j == 0)) {
        break;
      }
      return true;
    }
    float f1;
    float f2;
    float f3;
    float f4;
    int n;
    if (paramMotionEvent != null)
    {
      f1 = paramMotionEvent.getX();
      f2 = k;
      f3 = paramMotionEvent.getY();
      f4 = m;
      if (f2 >= 0.0F) {
        break label450;
      }
      ensureLeftGlow();
      boolean bool3 = this.mLeftGlow.onPull(-f2 / getWidth(), 1.0F - f3 / getHeight());
      n = 0;
      if (bool3) {
        n = 1;
      }
      label377:
      if (f4 >= 0.0F) {
        break label507;
      }
      ensureTopGlow();
      if (this.mTopGlow.onPull(-f4 / getHeight(), f1 / getWidth())) {
        n = 1;
      }
    }
    for (;;)
    {
      if ((n != 0) || (f2 != 0.0F) || (f4 != 0.0F)) {
        ViewCompat.postInvalidateOnAnimation(this);
      }
      considerReleasingGlowsOnScroll(paramInt1, paramInt2);
      break;
      label450:
      boolean bool1 = f2 < 0.0F;
      n = 0;
      if (!bool1) {
        break label377;
      }
      ensureRightGlow();
      boolean bool2 = this.mRightGlow.onPull(f2 / getWidth(), f3 / getHeight());
      n = 0;
      if (!bool2) {
        break label377;
      }
      n = 1;
      break label377;
      label507:
      if (f4 > 0.0F)
      {
        ensureBottomGlow();
        if (this.mBottomGlow.onPull(f4 / getHeight(), 1.0F - f1 / getWidth())) {
          n = 1;
        }
      }
    }
    return false;
  }
  
  private void setScrollState(int paramInt)
  {
    if (paramInt == this.mScrollState) {}
    for (;;)
    {
      return;
      this.mScrollState = paramInt;
      if (paramInt != 2) {
        stopScrollersInternal();
      }
      if (this.mScrollListener != null) {
        this.mScrollListener.onScrollStateChanged(this, paramInt);
      }
      if (this.mScrollListeners != null) {
        for (int i = -1 + this.mScrollListeners.size(); i >= 0; i--) {
          ((OnScrollListener)this.mScrollListeners.get(i)).onScrollStateChanged(this, paramInt);
        }
      }
    }
  }
  
  private void stopScrollersInternal()
  {
    ViewFlinger localViewFlinger = this.mViewFlinger;
    localViewFlinger.this$0.removeCallbacks(localViewFlinger);
    localViewFlinger.mScroller.abortAnimation();
    if (this.mLayout != null) {
      this.mLayout.stopSmoothScroller();
    }
  }
  
  public void addFocusables(ArrayList<View> paramArrayList, int paramInt1, int paramInt2)
  {
    super.addFocusables(paramArrayList, paramInt1, paramInt2);
  }
  
  public final void addItemDecoration(ItemDecoration paramItemDecoration)
  {
    if (this.mLayout != null) {
      this.mLayout.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
    }
    if (this.mItemDecorations.isEmpty()) {
      setWillNotDraw(false);
    }
    this.mItemDecorations.add(paramItemDecoration);
    markItemDecorInsetsDirty();
    requestLayout();
  }
  
  public final void addOnScrollListener(OnScrollListener paramOnScrollListener)
  {
    if (this.mScrollListeners == null) {
      this.mScrollListeners = new ArrayList();
    }
    this.mScrollListeners.add(paramOnScrollListener);
  }
  
  final void assertNotInLayoutOrScroll(String paramString)
  {
    if (isComputingLayout())
    {
      if (paramString == null) {
        throw new IllegalStateException("Cannot call this method while RecyclerView is computing a layout or scrolling");
      }
      throw new IllegalStateException(paramString);
    }
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return ((paramLayoutParams instanceof LayoutParams)) && (this.mLayout.checkLayoutParams((LayoutParams)paramLayoutParams));
  }
  
  public int computeHorizontalScrollExtent()
  {
    if (this.mLayout.canScrollHorizontally()) {
      return this.mLayout.computeHorizontalScrollExtent(this.mState);
    }
    return 0;
  }
  
  public int computeHorizontalScrollOffset()
  {
    if (this.mLayout.canScrollHorizontally()) {
      return this.mLayout.computeHorizontalScrollOffset(this.mState);
    }
    return 0;
  }
  
  public int computeHorizontalScrollRange()
  {
    if (this.mLayout.canScrollHorizontally()) {
      return this.mLayout.computeHorizontalScrollRange(this.mState);
    }
    return 0;
  }
  
  public int computeVerticalScrollExtent()
  {
    if (this.mLayout.canScrollVertically()) {
      return this.mLayout.computeVerticalScrollExtent(this.mState);
    }
    return 0;
  }
  
  public int computeVerticalScrollOffset()
  {
    if (this.mLayout.canScrollVertically()) {
      return this.mLayout.computeVerticalScrollOffset(this.mState);
    }
    return 0;
  }
  
  public int computeVerticalScrollRange()
  {
    if (this.mLayout.canScrollVertically()) {
      return this.mLayout.computeVerticalScrollRange(this.mState);
    }
    return 0;
  }
  
  public boolean dispatchNestedFling(float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    return this.mScrollingChildHelper.dispatchNestedFling(paramFloat1, paramFloat2, paramBoolean);
  }
  
  public boolean dispatchNestedPreFling(float paramFloat1, float paramFloat2)
  {
    return this.mScrollingChildHelper.dispatchNestedPreFling(paramFloat1, paramFloat2);
  }
  
  public boolean dispatchNestedPreScroll(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return this.mScrollingChildHelper.dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2);
  }
  
  public boolean dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt)
  {
    return this.mScrollingChildHelper.dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt);
  }
  
  final void dispatchOnScrolled(int paramInt1, int paramInt2)
  {
    int i = getScrollX();
    int j = getScrollY();
    onScrollChanged(i, j, i, j);
    if (this.mScrollListener != null) {
      this.mScrollListener.onScrolled(this, paramInt1, paramInt2);
    }
    if (this.mScrollListeners != null) {
      for (int k = -1 + this.mScrollListeners.size(); k >= 0; k--) {
        ((OnScrollListener)this.mScrollListeners.get(k)).onScrolled(this, paramInt1, paramInt2);
      }
    }
  }
  
  protected void dispatchRestoreInstanceState(SparseArray<Parcelable> paramSparseArray)
  {
    dispatchThawSelfOnly(paramSparseArray);
  }
  
  protected void dispatchSaveInstanceState(SparseArray<Parcelable> paramSparseArray)
  {
    dispatchFreezeSelfOnly(paramSparseArray);
  }
  
  public void draw(Canvas paramCanvas)
  {
    int i = 1;
    super.draw(paramCanvas);
    int j = this.mItemDecorations.size();
    for (int k = 0; k < j; k++) {
      ((ItemDecoration)this.mItemDecorations.get(k)).onDrawOver$13fcd2ff(paramCanvas, this);
    }
    EdgeEffectCompat localEdgeEffectCompat = this.mLeftGlow;
    int m = 0;
    int i8;
    if (localEdgeEffectCompat != null)
    {
      boolean bool = this.mLeftGlow.isFinished();
      m = 0;
      if (!bool)
      {
        int i7 = paramCanvas.save();
        if (!this.mClipToPadding) {
          break label456;
        }
        i8 = getPaddingBottom();
        paramCanvas.rotate(270.0F);
        paramCanvas.translate(i8 + -getHeight(), 0.0F);
        if ((this.mLeftGlow == null) || (!this.mLeftGlow.draw(paramCanvas))) {
          break label462;
        }
        m = i;
        label139:
        paramCanvas.restoreToCount(i7);
      }
    }
    int i6;
    label210:
    int i3;
    label265:
    int i4;
    label305:
    int n;
    if ((this.mTopGlow != null) && (!this.mTopGlow.isFinished()))
    {
      int i5 = paramCanvas.save();
      if (this.mClipToPadding) {
        paramCanvas.translate(getPaddingLeft(), getPaddingTop());
      }
      if ((this.mTopGlow != null) && (this.mTopGlow.draw(paramCanvas)))
      {
        i6 = i;
        m |= i6;
        paramCanvas.restoreToCount(i5);
      }
    }
    else
    {
      if ((this.mRightGlow != null) && (!this.mRightGlow.isFinished()))
      {
        int i1 = paramCanvas.save();
        int i2 = getWidth();
        if (!this.mClipToPadding) {
          break label474;
        }
        i3 = getPaddingTop();
        paramCanvas.rotate(90.0F);
        paramCanvas.translate(-i3, -i2);
        if ((this.mRightGlow == null) || (!this.mRightGlow.draw(paramCanvas))) {
          break label480;
        }
        i4 = i;
        m |= i4;
        paramCanvas.restoreToCount(i1);
      }
      if ((this.mBottomGlow != null) && (!this.mBottomGlow.isFinished()))
      {
        n = paramCanvas.save();
        paramCanvas.rotate(180.0F);
        if (!this.mClipToPadding) {
          break label486;
        }
        paramCanvas.translate(-getWidth() + getPaddingRight(), -getHeight() + getPaddingBottom());
        label381:
        if ((this.mBottomGlow == null) || (!this.mBottomGlow.draw(paramCanvas))) {
          break label505;
        }
      }
    }
    for (;;)
    {
      m |= i;
      paramCanvas.restoreToCount(n);
      if ((m == 0) && (this.mItemAnimator != null) && (this.mItemDecorations.size() > 0) && (this.mItemAnimator.isRunning())) {
        m = 1;
      }
      if (m != 0) {
        ViewCompat.postInvalidateOnAnimation(this);
      }
      return;
      label456:
      i8 = 0;
      break;
      label462:
      m = 0;
      break label139;
      i6 = 0;
      break label210;
      label474:
      i3 = 0;
      break label265;
      label480:
      i4 = 0;
      break label305;
      label486:
      paramCanvas.translate(-getWidth(), -getHeight());
      break label381;
      label505:
      i = 0;
    }
  }
  
  public boolean drawChild(Canvas paramCanvas, View paramView, long paramLong)
  {
    return super.drawChild(paramCanvas, paramView, paramLong);
  }
  
  final void eatRequestLayout()
  {
    if (!this.mEatRequestLayout)
    {
      this.mEatRequestLayout = true;
      if (!this.mLayoutFrozen) {
        this.mLayoutRequestEaten = false;
      }
    }
  }
  
  final void ensureBottomGlow()
  {
    if (this.mBottomGlow != null) {
      return;
    }
    this.mBottomGlow = new EdgeEffectCompat(getContext());
    if (this.mClipToPadding)
    {
      this.mBottomGlow.setSize(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight() - getPaddingTop() - getPaddingBottom());
      return;
    }
    this.mBottomGlow.setSize(getMeasuredWidth(), getMeasuredHeight());
  }
  
  final void ensureLeftGlow()
  {
    if (this.mLeftGlow != null) {
      return;
    }
    this.mLeftGlow = new EdgeEffectCompat(getContext());
    if (this.mClipToPadding)
    {
      this.mLeftGlow.setSize(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
      return;
    }
    this.mLeftGlow.setSize(getMeasuredHeight(), getMeasuredWidth());
  }
  
  final void ensureRightGlow()
  {
    if (this.mRightGlow != null) {
      return;
    }
    this.mRightGlow = new EdgeEffectCompat(getContext());
    if (this.mClipToPadding)
    {
      this.mRightGlow.setSize(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
      return;
    }
    this.mRightGlow.setSize(getMeasuredHeight(), getMeasuredWidth());
  }
  
  final void ensureTopGlow()
  {
    if (this.mTopGlow != null) {
      return;
    }
    this.mTopGlow = new EdgeEffectCompat(getContext());
    if (this.mClipToPadding)
    {
      this.mTopGlow.setSize(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight() - getPaddingTop() - getPaddingBottom());
      return;
    }
    this.mTopGlow.setSize(getMeasuredWidth(), getMeasuredHeight());
  }
  
  public boolean fling(int paramInt1, int paramInt2)
  {
    if (this.mLayout == null) {
      Log.e("RecyclerView", "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
    }
    boolean bool1;
    boolean bool2;
    do
    {
      do
      {
        return false;
      } while (this.mLayoutFrozen);
      bool1 = this.mLayout.canScrollHorizontally();
      bool2 = this.mLayout.canScrollVertically();
      if ((!bool1) || (Math.abs(paramInt1) < this.mMinFlingVelocity)) {
        paramInt1 = 0;
      }
      if ((!bool2) || (Math.abs(paramInt2) < this.mMinFlingVelocity)) {
        paramInt2 = 0;
      }
    } while (((paramInt1 == 0) && (paramInt2 == 0)) || (dispatchNestedPreFling(paramInt1, paramInt2)));
    if ((bool1) || (bool2)) {}
    for (boolean bool3 = true;; bool3 = false)
    {
      dispatchNestedFling(paramInt1, paramInt2, bool3);
      if (!bool3) {
        break;
      }
      int i = Math.max(-this.mMaxFlingVelocity, Math.min(paramInt1, this.mMaxFlingVelocity));
      int j = Math.max(-this.mMaxFlingVelocity, Math.min(paramInt2, this.mMaxFlingVelocity));
      ViewFlinger localViewFlinger = this.mViewFlinger;
      localViewFlinger.this$0.setScrollState(2);
      localViewFlinger.mLastFlingY = 0;
      localViewFlinger.mLastFlingX = 0;
      localViewFlinger.mScroller.fling$69c647f5(i, j, -2147483648, 2147483647);
      localViewFlinger.postOnAnimation();
      return true;
    }
  }
  
  public View focusSearch(View paramView, int paramInt)
  {
    View localView1 = this.mLayout.onInterceptFocusSearch(paramView, paramInt);
    if (localView1 != null) {
      return localView1;
    }
    View localView2 = FocusFinder.getInstance().findNextFocus(this, paramView, paramInt);
    if ((localView2 == null) && (this.mAdapter != null) && (this.mLayout != null) && (!isComputingLayout()) && (!this.mLayoutFrozen))
    {
      eatRequestLayout();
      localView2 = this.mLayout.onFocusSearchFailed$1539f5dc(paramInt, this.mRecycler, this.mState);
      resumeRequestLayout(false);
    }
    if (localView2 != null) {
      return localView2;
    }
    return super.focusSearch(paramView, paramInt);
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams()
  {
    if (this.mLayout == null) {
      throw new IllegalStateException("RecyclerView has no LayoutManager");
    }
    return this.mLayout.generateDefaultLayoutParams();
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    if (this.mLayout == null) {
      throw new IllegalStateException("RecyclerView has no LayoutManager");
    }
    return this.mLayout.generateLayoutParams(getContext(), paramAttributeSet);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if (this.mLayout == null) {
      throw new IllegalStateException("RecyclerView has no LayoutManager");
    }
    return this.mLayout.generateLayoutParams(paramLayoutParams);
  }
  
  public Adapter getAdapter()
  {
    return this.mAdapter;
  }
  
  public int getBaseline()
  {
    if (this.mLayout != null) {
      return -1;
    }
    return super.getBaseline();
  }
  
  protected int getChildDrawingOrder(int paramInt1, int paramInt2)
  {
    if (this.mChildDrawingOrderCallback == null) {
      return super.getChildDrawingOrder(paramInt1, paramInt2);
    }
    return this.mChildDrawingOrderCallback.onGetChildDrawingOrder$255f288();
  }
  
  public final ViewHolder getChildViewHolder(View paramView)
  {
    ViewParent localViewParent = paramView.getParent();
    if ((localViewParent != null) && (localViewParent != this)) {
      throw new IllegalArgumentException("View " + paramView + " is not a direct child of " + this);
    }
    return getChildViewHolderInt(paramView);
  }
  
  public RecyclerViewAccessibilityDelegate getCompatAccessibilityDelegate()
  {
    return this.mAccessibilityDelegate;
  }
  
  public ItemAnimator getItemAnimator()
  {
    return this.mItemAnimator;
  }
  
  final Rect getItemDecorInsetsForChild(View paramView)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    if (!localLayoutParams.mInsetsDirty) {
      return localLayoutParams.mDecorInsets;
    }
    Rect localRect = localLayoutParams.mDecorInsets;
    localRect.set(0, 0, 0, 0);
    int i = this.mItemDecorations.size();
    for (int j = 0; j < i; j++)
    {
      this.mTempRect.set(0, 0, 0, 0);
      ((ItemDecoration)this.mItemDecorations.get(j)).getItemOffsets$5c1923bd$3450f9fc(this.mTempRect, paramView);
      localRect.left += this.mTempRect.left;
      localRect.top += this.mTempRect.top;
      localRect.right += this.mTempRect.right;
      localRect.bottom += this.mTempRect.bottom;
    }
    localLayoutParams.mInsetsDirty = false;
    return localRect;
  }
  
  public LayoutManager getLayoutManager()
  {
    return this.mLayout;
  }
  
  public int getMaxFlingVelocity()
  {
    return this.mMaxFlingVelocity;
  }
  
  public int getMinFlingVelocity()
  {
    return this.mMinFlingVelocity;
  }
  
  public RecycledViewPool getRecycledViewPool()
  {
    return this.mRecycler.getRecycledViewPool();
  }
  
  public int getScrollState()
  {
    return this.mScrollState;
  }
  
  public boolean hasNestedScrollingParent()
  {
    return this.mScrollingChildHelper.hasNestedScrollingParent();
  }
  
  final boolean isAccessibilityEnabled()
  {
    return (this.mAccessibilityManager != null) && (this.mAccessibilityManager.isEnabled());
  }
  
  public boolean isAttachedToWindow()
  {
    return this.mIsAttached;
  }
  
  public boolean isNestedScrollingEnabled()
  {
    return this.mScrollingChildHelper.mIsNestedScrollingEnabled;
  }
  
  final void offsetPositionRecordsForRemove(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i = paramInt1 + paramInt2;
    int j = this.mChildHelper.getUnfilteredChildCount();
    int k = 0;
    if (k < j)
    {
      ViewHolder localViewHolder2 = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(k));
      if ((localViewHolder2 != null) && (!localViewHolder2.shouldIgnore()))
      {
        if (localViewHolder2.mPosition < i) {
          break label83;
        }
        localViewHolder2.offsetPosition(-paramInt2, paramBoolean);
        this.mState.mStructureChanged = true;
      }
      for (;;)
      {
        k++;
        break;
        label83:
        if (localViewHolder2.mPosition >= paramInt1)
        {
          int i1 = paramInt1 - 1;
          int i2 = -paramInt2;
          localViewHolder2.addFlags(8);
          localViewHolder2.offsetPosition(i2, paramBoolean);
          localViewHolder2.mPosition = i1;
          this.mState.mStructureChanged = true;
        }
      }
    }
    Recycler localRecycler = this.mRecycler;
    int m = paramInt1 + paramInt2;
    int n = -1 + localRecycler.mCachedViews.size();
    if (n >= 0)
    {
      ViewHolder localViewHolder1 = (ViewHolder)localRecycler.mCachedViews.get(n);
      if (localViewHolder1 != null)
      {
        if (localViewHolder1.getLayoutPosition() < m) {
          break label206;
        }
        localViewHolder1.offsetPosition(-paramInt2, paramBoolean);
      }
      for (;;)
      {
        n--;
        break;
        label206:
        if (localViewHolder1.getLayoutPosition() >= paramInt1)
        {
          localViewHolder1.addFlags(8);
          localRecycler.recycleCachedViewAt(n);
        }
      }
    }
    requestLayout();
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mLayoutOrScrollCounter = 0;
    this.mIsAttached = true;
    this.mFirstLayoutComplete = false;
    if (this.mLayout != null) {
      this.mLayout.mIsAttachedToWindow = true;
    }
    this.mPostedAnimatorRunner = false;
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (this.mItemAnimator != null) {
      this.mItemAnimator.endAnimations();
    }
    this.mFirstLayoutComplete = false;
    stopScroll();
    this.mIsAttached = false;
    if (this.mLayout != null) {
      this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
    }
    removeCallbacks(this.mItemAnimatorRunner);
    ViewInfoStore.InfoRecord.drainCache();
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = this.mItemDecorations.size();
    for (int j = 0; j < i; j++) {
      ((ItemDecoration)this.mItemDecorations.get(j)).onDraw$13fcd2ff(paramCanvas, this);
    }
  }
  
  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent)
  {
    if (this.mLayout == null) {}
    label110:
    label113:
    for (;;)
    {
      return false;
      if ((!this.mLayoutFrozen) && ((0x2 & MotionEventCompat.getSource(paramMotionEvent)) != 0) && (paramMotionEvent.getAction() == 8))
      {
        float f1;
        if (this.mLayout.canScrollVertically())
        {
          f1 = -MotionEventCompat.getAxisValue(paramMotionEvent, 9);
          if (!this.mLayout.canScrollHorizontally()) {
            break label110;
          }
        }
        for (float f2 = MotionEventCompat.getAxisValue(paramMotionEvent, 10);; f2 = 0.0F)
        {
          if ((f1 == 0.0F) && (f2 == 0.0F)) {
            break label113;
          }
          float f3 = getScrollFactor();
          scrollByInternal((int)(f2 * f3), (int)(f1 * f3), paramMotionEvent);
          return false;
          f1 = 0.0F;
          break;
        }
      }
    }
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.mLayoutFrozen) {
      return false;
    }
    int i = paramMotionEvent.getAction();
    if ((i == 3) || (i == 0)) {
      this.mActiveOnItemTouchListener = null;
    }
    int j = this.mOnItemTouchListeners.size();
    int k = 0;
    if (k < j)
    {
      OnItemTouchListener localOnItemTouchListener = (OnItemTouchListener)this.mOnItemTouchListeners.get(k);
      if ((localOnItemTouchListener.onInterceptTouchEvent$606727fc()) && (i != 3)) {
        this.mActiveOnItemTouchListener = localOnItemTouchListener;
      }
    }
    for (int m = 1;; m = 0)
    {
      if (m == 0) {
        break label106;
      }
      cancelTouch();
      return true;
      k++;
      break;
    }
    label106:
    if (this.mLayout == null) {
      return false;
    }
    boolean bool1 = this.mLayout.canScrollHorizontally();
    boolean bool2 = this.mLayout.canScrollVertically();
    if (this.mVelocityTracker == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    }
    this.mVelocityTracker.addMovement(paramMotionEvent);
    int n = MotionEventCompat.getActionMasked(paramMotionEvent);
    int i1 = MotionEventCompat.getActionIndex(paramMotionEvent);
    switch (n)
    {
    }
    while (this.mScrollState == 1)
    {
      return true;
      if (this.mIgnoreMotionEventTillDown) {
        this.mIgnoreMotionEventTillDown = false;
      }
      this.mScrollPointerId = MotionEventCompat.getPointerId(paramMotionEvent, 0);
      int i18 = (int)(0.5F + paramMotionEvent.getX());
      this.mLastTouchX = i18;
      this.mInitialTouchX = i18;
      int i19 = (int)(0.5F + paramMotionEvent.getY());
      this.mLastTouchY = i19;
      this.mInitialTouchY = i19;
      if (this.mScrollState == 2)
      {
        getParent().requestDisallowInterceptTouchEvent(true);
        setScrollState(1);
      }
      int[] arrayOfInt = this.mNestedOffsets;
      this.mNestedOffsets[1] = 0;
      arrayOfInt[0] = 0;
      int i20 = 0;
      if (bool1) {
        i20 = 1;
      }
      if (bool2) {
        i20 |= 0x2;
      }
      startNestedScroll(i20);
      continue;
      this.mScrollPointerId = MotionEventCompat.getPointerId(paramMotionEvent, i1);
      int i16 = (int)(0.5F + MotionEventCompat.getX(paramMotionEvent, i1));
      this.mLastTouchX = i16;
      this.mInitialTouchX = i16;
      int i17 = (int)(0.5F + MotionEventCompat.getY(paramMotionEvent, i1));
      this.mLastTouchY = i17;
      this.mInitialTouchY = i17;
      continue;
      int i2 = MotionEventCompat.findPointerIndex(paramMotionEvent, this.mScrollPointerId);
      if (i2 < 0)
      {
        Log.e("RecyclerView", "Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?");
        return false;
      }
      int i3 = (int)(0.5F + MotionEventCompat.getX(paramMotionEvent, i2));
      int i4 = (int)(0.5F + MotionEventCompat.getY(paramMotionEvent, i2));
      if (this.mScrollState != 1)
      {
        int i5 = i3 - this.mInitialTouchX;
        int i6 = i4 - this.mInitialTouchY;
        int i7 = 0;
        int i15;
        if (bool1)
        {
          int i11 = Math.abs(i5);
          int i12 = this.mTouchSlop;
          i7 = 0;
          if (i11 > i12)
          {
            int i13 = this.mInitialTouchX;
            int i14 = this.mTouchSlop;
            if (i5 >= 0) {
              break label658;
            }
            i15 = -1;
            label578:
            this.mLastTouchX = (i13 + i15 * i14);
            i7 = 1;
          }
        }
        int i8;
        int i9;
        if ((bool2) && (Math.abs(i6) > this.mTouchSlop))
        {
          i8 = this.mInitialTouchY;
          i9 = this.mTouchSlop;
          if (i6 >= 0) {
            break label664;
          }
        }
        label658:
        label664:
        for (int i10 = -1;; i10 = 1)
        {
          this.mLastTouchY = (i8 + i10 * i9);
          i7 = 1;
          if (i7 == 0) {
            break;
          }
          setScrollState(1);
          break;
          i15 = 1;
          break label578;
        }
        onPointerUp(paramMotionEvent);
        continue;
        this.mVelocityTracker.clear();
        stopNestedScroll();
        continue;
        cancelTouch();
      }
    }
    return false;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    eatRequestLayout();
    TraceCompat.beginSection("RV OnLayout");
    dispatchLayout();
    TraceCompat.endSection();
    resumeRequestLayout(false);
    this.mFirstLayoutComplete = true;
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mAdapterUpdateDuringMeasure)
    {
      eatRequestLayout();
      processAdapterUpdatesAndSetAnimationFlags();
      if (this.mState.mRunPredictiveAnimations)
      {
        this.mState.mInPreLayout = true;
        this.mAdapterUpdateDuringMeasure = false;
        resumeRequestLayout(false);
      }
    }
    else
    {
      if (this.mAdapter == null) {
        break label104;
      }
      this.mState.mItemCount = this.mAdapter.getItemCount();
      label64:
      if (this.mLayout != null) {
        break label115;
      }
      defaultOnMeasure(paramInt1, paramInt2);
    }
    for (;;)
    {
      this.mState.mInPreLayout = false;
      return;
      this.mAdapterHelper.consumeUpdatesInOnePass();
      this.mState.mInPreLayout = false;
      break;
      label104:
      this.mState.mItemCount = 0;
      break label64;
      label115:
      this.mLayout.mRecyclerView.defaultOnMeasure(paramInt1, paramInt2);
    }
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    this.mPendingSavedState = ((SavedState)paramParcelable);
    super.onRestoreInstanceState(this.mPendingSavedState.getSuperState());
    if ((this.mLayout != null) && (this.mPendingSavedState.mLayoutState != null)) {
      this.mLayout.onRestoreInstanceState(this.mPendingSavedState.mLayoutState);
    }
  }
  
  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    if (this.mPendingSavedState != null)
    {
      localSavedState.mLayoutState = this.mPendingSavedState;
      return localSavedState;
    }
    if (this.mLayout != null)
    {
      localSavedState.mLayoutState = this.mLayout.onSaveInstanceState();
      return localSavedState;
    }
    localSavedState.mLayoutState = null;
    return localSavedState;
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if ((paramInt1 != paramInt3) || (paramInt2 != paramInt4)) {
      invalidateGlows();
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((this.mLayoutFrozen) || (this.mIgnoreMotionEventTillDown)) {
      return false;
    }
    int i = paramMotionEvent.getAction();
    int i16;
    int j;
    if (this.mActiveOnItemTouchListener != null)
    {
      if (i == 0) {
        this.mActiveOnItemTouchListener = null;
      }
    }
    else
    {
      if (i == 0) {
        break label128;
      }
      int i15 = this.mOnItemTouchListeners.size();
      i16 = 0;
      if (i16 >= i15) {
        break label128;
      }
      OnItemTouchListener localOnItemTouchListener = (OnItemTouchListener)this.mOnItemTouchListeners.get(i16);
      if (!localOnItemTouchListener.onInterceptTouchEvent$606727fc()) {
        break label122;
      }
      this.mActiveOnItemTouchListener = localOnItemTouchListener;
      j = 1;
    }
    for (;;)
    {
      if (j == 0) {
        break label133;
      }
      cancelTouch();
      return true;
      if ((i == 3) || (i == 1)) {
        this.mActiveOnItemTouchListener = null;
      }
      j = 1;
      continue;
      label122:
      i16++;
      break;
      label128:
      j = 0;
    }
    label133:
    if (this.mLayout == null) {
      return false;
    }
    boolean bool1 = this.mLayout.canScrollHorizontally();
    boolean bool2 = this.mLayout.canScrollVertically();
    if (this.mVelocityTracker == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    }
    MotionEvent localMotionEvent = MotionEvent.obtain(paramMotionEvent);
    int k = MotionEventCompat.getActionMasked(paramMotionEvent);
    int m = MotionEventCompat.getActionIndex(paramMotionEvent);
    if (k == 0)
    {
      int[] arrayOfInt3 = this.mNestedOffsets;
      this.mNestedOffsets[1] = 0;
      arrayOfInt3[0] = 0;
    }
    localMotionEvent.offsetLocation(this.mNestedOffsets[0], this.mNestedOffsets[1]);
    int n = 0;
    switch (k)
    {
    }
    for (;;)
    {
      if (n == 0) {
        this.mVelocityTracker.addMovement(localMotionEvent);
      }
      localMotionEvent.recycle();
      return true;
      this.mScrollPointerId = MotionEventCompat.getPointerId(paramMotionEvent, 0);
      int i12 = (int)(0.5F + paramMotionEvent.getX());
      this.mLastTouchX = i12;
      this.mInitialTouchX = i12;
      int i13 = (int)(0.5F + paramMotionEvent.getY());
      this.mLastTouchY = i13;
      this.mInitialTouchY = i13;
      int i14 = 0;
      if (bool1) {
        i14 = 1;
      }
      if (bool2) {
        i14 |= 0x2;
      }
      startNestedScroll(i14);
      n = 0;
      continue;
      this.mScrollPointerId = MotionEventCompat.getPointerId(paramMotionEvent, m);
      int i10 = (int)(0.5F + MotionEventCompat.getX(paramMotionEvent, m));
      this.mLastTouchX = i10;
      this.mInitialTouchX = i10;
      int i11 = (int)(0.5F + MotionEventCompat.getY(paramMotionEvent, m));
      this.mLastTouchY = i11;
      this.mInitialTouchY = i11;
      n = 0;
      continue;
      int i1 = MotionEventCompat.findPointerIndex(paramMotionEvent, this.mScrollPointerId);
      if (i1 < 0)
      {
        Log.e("RecyclerView", "Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?");
        return false;
      }
      int i2 = (int)(0.5F + MotionEventCompat.getX(paramMotionEvent, i1));
      int i3 = (int)(0.5F + MotionEventCompat.getY(paramMotionEvent, i1));
      int i4 = this.mLastTouchX - i2;
      int i5 = this.mLastTouchY - i3;
      if (dispatchNestedPreScroll(i4, i5, this.mScrollConsumed, this.mScrollOffset))
      {
        i4 -= this.mScrollConsumed[0];
        i5 -= this.mScrollConsumed[1];
        localMotionEvent.offsetLocation(this.mScrollOffset[0], this.mScrollOffset[1]);
        int[] arrayOfInt1 = this.mNestedOffsets;
        arrayOfInt1[0] += this.mScrollOffset[0];
        int[] arrayOfInt2 = this.mNestedOffsets;
        arrayOfInt2[1] += this.mScrollOffset[1];
      }
      if (this.mScrollState != 1)
      {
        int i6 = 0;
        if (bool1)
        {
          int i8 = Math.abs(i4);
          int i9 = this.mTouchSlop;
          i6 = 0;
          if (i8 > i9)
          {
            if (i4 <= 0) {
              break label840;
            }
            i4 -= this.mTouchSlop;
            label706:
            i6 = 1;
          }
        }
        if ((bool2) && (Math.abs(i5) > this.mTouchSlop))
        {
          if (i5 <= 0) {
            break label852;
          }
          i5 -= this.mTouchSlop;
          label740:
          i6 = 1;
        }
        if (i6 != 0) {
          setScrollState(1);
        }
      }
      int i7 = this.mScrollState;
      n = 0;
      if (i7 == 1)
      {
        this.mLastTouchX = (i2 - this.mScrollOffset[0]);
        this.mLastTouchY = (i3 - this.mScrollOffset[1]);
        if (bool1) {
          label799:
          if (!bool2) {
            break label870;
          }
        }
        for (;;)
        {
          boolean bool3 = scrollByInternal(i4, i5, localMotionEvent);
          n = 0;
          if (!bool3) {
            break;
          }
          getParent().requestDisallowInterceptTouchEvent(true);
          n = 0;
          break;
          label840:
          i4 += this.mTouchSlop;
          break label706;
          label852:
          i5 += this.mTouchSlop;
          break label740;
          i4 = 0;
          break label799;
          label870:
          i5 = 0;
        }
        onPointerUp(paramMotionEvent);
        n = 0;
        continue;
        this.mVelocityTracker.addMovement(localMotionEvent);
        n = 1;
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxFlingVelocity);
        float f1;
        if (bool1)
        {
          f1 = -VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mScrollPointerId);
          label933:
          if (!bool2) {
            break label997;
          }
        }
        label997:
        for (float f2 = -VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mScrollPointerId);; f2 = 0.0F)
        {
          if (((f1 == 0.0F) && (f2 == 0.0F)) || (!fling((int)f1, (int)f2))) {
            setScrollState(0);
          }
          resetTouch();
          break;
          f1 = 0.0F;
          break label933;
        }
        cancelTouch();
        n = 0;
      }
    }
  }
  
  protected void removeDetachedView(View paramView, boolean paramBoolean)
  {
    ViewHolder localViewHolder = getChildViewHolderInt(paramView);
    if (localViewHolder != null)
    {
      if (!localViewHolder.isTmpDetached()) {
        break label32;
      }
      localViewHolder.clearTmpDetachFlag();
    }
    label32:
    while (localViewHolder.shouldIgnore())
    {
      dispatchChildDetached(paramView);
      super.removeDetachedView(paramView, paramBoolean);
      return;
    }
    throw new IllegalArgumentException("Called removeDetachedView with a view which is not flagged as tmp detached." + localViewHolder);
  }
  
  public void requestChildFocus(View paramView1, View paramView2)
  {
    boolean bool1 = true;
    LayoutManager localLayoutManager = this.mLayout;
    boolean bool2;
    boolean bool3;
    label45:
    Rect localRect1;
    if ((localLayoutManager.mSmoothScroller != null) && (localLayoutManager.mSmoothScroller.mRunning))
    {
      bool2 = bool1;
      if ((!bool2) && (!isComputingLayout())) {
        break label248;
      }
      bool3 = bool1;
      if ((!bool3) && (paramView2 != null))
      {
        this.mTempRect.set(0, 0, paramView2.getWidth(), paramView2.getHeight());
        ViewGroup.LayoutParams localLayoutParams = paramView2.getLayoutParams();
        if ((localLayoutParams instanceof LayoutParams))
        {
          LayoutParams localLayoutParams1 = (LayoutParams)localLayoutParams;
          if (!localLayoutParams1.mInsetsDirty)
          {
            Rect localRect2 = localLayoutParams1.mDecorInsets;
            Rect localRect3 = this.mTempRect;
            localRect3.left -= localRect2.left;
            Rect localRect4 = this.mTempRect;
            localRect4.right += localRect2.right;
            Rect localRect5 = this.mTempRect;
            localRect5.top -= localRect2.top;
            Rect localRect6 = this.mTempRect;
            localRect6.bottom += localRect2.bottom;
          }
        }
        offsetDescendantRectToMyCoords(paramView2, this.mTempRect);
        offsetRectIntoDescendantCoords(paramView1, this.mTempRect);
        localRect1 = this.mTempRect;
        if (this.mFirstLayoutComplete) {
          break label254;
        }
      }
    }
    for (;;)
    {
      requestChildRectangleOnScreen(paramView1, localRect1, bool1);
      super.requestChildFocus(paramView1, paramView2);
      return;
      bool2 = false;
      break;
      label248:
      bool3 = false;
      break label45;
      label254:
      bool1 = false;
    }
  }
  
  public boolean requestChildRectangleOnScreen(View paramView, Rect paramRect, boolean paramBoolean)
  {
    LayoutManager localLayoutManager = this.mLayout;
    int i = localLayoutManager.getPaddingLeft();
    int j = localLayoutManager.getPaddingTop();
    int k = localLayoutManager.getWidth() - localLayoutManager.getPaddingRight();
    int m = localLayoutManager.getHeight() - localLayoutManager.getPaddingBottom();
    int n = paramView.getLeft() + paramRect.left;
    int i1 = paramView.getTop() + paramRect.top;
    int i2 = n + paramRect.width();
    int i3 = i1 + paramRect.height();
    int i4 = Math.min(0, n - i);
    int i5 = Math.min(0, i1 - j);
    int i6 = Math.max(0, i2 - k);
    int i7 = Math.max(0, i3 - m);
    int i9;
    int i10;
    if (ViewCompat.getLayoutDirection(localLayoutManager.mRecyclerView) == 1) {
      if (i6 != 0)
      {
        i9 = i6;
        if (i5 == 0) {
          break label230;
        }
        i10 = i5;
        label160:
        if ((i9 == 0) && (i10 == 0)) {
          break label256;
        }
        if (!paramBoolean) {
          break label245;
        }
        scrollBy(i9, i10);
      }
    }
    for (;;)
    {
      return true;
      i6 = Math.max(i4, i2 - k);
      break;
      if (i4 != 0) {}
      for (int i8 = i4;; i8 = Math.min(n - i, i6))
      {
        i9 = i8;
        break;
      }
      label230:
      i10 = Math.min(i1 - j, i7);
      break label160;
      label245:
      smoothScrollBy(i9, i10);
    }
    label256:
    return false;
  }
  
  public void requestDisallowInterceptTouchEvent(boolean paramBoolean)
  {
    int i = this.mOnItemTouchListeners.size();
    for (int j = 0; j < i; j++) {
      this.mOnItemTouchListeners.get(j);
    }
    super.requestDisallowInterceptTouchEvent(paramBoolean);
  }
  
  public void requestLayout()
  {
    if ((!this.mEatRequestLayout) && (!this.mLayoutFrozen))
    {
      super.requestLayout();
      return;
    }
    this.mLayoutRequestEaten = true;
  }
  
  final void resumeRequestLayout(boolean paramBoolean)
  {
    if (this.mEatRequestLayout)
    {
      if ((paramBoolean) && (this.mLayoutRequestEaten) && (!this.mLayoutFrozen) && (this.mLayout != null) && (this.mAdapter != null)) {
        dispatchLayout();
      }
      this.mEatRequestLayout = false;
      if (!this.mLayoutFrozen) {
        this.mLayoutRequestEaten = false;
      }
    }
  }
  
  public void scrollBy(int paramInt1, int paramInt2)
  {
    if (this.mLayout == null) {}
    boolean bool1;
    boolean bool2;
    do
    {
      Log.e("RecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
      do
      {
        return;
      } while (this.mLayoutFrozen);
      bool1 = this.mLayout.canScrollHorizontally();
      bool2 = this.mLayout.canScrollVertically();
    } while ((!bool1) && (!bool2));
    if (bool1) {
      if (!bool2) {
        break label74;
      }
    }
    for (;;)
    {
      scrollByInternal(paramInt1, paramInt2, null);
      return;
      paramInt1 = 0;
      break;
      label74:
      paramInt2 = 0;
    }
  }
  
  public void scrollTo(int paramInt1, int paramInt2)
  {
    Log.w("RecyclerView", "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
  }
  
  public void scrollToPosition(int paramInt)
  {
    if (this.mLayoutFrozen) {
      return;
    }
    stopScroll();
    if (this.mLayout == null)
    {
      Log.e("RecyclerView", "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
      return;
    }
    this.mLayout.scrollToPosition(paramInt);
    awakenScrollBars();
  }
  
  public void sendAccessibilityEventUnchecked(AccessibilityEvent paramAccessibilityEvent)
  {
    boolean bool = isComputingLayout();
    int i = 0;
    if (bool) {
      if (paramAccessibilityEvent == null) {
        break label60;
      }
    }
    label60:
    for (int j = AccessibilityEventCompat.getContentChangeTypes(paramAccessibilityEvent);; j = 0)
    {
      int k = 0;
      if (j == 0) {}
      for (;;)
      {
        this.mEatenAccessibilityChangeFlags = (k | this.mEatenAccessibilityChangeFlags);
        i = 1;
        if (i != 0) {
          return;
        }
        super.sendAccessibilityEventUnchecked(paramAccessibilityEvent);
        return;
        k = j;
      }
    }
  }
  
  public void setAccessibilityDelegateCompat(RecyclerViewAccessibilityDelegate paramRecyclerViewAccessibilityDelegate)
  {
    this.mAccessibilityDelegate = paramRecyclerViewAccessibilityDelegate;
    ViewCompat.setAccessibilityDelegate(this, this.mAccessibilityDelegate);
  }
  
  public void setAdapter(Adapter paramAdapter)
  {
    setLayoutFrozen(false);
    setAdapterInternal(paramAdapter, false, true);
    requestLayout();
  }
  
  public final void setAdapterInternal(Adapter paramAdapter, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.mAdapter != null) {
      this.mAdapter.unregisterAdapterDataObserver(this.mObserver);
    }
    if ((!paramBoolean1) || (paramBoolean2))
    {
      if (this.mItemAnimator != null) {
        this.mItemAnimator.endAnimations();
      }
      if (this.mLayout != null)
      {
        this.mLayout.removeAndRecycleAllViews(this.mRecycler);
        this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
      }
      this.mRecycler.clear();
    }
    this.mAdapterHelper.reset();
    Adapter localAdapter1 = this.mAdapter;
    this.mAdapter = paramAdapter;
    if (paramAdapter != null) {
      paramAdapter.registerAdapterDataObserver(this.mObserver);
    }
    Recycler localRecycler = this.mRecycler;
    Adapter localAdapter2 = this.mAdapter;
    localRecycler.clear();
    RecycledViewPool localRecycledViewPool = localRecycler.getRecycledViewPool();
    if (localAdapter1 != null) {
      localRecycledViewPool.detach();
    }
    if ((!paramBoolean1) && (localRecycledViewPool.mAttachCount == 0)) {
      localRecycledViewPool.mScrap.clear();
    }
    if (localAdapter2 != null) {
      localRecycledViewPool.attach$b0de1c8();
    }
    this.mState.mStructureChanged = true;
    markKnownViewsInvalid();
  }
  
  public void setChildDrawingOrderCallback(ChildDrawingOrderCallback paramChildDrawingOrderCallback)
  {
    if (paramChildDrawingOrderCallback == this.mChildDrawingOrderCallback) {
      return;
    }
    this.mChildDrawingOrderCallback = paramChildDrawingOrderCallback;
    if (this.mChildDrawingOrderCallback != null) {}
    for (boolean bool = true;; bool = false)
    {
      setChildrenDrawingOrderEnabled(bool);
      return;
    }
  }
  
  public void setClipToPadding(boolean paramBoolean)
  {
    if (paramBoolean != this.mClipToPadding) {
      invalidateGlows();
    }
    this.mClipToPadding = paramBoolean;
    super.setClipToPadding(paramBoolean);
    if (this.mFirstLayoutComplete) {
      requestLayout();
    }
  }
  
  public final void setDataSetChangedAfterLayout()
  {
    if (this.mDataSetHasChangedAfterLayout) {}
    for (;;)
    {
      return;
      this.mDataSetHasChangedAfterLayout = true;
      int i = this.mChildHelper.getUnfilteredChildCount();
      for (int j = 0; j < i; j++)
      {
        ViewHolder localViewHolder2 = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(j));
        if ((localViewHolder2 != null) && (!localViewHolder2.shouldIgnore())) {
          localViewHolder2.addFlags(512);
        }
      }
      Recycler localRecycler = this.mRecycler;
      int k = localRecycler.mCachedViews.size();
      for (int m = 0; m < k; m++)
      {
        ViewHolder localViewHolder1 = (ViewHolder)localRecycler.mCachedViews.get(m);
        if (localViewHolder1 != null) {
          localViewHolder1.addFlags(512);
        }
      }
    }
  }
  
  public void setHasFixedSize(boolean paramBoolean)
  {
    this.mHasFixedSize = paramBoolean;
  }
  
  public void setItemAnimator(ItemAnimator paramItemAnimator)
  {
    if (this.mItemAnimator != null)
    {
      this.mItemAnimator.endAnimations();
      this.mItemAnimator.mListener = null;
    }
    this.mItemAnimator = paramItemAnimator;
    if (this.mItemAnimator != null) {
      this.mItemAnimator.mListener = this.mItemAnimatorListener;
    }
  }
  
  public void setItemViewCacheSize(int paramInt)
  {
    Recycler localRecycler = this.mRecycler;
    localRecycler.mViewCacheMax = paramInt;
    for (int i = -1 + localRecycler.mCachedViews.size(); (i >= 0) && (localRecycler.mCachedViews.size() > paramInt); i--) {
      localRecycler.recycleCachedViewAt(i);
    }
  }
  
  public void setLayoutFrozen(boolean paramBoolean)
  {
    if (paramBoolean != this.mLayoutFrozen)
    {
      assertNotInLayoutOrScroll("Do not setLayoutFrozen in layout or scroll");
      if (!paramBoolean)
      {
        this.mLayoutFrozen = paramBoolean;
        if ((this.mLayoutRequestEaten) && (this.mLayout != null) && (this.mAdapter != null)) {
          requestLayout();
        }
        this.mLayoutRequestEaten = false;
      }
    }
    else
    {
      return;
    }
    long l = SystemClock.uptimeMillis();
    onTouchEvent(MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0));
    this.mLayoutFrozen = paramBoolean;
    this.mIgnoreMotionEventTillDown = true;
    stopScroll();
  }
  
  public void setLayoutManager(LayoutManager paramLayoutManager)
  {
    if (paramLayoutManager == this.mLayout) {
      return;
    }
    if (this.mLayout != null)
    {
      if (this.mIsAttached) {
        this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
      }
      this.mLayout.setRecyclerView(null);
    }
    this.mRecycler.clear();
    ChildHelper localChildHelper = this.mChildHelper;
    for (ChildHelper.Bucket localBucket = localChildHelper.mBucket;; localBucket = localBucket.next)
    {
      localBucket.mData = 0L;
      if (localBucket.next == null) {
        break;
      }
    }
    for (int i = -1 + localChildHelper.mHiddenViews.size(); i >= 0; i--)
    {
      localChildHelper.mCallback.onLeftHiddenState((View)localChildHelper.mHiddenViews.get(i));
      localChildHelper.mHiddenViews.remove(i);
    }
    localChildHelper.mCallback.removeAllViews();
    this.mLayout = paramLayoutManager;
    if (paramLayoutManager != null)
    {
      if (paramLayoutManager.mRecyclerView != null) {
        throw new IllegalArgumentException("LayoutManager " + paramLayoutManager + " is already attached to a RecyclerView: " + paramLayoutManager.mRecyclerView);
      }
      this.mLayout.setRecyclerView(this);
      if (this.mIsAttached) {
        this.mLayout.mIsAttachedToWindow = true;
      }
    }
    requestLayout();
  }
  
  public void setNestedScrollingEnabled(boolean paramBoolean)
  {
    this.mScrollingChildHelper.setNestedScrollingEnabled(paramBoolean);
  }
  
  @Deprecated
  public void setOnScrollListener(OnScrollListener paramOnScrollListener)
  {
    this.mScrollListener = paramOnScrollListener;
  }
  
  public void setRecycledViewPool(RecycledViewPool paramRecycledViewPool)
  {
    Recycler localRecycler = this.mRecycler;
    if (localRecycler.mRecyclerPool != null) {
      localRecycler.mRecyclerPool.detach();
    }
    localRecycler.mRecyclerPool = paramRecycledViewPool;
    if (paramRecycledViewPool != null)
    {
      RecycledViewPool localRecycledViewPool = localRecycler.mRecyclerPool;
      localRecycler.this$0.getAdapter();
      localRecycledViewPool.attach$b0de1c8();
    }
  }
  
  public void setRecyclerListener(RecyclerListener paramRecyclerListener)
  {
    this.mRecyclerListener = paramRecyclerListener;
  }
  
  public void setScrollingTouchSlop(int paramInt)
  {
    ViewConfiguration localViewConfiguration = ViewConfiguration.get(getContext());
    switch (paramInt)
    {
    default: 
      Log.w("RecyclerView", "setScrollingTouchSlop(): bad argument constant " + paramInt + "; using default value");
    case 0: 
      this.mTouchSlop = localViewConfiguration.getScaledTouchSlop();
      return;
    }
    this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(localViewConfiguration);
  }
  
  public void setViewCacheExtension(ViewCacheExtension paramViewCacheExtension)
  {
    this.mRecycler.mViewCacheExtension = paramViewCacheExtension;
  }
  
  public final void smoothScrollBy(int paramInt1, int paramInt2)
  {
    if (this.mLayout == null) {}
    do
    {
      Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
      do
      {
        return;
      } while (this.mLayoutFrozen);
      if (!this.mLayout.canScrollHorizontally()) {
        paramInt1 = 0;
      }
      if (!this.mLayout.canScrollVertically()) {
        paramInt2 = 0;
      }
    } while ((paramInt1 == 0) && (paramInt2 == 0));
    this.mViewFlinger.smoothScrollBy(paramInt1, paramInt2);
  }
  
  public boolean startNestedScroll(int paramInt)
  {
    return this.mScrollingChildHelper.startNestedScroll(paramInt);
  }
  
  public void stopNestedScroll()
  {
    this.mScrollingChildHelper.stopNestedScroll();
  }
  
  public final void stopScroll()
  {
    setScrollState(0);
    stopScrollersInternal();
  }
  
  public static abstract class Adapter<VH extends RecyclerView.ViewHolder>
  {
    boolean mHasStableIds = false;
    public final RecyclerView.AdapterDataObservable mObservable = new RecyclerView.AdapterDataObservable();
    
    public abstract int getItemCount();
    
    public long getItemId(int paramInt)
    {
      return -1L;
    }
    
    public int getItemViewType(int paramInt)
    {
      return 0;
    }
    
    public final void notifyItemChanged(int paramInt)
    {
      this.mObservable.notifyItemRangeChanged$f13b8cf(paramInt, 1);
    }
    
    public final void notifyItemInserted(int paramInt)
    {
      this.mObservable.notifyItemRangeInserted(paramInt, 1);
    }
    
    public final void notifyItemRemoved(int paramInt)
    {
      this.mObservable.notifyItemRangeRemoved$255f295(paramInt);
    }
    
    public abstract void onBindViewHolder(VH paramVH, int paramInt);
    
    public abstract VH onCreateViewHolder(ViewGroup paramViewGroup, int paramInt);
    
    public boolean onFailedToRecycleView$cb3a904()
    {
      return false;
    }
    
    public void onViewRecycled(VH paramVH) {}
    
    public final void registerAdapterDataObserver(RecyclerView.AdapterDataObserver paramAdapterDataObserver)
    {
      this.mObservable.registerObserver(paramAdapterDataObserver);
    }
    
    public final void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver paramAdapterDataObserver)
    {
      this.mObservable.unregisterObserver(paramAdapterDataObserver);
    }
  }
  
  public static final class AdapterDataObservable
    extends Observable<RecyclerView.AdapterDataObserver>
  {
    public final void notifyChanged()
    {
      for (int i = -1 + this.mObservers.size(); i >= 0; i--) {
        ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onChanged();
      }
    }
    
    public final void notifyItemRangeChanged$f13b8cf(int paramInt1, int paramInt2)
    {
      for (int i = -1 + this.mObservers.size(); i >= 0; i--) {
        ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onItemRangeChanged(paramInt1, 1, null);
      }
    }
    
    public final void notifyItemRangeInserted(int paramInt1, int paramInt2)
    {
      for (int i = -1 + this.mObservers.size(); i >= 0; i--) {
        ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onItemRangeInserted(paramInt1, paramInt2);
      }
    }
    
    public final void notifyItemRangeRemoved$255f295(int paramInt)
    {
      for (int i = -1 + this.mObservers.size(); i >= 0; i--) {
        ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onItemRangeRemoved(paramInt, 1);
      }
    }
  }
  
  public static abstract class AdapterDataObserver
  {
    public void onChanged() {}
    
    public void onItemRangeChanged(int paramInt1, int paramInt2, Object paramObject)
    {
      onItemRangeChanged$255f295();
    }
    
    public void onItemRangeChanged$255f295() {}
    
    public void onItemRangeInserted(int paramInt1, int paramInt2) {}
    
    public void onItemRangeRemoved(int paramInt1, int paramInt2) {}
  }
  
  public static abstract interface ChildDrawingOrderCallback
  {
    public abstract int onGetChildDrawingOrder$255f288();
  }
  
  public static abstract class ItemAnimator
  {
    long mAddDuration = 120L;
    long mChangeDuration = 250L;
    private ArrayList<Object> mFinishedListeners = new ArrayList();
    ItemAnimatorListener mListener = null;
    long mMoveDuration = 250L;
    long mRemoveDuration = 120L;
    
    static int buildAdapterChangeFlagsForAnimations(RecyclerView.ViewHolder paramViewHolder)
    {
      int i = 0xE & RecyclerView.ViewHolder.access$6300(paramViewHolder);
      if (paramViewHolder.isInvalid()) {
        return 4;
      }
      if ((i & 0x4) == 0)
      {
        int j = paramViewHolder.mOldPosition;
        int k = paramViewHolder.getAdapterPosition();
        if ((j != -1) && (k != -1) && (j != k)) {
          i |= 0x800;
        }
      }
      return i;
    }
    
    public abstract boolean animateAppearance(RecyclerView.ViewHolder paramViewHolder, ItemHolderInfo paramItemHolderInfo1, ItemHolderInfo paramItemHolderInfo2);
    
    public abstract boolean animateChange(RecyclerView.ViewHolder paramViewHolder1, RecyclerView.ViewHolder paramViewHolder2, ItemHolderInfo paramItemHolderInfo1, ItemHolderInfo paramItemHolderInfo2);
    
    public abstract boolean animateDisappearance(RecyclerView.ViewHolder paramViewHolder, ItemHolderInfo paramItemHolderInfo1, ItemHolderInfo paramItemHolderInfo2);
    
    public abstract boolean animatePersistence(RecyclerView.ViewHolder paramViewHolder, ItemHolderInfo paramItemHolderInfo1, ItemHolderInfo paramItemHolderInfo2);
    
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder paramViewHolder)
    {
      return true;
    }
    
    public final void dispatchAnimationFinished(RecyclerView.ViewHolder paramViewHolder)
    {
      if (this.mListener != null) {
        this.mListener.onAnimationFinished(paramViewHolder);
      }
    }
    
    public final void dispatchAnimationsFinished()
    {
      int i = this.mFinishedListeners.size();
      for (int j = 0; j < i; j++) {
        this.mFinishedListeners.get(j);
      }
      this.mFinishedListeners.clear();
    }
    
    public abstract void endAnimation(RecyclerView.ViewHolder paramViewHolder);
    
    public abstract void endAnimations();
    
    public abstract boolean isRunning();
    
    public abstract void runPendingAnimations();
    
    static abstract interface ItemAnimatorListener
    {
      public abstract void onAnimationFinished(RecyclerView.ViewHolder paramViewHolder);
    }
    
    public static final class ItemHolderInfo
    {
      public int bottom;
      public int left;
      public int right;
      public int top;
      
      public final ItemHolderInfo setFrom(RecyclerView.ViewHolder paramViewHolder)
      {
        View localView = paramViewHolder.itemView;
        this.left = localView.getLeft();
        this.top = localView.getTop();
        this.right = localView.getRight();
        this.bottom = localView.getBottom();
        return this;
      }
    }
  }
  
  private final class ItemAnimatorRestoreListener
    implements RecyclerView.ItemAnimator.ItemAnimatorListener
  {
    private ItemAnimatorRestoreListener() {}
    
    public final void onAnimationFinished(RecyclerView.ViewHolder paramViewHolder)
    {
      paramViewHolder.setIsRecyclable(true);
      if ((paramViewHolder.mShadowedHolder != null) && (paramViewHolder.mShadowingHolder == null)) {
        paramViewHolder.mShadowedHolder = null;
      }
      paramViewHolder.mShadowingHolder = null;
      if ((!RecyclerView.ViewHolder.access$6100(paramViewHolder)) && (!RecyclerView.access$6200(RecyclerView.this, paramViewHolder.itemView)) && (paramViewHolder.isTmpDetached())) {
        RecyclerView.this.removeDetachedView(paramViewHolder.itemView, false);
      }
    }
  }
  
  public static abstract class ItemDecoration
  {
    public void getItemOffsets$5c1923bd$3450f9fc(Rect paramRect, View paramView)
    {
      ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mViewHolder.getLayoutPosition();
      paramRect.set(0, 0, 0, 0);
    }
    
    public void onDraw$13fcd2ff(Canvas paramCanvas, RecyclerView paramRecyclerView) {}
    
    public void onDrawOver$13fcd2ff(Canvas paramCanvas, RecyclerView paramRecyclerView) {}
  }
  
  public static abstract class LayoutManager
  {
    ChildHelper mChildHelper;
    boolean mIsAttachedToWindow = false;
    RecyclerView mRecyclerView;
    private boolean mRequestedSimpleAnimations = false;
    RecyclerView.SmoothScroller mSmoothScroller;
    
    public static int getChildMeasureSpec(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
    {
      int i = Math.max(0, paramInt1 - paramInt2);
      int k;
      int j;
      if (paramBoolean) {
        if (paramInt3 >= 0)
        {
          k = paramInt3;
          j = 1073741824;
        }
      }
      for (;;)
      {
        return View.MeasureSpec.makeMeasureSpec(k, j);
        j = 0;
        k = 0;
        continue;
        if (paramInt3 >= 0)
        {
          k = paramInt3;
          j = 1073741824;
        }
        else if (paramInt3 == -1)
        {
          k = i;
          j = 1073741824;
        }
        else
        {
          j = 0;
          k = 0;
          if (paramInt3 == -2)
          {
            k = i;
            j = -2147483648;
          }
        }
      }
    }
    
    public static int getDecoratedBottom(View paramView)
    {
      return paramView.getBottom() + ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets.bottom;
    }
    
    public static int getDecoratedLeft(View paramView)
    {
      return paramView.getLeft() - ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets.left;
    }
    
    public static int getDecoratedMeasuredHeight(View paramView)
    {
      Rect localRect = ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets;
      return paramView.getMeasuredHeight() + localRect.top + localRect.bottom;
    }
    
    public static int getDecoratedMeasuredWidth(View paramView)
    {
      Rect localRect = ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets;
      return paramView.getMeasuredWidth() + localRect.left + localRect.right;
    }
    
    public static int getDecoratedRight(View paramView)
    {
      return paramView.getRight() + ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets.right;
    }
    
    public static int getDecoratedTop(View paramView)
    {
      return paramView.getTop() - ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets.top;
    }
    
    public static int getPosition(View paramView)
    {
      return ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mViewHolder.getLayoutPosition();
    }
    
    public static void layoutDecorated(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      Rect localRect = ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets;
      paramView.layout(paramInt1 + localRect.left, paramInt2 + localRect.top, paramInt3 - localRect.right, paramInt4 - localRect.bottom);
    }
    
    void addViewInt(View paramView, int paramInt, boolean paramBoolean)
    {
      RecyclerView.ViewHolder localViewHolder1 = RecyclerView.getChildViewHolderInt(paramView);
      RecyclerView.LayoutParams localLayoutParams1;
      if ((paramBoolean) || (localViewHolder1.isRemoved()))
      {
        this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(localViewHolder1);
        localLayoutParams1 = (RecyclerView.LayoutParams)paramView.getLayoutParams();
        if ((!localViewHolder1.wasReturnedFromScrap()) && (!localViewHolder1.isScrap())) {
          break label128;
        }
        if (!localViewHolder1.isScrap()) {
          break label120;
        }
        localViewHolder1.unScrap();
        label68:
        this.mChildHelper.attachViewToParent(paramView, paramInt, paramView.getLayoutParams(), false);
      }
      for (;;)
      {
        if (localLayoutParams1.mPendingInvalidate)
        {
          localViewHolder1.itemView.invalidate();
          localLayoutParams1.mPendingInvalidate = false;
        }
        return;
        this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(localViewHolder1);
        break;
        label120:
        localViewHolder1.clearReturnedFromScrapFlag();
        break label68;
        label128:
        if (paramView.getParent() == this.mRecyclerView)
        {
          int i = this.mChildHelper.indexOfChild(paramView);
          if (paramInt == -1) {
            paramInt = this.mChildHelper.getChildCount();
          }
          if (i == -1) {
            throw new IllegalStateException("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:" + this.mRecyclerView.indexOfChild(paramView));
          }
          if (i != paramInt)
          {
            LayoutManager localLayoutManager = this.mRecyclerView.mLayout;
            View localView = localLayoutManager.getChildAt(i);
            if (localView == null) {
              throw new IllegalArgumentException("Cannot move a child from non-existing index:" + i);
            }
            localLayoutManager.detachViewAt(i);
            RecyclerView.LayoutParams localLayoutParams2 = (RecyclerView.LayoutParams)localView.getLayoutParams();
            RecyclerView.ViewHolder localViewHolder2 = RecyclerView.getChildViewHolderInt(localView);
            if (localViewHolder2.isRemoved()) {
              localLayoutManager.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(localViewHolder2);
            }
            for (;;)
            {
              localLayoutManager.mChildHelper.attachViewToParent(localView, paramInt, localLayoutParams2, localViewHolder2.isRemoved());
              break;
              localLayoutManager.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(localViewHolder2);
            }
          }
        }
        else
        {
          this.mChildHelper.addView(paramView, paramInt, false);
          localLayoutParams1.mInsetsDirty = true;
          if ((this.mSmoothScroller != null) && (this.mSmoothScroller.mRunning))
          {
            RecyclerView.SmoothScroller localSmoothScroller = this.mSmoothScroller;
            if (RecyclerView.getChildLayoutPosition(paramView) == localSmoothScroller.mTargetPosition) {
              localSmoothScroller.mTargetView = paramView;
            }
          }
        }
      }
    }
    
    public void assertNotInLayoutOrScroll(String paramString)
    {
      if (this.mRecyclerView != null) {
        this.mRecyclerView.assertNotInLayoutOrScroll(paramString);
      }
    }
    
    public boolean canScrollHorizontally()
    {
      return false;
    }
    
    public boolean canScrollVertically()
    {
      return false;
    }
    
    public boolean checkLayoutParams(RecyclerView.LayoutParams paramLayoutParams)
    {
      return paramLayoutParams != null;
    }
    
    public int computeHorizontalScrollExtent(RecyclerView.State paramState)
    {
      return 0;
    }
    
    public int computeHorizontalScrollOffset(RecyclerView.State paramState)
    {
      return 0;
    }
    
    public int computeHorizontalScrollRange(RecyclerView.State paramState)
    {
      return 0;
    }
    
    public int computeVerticalScrollExtent(RecyclerView.State paramState)
    {
      return 0;
    }
    
    public int computeVerticalScrollOffset(RecyclerView.State paramState)
    {
      return 0;
    }
    
    public int computeVerticalScrollRange(RecyclerView.State paramState)
    {
      return 0;
    }
    
    public final void detachViewAt(int paramInt)
    {
      getChildAt(paramInt);
      this.mChildHelper.detachViewFromParent(paramInt);
    }
    
    final void dispatchDetachedFromWindow(RecyclerView paramRecyclerView, RecyclerView.Recycler paramRecycler)
    {
      this.mIsAttachedToWindow = false;
      onDetachedFromWindow(paramRecyclerView, paramRecycler);
    }
    
    public View findViewByPosition(int paramInt)
    {
      int i = getChildCount();
      for (int j = 0; j < i; j++)
      {
        View localView = getChildAt(j);
        RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(localView);
        if ((localViewHolder != null) && (localViewHolder.getLayoutPosition() == paramInt) && (!localViewHolder.shouldIgnore()) && ((this.mRecyclerView.mState.mInPreLayout) || (!localViewHolder.isRemoved()))) {
          return localView;
        }
      }
      return null;
    }
    
    public abstract RecyclerView.LayoutParams generateDefaultLayoutParams();
    
    public RecyclerView.LayoutParams generateLayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      return new RecyclerView.LayoutParams(paramContext, paramAttributeSet);
    }
    
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      if ((paramLayoutParams instanceof RecyclerView.LayoutParams)) {
        return new RecyclerView.LayoutParams((RecyclerView.LayoutParams)paramLayoutParams);
      }
      if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
        return new RecyclerView.LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams);
      }
      return new RecyclerView.LayoutParams(paramLayoutParams);
    }
    
    public final View getChildAt(int paramInt)
    {
      if (this.mChildHelper != null) {
        return this.mChildHelper.getChildAt(paramInt);
      }
      return null;
    }
    
    public final int getChildCount()
    {
      if (this.mChildHelper != null) {
        return this.mChildHelper.getChildCount();
      }
      return 0;
    }
    
    public int getColumnCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      if ((this.mRecyclerView == null) || (this.mRecyclerView.mAdapter == null)) {}
      while (!canScrollHorizontally()) {
        return 1;
      }
      return this.mRecyclerView.mAdapter.getItemCount();
    }
    
    public final int getHeight()
    {
      if (this.mRecyclerView != null) {
        return this.mRecyclerView.getHeight();
      }
      return 0;
    }
    
    public final int getItemCount()
    {
      if (this.mRecyclerView != null) {}
      for (RecyclerView.Adapter localAdapter = this.mRecyclerView.getAdapter(); localAdapter != null; localAdapter = null) {
        return localAdapter.getItemCount();
      }
      return 0;
    }
    
    public final int getPaddingBottom()
    {
      if (this.mRecyclerView != null) {
        return this.mRecyclerView.getPaddingBottom();
      }
      return 0;
    }
    
    public final int getPaddingLeft()
    {
      if (this.mRecyclerView != null) {
        return this.mRecyclerView.getPaddingLeft();
      }
      return 0;
    }
    
    public final int getPaddingRight()
    {
      if (this.mRecyclerView != null) {
        return this.mRecyclerView.getPaddingRight();
      }
      return 0;
    }
    
    public final int getPaddingTop()
    {
      if (this.mRecyclerView != null) {
        return this.mRecyclerView.getPaddingTop();
      }
      return 0;
    }
    
    public int getRowCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      if ((this.mRecyclerView == null) || (this.mRecyclerView.mAdapter == null)) {}
      while (!canScrollVertically()) {
        return 1;
      }
      return this.mRecyclerView.mAdapter.getItemCount();
    }
    
    public final int getWidth()
    {
      if (this.mRecyclerView != null) {
        return this.mRecyclerView.getWidth();
      }
      return 0;
    }
    
    public void onDetachedFromWindow(RecyclerView paramRecyclerView, RecyclerView.Recycler paramRecycler) {}
    
    public View onFocusSearchFailed$1539f5dc(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      return null;
    }
    
    public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
    {
      int i = 1;
      AccessibilityRecordCompat localAccessibilityRecordCompat = AccessibilityEventCompat.asRecord(paramAccessibilityEvent);
      if (this.mRecyclerView == null) {
        return;
      }
      if ((ViewCompat.canScrollVertically(this.mRecyclerView, i)) || (ViewCompat.canScrollVertically(this.mRecyclerView, -1)) || (ViewCompat.canScrollHorizontally(this.mRecyclerView, -1)) || (ViewCompat.canScrollHorizontally(this.mRecyclerView, i))) {}
      for (;;)
      {
        localAccessibilityRecordCompat.setScrollable(i);
        if (this.mRecyclerView.mAdapter == null) {
          break;
        }
        localAccessibilityRecordCompat.setItemCount(this.mRecyclerView.mAdapter.getItemCount());
        return;
        int j = 0;
      }
    }
    
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      int i;
      if (canScrollVertically())
      {
        i = getPosition(paramView);
        if (!canScrollHorizontally()) {
          break label48;
        }
      }
      label48:
      for (int j = getPosition(paramView);; j = 0)
      {
        paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain$430787b1(i, 1, j, 1, false));
        return;
        i = 0;
        break;
      }
    }
    
    final void onInitializeAccessibilityNodeInfoForItem(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      if ((localViewHolder != null) && (!localViewHolder.isRemoved()) && (!this.mChildHelper.isHidden(localViewHolder.itemView))) {
        onInitializeAccessibilityNodeInfoForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, paramView, paramAccessibilityNodeInfoCompat);
      }
    }
    
    public View onInterceptFocusSearch(View paramView, int paramInt)
    {
      return null;
    }
    
    public void onItemsAdded$5927c743() {}
    
    public void onItemsChanged$57043c5d() {}
    
    public void onItemsMoved$342e6be0() {}
    
    public void onItemsRemoved$5927c743() {}
    
    public void onItemsUpdated$783f8c5f$5927c743() {}
    
    public void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      Log.e("RecyclerView", "You must override onLayoutChildren(Recycler recycler, State state) ");
    }
    
    public void onRestoreInstanceState(Parcelable paramParcelable) {}
    
    public Parcelable onSaveInstanceState()
    {
      return null;
    }
    
    public final void removeAndRecycleAllViews(RecyclerView.Recycler paramRecycler)
    {
      for (int i = -1 + getChildCount(); i >= 0; i--) {
        if (!RecyclerView.getChildViewHolderInt(getChildAt(i)).shouldIgnore()) {
          removeAndRecycleViewAt(i, paramRecycler);
        }
      }
    }
    
    final void removeAndRecycleScrapInt(RecyclerView.Recycler paramRecycler)
    {
      int i = paramRecycler.mAttachedScrap.size();
      for (int j = i - 1; j >= 0; j--)
      {
        View localView = ((RecyclerView.ViewHolder)paramRecycler.mAttachedScrap.get(j)).itemView;
        RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(localView);
        if (!localViewHolder.shouldIgnore())
        {
          localViewHolder.setIsRecyclable(false);
          if (localViewHolder.isTmpDetached()) {
            this.mRecyclerView.removeDetachedView(localView, false);
          }
          if (this.mRecyclerView.mItemAnimator != null) {
            this.mRecyclerView.mItemAnimator.endAnimation(localViewHolder);
          }
          localViewHolder.setIsRecyclable(true);
          paramRecycler.quickRecycleScrapView(localView);
        }
      }
      paramRecycler.mAttachedScrap.clear();
      if (paramRecycler.mChangedScrap != null) {
        paramRecycler.mChangedScrap.clear();
      }
      if (i > 0) {
        this.mRecyclerView.invalidate();
      }
    }
    
    public final void removeAndRecycleViewAt(int paramInt, RecyclerView.Recycler paramRecycler)
    {
      View localView = getChildAt(paramInt);
      removeViewAt(paramInt);
      paramRecycler.recycleView(localView);
    }
    
    public final void removeViewAt(int paramInt)
    {
      if (getChildAt(paramInt) != null)
      {
        ChildHelper localChildHelper = this.mChildHelper;
        int i = localChildHelper.getOffset(paramInt);
        View localView = localChildHelper.mCallback.getChildAt(i);
        if (localView != null)
        {
          if (localChildHelper.mBucket.remove(i)) {
            localChildHelper.unhideViewInternal(localView);
          }
          localChildHelper.mCallback.removeViewAt(i);
        }
      }
    }
    
    public final void requestLayout()
    {
      if (this.mRecyclerView != null) {
        this.mRecyclerView.requestLayout();
      }
    }
    
    public int scrollHorizontallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      return 0;
    }
    
    public void scrollToPosition(int paramInt) {}
    
    public int scrollVerticallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      return 0;
    }
    
    final void setRecyclerView(RecyclerView paramRecyclerView)
    {
      if (paramRecyclerView == null)
      {
        this.mRecyclerView = null;
        this.mChildHelper = null;
        return;
      }
      this.mRecyclerView = paramRecyclerView;
      this.mChildHelper = paramRecyclerView.mChildHelper;
    }
    
    public void smoothScrollToPosition$7d69765f(RecyclerView paramRecyclerView, int paramInt)
    {
      Log.e("RecyclerView", "You must override smoothScrollToPosition to support smooth scrolling");
    }
    
    public final void startSmoothScroll(RecyclerView.SmoothScroller paramSmoothScroller)
    {
      if ((this.mSmoothScroller != null) && (paramSmoothScroller != this.mSmoothScroller) && (this.mSmoothScroller.mRunning)) {
        this.mSmoothScroller.stop();
      }
      this.mSmoothScroller = paramSmoothScroller;
      RecyclerView.SmoothScroller localSmoothScroller = this.mSmoothScroller;
      localSmoothScroller.mRecyclerView = this.mRecyclerView;
      localSmoothScroller.mLayoutManager = this;
      if (localSmoothScroller.mTargetPosition == -1) {
        throw new IllegalArgumentException("Invalid target position");
      }
      localSmoothScroller.mRecyclerView.mState.mTargetPosition = localSmoothScroller.mTargetPosition;
      localSmoothScroller.mRunning = true;
      localSmoothScroller.mPendingInitialRun = true;
      int i = localSmoothScroller.mTargetPosition;
      localSmoothScroller.mTargetView = localSmoothScroller.mRecyclerView.mLayout.findViewByPosition(i);
      localSmoothScroller.mRecyclerView.mViewFlinger.postOnAnimation();
    }
    
    final void stopSmoothScroller()
    {
      if (this.mSmoothScroller != null) {
        this.mSmoothScroller.stop();
      }
    }
    
    public boolean supportsPredictiveItemAnimations()
    {
      return false;
    }
  }
  
  public static class LayoutParams
    extends ViewGroup.MarginLayoutParams
  {
    final Rect mDecorInsets = new Rect();
    boolean mInsetsDirty = true;
    boolean mPendingInvalidate = false;
    RecyclerView.ViewHolder mViewHolder;
    
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(-2);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
  }
  
  public static abstract interface OnItemTouchListener
  {
    public abstract boolean onInterceptTouchEvent$606727fc();
  }
  
  public static abstract class OnScrollListener
  {
    public void onScrollStateChanged(RecyclerView paramRecyclerView, int paramInt) {}
    
    public void onScrolled(RecyclerView paramRecyclerView, int paramInt1, int paramInt2) {}
  }
  
  public static final class RecycledViewPool
  {
    int mAttachCount = 0;
    SparseIntArray mMaxScrap = new SparseIntArray();
    SparseArray<ArrayList<RecyclerView.ViewHolder>> mScrap = new SparseArray();
    
    final void attach$b0de1c8()
    {
      this.mAttachCount = (1 + this.mAttachCount);
    }
    
    final void detach()
    {
      this.mAttachCount = (-1 + this.mAttachCount);
    }
    
    public final void setMaxRecycledViews(int paramInt1, int paramInt2)
    {
      this.mMaxScrap.put(paramInt1, paramInt2);
      ArrayList localArrayList = (ArrayList)this.mScrap.get(paramInt1);
      if (localArrayList != null) {
        while (localArrayList.size() > paramInt2) {
          localArrayList.remove(-1 + localArrayList.size());
        }
      }
    }
  }
  
  public final class Recycler
  {
    final ArrayList<RecyclerView.ViewHolder> mAttachedScrap = new ArrayList();
    final ArrayList<RecyclerView.ViewHolder> mCachedViews = new ArrayList();
    ArrayList<RecyclerView.ViewHolder> mChangedScrap = null;
    RecyclerView.RecycledViewPool mRecyclerPool;
    final List<RecyclerView.ViewHolder> mUnmodifiableAttachedScrap = Collections.unmodifiableList(this.mAttachedScrap);
    RecyclerView.ViewCacheExtension mViewCacheExtension;
    int mViewCacheMax = 2;
    
    public Recycler() {}
    
    private void addViewHolderToRecycledViewPool(RecyclerView.ViewHolder paramViewHolder)
    {
      ViewCompat.setAccessibilityDelegate(paramViewHolder.itemView, null);
      if ((RecyclerView.this.mRecyclerListener == null) || (RecyclerView.this.mAdapter != null)) {
        RecyclerView.this.mAdapter.onViewRecycled(paramViewHolder);
      }
      if (RecyclerView.this.mState != null) {
        RecyclerView.this.mViewInfoStore.removeViewHolder(paramViewHolder);
      }
      paramViewHolder.mOwnerRecyclerView = null;
      RecyclerView.RecycledViewPool localRecycledViewPool = getRecycledViewPool();
      int i = paramViewHolder.mItemViewType;
      ArrayList localArrayList = (ArrayList)localRecycledViewPool.mScrap.get(i);
      if (localArrayList == null)
      {
        localArrayList = new ArrayList();
        localRecycledViewPool.mScrap.put(i, localArrayList);
        if (localRecycledViewPool.mMaxScrap.indexOfKey(i) < 0) {
          localRecycledViewPool.mMaxScrap.put(i, 5);
        }
      }
      if (localRecycledViewPool.mMaxScrap.get(i) > localArrayList.size())
      {
        paramViewHolder.resetInternal();
        localArrayList.add(paramViewHolder);
      }
    }
    
    private RecyclerView.ViewHolder getChangedScrapViewForPosition(int paramInt)
    {
      int i;
      if (this.mChangedScrap != null)
      {
        i = this.mChangedScrap.size();
        if (i != 0) {}
      }
      else
      {
        return null;
      }
      for (int j = 0; j < i; j++)
      {
        RecyclerView.ViewHolder localViewHolder2 = (RecyclerView.ViewHolder)this.mChangedScrap.get(j);
        if ((!localViewHolder2.wasReturnedFromScrap()) && (localViewHolder2.getLayoutPosition() == paramInt))
        {
          localViewHolder2.addFlags(32);
          return localViewHolder2;
        }
      }
      if (RecyclerView.this.mAdapter.mHasStableIds)
      {
        int k = RecyclerView.this.mAdapterHelper.findPositionOffset(paramInt, 0);
        if ((k > 0) && (k < RecyclerView.this.mAdapter.getItemCount()))
        {
          long l = RecyclerView.this.mAdapter.getItemId(k);
          for (int m = 0; m < i; m++)
          {
            RecyclerView.ViewHolder localViewHolder1 = (RecyclerView.ViewHolder)this.mChangedScrap.get(m);
            if ((!localViewHolder1.wasReturnedFromScrap()) && (localViewHolder1.mItemId == l))
            {
              localViewHolder1.addFlags(32);
              return localViewHolder1;
            }
          }
        }
      }
      return null;
    }
    
    private RecyclerView.ViewHolder getScrapViewForId(long paramLong, int paramInt, boolean paramBoolean)
    {
      for (int i = -1 + this.mAttachedScrap.size(); i >= 0; i--)
      {
        RecyclerView.ViewHolder localViewHolder2 = (RecyclerView.ViewHolder)this.mAttachedScrap.get(i);
        if ((localViewHolder2.mItemId == paramLong) && (!localViewHolder2.wasReturnedFromScrap()))
        {
          if (paramInt == localViewHolder2.mItemViewType)
          {
            localViewHolder2.addFlags(32);
            if ((localViewHolder2.isRemoved()) && (!RecyclerView.this.mState.mInPreLayout)) {
              localViewHolder2.setFlags(2, 14);
            }
            return localViewHolder2;
          }
          this.mAttachedScrap.remove(i);
          RecyclerView.this.removeDetachedView(localViewHolder2.itemView, false);
          quickRecycleScrapView(localViewHolder2.itemView);
        }
      }
      for (int j = -1 + this.mCachedViews.size(); j >= 0; j--)
      {
        RecyclerView.ViewHolder localViewHolder1 = (RecyclerView.ViewHolder)this.mCachedViews.get(j);
        if (localViewHolder1.mItemId == paramLong)
        {
          if (paramInt == localViewHolder1.mItemViewType)
          {
            this.mCachedViews.remove(j);
            return localViewHolder1;
          }
          recycleCachedViewAt(j);
        }
      }
      return null;
    }
    
    private RecyclerView.ViewHolder getScrapViewForPosition$6d61fdc(int paramInt, boolean paramBoolean)
    {
      int i = this.mAttachedScrap.size();
      for (int j = 0; j < i; j++)
      {
        RecyclerView.ViewHolder localViewHolder4 = (RecyclerView.ViewHolder)this.mAttachedScrap.get(j);
        if ((!localViewHolder4.wasReturnedFromScrap()) && (localViewHolder4.getLayoutPosition() == paramInt) && (!localViewHolder4.isInvalid()) && ((RecyclerView.this.mState.mInPreLayout) || (!localViewHolder4.isRemoved())))
        {
          localViewHolder4.addFlags(32);
          return localViewHolder4;
        }
      }
      ChildHelper localChildHelper1 = RecyclerView.this.mChildHelper;
      int k = localChildHelper1.mHiddenViews.size();
      int m = 0;
      View localView1;
      if (m < k)
      {
        View localView2 = (View)localChildHelper1.mHiddenViews.get(m);
        RecyclerView.ViewHolder localViewHolder3 = localChildHelper1.mCallback.getChildViewHolder(localView2);
        if ((localViewHolder3.getLayoutPosition() == paramInt) && (!localViewHolder3.isInvalid())) {
          localView1 = localView2;
        }
      }
      for (;;)
      {
        if (localView1 != null)
        {
          RecyclerView.ViewHolder localViewHolder2 = RecyclerView.getChildViewHolderInt(localView1);
          ChildHelper localChildHelper2 = RecyclerView.this.mChildHelper;
          int i2 = localChildHelper2.mCallback.indexOfChild(localView1);
          if (i2 < 0)
          {
            throw new IllegalArgumentException("view is not a child, cannot hide " + localView1);
            m++;
            break;
            localView1 = null;
            continue;
          }
          if (!localChildHelper2.mBucket.get(i2)) {
            throw new RuntimeException("trying to unhide a view that was not hidden" + localView1);
          }
          localChildHelper2.mBucket.clear(i2);
          localChildHelper2.unhideViewInternal(localView1);
          int i3 = RecyclerView.this.mChildHelper.indexOfChild(localView1);
          if (i3 == -1) {
            throw new IllegalStateException("layout index should not be -1 after unhiding a view:" + localViewHolder2);
          }
          RecyclerView.this.mChildHelper.detachViewFromParent(i3);
          scrapView(localView1);
          localViewHolder2.addFlags(8224);
          return localViewHolder2;
        }
      }
      int n = this.mCachedViews.size();
      for (int i1 = 0; i1 < n; i1++)
      {
        RecyclerView.ViewHolder localViewHolder1 = (RecyclerView.ViewHolder)this.mCachedViews.get(i1);
        if ((!localViewHolder1.isInvalid()) && (localViewHolder1.getLayoutPosition() == paramInt))
        {
          this.mCachedViews.remove(i1);
          return localViewHolder1;
        }
      }
      return null;
    }
    
    private void invalidateDisplayListInt(ViewGroup paramViewGroup, boolean paramBoolean)
    {
      for (int i = -1 + paramViewGroup.getChildCount(); i >= 0; i--)
      {
        View localView = paramViewGroup.getChildAt(i);
        if ((localView instanceof ViewGroup)) {
          invalidateDisplayListInt((ViewGroup)localView, true);
        }
      }
      if (!paramBoolean) {
        return;
      }
      if (paramViewGroup.getVisibility() == 4)
      {
        paramViewGroup.setVisibility(0);
        paramViewGroup.setVisibility(4);
        return;
      }
      int j = paramViewGroup.getVisibility();
      paramViewGroup.setVisibility(4);
      paramViewGroup.setVisibility(j);
    }
    
    public final void clear()
    {
      this.mAttachedScrap.clear();
      recycleAndClearCachedViews();
    }
    
    public final int convertPreLayoutPositionToPostLayout(int paramInt)
    {
      if ((paramInt < 0) || (paramInt >= RecyclerView.this.mState.getItemCount())) {
        throw new IndexOutOfBoundsException("invalid position " + paramInt + ". State item count is " + RecyclerView.this.mState.getItemCount());
      }
      if (!RecyclerView.this.mState.mInPreLayout) {
        return paramInt;
      }
      return RecyclerView.this.mAdapterHelper.findPositionOffset(paramInt);
    }
    
    final RecyclerView.RecycledViewPool getRecycledViewPool()
    {
      if (this.mRecyclerPool == null) {
        this.mRecyclerPool = new RecyclerView.RecycledViewPool();
      }
      return this.mRecyclerPool;
    }
    
    final View getViewForPosition$3a4f3d28(int paramInt)
    {
      if ((paramInt < 0) || (paramInt >= RecyclerView.this.mState.getItemCount())) {
        throw new IndexOutOfBoundsException("Invalid item position " + paramInt + "(" + paramInt + "). Item count:" + RecyclerView.this.mState.getItemCount());
      }
      boolean bool1 = RecyclerView.this.mState.mInPreLayout;
      int i = 0;
      Object localObject = null;
      if (bool1)
      {
        localObject = getChangedScrapViewForPosition(paramInt);
        if (localObject == null) {
          break label184;
        }
        i = 1;
      }
      label184:
      int i2;
      while (localObject == null)
      {
        localObject = getScrapViewForPosition$6d61fdc(paramInt, false);
        if (localObject != null)
        {
          if (((RecyclerView.ViewHolder)localObject).isRemoved()) {
            break label410;
          }
          if ((((RecyclerView.ViewHolder)localObject).mPosition < 0) || (((RecyclerView.ViewHolder)localObject).mPosition >= RecyclerView.this.mAdapter.getItemCount()))
          {
            throw new IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + localObject);
            i = 0;
          }
          else
          {
            if ((RecyclerView.this.mState.mInPreLayout) || (RecyclerView.this.mAdapter.getItemViewType(((RecyclerView.ViewHolder)localObject).mPosition) == ((RecyclerView.ViewHolder)localObject).mItemViewType)) {
              break label367;
            }
            i2 = 0;
            if (i2 != 0) {
              break label432;
            }
            ((RecyclerView.ViewHolder)localObject).addFlags(4);
            if (!((RecyclerView.ViewHolder)localObject).isScrap()) {
              break label416;
            }
            RecyclerView.this.removeDetachedView(((RecyclerView.ViewHolder)localObject).itemView, false);
            ((RecyclerView.ViewHolder)localObject).unScrap();
            label265:
            recycleViewHolderInternal((RecyclerView.ViewHolder)localObject);
            localObject = null;
          }
        }
      }
      for (;;)
      {
        if (localObject == null)
        {
          int m = RecyclerView.this.mAdapterHelper.findPositionOffset(paramInt);
          if ((m < 0) || (m >= RecyclerView.this.mAdapter.getItemCount()))
          {
            throw new IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + paramInt + "(offset:" + m + ").state:" + RecyclerView.this.mState.getItemCount());
            label367:
            if ((RecyclerView.this.mAdapter.mHasStableIds) && (((RecyclerView.ViewHolder)localObject).mItemId != RecyclerView.this.mAdapter.getItemId(((RecyclerView.ViewHolder)localObject).mPosition)))
            {
              i2 = 0;
              break;
            }
            label410:
            i2 = 1;
            break;
            label416:
            if (!((RecyclerView.ViewHolder)localObject).wasReturnedFromScrap()) {
              break label265;
            }
            ((RecyclerView.ViewHolder)localObject).clearReturnedFromScrapFlag();
            break label265;
            label432:
            i = 1;
            continue;
          }
          int n = RecyclerView.this.mAdapter.getItemViewType(m);
          if (RecyclerView.this.mAdapter.mHasStableIds)
          {
            localObject = getScrapViewForId(RecyclerView.this.mAdapter.getItemId(m), n, false);
            if (localObject != null)
            {
              ((RecyclerView.ViewHolder)localObject).mPosition = m;
              i = 1;
            }
          }
          if ((localObject == null) && (this.mViewCacheExtension != null))
          {
            View localView2 = this.mViewCacheExtension.getViewForPositionAndType$430f8374();
            if (localView2 != null)
            {
              localObject = RecyclerView.this.getChildViewHolder(localView2);
              if (localObject == null) {
                throw new IllegalArgumentException("getViewForPositionAndType returned a view which does not have a ViewHolder");
              }
              if (((RecyclerView.ViewHolder)localObject).shouldIgnore()) {
                throw new IllegalArgumentException("getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view.");
              }
            }
          }
          if (localObject == null)
          {
            ArrayList localArrayList = (ArrayList)getRecycledViewPool().mScrap.get(n);
            if ((localArrayList == null) || (localArrayList.isEmpty())) {
              break label928;
            }
            int i1 = -1 + localArrayList.size();
            RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)localArrayList.get(i1);
            localArrayList.remove(i1);
            localObject = localViewHolder;
            if (localObject != null)
            {
              ((RecyclerView.ViewHolder)localObject).resetInternal();
              if ((RecyclerView.FORCE_INVALIDATE_DISPLAY_LIST) && ((((RecyclerView.ViewHolder)localObject).itemView instanceof ViewGroup))) {
                invalidateDisplayListInt((ViewGroup)((RecyclerView.ViewHolder)localObject).itemView, false);
              }
            }
          }
          if (localObject == null)
          {
            RecyclerView.Adapter localAdapter2 = RecyclerView.this.mAdapter;
            RecyclerView localRecyclerView = RecyclerView.this;
            TraceCompat.beginSection("RV CreateView");
            localObject = localAdapter2.onCreateViewHolder(localRecyclerView, n);
            ((RecyclerView.ViewHolder)localObject).mItemViewType = n;
            TraceCompat.endSection();
          }
        }
      }
      if ((i != 0) && (!RecyclerView.this.mState.mInPreLayout) && (((RecyclerView.ViewHolder)localObject).hasAnyOfTheFlags(8192)))
      {
        ((RecyclerView.ViewHolder)localObject).setFlags(0, 8192);
        if (RecyclerView.this.mState.mRunSimpleAnimations)
        {
          RecyclerView.ItemAnimator.buildAdapterChangeFlagsForAnimations((RecyclerView.ViewHolder)localObject);
          ((RecyclerView.ViewHolder)localObject).getUnmodifiedPayloads();
          RecyclerView.ItemAnimator.ItemHolderInfo localItemHolderInfo = new RecyclerView.ItemAnimator.ItemHolderInfo().setFrom((RecyclerView.ViewHolder)localObject);
          RecyclerView.this.recordAnimationInfoIfBouncedHiddenView((RecyclerView.ViewHolder)localObject, localItemHolderInfo);
        }
      }
      int j = 0;
      label859:
      ViewGroup.LayoutParams localLayoutParams;
      RecyclerView.LayoutParams localLayoutParams1;
      if ((RecyclerView.this.mState.mInPreLayout) && (((RecyclerView.ViewHolder)localObject).isBound()))
      {
        ((RecyclerView.ViewHolder)localObject).mPreLayoutPosition = paramInt;
        localLayoutParams = ((RecyclerView.ViewHolder)localObject).itemView.getLayoutParams();
        if (localLayoutParams != null) {
          break label1140;
        }
        localLayoutParams1 = (RecyclerView.LayoutParams)RecyclerView.this.generateDefaultLayoutParams();
        ((RecyclerView.ViewHolder)localObject).itemView.setLayoutParams(localLayoutParams1);
        label896:
        localLayoutParams1.mViewHolder = ((RecyclerView.ViewHolder)localObject);
        if ((i == 0) || (j == 0)) {
          break label1189;
        }
      }
      label928:
      label1189:
      for (boolean bool2 = true;; bool2 = false)
      {
        localLayoutParams1.mPendingInvalidate = bool2;
        return ((RecyclerView.ViewHolder)localObject).itemView;
        localObject = null;
        break;
        if ((((RecyclerView.ViewHolder)localObject).isBound()) && (!((RecyclerView.ViewHolder)localObject).needsUpdate()))
        {
          boolean bool3 = ((RecyclerView.ViewHolder)localObject).isInvalid();
          j = 0;
          if (!bool3) {
            break label859;
          }
        }
        int k = RecyclerView.this.mAdapterHelper.findPositionOffset(paramInt);
        ((RecyclerView.ViewHolder)localObject).mOwnerRecyclerView = RecyclerView.this;
        RecyclerView.Adapter localAdapter1 = RecyclerView.this.mAdapter;
        ((RecyclerView.ViewHolder)localObject).mPosition = k;
        if (localAdapter1.mHasStableIds) {
          ((RecyclerView.ViewHolder)localObject).mItemId = localAdapter1.getItemId(k);
        }
        ((RecyclerView.ViewHolder)localObject).setFlags(1, 519);
        TraceCompat.beginSection("RV OnBindView");
        ((RecyclerView.ViewHolder)localObject).getUnmodifiedPayloads();
        localAdapter1.onBindViewHolder((RecyclerView.ViewHolder)localObject, k);
        ((RecyclerView.ViewHolder)localObject).clearPayload();
        TraceCompat.endSection();
        View localView1 = ((RecyclerView.ViewHolder)localObject).itemView;
        if (RecyclerView.this.isAccessibilityEnabled())
        {
          if (ViewCompat.getImportantForAccessibility(localView1) == 0) {
            ViewCompat.setImportantForAccessibility(localView1, 1);
          }
          if (!ViewCompat.hasAccessibilityDelegate(localView1)) {
            ViewCompat.setAccessibilityDelegate(localView1, RecyclerView.this.mAccessibilityDelegate.mItemDelegate);
          }
        }
        j = 1;
        if (!RecyclerView.this.mState.mInPreLayout) {
          break label859;
        }
        ((RecyclerView.ViewHolder)localObject).mPreLayoutPosition = paramInt;
        break label859;
        label1140:
        if (!RecyclerView.this.checkLayoutParams(localLayoutParams))
        {
          localLayoutParams1 = (RecyclerView.LayoutParams)RecyclerView.this.generateLayoutParams(localLayoutParams);
          ((RecyclerView.ViewHolder)localObject).itemView.setLayoutParams(localLayoutParams1);
          break label896;
        }
        localLayoutParams1 = (RecyclerView.LayoutParams)localLayoutParams;
        break label896;
      }
    }
    
    final void quickRecycleScrapView(View paramView)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      RecyclerView.ViewHolder.access$4802$25172c8a(localViewHolder);
      RecyclerView.ViewHolder.access$4902$763f3ae0(localViewHolder);
      localViewHolder.clearReturnedFromScrapFlag();
      recycleViewHolderInternal(localViewHolder);
    }
    
    final void recycleAndClearCachedViews()
    {
      for (int i = -1 + this.mCachedViews.size(); i >= 0; i--) {
        recycleCachedViewAt(i);
      }
      this.mCachedViews.clear();
    }
    
    final void recycleCachedViewAt(int paramInt)
    {
      addViewHolderToRecycledViewPool((RecyclerView.ViewHolder)this.mCachedViews.get(paramInt));
      this.mCachedViews.remove(paramInt);
    }
    
    public final void recycleView(View paramView)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      if (localViewHolder.isTmpDetached()) {
        RecyclerView.this.removeDetachedView(paramView, false);
      }
      if (localViewHolder.isScrap()) {
        localViewHolder.unScrap();
      }
      for (;;)
      {
        recycleViewHolderInternal(localViewHolder);
        return;
        if (localViewHolder.wasReturnedFromScrap()) {
          localViewHolder.clearReturnedFromScrapFlag();
        }
      }
    }
    
    final void recycleViewHolderInternal(RecyclerView.ViewHolder paramViewHolder)
    {
      boolean bool1 = true;
      if ((paramViewHolder.isScrap()) || (paramViewHolder.itemView.getParent() != null))
      {
        StringBuilder localStringBuilder = new StringBuilder("Scrapped or attached views may not be recycled. isScrap:").append(paramViewHolder.isScrap()).append(" isAttached:");
        if (paramViewHolder.itemView.getParent() != null) {}
        for (;;)
        {
          throw new IllegalArgumentException(bool1);
          bool1 = false;
        }
      }
      if (paramViewHolder.isTmpDetached()) {
        throw new IllegalArgumentException("Tmp detached view should be removed from RecyclerView before it can be recycled: " + paramViewHolder);
      }
      if (paramViewHolder.shouldIgnore()) {
        throw new IllegalArgumentException("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle.");
      }
      boolean bool2 = RecyclerView.ViewHolder.access$4700(paramViewHolder);
      if ((RecyclerView.this.mAdapter != null) && (bool2) && (RecyclerView.this.mAdapter.onFailedToRecycleView$cb3a904())) {}
      for (boolean bool3 = bool1;; bool3 = false)
      {
        int i;
        int j;
        if (!bool3)
        {
          boolean bool5 = paramViewHolder.isRecyclable();
          i = 0;
          j = 0;
          if (!bool5) {}
        }
        else
        {
          boolean bool4 = paramViewHolder.hasAnyOfTheFlags(14);
          i = 0;
          if (!bool4)
          {
            int k = this.mCachedViews.size();
            if ((k == this.mViewCacheMax) && (k > 0)) {
              recycleCachedViewAt(0);
            }
            int m = this.mViewCacheMax;
            i = 0;
            if (k < m)
            {
              this.mCachedViews.add(paramViewHolder);
              i = 1;
            }
          }
          j = 0;
          if (i == 0)
          {
            addViewHolderToRecycledViewPool(paramViewHolder);
            j = 1;
          }
        }
        RecyclerView.this.mViewInfoStore.removeViewHolder(paramViewHolder);
        if ((i == 0) && (j == 0) && (bool2)) {
          paramViewHolder.mOwnerRecyclerView = null;
        }
        return;
      }
    }
    
    final void scrapView(View paramView)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      if ((!localViewHolder.isUpdated()) || (localViewHolder.isInvalid()) || (RecyclerView.access$5000(RecyclerView.this, localViewHolder)))
      {
        if ((localViewHolder.isInvalid()) && (!localViewHolder.isRemoved()) && (!RecyclerView.this.mAdapter.mHasStableIds)) {
          throw new IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool.");
        }
        localViewHolder.setScrapContainer(this, false);
        this.mAttachedScrap.add(localViewHolder);
        return;
      }
      if (this.mChangedScrap == null) {
        this.mChangedScrap = new ArrayList();
      }
      localViewHolder.setScrapContainer(this, true);
      this.mChangedScrap.add(localViewHolder);
    }
    
    final void unscrapView(RecyclerView.ViewHolder paramViewHolder)
    {
      if (RecyclerView.ViewHolder.access$4900(paramViewHolder)) {
        this.mChangedScrap.remove(paramViewHolder);
      }
      for (;;)
      {
        RecyclerView.ViewHolder.access$4802$25172c8a(paramViewHolder);
        RecyclerView.ViewHolder.access$4902$763f3ae0(paramViewHolder);
        paramViewHolder.clearReturnedFromScrapFlag();
        return;
        this.mAttachedScrap.remove(paramViewHolder);
      }
    }
  }
  
  public static abstract interface RecyclerListener {}
  
  private final class RecyclerViewDataObserver
    extends RecyclerView.AdapterDataObserver
  {
    private RecyclerViewDataObserver() {}
    
    private void triggerUpdateProcessor()
    {
      if ((RecyclerView.this.mPostUpdatesOnAnimation) && (RecyclerView.this.mHasFixedSize) && (RecyclerView.this.mIsAttached))
      {
        ViewCompat.postOnAnimation(RecyclerView.this, RecyclerView.this.mUpdateChildViewsRunnable);
        return;
      }
      RecyclerView.access$4302$767d6395(RecyclerView.this);
      RecyclerView.this.requestLayout();
    }
    
    public final void onChanged()
    {
      RecyclerView.this.assertNotInLayoutOrScroll(null);
      RecyclerView.this.mState.mStructureChanged = true;
      RecyclerView.this.setDataSetChangedAfterLayout();
      if (!RecyclerView.this.mAdapterHelper.hasPendingUpdates()) {
        RecyclerView.this.requestLayout();
      }
    }
    
    public final void onItemRangeChanged(int paramInt1, int paramInt2, Object paramObject)
    {
      int i = 1;
      RecyclerView.this.assertNotInLayoutOrScroll(null);
      AdapterHelper localAdapterHelper = RecyclerView.this.mAdapterHelper;
      localAdapterHelper.mPendingUpdates.add(localAdapterHelper.obtainUpdateOp(4, paramInt1, paramInt2, null));
      localAdapterHelper.mExistingUpdateTypes = (0x4 | localAdapterHelper.mExistingUpdateTypes);
      if (localAdapterHelper.mPendingUpdates.size() == i) {}
      for (;;)
      {
        if (i != 0) {
          triggerUpdateProcessor();
        }
        return;
        i = 0;
      }
    }
    
    public final void onItemRangeInserted(int paramInt1, int paramInt2)
    {
      int i = 1;
      RecyclerView.this.assertNotInLayoutOrScroll(null);
      AdapterHelper localAdapterHelper = RecyclerView.this.mAdapterHelper;
      localAdapterHelper.mPendingUpdates.add(localAdapterHelper.obtainUpdateOp(i, paramInt1, paramInt2, null));
      localAdapterHelper.mExistingUpdateTypes = (0x1 | localAdapterHelper.mExistingUpdateTypes);
      if (localAdapterHelper.mPendingUpdates.size() == i) {}
      for (;;)
      {
        if (i != 0) {
          triggerUpdateProcessor();
        }
        return;
        i = 0;
      }
    }
    
    public final void onItemRangeRemoved(int paramInt1, int paramInt2)
    {
      int i = 1;
      RecyclerView.this.assertNotInLayoutOrScroll(null);
      AdapterHelper localAdapterHelper = RecyclerView.this.mAdapterHelper;
      localAdapterHelper.mPendingUpdates.add(localAdapterHelper.obtainUpdateOp(2, paramInt1, i, null));
      localAdapterHelper.mExistingUpdateTypes = (0x2 | localAdapterHelper.mExistingUpdateTypes);
      if (localAdapterHelper.mPendingUpdates.size() == i) {}
      for (;;)
      {
        if (i != 0) {
          triggerUpdateProcessor();
        }
        return;
        i = 0;
      }
    }
  }
  
  public static class SavedState
    extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() {};
    Parcelable mLayoutState;
    
    SavedState(Parcel paramParcel)
    {
      super();
      this.mLayoutState = paramParcel.readParcelable(RecyclerView.LayoutManager.class.getClassLoader());
    }
    
    SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeParcelable(this.mLayoutState, 0);
    }
  }
  
  public static abstract class SmoothScroller
  {
    RecyclerView.LayoutManager mLayoutManager;
    boolean mPendingInitialRun;
    RecyclerView mRecyclerView;
    private final Action mRecyclingAction = new Action();
    boolean mRunning;
    public int mTargetPosition = -1;
    View mTargetView;
    
    public abstract void onSeekTargetStep$64702b56(int paramInt1, int paramInt2, Action paramAction);
    
    public abstract void onStop();
    
    public abstract void onTargetFound$68abd3fe(View paramView, Action paramAction);
    
    protected final void stop()
    {
      if (!this.mRunning) {
        return;
      }
      onStop();
      this.mRecyclerView.mState.mTargetPosition = -1;
      this.mTargetView = null;
      this.mTargetPosition = -1;
      this.mPendingInitialRun = false;
      this.mRunning = false;
      RecyclerView.LayoutManager.access$5800(this.mLayoutManager, this);
      this.mLayoutManager = null;
      this.mRecyclerView = null;
    }
    
    public static final class Action
    {
      public boolean changed = false;
      private int consecutiveUpdates = 0;
      public int mDuration = -2147483648;
      public int mDx = 0;
      private int mDy = 0;
      public Interpolator mInterpolator = null;
      int mJumpToPosition = -1;
      
      public Action()
      {
        this(0, 0);
      }
      
      private Action(int paramInt1, int paramInt2) {}
      
      public final void update(int paramInt1, int paramInt2, int paramInt3, Interpolator paramInterpolator)
      {
        this.mDx = paramInt1;
        this.mDy = paramInt2;
        this.mDuration = paramInt3;
        this.mInterpolator = paramInterpolator;
        this.changed = true;
      }
    }
  }
  
  public static final class State
  {
    private SparseArray<Object> mData;
    int mDeletedInvisibleItemCountSincePreviousLayout = 0;
    boolean mInPreLayout = false;
    int mItemCount = 0;
    int mPreviousLayoutItemCount = 0;
    boolean mRunPredictiveAnimations = false;
    boolean mRunSimpleAnimations = false;
    boolean mStructureChanged = false;
    int mTargetPosition = -1;
    boolean mTrackOldChangeHolders = false;
    
    public final int getItemCount()
    {
      if (this.mInPreLayout) {
        return this.mPreviousLayoutItemCount - this.mDeletedInvisibleItemCountSincePreviousLayout;
      }
      return this.mItemCount;
    }
    
    public final String toString()
    {
      return "State{mTargetPosition=" + this.mTargetPosition + ", mData=" + this.mData + ", mItemCount=" + this.mItemCount + ", mPreviousLayoutItemCount=" + this.mPreviousLayoutItemCount + ", mDeletedInvisibleItemCountSincePreviousLayout=" + this.mDeletedInvisibleItemCountSincePreviousLayout + ", mStructureChanged=" + this.mStructureChanged + ", mInPreLayout=" + this.mInPreLayout + ", mRunSimpleAnimations=" + this.mRunSimpleAnimations + ", mRunPredictiveAnimations=" + this.mRunPredictiveAnimations + '}';
    }
  }
  
  public static abstract class ViewCacheExtension
  {
    public abstract View getViewForPositionAndType$430f8374();
  }
  
  private final class ViewFlinger
    implements Runnable
  {
    private boolean mEatRunOnAnimationRequest = false;
    private Interpolator mInterpolator = RecyclerView.sQuinticInterpolator;
    int mLastFlingX;
    int mLastFlingY;
    private boolean mReSchedulePostAnimationCallback = false;
    ScrollerCompat mScroller = ScrollerCompat.create(RecyclerView.this.getContext(), RecyclerView.sQuinticInterpolator);
    
    public ViewFlinger() {}
    
    final void postOnAnimation()
    {
      if (this.mEatRunOnAnimationRequest)
      {
        this.mReSchedulePostAnimationCallback = true;
        return;
      }
      RecyclerView.this.removeCallbacks(this);
      ViewCompat.postOnAnimation(RecyclerView.this, this);
    }
    
    public final void run()
    {
      this.mReSchedulePostAnimationCallback = false;
      this.mEatRunOnAnimationRequest = true;
      RecyclerView.this.consumePendingUpdateOperations();
      ScrollerCompat localScrollerCompat = this.mScroller;
      RecyclerView.SmoothScroller localSmoothScroller = RecyclerView.this.mLayout.mSmoothScroller;
      int k;
      int m;
      int i1;
      int i2;
      int i10;
      int i4;
      int i5;
      label358:
      int i6;
      label378:
      RecyclerView localRecyclerView;
      label417:
      label439:
      int i7;
      label561:
      int i8;
      label589:
      int i9;
      if (localScrollerCompat.computeScrollOffset())
      {
        int i = localScrollerCompat.getCurrX();
        int j = localScrollerCompat.getCurrY();
        k = i - this.mLastFlingX;
        m = j - this.mLastFlingY;
        this.mLastFlingX = i;
        this.mLastFlingY = j;
        RecyclerView.Adapter localAdapter = RecyclerView.this.mAdapter;
        int n = 0;
        i1 = 0;
        i2 = 0;
        int i3 = 0;
        if (localAdapter != null)
        {
          RecyclerView.this.eatRequestLayout();
          RecyclerView.this.onEnterLayoutOrScroll();
          TraceCompat.beginSection("RV Scroll");
          n = 0;
          i1 = 0;
          if (k != 0)
          {
            n = RecyclerView.this.mLayout.scrollHorizontallyBy(k, RecyclerView.this.mRecycler, RecyclerView.this.mState);
            i1 = k - n;
          }
          i2 = 0;
          i3 = 0;
          if (m != 0)
          {
            i3 = RecyclerView.this.mLayout.scrollVerticallyBy(m, RecyclerView.this.mRecycler, RecyclerView.this.mState);
            i2 = m - i3;
          }
          TraceCompat.endSection();
          RecyclerView.this.repositionShadowingViews();
          RecyclerView.this.onExitLayoutOrScroll();
          RecyclerView.this.resumeRequestLayout(false);
          if ((localSmoothScroller != null) && (!localSmoothScroller.mPendingInitialRun) && (localSmoothScroller.mRunning))
          {
            i10 = RecyclerView.this.mState.getItemCount();
            if (i10 != 0) {
              break label677;
            }
            localSmoothScroller.stop();
          }
        }
        if (!RecyclerView.this.mItemDecorations.isEmpty()) {
          RecyclerView.this.invalidate();
        }
        if (ViewCompat.getOverScrollMode(RecyclerView.this) != 2) {
          RecyclerView.this.considerReleasingGlowsOnScroll(k, m);
        }
        if ((i1 != 0) || (i2 != 0))
        {
          i4 = (int)localScrollerCompat.getCurrVelocity();
          i5 = 0;
          if (i1 != i)
          {
            if (i1 >= 0) {
              break label711;
            }
            i5 = -i4;
          }
          i6 = 0;
          if (i2 != j)
          {
            if (i2 >= 0) {
              break label729;
            }
            i6 = -i4;
          }
          if (ViewCompat.getOverScrollMode(RecyclerView.this) != 2)
          {
            localRecyclerView = RecyclerView.this;
            if (i5 >= 0) {
              break label747;
            }
            localRecyclerView.ensureLeftGlow();
            localRecyclerView.mLeftGlow.onAbsorb(-i5);
            if (i6 >= 0) {
              break label771;
            }
            localRecyclerView.ensureTopGlow();
            localRecyclerView.mTopGlow.onAbsorb(-i6);
            if ((i5 != 0) || (i6 != 0)) {
              ViewCompat.postInvalidateOnAnimation(localRecyclerView);
            }
          }
          if (((i5 != 0) || (i1 == i) || (localScrollerCompat.getFinalX() == 0)) && ((i6 != 0) || (i2 == j) || (localScrollerCompat.getFinalY() == 0))) {
            localScrollerCompat.abortAnimation();
          }
        }
        if ((n != 0) || (i3 != 0)) {
          RecyclerView.this.dispatchOnScrolled(n, i3);
        }
        if (!RecyclerView.this.awakenScrollBars()) {
          RecyclerView.this.invalidate();
        }
        if ((m == 0) || (!RecyclerView.this.mLayout.canScrollVertically()) || (i3 != m)) {
          break label795;
        }
        i7 = 1;
        if ((k == 0) || (!RecyclerView.this.mLayout.canScrollHorizontally()) || (n != k)) {
          break label801;
        }
        i8 = 1;
        if (((k != 0) || (m != 0)) && (i8 == 0) && (i7 == 0)) {
          break label807;
        }
        i9 = 1;
        label612:
        if ((!localScrollerCompat.isFinished()) && (i9 != 0)) {
          break label813;
        }
        RecyclerView.this.setScrollState(0);
      }
      for (;;)
      {
        if (localSmoothScroller != null)
        {
          if (localSmoothScroller.mPendingInitialRun) {
            RecyclerView.SmoothScroller.access$3300(localSmoothScroller, 0, 0);
          }
          if (!this.mReSchedulePostAnimationCallback) {
            localSmoothScroller.stop();
          }
        }
        this.mEatRunOnAnimationRequest = false;
        if (this.mReSchedulePostAnimationCallback) {
          postOnAnimation();
        }
        return;
        label677:
        if (localSmoothScroller.mTargetPosition >= i10) {
          localSmoothScroller.mTargetPosition = (i10 - 1);
        }
        RecyclerView.SmoothScroller.access$3300(localSmoothScroller, k - i1, m - i2);
        break;
        label711:
        if (i1 > 0)
        {
          i5 = i4;
          break label358;
        }
        i5 = 0;
        break label358;
        label729:
        if (i2 > 0)
        {
          i6 = i4;
          break label378;
        }
        i6 = 0;
        break label378;
        label747:
        if (i5 <= 0) {
          break label417;
        }
        localRecyclerView.ensureRightGlow();
        localRecyclerView.mRightGlow.onAbsorb(i5);
        break label417;
        label771:
        if (i6 <= 0) {
          break label439;
        }
        localRecyclerView.ensureBottomGlow();
        localRecyclerView.mBottomGlow.onAbsorb(i6);
        break label439;
        label795:
        i7 = 0;
        break label561;
        label801:
        i8 = 0;
        break label589;
        label807:
        i9 = 0;
        break label612;
        label813:
        postOnAnimation();
      }
    }
    
    public final void smoothScrollBy(int paramInt1, int paramInt2)
    {
      int i = Math.abs(paramInt1);
      int j = Math.abs(paramInt2);
      int k;
      int m;
      int n;
      if (i > j)
      {
        k = 1;
        m = (int)Math.sqrt(0.0D);
        n = (int)Math.sqrt(paramInt1 * paramInt1 + paramInt2 * paramInt2);
        if (k == 0) {
          break label149;
        }
      }
      int i4;
      label149:
      for (int i1 = RecyclerView.this.getWidth();; i1 = RecyclerView.this.getHeight())
      {
        int i2 = i1 / 2;
        float f1 = Math.min(1.0F, 1.0F * n / i1);
        float f2 = i2 + i2 * (float)Math.sin((float)(0.47123891676382D * (f1 - 0.5F)));
        if (m <= 0) {
          break label161;
        }
        i4 = 4 * Math.round(1000.0F * Math.abs(f2 / m));
        smoothScrollBy(paramInt1, paramInt2, Math.min(i4, 2000));
        return;
        k = 0;
        break;
      }
      label161:
      if (k != 0) {}
      for (int i3 = i;; i3 = j)
      {
        i4 = (int)(300.0F * (1.0F + i3 / i1));
        break;
      }
    }
    
    public final void smoothScrollBy(int paramInt1, int paramInt2, int paramInt3)
    {
      smoothScrollBy(paramInt1, paramInt2, paramInt3, RecyclerView.sQuinticInterpolator);
    }
    
    public final void smoothScrollBy(int paramInt1, int paramInt2, int paramInt3, Interpolator paramInterpolator)
    {
      if (this.mInterpolator != paramInterpolator)
      {
        this.mInterpolator = paramInterpolator;
        this.mScroller = ScrollerCompat.create(RecyclerView.this.getContext(), paramInterpolator);
      }
      RecyclerView.this.setScrollState(2);
      this.mLastFlingY = 0;
      this.mLastFlingX = 0;
      this.mScroller.startScroll(0, 0, paramInt1, paramInt2, paramInt3);
      postOnAnimation();
    }
  }
  
  public static abstract class ViewHolder
  {
    private static final List<Object> FULLUPDATE_PAYLOADS = Collections.EMPTY_LIST;
    public final View itemView;
    private int mFlags;
    private boolean mInChangeScrap = false;
    private int mIsRecyclableCount = 0;
    long mItemId = -1L;
    public int mItemViewType = -1;
    int mOldPosition = -1;
    RecyclerView mOwnerRecyclerView;
    List<Object> mPayloads = null;
    int mPosition = -1;
    int mPreLayoutPosition = -1;
    private RecyclerView.Recycler mScrapContainer = null;
    ViewHolder mShadowedHolder = null;
    ViewHolder mShadowingHolder = null;
    List<Object> mUnmodifiedPayloads = null;
    private int mWasImportantForAccessibilityBeforeHidden = 0;
    
    public ViewHolder(View paramView)
    {
      if (paramView == null) {
        throw new IllegalArgumentException("itemView may not be null");
      }
      this.itemView = paramView;
    }
    
    final void addChangePayload(Object paramObject)
    {
      if (paramObject == null) {
        addFlags(1024);
      }
      while ((0x400 & this.mFlags) != 0) {
        return;
      }
      if (this.mPayloads == null)
      {
        this.mPayloads = new ArrayList();
        this.mUnmodifiedPayloads = Collections.unmodifiableList(this.mPayloads);
      }
      this.mPayloads.add(paramObject);
    }
    
    final void addFlags(int paramInt)
    {
      this.mFlags = (paramInt | this.mFlags);
    }
    
    final void clearOldPosition()
    {
      this.mOldPosition = -1;
      this.mPreLayoutPosition = -1;
    }
    
    final void clearPayload()
    {
      if (this.mPayloads != null) {
        this.mPayloads.clear();
      }
      this.mFlags = (0xFFFFFBFF & this.mFlags);
    }
    
    final void clearReturnedFromScrapFlag()
    {
      this.mFlags = (0xFFFFFFDF & this.mFlags);
    }
    
    final void clearTmpDetachFlag()
    {
      this.mFlags = (0xFFFFFEFF & this.mFlags);
    }
    
    public final int getAdapterPosition()
    {
      if (this.mOwnerRecyclerView == null) {
        return -1;
      }
      return RecyclerView.access$5500(this.mOwnerRecyclerView, this);
    }
    
    public final int getLayoutPosition()
    {
      if (this.mPreLayoutPosition == -1) {
        return this.mPosition;
      }
      return this.mPreLayoutPosition;
    }
    
    final List<Object> getUnmodifiedPayloads()
    {
      if ((0x400 & this.mFlags) == 0)
      {
        if ((this.mPayloads == null) || (this.mPayloads.size() == 0)) {
          return FULLUPDATE_PAYLOADS;
        }
        return this.mUnmodifiedPayloads;
      }
      return FULLUPDATE_PAYLOADS;
    }
    
    final boolean hasAnyOfTheFlags(int paramInt)
    {
      return (paramInt & this.mFlags) != 0;
    }
    
    final boolean isBound()
    {
      return (0x1 & this.mFlags) != 0;
    }
    
    final boolean isInvalid()
    {
      return (0x4 & this.mFlags) != 0;
    }
    
    public final boolean isRecyclable()
    {
      return ((0x10 & this.mFlags) == 0) && (!ViewCompat.hasTransientState(this.itemView));
    }
    
    final boolean isRemoved()
    {
      return (0x8 & this.mFlags) != 0;
    }
    
    final boolean isScrap()
    {
      return this.mScrapContainer != null;
    }
    
    final boolean isTmpDetached()
    {
      return (0x100 & this.mFlags) != 0;
    }
    
    final boolean isUpdated()
    {
      return (0x2 & this.mFlags) != 0;
    }
    
    final boolean needsUpdate()
    {
      return (0x2 & this.mFlags) != 0;
    }
    
    final void offsetPosition(int paramInt, boolean paramBoolean)
    {
      if (this.mOldPosition == -1) {
        this.mOldPosition = this.mPosition;
      }
      if (this.mPreLayoutPosition == -1) {
        this.mPreLayoutPosition = this.mPosition;
      }
      if (paramBoolean) {
        this.mPreLayoutPosition = (paramInt + this.mPreLayoutPosition);
      }
      this.mPosition = (paramInt + this.mPosition);
      if (this.itemView.getLayoutParams() != null) {
        ((RecyclerView.LayoutParams)this.itemView.getLayoutParams()).mInsetsDirty = true;
      }
    }
    
    final void resetInternal()
    {
      this.mFlags = 0;
      this.mPosition = -1;
      this.mOldPosition = -1;
      this.mItemId = -1L;
      this.mPreLayoutPosition = -1;
      this.mIsRecyclableCount = 0;
      this.mShadowedHolder = null;
      this.mShadowingHolder = null;
      clearPayload();
      this.mWasImportantForAccessibilityBeforeHidden = 0;
    }
    
    final void setFlags(int paramInt1, int paramInt2)
    {
      this.mFlags = (this.mFlags & (paramInt2 ^ 0xFFFFFFFF) | paramInt1 & paramInt2);
    }
    
    public final void setIsRecyclable(boolean paramBoolean)
    {
      int i;
      if (paramBoolean)
      {
        i = -1 + this.mIsRecyclableCount;
        this.mIsRecyclableCount = i;
        if (this.mIsRecyclableCount >= 0) {
          break label61;
        }
        this.mIsRecyclableCount = 0;
        Log.e("View", "isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this);
      }
      label61:
      do
      {
        return;
        i = 1 + this.mIsRecyclableCount;
        break;
        if ((!paramBoolean) && (this.mIsRecyclableCount == 1))
        {
          this.mFlags = (0x10 | this.mFlags);
          return;
        }
      } while ((!paramBoolean) || (this.mIsRecyclableCount != 0));
      this.mFlags = (0xFFFFFFEF & this.mFlags);
    }
    
    final void setScrapContainer(RecyclerView.Recycler paramRecycler, boolean paramBoolean)
    {
      this.mScrapContainer = paramRecycler;
      this.mInChangeScrap = paramBoolean;
    }
    
    final boolean shouldIgnore()
    {
      return (0x80 & this.mFlags) != 0;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder1 = new StringBuilder("ViewHolder{" + Integer.toHexString(hashCode()) + " position=" + this.mPosition + " id=" + this.mItemId + ", oldPos=" + this.mOldPosition + ", pLpos:" + this.mPreLayoutPosition);
      String str;
      if (isScrap())
      {
        StringBuilder localStringBuilder2 = localStringBuilder1.append(" scrap ");
        if (this.mInChangeScrap)
        {
          str = "[changeScrap]";
          localStringBuilder2.append(str);
        }
      }
      else
      {
        if (isInvalid()) {
          localStringBuilder1.append(" invalid");
        }
        if (!isBound()) {
          localStringBuilder1.append(" unbound");
        }
        if (needsUpdate()) {
          localStringBuilder1.append(" update");
        }
        if (isRemoved()) {
          localStringBuilder1.append(" removed");
        }
        if (shouldIgnore()) {
          localStringBuilder1.append(" ignored");
        }
        if (isTmpDetached()) {
          localStringBuilder1.append(" tmpDetached");
        }
        if (!isRecyclable()) {
          localStringBuilder1.append(" not recyclable(" + this.mIsRecyclableCount + ")");
        }
        if (((0x200 & this.mFlags) == 0) && (!isInvalid())) {
          break label307;
        }
      }
      label307:
      for (int i = 1;; i = 0)
      {
        if (i != 0) {
          localStringBuilder1.append(" undefined adapter position");
        }
        if (this.itemView.getParent() == null) {
          localStringBuilder1.append(" no parent");
        }
        localStringBuilder1.append("}");
        return localStringBuilder1.toString();
        str = "[attachedScrap]";
        break;
      }
    }
    
    final void unScrap()
    {
      this.mScrapContainer.unscrapView(this);
    }
    
    final boolean wasReturnedFromScrap()
    {
      return (0x20 & this.mFlags) != 0;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.RecyclerView
 * JD-Core Version:    0.7.0.1
 */