package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public final class ArtistExternalLinks
  extends MessageNano
{
  public String googlePlusProfileUrl = "";
  public boolean hasGooglePlusProfileUrl = false;
  public boolean hasYoutubeChannelUrl = false;
  public String[] websiteUrl = WireFormatNano.EMPTY_STRING_ARRAY;
  public String youtubeChannelUrl = "";
  
  public ArtistExternalLinks()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.websiteUrl != null) && (this.websiteUrl.length > 0))
    {
      int j = 0;
      int k = 0;
      for (int m = 0; m < this.websiteUrl.length; m++)
      {
        String str = this.websiteUrl[m];
        if (str != null)
        {
          j++;
          k += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
        }
      }
      i = i + k + j * 1;
    }
    if ((this.hasGooglePlusProfileUrl) || (!this.googlePlusProfileUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.googlePlusProfileUrl);
    }
    if ((this.hasYoutubeChannelUrl) || (!this.youtubeChannelUrl.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.youtubeChannelUrl);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.websiteUrl != null) && (this.websiteUrl.length > 0)) {
      for (int i = 0; i < this.websiteUrl.length; i++)
      {
        String str = this.websiteUrl[i];
        if (str != null) {
          paramCodedOutputByteBufferNano.writeString(1, str);
        }
      }
    }
    if ((this.hasGooglePlusProfileUrl) || (!this.googlePlusProfileUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.googlePlusProfileUrl);
    }
    if ((this.hasYoutubeChannelUrl) || (!this.youtubeChannelUrl.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.youtubeChannelUrl);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ArtistExternalLinks
 * JD-Core Version:    0.7.0.1
 */