package com.google.android.libraries.bind.widget;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.libraries.bind.card.CardGroup;
import com.google.android.libraries.bind.data.BindingViewGroup;
import com.google.android.libraries.bind.data.BindingViewGroup.BlendMode;
import com.google.android.libraries.bind.data.Bound;
import com.google.android.libraries.bind.data.Data;
import com.google.android.libraries.bind.data.DataList;
import com.google.android.libraries.bind.data.DataView;
import com.google.android.libraries.bind.data.DataViewHelper;
import com.google.android.libraries.bind.util.Util;

public final class BindingViewGroupHelper
  extends DataViewHelper
{
  static final Paint blendPaint = new Paint();
  long blendBitmapDurationMs;
  long blendBitmapStartTimeMs;
  BindingViewGroup.BlendMode blendMode;
  Bitmap blendedBitmap;
  boolean blendedBitmapDstComputed;
  final Rect blendedBitmapDstRect = new Rect();
  final Rect blendedBitmapSrcRect = new Rect();
  Data boundData;
  public boolean capturing;
  CardGroup cardGroup;
  int cardGroupPosition = -1;
  boolean isOwnedByParent;
  boolean supportsAnimationCapture;
  final ViewGroup viewGroup;
  
  public BindingViewGroupHelper(DataView paramDataView)
  {
    super(paramDataView);
    Util.checkPrecondition(paramDataView instanceof BindingViewGroup);
    Util.checkPrecondition(paramDataView instanceof ViewGroup);
    this.viewGroup = ((ViewGroup)paramDataView);
  }
  
  public static void markDescendantsAsOwned(ViewGroup paramViewGroup)
  {
    for (int i = -1 + paramViewGroup.getChildCount(); i >= 0; i--)
    {
      View localView = paramViewGroup.getChildAt(i);
      if ((localView instanceof ViewGroup))
      {
        if ((localView instanceof BindingViewGroup)) {
          ((BindingViewGroup)localView).setOwnedByParent(true);
        }
        markDescendantsAsOwned((ViewGroup)localView);
      }
    }
  }
  
  final void clearBlendedBitmap()
  {
    if (this.blendedBitmap != null)
    {
      this.blendedBitmap = null;
      this.blendMode = null;
      this.blendBitmapStartTimeMs = 0L;
      this.blendBitmapDurationMs = 0L;
      this.blendedBitmapDstComputed = false;
      this.viewGroup.setWillNotDraw(true);
    }
  }
  
  public final Data getData()
  {
    if (this.dataRow != null) {
      return this.dataRow.getData(0);
    }
    return this.boundData;
  }
  
  void sendDataToChildrenWhoWantIt(ViewGroup paramViewGroup, Data paramData)
  {
    int i = paramViewGroup.getChildCount();
    int j = 0;
    if (j < i)
    {
      View localView = paramViewGroup.getChildAt(j);
      if ((localView instanceof BindingViewGroup)) {
        if (((BindingViewGroup)localView).isOwnedByParent()) {
          ((BindingViewGroup)localView).onDataUpdated(paramData);
        }
      }
      for (;;)
      {
        if (((localView instanceof ViewGroup)) && (!(localView instanceof BindingViewGroup))) {
          sendDataToChildrenWhoWantIt((ViewGroup)localView, paramData);
        }
        j++;
        break;
        if ((localView instanceof Bound)) {
          ((Bound)localView).updateBoundData(paramData);
        }
      }
    }
  }
  
  public final void setDataRow(DataList paramDataList)
  {
    this.boundData = null;
    super.setDataRow(paramDataList);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.widget.BindingViewGroupHelper
 * JD-Core Version:    0.7.0.1
 */