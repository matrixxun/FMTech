package com.google.android.finsky.layout;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.billing.lightpurchase.AgeVerificationActivity;
import com.google.android.finsky.config.G;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.previews.PreviewController;
import com.google.android.finsky.previews.SongSnippetStatusListener;
import com.google.android.finsky.previews.StatusListener;
import com.google.android.finsky.protos.Badge;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.MusicDetails;
import com.google.android.finsky.protos.SongDetails;
import com.google.android.finsky.utils.BadgeUtils;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;
import com.google.android.play.layout.PlayActionButton;
import com.google.android.play.utils.config.GservicesValue;

public class SongSnippet
  extends RelativeLayout
  implements PlayStoreUiElementNode
{
  private ImageView mAddedDrawable;
  private TextView mAddedState;
  public Document mAlbumDocument;
  public BitmapLoader mBitmapLoader;
  private PlayActionButton mBuyButton;
  private final PreviewController mConnection = new PreviewController(this.mStatusListener);
  public boolean mInitialized;
  public boolean mIsNewPurchase;
  public boolean mIsPlayable;
  public MusicDetails mMusicDetails;
  public NavigationManager mNavigationManager;
  public PlayStoreUiElementNode mParentNode;
  public boolean mShouldShowArtistName;
  private ImageView mSongBadge;
  public SongDetails mSongDetails;
  public Document mSongDocument;
  private TextView mSongDuration;
  private SongIndex mSongIndex;
  private DecoratedTextView mSongSubTitle;
  private DecoratedTextView mSongTitle;
  private final StatusListener mStatusListener = new SongSnippetStatusListener()
  {
    protected final void update(int paramAnonymousInt, boolean paramAnonymousBoolean)
    {
      SongSnippet.access$502$935b0b8(SongSnippet.this);
      if (SongSnippet.this.mSongDetails != null)
      {
        SongDetails localSongDetails = PreviewController.getCurrentTrack();
        String str = SongSnippet.this.mSongDetails.previewUrl;
        if ((localSongDetails == null) || (str == null) || (!str.equals(localSongDetails.previewUrl))) {}
      }
      for (int i = 1; i == 0; i = 0)
      {
        SongSnippet.access$600(SongSnippet.this);
        return;
      }
      SongSnippet.this.mSongIndex.setState(paramAnonymousInt);
      SongSnippet.this.setHighlighted(paramAnonymousBoolean);
    }
  };
  public int mTrackNumber;
  public PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(502);
  
  public SongSnippet(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void clearBuyButtonStyle$13462e()
  {
    this.mBuyButton.setDrawAsLabel(true);
    this.mBuyButton.setActionStyle(2);
    this.mBuyButton.setEnabled(false);
    this.mBuyButton.configure(2, 2131361830, null);
  }
  
  private void setBuyButtonStyle()
  {
    this.mBuyButton.setDrawAsLabel(false);
    this.mBuyButton.setActionStyle(2);
    this.mBuyButton.setEnabled(true);
  }
  
  private void setHighlighted(boolean paramBoolean)
  {
    Resources localResources = getResources();
    if (paramBoolean)
    {
      setBackgroundResource(2130837577);
      int n = localResources.getColor(2131689682);
      this.mSongTitle.setTextColor(n);
      this.mSongSubTitle.setTextColor(n);
      this.mSongDuration.setTextColor(n);
      return;
    }
    int i = ViewCompat.getPaddingStart(this);
    int j = ViewCompat.getPaddingEnd(this);
    int k = getPaddingTop();
    int m = getPaddingBottom();
    setBackgroundResource(2130837960);
    ViewCompat.setPaddingRelative(this, i, k, j, m);
    this.mSongTitle.setTextColor(localResources.getColor(2131689625));
    this.mSongSubTitle.setTextColor(localResources.getColor(2131689798));
    this.mSongDuration.setTextColor(localResources.getColor(2131689798));
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    throw new IllegalStateException("unwanted children");
  }
  
  public Document getDocument()
  {
    return this.mSongDocument;
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if (this.mMusicDetails == null) {
      setVisibility(8);
    }
    for (;;)
    {
      return;
      updateContentView();
      if (this.mIsPlayable) {
        setOnClickListener(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            if ((this.val$isMature) && (FinskyApp.get().getClientMutationCache(this.val$accountName).isAgeVerificationRequired()))
            {
              Intent localIntent = AgeVerificationActivity.createIntent(this.val$accountName, 2, null);
              SongSnippet.this.getContext().startActivity(localIntent);
              return;
            }
            PreviewController.togglePlayback(SongSnippet.this.mSongDetails);
          }
        });
      }
      while (((Boolean)G.prePurchaseSharingEnabled.get()).booleanValue())
      {
        setOnLongClickListener(new View.OnLongClickListener()
        {
          public final boolean onLongClick(View paramAnonymousView)
          {
            IntentUtils.shareDocument(SongSnippet.this.getContext(), SongSnippet.this.mSongDocument, SongSnippet.this);
            return true;
          }
        });
        return;
        setOnClickListener(null);
      }
    }
  }
  
  protected void onDetachedFromWindow()
  {
    this.mConnection.unbind();
    super.onDetachedFromWindow();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mSongIndex = ((SongIndex)findViewById(2131755569));
    this.mBuyButton = ((PlayActionButton)findViewById(2131755378));
    this.mSongBadge = ((ImageView)findViewById(2131755706));
    this.mSongDuration = ((TextView)findViewById(2131755707));
    this.mSongTitle = ((DecoratedTextView)findViewById(2131755708));
    this.mSongSubTitle = ((DecoratedTextView)findViewById(2131755709));
    this.mAddedState = ((TextView)findViewById(2131755477));
    this.mAddedDrawable = ((ImageView)findViewById(2131755476));
  }
  
  public void setState(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      this.mSongIndex.setState(0);
      return;
    case 2: 
      setHighlighted(true);
      SongIndex localSongIndex = this.mSongIndex;
      boolean bool = this.mIsPlayable;
      int i = 0;
      if (bool) {
        i = 3;
      }
      localSongIndex.setState(i);
      return;
    }
    this.mSongIndex.setState(5);
  }
  
  public final void updateContentView()
  {
    this.mSongIndex.setTrackNumber(this.mTrackNumber);
    int j;
    label151:
    label209:
    label248:
    AccountLibrary localAccountLibrary;
    Account localAccount1;
    if (this.mIsPlayable)
    {
      String str2 = DateUtils.formatElapsedTime(this.mMusicDetails.durationSec);
      this.mSongDuration.setText(str2);
      this.mSongDuration.setContentDescription(getResources().getString(2131362026, new Object[] { str2 }));
      this.mSongDuration.setVisibility(0);
      this.mSongTitle.setText(this.mSongDocument.mDocument.title);
      if (this.mSongDetails.badge == null) {
        break label393;
      }
      this.mSongBadge.setVisibility(0);
      Badge localBadge = this.mSongDetails.badge;
      int i = this.mSongBadge.getMeasuredHeight();
      Common.Image localImage = BadgeUtils.getImage$7bb5454b(localBadge);
      if (localImage != null)
      {
        if (!localImage.supportsFifeUrlOptions) {
          break label387;
        }
        j = i;
        BitmapLoader localBitmapLoader = this.mBitmapLoader;
        String str1 = localImage.imageUrl;
        BitmapLoader.BitmapLoadedHandler local3 = new BitmapLoader.BitmapLoadedHandler()
        {
          public final void onResponse(BitmapLoader.BitmapContainer paramAnonymousBitmapContainer)
          {
            if (paramAnonymousBitmapContainer != null) {
              SongSnippet.this.mSongBadge.setImageBitmap(paramAnonymousBitmapContainer.mBitmap);
            }
          }
        };
        BitmapLoader.BitmapContainer localBitmapContainer = localBitmapLoader.get(str1, j, j, local3);
        if (localBitmapContainer.mBitmap != null) {
          this.mSongBadge.setImageBitmap(localBitmapContainer.mBitmap);
        }
      }
      if (!this.mShouldShowArtistName) {
        break label404;
      }
      this.mSongSubTitle.setText(this.mSongDocument.mDocument.creator);
      BadgeUtils.configureCreatorBadge(this.mSongDocument, this.mBitmapLoader, this.mSongSubTitle);
      this.mBuyButton.setVisibility(0);
      localAccountLibrary = FinskyApp.get().mLibraries.getAccountLibrary(FinskyApp.get().getCurrentAccount());
      localAccount1 = FinskyApp.get().getCurrentAccount();
      Libraries localLibraries = FinskyApp.get().mLibraries;
      final Account localAccount2 = LibraryUtils.getOwnerWithCurrentAccount(this.mSongDocument, localLibraries, localAccount1);
      if (localAccount2 == null) {
        break label416;
      }
      setBuyButtonStyle();
      this.mBuyButton.configure(2, 2131362305, new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          FinskyApp.get().getEventLogger().logClickEvent(218, null, SongSnippet.this);
          SongSnippet.this.mNavigationManager.openItem(localAccount2, SongSnippet.this.mSongDocument, false);
        }
      });
      label327:
      if (!this.mIsNewPurchase) {
        break label566;
      }
      this.mSongDuration.setVisibility(8);
      this.mAddedState.setVisibility(0);
      this.mAddedDrawable.setVisibility(0);
    }
    for (;;)
    {
      this.mSongIndex.setClickable(false);
      PreviewController.getStatusUpdate(this.mStatusListener);
      return;
      this.mSongDuration.setVisibility(8);
      break;
      label387:
      j = 0;
      break label151;
      label393:
      this.mSongBadge.setVisibility(4);
      break label209;
      label404:
      this.mSongSubTitle.setVisibility(8);
      break label248;
      label416:
      if (this.mSongDocument.getOffer(1) != null)
      {
        setBuyButtonStyle();
        this.mBuyButton.configure(2, this.mSongDocument.getFormattedPrice$47921032(), this.mNavigationManager.getBuyImmediateClickListener(localAccount1, this.mSongDocument, 1, null, null, 200, this));
        break label327;
      }
      if (!LibraryUtils.isAvailable(this.mSongDocument, FinskyApp.get().mToc, localAccountLibrary)) {
        switch (this.mSongDocument.getAvailabilityRestriction())
        {
        default: 
          this.mBuyButton.setVisibility(4);
          break;
        case 13: 
          clearBuyButtonStyle$13462e();
          break;
        }
      }
      if ((this.mAlbumDocument != null) && (this.mAlbumDocument.getOffer(1) != null))
      {
        clearBuyButtonStyle$13462e();
        break label327;
      }
      this.mBuyButton.setVisibility(4);
      break label327;
      label566:
      this.mSongDuration.setVisibility(0);
      this.mAddedState.setVisibility(8);
      this.mAddedDrawable.setVisibility(8);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.SongSnippet
 * JD-Core Version:    0.7.0.1
 */