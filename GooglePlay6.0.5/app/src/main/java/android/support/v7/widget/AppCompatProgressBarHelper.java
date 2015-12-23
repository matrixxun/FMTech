package android.support.v7.widget;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v4.graphics.drawable.DrawableWrapper;
import android.support.v7.internal.widget.TintManager;
import android.support.v7.internal.widget.TintTypedArray;
import android.util.AttributeSet;
import android.widget.ProgressBar;

class AppCompatProgressBarHelper
{
  private static final int[] TINT_ATTRS = { 16843067, 16843068 };
  Bitmap mSampleTile;
  final TintManager mTintManager;
  private final ProgressBar mView;
  
  AppCompatProgressBarHelper(ProgressBar paramProgressBar, TintManager paramTintManager)
  {
    this.mView = paramProgressBar;
    this.mTintManager = paramTintManager;
  }
  
  private Drawable tileify(Drawable paramDrawable, boolean paramBoolean)
  {
    if ((paramDrawable instanceof DrawableWrapper))
    {
      Drawable localDrawable2 = ((DrawableWrapper)paramDrawable).getWrappedDrawable();
      if (localDrawable2 != null)
      {
        Drawable localDrawable3 = tileify(localDrawable2, paramBoolean);
        ((DrawableWrapper)paramDrawable).setWrappedDrawable(localDrawable3);
      }
    }
    do
    {
      Object localObject = paramDrawable;
      for (;;)
      {
        return localObject;
        if (!(paramDrawable instanceof LayerDrawable)) {
          break;
        }
        LayerDrawable localLayerDrawable = (LayerDrawable)paramDrawable;
        int i = localLayerDrawable.getNumberOfLayers();
        Drawable[] arrayOfDrawable = new Drawable[i];
        int j = 0;
        if (j < i)
        {
          int m = localLayerDrawable.getId(j);
          Drawable localDrawable1 = localLayerDrawable.getDrawable(j);
          if ((m == 16908301) || (m == 16908303)) {}
          for (boolean bool = true;; bool = false)
          {
            arrayOfDrawable[j] = tileify(localDrawable1, bool);
            j++;
            break;
          }
        }
        localObject = new LayerDrawable(arrayOfDrawable);
        for (int k = 0; k < i; k++) {
          ((LayerDrawable)localObject).setId(k, localLayerDrawable.getId(k));
        }
      }
    } while (!(paramDrawable instanceof BitmapDrawable));
    Bitmap localBitmap = ((BitmapDrawable)paramDrawable).getBitmap();
    if (this.mSampleTile == null) {
      this.mSampleTile = localBitmap;
    }
    ShapeDrawable localShapeDrawable = new ShapeDrawable(new RoundRectShape(new float[] { 5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F }, null, null));
    BitmapShader localBitmapShader = new BitmapShader(localBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
    localShapeDrawable.getPaint().setShader(localBitmapShader);
    if (paramBoolean) {
      return new ClipDrawable(localShapeDrawable, 3, 1);
    }
    return localShapeDrawable;
  }
  
  void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt)
  {
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes$1a6c1917(this.mView.getContext(), paramAttributeSet, TINT_ATTRS, paramInt);
    Object localObject = localTintTypedArray.getDrawableIfKnown(0);
    if (localObject != null)
    {
      ProgressBar localProgressBar = this.mView;
      if ((localObject instanceof AnimationDrawable))
      {
        AnimationDrawable localAnimationDrawable1 = (AnimationDrawable)localObject;
        int i = localAnimationDrawable1.getNumberOfFrames();
        AnimationDrawable localAnimationDrawable2 = new AnimationDrawable();
        localAnimationDrawable2.setOneShot(localAnimationDrawable1.isOneShot());
        for (int j = 0; j < i; j++)
        {
          Drawable localDrawable2 = tileify(localAnimationDrawable1.getFrame(j), true);
          localDrawable2.setLevel(10000);
          localAnimationDrawable2.addFrame(localDrawable2, localAnimationDrawable1.getDuration(j));
        }
        localAnimationDrawable2.setLevel(10000);
        localObject = localAnimationDrawable2;
      }
      localProgressBar.setIndeterminateDrawable((Drawable)localObject);
    }
    Drawable localDrawable1 = localTintTypedArray.getDrawableIfKnown(1);
    if (localDrawable1 != null) {
      this.mView.setProgressDrawable(tileify(localDrawable1, false));
    }
    localTintTypedArray.mWrapped.recycle();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.AppCompatProgressBarHelper
 * JD-Core Version:    0.7.0.1
 */