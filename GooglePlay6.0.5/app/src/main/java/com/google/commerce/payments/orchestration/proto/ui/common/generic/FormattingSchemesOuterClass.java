package com.google.commerce.payments.orchestration.proto.ui.common.generic;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface FormattingSchemesOuterClass
{
  public static final class TemplateFormattingScheme
    extends MessageNano
  {
    public InputCharacter[] inputCharacter = InputCharacter.emptyArray();
    public boolean showTemplate = false;
    public String template = "";
    
    public TemplateFormattingScheme()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.inputCharacter != null) && (this.inputCharacter.length > 0)) {
        for (int j = 0; j < this.inputCharacter.length; j++)
        {
          InputCharacter localInputCharacter = this.inputCharacter[j];
          if (localInputCharacter != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localInputCharacter);
          }
        }
      }
      if (!this.template.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.template);
      }
      if (this.showTemplate) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(3);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.inputCharacter != null) && (this.inputCharacter.length > 0)) {
        for (int i = 0; i < this.inputCharacter.length; i++)
        {
          InputCharacter localInputCharacter = this.inputCharacter[i];
          if (localInputCharacter != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localInputCharacter);
          }
        }
      }
      if (!this.template.equals("")) {
        paramCodedOutputByteBufferNano.writeString(2, this.template);
      }
      if (this.showTemplate) {
        paramCodedOutputByteBufferNano.writeBool(3, this.showTemplate);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class InputCharacter
      extends MessageNano
    {
      private static volatile InputCharacter[] _emptyArray;
      public String displayCharacter = "";
      public String placeholderCharacter = "";
      public String regex = "";
      
      public InputCharacter()
      {
        this.cachedSize = -1;
      }
      
      public static InputCharacter[] emptyArray()
      {
        if (_emptyArray == null) {}
        synchronized (InternalNano.LAZY_INIT_LOCK)
        {
          if (_emptyArray == null) {
            _emptyArray = new InputCharacter[0];
          }
          return _emptyArray;
        }
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if (!this.placeholderCharacter.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(1, this.placeholderCharacter);
        }
        if (!this.displayCharacter.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(2, this.displayCharacter);
        }
        if (!this.regex.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(3, this.regex);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if (!this.placeholderCharacter.equals("")) {
          paramCodedOutputByteBufferNano.writeString(1, this.placeholderCharacter);
        }
        if (!this.displayCharacter.equals("")) {
          paramCodedOutputByteBufferNano.writeString(2, this.displayCharacter);
        }
        if (!this.regex.equals("")) {
          paramCodedOutputByteBufferNano.writeString(3, this.regex);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.generic.FormattingSchemesOuterClass
 * JD-Core Version:    0.7.0.1
 */