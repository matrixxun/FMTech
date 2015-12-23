package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface Browse
{
  public static final class BrowseLink
    extends MessageNano
  {
    private static volatile BrowseLink[] _emptyArray;
    public String dataUrl = "";
    public boolean hasDataUrl = false;
    public boolean hasName = false;
    public boolean hasServerLogsCookie = false;
    public Common.Image image = null;
    public String name = "";
    public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
    
    public BrowseLink()
    {
      this.cachedSize = -1;
    }
    
    public static BrowseLink[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new BrowseLink[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasName) || (!this.name.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.name);
      }
      if ((this.hasDataUrl) || (!this.dataUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.dataUrl);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(4, this.serverLogsCookie);
      }
      if (this.image != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.image);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasName) || (!this.name.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.name);
      }
      if ((this.hasDataUrl) || (!this.dataUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.dataUrl);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(4, this.serverLogsCookie);
      }
      if (this.image != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.image);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class BrowseResponse
    extends MessageNano
  {
    public int backendId = 0;
    public Browse.BrowseLink[] breadcrumb = Browse.BrowseLink.emptyArray();
    public Browse.BrowseTab[] browseTab = Browse.BrowseTab.emptyArray();
    public Browse.BrowseLink[] category = Browse.BrowseLink.emptyArray();
    public String contentsUrl = "";
    public boolean hasBackendId = false;
    public boolean hasContentsUrl = false;
    public boolean hasIsFamilySafe = false;
    public boolean hasLandingTabIndex = false;
    public boolean hasPromoUrl = false;
    public boolean hasQuickLinkFallbackTabIndex = false;
    public boolean hasQuickLinkTabIndex = false;
    public boolean hasServerLogsCookie = false;
    public boolean hasTitle = false;
    public boolean isFamilySafe = false;
    public int landingTabIndex = 0;
    public String promoUrl = "";
    public Browse.QuickLink[] quickLink = Browse.QuickLink.emptyArray();
    public int quickLinkFallbackTabIndex = 0;
    public int quickLinkTabIndex = 0;
    public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
    public String title = "";
    
    public BrowseResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasContentsUrl) || (!this.contentsUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.contentsUrl);
      }
      if ((this.hasPromoUrl) || (!this.promoUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.promoUrl);
      }
      if ((this.category != null) && (this.category.length > 0)) {
        for (int n = 0; n < this.category.length; n++)
        {
          Browse.BrowseLink localBrowseLink2 = this.category[n];
          if (localBrowseLink2 != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, localBrowseLink2);
          }
        }
      }
      if ((this.breadcrumb != null) && (this.breadcrumb.length > 0)) {
        for (int m = 0; m < this.breadcrumb.length; m++)
        {
          Browse.BrowseLink localBrowseLink1 = this.breadcrumb[m];
          if (localBrowseLink1 != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(4, localBrowseLink1);
          }
        }
      }
      if ((this.quickLink != null) && (this.quickLink.length > 0)) {
        for (int k = 0; k < this.quickLink.length; k++)
        {
          Browse.QuickLink localQuickLink = this.quickLink[k];
          if (localQuickLink != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(5, localQuickLink);
          }
        }
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(6, this.serverLogsCookie);
      }
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.title);
      }
      if ((this.backendId != 0) || (this.hasBackendId)) {
        i += CodedOutputByteBufferNano.computeInt32Size(8, this.backendId);
      }
      if ((this.browseTab != null) && (this.browseTab.length > 0)) {
        for (int j = 0; j < this.browseTab.length; j++)
        {
          Browse.BrowseTab localBrowseTab = this.browseTab[j];
          if (localBrowseTab != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(9, localBrowseTab);
          }
        }
      }
      if ((this.hasLandingTabIndex) || (this.landingTabIndex != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(10, this.landingTabIndex);
      }
      if ((this.hasQuickLinkTabIndex) || (this.quickLinkTabIndex != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(11, this.quickLinkTabIndex);
      }
      if ((this.hasQuickLinkFallbackTabIndex) || (this.quickLinkFallbackTabIndex != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(12, this.quickLinkFallbackTabIndex);
      }
      if ((this.hasIsFamilySafe) || (this.isFamilySafe)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(14);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasContentsUrl) || (!this.contentsUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.contentsUrl);
      }
      if ((this.hasPromoUrl) || (!this.promoUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.promoUrl);
      }
      if ((this.category != null) && (this.category.length > 0)) {
        for (int m = 0; m < this.category.length; m++)
        {
          Browse.BrowseLink localBrowseLink2 = this.category[m];
          if (localBrowseLink2 != null) {
            paramCodedOutputByteBufferNano.writeMessage(3, localBrowseLink2);
          }
        }
      }
      if ((this.breadcrumb != null) && (this.breadcrumb.length > 0)) {
        for (int k = 0; k < this.breadcrumb.length; k++)
        {
          Browse.BrowseLink localBrowseLink1 = this.breadcrumb[k];
          if (localBrowseLink1 != null) {
            paramCodedOutputByteBufferNano.writeMessage(4, localBrowseLink1);
          }
        }
      }
      if ((this.quickLink != null) && (this.quickLink.length > 0)) {
        for (int j = 0; j < this.quickLink.length; j++)
        {
          Browse.QuickLink localQuickLink = this.quickLink[j];
          if (localQuickLink != null) {
            paramCodedOutputByteBufferNano.writeMessage(5, localQuickLink);
          }
        }
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(6, this.serverLogsCookie);
      }
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.title);
      }
      if ((this.backendId != 0) || (this.hasBackendId)) {
        paramCodedOutputByteBufferNano.writeInt32(8, this.backendId);
      }
      if ((this.browseTab != null) && (this.browseTab.length > 0)) {
        for (int i = 0; i < this.browseTab.length; i++)
        {
          Browse.BrowseTab localBrowseTab = this.browseTab[i];
          if (localBrowseTab != null) {
            paramCodedOutputByteBufferNano.writeMessage(9, localBrowseTab);
          }
        }
      }
      if ((this.hasLandingTabIndex) || (this.landingTabIndex != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(10, this.landingTabIndex);
      }
      if ((this.hasQuickLinkTabIndex) || (this.quickLinkTabIndex != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(11, this.quickLinkTabIndex);
      }
      if ((this.hasQuickLinkFallbackTabIndex) || (this.quickLinkFallbackTabIndex != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(12, this.quickLinkFallbackTabIndex);
      }
      if ((this.hasIsFamilySafe) || (this.isFamilySafe)) {
        paramCodedOutputByteBufferNano.writeBool(14, this.isFamilySafe);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class BrowseTab
    extends MessageNano
  {
    private static volatile BrowseTab[] _emptyArray;
    public int backendId = 0;
    public String categoriesTitle = "";
    public Browse.BrowseLink[] category = Browse.BrowseLink.emptyArray();
    public boolean hasBackendId = false;
    public boolean hasCategoriesTitle = false;
    public boolean hasHighlightsBannerUrl = false;
    public boolean hasListUrl = false;
    public boolean hasQuickLinkTitle = false;
    public boolean hasServerLogsCookie = false;
    public boolean hasTitle = false;
    public String highlightsBannerUrl = "";
    public String listUrl = "";
    public Browse.QuickLink[] quickLink = Browse.QuickLink.emptyArray();
    public String quickLinkTitle = "";
    public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
    public String title = "";
    
    public BrowseTab()
    {
      this.cachedSize = -1;
    }
    
    public static BrowseTab[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new BrowseTab[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(2, this.serverLogsCookie);
      }
      if ((this.hasListUrl) || (!this.listUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.listUrl);
      }
      if ((this.category != null) && (this.category.length > 0)) {
        for (int k = 0; k < this.category.length; k++)
        {
          Browse.BrowseLink localBrowseLink = this.category[k];
          if (localBrowseLink != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(4, localBrowseLink);
          }
        }
      }
      if ((this.quickLink != null) && (this.quickLink.length > 0)) {
        for (int j = 0; j < this.quickLink.length; j++)
        {
          Browse.QuickLink localQuickLink = this.quickLink[j];
          if (localQuickLink != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(5, localQuickLink);
          }
        }
      }
      if ((this.hasQuickLinkTitle) || (!this.quickLinkTitle.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(6, this.quickLinkTitle);
      }
      if ((this.hasCategoriesTitle) || (!this.categoriesTitle.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(7, this.categoriesTitle);
      }
      if ((this.backendId != 0) || (this.hasBackendId)) {
        i += CodedOutputByteBufferNano.computeInt32Size(8, this.backendId);
      }
      if ((this.hasHighlightsBannerUrl) || (!this.highlightsBannerUrl.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(9, this.highlightsBannerUrl);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.title);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(2, this.serverLogsCookie);
      }
      if ((this.hasListUrl) || (!this.listUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.listUrl);
      }
      if ((this.category != null) && (this.category.length > 0)) {
        for (int j = 0; j < this.category.length; j++)
        {
          Browse.BrowseLink localBrowseLink = this.category[j];
          if (localBrowseLink != null) {
            paramCodedOutputByteBufferNano.writeMessage(4, localBrowseLink);
          }
        }
      }
      if ((this.quickLink != null) && (this.quickLink.length > 0)) {
        for (int i = 0; i < this.quickLink.length; i++)
        {
          Browse.QuickLink localQuickLink = this.quickLink[i];
          if (localQuickLink != null) {
            paramCodedOutputByteBufferNano.writeMessage(5, localQuickLink);
          }
        }
      }
      if ((this.hasQuickLinkTitle) || (!this.quickLinkTitle.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(6, this.quickLinkTitle);
      }
      if ((this.hasCategoriesTitle) || (!this.categoriesTitle.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(7, this.categoriesTitle);
      }
      if ((this.backendId != 0) || (this.hasBackendId)) {
        paramCodedOutputByteBufferNano.writeInt32(8, this.backendId);
      }
      if ((this.hasHighlightsBannerUrl) || (!this.highlightsBannerUrl.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(9, this.highlightsBannerUrl);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class QuickLink
    extends MessageNano
  {
    private static volatile QuickLink[] _emptyArray;
    public int backendId = 0;
    public boolean displayRequired = false;
    public boolean hasBackendId = false;
    public boolean hasDisplayRequired = false;
    public boolean hasName = false;
    public boolean hasPrismStyle = false;
    public boolean hasServerLogsCookie = false;
    public Common.Image image = null;
    public ResolveLink.ResolvedLink link = null;
    public String name = "";
    public boolean prismStyle = false;
    public byte[] serverLogsCookie = WireFormatNano.EMPTY_BYTES;
    
    public QuickLink()
    {
      this.cachedSize = -1;
    }
    
    public static QuickLink[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new QuickLink[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasName) || (!this.name.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.name);
      }
      if (this.image != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.image);
      }
      if (this.link != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.link);
      }
      if ((this.hasDisplayRequired) || (this.displayRequired)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(4);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        i += CodedOutputByteBufferNano.computeBytesSize(5, this.serverLogsCookie);
      }
      if ((this.backendId != 0) || (this.hasBackendId)) {
        i += CodedOutputByteBufferNano.computeInt32Size(6, this.backendId);
      }
      if ((this.hasPrismStyle) || (this.prismStyle)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(7);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasName) || (!this.name.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.name);
      }
      if (this.image != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.image);
      }
      if (this.link != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.link);
      }
      if ((this.hasDisplayRequired) || (this.displayRequired)) {
        paramCodedOutputByteBufferNano.writeBool(4, this.displayRequired);
      }
      if ((this.hasServerLogsCookie) || (!Arrays.equals(this.serverLogsCookie, WireFormatNano.EMPTY_BYTES))) {
        paramCodedOutputByteBufferNano.writeBytes(5, this.serverLogsCookie);
      }
      if ((this.backendId != 0) || (this.hasBackendId)) {
        paramCodedOutputByteBufferNano.writeInt32(6, this.backendId);
      }
      if ((this.hasPrismStyle) || (this.prismStyle)) {
        paramCodedOutputByteBufferNano.writeBool(7, this.prismStyle);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Browse
 * JD-Core Version:    0.7.0.1
 */