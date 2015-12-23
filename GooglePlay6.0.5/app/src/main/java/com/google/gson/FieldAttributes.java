package com.google.gson;

import com.google.gson.internal..Gson.Preconditions;
import java.lang.reflect.Field;

public final class FieldAttributes
{
  private final Field field;
  
  public FieldAttributes(Field paramField)
  {
    .Gson.Preconditions.checkNotNull(paramField);
    this.field = paramField;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.FieldAttributes
 * JD-Core Version:    0.7.0.1
 */