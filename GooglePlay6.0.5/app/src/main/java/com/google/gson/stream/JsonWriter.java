package com.google.gson.stream;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class JsonWriter
  implements Closeable
{
  private String deferredName;
  public boolean htmlSafe;
  public String indent;
  public boolean lenient;
  private final Writer out;
  public String separator;
  public boolean serializeNulls;
  private final List<JsonScope> stack = new ArrayList();
  
  public JsonWriter(Writer paramWriter)
  {
    this.stack.add(JsonScope.EMPTY_DOCUMENT);
    this.separator = ":";
    this.serializeNulls = true;
    if (paramWriter == null) {
      throw new NullPointerException("out == null");
    }
    this.out = paramWriter;
  }
  
  private void beforeValue(boolean paramBoolean)
    throws IOException
  {
    switch (1.$SwitchMap$com$google$gson$stream$JsonScope[peek().ordinal()])
    {
    default: 
      throw new IllegalStateException("Nesting problem: " + this.stack);
    case 1: 
      if ((!this.lenient) && (!paramBoolean)) {
        throw new IllegalStateException("JSON must start with an array or an object.");
      }
      replaceTop(JsonScope.NONEMPTY_DOCUMENT);
      return;
    case 2: 
      replaceTop(JsonScope.NONEMPTY_ARRAY);
      newline();
      return;
    case 3: 
      this.out.append(',');
      newline();
      return;
    case 4: 
      this.out.append(this.separator);
      replaceTop(JsonScope.NONEMPTY_OBJECT);
      return;
    }
    throw new IllegalStateException("JSON must have only one top-level value.");
  }
  
  private JsonWriter close(JsonScope paramJsonScope1, JsonScope paramJsonScope2, String paramString)
    throws IOException
  {
    JsonScope localJsonScope = peek();
    if ((localJsonScope != paramJsonScope2) && (localJsonScope != paramJsonScope1)) {
      throw new IllegalStateException("Nesting problem: " + this.stack);
    }
    if (this.deferredName != null) {
      throw new IllegalStateException("Dangling name: " + this.deferredName);
    }
    this.stack.remove(-1 + this.stack.size());
    if (localJsonScope == paramJsonScope2) {
      newline();
    }
    this.out.write(paramString);
    return this;
  }
  
  private void newline()
    throws IOException
  {
    if (this.indent == null) {}
    for (;;)
    {
      return;
      this.out.write("\n");
      for (int i = 1; i < this.stack.size(); i++) {
        this.out.write(this.indent);
      }
    }
  }
  
  private JsonWriter open(JsonScope paramJsonScope, String paramString)
    throws IOException
  {
    beforeValue(true);
    this.stack.add(paramJsonScope);
    this.out.write(paramString);
    return this;
  }
  
  private JsonScope peek()
  {
    return (JsonScope)this.stack.get(-1 + this.stack.size());
  }
  
  private void replaceTop(JsonScope paramJsonScope)
  {
    this.stack.set(-1 + this.stack.size(), paramJsonScope);
  }
  
  private void string(String paramString)
    throws IOException
  {
    this.out.write("\"");
    int i = 0;
    int j = paramString.length();
    if (i < j)
    {
      int k = paramString.charAt(i);
      switch (k)
      {
      default: 
        if (k <= 31)
        {
          Writer localWriter3 = this.out;
          Object[] arrayOfObject3 = new Object[1];
          arrayOfObject3[0] = Integer.valueOf(k);
          localWriter3.write(String.format("\\u%04x", arrayOfObject3));
        }
        break;
      }
      for (;;)
      {
        i++;
        break;
        this.out.write(92);
        this.out.write(k);
        continue;
        this.out.write("\\t");
        continue;
        this.out.write("\\b");
        continue;
        this.out.write("\\n");
        continue;
        this.out.write("\\r");
        continue;
        this.out.write("\\f");
        continue;
        if (this.htmlSafe)
        {
          Writer localWriter2 = this.out;
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = Integer.valueOf(k);
          localWriter2.write(String.format("\\u%04x", arrayOfObject2));
        }
        else
        {
          this.out.write(k);
          continue;
          Writer localWriter1 = this.out;
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = Integer.valueOf(k);
          localWriter1.write(String.format("\\u%04x", arrayOfObject1));
        }
      }
    }
    this.out.write("\"");
  }
  
  private void writeDeferredName()
    throws IOException
  {
    JsonScope localJsonScope;
    if (this.deferredName != null)
    {
      localJsonScope = peek();
      if (localJsonScope != JsonScope.NONEMPTY_OBJECT) {
        break label53;
      }
      this.out.write(44);
    }
    label53:
    while (localJsonScope == JsonScope.EMPTY_OBJECT)
    {
      newline();
      replaceTop(JsonScope.DANGLING_NAME);
      string(this.deferredName);
      this.deferredName = null;
      return;
    }
    throw new IllegalStateException("Nesting problem: " + this.stack);
  }
  
  public JsonWriter beginArray()
    throws IOException
  {
    writeDeferredName();
    return open(JsonScope.EMPTY_ARRAY, "[");
  }
  
  public JsonWriter beginObject()
    throws IOException
  {
    writeDeferredName();
    return open(JsonScope.EMPTY_OBJECT, "{");
  }
  
  public void close()
    throws IOException
  {
    this.out.close();
    if (peek() != JsonScope.NONEMPTY_DOCUMENT) {
      throw new IOException("Incomplete document");
    }
  }
  
  public JsonWriter endArray()
    throws IOException
  {
    return close(JsonScope.EMPTY_ARRAY, JsonScope.NONEMPTY_ARRAY, "]");
  }
  
  public JsonWriter endObject()
    throws IOException
  {
    return close(JsonScope.EMPTY_OBJECT, JsonScope.NONEMPTY_OBJECT, "}");
  }
  
  public JsonWriter name(String paramString)
    throws IOException
  {
    if (paramString == null) {
      throw new NullPointerException("name == null");
    }
    if (this.deferredName != null) {
      throw new IllegalStateException();
    }
    this.deferredName = paramString;
    return this;
  }
  
  public JsonWriter nullValue()
    throws IOException
  {
    if (this.deferredName != null)
    {
      if (this.serializeNulls) {
        writeDeferredName();
      }
    }
    else
    {
      beforeValue(false);
      this.out.write("null");
      return this;
    }
    this.deferredName = null;
    return this;
  }
  
  public JsonWriter value(long paramLong)
    throws IOException
  {
    writeDeferredName();
    beforeValue(false);
    this.out.write(Long.toString(paramLong));
    return this;
  }
  
  public JsonWriter value(Number paramNumber)
    throws IOException
  {
    if (paramNumber == null) {
      return nullValue();
    }
    writeDeferredName();
    String str = paramNumber.toString();
    if ((!this.lenient) && ((str.equals("-Infinity")) || (str.equals("Infinity")) || (str.equals("NaN")))) {
      throw new IllegalArgumentException("Numeric values must be finite, but was " + paramNumber);
    }
    beforeValue(false);
    this.out.append(str);
    return this;
  }
  
  public JsonWriter value(String paramString)
    throws IOException
  {
    if (paramString == null) {
      return nullValue();
    }
    writeDeferredName();
    beforeValue(false);
    string(paramString);
    return this;
  }
  
  public JsonWriter value(boolean paramBoolean)
    throws IOException
  {
    writeDeferredName();
    beforeValue(false);
    Writer localWriter = this.out;
    if (paramBoolean) {}
    for (String str = "true";; str = "false")
    {
      localWriter.write(str);
      return this;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.stream.JsonWriter
 * JD-Core Version:    0.7.0.1
 */