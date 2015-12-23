package com.google.android.play.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Build.VERSION;
import android.support.v4.widget.TextViewCompat;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.TextView;
import com.google.android.play.R.bool;
import com.google.android.play.R.color;
import com.google.android.play.R.dimen;
import com.google.android.play.R.styleable;
import com.google.android.play.utils.PlayUtils;
import java.util.Locale;

public class PlayTextView
  extends TextView
{
  private static final boolean RESPECT_ORIGINAL_LINE_SPACING;
  private boolean mAlwaysDrawHint;
  public Paint mBorderPaint;
  private float mBorderThickness;
  private final float mCompactFactor;
  private int mDecorationPosition;
  public boolean mDrawBorder;
  private GradientDrawable mLastLineFadeOutDrawable;
  private int mLastLineFadeOutHintMargin;
  private int mLastLineFadeOutSize;
  private final String mLastLineOverdrawHint;
  private Paint mLastLineOverdrawHintPaint;
  private Paint mLastLineOverdrawPaint;
  private boolean mToDrawOverLastLineIfNecessary;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 16) {}
    for (boolean bool = true;; bool = false)
    {
      RESPECT_ORIGINAL_LINE_SPACING = bool;
      return;
    }
  }
  
  public PlayTextView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  @TargetApi(16)
  public PlayTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PlayTextView);
    this.mToDrawOverLastLineIfNecessary = localTypedArray.hasValue(R.styleable.PlayTextView_lastLineOverdrawColor);
    this.mAlwaysDrawHint = localTypedArray.getBoolean(R.styleable.PlayTextView_lastLineOverdrawHintAlways, false);
    if (this.mToDrawOverLastLineIfNecessary)
    {
      int m = localTypedArray.getColor(R.styleable.PlayTextView_lastLineOverdrawColor, localResources.getColor(R.color.play_white));
      this.mLastLineOverdrawPaint = new Paint();
      this.mLastLineOverdrawPaint.setColor(m);
      this.mLastLineOverdrawPaint.setStyle(Paint.Style.FILL_AND_STROKE);
      this.mLastLineFadeOutSize = localResources.getDimensionPixelSize(R.dimen.play_text_view_fadeout);
      GradientDrawable.Orientation localOrientation = GradientDrawable.Orientation.LEFT_RIGHT;
      int[] arrayOfInt = new int[2];
      arrayOfInt[0] = (0xFFFFFF & m);
      arrayOfInt[1] = m;
      this.mLastLineFadeOutDrawable = new GradientDrawable(localOrientation, arrayOfInt);
      this.mLastLineFadeOutHintMargin = localResources.getDimensionPixelSize(R.dimen.play_text_view_fadeout_hint_margin);
    }
    String str = localTypedArray.getString(R.styleable.PlayTextView_lastLineOverdrawHint);
    if (localTypedArray.getBoolean(R.styleable.PlayTextView_lastLineOverdrawAllCaps, false)) {
      str = str.toUpperCase(Locale.getDefault());
    }
    this.mLastLineOverdrawHint = str;
    if (!TextUtils.isEmpty(this.mLastLineOverdrawHint))
    {
      this.mLastLineOverdrawHintPaint = new Paint();
      this.mLastLineOverdrawHintPaint.setColor(localTypedArray.getColor(R.styleable.PlayTextView_lastLineOverdrawHintColor, getCurrentTextColor()));
      this.mLastLineOverdrawHintPaint.setTextSize(getTextSize());
      this.mLastLineOverdrawHintPaint.setTypeface(getTypeface());
      this.mLastLineOverdrawHintPaint.setAntiAlias(true);
    }
    int i = localTypedArray.getInt(R.styleable.PlayTextView_decoration_position, 3);
    boolean bool1 = PlayUtils.useLtr(paramContext);
    float f1;
    Paint.FontMetrics localFontMetrics;
    switch (i)
    {
    default: 
      this.mDecorationPosition = i;
      f1 = getTextSize();
      boolean bool2 = localResources.getBoolean(R.bool.play_text_compact_mode_enable);
      boolean bool3 = localTypedArray.getBoolean(R.styleable.PlayTextView_allowsCompactLineSpacing, false);
      if ((bool2) && (bool3)) {
        localFontMetrics = getPaint().getFontMetrics();
      }
      break;
    }
    for (this.mCompactFactor = Math.max(0.0F, 1.0F - 1.172F * f1 / (Math.abs(localFontMetrics.ascent) + Math.abs(localFontMetrics.descent)));; this.mCompactFactor = 0.0F)
    {
      if (this.mCompactFactor > 0.0F)
      {
        float f2 = f1 * -this.mCompactFactor;
        if (RESPECT_ORIGINAL_LINE_SPACING) {
          f2 += getLineHeight() * (getLineSpacingMultiplier() - 1.0F);
        }
        setLineSpacing(f2, 1.0F);
      }
      localTypedArray.recycle();
      this.mBorderThickness = paramContext.getResources().getDimension(R.dimen.play_text_view_outline);
      this.mBorderPaint = new Paint();
      this.mBorderPaint.setStrokeWidth(this.mBorderThickness);
      this.mBorderPaint.setStyle(Paint.Style.STROKE);
      setWillNotDraw(false);
      return;
      if (bool1) {}
      for (int k = 2;; k = 3)
      {
        this.mDecorationPosition = k;
        break;
      }
      if (bool1) {}
      for (int j = 3;; j = 2)
      {
        this.mDecorationPosition = j;
        break;
      }
    }
  }
  
  private void drawHint(Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    Layout localLayout1 = getLayout();
    int i = getPaddingTop();
    int j = -1;
    int k = paramInt2;
    if (k >= 0)
    {
      Layout localLayout2 = getLayout();
      CharSequence localCharSequence = localLayout2.getText();
      int i11 = localLayout2.getLineStart(k);
      j = localLayout2.getLineEnd(k);
      label54:
      if (j > i11) {
        if (Character.isWhitespace(localCharSequence.charAt(j - 1))) {}
      }
      for (;;)
      {
        if (j != -1) {
          break label102;
        }
        k--;
        break;
        j--;
        break label54;
        j = -1;
      }
    }
    label102:
    if (j == -1) {
      j = 0;
    }
    int m = localLayout1.getLineTop(k);
    int n = localLayout1.getLineBottom(k);
    int i1;
    int i2;
    int i4;
    int i5;
    int i6;
    int i7;
    label212:
    int i9;
    if (localLayout1.getParagraphDirection(k) == -1)
    {
      i1 = 1;
      i2 = (int)localLayout1.getPrimaryHorizontal(j);
      int i3 = getPaddingLeft();
      i4 = getPaddingRight();
      i5 = i3 + i2;
      boolean bool = TextUtils.isEmpty(this.mLastLineOverdrawHint);
      i6 = 0;
      if (!bool)
      {
        i7 = (int)this.mLastLineOverdrawHintPaint.measureText(this.mLastLineOverdrawHint);
        if (i1 == 0) {
          break label395;
        }
        i8 = i3;
        i9 = i8 + i7;
        if ((i1 == 0) || (i9 + this.mLastLineFadeOutHintMargin <= i2)) {
          break label407;
        }
        i6 = 1;
        i5 = i9 + this.mLastLineFadeOutHintMargin;
        paramCanvas.drawRect(i8, i + m, i5, i + n, this.mLastLineOverdrawPaint);
        label274:
        if ((i1 == 0) || (i9 + this.mLastLineFadeOutHintMargin >= i2)) {
          break label475;
        }
      }
    }
    for (int i8 = i2 - this.mLastLineFadeOutHintMargin - i7;; i8 = i2 + this.mLastLineFadeOutHintMargin) {
      label395:
      label407:
      label475:
      do
      {
        paramCanvas.drawText(this.mLastLineOverdrawHint, i8, localLayout1.getLineBaseline(paramInt2), this.mLastLineOverdrawHintPaint);
        if (i6 != 0)
        {
          paramCanvas.save(1);
          paramCanvas.translate(i5, i);
          if (i1 != 0) {
            paramCanvas.scale(-1.0F, 1.0F);
          }
          this.mLastLineFadeOutDrawable.setBounds(-this.mLastLineFadeOutSize, m, 0, n);
          this.mLastLineFadeOutDrawable.draw(paramCanvas);
          paramCanvas.restore();
        }
        return;
        i1 = 0;
        break;
        i8 = paramInt1 - i4 - i7;
        break label212;
        i6 = 0;
        if (i1 != 0) {
          break label274;
        }
        int i10 = i8 - this.mLastLineFadeOutHintMargin;
        i6 = 0;
        if (i10 >= i2) {
          break label274;
        }
        i6 = 1;
        i5 = i8 - this.mLastLineFadeOutHintMargin;
        paramCanvas.drawRect(i5, i + m, i9, i + n, this.mLastLineOverdrawPaint);
        break label274;
      } while ((i1 != 0) || (i8 - this.mLastLineFadeOutHintMargin <= i2));
    }
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.mDrawBorder)
    {
      int i4 = (int)Math.ceil(this.mBorderThickness / 2.0F);
      paramCanvas.save();
      paramCanvas.translate(getScrollX(), 0.0F);
      paramCanvas.drawRect(i4, i4, getWidth() - i4, getHeight() - i4, this.mBorderPaint);
      paramCanvas.restore();
    }
    if (!this.mToDrawOverLastLineIfNecessary) {}
    label228:
    for (;;)
    {
      return;
      int i = getHeight() - getPaddingBottom();
      int j = getWidth();
      Layout localLayout = getLayout();
      if (localLayout != null)
      {
        int k = getPaddingTop();
        int m = localLayout.getLineCount();
        for (int n = 0;; n++)
        {
          if (n >= m) {
            break label228;
          }
          int i1 = localLayout.getLineTop(n);
          int i2 = localLayout.getLineBottom(n);
          if ((i1 <= i) && (i2 > i))
          {
            paramCanvas.drawRect(0.0F, k + i1, j, i, this.mLastLineOverdrawPaint);
            if (n <= 0) {
              break;
            }
            drawHint(paramCanvas, j, n - 1);
            return;
          }
          int i3 = m - 1;
          if ((n == i3) && (this.mAlwaysDrawHint)) {
            drawHint(paramCanvas, j, n);
          }
        }
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if (this.mCompactFactor == 0.0F) {}
    while (View.MeasureSpec.getMode(paramInt2) == 1073741824) {
      return;
    }
    setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + (int)(this.mCompactFactor * getLineHeight()));
  }
  
  public final void setContentColorId(int paramInt, boolean paramBoolean)
  {
    int i = getResources().getColor(paramInt);
    setTextColor(i);
    this.mDrawBorder = paramBoolean;
    if (this.mDrawBorder) {
      this.mBorderPaint.setColor(i);
    }
    invalidate();
  }
  
  public void setDecorationBitmap(Bitmap paramBitmap)
  {
    BitmapDrawable localBitmapDrawable = new BitmapDrawable(getResources(), paramBitmap);
    switch (this.mDecorationPosition)
    {
    default: 
      return;
    case 2: 
      TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds$16207aff(this, localBitmapDrawable, null, null);
      return;
    }
    TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds$16207aff(this, null, null, localBitmapDrawable);
  }
  
  public void setLastLineOverdrawColor(int paramInt)
  {
    if (!this.mToDrawOverLastLineIfNecessary)
    {
      this.mLastLineOverdrawPaint = new Paint();
      this.mLastLineOverdrawPaint.setStyle(Paint.Style.FILL_AND_STROKE);
      Resources localResources = getResources();
      this.mLastLineFadeOutSize = localResources.getDimensionPixelSize(R.dimen.play_text_view_fadeout);
      this.mLastLineFadeOutHintMargin = localResources.getDimensionPixelSize(R.dimen.play_text_view_fadeout_hint_margin);
    }
    this.mLastLineOverdrawPaint.setColor(paramInt);
    GradientDrawable.Orientation localOrientation = GradientDrawable.Orientation.LEFT_RIGHT;
    int[] arrayOfInt = new int[2];
    arrayOfInt[0] = (0xFFFFFF & paramInt);
    arrayOfInt[1] = paramInt;
    this.mLastLineFadeOutDrawable = new GradientDrawable(localOrientation, arrayOfInt);
    this.mToDrawOverLastLineIfNecessary = true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.layout.PlayTextView
 * JD-Core Version:    0.7.0.1
 */