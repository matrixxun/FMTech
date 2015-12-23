package com.google.android.finsky.layout;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.view.ViewStub;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.VideoDetails;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.ForegroundRelativeLayout;
import com.google.android.play.layout.PlayActionButton;
import com.google.android.play.utils.PlayCorpusUtils;
import java.util.Locale;

public class ExtrasItemSnippet
  extends ForegroundRelativeLayout
  implements PlayStoreUiElementNode
{
  public BitmapLoader mBitmapLoader;
  public OnCollapsedStateChanged mCollapsedStateChangedListener;
  public Document mDocument;
  private TextView mDuration;
  public View mExpandedContent;
  private TextView mExpandedDescription;
  private HeroGraphicView mExpandedVideoScreencap;
  private ViewStub mExpandedViewStub;
  private TextView mExtrasItemTitle;
  private final Handler mHandler = new Handler(Looper.getMainLooper());
  public NavigationManager mNavigationManager;
  public PlayStoreUiElementNode mParentNode;
  private PlayActionButton mPlayButton;
  private final Runnable mScrollerRunnable = new Runnable()
  {
    public final void run()
    {
      int[] arrayOfInt = new int[2];
      ExtrasItemSnippet.this.getLocationInWindow(arrayOfInt);
      int i = arrayOfInt[1];
      ViewParent localViewParent = ExtrasItemSnippet.this.getParent();
      for (;;)
      {
        if ((localViewParent instanceof RecyclerView))
        {
          RecyclerView localRecyclerView = (RecyclerView)localViewParent;
          localRecyclerView.getLocationInWindow(arrayOfInt);
          localRecyclerView.smoothScrollBy(0, i - arrayOfInt[1]);
          return;
        }
        localViewParent = localViewParent.getParent();
        if (localViewParent == null) {
          FinskyLog.wtf("Found no suitable parent.", new Object[0]);
        }
      }
    }
  };
  public PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(2702);
  
  public ExtrasItemSnippet(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    throw new IllegalStateException("unwanted children");
  }
  
  public Document getDocument()
  {
    return this.mDocument;
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public final boolean isExpanded()
  {
    return (this.mExpandedContent != null) && (this.mExpandedContent.getVisibility() == 0);
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if (this.mDocument.getVideoDetails() == null)
    {
      setVisibility(8);
      return;
    }
    updateContentView();
    setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        ExtrasItemSnippet.access$000(ExtrasItemSnippet.this);
        ExtrasItemSnippet.access$100(ExtrasItemSnippet.this);
      }
    });
  }
  
  protected void onDetachedFromWindow()
  {
    this.mHandler.removeCallbacks(this.mScrollerRunnable);
    super.onDetachedFromWindow();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mExpandedViewStub = ((ViewStub)findViewById(2131755478));
    this.mPlayButton = ((PlayActionButton)findViewById(2131755499));
    this.mDuration = ((TextView)findViewById(2131755500));
    this.mExtrasItemTitle = ((TextView)findViewById(2131755501));
    this.mDuration.setTextColor(PlayCorpusUtils.getPrimaryTextColor(getContext(), 4));
  }
  
  public void setExpandedContentVisibility(int paramInt)
  {
    if (this.mExpandedContent == null)
    {
      this.mExpandedContent = this.mExpandedViewStub.inflate();
      this.mExpandedDescription = ((TextView)findViewById(2131755494));
      this.mExpandedVideoScreencap = ((HeroGraphicView)findViewById(2131755493));
    }
    this.mExpandedContent.setVisibility(paramInt);
    CharSequence localCharSequence;
    if (paramInt == 8)
    {
      this.mExtrasItemTitle.setMaxLines(2);
      this.mExtrasItemTitle.setEllipsize(TextUtils.TruncateAt.END);
      if (paramInt == 0)
      {
        this.mExpandedVideoScreencap.bindVideoThumbnail(this.mBitmapLoader, this.mDocument);
        localCharSequence = this.mDocument.getDescription();
        if (!TextUtils.isEmpty(localCharSequence)) {
          break label170;
        }
        this.mExpandedDescription.setVisibility(8);
      }
    }
    for (;;)
    {
      if (this.mCollapsedStateChangedListener != null) {
        this.mCollapsedStateChangedListener.onCollapsedStateChanged$2f40e1a3(this);
      }
      this.mHandler.post(this.mScrollerRunnable);
      return;
      this.mExtrasItemTitle.setMaxLines(1000);
      this.mExtrasItemTitle.setEllipsize(null);
      break;
      label170:
      this.mExpandedDescription.setVisibility(0);
      this.mExpandedDescription.setText(localCharSequence);
    }
  }
  
  public final void updateContentView()
  {
    VideoDetails localVideoDetails = this.mDocument.getVideoDetails();
    this.mExtrasItemTitle.setText(this.mDocument.mDocument.title);
    this.mExtrasItemTitle.setMaxLines(2);
    this.mExtrasItemTitle.setEllipsize(TextUtils.TruncateAt.END);
    Account localAccount1 = FinskyApp.get().getCurrentAccount();
    Libraries localLibraries = FinskyApp.get().mLibraries;
    final Account localAccount2 = LibraryUtils.getOwnerWithCurrentAccount(this.mDocument, localLibraries, localAccount1);
    if (localAccount2 != null)
    {
      this.mDuration.setText(null);
      this.mPlayButton.setVisibility(0);
      this.mPlayButton.setDrawAsLabel(false);
      this.mPlayButton.setActionStyle(2);
      this.mPlayButton.setEnabled(true);
      this.mPlayButton.configure(4, 2131362550, new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          FinskyApp.get().getEventLogger().logClickEvent(218, null, ExtrasItemSnippet.this.mParentNode);
          ExtrasItemSnippet.this.mNavigationManager.openItem(localAccount2, ExtrasItemSnippet.this.mDocument, false);
        }
      });
      return;
    }
    this.mPlayButton.setVisibility(8);
    this.mDuration.setText(localVideoDetails.duration.toUpperCase(Locale.getDefault()));
  }
  
  public static abstract interface OnCollapsedStateChanged
  {
    public abstract void onCollapsedStateChanged$2f40e1a3(ExtrasItemSnippet paramExtrasItemSnippet);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ExtrasItemSnippet
 * JD-Core Version:    0.7.0.1
 */