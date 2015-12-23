package com.squareup.okhttp.internal.tls;

import javax.security.auth.x500.X500Principal;

final class DistinguishedNameParser
{
  int beg;
  char[] chars;
  int cur;
  final String dn;
  int end;
  final int length;
  int pos;
  
  public DistinguishedNameParser(X500Principal paramX500Principal)
  {
    this.dn = paramX500Principal.getName("RFC2253");
    this.length = this.dn.length();
  }
  
  private int getByte(int paramInt)
  {
    if (paramInt + 1 >= this.length) {
      throw new IllegalStateException("Malformed DN: " + this.dn);
    }
    int i = this.chars[paramInt];
    int j;
    int k;
    int m;
    if ((i >= 48) && (i <= 57))
    {
      j = i - 48;
      k = this.chars[(paramInt + 1)];
      if ((k < 48) || (k > 57)) {
        break label166;
      }
      m = k - 48;
    }
    for (;;)
    {
      return m + (j << 4);
      if ((i >= 97) && (i <= 102))
      {
        j = i - 87;
        break;
      }
      if ((i >= 65) && (i <= 70))
      {
        j = i - 55;
        break;
      }
      throw new IllegalStateException("Malformed DN: " + this.dn);
      label166:
      if ((k >= 97) && (k <= 102))
      {
        m = k - 87;
      }
      else
      {
        if ((k < 65) || (k > 70)) {
          break label214;
        }
        m = k - 55;
      }
    }
    label214:
    throw new IllegalStateException("Malformed DN: " + this.dn);
  }
  
  private char getUTF8()
  {
    char c = '?';
    int i = getByte(this.pos);
    this.pos = (1 + this.pos);
    if (i < 128) {
      c = (char)i;
    }
    while ((i < 192) || (i > 247)) {
      return c;
    }
    int j;
    int k;
    if (i <= 223)
    {
      j = 1;
      k = i & 0x1F;
    }
    for (;;)
    {
      for (int m = 0;; m++)
      {
        if (m >= j) {
          break label197;
        }
        this.pos = (1 + this.pos);
        if ((this.pos == this.length) || (this.chars[this.pos] != '\\')) {
          break;
        }
        this.pos = (1 + this.pos);
        int n = getByte(this.pos);
        this.pos = (1 + this.pos);
        if ((n & 0xC0) != 128) {
          break;
        }
        k = (k << 6) + (n & 0x3F);
      }
      if (i <= 239)
      {
        j = 2;
        k = i & 0xF;
      }
      else
      {
        j = 3;
        k = i & 0x7;
      }
    }
    label197:
    return (char)k;
  }
  
  final String escapedAV()
  {
    this.beg = this.pos;
    this.end = this.pos;
    do
    {
      for (;;)
      {
        if (this.pos >= this.length) {
          return new String(this.chars, this.beg, this.end - this.beg);
        }
        switch (this.chars[this.pos])
        {
        default: 
          char[] arrayOfChar4 = this.chars;
          int m = this.end;
          this.end = (m + 1);
          arrayOfChar4[m] = this.chars[this.pos];
          this.pos = (1 + this.pos);
          break;
        case '+': 
        case ',': 
        case ';': 
          return new String(this.chars, this.beg, this.end - this.beg);
        case '\\': 
          char[] arrayOfChar3 = this.chars;
          int k = this.end;
          this.end = (k + 1);
          arrayOfChar3[k] = getEscaped();
          this.pos = (1 + this.pos);
        }
      }
      this.cur = this.end;
      this.pos = (1 + this.pos);
      char[] arrayOfChar1 = this.chars;
      int i = this.end;
      this.end = (i + 1);
      arrayOfChar1[i] = ' ';
      while ((this.pos < this.length) && (this.chars[this.pos] == ' '))
      {
        char[] arrayOfChar2 = this.chars;
        int j = this.end;
        this.end = (j + 1);
        arrayOfChar2[j] = ' ';
        this.pos = (1 + this.pos);
      }
    } while ((this.pos != this.length) && (this.chars[this.pos] != ',') && (this.chars[this.pos] != '+') && (this.chars[this.pos] != ';'));
    return new String(this.chars, this.beg, this.cur - this.beg);
  }
  
