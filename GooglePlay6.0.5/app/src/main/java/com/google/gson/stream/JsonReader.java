package com.google.gson.stream;

import com.google.gson.JsonPrimitive;
import com.google.gson.internal.JsonReaderInternalAccess;
import com.google.gson.internal.bind.JsonTreeReader;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class JsonReader
  implements Closeable
{
  private static final char[] NON_EXECUTE_PREFIX = ")]}'\n".toCharArray();
  private final char[] buffer = new char[1024];
  private int bufferStartColumn = 1;
  private int bufferStartLine = 1;
  private final Reader in;
  public boolean lenient = false;
  private int limit = 0;
  private String name;
  private int pos = 0;
  private boolean skipping;
  private JsonScope[] stack = new JsonScope[32];
  private int stackSize = 0;
  private final StringPool stringPool = new StringPool();
  private JsonToken token;
  private String value;
  private int valueLength;
  private int valuePos;
  
  static
  {
    JsonReaderInternalAccess.INSTANCE = new JsonReaderInternalAccess()
    {
      public final void promoteNameToValue(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if ((paramAnonymousJsonReader instanceof JsonTreeReader))
        {
          JsonTreeReader localJsonTreeReader = (JsonTreeReader)paramAnonymousJsonReader;
          localJsonTreeReader.expect(JsonToken.NAME);
          Map.Entry localEntry = (Map.Entry)((Iterator)localJsonTreeReader.peekStack()).next();
          localJsonTreeReader.stack.add(localEntry.getValue());
          localJsonTreeReader.stack.add(new JsonPrimitive((String)localEntry.getKey()));
          return;
        }
        paramAnonymousJsonReader.peek();
        if (paramAnonymousJsonReader.token != JsonToken.NAME) {
          throw new IllegalStateException("Expected a name but was " + paramAnonymousJsonReader.peek() + "  at line " + paramAnonymousJsonReader.getLineNumber() + " column " + paramAnonymousJsonReader.getColumnNumber());
        }
        JsonReader.access$302(paramAnonymousJsonReader, paramAnonymousJsonReader.name);
        JsonReader.access$402$66c5ad48(paramAnonymousJsonReader);
        JsonReader.access$002(paramAnonymousJsonReader, JsonToken.STRING);
      }
    };
  }
  
  public JsonReader(Reader paramReader)
  {
    push(JsonScope.EMPTY_DOCUMENT);
    this.skipping = false;
    this.in = paramReader;
  }
  
  private JsonToken advance()
    throws IOException
  {
    peek();
    JsonToken localJsonToken = this.token;
    this.token = null;
    this.value = null;
    this.name = null;
    return localJsonToken;
  }
  
  private void checkLenient()
    throws IOException
  {
    if (!this.lenient) {
      throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
    }
  }
  
  private void expect(JsonToken paramJsonToken)
    throws IOException
  {
    peek();
    if (this.token != paramJsonToken) {
      throw new IllegalStateException("Expected " + paramJsonToken + " but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber());
    }
    advance();
  }
  
  private boolean fillBuffer(int paramInt)
    throws IOException
  {
    char[] arrayOfChar = this.buffer;
    int i = this.bufferStartLine;
    int j = this.bufferStartColumn;
    int k = 0;
    int m = this.pos;
    if (k < m)
    {
      if (arrayOfChar[k] == '\n') {
        i++;
      }
      for (j = 1;; j++)
      {
        k++;
        break;
      }
    }
    this.bufferStartLine = i;
    this.bufferStartColumn = j;
    if (this.limit != this.pos)
    {
      this.limit -= this.pos;
      System.arraycopy(arrayOfChar, this.pos, arrayOfChar, 0, this.limit);
    }
    for (;;)
    {
      this.pos = 0;
      do
      {
        int n = this.in.read(arrayOfChar, this.limit, arrayOfChar.length - this.limit);
        if (n == -1) {
          break;
        }
        this.limit = (n + this.limit);
        if ((this.bufferStartLine == 1) && (this.bufferStartColumn == 1) && (this.limit > 0) && (arrayOfChar[0] == 65279))
        {
          this.pos = (1 + this.pos);
          this.bufferStartColumn = (-1 + this.bufferStartColumn);
        }
      } while (this.limit < paramInt);
      return true;
      this.limit = 0;
    }
    return false;
  }
  
  private int getColumnNumber()
  {
    int i = this.bufferStartColumn;
    int j = 0;
    if (j < this.pos)
    {
      if (this.buffer[j] == '\n') {}
      for (i = 1;; i++)
      {
        j++;
        break;
      }
    }
    return i;
  }
  
  private int getLineNumber()
  {
    int i = this.bufferStartLine;
    for (int j = 0; j < this.pos; j++) {
      if (this.buffer[j] == '\n') {
        i++;
      }
    }
    return i;
  }
  
  private JsonToken nextInArray(boolean paramBoolean)
    throws IOException
  {
    if (paramBoolean) {
      this.stack[(-1 + this.stackSize)] = JsonScope.NONEMPTY_ARRAY;
    }
    for (;;)
    {
      switch (nextNonWhitespace(true))
      {
      default: 
        this.pos = (-1 + this.pos);
        return nextValue();
        switch (nextNonWhitespace(true))
        {
        case 44: 
        default: 
          throw syntaxError("Unterminated array");
        case 93: 
          this.stackSize = (-1 + this.stackSize);
          JsonToken localJsonToken3 = JsonToken.END_ARRAY;
          this.token = localJsonToken3;
          return localJsonToken3;
        }
        checkLenient();
      }
    }
    if (paramBoolean)
    {
      this.stackSize = (-1 + this.stackSize);
      JsonToken localJsonToken2 = JsonToken.END_ARRAY;
      this.token = localJsonToken2;
      return localJsonToken2;
    }
    checkLenient();
    this.pos = (-1 + this.pos);
    this.value = "null";
    JsonToken localJsonToken1 = JsonToken.NULL;
    this.token = localJsonToken1;
    return localJsonToken1;
  }
  
  private JsonToken nextInObject(boolean paramBoolean)
    throws IOException
  {
    int i;
    if (paramBoolean)
    {
      switch (nextNonWhitespace(true))
      {
      default: 
        this.pos = (-1 + this.pos);
        i = nextNonWhitespace(true);
        switch (i)
        {
        default: 
          checkLenient();
          this.pos = (-1 + this.pos);
          this.name = nextLiteral(false);
          if (this.name.length() != 0) {
            break label218;
          }
          throw syntaxError("Expected name");
        }
      case 125: 
        this.stackSize = (-1 + this.stackSize);
        JsonToken localJsonToken3 = JsonToken.END_OBJECT;
        this.token = localJsonToken3;
        return localJsonToken3;
      }
    }
    else
    {
      switch (nextNonWhitespace(true))
      {
      case 44: 
      case 59: 
      default: 
        throw syntaxError("Unterminated object");
      }
      this.stackSize = (-1 + this.stackSize);
      JsonToken localJsonToken1 = JsonToken.END_OBJECT;
      this.token = localJsonToken1;
      return localJsonToken1;
      checkLenient();
      this.name = nextString((char)i);
    }
    label218:
    this.stack[(-1 + this.stackSize)] = JsonScope.DANGLING_NAME;
    JsonToken localJsonToken2 = JsonToken.NAME;
    this.token = localJsonToken2;
    return localJsonToken2;
  }
  
  private String nextLiteral(boolean paramBoolean)
    throws IOException
  {
    StringBuilder localStringBuilder = null;
    this.valuePos = -1;
    this.valueLength = 0;
    int i = 0;
    label186:
    String str;
    while (i + this.pos < this.limit) {
      switch (this.buffer[(i + this.pos)])
      {
      default: 
        i++;
        break;
      case '#': 
      case '/': 
      case ';': 
      case '=': 
      case '\\': 
        checkLenient();
      case '\t': 
      case '\n': 
      case '\f': 
      case '\r': 
      case ' ': 
      case ',': 
      case ':': 
      case '[': 
      case ']': 
      case '{': 
      case '}': 
        if ((!paramBoolean) || (localStringBuilder != null)) {
          break label325;
        }
        this.valuePos = this.pos;
        str = null;
      }
    }
    for (;;)
    {
      this.valueLength = (i + this.valueLength);
      this.pos = (i + this.pos);
      return str;
      if (i < this.buffer.length)
      {
        if (fillBuffer(i + 1)) {
          break;
        }
        this.buffer[this.limit] = '\000';
        break label186;
      }
      if (localStringBuilder == null) {
        localStringBuilder = new StringBuilder();
      }
      localStringBuilder.append(this.buffer, this.pos, i);
      this.valueLength = (i + this.valueLength);
      this.pos = (i + this.pos);
      boolean bool = fillBuffer(1);
      i = 0;
      if (bool) {
        break;
      }
      i = 0;
      break label186;
      label325:
      if (this.skipping)
      {
        str = "skipped!";
      }
      else if (localStringBuilder == null)
      {
        str = this.stringPool.get(this.buffer, this.pos, i);
      }
      else
      {
        localStringBuilder.append(this.buffer, this.pos, i);
        str = localStringBuilder.toString();
      }
    }
  }
  
  private int nextNonWhitespace(boolean paramBoolean)
    throws IOException
  {
    char[] arrayOfChar = this.buffer;
    int i = this.pos;
    int j = this.limit;
    for (;;)
    {
      if (i == j)
      {
        this.pos = i;
        if (!fillBuffer(1)) {
          break;
        }
        i = this.pos;
        j = this.limit;
      }
      int k = i + 1;
      int m = arrayOfChar[i];
      switch (m)
      {
      default: 
        this.pos = k;
        return m;
      case 9: 
      case 10: 
      case 13: 
      case 32: 
        i = k;
        break;
      case 47: 
        this.pos = k;
        if ((k == j) && (!fillBuffer(1))) {
          return m;
        }
        checkLenient();
        switch (arrayOfChar[this.pos])
        {
        default: 
          return m;
        case '*': 
          this.pos = (1 + this.pos);
          if ((this.pos + "*/".length() <= this.limit) || (fillBuffer("*/".length()))) {
            for (int n = 0; n < "*/".length(); n++) {
              if (this.buffer[(n + this.pos)] != "*/".charAt(n)) {
                break label290;
              }
            }
          }
          for (int i1 = 1;; i1 = 0)
          {
            if (i1 != 0) {
              break label309;
            }
            throw syntaxError("Unterminated comment");
            this.pos = (1 + this.pos);
            break;
          }
          i = 2 + this.pos;
          j = this.limit;
          break;
        case '/': 
          this.pos = (1 + this.pos);
          skipToEndOfLine();
          i = this.pos;
          j = this.limit;
        }
        break;
      case 35: 
        label290:
        label309:
        this.pos = k;
        checkLenient();
        skipToEndOfLine();
        i = this.pos;
        j = this.limit;
      }
    }
    if (paramBoolean) {
      throw new EOFException("End of input at line " + getLineNumber() + " column " + getColumnNumber());
    }
    return -1;
  }
  
  private String nextString(char paramChar)
    throws IOException
  {
    char[] arrayOfChar = this.buffer;
    StringBuilder localStringBuilder = null;
    do
    {
      int i = this.pos;
      int j = this.limit;
      int k = i;
      int n;
      for (int m = i; m < j; m = n)
      {
        n = m + 1;
        char c = arrayOfChar[m];
        if (c == paramChar)
        {
          this.pos = n;
          if (this.skipping) {
            return "skipped!";
          }
          if (localStringBuilder == null) {
            return this.stringPool.get(arrayOfChar, k, -1 + (n - k));
          }
          localStringBuilder.append(arrayOfChar, k, -1 + (n - k));
          return localStringBuilder.toString();
        }
        if (c == '\\')
        {
          this.pos = n;
          if (localStringBuilder == null) {
            localStringBuilder = new StringBuilder();
          }
          localStringBuilder.append(arrayOfChar, k, -1 + (n - k));
          localStringBuilder.append(readEscapeCharacter());
          n = this.pos;
          j = this.limit;
          k = n;
        }
      }
      if (localStringBuilder == null) {
        localStringBuilder = new StringBuilder();
      }
      localStringBuilder.append(arrayOfChar, k, m - k);
      this.pos = m;
    } while (fillBuffer(1));
    throw syntaxError("Unterminated string");
  }
  
  private JsonToken nextValue()
    throws IOException
  {
    int i = nextNonWhitespace(true);
    switch (i)
    {
    default: 
      this.pos = (-1 + this.pos);
      this.value = nextLiteral(true);
      if (this.valueLength == 0) {
        throw syntaxError("Expected literal value");
      }
      break;
    case 123: 
      push(JsonScope.EMPTY_OBJECT);
      JsonToken localJsonToken3 = JsonToken.BEGIN_OBJECT;
      this.token = localJsonToken3;
      return localJsonToken3;
    case 91: 
      push(JsonScope.EMPTY_ARRAY);
      JsonToken localJsonToken2 = JsonToken.BEGIN_ARRAY;
      this.token = localJsonToken2;
      return localJsonToken2;
    case 39: 
      checkLenient();
    case 34: 
      this.value = nextString((char)i);
      JsonToken localJsonToken1 = JsonToken.STRING;
      this.token = localJsonToken1;
      return localJsonToken1;
    }
    JsonToken localJsonToken4;
    char[] arrayOfChar;
    int j;
    int k;
    int m;
    int n;
    if (this.valuePos != -1)
    {
      if ((this.valueLength == 4) && (('n' == this.buffer[this.valuePos]) || ('N' == this.buffer[this.valuePos])) && (('u' == this.buffer[(1 + this.valuePos)]) || ('U' == this.buffer[(1 + this.valuePos)])) && (('l' == this.buffer[(2 + this.valuePos)]) || ('L' == this.buffer[(2 + this.valuePos)])) && (('l' == this.buffer[(3 + this.valuePos)]) || ('L' == this.buffer[(3 + this.valuePos)])))
      {
        this.value = "null";
        localJsonToken4 = JsonToken.NULL;
      }
      for (;;)
      {
        this.token = localJsonToken4;
        if (this.token == JsonToken.STRING) {
          checkLenient();
        }
        return this.token;
        if ((this.valueLength == 4) && (('t' == this.buffer[this.valuePos]) || ('T' == this.buffer[this.valuePos])) && (('r' == this.buffer[(1 + this.valuePos)]) || ('R' == this.buffer[(1 + this.valuePos)])) && (('u' == this.buffer[(2 + this.valuePos)]) || ('U' == this.buffer[(2 + this.valuePos)])) && (('e' == this.buffer[(3 + this.valuePos)]) || ('E' == this.buffer[(3 + this.valuePos)])))
        {
          this.value = "true";
          localJsonToken4 = JsonToken.BOOLEAN;
        }
        else
        {
          if ((this.valueLength != 5) || (('f' != this.buffer[this.valuePos]) && ('F' != this.buffer[this.valuePos])) || (('a' != this.buffer[(1 + this.valuePos)]) && ('A' != this.buffer[(1 + this.valuePos)])) || (('l' != this.buffer[(2 + this.valuePos)]) && ('L' != this.buffer[(2 + this.valuePos)])) || (('s' != this.buffer[(3 + this.valuePos)]) && ('S' != this.buffer[(3 + this.valuePos)])) || (('e' != this.buffer[(4 + this.valuePos)]) && ('E' != this.buffer[(4 + this.valuePos)]))) {
            break;
          }
          this.value = "false";
          localJsonToken4 = JsonToken.BOOLEAN;
        }
      }
      this.value = this.stringPool.get(this.buffer, this.valuePos, this.valueLength);
      arrayOfChar = this.buffer;
      j = this.valuePos;
      k = this.valueLength;
      m = arrayOfChar[j];
      if (m != 45) {
        break label1008;
      }
      n = j + 1;
      m = arrayOfChar[n];
    }
    for (;;)
    {
      int i1;
      int i2;
      if (m == 48)
      {
        i1 = n + 1;
        i2 = arrayOfChar[i1];
      }
      for (;;)
      {
        label736:
        if (i2 == 46)
        {
          i1++;
          i2 = arrayOfChar[i1];
          for (;;)
          {
            if ((i2 >= 48) && (i2 <= 57))
            {
              i1++;
              i2 = arrayOfChar[i1];
              continue;
              if ((m >= 49) && (m <= 57))
              {
                i1 = n + 1;
                for (i2 = arrayOfChar[i1]; (i2 >= 48) && (i2 <= 57); i2 = arrayOfChar[i1]) {
                  i1++;
                }
                break label736;
              }
              localJsonToken4 = JsonToken.STRING;
              break;
            }
          }
        }
      }
      int i3 = i2;
      int i4 = i1;
      if ((i3 == 101) || (i3 == 69))
      {
        int i5 = i4 + 1;
        int i6 = arrayOfChar[i5];
        if ((i6 == 43) || (i6 == 45))
        {
          i5++;
          i6 = arrayOfChar[i5];
        }
        int i9;
        if ((i6 >= 48) && (i6 <= 57))
        {
          int i7 = i5 + 1;
          int i8 = arrayOfChar[i7];
          i4 = i7;
          i9 = i8;
        }
        for (;;)
        {
          if ((i9 >= 48) && (i9 <= 57))
          {
            int i10 = i4 + 1;
            int i11 = arrayOfChar[i10];
            i4 = i10;
            i9 = i11;
            continue;
            localJsonToken4 = JsonToken.STRING;
            break;
          }
        }
      }
      if (i4 == j + k)
      {
        localJsonToken4 = JsonToken.NUMBER;
        break;
      }
      localJsonToken4 = JsonToken.STRING;
      break;
      label1008:
      n = j;
    }
  }
  
  private void push(JsonScope paramJsonScope)
  {
    if (this.stackSize == this.stack.length)
    {
      JsonScope[] arrayOfJsonScope2 = new JsonScope[2 * this.stackSize];
      System.arraycopy(this.stack, 0, arrayOfJsonScope2, 0, this.stackSize);
      this.stack = arrayOfJsonScope2;
    }
    JsonScope[] arrayOfJsonScope1 = this.stack;
    int i = this.stackSize;
    this.stackSize = (i + 1);
    arrayOfJsonScope1[i] = paramJsonScope;
  }
  
  private char readEscapeCharacter()
    throws IOException
  {
    if ((this.pos == this.limit) && (!fillBuffer(1))) {
      throw syntaxError("Unterminated escape sequence");
    }
    char[] arrayOfChar = this.buffer;
    int i = this.pos;
    this.pos = (i + 1);
    char c1 = arrayOfChar[i];
    switch (c1)
    {
    default: 
      return c1;
    case 'u': 
      if ((4 + this.pos > this.limit) && (!fillBuffer(4))) {
        throw syntaxError("Unterminated escape sequence");
      }
      char c2 = '\000';
      int j = this.pos;
      int k = j + 4;
      if (j < k)
      {
        int m = this.buffer[j];
        int n = (char)(c2 << '\004');
        if ((m >= 48) && (m <= 57)) {
          c2 = (char)(n + (m - 48));
        }
        for (;;)
        {
          j++;
          break;
          if ((m >= 97) && (m <= 102))
          {
            c2 = (char)(n + (10 + (m - 97)));
          }
          else
          {
            if ((m < 65) || (m > 70)) {
              break label270;
            }
            c2 = (char)(n + (10 + (m - 65)));
          }
        }
        throw new NumberFormatException("\\u" + this.stringPool.get(this.buffer, this.pos, 4));
      }
      this.pos = (4 + this.pos);
      return c2;
    case 't': 
      return '\t';
    case 'b': 
      return '\b';
    case 'n': 
      return '\n';
    case 'r': 
      label270:
      return '\r';
    }
    return '\f';
  }
  
  private void skipToEndOfLine()
    throws IOException
  {
    int j;
    do
    {
      if ((this.pos >= this.limit) && (!fillBuffer(1))) {
        break;
      }
      char[] arrayOfChar = this.buffer;
      int i = this.pos;
      this.pos = (i + 1);
      j = arrayOfChar[i];
    } while ((j != 13) && (j != 10));
  }
  
  private IOException syntaxError(String paramString)
    throws IOException
  {
    throw new MalformedJsonException(paramString + " at line " + getLineNumber() + " column " + getColumnNumber());
  }
  
  public void beginArray()
    throws IOException
  {
    expect(JsonToken.BEGIN_ARRAY);
  }
  
  public void beginObject()
    throws IOException
  {
    expect(JsonToken.BEGIN_OBJECT);
  }
  
  public void close()
    throws IOException
  {
    this.value = null;
    this.token = null;
    this.stack[0] = JsonScope.CLOSED;
    this.stackSize = 1;
    this.in.close();
  }
  
  public void endArray()
    throws IOException
  {
    expect(JsonToken.END_ARRAY);
  }
  
  public void endObject()
    throws IOException
  {
    expect(JsonToken.END_OBJECT);
  }
  
  public boolean hasNext()
    throws IOException
  {
    peek();
    return (this.token != JsonToken.END_OBJECT) && (this.token != JsonToken.END_ARRAY);
  }
  
  public boolean nextBoolean()
    throws IOException
  {
    peek();
    if (this.token != JsonToken.BOOLEAN) {
      throw new IllegalStateException("Expected a boolean but was " + this.token + " at line " + getLineNumber() + " column " + getColumnNumber());
    }
    if (this.value == "true") {}
    for (boolean bool = true;; bool = false)
    {
      advance();
      return bool;
    }
  }
  
  public double nextDouble()
    throws IOException
  {
    peek();
    if ((this.token != JsonToken.STRING) && (this.token != JsonToken.NUMBER)) {
      throw new IllegalStateException("Expected a double but was " + this.token + " at line " + getLineNumber() + " column " + getColumnNumber());
    }
    double d = Double.parseDouble(this.value);
    if ((d >= 1.0D) && (this.value.startsWith("0"))) {
      throw new MalformedJsonException("JSON forbids octal prefixes: " + this.value + " at line " + getLineNumber() + " column " + getColumnNumber());
    }
    if ((!this.lenient) && ((Double.isNaN(d)) || (Double.isInfinite(d)))) {
      throw new MalformedJsonException("JSON forbids NaN and infinities: " + this.value + " at line " + getLineNumber() + " column " + getColumnNumber());
    }
    advance();
    return d;
  }
  
  public int nextInt()
    throws IOException
  {
    peek();
    if ((this.token != JsonToken.STRING) && (this.token != JsonToken.NUMBER)) {
      throw new IllegalStateException("Expected an int but was " + this.token + " at line " + getLineNumber() + " column " + getColumnNumber());
    }
    int i;
    try
    {
      int j = Integer.parseInt(this.value);
      i = j;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      double d;
      do
      {
        d = Double.parseDouble(this.value);
        i = (int)d;
      } while (i == d);
      throw new NumberFormatException("Expected an int but was " + this.value + " at line " + getLineNumber() + " column " + getColumnNumber());
    }
    if ((i >= 1L) && (this.value.startsWith("0"))) {
      throw new MalformedJsonException("JSON forbids octal prefixes: " + this.value + " at line " + getLineNumber() + " column " + getColumnNumber());
    }
    advance();
    return i;
  }
  
  public long nextLong()
    throws IOException
  {
    peek();
    if ((this.token != JsonToken.STRING) && (this.token != JsonToken.NUMBER)) {
      throw new IllegalStateException("Expected a long but was " + this.token + " at line " + getLineNumber() + " column " + getColumnNumber());
    }
    long l1;
    try
    {
      long l2 = Long.parseLong(this.value);
      l1 = l2;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      double d;
      do
      {
        d = Double.parseDouble(this.value);
        l1 = d;
      } while (l1 == d);
      throw new NumberFormatException("Expected a long but was " + this.value + " at line " + getLineNumber() + " column " + getColumnNumber());
    }
    if ((l1 >= 1L) && (this.value.startsWith("0"))) {
      throw new MalformedJsonException("JSON forbids octal prefixes: " + this.value + " at line " + getLineNumber() + " column " + getColumnNumber());
    }
    advance();
    return l1;
  }
  
  public String nextName()
    throws IOException
  {
    peek();
    if (this.token != JsonToken.NAME) {
      throw new IllegalStateException("Expected a name but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber());
    }
    String str = this.name;
    advance();
    return str;
  }
  
  public void nextNull()
    throws IOException
  {
    peek();
    if (this.token != JsonToken.NULL) {
      throw new IllegalStateException("Expected null but was " + this.token + " at line " + getLineNumber() + " column " + getColumnNumber());
    }
    advance();
  }
  
  public String nextString()
    throws IOException
  {
    peek();
    if ((this.token != JsonToken.STRING) && (this.token != JsonToken.NUMBER)) {
      throw new IllegalStateException("Expected a string but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber());
    }
    String str = this.value;
    advance();
    return str;
  }
  
  public JsonToken peek()
    throws IOException
  {
    JsonToken localJsonToken;
    if (this.token != null) {
      localJsonToken = this.token;
    }
    label203:
    do
    {
      return localJsonToken;
      switch (2.$SwitchMap$com$google$gson$stream$JsonScope[this.stack[(-1 + this.stackSize)].ordinal()])
      {
      default: 
        throw new AssertionError();
      case 1: 
        if (this.lenient)
        {
          nextNonWhitespace(true);
          this.pos = (-1 + this.pos);
          int i = this.pos + NON_EXECUTE_PREFIX.length;
          int j = this.limit;
          int k = 0;
          if (i > j)
          {
            boolean bool = fillBuffer(NON_EXECUTE_PREFIX.length);
            k = 0;
            if (!bool) {}
          }
          else
          {
            while (k < NON_EXECUTE_PREFIX.length)
            {
              if (this.buffer[(k + this.pos)] != NON_EXECUTE_PREFIX[k]) {
                break label203;
              }
              k++;
            }
            this.pos += NON_EXECUTE_PREFIX.length;
          }
        }
        this.stack[(-1 + this.stackSize)] = JsonScope.NONEMPTY_DOCUMENT;
        localJsonToken = nextValue();
      }
    } while ((this.lenient) || (this.token == JsonToken.BEGIN_ARRAY) || (this.token == JsonToken.BEGIN_OBJECT));
    throw new IOException("Expected JSON document to start with '[' or '{' but was " + this.token + " at line " + getLineNumber() + " column " + getColumnNumber());
    return nextInArray(true);
    return nextInArray(false);
    return nextInObject(true);
    switch (nextNonWhitespace(true))
    {
    case 59: 
    case 60: 
    default: 
      throw syntaxError("Expected ':'");
    case 61: 
      checkLenient();
      if (((this.pos < this.limit) || (fillBuffer(1))) && (this.buffer[this.pos] == '>')) {
        this.pos = (1 + this.pos);
      }
      break;
    }
    this.stack[(-1 + this.stackSize)] = JsonScope.NONEMPTY_OBJECT;
    return nextValue();
    return nextInObject(false);
    if (nextNonWhitespace(false) == -1) {
      return JsonToken.END_DOCUMENT;
    }
    this.pos = (-1 + this.pos);
    if (!this.lenient) {
      throw syntaxError("Expected EOF");
    }
    return nextValue();
    throw new IllegalStateException("JsonReader is closed");
  }
  
  /* Error */
  public void skipValue()
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: iconst_1
    //   2: putfield 89	com/google/gson/stream/JsonReader:skipping	Z
    //   5: iconst_0
    //   6: istore_1
    //   7: aload_0
    //   8: invokespecial 164	com/google/gson/stream/JsonReader:advance	()Lcom/google/gson/stream/JsonToken;
    //   11: astore_3
    //   12: aload_3
    //   13: getstatic 285	com/google/gson/stream/JsonToken:BEGIN_ARRAY	Lcom/google/gson/stream/JsonToken;
    //   16: if_acmpeq +14 -> 30
    //   19: getstatic 279	com/google/gson/stream/JsonToken:BEGIN_OBJECT	Lcom/google/gson/stream/JsonToken;
    //   22: astore 4
    //   24: aload_3
    //   25: aload 4
    //   27: if_acmpne +16 -> 43
    //   30: iinc 1 1
    //   33: iload_1
    //   34: ifne -27 -> 7
    //   37: aload_0
    //   38: iconst_0
    //   39: putfield 89	com/google/gson/stream/JsonReader:skipping	Z
    //   42: return
    //   43: aload_3
    //   44: getstatic 198	com/google/gson/stream/JsonToken:END_ARRAY	Lcom/google/gson/stream/JsonToken;
    //   47: if_acmpeq +14 -> 61
    //   50: getstatic 218	com/google/gson/stream/JsonToken:END_OBJECT	Lcom/google/gson/stream/JsonToken;
    //   53: astore 5
    //   55: aload_3
    //   56: aload 5
    //   58: if_acmpne -25 -> 33
    //   61: iinc 1 255
    //   64: goto -31 -> 33
    //   67: astore_2
    //   68: aload_0
    //   69: iconst_0
    //   70: putfield 89	com/google/gson/stream/JsonReader:skipping	Z
    //   73: aload_2
    //   74: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	75	0	this	JsonReader
    //   6	56	1	i	int
    //   67	7	2	localObject	Object
    //   11	45	3	localJsonToken1	JsonToken
    //   22	4	4	localJsonToken2	JsonToken
    //   53	4	5	localJsonToken3	JsonToken
    // Exception table:
    //   from	to	target	type
    //   7	24	67	finally
    //   43	55	67	finally
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder1 = new StringBuilder().append(getClass().getSimpleName()).append(" near ");
    StringBuilder localStringBuilder2 = new StringBuilder();
    int i = Math.min(this.pos, 20);
    localStringBuilder2.append(this.buffer, this.pos - i, i);
    int j = Math.min(this.limit - this.pos, 20);
    localStringBuilder2.append(this.buffer, this.pos, j);
    return localStringBuilder2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.stream.JsonReader
 * JD-Core Version:    0.7.0.1
 */