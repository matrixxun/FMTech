package com.google.android.finsky.layout;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

public class MultiLineDoneButtonEditText
  extends AppCompatEditText
{
  public MultiLineDoneButtonEditText(Context paramContext)
  {
    super(paramContext);
  }
  
  public MultiLineDoneButtonEditText(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public InputConnection onCreateInputConnection(EditorInfo paramEditorInfo)
  {
    InputConnection localInputConnection = super.onCreateInputConnection(paramEditorInfo);
    paramEditorInfo.imeOptions = (0x6 | 0xBFFFFFFF & paramEditorInfo.imeOptions);
    return localInputConnection;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.MultiLineDoneButtonEditText
 * JD-Core Version:    0.7.0.1
 */