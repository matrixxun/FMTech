package com.google.android.libraries.bind.data;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.libraries.bind.R.styleable;
import com.google.android.libraries.bind.logging.Logd;

public class BoundHelper
{
  private static final Logd LOGD = Logd.get(BoundHelper.class);
  public final Integer bindBackgroundKey;
  public final Integer bindContentDescriptionKey;
  public final Integer bindEnabledKey;
  public final Integer bindInvisibilityKey;
  public final Integer bindMinHeightKey;
  public final Integer bindOnClickListenerKey;
  public final Integer bindPaddingTopKey;
  public final Integer bindTransitionNameKey;
  public final Integer bindVisibilityKey;
  protected final View view;
  
  public BoundHelper(Context paramContext, AttributeSet paramAttributeSet, View paramView)
  {
    this.view = paramView;
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.BoundView);
    this.bindBackgroundKey = getInteger(localTypedArray, R.styleable.BoundView_bindBackground);
    this.bindContentDescriptionKey = getInteger(localTypedArray, R.styleable.BoundView_bindContentDescription);
    this.bindEnabledKey = getInteger(localTypedArray, R.styleable.BoundView_bindEnabled);
    this.bindOnClickListenerKey = getInteger(localTypedArray, R.styleable.BoundView_bindOnClickListener);
    this.bindInvisibilityKey = getInteger(localTypedArray, R.styleable.BoundView_bindInvisibility);
    this.bindMinHeightKey = getInteger(localTypedArray, R.styleable.BoundView_bindMinHeight);
    this.bindPaddingTopKey = getInteger(localTypedArray, R.styleable.BoundView_bindPaddingTop);
    this.bindVisibilityKey = getInteger(localTypedArray, R.styleable.BoundView_bindVisibility);
    this.bindTransitionNameKey = getInteger(localTypedArray, R.styleable.BoundView_bindTransitionName);
    localTypedArray.recycle();
  }
  
  private static void bindVisibility(View paramView, Integer paramInteger, Data paramData, boolean paramBoolean)
  {
    if (paramInteger != null)
    {
      if ((paramData != null) && (paramData.containsKey(paramInteger.intValue()))) {
        break label43;
      }
      i = 8;
      if (paramBoolean) {
        if (i != 0) {
          break label93;
        }
      }
    }
    label43:
    label93:
    for (int i = 8;; i = 0)
    {
      paramView.setVisibility(i);
      return;
      if ((paramData.get(paramInteger.intValue()) instanceof Boolean))
      {
        if (((Boolean)paramData.get(paramInteger.intValue())).booleanValue()) {}
        for (i = 0;; i = 8) {
          break;
        }
      }
      i = 0;
      break;
    }
  }
  
  public static Integer getInteger(TypedArray paramTypedArray, int paramInt)
  {
    int i = paramTypedArray.getResourceId(paramInt, 2147483647);
    if (i == 2147483647) {
      return null;
    }
    return Integer.valueOf(i);
  }
  
  public final void updateBoundData(Data paramData)
  {
    View localView1 = this.view;
    Integer localInteger1 = this.bindBackgroundKey;
    Object localObject4;
    label31:
    Integer localInteger2;
    CharSequence localCharSequence;
    label55:
    boolean bool;
    label116:
    Integer localInteger4;
    Object localObject3;
    label163:
    int k;
    label171:
    Integer localInteger5;
    View.OnClickListener localOnClickListener;
    label202:
    View localView6;
    Integer localInteger6;
    Object localObject2;
    label236:
    int i;
    label244:
    int j;
    if (localInteger1 != null)
    {
      if (paramData == null)
      {
        localObject4 = null;
        if (localObject4 != null) {
          break label378;
        }
        localView1.setBackgroundResource(0);
      }
    }
    else
    {
      View localView2 = this.view;
      localInteger2 = this.bindContentDescriptionKey;
      if (localInteger2 != null)
      {
        if (paramData != null) {
          break label440;
        }
        localCharSequence = null;
        localView2.setContentDescription(localCharSequence);
      }
      View localView3 = this.view;
      Integer localInteger3 = this.bindEnabledKey;
      if (localInteger3 != null)
      {
        if ((paramData == null) || (!paramData.containsKey(localInteger3.intValue())) || (paramData.get(localInteger3.intValue()).equals(Boolean.FALSE))) {
          break label457;
        }
        bool = true;
        localView3.setEnabled(bool);
      }
      bindVisibility(this.view, this.bindInvisibilityKey, paramData, true);
      View localView4 = this.view;
      localInteger4 = this.bindMinHeightKey;
      if (localInteger4 != null)
      {
        if (paramData != null) {
          break label463;
        }
        localObject3 = Integer.valueOf(0);
        if (localObject3 != null) {
          break label480;
        }
        k = 0;
        localView4.setMinimumHeight(k);
      }
      View localView5 = this.view;
      localInteger5 = this.bindOnClickListenerKey;
      if (localInteger5 != null)
      {
        if (paramData != null) {
          break label490;
        }
        localOnClickListener = null;
        localView5.setOnClickListener(localOnClickListener);
      }
      localView6 = this.view;
      localInteger6 = this.bindPaddingTopKey;
      if (localInteger6 != null)
      {
        if (paramData != null) {
          break label507;
        }
        localObject2 = Integer.valueOf(0);
        if (localObject2 != null) {
          break label524;
        }
        i = 0;
        j = localView6.getPaddingBottom();
        if ((Build.VERSION.SDK_INT < 17) || (!localView6.isPaddingRelative())) {
          break label534;
        }
        localView6.setPaddingRelative(localView6.getPaddingStart(), i, localView6.getPaddingEnd(), j);
      }
    }
    for (;;)
    {
      View localView7 = this.view;
      Integer localInteger7 = this.bindTransitionNameKey;
      if ((localInteger7 != null) && (Build.VERSION.SDK_INT >= 21))
      {
        String str = null;
        if (paramData != null)
        {
          Object localObject1 = paramData.get(localInteger7.intValue());
          str = null;
          if (localObject1 != null) {
            str = localObject1.toString();
          }
        }
        localView7.setTransitionName(str);
      }
      bindVisibility(this.view, this.bindVisibilityKey, paramData, false);
      return;
      localObject4 = paramData.get(localInteger1.intValue());
      break;
      label378:
      if ((localObject4 instanceof Integer))
      {
        localView1.setBackgroundResource(((Integer)localObject4).intValue());
        break label31;
      }
      if ((localObject4 instanceof Drawable))
      {
        localView1.setBackgroundDrawable((Drawable)localObject4);
        break label31;
      }
      LOGD.e("Unrecognized bound background for key: %s", new Object[] { localInteger1 });
      break label31;
      label440:
      localCharSequence = (CharSequence)paramData.get(localInteger2.intValue());
      break label55;
      label457:
      bool = false;
      break label116;
      label463:
      localObject3 = (Number)paramData.get(localInteger4.intValue());
      break label163;
      label480:
      k = ((Number)localObject3).intValue();
      break label171;
      label490:
      localOnClickListener = (View.OnClickListener)paramData.get(localInteger5.intValue());
      break label202;
      label507:
      localObject2 = (Number)paramData.get(localInteger6.intValue());
      break label236;
      label524:
      i = ((Number)localObject2).intValue();
      break label244;
      label534:
      localView6.setPadding(localView6.getPaddingLeft(), i, localView6.getPaddingRight(), j);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.data.BoundHelper
 * JD-Core Version:    0.7.0.1
 */