package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.text.TextUtilsCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.widget.AppCompatPopupWindow;
import android.support.v7.internal.widget.ListViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ListPopupWindow
{
  private static Method sClipToWindowEnabledMethod;
  private static Method sGetMaxAvailableHeightMethod;
  private ListAdapter mAdapter;
  private Context mContext;
  private boolean mDropDownAlwaysVisible = false;
  public View mDropDownAnchorView;
  public int mDropDownGravity = 0;
  private int mDropDownHeight = -2;
  int mDropDownHorizontalOffset;
  public DropDownListView mDropDownList;
  private Drawable mDropDownListHighlight;
  int mDropDownVerticalOffset;
  boolean mDropDownVerticalOffsetSet;
  int mDropDownWidth = -2;
  private int mDropDownWindowLayoutType = 1002;
  private boolean mForceIgnoreOutsideTouch = false;
  private final Handler mHandler;
  private final ListSelectorHider mHideSelector = new ListSelectorHider((byte)0);
  public AdapterView.OnItemClickListener mItemClickListener;
  private AdapterView.OnItemSelectedListener mItemSelectedListener;
  private int mLayoutDirection;
  int mListItemExpandMaximum = 2147483647;
  private boolean mModal;
  private DataSetObserver mObserver;
  public PopupWindow mPopup;
  int mPromptPosition = 0;
  private View mPromptView;
  private final ResizePopupRunnable mResizePopupRunnable = new ResizePopupRunnable((byte)0);
  private final PopupScrollListener mScrollListener = new PopupScrollListener((byte)0);
  private Runnable mShowDropDownRunnable;
  private Rect mTempRect = new Rect();
  private final PopupTouchInterceptor mTouchInterceptor = new PopupTouchInterceptor((byte)0);
  
  static
  {
    try
    {
      Class[] arrayOfClass2 = new Class[1];
      arrayOfClass2[0] = Boolean.TYPE;
      sClipToWindowEnabledMethod = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", arrayOfClass2);
    }
    catch (NoSuchMethodException localNoSuchMethodException1)
    {
      for (;;)
      {
        try
        {
          Class[] arrayOfClass1 = new Class[3];
          arrayOfClass1[0] = View.class;
          arrayOfClass1[1] = Integer.TYPE;
          arrayOfClass1[2] = Boolean.TYPE;
          sGetMaxAvailableHeightMethod = PopupWindow.class.getDeclaredMethod("getMaxAvailableHeight", arrayOfClass1);
          return;
        }
        catch (NoSuchMethodException localNoSuchMethodException2)
        {
          Log.i("ListPopupWindow", "Could not find method getMaxAvailableHeight(View, int, boolean) on PopupWindow. Oh well.");
        }
        localNoSuchMethodException1 = localNoSuchMethodException1;
        Log.i("ListPopupWindow", "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
      }
    }
  }
  
  public ListPopupWindow(Context paramContext)
  {
    this(paramContext, null, R.attr.listPopupWindowStyle);
  }
  
  public ListPopupWindow(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, 0);
  }
  
  public ListPopupWindow(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    this.mContext = paramContext;
    this.mHandler = new Handler(paramContext.getMainLooper());
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ListPopupWindow, paramInt1, paramInt2);
    this.mDropDownHorizontalOffset = localTypedArray.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownHorizontalOffset, 0);
    this.mDropDownVerticalOffset = localTypedArray.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownVerticalOffset, 0);
    if (this.mDropDownVerticalOffset != 0) {
      this.mDropDownVerticalOffsetSet = true;
    }
    localTypedArray.recycle();
    this.mPopup = new AppCompatPopupWindow(paramContext, paramAttributeSet, paramInt1);
    this.mPopup.setInputMethodMode(1);
    this.mLayoutDirection = TextUtilsCompat.getLayoutDirectionFromLocale(this.mContext.getResources().getConfiguration().locale);
  }
  
  private int getMaxAvailableHeight(View paramView, int paramInt, boolean paramBoolean)
  {
    if (sGetMaxAvailableHeightMethod != null) {
      try
      {
        Method localMethod = sGetMaxAvailableHeightMethod;
        PopupWindow localPopupWindow = this.mPopup;
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = paramView;
        arrayOfObject[1] = Integer.valueOf(paramInt);
        arrayOfObject[2] = Boolean.valueOf(paramBoolean);
        int i = ((Integer)localMethod.invoke(localPopupWindow, arrayOfObject)).intValue();
        return i;
      }
      catch (Exception localException)
      {
        Log.i("ListPopupWindow", "Could not call getMaxAvailableHeightMethod(View, int, boolean) on PopupWindow. Using the public version.");
      }
    }
    return this.mPopup.getMaxAvailableHeight(paramView, paramInt);
  }
  
  public final void clearListSelection()
  {
    DropDownListView localDropDownListView = this.mDropDownList;
    if (localDropDownListView != null)
    {
      DropDownListView.access$502(localDropDownListView, true);
      localDropDownListView.requestLayout();
    }
  }
  
  public final void dismiss()
  {
    this.mPopup.dismiss();
    if (this.mPromptView != null)
    {
      ViewParent localViewParent = this.mPromptView.getParent();
      if ((localViewParent instanceof ViewGroup)) {
        ((ViewGroup)localViewParent).removeView(this.mPromptView);
      }
    }
    this.mPopup.setContentView(null);
    this.mDropDownList = null;
    this.mHandler.removeCallbacks(this.mResizePopupRunnable);
  }
  
  public final boolean isInputMethodNotNeeded()
  {
    return this.mPopup.getInputMethodMode() == 2;
  }
  
  public void setAdapter(ListAdapter paramListAdapter)
  {
    if (this.mObserver == null) {
      this.mObserver = new PopupDataSetObserver((byte)0);
    }
    for (;;)
    {
      this.mAdapter = paramListAdapter;
      if (this.mAdapter != null) {
        paramListAdapter.registerDataSetObserver(this.mObserver);
      }
      if (this.mDropDownList != null) {
        this.mDropDownList.setAdapter(this.mAdapter);
      }
      return;
      if (this.mAdapter != null) {
        this.mAdapter.unregisterDataSetObserver(this.mObserver);
      }
    }
  }
  
  public final void setBackgroundDrawable(Drawable paramDrawable)
  {
    this.mPopup.setBackgroundDrawable(paramDrawable);
  }
  
  public final void setContentWidth(int paramInt)
  {
    Drawable localDrawable = this.mPopup.getBackground();
    if (localDrawable != null)
    {
      localDrawable.getPadding(this.mTempRect);
      this.mDropDownWidth = (paramInt + (this.mTempRect.left + this.mTempRect.right));
      return;
    }
    this.mDropDownWidth = paramInt;
  }
  
  public final void setInputMethodMode$13462e()
  {
    this.mPopup.setInputMethodMode(2);
  }
  
  public final void setModal$1385ff()
  {
    this.mModal = true;
    this.mPopup.setFocusable(true);
  }
  
  public final void setOnDismissListener(PopupWindow.OnDismissListener paramOnDismissListener)
  {
    this.mPopup.setOnDismissListener(paramOnDismissListener);
  }
  
  public void show()
  {
    int i = 1;
    DropDownListView localDropDownListView;
    View localView3;
    LinearLayout localLinearLayout;
    LinearLayout.LayoutParams localLayoutParams2;
    label250:
    int i18;
    int i17;
    label268:
    int j;
    Object localObject;
    if (this.mDropDownList == null)
    {
      Context localContext = this.mContext;
      this.mShowDropDownRunnable = new Runnable()
      {
        public final void run()
        {
          View localView = ListPopupWindow.this.mDropDownAnchorView;
          if ((localView != null) && (localView.getWindowToken() != null)) {
            ListPopupWindow.this.show();
          }
        }
      };
      if (!this.mModal)
      {
        int i15 = i;
        this.mDropDownList = new DropDownListView(localContext, i15);
        if (this.mDropDownListHighlight != null) {
          this.mDropDownList.setSelector(this.mDropDownListHighlight);
        }
        this.mDropDownList.setAdapter(this.mAdapter);
        this.mDropDownList.setOnItemClickListener(this.mItemClickListener);
        this.mDropDownList.setFocusable(i);
        this.mDropDownList.setFocusableInTouchMode(i);
        this.mDropDownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
          public final void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
          {
            if (paramAnonymousInt != -1)
            {
              ListPopupWindow.DropDownListView localDropDownListView = ListPopupWindow.this.mDropDownList;
              if (localDropDownListView != null) {
                ListPopupWindow.DropDownListView.access$502(localDropDownListView, false);
              }
            }
          }
          
          public final void onNothingSelected(AdapterView<?> paramAnonymousAdapterView) {}
        });
        this.mDropDownList.setOnScrollListener(this.mScrollListener);
        if (this.mItemSelectedListener != null) {
          this.mDropDownList.setOnItemSelectedListener(this.mItemSelectedListener);
        }
        localDropDownListView = this.mDropDownList;
        localView3 = this.mPromptView;
        if (localView3 == null) {
          break label1283;
        }
        localLinearLayout = new LinearLayout(localContext);
        localLinearLayout.setOrientation(i);
        localLayoutParams2 = new LinearLayout.LayoutParams(-1, 0, 1.0F);
        switch (this.mPromptPosition)
        {
        default: 
          Log.e("ListPopupWindow", "Invalid hint position " + this.mPromptPosition);
          if (this.mDropDownWidth >= 0)
          {
            i18 = this.mDropDownWidth;
            i17 = -2147483648;
            localView3.measure(View.MeasureSpec.makeMeasureSpec(i18, i17), 0);
            LinearLayout.LayoutParams localLayoutParams3 = (LinearLayout.LayoutParams)localView3.getLayoutParams();
            j = localView3.getMeasuredHeight() + localLayoutParams3.topMargin + localLayoutParams3.bottomMargin;
            localObject = localLinearLayout;
          }
          break;
        }
      }
    }
    for (;;)
    {
      this.mPopup.setContentView((View)localObject);
      for (;;)
      {
        label323:
        Drawable localDrawable = this.mPopup.getBackground();
        int k;
        label383:
        int i1;
        label397:
        int i2;
        int i5;
        label473:
        int i6;
        label490:
        int i12;
        label512:
        label527:
        label547:
        int i9;
        label585:
        int i10;
        if (localDrawable != null)
        {
          localDrawable.getPadding(this.mTempRect);
          k = this.mTempRect.top + this.mTempRect.bottom;
          if (!this.mDropDownVerticalOffsetSet) {
            this.mDropDownVerticalOffset = (-this.mTempRect.top);
          }
          if (this.mPopup.getInputMethodMode() != 2) {
            break label722;
          }
          int m = i;
          i1 = getMaxAvailableHeight(this.mDropDownAnchorView, this.mDropDownVerticalOffset, m);
          if ((!this.mDropDownAlwaysVisible) && (this.mDropDownHeight != -1)) {
            break label728;
          }
          i2 = i1 + k;
          boolean bool2 = isInputMethodNotNeeded();
          PopupWindowCompat.setWindowLayoutType(this.mPopup, this.mDropDownWindowLayoutType);
          if (!this.mPopup.isShowing()) {
            break label1013;
          }
          if (this.mDropDownWidth != -1) {
            break label886;
          }
          i5 = -1;
          if (this.mDropDownHeight != -1) {
            break label969;
          }
          if (!bool2) {
            break label916;
          }
          i6 = i2;
          if (!bool2) {
            break label928;
          }
          PopupWindow localPopupWindow6 = this.mPopup;
          if (this.mDropDownWidth != -1) {
            break label922;
          }
          i12 = -1;
          localPopupWindow6.setWidth(i12);
          this.mPopup.setHeight(0);
          PopupWindow localPopupWindow3 = this.mPopup;
          if ((this.mForceIgnoreOutsideTouch) || (this.mDropDownAlwaysVisible)) {
            break label994;
          }
          localPopupWindow3.setOutsideTouchable(i);
          PopupWindow localPopupWindow4 = this.mPopup;
          View localView2 = this.mDropDownAnchorView;
          int i7 = this.mDropDownHorizontalOffset;
          int i8 = this.mDropDownVerticalOffset;
          if (i5 >= 0) {
            break label999;
          }
          i9 = -1;
          if (i6 >= 0) {
            break label1006;
          }
          i10 = -1;
          label593:
          localPopupWindow4.update(localView2, i7, i8, i9, i10);
        }
        for (;;)
        {
          return;
          int i16 = 0;
          break;
          localLinearLayout.addView(localDropDownListView, localLayoutParams2);
          localLinearLayout.addView(localView3);
          break label250;
          localLinearLayout.addView(localView3);
          localLinearLayout.addView(localDropDownListView, localLayoutParams2);
          break label250;
          i17 = 0;
          i18 = 0;
          break label268;
          this.mPopup.getContentView();
          View localView1 = this.mPromptView;
          if (localView1 != null)
          {
            LinearLayout.LayoutParams localLayoutParams1 = (LinearLayout.LayoutParams)localView1.getLayoutParams();
            j = localView1.getMeasuredHeight() + localLayoutParams1.topMargin + localLayoutParams1.bottomMargin;
            break label323;
            this.mTempRect.setEmpty();
            k = 0;
            break label383;
            label722:
            int n = 0;
            break label397;
            label728:
            int i13;
            switch (this.mDropDownWidth)
            {
            default: 
              i13 = View.MeasureSpec.makeMeasureSpec(this.mDropDownWidth, 1073741824);
            }
            for (;;)
            {
              int i14 = this.mDropDownList.measureHeightOfChildrenCompat$2e71581f$4868d301(i13, i1 - j);
              if (i14 > 0) {
                j += k;
              }
              i2 = i14 + j;
              break;
              i13 = View.MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right), -2147483648);
              continue;
              i13 = View.MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right), 1073741824);
            }
            label886:
            if (this.mDropDownWidth == -2)
            {
              i5 = this.mDropDownAnchorView.getWidth();
              break label473;
            }
            i5 = this.mDropDownWidth;
            break label473;
            label916:
            i6 = -1;
            break label490;
            label922:
            i12 = 0;
            break label512;
            label928:
            PopupWindow localPopupWindow5 = this.mPopup;
            if (this.mDropDownWidth == -1) {}
            for (int i11 = -1;; i11 = 0)
            {
              localPopupWindow5.setWidth(i11);
              this.mPopup.setHeight(-1);
              break;
            }
            label969:
            if (this.mDropDownHeight == -2)
            {
              i6 = i2;
              break label527;
            }
            i6 = this.mDropDownHeight;
            break label527;
            label994:
            boolean bool1 = false;
            break label547;
            label999:
            i9 = i5;
            break label585;
            label1006:
            i10 = i6;
            break label593;
            label1013:
            int i3;
            label1024:
            int i4;
            if (this.mDropDownWidth == -1)
            {
              i3 = -1;
              if (this.mDropDownHeight != -1) {
                break label1233;
              }
              i4 = -1;
              label1035:
              this.mPopup.setWidth(i3);
              this.mPopup.setHeight(i4);
              if (sClipToWindowEnabledMethod == null) {}
            }
            try
            {
              Method localMethod = sClipToWindowEnabledMethod;
              PopupWindow localPopupWindow2 = this.mPopup;
              Object[] arrayOfObject = new Object[1];
              arrayOfObject[0] = Boolean.valueOf(true);
              localMethod.invoke(localPopupWindow2, arrayOfObject);
              PopupWindow localPopupWindow1 = this.mPopup;
              if ((!this.mForceIgnoreOutsideTouch) && (!this.mDropDownAlwaysVisible))
              {
                localPopupWindow1.setOutsideTouchable(bool1);
                this.mPopup.setTouchInterceptor(this.mTouchInterceptor);
                PopupWindowCompat.showAsDropDown(this.mPopup, this.mDropDownAnchorView, this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, this.mDropDownGravity);
                this.mDropDownList.setSelection(-1);
                if ((!this.mModal) || (this.mDropDownList.isInTouchMode())) {
                  clearListSelection();
                }
                if (this.mModal) {
                  continue;
                }
                this.mHandler.post(this.mHideSelector);
                return;
                if (this.mDropDownWidth == -2)
                {
                  i3 = this.mDropDownAnchorView.getWidth();
                  break label1024;
                }
                i3 = this.mDropDownWidth;
                break label1024;
                label1233:
                if (this.mDropDownHeight == -2)
                {
                  i4 = i2;
                  break label1035;
                }
                i4 = this.mDropDownHeight;
              }
            }
            catch (Exception localException)
            {
              for (;;)
              {
                Log.i("ListPopupWindow", "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
                continue;
                bool1 = false;
              }
            }
          }
        }
        j = 0;
      }
      label1283:
      localObject = localDropDownListView;
      j = 0;
    }
  }
  
  private static final class DropDownListView
    extends ListViewCompat
  {
    private ViewPropertyAnimatorCompat mClickAnimation;
    private boolean mDrawsInPressedState;
    private boolean mHijackFocus;
    private boolean mListSelectionHidden;
    private ListViewAutoScrollHelper mScrollHelper;
    
    public DropDownListView(Context paramContext, boolean paramBoolean)
    {
      super(R.attr.dropDownListViewStyle);
      this.mHijackFocus = paramBoolean;
      setCacheColorHint(0);
    }
    
    public final boolean hasFocus()
    {
      return (this.mHijackFocus) || (super.hasFocus());
    }
    
    public final boolean hasWindowFocus()
    {
      return (this.mHijackFocus) || (super.hasWindowFocus());
    }
    
    public final boolean isFocused()
    {
      return (this.mHijackFocus) || (super.isFocused());
    }
    
    public final boolean isInTouchMode()
    {
      return ((this.mHijackFocus) && (this.mListSelectionHidden)) || (super.isInTouchMode());
    }
    
    public final boolean onForwardedEvent(MotionEvent paramMotionEvent, int paramInt)
    {
      boolean bool1 = true;
      int i = MotionEventCompat.getActionMasked(paramMotionEvent);
      int j = 0;
      switch (i)
      {
      default: 
        if ((!bool1) || (j != 0))
        {
          this.mDrawsInPressedState = false;
          setPressed(false);
          drawableStateChanged();
          View localView1 = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
          if (localView1 != null) {
            localView1.setPressed(false);
          }
          if (this.mClickAnimation != null)
          {
            this.mClickAnimation.cancel();
            this.mClickAnimation = null;
          }
        }
        if (bool1)
        {
          if (this.mScrollHelper == null) {
            this.mScrollHelper = new ListViewAutoScrollHelper(this);
          }
          this.mScrollHelper.setEnabled(true);
          this.mScrollHelper.onTouch(this, paramMotionEvent);
        }
        break;
      }
      label416:
      while (this.mScrollHelper == null)
      {
        return bool1;
        j = 0;
        bool1 = false;
        break;
        bool1 = false;
        int k = paramMotionEvent.findPointerIndex(paramInt);
        if (k < 0)
        {
          j = 0;
          bool1 = false;
          break;
        }
        int m = (int)paramMotionEvent.getX(k);
        int n = (int)paramMotionEvent.getY(k);
        int i1 = pointToPosition(m, n);
        if (i1 == -1)
        {
          j = 1;
          break;
        }
        View localView2 = getChildAt(i1 - getFirstVisiblePosition());
        float f1 = m;
        float f2 = n;
        this.mDrawsInPressedState = true;
        if (Build.VERSION.SDK_INT >= 21) {
          drawableHotspotChanged(f1, f2);
        }
        if (!isPressed()) {
          setPressed(true);
        }
        layoutChildren();
        if (this.mMotionPosition != -1)
        {
          View localView3 = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
          if ((localView3 != null) && (localView3 != localView2) && (localView3.isPressed())) {
            localView3.setPressed(false);
          }
        }
        this.mMotionPosition = i1;
        float f3 = f1 - localView2.getLeft();
        float f4 = f2 - localView2.getTop();
        if (Build.VERSION.SDK_INT >= 21) {
          localView2.drawableHotspotChanged(f3, f4);
        }
        if (!localView2.isPressed()) {
          localView2.setPressed(true);
        }
        setSelection(i1);
        Drawable localDrawable1 = getSelector();
        int i2;
        if ((localDrawable1 != null) && (i1 != -1))
        {
          i2 = 1;
          if (i2 != 0) {
            localDrawable1.setVisible(false, false);
          }
          Rect localRect1 = this.mSelectorRect;
          localRect1.set(localView2.getLeft(), localView2.getTop(), localView2.getRight(), localView2.getBottom());
          localRect1.left -= this.mSelectionLeftPadding;
          localRect1.top -= this.mSelectionTopPadding;
          localRect1.right += this.mSelectionRightPadding;
          localRect1.bottom += this.mSelectionBottomPadding;
        }
        for (;;)
        {
          try
          {
            boolean bool3 = this.mIsChildViewEnabled.getBoolean(this);
            if (localView2.isEnabled() != bool3)
            {
              Field localField = this.mIsChildViewEnabled;
              if (bool3) {
                continue;
              }
              bool4 = true;
              localField.set(this, Boolean.valueOf(bool4));
              if (i1 != -1) {
                refreshDrawableState();
              }
            }
          }
          catch (IllegalAccessException localIllegalAccessException)
          {
            boolean bool4;
            Rect localRect2;
            float f5;
            float f6;
            Drawable localDrawable2;
            localIllegalAccessException.printStackTrace();
            continue;
            boolean bool2 = false;
            continue;
          }
          if (i2 != 0)
          {
            localRect2 = this.mSelectorRect;
            f5 = localRect2.exactCenterX();
            f6 = localRect2.exactCenterY();
            if (getVisibility() != 0) {
              continue;
            }
            bool2 = true;
            localDrawable1.setVisible(bool2, false);
            DrawableCompat.setHotspot(localDrawable1, f5, f6);
          }
          localDrawable2 = getSelector();
          if ((localDrawable2 != null) && (i1 != -1)) {
            DrawableCompat.setHotspot(localDrawable2, f1, f2);
          }
          setSelectorEnabled(false);
          refreshDrawableState();
          bool1 = true;
          j = 0;
          if (i != 1) {
            break;
          }
          performItemClick(localView2, i1, getItemIdAtPosition(i1));
          j = 0;
          break;
          i2 = 0;
          break label416;
          bool4 = false;
        }
      }
      this.mScrollHelper.setEnabled(false);
      return bool1;
    }
    
    protected final boolean touchModeDrawsInPressedStateCompat()
    {
      return (this.mDrawsInPressedState) || (super.touchModeDrawsInPressedStateCompat());
    }
  }
  
  public static abstract class ForwardingListener
    implements View.OnTouchListener
  {
    private int mActivePointerId;
    private Runnable mDisallowIntercept;
    private boolean mForwarding;
    private final int mLongPressTimeout;
    private final float mScaledTouchSlop;
    private final View mSrc;
    private final int mTapTimeout;
    private final int[] mTmpLocation = new int[2];
    private Runnable mTriggerLongPress;
    private boolean mWasLongPress;
    
    public ForwardingListener(View paramView)
    {
      this.mSrc = paramView;
      this.mScaledTouchSlop = ViewConfiguration.get(paramView.getContext()).getScaledTouchSlop();
      this.mTapTimeout = ViewConfiguration.getTapTimeout();
      this.mLongPressTimeout = ((this.mTapTimeout + ViewConfiguration.getLongPressTimeout()) / 2);
    }
    
    private void clearCallbacks()
    {
      if (this.mTriggerLongPress != null) {
        this.mSrc.removeCallbacks(this.mTriggerLongPress);
      }
      if (this.mDisallowIntercept != null) {
        this.mSrc.removeCallbacks(this.mDisallowIntercept);
      }
    }
    
    private boolean onTouchForwarded(MotionEvent paramMotionEvent)
    {
      int i = 1;
      View localView = this.mSrc;
      ListPopupWindow localListPopupWindow = getPopup();
      if ((localListPopupWindow == null) || (!localListPopupWindow.mPopup.isShowing())) {
        i = 0;
      }
      for (;;)
      {
        return i;
        ListPopupWindow.DropDownListView localDropDownListView = localListPopupWindow.mDropDownList;
        if ((localDropDownListView == null) || (!localDropDownListView.isShown())) {
          return false;
        }
        MotionEvent localMotionEvent = MotionEvent.obtainNoHistory(paramMotionEvent);
        int[] arrayOfInt1 = this.mTmpLocation;
        localView.getLocationOnScreen(arrayOfInt1);
        localMotionEvent.offsetLocation(arrayOfInt1[0], arrayOfInt1[i]);
        int[] arrayOfInt2 = this.mTmpLocation;
        localDropDownListView.getLocationOnScreen(arrayOfInt2);
        localMotionEvent.offsetLocation(-arrayOfInt2[0], -arrayOfInt2[i]);
        boolean bool = localDropDownListView.onForwardedEvent(localMotionEvent, this.mActivePointerId);
        localMotionEvent.recycle();
        int j = MotionEventCompat.getActionMasked(paramMotionEvent);
        if ((j != i) && (j != 3)) {}
        int m;
        for (int k = i; (!bool) || (k == 0); m = 0) {
          return false;
        }
      }
    }
    
    public abstract ListPopupWindow getPopup();
    
    public boolean onForwardingStarted()
    {
      ListPopupWindow localListPopupWindow = getPopup();
      if ((localListPopupWindow != null) && (!localListPopupWindow.mPopup.isShowing())) {
        localListPopupWindow.show();
      }
      return true;
    }
    
    protected boolean onForwardingStopped()
    {
      ListPopupWindow localListPopupWindow = getPopup();
      if ((localListPopupWindow != null) && (localListPopupWindow.mPopup.isShowing())) {
        localListPopupWindow.dismiss();
      }
      return true;
    }
    
    public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
    {
      boolean bool1 = this.mForwarding;
      boolean bool2;
      if (bool1)
      {
        if (this.mWasLongPress)
        {
          bool2 = onTouchForwarded(paramMotionEvent);
          this.mForwarding = bool2;
          if ((bool2) || (bool1)) {
            return true;
          }
        }
        else
        {
          if ((onTouchForwarded(paramMotionEvent)) || (!onForwardingStopped())) {}
          for (bool2 = true;; bool2 = false) {
            break;
          }
        }
      }
      else
      {
        View localView = this.mSrc;
        if (localView.isEnabled()) {}
        label116:
        int i;
        switch (MotionEventCompat.getActionMasked(paramMotionEvent))
        {
        default: 
          i = 0;
          label119:
          if ((i == 0) || (!onForwardingStarted())) {
            break;
          }
        }
        for (bool2 = true; bool2; bool2 = false)
        {
          long l = SystemClock.uptimeMillis();
          MotionEvent localMotionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
          this.mSrc.onTouchEvent(localMotionEvent);
          localMotionEvent.recycle();
          break;
          this.mActivePointerId = paramMotionEvent.getPointerId(0);
          this.mWasLongPress = false;
          if (this.mDisallowIntercept == null) {
            this.mDisallowIntercept = new DisallowIntercept((byte)0);
          }
          localView.postDelayed(this.mDisallowIntercept, this.mTapTimeout);
          if (this.mTriggerLongPress == null) {
            this.mTriggerLongPress = new TriggerLongPress((byte)0);
          }
          localView.postDelayed(this.mTriggerLongPress, this.mLongPressTimeout);
          break label116;
          int j = paramMotionEvent.findPointerIndex(this.mActivePointerId);
          if (j < 0) {
            break label116;
          }
          float f1 = paramMotionEvent.getX(j);
          float f2 = paramMotionEvent.getY(j);
          float f3 = this.mScaledTouchSlop;
          if ((f1 >= -f3) && (f2 >= -f3) && (f1 < f3 + (localView.getRight() - localView.getLeft())) && (f2 < f3 + (localView.getBottom() - localView.getTop()))) {}
          for (int k = 1; k == 0; k = 0)
          {
            clearCallbacks();
            localView.getParent().requestDisallowInterceptTouchEvent(true);
            i = 1;
            break label119;
          }
          clearCallbacks();
          break label116;
        }
      }
      return false;
    }
    
    private final class DisallowIntercept
      implements Runnable
    {
      private DisallowIntercept() {}
      
      public final void run()
      {
        ListPopupWindow.ForwardingListener.this.mSrc.getParent().requestDisallowInterceptTouchEvent(true);
      }
    }
    
    private final class TriggerLongPress
      implements Runnable
    {
      private TriggerLongPress() {}
      
      public final void run()
      {
        ListPopupWindow.ForwardingListener.access$1000(ListPopupWindow.ForwardingListener.this);
      }
    }
  }
  
  private final class ListSelectorHider
    implements Runnable
  {
    private ListSelectorHider() {}
    
    public final void run()
    {
      ListPopupWindow.this.clearListSelection();
    }
  }
  
  private final class PopupDataSetObserver
    extends DataSetObserver
  {
    private PopupDataSetObserver() {}
    
    public final void onChanged()
    {
      if (ListPopupWindow.this.mPopup.isShowing()) {
        ListPopupWindow.this.show();
      }
    }
    
    public final void onInvalidated()
    {
      ListPopupWindow.this.dismiss();
    }
  }
  
  private final class PopupScrollListener
    implements AbsListView.OnScrollListener
  {
    private PopupScrollListener() {}
    
    public final void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3) {}
    
    public final void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
    {
      if ((paramInt == 1) && (!ListPopupWindow.this.isInputMethodNotNeeded()) && (ListPopupWindow.this.mPopup.getContentView() != null))
      {
        ListPopupWindow.this.mHandler.removeCallbacks(ListPopupWindow.this.mResizePopupRunnable);
        ListPopupWindow.this.mResizePopupRunnable.run();
      }
    }
  }
  
  private final class PopupTouchInterceptor
    implements View.OnTouchListener
  {
    private PopupTouchInterceptor() {}
    
    public final boolean onTouch(View paramView, MotionEvent paramMotionEvent)
    {
      int i = paramMotionEvent.getAction();
      int j = (int)paramMotionEvent.getX();
      int k = (int)paramMotionEvent.getY();
      if ((i == 0) && (ListPopupWindow.this.mPopup != null) && (ListPopupWindow.this.mPopup.isShowing()) && (j >= 0) && (j < ListPopupWindow.this.mPopup.getWidth()) && (k >= 0) && (k < ListPopupWindow.this.mPopup.getHeight())) {
        ListPopupWindow.this.mHandler.postDelayed(ListPopupWindow.this.mResizePopupRunnable, 250L);
      }
      for (;;)
      {
        return false;
        if (i == 1) {
          ListPopupWindow.this.mHandler.removeCallbacks(ListPopupWindow.this.mResizePopupRunnable);
        }
      }
    }
  }
  
  private final class ResizePopupRunnable
    implements Runnable
  {
    private ResizePopupRunnable() {}
    
    public final void run()
    {
      if ((ListPopupWindow.this.mDropDownList != null) && (ViewCompat.isAttachedToWindow(ListPopupWindow.this.mDropDownList)) && (ListPopupWindow.this.mDropDownList.getCount() > ListPopupWindow.this.mDropDownList.getChildCount()) && (ListPopupWindow.this.mDropDownList.getChildCount() <= ListPopupWindow.this.mListItemExpandMaximum))
      {
        ListPopupWindow.this.mPopup.setInputMethodMode(2);
        ListPopupWindow.this.show();
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.ListPopupWindow
 * JD-Core Version:    0.7.0.1
 */