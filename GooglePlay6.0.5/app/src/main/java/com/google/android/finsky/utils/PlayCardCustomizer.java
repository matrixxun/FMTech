package com.google.android.finsky.utils;

import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.DocV2;
import com.google.android.play.layout.PlayCardViewBase;

public class PlayCardCustomizer<T extends PlayCardViewBase>
{
  public void preBind(T paramT, Document paramDocument)
  {
    paramT.setData(paramDocument, paramDocument.mDocument.backendId);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.PlayCardCustomizer
 * JD-Core Version:    0.7.0.1
 */