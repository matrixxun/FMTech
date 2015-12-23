package com.android.ex.photo.util;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public final class InputStreamBuffer
{
  private boolean mAutoAdvance;
  private byte[] mBuffer;
  private int mFilled = 0;
  private InputStream mInputStream;
  private int mOffset = 0;
  
  public InputStreamBuffer(InputStream paramInputStream)
  {
    this.mInputStream = paramInputStream;
    this.mBuffer = new byte[leastPowerOf2(16)];
    this.mAutoAdvance = false;
  }
  
  private boolean fill(int paramInt)
  {
    Trace.beginSection("fill");
    if (paramInt < this.mOffset)
    {
      Trace.endSection();
      Object[] arrayOfObject3 = new Object[2];
      arrayOfObject3[0] = Integer.valueOf(paramInt);
      arrayOfObject3[1] = Integer.valueOf(this.mOffset);
      throw new IllegalStateException(String.format("Index %d is before buffer %d", arrayOfObject3));
    }
    int i = paramInt - this.mOffset;
    if (this.mInputStream == null)
    {
      Trace.endSection();
      return false;
    }
    int j = i + 1;
    label107:
    int k;
    if (j > this.mBuffer.length)
    {
      if (this.mAutoAdvance)
      {
        advanceTo(paramInt);
        i = paramInt - this.mOffset;
      }
    }
    else {
      k = -1;
    }
    try
    {
      int m = this.mInputStream.read(this.mBuffer, this.mFilled, this.mBuffer.length - this.mFilled);
      k = m;
    }
    catch (IOException localIOException)
    {
      label141:
      break label141;
    }
    if (k != -1) {
      this.mFilled = (k + this.mFilled);
    }
    for (;;)
    {
      if (Log.isLoggable("InputStreamBuffer", 3))
      {
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = Integer.valueOf(i);
        arrayOfObject1[1] = this;
        Log.d("InputStreamBuffer", String.format("fill %d      buffer: %s", arrayOfObject1));
      }
      Trace.endSection();
      if (i >= this.mFilled) {
        break;
      }
      return true;
      int n = leastPowerOf2(j);
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = Integer.valueOf(this.mBuffer.length);
      arrayOfObject2[1] = Integer.valueOf(n);
      Log.w("InputStreamBuffer", String.format("Increasing buffer length from %d to %d. Bad buffer size chosen, or advanceTo() not called.", arrayOfObject2));
      this.mBuffer = Arrays.copyOf(this.mBuffer, n);
      break label107;
      this.mInputStream = null;
    }
  }
  
  private static int leastPowerOf2(int paramInt)
  {
    int i = paramInt - 1;
    int j = i | i >> 1;
    int k = j | j >> 2;
    int m = k | k >> 4;
    int n = m | m >> 8;
    return 1 + (n | n >> 16);
  }
  
  public final void advanceTo(int paramInt)
    throws IllegalStateException, IndexOutOfBoundsException
  {
    Trace.beginSection("advance to");
    int i = paramInt - this.mOffset;
    if (i <= 0)
    {
      Trace.endSection();
      return;
    }
    if (i < this.mFilled)
    {
      if (i >= this.mBuffer.length)
      {
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = Integer.valueOf(i);
        arrayOfObject2[1] = Integer.valueOf(this.mBuffer.length);
        throw new IndexOutOfBoundsException(String.format("Index %d out of bounds. Length %d", arrayOfObject2));
      }
      for (int n = 0; n + i < this.mFilled; n++) {
        this.mBuffer[n] = this.mBuffer[(n + i)];
      }
      this.mOffset = paramInt;
      this.mFilled -= i;
    }
    for (;;)
    {
      if (Log.isLoggable("InputStreamBuffer", 3))
      {
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = Integer.valueOf(i);
        arrayOfObject1[1] = this;
        Log.d("InputStreamBuffer", String.format("advanceTo %d buffer: %s", arrayOfObject1));
      }
      Trace.endSection();
      return;
      label188:
      int m;
      if (this.mInputStream != null)
      {
        int j = i - this.mFilled;
        int k = 0;
        m = 0;
        if (j > 0) {}
        for (;;)
        {
          try
          {
            l = this.mInputStream.skip(j);
            if (l > 0L) {
              continue;
            }
            k++;
            if (k < 5) {
              break label188;
            }
            m = 1;
          }
          catch (IOException localIOException)
          {
            long l;
            m = 1;
            continue;
          }
          if (m != 0) {
            this.mInputStream = null;
          }
          this.mOffset = (paramInt - j);
          this.mFilled = 0;
          break;
          j = (int)(j - l);
        }
      }
      this.mOffset = paramInt;
      this.mFilled = 0;
    }
  }
  
  public final byte get(int paramInt)
    throws IllegalStateException, IndexOutOfBoundsException
  {
    Trace.beginSection("get");
    if (has(paramInt))
    {
      int i = paramInt - this.mOffset;
      Trace.endSection();
      return this.mBuffer[i];
    }
    Trace.endSection();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(paramInt);
    throw new IndexOutOfBoundsException(String.format("Index %d beyond length.", arrayOfObject));
  }
  
  public final boolean has(int paramInt)
    throws IllegalStateException, IndexOutOfBoundsException
  {
    Trace.beginSection("has");
    if (paramInt < this.mOffset)
    {
      Trace.endSection();
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      arrayOfObject[1] = Integer.valueOf(this.mOffset);
      throw new IllegalStateException(String.format("Index %d is before buffer %d", arrayOfObject));
    }
    int i = paramInt - this.mOffset;
    if ((i >= this.mFilled) || (i >= this.mBuffer.length))
    {
      Trace.endSection();
      return fill(paramInt);
    }
    Trace.endSection();
    return true;
  }
  
  public final String toString()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = Integer.valueOf(this.mOffset);
    arrayOfObject[1] = Integer.valueOf(this.mBuffer.length);
    arrayOfObject[2] = Integer.valueOf(this.mFilled);
    return String.format("+%d+%d [%d]", arrayOfObject);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.ex.photo.util.InputStreamBuffer
 * JD-Core Version:    0.7.0.1
 */