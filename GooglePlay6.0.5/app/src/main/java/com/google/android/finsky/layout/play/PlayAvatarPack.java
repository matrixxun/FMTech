package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;
import com.android.vending.R.styleable;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.adapters.Recyclable;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.GPlusDialogsHelper;
import com.google.android.play.image.AvatarCropTransformation;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;

public class PlayAvatarPack
  extends ViewGroup
  implements Recyclable
{
  private PersonAvatarView mAvatarPrimary;
  private PersonAvatarView[] mAvatarsSecondary;
  private final int mPrimarySize;
  
  public PlayAvatarPack(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayAvatarPack(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PlayAvatarPack);
    this.mPrimarySize = localTypedArray.getDimensionPixelSize(0, paramContext.getResources().getDimensionPixelSize(2131492936));
    localTypedArray.recycle();
  }
  
  private PersonAvatarView makePersonAvatarView()
  {
    PersonAvatarView localPersonAvatarView = new PersonAvatarView(getContext());
    localPersonAvatarView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    localPersonAvatarView.setBitmapTransformation(AvatarCropTransformation.getFullAvatarCrop(getResources()));
    localPersonAvatarView.setHasFixedBounds(true);
    localPersonAvatarView.setFocusable(true);
    return localPersonAvatarView;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getWidth();
    int j = getHeight();
    int k = i / 2 - this.mPrimarySize / 2;
    int m = k + this.mPrimarySize;
    if (this.mAvatarPrimary != null) {
      this.mAvatarPrimary.layout(k, 0, m, this.mPrimarySize);
    }
    if (this.mAvatarsSecondary == null) {}
    for (int n = 0; n > 0; n = this.mAvatarsSecondary.length)
    {
      int i1 = k;
      int i2 = m;
      for (int i3 = 0; i3 < n; i3 += 2)
      {
        PersonAvatarView localPersonAvatarView1 = this.mAvatarsSecondary[i3];
        int i4 = localPersonAvatarView1.getMeasuredWidth();
        int i5 = localPersonAvatarView1.getMeasuredHeight();
        int i6 = i1 + (int)(0.33F * i4);
        int i7 = (j - i5) / 2;
        localPersonAvatarView1.layout(i6 - i4, i7, i6, i7 + i5);
        i1 = i6 - i4;
        PersonAvatarView localPersonAvatarView2 = this.mAvatarsSecondary[(i3 + 1)];
        int i8 = localPersonAvatarView2.getMeasuredWidth();
        int i9 = localPersonAvatarView2.getMeasuredHeight();
        int i10 = i2 - (int)(0.33F * i8);
        int i11 = (j - i9) / 2;
        localPersonAvatarView2.layout(i10, i11, i10 + i8, i11 + i9);
        i2 = i10 + i8;
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    if (this.mAvatarPrimary != null)
    {
      int i2 = View.MeasureSpec.makeMeasureSpec(this.mPrimarySize, 1073741824);
      this.mAvatarPrimary.measure(i2, i2);
    }
    if (this.mAvatarsSecondary == null) {}
    for (int j = 0; j > 0; j = this.mAvatarsSecondary.length)
    {
      int k = i - this.mPrimarySize - getPaddingLeft() - getPaddingRight();
      int m = (int)(0.7F * this.mPrimarySize);
      if (j * (int)(0.67F * m) > k) {
        m = (int)(k / j / 0.67F);
      }
      int n = View.MeasureSpec.makeMeasureSpec(m, 1073741824);
      for (int i1 = 0; i1 < j; i1++) {
        this.mAvatarsSecondary[i1].measure(n, n);
      }
    }
    setMeasuredDimension(i, this.mPrimarySize);
  }
  
  public final void onRecycle()
  {
    if (this.mAvatarPrimary != null) {
      this.mAvatarPrimary.clearImage();
    }
    if (this.mAvatarsSecondary != null) {
      for (int i = 0; i < this.mAvatarsSecondary.length; i++) {
        if (this.mAvatarsSecondary[i] != null) {
          this.mAvatarsSecondary[i].clearImage();
        }
      }
    }
  }
  
  public final void setData(DocV2 paramDocV2, DocV2[] paramArrayOfDocV2, final NavigationManager paramNavigationManager, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    if (paramDocV2 == null)
    {
      setVisibility(4);
      return;
    }
    setVisibility(0);
    removeAllViews();
    BitmapLoader localBitmapLoader = FinskyApp.get().mBitmapLoader;
    getResources();
    if (paramArrayOfDocV2 == null) {}
    for (int i = 0;; i = Math.min(4, 2 * (paramArrayOfDocV2.length / 2)))
    {
      this.mAvatarsSecondary = new PersonAvatarView[i];
      if (i <= 0) {
        break label193;
      }
      for (int j = 0; j < i; j++)
      {
        final DocV2 localDocV2 = paramArrayOfDocV2[j];
        this.mAvatarsSecondary[j] = makePersonAvatarView();
        final GenericUiElementNode localGenericUiElementNode2 = new GenericUiElementNode(279, localDocV2.serverLogsCookie, null, paramPlayStoreUiElementNode);
        paramPlayStoreUiElementNode.childImpression(localGenericUiElementNode2);
        View.OnClickListener local1 = new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            paramNavigationManager.getClickListener(new Document(localDocV2), localGenericUiElementNode2).onClick(paramAnonymousView);
          }
        };
        this.mAvatarsSecondary[j].bindView(localDocV2, local1, localBitmapLoader);
      }
    }
    for (int k = 0; k < i; k++) {
      addView(this.mAvatarsSecondary[(-1 + (i - k))]);
    }
    label193:
    this.mAvatarPrimary = makePersonAvatarView();
    GenericUiElementNode localGenericUiElementNode1 = new GenericUiElementNode(279, null, null, paramPlayStoreUiElementNode);
    paramPlayStoreUiElementNode.childImpression(localGenericUiElementNode1);
    Document localDocument = new Document(paramDocV2);
    if (NavigationManager.hasClickListener(localDocument)) {}
    for (Object localObject = paramNavigationManager.getClickListener(localDocument, localGenericUiElementNode1);; localObject = new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            GPlusDialogsHelper.showGPlusSignUpDialog(paramNavigationManager.getActivePage().mFragmentManager, PlayAvatarPack.this.getContext());
          }
        })
    {
      this.mAvatarPrimary.bindView(paramDocV2, (View.OnClickListener)localObject, localBitmapLoader);
      addView(this.mAvatarPrimary);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayAvatarPack
 * JD-Core Version:    0.7.0.1
 */