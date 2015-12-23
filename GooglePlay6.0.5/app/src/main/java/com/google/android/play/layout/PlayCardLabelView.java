package com.google.android.play.layout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint.FontMetrics;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewDebug.ExportedProperty;
import android.view.accessibility.AccessibilityEvent;
import com.google.android.play.R.color;
import com.google.android.play.R.dimen;
import com.google.android.play.R.styleable;
import com.google.android.play.utils.PlayCorpusUtils;
import com.google.android.play.utils.PlayUtils;
import java.util.List;

public class PlayCardLabelView
  extends View
{
  private boolean mCanShowStrikeText;
  private final int mDefaultStrikeTextColor;
  public Drawable mIcon;
  private final int mIconGap;
  private String mStrikeText;
  private final TextPaint mStrikeTextPaint;
  private int mStrikeTextWidth;
  private String mText;
  private final int mTextBaseline;
  private final int mTextHeight;
  private final TextPaint mTextPaint;
  private final float mTextSize;
  private int mTextWidth;
  private final int mTextsGap;
  
  public PlayCardLabelView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardLabelView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mIconGap = localResources.getDimensionPixelSize(R.dimen.play_card_label_icon_gap);
    this.mTextsGap = localResources.getDimensionPixelSize(R.dimen.play_card_label_texts_gap);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PlayCardLabelView);
    this.mTextSize = localTypedArray.getDimension(R.styleable.PlayCardLabelView_play_label_text_size, localResources.getDimension(R.dimen.play_medium_size));
    String str = localTypedArray.getString(R.styleable.PlayCardLabelView_play_label_font_family);
    localTypedArray.recycle();
    this.mTextPaint = new TextPaint(1);
    this.mTextPaint.density = localResources.getDisplayMetrics().density;
    this.mTextPaint.setTextSize(this.mTextSize);
    this.mTextPaint.setFakeBoldText(false);
    this.mStrikeTextPaint = new TextPaint(1);
    this.mStrikeTextPaint.density = localResources.getDisplayMetrics().density;
    this.mStrikeTextPaint.setTextSize(this.mTextSize);
    this.mDefaultStrikeTextColor = localResources.getColor(R.color.play_fg_secondary);
    this.mStrikeTextPaint.setColor(this.mDefaultStrikeTextColor);
    this.mStrikeTextPaint.setStrikeThruText(true);
    this.mStrikeTextPaint.setFakeBoldText(false);
    if (str != null)
    {
      Typeface localTypeface = Typeface.create(str, 0);
      this.mTextPaint.setTypeface(localTypeface);
      this.mStrikeTextPaint.setTypeface(localTypeface);
    }
    Paint.FontMetrics localFontMetrics = this.mTextPaint.getFontMetrics();
    this.mTextHeight = ((int)(Math.abs(localFontMetrics.top) + Math.abs(localFontMetrics.bottom)));
    this.mTextBaseline = ((int)Math.abs(localFontMetrics.top));
    setWillNotDraw(false);
  }
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    boolean bool = super.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent);
    if (paramAccessibilityEvent.getEventType() == 8)
    {
      paramAccessibilityEvent.getText().add(getContentDescription());
      bool = true;
    }
    return bool;
  }
  
  @ViewDebug.ExportedProperty(category="layout")
  public int getBaseline()
  {
    return getPaddingTop() + this.mTextBaseline;
  }
  
  public String getStrikeText()
  {
    return this.mStrikeText;
  }
  
  public String getText()
  {
    return this.mText;
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.save();
    int i = ViewCompat.getPaddingStart(this);
    int j = i;
    int k = getPaddingTop();
    boolean bool;
    int m;
    if (ViewCompat.getLayoutDirection(this) == 0)
    {
      bool = true;
      m = getWidth();
      if (TextUtils.isEmpty(this.mText)) {
        break label261;
      }
    }
    label261:
    for (int n = 1;; n = 0)
    {
      if (this.mIcon != null)
      {
        int i3 = k + (getHeight() - getPaddingTop() - getPaddingBottom() - this.mIcon.getIntrinsicHeight()) / 2;
        int i4 = PlayUtils.getViewLeftFromParentStart(m, this.mIcon.getIntrinsicWidth(), bool, i);
        paramCanvas.translate(i4, i3);
        this.mIcon.draw(paramCanvas);
        paramCanvas.translate(-i4, -i3);
        j += this.mIcon.getIntrinsicWidth() + this.mIconGap;
      }
      if (this.mCanShowStrikeText)
      {
        int i2 = PlayUtils.getViewLeftFromParentStart(m, this.mStrikeTextWidth, bool, j);
        paramCanvas.drawText(this.mStrikeText, i2, k + this.mTextBaseline, this.mStrikeTextPaint);
        j += this.mStrikeTextWidth + this.mTextsGap;
      }
      if (n != 0)
      {
        int i1 = PlayUtils.getViewLeftFromParentStart(m, this.mTextWidth, bool, j);
        paramCanvas.drawText(this.mText, i1, k + this.mTextBaseline, this.mTextPaint);
      }
      paramCanvas.restore();
      return;
      bool = false;
      break;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i;
    int k;
    label48:
    int m;
    int i3;
    if ((View.MeasureSpec.getMode(paramInt1) == 1073741824) && (View.MeasureSpec.getSize(paramInt1) == 0))
    {
      i = 1;
      int j = View.MeasureSpec.getSize(paramInt1);
      this.mStrikeTextWidth = 0;
      this.mCanShowStrikeText = false;
      if (TextUtils.isEmpty(this.mText)) {
        break label250;
      }
      k = 1;
      m = 0;
      if (i == 0)
      {
        Drawable localDrawable = this.mIcon;
        int i1 = 0;
        if (localDrawable != null)
        {
          i1 = this.mIcon.getIntrinsicWidth();
          if (k != 0) {
            i1 += this.mIconGap;
          }
        }
        if (k != 0)
        {
          this.mTextWidth = ((int)this.mTextPaint.measureText(this.mText));
          i1 += this.mTextWidth;
        }
        if (!TextUtils.isEmpty(this.mStrikeText))
        {
          this.mStrikeTextWidth = ((int)this.mStrikeTextPaint.measureText(this.mStrikeText));
          int i2 = this.mStrikeTextWidth;
          if (k == 0) {
            break label256;
          }
          i3 = this.mTextsGap;
          label165:
          int i4 = i2 + i3;
          if ((j <= 0) || (i1 + i4 > j)) {
            break label262;
          }
          i1 += i4;
          this.mCanShowStrikeText = true;
        }
        label199:
        m = i1 + (ViewCompat.getPaddingStart(this) + ViewCompat.getPaddingEnd(this));
      }
      if (this.mIcon != null) {
        break label270;
      }
    }
    label256:
    label262:
    label270:
    for (int n = this.mTextHeight;; n = Math.max(this.mTextHeight, this.mIcon.getIntrinsicHeight()))
    {
      setMeasuredDimension(m, n + (getPaddingTop() + getPaddingBottom()));
      return;
      i = 0;
      break;
      label250:
      k = 0;
      break label48;
      i3 = 0;
      break label165;
      this.mCanShowStrikeText = false;
      break label199;
    }
  }
  
  public void setIcon(int paramInt)
  {
    this.mIcon = ResourcesCompat.getDrawable(getResources(), paramInt, getContext().getTheme());
    this.mIcon.setBounds(0, 0, this.mIcon.getIntrinsicWidth(), this.mIcon.getIntrinsicHeight());
    invalidate();
    requestLayout();
  }
  
  public final void setText(int paramInt1, int paramInt2)
  {
    setText(getResources().getString(paramInt1), paramInt2);
  }
  
  public final void setText(String paramString, int paramInt)
  {
    setText(paramString, null, paramInt, paramString);
  }
  
  public final void setText(String paramString1, String paramString2, int paramInt, String paramString3)
  {
    int i = PlayCorpusUtils.getPrimaryTextColor(getContext(), paramInt).getDefaultColor();
    int j = this.mDefaultStrikeTextColor;
    if (paramString1 != null) {}
    for (String str1 = paramString1.toUpperCase();; str1 = null)
    {
      this.mText = str1;
      String str2 = null;
      if (paramString2 != null) {
        str2 = paramString2.toUpperCase();
      }
      this.mStrikeText = str2;
      this.mTextPaint.setColor(i);
      this.mStrikeTextPaint.setColor(j);
      setContentDescription(paramString3);
      invalidate();
      requestLayout();
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.layout.PlayCardLabelView
 * JD-Core Version:    0.7.0.1
 */