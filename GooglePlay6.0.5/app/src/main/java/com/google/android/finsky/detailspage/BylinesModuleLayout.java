package com.google.android.finsky.detailspage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.DecoratedTextView;

public class BylinesModuleLayout
  extends LinearLayout
{
  boolean mBinded;
  DecoratedTextView mHeaderView;
  LayoutInflater mInflater;
  LinearLayout mListingLayout;
  
  public BylinesModuleLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public BylinesModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mInflater = LayoutInflater.from(paramContext);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mListingLayout = ((LinearLayout)findViewById(2131755304));
    this.mHeaderView = ((DecoratedTextView)findViewById(2131755390));
  }
  
  public static final class BylineEntryInfo
  {
    public int bylineEntryType;
    public int clickEventType;
    Document doc;
    public int iconResourceId;
    String link;
    CharSequence text = null;
    public int textResourceId;
    
    public BylineEntryInfo(int paramInt1, int paramInt2, int paramInt3, Document paramDocument, String paramString, int paramInt4)
    {
      this.textResourceId = paramInt1;
      this.iconResourceId = paramInt2;
      this.doc = paramDocument;
      this.link = paramString;
      this.bylineEntryType = paramInt4;
      this.clickEventType = paramInt3;
    }
  }
  
  public static abstract interface BylinesClickListener
  {
    public abstract void onBylinesClick(BylinesModuleLayout.BylineEntryInfo paramBylineEntryInfo);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.BylinesModuleLayout
 * JD-Core Version:    0.7.0.1
 */