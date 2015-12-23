package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public abstract interface ModifyLibrary
{
  public static final class ModifyLibraryRequest
    extends MessageNano
  {
    public String[] forAddDocid = WireFormatNano.EMPTY_STRING_ARRAY;
    public String[] forArchiveDocid = WireFormatNano.EMPTY_STRING_ARRAY;
    public String[] forRemovalDocid = WireFormatNano.EMPTY_STRING_ARRAY;
    public boolean hasLibraryId = false;
    public String libraryId = "";
    
    public ModifyLibraryRequest()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasLibraryId) || (!this.libraryId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.libraryId);
      }
      if ((this.forAddDocid != null) && (this.forAddDocid.length > 0))
      {
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < this.forAddDocid.length; i5++)
        {
          String str3 = this.forAddDocid[i5];
          if (str3 != null)
          {
            i3++;
            i4 += CodedOutputByteBufferNano.computeStringSizeNoTag(str3);
          }
        }
        i = i + i4 + i3 * 1;
      }
      if ((this.forRemovalDocid != null) && (this.forRemovalDocid.length > 0))
      {
        int n = 0;
        int i1 = 0;
        for (int i2 = 0; i2 < this.forRemovalDocid.length; i2++)
        {
          String str2 = this.forRemovalDocid[i2];
          if (str2 != null)
          {
            n++;
            i1 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
          }
        }
        i = i + i1 + n * 1;
      }
      if ((this.forArchiveDocid != null) && (this.forArchiveDocid.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.forArchiveDocid.length; m++)
        {
          String str1 = this.forArchiveDocid[m];
          if (str1 != null)
          {
            j++;
            k += CodedOutputByteBufferNano.computeStringSizeNoTag(str1);
          }
        }
        i = i + k + j * 1;
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasLibraryId) || (!this.libraryId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.libraryId);
      }
      if ((this.forAddDocid != null) && (this.forAddDocid.length > 0)) {
        for (int k = 0; k < this.forAddDocid.length; k++)
        {
          String str3 = this.forAddDocid[k];
          if (str3 != null) {
            paramCodedOutputByteBufferNano.writeString(2, str3);
          }
        }
      }
      if ((this.forRemovalDocid != null) && (this.forRemovalDocid.length > 0)) {
        for (int j = 0; j < this.forRemovalDocid.length; j++)
        {
          String str2 = this.forRemovalDocid[j];
          if (str2 != null) {
            paramCodedOutputByteBufferNano.writeString(3, str2);
          }
        }
      }
      if ((this.forArchiveDocid != null) && (this.forArchiveDocid.length > 0)) {
        for (int i = 0; i < this.forArchiveDocid.length; i++)
        {
          String str1 = this.forArchiveDocid[i];
          if (str1 != null) {
            paramCodedOutputByteBufferNano.writeString(4, str1);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ModifyLibraryResponse
    extends MessageNano
  {
    public LibraryUpdateProto.LibraryUpdate libraryUpdate = null;
    
    public ModifyLibraryResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.libraryUpdate != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.libraryUpdate);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.libraryUpdate != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.libraryUpdate);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ModifyLibrary
 * JD-Core Version:    0.7.0.1
 */