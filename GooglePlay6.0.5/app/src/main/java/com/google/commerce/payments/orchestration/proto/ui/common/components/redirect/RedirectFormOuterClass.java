package com.google.commerce.payments.orchestration.proto.ui.common.components.redirect;

import com.google.commerce.payments.orchestration.proto.ui.common.components.FormHeaderOuterClass.FormHeader;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface RedirectFormOuterClass
{
  public static final class RedirectForm
    extends MessageNano
  {
    public int displayType = 0;
    public FormHeaderOuterClass.FormHeader formHeader = null;
    public String initialPostBody = "";
    public String initialUrl = "";
    public String interceptNonTerminalUrlRegex = "";
    public String interceptTerminalUrlRegex = "";
    public boolean mustEnforceWhitelist = false;
    public String userAgent = "";
    public String[] whitelistUrlRegex = WireFormatNano.EMPTY_STRING_ARRAY;
    
    public RedirectForm()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.formHeader != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.formHeader);
      }
      if (!this.initialUrl.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.initialUrl);
      }
      if (!this.initialPostBody.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.initialPostBody);
      }
      if (!this.interceptNonTerminalUrlRegex.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.interceptNonTerminalUrlRegex);
      }
      if (!this.interceptTerminalUrlRegex.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.interceptTerminalUrlRegex);
      }
      if (this.displayType != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(7, this.displayType);
      }
      if (this.mustEnforceWhitelist) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(8);
      }
      if ((this.whitelistUrlRegex != null) && (this.whitelistUrlRegex.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.whitelistUrlRegex.length; m++)
        {
          String str = this.whitelistUrlRegex[m];
          if (str != null)
          {
            j++;
            k += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
          }
        }
        i = i + k + j * 1;
      }
      if (!this.userAgent.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(10, this.userAgent);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.formHeader != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.formHeader);
      }
      if (!this.initialUrl.equals("")) {
        paramCodedOutputByteBufferNano.writeString(2, this.initialUrl);
      }
      if (!this.initialPostBody.equals("")) {
        paramCodedOutputByteBufferNano.writeString(3, this.initialPostBody);
      }
      if (!this.interceptNonTerminalUrlRegex.equals("")) {
        paramCodedOutputByteBufferNano.writeString(4, this.interceptNonTerminalUrlRegex);
      }
      if (!this.interceptTerminalUrlRegex.equals("")) {
        paramCodedOutputByteBufferNano.writeString(5, this.interceptTerminalUrlRegex);
      }
      if (this.displayType != 0) {
        paramCodedOutputByteBufferNano.writeInt32(7, this.displayType);
      }
      if (this.mustEnforceWhitelist) {
        paramCodedOutputByteBufferNano.writeBool(8, this.mustEnforceWhitelist);
      }
      if ((this.whitelistUrlRegex != null) && (this.whitelistUrlRegex.length > 0)) {
        for (int i = 0; i < this.whitelistUrlRegex.length; i++)
        {
          String str = this.whitelistUrlRegex[i];
          if (str != null) {
            paramCodedOutputByteBufferNano.writeString(9, str);
          }
        }
      }
      if (!this.userAgent.equals("")) {
        paramCodedOutputByteBufferNano.writeString(10, this.userAgent);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class RedirectFormValue
    extends MessageNano
  {
    public byte[] dataToken = WireFormatNano.EMPTY_BYTES;
    public String id = "";
    public String interceptedPostBody = "";
    public String nonTerminalUrl = "";
    public String terminalUrl = "";
    
    public RedirectFormValue()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.id.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.id);
      }
      if (!Arrays.equals(this.dataToken, WireFormatNano.EMPTY_BYTES)) {
        i += CodedOutputByteBufferNano.computeBytesSize(2, this.dataToken);
      }
      if (!this.nonTerminalUrl.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.nonTerminalUrl);
      }
      if (!this.terminalUrl.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.terminalUrl);
      }
      if (!this.interceptedPostBody.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.interceptedPostBody);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.id.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.id);
      }
      if (!Arrays.equals(this.dataToken, WireFormatNano.EMPTY_BYTES)) {
        paramCodedOutputByteBufferNano.writeBytes(2, this.dataToken);
      }
      if (!this.nonTerminalUrl.equals("")) {
        paramCodedOutputByteBufferNano.writeString(3, this.nonTerminalUrl);
      }
      if (!this.terminalUrl.equals("")) {
        paramCodedOutputByteBufferNano.writeString(4, this.terminalUrl);
      }
      if (!this.interceptedPostBody.equals("")) {
        paramCodedOutputByteBufferNano.writeString(5, this.interceptedPostBody);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.components.redirect.RedirectFormOuterClass
 * JD-Core Version:    0.7.0.1
 */