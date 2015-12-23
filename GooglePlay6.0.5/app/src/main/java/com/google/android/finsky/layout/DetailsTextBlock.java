package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.vending.R.styleable;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.play.layout.PlayTextView;

public class DetailsTextBlock
  extends RelativeLayout
{
  private TextView mBodyHeaderView;
  private PlayTextView mBodyView;
  private final int mCorpusXMargin;
  private ImageView mIconView;
  private final int mRegularXMargin;
  
  public DetailsTextBlock(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DetailsTextBlock(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.DetailsTextBlock);
    this.mRegularXMargin = localTypedArray.getDimensionPixelSize(0, 0);
    this.mCorpusXMargin = localTypedArray.getDimensionPixelSize(1, 0);
    localTypedArray.recycle();
  }
  
  public final void bind(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt)
  {
    if (!TextUtils.isEmpty(paramCharSequence1))
    {
      this.mBodyHeaderView.setText(paramCharSequence1);
      this.mBodyHeaderView.setVisibility(0);
    }
    for (;;)
    {
      setBody(paramCharSequence2);
      setBodyMaxLines(paramInt);
      setVisibility(0);
      return;
      this.mBodyHeaderView.setVisibility(8);
    }
  }
  
  public int getBodyLineCount()
  {
    return this.mBodyView.getLineCount();
  }
  
  public final boolean hasBody()
  {
    return (this.mBodyView.getVisibility() == 0) && (!TextUtils.isEmpty(this.mBodyView.getText()));
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mIconView = ((ImageView)findViewById(2131755150));
    this.mBodyHeaderView = ((TextView)findViewById(2131755413));
    this.mBodyView = ((PlayTextView)findViewById(2131755414));
    this.mBodyView.setMovementMethod(LinkMovementMethod.getInstance());
  }
  
  public final void removeCorpusStyle()
  {
    setBackgroundResource(0);
    this.mBodyView.setLastLineOverdrawColor(-1);
    Resources localResources = getResources();
    int i = localResources.getColor(2131689624);
    this.mBodyView.setTextColor(i);
    this.mBodyView.setLinkTextColor(i);
    int j = localResources.getColor(2131689625);
    this.mBodyHeaderView.setTextColor(j);
    this.mIconView.setVisibility(8);
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)getLayoutParams();
    localMarginLayoutParams.topMargin = 0;
    localMarginLayoutParams.bottomMargin = 0;
    localMarginLayoutParams.leftMargin = this.mRegularXMargin;
    localMarginLayoutParams.rightMargin = this.mRegularXMargin;
    setLayoutParams(localMarginLayoutParams);
  }
  
  public void setBody(CharSequence paramCharSequence)
  {
    if (TextUtils.isEmpty(paramCharSequence))
    {
      this.mBodyView.setVisibility(8);
      return;
    }
    this.mBodyView.setText(paramCharSequence);
    this.mBodyView.setVisibility(0);
  }
  
  public void setBodyClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mBodyView.setOnClickListener(paramOnClickListener);
  }
  
  public void setBodyMaxLines(int paramInt)
  {
    this.mBodyView.setMaxLines(paramInt);
  }
  
  public void setBodyTextIsSelectable(boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 11)
    {
      this.mBodyView.setTextIsSelectable(paramBoolean);
      this.mBodyView.setAutoLinkMask(15);
      this.mBodyView.setMovementMethod(ArrowKeyMovementMethod.getInstance());
    }
  }
  
  public final void setCorpusStyle(int paramInt1, int paramInt2, int paramInt3)
  {
    Context localContext = getContext();
    Resources localResources = localContext.getResources();
    int i;
    switch (paramInt1)
    {
    case 5: 
    default: 
      throw new IllegalArgumentException("Unsupported backend ID (" + paramInt1 + ")");
    case 3: 
      if (CorpusResourceUtils.isEnterpriseTheme()) {
        i = 2131689748;
      }
      break;
    }
    for (;;)
    {
      int j = localContext.getResources().getColor(i);
      setBackgroundColor(j);
      this.mBodyView.setLastLineOverdrawColor(j);
      int k = localResources.getDimensionPixelSize(2131492993);
      ViewCompat.setPaddingRelative(this, k, k, k, k);
      ColorStateList localColorStateList = CorpusResourceUtils.getSecondaryTextColor(localContext, paramInt1);
      this.mBodyHeaderView.setTextColor(localColorStateList);
      this.mBodyView.setTextColor(localColorStateList);
      this.mBodyView.setLinkTextColor(localColorStateList);
      this.mIconView.setVisibility(0);
      Drawable localDrawable = DrawableCompat.wrap(ResourcesCompat.getDrawable(localResources, 2130837901, localContext.getTheme()).mutate());
      DrawableCompat.setTint(localDrawable, localColorStateList.getDefaultColor());
      this.mIconView.setImageDrawable(localDrawable);
      ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)getLayoutParams();
      localMarginLayoutParams.topMargin = paramInt2;
      localMarginLayoutParams.bottomMargin = paramInt3;
      localMarginLayoutParams.leftMargin = this.mCorpusXMargin;
      localMarginLayoutParams.rightMargin = this.mCorpusXMargin;
      setLayoutParams(localMarginLayoutParams);
      return;
      i = 2131689747;
      continue;
      i = 2131689749;
      continue;
      i = 2131689752;
      continue;
      i = 2131689750;
      continue;
      i = 2131689751;
    }
  }
  
  public final void setEditorialStyle(int paramInt1, int paramInt2)
  {
    this.mBodyView.setLastLineOverdrawColor(paramInt1);
    this.mBodyView.setTextColor(paramInt2);
    this.mBodyView.setLinkTextColor(paramInt2);
    this.mIconView.setVisibility(8);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DetailsTextBlock
 * JD-Core Version:    0.7.0.1
 */