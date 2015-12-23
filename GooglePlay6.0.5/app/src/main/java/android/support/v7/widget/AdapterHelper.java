package android.support.v7.widget;

import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SimplePool;
import java.util.ArrayList;
import java.util.List;

final class AdapterHelper
  implements OpReorderer.Callback
{
  final Callback mCallback;
  final boolean mDisableRecycler;
  int mExistingUpdateTypes = 0;
  Runnable mOnItemProcessedCallback;
  final OpReorderer mOpReorderer;
  final ArrayList<UpdateOp> mPendingUpdates = new ArrayList();
  final ArrayList<UpdateOp> mPostponedList = new ArrayList();
  private Pools.Pool<UpdateOp> mUpdateOpPool = new Pools.SimplePool(30);
  
  AdapterHelper(Callback paramCallback)
  {
    this(paramCallback, (byte)0);
  }
  
  private AdapterHelper(Callback paramCallback, byte paramByte)
  {
    this.mCallback = paramCallback;
    this.mDisableRecycler = false;
    this.mOpReorderer = new OpReorderer(this);
  }
  
  private boolean canFindInPreLayout(int paramInt)
  {
    int i = this.mPostponedList.size();
    label111:
    for (int j = 0; j < i; j++)
    {
      UpdateOp localUpdateOp = (UpdateOp)this.mPostponedList.get(j);
      if (localUpdateOp.cmd == 8)
      {
        if (findPositionOffset(localUpdateOp.itemCount, j + 1) == paramInt) {
          return true;
        }
      }
      else if (localUpdateOp.cmd == 1)
      {
        int k = localUpdateOp.positionStart + localUpdateOp.itemCount;
        for (int m = localUpdateOp.positionStart;; m++)
        {
          if (m >= k) {
            break label111;
          }
          if (findPositionOffset(m, j + 1) == paramInt) {
            break;
          }
        }
      }
    }
    return false;
  }
  
  private void dispatchAndUpdateViewHolders(UpdateOp paramUpdateOp)
  {
    if ((paramUpdateOp.cmd == 1) || (paramUpdateOp.cmd == 8)) {
      throw new IllegalArgumentException("should not dispatch add or move for pre layout");
    }
    int i = updatePositionWithPostponed(paramUpdateOp.positionStart, paramUpdateOp.cmd);
    int j = 1;
    int k = paramUpdateOp.positionStart;
    int m;
    int n;
    label110:
    int i1;
    int i3;
    switch (paramUpdateOp.cmd)
    {
    case 3: 
    default: 
      throw new IllegalArgumentException("op should be remove or update." + paramUpdateOp);
    case 4: 
      m = 1;
      n = 1;
      if (n >= paramUpdateOp.itemCount) {
        break label286;
      }
      i1 = updatePositionWithPostponed(paramUpdateOp.positionStart + m * n, paramUpdateOp.cmd);
      int i2 = paramUpdateOp.cmd;
      i3 = 0;
      switch (i2)
      {
      case 3: 
      default: 
        if (i3 != 0) {
          j++;
        }
        break;
      }
      break;
    }
    for (;;)
    {
      n++;
      break label110;
      m = 0;
      break;
      if (i1 == i + 1) {}
      for (i3 = 1;; i3 = 0) {
        break;
      }
      if (i1 == i) {}
      for (i3 = 1;; i3 = 0) {
        break;
      }
      UpdateOp localUpdateOp2 = obtainUpdateOp(paramUpdateOp.cmd, i, j, paramUpdateOp.payload);
      dispatchFirstPassAndUpdateViewHolders(localUpdateOp2, k);
      recycleUpdateOp(localUpdateOp2);
      if (paramUpdateOp.cmd == 4) {
        k += j;
      }
      i = i1;
      j = 1;
    }
    label286:
    Object localObject = paramUpdateOp.payload;
    recycleUpdateOp(paramUpdateOp);
    if (j > 0)
    {
      UpdateOp localUpdateOp1 = obtainUpdateOp(paramUpdateOp.cmd, i, j, localObject);
      dispatchFirstPassAndUpdateViewHolders(localUpdateOp1, k);
      recycleUpdateOp(localUpdateOp1);
    }
  }
  
  private void dispatchFirstPassAndUpdateViewHolders(UpdateOp paramUpdateOp, int paramInt)
  {
    this.mCallback.onDispatchFirstPass(paramUpdateOp);
    switch (paramUpdateOp.cmd)
    {
    case 3: 
    default: 
      throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
    case 2: 
      this.mCallback.offsetPositionsForRemovingInvisible(paramInt, paramUpdateOp.itemCount);
      return;
    }
    this.mCallback.markViewHoldersUpdated(paramInt, paramUpdateOp.itemCount, paramUpdateOp.payload);
  }
  
  private void postponeAndUpdateViewHolders(UpdateOp paramUpdateOp)
  {
    this.mPostponedList.add(paramUpdateOp);
    switch (paramUpdateOp.cmd)
    {
    case 3: 
    case 5: 
    case 6: 
    case 7: 
    default: 
      throw new IllegalArgumentException("Unknown update op type for " + paramUpdateOp);
    case 1: 
      this.mCallback.offsetPositionsForAdd(paramUpdateOp.positionStart, paramUpdateOp.itemCount);
      return;
    case 8: 
      this.mCallback.offsetPositionsForMove(paramUpdateOp.positionStart, paramUpdateOp.itemCount);
      return;
    case 2: 
      this.mCallback.offsetPositionsForRemovingLaidOutOrNewView(paramUpdateOp.positionStart, paramUpdateOp.itemCount);
      return;
    }
    this.mCallback.markViewHoldersUpdated(paramUpdateOp.positionStart, paramUpdateOp.itemCount, paramUpdateOp.payload);
  }
  
  private void recycleUpdateOpsAndClearList(List<UpdateOp> paramList)
  {
    int i = paramList.size();
    for (int j = 0; j < i; j++) {
      recycleUpdateOp((UpdateOp)paramList.get(j));
    }
    paramList.clear();
  }
  
  private int updatePositionWithPostponed(int paramInt1, int paramInt2)
  {
    int i = -1 + this.mPostponedList.size();
    if (i >= 0)
    {
      UpdateOp localUpdateOp2 = (UpdateOp)this.mPostponedList.get(i);
      int k;
      int m;
      if (localUpdateOp2.cmd == 8) {
        if (localUpdateOp2.positionStart < localUpdateOp2.itemCount)
        {
          k = localUpdateOp2.positionStart;
          m = localUpdateOp2.itemCount;
          label64:
          if ((paramInt1 < k) || (paramInt1 > m)) {
            break label192;
          }
          if (k != localUpdateOp2.positionStart) {
            break label149;
          }
          if (paramInt2 != 1) {
            break label129;
          }
          localUpdateOp2.itemCount = (1 + localUpdateOp2.itemCount);
          label103:
          paramInt1++;
        }
      }
      for (;;)
      {
        i--;
        break;
        k = localUpdateOp2.itemCount;
        m = localUpdateOp2.positionStart;
        break label64;
        label129:
        if (paramInt2 != 2) {
          break label103;
        }
        localUpdateOp2.itemCount = (-1 + localUpdateOp2.itemCount);
        break label103;
        label149:
        if (paramInt2 == 1) {
          localUpdateOp2.positionStart = (1 + localUpdateOp2.positionStart);
        }
        for (;;)
        {
          paramInt1--;
          break;
          if (paramInt2 == 2) {
            localUpdateOp2.positionStart = (-1 + localUpdateOp2.positionStart);
          }
        }
        label192:
        if (paramInt1 < localUpdateOp2.positionStart) {
          if (paramInt2 == 1)
          {
            localUpdateOp2.positionStart = (1 + localUpdateOp2.positionStart);
            localUpdateOp2.itemCount = (1 + localUpdateOp2.itemCount);
          }
          else if (paramInt2 == 2)
          {
            localUpdateOp2.positionStart = (-1 + localUpdateOp2.positionStart);
            localUpdateOp2.itemCount = (-1 + localUpdateOp2.itemCount);
            continue;
            if (localUpdateOp2.positionStart <= paramInt1)
            {
              if (localUpdateOp2.cmd == 1) {
                paramInt1 -= localUpdateOp2.itemCount;
              } else if (localUpdateOp2.cmd == 2) {
                paramInt1 += localUpdateOp2.itemCount;
              }
            }
            else if (paramInt2 == 1) {
              localUpdateOp2.positionStart = (1 + localUpdateOp2.positionStart);
            } else if (paramInt2 == 2) {
              localUpdateOp2.positionStart = (-1 + localUpdateOp2.positionStart);
            }
          }
        }
      }
    }
    int j = -1 + this.mPostponedList.size();
    if (j >= 0)
    {
      UpdateOp localUpdateOp1 = (UpdateOp)this.mPostponedList.get(j);
      if (localUpdateOp1.cmd == 8) {
        if ((localUpdateOp1.itemCount == localUpdateOp1.positionStart) || (localUpdateOp1.itemCount < 0))
        {
          this.mPostponedList.remove(j);
          recycleUpdateOp(localUpdateOp1);
        }
      }
      for (;;)
      {
        j--;
        break;
        if (localUpdateOp1.itemCount <= 0)
        {
          this.mPostponedList.remove(j);
          recycleUpdateOp(localUpdateOp1);
        }
      }
    }
    return paramInt1;
  }
  
  final void consumePostponedUpdates()
  {
    int i = this.mPostponedList.size();
    for (int j = 0; j < i; j++) {
      this.mCallback.onDispatchSecondPass((UpdateOp)this.mPostponedList.get(j));
    }
    recycleUpdateOpsAndClearList(this.mPostponedList);
    this.mExistingUpdateTypes = 0;
  }
  
  final void consumeUpdatesInOnePass()
  {
    consumePostponedUpdates();
    int i = this.mPendingUpdates.size();
    int j = 0;
    if (j < i)
    {
      UpdateOp localUpdateOp = (UpdateOp)this.mPendingUpdates.get(j);
      switch (localUpdateOp.cmd)
      {
      }
      for (;;)
      {
        if (this.mOnItemProcessedCallback != null) {
          this.mOnItemProcessedCallback.run();
        }
        j++;
        break;
        this.mCallback.onDispatchSecondPass(localUpdateOp);
        this.mCallback.offsetPositionsForAdd(localUpdateOp.positionStart, localUpdateOp.itemCount);
        continue;
        this.mCallback.onDispatchSecondPass(localUpdateOp);
        this.mCallback.offsetPositionsForRemovingInvisible(localUpdateOp.positionStart, localUpdateOp.itemCount);
        continue;
        this.mCallback.onDispatchSecondPass(localUpdateOp);
        this.mCallback.markViewHoldersUpdated(localUpdateOp.positionStart, localUpdateOp.itemCount, localUpdateOp.payload);
        continue;
        this.mCallback.onDispatchSecondPass(localUpdateOp);
        this.mCallback.offsetPositionsForMove(localUpdateOp.positionStart, localUpdateOp.itemCount);
      }
    }
    recycleUpdateOpsAndClearList(this.mPendingUpdates);
    this.mExistingUpdateTypes = 0;
  }
  
  final int findPositionOffset(int paramInt)
  {
    return findPositionOffset(paramInt, 0);
  }
  
  final int findPositionOffset(int paramInt1, int paramInt2)
  {
    int i = this.mPostponedList.size();
    int j = paramInt2;
    UpdateOp localUpdateOp;
    if (j < i)
    {
      localUpdateOp = (UpdateOp)this.mPostponedList.get(j);
      if (localUpdateOp.cmd == 8) {
        if (localUpdateOp.positionStart == paramInt1) {
          paramInt1 = localUpdateOp.itemCount;
        }
      }
    }
    for (;;)
    {
      j++;
      break;
      if (localUpdateOp.positionStart < paramInt1) {
        paramInt1--;
      }
      if (localUpdateOp.itemCount <= paramInt1)
      {
        paramInt1++;
        continue;
        if (localUpdateOp.positionStart <= paramInt1) {
          if (localUpdateOp.cmd == 2)
          {
            if (paramInt1 < localUpdateOp.positionStart + localUpdateOp.itemCount)
            {
              paramInt1 = -1;
              return paramInt1;
            }
            paramInt1 -= localUpdateOp.itemCount;
          }
          else if (localUpdateOp.cmd == 1)
          {
            paramInt1 += localUpdateOp.itemCount;
          }
        }
      }
    }
  }
  
  final boolean hasAnyUpdateTypes(int paramInt)
  {
    return (paramInt & this.mExistingUpdateTypes) != 0;
  }
  
  final boolean hasPendingUpdates()
  {
    return this.mPendingUpdates.size() > 0;
  }
  
  public final UpdateOp obtainUpdateOp(int paramInt1, int paramInt2, int paramInt3, Object paramObject)
  {
    UpdateOp localUpdateOp = (UpdateOp)this.mUpdateOpPool.acquire();
    if (localUpdateOp == null) {
      return new UpdateOp(paramInt1, paramInt2, paramInt3, paramObject);
    }
    localUpdateOp.cmd = paramInt1;
    localUpdateOp.positionStart = paramInt2;
    localUpdateOp.itemCount = paramInt3;
    localUpdateOp.payload = paramObject;
    return localUpdateOp;
  }
  
  final void preProcess()
  {
    OpReorderer localOpReorderer = this.mOpReorderer;
    ArrayList localArrayList = this.mPendingUpdates;
    int i;
    int j;
    label22:
    int k;
    label54:
    int i24;
    UpdateOp localUpdateOp2;
    UpdateOp localUpdateOp3;
    for (;;)
    {
      i = 0;
      j = -1 + localArrayList.size();
      if (j < 0) {
        break label282;
      }
      if (((UpdateOp)localArrayList.get(j)).cmd != 8) {
        break;
      }
      if (i == 0) {
        break label1914;
      }
      k = j;
      if (k == -1) {
        break label1301;
      }
      i24 = k + 1;
      localUpdateOp2 = (UpdateOp)localArrayList.get(k);
      localUpdateOp3 = (UpdateOp)localArrayList.get(i24);
      switch (localUpdateOp3.cmd)
      {
      case 3: 
      default: 
        break;
      case 1: 
        int i43 = localUpdateOp2.itemCount;
        int i44 = localUpdateOp3.positionStart;
        int i45 = 0;
        if (i43 < i44) {
          i45 = -1;
        }
        if (localUpdateOp2.positionStart < localUpdateOp3.positionStart) {
          i45++;
        }
        if (localUpdateOp3.positionStart <= localUpdateOp2.positionStart) {
          localUpdateOp2.positionStart += localUpdateOp3.itemCount;
        }
        if (localUpdateOp3.positionStart <= localUpdateOp2.itemCount) {
          localUpdateOp2.itemCount += localUpdateOp3.itemCount;
        }
        localUpdateOp3.positionStart = (i45 + localUpdateOp3.positionStart);
        localArrayList.set(k, localUpdateOp3);
        localArrayList.set(i24, localUpdateOp2);
      }
    }
    label282:
    label367:
    label1914:
    for (int i46 = 1;; i46 = i)
    {
      j--;
      i = i46;
      break label22;
      k = -1;
      break label54;
      UpdateOp localUpdateOp4 = null;
      int i30;
      int i33;
      if (localUpdateOp2.positionStart < localUpdateOp2.itemCount)
      {
        int i39 = localUpdateOp3.positionStart;
        int i40 = localUpdateOp2.positionStart;
        i30 = 0;
        i33 = 0;
        if (i39 == i40)
        {
          int i41 = localUpdateOp3.itemCount;
          int i42 = localUpdateOp2.itemCount - localUpdateOp2.positionStart;
          i30 = 0;
          i33 = 0;
          if (i41 == i42) {
            i33 = 1;
          }
        }
        if (localUpdateOp2.itemCount >= localUpdateOp3.positionStart) {
          break label521;
        }
        localUpdateOp3.positionStart = (-1 + localUpdateOp3.positionStart);
        if (localUpdateOp2.positionStart > localUpdateOp3.positionStart) {
          break label595;
        }
        localUpdateOp3.positionStart = (1 + localUpdateOp3.positionStart);
      }
      for (;;)
      {
        if (i33 == 0) {
          break label685;
        }
        localArrayList.set(k, localUpdateOp3);
        localArrayList.remove(i24);
        localOpReorderer.mCallback.recycleUpdateOp(localUpdateOp2);
        break;
        i30 = 1;
        int i31 = localUpdateOp3.positionStart;
        int i32 = 1 + localUpdateOp2.itemCount;
        i33 = 0;
        if (i31 != i32) {
          break label367;
        }
        int i34 = localUpdateOp3.itemCount;
        int i35 = localUpdateOp2.positionStart - localUpdateOp2.itemCount;
        i33 = 0;
        if (i34 != i35) {
          break label367;
        }
        i33 = 1;
        break label367;
        if (localUpdateOp2.itemCount >= localUpdateOp3.positionStart + localUpdateOp3.itemCount) {
          break label392;
        }
        localUpdateOp3.itemCount = (-1 + localUpdateOp3.itemCount);
        localUpdateOp2.cmd = 2;
        localUpdateOp2.itemCount = 1;
        if (localUpdateOp3.itemCount != 0) {
          break;
        }
        localArrayList.remove(i24);
        localOpReorderer.mCallback.recycleUpdateOp(localUpdateOp3);
        break;
        int i36 = localUpdateOp2.positionStart;
        int i37 = localUpdateOp3.positionStart + localUpdateOp3.itemCount;
        localUpdateOp4 = null;
        if (i36 < i37)
        {
          int i38 = localUpdateOp3.positionStart + localUpdateOp3.itemCount - localUpdateOp2.positionStart;
          localUpdateOp4 = localOpReorderer.mCallback.obtainUpdateOp(2, 1 + localUpdateOp2.positionStart, i38, null);
          localUpdateOp3.itemCount = (localUpdateOp2.positionStart - localUpdateOp3.positionStart);
        }
      }
      if (i30 != 0)
      {
        if (localUpdateOp4 != null)
        {
          if (localUpdateOp2.positionStart > localUpdateOp4.positionStart) {
            localUpdateOp2.positionStart -= localUpdateOp4.itemCount;
          }
          if (localUpdateOp2.itemCount > localUpdateOp4.positionStart) {
            localUpdateOp2.itemCount -= localUpdateOp4.itemCount;
          }
        }
        if (localUpdateOp2.positionStart > localUpdateOp3.positionStart) {
          localUpdateOp2.positionStart -= localUpdateOp3.itemCount;
        }
        if (localUpdateOp2.itemCount > localUpdateOp3.positionStart) {
          localUpdateOp2.itemCount -= localUpdateOp3.itemCount;
        }
        localArrayList.set(k, localUpdateOp3);
        if (localUpdateOp2.positionStart == localUpdateOp2.itemCount) {
          break label988;
        }
        localArrayList.set(i24, localUpdateOp2);
      }
      while (localUpdateOp4 != null)
      {
        localArrayList.add(k, localUpdateOp4);
        break;
        if (localUpdateOp4 != null)
        {
          if (localUpdateOp2.positionStart >= localUpdateOp4.positionStart) {
            localUpdateOp2.positionStart -= localUpdateOp4.itemCount;
          }
          if (localUpdateOp2.itemCount >= localUpdateOp4.positionStart) {
            localUpdateOp2.itemCount -= localUpdateOp4.itemCount;
          }
        }
        if (localUpdateOp2.positionStart >= localUpdateOp3.positionStart) {
          localUpdateOp2.positionStart -= localUpdateOp3.itemCount;
        }
        if (localUpdateOp2.itemCount < localUpdateOp3.positionStart) {
          break label811;
        }
        localUpdateOp2.itemCount -= localUpdateOp3.itemCount;
        break label811;
        localArrayList.remove(i24);
      }
      Object localObject2 = null;
      Object localObject3 = null;
      if (localUpdateOp2.itemCount < localUpdateOp3.positionStart)
      {
        localUpdateOp3.positionStart = (-1 + localUpdateOp3.positionStart);
        if (localUpdateOp2.positionStart > localUpdateOp3.positionStart) {
          break label1187;
        }
        localUpdateOp3.positionStart = (1 + localUpdateOp3.positionStart);
        localArrayList.set(i24, localUpdateOp2);
        if (localUpdateOp3.itemCount <= 0) {
          break label1278;
        }
        localArrayList.set(k, localUpdateOp3);
      }
      for (;;)
      {
        if (localObject2 != null) {
          localArrayList.add(k, localObject2);
        }
        if (localObject3 == null) {
          break;
        }
        localArrayList.add(k, localObject3);
        break;
        int i25 = localUpdateOp2.itemCount;
        int i26 = localUpdateOp3.positionStart + localUpdateOp3.itemCount;
        localObject2 = null;
        if (i25 >= i26) {
          break label1031;
        }
        localUpdateOp3.itemCount = (-1 + localUpdateOp3.itemCount);
        localObject2 = localOpReorderer.mCallback.obtainUpdateOp(4, localUpdateOp2.positionStart, 1, localUpdateOp3.payload);
        break label1031;
        int i27 = localUpdateOp2.positionStart;
        int i28 = localUpdateOp3.positionStart + localUpdateOp3.itemCount;
        localObject3 = null;
        if (i27 >= i28) {
          break label1056;
        }
        int i29 = localUpdateOp3.positionStart + localUpdateOp3.itemCount - localUpdateOp2.positionStart;
        localObject3 = localOpReorderer.mCallback.obtainUpdateOp(4, 1 + localUpdateOp2.positionStart, i29, localUpdateOp3.payload);
        localUpdateOp3.itemCount -= i29;
        break label1056;
        label1278:
        localArrayList.remove(k);
        localOpReorderer.mCallback.recycleUpdateOp(localUpdateOp3);
      }
      int m = this.mPendingUpdates.size();
      int n = 0;
      if (n < m)
      {
        UpdateOp localUpdateOp1 = (UpdateOp)this.mPendingUpdates.get(n);
        switch (localUpdateOp1.cmd)
        {
        }
        for (;;)
        {
          if (this.mOnItemProcessedCallback != null) {
            this.mOnItemProcessedCallback.run();
          }
          n++;
          break;
          postponeAndUpdateViewHolders(localUpdateOp1);
          continue;
          int i12 = localUpdateOp1.positionStart;
          int i13 = localUpdateOp1.positionStart + localUpdateOp1.itemCount;
          int i14 = -1;
          int i15 = localUpdateOp1.positionStart;
          int i16 = 0;
          if (i15 < i13)
          {
            int i17;
            int i22;
            int i20;
            int i21;
            if ((this.mCallback.findViewHolder(i15) != null) || (canFindInPreLayout(i15)))
            {
              i17 = 0;
              if (i14 == 0)
              {
                dispatchAndUpdateViewHolders(obtainUpdateOp(2, i12, i16, null));
                i17 = 1;
              }
              i14 = 1;
              if (i17 == 0) {
                break label1581;
              }
              i22 = i15 - i16;
              i20 = i13 - i16;
              i21 = 1;
            }
            for (;;)
            {
              int i23 = i22 + 1;
              i16 = i21;
              i13 = i20;
              i15 = i23;
              break;
              i17 = 0;
              if (i14 == 1)
              {
                postponeAndUpdateViewHolders(obtainUpdateOp(2, i12, i16, null));
                i17 = 1;
              }
              i14 = 0;
              break label1506;
              int i18 = i16 + 1;
              int i19 = i15;
              i20 = i13;
              i21 = i18;
              i22 = i19;
            }
          }
          if (i16 != localUpdateOp1.itemCount)
          {
            recycleUpdateOp(localUpdateOp1);
            localUpdateOp1 = obtainUpdateOp(2, i12, i16, null);
          }
          if (i14 == 0)
          {
            dispatchAndUpdateViewHolders(localUpdateOp1);
          }
          else
          {
            postponeAndUpdateViewHolders(localUpdateOp1);
            continue;
            int i1 = localUpdateOp1.positionStart;
            int i2 = localUpdateOp1.positionStart + localUpdateOp1.itemCount;
            int i3 = localUpdateOp1.positionStart;
            int i4 = 0;
            int i5 = i1;
            int i6 = -1;
            if (i3 < i2)
            {
              int i7;
              int i8;
              if ((this.mCallback.findViewHolder(i3) != null) || (canFindInPreLayout(i3)))
              {
                if (i6 == 0)
                {
                  dispatchAndUpdateViewHolders(obtainUpdateOp(4, i5, i4, localUpdateOp1.payload));
                  i4 = 0;
                  i5 = i3;
                }
                i7 = i5;
                i8 = i4;
              }
              for (int i9 = 1;; i9 = 0)
              {
                int i10 = i8 + 1;
                i3++;
                int i11 = i9;
                i4 = i10;
                i5 = i7;
                i6 = i11;
                break;
                if (i6 == 1)
                {
                  postponeAndUpdateViewHolders(obtainUpdateOp(4, i5, i4, localUpdateOp1.payload));
                  i4 = 0;
                  i5 = i3;
                }
                i7 = i5;
                i8 = i4;
              }
            }
            if (i4 != localUpdateOp1.itemCount)
            {
              Object localObject1 = localUpdateOp1.payload;
              recycleUpdateOp(localUpdateOp1);
              localUpdateOp1 = obtainUpdateOp(4, i5, i4, localObject1);
            }
            if (i6 == 0)
            {
              dispatchAndUpdateViewHolders(localUpdateOp1);
            }
            else
            {
              postponeAndUpdateViewHolders(localUpdateOp1);
              continue;
              postponeAndUpdateViewHolders(localUpdateOp1);
            }
          }
        }
      }
      this.mPendingUpdates.clear();
      return;
    }
  }
  
  public final void recycleUpdateOp(UpdateOp paramUpdateOp)
  {
    if (!this.mDisableRecycler)
    {
      paramUpdateOp.payload = null;
      this.mUpdateOpPool.release(paramUpdateOp);
    }
  }
  
  final void reset()
  {
    recycleUpdateOpsAndClearList(this.mPendingUpdates);
    recycleUpdateOpsAndClearList(this.mPostponedList);
    this.mExistingUpdateTypes = 0;
  }
  
  static abstract interface Callback
  {
    public abstract RecyclerView.ViewHolder findViewHolder(int paramInt);
    
    public abstract void markViewHoldersUpdated(int paramInt1, int paramInt2, Object paramObject);
    
    public abstract void offsetPositionsForAdd(int paramInt1, int paramInt2);
    
    public abstract void offsetPositionsForMove(int paramInt1, int paramInt2);
    
    public abstract void offsetPositionsForRemovingInvisible(int paramInt1, int paramInt2);
    
    public abstract void offsetPositionsForRemovingLaidOutOrNewView(int paramInt1, int paramInt2);
    
    public abstract void onDispatchFirstPass(AdapterHelper.UpdateOp paramUpdateOp);
    
    public abstract void onDispatchSecondPass(AdapterHelper.UpdateOp paramUpdateOp);
  }
  
  static final class UpdateOp
  {
    int cmd;
    int itemCount;
    Object payload;
    int positionStart;
    
    UpdateOp(int paramInt1, int paramInt2, int paramInt3, Object paramObject)
    {
      this.cmd = paramInt1;
      this.positionStart = paramInt2;
      this.itemCount = paramInt3;
      this.payload = paramObject;
    }
    
    public final boolean equals(Object paramObject)
    {
      if (this == paramObject) {}
      UpdateOp localUpdateOp;
      do
      {
        do
        {
          do
          {
            return true;
            if ((paramObject == null) || (getClass() != paramObject.getClass())) {
              return false;
            }
            localUpdateOp = (UpdateOp)paramObject;
            if (this.cmd != localUpdateOp.cmd) {
              return false;
            }
          } while ((this.cmd == 8) && (Math.abs(this.itemCount - this.positionStart) == 1) && (this.itemCount == localUpdateOp.positionStart) && (this.positionStart == localUpdateOp.itemCount));
          if (this.itemCount != localUpdateOp.itemCount) {
            return false;
          }
          if (this.positionStart != localUpdateOp.positionStart) {
            return false;
          }
          if (this.payload == null) {
            break;
          }
        } while (this.payload.equals(localUpdateOp.payload));
        return false;
      } while (localUpdateOp.payload == null);
      return false;
    }
    
    public final int hashCode()
    {
      return 31 * (31 * this.cmd + this.positionStart) + this.itemCount;
    }
    
    public final String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder().append(Integer.toHexString(System.identityHashCode(this))).append("[");
      String str;
      switch (this.cmd)
      {
      case 3: 
      case 5: 
      case 6: 
      case 7: 
      default: 
        str = "??";
      }
      for (;;)
      {
        return str + ",s:" + this.positionStart + "c:" + this.itemCount + ",p:" + this.payload + "]";
        str = "add";
        continue;
        str = "rm";
        continue;
        str = "up";
        continue;
        str = "mv";
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.AdapterHelper
 * JD-Core Version:    0.7.0.1
 */