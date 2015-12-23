package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import com.android.vending.R.styleable;

public class LabelEditText
  extends EditText
{
  private int mLabelFormat;
  private String mTextValue;
  
  public LabelEditText(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public LabelEditText(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.getTheme().obtainStyledAttributes(paramAttributeSet, R.styleable.LabelEditText, 0, 0);
    try
    {
      this.mLabelFormat = localTypedArray.getResourceId(0, 0);
      localTypedArray.recycle();
      this.mTextValue = getText().toString();
      setOnFocusChangeListener(new View.OnFocusChangeListener()
      {
        public final void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
        {
          if (paramAnonymousBoolean)
          {
            LabelEditText.this.setText(LabelEditText.this.mTextValue);
            LabelEditText.this.setSelection(LabelEditText.this.mTextValue.length());
          }
          do
          {
            return;
            LabelEditText.access$002(LabelEditText.this, LabelEditText.this.getText().toString().trim());
          } while ((TextUtils.isEmpty(LabelEditText.this.mTextValue)) || (LabelEditText.this.mLabelFormat == 0));
          LabelEditText localLabelEditText = LabelEditText.this;
          Context localContext = LabelEditText.this.getContext();
          int i = LabelEditText.this.mLabelFormat;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = LabelEditText.this.mTextValue;
          localLabelEditText.setText(localContext.getString(i, arrayOfObject));
        }
      });
      return;
    }
    finally
    {
      localTypedArray.recycle();
    }
  }
  
  public String getTextValue()
  {
    if (hasFocus()) {
      this.mTextValue = getText().toString().trim();
    }
    return this.mTextValue;
  }
  
  public void setTextValue(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    this.mTextValue = paramString.toString().trim();
    if (this.mLabelFormat != 0)
    {
      Context localContext = getContext();
      int i = this.mLabelFormat;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mTextValue;
      setText(localContext.getString(i, arrayOfObject));
      return;
    }
    setText(this.mTextValue);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.LabelEditText
 * JD-Core Version:    0.7.0.1
 */