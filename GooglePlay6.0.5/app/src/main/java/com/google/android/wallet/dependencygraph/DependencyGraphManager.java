package com.google.android.wallet.dependencygraph;

import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.ComponentValuesDependency;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.DependencyGraph;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.ResultingActionReference;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.TriggerValueReference;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.TriggerValueReference.ComponentValue;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.TriggerValueReference.ValueChangedTrigger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public final class DependencyGraphManager
  implements TriggerListener
{
  public final SparseBooleanArray mResultingActionComponentIds;
  public final SparseArray<ResultingActionComponent> mResultingActionComponentMap;
  public final SparseArray<TriggerComponent> mTriggerComponentMap;
  public final SparseArray<ArrayList<DependencyGraphOuterClass.TriggerValueReference>> mTriggerValueReferenceMap;
  final SimpleArrayMap<TriggerValueKey, DependencyGraphOuterClass.ComponentValuesDependency> mTriggerValueToDependencyMap;
  
  public DependencyGraphManager(DependencyGraphOuterClass.DependencyGraph paramDependencyGraph)
  {
    int i;
    if ((paramDependencyGraph != null) && (paramDependencyGraph.valuesDependency != null))
    {
      i = 1;
      int j = 0;
      if (i != 0) {
        j = paramDependencyGraph.valuesDependency.length;
      }
      this.mTriggerComponentMap = new SparseArray(j);
      this.mTriggerValueReferenceMap = new SparseArray(j);
      this.mResultingActionComponentMap = new SparseArray(j);
      this.mResultingActionComponentIds = new SparseBooleanArray(j);
      this.mTriggerValueToDependencyMap = new SimpleArrayMap(j);
      if (i != 0) {
        break label99;
      }
    }
    for (;;)
    {
      return;
      i = 0;
      break;
      label99:
      for (DependencyGraphOuterClass.ComponentValuesDependency localComponentValuesDependency : paramDependencyGraph.valuesDependency)
      {
        for (DependencyGraphOuterClass.TriggerValueReference localTriggerValueReference : localComponentValuesDependency.triggerValueReference)
        {
          TriggerValueKey localTriggerValueKey = new TriggerValueKey(localTriggerValueReference);
          this.mTriggerValueToDependencyMap.put(localTriggerValueKey, localComponentValuesDependency);
          ArrayList localArrayList = (ArrayList)this.mTriggerValueReferenceMap.get(localTriggerValueReference.componentId);
          if (localArrayList == null)
          {
            localArrayList = new ArrayList();
            this.mTriggerValueReferenceMap.put(localTriggerValueReference.componentId, localArrayList);
          }
          localArrayList.add(localTriggerValueReference);
        }
        for (DependencyGraphOuterClass.ResultingActionReference localResultingActionReference : localComponentValuesDependency.resultingActionReference) {
          this.mResultingActionComponentIds.put(localResultingActionReference.componentId, true);
        }
      }
    }
  }
  
  public final void onTriggerOccurred(DependencyGraphOuterClass.TriggerValueReference paramTriggerValueReference)
  {
    DependencyGraphOuterClass.ComponentValuesDependency localComponentValuesDependency = (DependencyGraphOuterClass.ComponentValuesDependency)this.mTriggerValueToDependencyMap.get(new TriggerValueKey(paramTriggerValueReference));
    DependencyGraphOuterClass.TriggerValueReference[] arrayOfTriggerValueReference = localComponentValuesDependency.triggerValueReference;
    int i = arrayOfTriggerValueReference.length;
    int j = 0;
    if (j < i)
    {
      DependencyGraphOuterClass.TriggerValueReference localTriggerValueReference = arrayOfTriggerValueReference[j];
      if (localTriggerValueReference != paramTriggerValueReference) {
        ((TriggerComponent)this.mTriggerComponentMap.get(localTriggerValueReference.componentId)).checkTrigger(localTriggerValueReference);
      }
    }
    for (;;)
    {
      return;
      j++;
      break;
      for (DependencyGraphOuterClass.ResultingActionReference localResultingActionReference : localComponentValuesDependency.resultingActionReference) {
        ((ResultingActionComponent)this.mResultingActionComponentMap.get(localResultingActionReference.componentId)).applyResultingAction(localResultingActionReference, localComponentValuesDependency.triggerValueReference);
      }
    }
  }
  
  static final class TriggerValueKey
  {
    private final DependencyGraphOuterClass.TriggerValueReference mTriggerValueReference;
    
    public TriggerValueKey(DependencyGraphOuterClass.TriggerValueReference paramTriggerValueReference)
    {
      this.mTriggerValueReference = paramTriggerValueReference;
    }
    
    public final boolean equals(Object paramObject)
    {
      if (paramObject == this) {}
      DependencyGraphOuterClass.TriggerValueReference localTriggerValueReference;
      do
      {
        return true;
        if (!(paramObject instanceof TriggerValueKey)) {
          return false;
        }
        localTriggerValueReference = ((TriggerValueKey)paramObject).mTriggerValueReference;
        if (this.mTriggerValueReference.componentId != localTriggerValueReference.componentId) {
          return false;
        }
        if (this.mTriggerValueReference.triggerType != localTriggerValueReference.triggerType) {
          return false;
        }
        switch (this.mTriggerValueReference.triggerType)
        {
        case 2: 
        default: 
          Locale localLocale = Locale.US;
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = Integer.valueOf(this.mTriggerValueReference.componentId);
          throw new IllegalArgumentException(String.format(localLocale, "Unknown trigger type for trigger on component %s", arrayOfObject2));
        }
        if (this.mTriggerValueReference.valueChangedTrigger.newValue != null) {
          break;
        }
      } while (localTriggerValueReference.valueChangedTrigger.newValue == null);
      return false;
      if (this.mTriggerValueReference.valueChangedTrigger.newValue.valueId != null) {
        return Arrays.equals(this.mTriggerValueReference.valueChangedTrigger.newValue.valueId, localTriggerValueReference.valueChangedTrigger.newValue.valueId);
      }
      if (!TextUtils.isEmpty(this.mTriggerValueReference.valueChangedTrigger.newValue.valueStringRegex)) {
        return this.mTriggerValueReference.valueChangedTrigger.newValue.valueStringRegex.equals(localTriggerValueReference.valueChangedTrigger.newValue.valueStringRegex);
      }
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(this.mTriggerValueReference.componentId);
      throw new IllegalArgumentException(String.format("TriggerValueReference for component %d does not have a known value for newValue component value", arrayOfObject1));
    }
    
    public final int hashCode()
    {
      int i = 31 * this.mTriggerValueReference.componentId * (37 * this.mTriggerValueReference.triggerType);
      DependencyGraphOuterClass.TriggerValueReference.ComponentValue localComponentValue;
      if (this.mTriggerValueReference.valueChangedTrigger != null)
      {
        localComponentValue = this.mTriggerValueReference.valueChangedTrigger.newValue;
        if (localComponentValue == null) {
          i *= 41;
        }
      }
      else
      {
        return i;
      }
      if (localComponentValue.valueId != null) {
        return i * Arrays.hashCode(localComponentValue.valueId);
      }
      if (!TextUtils.isEmpty(localComponentValue.valueStringRegex)) {
        return i * localComponentValue.valueStringRegex.hashCode();
      }
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(this.mTriggerValueReference.componentId);
      throw new IllegalArgumentException(String.format("TriggerValueReference for component %d does not have a known value for newValue component value", arrayOfObject));
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.dependencygraph.DependencyGraphManager
 * JD-Core Version:    0.7.0.1
 */