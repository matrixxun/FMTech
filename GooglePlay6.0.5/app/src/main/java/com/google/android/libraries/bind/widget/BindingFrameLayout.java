package com.google.android.libraries.bind.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import com.google.android.libraries.bind.R.styleable;
import com.google.android.libraries.bind.card.CardGroup;
import com.google.android.libraries.bind.data.BindingViewGroup;
import com.google.android.libraries.bind.data.BindingViewGroup.BlendMode;
import com.google.android.libraries.bind.data.Data;
import com.google.android.libraries.bind.data.DataList;
import com.google.android.libraries.bind.data.DataViewHelper;
import com.google.android.libraries.bind.experimental.card.EditableAdapterView;
import com.google.android.libraries.bind.logging.Logd;
import com.google.android.libraries.bind.util.Util;

public class BindingFrameLayout
  extends BoundFrameLayout
  implements BindingViewGroup
{
  protected final BindingViewGroupHelper bindingViewGroupHelper = new BindingViewGroupHelper(this);
  
  public BindingFrameLayout(Context paramContext)
  {
    this(paramContext, null, 0);
  }
  
  public BindingFrameLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public BindingFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.BindingFrameLayout);
    this.bindingViewGroupHelper.isOwnedByParent = localTypedArray.getBoolean(R.styleable.BindingFrameLayout_bind__isOwnedByParent, false);
    this.bindingViewGroupHelper.supportsAnimationCapture = localTypedArray.getBoolean(R.styleable.BindingFrameLayout_bind__supportsAnimationCapture, true);
    localTypedArray.recycle();
  }
  
  public final void blendCapturedBitmap(Bitmap paramBitmap, Rect paramRect, long paramLong, BindingViewGroup.BlendMode paramBlendMode)
  {
    BindingViewGroupHelper localBindingViewGroupHelper = this.bindingViewGroupHelper;
    if (paramLong > 0L) {}
    for (boolean bool = true;; bool = false)
    {
      Util.checkPrecondition(bool);
      localBindingViewGroupHelper.blendMode = paramBlendMode;
      localBindingViewGroupHelper.blendedBitmap = paramBitmap;
      if (localBindingViewGroupHelper.blendedBitmap != null)
      {
        localBindingViewGroupHelper.blendedBitmapSrcRect.set(paramRect);
        localBindingViewGroupHelper.blendBitmapStartTimeMs = System.currentTimeMillis();
        localBindingViewGroupHelper.blendBitmapDurationMs = paramLong;
        localBindingViewGroupHelper.viewGroup.setWillNotDraw(false);
        localBindingViewGroupHelper.viewGroup.invalidate();
      }
      return;
    }
  }
  
  public final boolean captureToBitmap(Bitmap paramBitmap, float paramFloat1, float paramFloat2)
  {
    BindingViewGroupHelper localBindingViewGroupHelper = this.bindingViewGroupHelper;
    if ((localBindingViewGroupHelper.supportsAnimationCapture) && (localBindingViewGroupHelper.viewGroup.getWidth() > 0) && (localBindingViewGroupHelper.viewGroup.getHeight() > 0))
    {
      Canvas localCanvas = new Canvas(paramBitmap);
      localCanvas.translate(paramFloat1, paramFloat2);
      localCanvas.clipRect(new Rect(0, 0, localBindingViewGroupHelper.viewGroup.getWidth(), localBindingViewGroupHelper.viewGroup.getHeight()));
      localCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
      localBindingViewGroupHelper.capturing = true;
      localBindingViewGroupHelper.viewGroup.draw(localCanvas);
      localBindingViewGroupHelper.capturing = false;
      return true;
    }
    return false;
  }
  
  public void draw(Canvas paramCanvas)
  {
    BindingViewGroupHelper localBindingViewGroupHelper = this.bindingViewGroupHelper;
    if ((localBindingViewGroupHelper.blendMode != BindingViewGroup.BlendMode.SHOW_SOURCE_HIDE_DESTINATION) && ((localBindingViewGroupHelper.viewGroup instanceof BindingViewGroup))) {
      ((BindingViewGroup)localBindingViewGroupHelper.viewGroup).superDrawProxy(paramCanvas);
    }
    float f;
    if ((localBindingViewGroupHelper.blendedBitmap != null) && (!localBindingViewGroupHelper.capturing))
    {
      f = (float)(System.currentTimeMillis() - localBindingViewGroupHelper.blendBitmapStartTimeMs) / (float)localBindingViewGroupHelper.blendBitmapDurationMs;
      if (f >= 1.0F) {
        localBindingViewGroupHelper.clearBlendedBitmap();
      }
    }
    else
    {
      return;
    }
    if (!localBindingViewGroupHelper.blendedBitmapDstComputed)
    {
      localBindingViewGroupHelper.blendedBitmapDstRect.left = 0;
      localBindingViewGroupHelper.blendedBitmapDstRect.top = 0;
      localBindingViewGroupHelper.blendedBitmapDstRect.right = localBindingViewGroupHelper.viewGroup.getWidth();
      localBindingViewGroupHelper.blendedBitmapDstRect.bottom = localBindingViewGroupHelper.viewGroup.getHeight();
      if (localBindingViewGroupHelper.blendedBitmapSrcRect.left < 0)
      {
        Rect localRect4 = localBindingViewGroupHelper.blendedBitmapDstRect;
        localRect4.left += -localBindingViewGroupHelper.blendedBitmapSrcRect.left * localBindingViewGroupHelper.viewGroup.getWidth() / localBindingViewGroupHelper.blendedBitmapSrcRect.width();
      }
      if (localBindingViewGroupHelper.blendedBitmapSrcRect.top < 0)
      {
        Rect localRect3 = localBindingViewGroupHelper.blendedBitmapDstRect;
        localRect3.top += -localBindingViewGroupHelper.blendedBitmapSrcRect.top * localBindingViewGroupHelper.viewGroup.getHeight() / localBindingViewGroupHelper.blendedBitmapSrcRect.height();
      }
      if (localBindingViewGroupHelper.blendedBitmapSrcRect.right > localBindingViewGroupHelper.blendedBitmap.getWidth())
      {
        Rect localRect2 = localBindingViewGroupHelper.blendedBitmapDstRect;
        localRect2.right -= (localBindingViewGroupHelper.blendedBitmapSrcRect.right - localBindingViewGroupHelper.blendedBitmap.getWidth()) * localBindingViewGroupHelper.viewGroup.getWidth() / localBindingViewGroupHelper.blendedBitmapSrcRect.width();
      }
      if (localBindingViewGroupHelper.blendedBitmapSrcRect.bottom > localBindingViewGroupHelper.blendedBitmap.getHeight())
      {
        Rect localRect1 = localBindingViewGroupHelper.blendedBitmapDstRect;
        localRect1.bottom -= (localBindingViewGroupHelper.blendedBitmapSrcRect.bottom - localBindingViewGroupHelper.blendedBitmap.getHeight()) * localBindingViewGroupHelper.viewGroup.getHeight() / localBindingViewGroupHelper.blendedBitmapSrcRect.height();
      }
      localBindingViewGroupHelper.blendedBitmapSrcRect.left = Math.max(0, localBindingViewGroupHelper.blendedBitmapSrcRect.left);
      localBindingViewGroupHelper.blendedBitmapSrcRect.top = Math.max(0, localBindingViewGroupHelper.blendedBitmapSrcRect.top);
      localBindingViewGroupHelper.blendedBitmapSrcRect.right = Math.min(localBindingViewGroupHelper.blendedBitmap.getWidth(), localBindingViewGroupHelper.blendedBitmapSrcRect.right);
      localBindingViewGroupHelper.blendedBitmapSrcRect.bottom = Math.min(localBindingViewGroupHelper.blendedBitmap.getHeight(), localBindingViewGroupHelper.blendedBitmapSrcRect.bottom);
      localBindingViewGroupHelper.blendedBitmapDstComputed = true;
    }
    if (localBindingViewGroupHelper.blendMode == BindingViewGroup.BlendMode.FADE_SOURCE_ONLY) {
      BindingViewGroupHelper.blendPaint.setAlpha((int)Math.floor(255.0F * (1.0F - f)));
    }
    paramCanvas.drawBitmap(localBindingViewGroupHelper.blendedBitmap, localBindingViewGroupHelper.blendedBitmapSrcRect, localBindingViewGroupHelper.blendedBitmapDstRect, BindingViewGroupHelper.blendPaint);
    localBindingViewGroupHelper.viewGroup.invalidate();
  }
  
  public BindingViewGroupHelper getBindingViewGroupHelper()
  {
    return this.bindingViewGroupHelper;
  }
  
  public Data getData()
  {
    return this.bindingViewGroupHelper.getData();
  }
  
  public DataList getDataRow()
  {
    return this.bindingViewGroupHelper.dataRow;
  }
  
  public final boolean isOwnedByParent()
  {
    return this.bindingViewGroupHelper.isOwnedByParent;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    BindingViewGroupHelper localBindingViewGroupHelper = this.bindingViewGroupHelper;
    localBindingViewGroupHelper.attached = true;
    localBindingViewGroupHelper.temporarilyDetached = false;
    localBindingViewGroupHelper.updateRegistrationIfNeeded();
  }
  
  public final void onDataUpdated(Data paramData)
  {
    BindingViewGroupHelper localBindingViewGroupHelper = this.bindingViewGroupHelper;
    ((BindingViewGroup)localBindingViewGroupHelper.viewGroup).updateBoundDataProxy(paramData);
    localBindingViewGroupHelper.boundData = paramData;
    localBindingViewGroupHelper.clearBlendedBitmap();
    localBindingViewGroupHelper.sendDataToChildrenWhoWantIt(localBindingViewGroupHelper.viewGroup, paramData);
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    BindingViewGroupHelper localBindingViewGroupHelper = this.bindingViewGroupHelper;
    localBindingViewGroupHelper.attached = false;
    localBindingViewGroupHelper.temporarilyDetached = false;
    localBindingViewGroupHelper.updateRegistrationIfNeeded();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    BindingViewGroupHelper.markDescendantsAsOwned(this.bindingViewGroupHelper.viewGroup);
  }
  
  public void onFinishTemporaryDetach()
  {
    super.onFinishTemporaryDetach();
    this.bindingViewGroupHelper.onFinishTemporaryDetach();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    BindingViewGroupHelper localBindingViewGroupHelper = this.bindingViewGroupHelper;
    if (localBindingViewGroupHelper.temporarilyDetached) {
      localBindingViewGroupHelper.onFinishTemporaryDetach();
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public void onStartTemporaryDetach()
  {
    super.onStartTemporaryDetach();
    BindingViewGroupHelper localBindingViewGroupHelper = this.bindingViewGroupHelper;
    localBindingViewGroupHelper.temporarilyDetached = true;
    localBindingViewGroupHelper.updateRegistrationIfNeeded();
  }
  
  public final void prepareForRecycling()
  {
    BindingViewGroupHelper localBindingViewGroupHelper = this.bindingViewGroupHelper;
    localBindingViewGroupHelper.setDataRow(null);
    localBindingViewGroupHelper.cardGroup = null;
    localBindingViewGroupHelper.cardGroupPosition = -1;
    localBindingViewGroupHelper.viewGroup.setOnLongClickListener(null);
    localBindingViewGroupHelper.viewGroup.setLongClickable(false);
    if (Build.VERSION.SDK_INT >= 14) {
      localBindingViewGroupHelper.viewGroup.animate().cancel();
    }
    if (Build.VERSION.SDK_INT >= 12)
    {
      localBindingViewGroupHelper.viewGroup.setTranslationX(0.0F);
      localBindingViewGroupHelper.viewGroup.setTranslationY(0.0F);
      localBindingViewGroupHelper.viewGroup.setScaleX(1.0F);
      localBindingViewGroupHelper.viewGroup.setScaleY(1.0F);
      localBindingViewGroupHelper.viewGroup.setAlpha(1.0F);
      localBindingViewGroupHelper.viewGroup.setRotation(0.0F);
    }
    if ((localBindingViewGroupHelper.viewGroup instanceof BindingViewGroup)) {
      ((BindingViewGroup)localBindingViewGroupHelper.viewGroup).setMeasuredDimensionProxy$255f295();
    }
  }
  
  public void setDataRow(DataList paramDataList)
  {
    this.bindingViewGroupHelper.setDataRow(paramDataList);
  }
  
  public final void setMeasuredDimensionProxy$255f295()
  {
    setMeasuredDimension(0, 0);
  }
  
  public void setOwnedByParent(boolean paramBoolean)
  {
    this.bindingViewGroupHelper.isOwnedByParent = paramBoolean;
  }
  
  public final boolean startEditingIfPossible()
  {
    BindingViewGroupHelper localBindingViewGroupHelper = this.bindingViewGroupHelper;
    if (localBindingViewGroupHelper.cardGroup != null)
    {
      CardGroup localCardGroup = localBindingViewGroupHelper.cardGroup;
      ViewGroup localViewGroup = localBindingViewGroupHelper.viewGroup;
      int i = localBindingViewGroupHelper.cardGroupPosition;
      Logd localLogd = CardGroup.LOGD;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(i);
      localLogd.i("startEditing position: %d", arrayOfObject);
      ViewParent localViewParent = localViewGroup.getParent();
      EditableAdapterView localEditableAdapterView = null;
      Object localObject;
      if (localViewParent != null)
      {
        if ((localViewParent instanceof EditableAdapterView)) {
          localEditableAdapterView = (EditableAdapterView)localViewParent;
        }
      }
      else
      {
        if (localEditableAdapterView == null) {
          break label165;
        }
        localObject = localCardGroup.groupList.getData(i).get(EditableAdapterView.DK_IS_EDITABLE);
        if (localObject != null) {
          break label152;
        }
      }
      label152:
      for (boolean bool = false;; bool = ((Boolean)localObject).booleanValue())
      {
        if (!bool) {
          break label165;
        }
        localCardGroup.groupList.getItemId(i);
        return localEditableAdapterView.startEditing$7df9d7e5();
        localViewParent = localViewParent.getParent();
        break;
      }
    }
    label165:
    return false;
  }
  
  public final void superDrawProxy(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
  }
  
  public final void updateBoundDataProxy(Data paramData)
  {
    updateBoundData(paramData);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.widget.BindingFrameLayout
 * JD-Core Version:    0.7.0.1
 */