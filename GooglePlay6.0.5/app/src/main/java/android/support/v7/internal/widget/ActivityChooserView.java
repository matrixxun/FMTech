package android.support.v7.internal.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R.id;
import android.support.v7.appcompat.R.layout;
import android.support.v7.appcompat.R.string;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.ListPopupWindow;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import java.util.List;

public final class ActivityChooserView
  extends ViewGroup
{
  private final LinearLayoutCompat mActivityChooserContent;
  private final ActivityChooserViewAdapter mAdapter;
  private final Callbacks mCallbacks;
  private int mDefaultActionButtonContentDescription;
  private final FrameLayout mDefaultActivityButton;
  private final FrameLayout mExpandActivityOverflowButton;
  private final ImageView mExpandActivityOverflowButtonImage;
  private int mInitialActivityCount;
  private boolean mIsAttachedToWindow;
  private boolean mIsSelectingDefaultActivity;
  private final int mListPopupMaxWidth;
  private ListPopupWindow mListPopupWindow;
  private final DataSetObserver mModelDataSetOberver;
  private PopupWindow.OnDismissListener mOnDismissListener;
  private final ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;
  ActionProvider mProvider;
  
  private ListPopupWindow getListPopupWindow()
  {
    if (this.mListPopupWindow == null)
    {
      this.mListPopupWindow = new ListPopupWindow(getContext());
      this.mListPopupWindow.setAdapter(this.mAdapter);
      this.mListPopupWindow.mDropDownAnchorView = this;
      this.mListPopupWindow.setModal$1385ff();
      this.mListPopupWindow.mItemClickListener = this.mCallbacks;
      this.mListPopupWindow.setOnDismissListener(this.mCallbacks);
    }
    return this.mListPopupWindow;
  }
  
  private boolean isShowingPopup()
  {
    return getListPopupWindow().mPopup.isShowing();
  }
  
  private void showPopupUnchecked(int paramInt)
  {
    if (this.mAdapter.mDataModel == null) {
      throw new IllegalStateException("No data model. Did you call #setDataModel?");
    }
    getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
    boolean bool;
    int j;
    label61:
    label93:
    ListPopupWindow localListPopupWindow;
    if (this.mDefaultActivityButton.getVisibility() == 0)
    {
      bool = true;
      int i = this.mAdapter.mDataModel.getActivityCount();
      if (!bool) {
        break label193;
      }
      j = 1;
      if ((paramInt == 2147483647) || (i <= paramInt + j)) {
        break label199;
      }
      this.mAdapter.setShowFooterView(true);
      this.mAdapter.setMaxActivityCount(paramInt - 1);
      localListPopupWindow = getListPopupWindow();
      if (!localListPopupWindow.mPopup.isShowing())
      {
        if ((!this.mIsSelectingDefaultActivity) && (bool)) {
          break label218;
        }
        this.mAdapter.setShowDefaultActivity(true, bool);
      }
    }
    for (;;)
    {
      localListPopupWindow.setContentWidth(Math.min(this.mAdapter.measureContentWidth(), this.mListPopupMaxWidth));
      localListPopupWindow.show();
      if (this.mProvider != null) {
        this.mProvider.subUiVisibilityChanged(true);
      }
      localListPopupWindow.mDropDownList.setContentDescription(getContext().getString(R.string.abc_activitychooserview_choose_application));
      return;
      bool = false;
      break;
      label193:
      j = 0;
      break label61;
      label199:
      this.mAdapter.setShowFooterView(false);
      this.mAdapter.setMaxActivityCount(paramInt);
      break label93;
      label218:
      this.mAdapter.setShowDefaultActivity(false, false);
    }
  }
  
  public final boolean dismissPopup()
  {
    if (getListPopupWindow().mPopup.isShowing())
    {
      getListPopupWindow().dismiss();
      ViewTreeObserver localViewTreeObserver = getViewTreeObserver();
      if (localViewTreeObserver.isAlive()) {
        localViewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
      }
    }
    return true;
  }
  
  public final ActivityChooserModel getDataModel()
  {
    return this.mAdapter.mDataModel;
  }
  
  protected final void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    ActivityChooserModel localActivityChooserModel = this.mAdapter.mDataModel;
    if (localActivityChooserModel != null) {
      localActivityChooserModel.registerObserver(this.mModelDataSetOberver);
    }
    this.mIsAttachedToWindow = true;
  }
  
  protected final void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    ActivityChooserModel localActivityChooserModel = this.mAdapter.mDataModel;
    if (localActivityChooserModel != null) {
      localActivityChooserModel.unregisterObserver(this.mModelDataSetOberver);
    }
    ViewTreeObserver localViewTreeObserver = getViewTreeObserver();
    if (localViewTreeObserver.isAlive()) {
      localViewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
    }
    if (isShowingPopup()) {
      dismissPopup();
    }
    this.mIsAttachedToWindow = false;
  }
  
  protected final void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mActivityChooserContent.layout(0, 0, paramInt3 - paramInt1, paramInt4 - paramInt2);
    if (!isShowingPopup()) {
      dismissPopup();
    }
  }
  
  protected final void onMeasure(int paramInt1, int paramInt2)
  {
    LinearLayoutCompat localLinearLayoutCompat = this.mActivityChooserContent;
    if (this.mDefaultActivityButton.getVisibility() != 0) {
      paramInt2 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt2), 1073741824);
    }
    measureChild(localLinearLayoutCompat, paramInt1, paramInt2);
    setMeasuredDimension(localLinearLayoutCompat.getMeasuredWidth(), localLinearLayoutCompat.getMeasuredHeight());
  }
  
  public final void setActivityChooserModel(ActivityChooserModel paramActivityChooserModel)
  {
    ActivityChooserViewAdapter localActivityChooserViewAdapter = this.mAdapter;
    ActivityChooserModel localActivityChooserModel = localActivityChooserViewAdapter.this$0.mAdapter.mDataModel;
    if ((localActivityChooserModel != null) && (localActivityChooserViewAdapter.this$0.isShown())) {
      localActivityChooserModel.unregisterObserver(localActivityChooserViewAdapter.this$0.mModelDataSetOberver);
    }
    localActivityChooserViewAdapter.mDataModel = paramActivityChooserModel;
    if ((paramActivityChooserModel != null) && (localActivityChooserViewAdapter.this$0.isShown())) {
      paramActivityChooserModel.registerObserver(localActivityChooserViewAdapter.this$0.mModelDataSetOberver);
    }
    localActivityChooserViewAdapter.notifyDataSetChanged();
    if (getListPopupWindow().mPopup.isShowing())
    {
      dismissPopup();
      if ((!getListPopupWindow().mPopup.isShowing()) && (this.mIsAttachedToWindow)) {}
    }
    else
    {
      return;
    }
    this.mIsSelectingDefaultActivity = false;
    showPopupUnchecked(this.mInitialActivityCount);
  }
  
  public final void setDefaultActionButtonContentDescription(int paramInt)
  {
    this.mDefaultActionButtonContentDescription = paramInt;
  }
  
  public final void setExpandActivityOverflowButtonContentDescription(int paramInt)
  {
    String str = getContext().getString(paramInt);
    this.mExpandActivityOverflowButtonImage.setContentDescription(str);
  }
  
  public final void setExpandActivityOverflowButtonDrawable(Drawable paramDrawable)
  {
    this.mExpandActivityOverflowButtonImage.setImageDrawable(paramDrawable);
  }
  
  public final void setInitialActivityCount(int paramInt)
  {
    this.mInitialActivityCount = paramInt;
  }
  
  public final void setOnDismissListener(PopupWindow.OnDismissListener paramOnDismissListener)
  {
    this.mOnDismissListener = paramOnDismissListener;
  }
  
  public final void setProvider(ActionProvider paramActionProvider)
  {
    this.mProvider = paramActionProvider;
  }
  
  private final class ActivityChooserViewAdapter
    extends BaseAdapter
  {
    ActivityChooserModel mDataModel;
    private boolean mHighlightDefaultActivity;
    private int mMaxActivityCount;
    boolean mShowDefaultActivity;
    private boolean mShowFooterView;
    
    public final int getCount()
    {
      int i = this.mDataModel.getActivityCount();
      if ((!this.mShowDefaultActivity) && (this.mDataModel.getDefaultActivity() != null)) {
        i--;
      }
      int j = Math.min(i, this.mMaxActivityCount);
      if (this.mShowFooterView) {
        j++;
      }
      return j;
    }
    
    public final Object getItem(int paramInt)
    {
      switch (getItemViewType(paramInt))
      {
      default: 
        throw new IllegalArgumentException();
      case 1: 
        return null;
      }
      if ((!this.mShowDefaultActivity) && (this.mDataModel.getDefaultActivity() != null)) {
        paramInt++;
      }
      return this.mDataModel.getActivity(paramInt);
    }
    
    public final long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public final int getItemViewType(int paramInt)
    {
      if ((this.mShowFooterView) && (paramInt == -1 + getCount())) {
        return 1;
      }
      return 0;
    }
    
    public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      switch (getItemViewType(paramInt))
      {
      default: 
        throw new IllegalArgumentException();
      case 1: 
        if ((paramView == null) || (paramView.getId() != 1))
        {
          paramView = LayoutInflater.from(this.this$0.getContext()).inflate(R.layout.abc_activity_chooser_view_list_item, paramViewGroup, false);
          paramView.setId(1);
          ((TextView)paramView.findViewById(R.id.title)).setText(this.this$0.getContext().getString(R.string.abc_activity_chooser_view_see_all));
        }
        return paramView;
      }
      if ((paramView == null) || (paramView.getId() != R.id.list_item)) {
        paramView = LayoutInflater.from(this.this$0.getContext()).inflate(R.layout.abc_activity_chooser_view_list_item, paramViewGroup, false);
      }
      PackageManager localPackageManager = this.this$0.getContext().getPackageManager();
      ImageView localImageView = (ImageView)paramView.findViewById(R.id.icon);
      ResolveInfo localResolveInfo = (ResolveInfo)getItem(paramInt);
      localImageView.setImageDrawable(localResolveInfo.loadIcon(localPackageManager));
      ((TextView)paramView.findViewById(R.id.title)).setText(localResolveInfo.loadLabel(localPackageManager));
      if ((this.mShowDefaultActivity) && (paramInt == 0) && (this.mHighlightDefaultActivity)) {
        ViewCompat.setActivated(paramView, true);
      }
      for (;;)
      {
        return paramView;
        ViewCompat.setActivated(paramView, false);
      }
    }
    
    public final int getViewTypeCount()
    {
      return 3;
    }
    
    public final int measureContentWidth()
    {
      int i = this.mMaxActivityCount;
      this.mMaxActivityCount = 2147483647;
      int j = 0;
      View localView = null;
      int k = View.MeasureSpec.makeMeasureSpec(0, 0);
      int m = View.MeasureSpec.makeMeasureSpec(0, 0);
      int n = getCount();
      for (int i1 = 0; i1 < n; i1++)
      {
        localView = getView(i1, localView, null);
        localView.measure(k, m);
        j = Math.max(j, localView.getMeasuredWidth());
      }
      this.mMaxActivityCount = i;
      return j;
    }
    
    public final void setMaxActivityCount(int paramInt)
    {
      if (this.mMaxActivityCount != paramInt)
      {
        this.mMaxActivityCount = paramInt;
        notifyDataSetChanged();
      }
    }
    
    public final void setShowDefaultActivity(boolean paramBoolean1, boolean paramBoolean2)
    {
      if ((this.mShowDefaultActivity != paramBoolean1) || (this.mHighlightDefaultActivity != paramBoolean2))
      {
        this.mShowDefaultActivity = paramBoolean1;
        this.mHighlightDefaultActivity = paramBoolean2;
        notifyDataSetChanged();
      }
    }
    
    public final void setShowFooterView(boolean paramBoolean)
    {
      if (this.mShowFooterView != paramBoolean)
      {
        this.mShowFooterView = paramBoolean;
        notifyDataSetChanged();
      }
    }
  }
  
  private final class Callbacks
    implements View.OnClickListener, View.OnLongClickListener, AdapterView.OnItemClickListener, PopupWindow.OnDismissListener
  {
    public final void onClick(View paramView)
    {
      if (paramView == this.this$0.mDefaultActivityButton)
      {
        this.this$0.dismissPopup();
        ResolveInfo localResolveInfo = this.this$0.mAdapter.mDataModel.getDefaultActivity();
        int i = this.this$0.mAdapter.mDataModel.getActivityIndex(localResolveInfo);
        Intent localIntent = this.this$0.mAdapter.mDataModel.chooseActivity(i);
        if (localIntent != null)
        {
          localIntent.addFlags(524288);
          this.this$0.getContext().startActivity(localIntent);
        }
        return;
      }
      if (paramView == this.this$0.mExpandActivityOverflowButton)
      {
        ActivityChooserView.access$602(this.this$0, false);
        this.this$0.showPopupUnchecked(this.this$0.mInitialActivityCount);
        return;
      }
      throw new IllegalArgumentException();
    }
    
    public final void onDismiss()
    {
      if (this.this$0.mOnDismissListener != null) {
        this.this$0.mOnDismissListener.onDismiss();
      }
      if (this.this$0.mProvider != null) {
        this.this$0.mProvider.subUiVisibilityChanged(false);
      }
    }
    
    public final void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
      switch (((ActivityChooserView.ActivityChooserViewAdapter)paramAdapterView.getAdapter()).getItemViewType(paramInt))
      {
      default: 
        throw new IllegalArgumentException();
      case 1: 
        this.this$0.showPopupUnchecked(2147483647);
      }
      do
      {
        return;
        this.this$0.dismissPopup();
        if (!this.this$0.mIsSelectingDefaultActivity) {
          break;
        }
      } while (paramInt <= 0);
      ActivityChooserModel localActivityChooserModel = this.this$0.mAdapter.mDataModel;
      for (;;)
      {
        synchronized (localActivityChooserModel.mInstanceLock)
        {
          localActivityChooserModel.ensureConsistentState();
          ActivityChooserModel.ActivityResolveInfo localActivityResolveInfo1 = (ActivityChooserModel.ActivityResolveInfo)localActivityChooserModel.mActivities.get(paramInt);
          ActivityChooserModel.ActivityResolveInfo localActivityResolveInfo2 = (ActivityChooserModel.ActivityResolveInfo)localActivityChooserModel.mActivities.get(0);
          if (localActivityResolveInfo2 != null)
          {
            f = 5.0F + (localActivityResolveInfo2.weight - localActivityResolveInfo1.weight);
            localActivityChooserModel.addHisoricalRecord(new ActivityChooserModel.HistoricalRecord(new ComponentName(localActivityResolveInfo1.resolveInfo.activityInfo.packageName, localActivityResolveInfo1.resolveInfo.activityInfo.name), System.currentTimeMillis(), f));
            return;
          }
        }
        float f = 1.0F;
      }
      if (this.this$0.mAdapter.mShowDefaultActivity) {}
      for (;;)
      {
        Intent localIntent = this.this$0.mAdapter.mDataModel.chooseActivity(paramInt);
        if (localIntent == null) {
          break;
        }
        localIntent.addFlags(524288);
        this.this$0.getContext().startActivity(localIntent);
        return;
        paramInt++;
      }
    }
    
    public final boolean onLongClick(View paramView)
    {
      if (paramView == this.this$0.mDefaultActivityButton)
      {
        if (this.this$0.mAdapter.getCount() > 0)
        {
          ActivityChooserView.access$602(this.this$0, true);
          this.this$0.showPopupUnchecked(this.this$0.mInitialActivityCount);
        }
        return true;
      }
      throw new IllegalArgumentException();
    }
  }
  
  public static class InnerLayout
    extends LinearLayoutCompat
  {
    private static final int[] TINT_ATTRS = { 16842964 };
    
    public InnerLayout(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, TINT_ATTRS);
      setBackgroundDrawable(localTintTypedArray.getDrawable(0));
      localTintTypedArray.mWrapped.recycle();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.ActivityChooserView
 * JD-Core Version:    0.7.0.1
 */