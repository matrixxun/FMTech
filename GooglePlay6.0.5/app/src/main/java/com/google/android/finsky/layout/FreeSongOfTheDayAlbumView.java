package com.google.android.finsky.layout;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.PlayCardUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.PlayCardViewBase;

public class FreeSongOfTheDayAlbumView
  extends LinearLayout
  implements OnDataChangedListener
{
  private PlayCardViewBase mAlbumCard;
  private BitmapLoader mBitmapLoader;
  private DfeDetails mDetailsData;
  protected DfeApi mDfeApi;
  public TextView mHeader;
  protected NavigationManager mNavigationManager;
  public Document mParentDoc;
  public PlayStoreUiElementNode mParentNode;
  public String mUrl;
  
  public FreeSongOfTheDayAlbumView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public FreeSongOfTheDayAlbumView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void detachListeners()
  {
    if (this.mDetailsData != null) {
      this.mDetailsData.removeDataChangedListener(this);
    }
  }
  
  private void prepareAndPopulateContent()
  {
    Document localDocument = this.mDetailsData.getDocument();
    PlayCardUtils.bindCard(this.mAlbumCard, localDocument, this.mParentDoc.mDocument.docid, this.mBitmapLoader, this.mNavigationManager, this.mParentNode);
  }
  
  public final void init(DfeApi paramDfeApi, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader)
  {
    this.mDfeApi = paramDfeApi;
    this.mNavigationManager = paramNavigationManager;
    this.mBitmapLoader = paramBitmapLoader;
  }
  
  public final void onDataChanged()
  {
    if ((this.mDetailsData != null) && (this.mDetailsData.isReady()) && (this.mDetailsData.getDocument() != null))
    {
      prepareAndPopulateContent();
      return;
    }
    setVisibility(8);
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    detachListeners();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mAlbumCard = ((PlayCardViewBase)findViewById(2131755454));
    this.mHeader = ((TextView)findViewById(2131755487));
  }
  
  public final void setupView()
  {
    if (!TextUtils.isEmpty(this.mUrl))
    {
      setVisibility(0);
      detachListeners();
      this.mDetailsData = new DfeDetails(this.mDfeApi, this.mUrl);
      this.mDetailsData.addDataChangedListener(this);
      if (this.mDetailsData.getDocument() != null)
      {
        setVisibility(0);
        prepareAndPopulateContent();
        return;
      }
      this.mAlbumCard.bindLoading();
      return;
    }
    setVisibility(8);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.FreeSongOfTheDayAlbumView
 * JD-Core Version:    0.7.0.1
 */