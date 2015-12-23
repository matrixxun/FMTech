package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.ButtonBar.ClickListener;
import com.google.android.finsky.layout.MyReviewReplyLayout;
import com.google.android.finsky.layout.RateReviewEditor.ReviewChangeListener;
import com.google.android.finsky.layout.RateReviewEditor2;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.layout.play.PersonAvatarView;
import com.google.android.finsky.layout.play.PlayRatingBar;
import com.google.android.finsky.layout.play.PlayRatingBar.OnRatingChangeListener;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.layout.PlayPopupMenu;
import com.google.android.play.layout.PlayPopupMenu.OnPopupActionSelectedListener;
import com.google.android.play.layout.PlayTextView;
import com.google.android.play.layout.StarRatingBar;
import com.google.android.play.utils.PlayTouchDelegate;

public class RateReviewModuleV2Layout
  extends FrameLayout
  implements ModuleDividerItemDecoration.NoBottomDivider, PlayPopupMenu.OnPopupActionSelectedListener
{
  DocV2 mAuthor;
  View mBottomDivider;
  View mDisclaimer;
  Document mDocument;
  boolean mHasBinded;
  private TextView mHeaderText;
  PersonAvatarView mMyAvatar;
  StarRatingBar mMyRatingBar;
  TextView mMyRatingText;
  NavigationManager mNavigationManager;
  private Rect mOverflowArea = new Rect();
  private int mOverflowTouchExtend;
  PlayStoreUiElementNode mParentNode;
  RateReviewClickListener mRateReviewClickListener;
  ImageView mRatingOverflow;
  RateReviewEditor2 mReviewEditor;
  PlayRatingBar mReviewRatingBar;
  MyReviewReplyLayout mReviewReplyLayout;
  ViewStub mReviewReplyStub;
  PlayTextView mReviewText;
  
  public RateReviewModuleV2Layout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public RateReviewModuleV2Layout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mOverflowTouchExtend = paramContext.getResources().getDimensionPixelSize(2131493389);
  }
  
  final void bindHeader()
  {
    Object localObject;
    if (this.mAuthor == null)
    {
      localObject = null;
      if (localObject == null) {
        break label85;
      }
      this.mHeaderText.setText((CharSequence)localObject);
    }
    for (;;)
    {
      if (this.mAuthor == null) {
        break label97;
      }
      GenericUiElementNode localGenericUiElementNode = new GenericUiElementNode(279, this.mAuthor.serverLogsCookie, null, this.mParentNode);
      this.mMyAvatar.bindView(this.mAuthor, this.mNavigationManager, FinskyApp.get().mBitmapLoader, localGenericUiElementNode);
      return;
      localObject = this.mAuthor.title;
      break;
      label85:
      this.mHeaderText.setVisibility(8);
    }
    label97:
    this.mMyAvatar.setOnClickListener(null);
    this.mMyAvatar.setVisibility(8);
  }
  
  final void configureOverflow(final boolean paramBoolean)
  {
    this.mRatingOverflow.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        PlayPopupMenu localPlayPopupMenu = new PlayPopupMenu(RateReviewModuleV2Layout.this.getContext(), RateReviewModuleV2Layout.this.mRatingOverflow);
        Resources localResources = RateReviewModuleV2Layout.this.getContext().getResources();
        FinskyApp.get().getEventLogger().logClickEvent(238, null, RateReviewModuleV2Layout.this.mParentNode);
        if (!paramBoolean) {
          localPlayPopupMenu.addMenuItem(1, localResources.getString(2131362106), true, RateReviewModuleV2Layout.this);
        }
        localPlayPopupMenu.addMenuItem(2, localResources.getString(2131362078), true, RateReviewModuleV2Layout.this);
        RateReviewModuleV2Layout.this.mRatingOverflow.setImageResource(2130838073);
        localPlayPopupMenu.mOnPopupDismissListener = new PopupWindow.OnDismissListener()
        {
          public final void onDismiss()
          {
            RateReviewModuleV2Layout.this.mRatingOverflow.setImageResource(2130838071);
          }
        };
        localPlayPopupMenu.show();
      }
    });
    this.mRatingOverflow.setVisibility(0);
    if (this.mOverflowArea.isEmpty())
    {
      this.mRatingOverflow.getHitRect(this.mOverflowArea);
      this.mOverflowArea.inset(-this.mOverflowTouchExtend, -this.mOverflowTouchExtend);
      setTouchDelegate(new PlayTouchDelegate(this.mOverflowArea, this.mRatingOverflow));
    }
  }
  
  public final void onActionSelected(int paramInt)
  {
    if (this.mRateReviewClickListener == null) {
      return;
    }
    switch (paramInt)
    {
    default: 
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      FinskyLog.wtf("Unknown item selected on RateReviewModuleV2Layout overflow menu: %d", arrayOfObject);
      return;
    case 1: 
      this.mRateReviewClickListener.onEditClicked();
      return;
    }
    this.mRateReviewClickListener.onDeleteClicked();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mMyAvatar = ((PersonAvatarView)findViewById(2131755981));
    this.mHeaderText = ((TextView)findViewById(2131755982));
    this.mMyRatingText = ((TextView)findViewById(2131755983));
    this.mReviewRatingBar = ((PlayRatingBar)findViewById(2131755998));
    this.mMyRatingBar = ((StarRatingBar)findViewById(2131755996));
    this.mRatingOverflow = ((ImageView)findViewById(2131756003));
    this.mReviewText = ((PlayTextView)findViewById(2131755999));
    this.mDisclaimer = findViewById(2131755997);
    this.mReviewEditor = ((RateReviewEditor2)findViewById(2131756001));
    this.mReviewReplyStub = ((ViewStub)findViewById(2131756000));
    this.mBottomDivider = findViewById(2131756002);
  }
  
  public static abstract interface RateReviewClickListener
  {
    public abstract void onCancelClicked();
    
    public abstract void onDeleteClicked();
    
    public abstract void onEditClicked();
    
    public abstract void onRatingClicked(int paramInt);
    
    public abstract void onSubmitClicked(int paramInt, String paramString);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.RateReviewModuleV2Layout
 * JD-Core Version:    0.7.0.1
 */