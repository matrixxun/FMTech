package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.PlayActionButton;
import com.google.android.play.layout.PlayPopupMenu;
import com.google.android.play.layout.PlayPopupMenu.OnPopupActionSelectedListener;
import java.util.List;

public class PlayBundleBannerView
  extends PlayClusterView
{
  private FifeImageView mBackgroundImage;
  private TextView mCardDescription;
  private int mColumnCount;
  private View.OnClickListener mDismissListener;
  private String mDocId;
  private TextView mExploreButton;
  private LinearLayout mImages;
  private PlayActionButton mInstallAllButton;
  private View mOverflowMenu;
  
  public PlayBundleBannerView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayBundleBannerView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void configureExtraContent(BitmapLoader paramBitmapLoader, Document paramDocument, View.OnClickListener paramOnClickListener1, View.OnClickListener paramOnClickListener2, int paramInt, final View.OnClickListener paramOnClickListener3)
  {
    this.mDismissListener = paramOnClickListener3;
    if (paramDocument.mDocument.docid == this.mDocId) {
      return;
    }
    Resources localResources = getResources();
    this.mDocId = paramDocument.mDocument.docid;
    this.mColumnCount = paramInt;
    Common.Image localImage = (Common.Image)paramDocument.getImages(14).get(0);
    this.mBackgroundImage.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, paramBitmapLoader);
    this.mBackgroundImage.setOnClickListener(paramOnClickListener1);
    FifeImageView localFifeImageView1 = this.mBackgroundImage;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramDocument.mDocument.title;
    localFifeImageView1.setContentDescription(localResources.getString(2131361987, arrayOfObject));
    this.mCardDescription.setText(paramDocument.mDocument.title);
    this.mExploreButton.setText(localResources.getString(2131362137).toUpperCase());
    this.mInstallAllButton.configure(3, 2131362225, paramOnClickListener2);
    int i = Math.min(5, paramDocument.getChildCount());
    int j = 0;
    if (j < 5)
    {
      FifeImageView localFifeImageView2 = (FifeImageView)this.mImages.getChildAt(j);
      if (j < i)
      {
        localFifeImageView2.setImage(((Common.Image)paramDocument.getChildAt(j).getImages(4).get(0)).imageUrl, false, paramBitmapLoader);
        localFifeImageView2.setVisibility(0);
      }
      for (;;)
      {
        j++;
        break;
        localFifeImageView2.setVisibility(8);
      }
    }
    if (paramOnClickListener3 != null)
    {
      final PlayPopupMenu localPlayPopupMenu = new PlayPopupMenu(getContext(), this.mOverflowMenu);
      PlayPopupMenu.OnPopupActionSelectedListener local1 = new PlayPopupMenu.OnPopupActionSelectedListener()
      {
        public final void onActionSelected(int paramAnonymousInt)
        {
          paramOnClickListener3.onClick(PlayBundleBannerView.this);
          FinskyApp.get().getEventLogger().logClickEvent(212, null, PlayBundleBannerView.this.getPlayStoreUiElementNode());
        }
      };
      localPlayPopupMenu.addMenuItem(0, localResources.getString(2131362004), true, local1);
      View.OnClickListener local2 = new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          localPlayPopupMenu.show();
        }
      };
      this.mOverflowMenu.setOnClickListener(local2);
      this.mOverflowMenu.setVisibility(0);
    }
    for (;;)
    {
      logEmptyClusterImpression();
      return;
      this.mOverflowMenu.setVisibility(8);
    }
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 420;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mBackgroundImage = ((FifeImageView)findViewById(2131755834));
    this.mCardDescription = ((TextView)findViewById(2131755836));
    this.mExploreButton = ((TextView)findViewById(2131755838));
    this.mInstallAllButton = ((PlayActionButton)findViewById(2131755839));
    this.mImages = ((LinearLayout)findViewById(2131755835));
    this.mOverflowMenu = findViewById(2131755455);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    if (this.mColumnCount <= 0)
    {
      setMeasuredDimension(i, 0);
      return;
    }
    super.onMeasure(paramInt1, View.MeasureSpec.makeMeasureSpec(i / this.mColumnCount + getPaddingTop() + getPaddingBottom(), 1073741824));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayBundleBannerView
 * JD-Core Version:    0.7.0.1
 */