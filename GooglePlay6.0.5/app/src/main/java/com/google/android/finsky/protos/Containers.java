package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface Containers
{
  public static final class ContainerMetadata
    extends MessageNano
  {
    public String analyticsCookie = "";
    public String browseUrl = "";
    public Containers.ContainerView[] containerView = Containers.ContainerView.emptyArray();
    public long estimatedResults = 0L;
    public boolean hasAnalyticsCookie = false;
    public boolean hasBrowseUrl = false;
    public boolean hasEstimatedResults = false;
    public boolean hasNextPageUrl = false;
    public boolean hasOrdered = false;
    public boolean hasRelevance = false;
    public Common.Image leftIcon = null;
    public String nextPageUrl = "";
    public boolean ordered = false;
    public double relevance = 0.0D;
    
    public ContainerMetadata()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasBrowseUrl) || (!this.browseUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.browseUrl);
      }
      if ((this.hasNextPageUrl) || (!this.nextPageUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.nextPageUrl);
      }
      if ((this.hasRelevance) || (Double.doubleToLongBits(this.relevance) != Double.doubleToLongBits(0.0D))) {
        i += 8 + CodedOutputByteBufferNano.computeTagSize(3);
      }
      if ((this.hasEstimatedResults) || (this.estimatedResults != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(4, this.estimatedResults);
      }
      if ((this.hasAnalyticsCookie) || (!this.analyticsCookie.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.analyticsCookie);
      }
      if ((this.hasOrdered) || (this.ordered)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(6);
      }
      if ((this.containerView != null) && (this.containerView.length > 0)) {
        for (int j = 0; j < this.containerView.length; j++)
        {
          Containers.ContainerView localContainerView = this.containerView[j];
          if (localContainerView != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(7, localContainerView);
          }
        }
      }
      if (this.leftIcon != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(8, this.leftIcon);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasBrowseUrl) || (!this.browseUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.browseUrl);
      }
      if ((this.hasNextPageUrl) || (!this.nextPageUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.nextPageUrl);
      }
      if ((this.hasRelevance) || (Double.doubleToLongBits(this.relevance) != Double.doubleToLongBits(0.0D))) {
        paramCodedOutputByteBufferNano.writeDouble(3, this.relevance);
      }
      if ((this.hasEstimatedResults) || (this.estimatedResults != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(4, this.estimatedResults);
      }
      if ((this.hasAnalyticsCookie) || (!this.analyticsCookie.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.analyticsCookie);
      }
      if ((this.hasOrdered) || (this.ordered)) {
        paramCodedOutputByteBufferNano.writeBool(6, this.ordered);
      }
      if ((this.containerView != null) && (this.containerView.length > 0)) {
        for (int i = 0; i < this.containerView.length; i++)
        {
          Containers.ContainerView localContainerView = this.containerView[i];
          if (localContainerView != null) {
            paramCodedOutputByteBufferNano.writeMessage(7, localContainerView);
          }
        }
      }
      if (this.leftIcon != null) {
        paramCodedOutputByteBufferNano.writeMessage(8, this.leftIcon);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ContainerView
    extends MessageNano
  {
    private static volatile ContainerView[] _emptyArray;
    public boolean hasListUrl = false;
    public boolean hasSelected = false;
    public boolean hasServerLogsCookie = false;
    public boolean hasTitle = false;
    public String listUrl = "";
    public boolean selected = false;
    public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
    public String title = "";
    
    public ContainerView()
    {
      this.cachedSize = -1;
    }
    
    public static ContainerView[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new ContainerView[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasSelected) || (this.selected)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(1);
      }
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.title);
      }
      if ((this.hasListUrl) || (!this.listUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.listUrl);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(4, this.serverLogsCookie);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasSelected) || (this.selected)) {
        paramCodedOutputByteBufferNano.writeBool(1, this.selected);
      }
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.title);
      }
      if ((this.hasListUrl) || (!this.listUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.listUrl);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(4, this.serverLogsCookie);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Containers
 * JD-Core Version:    0.7.0.1
 */