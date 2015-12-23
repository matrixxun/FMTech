package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.TintableBackgroundView;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.internal.widget.TintManager;
import android.support.v7.internal.widget.TintTypedArray;
import android.support.v7.internal.widget.ViewUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ThemedSpinnerAdapter;

public class AppCompatSpinner
  extends Spinner
  implements TintableBackgroundView
{
  private static final int[] ATTRS_ANDROID_SPINNERMODE;
  private static final boolean IS_AT_LEAST_JB;
  private static final boolean IS_AT_LEAST_M;
  private AppCompatBackgroundHelper mBackgroundTintHelper;
  private int mDropDownWidth;
  private ListPopupWindow.ForwardingListener mForwardingListener;
  private DropdownPopup mPopup;
  private Context mPopupContext;
  private boolean mPopupSet;
  private SpinnerAdapter mTempAdapter;
  private final Rect mTempRect = new Rect();
  private TintManager mTintManager;
  
  static
  {
    boolean bool1;
    if (Build.VERSION.SDK_INT >= 23)
    {
      bool1 = true;
      IS_AT_LEAST_M = bool1;
      if (Build.VERSION.SDK_INT < 16) {
        break label45;
      }
    }
    label45:
    for (boolean bool2 = true;; bool2 = false)
    {
      IS_AT_LEAST_JB = bool2;
      ATTRS_ANDROID_SPINNERMODE = new int[] { 16843505 };
      return;
      bool1 = false;
      break;
    }
  }
  
  public AppCompatSpinner(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AppCompatSpinner(Context paramContext, int paramInt)
  {
    this(paramContext, null, R.attr.spinnerStyle, paramInt);
  }
  
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.spinnerStyle);
  }
  
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, -1);
  }
  
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    this(paramContext, paramAttributeSet, paramInt1, paramInt2, null);
  }
  
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2, Resources.Theme paramTheme)
  {
    super(paramContext, paramAttributeSet, paramInt1);
    TintTypedArray localTintTypedArray1 = TintTypedArray.obtainStyledAttributes$1a6c1917(paramContext, paramAttributeSet, R.styleable.Spinner, paramInt1);
    this.mTintManager = localTintTypedArray1.getTintManager();
    this.mBackgroundTintHelper = new AppCompatBackgroundHelper(this, this.mTintManager);
    Object localObject1;
    AppCompatSpinner localAppCompatSpinner;
    TypedArray localTypedArray;
    if (paramTheme != null)
    {
      localObject1 = new ContextThemeWrapper(paramContext, paramTheme);
      localAppCompatSpinner = this;
      localAppCompatSpinner.mPopupContext = ((Context)localObject1);
      if (this.mPopupContext != null) {
        if (paramInt2 == -1)
        {
          if (Build.VERSION.SDK_INT < 11) {
            break label402;
          }
          localTypedArray = null;
        }
      }
    }
    for (;;)
    {
      try
      {
        localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, ATTRS_ANDROID_SPINNERMODE, paramInt1, 0);
        if (localTypedArray.hasValue(0))
        {
          int j = localTypedArray.getInt(0, 0);
          paramInt2 = j;
        }
      }
      catch (Exception localException)
      {
        final DropdownPopup localDropdownPopup;
        TintTypedArray localTintTypedArray2;
        int i;
        int k;
        Log.i("AppCompatSpinner", "Could not read android:spinnerMode", localException);
        if (localTypedArray == null) {
          continue;
        }
        localTypedArray.recycle();
        continue;
      }
      finally
      {
        if (localTypedArray == null) {
          continue;
        }
        localTypedArray.recycle();
      }
      if (paramInt2 == 1)
      {
        localDropdownPopup = new DropdownPopup(this.mPopupContext, paramAttributeSet, paramInt1);
        localTintTypedArray2 = TintTypedArray.obtainStyledAttributes$1a6c1917(this.mPopupContext, paramAttributeSet, R.styleable.Spinner, paramInt1);
        this.mDropDownWidth = localTintTypedArray2.getLayoutDimension(R.styleable.Spinner_android_dropDownWidth, -2);
        localDropdownPopup.setBackgroundDrawable(localTintTypedArray2.getDrawable(R.styleable.Spinner_android_popupBackground));
        i = R.styleable.Spinner_android_prompt;
        localDropdownPopup.mHintText = localTintTypedArray1.mWrapped.getString(i);
        localTintTypedArray2.mWrapped.recycle();
        this.mPopup = localDropdownPopup;
        this.mForwardingListener = new ListPopupWindow.ForwardingListener(this)
        {
          public final ListPopupWindow getPopup()
          {
            return localDropdownPopup;
          }
          
          public final boolean onForwardingStarted()
          {
            if (!AppCompatSpinner.this.mPopup.mPopup.isShowing()) {
              AppCompatSpinner.this.mPopup.show();
            }
            return true;
          }
        };
      }
      localTintTypedArray1.mWrapped.recycle();
      this.mPopupSet = true;
      if (this.mTempAdapter != null)
      {
        setAdapter(this.mTempAdapter);
        this.mTempAdapter = null;
      }
      this.mBackgroundTintHelper.loadFromAttributes(paramAttributeSet, paramInt1);
      return;
      k = localTintTypedArray1.getResourceId(R.styleable.Spinner_popupTheme, 0);
      if (k != 0)
      {
        localObject1 = new ContextThemeWrapper(paramContext, k);
        localAppCompatSpinner = this;
        break;
      }
      if (!IS_AT_LEAST_M)
      {
        localObject1 = paramContext;
        localAppCompatSpinner = this;
        break;
      }
      localAppCompatSpinner = this;
      localObject1 = null;
      break;
      label402:
      paramInt2 = 1;
    }
  }
  
  private int compatMeasureContentWidth(SpinnerAdapter paramSpinnerAdapter, Drawable paramDrawable)
  {
    int i;
    if (paramSpinnerAdapter == null) {
      i = 0;
    }
    do
    {
      return i;
      i = 0;
      View localView = null;
      int j = 0;
      int k = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
      int m = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
      int n = Math.max(0, getSelectedItemPosition());
      int i1 = Math.min(paramSpinnerAdapter.getCount(), n + 15);
      for (int i2 = Math.max(0, n - (15 - (i1 - n))); i2 < i1; i2++)
      {
        int i3 = paramSpinnerAdapter.getItemViewType(i2);
        if (i3 != j)
        {
          j = i3;
          localView = null;
        }
        localView = paramSpinnerAdapter.getView(i2, localView, this);
        if (localView.getLayoutParams() == null) {
          localView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        }
        localView.measure(k, m);
        i = Math.max(i, localView.getMeasuredWidth());
      }
    } while (paramDrawable == null);
    paramDrawable.getPadding(this.mTempRect);
    return i + (this.mTempRect.left + this.mTempRect.right);
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    if (this.mBackgroundTintHelper != null) {
      this.mBackgroundTintHelper.applySupportBackgroundTint();
    }
  }
  
  public int getDropDownHorizontalOffset()
  {
    if (this.mPopup != null) {
      return this.mPopup.mDropDownHorizontalOffset;
    }
    if (IS_AT_LEAST_JB) {
      return super.getDropDownHorizontalOffset();
    }
    return 0;
  }
  
  public int getDropDownVerticalOffset()
  {
    if (this.mPopup != null)
    {
      localDropdownPopup = this.mPopup;
      if (localDropdownPopup.mDropDownVerticalOffsetSet) {}
    }
    while (!IS_AT_LEAST_JB)
    {
      DropdownPopup localDropdownPopup;
      return 0;
      return localDropdownPopup.mDropDownVerticalOffset;
    }
    return super.getDropDownVerticalOffset();
  }
  
  public int getDropDownWidth()
  {
    if (this.mPopup != null) {
      return this.mDropDownWidth;
    }
    if (IS_AT_LEAST_JB) {
      return super.getDropDownWidth();
    }
    return 0;
  }
  
  public Drawable getPopupBackground()
  {
    if (this.mPopup != null) {
      return this.mPopup.mPopup.getBackground();
    }
    if (IS_AT_LEAST_JB) {
      return super.getPopupBackground();
    }
    return null;
  }
  
  public Context getPopupContext()
  {
    if (this.mPopup != null) {
      return this.mPopupContext;
    }
    if (IS_AT_LEAST_M) {
      return super.getPopupContext();
    }
    return null;
  }
  
  public CharSequence getPrompt()
  {
    if (this.mPopup != null) {
      return this.mPopup.mHintText;
    }
    return super.getPrompt();
  }
  
  public ColorStateList getSupportBackgroundTintList()
  {
    if (this.mBackgroundTintHelper != null) {
      return this.mBackgroundTintHelper.getSupportBackgroundTintList();
    }
    return null;
  }
  
  public PorterDuff.Mode getSupportBackgroundTintMode()
  {
    if (this.mBackgroundTintHelper != null) {
      return this.mBackgroundTintHelper.getSupportBackgroundTintMode();
    }
    return null;
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if ((this.mPopup != null) && (this.mPopup.mPopup.isShowing())) {
      this.mPopup.dismiss();
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if ((this.mPopup != null) && (View.MeasureSpec.getMode(paramInt1) == -2147483648)) {
      setMeasuredDimension(Math.min(Math.max(getMeasuredWidth(), compatMeasureContentWidth(getAdapter(), getBackground())), View.MeasureSpec.getSize(paramInt1)), getMeasuredHeight());
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((this.mForwardingListener != null) && (this.mForwardingListener.onTouch(this, paramMotionEvent))) {
      return true;
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public boolean performClick()
  {
    if ((this.mPopup != null) && (!this.mPopup.mPopup.isShowing()))
    {
      this.mPopup.show();
      return true;
    }
    return super.performClick();
  }
  
  public void setAdapter(SpinnerAdapter paramSpinnerAdapter)
  {
    if (!this.mPopupSet) {
      this.mTempAdapter = paramSpinnerAdapter;
    }
    do
    {
      return;
      super.setAdapter(paramSpinnerAdapter);
    } while (this.mPopup == null);
    if (this.mPopupContext == null) {}
    for (Context localContext = getContext();; localContext = this.mPopupContext)
    {
      this.mPopup.setAdapter(new DropDownAdapter(paramSpinnerAdapter, localContext.getTheme()));
      return;
    }
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable)
  {
    super.setBackgroundDrawable(paramDrawable);
    if (this.mBackgroundTintHelper != null) {
      this.mBackgroundTintHelper.setInternalBackgroundTint(null);
    }
  }
  
  public void setBackgroundResource(int paramInt)
  {
    super.setBackgroundResource(paramInt);
    if (this.mBackgroundTintHelper != null) {
      this.mBackgroundTintHelper.onSetBackgroundResource(paramInt);
    }
  }
  
  public void setDropDownHorizontalOffset(int paramInt)
  {
    if (this.mPopup != null) {
      this.mPopup.mDropDownHorizontalOffset = paramInt;
    }
    while (!IS_AT_LEAST_JB) {
      return;
    }
    super.setDropDownHorizontalOffset(paramInt);
  }
  
  public void setDropDownVerticalOffset(int paramInt)
  {
    if (this.mPopup != null)
    {
      localDropdownPopup = this.mPopup;
      localDropdownPopup.mDropDownVerticalOffset = paramInt;
      localDropdownPopup.mDropDownVerticalOffsetSet = true;
    }
    while (!IS_AT_LEAST_JB)
    {
      DropdownPopup localDropdownPopup;
      return;
    }
    super.setDropDownVerticalOffset(paramInt);
  }
  
  public void setDropDownWidth(int paramInt)
  {
    if (this.mPopup != null) {
      this.mDropDownWidth = paramInt;
    }
    while (!IS_AT_LEAST_JB) {
      return;
    }
    super.setDropDownWidth(paramInt);
  }
  
  public void setPopupBackgroundDrawable(Drawable paramDrawable)
  {
    if (this.mPopup != null) {
      this.mPopup.setBackgroundDrawable(paramDrawable);
    }
    while (!IS_AT_LEAST_JB) {
      return;
    }
    super.setPopupBackgroundDrawable(paramDrawable);
  }
  
  public void setPopupBackgroundResource(int paramInt)
  {
    setPopupBackgroundDrawable(getPopupContext().getDrawable(paramInt));
  }
  
  public void setPrompt(CharSequence paramCharSequence)
  {
    if (this.mPopup != null)
    {
      this.mPopup.mHintText = paramCharSequence;
      return;
    }
    super.setPrompt(paramCharSequence);
  }
  
  public void setSupportBackgroundTintList(ColorStateList paramColorStateList)
  {
    if (this.mBackgroundTintHelper != null) {
      this.mBackgroundTintHelper.setSupportBackgroundTintList(paramColorStateList);
    }
  }
  
  public void setSupportBackgroundTintMode(PorterDuff.Mode paramMode)
  {
    if (this.mBackgroundTintHelper != null) {
      this.mBackgroundTintHelper.setSupportBackgroundTintMode(paramMode);
    }
  }
  
  private static final class DropDownAdapter
    implements ListAdapter, SpinnerAdapter
  {
    private SpinnerAdapter mAdapter;
    private ListAdapter mListAdapter;
    
    public DropDownAdapter(SpinnerAdapter paramSpinnerAdapter, Resources.Theme paramTheme)
    {
      this.mAdapter = paramSpinnerAdapter;
      if ((paramSpinnerAdapter instanceof ListAdapter)) {
        this.mListAdapter = ((ListAdapter)paramSpinnerAdapter);
      }
      if ((paramTheme != null) && (AppCompatSpinner.IS_AT_LEAST_M) && ((paramSpinnerAdapter instanceof ThemedSpinnerAdapter)))
      {
        ThemedSpinnerAdapter localThemedSpinnerAdapter = (ThemedSpinnerAdapter)paramSpinnerAdapter;
        if (localThemedSpinnerAdapter.getDropDownViewTheme() != paramTheme) {
          localThemedSpinnerAdapter.setDropDownViewTheme(paramTheme);
        }
      }
    }
    
    public final boolean areAllItemsEnabled()
    {
      ListAdapter localListAdapter = this.mListAdapter;
      if (localListAdapter != null) {
        return localListAdapter.areAllItemsEnabled();
      }
      return true;
    }
    
    public final int getCount()
    {
      if (this.mAdapter == null) {
        return 0;
      }
      return this.mAdapter.getCount();
    }
    
    public final View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (this.mAdapter == null) {
        return null;
      }
      return this.mAdapter.getDropDownView(paramInt, paramView, paramViewGroup);
    }
    
    public final Object getItem(int paramInt)
    {
      if (this.mAdapter == null) {
        return null;
      }
      return this.mAdapter.getItem(paramInt);
    }
    
    public final long getItemId(int paramInt)
    {
      if (this.mAdapter == null) {
        return -1L;
      }
      return this.mAdapter.getItemId(paramInt);
    }
    
    public final int getItemViewType(int paramInt)
    {
      return 0;
    }
    
    public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      return getDropDownView(paramInt, paramView, paramViewGroup);
    }
    
    public final int getViewTypeCount()
    {
      return 1;
    }
    
    public final boolean hasStableIds()
    {
      return (this.mAdapter != null) && (this.mAdapter.hasStableIds());
    }
    
    public final boolean isEmpty()
    {
      return getCount() == 0;
    }
    
    public final boolean isEnabled(int paramInt)
    {
      ListAdapter localListAdapter = this.mListAdapter;
      if (localListAdapter != null) {
        return localListAdapter.isEnabled(paramInt);
      }
      return true;
    }
    
    public final void registerDataSetObserver(DataSetObserver paramDataSetObserver)
    {
      if (this.mAdapter != null) {
        this.mAdapter.registerDataSetObserver(paramDataSetObserver);
      }
    }
    
    public final void unregisterDataSetObserver(DataSetObserver paramDataSetObserver)
    {
      if (this.mAdapter != null) {
        this.mAdapter.unregisterDataSetObserver(paramDataSetObserver);
      }
    }
  }
  
  private final class DropdownPopup
    extends ListPopupWindow
  {
    private ListAdapter mAdapter;
    CharSequence mHintText;
    private final Rect mVisibleRect = new Rect();
    
    public DropdownPopup(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
      super(paramAttributeSet, paramInt);
      this.mDropDownAnchorView = AppCompatSpinner.this;
      setModal$1385ff();
      this.mPromptPosition = 0;
      this.mItemClickListener = new AdapterView.OnItemClickListener()
      {
        public final void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
        {
          AppCompatSpinner.this.setSelection(paramAnonymousInt);
          if (AppCompatSpinner.this.getOnItemClickListener() != null) {
            AppCompatSpinner.this.performItemClick(paramAnonymousView, paramAnonymousInt, AppCompatSpinner.DropdownPopup.this.mAdapter.getItemId(paramAnonymousInt));
          }
          AppCompatSpinner.DropdownPopup.this.dismiss();
        }
      };
    }
    
    final void computeContentWidth()
    {
      Drawable localDrawable = this.mPopup.getBackground();
      int i;
      int j;
      int k;
      int m;
      if (localDrawable != null)
      {
        localDrawable.getPadding(AppCompatSpinner.this.mTempRect);
        if (ViewUtils.isLayoutRtl(AppCompatSpinner.this))
        {
          i = AppCompatSpinner.this.mTempRect.right;
          j = AppCompatSpinner.this.getPaddingLeft();
          k = AppCompatSpinner.this.getPaddingRight();
          m = AppCompatSpinner.this.getWidth();
          if (AppCompatSpinner.this.mDropDownWidth != -2) {
            break label250;
          }
          int i1 = AppCompatSpinner.this.compatMeasureContentWidth((SpinnerAdapter)this.mAdapter, this.mPopup.getBackground());
          int i2 = AppCompatSpinner.this.getContext().getResources().getDisplayMetrics().widthPixels - AppCompatSpinner.this.mTempRect.left - AppCompatSpinner.this.mTempRect.right;
          if (i1 > i2) {
            i1 = i2;
          }
          setContentWidth(Math.max(i1, m - j - k));
          label175:
          if (!ViewUtils.isLayoutRtl(AppCompatSpinner.this)) {
            break label290;
          }
        }
      }
      label290:
      for (int n = i + (m - k - this.mDropDownWidth);; n = i + j)
      {
        this.mDropDownHorizontalOffset = n;
        return;
        i = -AppCompatSpinner.this.mTempRect.left;
        break;
        Rect localRect = AppCompatSpinner.this.mTempRect;
        AppCompatSpinner.this.mTempRect.right = 0;
        localRect.left = 0;
        i = 0;
        break;
        label250:
        if (AppCompatSpinner.this.mDropDownWidth == -1)
        {
          setContentWidth(m - j - k);
          break label175;
        }
        setContentWidth(AppCompatSpinner.this.mDropDownWidth);
        break label175;
      }
    }
    
    public final void setAdapter(ListAdapter paramListAdapter)
    {
      super.setAdapter(paramListAdapter);
      this.mAdapter = paramListAdapter;
    }
    
    public final void show()
    {
      boolean bool = this.mPopup.isShowing();
      computeContentWidth();
      setInputMethodMode$13462e();
      super.show();
      this.mDropDownList.setChoiceMode(1);
      int i = AppCompatSpinner.this.getSelectedItemPosition();
      ListPopupWindow.DropDownListView localDropDownListView = this.mDropDownList;
      if ((this.mPopup.isShowing()) && (localDropDownListView != null))
      {
        ListPopupWindow.DropDownListView.access$502(localDropDownListView, false);
        localDropDownListView.setSelection(i);
        if ((Build.VERSION.SDK_INT >= 11) && (localDropDownListView.getChoiceMode() != 0)) {
          localDropDownListView.setItemChecked(i, true);
        }
      }
      if (bool) {}
      ViewTreeObserver localViewTreeObserver;
      do
      {
        return;
        localViewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
      } while (localViewTreeObserver == null);
      final ViewTreeObserver.OnGlobalLayoutListener local2 = new ViewTreeObserver.OnGlobalLayoutListener()
      {
        public final void onGlobalLayout()
        {
          if (!AppCompatSpinner.DropdownPopup.access$600(AppCompatSpinner.DropdownPopup.this, AppCompatSpinner.this))
          {
            AppCompatSpinner.DropdownPopup.this.dismiss();
            return;
          }
          AppCompatSpinner.DropdownPopup.this.computeContentWidth();
          AppCompatSpinner.DropdownPopup.this.show();
        }
      };
      localViewTreeObserver.addOnGlobalLayoutListener(local2);
      setOnDismissListener(new PopupWindow.OnDismissListener()
      {
        public final void onDismiss()
        {
          ViewTreeObserver localViewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
          if (localViewTreeObserver != null) {
            localViewTreeObserver.removeGlobalOnLayoutListener(local2);
          }
        }
      });
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.AppCompatSpinner
 * JD-Core Version:    0.7.0.1
 */