  final char getEscaped()
  {
    this.pos = (1 + this.pos);
    if (this.pos == this.length) {
      throw new IllegalStateException("Unexpected end of DN: " + this.dn);
    }
    switch (this.chars[this.pos])
    {
    default: 
      return getUTF8();
    }
    return this.chars[this.pos];
  }
  
  final String hexAV()
  {
    if (4 + this.pos >= this.length) {
      throw new IllegalStateException("Unexpected end of DN: " + this.dn);
    }
    this.beg = this.pos;
    int i;
    for (this.pos = (1 + this.pos);; this.pos = (1 + this.pos))
    {
      if ((this.pos == this.length) || (this.chars[this.pos] == '+') || (this.chars[this.pos] == ',') || (this.chars[this.pos] == ';')) {
        this.end = this.pos;
      }
      for (;;)
      {
        i = this.end - this.beg;
        if ((i >= 5) && ((i & 0x1) != 0)) {
          break label304;
        }
        throw new IllegalStateException("Unexpected end of DN: " + this.dn);
        if (this.chars[this.pos] != ' ') {
          break;
        }
        this.end = this.pos;
        for (this.pos = (1 + this.pos); (this.pos < this.length) && (this.chars[this.pos] == ' '); this.pos = (1 + this.pos)) {}
      }
      if ((this.chars[this.pos] >= 'A') && (this.chars[this.pos] <= 'F'))
      {
        char[] arrayOfChar = this.chars;
        int m = this.pos;
        arrayOfChar[m] = ((char)(' ' + arrayOfChar[m]));
      }
    }
    label304:
    byte[] arrayOfByte = new byte[i / 2];
    int j = 0;
    int k = 1 + this.beg;
    while (j < arrayOfByte.length)
    {
      arrayOfByte[j] = ((byte)getByte(k));
      k += 2;
      j++;
    }
    return new String(this.chars, this.beg, i);
  }
  
  final String nextAT()
  {
    while ((this.pos < this.length) && (this.chars[this.pos] == ' ')) {
      this.pos = (1 + this.pos);
    }
    if (this.pos == this.length) {
      return null;
    }
    this.beg = this.pos;
    for (this.pos = (1 + this.pos); (this.pos < this.length) && (this.chars[this.pos] != '=') && (this.chars[this.pos] != ' '); this.pos = (1 + this.pos)) {}
    if (this.pos >= this.length) {
      throw new IllegalStateException("Unexpected end of DN: " + this.dn);
    }
    this.end = this.pos;
    if (this.chars[this.pos] == ' ')
    {
      while ((this.pos < this.length) && (this.chars[this.pos] != '=') && (this.chars[this.pos] == ' ')) {
        this.pos = (1 + this.pos);
      }
      if ((this.chars[this.pos] != '=') || (this.pos == this.length)) {
        throw new IllegalStateException("Unexpected end of DN: " + this.dn);
      }
    }
    do
    {
      this.pos = (1 + this.pos);
    } while ((this.pos < this.length) && (this.chars[this.pos] == ' '));
    if ((this.end - this.beg > 4) && (this.chars[(3 + this.beg)] == '.') && ((this.chars[this.beg] == 'O') || (this.chars[this.beg] == 'o')) && ((this.chars[(1 + this.beg)] == 'I') || (this.chars[(1 + this.beg)] == 'i')) && ((this.chars[(2 + this.beg)] == 'D') || (this.chars[(2 + this.beg)] == 'd'))) {
      this.beg = (4 + this.beg);
    }
    return new String(this.chars, this.beg, this.end - this.beg);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.tls.DistinguishedNameParser
 * JD-Core Version:    0.7.0.1
 */