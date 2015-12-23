package com.google.android.finsky.layout;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.google.android.finsky.activities.TextSectionStateListener;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.layout.PlayTextView;
import com.google.android.play.utils.UrlSpanUtils;
import com.google.android.play.utils.UrlSpanUtils.Listener;

public abstract class DetailsTextSection
  extends ForegroundLinearLayout
{
  protected DetailsTextBlock mBodyContainerView;
  private PlayTextView mCalloutView;
  protected TextView mFooterLabel;
  protected final int mMaxCollapsedLines;
  private NavigationManager mNavigationManager;
  private TextSectionStateListener mSectionStateListener;
  private View mSpacerView;
  protected boolean mUrlSpanClicked;
  
  public DetailsTextSection(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DetailsTextSection(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mMaxCollapsedLines = paramContext.getResources().getInteger(2131623959);
  }
  
  public final void bindEditorialDescription$5a1ee44d$316bfc12(NavigationManager paramNavigationManager, Document paramDocument, int paramInt)
  {
    CharSequence localCharSequence = paramDocument.getDescription();
    this.mNavigationManager = paramNavigationManager;
    this.mSectionStateListener = null;
    if (!TextUtils.isEmpty(localCharSequence)) {
      UrlSpanUtils.selfishifyUrlSpans(localCharSequence, null, new UrlSpanUtils.Listener()
      {
        public final void onClick(View paramAnonymousView, String paramAnonymousString)
        {
          DetailsTextSection.this.mUrlSpanClicked = true;
          Context localContext = paramAnonymousView.getContext();
          Intent localIntent = new Intent("android.intent.action.VIEW");
          Uri localUri = Uri.parse(paramAnonymousString);
          localIntent.setData(localUri);
          localIntent.setPackage(localContext.getPackageName());
          if (localContext.getPackageManager().resolveActivity(localIntent, 65536) != null)
          {
            DetailsTextSection.this.mNavigationManager.handleDeepLink(localUri, null);
            return;
          }
          localIntent.setPackage(null);
          localContext.startActivity(localIntent);
        }
      });
    }
    this.mCalloutView.setVisibility(8);
    this.mBodyContainerView.bind(null, localCharSequence, this.mMaxCollapsedLines);
    this.mBodyContainerView.removeCorpusStyle();
    this.mSpacerView.setVisibility(0);
    setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        DetailsTextSection.this.handleClick();
      }
    });
    this.mBodyContainerView.setBodyClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        DetailsTextSection.this.handleClick();
      }
    });
    if (UiUtils.isColorBright(paramInt)) {}
    for (int i = 2131689624;; i = 2131689753)
    {
      int j = getResources().getColor(i);
      setBackgroundColor(paramInt);
      this.mBodyContainerView.setEditorialStyle(paramInt, j);
      this.mFooterLabel.setTextColor(j);
      setVisibility(0);
      return;
    }
  }
  
  protected void handleClick()
  {
    if (this.mUrlSpanClicked) {
      this.mUrlSpanClicked = false;
    }
    while (this.mSectionStateListener == null) {
      return;
    }
    getId();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mCalloutView = ((PlayTextView)findViewById(2131755415));
    this.mSpacerView = findViewById(2131755175);
    this.mBodyContainerView = ((DetailsTextBlock)findViewById(2131755416));
    this.mFooterLabel = ((TextView)findViewById(2131755419));
    this.mFooterLabel.setText(getContext().getString(2131362634).toUpperCase());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DetailsTextSection
 * JD-Core Version:    0.7.0.1
 */