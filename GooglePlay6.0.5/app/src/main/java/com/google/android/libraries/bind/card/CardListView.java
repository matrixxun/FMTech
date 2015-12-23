package com.google.android.libraries.bind.card;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.google.android.libraries.bind.async.AsyncUtil;
import com.google.android.libraries.bind.async.DelayedRunnable;
import com.google.android.libraries.bind.async.JankLock;
import com.google.android.libraries.bind.data.BindingDataAdapter;
import com.google.android.libraries.bind.data.BindingViewGroup;
import com.google.android.libraries.bind.data.BindingViewGroup.BlendMode;
import com.google.android.libraries.bind.data.DataAdapter;
import com.google.android.libraries.bind.data.DataList;
import com.google.android.libraries.bind.data.DataView;
import com.google.android.libraries.bind.logging.Logd;
import com.google.android.libraries.bind.util.Util;
import com.google.android.libraries.bind.widget.MulticastOnScrollListener;
import com.google.android.libraries.bind.widget.WidgetUtil;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class CardListView
  extends ListView
{
  protected static final boolean ENABLE_ANIMATION;
  static final Logd LOGD = Logd.get(CardListView.class);
  private static final int[] a11yTempCount;
  private static final Interpolator alphaInterpolator;
  private static Bitmap listScrapBitmap;
  private static final Interpolator translationInterpolator;
  private boolean animateChanges = true;
  private boolean blendAnimationOnNextInvalidation;
  private final Map<Object, CaptureData> captures = new HashMap();
  private int invisibleHeight = 0;
  private AbsListView.OnScrollListener legacyOnScrollListener;
  private final MulticastOnScrollListener multicastOnScrollListener = new MulticastOnScrollListener();
  private final DataSetObserver postUpdateObserver;
  private final DataSetObserver preUpdateObserver;
  SavedState stashedSavedState;
  private final RectF tempRect = new RectF();
  
  static
  {
    if (Build.VERSION.SDK_INT >= 14) {}
    for (boolean bool = true;; bool = false)
    {
      ENABLE_ANIMATION = bool;
      translationInterpolator = new AccelerateDecelerateInterpolator();
      alphaInterpolator = new LinearInterpolator();
      a11yTempCount = new int[3];
      return;
    }
  }
  
  public CardListView(Context paramContext)
  {
    this(paramContext, null, 0);
  }
  
  public CardListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public CardListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setSelector(17170445);
    super.setOnScrollListener(this.multicastOnScrollListener);
    this.postUpdateObserver = new DataSetObserver()
    {
      public final void onChanged()
      {
        CardListView localCardListView = CardListView.this;
        if (localCardListView.stashedSavedState != null)
        {
          CardListView.SavedState localSavedState = localCardListView.stashedSavedState;
          localCardListView.stashedSavedState = null;
          CardListView.LOGD.i("Trying to restore stashed state", new Object[0]);
          localCardListView.restoreSavedState(localSavedState);
        }
      }
    };
    this.preUpdateObserver = new DataSetObserver()
    {
      public final void onChanged()
      {
        if (CardListView.this.stashedSavedState == null)
        {
          CardListView localCardListView = CardListView.this;
          localCardListView.stashedSavedState = localCardListView.captureState();
        }
        CardListView.this.prepareInvalidationAnimation();
      }
    };
  }
  
  private static RectF capturePosition(View paramView1, View paramView2, RectF paramRectF)
  {
    paramRectF.set(0.0F, 0.0F, paramView1.getWidth(), paramView1.getHeight());
    while (paramView1 != paramView2)
    {
      paramRectF.offset(paramView1.getLeft(), paramView1.getTop());
      paramView1 = (View)paramView1.getParent();
    }
    return paramRectF;
  }
  
  private SavedState captureState(int paramInt)
  {
    ListAdapter localListAdapter = getAdapter();
    if ((localListAdapter instanceof BindingDataAdapter))
    {
      BindingDataAdapter localBindingDataAdapter = (BindingDataAdapter)localListAdapter;
      int i = pointToPosition(getWidth() / 2, paramInt);
      if (i != -1)
      {
        Object localObject = localBindingDataAdapter.getRowFirstCardId(i);
        if (localObject != null)
        {
          LOGD.i("Saving state - cardId: %s", new Object[] { localObject });
          View localView = getChildAt(i - getFirstVisiblePosition());
          return new SavedState(super.onSaveInstanceState(), localObject, localView.getTop());
        }
      }
    }
    return null;
  }
  
  private void disableClipChildren(ViewParent paramViewParent)
  {
    while ((paramViewParent instanceof ViewGroup))
    {
      ((ViewGroup)paramViewParent).setClipChildren(false);
      ViewParent localViewParent = paramViewParent.getParent();
      if ((!(localViewParent instanceof ViewGroup)) || (paramViewParent == this)) {
        break;
      }
      paramViewParent = localViewParent;
    }
  }
  
  @TargetApi(16)
  private void traverse(View paramView, boolean paramBoolean)
  {
    Object localObject;
    BindingViewGroup.BlendMode localBlendMode;
    if ((paramView instanceof DataView))
    {
      DataList localDataList = ((DataView)paramView).getDataRow();
      if ((localDataList != null) && (localDataList.getCount() > 0))
      {
        localObject = localDataList.getItemId(0);
        if (!paramBoolean) {
          break label268;
        }
        if (!this.blendAnimationOnNextInvalidation) {
          break label256;
        }
        localBlendMode = BindingViewGroup.BlendMode.FADE_SOURCE_ONLY;
        CaptureData localCaptureData2 = new CaptureData((byte)0);
        RectF localRectF = capturePosition(paramView, this, new RectF());
        Rect localRect2 = new Rect();
        localRect2.left = Math.round(localRectF.left);
        localRect2.top = Math.round(localRectF.top);
        localRect2.right = Math.round(localRectF.right);
        localRect2.bottom = Math.round(localRectF.bottom);
        localCaptureData2.position = localRect2;
        localCaptureData2.animationDuration = 250L;
        if (((paramView instanceof BindingViewGroup)) && (localBlendMode != null) && (getScrapBitmap() != null))
        {
          if (!((BindingViewGroup)paramView).captureToBitmap(listScrapBitmap, localCaptureData2.position.left, localCaptureData2.position.top)) {
            break label262;
          }
          label207:
          localCaptureData2.blendMode = localBlendMode;
        }
        this.captures.put(localObject, localCaptureData2);
        paramView.animate().cancel();
        paramView.setAlpha(1.0F);
        paramView.setTranslationX(0.0F);
        paramView.setTranslationY(0.0F);
        paramView.setRotation(0.0F);
      }
    }
    for (;;)
    {
      return;
      label256:
      localBlendMode = null;
      break;
      label262:
      localBlendMode = null;
      break label207;
      label268:
      CaptureData localCaptureData1 = (CaptureData)this.captures.remove(localObject);
      if (localCaptureData1 != null)
      {
        Rect localRect1 = localCaptureData1.position;
        capturePosition(paramView, this, this.tempRect);
        float f1 = localRect1.centerX() - this.tempRect.centerX();
        float f2 = localRect1.centerY() - this.tempRect.centerY();
        if ((localCaptureData1.blendMode != null) && ((paramView instanceof BindingViewGroup))) {}
        for (int j = 1; (Math.abs(f1) > 5.0F) || (Math.abs(f2) > 5.0F) || (j != 0); j = 0)
        {
          if (j != 0)
          {
            ((BindingViewGroup)paramView).blendCapturedBitmap(listScrapBitmap, localCaptureData1.position, localCaptureData1.animationDuration, localCaptureData1.blendMode);
            paramView.setScaleX(localRect1.width() / paramView.getWidth());
            paramView.setScaleY(localRect1.height() / paramView.getHeight());
          }
          paramView.setTranslationX(f1);
          paramView.setTranslationY(f2);
          paramView.setRotation(0.0F);
          paramView.animate().translationX(0.0F).translationY(0.0F).scaleX(1.0F).scaleY(1.0F).setDuration(localCaptureData1.animationDuration).setInterpolator(translationInterpolator).setStartDelay(0L);
          disableClipChildren(paramView.getParent());
          return;
        }
      }
      else
      {
        paramView.setAlpha(0.0F);
        paramView.animate().alpha(1.0F).rotation(0.0F).setStartDelay(250L).setInterpolator(alphaInterpolator).setDuration(250L);
        if (Build.VERSION.SDK_INT >= 16)
        {
          paramView.animate().withLayer();
          return;
          if ((paramView instanceof ViewGroup))
          {
            ViewGroup localViewGroup = (ViewGroup)paramView;
            for (int i = 0; i < localViewGroup.getChildCount(); i++) {
              traverse(localViewGroup.getChildAt(i), paramBoolean);
            }
          }
        }
      }
    }
  }
  
  final SavedState captureState()
  {
    if (getChildCount() > 0)
    {
      if ((getFirstVisiblePosition() == 0) && (getChildAt(0).getTop() == 0)) {
        return captureState(0);
      }
      if ((getFirstVisiblePosition() + getChildCount() == getAdapter().getCount()) && (getChildAt(-1 + getChildCount()).getBottom() <= getHeight())) {
        return captureState(-1 + getChildAt(-1 + getChildCount()).getBottom());
      }
    }
    return captureState(getHeight() / 2);
  }
  
  protected void dispatchDraw(Canvas paramCanvas)
  {
    JankLock localJankLock;
    if ((this.animateChanges) && (!this.captures.isEmpty()))
    {
      LOGD.i("animateTransition", new Object[0]);
      if (ENABLE_ANIMATION)
      {
        traverse(this, false);
        this.captures.clear();
        localJankLock = JankLock.global;
        if (!JankLock.DISABLED) {
          if (!JankLock.DISABLED)
          {
            AsyncUtil.checkMainThread();
            localJankLock.pauseLock.lock();
          }
        }
      }
    }
    try
    {
      localJankLock.isPaused = true;
      localJankLock.pauseLock.unlock();
      localJankLock.resumeRunnable.postDelayed$25666f8(500L);
      super.dispatchDraw(paramCanvas);
      return;
    }
    finally
    {
      localJankLock.pauseLock.unlock();
    }
  }
  
  protected boolean getAnimateChanges()
  {
    return this.animateChanges;
  }
  
  public int getInvisibleHeight()
  {
    return this.invisibleHeight;
  }
  
  protected Bitmap getScrapBitmap()
  {
    if (!Util.isLowMemoryDevice()) {}
    for (int i = 1;; i = 0)
    {
      if (i != 0)
      {
        if ((listScrapBitmap != null) && ((listScrapBitmap.getWidth() != getWidth()) || (listScrapBitmap.getHeight() != getHeight())) && (listScrapBitmap != null))
        {
          listScrapBitmap.recycle();
          listScrapBitmap = null;
        }
        if (listScrapBitmap == null) {
          listScrapBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        }
      }
      return listScrapBitmap;
    }
  }
  
  public int getVisibleHeight()
  {
    return getHeight() - this.invisibleHeight;
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    setAdapter(null);
    this.multicastOnScrollListener.listeners.clear();
  }
  
  @TargetApi(14)
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    ListAdapter localListAdapter = getAdapter();
    if ((localListAdapter instanceof BindingDataAdapter))
    {
      BindingDataAdapter localBindingDataAdapter = (BindingDataAdapter)localListAdapter;
      int j = 0;
      int k = 0;
      while (j < getFirstVisiblePosition())
      {
        int i3 = localBindingDataAdapter.getA11yRowCount(j);
        Logd localLogd3 = LOGD;
        Object[] arrayOfObject3 = new Object[2];
        arrayOfObject3[0] = Integer.valueOf(j);
        arrayOfObject3[1] = Integer.valueOf(i3);
        localLogd3.i("position %d, count: %d", arrayOfObject3);
        k += i3;
        j++;
      }
      a11yTempCount[0] = k;
      for (int m = getFirstVisiblePosition(); m < getLastVisiblePosition(); m++)
      {
        int i2 = localBindingDataAdapter.getA11yRowCount(m);
        Logd localLogd2 = LOGD;
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = Integer.valueOf(m);
        arrayOfObject2[1] = Integer.valueOf(i2);
        localLogd2.i("position %d, count: %d", arrayOfObject2);
        k += i2;
      }
      a11yTempCount[1] = k;
      for (int n = getLastVisiblePosition(); n < localBindingDataAdapter.getCount(); n++)
      {
        int i1 = localBindingDataAdapter.getA11yRowCount(n);
        Logd localLogd1 = LOGD;
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = Integer.valueOf(n);
        arrayOfObject1[1] = Integer.valueOf(i1);
        localLogd1.i("position %d, count: %d", arrayOfObject1);
        k += i1;
      }
      a11yTempCount[2] = k;
    }
    for (int i = 1;; i = 0)
    {
      if (i != 0)
      {
        paramAccessibilityEvent.setFromIndex(a11yTempCount[0]);
        if (Build.VERSION.SDK_INT >= 14) {
          paramAccessibilityEvent.setToIndex(-1 + a11yTempCount[1]);
        }
        paramAccessibilityEvent.setItemCount(a11yTempCount[2]);
      }
      return;
    }
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    LOGD.i("onRestoreInstanceState", new Object[0]);
    if ((paramParcelable instanceof SavedState))
    {
      SavedState localSavedState = (SavedState)paramParcelable;
      super.onRestoreInstanceState(localSavedState.getSuperState());
      restoreSavedState(localSavedState);
      return;
    }
    super.onRestoreInstanceState(paramParcelable);
  }
  
  public Parcelable onSaveInstanceState()
  {
    Object localObject = captureState();
    if (localObject == null) {
      localObject = super.onSaveInstanceState();
    }
    return localObject;
  }
  
  protected final void prepareInvalidationAnimation()
  {
    LOGD.i("captureCardPositions", new Object[0]);
    if ((ENABLE_ANIMATION) && (this.captures.isEmpty()))
    {
      if (!WidgetUtil.isVisibleOnScreen(this)) {
        LOGD.i("Skipping capture since we're offscreen", new Object[0]);
      }
    }
    else {
      return;
    }
    if (getChildCount() == 0)
    {
      LOGD.i("Skipping capture since we're offscreen", new Object[0]);
      return;
    }
    LOGD.i("capturing", new Object[0]);
    if (this.blendAnimationOnNextInvalidation) {
      getScrapBitmap();
    }
    traverse(this, true);
    this.blendAnimationOnNextInvalidation = false;
  }
  
  final void restoreSavedState(SavedState paramSavedState)
  {
    Object localObject = paramSavedState.cardId;
    ListAdapter localListAdapter = getAdapter();
    if ((localListAdapter instanceof BindingDataAdapter))
    {
      BindingDataAdapter localBindingDataAdapter = (BindingDataAdapter)localListAdapter;
      if (!localBindingDataAdapter.hasRefreshedOnce()) {
        break label91;
      }
      int i = localBindingDataAdapter.findRowWithCardId(localObject);
      if (i != -1)
      {
        setSelectionFromTop(i, paramSavedState.offsetFromTop);
        Logd localLogd = LOGD;
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = localObject;
        arrayOfObject[1] = Integer.valueOf(i);
        localLogd.i("Restoring for cardId %s to position %d", arrayOfObject);
      }
    }
    return;
    label91:
    this.stashedSavedState = paramSavedState;
    LOGD.i("Stashing restore state", new Object[0]);
  }
  
  public void setAdapter(ListAdapter paramListAdapter)
  {
    ListAdapter localListAdapter = getAdapter();
    if (localListAdapter != null)
    {
      localListAdapter.unregisterDataSetObserver(this.postUpdateObserver);
      if (this.animateChanges) {
        localListAdapter.unregisterDataSetObserver(this.preUpdateObserver);
      }
    }
    super.setAdapter(paramListAdapter);
    if (paramListAdapter != null)
    {
      DataAdapter localDataAdapter = (DataAdapter)paramListAdapter;
      localDataAdapter.registerDataSetObserver(this.postUpdateObserver, 1);
      if (this.animateChanges) {
        localDataAdapter.registerDataSetObserver(this.preUpdateObserver, -1);
      }
    }
  }
  
  public void setAnimateChanges(boolean paramBoolean)
  {
    DataAdapter localDataAdapter;
    if (this.animateChanges != paramBoolean)
    {
      this.animateChanges = paramBoolean;
      localDataAdapter = (DataAdapter)getAdapter();
      if (localDataAdapter != null)
      {
        if (!paramBoolean) {
          break label39;
        }
        localDataAdapter.registerDataSetObserver(this.preUpdateObserver, -1);
      }
    }
    return;
    label39:
    localDataAdapter.unregisterDataSetObserver(this.preUpdateObserver);
  }
  
  public void setInvisibleHeight(int paramInt)
  {
    this.invisibleHeight = paramInt;
  }
  
  public void setOnScrollListener(AbsListView.OnScrollListener paramOnScrollListener)
  {
    if (paramOnScrollListener == null)
    {
      if (this.legacyOnScrollListener != null)
      {
        AbsListView.OnScrollListener localOnScrollListener2 = this.legacyOnScrollListener;
        this.multicastOnScrollListener.listeners.remove(localOnScrollListener2);
        this.legacyOnScrollListener = null;
      }
      return;
    }
    if (this.legacyOnScrollListener == null) {}
    for (boolean bool = true;; bool = false)
    {
      Util.checkPrecondition(bool);
      this.legacyOnScrollListener = paramOnScrollListener;
      AbsListView.OnScrollListener localOnScrollListener1 = this.legacyOnScrollListener;
      this.multicastOnScrollListener.listeners.add(localOnScrollListener1);
      return;
    }
  }
  
  private static final class CaptureData
  {
    long animationDuration;
    BindingViewGroup.BlendMode blendMode;
    Rect position;
  }
  
  static class SavedState
    extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() {};
    public final Object cardId;
    public final int offsetFromTop;
    
    private SavedState(Parcel paramParcel)
    {
      super();
      ClassLoader localClassLoader = SavedState.class.getClassLoader();
      if (paramParcel.readByte() == i) {
        if (i == 0) {
          break label52;
        }
      }
      label52:
      for (Object localObject = paramParcel.readParcelable(localClassLoader);; localObject = paramParcel.readSerializable())
      {
        this.cardId = localObject;
        this.offsetFromTop = paramParcel.readInt();
        return;
        i = 0;
        break;
      }
    }
    
    SavedState(Parcelable paramParcelable, Object paramObject, int paramInt)
    {
      super();
      this.cardId = paramObject;
      this.offsetFromTop = paramInt;
    }
    
    public String toString()
    {
      return this.cardId.toString();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      Object localObject = this.cardId;
      boolean bool = localObject instanceof Parcelable;
      int i;
      if (bool)
      {
        i = 1;
        paramParcel.writeByte((byte)i);
        if (!bool) {
          break label61;
        }
        paramParcel.writeParcelable((Parcelable)localObject, paramInt);
      }
      for (;;)
      {
        paramParcel.writeInt(this.offsetFromTop);
        return;
        i = 0;
        break;
        label61:
        paramParcel.writeSerializable((Serializable)localObject);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.card.CardListView
 * JD-Core Version:    0.7.0.1
 */