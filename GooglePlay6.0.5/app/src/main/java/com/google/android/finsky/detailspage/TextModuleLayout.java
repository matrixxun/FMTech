package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.layout.DecoratedTextView;
import com.google.android.finsky.layout.DetailsTextBlock;
import com.google.android.finsky.layout.DetailsTextIconContainer;
import com.google.android.play.layout.PlayTextView;
import com.google.android.play.utils.UrlSpanUtils;
import com.google.android.play.utils.UrlSpanUtils.Listener;

public class TextModuleLayout
  extends LinearLayout
{
  DetailsTextBlock mBodyContainerView;
  PlayTextView mCalloutView;
  final CharSequence mExpandedWhatsNewHeader;
  TextView mFooterLabel;
  DetailsTextIconContainer mIconContainer;
  UrlSpanUtils.Listener mLinkClickListener;
  int mMaxCollapsedLines;
  View.OnClickListener mReadMoreClickListener;
  View mSpacerView;
  final int mTopDeveloperColor;
  DecoratedTextView mTopDeveloperLabel;
  private boolean mUrlSpanClicked;
  final int mWhatsNewVerticalMargin;
  
  public TextModuleLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public TextModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mMaxCollapsedLines = localResources.getInteger(2131623959);
    this.mTopDeveloperColor = localResources.getColor(2131689740);
    this.mWhatsNewVerticalMargin = localResources.getDimensionPixelSize(2131493292);
    this.mExpandedWhatsNewHeader = localResources.getString(2131362092).toUpperCase();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mCalloutView = ((PlayTextView)findViewById(2131755415));
    this.mCalloutView.setMovementMethod(LinkMovementMethod.getInstance());
    this.mSpacerView = findViewById(2131755175);
    this.mBodyContainerView = ((DetailsTextBlock)findViewById(2131755416));
    this.mIconContainer = ((DetailsTextIconContainer)findViewById(2131755417));
    this.mTopDeveloperLabel = ((DecoratedTextView)findViewById(2131755418));
    this.mFooterLabel = ((TextView)findViewById(2131755419));
    this.mFooterLabel.setText(getContext().getString(2131362634).toUpperCase());
  }
  
  final void selfishifyUrlSpans(CharSequence paramCharSequence)
  {
    if (TextUtils.isEmpty(paramCharSequence)) {
      return;
    }
    UrlSpanUtils.selfishifyUrlSpans(paramCharSequence, null, new UrlSpanUtils.Listener()
    {
      public final void onClick(View paramAnonymousView, String paramAnonymousString)
      {
        TextModuleLayout.access$102$7104479a(TextModuleLayout.this);
        if (TextModuleLayout.this.mLinkClickListener != null) {
          TextModuleLayout.this.mLinkClickListener.onClick(paramAnonymousView, paramAnonymousString);
        }
      }
    });
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.TextModuleLayout
 * JD-Core Version:    0.7.0.1
 */