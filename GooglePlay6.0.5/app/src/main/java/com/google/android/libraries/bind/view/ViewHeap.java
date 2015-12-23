package com.google.android.libraries.bind.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.google.android.libraries.bind.data.BindingViewGroup;
import com.google.android.libraries.bind.logging.Logd;
import com.google.android.libraries.bind.util.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class ViewHeap
{
  private static final Logd LOGD = Logd.get(ViewHeap.class);
  private static final WeakHashMap<View, View> inUseDebugMap = null;
  private final Map<Integer, List<View>> heap;
  private int inflatedCount;
  private final LayoutInflater inflater;
  private boolean isDisabledUntilNextGet;
  private int returnedCount;
  private int reusedCount;
  
  private int efficiencyPercent()
  {
    return 100 * this.reusedCount / (this.reusedCount + this.inflatedCount);
  }
  
  private static String getResourceName(int paramInt)
  {
    if (LOGD.isEnabled()) {
      return Util.getResourceName(paramInt);
    }
    return Integer.toString(paramInt);
  }
  
  private void logReuse(int paramInt, boolean paramBoolean)
  {
    this.reusedCount = (1 + this.reusedCount);
    if (!paramBoolean) {
      this.returnedCount = (1 + this.returnedCount);
    }
    Logd localLogd = LOGD;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = getResourceName(paramInt);
    arrayOfObject[1] = Integer.valueOf(efficiencyPercent());
    localLogd.d("Reusing view of type %s, efficiency: %d %%", arrayOfObject);
  }
  
  private void tryRecycleChildren(ViewGroup paramViewGroup)
  {
    int i = -1 + paramViewGroup.getChildCount();
    if (i >= 0)
    {
      View localView = paramViewGroup.getChildAt(i);
      boolean bool1;
      label129:
      Integer localInteger;
      if (((localView instanceof BindingViewGroup)) && (!((BindingViewGroup)localView).isOwnedByParent()))
      {
        paramViewGroup.removeViewAt(i);
        if ((Integer)localView.getTag() != null)
        {
          if (((localView instanceof BindingViewGroup)) && (!((BindingViewGroup)localView).isOwnedByParent())) {
            ((BindingViewGroup)localView).prepareForRecycling();
          }
          if ((localView instanceof ViewGroup)) {
            tryRecycleChildren((ViewGroup)localView);
          }
          if ((!(localView instanceof BindingViewGroup)) || (((BindingViewGroup)localView).isOwnedByParent())) {
            localView.measure(0, 0);
          }
          if (localView.getParent() != null) {
            break label188;
          }
          bool1 = true;
          Util.checkPrecondition(bool1);
          localInteger = (Integer)localView.getTag();
          if (!this.isDisabledUntilNextGet) {
            break label194;
          }
          Logd localLogd2 = LOGD;
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = getResourceName(localInteger.intValue());
          localLogd2.v("The heap is temporarily disabled after being cleared, not recycling view of type %s", arrayOfObject2);
        }
      }
      for (;;)
      {
        i--;
        break;
        label188:
        bool1 = false;
        break label129;
        label194:
        Object localObject = (List)this.heap.get(localInteger);
        if (localObject == null) {
          localObject = new ArrayList();
        }
        ((List)localObject).add(localView);
        Logd localLogd1 = LOGD;
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = getResourceName(localInteger.intValue());
        arrayOfObject1[1] = Integer.valueOf(((List)localObject).size());
        localLogd1.v("Recycled view of type %s, got %d in heap", arrayOfObject1);
        this.heap.put(localInteger, localObject);
        if (inUseDebugMap != null) {
          if (inUseDebugMap.remove(localView) == null) {
            break label346;
          }
        }
        label346:
        for (boolean bool2 = true;; bool2 = false)
        {
          Util.checkPrecondition(bool2, "Recycling a view we didn't create: " + localView);
          this.returnedCount = (1 + this.returnedCount);
          break;
        }
        if ((localView instanceof ViewGroup)) {
          tryRecycleChildren((ViewGroup)localView);
        }
      }
    }
  }
  
  public final View get(int paramInt, View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    Util.checkPrecondition(true);
    this.isDisabledUntilNextGet = false;
    Integer localInteger = null;
    Logd localLogd1;
    Object[] arrayOfObject1;
    if (paramView == null)
    {
      localLogd1 = LOGD;
      arrayOfObject1 = new Object[1];
      if (localInteger != null) {
        break label133;
      }
    }
    label133:
    for (String str = "null";; str = getResourceName(localInteger.intValue()))
    {
      arrayOfObject1[0] = str;
      localLogd1.v("convertViewResId: %s", arrayOfObject1);
      if (localInteger == null) {
        break label146;
      }
      if ((paramView instanceof ViewGroup))
      {
        LOGD.v("recycling children", new Object[0]);
        tryRecycleChildren((ViewGroup)paramView);
      }
      if (paramInt != localInteger.intValue()) {
        break label146;
      }
      logReuse(paramInt, false);
      paramView.setLayoutParams(paramLayoutParams);
      if (inUseDebugMap != null) {
        Util.checkPrecondition(inUseDebugMap.containsKey(paramView));
      }
      return paramView;
      localInteger = (Integer)paramView.getTag();
      break;
    }
    label146:
    List localList = (List)this.heap.get(Integer.valueOf(paramInt));
    Object localObject = null;
    if (localList != null)
    {
      int i = localList.size();
      localObject = null;
      if (i > 0) {
        localObject = (View)localList.remove(-1 + localList.size());
      }
    }
    if (localObject == null) {}
    for (;;)
    {
      try
      {
        View localView = this.inflater.inflate(paramInt, null, false);
        localObject = localView;
        ((View)localObject).setTag(Integer.valueOf(paramInt));
        this.inflatedCount = (1 + this.inflatedCount);
        Logd localLogd3 = LOGD;
        Object[] arrayOfObject3 = new Object[2];
        arrayOfObject3[0] = getResourceName(paramInt);
        arrayOfObject3[1] = Integer.valueOf(efficiencyPercent());
        localLogd3.i("Inflating view of type %s, efficiency: %d %%", arrayOfObject3);
        ((View)localObject).setLayoutParams(paramLayoutParams);
        if (inUseDebugMap != null)
        {
          if (inUseDebugMap.put(localObject, localObject) != null) {
            break label367;
          }
          bool = true;
          Util.checkPrecondition(bool);
        }
        return localObject;
      }
      catch (RuntimeException localRuntimeException)
      {
        Logd localLogd2 = LOGD;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Util.getResourceName(paramInt);
        localLogd2.e("Failed to inflate view resource: %s", arrayOfObject2);
        throw localRuntimeException;
      }
      logReuse(paramInt, true);
      continue;
      label367:
      boolean bool = false;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.view.ViewHeap
 * JD-Core Version:    0.7.0.1
 */